package com.telink.ble.mesh.core.message.scene;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.generic.GenericMessage;

public class SceneStoreMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t;
    private boolean u;

    public static SceneStoreMessage I(int address, int appKeyIndex, int sceneNumber, boolean ack, int rspMax) {
        Object[] objArr = {new Integer(address), new Integer(appKeyIndex), new Integer(sceneNumber), new Byte(ack ? (byte) 1 : 0), new Integer(rspMax)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12775, new Class[]{cls, cls, cls, Boolean.TYPE, cls}, SceneStoreMessage.class);
        if (proxy.isSupported) {
            return (SceneStoreMessage) proxy.result;
        }
        SceneStoreMessage message = new SceneStoreMessage(address, appKeyIndex);
        message.t = sceneNumber;
        message.u = ack;
        message.C(rspMax);
        return message;
    }

    public SceneStoreMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
        e(90);
    }

    public int o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12776, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return this.u ? Opcode.SCENE_REG_STATUS.value : super.o();
    }

    public int k() {
        return (this.u ? Opcode.SCENE_STORE : Opcode.SCENE_STORE_NOACK).value;
    }

    public byte[] l() {
        int i = this.t;
        return new byte[]{(byte) i, (byte) (i >> 8)};
    }
}
