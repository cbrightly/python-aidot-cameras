package com.king.zxing.camera.open;

import android.hardware.Camera;
import io.netty.util.internal.StringUtil;

/* compiled from: OpenCamera */
public final class b {
    private final int a;
    private final Camera b;
    private final a c;
    private final int d;

    public b(int index, Camera camera, a facing, int orientation) {
        this.a = index;
        this.b = camera;
        this.c = facing;
        this.d = orientation;
    }

    public Camera a() {
        return this.b;
    }

    public a b() {
        return this.c;
    }

    public int c() {
        return this.d;
    }

    public String toString() {
        return "Camera #" + this.a + " : " + this.c + StringUtil.COMMA + this.d;
    }
}
