package com.telink.ble.mesh.core.message.firmwareupdate;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.util.ArrayList;
import java.util.List;
import org.webrtc.EglBase;

public class FirmwareUpdateInfoStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<FirmwareUpdateInfoStatusMessage> CREATOR = new Parcelable.Creator<FirmwareUpdateInfoStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12608, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12607, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public FirmwareUpdateInfoStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12606, new Class[]{Parcel.class}, FirmwareUpdateInfoStatusMessage.class);
            if (proxy.isSupported) {
                return (FirmwareUpdateInfoStatusMessage) proxy.result;
            }
            return new FirmwareUpdateInfoStatusMessage(in);
        }

        public FirmwareUpdateInfoStatusMessage[] b(int size) {
            return new FirmwareUpdateInfoStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;
    private List<FirmwareInformationEntry> f;

    public FirmwareUpdateInfoStatusMessage() {
    }

    public FirmwareUpdateInfoStatusMessage(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.createTypedArrayList(FirmwareInformationEntry.CREATOR);
    }

    public void b(byte[] params) {
        byte[] currentFirmwareID;
        byte[] updateURI;
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12602, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            int index = 0 + 1;
            this.c = params[0] & 255;
            int firmwareIdLength = index + 1;
            this.d = params[index] & 255;
            this.f = new ArrayList();
            int i = 0;
            while (i < this.c) {
                int index2 = firmwareIdLength + 1;
                int firmwareIdLength2 = params[firmwareIdLength] & 255;
                if (firmwareIdLength2 > 0) {
                    currentFirmwareID = new byte[firmwareIdLength2];
                    System.arraycopy(params, index2, currentFirmwareID, 0, firmwareIdLength2);
                    index2 += firmwareIdLength2;
                } else {
                    currentFirmwareID = null;
                }
                int index3 = index2 + 1;
                byte uriLen = params[index2];
                if (uriLen > 0) {
                    updateURI = new byte[uriLen];
                    System.arraycopy(params, index3, updateURI, 0, uriLen);
                    index3 += uriLen;
                } else {
                    updateURI = null;
                }
                FirmwareInformationEntry informationEntry = new FirmwareInformationEntry();
                informationEntry.c = firmwareIdLength2;
                informationEntry.d = currentFirmwareID;
                informationEntry.f = uriLen;
                informationEntry.q = updateURI;
                this.f.add(informationEntry);
                i++;
                firmwareIdLength = index3;
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12603, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeTypedList(this.f);
        }
    }

    public int e() {
        return this.c;
    }

    public int d() {
        return this.d;
    }

    public List<FirmwareInformationEntry> c() {
        return this.f;
    }

    public static class FirmwareInformationEntry implements Parcelable {
        public static final Parcelable.Creator<FirmwareInformationEntry> CREATOR = new Parcelable.Creator<FirmwareInformationEntry>() {
            public static ChangeQuickRedirect changeQuickRedirect;

            public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12612, new Class[]{Parcel.class}, Object.class);
                return proxy.isSupported ? proxy.result : a(parcel);
            }

            public /* bridge */ /* synthetic */ Object[] newArray(int i) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12611, new Class[]{Integer.TYPE}, Object[].class);
                return proxy.isSupported ? (Object[]) proxy.result : b(i);
            }

            public FirmwareInformationEntry a(Parcel in) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, EglBase.EGL_RECORDABLE_ANDROID, new Class[]{Parcel.class}, FirmwareInformationEntry.class);
                if (proxy.isSupported) {
                    return (FirmwareInformationEntry) proxy.result;
                }
                return new FirmwareInformationEntry(in);
            }

            public FirmwareInformationEntry[] b(int size) {
                return new FirmwareInformationEntry[size];
            }
        };
        public static ChangeQuickRedirect changeQuickRedirect;
        public int c;
        public byte[] d;
        public int f;
        public byte[] q;

        public FirmwareInformationEntry() {
        }

        public FirmwareInformationEntry(Parcel in) {
            this.c = in.readInt();
            this.d = in.createByteArray();
            this.f = in.readInt();
            this.q = in.createByteArray();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int i) {
            if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12609, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
                dest.writeInt(this.c);
                dest.writeByteArray(this.d);
                dest.writeInt(this.f);
                dest.writeByteArray(this.q);
            }
        }
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12605, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "FirmwareUpdateInfoStatusMessage{listCount=" + this.c + ", firstIndex=" + this.d + ", firmwareInformationList=" + this.f.size() + '}';
    }
}
