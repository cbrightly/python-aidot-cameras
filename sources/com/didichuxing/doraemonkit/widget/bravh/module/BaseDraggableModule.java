package com.didichuxing.doraemonkit.widget.bravh.module;

import android.graphics.Canvas;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter;
import com.didichuxing.doraemonkit.widget.bravh.dragswipe.DragAndSwipeCallback;
import com.didichuxing.doraemonkit.widget.bravh.listener.DraggableListenerImp;
import com.didichuxing.doraemonkit.widget.bravh.listener.OnItemDragListener;
import com.didichuxing.doraemonkit.widget.bravh.listener.OnItemSwipeListener;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import java.util.Collections;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DraggableModule.kt */
public class BaseDraggableModule implements DraggableListenerImp {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private static final int NO_TOGGLE_VIEW = 0;
    private final BaseQuickAdapter<?, ?> baseQuickAdapter;
    private boolean isDragEnabled;
    private boolean isDragOnLongPressEnabled = true;
    private boolean isSwipeEnabled;
    @NotNull
    public ItemTouchHelper itemTouchHelper;
    @NotNull
    public DragAndSwipeCallback itemTouchHelperCallback;
    @Nullable
    private OnItemDragListener mOnItemDragListener;
    @Nullable
    private OnItemSwipeListener mOnItemSwipeListener;
    @Nullable
    private View.OnLongClickListener mOnToggleViewLongClickListener;
    @Nullable
    private View.OnTouchListener mOnToggleViewTouchListener;
    private int toggleViewId;

    public BaseDraggableModule(@NotNull BaseQuickAdapter<?, ?> baseQuickAdapter2) {
        k.f(baseQuickAdapter2, "baseQuickAdapter");
        this.baseQuickAdapter = baseQuickAdapter2;
        initItemTouch();
    }

    public final boolean isDragEnabled() {
        return this.isDragEnabled;
    }

    public final void setDragEnabled(boolean z) {
        this.isDragEnabled = z;
    }

    public final boolean isSwipeEnabled() {
        return this.isSwipeEnabled;
    }

    public final void setSwipeEnabled(boolean z) {
        this.isSwipeEnabled = z;
    }

    public final int getToggleViewId() {
        return this.toggleViewId;
    }

    public final void setToggleViewId(int i) {
        this.toggleViewId = i;
    }

    @NotNull
    public final ItemTouchHelper getItemTouchHelper() {
        ItemTouchHelper itemTouchHelper2 = this.itemTouchHelper;
        if (itemTouchHelper2 == null) {
            k.t("itemTouchHelper");
        }
        return itemTouchHelper2;
    }

    public final void setItemTouchHelper(@NotNull ItemTouchHelper itemTouchHelper2) {
        k.f(itemTouchHelper2, "<set-?>");
        this.itemTouchHelper = itemTouchHelper2;
    }

    @NotNull
    public final DragAndSwipeCallback getItemTouchHelperCallback() {
        DragAndSwipeCallback dragAndSwipeCallback = this.itemTouchHelperCallback;
        if (dragAndSwipeCallback == null) {
            k.t("itemTouchHelperCallback");
        }
        return dragAndSwipeCallback;
    }

