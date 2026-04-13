package kotlin.reflect.jvm.internal;

import java.lang.reflect.Method;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b;
import org.jetbrains.annotations.NotNull;

/* compiled from: RuntimeTypeMapper.kt */
public final class f0 {

    /* compiled from: RuntimeTypeMapper.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<Class<?>, String> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final String invoke(Class<?> it) {
            k.b(it, "it");
            return b.c(it);
        }
    }

    /* access modifiers changed from: private */
    public static final String b(@NotNull Method $this$signature) {
        StringBuilder sb = new StringBuilder();
        sb.append($this$signature.getName());
        Class[] parameterTypes = $this$signature.getParameterTypes();
        k.b(parameterTypes, "parameterTypes");
        sb.append(kotlin.collections.l.E(parameterTypes, "", "(", ")", 0, (CharSequence) null, a.INSTANCE, 24, (Object) null));
        Class<?> returnType = $this$signature.getReturnType();
        k.b(returnType, "returnType");
        sb.append(b.c(returnType));
        return sb.toString();
    }
}
