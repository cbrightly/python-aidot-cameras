package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import com.airbnb.lottie.utils.h;
import java.util.ArrayList;
import java.util.List;

/* compiled from: CompoundTrimPathContent */
public class b {
    private final List<u> a = new ArrayList();

    /* access modifiers changed from: package-private */
    public void a(u trimPath) {
        this.a.add(trimPath);
    }

    public void b(Path path) {
        for (int i = this.a.size() - 1; i >= 0; i--) {
            h.b(path, this.a.get(i));
        }
    }
}
