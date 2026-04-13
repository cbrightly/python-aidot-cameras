package org.apache.http.client.protocol;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.List;
import org.apache.http.client.config.a;
import org.apache.http.o;
import org.apache.http.p;
import org.apache.http.protocol.f;

/* compiled from: RequestAcceptEncoding */
public class b implements p {
    private final String c;

    public b(List<String> encodings) {
        if (encodings == null || encodings.isEmpty()) {
            this.c = "gzip,deflate";
            return;
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < encodings.size(); i++) {
            if (i > 0) {
                buf.append(",");
            }
            buf.append(encodings.get(i));
        }
        this.c = buf.toString();
    }

    public b() {
        this((List<String>) null);
    }

    public void b(o request, f context) {
        a requestConfig = a.g(context).s();
        if (!request.containsHeader(HttpHeaders.HEAD_KEY_ACCEPT_ENCODING) && requestConfig.p()) {
            request.addHeader(HttpHeaders.HEAD_KEY_ACCEPT_ENCODING, this.c);
        }
    }
}
