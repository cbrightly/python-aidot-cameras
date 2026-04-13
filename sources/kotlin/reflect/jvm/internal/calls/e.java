package kotlin.reflect.jvm.internal.calls;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.c0;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.calls.d;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CallerImpl.kt */
public abstract class e<M extends Member> implements d<M> {
    public static final d a = new d((DefaultConstructorMarker) null);
    @NotNull
    private final List<Type> b;
    @NotNull
    private final M c;
    @NotNull
    private final Type d;
    @Nullable
    private final Class<?> e;

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0029, code lost:
        if (r0 != null) goto L_0x0030;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private e(M r5, java.lang.reflect.Type r6, java.lang.Class<?> r7, java.lang.reflect.Type[] r8) {
        /*
            r4 = this;
            r4.<init>()
            r4.c = r5
            r4.d = r6
            r4.e = r7
            if (r7 == 0) goto L_0x002c
            r0 = r7
            r1 = 0
            kotlin.jvm.internal.c0 r2 = new kotlin.jvm.internal.c0
            r3 = 2
            r2.<init>(r3)
            r2.a(r0)
            r2.b(r8)
            int r3 = r2.c()
            java.lang.reflect.Type[] r3 = new java.lang.reflect.Type[r3]
            java.lang.Object[] r2 = r2.d(r3)
            java.lang.reflect.Type[] r2 = (java.lang.reflect.Type[]) r2
            java.util.List r0 = kotlin.collections.q.j(r2)
            if (r0 == 0) goto L_0x002c
            goto L_0x0030
        L_0x002c:
            java.util.List r0 = kotlin.collections.l.U(r8)
        L_0x0030:
            r4.b = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.calls.e.<init>(java.lang.reflect.Member, java.lang.reflect.Type, java.lang.Class, java.lang.reflect.Type[]):void");
    }

    public /* synthetic */ e(Member member, Type returnType, Class instanceClass, Type[] valueParameterTypes, DefaultConstructorMarker $constructor_marker) {
        this(member, returnType, instanceClass, valueParameterTypes);
    }

    public void c(@NotNull Object[] args) {
        k.f(args, "args");
        d.a.a(this, args);
    }

    @NotNull
    public final M b() {
        return this.c;
    }

    @NotNull
    public final Type getReturnType() {
        return this.d;
    }

    @Nullable
    public final Class<?> e() {
        return this.e;
    }

    @NotNull
    public List<Type> a() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public final void d(@Nullable Object obj) {
        if (obj == null || !this.c.getDeclaringClass().isInstance(obj)) {
            throw new IllegalArgumentException("An object member requires the object instance passed as the first argument.");
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.calls.e$e  reason: collision with other inner class name */
    /* compiled from: CallerImpl.kt */
    public static final class C0332e extends e<Constructor<?>> {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public C0332e(@org.jetbrains.annotations.NotNull java.lang.reflect.Constructor<?> r8) {
            /*
                r7 = this;
                java.lang.String r0 = "constructor"
                kotlin.jvm.internal.k.f(r8, r0)
                java.lang.Class r3 = r8.getDeclaringClass()
                java.lang.String r0 = "constructor.declaringClass"
                kotlin.jvm.internal.k.b(r3, r0)
                java.lang.Class r0 = r8.getDeclaringClass()
                r1 = 0
                java.lang.String r2 = "klass"
                kotlin.jvm.internal.k.b(r0, r2)
                java.lang.Class r2 = r0.getDeclaringClass()
                if (r2 == 0) goto L_0x002c
                int r4 = r0.getModifiers()
                boolean r4 = java.lang.reflect.Modifier.isStatic(r4)
                if (r4 != 0) goto L_0x002c
                r4 = r2
                goto L_0x002e
            L_0x002c:
                r2 = 0
                r4 = r2
            L_0x002e:
                java.lang.reflect.Type[] r5 = r8.getGenericParameterTypes()
                java.lang.String r0 = "constructor.genericParameterTypes"
                kotlin.jvm.internal.k.b(r5, r0)
                r6 = 0
                r1 = r7
                r2 = r8
                r1.<init>(r2, r3, r4, r5, r6)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.calls.e.C0332e.<init>(java.lang.reflect.Constructor):void");
        }

        @Nullable
        public Object call(@NotNull Object[] args) {
            k.f(args, "args");
            c(args);
            return ((Constructor) b()).newInstance(Arrays.copyOf(args, args.length));
        }
    }

    /* compiled from: CallerImpl.kt */
    public static final class c extends e<Constructor<?>> implements c {
        private final Object f;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public c(@org.jetbrains.annotations.NotNull java.lang.reflect.Constructor<?> r8, @org.jetbrains.annotations.Nullable java.lang.Object r9) {
            /*
                r7 = this;
                java.lang.String r0 = "constructor"
                kotlin.jvm.internal.k.f(r8, r0)
                java.lang.Class r3 = r8.getDeclaringClass()
                java.lang.String r0 = "constructor.declaringClass"
                kotlin.jvm.internal.k.b(r3, r0)
                java.lang.reflect.Type[] r5 = r8.getGenericParameterTypes()
                java.lang.String r0 = "constructor.genericParameterTypes"
                kotlin.jvm.internal.k.b(r5, r0)
                r4 = 0
                r6 = 0
                r1 = r7
                r2 = r8
                r1.<init>(r2, r3, r4, r5, r6)
                r7.f = r9
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.calls.e.c.<init>(java.lang.reflect.Constructor, java.lang.Object):void");
        }

        @Nullable
        public Object call(@NotNull Object[] args) {
            k.f(args, "args");
            c(args);
            c0 c0Var = new c0(2);
            c0Var.a(this.f);
            c0Var.b(args);
            return ((Constructor) b()).newInstance(c0Var.d(new Object[c0Var.c()]));
        }
    }

    /* compiled from: CallerImpl.kt */
    public static final class b extends e<Constructor<?>> {
        /* JADX WARNING: type inference failed for: r4v5, types: [java.lang.Object[]] */
        /* JADX WARNING: Illegal instructions before constructor call */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public b(@org.jetbrains.annotations.NotNull java.lang.reflect.Constructor<?> r8) {
            /*
                r7 = this;
                java.lang.String r0 = "constructor"
                kotlin.jvm.internal.k.f(r8, r0)
                java.lang.Class r3 = r8.getDeclaringClass()
                java.lang.String r0 = "constructor.declaringClass"
                kotlin.jvm.internal.k.b(r3, r0)
                kotlin.reflect.jvm.internal.calls.e$d r0 = kotlin.reflect.jvm.internal.calls.e.a
                java.lang.reflect.Type[] r1 = r8.getGenericParameterTypes()
                java.lang.String r2 = "constructor.genericParameterTypes"
                kotlin.jvm.internal.k.b(r1, r2)
                r2 = 0
                int r4 = r1.length
                r5 = 0
                r6 = 1
                if (r4 > r6) goto L_0x0023
                java.lang.reflect.Type[] r4 = new java.lang.reflect.Type[r5]
                goto L_0x002b
            L_0x0023:
                int r4 = r1.length
                int r4 = r4 - r6
                java.lang.Object[] r4 = kotlin.collections.k.i(r1, r5, r4)
                if (r4 == 0) goto L_0x0036
            L_0x002b:
                r5 = r4
                java.lang.reflect.Type[] r5 = (java.lang.reflect.Type[]) r5
                r6 = 0
                r4 = 0
                r1 = r7
                r2 = r8
                r1.<init>(r2, r3, r4, r5, r6)
                return
            L_0x0036:
                kotlin.TypeCastException r3 = new kotlin.TypeCastException
                java.lang.String r4 = "null cannot be cast to non-null type kotlin.Array<T>"
                r3.<init>(r4)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.calls.e.b.<init>(java.lang.reflect.Constructor):void");
        }

        @Nullable
        public Object call(@NotNull Object[] args) {
            k.f(args, "args");
            c(args);
            c0 c0Var = new c0(2);
            c0Var.b(args);
            c0Var.a((Object) null);
            return ((Constructor) b()).newInstance(c0Var.d(new Object[c0Var.c()]));
        }
    }

    /* compiled from: CallerImpl.kt */
    public static final class a extends e<Constructor<?>> implements c {
        private final Object f;

        /* JADX WARNING: type inference failed for: r4v5, types: [java.lang.Object[]] */
        /* JADX WARNING: Illegal instructions before constructor call */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public a(@org.jetbrains.annotations.NotNull java.lang.reflect.Constructor<?> r8, @org.jetbrains.annotations.Nullable java.lang.Object r9) {
            /*
                r7 = this;
                java.lang.String r0 = "constructor"
                kotlin.jvm.internal.k.f(r8, r0)
                java.lang.Class r3 = r8.getDeclaringClass()
                java.lang.String r0 = "constructor.declaringClass"
                kotlin.jvm.internal.k.b(r3, r0)
                kotlin.reflect.jvm.internal.calls.e$d r0 = kotlin.reflect.jvm.internal.calls.e.a
                java.lang.reflect.Type[] r1 = r8.getGenericParameterTypes()
                java.lang.String r2 = "constructor.genericParameterTypes"
                kotlin.jvm.internal.k.b(r1, r2)
                r2 = 0
                int r4 = r1.length
                r5 = 2
                if (r4 > r5) goto L_0x0024
                r4 = 0
                java.lang.reflect.Type[] r4 = new java.lang.reflect.Type[r4]
                goto L_0x002d
            L_0x0024:
                int r4 = r1.length
                r5 = 1
                int r4 = r4 - r5
                java.lang.Object[] r4 = kotlin.collections.k.i(r1, r5, r4)
                if (r4 == 0) goto L_0x003a
            L_0x002d:
                r5 = r4
                java.lang.reflect.Type[] r5 = (java.lang.reflect.Type[]) r5
                r6 = 0
                r4 = 0
                r1 = r7
                r2 = r8
                r1.<init>(r2, r3, r4, r5, r6)
                r7.f = r9
                return
            L_0x003a:
                kotlin.TypeCastException r3 = new kotlin.TypeCastException
                java.lang.String r4 = "null cannot be cast to non-null type kotlin.Array<T>"
                r3.<init>(r4)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.calls.e.a.<init>(java.lang.reflect.Constructor, java.lang.Object):void");
        }

        @Nullable
        public Object call(@NotNull Object[] args) {
            k.f(args, "args");
            c(args);
            c0 c0Var = new c0(3);
            c0Var.a(this.f);
            c0Var.b(args);
            c0Var.a((Object) null);
            return ((Constructor) b()).newInstance(c0Var.d(new Object[c0Var.c()]));
        }
    }

    /* compiled from: CallerImpl.kt */
    public static abstract class h extends e<Method> {
        private final boolean f;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private h(java.lang.reflect.Method r7, boolean r8, java.lang.reflect.Type[] r9) {
            /*
                r6 = this;
                java.lang.reflect.Type r2 = r7.getGenericReturnType()
                java.lang.String r0 = "method.genericReturnType"
                kotlin.jvm.internal.k.b(r2, r0)
                if (r8 == 0) goto L_0x0012
                java.lang.Class r0 = r7.getDeclaringClass()
                goto L_0x0013
            L_0x0012:
                r0 = 0
            L_0x0013:
                r3 = r0
                r5 = 0
                r0 = r6
                r1 = r7
                r4 = r9
                r0.<init>(r1, r2, r3, r4, r5)
                java.lang.reflect.Type r0 = r6.getReturnType()
                java.lang.Class r1 = java.lang.Void.TYPE
                boolean r0 = kotlin.jvm.internal.k.a(r0, r1)
                r6.f = r0
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.calls.e.h.<init>(java.lang.reflect.Method, boolean, java.lang.reflect.Type[]):void");
        }

        public /* synthetic */ h(Method method, boolean requiresInstance, Type[] parameterTypes, DefaultConstructorMarker $constructor_marker) {
            this(method, requiresInstance, parameterTypes);
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        /* synthetic */ h(java.lang.reflect.Method r1, boolean r2, java.lang.reflect.Type[] r3, int r4, kotlin.jvm.internal.DefaultConstructorMarker r5) {
            /*
                r0 = this;
                r5 = r4 & 2
                if (r5 == 0) goto L_0x000e
                int r2 = r1.getModifiers()
                boolean r2 = java.lang.reflect.Modifier.isStatic(r2)
                r2 = r2 ^ 1
            L_0x000e:
                r4 = r4 & 4
                if (r4 == 0) goto L_0x001b
                java.lang.reflect.Type[] r3 = r1.getGenericParameterTypes()
                java.lang.String r4 = "method.genericParameterTypes"
                kotlin.jvm.internal.k.b(r3, r4)
            L_0x001b:
                r0.<init>(r1, r2, r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.calls.e.h.<init>(java.lang.reflect.Method, boolean, java.lang.reflect.Type[], int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        /* access modifiers changed from: protected */
        @Nullable
        public final Object f(@Nullable Object instance, @NotNull Object[] args) {
            k.f(args, "args");
            return this.f ? x.a : ((Method) b()).invoke(instance, Arrays.copyOf(args, args.length));
        }

        /* compiled from: CallerImpl.kt */
        public static final class f extends h {
            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public f(@NotNull Method method) {
                super(method, false, (Type[]) null, 6, (DefaultConstructorMarker) null);
                k.f(method, FirebaseAnalytics.Param.METHOD);
            }

            @Nullable
            public Object call(@NotNull Object[] args) {
                k.f(args, "args");
                c(args);
                return f((Object) null, args);
            }
        }

        /* compiled from: CallerImpl.kt */
        public static final class d extends h {
            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public d(@NotNull Method method) {
                super(method, false, (Type[]) null, 6, (DefaultConstructorMarker) null);
                k.f(method, FirebaseAnalytics.Param.METHOD);
            }

            @Nullable
            public Object call(@NotNull Object[] args) {
                Object[] objArr;
                k.f(args, "args");
                c(args);
                Object obj = args[0];
                d dVar = e.a;
                Object[] $this$dropFirst$iv = args;
                if ($this$dropFirst$iv.length <= 1) {
                    objArr = new Object[0];
                } else {
                    objArr = kotlin.collections.k.i($this$dropFirst$iv, 1, $this$dropFirst$iv.length);
                    if (objArr == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                    }
                }
                return f(obj, objArr);
            }
        }

        /* renamed from: kotlin.reflect.jvm.internal.calls.e$h$e  reason: collision with other inner class name */
        /* compiled from: CallerImpl.kt */
        public static final class C0335e extends h {
            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public C0335e(@NotNull Method method) {
                super(method, true, (Type[]) null, 4, (DefaultConstructorMarker) null);
                k.f(method, FirebaseAnalytics.Param.METHOD);
            }

            @Nullable
            public Object call(@NotNull Object[] args) {
                Object[] objArr;
                k.f(args, "args");
                c(args);
                d(l.v(args));
                d dVar = e.a;
                Object[] $this$dropFirst$iv = args;
                if ($this$dropFirst$iv.length <= 1) {
                    objArr = new Object[0];
                } else {
                    objArr = kotlin.collections.k.i($this$dropFirst$iv, 1, $this$dropFirst$iv.length);
                    if (objArr == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                    }
                }
                return f((Object) null, objArr);
            }
        }

        /* compiled from: CallerImpl.kt */
        public static final class c extends h implements c {
            private final Object g;

            /* JADX WARNING: Illegal instructions before constructor call */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public c(@org.jetbrains.annotations.NotNull java.lang.reflect.Method r7, @org.jetbrains.annotations.Nullable java.lang.Object r8) {
                /*
                    r6 = this;
                    java.lang.String r0 = "method"
                    kotlin.jvm.internal.k.f(r7, r0)
                    kotlin.reflect.jvm.internal.calls.e$d r0 = kotlin.reflect.jvm.internal.calls.e.a
                    java.lang.reflect.Type[] r1 = r7.getGenericParameterTypes()
                    java.lang.String r2 = "method.genericParameterTypes"
                    kotlin.jvm.internal.k.b(r1, r2)
                    r2 = 0
                    int r3 = r1.length
                    r4 = 1
                    r5 = 0
                    if (r3 > r4) goto L_0x001a
                    java.lang.reflect.Type[] r3 = new java.lang.reflect.Type[r5]
                    goto L_0x0021
                L_0x001a:
                    int r3 = r1.length
                    java.lang.Object[] r3 = kotlin.collections.k.i(r1, r4, r3)
                    if (r3 == 0) goto L_0x002a
                L_0x0021:
                    java.lang.reflect.Type[] r3 = (java.lang.reflect.Type[]) r3
                    r0 = 0
                    r6.<init>(r7, r5, r3, r0)
                    r6.g = r8
                    return
                L_0x002a:
                    kotlin.TypeCastException r3 = new kotlin.TypeCastException
                    java.lang.String r4 = "null cannot be cast to non-null type kotlin.Array<T>"
                    r3.<init>(r4)
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.calls.e.h.c.<init>(java.lang.reflect.Method, java.lang.Object):void");
            }

            @Nullable
            public Object call(@NotNull Object[] args) {
                k.f(args, "args");
                c(args);
                c0 c0Var = new c0(2);
                c0Var.a(this.g);
                c0Var.b(args);
                return f((Object) null, c0Var.d(new Object[c0Var.c()]));
            }
        }

        /* compiled from: CallerImpl.kt */
        public static final class a extends h implements c {
            private final Object g;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public a(@NotNull Method method, @Nullable Object boundReceiver) {
                super(method, false, (Type[]) null, 4, (DefaultConstructorMarker) null);
                k.f(method, FirebaseAnalytics.Param.METHOD);
                this.g = boundReceiver;
            }

            @Nullable
            public Object call(@NotNull Object[] args) {
                k.f(args, "args");
                c(args);
                return f(this.g, args);
            }
        }

        /* compiled from: CallerImpl.kt */
        public static final class b extends h implements c {
            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public b(@NotNull Method method) {
                super(method, false, (Type[]) null, 4, (DefaultConstructorMarker) null);
                k.f(method, FirebaseAnalytics.Param.METHOD);
            }

            @Nullable
            public Object call(@NotNull Object[] args) {
                k.f(args, "args");
                c(args);
                return f((Object) null, args);
            }
        }
    }

    /* compiled from: CallerImpl.kt */
    public static abstract class f extends e<Field> {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private f(java.lang.reflect.Field r7, boolean r8) {
            /*
                r6 = this;
                java.lang.reflect.Type r2 = r7.getGenericType()
                java.lang.String r0 = "field.genericType"
                kotlin.jvm.internal.k.b(r2, r0)
                if (r8 == 0) goto L_0x0012
                java.lang.Class r0 = r7.getDeclaringClass()
                goto L_0x0013
            L_0x0012:
                r0 = 0
            L_0x0013:
                r3 = r0
                r0 = 0
                java.lang.reflect.Type[] r4 = new java.lang.reflect.Type[r0]
                r5 = 0
                r0 = r6
                r1 = r7
                r0.<init>(r1, r2, r3, r4, r5)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.calls.e.f.<init>(java.lang.reflect.Field, boolean):void");
        }

        public /* synthetic */ f(Field field, boolean requiresInstance, DefaultConstructorMarker $constructor_marker) {
            this(field, requiresInstance);
        }

        @Nullable
        public Object call(@NotNull Object[] args) {
            k.f(args, "args");
            c(args);
            return ((Field) b()).get(e() != null ? l.u(args) : null);
        }

        /* renamed from: kotlin.reflect.jvm.internal.calls.e$f$e  reason: collision with other inner class name */
        /* compiled from: CallerImpl.kt */
        public static final class C0333e extends f {
            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public C0333e(@NotNull Field field) {
                super(field, false, (DefaultConstructorMarker) null);
                k.f(field, "field");
            }
        }

        /* compiled from: CallerImpl.kt */
        public static final class c extends f {
            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public c(@NotNull Field field) {
                super(field, true, (DefaultConstructorMarker) null);
                k.f(field, "field");
            }
        }

        /* compiled from: CallerImpl.kt */
        public static final class d extends f {
            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public d(@NotNull Field field) {
                super(field, true, (DefaultConstructorMarker) null);
                k.f(field, "field");
            }

            public void c(@NotNull Object[] args) {
                k.f(args, "args");
                e.super.c(args);
                d(l.v(args));
            }
        }

        /* compiled from: CallerImpl.kt */
        public static final class a extends f implements c {
            private final Object f;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public a(@NotNull Field field, @Nullable Object boundReceiver) {
                super(field, false, (DefaultConstructorMarker) null);
                k.f(field, "field");
                this.f = boundReceiver;
            }

            @Nullable
            public Object call(@NotNull Object[] args) {
                k.f(args, "args");
                c(args);
                return ((Field) b()).get(this.f);
            }
        }

        /* compiled from: CallerImpl.kt */
        public static final class b extends f implements c {
            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public b(@NotNull Field field) {
                super(field, false, (DefaultConstructorMarker) null);
                k.f(field, "field");
            }
        }
    }

    /* compiled from: CallerImpl.kt */
    public static abstract class g extends e<Field> {
        private final boolean f;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private g(java.lang.reflect.Field r7, boolean r8, boolean r9) {
            /*
                r6 = this;
                java.lang.Class r2 = java.lang.Void.TYPE
                java.lang.String r0 = "Void.TYPE"
                kotlin.jvm.internal.k.b(r2, r0)
                if (r9 == 0) goto L_0x0010
                java.lang.Class r0 = r7.getDeclaringClass()
                goto L_0x0011
            L_0x0010:
                r0 = 0
            L_0x0011:
                r3 = r0
                r0 = 1
                java.lang.reflect.Type[] r4 = new java.lang.reflect.Type[r0]
                r0 = 0
                java.lang.reflect.Type r1 = r7.getGenericType()
                java.lang.String r5 = "field.genericType"
                kotlin.jvm.internal.k.b(r1, r5)
                r4[r0] = r1
                r5 = 0
                r0 = r6
                r1 = r7
                r0.<init>(r1, r2, r3, r4, r5)
                r6.f = r8
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.calls.e.g.<init>(java.lang.reflect.Field, boolean, boolean):void");
        }

        public /* synthetic */ g(Field field, boolean notNull, boolean requiresInstance, DefaultConstructorMarker $constructor_marker) {
            this(field, notNull, requiresInstance);
        }

        public void c(@NotNull Object[] args) {
            k.f(args, "args");
            e.super.c(args);
            if (this.f && l.F(args) == null) {
                throw new IllegalArgumentException("null is not allowed as a value for this property.");
            }
        }

        @Nullable
        public Object call(@NotNull Object[] args) {
            k.f(args, "args");
            c(args);
            ((Field) b()).set(e() != null ? l.u(args) : null, l.F(args));
            return x.a;
        }

        /* renamed from: kotlin.reflect.jvm.internal.calls.e$g$e  reason: collision with other inner class name */
        /* compiled from: CallerImpl.kt */
        public static final class C0334e extends g {
            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public C0334e(@NotNull Field field, boolean notNull) {
                super(field, notNull, false, (DefaultConstructorMarker) null);
                k.f(field, "field");
            }
        }

        /* compiled from: CallerImpl.kt */
        public static final class c extends g {
            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public c(@NotNull Field field, boolean notNull) {
                super(field, notNull, true, (DefaultConstructorMarker) null);
                k.f(field, "field");
            }
        }

        /* compiled from: CallerImpl.kt */
        public static final class d extends g {
            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public d(@NotNull Field field, boolean notNull) {
                super(field, notNull, true, (DefaultConstructorMarker) null);
                k.f(field, "field");
            }

            public void c(@NotNull Object[] args) {
                k.f(args, "args");
                super.c(args);
                d(l.v(args));
            }
        }

        /* compiled from: CallerImpl.kt */
        public static final class a extends g implements c {
            private final Object g;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public a(@NotNull Field field, boolean notNull, @Nullable Object boundReceiver) {
                super(field, notNull, false, (DefaultConstructorMarker) null);
                k.f(field, "field");
                this.g = boundReceiver;
            }

            @Nullable
            public Object call(@NotNull Object[] args) {
                k.f(args, "args");
                c(args);
                ((Field) b()).set(this.g, l.u(args));
                return x.a;
            }
        }

        /* compiled from: CallerImpl.kt */
        public static final class b extends g implements c {
            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public b(@NotNull Field field, boolean notNull) {
                super(field, notNull, false, (DefaultConstructorMarker) null);
                k.f(field, "field");
            }

            @Nullable
            public Object call(@NotNull Object[] args) {
                k.f(args, "args");
                c(args);
                ((Field) b()).set((Object) null, l.F(args));
                return x.a;
            }
        }
    }

    /* compiled from: CallerImpl.kt */
    public static final class d {
        private d() {
        }

        public /* synthetic */ d(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
