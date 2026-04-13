package kotlin.reflect.jvm.internal.impl.metadata;

import kotlin.reflect.jvm.internal.impl.protobuf.i;

/* compiled from: ProtoBuf */
public enum x implements i.a {
    INTERNAL(0, 0),
    PRIVATE(1, 1),
    PROTECTED(2, 2),
    PUBLIC(3, 3),
    PRIVATE_TO_THIS(4, 4),
    LOCAL(5, 5);
    
    private static i.b<x> c;
    private final int value;

    static {
        c = new a();
    }

    public final int getNumber() {
        return this.value;
    }

    public static x valueOf(int value2) {
        switch (value2) {
            case 0:
                return INTERNAL;
            case 1:
                return PRIVATE;
            case 2:
                return PROTECTED;
            case 3:
                return PUBLIC;
            case 4:
                return PRIVATE_TO_THIS;
            case 5:
                return LOCAL;
            default:
                return null;
        }
    }

    /* compiled from: ProtoBuf */
    public static final class a implements i.b<x> {
        a() {
        }

        /* renamed from: b */
        public x a(int number) {
            return x.valueOf(number);
        }
    }

    private x(int index, int value2) {
        this.value = value2;
    }
}
