package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.Arrays;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.java.structure.g;
import kotlin.reflect.jvm.internal.impl.load.java.structure.t;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaClassFinder.kt */
public interface m {
    @Nullable
    g a(@NotNull a aVar);

    @Nullable
    t b(@NotNull b bVar);

    @Nullable
    Set<String> c(@NotNull b bVar);

    /* compiled from: JavaClassFinder.kt */
    public static final class a {
        @NotNull
        private final kotlin.reflect.jvm.internal.impl.name.a a;
        @Nullable
        private final byte[] b;
        @Nullable
        private final g c;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return k.a(this.a, aVar.a) && k.a(this.b, aVar.b) && k.a(this.c, aVar.c);
        }

        public int hashCode() {
            kotlin.reflect.jvm.internal.impl.name.a aVar = this.a;
            int i = 0;
            int hashCode = (aVar != null ? aVar.hashCode() : 0) * 31;
            byte[] bArr = this.b;
            int hashCode2 = (hashCode + (bArr != null ? Arrays.hashCode(bArr) : 0)) * 31;
            g gVar = this.c;
            if (gVar != null) {
                i = gVar.hashCode();
            }
            return hashCode2 + i;
        }

        @NotNull
        public String toString() {
            return "Request(classId=" + this.a + ", previouslyFoundClassFileContent=" + Arrays.toString(this.b) + ", outerClass=" + this.c + ")";
        }

        public a(@NotNull kotlin.reflect.jvm.internal.impl.name.a classId, @Nullable byte[] previouslyFoundClassFileContent, @Nullable g outerClass) {
            k.f(classId, "classId");
            this.a = classId;
            this.b = previouslyFoundClassFileContent;
            this.c = outerClass;
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.name.a a() {
            return this.a;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ a(kotlin.reflect.jvm.internal.impl.name.a aVar, byte[] bArr, g gVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(aVar, (i & 2) != 0 ? null : bArr, (i & 4) != 0 ? null : gVar);
        }
    }
}
