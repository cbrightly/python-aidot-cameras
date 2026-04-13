package com.telink.ble.mesh.core.message.fastpv;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.generic.GenericMessage;

public class MeshSetNetInfoMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte[] t;

    public static MeshSetNetInfoMessage I(int destinationAddress, int appKeyIndex, byte[] netInfoData) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(appKeyIndex), netInfoData};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 12535, new Class[]{cls, cls, byte[].class}, MeshSetNetInfoMessage.class);
        if (proxy.isSupported) {
            return (MeshSetNetInfoMessage) proxy.result;
        }
        MeshSetNetInfoMessage message = new MeshSetNetInfoMessage(destinationAddress, appKeyIndex);
        message.t = netInfoData;
        return message;
    }

    public MeshSetNetInfoMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
    }

    public int k() {
        return Opcode.VD_MESH_PROV_DATA_SET.value;
    }

    public int o() {
        return -1;
    }

    public byte[] l() {
        return this.t;
    }
}
