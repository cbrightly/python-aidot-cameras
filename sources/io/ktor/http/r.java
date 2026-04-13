package io.ktor.http;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.l;
import kotlin.k;
import kotlin.n;
import kotlin.t;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HttpHeaderValueParser.kt */
public final class r {
    @NotNull
    public static final List<k> b(@Nullable String header) {
        return y.u0(c(header), new a());
    }

    @NotNull
    public static final List<k> c(@Nullable String text) {
        return d(text, false);
    }

    @NotNull
    public static final List<k> d(@Nullable String text, boolean parametersOnly) {
        if (text == null) {
            return q.g();
        }
        int pos = 0;
        g items = i.a(k.NONE, b.INSTANCE);
        while (pos <= x.Z(text)) {
            pos = e(text, pos, items, parametersOnly);
        }
        return j(items);
    }

    /* compiled from: HttpHeaderValueParser.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<ArrayList<k>> {
        public static final b INSTANCE = new b();

        b() {
            super(0);
        }

        @NotNull
        public final ArrayList<k> invoke() {
            return new ArrayList<>();
        }
    }

    private static final <T> List<T> j(@NotNull g<? extends List<? extends T>> $this$valueOrEmpty) {
        return $this$valueOrEmpty.isInitialized() ? (List) $this$valueOrEmpty.getValue() : q.g();
    }

    /* access modifiers changed from: private */
    public static final String i(@NotNull String $this$subtrim, int start, int end) {
        if ($this$subtrim != null) {
            String substring = $this$subtrim.substring(start, end);
            kotlin.jvm.internal.k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            if (substring != null) {
                return x.e1(substring).toString();
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    /* compiled from: HttpHeaderValueParser.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<ArrayList<l>> {
        public static final c INSTANCE = new c();

        c() {
            super(0);
        }

        @NotNull
        public final ArrayList<l> invoke() {
            return new ArrayList<>();
        }
    }

    private static final int e(String text, int start, g<? extends ArrayList<k>> items, boolean parametersOnly) {
        int i;
        int pos = start;
        g parameters = i.a(k.NONE, c.INSTANCE);
        Integer valueEnd = parametersOnly ? Integer.valueOf(pos) : null;
        while (pos <= x.Z(text)) {
            switch (text.charAt(pos)) {
                case ',':
                    ((ArrayList) items.getValue()).add(new k(i(text, start, valueEnd != null ? valueEnd.intValue() : pos), j(parameters)));
                    return pos + 1;
                case ';':
                    if (valueEnd == null) {
                        valueEnd = Integer.valueOf(pos);
                    }
                    pos = f(text, pos + 1, parameters);
                    break;
                default:
                    if (parametersOnly) {
                        i = f(text, pos, parameters);
                    } else {
                        i = pos + 1;
                    }
                    pos = i;
                    break;
            }
        }
        ((ArrayList) items.getValue()).add(new k(i(text, start, valueEnd != null ? valueEnd.intValue() : pos), j(parameters)));
        return pos;
    }

    /* compiled from: Comparisons.kt */
    public static final class a<T> implements Comparator<T> {
        public final int compare(T a, T b) {
            return kotlin.comparisons.a.c(Double.valueOf(((k) b).c()), Double.valueOf(((k) a).c()));
        }
    }

    /* compiled from: HttpHeaderValueParser.kt */
    public static final class d extends l implements kotlin.jvm.functions.r<String, Integer, Integer, String, kotlin.x> {
        final /* synthetic */ g $parameters;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(g gVar) {
            super(4);
            this.$parameters = gVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            invoke((String) obj, ((Number) obj2).intValue(), ((Number) obj3).intValue(), (String) obj4);
            return kotlin.x.a;
        }

        public final void invoke(@NotNull String text, int start, int end, @NotNull String value) {
            kotlin.jvm.internal.k.f(text, "text");
            kotlin.jvm.internal.k.f(value, "value");
            String name = r.i(text, start, end);
            if (!(name.length() == 0)) {
                ((ArrayList) this.$parameters.getValue()).add(new l(name, value));
            }
        }
    }

    private static final int f(String text, int start, g<? extends ArrayList<l>> parameters) {
        d $fun$addParam$1 = new d(parameters);
        int pos = start;
        while (pos <= x.Z(text)) {
            switch (text.charAt(pos)) {
                case ',':
                case ';':
                    $fun$addParam$1.invoke(text, start, pos, "");
                    return pos;
                case '=':
                    n<Integer, String> g = g(text, pos + 1);
                    int paramEnd = g.component1().intValue();
                    $fun$addParam$1.invoke(text, start, pos, g.component2());
                    return paramEnd;
                default:
                    pos++;
            }
        }
        $fun$addParam$1.invoke(text, start, pos, "");
        return pos;
    }

    private static final n<Integer, String> g(String value, int start) {
        int pos = start;
        while (pos <= x.Z(value)) {
            switch (value.charAt(pos)) {
                case '\"':
                    return h(value, pos + 1);
                case ',':
                case ';':
                    return t.a(Integer.valueOf(pos), i(value, start, pos));
                default:
                    pos++;
            }
        }
        return t.a(Integer.valueOf(pos), i(value, start, pos));
    }

    private static final n<Integer, String> h(String value, int start) {
        int pos = start;
        StringBuilder sb = new StringBuilder();
        while (pos <= x.Z(value)) {
            char c2 = value.charAt(pos);
            switch (c2) {
                case '\"':
                    return t.a(Integer.valueOf(pos + 1), sb.toString());
                case '\\':
                    if (pos >= x.Z(value) - 2) {
                        sb.append(c2);
                        pos++;
                        break;
                    } else {
                        sb.append(value.charAt(pos + 1));
                        pos += 2;
                        break;
                    }
                default:
                    sb.append(c2);
                    pos++;
                    break;
            }
        }
        return t.a(Integer.valueOf(pos), sb.toString());
    }
}
