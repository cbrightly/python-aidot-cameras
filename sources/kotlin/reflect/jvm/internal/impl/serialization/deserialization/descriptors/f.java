package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.v;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.c;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.h;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.j;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.k;
import kotlin.reflect.jvm.internal.impl.protobuf.o;
import org.jetbrains.annotations.NotNull;

/* compiled from: DeserializedMemberDescriptor.kt */
public interface f extends v {

    /* compiled from: DeserializedMemberDescriptor.kt */
    public enum a {
        COMPATIBLE,
        NEEDS_WRAPPER,
        INCOMPATIBLE
    }

    @NotNull
    h C();

    @NotNull
    List<j> E0();

    @NotNull
    k F();

    @NotNull
    c G();

    @NotNull
    o Y();

    /* compiled from: DeserializedMemberDescriptor.kt */
    public static final class b {
        @NotNull
        public static List<j> a(f $this) {
            return j.a.a($this.Y(), $this.G(), $this.F());
        }
    }
}
