package zendesk.conversationkit.android.internal;

import android.os.Build;
import org.jetbrains.annotations.NotNull;

/* compiled from: HostAppInfo.kt */
public final class f {
    @NotNull
    public static final f a = new f();
    @NotNull
    private static final String b;
    @NotNull
    private static final String c;
    @NotNull
    private static final String d;

    private f() {
    }

    static {
        String str = Build.MANUFACTURER;
        String str2 = "";
        if (str == null) {
            str = str2;
        }
        b = str;
        String str3 = Build.MODEL;
        if (str3 == null) {
            str3 = str2;
        }
        c = str3;
        String str4 = Build.VERSION.RELEASE;
        if (str4 != null) {
            str2 = str4;
        }
        d = str2;
    }

    @NotNull
    public final String a() {
        return b;
    }

    @NotNull
    public final String b() {
        return c;
    }

    @NotNull
    public final String c() {
        return d;
    }
}
