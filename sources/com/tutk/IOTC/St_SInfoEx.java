package com.tutk.IOTC;

import java.util.Arrays;

public class St_SInfoEx {
    public byte CorD;
    public int GID;
    public int IOTCVersion;
    public byte LocalNatType;
    public byte Mode;
    public int NetState;
    public int PID;
    public int RX_count;
    public byte RelayType;
    public byte[] RemoteIP = new byte[17];
    public byte RemoteNatType;
    public int RemotePort;
    public byte[] RemoteWANIP = new byte[17];
    public int RemoteWANPort;
    public int TX_count;
    public byte[] UID = new byte[21];
    public int VID;
    public byte isNebula;
    public byte isSecure;
    public byte isUseAuthkey;
    public int size;

    public String toString() {
        return "St_SInfoEx{size=" + this.size + ", Mode=" + this.Mode + ", CorD=" + this.CorD + ", UID=" + Arrays.toString(this.UID) + ", RemoteIP=" + Arrays.toString(this.RemoteIP) + ", RemotePort=" + this.RemotePort + ", TX_count=" + this.TX_count + ", RX_count=" + this.RX_count + ", VID=" + this.VID + ", PID=" + this.PID + ", GID=" + this.GID + ", IOTCVersion=" + this.IOTCVersion + ", isSecure=" + this.isSecure + ", LocalNatType=" + this.LocalNatType + ", RemoteNatType=" + this.RemoteNatType + ", RelayType=" + this.RelayType + ", NetState=" + this.NetState + ", RemoteWANIP=" + Arrays.toString(this.RemoteWANIP) + ", RemoteWANPort=" + this.RemoteWANPort + ", isNebula=" + this.isNebula + ", isUseAuthkey=" + this.isUseAuthkey + '}';
    }
}
