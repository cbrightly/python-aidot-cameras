package com.telink.ble.mesh.core.provisioning.pdu;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ProvisioningRecordRequestPDU implements ProvisioningStatePDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    public int a;
    public int b;
    public int c;

    public ProvisioningRecordRequestPDU(int recordID, int fragmentOffset, int fragmentMaxSize) {
        this.a = recordID;
        this.b = fragmentOffset;
        this.c = fragmentMaxSize;
    }

    public byte[] a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12973, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return ByteBuffer.allocate(6).order(ByteOrder.BIG_ENDIAN).putShort((short) this.a).putShort((short) this.b).putShort((short) this.c).array();
    }

    public byte getState() {
        return 10;
    }
}
