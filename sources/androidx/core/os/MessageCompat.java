package androidx.core.os;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Message;
import androidx.annotation.NonNull;

public final class MessageCompat {
    private static boolean sTryIsAsynchronous = true;
    private static boolean sTrySetAsynchronous = true;

    @SuppressLint({"NewApi"})
    public static void setAsynchronous(@NonNull Message message, boolean async) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 22) {
            message.setAsynchronous(async);
        } else if (sTrySetAsynchronous && i >= 16) {
            try {
                message.setAsynchronous(async);
            } catch (NoSuchMethodError e) {
                sTrySetAsynchronous = false;
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static boolean isAsynchronous(@NonNull Message message) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 22) {
            return message.isAsynchronous();
        }
        if (sTryIsAsynchronous && i >= 16) {
            try {
                return message.isAsynchronous();
            } catch (NoSuchMethodError e) {
                sTryIsAsynchronous = false;
            }
        }
        return false;
    }

    private MessageCompat() {
    }
}
