package androidx.core.view;

import android.view.Menu;
import android.view.MenuItem;
import java.util.Iterator;
import kotlin.sequences.h;
import org.jetbrains.annotations.NotNull;

/* compiled from: Menu.kt */
public final class MenuKt$children$1 implements h<MenuItem> {
    final /* synthetic */ Menu $this_children;

    MenuKt$children$1(Menu $receiver) {
        this.$this_children = $receiver;
    }

    @NotNull
    public Iterator<MenuItem> iterator() {
        return MenuKt.iterator(this.$this_children);
    }
}
