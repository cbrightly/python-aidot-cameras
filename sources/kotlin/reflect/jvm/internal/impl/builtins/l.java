package kotlin.reflect.jvm.internal.impl.builtins;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: UnsignedType.kt */
public enum l {
    UBYTE(r2),
    USHORT(r2),
    UINT(r2),
    ULONG(r2);
    
    @NotNull
    private final a arrayClassId;
    @NotNull
    private final a classId;
    @NotNull
    private final f typeName;

    private l(a classId2) {
        this.classId = classId2;
        f j = classId2.j();
        k.b(j, "classId.shortClassName");
        this.typeName = j;
        b h = classId2.h();
        this.arrayClassId = new a(h, f.f(j.b() + "Array"));
    }

    @NotNull
    public final a getClassId() {
        return this.classId;
    }

    @NotNull
    public final f getTypeName() {
        return this.typeName;
    }

    @NotNull
    public final a getArrayClassId() {
        return this.arrayClassId;
    }
}
