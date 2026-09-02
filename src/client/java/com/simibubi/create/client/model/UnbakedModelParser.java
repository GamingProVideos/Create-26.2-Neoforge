/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package com.simibubi.create.client.model;

import com.google.gson.*;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.internal.bind.TreeTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.mojang.math.Transformation;
import net.neoforged.neoforge.client.model.obj.ObjLoader;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.client.resources.model.cuboid.CuboidModel;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Type;

public class UnbakedModelParser {
    public static Gson wrap(Gson gson) {
        return new GsonBuilder().registerTypeAdapterFactory(new Deserializer(gson))
            .registerTypeAdapter(Transformation.class, new TransformationHelper.Deserializer()).create();
    }

    public static class Deserializer implements JsonDeserializer<UnbakedModel>, TypeAdapterFactory {
        private static final TypeToken<? extends UnbakedModel> NEXT_TYPE = TypeToken.get(CuboidModel.class);
        private final Gson gson;
        private @Nullable TypeAdapter<?> cached;

        public Deserializer(Gson gson) {
            this.gson = gson;
        }

        @Override
        public UnbakedModel deserialize(
            JsonElement jsonElement,
            Type type,
            JsonDeserializationContext jsonDeserializationContext
        ) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement element = jsonObject.get("loader");
            if (element != null && element.isJsonPrimitive()) {
                if (element.getAsString().equals("neoforge:obj")) {
                    // Use NeoForge's own 26.2 OBJ implementation. Create previously
                    // carried a copied pre-fix loader which ignored part of ModelState;
                    // that breaks rotated/UV-locked OBJ models such as large water wheels
                    // and the chain conveyor's animated OBJ pieces.
                    return ObjLoader.INSTANCE.read(jsonObject, jsonDeserializationContext);
                }

                // Another mod owns this custom loader. Delegate to the Gson
                // instance that was installed before Create wrapped it. The
                // old fallback forced every unknown loader through CuboidModel,
                // breaking Sophisticated Backpacks and Sophisticated Storage.
                return gson.fromJson(new JsonTreeReader(jsonObject), UnbakedModel.class);
            }
            return gson.fromJson(new JsonTreeReader(jsonObject), NEXT_TYPE);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson proxy, TypeToken<T> type) {
            if (type.getRawType() == UnbakedModel.class) {
                if (cached != null) {
                    return (TypeAdapter<T>) cached;
                }
                TreeTypeAdapter<T> adapter = new TreeTypeAdapter<>(null, (JsonDeserializer<T>) this, proxy, type, this);
                cached = adapter;
                return adapter;
            }
            return gson.getAdapter(type);
        }
    }
}
