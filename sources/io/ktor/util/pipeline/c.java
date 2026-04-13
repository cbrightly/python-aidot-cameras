package io.ktor.util.pipeline;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.ktor.util.d;
import java.util.ArrayList;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.y;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Pipeline.kt */
public class c<TSubject, TContext> {
    @NotNull
    private final io.ktor.util.b c = d.a(true);
    private final ArrayList<Object> d;
    private int f;
    private volatile List<? extends q<? super d<TSubject, TContext>, ? super TSubject, ? super kotlin.coroutines.d<? super x>, ? extends Object>> interceptors;
    private boolean q;
    private g x;

    public c(@NotNull g... phases) {
        k.f(phases, "phases");
        ArrayList<Object> arrayList = new ArrayList<>(phases.length + 1);
        for (g it : phases) {
            arrayList.add(it);
        }
        this.d = arrayList;
    }

    @NotNull
    public final io.ktor.util.b i() {
        return this.c;
    }

    @Nullable
    public final Object e(@NotNull TContext context, @NotNull TSubject subject, @NotNull kotlin.coroutines.d<? super TSubject> $completion) {
        return d(context, subject).a(subject, $completion);
    }

    private final f<TSubject> d(TContext context, TSubject subject) {
        return e.a(context, w(), subject);
    }

    /* compiled from: Pipeline.kt */
    public static final class a<TSubject, Call> {
        @NotNull
        private static final ArrayList<Object> a = new ArrayList<>(0);
        public static final C0276a b = new C0276a((DefaultConstructorMarker) null);
        private boolean c;
        @NotNull
        private final g d;
        @NotNull
        private final b e;
        private ArrayList<q<d<TSubject, Call>, TSubject, kotlin.coroutines.d<? super x>, Object>> f;

        public a(@NotNull g phase, @NotNull b relation, @NotNull ArrayList<q<d<TSubject, Call>, TSubject, kotlin.coroutines.d<? super x>, Object>> interceptors) {
            k.f(phase, TypedValues.CycleType.S_WAVE_PHASE);
            k.f(relation, "relation");
            k.f(interceptors, "interceptors");
            this.d = phase;
            this.e = relation;
            this.f = interceptors;
            this.c = true;
        }

        @NotNull
        public final g f() {
            return this.d;
        }

