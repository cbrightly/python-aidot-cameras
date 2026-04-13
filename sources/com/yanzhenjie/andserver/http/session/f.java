package com.yanzhenjie.andserver.http.session;

import android.content.Context;
import androidx.annotation.NonNull;
import java.io.File;

/* compiled from: StandardSessionManager */
public class f implements c {
    private a a = new d();
    private h b;

    public f(Context context) {
        this.b = new g(new File(context.getCacheDir(), "_andserver_session_"));
    }

    public void a(@NonNull b session) {
        if ((session instanceof e) && session.a()) {
            e standardSession = (e) session;
            standardSession.c(false);
            this.b.a(standardSession);
        }
    }
}
