package com.telink.ble.mesh.entity;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class TransitionTime {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte a;
    private byte b;

    public TransitionTime(byte number, byte step) {
        this.a = number;
        this.b = step;
    }

    public static TransitionTime a(long millisecond) {
        Object[] objArr = {new Long(millisecond)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 13062, new Class[]{Long.TYPE}, TransitionTime.class);
        if (proxy.isSupported) {
            return (TransitionTime) proxy.result;
        }
        byte step = 0;
        byte number = 0;
        if (millisecond <= 0) {
            step = 0;
            number = 0;
        } else if (millisecond <= 6300) {
            step = 0;
            number = (byte) ((int) (millisecond / 100));
        } else if (millisecond <= 63000) {
            step = 1;
            number = (byte) ((int) (millisecond / 1000));
        } else if (millisecond <= 630000) {
            step = 2;
            number = (byte) ((int) (millisecond / 10000));
        } else if (millisecond <= 37800000) {
            step = 3;
            number = (byte) ((int) (millisecond / 600000));
        }
        return new TransitionTime(number, step);
    }

    public byte d() {
        return (byte) ((this.b << 6) | this.a);
    }

    public byte b() {
        return this.a;
    }

    public int c() {
        switch (this.b) {
            case 0:
                return 100;
            case 1:
                return 1000;
            case 2:
                return 10000;
            case 3:
                return 600000;
            default:
                return 0;
        }
    }
}
