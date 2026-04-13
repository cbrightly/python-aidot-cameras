package com.leedarson.smartcamera.utils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.Serializable;

/* compiled from: StreamData */
public class h implements Serializable {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte[] bytes;
    private int codec;
    private boolean isAudio = false;
    private long timestap;

    public h() {
    }

    public h(byte[] bytes2, long timestap2, boolean isAudio2, int codec2) {
        this.bytes = bytes2;
        this.timestap = timestap2;
        this.isAudio = isAudio2;
        this.codec = codec2;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public void setBytes(byte[] bytes2) {
        this.bytes = bytes2;
    }

    public long getTimestap() {
        return this.timestap;
    }

    public void setTimestap(int timestap2) {
        this.timestap = (long) timestap2;
    }

    public boolean isAudio() {
        return this.isAudio;
    }

    public void setAudio(boolean audio) {
        this.isAudio = audio;
    }

    public int getCodec() {
        return this.codec;
    }

    public void setCodec(int codec2) {
        this.codec = codec2;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10517, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "VideoData{, timestap=" + this.timestap + '}';
    }
}
