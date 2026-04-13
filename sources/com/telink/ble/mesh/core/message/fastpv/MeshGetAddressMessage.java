package com.telink.ble.mesh.core.message.fastpv;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.generic.GenericMessage;
import java.nio.ByteOrder;

public class MeshGetAddressMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t;

    public static MeshGetAddressMessage I(int destinationAddress, int appKeyIndex, int rspMax, int pid) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(appKeyIndex), new Integer(rspMax), new Integer(pid)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12529, new Class[]{cls, cls, cls, cls}, MeshGetAddressMessage.class);
        if (proxy.isSupported) {
            return (MeshGetAddressMessage) proxy.result;
        }
        MeshGetAddressMessage message = new MeshGetAddressMessage(destinationAddress, appKeyIndex);
        message.E(0);
        message.C(rspMax);
        message.t = pid;
        return message;
    }

    public MeshGetAddressMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
    }

    public int k() {
        return Opcode.VD_MESH_ADDR_GET.value;
    }

    public int o() {
        return Opcode.VD_MESH_ADDR_GET_STS.value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12530, new Class[0], byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : MeshUtils.l(this.t, 2, ByteOrder.LITTLE_ENDIAN);
    }
}
