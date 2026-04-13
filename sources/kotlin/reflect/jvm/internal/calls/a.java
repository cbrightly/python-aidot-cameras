package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.l0;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.calls.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AnnotationConstructorCaller.kt */
public final class a implements d {
    @NotNull
    private final List<Type> a;
    private final List<Class<?>> b;
    private final List<Object> c;
    private final Class<?> d;
    private final List<String> e;
    private final C0330a f;
    private final List<Method> g;

    /* renamed from: kotlin.reflect.jvm.internal.calls.a$a  reason: collision with other inner class name */
    /* compiled from: AnnotationConstructorCaller.kt */
    public enum C0330a {
        CALL_BY_NAME,
        POSITIONAL_CALL
    }

    /* compiled from: AnnotationConstructorCaller.kt */
    public enum b {
        JAVA,
        KOTLIN
    }

    public a(@NotNull Class<?> jClass, @NotNull List<String> parameterNames, @NotNull C0330a callMode, @NotNull b origin, @NotNull List<Method> methods) {
        Class<?> cls = jClass;
        List<String> list = parameterNames;
        C0330a aVar = callMode;
        b bVar = origin;
        List<Method> list2 = methods;
        k.f(cls, "jClass");
        k.f(list, "parameterNames");
        k.f(aVar, "callMode");
        k.f(bVar, "origin");
        k.f(list2, "methods");
        this.d = cls;
        this.e = list;
        this.f = aVar;
        this.g = list2;
        Iterable<Method> $this$mapTo$iv$iv = methods;
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (Method it : $this$mapTo$iv$iv) {
            arrayList.add(it.getGenericReturnType());
        }
        this.a = arrayList;
        Iterable<Method> $this$mapTo$iv$iv2 = this.g;
        ArrayList arrayList2 = new ArrayList(r.r($this$mapTo$iv$iv2, 10));
        for (Method method : $this$mapTo$iv$iv2) {
            Class it2 = method.getReturnType();
            k.b(it2, "it");
            Class h = kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b.h(it2);
            if (h != null) {
                it2 = h;
            }
            arrayList2.add(it2);
            Class<?> cls2 = jClass;
        }
        this.b = arrayList2;
        Iterable<Method> $this$mapTo$iv$iv3 = this.g;
        ArrayList arrayList3 = new ArrayList(r.r($this$mapTo$iv$iv3, 10));
        for (Method method2 : $this$mapTo$iv$iv3) {
            arrayList3.add(method2.getDefaultValue());
        }
        this.c = arrayList3;
        if (this.f == C0330a.POSITIONAL_CALL && bVar == b.JAVA && (!y.k0(this.e, "value").isEmpty())) {
            throw new UnsupportedOperationException("Positional call of a Java annotation constructor is allowed only if there are no parameters or one parameter named \"value\". This restriction exists because Java annotations (in contrast to Kotlin)do not impose any order on their arguments. Use KCallable#callBy instead.");
        }
    }

    public /* bridge */ /* synthetic */ Member b() {
        return (Member) d();
    }

    public void c(@NotNull Object[] args) {
        k.f(args, "args");
        d.a.a(this, args);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ a(java.lang.Class r17, java.util.List r18, kotlin.reflect.jvm.internal.calls.a.C0330a r19, kotlin.reflect.jvm.internal.calls.a.b r20, java.util.List r21, int r22, kotlin.jvm.internal.DefaultConstructorMarker r23) {
        /*
            r16 = this;
            r0 = r22 & 16
            if (r0 == 0) goto L_0x0037
            r0 = r18
            r1 = 0
            java.util.ArrayList r2 = new java.util.ArrayList
            r3 = 10
            int r3 = kotlin.collections.r.r(r0, r3)
            r2.<init>(r3)
            r3 = r0
            r4 = 0
            java.util.Iterator r5 = r3.iterator()
        L_0x0018:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x0033
            java.lang.Object r6 = r5.next()
            r7 = r6
            java.lang.String r7 = (java.lang.String) r7
            r8 = 0
            r9 = 0
            java.lang.Class[] r9 = new java.lang.Class[r9]
            r11 = r17
            java.lang.reflect.Method r7 = r11.getDeclaredMethod(r7, r9)
            r2.add(r7)
            goto L_0x0018
        L_0x0033:
            r11 = r17
            r15 = r2
            goto L_0x003b
        L_0x0037:
            r11 = r17
            r15 = r21
        L_0x003b:
            r10 = r16
            r11 = r17
            r12 = r18
            r13 = r19
            r14 = r20
            r10.<init>(r11, r12, r13, r14, r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.calls.a.<init>(java.lang.Class, java.util.List, kotlin.reflect.jvm.internal.calls.a$a, kotlin.reflect.jvm.internal.calls.a$b, java.util.List, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @Nullable
    public Void d() {
        return null;
    }

    @NotNull
    public Type getReturnType() {
        return this.d;
    }

    @NotNull
    public List<Type> a() {
        return this.a;
    }

    @Nullable
    public Object call(@NotNull Object[] args) {
        Object value;
        k.f(args, "args");
        c(args);
        Object[] $this$mapIndexed$iv = args;
        List values = new ArrayList($this$mapIndexed$iv.length);
        Object[] $this$mapIndexedTo$iv$iv = $this$mapIndexed$iv;
        int index$iv$iv = 0;
        int length = $this$mapIndexedTo$iv$iv.length;
        int i = 0;
        while (i < length) {
            int index$iv$iv2 = index$iv$iv + 1;
            Object arg = $this$mapIndexedTo$iv$iv[i];
            if (arg == null && this.f == C0330a.CALL_BY_NAME) {
                value = this.c.get(index$iv$iv);
            } else {
                value = b.f(arg, this.b.get(index$iv$iv));
            }
            if (value != null) {
                values.add(value);
                i++;
                index$iv$iv = index$iv$iv2;
            } else {
                Void unused = b.e(index$iv$iv, this.e.get(index$iv$iv), this.b.get(index$iv$iv));
                throw null;
            }
        }
        return b.c(this.d, l0.o(y.K0(this.e, values)), this.g);
    }
}
