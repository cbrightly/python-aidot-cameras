package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;

/* compiled from: Path */
public final class s {
    private final String a;
    private final s b;

    s(String first, s remainder) {
        this.a = first;
        this.b = remainder;
    }

    /* access modifiers changed from: package-private */
    public String b() {
        return this.a;
    }

    /* access modifiers changed from: package-private */
    public s j() {
        return this.b;
    }

    /* access modifiers changed from: package-private */
    public s h() {
        if (this.b == null) {
            return null;
        }
        t pb = new t();
        for (s p = this; p.b != null; p = p.b) {
            pb.a(p.a);
        }
        return pb.d();
    }

    /* access modifiers changed from: package-private */
    public String d() {
        s p = this;
        while (p.b != null) {
            p = p.b;
        }
        return p.a;
    }

    /* access modifiers changed from: package-private */
    public s i(s toPrepend) {
        t pb = new t();
        pb.b(toPrepend);
        pb.b(this);
        return pb.d();
    }

    /* access modifiers changed from: package-private */
    public int e() {
        int count = 1;
        for (s p = this.b; p != null; p = p.b) {
            count++;
        }
        return count;
    }

    /* access modifiers changed from: package-private */
    public s l(int removeFromFront) {
        int count = removeFromFront;
        s p = this;
        while (p != null && count > 0) {
            count--;
            p = p.b;
        }
        return p;
    }

    /* access modifiers changed from: package-private */
    public s m(int firstIndex, int lastIndex) {
        if (lastIndex >= firstIndex) {
            s from = l(firstIndex);
            t pb = new t();
            int count = lastIndex - firstIndex;
            while (count > 0) {
                count--;
                pb.a(from.b());
                from = from.j();
                if (from == null) {
                    throw new ConfigException.BugOrBroken("subPath lastIndex out of range " + lastIndex);
                }
            }
            return pb.d();
        }
        throw new ConfigException.BugOrBroken("bad call to subPath");
    }

    public boolean equals(Object other) {
        if (!(other instanceof s)) {
            return false;
        }
        s that = (s) other;
        if (!this.a.equals(that.a) || !g.a(this.b, that.b)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i;
        int hashCode = (this.a.hashCode() + 41) * 41;
        s sVar = this.b;
        if (sVar == null) {
            i = 0;
        } else {
            i = sVar.hashCode();
        }
        return hashCode + i;
    }

    static boolean c(String s) {
        int length = s.length();
        if (length == 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '-' && c != '_') {
                return true;
            }
        }
        return false;
    }

    private void a(StringBuilder sb) {
        if (c(this.a) || this.a.isEmpty()) {
            sb.append(g.f(this.a));
        } else {
            sb.append(this.a);
        }
        if (this.b != null) {
            sb.append(".");
            this.b.a(sb);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Path(");
        a(sb);
        sb.append(")");
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public String k() {
        StringBuilder sb = new StringBuilder();
        a(sb);
        return sb.toString();
    }

    static s f(String key) {
        return new s(key, (s) null);
    }

    static s g(String path) {
        return u.d(path);
    }
}
