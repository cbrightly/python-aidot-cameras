package com.google.android.material.shape;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import androidx.annotation.AttrRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import com.google.android.material.R;

public class ShapeAppearanceModel {
    public static final CornerSize PILL = new RelativeCornerSize(0.5f);
    EdgeTreatment bottomEdge;
    CornerTreatment bottomLeftCorner;
    CornerSize bottomLeftCornerSize;
    CornerTreatment bottomRightCorner;
    CornerSize bottomRightCornerSize;
    EdgeTreatment leftEdge;
    EdgeTreatment rightEdge;
    EdgeTreatment topEdge;
    CornerTreatment topLeftCorner;
    CornerSize topLeftCornerSize;
    CornerTreatment topRightCorner;
    CornerSize topRightCornerSize;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface CornerSizeUnaryOperator {
        @NonNull
        CornerSize apply(@NonNull CornerSize cornerSize);
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        @NonNull
        public EdgeTreatment bottomEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        /* access modifiers changed from: private */
        @NonNull
        public CornerTreatment bottomLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        /* access modifiers changed from: private */
        @NonNull
        public CornerSize bottomLeftCornerSize = new AbsoluteCornerSize(0.0f);
        /* access modifiers changed from: private */
        @NonNull
        public CornerTreatment bottomRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        /* access modifiers changed from: private */
        @NonNull
        public CornerSize bottomRightCornerSize = new AbsoluteCornerSize(0.0f);
        /* access modifiers changed from: private */
        @NonNull
        public EdgeTreatment leftEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        /* access modifiers changed from: private */
        @NonNull
        public EdgeTreatment rightEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        /* access modifiers changed from: private */
        @NonNull
        public EdgeTreatment topEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        /* access modifiers changed from: private */
        @NonNull
        public CornerTreatment topLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        /* access modifiers changed from: private */
        @NonNull
        public CornerSize topLeftCornerSize = new AbsoluteCornerSize(0.0f);
        /* access modifiers changed from: private */
        @NonNull
        public CornerTreatment topRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        /* access modifiers changed from: private */
        @NonNull
        public CornerSize topRightCornerSize = new AbsoluteCornerSize(0.0f);

        public Builder() {
        }

        public Builder(@NonNull ShapeAppearanceModel other) {
            this.topLeftCorner = other.topLeftCorner;
            this.topRightCorner = other.topRightCorner;
            this.bottomRightCorner = other.bottomRightCorner;
            this.bottomLeftCorner = other.bottomLeftCorner;
            this.topLeftCornerSize = other.topLeftCornerSize;
            this.topRightCornerSize = other.topRightCornerSize;
            this.bottomRightCornerSize = other.bottomRightCornerSize;
            this.bottomLeftCornerSize = other.bottomLeftCornerSize;
            this.topEdge = other.topEdge;
            this.rightEdge = other.rightEdge;
            this.bottomEdge = other.bottomEdge;
            this.leftEdge = other.leftEdge;
        }

        @NonNull
        public Builder setAllCorners(int cornerFamily, @Dimension float cornerSize) {
            return setAllCorners(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setAllCornerSizes(cornerSize);
        }

        @NonNull
        public Builder setAllCorners(@NonNull CornerTreatment cornerTreatment) {
            return setTopLeftCorner(cornerTreatment).setTopRightCorner(cornerTreatment).setBottomRightCorner(cornerTreatment).setBottomLeftCorner(cornerTreatment);
        }

        @NonNull
        public Builder setAllCornerSizes(@NonNull CornerSize cornerSize) {
            return setTopLeftCornerSize(cornerSize).setTopRightCornerSize(cornerSize).setBottomRightCornerSize(cornerSize).setBottomLeftCornerSize(cornerSize);
        }

        @NonNull
        public Builder setAllCornerSizes(@Dimension float cornerSize) {
            return setTopLeftCornerSize(cornerSize).setTopRightCornerSize(cornerSize).setBottomRightCornerSize(cornerSize).setBottomLeftCornerSize(cornerSize);
        }

        @NonNull
        public Builder setTopLeftCornerSize(@Dimension float cornerSize) {
            this.topLeftCornerSize = new AbsoluteCornerSize(cornerSize);
            return this;
        }

        @NonNull
        public Builder setTopLeftCornerSize(@NonNull CornerSize cornerSize) {
            this.topLeftCornerSize = cornerSize;
            return this;
        }

        @NonNull
        public Builder setTopRightCornerSize(@Dimension float cornerSize) {
            this.topRightCornerSize = new AbsoluteCornerSize(cornerSize);
            return this;
        }

        @NonNull
        public Builder setTopRightCornerSize(@NonNull CornerSize cornerSize) {
            this.topRightCornerSize = cornerSize;
            return this;
        }

        @NonNull
        public Builder setBottomRightCornerSize(@Dimension float cornerSize) {
            this.bottomRightCornerSize = new AbsoluteCornerSize(cornerSize);
            return this;
        }

        @NonNull
        public Builder setBottomRightCornerSize(@NonNull CornerSize cornerSize) {
            this.bottomRightCornerSize = cornerSize;
            return this;
        }

        @NonNull
        public Builder setBottomLeftCornerSize(@Dimension float cornerSize) {
            this.bottomLeftCornerSize = new AbsoluteCornerSize(cornerSize);
            return this;
        }

        @NonNull
        public Builder setBottomLeftCornerSize(@NonNull CornerSize cornerSize) {
            this.bottomLeftCornerSize = cornerSize;
            return this;
        }

        @NonNull
        public Builder setTopLeftCorner(int cornerFamily, @Dimension float cornerSize) {
            return setTopLeftCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setTopLeftCornerSize(cornerSize);
        }

        @NonNull
        public Builder setTopLeftCorner(int cornerFamily, @NonNull CornerSize cornerSize) {
            return setTopLeftCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setTopLeftCornerSize(cornerSize);
        }

        @NonNull
        public Builder setTopLeftCorner(@NonNull CornerTreatment topLeftCorner2) {
            this.topLeftCorner = topLeftCorner2;
            float size = compatCornerTreatmentSize(topLeftCorner2);
            if (size != -1.0f) {
                setTopLeftCornerSize(size);
            }
            return this;
        }

        @NonNull
        public Builder setTopRightCorner(int cornerFamily, @Dimension float cornerSize) {
            return setTopRightCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setTopRightCornerSize(cornerSize);
        }

        @NonNull
        public Builder setTopRightCorner(int cornerFamily, @NonNull CornerSize cornerSize) {
            return setTopRightCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setTopRightCornerSize(cornerSize);
        }

        @NonNull
        public Builder setTopRightCorner(@NonNull CornerTreatment topRightCorner2) {
            this.topRightCorner = topRightCorner2;
            float size = compatCornerTreatmentSize(topRightCorner2);
            if (size != -1.0f) {
                setTopRightCornerSize(size);
            }
            return this;
        }

        @NonNull
        public Builder setBottomRightCorner(int cornerFamily, @Dimension float cornerSize) {
            return setBottomRightCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setBottomRightCornerSize(cornerSize);
        }

        @NonNull
        public Builder setBottomRightCorner(int cornerFamily, @NonNull CornerSize cornerSize) {
            return setBottomRightCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setBottomRightCornerSize(cornerSize);
        }

        @NonNull
        public Builder setBottomRightCorner(@NonNull CornerTreatment bottomRightCorner2) {
            this.bottomRightCorner = bottomRightCorner2;
            float size = compatCornerTreatmentSize(bottomRightCorner2);
            if (size != -1.0f) {
                setBottomRightCornerSize(size);
            }
            return this;
        }

        @NonNull
        public Builder setBottomLeftCorner(int cornerFamily, @Dimension float cornerSize) {
            return setBottomLeftCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setBottomLeftCornerSize(cornerSize);
        }

        @NonNull
        public Builder setBottomLeftCorner(int cornerFamily, @NonNull CornerSize cornerSize) {
            return setBottomLeftCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setBottomLeftCornerSize(cornerSize);
        }

        @NonNull
        public Builder setBottomLeftCorner(@NonNull CornerTreatment bottomLeftCorner2) {
            this.bottomLeftCorner = bottomLeftCorner2;
            float size = compatCornerTreatmentSize(bottomLeftCorner2);
            if (size != -1.0f) {
                setBottomLeftCornerSize(size);
            }
            return this;
        }

        @NonNull
        public Builder setAllEdges(@NonNull EdgeTreatment edgeTreatment) {
            return setLeftEdge(edgeTreatment).setTopEdge(edgeTreatment).setRightEdge(edgeTreatment).setBottomEdge(edgeTreatment);
        }

        @NonNull
        public Builder setLeftEdge(@NonNull EdgeTreatment leftEdge2) {
            this.leftEdge = leftEdge2;
            return this;
        }

        @NonNull
        public Builder setTopEdge(@NonNull EdgeTreatment topEdge2) {
            this.topEdge = topEdge2;
            return this;
        }

        @NonNull
        public Builder setRightEdge(@NonNull EdgeTreatment rightEdge2) {
            this.rightEdge = rightEdge2;
            return this;
        }

        @NonNull
        public Builder setBottomEdge(@NonNull EdgeTreatment bottomEdge2) {
            this.bottomEdge = bottomEdge2;
            return this;
        }

        private static float compatCornerTreatmentSize(CornerTreatment treatment) {
            if (treatment instanceof RoundedCornerTreatment) {
                return ((RoundedCornerTreatment) treatment).radius;
            }
            if (treatment instanceof CutCornerTreatment) {
                return ((CutCornerTreatment) treatment).size;
            }
            return -1.0f;
        }

        @NonNull
        public ShapeAppearanceModel build() {
            return new ShapeAppearanceModel(this);
        }
    }

    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    @NonNull
    public static Builder builder(@NonNull Context context, AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        return builder(context, attrs, defStyleAttr, defStyleRes, 0);
    }

    @NonNull
    public static Builder builder(@NonNull Context context, AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes, int defaultCornerSize) {
        return builder(context, attrs, defStyleAttr, defStyleRes, (CornerSize) new AbsoluteCornerSize((float) defaultCornerSize));
    }

    @NonNull
    public static Builder builder(@NonNull Context context, AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes, @NonNull CornerSize defaultCornerSize) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaterialShape, defStyleAttr, defStyleRes);
        int shapeAppearanceResId = a.getResourceId(R.styleable.MaterialShape_shapeAppearance, 0);
        int shapeAppearanceOverlayResId = a.getResourceId(R.styleable.MaterialShape_shapeAppearanceOverlay, 0);
        a.recycle();
        return builder(context, shapeAppearanceResId, shapeAppearanceOverlayResId, defaultCornerSize);
    }

    @NonNull
    public static Builder builder(Context context, @StyleRes int shapeAppearanceResId, @StyleRes int shapeAppearanceOverlayResId) {
        return builder(context, shapeAppearanceResId, shapeAppearanceOverlayResId, 0);
    }

    @NonNull
    private static Builder builder(Context context, @StyleRes int shapeAppearanceResId, @StyleRes int shapeAppearanceOverlayResId, int defaultCornerSize) {
        return builder(context, shapeAppearanceResId, shapeAppearanceOverlayResId, (CornerSize) new AbsoluteCornerSize((float) defaultCornerSize));
    }

    @NonNull
    private static Builder builder(Context context, @StyleRes int shapeAppearanceResId, @StyleRes int shapeAppearanceOverlayResId, @NonNull CornerSize defaultCornerSize) {
        if (shapeAppearanceOverlayResId != 0) {
            context = new ContextThemeWrapper(context, shapeAppearanceResId);
            shapeAppearanceResId = shapeAppearanceOverlayResId;
        }
        TypedArray a = context.obtainStyledAttributes(shapeAppearanceResId, R.styleable.ShapeAppearance);
        try {
            int cornerFamily = a.getInt(R.styleable.ShapeAppearance_cornerFamily, 0);
            int cornerFamilyTopLeft = a.getInt(R.styleable.ShapeAppearance_cornerFamilyTopLeft, cornerFamily);
            int cornerFamilyTopRight = a.getInt(R.styleable.ShapeAppearance_cornerFamilyTopRight, cornerFamily);
            int cornerFamilyBottomRight = a.getInt(R.styleable.ShapeAppearance_cornerFamilyBottomRight, cornerFamily);
            int cornerFamilyBottomLeft = a.getInt(R.styleable.ShapeAppearance_cornerFamilyBottomLeft, cornerFamily);
            CornerSize cornerSize = getCornerSize(a, R.styleable.ShapeAppearance_cornerSize, defaultCornerSize);
            CornerSize cornerSizeTopLeft = getCornerSize(a, R.styleable.ShapeAppearance_cornerSizeTopLeft, cornerSize);
            CornerSize cornerSizeTopRight = getCornerSize(a, R.styleable.ShapeAppearance_cornerSizeTopRight, cornerSize);
            CornerSize cornerSizeBottomRight = getCornerSize(a, R.styleable.ShapeAppearance_cornerSizeBottomRight, cornerSize);
            return new Builder().setTopLeftCorner(cornerFamilyTopLeft, cornerSizeTopLeft).setTopRightCorner(cornerFamilyTopRight, cornerSizeTopRight).setBottomRightCorner(cornerFamilyBottomRight, cornerSizeBottomRight).setBottomLeftCorner(cornerFamilyBottomLeft, getCornerSize(a, R.styleable.ShapeAppearance_cornerSizeBottomLeft, cornerSize));
        } finally {
            a.recycle();
        }
    }

    @NonNull
    private static CornerSize getCornerSize(TypedArray a, int index, @NonNull CornerSize defaultValue) {
        TypedValue value = a.peekValue(index);
        if (value == null) {
            return defaultValue;
        }
        int i = value.type;
        if (i == 5) {
            return new AbsoluteCornerSize((float) TypedValue.complexToDimensionPixelSize(value.data, a.getResources().getDisplayMetrics()));
        }
        if (i == 6) {
            return new RelativeCornerSize(value.getFraction(1.0f, 1.0f));
        }
        return defaultValue;
    }

    private ShapeAppearanceModel(@NonNull Builder builder) {
        this.topLeftCorner = builder.topLeftCorner;
        this.topRightCorner = builder.topRightCorner;
        this.bottomRightCorner = builder.bottomRightCorner;
        this.bottomLeftCorner = builder.bottomLeftCorner;
        this.topLeftCornerSize = builder.topLeftCornerSize;
        this.topRightCornerSize = builder.topRightCornerSize;
        this.bottomRightCornerSize = builder.bottomRightCornerSize;
        this.bottomLeftCornerSize = builder.bottomLeftCornerSize;
        this.topEdge = builder.topEdge;
        this.rightEdge = builder.rightEdge;
        this.bottomEdge = builder.bottomEdge;
        this.leftEdge = builder.leftEdge;
    }

    public ShapeAppearanceModel() {
        this.topLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        this.topRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        this.bottomRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        this.bottomLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        this.topLeftCornerSize = new AbsoluteCornerSize(0.0f);
        this.topRightCornerSize = new AbsoluteCornerSize(0.0f);
        this.bottomRightCornerSize = new AbsoluteCornerSize(0.0f);
        this.bottomLeftCornerSize = new AbsoluteCornerSize(0.0f);
        this.topEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        this.rightEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        this.bottomEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        this.leftEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
    }

    @NonNull
    public CornerTreatment getTopLeftCorner() {
        return this.topLeftCorner;
    }

    @NonNull
    public CornerTreatment getTopRightCorner() {
        return this.topRightCorner;
    }

    @NonNull
    public CornerTreatment getBottomRightCorner() {
        return this.bottomRightCorner;
    }

    @NonNull
    public CornerTreatment getBottomLeftCorner() {
        return this.bottomLeftCorner;
    }

    @NonNull
    public CornerSize getTopLeftCornerSize() {
        return this.topLeftCornerSize;
    }

    @NonNull
    public CornerSize getTopRightCornerSize() {
        return this.topRightCornerSize;
    }

    @NonNull
    public CornerSize getBottomRightCornerSize() {
        return this.bottomRightCornerSize;
    }

    @NonNull
    public CornerSize getBottomLeftCornerSize() {
        return this.bottomLeftCornerSize;
    }

    @NonNull
    public EdgeTreatment getLeftEdge() {
        return this.leftEdge;
    }

    @NonNull
    public EdgeTreatment getTopEdge() {
        return this.topEdge;
    }

    @NonNull
    public EdgeTreatment getRightEdge() {
        return this.rightEdge;
    }

    @NonNull
    public EdgeTreatment getBottomEdge() {
        return this.bottomEdge;
    }

    @NonNull
    public Builder toBuilder() {
        return new Builder(this);
    }

    @NonNull
    public ShapeAppearanceModel withCornerSize(float cornerSize) {
        return toBuilder().setAllCornerSizes(cornerSize).build();
    }

    @NonNull
    public ShapeAppearanceModel withCornerSize(@NonNull CornerSize cornerSize) {
        return toBuilder().setAllCornerSizes(cornerSize).build();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public ShapeAppearanceModel withTransformedCornerSizes(@NonNull CornerSizeUnaryOperator op) {
        return toBuilder().setTopLeftCornerSize(op.apply(getTopLeftCornerSize())).setTopRightCornerSize(op.apply(getTopRightCornerSize())).setBottomLeftCornerSize(op.apply(getBottomLeftCornerSize())).setBottomRightCornerSize(op.apply(getBottomRightCornerSize())).build();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isRoundRect(@NonNull RectF bounds) {
        Class<EdgeTreatment> cls = EdgeTreatment.class;
        boolean hasDefaultEdges = this.leftEdge.getClass().equals(cls) && this.rightEdge.getClass().equals(cls) && this.topEdge.getClass().equals(cls) && this.bottomEdge.getClass().equals(cls);
        float cornerSize = this.topLeftCornerSize.getCornerSize(bounds);
        boolean cornersHaveSameSize = this.topRightCornerSize.getCornerSize(bounds) == cornerSize && this.bottomLeftCornerSize.getCornerSize(bounds) == cornerSize && this.bottomRightCornerSize.getCornerSize(bounds) == cornerSize;
        boolean hasRoundedCorners = (this.topRightCorner instanceof RoundedCornerTreatment) && (this.topLeftCorner instanceof RoundedCornerTreatment) && (this.bottomRightCorner instanceof RoundedCornerTreatment) && (this.bottomLeftCorner instanceof RoundedCornerTreatment);
        if (!hasDefaultEdges || !cornersHaveSameSize || !hasRoundedCorners) {
            return false;
        }
        return true;
    }
}
