package com.android.volley.toolbox;

import com.android.volley.Header;
import com.android.volley.e;
import com.android.volley.i;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.d;
import org.apache.http.q;

/* compiled from: AdaptedHttpStack */
public class a extends b {
    private final i a;

    a(i httpStack) {
        this.a = httpStack;
    }

    public h b(i<?> request, Map<String, String> additionalHeaders) {
        try {
            q apacheResp = this.a.a(request, additionalHeaders);
            int statusCode = apacheResp.j().getStatusCode();
            d[] headers = apacheResp.v();
            List<Header> headerList = new ArrayList<>(headers.length);
            for (d header : headers) {
                headerList.add(new e(header.getName(), header.getValue()));
            }
            if (apacheResp.a() == null) {
                return new h(statusCode, headerList);
            }
            long contentLength = apacheResp.a().getContentLength();
            if (((long) ((int) contentLength)) == contentLength) {
                return new h(statusCode, headerList, (int) apacheResp.a().getContentLength(), apacheResp.a().getContent());
            }
            throw new IOException("Response too large: " + contentLength);
        } catch (ConnectTimeoutException e) {
            throw new SocketTimeoutException(e.getMessage());
        }
    }
}
