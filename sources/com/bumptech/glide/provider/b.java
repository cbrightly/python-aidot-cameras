package com.bumptech.glide.provider;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.ImageHeaderParser;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ImageHeaderParserRegistry */
public final class b {
    private final List<ImageHeaderParser> a = new ArrayList();

    @NonNull
    public synchronized List<ImageHeaderParser> b() {
        return this.a;
    }

    public synchronized void a(@NonNull ImageHeaderParser parser) {
        this.a.add(parser);
    }
}
