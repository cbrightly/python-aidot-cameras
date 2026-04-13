package com.didichuxing.doraemonkit.widget.bravh.diff;

import androidx.annotation.NonNull;

public interface DifferImp<T> {
    void addListListener(@NonNull ListChangeListener<T> listChangeListener);
}
