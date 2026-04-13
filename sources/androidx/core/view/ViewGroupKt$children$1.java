package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import kotlin.sequences.h;
import org.jetbrains.annotations.NotNull;

/* compiled from: ViewGroup.kt */
public final class ViewGroupKt$children$1 implements h<View> {
    final /* synthetic */ ViewGroup $this_children;

    ViewGroupKt$children$1(ViewGroup $receiver) {
        this.$this_children = $receiver;
    }

    @NotNull
    public Iterator<View> iterator() {
        return ViewGroupKt.iterator(this.$this_children);
    }
}
