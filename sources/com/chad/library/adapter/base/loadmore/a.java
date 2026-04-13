package com.chad.library.adapter.base.loadmore;

import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import org.jetbrains.annotations.NotNull;

/* compiled from: BaseLoadMoreView.kt */
public abstract class a {
    public abstract void a(@NotNull BaseViewHolder baseViewHolder, int i, @NotNull b bVar);

    @NotNull
    public abstract View b(@NotNull ViewGroup viewGroup);
}
