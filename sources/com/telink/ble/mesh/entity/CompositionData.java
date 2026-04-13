package com.telink.ble.mesh.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.MeshSigModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.glassfish.grizzly.http.server.util.MappingData;

public class CompositionData implements Serializable, Parcelable {
    public static final Parcelable.Creator<CompositionData> CREATOR = new Parcelable.Creator<CompositionData>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13002, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13001, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public CompositionData a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13000, new Class[]{Parcel.class}, CompositionData.class);
            if (proxy.isSupported) {
                return (CompositionData) proxy.result;
            }
            return new CompositionData(in);
        }

        public CompositionData[] b(int size) {
            return new CompositionData[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    public int cid;
    public int crpl;
    public List<Element> elements;
    public int features;
    public int pid;
    public int vid;

    public CompositionData() {
    }

    public CompositionData(Parcel in) {
        this.cid = in.readInt();
        this.pid = in.readInt();
        this.vid = in.readInt();
        this.crpl = in.readInt();
        this.features = in.readInt();
    }

    public static CompositionData from(byte[] data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, (Object) null, changeQuickRedirect, true, 12996, new Class[]{byte[].class}, CompositionData.class);
        if (proxy.isSupported) {
            return (CompositionData) proxy.result;
        }
        CompositionData cpsData = new CompositionData();
        int index = 0 + 1;
        int index2 = index + 1;
        cpsData.cid = (data[0] & 255) | ((data[index] & 255) << 8);
        int index3 = index2 + 1;
        int index4 = index3 + 1;
        cpsData.pid = ((data[index3] & 255) << 8) | (data[index2] & 255);
        int index5 = index4 + 1;
        int index6 = index5 + 1;
        cpsData.vid = ((data[index5] & 255) << 8) | (data[index4] & 255);
        int index7 = index6 + 1;
        int index8 = index7 + 1;
        cpsData.crpl = ((data[index7] & 255) << 8) | (data[index6] & 255);
        int index9 = index8 + 1;
        int index10 = index9 + 1;
        cpsData.features = ((data[index9] & 255) << 8) | (data[index8] & 255);
        cpsData.elements = new ArrayList();
        while (index10 < data.length) {
            Element element = new Element();
            int index11 = index10 + 1;
            int index12 = index11 + 1;
            element.location = ((data[index11] & 255) << 8) | (data[index10] & 255);
            int index13 = index12 + 1;
            element.sigNum = data[index12] & 255;
            index10 = index13 + 1;
            element.vendorNum = data[index13] & 255;
            element.sigModels = new ArrayList();
            int i = 0;
            while (i < element.sigNum) {
                int index14 = index10 + 1;
                element.sigModels.add(Integer.valueOf((data[index10] & 255) | ((data[index14] & 255) << 8)));
                i++;
                index10 = index14 + 1;
            }
            element.vendorModels = new ArrayList();
            int j = 0;
            while (j < element.vendorNum) {
                List<Integer> list = element.vendorModels;
                int index15 = index10 + 1;
                int index16 = index15 + 1;
                byte b = (data[index10] & 255) | ((data[index15] & 255) << 8);
                int index17 = index16 + 1;
                byte b2 = b | ((data[index16] & 255) << MappingData.PATH);
                list.add(Integer.valueOf(b2 | ((data[index17] & 255) << 24)));
                j++;
                index10 = index17 + 1;
            }
            cpsData.elements.add(element);
        }
        return cpsData;
    }

    public List<Integer> getAllModels(boolean configExcept) {
        if (this.elements == null) {
            return null;
        }
        List<Integer> models = new ArrayList<>();
        for (Element ele : this.elements) {
            List<Integer> list = ele.sigModels;
            if (list != null) {
                if (!configExcept) {
                    models.addAll(list);
                } else {
                    for (Integer intValue : list) {
                        int modelId = intValue.intValue();
                        if (!MeshSigModel.isConfigurationModel(modelId)) {
                            models.add(Integer.valueOf(modelId));
                        }
                    }
                }
            }
            List<Integer> list2 = ele.vendorModels;
            if (list2 != null) {
                models.addAll(list2);
            }
        }
        return models;
    }

    public int getElementOffset(int modelId) {
        Object[] objArr = {new Integer(modelId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12997, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int offset = 0;
        for (Element ele : this.elements) {
            List<Integer> list = ele.sigModels;
            if (list != null && list.contains(Integer.valueOf(modelId))) {
                return offset;
            }
            List<Integer> list2 = ele.vendorModels;
            if (list2 != null && list2.contains(Integer.valueOf(modelId))) {
                return offset;
            }
            offset++;
        }
        return -1;
    }

    public boolean relaySupport() {
        return (this.features & 1) != 0;
    }

    public boolean proxySupport() {
        return (this.features & 2) != 0;
    }

    public boolean friendSupport() {
        return (this.features & 4) != 0;
    }

    public boolean lowPowerSupport() {
        return (this.features & 8) != 0;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12998, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.cid);
            dest.writeInt(this.pid);
            dest.writeInt(this.vid);
            dest.writeInt(this.crpl);
            dest.writeInt(this.features);
            dest.writeTypedList(this.elements);
        }
    }

    public static class Element implements Serializable, Parcelable {
        public static final Parcelable.Creator<Element> CREATOR = new Parcelable.Creator<Element>() {
            public static ChangeQuickRedirect changeQuickRedirect;

            public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13007, new Class[]{Parcel.class}, Object.class);
                return proxy.isSupported ? proxy.result : a(parcel);
            }

            public /* bridge */ /* synthetic */ Object[] newArray(int i) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13006, new Class[]{Integer.TYPE}, Object[].class);
                return proxy.isSupported ? (Object[]) proxy.result : b(i);
            }

            public Element a(Parcel in) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13005, new Class[]{Parcel.class}, Element.class);
                if (proxy.isSupported) {
                    return (Element) proxy.result;
                }
                return new Element(in);
            }

            public Element[] b(int size) {
                return new Element[size];
            }
        };
        public static ChangeQuickRedirect changeQuickRedirect;
        public int location;
        public List<Integer> sigModels;
        public int sigNum;
        public List<Integer> vendorModels;
        public int vendorNum;

        public Element() {
        }

        public Element(Parcel in) {
            this.location = in.readInt();
            this.sigNum = in.readInt();
            this.vendorNum = in.readInt();
        }

        public boolean containModel(int sigModelId) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(sigModelId)}, this, changeQuickRedirect, false, 13003, new Class[]{Integer.TYPE}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            List<Integer> list = this.sigModels;
            if (list == null || list.size() == 0) {
                return false;
            }
            for (Integer intValue : this.sigModels) {
                if (sigModelId == intValue.intValue()) {
                    return true;
                }
            }
            return false;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int i) {
            if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13004, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
                dest.writeInt(this.location);
                dest.writeInt(this.sigNum);
                dest.writeInt(this.vendorNum);
            }
        }
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12999, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder elementInfo = new StringBuilder();
        for (int i = 0; i < this.elements.size(); i++) {
            Element element = this.elements.get(i);
            elementInfo.append("element ");
            elementInfo.append(i);
            elementInfo.append(" : \n");
            elementInfo.append("SIG\n");
            for (int j = 0; j < element.sigModels.size(); j++) {
                elementInfo.append(String.format(Locale.US, "%04X", new Object[]{element.sigModels.get(j)}));
                elementInfo.append("\n");
            }
            elementInfo.append("VENDOR\n");
            for (int j2 = 0; j2 < element.vendorModels.size(); j2++) {
                elementInfo.append(String.format(Locale.US, "%08X", new Object[]{element.vendorModels.get(j2)}));
                elementInfo.append("\n");
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("CompositionData{cid=");
        Locale locale = Locale.US;
        sb.append(String.format(locale, "%04X", new Object[]{Integer.valueOf(this.cid)}));
        sb.append(", pid=");
        sb.append(String.format(locale, "%04X", new Object[]{Integer.valueOf(this.pid)}));
        sb.append(", vid=");
        sb.append(String.format(locale, "%04X", new Object[]{Integer.valueOf(this.vid)}));
        sb.append(", crpl=");
        sb.append(String.format(locale, "%04X", new Object[]{Integer.valueOf(this.crpl)}));
        sb.append(", features=");
        sb.append(String.format(locale, "%04X", new Object[]{Integer.valueOf(this.features)}));
        sb.append(", elements=");
        sb.append(elementInfo);
        sb.append('}');
        return sb.toString();
    }
}