        @NotNull
        public final b g() {
            return this.e;
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public a(@org.jetbrains.annotations.NotNull io.ktor.util.pipeline.g r3, @org.jetbrains.annotations.NotNull io.ktor.util.pipeline.c.b r4) {
            /*
                r2 = this;
                java.lang.String r0 = "phase"
                kotlin.jvm.internal.k.f(r3, r0)
                java.lang.String r0 = "relation"
                kotlin.jvm.internal.k.f(r4, r0)
                java.util.ArrayList<java.lang.Object> r0 = a
                if (r0 == 0) goto L_0x0028
                r2.<init>(r3, r4, r0)
                boolean r0 = r0.isEmpty()
                if (r0 == 0) goto L_0x001b
                return
            L_0x001b:
                r0 = 0
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "The shared empty array list has been modified"
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                throw r0
            L_0x0028:
                kotlin.TypeCastException r0 = new kotlin.TypeCastException
                java.lang.String r1 = "null cannot be cast to non-null type kotlin.collections.ArrayList<io.ktor.util.pipeline.PipelineInterceptor<TSubject, Call> /* = suspend io.ktor.util.pipeline.PipelineContext<TSubject, Call>.(TSubject) -> kotlin.Unit */> /* = java.util.ArrayList<suspend io.ktor.util.pipeline.PipelineContext<TSubject, Call>.(TSubject) -> kotlin.Unit> */"
                r0.<init>(r1)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.util.pipeline.c.a.<init>(io.ktor.util.pipeline.g, io.ktor.util.pipeline.c$b):void");
        }

        public final boolean i() {
            return this.f.isEmpty();
        }

        public final int h() {
            return this.f.size();
        }

        public final void a(@NotNull q<? super d<TSubject, Call>, ? super TSubject, ? super kotlin.coroutines.d<? super x>, ? extends Object> interceptor) {
            k.f(interceptor, "interceptor");
            if (this.c) {
                e();
            }
            this.f.add(interceptor);
        }

        public final void c(@NotNull ArrayList<q<d<TSubject, Call>, TSubject, kotlin.coroutines.d<? super x>, Object>> destination) {
            k.f(destination, FirebaseAnalytics.Param.DESTINATION);
            ArrayList interceptors = this.f;
            destination.ensureCapacity(destination.size() + interceptors.size());
            int size = interceptors.size();
            for (int index = 0; index < size; index++) {
                destination.add(interceptors.get(index));
            }
        }

        public final void b(@NotNull a<TSubject, Call> destination) {
            k.f(destination, FirebaseAnalytics.Param.DESTINATION);
            if (!i()) {
                if (destination.i()) {
                    destination.f = j();
                    destination.c = true;
                    return;
                }
                if (destination.c) {
                    destination.e();
                }
                c(destination.f);
            }
        }

        @NotNull
        public final ArrayList<q<d<TSubject, Call>, TSubject, kotlin.coroutines.d<? super x>, Object>> j() {
            this.c = true;
            return this.f;
        }

        @NotNull
        public final ArrayList<q<d<TSubject, Call>, TSubject, kotlin.coroutines.d<? super x>, Object>> d() {
            return new ArrayList<>(this.f);
        }

        @NotNull
        public String toString() {
            return "Phase `" + this.d.a() + "`, " + h() + " handlers";
        }

        private final void e() {
            this.f = d();
            this.c = false;
        }

        /* renamed from: io.ktor.util.pipeline.c$a$a  reason: collision with other inner class name */
        /* compiled from: Pipeline.kt */
        public static final class C0276a {
            private C0276a() {
            }

            public /* synthetic */ C0276a(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }
    }

    /* compiled from: Pipeline.kt */
    public static abstract class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* compiled from: Pipeline.kt */
        public static final class a extends b {
            @NotNull
            private final g a;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public a(@NotNull g relativeTo) {
                super((DefaultConstructorMarker) null);
                k.f(relativeTo, "relativeTo");
                this.a = relativeTo;
            }

            @NotNull
            public final g a() {
                return this.a;
            }
        }

        /* renamed from: io.ktor.util.pipeline.c$b$b  reason: collision with other inner class name */
        /* compiled from: Pipeline.kt */
        public static final class C0277b extends b {
            @NotNull
            private final g a;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public C0277b(@NotNull g relativeTo) {
                super((DefaultConstructorMarker) null);
                k.f(relativeTo, "relativeTo");
                this.a = relativeTo;
            }

            @NotNull
            public final g a() {
                return this.a;
            }
        }

        /* renamed from: io.ktor.util.pipeline.c$b$c  reason: collision with other inner class name */
        /* compiled from: Pipeline.kt */
        public static final class C0278c extends b {
            public static final C0278c a = new C0278c();

            private C0278c() {
                super((DefaultConstructorMarker) null);
            }
        }
    }

    private final a<TSubject, TContext> g(g phase) {
        ArrayList phasesList = this.d;
        int size = phasesList.size();
        int index = 0;
        while (index < size) {
            Object e = phasesList.get(index);
            k.b(e, "phasesList[index]");
            if (e == phase) {
                a content = new a(phase, b.C0278c.a);
                phasesList.set(index, content);
                return content;
            } else if ((e instanceof a) && ((a) e).f() == phase) {
                return (a) e;
            } else {
                index++;
            }
        }
        return null;
    }

    private final int h(g phase) {
        ArrayList phasesList = this.d;
        int size = phasesList.size();
        for (int index = 0; index < size; index++) {
            Object e = phasesList.get(index);
            k.b(e, "phasesList[index]");
            if (e == phase) {
                return index;
            }
            if ((e instanceof a) && ((a) e).f() == phase) {
                return index;
            }
        }
        return -1;
    }

    private final boolean k(g phase) {
        ArrayList phasesList = this.d;
        int size = phasesList.size();
        for (int index = 0; index < size; index++) {
            Object e = phasesList.get(index);
            k.b(e, "phasesList[index]");
            if (e == phase) {
                return true;
            }
            if ((e instanceof a) && ((a) e).f() == phase) {
                return true;
            }
        }
        return false;
    }

    public final void a(@NotNull g phase) {
        k.f(phase, TypedValues.CycleType.S_WAVE_PHASE);
        if (!k(phase)) {
            this.d.add(phase);
        }
    }

