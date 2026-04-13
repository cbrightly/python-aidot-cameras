package com.typesafe.config.impl;

import com.typesafe.config.f;
import com.typesafe.config.g;
import com.typesafe.config.j;
import com.typesafe.config.k;
import java.io.Serializable;

/* compiled from: ConfigString */
public abstract class m extends AbstractConfigValue implements Serializable {
    private static final long serialVersionUID = 2;
    protected final String value;

    protected m(f origin, String value2) {
        super(origin);
        this.value = value2;
    }

    /* compiled from: ConfigString */
    public static final class a extends m {
        public /* bridge */ /* synthetic */ Object unwrapped() {
            return m.super.unwrapped();
        }

        a(f origin, String value) {
            super(origin, value);
        }

        /* access modifiers changed from: protected */
        public a newCopy(f origin) {
            return new a(origin, this.value);
        }

        private Object writeReplace() {
            return new b0((j) this);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean wasQuoted() {
        return this instanceof a;
    }

    public k valueType() {
        return k.STRING;
    }

    public String unwrapped() {
        return this.value;
    }

    /* access modifiers changed from: package-private */
    public String transformToString() {
        return this.value;
    }

    /* access modifiers changed from: protected */
    public void render(StringBuilder sb, int indent, boolean atRoot, g options) {
        String rendered;
        if (options.e()) {
            rendered = g.f(this.value);
        } else {
            rendered = g.g(this.value);
        }
        sb.append(rendered);
    }
}
