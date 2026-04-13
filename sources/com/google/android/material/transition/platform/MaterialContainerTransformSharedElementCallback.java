package com.google.android.material.transition.platform;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcelable;
import android.transition.Transition;
import android.view.View;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ContextUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

@RequiresApi(21)
public class MaterialContainerTransformSharedElementCallback extends SharedElementCallback {
    /* access modifiers changed from: private */
    @Nullable
    public static WeakReference<View> capturedSharedElement;
    private boolean entering = true;
    @Nullable
    private Rect returnEndBounds;
    @Nullable
    private ShapeProvider shapeProvider = new ShapeableViewShapeProvider();
    private boolean sharedElementReenterTransitionEnabled = false;
    private boolean transparentWindowBackgroundEnabled = true;

    public interface ShapeProvider {
        @Nullable
        ShapeAppearanceModel provideShape(@NonNull View view);
    }

    public static class ShapeableViewShapeProvider implements ShapeProvider {
        @Nullable
        public ShapeAppearanceModel provideShape(@NonNull View sharedElement) {
            if (sharedElement instanceof Shapeable) {
                return ((Shapeable) sharedElement).getShapeAppearanceModel();
            }
            return null;
        }
    }

    @Nullable
    public Parcelable onCaptureSharedElementSnapshot(@NonNull View sharedElement, @NonNull Matrix viewToGlobalMatrix, @NonNull RectF screenBounds) {
        capturedSharedElement = new WeakReference<>(sharedElement);
        return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
    }

    @Nullable
    public View onCreateSnapshotView(@NonNull Context context, @Nullable Parcelable snapshot) {
        WeakReference<View> weakReference;
        View sharedElement;
        ShapeAppearanceModel shapeAppearanceModel;
        View snapshotView = super.onCreateSnapshotView(context, snapshot);
        if (!(snapshotView == null || (weakReference = capturedSharedElement) == null || this.shapeProvider == null || (sharedElement = (View) weakReference.get()) == null || (shapeAppearanceModel = this.shapeProvider.provideShape(sharedElement)) == null)) {
            snapshotView.setTag(R.id.mtrl_motion_snapshot_view, shapeAppearanceModel);
        }
        return snapshotView;
    }

    public void onMapSharedElements(@NonNull List<String> names, @NonNull Map<String, View> sharedElements) {
        View sharedElement;
        Activity activity;
        if (!names.isEmpty() && !sharedElements.isEmpty() && (sharedElement = sharedElements.get(names.get(0))) != null && (activity = ContextUtils.getActivity(sharedElement.getContext())) != null) {
            Window window = activity.getWindow();
            if (this.entering) {
                setUpEnterTransform(window);
            } else {
                setUpReturnTransform(activity, window);
            }
        }
    }

    public void onSharedElementStart(@NonNull List<String> list, @NonNull List<View> sharedElements, @NonNull List<View> sharedElementSnapshots) {
        if (!sharedElements.isEmpty() && !sharedElementSnapshots.isEmpty()) {
            sharedElements.get(0).setTag(R.id.mtrl_motion_snapshot_view, sharedElementSnapshots.get(0));
        }
        if (!this.entering && !sharedElements.isEmpty() && this.returnEndBounds != null) {
            View sharedElement = sharedElements.get(0);
            sharedElement.measure(View.MeasureSpec.makeMeasureSpec(this.returnEndBounds.width(), 1073741824), View.MeasureSpec.makeMeasureSpec(this.returnEndBounds.height(), 1073741824));
            Rect rect = this.returnEndBounds;
            sharedElement.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    public void onSharedElementEnd(@NonNull List<String> list, @NonNull List<View> sharedElements, @NonNull List<View> list2) {
        if (!sharedElements.isEmpty()) {
            int i = R.id.mtrl_motion_snapshot_view;
            if (sharedElements.get(0).getTag(i) instanceof View) {
                sharedElements.get(0).setTag(i, (Object) null);
            }
        }
        if (!this.entering && !sharedElements.isEmpty()) {
            this.returnEndBounds = TransitionUtils.getRelativeBoundsRect(sharedElements.get(0));
        }
        this.entering = false;
    }

    @Nullable
    public ShapeProvider getShapeProvider() {
        return this.shapeProvider;
    }

    public void setShapeProvider(@Nullable ShapeProvider shapeProvider2) {
        this.shapeProvider = shapeProvider2;
    }

    public boolean isTransparentWindowBackgroundEnabled() {
        return this.transparentWindowBackgroundEnabled;
    }

    public void setTransparentWindowBackgroundEnabled(boolean transparentWindowBackgroundEnabled2) {
        this.transparentWindowBackgroundEnabled = transparentWindowBackgroundEnabled2;
    }

    public boolean isSharedElementReenterTransitionEnabled() {
        return this.sharedElementReenterTransitionEnabled;
    }

    public void setSharedElementReenterTransitionEnabled(boolean sharedElementReenterTransitionEnabled2) {
        this.sharedElementReenterTransitionEnabled = sharedElementReenterTransitionEnabled2;
    }

    private void setUpEnterTransform(final Window window) {
        Transition transition = window.getSharedElementEnterTransition();
        if (transition instanceof MaterialContainerTransform) {
            MaterialContainerTransform transform = (MaterialContainerTransform) transition;
            if (!this.sharedElementReenterTransitionEnabled) {
                window.setSharedElementReenterTransition((Transition) null);
            }
            if (this.transparentWindowBackgroundEnabled) {
                updateBackgroundFadeDuration(window, transform);
                transform.addListener(new TransitionListenerAdapter() {
                    public void onTransitionStart(Transition transition) {
                        MaterialContainerTransformSharedElementCallback.removeWindowBackground(window);
                    }

                    public void onTransitionEnd(Transition transition) {
                        MaterialContainerTransformSharedElementCallback.restoreWindowBackground(window);
                    }
                });
            }
        }
    }

    private void setUpReturnTransform(final Activity activity, final Window window) {
        Transition transition = window.getSharedElementReturnTransition();
        if (transition instanceof MaterialContainerTransform) {
            MaterialContainerTransform transform = (MaterialContainerTransform) transition;
            transform.setHoldAtEndEnabled(true);
            transform.addListener(new TransitionListenerAdapter() {
                public void onTransitionEnd(Transition transition) {
                    View sharedElement;
                    if (!(MaterialContainerTransformSharedElementCallback.capturedSharedElement == null || (sharedElement = (View) MaterialContainerTransformSharedElementCallback.capturedSharedElement.get()) == null)) {
                        sharedElement.setAlpha(1.0f);
                        WeakReference unused = MaterialContainerTransformSharedElementCallback.capturedSharedElement = null;
                    }
                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                }
            });
            if (this.transparentWindowBackgroundEnabled) {
                updateBackgroundFadeDuration(window, transform);
                transform.addListener(new TransitionListenerAdapter() {
                    public void onTransitionStart(Transition transition) {
                        MaterialContainerTransformSharedElementCallback.removeWindowBackground(window);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public static void removeWindowBackground(Window window) {
        window.getDecorView().getBackground().mutate().setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(0, BlendModeCompat.CLEAR));
    }

    /* access modifiers changed from: private */
    public static void restoreWindowBackground(Window window) {
        window.getDecorView().getBackground().mutate().clearColorFilter();
    }

    private static void updateBackgroundFadeDuration(Window window, MaterialContainerTransform transform) {
        if (transform.getDuration() >= 0) {
            window.setTransitionBackgroundFadeDuration(transform.getDuration());
        }
    }
}
