package org.webrtc;

import androidx.annotation.Nullable;
import javax.microedition.khronos.egl.EGLContext;
import org.webrtc.EglBase;
import org.webrtc.EglBase10;
import org.webrtc.EglBase14;

/* compiled from: EglBase */
public final /* synthetic */ class r0 {
    public static EglBase.ConfigBuilder a() {
        return new EglBase.ConfigBuilder();
    }

    public static int h(int[] configAttributes) {
        int i = 0;
        while (i < configAttributes.length - 1) {
            if (configAttributes[i] == 12352) {
                switch (configAttributes[i + 1]) {
                    case 4:
                        return 2;
                    case 64:
                        return 3;
                    default:
                        return 1;
                }
            } else {
                i++;
            }
        }
        return 1;
    }

    public static EglBase c(@Nullable EglBase.Context sharedContext, int[] configAttributes) {
        if (sharedContext == null) {
            if (EglBase14Impl.isEGL14Supported()) {
                return g(configAttributes);
            }
            return e(configAttributes);
        } else if (sharedContext instanceof EglBase14.Context) {
            return f((EglBase14.Context) sharedContext, configAttributes);
        } else {
            if (sharedContext instanceof EglBase10.Context) {
                return d((EglBase10.Context) sharedContext, configAttributes);
            }
            throw new IllegalArgumentException("Unrecognized Context");
        }
    }

    public static EglBase b() {
        return c((EglBase.Context) null, EglBase.CONFIG_PLAIN);
    }

    public static EglBase10 e(int[] configAttributes) {
        return new EglBase10Impl((EGLContext) null, configAttributes);
    }

    public static EglBase10 d(EglBase10.Context sharedContext, int[] configAttributes) {
        return new EglBase10Impl(sharedContext == null ? null : sharedContext.getRawContext(), configAttributes);
    }

    public static EglBase14 g(int[] configAttributes) {
        return new EglBase14Impl((android.opengl.EGLContext) null, configAttributes);
    }

    public static EglBase14 f(EglBase14.Context sharedContext, int[] configAttributes) {
        return new EglBase14Impl(sharedContext == null ? null : sharedContext.getRawContext(), configAttributes);
    }
}
