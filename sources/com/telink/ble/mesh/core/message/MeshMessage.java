package com.telink.ble.mesh.core.message;

import android.text.TextUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.networking.AccessType;

public class MeshMessage extends PriorityMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    protected int c;
    protected byte[] d;
    protected byte[] e;
    protected AccessType f = AccessType.APPLICATION;
    protected int g;
    protected int h = 0;
    protected int i;
    protected int j = 0;
    protected int k = 10;
    protected int l = 2;
    protected int m = 0;
    protected boolean n = false;
    protected int o = -1;
    protected int p = -1;
    protected boolean q = false;
    protected int r;
    protected boolean s;

    public boolean u() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12421, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return o() != -1;
    }

    public int o() {
        return this.o;
    }

    public void D(int responseOpcode) {
        this.o = responseOpcode;
    }

    public int r() {
        return this.p;
    }

    public void H(int tidPosition) {
        this.p = tidPosition;
    }

    public int k() {
        return this.c;
    }

    public void z(int opcode) {
        this.c = opcode;
    }

    public boolean w() {
        return this.s;
    }

    public void G(boolean setCmd) {
        this.s = setCmd;
    }

    public byte[] l() {
        return this.d;
    }

    public void A(byte[] params) {
        this.d = params;
    }

    public int p() {
        return this.l;
    }

    public void E(int retryCnt) {
        this.l = retryCnt;
    }

    public int n() {
        return this.m;
    }

    public void C(int responseMax) {
        this.m = responseMax;
    }

    public void B(int propertyId) {
        if (!PatchProxy.proxy(new Object[]{new Integer(propertyId)}, this, changeQuickRedirect, false, 12422, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.n = true;
            this.r = propertyId;
            if (TextUtils.isEmpty(a())) {
                d(String.format("propertyId:0x%04X,opcode:0x%04X", new Object[]{Integer.valueOf(this.r), Integer.valueOf(k())}));
            }
        }
    }

    public boolean t() {
        return this.n;
    }

    public int m() {
        return this.r;
    }

    public AccessType g() {
        return this.f;
    }

    public int i() {
        return this.j;
    }

    public int s() {
        return this.k;
    }

    public int q() {
        return this.h;
    }

    public byte[] f() {
        return this.e;
    }

    public void x(byte[] accessKey) {
        this.e = accessKey;
    }

    public int j() {
        return this.i;
    }

    public void y(int destinationAddress) {
        this.i = destinationAddress;
    }

    public int h() {
        return this.g;
    }

    public boolean v() {
        return this.q;
    }

    public void F(boolean segmented) {
        this.q = segmented;
    }
}
