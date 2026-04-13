package com.amazonaws.mobileconnectors.kinesisvideo.util;

import android.util.Log;
import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.common.logging.OutputChannel;

public class AndroidLogOutputChannel implements OutputChannel {
    public void print(int level, @NonNull String tag, @NonNull String message) {
        Log.println(level, tag, message);
    }
}
