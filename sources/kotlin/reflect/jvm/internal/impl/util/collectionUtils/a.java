package kotlin.reflect.jvm.internal.impl.util.collectionUtils;

import java.util.Collection;
import java.util.LinkedHashSet;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: scopeUtils.kt */
public final class a {
    @Nullable
    public static final <T> Collection<T> a(@Nullable Collection<? extends T> $this$concat, @NotNull Collection<? extends T> collection) {
        k.f(collection, "collection");
        if (collection.isEmpty()) {
            return $this$concat;
        }
        if ($this$concat == null) {
            return collection;
        }
        if ($this$concat instanceof LinkedHashSet) {
            ((LinkedHashSet) $this$concat).addAll(collection);
            return $this$concat;
        }
        LinkedHashSet result = new LinkedHashSet($this$concat);
        result.addAll(collection);
        return result;
    }
}
