package okhttp3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import kotlin.TypeCastException;
import kotlin.collections.q;
import kotlin.collections.v;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.d0;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.ranges.g;
import kotlin.t;
import kotlin.text.w;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Headers.kt */
public final class u implements Iterable<n<? extends String, ? extends String>>, kotlin.jvm.internal.markers.a {
    public static final b c = new b((DefaultConstructorMarker) null);
    private final String[] d;

    @NotNull
    public static final u g(@NotNull String... strArr) {
        return c.g(strArr);
    }

    private u(String[] namesAndValues) {
        this.d = namesAndValues;
    }

    public /* synthetic */ u(String[] namesAndValues, DefaultConstructorMarker $constructor_marker) {
        this(namesAndValues);
    }

    @Nullable
    public final String a(@NotNull String name) {
        k.f(name, "name");
        return c.f(this.d, name);
    }

    public final int size() {
        return this.d.length / 2;
    }

    @NotNull
    public final String b(int index) {
        return this.d[index * 2];
    }

    @NotNull
    public final String h(int index) {
        return this.d[(index * 2) + 1];
    }

    @NotNull
    public final Set<String> e() {
        TreeSet result = new TreeSet(w.z(d0.a));
        int size = size();
        for (int i = 0; i < size; i++) {
            result.add(b(i));
        }
        Set<String> unmodifiableSet = Collections.unmodifiableSet(result);
        k.b(unmodifiableSet, "Collections.unmodifiableSet(result)");
        return unmodifiableSet;
    }

    @NotNull
    public final List<String> i(@NotNull String name) {
        k.f(name, "name");
        List result = null;
        int size = size();
        for (int i = 0; i < size; i++) {
            if (w.y(name, b(i), true)) {
                if (result == null) {
                    result = new ArrayList(2);
                }
                result.add(h(i));
            }
        }
        if (result == null) {
            return q.g();
        }
        List<String> unmodifiableList = Collections.unmodifiableList(result);
        k.b(unmodifiableList, "Collections.unmodifiableList(result)");
        return unmodifiableList;
    }

    @NotNull
    public Iterator<n<String, String>> iterator() {
        int size = size();
        n[] nVarArr = new n[size];
        for (int i = 0; i < size; i++) {
            int it = i;
            nVarArr[i] = t.a(b(it), h(it));
        }
        return kotlin.jvm.internal.b.a(nVarArr);
    }

