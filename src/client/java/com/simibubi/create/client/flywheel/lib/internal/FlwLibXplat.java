package com.simibubi.create.client.flywheel.lib.internal;

import com.simibubi.create.client.flywheel.impl.FlwLibXplatImpl;
import com.simibubi.create.client.flywheel.lib.model.SimpleModel;
import com.simibubi.create.client.flywheel.lib.model.baked.BakedModelBuilder;
import com.simibubi.create.client.flywheel.lib.model.baked.BlockModelBuilder;

public interface FlwLibXplat {
    FlwLibXplat INSTANCE = new FlwLibXplatImpl();

    SimpleModel buildBakedModelBuilder(BakedModelBuilder builder);

    SimpleModel buildBlockModelBuilder(BlockModelBuilder builder);
}
