package com.airbnb.lottie.model;

import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: KeyPath */
public class e {
    public static final e a = new e("COMPOSITION");
    private final List<String> b;
    @Nullable
    private f c;

    public e(String... keys) {
        this.b = Arrays.asList(keys);
    }

    private e(e keyPath) {
        this.b = new ArrayList(keyPath.b);
        this.c = keyPath.c;
    }

    @CheckResult
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public e a(String key) {
        e newKeyPath = new e(this);
        newKeyPath.b.add(key);
        return newKeyPath;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public e i(f element) {
        e keyPath = new e(this);
        keyPath.c = element;
        return keyPath;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public f d() {
        return this.c;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean g(String key, int depth) {
        if (f(key)) {
            return true;
        }
        if (depth >= this.b.size()) {
            return false;
        }
        if (this.b.get(depth).equals(key) || this.b.get(depth).equals("**") || this.b.get(depth).equals(org.slf4j.e.ANY_MARKER)) {
            return true;
        }
        return false;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int e(String key, int depth) {
        if (f(key)) {
            return 0;
        }
        if (!this.b.get(depth).equals("**")) {
            return 1;
        }
        if (depth != this.b.size() - 1 && this.b.get(depth + 1).equals(key)) {
            return 2;
        }
        return 0;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean c(String key, int depth) {
        if (depth >= this.b.size()) {
            return false;
        }
        boolean isLastDepth = depth == this.b.size() - 1;
        String keyAtDepth = this.b.get(depth);
        if (!keyAtDepth.equals("**")) {
            boolean matches = keyAtDepth.equals(key) || keyAtDepth.equals(org.slf4j.e.ANY_MARKER);
            if ((isLastDepth || (depth == this.b.size() - 2 && b())) && matches) {
                return true;
            }
            return false;
        }
        if (!isLastDepth && this.b.get(depth + 1).equals(key)) {
            if (depth == this.b.size() - 2 || (depth == this.b.size() - 3 && b())) {
                return true;
            }
            return false;
        } else if (isLastDepth) {
            return true;
        } else {
            if (depth + 1 < this.b.size() - 1) {
                return false;
            }
            return this.b.get(depth + 1).equals(key);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean h(String key, int depth) {
        if (!"__container".equals(key) && depth >= this.b.size() - 1 && !this.b.get(depth).equals("**")) {
            return false;
        }
        return true;
    }

    private boolean f(String key) {
        return "__container".equals(key);
    }

    private boolean b() {
        List<String> list = this.b;
        return list.get(list.size() - 1).equals("**");
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        e keyPath = (e) o;
        if (!this.b.equals(keyPath.b)) {
            return false;
        }
        f fVar = this.c;
        if (fVar != null) {
            return fVar.equals(keyPath.c);
        }
        if (keyPath.c == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode = this.b.hashCode() * 31;
        f fVar = this.c;
        return hashCode + (fVar != null ? fVar.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("KeyPath{keys=");
        sb.append(this.b);
        sb.append(",resolved=");
        sb.append(this.c != null);
        sb.append('}');
        return sb.toString();
    }
}
