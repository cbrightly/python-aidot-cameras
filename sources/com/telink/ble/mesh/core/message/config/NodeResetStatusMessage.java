package com.telink.ble.mesh.core.message.config;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.StatusMessage;

public class NodeResetStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<NodeResetStatusMessage> CREATOR = new Parcelable.Creator<NodeResetStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12517, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12516, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public NodeResetStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12515, new Class[]{Parcel.class}, NodeResetStatusMessage.class);
            if (proxy.isSupported) {
                return (NodeResetStatusMessage) proxy.result;
            }
            return new NodeResetStatusMessage(in);
        }

        public NodeResetStatusMessage[] b(int size) {
            return new NodeResetStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;

    public NodeResetStatusMessage() {
    }

    public NodeResetStatusMessage(Parcel in) {
    }

    public void b(byte[] params) {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }
}
