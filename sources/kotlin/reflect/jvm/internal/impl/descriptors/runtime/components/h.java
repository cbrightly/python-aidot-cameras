package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReflectKotlinClassFinder.kt */
public final class h {
    /* access modifiers changed from: private */
    public static final String b(@NotNull a $this$toRuntimeFqName) {
        String b = $this$toRuntimeFqName.i().b();
        k.b(b, "relativeClassName.asString()");
        String className = w.G(b, '.', '$', false, 4, (Object) null);
        b h = $this$toRuntimeFqName.h();
        k.b(h, "packageFqName");
        if (h.d()) {
            return className;
        }
        return $this$toRuntimeFqName.h() + '.' + className;
    }
}
