package com.simibubi.create.compat.computercraft.events;

import com.simibubi.create.content.trains.entity.Train;

public class TrainPassEvent implements ComputerEvent {

    public Train train;
    public boolean passing;

    public TrainPassEvent(Train train, boolean passing) {
        this.train = train;
        this.passing = passing;
    }

}
