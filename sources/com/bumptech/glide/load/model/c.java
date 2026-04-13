package com.bumptech.glide.load.model;

import android.util.Log;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.d;
import com.bumptech.glide.load.i;
import com.bumptech.glide.util.a;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/* compiled from: ByteBufferEncoder */
public class c implements d<ByteBuffer> {
    /* renamed from: c */
    public boolean a(@NonNull ByteBuffer data, @NonNull File file, @NonNull i options) {
        try {
            a.e(data, file);
            return true;
        } catch (IOException e) {
            if (!Log.isLoggable("ByteBufferEncoder", 3)) {
                return false;
            }
            Log.d("ByteBufferEncoder", "Failed to write data", e);
            return false;
        }
    }
}
