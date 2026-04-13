package org.apache.httpcore.util;

import java.io.InputStream;
import org.apache.httpcore.j;

/* compiled from: EntityUtils */
public final class f {
    public static void a(j entity) {
        InputStream inStream;
        if (entity != null && entity.isStreaming() && (inStream = entity.getContent()) != null) {
            inStream.close();
        }
    }
}
