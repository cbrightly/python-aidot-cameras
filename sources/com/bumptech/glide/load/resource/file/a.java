package com.bumptech.glide.load.resource.file;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import java.io.File;

/* compiled from: FileDecoder */
public class a implements k<File, File> {
    /* renamed from: d */
    public boolean a(@NonNull File source, @NonNull i options) {
        return true;
    }

    /* renamed from: c */
    public t<File> b(@NonNull File source, int width, int height, @NonNull i options) {
        return new b(source);
    }
}
