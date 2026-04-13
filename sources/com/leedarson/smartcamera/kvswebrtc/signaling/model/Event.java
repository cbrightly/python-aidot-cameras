package com.leedarson.smartcamera.kvswebrtc.signaling.model;

import android.util.Base64;
import android.util.Log;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.webrtc.IceCandidate;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class Event {
    private static final String TAG = "Event";
    public static ChangeQuickRedirect changeQuickRedirect;
    private String body;
    private String messagePayload;
    private String messageType;
    private boolean pskEnable = false;
    private String sdp;
    private String senderClientId;
    private String statusCode;
    private int supportRtpExt;
    private int trackId = 0;

    public int getSupportRtpExt() {
        return this.supportRtpExt;
    }

    public void setSupportRtpExt(int supportRtpExt2) {
        this.supportRtpExt = supportRtpExt2;
    }

    public boolean getPskEnable() {
        return this.pskEnable;
    }

    public void setPskEnable(boolean pskEnable2) {
        this.pskEnable = pskEnable2;
    }

    public int getTrackId() {
        return this.trackId;
    }

    public void setTrackId(int trackId2) {
        this.trackId = trackId2;
    }

    public String getSdp() {
        return this.sdp;
    }

    public void setSdp(String sdp2) {
        this.sdp = sdp2;
    }

    public void setSenderClientId(String senderClientId2) {
        this.senderClientId = senderClientId2;
    }

    public void setMessageType(String messageType2) {
        this.messageType = messageType2;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(String statusCode2) {
        this.statusCode = statusCode2;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body2) {
        this.body = body2;
    }

    public String getSenderClientId() {
        return this.senderClientId;
    }

    public String getMessageType() {
        return this.messageType;
    }

    public String getMessagePayload() {
        return this.messagePayload;
    }

    public Event() {
    }

    public Event(String senderClientId2, String messageType2, String messagePayload2) {
        this.senderClientId = senderClientId2;
        this.messageType = messageType2;
        this.messagePayload = messagePayload2;
    }

    public static IceCandidate parseIceCandidate(Event event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, (Object) null, changeQuickRedirect, true, 9996, new Class[]{Event.class}, IceCandidate.class);
        if (proxy.isSupported) {
            return (IceCandidate) proxy.result;
        }
        JsonObject jsonObject = JsonParser.parseString(new String(Base64.decode(event.getMessagePayload(), 0))).getAsJsonObject();
        String sdpMid = jsonObject.get("sdpMid").toString();
        if (sdpMid.length() > 2) {
            sdpMid = sdpMid.substring(1, sdpMid.length() - 1);
        }
        try {
            int sdpMLineIndex = Integer.parseInt(jsonObject.get("sdpMLineIndex").toString());
            String candidate = jsonObject.get("candidate").toString();
            if (candidate.length() > 2) {
                candidate = candidate.substring(1, candidate.length() - 1);
            }
            return new IceCandidate(sdpMid, sdpMLineIndex, candidate);
        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid sdpMLineIndex");
            return null;
        }
    }

    public static String parseSdpEvent(Event answerEvent) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{answerEvent}, (Object) null, changeQuickRedirect, true, 9997, new Class[]{Event.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        JsonObject jsonObject = JsonParser.parseString(new String(Base64.decode(answerEvent.getMessagePayload().getBytes(), 0))).getAsJsonObject();
        if (!jsonObject.get(IjkMediaMeta.IJKM_KEY_TYPE).toString().equalsIgnoreCase("\"answer\"")) {
            Log.e(TAG, "Error in answer message");
        }
        String sdp2 = jsonObject.get("sdp").getAsString();
        Log.d(TAG, "SDP answer received from master:" + sdp2);
        return sdp2;
    }

    public static String parseOfferEvent(Event offerEvent) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{offerEvent}, (Object) null, changeQuickRedirect, true, 9998, new Class[]{Event.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return JsonParser.parseString(new String(Base64.decode(offerEvent.getMessagePayload(), 0))).getAsJsonObject().get("sdp").getAsString();
    }
}
