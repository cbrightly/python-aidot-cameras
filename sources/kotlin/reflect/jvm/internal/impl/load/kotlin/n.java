package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.java.structure.g;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KotlinClassFinder.kt */
public interface n extends u {
    @Nullable
    a a(@NotNull g gVar);

    @Nullable
    a c(@NotNull kotlin.reflect.jvm.internal.impl.name.a aVar);

    /* compiled from: KotlinClassFinder.kt */
    public static abstract class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Nullable
        public final p a() {
            b bVar = (b) (!(this instanceof b) ? null : this);
            if (bVar != null) {
                return bVar.b();
            }
            return null;
        }

        /* compiled from: KotlinClassFinder.kt */
        public static final class b extends a {
            @NotNull
            private final p a;

            public boolean equals(@Nullable Object obj) {
                if (this != obj) {
                    return (obj instanceof b) && k.a(this.a, ((b) obj).a);
                }
                return true;
            }

            public int hashCode() {
                p pVar = this.a;
                if (pVar != null) {
                    return pVar.hashCode();
                }
                return 0;
            }

            @NotNull
            public String toString() {
                return "KotlinClass(kotlinJvmBinaryClass=" + this.a + ")";
            }

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public b(@NotNull p kotlinJvmBinaryClass) {
                super((DefaultConstructorMarker) null);
                k.f(kotlinJvmBinaryClass, "kotlinJvmBinaryClass");
                this.a = kotlinJvmBinaryClass;
            }

            @NotNull
            public final p b() {
                return this.a;
            }
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.load.kotlin.n$a$a  reason: collision with other inner class name */
        /* compiled from: KotlinClassFinder.kt */
        public static final class C0378a extends a {
            @NotNull
            private final byte[] a;

            public boolean equals(@Nullable Object obj) {
                if (this != obj) {
                    return (obj instanceof C0378a) && k.a(this.a, ((C0378a) obj).a);
                }
                return true;
            }

            public int hashCode() {
                byte[] bArr = this.a;
                if (bArr != null) {
                    return Arrays.hashCode(bArr);
                }
                return 0;
            }

            @NotNull
            public String toString() {
                return "ClassFileContent(content=" + Arrays.toString(this.a) + ")";
            }

            @NotNull
            public final byte[] b() {
                return this.a;
            }
        }
    }
}
