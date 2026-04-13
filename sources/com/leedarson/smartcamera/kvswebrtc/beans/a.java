package com.leedarson.smartcamera.kvswebrtc.beans;

import android.graphics.Color;
import com.leedarson.base.utils.i;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.apache.commons.math3.linear.d;
import org.apache.commons.math3.linear.q;

/* compiled from: LdsRadarData */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    public long a;
    public long b;
    public int c;
    public String d;
    public int e;
    public float f;
    public float g;
    public int h;
    public int i;
    public int j;
    public int k;
    public float l;
    public float m;
    public float n;
    public float o;
    public int p;
    public int q;
    public int r = Color.parseColor("#FFFFFF");
    public int s;
    public int t;
    public boolean u;
    public double v = -1.0d;
    public double w = -1.0d;

    public a(long timestamp, int id, int x, int y, int z, int xSpeed, int ySpeed, int zSpeed) {
        this.a = timestamp;
        this.b = timestamp / 1000;
        this.e = id;
        this.f = (float) x;
        this.g = (float) y;
        this.h = z;
        this.i = xSpeed;
        this.j = ySpeed;
        this.k = zSpeed;
        this.d = i.a(timestamp, "yyyy-MM-dd HH:mm:ss:SSS");
    }

    public void a(float xPer, float yPer) {
        this.l = this.f * xPer;
        this.m = (-this.g) * yPer;
    }

    public void d(int color) {
        this.p = color;
    }

    public final q c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9925, new Class[0], q.class);
        if (proxy.isSupported) {
            return (q) proxy.result;
        }
        q state = new d(4);
        state.setEntry(0, (double) this.f);
        state.setEntry(1, (double) this.g);
        return state;
    }

    public final q b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9926, new Class[0], q.class);
        if (proxy.isSupported) {
            return (q) proxy.result;
        }
        return new d(new double[]{(double) this.f, (double) this.g});
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9927, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "{ id=" + this.e + ",x=" + this.f + ",y=" + this.g + ",timestamp=" + this.a + '}';
    }
}
