package com.google.firebase.encoders.proto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.encoders.EncodingException;
import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ValueEncoderContext;

public class ProtobufValueEncoderContext implements ValueEncoderContext {
    private boolean encoded = false;
    private FieldDescriptor field;
    private final ProtobufDataEncoderContext objEncoderCtx;
    private boolean skipDefault = false;

    ProtobufValueEncoderContext(ProtobufDataEncoderContext objEncoderCtx2) {
        this.objEncoderCtx = objEncoderCtx2;
    }

    /* access modifiers changed from: package-private */
    public void resetContext(FieldDescriptor field2, boolean skipDefault2) {
        this.encoded = false;
        this.field = field2;
        this.skipDefault = skipDefault2;
    }

    private void checkNotUsed() {
        if (!this.encoded) {
            this.encoded = true;
            return;
        }
        throw new EncodingException("Cannot encode a second value in the ValueEncoderContext");
    }

    @NonNull
    public ValueEncoderContext add(@Nullable String value) {
        checkNotUsed();
        this.objEncoderCtx.add(this.field, (Object) value, this.skipDefault);
        return this;
    }

    @NonNull
    public ValueEncoderContext add(float value) {
        checkNotUsed();
        this.objEncoderCtx.add(this.field, value, this.skipDefault);
        return this;
    }

    @NonNull
    public ValueEncoderContext add(double value) {
        checkNotUsed();
        this.objEncoderCtx.add(this.field, value, this.skipDefault);
        return this;
    }

    @NonNull
    public ValueEncoderContext add(int value) {
        checkNotUsed();
        this.objEncoderCtx.add(this.field, value, this.skipDefault);
        return this;
    }

    @NonNull
    public ValueEncoderContext add(long value) {
        checkNotUsed();
        this.objEncoderCtx.add(this.field, value, this.skipDefault);
        return this;
    }

    @NonNull
    public ValueEncoderContext add(boolean value) {
        checkNotUsed();
        this.objEncoderCtx.add(this.field, value, this.skipDefault);
        return this;
    }

    @NonNull
    public ValueEncoderContext add(@NonNull byte[] bytes) {
        checkNotUsed();
        this.objEncoderCtx.add(this.field, (Object) bytes, this.skipDefault);
        return this;
    }
}
