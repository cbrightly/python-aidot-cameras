package com.telink.ble.mesh.foundation;

import android.util.SparseArray;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class MeshConfiguration {
    public static ChangeQuickRedirect changeQuickRedirect;
    public int a;
    public byte[] b;
    public SparseArray<byte[]> c = new SparseArray<>();
    public int d;
    public int e;
    public int f;
    public SparseArray<byte[]> g = new SparseArray<>();

    public int b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13083, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (this.c.size() > 0) {
            return this.c.keyAt(0);
        }
        return 0;
    }

    public byte[] a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13084, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (this.c.size() > 0) {
            return this.c.valueAt(0);
        }
        return null;
    }
}
