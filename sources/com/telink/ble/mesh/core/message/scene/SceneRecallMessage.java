package com.telink.ble.mesh.core.message.scene;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.generic.GenericMessage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SceneRecallMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t;
    private byte u = 0;
    private byte v = 0;
    private byte w = 0;
    private boolean x = false;
    private boolean y = false;

    public static SceneRecallMessage I(int address, int appKeyIndex, int sceneNumber, boolean ack, int rspMax) {
        Object[] objArr = {new Integer(address), new Integer(appKeyIndex), new Integer(sceneNumber), new Byte(ack ? (byte) 1 : 0), new Integer(rspMax)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12761, new Class[]{cls, cls, cls, Boolean.TYPE, cls}, SceneRecallMessage.class);
        if (proxy.isSupported) {
            return (SceneRecallMessage) proxy.result;
        }
        SceneRecallMessage message = new SceneRecallMessage(address, appKeyIndex);
        message.t = sceneNumber;
        message.x = ack;
        message.C(rspMax);
        return message;
    }

    public SceneRecallMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
        H(2);
        e(50);
    }

    public int o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12762, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return this.x ? Opcode.SCENE_STATUS.value : super.o();
    }

    public int k() {
        return (this.x ? Opcode.SCENE_RECALL : Opcode.SCENE_RECALL_NOACK).value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12763, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (this.y) {
            return ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.t).put(this.u).put(this.v).put(this.w).array();
        }
        return ByteBuffer.allocate(3).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.t).put(this.u).array();
    }

    public void K(byte transitionTime) {
        this.v = transitionTime;
    }

    public void J(boolean complete) {
        this.y = complete;
    }
}
