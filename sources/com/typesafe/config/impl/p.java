package com.typesafe.config.impl;

/* compiled from: MemoKey */
public final class p {
    private final AbstractConfigValue a;
    private final s b;

    p(AbstractConfigValue value, s restrictToChildOrNull) {
        this.a = value;
        this.b = restrictToChildOrNull;
    }

    public final int hashCode() {
        int h = System.identityHashCode(this.a);
        s sVar = this.b;
        if (sVar != null) {
            return ((sVar.hashCode() + 41) * 41) + h;
        }
        return h;
    }

    public final boolean equals(Object other) {
        if (!(other instanceof p)) {
            return false;
        }
        p o = (p) other;
        if (o.a != this.a) {
            return false;
        }
        s sVar = o.b;
        s sVar2 = this.b;
        if (sVar == sVar2) {
            return true;
        }
        if (sVar == null || sVar2 == null) {
            return false;
        }
        return sVar.equals(sVar2);
    }

    public final String toString() {
        return "MemoKey(" + this.a + "@" + System.identityHashCode(this.a) + "," + this.b + ")";
    }
}
