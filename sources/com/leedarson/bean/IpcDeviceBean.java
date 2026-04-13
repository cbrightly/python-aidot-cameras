package com.leedarson.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class IpcDeviceBean implements Parcelable {
    public static final Parcelable.Creator<IpcDeviceBean> CREATOR = new Parcelable.Creator<IpcDeviceBean>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public IpcDeviceBean createFromParcel(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 1044, new Class[]{Parcel.class}, IpcDeviceBean.class);
            if (proxy.isSupported) {
                return (IpcDeviceBean) proxy.result;
            }
            return new IpcDeviceBean(in);
        }

        public IpcDeviceBean[] newArray(int size) {
            return new IpcDeviceBean[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    public String account;
    public boolean cloudPlayback = true;
    public String firmwareVersion = "";
    public String hardwareVersion = "";
    public String houseId;
    public String id;
    public Boolean isCurrentDevice = false;
    public boolean isOwner = true;
    public String modelId;
    public String name;
    public Boolean online;
    public String p2pId;
    public String password;
    public String picture;
    public String productId;
    public PropsBean props;
    public PushBean pushBean;
    public Boolean share;
    public String simpleVersion = "";
    public String type;

    public boolean isLowPowerDevice() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1032, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String str = this.modelId;
        if (str != null) {
            return str.contains("IPC.A001108") || this.modelId.contains("IPC.A001360") || this.modelId.contains("LK.IPC.A001513");
        }
        return false;
    }

    public boolean isLS101() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1033, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String str = this.modelId;
        return str != null && str.contains("IPC.A001360");
    }

    public boolean hasPTZ() {
        PushBean pushBean2 = this.pushBean;
        return pushBean2 != null && pushBean2.PTZ;
    }

    public boolean hasLight() {
        PushBean pushBean2 = this.pushBean;
        return pushBean2 != null && (pushBean2.light || pushBean2.DimmingEnable);
    }

    public boolean hasPath() {
        PushBean pushBean2 = this.pushBean;
        return pushBean2 != null && pushBean2.radarConfig;
    }

    public int getRadarPhyRadius() {
        int[] iArr = this.pushBean.radarRangeConfig;
        if (iArr == null || iArr.length <= 0) {
            return 0;
        }
        return iArr[0];
    }

    public boolean isMultiLightControl() {
        PushBean pushBean2 = this.pushBean;
        return pushBean2 != null && pushBean2.DimmingEnable;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0025, code lost:
        r2 = r1.props;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isCriticalBattery() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Boolean.TYPE
            r4 = 0
            r5 = 1034(0x40a, float:1.449E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x001e
            java.lang.Object r0 = r1.result
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            return r0
        L_0x001e:
            r1 = r8
            boolean r2 = r1.isLowPowerDevice()
            if (r2 == 0) goto L_0x003e
            com.leedarson.bean.PropsBean r2 = r1.props
            if (r2 == 0) goto L_0x003e
            java.lang.String r2 = r2.lowPowerStatus
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x003e
            com.leedarson.bean.PropsBean r2 = r1.props
            java.lang.String r2 = r2.lowPowerStatus
            java.lang.String r3 = "1"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x003e
            r0 = 1
        L_0x003e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.bean.IpcDeviceBean.isCriticalBattery():boolean");
    }

    public IpcDeviceBean(Parcel in) {
        Boolean bool;
        Boolean bool2;
        boolean z = false;
        this.id = in.readString();
        this.type = in.readString();
        this.modelId = in.readString();
        this.p2pId = in.readString();
        this.password = in.readString();
        this.picture = in.readString();
        this.name = in.readString();
        byte tmpOnline = in.readByte();
        Boolean bool3 = null;
        if (tmpOnline == 0) {
            bool = null;
        } else {
            bool = Boolean.valueOf(tmpOnline == 1);
        }
        this.online = bool;
        this.props = (PropsBean) in.readParcelable(PropsBean.class.getClassLoader());
        this.pushBean = (PushBean) in.readParcelable(PushBean.class.getClassLoader());
        byte tmpisCurrent = in.readByte();
        if (tmpisCurrent == 0) {
            bool2 = null;
        } else {
            bool2 = Boolean.valueOf(tmpisCurrent == 1);
        }
        this.isCurrentDevice = bool2;
        this.account = in.readString();
        this.productId = in.readString();
        this.isOwner = in.readByte() != 0;
        this.cloudPlayback = in.readByte() != 0;
        this.houseId = in.readString();
        byte tempShare = in.readByte();
        if (tempShare != 0) {
            bool3 = Boolean.valueOf(tempShare == 1 ? true : z);
        }
        this.share = bool3;
        this.hardwareVersion = in.readString();
        this.firmwareVersion = in.readString();
        this.simpleVersion = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i = 2;
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 1035, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeString(this.id);
            dest.writeString(this.type);
            dest.writeString(this.modelId);
            dest.writeString(this.p2pId);
            dest.writeString(this.password);
            dest.writeString(this.picture);
            dest.writeString(this.name);
            Boolean bool = this.online;
            dest.writeByte((byte) (bool == null ? 0 : bool.booleanValue() ? 1 : 2));
            dest.writeParcelable(this.props, flags);
            dest.writeParcelable(this.pushBean, flags);
            Boolean bool2 = this.isCurrentDevice;
            dest.writeByte((byte) (bool2 == null ? 0 : bool2.booleanValue() ? 1 : 2));
            dest.writeString(this.account);
            dest.writeString(this.productId);
            dest.writeByte(this.isOwner ? (byte) 1 : 0);
            dest.writeByte(this.cloudPlayback ? (byte) 1 : 0);
            dest.writeString(this.houseId);
            Boolean bool3 = this.share;
            if (bool3 == null) {
                i = 0;
            } else if (bool3.booleanValue()) {
                i = 1;
            }
            dest.writeByte((byte) i);
            dest.writeString(this.hardwareVersion);
            dest.writeString(this.firmwareVersion);
            dest.writeString(this.simpleVersion);
        }
    }

    private void refreshPushBean() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1036, new Class[0], Void.TYPE).isSupported) {
            if (this.pushBean == null) {
                String thingStr = SharePreferenceUtils.getPrefString(BaseApplication.b(), this.productId, "");
                if (!TextUtils.isEmpty(thingStr)) {
                    this.pushBean = (PushBean) new Gson().fromJson(thingStr, new TypeToken<PushBean>() {
                    }.getType());
                }
            }
        }
    }

    public float getAspectRatio() {
        String[] str;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1037, new Class[0], Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        try {
            refreshPushBean();
            PushBean pushBean2 = this.pushBean;
            if (pushBean2 == null || TextUtils.isEmpty(pushBean2.aspectRatio) || !this.pushBean.aspectRatio.contains("/") || (str = this.pushBean.aspectRatio.split("/")) == null || str.length != 2) {
                return 1.7777778f;
            }
            return Float.parseFloat(str[0]) / Float.parseFloat(str[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return 1.7777778f;
        }
    }

    public float getPlayerAspectRatio() {
        String[] str;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1038, new Class[0], Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        try {
            refreshPushBean();
            PushBean pushBean2 = this.pushBean;
            if (pushBean2 == null || TextUtils.isEmpty(pushBean2.playerAspectRatio) || !this.pushBean.playerAspectRatio.contains("/") || (str = this.pushBean.playerAspectRatio.split("/")) == null || str.length != 2) {
                return 1.7777778f;
            }
            return Float.parseFloat(str[0]) / Float.parseFloat(str[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return 1.7777778f;
        }
    }

    public boolean liveHasFocus() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1039, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        refreshPushBean();
        PushBean pushBean2 = this.pushBean;
        if (pushBean2 != null) {
            return pushBean2.liveHasFocus();
        }
        return false;
    }

    public boolean eventHasFocus() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1040, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        refreshPushBean();
        PushBean pushBean2 = this.pushBean;
        if (pushBean2 != null) {
            return pushBean2.eventHasFocus();
        }
        return false;
    }

    public int getPlayerFillMode() {
        PushBean pushBean2 = this.pushBean;
        if (pushBean2 != null) {
            return pushBean2.playerFillMode;
        }
        return 0;
    }

    public boolean isSupportPreCon() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1041, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        PropsBean propsBean = this.props;
        if (propsBean != null) {
            return propsBean.isSupportPreCon();
        }
        return false;
    }

    public boolean isOwner() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1042, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!this.isOwner || this.share.booleanValue()) {
            return false;
        }
        return true;
    }

    public int getSpkNSLevel() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1043, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            PropsBean propsBean = this.props;
            if (propsBean == null || TextUtils.isEmpty(propsBean.spkNSLevel)) {
                return -1;
            }
            return Integer.parseInt(this.props.spkNSLevel);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
