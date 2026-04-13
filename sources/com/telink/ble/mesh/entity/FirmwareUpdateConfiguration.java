package com.telink.ble.mesh.entity;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.List;

public class FirmwareUpdateConfiguration {
    public static ChangeQuickRedirect changeQuickRedirect;
    private List<MeshUpdatingDevice> a;
    private byte[] b;
    private byte[] c;
    private int d;
    private int e;
    private long f;
    private boolean g;
    private int h;

    public List<MeshUpdatingDevice> g() {
        return this.a;
    }

    public byte[] d() {
        return this.b;
    }

    public int a() {
        return this.d;
    }

    public int e() {
        return this.e;
    }

    public long b() {
        return this.f;
    }

    public boolean h() {
        return this.g;
    }

    public void j(boolean singleAndDirect) {
        this.g = singleAndDirect;
    }

    public int c() {
        return this.h;
    }

    public void i(int dleLength) {
        this.h = dleLength;
    }

    public byte[] f() {
        return this.c;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13018, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "FirmwareUpdateConfiguration{updatingDevices=" + this.a.size() + ", firmwareData=" + this.b.length + ", metadata=" + this.c.length + ", appKeyIndex=" + this.d + ", groupAddress=" + this.e + ", blobId=" + this.f + '}';
    }
}
