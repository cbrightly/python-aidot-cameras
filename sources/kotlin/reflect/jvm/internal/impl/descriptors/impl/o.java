package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.b;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.s;
import org.jetbrains.annotations.NotNull;

/* compiled from: FieldDescriptorImpl.kt */
public final class o extends b implements s {
    @NotNull
    private final i0 d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public o(@NotNull g annotations, @NotNull i0 correspondingProperty) {
        super(annotations);
        k.f(annotations, "annotations");
        k.f(correspondingProperty, "correspondingProperty");
        this.d = correspondingProperty;
    }
}
