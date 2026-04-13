package kotlin.reflect.jvm.internal.impl.incremental.components;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: LookupTracker.kt */
public interface c {
    boolean a();

    void b(@NotNull String str, @NotNull e eVar, @NotNull String str2, @NotNull f fVar, @NotNull String str3);

    /* compiled from: LookupTracker.kt */
    public static final class a implements c {
        public static final a a = new a();

        private a() {
        }

        public boolean a() {
            return false;
        }

        public void b(@NotNull String filePath, @NotNull e position, @NotNull String scopeFqName, @NotNull f scopeKind, @NotNull String name) {
            k.f(filePath, Progress.FILE_PATH);
            k.f(position, "position");
            k.f(scopeFqName, "scopeFqName");
            k.f(scopeKind, "scopeKind");
            k.f(name, "name");
        }
    }
}
