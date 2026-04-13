package kotlin.reflect.jvm.internal;

import java.util.List;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.renderer.c;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ReflectionObjectRenderer.kt */
public final class d0 {
    private static final c a = c.f;
    public static final d0 b = new d0();

    private d0() {
    }

    private final void a(@NotNull StringBuilder $this$appendReceiverType, l0 receiver) {
        if (receiver != null) {
            b0 type = receiver.getType();
            k.b(type, "receiver.type");
            $this$appendReceiverType.append(h(type));
            $this$appendReceiverType.append(".");
        }
    }

    private final void b(@NotNull StringBuilder $this$appendReceivers, kotlin.reflect.jvm.internal.impl.descriptors.a callable) {
        l0 dispatchReceiver = h0.f(callable);
        l0 extensionReceiver = callable.L();
        a($this$appendReceivers, dispatchReceiver);
        boolean addParentheses = (dispatchReceiver == null || extensionReceiver == null) ? false : true;
        if (addParentheses) {
            $this$appendReceivers.append("(");
        }
        a($this$appendReceivers, extensionReceiver);
        if (addParentheses) {
            $this$appendReceivers.append(")");
        }
    }

    private final String c(kotlin.reflect.jvm.internal.impl.descriptors.a descriptor) {
        if (descriptor instanceof i0) {
            return g((i0) descriptor);
        }
        if (descriptor instanceof u) {
            return d((u) descriptor);
        }
        throw new IllegalStateException(("Illegal callable: " + descriptor).toString());
    }

    @NotNull
    public final String g(@NotNull i0 descriptor) {
        k.f(descriptor, "descriptor");
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        $this$buildString.append(descriptor.K() ? "var " : "val ");
        d0 d0Var = b;
        d0Var.b($this$buildString, descriptor);
        c cVar = a;
        f name = descriptor.getName();
        k.b(name, "descriptor.name");
        $this$buildString.append(cVar.w(name, true));
        $this$buildString.append(": ");
        b0 type = descriptor.getType();
        k.b(type, "descriptor.type");
        $this$buildString.append(d0Var.h(type));
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    @NotNull
    public final String d(@NotNull u descriptor) {
        k.f(descriptor, "descriptor");
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        $this$buildString.append("fun ");
        d0 d0Var = b;
        d0Var.b($this$buildString, descriptor);
        c cVar = a;
        f name = descriptor.getName();
        k.b(name, "descriptor.name");
        $this$buildString.append(cVar.w(name, true));
        List<w0> g = descriptor.g();
        k.b(g, "descriptor.valueParameters");
        y.Z(g, $this$buildString, ", ", "(", ")", 0, (CharSequence) null, a.INSTANCE, 48, (Object) null);
        $this$buildString.append(": ");
        b0 returnType = descriptor.getReturnType();
        if (returnType == null) {
            k.n();
        }
        k.b(returnType, "descriptor.returnType!!");
        $this$buildString.append(d0Var.h(returnType));
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    /* compiled from: ReflectionObjectRenderer.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<w0, String> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final String invoke(w0 it) {
            d0 d0Var = d0.b;
            k.b(it, "it");
            b0 type = it.getType();
            k.b(type, "it.type");
            return d0Var.h(type);
        }
    }

    @NotNull
    public final String e(@NotNull u invoke) {
        k.f(invoke, "invoke");
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        d0 d0Var = b;
        d0Var.b($this$buildString, invoke);
        List<w0> g = invoke.g();
        k.b(g, "invoke.valueParameters");
        y.Z(g, $this$buildString, ", ", "(", ")", 0, (CharSequence) null, b.INSTANCE, 48, (Object) null);
        $this$buildString.append(" -> ");
        b0 returnType = invoke.getReturnType();
        if (returnType == null) {
            k.n();
        }
        k.b(returnType, "invoke.returnType!!");
        $this$buildString.append(d0Var.h(returnType));
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    /* compiled from: ReflectionObjectRenderer.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<w0, String> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        @NotNull
        public final String invoke(w0 it) {
            d0 d0Var = d0.b;
            k.b(it, "it");
            b0 type = it.getType();
            k.b(type, "it.type");
            return d0Var.h(type);
        }
    }

    @NotNull
    public final String f(@NotNull p parameter) {
        k.f(parameter, "parameter");
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        switch (c0.a[parameter.h().ordinal()]) {
            case 1:
                $this$buildString.append("extension receiver parameter");
                break;
            case 2:
                $this$buildString.append("instance parameter");
                break;
            case 3:
                $this$buildString.append("parameter #" + parameter.e() + ' ' + parameter.getName());
                break;
        }
        $this$buildString.append(" of ");
        $this$buildString.append(b.c(parameter.c().s()));
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    @NotNull
    public final String i(@NotNull t0 typeParameter) {
        k.f(typeParameter, "typeParameter");
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        switch (c0.b[typeParameter.y().ordinal()]) {
            case 2:
                $this$buildString.append("in ");
                break;
            case 3:
                $this$buildString.append("out ");
                break;
        }
        $this$buildString.append(typeParameter.getName());
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    @NotNull
    public final String h(@NotNull b0 type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        return a.x(type);
    }
}
