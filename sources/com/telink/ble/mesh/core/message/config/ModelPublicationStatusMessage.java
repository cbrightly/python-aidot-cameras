package com.telink.ble.mesh.core.message.config;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import com.telink.ble.mesh.entity.ModelPublication;
import java.nio.ByteOrder;
import org.glassfish.grizzly.http.server.util.MappingData;

public class ModelPublicationStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<ModelPublicationStatusMessage> CREATOR = new Parcelable.Creator<ModelPublicationStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12478, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12477, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public ModelPublicationStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12476, new Class[]{Parcel.class}, ModelPublicationStatusMessage.class);
            if (proxy.isSupported) {
                return (ModelPublicationStatusMessage) proxy.result;
            }
            return new ModelPublicationStatusMessage(in);
        }

        public ModelPublicationStatusMessage[] b(int size) {
            return new ModelPublicationStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte c;
    private ModelPublication d;

    public ModelPublicationStatusMessage() {
    }

    public ModelPublicationStatusMessage(Parcel in) {
        this.c = in.readByte();
        this.d = (ModelPublication) in.readParcelable(ModelPublication.class.getClassLoader());
    }

    public void b(byte[] data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 12474, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            int index = 0 + 1;
            this.c = data[0];
            ModelPublication modelPublication = new ModelPublication();
            ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
            modelPublication.elementAddress = MeshUtils.b(data, index, 2, byteOrder);
            int index2 = index + 2;
            modelPublication.publishAddress = MeshUtils.b(data, index2, 2, byteOrder);
            int index3 = index2 + 2;
            int index4 = index3 + 1;
            modelPublication.appKeyIndex = (data[index3] & 255) | ((data[index4] & 15) << 8);
            modelPublication.credentialFlag = (data[index4] >> 4) & 1;
            modelPublication.rfu = (data[index4] >> 5) & 7;
            int index5 = index4 + 1;
            int index6 = index5 + 1;
            modelPublication.ttl = data[index5];
            int index7 = index6 + 1;
            modelPublication.period = data[index6];
            modelPublication.retransmitCount = (data[index7] >> 5) & 7;
            int index8 = index7 + 1;
            modelPublication.retransmitIntervalSteps = data[index7] & 31;
            int index9 = index8 + 1;
            int index10 = index9 + 1;
            byte b = (data[index8] & 255) | ((data[index9] & 255) << 8);
            modelPublication.modelId = b;
            if (index10 + 2 <= data.length) {
                modelPublication.sig = true;
                int index11 = index10 + 1;
                modelPublication.modelId = b | ((data[index10] & 255) << MappingData.PATH) | ((data[index11] & 255) << 24);
                int i = index11;
            }
            this.d = modelPublication;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 12475, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByte(this.c);
            dest.writeParcelable(this.d, flags);
        }
    }

    public byte d() {
        return this.c;
    }

    public ModelPublication c() {
        return this.d;
    }
}
