package com.telink.ble.mesh.entity;

import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class FastProvisioningConfiguration {
    public static final byte[] a = {125, -41, 54, 76, -40, 66, -83, 24, -63, 124, 116, 101, 108, 105, 110, 107};
    public static final byte[] b = {99, -106, 71, 113, 115, 79, -67, 118, -29, -76, 116, 101, 108, 105, 110, 107};
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;
    private SparseIntArray e;
    private int f;
    private int g;
    @NonNull
    private byte[] h;
    private int i = 0;
    @NonNull
    private byte[] j;
    private int k = 0;

    public int e(int pid) {
        Object[] objArr = {new Integer(pid)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13010, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        SparseIntArray sparseIntArray = this.e;
        if (sparseIntArray != null) {
            return sparseIntArray.get(pid);
        }
        return 0;
    }

    public int g() {
        return this.d;
    }

    public void j(int elementCount) {
        this.d += elementCount;
    }

    public int i() {
        return this.f;
    }

    public int f() {
        return this.g;
    }

    @NonNull
    public byte[] c() {
        return this.h;
    }

    public int d() {
        return this.i;
    }

    @NonNull
    public byte[] a() {
        return this.j;
    }

    public int b() {
        return this.k;
    }

    public int h() {
        return this.c;
    }
}
