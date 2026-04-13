package com.didichuxing.doraemonkit.kit.layoutborder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.LayoutBorderConfig;
import com.didichuxing.doraemonkit.kit.core.DokitViewInterface;
import java.util.ArrayList;
import java.util.List;

public class ViewBorderFrameLayout extends FrameLayout {
    private static final String TAG = "ViewBorderFrameLayout";

    public ViewBorderFrameLayout(@NonNull Context context) {
        super(context);
        setId(R.id.dokit_view_border_id);
    }

    public ViewBorderFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setId(R.id.dokit_view_border_id);
    }

    public ViewBorderFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setId(R.id.dokit_view_border_id);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (LayoutBorderConfig.isLayoutBorderOpen()) {
            traverseChild(this);
        } else {
            clearChild(this);
        }
    }

    private void traverseChild(View view) {
        if (!(view instanceof ViewGroup) || (view instanceof DokitViewInterface)) {
            replaceDrawable(view);
            return;
        }
        replaceDrawable(view);
        int childCount = ((ViewGroup) view).getChildCount();
        if (childCount != 0) {
            for (int index = 0; index < childCount; index++) {
                traverseChild(((ViewGroup) view).getChildAt(index));
            }
        }
    }

    private void replaceDrawable(View view) {
        LayerDrawable newDrawable;
        if (!(view instanceof TextureView)) {
            if (view.getBackground() != null) {
                Drawable oldDrawable = view.getBackground();
                if (oldDrawable instanceof LayerDrawable) {
                    int i = 0;
                    while (i < ((LayerDrawable) oldDrawable).getNumberOfLayers()) {
                        if (!(((LayerDrawable) oldDrawable).getDrawable(i) instanceof ViewBorderDrawable)) {
                            i++;
                        } else {
                            return;
                        }
                    }
                    newDrawable = new LayerDrawable(new Drawable[]{oldDrawable, new ViewBorderDrawable(view)});
                } else {
                    newDrawable = new LayerDrawable(new Drawable[]{oldDrawable, new ViewBorderDrawable(view)});
                }
            } else {
                newDrawable = new LayerDrawable(new Drawable[]{new ViewBorderDrawable(view)});
            }
            try {
                view.setBackground(newDrawable);
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearChild(View view) {
        if (view instanceof ViewGroup) {
            clearDrawable(view);
            int childCount = ((ViewGroup) view).getChildCount();
            if (childCount != 0) {
                for (int index = 0; index < childCount; index++) {
                    clearChild(((ViewGroup) view).getChildAt(index));
                }
                return;
            }
            return;
        }
        clearDrawable(view);
    }

    private void clearDrawable(View view) {
        if (view.getBackground() != null) {
            Drawable oldDrawable = view.getBackground();
            if (oldDrawable instanceof LayerDrawable) {
                LayerDrawable layerDrawable = (LayerDrawable) oldDrawable;
                List<Drawable> drawables = new ArrayList<>();
                for (int i = 0; i < layerDrawable.getNumberOfLayers(); i++) {
                    if (!(layerDrawable.getDrawable(i) instanceof ViewBorderDrawable)) {
                        drawables.add(layerDrawable.getDrawable(i));
                    }
                }
                view.setBackground(new LayerDrawable((Drawable[]) drawables.toArray(new Drawable[drawables.size()])));
            }
        }
    }
}
