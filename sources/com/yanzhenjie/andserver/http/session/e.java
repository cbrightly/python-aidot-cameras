package com.yanzhenjie.andserver.http.session;

import androidx.annotation.NonNull;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: StandardSession */
public class e implements b {
    private static final String[] a = new String[0];
    private String b;
    private long c;
    private long d;
    private int e = -1;
    private Map<String, Object> f = new ConcurrentHashMap();
    private boolean g;
    private boolean h;

    @NonNull
    public String getId() {
        return this.b;
    }

    public void c(boolean aNew) {
        this.g = aNew;
    }

    public boolean a() {
        d();
        return this.g;
    }

    private void d() {
        if (!b()) {
            throw new IllegalStateException("This session is invalid.");
        }
    }

    public boolean b() {
        if (!this.h) {
            return false;
        }
        if (this.e <= 0) {
            this.h = true;
        } else if (((int) ((System.currentTimeMillis() - this.d) / 1000)) >= this.e) {
            this.h = false;
        }
        return this.h;
    }

    public void e(@NonNull ObjectOutputStream stream) {
        stream.writeObject(this.b);
        stream.writeLong(this.c);
        stream.writeLong(this.d);
        stream.writeInt(this.e);
        stream.writeBoolean(this.g);
        stream.writeBoolean(this.h);
        stream.writeInt(this.f.size());
        for (String key : (String[]) this.f.keySet().toArray(a)) {
            Object value = this.f.get(key);
            if (value != null && (value instanceof Serializable)) {
                stream.writeObject(key);
                stream.writeObject(value);
            }
        }
    }
}
