package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.a;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: IncompatibleVersionErrorData.kt */
public final class t<T extends a> {
    @NotNull
    private final T a;
    @NotNull
    private final T b;
    @NotNull
    private final String c;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.name.a d;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof t)) {
            return false;
        }
        t tVar = (t) obj;
        return k.a(this.a, tVar.a) && k.a(this.b, tVar.b) && k.a(this.c, tVar.c) && k.a(this.d, tVar.d);
    }

    public int hashCode() {
        T t = this.a;
        int i = 0;
        int hashCode = (t != null ? t.hashCode() : 0) * 31;
        T t2 = this.b;
        int hashCode2 = (hashCode + (t2 != null ? t2.hashCode() : 0)) * 31;
        String str = this.c;
        int hashCode3 = (hashCode2 + (str != null ? str.hashCode() : 0)) * 31;
        kotlin.reflect.jvm.internal.impl.name.a aVar = this.d;
        if (aVar != null) {
            i = aVar.hashCode();
        }
        return hashCode3 + i;
    }

    @NotNull
    public String toString() {
        return "IncompatibleVersionErrorData(actualVersion=" + this.a + ", expectedVersion=" + this.b + ", filePath=" + this.c + ", classId=" + this.d + ")";
    }

    public t(@NotNull T actualVersion, @NotNull T expectedVersion, @NotNull String filePath, @NotNull kotlin.reflect.jvm.internal.impl.name.a classId) {
        k.f(actualVersion, "actualVersion");
        k.f(expectedVersion, "expectedVersion");
        k.f(filePath, Progress.FILE_PATH);
        k.f(classId, "classId");
        this.a = actualVersion;
        this.b = expectedVersion;
        this.c = filePath;
        this.d = classId;
    }
}
