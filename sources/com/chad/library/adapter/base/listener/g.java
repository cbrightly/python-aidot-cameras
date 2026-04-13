package com.chad.library.adapter.base.listener;

import android.graphics.Canvas;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: OnItemSwipeListener */
public interface g {
    void clearView(RecyclerView.ViewHolder viewHolder, int i);

    void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float f, float f2, boolean z);

    void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int i);

    void onItemSwiped(RecyclerView.ViewHolder viewHolder, int i);
}