    public final void m(@NotNull g reference, @NotNull g phase) {
        k.f(reference, "reference");
        k.f(phase, TypedValues.CycleType.S_WAVE_PHASE);
        if (!k(phase)) {
            int index = h(reference);
            if (index != -1) {
                this.d.add(index + 1, new a(phase, new b.a(reference)));
                return;
            }
            throw new b("Phase " + reference + " was not registered for this pipeline");
        }
    }

    public final void n(@NotNull g reference, @NotNull g phase) {
        k.f(reference, "reference");
        k.f(phase, TypedValues.CycleType.S_WAVE_PHASE);
        if (!k(phase)) {
            int index = h(reference);
            if (index != -1) {
                this.d.add(index, new a(phase, new b.C0277b(reference)));
                return;
            }
            throw new b("Phase " + reference + " was not registered for this pipeline");
        }
    }

    public final boolean q() {
        return this.f == 0;
    }

    private final List<q<d<TSubject, TContext>, TSubject, kotlin.coroutines.d<? super x>, Object>> c() {
        int i;
        int interceptorsQuantity = this.f;
        if (interceptorsQuantity == 0) {
            s(kotlin.collections.q.g());
            return kotlin.collections.q.g();
        }
        ArrayList phases = this.d;
        int phaseIndex = 0;
        if (interceptorsQuantity == 1 && (i = kotlin.collections.q.i(phases)) >= 0) {
            int phaseIndex2 = 0;
            while (true) {
                Object obj = phases.get(phaseIndex2);
                if (!(obj instanceof a)) {
                    obj = null;
                }
                a phaseContent = (a) obj;
                if (phaseContent == null || phaseContent.i()) {
                    if (phaseIndex2 == i) {
                        break;
                    }
                    phaseIndex2++;
                } else {
                    ArrayList interceptors2 = phaseContent.j();
                    v(phaseContent);
                    return interceptors2;
                }
            }
        }
        ArrayList destination = new ArrayList(interceptorsQuantity);
        int i2 = kotlin.collections.q.i(phases);
        if (i2 >= 0) {
            while (true) {
                Object obj2 = phases.get(phaseIndex);
                if (!(obj2 instanceof a)) {
                    obj2 = null;
                }
                a phase = (a) obj2;
                if (phase != null) {
                    phase.c(destination);
                }
                if (phaseIndex == i2) {
                    break;
                }
                phaseIndex++;
            }
        }
        s(destination);
        return destination;
    }

    public final void p(@NotNull g phase, @NotNull q<? super d<TSubject, TContext>, ? super TSubject, ? super kotlin.coroutines.d<? super x>, ? extends Object> block) {
        k.f(phase, TypedValues.CycleType.S_WAVE_PHASE);
        k.f(block, "block");
        a phaseContent = g(phase);
        if (phaseContent == null) {
            throw new b("Phase " + phase + " was not registered for this pipeline");
        } else if (x(phase, block)) {
            this.f++;
        } else {
            phaseContent.a(block);
            this.f++;
            t();
            b();
        }
    }

    public void b() {
    }

