package com.didichuxing.doraemonkit.widget.bravh.diff;

import androidx.annotation.NonNull;
import java.util.List;

public interface ListChangeListener<T> {
    void onCurrentListChanged(@NonNull List<T> list, @NonNull List<T> list2);
}
