package com.yanzhenjie.andserver;

import android.content.Context;
import androidx.annotation.NonNull;
import com.yanzhenjie.andserver.e;
import com.yanzhenjie.andserver.server.b;

/* compiled from: AndServer */
public class a {
    public static final String a = String.format("AndServer/%1$s", new Object[]{"2.1.10"});

    @NonNull
    public static e.a a(@NonNull Context context) {
        return b.e(context, "default");
    }
}
