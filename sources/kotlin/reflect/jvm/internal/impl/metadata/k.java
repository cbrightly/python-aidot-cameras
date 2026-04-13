package kotlin.reflect.jvm.internal.impl.metadata;

import kotlin.reflect.jvm.internal.impl.protobuf.i;

/* compiled from: ProtoBuf */
public enum k implements i.a {
    FINAL(0, 0),
    OPEN(1, 1),
    ABSTRACT(2, 2),
    SEALED(3, 3);
    
    private static i.b<k> c;
    private final int value;

    static {
        c = new a();
    }

    public final int getNumber() {
        return this.value;
    }

    public static k valueOf(int value2) {
        switch (value2) {
            case 0:
                return FINAL;
            case 1:
                return OPEN;
            case 2:
                return ABSTRACT;
            case 3:
                return SEALED;
            default:
                return null;
        }
    }

    /* compiled from: ProtoBuf */
    public static final class a implements i.b<k> {
        a() {
        }

        /* renamed from: b */
        public k a(int number) {
            return k.valueOf(number);
        }
    }

    private k(int index, int value2) {
        this.value = value2;
    }
}
