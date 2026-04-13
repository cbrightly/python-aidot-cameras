package chip.platform;

import androidx.annotation.Nullable;

public class NetworkInterface {
    public static final int INTERFACE_TYPE_CELLULAR = 3;
    public static final int INTERFACE_TYPE_ETHERNET = 2;
    public static final int INTERFACE_TYPE_THREAD = 4;
    public static final int INTERFACE_TYPE_UNSPECIFIED = 0;
    public static final int INTERFACE_TYPE_WI_FI = 1;
    public byte[] hardwareAddress;
    public byte[] ipv4Address;
    public byte[] ipv6Address;
    public boolean isOperational;
    public String name;
    @Nullable
    public Boolean offPremiseServicesReachableIPv4;
    @Nullable
    public Boolean offPremiseServicesReachableIPv6;
    public int type;
}