    @NotNull
    public final a f() {
        a result = new a();
        v.y(result.h(), this.d);
        return result;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof u) && Arrays.equals(this.d, ((u) other).d);
    }

    public int hashCode() {
        return Arrays.hashCode(this.d);
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        int size = size();
        for (int i = 0; i < size; i++) {
            $this$buildString.append(b(i));
            $this$buildString.append(": ");
            $this$buildString.append(h(i));
            $this$buildString.append("\n");
        }
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    /* compiled from: Headers.kt */
    public static final class a {
        @NotNull
        private final List<String> a = new ArrayList(20);

        @NotNull
        public final List<String> h() {
            return this.a;
        }

        @NotNull
        public final a c(@NotNull String line) {
            k.f(line, "line");
            int index = x.e0(line, ':', 1, false, 4, (Object) null);
            if (index != -1) {
                String substring = line.substring(0, index);
                k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                String substring2 = line.substring(index + 1);
                k.b(substring2, "(this as java.lang.String).substring(startIndex)");
                d(substring, substring2);
            } else if (line.charAt(0) == ':') {
                String substring3 = line.substring(1);
                k.b(substring3, "(this as java.lang.String).substring(startIndex)");
                d("", substring3);
            } else {
                d("", line);
            }
            return this;
        }

        @NotNull
        public final a a(@NotNull String name, @NotNull String value) {
            k.f(name, "name");
            k.f(value, "value");
            b bVar = u.c;
            bVar.d(name);
            bVar.e(value, name);
            d(name, value);
            return this;
        }

        @NotNull
        public final a e(@NotNull String name, @NotNull String value) {
            k.f(name, "name");
            k.f(value, "value");
            u.c.d(name);
            d(name, value);
            return this;
        }

        @NotNull
        public final a b(@NotNull u headers) {
            k.f(headers, "headers");
            int size = headers.size();
            for (int i = 0; i < size; i++) {
                d(headers.b(i), headers.h(i));
            }
            return this;
        }

        @NotNull
        public final a d(@NotNull String name, @NotNull String value) {
            k.f(name, "name");
            k.f(value, "value");
            this.a.add(name);
            this.a.add(x.e1(value).toString());
            return this;
        }

        @NotNull
        public final a i(@NotNull String name) {
            k.f(name, "name");
            int i = 0;
            while (i < this.a.size()) {
                if (w.y(name, this.a.get(i), true)) {
                    this.a.remove(i);
                    this.a.remove(i);
                    i -= 2;
                }
                i += 2;
            }
            return this;
        }

        @NotNull
        public final a j(@NotNull String name, @NotNull String value) {
            k.f(name, "name");
            k.f(value, "value");
            b bVar = u.c;
            bVar.d(name);
            bVar.e(value, name);
            i(name);
            d(name, value);
            return this;
        }

        @Nullable
        public final String g(@NotNull String name) {
            k.f(name, "name");
            g k = kotlin.ranges.n.k(kotlin.ranges.n.j(this.a.size() - 2, 0), 2);
            int i = k.a();
            int b = k.b();
            int e = k.e();
            if (e >= 0) {
                if (i > b) {
                    return null;
                }
            } else if (i < b) {
                return null;
            }
            while (!w.y(name, this.a.get(i), true)) {
                if (i == b) {
                    return null;
                }
                i += e;
            }
            return this.a.get(i + 1);
        }

        @NotNull
        public final u f() {
            Object[] array = this.a.toArray(new String[0]);
            if (array != null) {
                return new u((String[]) array, (DefaultConstructorMarker) null);
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
    }

    /* compiled from: Headers.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* access modifiers changed from: private */
        public final String f(String[] namesAndValues, String name) {
            g k = kotlin.ranges.n.k(kotlin.ranges.n.j(namesAndValues.length - 2, 0), 2);
            int i = k.a();
            int b = k.b();
            int e = k.e();
            if (e >= 0) {
                if (i > b) {
                    return null;
                }
            } else if (i < b) {
                return null;
            }
            while (!w.y(name, namesAndValues[i], true)) {
                if (i == b) {
                    return null;
                }
                i += e;
            }
            return namesAndValues[i + 1];
        }

        @NotNull
        public final u g(@NotNull String... namesAndValues) {
            k.f(namesAndValues, "namesAndValues");
            if (namesAndValues.length % 2 == 0) {
                Object clone = namesAndValues.clone();
                if (clone != null) {
                    String[] namesAndValues2 = (String[]) clone;
                    int length = namesAndValues2.length;
                    int i = 0;
                    while (i < length) {
                        if (namesAndValues2[i] != null) {
                            String str = namesAndValues2[i];
                            if (str != null) {
                                namesAndValues2[i] = x.e1(str).toString();
                                i++;
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                            }
                        } else {
                            throw new IllegalArgumentException("Headers cannot be null".toString());
                        }
                    }
                    g k = kotlin.ranges.n.k(kotlin.ranges.n.l(0, namesAndValues2.length), 2);
                    int i2 = k.a();
                    int b = k.b();
                    int e = k.e();
                    if (e < 0 ? i2 >= b : i2 <= b) {
                        while (true) {
                            String name = namesAndValues2[i2];
                            String value = namesAndValues2[i2 + 1];
                            d(name);
                            e(value, name);
                            if (i2 == b) {
                                break;
                            }
                            i2 += e;
                        }
                    }
                    return new u(namesAndValues2, (DefaultConstructorMarker) null);
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
            }
            throw new IllegalArgumentException("Expected alternating header names and values".toString());
        }

        /* access modifiers changed from: private */
        public final void d(String name) {
            if (name.length() > 0) {
                int length = name.length();
                int i = 0;
                while (i < length) {
                    char c = name.charAt(i);
                    if ('!' <= c && '~' >= c) {
                        i++;
                    } else {
                        throw new IllegalArgumentException(okhttp3.internal.b.q("Unexpected char %#04x at %d in header name: %s", Integer.valueOf(c), Integer.valueOf(i), name).toString());
                    }
                }
                return;
            }
            throw new IllegalArgumentException("name is empty".toString());
        }

        /* access modifiers changed from: private */
        public final void e(String value, String name) {
            int length = value.length();
            int i = 0;
            while (i < length) {
                char c = value.charAt(i);
                if (c == 9 || (' ' <= c && '~' >= c)) {
                    i++;
                } else {
                    throw new IllegalArgumentException(okhttp3.internal.b.q("Unexpected char %#04x at %d in %s value: %s", Integer.valueOf(c), Integer.valueOf(i), name, value).toString());
                }
            }
        }
    }
}
