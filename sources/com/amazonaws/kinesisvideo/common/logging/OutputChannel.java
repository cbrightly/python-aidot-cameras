package com.amazonaws.kinesisvideo.common.logging;

import androidx.annotation.NonNull;

public interface OutputChannel {
    void print(int i, @NonNull String str, @NonNull String str2);
}
