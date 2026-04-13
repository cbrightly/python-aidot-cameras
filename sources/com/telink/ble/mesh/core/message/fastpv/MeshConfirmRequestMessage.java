package com.telink.ble.mesh.core.message.fastpv;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.generic.GenericMessage;

public class MeshConfirmRequestMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static MeshConfirmRequestMessage I(int destinationAddress, int appKeyIndex) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(appKeyIndex)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12528, new Class[]{cls, cls}, MeshConfirmRequestMessage.class);
        if (proxy.isSupported) {
            return (MeshConfirmRequestMessage) proxy.result;
        }
        MeshConfirmRequestMessage message = new MeshConfirmRequestMessage(destinationAddress, appKeyIndex);
        message.C(0);
        message.E(1);
        return message;
    }

    public MeshConfirmRequestMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
    }

    public int k() {
        return Opcode.VD_MESH_PROV_CONFIRM.value;
    }

    public int o() {
        return Opcode.VD_MESH_PROV_CONFIRM_STS.value;
    }

    public byte[] l() {
        return null;
    }
}
