package com.google.firebase.encoders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ObjectEncoderContext {
    @NonNull
    ObjectEncoderContext add(@NonNull FieldDescriptor fieldDescriptor, double d);

    @NonNull
    ObjectEncoderContext add(@NonNull FieldDescriptor fieldDescriptor, float f);

    @NonNull
    ObjectEncoderContext add(@NonNull FieldDescriptor fieldDescriptor, int i);

    @NonNull
    ObjectEncoderContext add(@NonNull FieldDescriptor fieldDescriptor, long j);

    @NonNull
    ObjectEncoderContext add(@NonNull FieldDescriptor fieldDescriptor, @Nullable Object obj);

    @NonNull
    ObjectEncoderContext add(@NonNull FieldDescriptor fieldDescriptor, boolean z);

    @NonNull
    @Deprecated
    ObjectEncoderContext add(@NonNull String str, double d);

    @NonNull
    @Deprecated
    ObjectEncoderContext add(@NonNull String str, int i);

    @NonNull
    @Deprecated
    ObjectEncoderContext add(@NonNull String str, long j);

    @NonNull
    @Deprecated
    ObjectEncoderContext add(@NonNull String str, @Nullable Object obj);

    @NonNull
    @Deprecated
    ObjectEncoderContext add(@NonNull String str, boolean z);

    @NonNull
    ObjectEncoderContext inline(@Nullable Object obj);

    @NonNull
    ObjectEncoderContext nested(@NonNull FieldDescriptor fieldDescriptor);

    @NonNull
    ObjectEncoderContext nested(@NonNull String str);
}
