package com.amazonaws.kinesisvideo.producer;

import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;

public class Tag {
    private final String mName;
    private final String mValue;

    public Tag(@NonNull String name, @NonNull String value) {
        this.mName = (String) Preconditions.checkNotNull(name);
        this.mValue = (String) Preconditions.checkNotNull(value);
    }

    @NonNull
    public String getName() {
        return this.mName;
    }

    @NonNull
    public String getValue() {
        return this.mValue;
    }
}