    public final void setItemTouchHelperCallback(@NotNull DragAndSwipeCallback dragAndSwipeCallback) {
        k.f(dragAndSwipeCallback, "<set-?>");
        this.itemTouchHelperCallback = dragAndSwipeCallback;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final View.OnTouchListener getMOnToggleViewTouchListener() {
        return this.mOnToggleViewTouchListener;
    }

    /* access modifiers changed from: protected */
    public final void setMOnToggleViewTouchListener(@Nullable View.OnTouchListener onTouchListener) {
        this.mOnToggleViewTouchListener = onTouchListener;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final View.OnLongClickListener getMOnToggleViewLongClickListener() {
        return this.mOnToggleViewLongClickListener;
    }

    /* access modifiers changed from: protected */
    public final void setMOnToggleViewLongClickListener(@Nullable View.OnLongClickListener onLongClickListener) {
        this.mOnToggleViewLongClickListener = onLongClickListener;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final OnItemDragListener getMOnItemDragListener() {
        return this.mOnItemDragListener;
    }

    /* access modifiers changed from: protected */
    public final void setMOnItemDragListener(@Nullable OnItemDragListener onItemDragListener) {
        this.mOnItemDragListener = onItemDragListener;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final OnItemSwipeListener getMOnItemSwipeListener() {
        return this.mOnItemSwipeListener;
    }

    /* access modifiers changed from: protected */
    public final void setMOnItemSwipeListener(@Nullable OnItemSwipeListener onItemSwipeListener) {
        this.mOnItemSwipeListener = onItemSwipeListener;
    }

    private final void initItemTouch() {
        DragAndSwipeCallback dragAndSwipeCallback = new DragAndSwipeCallback(this);
        this.itemTouchHelperCallback = dragAndSwipeCallback;
        if (dragAndSwipeCallback == null) {
            k.t("itemTouchHelperCallback");
        }
        this.itemTouchHelper = new ItemTouchHelper(dragAndSwipeCallback);
    }

    public final void initView$doraemonkit_release(@NotNull BaseViewHolder holder) {
        View toggleView;
        k.f(holder, "holder");
        if (this.isDragEnabled && hasToggleView() && (toggleView = holder.itemView.findViewById(this.toggleViewId)) != null) {
            toggleView.setTag(R.id.dokit_baseQuickAdapter_viewholder_support, holder);
            if (isDragOnLongPressEnabled()) {
                toggleView.setOnLongClickListener(this.mOnToggleViewLongClickListener);
            } else {
                toggleView.setOnTouchListener(this.mOnToggleViewTouchListener);
            }
        }
    }

    public final void attachToRecyclerView(@NotNull RecyclerView recyclerView) {
        k.f(recyclerView, "recyclerView");
        ItemTouchHelper itemTouchHelper2 = this.itemTouchHelper;
        if (itemTouchHelper2 == null) {
            k.t("itemTouchHelper");
        }
        itemTouchHelper2.attachToRecyclerView(recyclerView);
    }

    public boolean hasToggleView() {
        return this.toggleViewId != 0;
    }

    public boolean isDragOnLongPressEnabled() {
        return this.isDragOnLongPressEnabled;
    }

    public void setDragOnLongPressEnabled(boolean value) {
        this.isDragOnLongPressEnabled = value;
        if (value) {
            this.mOnToggleViewTouchListener = null;
            this.mOnToggleViewLongClickListener = new BaseDraggableModule$isDragOnLongPressEnabled$1(this);
            return;
        }
        this.mOnToggleViewTouchListener = new BaseDraggableModule$isDragOnLongPressEnabled$2(this);
        this.mOnToggleViewLongClickListener = null;
    }

    /* access modifiers changed from: protected */
    public final int getViewHolderPosition(@NotNull RecyclerView.ViewHolder viewHolder) {
        k.f(viewHolder, "viewHolder");
        return viewHolder.getAdapterPosition() - this.baseQuickAdapter.getHeaderLayoutCount();
    }

    public void onItemDragStart(@NotNull RecyclerView.ViewHolder viewHolder) {
        k.f(viewHolder, "viewHolder");
        OnItemDragListener onItemDragListener = this.mOnItemDragListener;
        if (onItemDragListener != null) {
            onItemDragListener.onItemDragStart(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    public void onItemDragMoving(@NotNull RecyclerView.ViewHolder source, @NotNull RecyclerView.ViewHolder target) {
        k.f(source, "source");
        k.f(target, TypedValues.AttributesType.S_TARGET);
        int from = getViewHolderPosition(source);
        int to = getViewHolderPosition(target);
        if (inRange(from) && inRange(to)) {
            if (from < to) {
                for (int i = from; i < to; i++) {
                    Collections.swap(this.baseQuickAdapter.getData(), i, i + 1);
                }
            } else {
                int i2 = to + 1;
                if (from >= i2) {
                    int i3 = from;
                    while (true) {
                        Collections.swap(this.baseQuickAdapter.getData(), i3, i3 - 1);
                        if (i3 == i2) {
                            break;
                        }
                        i3--;
                    }
                }
            }
            this.baseQuickAdapter.notifyItemMoved(source.getAdapterPosition(), target.getAdapterPosition());
        }
        OnItemDragListener onItemDragListener = this.mOnItemDragListener;
        if (onItemDragListener != null) {
            onItemDragListener.onItemDragMoving(source, from, target, to);
        }
    }

    public void onItemDragEnd(@NotNull RecyclerView.ViewHolder viewHolder) {
        k.f(viewHolder, "viewHolder");
        OnItemDragListener onItemDragListener = this.mOnItemDragListener;
        if (onItemDragListener != null) {
            onItemDragListener.onItemDragEnd(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    public boolean canDropOver(@NotNull @NonNull RecyclerView recyclerView, @NotNull @NonNull RecyclerView.ViewHolder current, @NotNull @NonNull RecyclerView.ViewHolder target) {
        k.f(recyclerView, "recyclerView");
        k.f(current, "current");
        k.f(target, TypedValues.AttributesType.S_TARGET);
        OnItemDragListener onItemDragListener = this.mOnItemDragListener;
        Boolean valueOf = onItemDragListener != null ? Boolean.valueOf(onItemDragListener.canDropOver(recyclerView, current, target)) : null;
        if (valueOf == null) {
            k.n();
        }
        return valueOf.booleanValue();
    }

    public void onItemSwipeStart(@NotNull RecyclerView.ViewHolder viewHolder) {
        OnItemSwipeListener onItemSwipeListener;
        k.f(viewHolder, "viewHolder");
        if (this.isSwipeEnabled && (onItemSwipeListener = this.mOnItemSwipeListener) != null) {
            onItemSwipeListener.onItemSwipeStart(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    public void onItemSwipeClear(@NotNull RecyclerView.ViewHolder viewHolder) {
        OnItemSwipeListener onItemSwipeListener;
        k.f(viewHolder, "viewHolder");
        if (this.isSwipeEnabled && (onItemSwipeListener = this.mOnItemSwipeListener) != null) {
            onItemSwipeListener.clearView(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    public void onItemSwiped(@NotNull RecyclerView.ViewHolder viewHolder) {
        OnItemSwipeListener onItemSwipeListener;
        k.f(viewHolder, "viewHolder");
        int pos = getViewHolderPosition(viewHolder);
        if (inRange(pos)) {
            this.baseQuickAdapter.getData().remove(pos);
            this.baseQuickAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            if (this.isSwipeEnabled && (onItemSwipeListener = this.mOnItemSwipeListener) != null) {
                onItemSwipeListener.onItemSwiped(viewHolder, pos);
            }
        }
    }

    public void onItemSwiping(@Nullable Canvas canvas, @Nullable RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
        OnItemSwipeListener onItemSwipeListener;
        if (this.isSwipeEnabled && (onItemSwipeListener = this.mOnItemSwipeListener) != null) {
            onItemSwipeListener.onItemSwipeMoving(canvas, viewHolder, dX, dY, isCurrentlyActive);
        }
    }

    private final boolean inRange(int position) {
        return position >= 0 && position < this.baseQuickAdapter.getData().size();
    }

    public void setOnItemDragListener(@Nullable OnItemDragListener onItemDragListener) {
        this.mOnItemDragListener = onItemDragListener;
    }

    public void setOnItemSwipeListener(@Nullable OnItemSwipeListener onItemSwipeListener) {
        this.mOnItemSwipeListener = onItemSwipeListener;
    }

    /* compiled from: DraggableModule.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
