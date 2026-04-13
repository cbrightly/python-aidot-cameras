package kotlin.reflect.jvm.internal.impl.load.java.components;

import com.didichuxing.doraemonkit.zxing.decoding.Intents;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.l0;
import kotlin.collections.o0;
import kotlin.collections.r;
import kotlin.collections.v;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.m;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.n;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.load.java.structure.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.constants.g;
import kotlin.reflect.jvm.internal.impl.resolve.constants.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.t;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaAnnotationMapper.kt */
public final class d {
    private static final Map<String, EnumSet<n>> a = l0.h(t.a("PACKAGE", EnumSet.noneOf(n.class)), t.a(Intents.WifiConnect.TYPE, EnumSet.of(n.CLASS, n.FILE)), t.a("ANNOTATION_TYPE", EnumSet.of(n.ANNOTATION_CLASS)), t.a("TYPE_PARAMETER", EnumSet.of(n.TYPE_PARAMETER)), t.a("FIELD", EnumSet.of(n.FIELD)), t.a("LOCAL_VARIABLE", EnumSet.of(n.LOCAL_VARIABLE)), t.a("PARAMETER", EnumSet.of(n.VALUE_PARAMETER)), t.a("CONSTRUCTOR", EnumSet.of(n.CONSTRUCTOR)), t.a("METHOD", EnumSet.of(n.FUNCTION, n.PROPERTY_GETTER, n.PROPERTY_SETTER)), t.a("TYPE_USE", EnumSet.of(n.TYPE)));
    private static final Map<String, m> b = l0.h(t.a("RUNTIME", m.RUNTIME), t.a("CLASS", m.BINARY), t.a("SOURCE", m.SOURCE));
    public static final d c = new d();

    private d() {
    }

    @NotNull
    public final Set<n> b(@Nullable String argumentName) {
        EnumSet enumSet = a.get(argumentName);
        return enumSet != null ? enumSet : o0.d();
    }

    @NotNull
    public final g<?> c(@NotNull List<? extends b> arguments) {
        k.f(arguments, "arguments");
        ArrayList arrayList = new ArrayList();
        for (Object element$iv$iv : arguments) {
            if (element$iv$iv instanceof kotlin.reflect.jvm.internal.impl.load.java.structure.m) {
                arrayList.add(element$iv$iv);
            }
        }
        Iterable<kotlin.reflect.jvm.internal.impl.load.java.structure.m> $this$flatMapTo$iv$iv = arrayList;
        ArrayList arrayList2 = new ArrayList();
        for (kotlin.reflect.jvm.internal.impl.load.java.structure.m it : $this$flatMapTo$iv$iv) {
            d dVar = c;
            f d = it.d();
            v.x(arrayList2, dVar.b(d != null ? d.b() : null));
        }
        Iterable<n> $this$mapTo$iv$iv = arrayList2;
        List kotlinTargets = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (n kotlinTarget : $this$mapTo$iv$iv) {
            kotlin.reflect.jvm.internal.impl.name.a m = kotlin.reflect.jvm.internal.impl.name.a.m(kotlin.reflect.jvm.internal.impl.builtins.g.h.E);
            k.b(m, "ClassId.topLevel(KotlinB…Q_NAMES.annotationTarget)");
            f f = f.f(kotlinTarget.name());
            k.b(f, "Name.identifier(kotlinTarget.name)");
            kotlinTargets.add(new j(m, f));
        }
        return new kotlin.reflect.jvm.internal.impl.resolve.constants.b(kotlinTargets, a.INSTANCE);
    }

    /* compiled from: JavaAnnotationMapper.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<y, b0> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final b0 invoke(@NotNull y module) {
            b0 type;
            k.f(module, "module");
            w0 parameterDescriptor = a.b(c.k.d(), module.j().o(kotlin.reflect.jvm.internal.impl.builtins.g.h.D));
            if (parameterDescriptor != null && (type = parameterDescriptor.getType()) != null) {
                return type;
            }
            i0 j = u.j("Error: AnnotationTarget[]");
            k.b(j, "ErrorUtils.createErrorTy…ror: AnnotationTarget[]\")");
            return j;
        }
    }

    @Nullable
    public final g<?> a(@Nullable b element) {
        kotlin.reflect.jvm.internal.impl.load.java.structure.m it = (kotlin.reflect.jvm.internal.impl.load.java.structure.m) (!(element instanceof kotlin.reflect.jvm.internal.impl.load.java.structure.m) ? null : element);
        if (it == null) {
            return null;
        }
        Map<String, m> map = b;
        f d = it.d();
        m retention = map.get(d != null ? d.b() : null);
        if (retention == null) {
            return null;
        }
        kotlin.reflect.jvm.internal.impl.name.a m = kotlin.reflect.jvm.internal.impl.name.a.m(kotlin.reflect.jvm.internal.impl.builtins.g.h.F);
        k.b(m, "ClassId.topLevel(KotlinB…AMES.annotationRetention)");
        f f = f.f(retention.name());
        k.b(f, "Name.identifier(retention.name)");
        return new j(m, f);
    }
}
