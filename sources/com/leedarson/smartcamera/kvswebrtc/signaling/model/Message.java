package com.leedarson.smartcamera.kvswebrtc.signaling.model;

import android.util.Base64;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.webrtc.SessionDescription;

public class Message {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String action;
    private String messagePayload;
    private String recipientClientId;
    private String senderClientId;

    public Message() {
    }

    public Message(String action2, String recipientClientId2, String senderClientId2, String messagePayload2) {
        this.action = action2;
        this.recipientClientId = recipientClientId2;
        this.senderClientId = senderClientId2;
        this.messagePayload = messagePayload2;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action2) {
        this.action = action2;
    }

    public String getRecipientClientId() {
        return this.recipientClientId;
    }

    public void setRecipientClientId(String recipientClientId2) {
        this.recipientClientId = recipientClientId2;
    }

    public String getSenderClientId() {
        return this.senderClientId;
    }

    public void setSenderClientId(String senderClientId2) {
        this.senderClientId = senderClientId2;
    }

    public String getMessagePayload() {
        return this.messagePayload;
    }

    public void setMessagePayload(String messagePayload2) {
        this.messagePayload = messagePayload2;
    }

    public static Message createAnswerMessage(SessionDescription sessionDescription, boolean z, String recipientClientId2) {
        Object[] objArr = {sessionDescription, new Byte(z ? (byte) 1 : 0), recipientClientId2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 10000, new Class[]{SessionDescription.class, Boolean.TYPE, String.class}, Message.class);
        if (proxy.isSupported) {
            return (Message) proxy.result;
        }
        String description = sessionDescription.description;
        return new Message("SDP_ANSWER", recipientClientId2, "", new String(Base64.encode(("{\"type\":\"answer\",\"sdp\":\"" + description.replace("\r\n", "\\r\\n") + "\"}").getBytes(), 11)));
    }

    public static Message createOfferMessage(SessionDescription sessionDescription, String clientId) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{sessionDescription, clientId}, (Object) null, changeQuickRedirect2, true, 10001, new Class[]{SessionDescription.class, String.class}, Message.class);
        if (proxy.isSupported) {
            return (Message) proxy.result;
        }
        String description = sessionDescription.description;
        return new Message("SDP_OFFER", "", clientId, new String(Base64.encode(("{\"type\":\"offer\",\"sdp\":\"" + description.replace("\r\n", "\\r\\n") + "\"}").getBytes(), 11)));
    }
}
