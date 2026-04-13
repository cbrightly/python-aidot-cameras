package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.content.s;
import com.airbnb.lottie.model.content.n;
import com.airbnb.lottie.utils.g;
import com.airbnb.lottie.value.a;
import java.util.List;

/* compiled from: ShapeKeyframeAnimation */
public class m extends a<n, Path> {
    private final n i = new n();
    private final Path j = new Path();
    private List<s> k;

    public m(List<a<n>> keyframes) {
        super(keyframes);
    }

    /* renamed from: p */
    public Path i(a<n> keyframe, float keyframeProgress) {
        this.i.c((n) keyframe.b, (n) keyframe.c, keyframeProgress);
        n modifiedShapeData = this.i;
        List<s> list = this.k;
        if (list != null) {
            for (int i2 = list.size() - 1; i2 >= 0; i2--) {
                modifiedShapeData = this.k.get(i2).c(modifiedShapeData);
            }
        }
        g.h(modifiedShapeData, this.j);
        return this.j;
    }

    public void q(@Nullable List<s> shapeModifiers) {
        this.k = shapeModifiers;
    }
}
