package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.Date;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.b;
import org.apache.http.cookie.n;
import org.apache.http.util.a;

/* compiled from: BasicMaxAgeHandler */
public class h extends a implements b {
    public void d(n cookie, String value) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        if (value != null) {
            try {
                int age = Integer.parseInt(value);
                if (age >= 0) {
                    cookie.setExpiryDate(new Date(System.currentTimeMillis() + (((long) age) * 1000)));
                    return;
                }
                throw new MalformedCookieException("Negative 'max-age' attribute: " + value);
            } catch (NumberFormatException e) {
                throw new MalformedCookieException("Invalid 'max-age' attribute: " + value);
            }
        } else {
            throw new MalformedCookieException("Missing value for 'max-age' attribute");
        }
    }

    public String c() {
        return "max-age";
    }
}
