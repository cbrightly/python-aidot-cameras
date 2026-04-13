package com.leedarson.event;

public class DeleteRecordEvent {
    public String callback;
    public String[] eventStrs;
    public int eventType;
    public int type;

    public DeleteRecordEvent(String[] eventStrs2, int eventType2, int type2, String callbackKey) {
        this.eventStrs = eventStrs2;
        this.eventType = eventType2;
        this.type = type2;
        this.callback = callbackKey;
    }
}
