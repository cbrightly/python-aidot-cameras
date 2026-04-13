package com.google.android.material.transition.platform;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.transition.PathMotion;
import android.transition.PatternPathMotion;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.PathParser;
import androidx.core.view.animation.PathInterpolatorCompat;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;

@RequiresApi(21)
public class TransitionUtils {
    private static final String EASING_TYPE_CUBIC_BEZIER = "cubic-bezier";
    private static final String EASING_TYPE_FORMAT_END = ")";
    private static final String EASING_TYPE_FORMAT_START = "(";
    private static final String EASING_TYPE_PATH = "path";
    @AttrRes
    static final int NO_ATTR_RES_ID = 0;
    static final int NO_DURATION = -1;
    private static final int PATH_TYPE_ARC = 1;
    private static final int PATH_TYPE_LINEAR = 0;
    private static final RectF transformAlphaRectF = new RectF();

    public interface CanvasOperation {
        void run(Canvas canvas);
    }

    public interface CornerSizeBinaryOperator {
        @NonNull
        CornerSize apply(@NonNull CornerSize cornerSize, @NonNull CornerSize cornerSize2);
    }

    private TransitionUtils() {
    }

    static boolean maybeApplyThemeInterpolator(Transition transition, Context context, @AttrRes int attrResId, TimeInterpolator defaultInterpolator) {
        if (attrResId == 0 || transition.getInterpolator() != null) {
            return false;
        }
        transition.setInterpolator(resolveThemeInterpolator(context, attrResId, defaultInterpolator));
        return true;
    }

    static boolean maybeApplyThemeDuration(Transition transition, Context context, @AttrRes int attrResId) {
        int duration;
        if (attrResId == 0 || transition.getDuration() != -1 || (duration = MaterialAttributes.resolveInteger(context, attrResId, -1)) == -1) {
            return false;
        }
        transition.setDuration((long) duration);
        return true;
    }

    static boolean maybeApplyThemePath(Transition transition, Context context, @AttrRes int attrResId) {
        PathMotion pathMotion;
        if (attrResId == 0 || (pathMotion = resolveThemePath(context, attrResId)) == null) {
            return false;
        }
        transition.setPathMotion(pathMotion);
        return true;
    }

