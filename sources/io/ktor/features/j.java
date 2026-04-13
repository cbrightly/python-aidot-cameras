package io.ktor.features;

import io.ktor.http.f;
import io.ktor.http.o;
import io.ktor.http.s;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DefaultHeaders.kt */
public final class j {
    /* access modifiers changed from: private */
    public static final TimeZone a;
    private static final b b = new b();
    /* access modifiers changed from: private */
    @NotNull
    public static final io.ktor.util.a<j> c = new io.ktor.util.a<>("Default Headers");
    public static final c d = new c((DefaultConstructorMarker) null);
    private volatile Object cachedDateText = "";
    private final o e;
    private final kotlin.jvm.functions.a<Long> f;
    private long g;

    /* compiled from: DefaultHeaders.kt */
    public static final class d extends l implements p<String, List<? extends String>, x> {
        final /* synthetic */ io.ktor.application.b $call;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(io.ktor.application.b bVar) {
            super(2);
            this.$call = bVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            invoke((String) obj, (List<String>) (List) obj2);
            return x.a;
        }

        public final void invoke(@NotNull String name, @NotNull List<String> value) {
            k.f(name, "name");
            k.f(value, "value");
            for (String it : value) {
                io.ktor.response.c.a(this.$call.b(), name, it);
            }
        }
    }

    public j(@NotNull a config) {
        k.f(config, "config");
        this.e = config.b().m();
        this.f = config.a();
    }

    /* compiled from: DefaultHeaders.kt */
    public static final class a {
        @NotNull
        private final io.ktor.http.p a = new io.ktor.http.p(0, 1, (DefaultConstructorMarker) null);
        @NotNull
        private kotlin.jvm.functions.a<Long> b = C0246a.INSTANCE;

        @NotNull
        public final io.ktor.http.p b() {
            return this.a;
        }

        /* renamed from: io.ktor.features.j$a$a  reason: collision with other inner class name */
        /* compiled from: DefaultHeaders.kt */
        public static final class C0246a extends l implements kotlin.jvm.functions.a<Long> {
            public static final C0246a INSTANCE = new C0246a();

            C0246a() {
                super(0);
            }

            public final long invoke() {
                return System.currentTimeMillis();
            }
        }

        @NotNull
        public final kotlin.jvm.functions.a<Long> a() {
            return this.b;
        }
    }

    /* access modifiers changed from: private */
    public final void e(io.ktor.application.b call) {
        d(call);
        this.e.a(new d(call));
    }

    private final void d(io.ktor.application.b call) {
        long captureCached = this.g;
        long currentTimeStamp = this.f.invoke().longValue();
        if (((long) 1000) + captureCached <= currentTimeStamp) {
            this.g = currentTimeStamp;
            this.cachedDateText = f.b(f(currentTimeStamp));
        }
        io.ktor.response.c.a(call.b(), s.V0.t(), (String) this.cachedDateText);
    }

    private final io.ktor.util.date.c f(long time) {
        Object obj = b.get();
        k.b(obj, "calendar.get()");
        return io.ktor.util.date.a.c((Calendar) obj, Long.valueOf(time));
    }

    /* compiled from: DefaultHeaders.kt */
    public static final class c implements io.ktor.application.f<io.ktor.application.a, a, j> {
        private c() {
        }

        public /* synthetic */ c(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public io.ktor.util.a<j> getKey() {
            return j.c;
        }

        @NotNull
        /* renamed from: b */
        public j a(@NotNull io.ktor.application.a pipeline, @NotNull kotlin.jvm.functions.l<? super a, x> configure) {
            Class<io.ktor.application.a> cls = io.ktor.application.a.class;
            k.f(pipeline, "pipeline");
            k.f(configure, "configure");
            a config = new a();
            configure.invoke(config);
            io.ktor.http.p b = config.b();
            s sVar = s.V0;
            if (b.g(sVar.z()) == null) {
                Class applicationClass = pipeline.getClass();
                Package packageR = cls.getPackage();
                k.b(packageR, "Application::class.java.`package`");
                String ktorPackageName = packageR.getImplementationTitle();
                if (ktorPackageName == null) {
                    ktorPackageName = "ktor";
                }
                Package packageR2 = cls.getPackage();
                k.b(packageR2, "Application::class.java.`package`");
                String ktorPackageVersion = packageR2.getImplementationVersion();
                String applicationPackageVersion = "debug";
                if (ktorPackageVersion == null) {
                    ktorPackageVersion = applicationPackageVersion;
                }
                Package packageR3 = applicationClass.getPackage();
                k.b(packageR3, "applicationClass.`package`");
                String applicationPackageName = packageR3.getImplementationTitle();
                if (applicationPackageName == null) {
                    applicationPackageName = applicationClass.getSimpleName();
                    k.b(applicationPackageName, "applicationClass.simpleName");
                }
                Package packageR4 = applicationClass.getPackage();
                k.b(packageR4, "applicationClass.`package`");
                String implementationVersion = packageR4.getImplementationVersion();
                if (implementationVersion != null) {
                    applicationPackageVersion = implementationVersion;
                }
                io.ktor.http.p b2 = config.b();
                String z = sVar.z();
                b2.a(z, applicationPackageName + '/' + applicationPackageVersion + ' ' + ktorPackageName + '/' + ktorPackageVersion);
            }
            j feature = new j(config);
            pipeline.p(io.ktor.application.c.a2.b(), new a(feature, (kotlin.coroutines.d) null));
            return feature;
        }

        @kotlin.coroutines.jvm.internal.f(c = "io.ktor.features.DefaultHeaders$Feature$install$1", f = "DefaultHeaders.kt", l = {}, m = "invokeSuspend")
        /* compiled from: DefaultHeaders.kt */
        public static final class a extends kotlin.coroutines.jvm.internal.l implements q<io.ktor.util.pipeline.d<x, io.ktor.application.b>, x, kotlin.coroutines.d<? super x>, Object> {
            final /* synthetic */ j $feature;
            int label;
            private io.ktor.util.pipeline.d p$;
            private x p$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(j jVar, kotlin.coroutines.d dVar) {
                super(3, dVar);
                this.$feature = jVar;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@NotNull io.ktor.util.pipeline.d<x, io.ktor.application.b> dVar, @NotNull x xVar, @NotNull kotlin.coroutines.d<? super x> dVar2) {
                k.f(dVar, "$this$create");
                k.f(xVar, "it");
                k.f(dVar2, "continuation");
                a aVar = new a(this.$feature, dVar2);
                aVar.p$ = dVar;
                aVar.p$0 = xVar;
                return aVar;
            }

            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return ((a) create((io.ktor.util.pipeline.d) obj, (x) obj2, (kotlin.coroutines.d) obj3)).invokeSuspend(x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b($result);
                        io.ktor.util.pipeline.d $this$intercept = this.p$;
                        x xVar = this.p$0;
                        this.$feature.e((io.ktor.application.b) $this$intercept.getContext());
                        return x.a;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }
    }

    static {
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        if (timeZone == null) {
            k.n();
        }
        a = timeZone;
    }

    /* compiled from: DefaultHeaders.kt */
    public static final class b extends ThreadLocal<Calendar> {
        b() {
        }

        /* access modifiers changed from: protected */
        @NotNull
        /* renamed from: a */
        public Calendar initialValue() {
            Calendar instance = Calendar.getInstance(j.a, Locale.ROOT);
            k.b(instance, "Calendar.getInstance(GMT_TIMEZONE, Locale.ROOT)");
            return instance;
        }
    }
}
