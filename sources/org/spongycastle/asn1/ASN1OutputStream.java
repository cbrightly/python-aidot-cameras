package org.spongycastle.asn1;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.io.IOException;
import java.io.OutputStream;

public class ASN1OutputStream {
    private OutputStream a;

    public ASN1OutputStream(OutputStream os) {
        this.a = os;
    }

    /* access modifiers changed from: package-private */
    public void i(int length) {
        if (length > 127) {
            int size = 1;
            int val = length;
            while (true) {
                int i = val >>> 8;
                val = i;
                if (i == 0) {
                    break;
                }
                size++;
            }
            c((byte) (size | 128));
            for (int i2 = (size - 1) * 8; i2 >= 0; i2 -= 8) {
                c((byte) (length >> i2));
            }
            return;
        }
        c((byte) length);
    }

    /* access modifiers changed from: package-private */
    public void c(int b) {
        this.a.write(b);
    }

    /* access modifiers changed from: package-private */
    public void d(byte[] bytes) {
        this.a.write(bytes);
    }

    /* access modifiers changed from: package-private */
    public void e(byte[] bytes, int off, int len) {
        this.a.write(bytes, off, len);
    }

    /* access modifiers changed from: package-private */
    public void g(int tag, byte[] bytes) {
        c(tag);
        i(bytes.length);
        d(bytes);
    }

    /* access modifiers changed from: package-private */
    public void k(int flags, int tagNo) {
        if (tagNo < 31) {
            c(flags | tagNo);
            return;
        }
        c(flags | 31);
        if (tagNo < 128) {
            c(tagNo);
            return;
        }
        byte[] stack = new byte[5];
        int pos = stack.length - 1;
        stack[pos] = (byte) (tagNo & NeedPermissionEvent.PER_IPC_SPEAK_PERM);
        do {
            tagNo >>= 7;
            pos--;
            stack[pos] = (byte) ((tagNo & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128);
        } while (tagNo > 127);
        e(stack, pos, stack.length - pos);
    }

    /* access modifiers changed from: package-private */
    public void f(int flags, int tagNo, byte[] bytes) {
        k(flags, tagNo);
        i(bytes.length);
        d(bytes);
    }

    public void j(ASN1Encodable obj) {
        if (obj != null) {
            obj.toASN1Primitive().f(this);
            return;
        }
        throw new IOException("null object detected");
    }

    /* access modifiers changed from: package-private */
    public void h(ASN1Primitive obj) {
        if (obj != null) {
            obj.f(new ImplicitOutputStream(this.a));
            return;
        }
        throw new IOException("null object detected");
    }

    /* access modifiers changed from: package-private */
    public ASN1OutputStream a() {
        return new DEROutputStream(this.a);
    }

    /* access modifiers changed from: package-private */
    public ASN1OutputStream b() {
        return new DLOutputStream(this.a);
    }

    public class ImplicitOutputStream extends ASN1OutputStream {
        private boolean b = true;

        public ImplicitOutputStream(OutputStream os) {
            super(os);
        }

        public void c(int b2) {
            if (this.b) {
                this.b = false;
            } else {
                ASN1OutputStream.super.c(b2);
            }
        }
    }
}
