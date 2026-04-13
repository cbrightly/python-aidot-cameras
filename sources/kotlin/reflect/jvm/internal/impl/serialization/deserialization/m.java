package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

/* compiled from: DeserializationConfiguration.kt */
public interface m {
    boolean a();

    boolean b();

    boolean c();

    boolean d();

    /* compiled from: DeserializationConfiguration.kt */
    public static final class b {
        public static boolean c(m $this) {
            return false;
        }

        public static boolean b(m $this) {
            return false;
        }

        public static boolean d(m $this) {
            return true;
        }

        public static boolean a(m $this) {
            return false;
        }
    }

    /* compiled from: DeserializationConfiguration.kt */
    public static final class a implements m {
        public static final a a = new a();

        private a() {
        }

        public boolean a() {
            return b.d(this);
        }

        public boolean b() {
            return b.c(this);
        }

        public boolean c() {
            return b.b(this);
        }

        public boolean d() {
            return b.a(this);
        }
    }
}
