package androidx.core.view;

import android.view.Menu;
import android.view.MenuItem;
import java.util.Iterator;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.sequences.h;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Menu.kt */
public final class MenuKt {
    @NotNull
    public static final MenuItem get(@NotNull Menu $this$get, int index) {
        k.e($this$get, "<this>");
        MenuItem item = $this$get.getItem(index);
        k.d(item, "getItem(index)");
        return item;
    }

    public static final boolean contains(@NotNull Menu $this$contains, @NotNull MenuItem item) {
        k.e($this$contains, "<this>");
        k.e(item, "item");
        int size = $this$contains.size();
        if (size > 0) {
            int i = 0;
            do {
                int index = i;
                i++;
                if (k.a($this$contains.getItem(index), item)) {
                    return true;
                }
            } while (i < size);
        }
        return false;
    }

    public static final void minusAssign(@NotNull Menu $this$minusAssign, @NotNull MenuItem item) {
        k.e($this$minusAssign, "<this>");
        k.e(item, "item");
        $this$minusAssign.removeItem(item.getItemId());
    }

    public static final int getSize(@NotNull Menu $this$size) {
        k.e($this$size, "<this>");
        return $this$size.size();
    }

    public static final boolean isEmpty(@NotNull Menu $this$isEmpty) {
        k.e($this$isEmpty, "<this>");
        return $this$isEmpty.size() == 0;
    }

    public static final boolean isNotEmpty(@NotNull Menu $this$isNotEmpty) {
        k.e($this$isNotEmpty, "<this>");
        return $this$isNotEmpty.size() != 0;
    }

    public static final void forEach(@NotNull Menu $this$forEach, @NotNull l<? super MenuItem, x> action) {
        k.e($this$forEach, "<this>");
        k.e(action, "action");
        int size = $this$forEach.size();
        if (size > 0) {
            int i = 0;
            do {
                int index = i;
                i++;
                MenuItem item = $this$forEach.getItem(index);
                k.d(item, "getItem(index)");
                action.invoke(item);
            } while (i < size);
        }
    }

    public static final void forEachIndexed(@NotNull Menu $this$forEachIndexed, @NotNull p<? super Integer, ? super MenuItem, x> action) {
        k.e($this$forEachIndexed, "<this>");
        k.e(action, "action");
        int size = $this$forEachIndexed.size();
        if (size > 0) {
            int i = 0;
            do {
                int index = i;
                i++;
                Integer valueOf = Integer.valueOf(index);
                MenuItem item = $this$forEachIndexed.getItem(index);
                k.d(item, "getItem(index)");
                action.invoke(valueOf, item);
            } while (i < size);
        }
    }

    @NotNull
    public static final Iterator<MenuItem> iterator(@NotNull Menu $this$iterator) {
        k.e($this$iterator, "<this>");
        return new MenuKt$iterator$1($this$iterator);
    }

    @NotNull
    public static final h<MenuItem> getChildren(@NotNull Menu $this$children) {
        k.e($this$children, "<this>");
        return new MenuKt$children$1($this$children);
    }
}
