package com.google.android.material.shape;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;

public class ShapeAppearancePathProvider {
    private final Path boundsPath = new Path();
    private final Path cornerPath = new Path();
    private final ShapePath[] cornerPaths = new ShapePath[4];
    private final Matrix[] cornerTransforms = new Matrix[4];
    private boolean edgeIntersectionCheckEnabled = true;
    private final Path edgePath = new Path();
    private final Matrix[] edgeTransforms = new Matrix[4];
    private final Path overlappedEdgePath = new Path();
    private final PointF pointF = new PointF();
    private final float[] scratch = new float[2];
    private final float[] scratch2 = new float[2];
    private final ShapePath shapePath = new ShapePath();

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface PathListener {
        void onCornerPathCreated(ShapePath shapePath, Matrix matrix, int i);

        void onEdgePathCreated(ShapePath shapePath, Matrix matrix, int i);
    }

    public static class Lazy {
        static final ShapeAppearancePathProvider INSTANCE = new ShapeAppearancePathProvider();

        private Lazy() {
        }
    }

    public ShapeAppearancePathProvider() {
        for (int i = 0; i < 4; i++) {
            this.cornerPaths[i] = new ShapePath();
            this.cornerTransforms[i] = new Matrix();
            this.edgeTransforms[i] = new Matrix();
        }
    }

    @UiThread
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static ShapeAppearancePathProvider getInstance() {
        return Lazy.INSTANCE;
    }

