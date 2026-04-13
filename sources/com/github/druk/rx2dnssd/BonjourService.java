package com.github.druk.rx2dnssd;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BonjourService implements Parcelable {
    public static final Parcelable.Creator<BonjourService> CREATOR = new Parcelable.Creator<BonjourService>() {
        @NonNull
        public BonjourService createFromParcel(@NonNull Parcel source) {
            return new BonjourService(source);
        }

        @NonNull
        public BonjourService[] newArray(int size) {
            return new BonjourService[size];
        }
    };
    public static final int LOST = 256;
    /* access modifiers changed from: private */
    public final Map<String, String> dnsRecords;
    /* access modifiers changed from: private */
    public final String domain;
    /* access modifiers changed from: private */
    public final int flags;
    /* access modifiers changed from: private */
    public final String hostname;
    /* access modifiers changed from: private */
    public final int ifIndex;
    /* access modifiers changed from: private */
    public final List<InetAddress> inetAddresses;
    /* access modifiers changed from: private */
    public final int port;
    /* access modifiers changed from: private */
    public final String regType;
    /* access modifiers changed from: private */
    public final String serviceName;

    protected BonjourService(@NonNull Builder builder) {
        this.flags = builder.flags;
        this.serviceName = builder.serviceName;
        this.regType = builder.regType;
        this.domain = builder.domain;
        this.ifIndex = builder.ifIndex;
        this.inetAddresses = Collections.unmodifiableList(builder.inetAddresses);
        this.dnsRecords = Collections.unmodifiableMap(builder.dnsRecords);
        this.hostname = builder.hostname;
        this.port = builder.port;
    }

    protected BonjourService(@NonNull Parcel in) {
        this.flags = in.readInt();
        this.serviceName = in.readString();
        this.regType = in.readString();
        this.domain = in.readString();
        this.dnsRecords = readMap(in);
        this.inetAddresses = readAddresses(in);
        this.ifIndex = in.readInt();
        this.hostname = in.readString();
        this.port = in.readInt();
    }

    private static void writeAddresses(Parcel dest, List<InetAddress> val) {
        if (val == null) {
            dest.writeInt(-1);
            return;
        }
        dest.writeInt(val.size());
        for (InetAddress address : val) {
            dest.writeSerializable(address);
        }
    }

    private static List<InetAddress> readAddresses(Parcel in) {
        int n = in.readInt();
        if (n < 0) {
            return null;
        }
        List<InetAddress> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add((InetAddress) in.readSerializable());
        }
        return Collections.unmodifiableList(result);
    }

    private static void writeMap(Parcel dest, Map<String, String> val) {
        if (val == null) {
            dest.writeInt(-1);
            return;
        }
        dest.writeInt(val.size());
        for (Map.Entry<String, String> entry : val.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    private static Map<String, String> readMap(Parcel in) {
        int n = in.readInt();
        if (n < 0) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String key = in.readString();
            String value = in.readString();
            if (!(key == null || value == null)) {
                result.put(key, value);
            }
        }
        return Collections.unmodifiableMap(result);
    }

    public int getFlags() {
        return this.flags;
    }

    @NonNull
    public String getServiceName() {
        return this.serviceName;
    }

    @NonNull
    public String getRegType() {
        return this.regType;
    }

    @Nullable
    public String getDomain() {
        return this.domain;
    }

    public int getIfIndex() {
        return this.ifIndex;
    }

    @Nullable
    public String getHostname() {
        return this.hostname;
    }

    public int getPort() {
        return this.port;
    }

    @NonNull
    public Map<String, String> getTxtRecords() {
        return this.dnsRecords;
    }

    @Nullable
    public Inet4Address getInet4Address() {
        for (InetAddress inetAddress : this.inetAddresses) {
            if (inetAddress instanceof Inet4Address) {
                return (Inet4Address) inetAddress;
            }
        }
        return null;
    }

    @Nullable
    public Inet6Address getInet6Address() {
        for (InetAddress inetAddress : this.inetAddresses) {
            if (inetAddress instanceof Inet6Address) {
                return (Inet6Address) inetAddress;
            }
        }
        return null;
    }

    public List<InetAddress> getInetAddresses() {
        return this.inetAddresses;
    }

    public boolean isLost() {
        return (this.flags & 256) == 256;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonjourService)) {
            return false;
        }
        BonjourService that = (BonjourService) o;
        if (this.ifIndex != that.ifIndex) {
            return false;
        }
        String str = this.serviceName;
        if (str == null ? that.serviceName != null : !str.equals(that.serviceName)) {
            return false;
        }
        String str2 = this.regType;
        if (str2 == null ? that.regType != null : !str2.equals(that.regType)) {
            return false;
        }
        String str3 = this.domain;
        if (str3 != null) {
            if (str3.equals(that.domain)) {
                return true;
            }
        } else if (that.domain == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String str = this.serviceName;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.regType;
        int result = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.domain;
        if (str3 != null) {
            i = str3.hashCode();
        }
        return ((result + i) * 31) + this.ifIndex;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel dest, int flags2) {
        dest.writeInt(this.flags);
        dest.writeString(this.serviceName);
        dest.writeString(this.regType);
        dest.writeString(this.domain);
        writeMap(dest, this.dnsRecords);
        writeAddresses(dest, this.inetAddresses);
        dest.writeInt(this.ifIndex);
        dest.writeString(this.hostname);
        dest.writeInt(this.port);
    }

    @NonNull
    public String toString() {
        return "BonjourService{flags=" + this.flags + ", domain='" + this.domain + '\'' + ", regType='" + this.regType + '\'' + ", serviceName='" + this.serviceName + '\'' + ", dnsRecords=" + this.dnsRecords + ", ifIndex=" + this.ifIndex + ", hostname='" + this.hostname + '\'' + ", port=" + this.port + '}';
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public Map<String, String> dnsRecords = new HashMap();
        /* access modifiers changed from: private */
        public final String domain;
        /* access modifiers changed from: private */
        public final int flags;
        /* access modifiers changed from: private */
        public String hostname;
        /* access modifiers changed from: private */
        public final int ifIndex;
        /* access modifiers changed from: private */
        public List<InetAddress> inetAddresses = new ArrayList();
        /* access modifiers changed from: private */
        public int port;
        /* access modifiers changed from: private */
        public final String regType;
        /* access modifiers changed from: private */
        public final String serviceName;

        public Builder(int flags2, int ifIndex2, @NonNull String serviceName2, @NonNull String regType2, String domain2) {
            this.flags = flags2;
            this.serviceName = serviceName2;
            this.regType = regType2;
            this.domain = domain2;
            this.ifIndex = ifIndex2;
        }

        public Builder(@NonNull BonjourService service) {
            this.flags = service.flags;
            this.serviceName = service.serviceName;
            this.regType = service.regType;
            this.domain = service.domain;
            this.ifIndex = service.ifIndex;
            this.dnsRecords = new HashMap(service.dnsRecords);
            this.inetAddresses = new ArrayList(service.inetAddresses);
            this.hostname = service.hostname;
            this.port = service.port;
        }

        @NonNull
        public Builder hostname(String hostname2) {
            this.hostname = hostname2;
            return this;
        }

        @NonNull
        public Builder port(int port2) {
            this.port = port2;
            return this;
        }

        @NonNull
        public Builder dnsRecords(Map<String, String> dnsRecords2) {
            this.dnsRecords = dnsRecords2;
            return this;
        }

        @NonNull
        public Builder inet4Address(Inet4Address inet4Address) {
            this.inetAddresses.add(inet4Address);
            return this;
        }

        @NonNull
        public Builder inet6Address(Inet6Address inet6Address) {
            this.inetAddresses.add(inet6Address);
            return this;
        }

        @NonNull
        public BonjourService build() {
            return new BonjourService(this);
        }

        public void inetAddress(InetAddress inetAddress) {
            this.inetAddresses.add(inetAddress);
        }
    }
}
