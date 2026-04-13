package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class FcmMessageArrivedEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String messageBody;
    private String tag;
    private String title;

    public FcmMessageArrivedEvent(String title2, String messageBody2, String tag2) {
        this.title = title2;
        this.messageBody = messageBody2;
        this.tag = tag2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public String getMessageBody() {
        return this.messageBody;
    }

    public void setMessageBody(String messageBody2) {
        this.messageBody = messageBody2;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag2) {
        this.tag = tag2;
    }
}
