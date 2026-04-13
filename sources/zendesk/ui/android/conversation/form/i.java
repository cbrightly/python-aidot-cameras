package zendesk.ui.android.conversation.form;

import com.leedarson.bean.Constants;
import java.util.List;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.conversation.form.m;

/* compiled from: FieldRendering.kt */
public abstract class i<T> {
    @NotNull
    private final m a;
    private final T b;

    public /* synthetic */ i(m mVar, Object obj, DefaultConstructorMarker defaultConstructorMarker) {
        this(mVar, obj);
    }

    private i(m state, T normalizedState) {
        this.a = state;
        this.b = normalizedState;
    }

    @NotNull
    public m b() {
        return this.a;
    }

    public T a() {
        return this.b;
    }

    /* compiled from: FieldRendering.kt */
    public static final class c<T> extends i<T> {
        @NotNull
        private final m.c c;
        @NotNull
        private final l<m.c, x> d;
        @NotNull
        private final l<String, x> e;
        @NotNull
        private final l<m.c, T> f;
        @NotNull
        private final l<Boolean, x> g;

        public static /* synthetic */ c d(c cVar, m.c cVar2, l<m.c, x> lVar, l<String, x> lVar2, l<m.c, T> lVar3, l<Boolean, x> lVar4, int i, Object obj) {
            if ((i & 1) != 0) {
                cVar2 = cVar.b();
            }
            if ((i & 2) != 0) {
                lVar = cVar.d;
            }
            l<m.c, x> lVar5 = lVar;
            if ((i & 4) != 0) {
                lVar2 = cVar.e;
            }
            l<String, x> lVar6 = lVar2;
            if ((i & 8) != 0) {
                lVar3 = cVar.f;
            }
            l<m.c, T> lVar7 = lVar3;
            if ((i & 16) != 0) {
                lVar4 = cVar.g;
            }
            return cVar.c(cVar2, lVar5, lVar6, lVar7, lVar4);
        }

