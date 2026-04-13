package com.telink.ble.mesh.core.message.rp;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import java.nio.ByteBuffer;

public class ProvisioningPduSendMessage extends RemoteProvisionMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte t;
    private byte[] u;

    public static ProvisioningPduSendMessage I(int destinationAddress, int rspMax, byte outboundPDUNumber, byte[] provisioningPDU) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(rspMax), new Byte(outboundPDUNumber), provisioningPDU};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12744, new Class[]{cls, cls, Byte.TYPE, byte[].class}, ProvisioningPduSendMessage.class);
        if (proxy.isSupported) {
            return (ProvisioningPduSendMessage) proxy.result;
        }
        ProvisioningPduSendMessage message = new ProvisioningPduSendMessage(destinationAddress);
        message.C(rspMax);
        message.t = outboundPDUNumber;
        message.u = provisioningPDU;
        return message;
    }

    public ProvisioningPduSendMessage(int destinationAddress) {
        super(destinationAddress);
    }

    public int k() {
        return Opcode.REMOTE_PROV_PDU_SEND.value;
    }

    public int o() {
        return -1;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12745, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return ByteBuffer.allocate(this.u.length + 1).put(this.t).put(this.u).array();
    }
}
