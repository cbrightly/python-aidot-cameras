package com.didichuxing.doraemonkit.kit.filemanager.convert;

import io.ktor.features.ContentTransformationException;

/* compiled from: GsonConverter.kt */
public final class UnsupportedNullValuesException extends ContentTransformationException {
    public UnsupportedNullValuesException() {
        super("Receiving null values is not supported");
    }
}