    public final void r(@NotNull c<TSubject, TContext> from) {
        b fromPhaseRelation;
        k.f(from, "from");
        if (!f(from)) {
            if (this.f == 0) {
                u(from);
            } else {
                t();
            }
            ArrayList fromPhases = from.d;
            int index = 0;
            int i = kotlin.collections.q.i(fromPhases);
            if (i >= 0) {
                while (true) {
                    Object fromPhaseOrContent = fromPhases.get(index);
                    k.b(fromPhaseOrContent, "fromPhases[index]");
                    g fromPhase = (g) (!(fromPhaseOrContent instanceof g) ? null : fromPhaseOrContent);
                    if (fromPhase == null) {
                        if (fromPhaseOrContent != null) {
                            fromPhase = ((a) fromPhaseOrContent).f();
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type io.ktor.util.pipeline.Pipeline.PhaseContent<*, *>");
                        }
                    }
                    if (!k(fromPhase)) {
                        if (fromPhaseOrContent == fromPhase) {
                            fromPhaseRelation = b.C0278c.a;
                        } else if (fromPhaseOrContent != null) {
                            fromPhaseRelation = ((a) fromPhaseOrContent).g();
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type io.ktor.util.pipeline.Pipeline.PhaseContent<*, *>");
                        }
                        if (fromPhaseRelation instanceof b.C0278c) {
                            a(fromPhase);
                        } else if (fromPhaseRelation instanceof b.C0277b) {
                            n(((b.C0277b) fromPhaseRelation).a(), fromPhase);
                        } else if (fromPhaseRelation instanceof b.a) {
                            m(((b.a) fromPhaseRelation).a(), fromPhase);
                        }
                    }
                    if ((fromPhaseOrContent instanceof a) && !((a) fromPhaseOrContent).i()) {
                        a aVar = (a) fromPhaseOrContent;
                        a aVar2 = (a) fromPhaseOrContent;
                        a g = g(fromPhase);
                        if (g == null) {
                            k.n();
                        }
                        aVar2.b(g);
                        this.f += ((a) fromPhaseOrContent).h();
                    }
                    if (index != i) {
                        index++;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    private final boolean f(c<TSubject, TContext> from) {
        if (from.d.isEmpty()) {
            return true;
        }
        int index = 0;
        if (!this.d.isEmpty()) {
            return false;
        }
        ArrayList fromPhases = from.d;
        int i = kotlin.collections.q.i(fromPhases);
        if (i >= 0) {
            while (true) {
                Object fromPhaseOrContent = fromPhases.get(index);
                k.b(fromPhaseOrContent, "fromPhases[index]");
                if (fromPhaseOrContent instanceof g) {
                    this.d.add(fromPhaseOrContent);
                } else if (fromPhaseOrContent instanceof a) {
                    a aVar = (a) fromPhaseOrContent;
                    this.d.add(new a(((a) fromPhaseOrContent).f(), ((a) fromPhaseOrContent).g(), ((a) fromPhaseOrContent).j()));
                }
                if (index == i) {
                    break;
                }
                index++;
            }
        }
        this.f += from.f;
        u(from);
        return true;
    }

    private final List<q<d<TSubject, TContext>, TSubject, kotlin.coroutines.d<? super x>, Object>> w() {
        if (this.interceptors == null) {
            c();
        }
        this.q = true;
        List<? extends q<? super d<TSubject, TContext>, ? super TSubject, ? super kotlin.coroutines.d<? super x>, ? extends Object>> list = this.interceptors;
        if (list == null) {
            k.n();
        }
        return list;
    }

    private final void t() {
        this.interceptors = null;
        this.q = false;
        this.x = null;
    }

    private final void s(List<? extends q<? super d<TSubject, TContext>, ? super TSubject, ? super kotlin.coroutines.d<? super x>, ? extends Object>> list) {
        this.interceptors = list;
        this.q = false;
        this.x = null;
    }

    private final void v(a<TSubject, TContext> phaseContent) {
        this.interceptors = phaseContent.j();
        this.q = false;
        this.x = phaseContent.f();
    }

    private final void u(c<TSubject, TContext> pipeline) {
        this.interceptors = pipeline.w();
        this.q = true;
        this.x = null;
    }

    private final boolean x(g phase, q<? super d<TSubject, TContext>, ? super TSubject, ? super kotlin.coroutines.d<? super x>, ? extends Object> block) {
        if (!this.d.isEmpty() && this.interceptors != null && !this.q) {
            if (k.a(this.x, phase)) {
                List it = this.interceptors;
                if (!e0.l(it)) {
                    it = null;
                }
                if (it != null) {
                    it.add(block);
                    return true;
                }
            }
            if ((k.a(phase, y.d0(this.d)) || h(phase) == kotlin.collections.q.i(this.d)) && e0.l(this.interceptors)) {
                a g = g(phase);
                if (g == null) {
                    k.n();
                }
                g.a(block);
                List<? extends q<? super d<TSubject, TContext>, ? super TSubject, ? super kotlin.coroutines.d<? super x>, ? extends Object>> list = this.interceptors;
                if (list != null) {
                    e0.b(list).add(block);
                    return true;
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableList<io.ktor.util.pipeline.PipelineInterceptor<TSubject, TContext> /* = suspend io.ktor.util.pipeline.PipelineContext<TSubject, TContext>.(TSubject) -> kotlin.Unit */>");
            }
        }
        return false;
    }
}
