package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import org.apache.http.cookie.b;
import org.apache.http.cookie.n;
import org.apache.http.util.a;

/* compiled from: BasicCommentHandler */
public class e extends a implements b {
    public void d(n cookie, String value) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        cookie.setComment(value);
    }

    public String c() {
        return "comment";
    }
}
