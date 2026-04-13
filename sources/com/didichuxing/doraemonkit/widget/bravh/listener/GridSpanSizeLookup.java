package com.didichuxing.doraemonkit.widget.bravh.listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

public interface GridSpanSizeLookup {
    int getSpanSize(@NonNull GridLayoutManager gridLayoutManager, int i, int i2);
}
