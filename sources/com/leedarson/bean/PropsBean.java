package com.leedarson.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.util.ArrayList;
import java.util.List;

public class PropsBean implements Parcelable {
    public static final Parcelable.Creator<PropsBean> CREATOR = new Parcelable.Creator<PropsBean>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public PropsBean createFromParcel(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 1062, new Class[]{Parcel.class}, PropsBean.class);
            if (proxy.isSupported) {
                return (PropsBean) proxy.result;
            }
            return new PropsBean(in);
        }

        public PropsBean[] newArray(int size) {
            return new PropsBean[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    public String Battery_remaining;
    public String Dimming;
    public boolean LightOnOff;
    public String MotionDetection_Enable = "0";
    public int PrivacyNo = 0;
    public String SdcardRecord_Type;
    public String StreamType = "";
    public boolean TurnOnOff = true;
    public String WifiStrength;
    public int alarmType;
    public String audioCodec;
    public String charging;
    public String digitZoom;
    public String dynamicStream = "";
    public int enableSdes = 0;
    public boolean isAuto;
    public String isDTLS;
    public boolean isSupportAuto;
    public String liveType;
    public String lowPowerStatus;
    public int micEnable = 1;
    public String networkRssi;
    public String p2pCache;
    public boolean sirenRing;
    public String spkNSLevel;
    public String splicingDistance;
    public String sptPreconn;
    public String supportIpv6;
    public String talkMode;
    public boolean trackingMode;
    public String videoCodec;

    public ArrayList<String> getVideoCodesArr() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETGUARD_RESP, new Class[0], ArrayList.class);
        if (proxy.isSupported) {
            return (ArrayList) proxy.result;
        }
        try {
            return (ArrayList) new Gson().fromJson(this.videoCodec, new TypeToken<List<String>>() {
            }.getType());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public PropsBean(Parcel in) {
        boolean z = true;
        this.liveType = in.readString();
        this.isDTLS = in.readString();
        this.talkMode = in.readString();
        this.digitZoom = in.readString();
        this.TurnOnOff = in.readByte() != 0;
        this.LightOnOff = in.readByte() != 0;
        this.trackingMode = in.readByte() != 0;
        this.sirenRing = in.readByte() != 0;
        this.micEnable = in.readInt();
        this.SdcardRecord_Type = in.readString();
        this.Battery_remaining = in.readString();
        this.audioCodec = in.readString();
        this.PrivacyNo = in.readInt();
        this.supportIpv6 = in.readString();
        this.MotionDetection_Enable = in.readString();
        this.charging = in.readString();
        this.videoCodec = in.readString();
        this.Dimming = in.readString();
        this.alarmType = in.readInt();
        this.sptPreconn = in.readString();
        this.lowPowerStatus = in.readString();
        this.splicingDistance = in.readString();
        this.p2pCache = in.readString();
        this.enableSdes = in.readInt();
        this.spkNSLevel = in.readString();
        this.isSupportAuto = in.readByte() != 0;
        this.isAuto = in.readByte() == 0 ? false : z;
        this.networkRssi = in.readString();
        this.WifiStrength = in.readString();
        this.dynamicStream = in.readString();
        this.StreamType = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETGUARD_REQ, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeString(this.liveType);
            dest.writeString(this.isDTLS);
            dest.writeString(this.talkMode);
            dest.writeString(this.digitZoom);
            dest.writeByte(this.TurnOnOff ? (byte) 1 : 0);
            dest.writeByte(this.LightOnOff ? (byte) 1 : 0);
            dest.writeByte(this.trackingMode ? (byte) 1 : 0);
            dest.writeByte(this.sirenRing ? (byte) 1 : 0);
            dest.writeInt(this.micEnable);
            dest.writeString(this.SdcardRecord_Type);
            dest.writeString(this.Battery_remaining);
            dest.writeString(this.audioCodec);
            dest.writeInt(this.PrivacyNo);
            dest.writeString(this.supportIpv6);
            dest.writeString(this.MotionDetection_Enable);
            dest.writeString(this.charging);
            dest.writeString(this.videoCodec);
            dest.writeString(this.Dimming);
            dest.writeInt(this.alarmType);
            dest.writeString(this.sptPreconn);
            dest.writeString(this.lowPowerStatus);
            dest.writeString(this.splicingDistance);
            dest.writeString(this.p2pCache);
            dest.writeInt(this.enableSdes);
            dest.writeString(this.spkNSLevel);
            dest.writeByte(this.isSupportAuto ? (byte) 1 : 0);
            dest.writeByte(this.isAuto ? (byte) 1 : 0);
            dest.writeString(this.networkRssi);
            dest.writeString(this.WifiStrength);
            dest.writeString(this.dynamicStream);
            dest.writeString(this.StreamType);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r2.alarmType;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isAutoAlarming() {
        /*
            r2 = this;
            boolean r0 = r2.sirenRing
            if (r0 == 0) goto L_0x000e
            int r0 = r2.alarmType
            if (r0 <= 0) goto L_0x000e
            r1 = 128(0x80, float:1.794E-43)
            if (r0 == r1) goto L_0x000e
            r0 = 1
            goto L_0x000f
        L_0x000e:
            r0 = 0
        L_0x000f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.bean.PropsBean.isAutoAlarming():boolean");
    }

    public boolean isSupportPreCon() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETGUARD_RESP, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return !TextUtils.isEmpty(this.sptPreconn) && this.sptPreconn.equals("1");
    }

    public int getSplicingDistance() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1060, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            return Integer.parseInt(this.splicingDistance);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 4;
        }
    }

    public boolean isSupportDynamicStream() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1061, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return !TextUtils.isEmpty(this.dynamicStream) && "1".equals(this.dynamicStream);
    }
}
