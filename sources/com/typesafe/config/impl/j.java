package com.typesafe.config.impl;

import com.google.maps.android.BuildConfig;
import com.typesafe.config.f;
import com.typesafe.config.g;
import com.typesafe.config.k;
import java.io.Serializable;

/* compiled from: ConfigNull */
public final class j extends AbstractConfigValue implements Serializable {
    private static final long serialVersionUID = 2;

    j(f origin) {
        super(origin);
    }

    public k valueType() {
        return k.NULL;
    }

    public Object unwrapped() {
        return null;
    }

    /* access modifiers changed from: package-private */
    public String transformToString() {
        return BuildConfig.TRAVIS;
    }

    /* access modifiers changed from: protected */
    public void render(StringBuilder sb, int indent, boolean atRoot, g options) {
        sb.append(BuildConfig.TRAVIS);
    }

    /* access modifiers changed from: protected */
    public j newCopy(f origin) {
        return new j(origin);
    }

    private Object writeReplace() {
        return new b0((com.typesafe.config.j) this);
    }
}
