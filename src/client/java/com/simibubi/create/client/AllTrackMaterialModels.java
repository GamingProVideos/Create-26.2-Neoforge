package com.simibubi.create.client;

import com.simibubi.create.AllTrackMaterials;
import com.simibubi.create.client.flywheel.lib.model.baked.PartialModel;
import com.simibubi.create.content.trains.track.TrackMaterial;

public class AllTrackMaterialModels {
    static final TrackModelHolder ANDESITE = new TrackModelHolder(
        AllPartialModels.TRACK_TIE,
        AllPartialModels.TRACK_SEGMENT_LEFT,
        AllPartialModels.TRACK_SEGMENT_RIGHT
    );

    public record TrackModelHolder(PartialModel tie, PartialModel leftSegment, PartialModel rightSegment) {
    }

    public static void register(TrackMaterial material, TrackModelHolder holder) {
        material.modelHolder = holder;
    }

    public static void register() {
        register(AllTrackMaterials.ANDESITE, ANDESITE);
    }
}
