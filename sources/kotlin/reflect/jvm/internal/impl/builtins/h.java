package kotlin.reflect.jvm.internal.impl.builtins;

import com.didichuxing.doraemonkit.constant.SpInputType;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: PrimitiveType */
public enum h {
    BOOLEAN(SpInputType.BOOLEAN),
    CHAR("Char"),
    BYTE("Byte"),
    SHORT("Short"),
    INT("Int"),
    FLOAT(SpInputType.FLOAT),
    LONG(SpInputType.LONG),
    DOUBLE("Double");
    
    public static final Set<h> NUMBER_TYPES = null;
    private b arrayTypeFqName;
    private final f arrayTypeName;
    private b typeFqName;
    private final f typeName;

    static {
        h hVar;
        h hVar2;
        h hVar3;
        h hVar4;
        h hVar5;
        h hVar6;
        h hVar7;
        NUMBER_TYPES = Collections.unmodifiableSet(EnumSet.of(hVar, new h[]{hVar2, hVar3, hVar4, hVar5, hVar6, hVar7}));
    }

    private h(String typeName2) {
        this.typeFqName = null;
        this.arrayTypeFqName = null;
        this.typeName = f.f(typeName2);
        this.arrayTypeName = f.f(typeName2 + "Array");
    }

    @NotNull
    public f getTypeName() {
        f fVar = this.typeName;
        if (fVar == null) {
            a(0);
        }
        return fVar;
    }

    @NotNull
    public b getTypeFqName() {
        b bVar = this.typeFqName;
        if (bVar != null) {
            if (bVar == null) {
                a(1);
            }
            return bVar;
        }
        b c2 = g.b.c(this.typeName);
        this.typeFqName = c2;
        if (c2 == null) {
            a(2);
        }
        return c2;
    }

    @NotNull
    public f getArrayTypeName() {
        f fVar = this.arrayTypeName;
        if (fVar == null) {
            a(3);
        }
        return fVar;
    }

    @NotNull
    public b getArrayTypeFqName() {
        b bVar = this.arrayTypeFqName;
        if (bVar != null) {
            if (bVar == null) {
                a(4);
            }
            return bVar;
        }
        b c2 = g.b.c(this.arrayTypeName);
        this.arrayTypeFqName = c2;
        if (c2 == null) {
            a(5);
        }
        return c2;
    }
}
