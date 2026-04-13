package com.leedarson.serviceinterface.event;

public class RecordPlayStatusEvent {
    public static final int STATE_PAUSE = 0;
    public static final int STATE_PLAY = 1;
    public int state;

    public RecordPlayStatusEvent(int state2) {
        this.state = state2;
    }
}
