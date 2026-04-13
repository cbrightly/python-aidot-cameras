package kotlin.collections.builders;

import java.util.Map;
import java.util.Map.Entry;
import kotlin.collections.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: MapBuilder.kt */
public abstract class a<E extends Map.Entry<? extends K, ? extends V>, K, V> extends g<E> {
    public abstract boolean e(@NotNull Map.Entry<? extends K, ? extends V> entry);

    public abstract /* bridge */ boolean f(Map.Entry entry);

    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof Map.Entry) {
            return b((Map.Entry) obj);
        }
        return false;
    }

    public final /* bridge */ boolean remove(Object obj) {
        if (obj instanceof Map.Entry) {
            return f((Map.Entry) obj);
        }
        return false;
    }

    public final boolean b(@NotNull E element) {
        k.e(element, "element");
        return e(element);
    }
}
