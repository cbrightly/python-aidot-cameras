package com.google.android.material.datepicker;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public abstract class OnSelectionChangedListener<S> {
    public abstract void onSelectionChanged(@NonNull S s);

    public void onIncompleteSelectionChanged() {
    }
}
