package org.apache.commons.math3.complex;

import java.io.Serializable;

/* compiled from: ComplexField */
public class b implements org.apache.commons.math3.a<a>, Serializable {
    private static final long serialVersionUID = -6130362688700788798L;

    /* renamed from: org.apache.commons.math3.complex.b$b  reason: collision with other inner class name */
    /* compiled from: ComplexField */
    public static class C0483b {
        /* access modifiers changed from: private */
        public static final b a = new b();
    }

    private b() {
    }

    public static b getInstance() {
        return C0483b.a;
    }

    public a getOne() {
        return a.ONE;
    }

    public a getZero() {
        return a.ZERO;
    }

    public Class<?> getRuntimeClass() {
        return a.class;
    }

    private Object readResolve() {
        return C0483b.a;
    }
}
