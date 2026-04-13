package com.leedarson.serviceimpl.bean;

import android.os.Parcel;
import android.os.Parcelable;
import chip.setuppayload.OptionalQRCodeInfo;
import com.leedarson.base.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.netty.util.internal.StringUtil;
import java.util.Map;

public class LeedarsonParams implements Parcelable {
    public static final Parcelable.Creator<LeedarsonParams> CREATOR = new Parcelable.Creator<LeedarsonParams>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public LeedarsonParams createFromParcel(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 6175, new Class[]{Parcel.class}, LeedarsonParams.class);
            if (proxy.isSupported) {
                return (LeedarsonParams) proxy.result;
            }
            return new LeedarsonParams(in);
        }

        public LeedarsonParams[] newArray(int size) {
            return new LeedarsonParams[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    public String pairingFlag = "";
    public String shortModelId = "";
    public int version = 0;
    public String wifiMac = "";

    public LeedarsonParams() {
    }

    public LeedarsonParams(Parcel in) {
        this.shortModelId = in.readString();
        this.wifiMac = in.readString();
        this.version = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 6171, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeString(this.shortModelId);
            dest.writeString(this.wifiMac);
            dest.writeInt(this.version);
        }
    }

    public static LeedarsonParams fromScanRecord(byte[] bytes) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bytes}, (Object) null, changeQuickRedirect, true, 6172, new Class[]{byte[].class}, LeedarsonParams.class);
        if (proxy.isSupported) {
            return (LeedarsonParams) proxy.result;
        }
        byte[] macArr = new byte[6];
        System.arraycopy(bytes, 12, macArr, 0, macArr.length);
        LeedarsonParams leedarsonParams = new LeedarsonParams();
        leedarsonParams.version = (bytes[10] >> 4) & 15;
        leedarsonParams.wifiMac = e.a(macArr).toUpperCase();
        try {
            leedarsonParams.shortModelId = e.a(new byte[]{bytes[18], bytes[19], bytes[20]});
            if (bytes.length > 21) {
                leedarsonParams.pairingFlag = e.a(new byte[]{bytes[21]});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return leedarsonParams;
    }

    public static LeedarsonParams fromOptionalInfo(Map<Integer, OptionalQRCodeInfo> optionalQRCodeInfo) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{optionalQRCodeInfo}, (Object) null, changeQuickRedirect, true, 6173, new Class[]{Map.class}, LeedarsonParams.class);
        if (proxy.isSupported) {
            return (LeedarsonParams) proxy.result;
        }
        LeedarsonParams leedarsonParams = new LeedarsonParams();
        if (optionalQRCodeInfo != null) {
            try {
                optionalQRCodeInfo.containsKey(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return leedarsonParams;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6174, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "{" + "\"shortModelId\":\"" + this.shortModelId + StringUtil.DOUBLE_QUOTE + ",\"wifiMac\":\"" + this.wifiMac + StringUtil.DOUBLE_QUOTE + ",\"version\":" + this.version + ",\"pairingFlag\":\"" + this.pairingFlag + StringUtil.DOUBLE_QUOTE + '}';
    }
}
