package com.android.volley.toolbox;

import com.android.volley.e;
import com.android.volley.i;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.d;
import org.apache.http.message.o;
import org.apache.http.q;
import org.apache.http.v;

/* compiled from: BaseHttpStack */
public abstract class b implements i {
    public abstract h b(i<?> iVar, Map<String, String> map);

    @Deprecated
    public final q a(i<?> request, Map<String, String> additionalHeaders) {
        h response = b(request, additionalHeaders);
        org.apache.http.message.i apacheResponse = new org.apache.http.message.i(new o(new v("HTTP", 1, 1), response.d(), ""));
        List<Header> headers = new ArrayList<>();
        for (e header : response.c()) {
            headers.add(new org.apache.http.message.b(header.a(), header.b()));
        }
        apacheResponse.u0((d[]) headers.toArray(new d[0]));
        InputStream responseStream = response.a();
        if (responseStream != null) {
            org.apache.http.entity.b entity = new org.apache.http.entity.b();
            entity.setContent(responseStream);
            entity.setContentLength((long) response.b());
            apacheResponse.l(entity);
        }
        return apacheResponse;
    }
}
