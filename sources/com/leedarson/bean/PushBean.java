package com.leedarson.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.android.arouter.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class PushBean implements Parcelable {
    public static final Parcelable.Creator<PushBean> CREATOR = new Parcelable.Creator<PushBean>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public PushBean createFromParcel(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 1067, new Class[]{Parcel.class}, PushBean.class);
            if (proxy.isSupported) {
                return (PushBean) proxy.result;
            }
            return new PushBean(in);
        }

        public PushBean[] newArray(int size) {
            return new PushBean[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    public boolean DimmingEnable = false;
    public boolean PTZ = false;
    public int[] PTZDirection;
    public String account;
    public boolean activityZoneConfig = false;
    public String aspectRatio;
    public String defaultResolution;
    public String deviceId;
    public int digitZoom;
    public int[] humanFocusConfig;
    public boolean light = false;
    private int max_res = -1;
    private int min_res = -1;
    public boolean mute = true;
    public String playerAspectRatio;
    public int playerFillMode;
    public String poster;
    public boolean privacyConfig = false;
    public boolean radarConfig;
    public int[] radarRangeConfig;
    public int[] resolution;
    public boolean siren = false;
    public boolean tracing = false;

    public PushBean() {
    }

    public PushBean(Parcel in) {
        boolean z = false;
        this.deviceId = in.readString();
        this.account = in.readString();
        this.activityZoneConfig = in.readByte() != 0;
        this.privacyConfig = in.readByte() != 0;
        this.siren = in.readByte() != 0;
        this.light = in.readByte() != 0;
        this.tracing = in.readByte() != 0;
        this.PTZ = in.readByte() != 0;
        this.mute = in.readByte() != 0;
        this.PTZDirection = in.createIntArray();
        this.poster = in.readString();
        this.resolution = in.createIntArray();
        this.digitZoom = in.readInt();
        this.defaultResolution = in.readString();
        this.DimmingEnable = in.readByte() != 0;
        this.aspectRatio = in.readString();
        this.playerAspectRatio = in.readString();
        this.playerFillMode = in.readInt();
        this.humanFocusConfig = in.createIntArray();
        this.radarConfig = in.readInt() != 0 ? true : z;
        this.radarRangeConfig = in.createIntArray();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 1065, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeString(this.deviceId);
            dest.writeString(this.account);
            dest.writeByte(this.activityZoneConfig ? (byte) 1 : 0);
            dest.writeByte(this.privacyConfig ? (byte) 1 : 0);
            dest.writeByte(this.siren ? (byte) 1 : 0);
            dest.writeByte(this.light ? (byte) 1 : 0);
            dest.writeByte(this.tracing ? (byte) 1 : 0);
            dest.writeByte(this.PTZ ? (byte) 1 : 0);
            dest.writeByte(this.mute ? (byte) 1 : 0);
            dest.writeIntArray(this.PTZDirection);
            dest.writeString(this.poster);
            dest.writeIntArray(this.resolution);
            dest.writeInt(this.digitZoom);
            dest.writeString(this.defaultResolution);
            dest.writeByte(this.DimmingEnable ? (byte) 1 : 0);
            dest.writeString(this.aspectRatio);
            dest.writeString(this.playerAspectRatio);
            dest.writeInt(this.playerFillMode);
            dest.writeIntArray(this.humanFocusConfig);
            dest.writeByte(this.radarConfig ? (byte) 1 : 0);
            dest.writeIntArray(this.radarRangeConfig);
        }
    }

    public int getMax_res() {
        return this.max_res;
    }

    public void setMax_res(int max_res2) {
        this.max_res = max_res2;
    }

    public int getMin_res() {
        return this.min_res;
    }

    public void setMin_res(int min_res2) {
        this.min_res = min_res2;
    }

    public boolean isDefaultHD() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1066, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (e.b(this.defaultResolution)) {
            return false;
        }
        String str = this.defaultResolution;
        if (str.equals(this.max_res + "")) {
            return true;
        }
        return false;
    }

    public boolean liveHasFocus() {
        int[] iArr = this.humanFocusConfig;
        if (iArr == null || iArr.length <= 0) {
            return false;
        }
        int i = 0;
        while (true) {
            int[] iArr2 = this.humanFocusConfig;
            if (i >= iArr2.length) {
                return false;
            }
            if (iArr2[i] == 1) {
                return true;
            }
            i++;
        }
    }

    public boolean eventHasFocus() {
        int[] iArr = this.humanFocusConfig;
        if (iArr == null || iArr.length <= 0) {
            return false;
        }
        int i = 0;
        while (true) {
            int[] iArr2 = this.humanFocusConfig;
            if (i >= iArr2.length) {
                return false;
            }
            if (iArr2[i] == 2) {
                return true;
            }
            i++;
        }
    }
}
