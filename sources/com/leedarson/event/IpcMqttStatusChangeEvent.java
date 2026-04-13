package com.leedarson.event;

public class IpcMqttStatusChangeEvent {
    public int standbyStatus;
    public boolean status;

    public IpcMqttStatusChangeEvent(boolean status2, int standbyStatus2) {
        this.status = status2;
        this.standbyStatus = standbyStatus2;
    }
}
