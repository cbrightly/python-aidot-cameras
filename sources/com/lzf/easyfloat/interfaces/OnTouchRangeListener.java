package com.lzf.easyfloat.interfaces;

import com.lzf.easyfloat.widget.BaseSwitchView;
import org.jetbrains.annotations.NotNull;

/* compiled from: OnTouchRangeListener.kt */
public interface OnTouchRangeListener {
    void touchInRange(boolean z, @NotNull BaseSwitchView baseSwitchView);

    void touchUpInRange();
}
