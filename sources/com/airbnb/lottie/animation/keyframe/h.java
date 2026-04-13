package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import com.airbnb.lottie.model.content.n;
import java.util.ArrayList;
import java.util.List;

/* compiled from: MaskKeyframeAnimation */
public class h {
    private final List<a<n, Path>> a;
    private final List<a<Integer, Integer>> b;
    private final List<com.airbnb.lottie.model.content.h> c;

    public h(List<com.airbnb.lottie.model.content.h> masks) {
        this.c = masks;
        this.a = new ArrayList(masks.size());
        this.b = new ArrayList(masks.size());
        for (int i = 0; i < masks.size(); i++) {
            this.a.add(masks.get(i).b().j());
            this.b.add(masks.get(i).c().j());
        }
    }

    public List<com.airbnb.lottie.model.content.h> b() {
        return this.c;
    }

    public List<a<n, Path>> a() {
        return this.a;
    }

    public List<a<Integer, Integer>> c() {
        return this.b;
    }
}
