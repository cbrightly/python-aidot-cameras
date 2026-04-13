package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.protobuf.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ProtoBufUtil.kt */
public final class f {
    @Nullable
    public static final <M extends h.d<M>, T> T a(@NotNull h.d<M> $this$getExtensionOrNull, @NotNull h.f<M, T> extension) {
        k.f($this$getExtensionOrNull, "$this$getExtensionOrNull");
        k.f(extension, "extension");
        if ($this$getExtensionOrNull.hasExtension(extension)) {
            return $this$getExtensionOrNull.getExtension(extension);
        }
        return null;
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [kotlin.reflect.jvm.internal.impl.protobuf.h$f, java.lang.Object, kotlin.reflect.jvm.internal.impl.protobuf.h$f<M, java.util.List<T>>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <M extends kotlin.reflect.jvm.internal.impl.protobuf.h.d<M>, T> T b(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.protobuf.h.d<M> r1, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.protobuf.h.f<M, java.util.List<T>> r2, int r3) {
        /*
            java.lang.String r0 = "$this$getExtensionOrNull"
            kotlin.jvm.internal.k.f(r1, r0)
            java.lang.String r0 = "extension"
            kotlin.jvm.internal.k.f(r2, r0)
            int r0 = r1.getExtensionCount(r2)
            if (r3 >= r0) goto L_0x0015
            java.lang.Object r0 = r1.getExtension(r2, r3)
            goto L_0x0016
        L_0x0015:
            r0 = 0
        L_0x0016:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.deserialization.f.b(kotlin.reflect.jvm.internal.impl.protobuf.h$d, kotlin.reflect.jvm.internal.impl.protobuf.h$f, int):java.lang.Object");
    }
}
