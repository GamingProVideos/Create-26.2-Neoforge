package com.simibubi.create.client.content.trains;

import com.simibubi.create.client.Create;
import com.simibubi.create.client.content.kinetics.KineticDebugger;
import com.simibubi.create.client.content.trains.graph.TrackGraphVisualizer;
import com.simibubi.create.client.infrastructure.config.AllConfigs;
import com.simibubi.create.content.trains.graph.TrackGraph;
import net.minecraft.client.Minecraft;

public class GlobalRailwayManagerClient {
    public static void tickSignalOverlay(Minecraft mc) {
        if (!isTrackGraphDebugActive()) {
            for (TrackGraph trackGraph : Create.RAILWAYS.trackNetworks.values()) {
                TrackGraphVisualizer.visualiseSignalEdgeGroups(mc, trackGraph);
            }
        }
    }

    public static void tick(Minecraft mc) {
        if (isTrackGraphDebugActive()) {
            for (TrackGraph trackGraph : Create.RAILWAYS.trackNetworks.values()) {
                TrackGraphVisualizer.debugViewGraph(mc, trackGraph, isTrackGraphDebugExtended());
            }
        }
    }

    private static boolean isTrackGraphDebugActive() {
        return KineticDebugger.isF3DebugModeActive() && AllConfigs.client().showTrackGraphOnF3.get();
    }

    private static boolean isTrackGraphDebugExtended() {
        return AllConfigs.client().showExtendedTrackGraphOnF3.get();
    }
}
