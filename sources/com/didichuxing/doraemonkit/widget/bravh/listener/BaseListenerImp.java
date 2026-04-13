package com.didichuxing.doraemonkit.widget.bravh.listener;

import androidx.annotation.Nullable;

public interface BaseListenerImp {
    void setGridSpanSizeLookup(@Nullable GridSpanSizeLookup gridSpanSizeLookup);

    void setOnItemChildClickListener(@Nullable OnItemChildClickListener onItemChildClickListener);

    void setOnItemChildLongClickListener(@Nullable OnItemChildLongClickListener onItemChildLongClickListener);

    void setOnItemClickListener(@Nullable OnItemClickListener onItemClickListener);

    void setOnItemLongClickListener(@Nullable OnItemLongClickListener onItemLongClickListener);
}
