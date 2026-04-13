package com.leedarson.smartcamera.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.android.arouter.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class KVSParamBean implements Parcelable {
    public static final Parcelable.Creator<KVSParamBean> CREATOR = new Parcelable.Creator<KVSParamBean>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public KVSParamBean createFromParcel(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 9638, new Class[]{Parcel.class}, KVSParamBean.class);
            if (proxy.isSupported) {
                return (KVSParamBean) proxy.result;
            }
            return new KVSParamBean(in);
        }

        public KVSParamBean[] newArray(int size) {
            return new KVSParamBean[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    public String accessKeyId;
    public String channelArn;
    public long expiresIn;
    public long nowTime;
    public String region;
    public String secretAccessKey;
    public String sessionToken;

    public KVSParamBean(String awsAccessKey, String awsSecretKey, String sessionToken2, String channelArn2, String region2) {
    }

    public KVSParamBean(Parcel in) {
        this.accessKeyId = in.readString();
        this.secretAccessKey = in.readString();
        this.sessionToken = in.readString();
        this.channelArn = in.readString();
        this.region = in.readString();
        this.expiresIn = in.readLong();
        this.nowTime = in.readLong();
    }

    public boolean requireFiledCheck() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9636, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return !e.b(this.accessKeyId) && !e.b(this.secretAccessKey) && !e.b(this.channelArn) && !e.b(this.channelArn) && !e.b(this.sessionToken) && !e.b(this.region);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 9637, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeString(this.accessKeyId);
            dest.writeString(this.secretAccessKey);
            dest.writeString(this.sessionToken);
            dest.writeString(this.channelArn);
            dest.writeString(this.region);
            dest.writeLong(this.expiresIn);
            dest.writeLong(this.nowTime);
        }
    }
}
