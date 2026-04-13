package com.leedarson.event;

public class GetSDRecordListEvent {
    public String callback;
    public String endTime;
    public int eventType;
    public String hour;
    public String startTime;

    public GetSDRecordListEvent(String startTime2, String endTime2, int eventType2, String hour2, String callbackKey) {
        this.startTime = startTime2;
        this.endTime = endTime2;
        this.eventType = eventType2;
        this.hour = hour2;
        this.callback = callbackKey;
    }
}
