package kotlin.coroutines.jvm.internal;

import com.google.firebase.messaging.Constants;
import java.lang.reflect.Field;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DebugMetadata.kt */
public final class g {
    @Nullable
    public static final StackTraceElement d(@NotNull a $this$getStackTraceElementImpl) {
        String moduleAndClass;
        k.e($this$getStackTraceElementImpl, "$this$getStackTraceElementImpl");
        f debugMetadata = b($this$getStackTraceElementImpl);
        if (debugMetadata == null) {
            return null;
        }
        a(1, debugMetadata.v());
        int label = c($this$getStackTraceElementImpl);
        int lineNumber = label < 0 ? -1 : debugMetadata.l()[label];
        String moduleName = i.c.b($this$getStackTraceElementImpl);
        if (moduleName == null) {
            moduleAndClass = debugMetadata.c();
        } else {
            moduleAndClass = moduleName + '/' + debugMetadata.c();
        }
        return new StackTraceElement(moduleAndClass, debugMetadata.m(), debugMetadata.f(), lineNumber);
    }

    private static final f b(a $this$getDebugMetadataAnnotation) {
        return (f) $this$getDebugMetadataAnnotation.getClass().getAnnotation(f.class);
    }

    private static final int c(a $this$getLabel) {
        try {
            Field field = $this$getLabel.getClass().getDeclaredField(Constants.ScionAnalytics.PARAM_LABEL);
            k.d(field, "field");
            field.setAccessible(true);
            Object obj = field.get($this$getLabel);
            if (!(obj instanceof Integer)) {
                obj = null;
            }
            Integer num = (Integer) obj;
            return (num != null ? num.intValue() : 0) - 1;
        } catch (Exception e) {
            return -1;
        }
    }

    private static final void a(int expected, int actual) {
        if (actual > expected) {
            throw new IllegalStateException(("Debug metadata version mismatch. Expected: " + expected + ", got " + actual + ". Please update the Kotlin standard library.").toString());
        }
    }
}
