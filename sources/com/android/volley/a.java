package com.android.volley;

import androidx.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* compiled from: Cache */
public interface a {
    void a(String str, boolean z);

    void b(String str, C0017a aVar);

    @Nullable
    C0017a get(String str);

    void initialize();

    /* renamed from: com.android.volley.a$a  reason: collision with other inner class name */
    /* compiled from: Cache */
    public static class C0017a {
        public byte[] a;
        public String b;
        public long c;
        public long d;
        public long e;
        public long f;
        public Map<String, String> g = Collections.emptyMap();
        public List<e> h;

        public boolean a() {
            return b(System.currentTimeMillis());
        }

        /* access modifiers changed from: package-private */
        public boolean b(long currentTimeMillis) {
            return this.e < currentTimeMillis;
        }

        /* access modifiers changed from: package-private */
        public boolean c(long currentTimeMillis) {
            return this.f < currentTimeMillis;
        }
    }
}
