package com.telink.ble.mesh.core.message.fastpv;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.generic.GenericMessage;
import java.nio.ByteOrder;

public class ResetNetworkMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t;

    public static ResetNetworkMessage I(int destinationAddress, int appKeyIndex, int delay) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(appKeyIndex), new Integer(delay)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12536, new Class[]{cls, cls, cls}, ResetNetworkMessage.class);
        if (proxy.isSupported) {
            return (ResetNetworkMessage) proxy.result;
        }
        ResetNetworkMessage message = new ResetNetworkMessage(destinationAddress, appKeyIndex);
        message.t = delay;
        return message;
    }

    public ResetNetworkMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
    }

    public int k() {
        return Opcode.VD_MESH_RESET_NETWORK.value;
    }

    public int o() {
        return -1;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12537, new Class[0], byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : MeshUtils.l(this.t, 2, ByteOrder.LITTLE_ENDIAN);
    }
}
