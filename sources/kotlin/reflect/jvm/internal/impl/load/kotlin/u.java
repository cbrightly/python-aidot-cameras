package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: PackagePartProvider.kt */
public interface u {
    @NotNull
    List<String> a(@NotNull String str);

    /* compiled from: PackagePartProvider.kt */
    public static final class a implements u {
        public static final a a = new a();

        private a() {
        }

        @NotNull
        public List<String> a(@NotNull String packageFqName) {
            k.f(packageFqName, "packageFqName");
            return q.g();
        }
    }
}
