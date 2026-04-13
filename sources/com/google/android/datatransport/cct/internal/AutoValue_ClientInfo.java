package com.google.android.datatransport.cct.internal;

import androidx.annotation.Nullable;
import com.google.android.datatransport.cct.internal.ClientInfo;

public final class AutoValue_ClientInfo extends ClientInfo {
    private final AndroidClientInfo androidClientInfo;
    private final ClientInfo.ClientType clientType;

    private AutoValue_ClientInfo(@Nullable ClientInfo.ClientType clientType2, @Nullable AndroidClientInfo androidClientInfo2) {
        this.clientType = clientType2;
        this.androidClientInfo = androidClientInfo2;
    }

    @Nullable
    public ClientInfo.ClientType getClientType() {
        return this.clientType;
    }

    @Nullable
    public AndroidClientInfo getAndroidClientInfo() {
        return this.androidClientInfo;
    }

    public String toString() {
        return "ClientInfo{clientType=" + this.clientType + ", androidClientInfo=" + this.androidClientInfo + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ClientInfo)) {
            return false;
        }
        ClientInfo that = (ClientInfo) o;
        ClientInfo.ClientType clientType2 = this.clientType;
        if (clientType2 != null ? clientType2.equals(that.getClientType()) : that.getClientType() == null) {
            AndroidClientInfo androidClientInfo2 = this.androidClientInfo;
            if (androidClientInfo2 == null) {
                if (that.getAndroidClientInfo() == null) {
                    return true;
                }
            } else if (androidClientInfo2.equals(that.getAndroidClientInfo())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1 * 1000003;
        ClientInfo.ClientType clientType2 = this.clientType;
        int i = 0;
        int h$2 = (h$ ^ (clientType2 == null ? 0 : clientType2.hashCode())) * 1000003;
        AndroidClientInfo androidClientInfo2 = this.androidClientInfo;
        if (androidClientInfo2 != null) {
            i = androidClientInfo2.hashCode();
        }
        return h$2 ^ i;
    }

    public static final class Builder extends ClientInfo.Builder {
        private AndroidClientInfo androidClientInfo;
        private ClientInfo.ClientType clientType;

        Builder() {
        }

        public ClientInfo.Builder setClientType(@Nullable ClientInfo.ClientType clientType2) {
            this.clientType = clientType2;
            return this;
        }

        public ClientInfo.Builder setAndroidClientInfo(@Nullable AndroidClientInfo androidClientInfo2) {
            this.androidClientInfo = androidClientInfo2;
            return this;
        }

        public ClientInfo build() {
            return new AutoValue_ClientInfo(this.clientType, this.androidClientInfo);
        }
    }
}
