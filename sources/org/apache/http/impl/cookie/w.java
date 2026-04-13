package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.Date;
import java.util.regex.Pattern;
import org.apache.http.cookie.b;
import org.apache.http.cookie.n;
import org.apache.http.util.a;
import org.apache.http.util.j;

/* compiled from: LaxMaxAgeHandler */
public class w extends a implements b {
    private static final Pattern a = Pattern.compile("^\\-?[0-9]+$");

    public void d(n cookie, String value) {
        Date expiryDate;
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        if (!j.b(value) && a.matcher(value).matches()) {
            try {
                int age = Integer.parseInt(value);
                if (age >= 0) {
                    long currentTimeMillis = System.currentTimeMillis() + (((long) age) * 1000);
                } else {
                    expiryDate = new Date(Long.MIN_VALUE);
                }
                cookie.setExpiryDate(expiryDate);
            } catch (NumberFormatException e) {
            }
        }
    }

    public String c() {
        return "max-age";
    }
}
