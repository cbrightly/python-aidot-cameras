package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.w;
import kotlin.reflect.jvm.internal.impl.load.java.structure.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReflectJavaField.kt */
public final class p extends r implements n {
    @NotNull
    private final Field a;

    public p(@NotNull Field member) {
        k.f(member, "member");
        this.a = member;
    }

    @NotNull
    /* renamed from: L */
    public Field J() {
        return this.a;
    }

    public boolean C() {
        return J().isEnumConstant();
    }

    @NotNull
    /* renamed from: M */
    public w getType() {
        w.a aVar = w.a;
        Type genericType = J().getGenericType();
        k.b(genericType, "member.genericType");
        return aVar.a(genericType);
    }

    public boolean H() {
        return false;
    }
}
