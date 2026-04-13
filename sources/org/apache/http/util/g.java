package org.apache.http.util;

import java.io.InputStream;
import org.apache.http.j;

/* compiled from: EntityUtils */
public final class g {
    public static void a(j entity) {
        InputStream instream;
        if (entity != null && entity.isStreaming() && (instream = entity.getContent()) != null) {
            instream.close();
        }
    }
}
