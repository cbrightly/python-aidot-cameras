package org.apache.http.client.protocol;

import com.amazonaws.http.HttpHeader;
import org.apache.http.j;
import org.apache.http.k;
import org.apache.http.o;
import org.apache.http.p;
import org.apache.http.protocol.f;
import org.apache.http.t;
import org.apache.http.util.a;
import org.apache.http.v;

/* compiled from: RequestExpectContinue */
public class h implements p {
    public void b(o request, f context) {
        a.i(request, "HTTP request");
        if (!request.containsHeader(HttpHeader.EXPECT) && (request instanceof k)) {
            v ver = request.r().getProtocolVersion();
            j entity = ((k) request).a();
            if (entity != null && entity.getContentLength() != 0 && !ver.lessEquals(t.HTTP_1_0) && a.g(context).s().r()) {
                request.addHeader(HttpHeader.EXPECT, "100-continue");
            }
        }
    }
}
