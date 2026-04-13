package com.telink.ble.mesh.core.message.fastpv;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.generic.GenericMessage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MeshSetAddressMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte[] t;
    private int u;

    public static MeshSetAddressMessage I(int destinationAddress, int appKeyIndex, byte[] mac, int newMeshAddress) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(appKeyIndex), mac, new Integer(newMeshAddress)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 12533, new Class[]{cls, cls, byte[].class, cls}, MeshSetAddressMessage.class);
        if (proxy.isSupported) {
            return (MeshSetAddressMessage) proxy.result;
        }
        MeshSetAddressMessage message = new MeshSetAddressMessage(destinationAddress, appKeyIndex);
        message.C(1);
        message.t = mac;
        message.u = newMeshAddress;
        return message;
    }

    public MeshSetAddressMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
    }

    public int k() {
        return Opcode.VD_MESH_ADDR_SET.value;
    }

    public int o() {
        return Opcode.VD_MESH_ADDR_SET_STS.value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12534, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).put(this.t).putShort((short) this.u).array();
    }
}
