package com.didichuxing.doraemonkit.widget.bravh.listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public interface OnItemDragListener {
    boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder2);

    void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int i);

    void onItemDragMoving(RecyclerView.ViewHolder viewHolder, int i, RecyclerView.ViewHolder viewHolder2, int i2);

    void onItemDragStart(RecyclerView.ViewHolder viewHolder, int i);
}
