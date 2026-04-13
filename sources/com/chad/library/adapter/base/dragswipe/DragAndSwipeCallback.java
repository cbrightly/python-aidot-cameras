package com.chad.library.adapter.base.dragswipe;

import android.graphics.Canvas;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.R$id;
import com.chad.library.adapter.base.module.a;

public class DragAndSwipeCallback extends ItemTouchHelper.Callback {
    private a a;
    private float b = 0.1f;
    private float c = 0.7f;
    private int d = 15;
    private int e = 32;

    public DragAndSwipeCallback(a draggableModule) {
        this.a = draggableModule;
    }

    public boolean isLongPressDragEnabled() {
        a aVar = this.a;
        if (aVar == null || !aVar.i() || this.a.e()) {
            return false;
        }
        return true;
    }

    public boolean isItemViewSwipeEnabled() {
        a aVar = this.a;
        if (aVar != null) {
            return aVar.k();
        }
        return false;
    }

    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState == 2 && !isViewCreateByAdapter(viewHolder)) {
            a aVar = this.a;
            if (aVar != null) {
                aVar.n(viewHolder);
            }
            viewHolder.itemView.setTag(R$id.BaseQuickAdapter_dragging_support, true);
        } else if (actionState == 1 && !isViewCreateByAdapter(viewHolder)) {
            a aVar2 = this.a;
            if (aVar2 != null) {
                aVar2.p(viewHolder);
            }
            viewHolder.itemView.setTag(R$id.BaseQuickAdapter_swiping_support, true);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (!isViewCreateByAdapter(viewHolder)) {
            View view = viewHolder.itemView;
            int i = R$id.BaseQuickAdapter_dragging_support;
            if (view.getTag(i) != null && ((Boolean) viewHolder.itemView.getTag(i)).booleanValue()) {
                a aVar = this.a;
                if (aVar != null) {
                    aVar.l(viewHolder);
                }
                viewHolder.itemView.setTag(i, false);
            }
            View view2 = viewHolder.itemView;
            int i2 = R$id.BaseQuickAdapter_swiping_support;
            if (view2.getTag(i2) != null && ((Boolean) viewHolder.itemView.getTag(i2)).booleanValue()) {
                a aVar2 = this.a;
                if (aVar2 != null) {
                    aVar2.o(viewHolder);
                }
                viewHolder.itemView.setTag(i2, false);
            }
        }
    }

    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (isViewCreateByAdapter(viewHolder)) {
            return ItemTouchHelper.Callback.makeMovementFlags(0, 0);
        }
        return ItemTouchHelper.Callback.makeMovementFlags(this.d, this.e);
    }

    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) {
        return source.getItemViewType() == target.getItemViewType();
    }

    public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, source, fromPos, target, toPos, x, y);
        a aVar = this.a;
        if (aVar != null) {
            aVar.m(source, target);
        }
    }

    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        a aVar;
        if (!isViewCreateByAdapter(viewHolder) && (aVar = this.a) != null) {
            aVar.q(viewHolder);
        }
    }

    public float getMoveThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return this.b;
    }

    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return this.c;
    }

    public void setDragMoveFlags(int dragMoveFlags) {
        this.d = dragMoveFlags;
    }

    public void setSwipeMoveFlags(int swipeMoveFlags) {
        this.e = swipeMoveFlags;
    }

    public void onChildDrawOver(@NonNull Canvas c2, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        Canvas canvas = c2;
        RecyclerView.ViewHolder viewHolder2 = viewHolder;
        super.onChildDrawOver(c2, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == 1 && !isViewCreateByAdapter(viewHolder)) {
            View itemView = viewHolder2.itemView;
            c2.save();
            if (dX > 0.0f) {
                c2.clipRect((float) itemView.getLeft(), (float) itemView.getTop(), ((float) itemView.getLeft()) + dX, (float) itemView.getBottom());
                c2.translate((float) itemView.getLeft(), (float) itemView.getTop());
            } else {
                c2.clipRect(((float) itemView.getRight()) + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                c2.translate(((float) itemView.getRight()) + dX, (float) itemView.getTop());
            }
            a aVar = this.a;
            if (aVar != null) {
                aVar.r(c2, viewHolder, dX, dY, isCurrentlyActive);
            }
            c2.restore();
        }
    }

    private boolean isViewCreateByAdapter(@NonNull RecyclerView.ViewHolder viewHolder) {
        int type = viewHolder.getItemViewType();
        return type == 268435729 || type == 268436002 || type == 268436275 || type == 268436821;
    }
}
