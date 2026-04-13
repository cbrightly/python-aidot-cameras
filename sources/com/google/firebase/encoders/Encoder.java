package com.google.firebase.encoders;

import androidx.annotation.NonNull;

public interface Encoder<TValue, TContext> {
    void encode(@NonNull TValue tvalue, @NonNull TContext tcontext);
}
