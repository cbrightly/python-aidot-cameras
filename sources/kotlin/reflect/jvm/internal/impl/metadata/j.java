package kotlin.reflect.jvm.internal.impl.metadata;

import kotlin.reflect.jvm.internal.impl.protobuf.i;

/* compiled from: ProtoBuf */
public enum j implements i.a {
    DECLARATION(0, 0),
    FAKE_OVERRIDE(1, 1),
    DELEGATION(2, 2),
    SYNTHESIZED(3, 3);
    
    private static i.b<j> c;
    private final int value;

    static {
        c = new a();
    }

    public final int getNumber() {
        return this.value;
    }

    public static j valueOf(int value2) {
        switch (value2) {
            case 0:
                return DECLARATION;
            case 1:
                return FAKE_OVERRIDE;
            case 2:
                return DELEGATION;
            case 3:
                return SYNTHESIZED;
            default:
                return null;
        }
    }

    /* compiled from: ProtoBuf */
    public static final class a implements i.b<j> {
        a() {
        }

        /* renamed from: b */
        public j a(int number) {
            return j.valueOf(number);
        }
    }

    private j(int index, int value2) {
        this.value = value2;
    }
}
