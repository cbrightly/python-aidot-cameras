package com.bumptech.glide.load.data.mediastore;

import java.io.File;

/* compiled from: FileService */
public class a {
    a() {
    }

    public boolean a(File file) {
        return file.exists();
    }

    public long c(File file) {
        return file.length();
    }

    public File b(String path) {
        return new File(path);
    }
}
