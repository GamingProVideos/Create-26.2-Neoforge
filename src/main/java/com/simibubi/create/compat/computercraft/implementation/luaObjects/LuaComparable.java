package com.simibubi.create.compat.computercraft.implementation.luaObjects;

import org.jspecify.annotations.Nullable;

import java.util.Map;

public interface LuaComparable {
    @Nullable Map<?, ?> getTableRepresentation();
}

