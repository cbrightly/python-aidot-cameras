package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import meshsdk.ConfigUtil;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.b;
import org.apache.http.cookie.n;
import org.apache.http.util.a;

@Deprecated
/* compiled from: BrowserCompatVersionAttributeHandler */
public class o extends a implements b {
    public void d(n cookie, String value) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        if (value != null) {
            int version = 0;
            try {
                version = Integer.parseInt(value);
            } catch (NumberFormatException e) {
            }
            cookie.setVersion(version);
            return;
        }
        throw new MalformedCookieException("Missing value for version attribute");
    }

    public String c() {
        return ConfigUtil.VERSION_FILE;
    }
}
