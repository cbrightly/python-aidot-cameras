package org.webrtc;

import androidx.annotation.Nullable;
import java.util.List;

public interface NetworkChangeDetector {

    public enum ConnectionType {
        CONNECTION_UNKNOWN,
        CONNECTION_ETHERNET,
        CONNECTION_WIFI,
        CONNECTION_5G,
        CONNECTION_4G,
        CONNECTION_3G,
        CONNECTION_2G,
        CONNECTION_UNKNOWN_CELLULAR,
        CONNECTION_BLUETOOTH,
        CONNECTION_VPN,
        CONNECTION_NONE
    }

    public interface Observer {
        void onConnectionTypeChanged(ConnectionType connectionType);

        void onNetworkConnect(NetworkInformation networkInformation);

        void onNetworkDisconnect(long j);

        void onNetworkPreference(List<ConnectionType> list, int i);
    }

    void destroy();

    @Nullable
    List<NetworkInformation> getActiveNetworkList();

    ConnectionType getCurrentConnectionType();

    boolean supportNetworkCallback();

    public static class IPAddress {
        public final byte[] address;

        public IPAddress(byte[] address2) {
            this.address = address2;
        }

        @CalledByNative("IPAddress")
        private byte[] getAddress() {
            return this.address;
        }
    }

    public static class NetworkInformation {
        public final long handle;
        public final IPAddress[] ipAddresses;
        public final String name;
        public final ConnectionType type;
        public final ConnectionType underlyingTypeForVpn;

        public NetworkInformation(String name2, ConnectionType type2, ConnectionType underlyingTypeForVpn2, long handle2, IPAddress[] addresses) {
            this.name = name2;
            this.type = type2;
            this.underlyingTypeForVpn = underlyingTypeForVpn2;
            this.handle = handle2;
            this.ipAddresses = addresses;
        }

        @CalledByNative("NetworkInformation")
        private IPAddress[] getIpAddresses() {
            return this.ipAddresses;
        }

        @CalledByNative("NetworkInformation")
        private ConnectionType getConnectionType() {
            return this.type;
        }

        @CalledByNative("NetworkInformation")
        private ConnectionType getUnderlyingConnectionTypeForVpn() {
            return this.underlyingTypeForVpn;
        }

        @CalledByNative("NetworkInformation")
        private long getHandle() {
            return this.handle;
        }

        @CalledByNative("NetworkInformation")
        private String getName() {
            return this.name;
        }
    }
}
