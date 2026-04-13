package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.InputStream;

/* compiled from: MultiByteArrayInputStream */
public class v extends InputStream {
    private byte[] c;
    private int d;
    private int f;
    private byte[] q;
    private int x;
    private int y;
    private int z = 0;

    public v(byte[] bytesA, int offsetA, int lengthA, byte[] bytesB, int offsetB, int lengthB) {
        this.c = (byte[]) bytesA.clone();
        this.q = (byte[]) bytesB.clone();
        this.d = offsetA;
        this.x = offsetB;
        this.f = lengthA;
        this.y = lengthB;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: byte} */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read() {
        /*
            r5 = this;
            r0 = -1
            int r1 = r5.z
            int r2 = r5.f
            if (r1 >= r2) goto L_0x000f
            byte[] r2 = r5.c
            int r3 = r5.d
            int r3 = r3 + r1
            byte r0 = r2[r3]
            goto L_0x001d
        L_0x000f:
            int r3 = r5.y
            int r3 = r3 + r2
            if (r1 >= r3) goto L_0x0026
            byte[] r3 = r5.q
            int r4 = r5.x
            int r4 = r4 + r1
            int r4 = r4 - r2
            byte r0 = r3[r4]
        L_0x001d:
            if (r0 >= 0) goto L_0x0021
            int r0 = r0 + 256
        L_0x0021:
            int r1 = r1 + 1
            r5.z = r1
            return r0
        L_0x0026:
            r1 = -1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.wire.v.read():int");
    }
}
