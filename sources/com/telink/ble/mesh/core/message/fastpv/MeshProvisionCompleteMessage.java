package com.telink.ble.mesh.core.message.fastpv;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.generic.GenericMessage;
import java.nio.ByteOrder;

public class MeshProvisionCompleteMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t;

    public static MeshProvisionCompleteMessage I(int destinationAddress, int appKeyIndex, int delay) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(appKeyIndex), new Integer(delay)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12531, new Class[]{cls, cls, cls}, MeshProvisionCompleteMessage.class);
        if (proxy.isSupported) {
            return (MeshProvisionCompleteMessage) proxy.result;
        }
        MeshProvisionCompleteMessage message = new MeshProvisionCompleteMessage(destinationAddress, appKeyIndex);
        message.t = delay;
        return message;
    }

    public MeshProvisionCompleteMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
    }

    public int k() {
        return Opcode.VD_MESH_PROV_COMPLETE.value;
    }

    public int o() {
        return -1;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12532, new Class[0], byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : MeshUtils.l(this.t, 2, ByteOrder.LITTLE_ENDIAN);
    }
}
