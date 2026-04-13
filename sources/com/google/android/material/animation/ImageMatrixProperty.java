package com.google.android.material.animation;

import android.graphics.Matrix;
import android.util.Property;
import android.widget.ImageView;
import androidx.annotation.NonNull;

public class ImageMatrixProperty extends Property<ImageView, Matrix> {
    private final Matrix matrix = new Matrix();

    public ImageMatrixProperty() {
        super(Matrix.class, "imageMatrixProperty");
    }

    public void set(@NonNull ImageView object, @NonNull Matrix value) {
        object.setImageMatrix(value);
    }

    @NonNull
    public Matrix get(@NonNull ImageView object) {
        this.matrix.set(object.getImageMatrix());
        return this.matrix;
    }
}
