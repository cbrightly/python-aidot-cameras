package com.caverock.androidsvg;

import android.graphics.Canvas;
import java.lang.reflect.Method;

/* compiled from: CanvasLegacy */
public class b {
    static final int a;
    private static final Method b;

    static {
        try {
            a = ((Integer) Canvas.class.getField("MATRIX_SAVE_FLAG").get((Object) null)).intValue();
            b = Canvas.class.getMethod("save", new Class[]{Integer.TYPE});
        } catch (Throwable e) {
            throw b(e);
        }
    }

    static void a(Canvas canvas, int saveFlags) {
        try {
            b.invoke(canvas, new Object[]{Integer.valueOf(saveFlags)});
        } catch (Throwable e) {
            throw b(e);
        }
    }

    private static RuntimeException b(Throwable t) {
        if (t != null) {
            return (RuntimeException) c(t);
        }
        throw new NullPointerException("t");
    }

    private static <T extends Throwable> T c(Throwable t) {
        throw t;
    }
}
