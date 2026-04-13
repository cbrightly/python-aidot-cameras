package androidx.core.view;

import android.view.Menu;
import android.view.MenuItem;
import java.util.Iterator;
import kotlin.jvm.internal.markers.a;
import org.jetbrains.annotations.NotNull;

/* compiled from: Menu.kt */
public final class MenuKt$iterator$1 implements Iterator<MenuItem>, a {
    final /* synthetic */ Menu $this_iterator;
    private int index;

    MenuKt$iterator$1(Menu $receiver) {
        this.$this_iterator = $receiver;
    }

    public boolean hasNext() {
        return this.index < this.$this_iterator.size();
    }

    @NotNull
    public MenuItem next() {
        Menu menu = this.$this_iterator;
        int i = this.index;
        this.index = i + 1;
        MenuItem item = menu.getItem(i);
        if (item != null) {
            return item;
        }
        throw new IndexOutOfBoundsException();
    }

    public void remove() {
        Menu menu = this.$this_iterator;
        int i = this.index - 1;
        this.index = i;
        menu.removeItem(i);
    }
}