    static TimeInterpolator resolveThemeInterpolator(Context context, @AttrRes int attrResId, @NonNull TimeInterpolator defaultInterpolator) {
        TypedValue easingValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(attrResId, easingValue, true)) {
            return defaultInterpolator;
        }
        if (easingValue.type == 3) {
            String easingString = String.valueOf(easingValue.string);
            if (isEasingType(easingString, EASING_TYPE_CUBIC_BEZIER)) {
                String[] controlPoints = getEasingContent(easingString, EASING_TYPE_CUBIC_BEZIER).split(",");
                if (controlPoints.length == 4) {
                    return PathInterpolatorCompat.create(getControlPoint(controlPoints, 0), getControlPoint(controlPoints, 1), getControlPoint(controlPoints, 2), getControlPoint(controlPoints, 3));
                }
                throw new IllegalArgumentException("Motion easing theme attribute must have 4 control points if using bezier curve format; instead got: " + controlPoints.length);
            } else if (isEasingType(easingString, EASING_TYPE_PATH)) {
                return PathInterpolatorCompat.create(PathParser.createPathFromPathData(getEasingContent(easingString, EASING_TYPE_PATH)));
            } else {
                throw new IllegalArgumentException("Invalid motion easing type: " + easingString);
            }
        } else {
            throw new IllegalArgumentException("Motion easing theme attribute must be a string");
        }
    }

    private static boolean isEasingType(String easingString, String easingType) {
        StringBuilder sb = new StringBuilder();
        sb.append(easingType);
        sb.append(EASING_TYPE_FORMAT_START);
        return easingString.startsWith(sb.toString()) && easingString.endsWith(EASING_TYPE_FORMAT_END);
    }

    private static String getEasingContent(String easingString, String easingType) {
        return easingString.substring(easingType.length() + EASING_TYPE_FORMAT_START.length(), easingString.length() - EASING_TYPE_FORMAT_END.length());
    }

    private static float getControlPoint(String[] controlPoints, int index) {
        float controlPoint = Float.parseFloat(controlPoints[index]);
        if (controlPoint >= 0.0f && controlPoint <= 1.0f) {
            return controlPoint;
        }
        throw new IllegalArgumentException("Motion easing control point value must be between 0 and 1; instead got: " + controlPoint);
    }

    @Nullable
    static PathMotion resolveThemePath(Context context, @AttrRes int attrResId) {
        TypedValue pathValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(attrResId, pathValue, true)) {
            return null;
        }
        int i = pathValue.type;
        if (i == 16) {
            int pathInt = pathValue.data;
            if (pathInt == 0) {
                return null;
            }
            if (pathInt == 1) {
                return new MaterialArcMotion();
            }
            throw new IllegalArgumentException("Invalid motion path type: " + pathInt);
        } else if (i == 3) {
            return new PatternPathMotion(PathParser.createPathFromPathData(String.valueOf(pathValue.string)));
        } else {
            throw new IllegalArgumentException("Motion path theme attribute must either be an enum value or path data string");
        }
    }

    static ShapeAppearanceModel convertToRelativeCornerSizes(ShapeAppearanceModel shapeAppearanceModel, final RectF bounds) {
        return shapeAppearanceModel.withTransformedCornerSizes(new ShapeAppearanceModel.CornerSizeUnaryOperator() {
            @NonNull
            public CornerSize apply(@NonNull CornerSize cornerSize) {
                if (cornerSize instanceof RelativeCornerSize) {
                    return cornerSize;
                }
                return new RelativeCornerSize(cornerSize.getCornerSize(bounds) / bounds.height());
            }
        });
    }

    static ShapeAppearanceModel transformCornerSizes(ShapeAppearanceModel shapeAppearanceModel1, ShapeAppearanceModel shapeAppearanceModel2, RectF shapeAppearanceModel1Bounds, CornerSizeBinaryOperator op) {
        return (isShapeAppearanceSignificant(shapeAppearanceModel1, shapeAppearanceModel1Bounds) ? shapeAppearanceModel1 : shapeAppearanceModel2).toBuilder().setTopLeftCornerSize(op.apply(shapeAppearanceModel1.getTopLeftCornerSize(), shapeAppearanceModel2.getTopLeftCornerSize())).setTopRightCornerSize(op.apply(shapeAppearanceModel1.getTopRightCornerSize(), shapeAppearanceModel2.getTopRightCornerSize())).setBottomLeftCornerSize(op.apply(shapeAppearanceModel1.getBottomLeftCornerSize(), shapeAppearanceModel2.getBottomLeftCornerSize())).setBottomRightCornerSize(op.apply(shapeAppearanceModel1.getBottomRightCornerSize(), shapeAppearanceModel2.getBottomRightCornerSize())).build();
    }

    private static boolean isShapeAppearanceSignificant(ShapeAppearanceModel shapeAppearanceModel, RectF bounds) {
        return (shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(bounds) == 0.0f && shapeAppearanceModel.getTopRightCornerSize().getCornerSize(bounds) == 0.0f && shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(bounds) == 0.0f && shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(bounds) == 0.0f) ? false : true;
    }

    static float lerp(float startValue, float endValue, float fraction) {
        return ((endValue - startValue) * fraction) + startValue;
    }

    static float lerp(float startValue, float endValue, @FloatRange(from = 0.0d, to = 1.0d) float startFraction, @FloatRange(from = 0.0d, to = 1.0d) float endFraction, @FloatRange(from = 0.0d, to = 1.0d) float fraction) {
        return lerp(startValue, endValue, startFraction, endFraction, fraction, false);
    }

    static float lerp(float startValue, float endValue, @FloatRange(from = 0.0d, to = 1.0d) float startFraction, @FloatRange(from = 0.0d, to = 1.0d) float endFraction, @FloatRange(from = 0.0d) float fraction, boolean allowOvershoot) {
        if (allowOvershoot && (fraction < 0.0f || fraction > 1.0f)) {
            return lerp(startValue, endValue, fraction);
        }
        if (fraction < startFraction) {
            return startValue;
        }
        if (fraction > endFraction) {
            return endValue;
        }
        return lerp(startValue, endValue, (fraction - startFraction) / (endFraction - startFraction));
    }

    static int lerp(int startValue, int endValue, @FloatRange(from = 0.0d, to = 1.0d) float startFraction, @FloatRange(from = 0.0d, to = 1.0d) float endFraction, @FloatRange(from = 0.0d, to = 1.0d) float fraction) {
        if (fraction < startFraction) {
            return startValue;
        }
        if (fraction > endFraction) {
            return endValue;
        }
        return (int) lerp((float) startValue, (float) endValue, (fraction - startFraction) / (endFraction - startFraction));
    }

    static ShapeAppearanceModel lerp(ShapeAppearanceModel startValue, ShapeAppearanceModel endValue, RectF startBounds, RectF endBounds, @FloatRange(from = 0.0d, to = 1.0d) float startFraction, @FloatRange(from = 0.0d, to = 1.0d) float endFraction, @FloatRange(from = 0.0d, to = 1.0d) float fraction) {
        if (fraction < startFraction) {
            return startValue;
        }
        if (fraction > endFraction) {
            return endValue;
        }
        final RectF rectF = startBounds;
        final RectF rectF2 = endBounds;
        final float f = startFraction;
        final float f2 = endFraction;
        final float f3 = fraction;
        return transformCornerSizes(startValue, endValue, startBounds, new CornerSizeBinaryOperator() {
            @NonNull
            public CornerSize apply(@NonNull CornerSize cornerSize1, @NonNull CornerSize cornerSize2) {
                return new AbsoluteCornerSize(TransitionUtils.lerp(cornerSize1.getCornerSize(rectF), cornerSize2.getCornerSize(rectF2), f, f2, f3));
            }
        });
    }

    static Shader createColorShader(@ColorInt int color) {
        return new LinearGradient(0.0f, 0.0f, 0.0f, 0.0f, color, color, Shader.TileMode.CLAMP);
    }

    static View findDescendantOrAncestorById(View view, @IdRes int viewId) {
        View descendant = view.findViewById(viewId);
        if (descendant != null) {
            return descendant;
        }
        return findAncestorById(view, viewId);
    }

    /* JADX WARNING: type inference failed for: r1v2, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static android.view.View findAncestorById(android.view.View r4, @androidx.annotation.IdRes int r5) {
        /*
            android.content.res.Resources r0 = r4.getResources()
            java.lang.String r0 = r0.getResourceName(r5)
        L_0x0008:
            if (r4 == 0) goto L_0x001d
            int r1 = r4.getId()
            if (r1 != r5) goto L_0x0011
            return r4
        L_0x0011:
            android.view.ViewParent r1 = r4.getParent()
            boolean r2 = r1 instanceof android.view.View
            if (r2 == 0) goto L_0x001d
            r4 = r1
            android.view.View r4 = (android.view.View) r4
            goto L_0x0008
        L_0x001d:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            java.lang.String r3 = " is not a valid ancestor"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.transition.platform.TransitionUtils.findAncestorById(android.view.View, int):android.view.View");
    }

    static RectF getRelativeBounds(View view) {
        return new RectF((float) view.getLeft(), (float) view.getTop(), (float) view.getRight(), (float) view.getBottom());
    }

    static Rect getRelativeBoundsRect(View view) {
        return new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    static RectF getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        return new RectF((float) left, (float) top, (float) (view.getWidth() + left), (float) (view.getHeight() + top));
    }

    @NonNull
    static <T> T defaultIfNull(@Nullable T value, @NonNull T defaultValue) {
        return value != null ? value : defaultValue;
    }

    static float calculateArea(@NonNull RectF bounds) {
        return bounds.width() * bounds.height();
    }

    private static int saveLayerAlphaCompat(Canvas canvas, Rect bounds, int alpha) {
        RectF rectF = transformAlphaRectF;
        rectF.set(bounds);
        if (Build.VERSION.SDK_INT >= 21) {
            return canvas.saveLayerAlpha(rectF, alpha);
        }
        return canvas.saveLayerAlpha(rectF.left, rectF.top, rectF.right, rectF.bottom, alpha, 31);
    }

    static void transform(Canvas canvas, Rect bounds, float dx, float dy, float scale, int alpha, CanvasOperation op) {
        if (alpha > 0) {
            int checkpoint = canvas.save();
            canvas.translate(dx, dy);
            canvas.scale(scale, scale);
            if (alpha < 255) {
                saveLayerAlphaCompat(canvas, bounds, alpha);
            }
            op.run(canvas);
            canvas.restoreToCount(checkpoint);
        }
    }

    static void maybeAddTransition(TransitionSet transitionSet, @Nullable Transition transition) {
        if (transition != null) {
            transitionSet.addTransition(transition);
        }
    }

    static void maybeRemoveTransition(TransitionSet transitionSet, @Nullable Transition transition) {
        if (transition != null) {
            transitionSet.removeTransition(transition);
        }
    }
}
