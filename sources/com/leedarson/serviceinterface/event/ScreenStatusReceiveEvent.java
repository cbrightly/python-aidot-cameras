package com.leedarson.serviceinterface.event;

public class ScreenStatusReceiveEvent {
    public boolean screenOn = true;

    public ScreenStatusReceiveEvent(boolean screenOn2) {
        this.screenOn = screenOn2;
    }
}
