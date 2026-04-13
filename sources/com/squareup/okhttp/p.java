package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.g;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/* compiled from: Headers */
public final class p {
    private final String[] a;

    private p(b builder) {
        this.a = (String[]) builder.a.toArray(new String[builder.a.size()]);
    }

    public String a(String name) {
        return b(this.a, name);
    }

    public Date c(String name) {
        String value = a(name);
        if (value != null) {
            return g.b(value);
        }
        return null;
    }

    public int f() {
        return this.a.length / 2;
    }

    public String d(int index) {
        int nameIndex = index * 2;
        if (nameIndex < 0) {
            return null;
        }
        String[] strArr = this.a;
        if (nameIndex >= strArr.length) {
            return null;
        }
        return strArr[nameIndex];
    }

    public String g(int index) {
        int valueIndex = (index * 2) + 1;
        if (valueIndex < 0) {
            return null;
        }
        String[] strArr = this.a;
        if (valueIndex >= strArr.length) {
            return null;
        }
        return strArr[valueIndex];
    }

    public List<String> h(String name) {
        List<String> result = null;
        int size = f();
        for (int i = 0; i < size; i++) {
            if (name.equalsIgnoreCase(d(i))) {
                if (result == null) {
                    result = new ArrayList<>(2);
                }
                result.add(g(i));
            }
        }
        if (result != null) {
            return Collections.unmodifiableList(result);
        }
        return Collections.emptyList();
    }

    public b e() {
        b result = new b();
        Collections.addAll(result.a, this.a);
        return result;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        int size = f();
        for (int i = 0; i < size; i++) {
            result.append(d(i));
            result.append(": ");
            result.append(g(i));
            result.append("\n");
        }
        return result.toString();
    }

    private static String b(String[] namesAndValues, String name) {
        for (int i = namesAndValues.length - 2; i >= 0; i -= 2) {
            if (name.equalsIgnoreCase(namesAndValues[i])) {
                return namesAndValues[i + 1];
            }
        }
        return null;
    }

    /* compiled from: Headers */
    public static final class b {
        /* access modifiers changed from: private */
        public final List<String> a = new ArrayList(20);

        /* access modifiers changed from: package-private */
        public b c(String line) {
            int index = line.indexOf(":", 1);
            if (index != -1) {
                return d(line.substring(0, index), line.substring(index + 1));
            }
            if (line.startsWith(":")) {
                return d("", line.substring(1));
            }
            return d("", line);
        }

        public b b(String name, String value) {
            f(name, value);
            return d(name, value);
        }

        /* access modifiers changed from: package-private */
        public b d(String name, String value) {
            this.a.add(name);
            this.a.add(value.trim());
            return this;
        }

        public b g(String name) {
            int i = 0;
            while (i < this.a.size()) {
                if (name.equalsIgnoreCase(this.a.get(i))) {
                    this.a.remove(i);
                    this.a.remove(i);
                    i -= 2;
                }
                i += 2;
            }
            return this;
        }

        public b h(String name, String value) {
            f(name, value);
            g(name);
            d(name, value);
            return this;
        }

        private void f(String name, String value) {
            if (name == null) {
                throw new IllegalArgumentException("name == null");
            } else if (!name.isEmpty()) {
                int length = name.length();
                for (int i = 0; i < length; i++) {
                    char c = name.charAt(i);
                    if (c <= 31 || c >= 127) {
                        throw new IllegalArgumentException(String.format("Unexpected char %#04x at %d in header name: %s", new Object[]{Integer.valueOf(c), Integer.valueOf(i), name}));
                    }
                }
                if (value != null) {
                    int length2 = value.length();
                    for (int i2 = 0; i2 < length2; i2++) {
                        char c2 = value.charAt(i2);
                        if (c2 <= 31 || c2 >= 127) {
                            throw new IllegalArgumentException(String.format("Unexpected char %#04x at %d in header value: %s", new Object[]{Integer.valueOf(c2), Integer.valueOf(i2), value}));
                        }
                    }
                    return;
                }
                throw new IllegalArgumentException("value == null");
            } else {
                throw new IllegalArgumentException("name is empty");
            }
        }

        public p e() {
            return new p(this);
        }
    }
}
