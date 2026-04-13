package com.yanzhenjie.andserver.http.session;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.yanzhenjie.andserver.util.a;
import com.yanzhenjie.andserver.util.e;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/* compiled from: StandardStore */
public class g implements h {
    private File a;

    public g(File directory) {
        this.a = directory;
    }

    public boolean a(@NonNull e session) {
        a.c(session, "The session can not be null.");
        String id = session.getId();
        if (!TextUtils.isEmpty(id)) {
            ObjectOutputStream writer = null;
            try {
                if (!e.c(this.a)) {
                    e.a((Closeable) null);
                    return false;
                }
                File file = new File(this.a, id);
                if (!e.d(file)) {
                    e.a((Closeable) null);
                    return false;
                }
                writer = new ObjectOutputStream(new FileOutputStream(file));
                session.e(writer);
                e.a(writer);
                return true;
            } catch (IOException e) {
                e.e(new File(this.a, id));
                throw e;
            } catch (Throwable th) {
                e.a(writer);
                throw th;
            }
        } else {
            throw new IllegalStateException("The session id can not be empty or null.");
        }
    }
}
