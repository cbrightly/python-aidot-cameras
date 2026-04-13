package com.didichuxing.doraemonkit.widget.bravh.listener;

import android.graphics.Canvas;
import androidx.recyclerview.widget.RecyclerView;

public interface OnItemSwipeListener {
    void clearView(RecyclerView.ViewHolder viewHolder, int i);

    void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float f, float f2, boolean z);

    void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int i);

    void onItemSwiped(RecyclerView.ViewHolder viewHolder, int i);
}
