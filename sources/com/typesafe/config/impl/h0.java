package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import com.typesafe.config.f;

/* compiled from: Token */
public class h0 {
    private final i0 a;
    private final String b;
    private final f c;
    private final String d;

    h0(i0 tokenType, f origin) {
        this(tokenType, origin, (String) null);
    }

    h0(i0 tokenType, f origin, String tokenText) {
        this(tokenType, origin, tokenText, (String) null);
    }

    h0(i0 tokenType, f origin, String tokenText, String debugString) {
        this.a = tokenType;
        this.c = origin;
        this.b = debugString;
        this.d = tokenText;
    }

    static h0 c(i0 tokenType, String debugString, String tokenText) {
        return new h0(tokenType, (f) null, tokenText, debugString);
    }

    public String e() {
        return this.d;
    }

    /* access modifiers changed from: package-private */
    public final f d() {
        f fVar = this.c;
        if (fVar != null) {
            return fVar;
        }
        throw new ConfigException.BugOrBroken("tried to get origin from token that doesn't have one: " + this);
    }

    /* access modifiers changed from: package-private */
    public final int b() {
        f fVar = this.c;
        if (fVar != null) {
            return fVar.b();
        }
        return -1;
    }

    public String toString() {
        String str = this.b;
        if (str != null) {
            return str;
        }
        return this.a.name();
    }

    /* access modifiers changed from: protected */
    public boolean a(Object other) {
        return other instanceof h0;
    }

    public boolean equals(Object other) {
        if (!(other instanceof h0) || !a(other) || this.a != ((h0) other).a) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
