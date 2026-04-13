package kotlin.reflect.jvm.internal.impl.types;

import com.meituan.robust.Constants;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.e;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.types.model.h;
import kotlin.reflect.jvm.internal.impl.types.model.i;
import kotlin.text.t;
import org.jetbrains.annotations.NotNull;

/* compiled from: KotlinType.kt */
public abstract class i0 extends g1 implements h, i {
    @NotNull
    public abstract i0 P0(boolean z);

    @NotNull
    public abstract i0 Q0(@NotNull g gVar);

    public i0() {
        super((DefaultConstructorMarker) null);
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        for (c annotation : getAnnotations()) {
            t.k($this$buildString, Constants.ARRAY_TYPE, kotlin.reflect.jvm.internal.impl.renderer.c.t(kotlin.reflect.jvm.internal.impl.renderer.c.i, annotation, (e) null, 2, (Object) null), "] ");
        }
        $this$buildString.append(I0());
        if (!H0().isEmpty()) {
            y.Z(H0(), $this$buildString, ", ", "<", ">", 0, (CharSequence) null, (l) null, 112, (Object) null);
        }
        if (J0()) {
            $this$buildString.append("?");
        }
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }
}
