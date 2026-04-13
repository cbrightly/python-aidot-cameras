package com.airbnb.lottie;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.model.c;
import com.airbnb.lottie.model.d;
import com.airbnb.lottie.model.h;
import com.airbnb.lottie.model.layer.e;
import com.airbnb.lottie.utils.g;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/* compiled from: LottieComposition */
public class c0 {
    private final n0 a = new n0();
    private final HashSet<String> b = new HashSet<>();
    private Map<String, List<e>> c;
    private Map<String, f0> d;
    private Map<String, c> e;
    private List<h> f;
    private SparseArrayCompat<d> g;
    private LongSparseArray<e> h;
    private List<e> i;
    private Rect j;
    private float k;
    private float l;
    private float m;
    private boolean n;
    private int o = 0;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void s(Rect bounds, float startFrame, float endFrame, float frameRate, List<e> layers, LongSparseArray<e> layerMap, Map<String, List<e>> precomps, Map<String, f0> images, SparseArrayCompat<d> characters, Map<String, c> fonts, List<h> markers) {
        this.j = bounds;
        this.k = startFrame;
        this.l = endFrame;
        this.m = frameRate;
        this.i = layers;
        this.h = layerMap;
        this.c = precomps;
        this.d = images;
        this.g = characters;
        this.e = fonts;
        this.f = markers;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void a(String warning) {
        com.airbnb.lottie.utils.d.c(warning);
        this.b.add(warning);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void u(boolean hasDashPattern) {
        this.n = hasDashPattern;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void r(int amount) {
        this.o += amount;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean q() {
        return this.n;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int m() {
        return this.o;
    }

    public void v(boolean enabled) {
        this.a.b(enabled);
    }

    public n0 n() {
        return this.a;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public e t(long id) {
        return this.h.get(id);
    }

    public Rect b() {
        return this.j;
    }

    public float d() {
        return (float) ((long) ((e() / this.m) * 1000.0f));
    }

    public float p() {
        return this.k;
    }

    public float f() {
        return this.l;
    }

    public float h(float progress) {
        return g.i(this.k, this.l, progress);
    }

    public float i() {
        return this.m;
    }

    public List<e> k() {
        return this.i;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public List<e> o(String id) {
        return this.c.get(id);
    }

    public SparseArrayCompat<d> c() {
        return this.g;
    }

    public Map<String, c> g() {
        return this.e;
    }

    @Nullable
    public h l(String markerName) {
        int size = this.f.size();
        for (int i2 = 0; i2 < size; i2++) {
            h marker = this.f.get(i2);
            if (marker.a(markerName)) {
                return marker;
            }
        }
        return null;
    }

    public Map<String, f0> j() {
        return this.d;
    }

    public float e() {
        return this.l - this.k;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("LottieComposition:\n");
        for (e layer : this.i) {
            sb.append(layer.y("\t"));
        }
        return sb.toString();
    }
}
