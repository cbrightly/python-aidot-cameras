package com.google.android.material.internal;

import android.text.Editable;
import android.text.TextWatcher;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class TextWatcherAdapter implements TextWatcher {
    public void beforeTextChanged(@NonNull CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(@NonNull CharSequence s, int start, int before, int count) {
    }

    public void afterTextChanged(@NonNull Editable s) {
    }
}
