package androidx.viewpager2.widget;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

public final class CompositePageTransformer implements ViewPager2.PageTransformer {
    private final List<ViewPager2.PageTransformer> mTransformers = new ArrayList();

    public void addTransformer(@NonNull ViewPager2.PageTransformer transformer) {
        this.mTransformers.add(transformer);
    }

    public void removeTransformer(@NonNull ViewPager2.PageTransformer transformer) {
        this.mTransformers.remove(transformer);
    }

    public void transformPage(@NonNull View page, float position) {
        for (ViewPager2.PageTransformer transformer : this.mTransformers) {
            transformer.transformPage(page, position);
        }
    }
}
