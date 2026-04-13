package com.didichuxing.doraemonkit.widget.bravh.dragswipe;

import android.graphics.Canvas;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.widget.bravh.module.BaseDraggableModule;

public class DragAndSwipeCallback extends ItemTouchHelper.Callback {
    private static final String TAG = "DragAndSwipeCallback";
    private int mDragMoveFlags = 15;
    private BaseDraggableModule mDraggableModule;
    private float mMoveThreshold = 0.1f;
    private int mSwipeMoveFlags = 32;
    private float mSwipeThreshold = 0.7f;

    public DragAndSwipeCallback(BaseDraggableModule draggableModule) {
        this.mDraggableModule = draggableModule;
    }

    public boolean isLongPressDragEnabled() {
        BaseDraggableModule baseDraggableModule = this.mDraggableModule;
        if (baseDraggableModule == null || !baseDraggableModule.isDragEnabled() || this.mDraggableModule.hasToggleView()) {
            return false;
        }
        return true;
    }

    public boolean isItemViewSwipeEnabled() {
        BaseDraggableModule baseDraggableModule = this.mDraggableModule;
        if (baseDraggableModule != null) {
            return baseDraggableModule.isSwipeEnabled();
        }
        return false;
    }

    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState == 2 && !isViewCreateByAdapter(viewHolder)) {
            BaseDraggableModule baseDraggableModule = this.mDraggableModule;
            if (baseDraggableModule != null) {
                baseDraggableModule.onItemDragStart(viewHolder);
            }
            viewHolder.itemView.setTag(R.id.dokit_baseQuickAdapter_dragging_support, true);
        } else if (actionState == 1 && !isViewCreateByAdapter(viewHolder)) {
            BaseDraggableModule baseDraggableModule2 = this.mDraggableModule;
            if (baseDraggableModule2 != null) {
                baseDraggableModule2.onItemSwipeStart(viewHolder);
            }
            viewHolder.itemView.setTag(R.id.dokit_baseQuickAdapter_swiping_support, true);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (!isViewCreateByAdapter(viewHolder)) {
            View view = viewHolder.itemView;
            int i = R.id.dokit_baseQuickAdapter_dragging_support;
            if (view.getTag(i) != null && ((Boolean) viewHolder.itemView.getTag(i)).booleanValue()) {
                BaseDraggableModule baseDraggableModule = this.mDraggableModule;
                if (baseDraggableModule != null) {
                    baseDraggableModule.onItemDragEnd(viewHolder);
                }
                viewHolder.itemView.setTag(i, false);
            }
            View view2 = viewHolder.itemView;
            int i2 = R.id.dokit_baseQuickAdapter_swiping_support;
            if (view2.getTag(i2) != null && ((Boolean) viewHolder.itemView.getTag(i2)).booleanValue()) {
                BaseDraggableModule baseDraggableModule2 = this.mDraggableModule;
                if (baseDraggableModule2 != null) {
                    baseDraggableModule2.onItemSwipeClear(viewHolder);
                }
                viewHolder.itemView.setTag(i2, false);
            }
        }
    }

    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.getItemViewType() == 999) {
            return ItemTouchHelper.Callback.makeMovementFlags(0, 0);
        }
        if (isViewCreateByAdapter(viewHolder)) {
            return ItemTouchHelper.Callback.makeMovementFlags(0, 0);
        }
        return ItemTouchHelper.Callback.makeMovementFlags(this.mDragMoveFlags, this.mSwipeMoveFlags);
    }

    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) {
        return source.getItemViewType() == target.getItemViewType();
    }

    public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder current, @NonNull RecyclerView.ViewHolder target) {
        return this.mDraggableModule.canDropOver(recyclerView, current, target);
    }

    public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, source, fromPos, target, toPos, x, y);
        BaseDraggableModule baseDraggableModule = this.mDraggableModule;
        if (baseDraggableModule != null) {
            baseDraggableModule.onItemDragMoving(source, target);
        }
    }

    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        BaseDraggableModule baseDraggableModule;
        if (!isViewCreateByAdapter(viewHolder) && (baseDraggableModule = this.mDraggableModule) != null) {
            baseDraggableModule.onItemSwiped(viewHolder);
        }
    }

    public float getMoveThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return this.mMoveThreshold;
    }

    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return this.mSwipeThreshold;
    }

    public void setSwipeThreshold(float swipeThreshold) {
        this.mSwipeThreshold = swipeThreshold;
    }

    public void setMoveThreshold(float moveThreshold) {
        this.mMoveThreshold = moveThreshold;
    }

    public void setDragMoveFlags(int dragMoveFlags) {
        this.mDragMoveFlags = dragMoveFlags;
    }

    public void setSwipeMoveFlags(int swipeMoveFlags) {
        this.mSwipeMoveFlags = swipeMoveFlags;
    }

    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        Canvas canvas = c;
        RecyclerView.ViewHolder viewHolder2 = viewHolder;
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == 1 && !isViewCreateByAdapter(viewHolder)) {
            View itemView = viewHolder2.itemView;
            c.save();
            if (dX > 0.0f) {
                c.clipRect((float) itemView.getLeft(), (float) itemView.getTop(), ((float) itemView.getLeft()) + dX, (float) itemView.getBottom());
                c.translate((float) itemView.getLeft(), (float) itemView.getTop());
            } else {
                c.clipRect(((float) itemView.getRight()) + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                c.translate(((float) itemView.getRight()) + dX, (float) itemView.getTop());
            }
            BaseDraggableModule baseDraggableModule = this.mDraggableModule;
            if (baseDraggableModule != null) {
                baseDraggableModule.onItemSwiping(c, viewHolder, dX, dY, isCurrentlyActive);
            }
            c.restore();
        }
    }

    private boolean isViewCreateByAdapter(@NonNull RecyclerView.ViewHolder viewHolder) {
        int type = viewHolder.getItemViewType();
        return type == 268435729 || type == 268436002 || type == 268436275 || type == 268436821;
    }
}
