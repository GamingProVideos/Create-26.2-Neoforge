package com.simibubi.create.client.flywheel.impl;

import com.simibubi.create.client.flywheel.api.backend.Backend;
import com.simibubi.create.client.flywheel.backend.BackendConfig;

public interface FlwConfig {
    String DEFAULT_BACKEND_STR = "DEFAULT";

    FlwConfig INSTANCE = FlwImplXplat.INSTANCE.getConfig();

    Backend backend();

    boolean limitUpdates();

    int workerThreads();

    BackendConfig backendConfig();
}
