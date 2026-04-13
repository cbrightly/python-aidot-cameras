package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.Date;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.b;
import org.apache.http.cookie.n;
import org.apache.http.util.a;

/* compiled from: BasicExpiresHandler */
public class g extends a implements b {
    private final String[] a;

    public g(String[] datepatterns) {
        a.i(datepatterns, "Array of date patterns");
        this.a = datepatterns;
    }

    public void d(n cookie, String value) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        if (value != null) {
            Date expiry = org.apache.http.client.utils.b.a(value, this.a);
            if (expiry != null) {
                cookie.setExpiryDate(expiry);
                return;
            }
            throw new MalformedCookieException("Invalid 'expires' attribute: " + value);
        }
        throw new MalformedCookieException("Missing value for 'expires' attribute");
    }

    public String c() {
        return "expires";
    }
}
