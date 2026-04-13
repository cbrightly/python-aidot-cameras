package kotlin.reflect.jvm.internal.impl.protobuf;

/* compiled from: WireFormat */
public final class w {
    static final int a = c(1, 3);
    static final int b = c(1, 4);
    static final int c = c(2, 0);
    static final int d = c(3, 2);

    static int b(int tag) {
        return tag & 7;
    }

    public static int a(int tag) {
        return tag >>> 3;
    }

    static int c(int fieldNumber, int wireType) {
        return (fieldNumber << 3) | wireType;
    }

    /* compiled from: WireFormat */
    public enum c {
        INT(0),
        LONG(0L),
        FLOAT(Float.valueOf(0.0f)),
        DOUBLE(Double.valueOf(0.0d)),
        BOOLEAN(false),
        STRING(""),
        BYTE_STRING(d.c),
        ENUM((String) null),
        MESSAGE((String) null);
        
        private final Object defaultDefault;

        private c(Object defaultDefault2) {
            this.defaultDefault = defaultDefault2;
        }
    }

    /* compiled from: WireFormat */
    public enum b {
        DOUBLE(c.DOUBLE, 1),
        FLOAT(c.FLOAT, 5),
        INT64(r5, 0),
        UINT64(r5, 0),
        INT32(r11, 0),
        FIXED64(r5, 1),
        FIXED32(r11, 5),
        BOOL(c.BOOLEAN, 0),
        STRING(c.STRING, 2) {
            public boolean isPackable() {
                return false;
            }
        },
        GROUP(r13, 3) {
            public boolean isPackable() {
                return false;
            }
        },
        MESSAGE(r13, 2) {
            public boolean isPackable() {
                return false;
            }
        },
        BYTES(c.BYTE_STRING, 2) {
            public boolean isPackable() {
                return false;
            }
        },
        UINT32(r11, 0),
        ENUM(c.ENUM, 0),
        SFIXED32(r11, 5),
        SFIXED64(r5, 1),
        SINT32(r11, 0),
        SINT64(r5, 0);
        
        private final c javaType;
        private final int wireType;

        private b(c javaType2, int wireType2) {
            this.javaType = javaType2;
            this.wireType = wireType2;
        }

        public c getJavaType() {
            return this.javaType;
        }

        public int getWireType() {
            return this.wireType;
        }

        public boolean isPackable() {
            return true;
        }
    }
}
