package org.apache.http.impl.client;

import org.apache.http.conn.f;
import org.apache.http.e;
import org.apache.http.message.d;
import org.apache.http.q;
import org.apache.http.util.a;

/* compiled from: DefaultConnectionKeepAliveStrategy */
public class m implements f {
    public static final m a = new m();

    public long a(q response, org.apache.http.protocol.f context) {
        a.i(response, "HTTP response");
        org.apache.http.f it = new d(response.o("Keep-Alive"));
        while (it.hasNext()) {
            e he = it.nextElement();
            String param = he.getName();
            String value = he.getValue();
            if (value != null && param.equalsIgnoreCase("timeout")) {
                try {
                    return Long.parseLong(value) * 1000;
                } catch (NumberFormatException e) {
                }
            }
        }
        return -1;
    }
}
