package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import java.util.Stack;

/* compiled from: PathBuilder */
public final class t {
    private final Stack<String> a = new Stack<>();
    private s b;

    t() {
    }

    private void c() {
        if (this.b != null) {
            throw new ConfigException.BugOrBroken("Adding to PathBuilder after getting result");
        }
    }

    /* access modifiers changed from: package-private */
    public void a(String key) {
        c();
        this.a.push(key);
    }

    /* access modifiers changed from: package-private */
    public void b(s path) {
        c();
        String first = path.b();
        s remainder = path.j();
        while (true) {
            this.a.push(first);
            if (remainder != null) {
                first = remainder.b();
                remainder = remainder.j();
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public s d() {
        if (this.b == null) {
            s remainder = null;
            while (!this.a.isEmpty()) {
                remainder = new s(this.a.pop(), remainder);
            }
            this.b = remainder;
        }
        return this.b;
    }
}
