package com.telink.ble.mesh.foundation.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.entity.OnlineStatusInfo;
import com.telink.ble.mesh.foundation.Event;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class OnlineStatusEvent extends Event<String> implements Parcelable {
    public static final Parcelable.Creator<OnlineStatusEvent> CREATOR = new Parcelable.Creator<OnlineStatusEvent>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13350, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13349, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public OnlineStatusEvent a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13348, new Class[]{Parcel.class}, OnlineStatusEvent.class);
            if (proxy.isSupported) {
                return (OnlineStatusEvent) proxy.result;
            }
            return new OnlineStatusEvent(in);
        }

        public OnlineStatusEvent[] b(int size) {
            return new OnlineStatusEvent[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private List<OnlineStatusInfo> c;

    public OnlineStatusEvent(Object sender, byte[] onlineStatusData) {
        this(sender, "com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_ONLINE_STATUS_NOTIFY");
        this.c = b(onlineStatusData);
    }

    public OnlineStatusEvent(Parcel in) {
        this.c = in.createTypedArrayList(OnlineStatusInfo.CREATOR);
    }

    private List<OnlineStatusInfo> b(byte[] bArr) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bArr}, this, changeQuickRedirect, false, 13346, new Class[]{byte[].class}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        byte[] rawData = bArr;
        if (rawData == null || rawData.length < 4) {
            return null;
        }
        int len = rawData.length;
        int index = 0 + 1;
        if (rawData[0] != 98) {
            return null;
        }
        int index2 = index + 1;
        int nodeLen = rawData[index] & 15;
        int statusLen = nodeLen - 3;
        if (statusLen <= 0) {
            return null;
        }
        int index3 = index2 + 1;
        int address = index3 + 1;
        byte b = ((rawData[index3] & 255) << 8) | (rawData[index2] & 255);
        List<OnlineStatusInfo> statusInfoList = null;
        while (true) {
            if (address + nodeLen > len) {
                break;
            }
            int index4 = address + 1;
            int index5 = index4 + 1;
            int address2 = (rawData[address] & 255) | ((rawData[index4] & Byte.MAX_VALUE) << 8);
            int index6 = index5 + 1;
            byte sn = rawData[index5];
            byte[] status = new byte[statusLen];
            System.arraycopy(rawData, index6, status, 0, statusLen);
            int index7 = index6 + statusLen;
            if (address2 == 0) {
                int address3 = index7;
                break;
            }
            OnlineStatusInfo statusInfo = new OnlineStatusInfo();
            statusInfo.c = address2;
            statusInfo.d = sn;
            statusInfo.f = status;
            if (statusInfoList == null) {
                statusInfoList = new ArrayList<>();
            }
            statusInfoList.add(statusInfo);
            address = index7;
        }
        return statusInfoList;
    }

    public OnlineStatusEvent(Object sender, String type) {
        super(sender, type);
    }

    public List<OnlineStatusInfo> a() {
        return this.c;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13347, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeTypedList(this.c);
        }
    }
}
