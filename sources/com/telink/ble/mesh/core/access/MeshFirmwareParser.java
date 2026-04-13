package com.telink.ble.mesh.core.access;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class MeshFirmwareParser {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte[] a;
    private int b;
    private int c = 4096;
    private int d = 256;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i = -1;

    public void i(byte[] data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 12227, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            this.a = data;
            int length = data.length;
            this.b = length;
            this.e = -1;
            this.f = -1;
            this.i = -1;
            this.g = (int) Math.ceil(((double) length) / ((double) this.c));
            this.h = (int) Math.ceil(((double) this.b) / ((double) this.d));
        }
    }

    public void j(byte[] data, int blockSize, int chunkSize) {
        Object[] objArr = {data, new Integer(blockSize), new Integer(chunkSize)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12228, new Class[]{byte[].class, cls, cls}, Void.TYPE).isSupported) {
            this.c = blockSize;
            this.d = chunkSize;
            i(data);
        }
    }

    public boolean g() {
        return this.e + 1 < this.g;
    }

    public int e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12229, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (!g()) {
            int i2 = this.b;
            int i3 = this.c;
            if (i2 % i3 != 0) {
                return i2 % i3;
            }
        }
        return this.c;
    }

    public boolean k() {
        int progress = (int) ((99.0f * ((float) ((this.e * (this.c / this.d)) + (this.f + 1)))) / ((float) this.h));
        if (progress <= this.i) {
            return false;
        }
        this.i = progress;
        return true;
    }

    public int f() {
        return this.i;
    }

    public byte[] h() {
        int chunkSize;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12230, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        double blockSize = (double) e();
        int chunkNumber = (int) Math.ceil(blockSize / ((double) this.d));
        int i2 = this.f;
        if (i2 + 1 >= chunkNumber) {
            return null;
        }
        int i3 = i2 + 1;
        this.f = i3;
        if (i3 + 1 >= chunkNumber) {
            int i4 = this.d;
            if (blockSize % ((double) i4) != 0.0d) {
                chunkSize = (int) (blockSize % ((double) i4));
                byte[] chunkData = new byte[chunkSize];
                System.arraycopy(this.a, (this.e * this.c) + (i3 * this.d), chunkData, 0, chunkSize);
                return chunkData;
            }
        }
        chunkSize = this.d;
        byte[] chunkData2 = new byte[chunkSize];
        System.arraycopy(this.a, (this.e * this.c) + (i3 * this.d), chunkData2, 0, chunkSize);
        return chunkData2;
    }

    public byte[] a(int chunkIndex) {
        int chunkSize;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(chunkIndex)}, this, changeQuickRedirect, false, 12231, new Class[]{Integer.TYPE}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        double blockSize = (double) e();
        int chunkNumber = (int) Math.ceil(blockSize / ((double) this.d));
        if (chunkIndex >= chunkNumber) {
            return null;
        }
        if (chunkIndex + 1 >= chunkNumber) {
            int i2 = this.d;
            if (blockSize % ((double) i2) != 0.0d) {
                chunkSize = (int) (blockSize % ((double) i2));
                byte[] chunkData = new byte[chunkSize];
                System.arraycopy(this.a, (this.e * this.c) + (this.d * chunkIndex), chunkData, 0, chunkSize);
                return chunkData;
            }
        }
        chunkSize = this.d;
        byte[] chunkData2 = new byte[chunkSize];
        System.arraycopy(this.a, (this.e * this.c) + (this.d * chunkIndex), chunkData2, 0, chunkSize);
        return chunkData2;
    }

    public int b() {
        return this.e;
    }

    public int c() {
        return this.f;
    }

    public int d() {
        return this.d;
    }
}
