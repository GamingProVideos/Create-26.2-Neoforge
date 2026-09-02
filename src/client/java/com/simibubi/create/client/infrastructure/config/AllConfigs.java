package com.simibubi.create.client.infrastructure.config;

import com.simibubi.create.catnip.config.Builder;

import static com.simibubi.create.Create.MOD_ID;

public class AllConfigs {
    private static CClient client;

    public static CClient client() {
        return client;
    }

    public static void register() {
        client = Builder.create(CClient::new, MOD_ID, "client");
    }
}
