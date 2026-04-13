package com.airbnb.lottie.model.content;

import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.content.c;
import com.airbnb.lottie.animation.content.l;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.layer.b;
import com.airbnb.lottie.utils.d;

/* compiled from: MergePaths */
public class i implements c {
    private final String a;
    private final a b;
    private final boolean c;

    /* compiled from: MergePaths */
    public enum a {
        MERGE,
        ADD,
        SUBTRACT,
        INTERSECT,
        EXCLUDE_INTERSECTIONS;

        public static a forId(int id) {
            switch (id) {
                case 1:
                    return MERGE;
                case 2:
                    return ADD;
                case 3:
                    return SUBTRACT;
                case 4:
                    return INTERSECT;
                case 5:
                    return EXCLUDE_INTERSECTIONS;
                default:
                    return MERGE;
            }
        }
    }

    public i(String name, a mode, boolean hidden) {
        this.a = name;
        this.b = mode;
        this.c = hidden;
    }

    public String c() {
        return this.a;
    }

    public a b() {
        return this.b;
    }

    public boolean d() {
        return this.c;
    }

    @Nullable
    public c a(e0 drawable, c0 composition, b layer) {
        if (drawable.n()) {
            return new l(this);
        }
        d.c("Animation contains merge paths but they are disabled.");
        return null;
    }

    public String toString() {
        return "MergePaths{mode=" + this.b + '}';
    }
}
