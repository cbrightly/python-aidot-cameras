package com.leedarson.serviceimpl.business.bean;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class OTACommand {
    public static final byte CMD_ID_END_OTA = 3;
    public static final byte CMD_ID_SEND_OTA = 2;
    public static final byte CMD_ID_SEND_OTA_MD5 = 4;
    public static final byte CMD_ID_SEND_OTA_POINT = 5;
    public static final byte CMD_ID_START_OTA = 1;
    public static final byte CMD_ID_VERSION = 0;
    public static final byte RESP_CMD_ID_SEND_OTA_POINT = -123;
    public static final byte RESP_ID_END_OTA = -125;
    public static final byte RESP_ID_END_OTA_MD5 = -124;
    public static final byte RESP_ID_SEND_OTA = -126;
    public static final byte RESP_ID_START_OTA = -127;
    public static final byte RESP_ID_VERSION = Byte.MIN_VALUE;
    public static ChangeQuickRedirect changeQuickRedirect;
    public byte commandId;
    public String mac;
    public byte packetFlag;
    public byte[] payload;
    public int seqNum;

    public OTACommand(String mac2, int seqNum2) {
        this.mac = mac2;
        this.seqNum = seqNum2;
    }

    public byte[] toDataFrame() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7110, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] bArr = this.payload;
        int payloadLen = bArr == null ? 0 : bArr.length;
        byte[] frame = new byte[(payloadLen + 4)];
        frame[0] = (byte) this.seqNum;
        frame[1] = this.packetFlag;
        frame[2] = this.commandId;
        frame[3] = (byte) payloadLen;
        if (bArr != null) {
            System.arraycopy(bArr, 0, frame, 4, bArr.length);
        }
        return frame;
    }
}
