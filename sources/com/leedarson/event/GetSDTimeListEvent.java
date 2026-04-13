package com.leedarson.event;

public class GetSDTimeListEvent {
    public String callback;
    public String endTime;
    public int eventType;
    public String startTime;

    public GetSDTimeListEvent(String startTime2, String endTime2, int eventType2, String callbackKey) {
        this.startTime = startTime2;
        this.endTime = endTime2;
        this.eventType = eventType2;
        this.callback = callbackKey;
    }
}