        @NotNull
        public final c<T> c(@NotNull m.c cVar, @NotNull l<? super m.c, x> lVar, @NotNull l<? super String, x> lVar2, @NotNull l<? super m.c, ? extends T> lVar3, @NotNull l<? super Boolean, x> lVar4) {
            k.e(cVar, Constants.ACTION_STATE);
            k.e(lVar, "onStateChanged");
            k.e(lVar2, "onTextChanged");
            k.e(lVar3, "normalize");
            k.e(lVar4, "onFieldFocusChanged");
            return new c(cVar, lVar, lVar2, lVar3, lVar4);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof c)) {
                return false;
            }
            c cVar = (c) obj;
            return k.a(b(), cVar.b()) && k.a(this.d, cVar.d) && k.a(this.e, cVar.e) && k.a(this.f, cVar.f) && k.a(this.g, cVar.g);
        }

        public int hashCode() {
            return (((((((b().hashCode() * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31) + this.f.hashCode()) * 31) + this.g.hashCode();
        }

        @NotNull
        public String toString() {
            return "Text(state=" + b() + ", onStateChanged=" + this.d + ", onTextChanged=" + this.e + ", normalize=" + this.f + ", onFieldFocusChanged=" + this.g + ')';
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public /* synthetic */ c(zendesk.ui.android.conversation.form.m.c r17, kotlin.jvm.functions.l r18, kotlin.jvm.functions.l r19, kotlin.jvm.functions.l r20, kotlin.jvm.functions.l r21, int r22, kotlin.jvm.internal.DefaultConstructorMarker r23) {
            /*
                r16 = this;
                r0 = r22 & 1
                if (r0 == 0) goto L_0x0015
                zendesk.ui.android.conversation.form.m$c r0 = new zendesk.ui.android.conversation.form.m$c
                r2 = 0
                r3 = 0
                r4 = 0
                r5 = 0
                r6 = 0
                r7 = 0
                r8 = 63
                r9 = 0
                r1 = r0
                r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
                r11 = r0
                goto L_0x0017
            L_0x0015:
                r11 = r17
            L_0x0017:
                r0 = r22 & 2
                if (r0 == 0) goto L_0x001f
                zendesk.ui.android.conversation.form.i$c$a r0 = zendesk.ui.android.conversation.form.i.c.a.INSTANCE
                r12 = r0
                goto L_0x0021
            L_0x001f:
                r12 = r18
            L_0x0021:
                r0 = r22 & 4
                if (r0 == 0) goto L_0x0029
                zendesk.ui.android.conversation.form.i$c$b r0 = zendesk.ui.android.conversation.form.i.c.b.INSTANCE
                r13 = r0
                goto L_0x002b
            L_0x0029:
                r13 = r19
            L_0x002b:
                r0 = r22 & 16
                if (r0 == 0) goto L_0x0033
                zendesk.ui.android.conversation.form.i$c$c r0 = zendesk.ui.android.conversation.form.i.c.C0565c.INSTANCE
                r15 = r0
                goto L_0x0035
            L_0x0033:
                r15 = r21
            L_0x0035:
                r10 = r16
                r14 = r20
                r10.<init>(r11, r12, r13, r14, r15)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: zendesk.ui.android.conversation.form.i.c.<init>(zendesk.ui.android.conversation.form.m$c, kotlin.jvm.functions.l, kotlin.jvm.functions.l, kotlin.jvm.functions.l, kotlin.jvm.functions.l, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        /* compiled from: FieldRendering.kt */
        public static final class a extends kotlin.jvm.internal.l implements l<m.c, x> {
            public static final a INSTANCE = new a();

            a() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((m.c) p1);
                return x.a;
            }

            public final void invoke(@NotNull m.c it) {
                k.e(it, "it");
            }
        }

        @NotNull
        /* renamed from: i */
        public m.c b() {
            return this.c;
        }

        /* compiled from: FieldRendering.kt */
        public static final class b extends kotlin.jvm.internal.l implements l<String, x> {
            public static final b INSTANCE = new b();

            b() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((String) p1);
                return x.a;
            }

            public final void invoke(@NotNull String it) {
                k.e(it, "it");
            }
        }

        @NotNull
        public final l<m.c, x> g() {
            return this.d;
        }

        @NotNull
        public final l<String, x> h() {
            return this.e;
        }

        /* renamed from: zendesk.ui.android.conversation.form.i$c$c  reason: collision with other inner class name */
        /* compiled from: FieldRendering.kt */
        public static final class C0565c extends kotlin.jvm.internal.l implements l<Boolean, x> {
            public static final C0565c INSTANCE = new C0565c();

            C0565c() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke(((Boolean) p1).booleanValue());
                return x.a;
            }

            public final void invoke(boolean it) {
            }
        }

        @NotNull
        public final l<m.c, T> e() {
            return this.f;
        }

        @NotNull
        public final l<Boolean, x> f() {
            return this.g;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(@NotNull m.c state, @NotNull l<? super m.c, x> onStateChanged, @NotNull l<? super String, x> onTextChanged, @NotNull l<? super m.c, ? extends T> normalize, @NotNull l<? super Boolean, x> onFieldFocusChanged) {
            super(state, normalize.invoke(state), (DefaultConstructorMarker) null);
            k.e(state, Constants.ACTION_STATE);
            k.e(onStateChanged, "onStateChanged");
            k.e(onTextChanged, "onTextChanged");
            k.e(normalize, "normalize");
            k.e(onFieldFocusChanged, "onFieldFocusChanged");
            this.c = state;
            this.d = onStateChanged;
            this.e = onTextChanged;
            this.f = normalize;
            this.g = onFieldFocusChanged;
        }

        /* compiled from: FieldRendering.kt */
        public static final class d<T> {
            @NotNull
            private c<T> a;

            public d(@NotNull l<? super m.c, ? extends T> normalize) {
                k.e(normalize, "normalize");
                this.a = new c((m.c) null, (l) null, (l) null, normalize, (l) null, 23, (DefaultConstructorMarker) null);
            }

            @NotNull
            public final d<T> b(@NotNull l<? super m.c, m.c> stateUpdate) {
                k.e(stateUpdate, "stateUpdate");
                c<T> cVar = this.a;
                this.a = c.d(cVar, stateUpdate.invoke(cVar.b()), (l) null, (l) null, (l) null, (l) null, 30, (Object) null);
                return this;
            }

            @NotNull
            public final c<T> a() {
                return this.a;
            }
        }
    }

    /* compiled from: FieldRendering.kt */
    public static final class a<T> extends i<T> {
        @NotNull
        private final m.a c;
        @NotNull
        private final l<m.a, x> d;
        @NotNull
        private final l<String, x> e;
        @NotNull
        private final l<m.a, T> f;
        @NotNull
        private final l<Boolean, x> g;

        public static /* synthetic */ a d(a aVar, m.a aVar2, l<m.a, x> lVar, l<String, x> lVar2, l<m.a, T> lVar3, l<Boolean, x> lVar4, int i, Object obj) {
            if ((i & 1) != 0) {
                aVar2 = aVar.b();
            }
            if ((i & 2) != 0) {
                lVar = aVar.d;
            }
            l<m.a, x> lVar5 = lVar;
            if ((i & 4) != 0) {
                lVar2 = aVar.e;
            }
            l<String, x> lVar6 = lVar2;
            if ((i & 8) != 0) {
                lVar3 = aVar.f;
            }
            l<m.a, T> lVar7 = lVar3;
            if ((i & 16) != 0) {
                lVar4 = aVar.g;
            }
            return aVar.c(aVar2, lVar5, lVar6, lVar7, lVar4);
        }

        @NotNull
        public final a<T> c(@NotNull m.a aVar, @NotNull l<? super m.a, x> lVar, @NotNull l<? super String, x> lVar2, @NotNull l<? super m.a, ? extends T> lVar3, @NotNull l<? super Boolean, x> lVar4) {
            k.e(aVar, Constants.ACTION_STATE);
            k.e(lVar, "onStateChanged");
            k.e(lVar2, "onEmailChanged");
            k.e(lVar3, "normalize");
            k.e(lVar4, "onFieldFocusChanged");
            return new a(aVar, lVar, lVar2, lVar3, lVar4);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return k.a(b(), aVar.b()) && k.a(this.d, aVar.d) && k.a(this.e, aVar.e) && k.a(this.f, aVar.f) && k.a(this.g, aVar.g);
        }

        public int hashCode() {
            return (((((((b().hashCode() * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31) + this.f.hashCode()) * 31) + this.g.hashCode();
        }

        @NotNull
        public String toString() {
            return "Email(state=" + b() + ", onStateChanged=" + this.d + ", onEmailChanged=" + this.e + ", normalize=" + this.f + ", onFieldFocusChanged=" + this.g + ')';
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public /* synthetic */ a(zendesk.ui.android.conversation.form.m.a r15, kotlin.jvm.functions.l r16, kotlin.jvm.functions.l r17, kotlin.jvm.functions.l r18, kotlin.jvm.functions.l r19, int r20, kotlin.jvm.internal.DefaultConstructorMarker r21) {
            /*
                r14 = this;
                r0 = r20 & 1
                if (r0 == 0) goto L_0x0013
                zendesk.ui.android.conversation.form.m$a r0 = new zendesk.ui.android.conversation.form.m$a
                r2 = 0
                r3 = 0
                r4 = 0
                r5 = 0
                r6 = 15
                r7 = 0
                r1 = r0
                r1.<init>(r2, r3, r4, r5, r6, r7)
                r9 = r0
                goto L_0x0014
            L_0x0013:
                r9 = r15
            L_0x0014:
                r0 = r20 & 2
                if (r0 == 0) goto L_0x001c
                zendesk.ui.android.conversation.form.i$a$a r0 = zendesk.ui.android.conversation.form.i.a.C0563a.INSTANCE
                r10 = r0
                goto L_0x001e
            L_0x001c:
                r10 = r16
            L_0x001e:
                r0 = r20 & 4
                if (r0 == 0) goto L_0x0026
                zendesk.ui.android.conversation.form.i$a$b r0 = zendesk.ui.android.conversation.form.i.a.b.INSTANCE
                r11 = r0
                goto L_0x0028
            L_0x0026:
                r11 = r17
            L_0x0028:
                r0 = r20 & 16
                if (r0 == 0) goto L_0x0030
                zendesk.ui.android.conversation.form.i$a$c r0 = zendesk.ui.android.conversation.form.i.a.c.INSTANCE
                r13 = r0
                goto L_0x0032
            L_0x0030:
                r13 = r19
            L_0x0032:
                r8 = r14
                r12 = r18
                r8.<init>(r9, r10, r11, r12, r13)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: zendesk.ui.android.conversation.form.i.a.<init>(zendesk.ui.android.conversation.form.m$a, kotlin.jvm.functions.l, kotlin.jvm.functions.l, kotlin.jvm.functions.l, kotlin.jvm.functions.l, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        /* renamed from: zendesk.ui.android.conversation.form.i$a$a  reason: collision with other inner class name */
        /* compiled from: FieldRendering.kt */
        public static final class C0563a extends kotlin.jvm.internal.l implements l<m.a, x> {
            public static final C0563a INSTANCE = new C0563a();

            C0563a() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((m.a) p1);
                return x.a;
            }

            public final void invoke(@NotNull m.a it) {
                k.e(it, "it");
            }
        }

        @NotNull
        /* renamed from: i */
        public m.a b() {
            return this.c;
        }

        /* compiled from: FieldRendering.kt */
        public static final class b extends kotlin.jvm.internal.l implements l<String, x> {
            public static final b INSTANCE = new b();

            b() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((String) p1);
                return x.a;
            }

            public final void invoke(@NotNull String it) {
                k.e(it, "it");
            }
        }

        @NotNull
        public final l<m.a, x> h() {
            return this.d;
        }

        @NotNull
        public final l<String, x> f() {
            return this.e;
        }

        /* compiled from: FieldRendering.kt */
        public static final class c extends kotlin.jvm.internal.l implements l<Boolean, x> {
            public static final c INSTANCE = new c();

            c() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke(((Boolean) p1).booleanValue());
                return x.a;
            }

            public final void invoke(boolean it) {
            }
        }

        @NotNull
        public final l<m.a, T> e() {
            return this.f;
        }

        @NotNull
        public final l<Boolean, x> g() {
            return this.g;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull m.a state, @NotNull l<? super m.a, x> onStateChanged, @NotNull l<? super String, x> onEmailChanged, @NotNull l<? super m.a, ? extends T> normalize, @NotNull l<? super Boolean, x> onFieldFocusChanged) {
            super(state, normalize.invoke(state), (DefaultConstructorMarker) null);
            k.e(state, Constants.ACTION_STATE);
            k.e(onStateChanged, "onStateChanged");
            k.e(onEmailChanged, "onEmailChanged");
            k.e(normalize, "normalize");
            k.e(onFieldFocusChanged, "onFieldFocusChanged");
            this.c = state;
            this.d = onStateChanged;
            this.e = onEmailChanged;
            this.f = normalize;
            this.g = onFieldFocusChanged;
        }

        /* compiled from: FieldRendering.kt */
        public static final class d<T> {
            @NotNull
            private a<T> a;

            public d(@NotNull l<? super m.a, ? extends T> normalize) {
                k.e(normalize, "normalize");
                this.a = new a((m.a) null, (l) null, (l) null, normalize, (l) null, 23, (DefaultConstructorMarker) null);
            }

            @NotNull
            public final d<T> b(@NotNull l<? super m.a, m.a> stateUpdate) {
                k.e(stateUpdate, "stateUpdate");
                a<T> aVar = this.a;
                this.a = a.d(aVar, stateUpdate.invoke(aVar.b()), (l) null, (l) null, (l) null, (l) null, 30, (Object) null);
                return this;
            }

            @NotNull
            public final a<T> a() {
                return this.a;
            }
        }
    }

    /* compiled from: FieldRendering.kt */
    public static final class b<T> extends i<T> {
        @NotNull
        private final m.b c;
        @NotNull
        private final l<m.b, x> d;
        @NotNull
        private final l<List<u>, x> e;
        @NotNull
        private final l<m.b, T> f;
        @NotNull
        private final l<Boolean, x> g;

        public static /* synthetic */ b d(b bVar, m.b bVar2, l<m.b, x> lVar, l<List<u>, x> lVar2, l<m.b, T> lVar3, l<Boolean, x> lVar4, int i, Object obj) {
            if ((i & 1) != 0) {
                bVar2 = bVar.b();
            }
            if ((i & 2) != 0) {
                lVar = bVar.d;
            }
            l<m.b, x> lVar5 = lVar;
            if ((i & 4) != 0) {
                lVar2 = bVar.e;
            }
            l<List<u>, x> lVar6 = lVar2;
            if ((i & 8) != 0) {
                lVar3 = bVar.f;
            }
            l<m.b, T> lVar7 = lVar3;
            if ((i & 16) != 0) {
                lVar4 = bVar.g;
            }
            return bVar.c(bVar2, lVar5, lVar6, lVar7, lVar4);
        }

        @NotNull
        public final b<T> c(@NotNull m.b bVar, @NotNull l<? super m.b, x> lVar, @NotNull l<? super List<u>, x> lVar2, @NotNull l<? super m.b, ? extends T> lVar3, @NotNull l<? super Boolean, x> lVar4) {
            k.e(bVar, Constants.ACTION_STATE);
            k.e(lVar, "onStateChanged");
            k.e(lVar2, "onSelected");
            k.e(lVar3, "normalize");
            k.e(lVar4, "onFieldFocusChanged");
            return new b(bVar, lVar, lVar2, lVar3, lVar4);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return k.a(b(), bVar.b()) && k.a(this.d, bVar.d) && k.a(this.e, bVar.e) && k.a(this.f, bVar.f) && k.a(this.g, bVar.g);
        }

        public int hashCode() {
            return (((((((b().hashCode() * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31) + this.f.hashCode()) * 31) + this.g.hashCode();
        }

        @NotNull
        public String toString() {
            return "Select(state=" + b() + ", onStateChanged=" + this.d + ", onSelected=" + this.e + ", normalize=" + this.f + ", onFieldFocusChanged=" + this.g + ')';
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public /* synthetic */ b(zendesk.ui.android.conversation.form.m.b r16, kotlin.jvm.functions.l r17, kotlin.jvm.functions.l r18, kotlin.jvm.functions.l r19, kotlin.jvm.functions.l r20, int r21, kotlin.jvm.internal.DefaultConstructorMarker r22) {
            /*
                r15 = this;
                r0 = r21 & 1
                if (r0 == 0) goto L_0x0014
                zendesk.ui.android.conversation.form.m$b r0 = new zendesk.ui.android.conversation.form.m$b
                r2 = 0
                r3 = 0
                r4 = 0
                r5 = 0
                r6 = 0
                r7 = 31
                r8 = 0
                r1 = r0
                r1.<init>(r2, r3, r4, r5, r6, r7, r8)
                r10 = r0
                goto L_0x0016
            L_0x0014:
                r10 = r16
            L_0x0016:
                r0 = r21 & 2
                if (r0 == 0) goto L_0x001e
                zendesk.ui.android.conversation.form.i$b$a r0 = zendesk.ui.android.conversation.form.i.b.a.INSTANCE
                r11 = r0
                goto L_0x0020
            L_0x001e:
                r11 = r17
            L_0x0020:
                r0 = r21 & 4
                if (r0 == 0) goto L_0x0028
                zendesk.ui.android.conversation.form.i$b$b r0 = zendesk.ui.android.conversation.form.i.b.C0564b.INSTANCE
                r12 = r0
                goto L_0x002a
            L_0x0028:
                r12 = r18
            L_0x002a:
                r0 = r21 & 16
                if (r0 == 0) goto L_0x0032
                zendesk.ui.android.conversation.form.i$b$c r0 = zendesk.ui.android.conversation.form.i.b.c.INSTANCE
                r14 = r0
                goto L_0x0034
            L_0x0032:
                r14 = r20
            L_0x0034:
                r9 = r15
                r13 = r19
                r9.<init>(r10, r11, r12, r13, r14)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: zendesk.ui.android.conversation.form.i.b.<init>(zendesk.ui.android.conversation.form.m$b, kotlin.jvm.functions.l, kotlin.jvm.functions.l, kotlin.jvm.functions.l, kotlin.jvm.functions.l, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        /* compiled from: FieldRendering.kt */
        public static final class a extends kotlin.jvm.internal.l implements l<m.b, x> {
            public static final a INSTANCE = new a();

            a() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((m.b) p1);
                return x.a;
            }

            public final void invoke(@NotNull m.b it) {
                k.e(it, "it");
            }
        }

        @NotNull
        /* renamed from: i */
        public m.b b() {
            return this.c;
        }

        /* renamed from: zendesk.ui.android.conversation.form.i$b$b  reason: collision with other inner class name */
        /* compiled from: FieldRendering.kt */
        public static final class C0564b extends kotlin.jvm.internal.l implements l<List<? extends u>, x> {
            public static final C0564b INSTANCE = new C0564b();

            C0564b() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((List<u>) (List) p1);
                return x.a;
            }

            public final void invoke(@NotNull List<u> it) {
                k.e(it, "it");
            }
        }

        @NotNull
        public final l<m.b, x> h() {
            return this.d;
        }

        @NotNull
        public final l<List<u>, x> g() {
            return this.e;
        }

        /* compiled from: FieldRendering.kt */
        public static final class c extends kotlin.jvm.internal.l implements l<Boolean, x> {
            public static final c INSTANCE = new c();

            c() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke(((Boolean) p1).booleanValue());
                return x.a;
            }

            public final void invoke(boolean it) {
            }
        }

        @NotNull
        public final l<m.b, T> e() {
            return this.f;
        }

        @NotNull
        public final l<Boolean, x> f() {
            return this.g;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull m.b state, @NotNull l<? super m.b, x> onStateChanged, @NotNull l<? super List<u>, x> onSelected, @NotNull l<? super m.b, ? extends T> normalize, @NotNull l<? super Boolean, x> onFieldFocusChanged) {
            super(state, normalize.invoke(state), (DefaultConstructorMarker) null);
            k.e(state, Constants.ACTION_STATE);
            k.e(onStateChanged, "onStateChanged");
            k.e(onSelected, "onSelected");
            k.e(normalize, "normalize");
            k.e(onFieldFocusChanged, "onFieldFocusChanged");
            this.c = state;
            this.d = onStateChanged;
            this.e = onSelected;
            this.f = normalize;
            this.g = onFieldFocusChanged;
        }

        /* compiled from: FieldRendering.kt */
        public static final class d<T> {
            @NotNull
            private b<T> a;

            public d(@NotNull l<? super m.b, ? extends T> normalize) {
                k.e(normalize, "normalize");
                this.a = new b((m.b) null, (l) null, (l) null, normalize, (l) null, 23, (DefaultConstructorMarker) null);
            }

            @NotNull
            public final d<T> b(@NotNull l<? super m.b, m.b> stateUpdate) {
                k.e(stateUpdate, "stateUpdate");
                b<T> bVar = this.a;
                this.a = b.d(bVar, stateUpdate.invoke(bVar.b()), (l) null, (l) null, (l) null, (l) null, 30, (Object) null);
                return this;
            }

            @NotNull
            public final b<T> a() {
                return this.a;
            }
        }
    }
}
