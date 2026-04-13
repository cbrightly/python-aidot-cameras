package com.bumptech.glide.load.model;

import com.bumptech.glide.load.model.j;
import java.util.Collections;
import java.util.Map;

/* compiled from: Headers */
public interface h {
    @Deprecated
    public static final h a = new a();
    public static final h b = new j.a().a();

    Map<String, String> getHeaders();

    /* compiled from: Headers */
    public class a implements h {
        a() {
        }

        public Map<String, String> getHeaders() {
            return Collections.emptyMap();
        }
    }
}