    public void calculatePath(ShapeAppearanceModel shapeAppearanceModel, float interpolation, RectF bounds, @NonNull Path path) {
        calculatePath(shapeAppearanceModel, interpolation, bounds, (PathListener) null, path);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void calculatePath(ShapeAppearanceModel shapeAppearanceModel, float interpolation, RectF bounds, PathListener pathListener, @NonNull Path path) {
        path.rewind();
        this.overlappedEdgePath.rewind();
        this.boundsPath.rewind();
        this.boundsPath.addRect(bounds, Path.Direction.CW);
        ShapeAppearancePathSpec shapeAppearancePathSpec = new ShapeAppearancePathSpec(shapeAppearanceModel, interpolation, bounds, pathListener, path);
        for (int index = 0; index < 4; index++) {
            setCornerPathAndTransform(shapeAppearancePathSpec, index);
            setEdgePathAndTransform(index);
        }
        for (int index2 = 0; index2 < 4; index2++) {
            appendCornerPath(shapeAppearancePathSpec, index2);
            appendEdgePath(shapeAppearancePathSpec, index2);
        }
        path.close();
        this.overlappedEdgePath.close();
        if (Build.VERSION.SDK_INT >= 19 && !this.overlappedEdgePath.isEmpty()) {
            path.op(this.overlappedEdgePath, Path.Op.UNION);
        }
    }

    private void setCornerPathAndTransform(@NonNull ShapeAppearancePathSpec spec, int index) {
        getCornerTreatmentForIndex(index, spec.shapeAppearanceModel).getCornerPath(this.cornerPaths[index], 90.0f, spec.interpolation, spec.bounds, getCornerSizeForIndex(index, spec.shapeAppearanceModel));
        float edgeAngle = angleOfEdge(index);
        this.cornerTransforms[index].reset();
        getCoordinatesOfCorner(index, spec.bounds, this.pointF);
        Matrix matrix = this.cornerTransforms[index];
        PointF pointF2 = this.pointF;
        matrix.setTranslate(pointF2.x, pointF2.y);
        this.cornerTransforms[index].preRotate(edgeAngle);
    }

    private void setEdgePathAndTransform(int index) {
        this.scratch[0] = this.cornerPaths[index].getEndX();
        this.scratch[1] = this.cornerPaths[index].getEndY();
        this.cornerTransforms[index].mapPoints(this.scratch);
        float edgeAngle = angleOfEdge(index);
        this.edgeTransforms[index].reset();
        Matrix matrix = this.edgeTransforms[index];
        float[] fArr = this.scratch;
        matrix.setTranslate(fArr[0], fArr[1]);
        this.edgeTransforms[index].preRotate(edgeAngle);
    }

    private void appendCornerPath(@NonNull ShapeAppearancePathSpec spec, int index) {
        this.scratch[0] = this.cornerPaths[index].getStartX();
        this.scratch[1] = this.cornerPaths[index].getStartY();
        this.cornerTransforms[index].mapPoints(this.scratch);
        if (index == 0) {
            Path path = spec.path;
            float[] fArr = this.scratch;
            path.moveTo(fArr[0], fArr[1]);
        } else {
            Path path2 = spec.path;
            float[] fArr2 = this.scratch;
            path2.lineTo(fArr2[0], fArr2[1]);
        }
        this.cornerPaths[index].applyToPath(this.cornerTransforms[index], spec.path);
        PathListener pathListener = spec.pathListener;
        if (pathListener != null) {
            pathListener.onCornerPathCreated(this.cornerPaths[index], this.cornerTransforms[index], index);
        }
    }

    private void appendEdgePath(@NonNull ShapeAppearancePathSpec spec, int index) {
        int nextIndex = (index + 1) % 4;
        this.scratch[0] = this.cornerPaths[index].getEndX();
        this.scratch[1] = this.cornerPaths[index].getEndY();
        this.cornerTransforms[index].mapPoints(this.scratch);
        this.scratch2[0] = this.cornerPaths[nextIndex].getStartX();
        this.scratch2[1] = this.cornerPaths[nextIndex].getStartY();
        this.cornerTransforms[nextIndex].mapPoints(this.scratch2);
        float[] fArr = this.scratch;
        float f = fArr[0];
        float[] fArr2 = this.scratch2;
        float edgeLength = Math.max(((float) Math.hypot((double) (f - fArr2[0]), (double) (fArr[1] - fArr2[1]))) - 0.001f, 0.0f);
        float center = getEdgeCenterForIndex(spec.bounds, index);
        this.shapePath.reset(0.0f, 0.0f);
        EdgeTreatment edgeTreatment = getEdgeTreatmentForIndex(index, spec.shapeAppearanceModel);
        edgeTreatment.getEdgePath(edgeLength, center, spec.interpolation, this.shapePath);
        this.edgePath.reset();
        this.shapePath.applyToPath(this.edgeTransforms[index], this.edgePath);
        if (!this.edgeIntersectionCheckEnabled || Build.VERSION.SDK_INT < 19 || (!edgeTreatment.forceIntersection() && !pathOverlapsCorner(this.edgePath, index) && !pathOverlapsCorner(this.edgePath, nextIndex))) {
            this.shapePath.applyToPath(this.edgeTransforms[index], spec.path);
        } else {
            Path path = this.edgePath;
            path.op(path, this.boundsPath, Path.Op.DIFFERENCE);
            this.scratch[0] = this.shapePath.getStartX();
            this.scratch[1] = this.shapePath.getStartY();
            this.edgeTransforms[index].mapPoints(this.scratch);
            Path path2 = this.overlappedEdgePath;
            float[] fArr3 = this.scratch;
            path2.moveTo(fArr3[0], fArr3[1]);
            this.shapePath.applyToPath(this.edgeTransforms[index], this.overlappedEdgePath);
        }
        PathListener pathListener = spec.pathListener;
        if (pathListener != null) {
            pathListener.onEdgePathCreated(this.shapePath, this.edgeTransforms[index], index);
        }
    }

    @RequiresApi(19)
    private boolean pathOverlapsCorner(Path edgePath2, int index) {
        this.cornerPath.reset();
        this.cornerPaths[index].applyToPath(this.cornerTransforms[index], this.cornerPath);
        RectF bounds = new RectF();
        edgePath2.computeBounds(bounds, true);
        this.cornerPath.computeBounds(bounds, true);
        edgePath2.op(this.cornerPath, Path.Op.INTERSECT);
        edgePath2.computeBounds(bounds, true);
        if (!bounds.isEmpty()) {
            return true;
        }
        if (bounds.width() <= 1.0f || bounds.height() <= 1.0f) {
            return false;
        }
        return true;
    }

    private float getEdgeCenterForIndex(@NonNull RectF bounds, int index) {
        float[] fArr = this.scratch;
        ShapePath[] shapePathArr = this.cornerPaths;
        fArr[0] = shapePathArr[index].endX;
        fArr[1] = shapePathArr[index].endY;
        this.cornerTransforms[index].mapPoints(fArr);
        switch (index) {
            case 1:
            case 3:
                return Math.abs(bounds.centerX() - this.scratch[0]);
            default:
                return Math.abs(bounds.centerY() - this.scratch[1]);
        }
    }

    private CornerTreatment getCornerTreatmentForIndex(int index, @NonNull ShapeAppearanceModel shapeAppearanceModel) {
        switch (index) {
            case 1:
                return shapeAppearanceModel.getBottomRightCorner();
            case 2:
                return shapeAppearanceModel.getBottomLeftCorner();
            case 3:
                return shapeAppearanceModel.getTopLeftCorner();
            default:
                return shapeAppearanceModel.getTopRightCorner();
        }
    }

    private CornerSize getCornerSizeForIndex(int index, @NonNull ShapeAppearanceModel shapeAppearanceModel) {
        switch (index) {
            case 1:
                return shapeAppearanceModel.getBottomRightCornerSize();
            case 2:
                return shapeAppearanceModel.getBottomLeftCornerSize();
            case 3:
                return shapeAppearanceModel.getTopLeftCornerSize();
            default:
                return shapeAppearanceModel.getTopRightCornerSize();
        }
    }

    private EdgeTreatment getEdgeTreatmentForIndex(int index, @NonNull ShapeAppearanceModel shapeAppearanceModel) {
        switch (index) {
            case 1:
                return shapeAppearanceModel.getBottomEdge();
            case 2:
                return shapeAppearanceModel.getLeftEdge();
            case 3:
                return shapeAppearanceModel.getTopEdge();
            default:
                return shapeAppearanceModel.getRightEdge();
        }
    }

    private void getCoordinatesOfCorner(int index, @NonNull RectF bounds, @NonNull PointF pointF2) {
        switch (index) {
            case 1:
                pointF2.set(bounds.right, bounds.bottom);
                return;
            case 2:
                pointF2.set(bounds.left, bounds.bottom);
                return;
            case 3:
                pointF2.set(bounds.left, bounds.top);
                return;
            default:
                pointF2.set(bounds.right, bounds.top);
                return;
        }
    }

    private float angleOfEdge(int index) {
        return (float) ((index + 1) * 90);
    }

    /* access modifiers changed from: package-private */
    public void setEdgeIntersectionCheckEnable(boolean enable) {
        this.edgeIntersectionCheckEnabled = enable;
    }

    public static final class ShapeAppearancePathSpec {
        @NonNull
        public final RectF bounds;
        public final float interpolation;
        @NonNull
        public final Path path;
        @Nullable
        public final PathListener pathListener;
        @NonNull
        public final ShapeAppearanceModel shapeAppearanceModel;

        ShapeAppearancePathSpec(@NonNull ShapeAppearanceModel shapeAppearanceModel2, float interpolation2, RectF bounds2, @Nullable PathListener pathListener2, Path path2) {
            this.pathListener = pathListener2;
            this.shapeAppearanceModel = shapeAppearanceModel2;
            this.interpolation = interpolation2;
            this.bounds = bounds2;
            this.path = path2;
        }
    }
}
