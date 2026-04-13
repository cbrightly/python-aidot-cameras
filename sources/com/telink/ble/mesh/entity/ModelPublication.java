package com.telink.ble.mesh.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class ModelPublication implements Serializable, Parcelable {
    public static final Parcelable.Creator<ModelPublication> CREATOR = new Parcelable.Creator<ModelPublication>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13028, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13027, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public ModelPublication a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13026, new Class[]{Parcel.class}, ModelPublication.class);
            if (proxy.isSupported) {
                return (ModelPublication) proxy.result;
            }
            return new ModelPublication(in);
        }

        public ModelPublication[] b(int size) {
            return new ModelPublication[size];
        }
    };
    public static final int CREDENTIAL_FLAG_DEFAULT = 1;
    public static final int RETRANSMIT_COUNT_DEFAULT = 5;
    public static final int RETRANSMIT_INTERVAL_STEP_DEFAULT = 2;
    public static final int RFU_DEFAULT = 0;
    public static final int TTL_DEFAULT = 255;
    public static ChangeQuickRedirect changeQuickRedirect;
    public int appKeyIndex;
    public int credentialFlag = 1;
    public int elementAddress;
    public int modelId;
    public byte period;
    public int publishAddress;
    public int retransmitCount = 5;
    public int retransmitIntervalSteps = 2;
    public int rfu = 0;
    public boolean sig;
    public int ttl = 255;

    public ModelPublication(Parcel in) {
        boolean z = true;
        this.elementAddress = in.readInt();
        this.publishAddress = in.readInt();
        this.appKeyIndex = in.readInt();
        this.credentialFlag = in.readInt();
        this.rfu = in.readInt();
        this.ttl = in.readInt();
        this.period = in.readByte();
        this.retransmitCount = in.readInt();
        this.retransmitIntervalSteps = in.readInt();
        this.modelId = in.readInt();
        this.sig = in.readByte() == 0 ? false : z;
    }

    public ModelPublication() {
    }

    public static ModelPublication createDefault(int elementAddress2, int publishAddress2, int appKeyIndex2, long periodMillisecond, int modelId2, boolean sig2) {
        Object[] objArr = {new Integer(elementAddress2), new Integer(publishAddress2), new Integer(appKeyIndex2), new Long(periodMillisecond), new Integer(modelId2), new Byte(sig2 ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 13023, new Class[]{cls, cls, cls, Long.TYPE, cls, Boolean.TYPE}, ModelPublication.class);
        if (proxy.isSupported) {
            return (ModelPublication) proxy.result;
        }
        ModelPublication instance = new ModelPublication();
        instance.elementAddress = elementAddress2;
        instance.publishAddress = publishAddress2;
        instance.appKeyIndex = appKeyIndex2;
        instance.period = TransitionTime.a(periodMillisecond).d();
        instance.modelId = modelId2;
        instance.sig = sig2;
        return instance;
    }

    public byte[] toBytes() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13024, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteBuffer bf = ByteBuffer.allocate(this.sig ? 11 : 13).order(ByteOrder.LITTLE_ENDIAN);
        bf.putShort((short) this.elementAddress).putShort((short) this.publishAddress).putShort((short) (((this.rfu & 7) << 13) | (this.appKeyIndex & 4095) | ((this.credentialFlag & 1) << 12))).put((byte) this.ttl).put(this.period).put((byte) ((this.retransmitCount & 7) | (this.retransmitIntervalSteps << 3)));
        if (this.sig) {
            bf.putShort((short) this.modelId);
        } else {
            bf.putInt(this.modelId);
        }
        return bf.array();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13025, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.elementAddress);
            dest.writeInt(this.publishAddress);
            dest.writeInt(this.appKeyIndex);
            dest.writeInt(this.credentialFlag);
            dest.writeInt(this.rfu);
            dest.writeInt(this.ttl);
            dest.writeByte(this.period);
            dest.writeInt(this.retransmitCount);
            dest.writeInt(this.retransmitIntervalSteps);
            dest.writeInt(this.modelId);
            dest.writeByte(this.sig ? (byte) 1 : 0);
        }
    }
}
