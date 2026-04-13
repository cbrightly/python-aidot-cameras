package androidx.transition;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Build;
import androidx.annotation.NonNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CanvasUtils {
    private static Method sInorderBarrierMethod;
    private static boolean sOrderMethodsFetched;
    private static Method sReorderBarrierMethod;

    @SuppressLint({"SoonBlockedPrivateApi"})
    static void enableZ(@NonNull Canvas canvas, boolean enable) {
        Method method;
        int i = Build.VERSION.SDK_INT;
        if (i >= 21) {
            if (i >= 29) {
                if (enable) {
                    canvas.enableZ();
                } else {
                    canvas.disableZ();
                }
            } else if (i != 28) {
                if (!sOrderMethodsFetched) {
                    try {
                        Method declaredMethod = Canvas.class.getDeclaredMethod("insertReorderBarrier", new Class[0]);
                        sReorderBarrierMethod = declaredMethod;
                        declaredMethod.setAccessible(true);
                        Method declaredMethod2 = Canvas.class.getDeclaredMethod("insertInorderBarrier", new Class[0]);
                        sInorderBarrierMethod = declaredMethod2;
                        declaredMethod2.setAccessible(true);
                    } catch (NoSuchMethodException e) {
                    }
                    sOrderMethodsFetched = true;
                }
                if (enable) {
                    try {
                        Method method2 = sReorderBarrierMethod;
                        if (method2 != null) {
                            method2.invoke(canvas, new Object[0]);
                        }
                    } catch (IllegalAccessException e2) {
                        return;
                    } catch (InvocationTargetException e3) {
                        throw new RuntimeException(e3.getCause());
                    }
                }
                if (!enable && (method = sInorderBarrierMethod) != null) {
                    method.invoke(canvas, new Object[0]);
                }
            } else {
                throw new IllegalStateException("This method doesn't work on Pie!");
            }
        }
    }

    private CanvasUtils() {
    }
}
