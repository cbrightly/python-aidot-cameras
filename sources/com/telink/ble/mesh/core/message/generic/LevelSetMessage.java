package com.telink.ble.mesh.core.message.generic;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;

public class LevelSetMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t;
    private byte u;
    private byte v;
    private byte w;
    private boolean x;
    private boolean y;

    public int k() {
        return (this.x ? Opcode.G_LEVEL_SET : Opcode.G_LEVEL_SET_NOACK).value;
    }

    public byte[] l() {
        if (this.y) {
            int i = this.t;
            return new byte[]{(byte) i, (byte) (i >> 8), this.u, this.v, this.w};
        }
        int i2 = this.t;
        return new byte[]{(byte) i2, (byte) (i2 >> 8), this.u};
    }
}
