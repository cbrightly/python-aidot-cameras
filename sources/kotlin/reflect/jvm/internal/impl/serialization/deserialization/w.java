package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.t;
import kotlin.reflect.e;
import kotlin.reflect.jvm.internal.impl.builtins.f;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.m;
import org.jetbrains.annotations.Nullable;

/* compiled from: MemberDeserializer.kt */
public final /* synthetic */ class w extends t {
    public static final m INSTANCE = new w();

    w() {
    }

    public String getName() {
        return "isSuspendFunctionType";
    }

    public e getOwner() {
        return a0.d(f.class, "deserialization");
    }

    public String getSignature() {
        return "isSuspendFunctionType(Lorg/jetbrains/kotlin/types/KotlinType;)Z";
    }

    @Nullable
    public Object get(@Nullable Object receiver) {
        return Boolean.valueOf(f.m((g1) receiver));
    }
}
