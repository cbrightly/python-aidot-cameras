package com.yanzhenjie.andserver.framework.body;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yanzhenjie.andserver.http.i;
import com.yanzhenjie.andserver.util.e;
import com.yanzhenjie.andserver.util.h;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: FileBody */
public class a implements i {
    private File a;

    public a(File body) {
        if (body != null) {
            this.a = body;
            return;
        }
        throw new IllegalArgumentException("The file cannot be null.");
    }

    public long a() {
        return this.a.length();
    }

    @Nullable
    public h d() {
        return h.getFileMediaType(this.a.getName());
    }

    public void writeTo(@NonNull OutputStream output) {
        InputStream is = new FileInputStream(this.a);
        e.i(is, output);
        e.a(is);
    }
}
