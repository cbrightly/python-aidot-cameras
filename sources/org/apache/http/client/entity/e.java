package org.apache.http.client.entity;

import java.nio.charset.Charset;
import org.apache.http.entity.f;
import org.apache.http.entity.i;
import org.apache.http.u;

/* compiled from: UrlEncodedFormEntity */
public class e extends i {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public e(Iterable<? extends u> parameters, Charset charset) {
        super(org.apache.http.client.utils.e.g(parameters, charset != null ? charset : org.apache.http.protocol.e.a), f.create("application/x-www-form-urlencoded", charset));
    }
}
