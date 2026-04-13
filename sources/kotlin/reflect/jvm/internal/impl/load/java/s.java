package kotlin.reflect.jvm.internal.impl.load.java;

import com.meituan.robust.Constants;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.c;

/* compiled from: JvmAnnotationNames */
public final class s {
    public static final b a;
    public static final String b;
    public static final f c = f.f("value");
    public static final b d = new b("org.jetbrains.annotations.NotNull");
    public static final b e = new b("org.jetbrains.annotations.Nullable");
    public static final b f = new b("org.jetbrains.annotations.Mutable");
    public static final b g = new b("org.jetbrains.annotations.ReadOnly");
    public static final b h = new b("kotlin.annotations.jvm.ReadOnly");
    public static final b i = new b("kotlin.annotations.jvm.Mutable");
    public static final b j = new b("kotlin.jvm.PurelyImplements");
    public static final b k = new b("kotlin.jvm.internal.EnhancedNullability");
    public static final b l = new b("kotlin.jvm.internal.EnhancedMutability");
    public static final b m = new b("kotlin.annotations.jvm.internal.ParameterName");
    public static final b n = new b("kotlin.annotations.jvm.internal.DefaultValue");
    public static final b o = new b("kotlin.annotations.jvm.internal.DefaultNull");

    static {
        b bVar = new b("kotlin.Metadata");
        a = bVar;
        b = "L" + c.c(bVar).f() + Constants.PACKNAME_END;
    }
}
