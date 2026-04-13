package io.ktor.routing;

import java.nio.charset.Charset;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.sequences.o;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: RoutingPath.kt */
public final class s {
    /* access modifiers changed from: private */
    @NotNull
    public static final s a = new s(q.g());
    public static final a b = new a((DefaultConstructorMarker) null);
    @NotNull
    private final List<t> c;

    /* compiled from: RoutingPath.kt */
    public static final class a {

        /* renamed from: io.ktor.routing.s$a$a  reason: collision with other inner class name */
        /* compiled from: RoutingPath.kt */
        public static final class C0261a extends l implements kotlin.jvm.functions.l<String, Boolean> {
            public static final C0261a INSTANCE = new C0261a();

            C0261a() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Boolean.valueOf(invoke((String) obj));
            }

            public final boolean invoke(@NotNull String it) {
                k.f(it, "it");
                return it.length() > 0;
            }
        }

        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final s a() {
            return s.a;
        }

        @NotNull
        public final s b(@NotNull String path) {
            k.f(path, "path");
            if (k.a(path, "/")) {
                return a();
            }
            return new s(o.C(o.w(o.o(x.H0(path, new String[]{"/"}, false, 0, 6, (Object) null), C0261a.INSTANCE), b.INSTANCE)), (DefaultConstructorMarker) null);
        }

        /* compiled from: RoutingPath.kt */
        public static final class b extends l implements kotlin.jvm.functions.l<String, t> {
            public static final b INSTANCE = new b();

            b() {
                super(1);
            }

            @NotNull
            public final t invoke(@NotNull String segment) {
                k.f(segment, "segment");
                if (!x.R(segment, '{', false, 2, (Object) null) || !x.R(segment, '}', false, 2, (Object) null)) {
                    return new t(io.ktor.http.a.e(segment, 0, 0, (Charset) null, 7, (Object) null), u.Constant);
                }
                return new t(segment, u.Parameter);
            }
        }
    }

    private s(List<t> parts) {
        this.c = parts;
    }

    public /* synthetic */ s(List parts, DefaultConstructorMarker $constructor_marker) {
        this(parts);
    }

    @NotNull
    public final List<t> b() {
        return this.c;
    }

    /* compiled from: RoutingPath.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<t, String> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        @NotNull
        public final String invoke(@NotNull t it) {
            k.f(it, "it");
            return it.d();
        }
    }

    @NotNull
    public String toString() {
        return y.b0(this.c, "/", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, b.INSTANCE, 30, (Object) null);
    }
}
