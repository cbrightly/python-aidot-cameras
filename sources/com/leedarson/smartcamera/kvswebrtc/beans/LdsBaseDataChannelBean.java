package com.leedarson.smartcamera.kvswebrtc.beans;

import com.meituan.robust.ChangeQuickRedirect;

public class LdsBaseDataChannelBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte[] rawBytes;

    public byte[] getRawBytes() {
        return this.rawBytes;
    }

    public void processBytes(byte[] bytes) {
        this.rawBytes = bytes;
    }
}
