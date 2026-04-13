package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import java.util.Iterator;
import kotlin.coroutines.d;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.sequences.h;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: ViewGroup.kt */
public final class ViewGroupKt {
    @NotNull
    public static final View get(@NotNull ViewGroup $this$get, int index) {
        k.e($this$get, "<this>");
        View childAt = $this$get.getChildAt(index);
        if (childAt != null) {
            return childAt;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + $this$get.getChildCount());
    }

    public static final boolean contains(@NotNull ViewGroup $this$contains, @NotNull View view) {
        k.e($this$contains, "<this>");
        k.e(view, "view");
        return $this$contains.indexOfChild(view) != -1;
    }

    public static final void plusAssign(@NotNull ViewGroup $this$plusAssign, @NotNull View view) {
        k.e($this$plusAssign, "<this>");
        k.e(view, "view");
        $this$plusAssign.addView(view);
    }

    public static final void minusAssign(@NotNull ViewGroup $this$minusAssign, @NotNull View view) {
        k.e($this$minusAssign, "<this>");
        k.e(view, "view");
        $this$minusAssign.removeView(view);
    }

    public static final int getSize(@NotNull ViewGroup $this$size) {
        k.e($this$size, "<this>");
        return $this$size.getChildCount();
    }

    public static final boolean isEmpty(@NotNull ViewGroup $this$isEmpty) {
        k.e($this$isEmpty, "<this>");
        return $this$isEmpty.getChildCount() == 0;
    }

    public static final boolean isNotEmpty(@NotNull ViewGroup $this$isNotEmpty) {
        k.e($this$isNotEmpty, "<this>");
        return $this$isNotEmpty.getChildCount() != 0;
    }

    public static final void forEach(@NotNull ViewGroup $this$forEach, @NotNull l<? super View, x> action) {
        k.e($this$forEach, "<this>");
        k.e(action, "action");
        int childCount = $this$forEach.getChildCount();
        if (childCount > 0) {
            int i = 0;
            do {
                int index = i;
                i++;
                View childAt = $this$forEach.getChildAt(index);
                k.d(childAt, "getChildAt(index)");
                action.invoke(childAt);
            } while (i < childCount);
        }
    }

    public static final void forEachIndexed(@NotNull ViewGroup $this$forEachIndexed, @NotNull p<? super Integer, ? super View, x> action) {
        k.e($this$forEachIndexed, "<this>");
        k.e(action, "action");
        int childCount = $this$forEachIndexed.getChildCount();
        if (childCount > 0) {
            int i = 0;
            do {
                int index = i;
                i++;
                Integer valueOf = Integer.valueOf(index);
                View childAt = $this$forEachIndexed.getChildAt(index);
                k.d(childAt, "getChildAt(index)");
                action.invoke(valueOf, childAt);
            } while (i < childCount);
        }
    }

    @NotNull
    public static final Iterator<View> iterator(@NotNull ViewGroup $this$iterator) {
        k.e($this$iterator, "<this>");
        return new ViewGroupKt$iterator$1($this$iterator);
    }

    @NotNull
    public static final h<View> getChildren(@NotNull ViewGroup $this$children) {
        k.e($this$children, "<this>");
        return new ViewGroupKt$children$1($this$children);
    }

    @NotNull
    public static final h<View> getDescendants(@NotNull ViewGroup $this$descendants) {
        k.e($this$descendants, "<this>");
        return kotlin.sequences.k.b(new ViewGroupKt$descendants$1($this$descendants, (d<? super ViewGroupKt$descendants$1>) null));
    }

    public static final void setMargins(@NotNull ViewGroup.MarginLayoutParams $this$setMargins, @Px int size) {
        k.e($this$setMargins, "<this>");
        $this$setMargins.setMargins(size, size, size, size);
    }

    public static /* synthetic */ void updateMargins$default(ViewGroup.MarginLayoutParams $this$updateMargins_u24default, int left, int top, int right, int bottom, int i, Object obj) {
        if ((i & 1) != 0) {
            left = $this$updateMargins_u24default.leftMargin;
        }
        if ((i & 2) != 0) {
            top = $this$updateMargins_u24default.topMargin;
        }
        if ((i & 4) != 0) {
            right = $this$updateMargins_u24default.rightMargin;
        }
        if ((i & 8) != 0) {
            bottom = $this$updateMargins_u24default.bottomMargin;
        }
        k.e($this$updateMargins_u24default, "<this>");
        $this$updateMargins_u24default.setMargins(left, top, right, bottom);
    }

    public static final void updateMargins(@NotNull ViewGroup.MarginLayoutParams $this$updateMargins, @Px int left, @Px int top, @Px int right, @Px int bottom) {
        k.e($this$updateMargins, "<this>");
        $this$updateMargins.setMargins(left, top, right, bottom);
    }

    public static /* synthetic */ void updateMarginsRelative$default(ViewGroup.MarginLayoutParams $this$updateMarginsRelative_u24default, int start, int top, int end, int bottom, int i, Object obj) {
        if ((i & 1) != 0) {
            start = $this$updateMarginsRelative_u24default.getMarginStart();
        }
        if ((i & 2) != 0) {
            top = $this$updateMarginsRelative_u24default.topMargin;
        }
        if ((i & 4) != 0) {
            end = $this$updateMarginsRelative_u24default.getMarginEnd();
        }
        if ((i & 8) != 0) {
            bottom = $this$updateMarginsRelative_u24default.bottomMargin;
        }
        k.e($this$updateMarginsRelative_u24default, "<this>");
        $this$updateMarginsRelative_u24default.setMarginStart(start);
        $this$updateMarginsRelative_u24default.topMargin = top;
        $this$updateMarginsRelative_u24default.setMarginEnd(end);
        $this$updateMarginsRelative_u24default.bottomMargin = bottom;
    }

    @RequiresApi(17)
    public static final void updateMarginsRelative(@NotNull ViewGroup.MarginLayoutParams $this$updateMarginsRelative, @Px int start, @Px int top, @Px int end, @Px int bottom) {
        k.e($this$updateMarginsRelative, "<this>");
        $this$updateMarginsRelative.setMarginStart(start);
        $this$updateMarginsRelative.topMargin = top;
        $this$updateMarginsRelative.setMarginEnd(end);
        $this$updateMarginsRelative.bottomMargin = bottom;
    }
}
