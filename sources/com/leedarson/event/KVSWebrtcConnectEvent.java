package com.leedarson.event;

import com.meituan.robust.ChangeQuickRedirect;

public class KVSWebrtcConnectEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String awsAccessKey;
    private String awsSecretKey;
    private String callback;
    private String channelArn;
    private String deviceId;
    private String region;
    private String sessionToken;

    public KVSWebrtcConnectEvent(String callback2, String awsAccessKey2, String awsSecretKey2, String sessionToken2, String channelArn2, String region2, String deviceId2) {
        this.callback = callback2;
        this.awsAccessKey = awsAccessKey2;
        this.awsSecretKey = awsSecretKey2;
        this.sessionToken = sessionToken2;
        this.channelArn = channelArn2;
        this.region = region2;
        this.deviceId = deviceId2;
    }

    public String getCallback() {
        return this.callback;
    }

    public String getAwsAccessKey() {
        return this.awsAccessKey;
    }

    public String getAwsSecretKey() {
        return this.awsSecretKey;
    }

    public String getSessionToken() {
        return this.sessionToken;
    }

    public String getChannelArn() {
        return this.channelArn;
    }

    public String getRegion() {
        return this.region;
    }

    public String getDeviceId() {
        return this.deviceId;
    }
}
