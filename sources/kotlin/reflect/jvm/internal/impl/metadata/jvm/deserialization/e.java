package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JvmMemberSignature.kt */
public abstract class e {
    @NotNull
    public abstract String a();

    @NotNull
    public abstract String b();

    @NotNull
    public abstract String c();

    private e() {
    }

    public /* synthetic */ e(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    /* compiled from: JvmMemberSignature.kt */
    public static final class b extends e {
        @NotNull
        private final String a;
        @NotNull
        private final String b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return k.a(c(), bVar.c()) && k.a(b(), bVar.b());
        }

        public int hashCode() {
            String c = c();
            int i = 0;
            int hashCode = (c != null ? c.hashCode() : 0) * 31;
            String b2 = b();
            if (b2 != null) {
                i = b2.hashCode();
            }
            return hashCode + i;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull String name, @NotNull String desc) {
            super((DefaultConstructorMarker) null);
            k.f(name, "name");
            k.f(desc, "desc");
            this.a = name;
            this.b = desc;
        }

        @NotNull
        public String b() {
            return this.b;
        }

        @NotNull
        public String c() {
            return this.a;
        }

        @NotNull
        public String a() {
            return c() + b();
        }
    }

    /* compiled from: JvmMemberSignature.kt */
    public static final class a extends e {
        @NotNull
        private final String a;
        @NotNull
        private final String b;

        @NotNull
        public final String d() {
            return c();
        }

        @NotNull
        public final String e() {
            return b();
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return k.a(c(), aVar.c()) && k.a(b(), aVar.b());
        }

        public int hashCode() {
            String c = c();
            int i = 0;
            int hashCode = (c != null ? c.hashCode() : 0) * 31;
            String b2 = b();
            if (b2 != null) {
                i = b2.hashCode();
            }
            return hashCode + i;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull String name, @NotNull String desc) {
            super((DefaultConstructorMarker) null);
            k.f(name, "name");
            k.f(desc, "desc");
            this.a = name;
            this.b = desc;
        }

        @NotNull
        public String b() {
            return this.b;
        }

        @NotNull
        public String c() {
            return this.a;
        }

        @NotNull
        public String a() {
            return c() + ':' + b();
        }
    }

    @NotNull
    public final String toString() {
        return a();
    }
}
