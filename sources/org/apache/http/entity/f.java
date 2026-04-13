package org.apache.http.entity;

import com.yanzhenjie.andserver.util.h;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.b;
import org.apache.http.e;
import org.apache.http.message.g;
import org.apache.http.message.m;
import org.apache.http.message.v;
import org.apache.http.u;
import org.apache.http.util.a;
import org.apache.http.util.d;
import org.apache.http.util.j;

/* compiled from: ContentType */
public final class f implements Serializable {
    public static final f APPLICATION_ATOM_XML;
    public static final f APPLICATION_FORM_URLENCODED;
    public static final f APPLICATION_JSON;
    public static final f APPLICATION_OCTET_STREAM = create(h.APPLICATION_OCTET_STREAM_VALUE, (Charset) null);
    public static final f APPLICATION_SVG_XML;
    public static final f APPLICATION_XHTML_XML;
    public static final f APPLICATION_XML;
    public static final f DEFAULT_BINARY = APPLICATION_OCTET_STREAM;
    public static final f DEFAULT_TEXT = TEXT_PLAIN;
    public static final f MULTIPART_FORM_DATA;
    public static final f TEXT_HTML;
    public static final f TEXT_PLAIN;
    public static final f TEXT_XML;
    public static final f WILDCARD = create(h.ALL_VALUE, (Charset) null);
    private static final Map<String, f> c;
    private static final long serialVersionUID = -7768694718232371896L;
    private final Charset charset;
    private final String mimeType;
    private final u[] params;

    static {
        Charset charset2 = b.c;
        f create = create(h.APPLICATION_ATOM_XML_VALUE, charset2);
        APPLICATION_ATOM_XML = create;
        f create2 = create("application/x-www-form-urlencoded", charset2);
        APPLICATION_FORM_URLENCODED = create2;
        f create3 = create("application/json", b.a);
        APPLICATION_JSON = create3;
        f create4 = create("application/svg+xml", charset2);
        APPLICATION_SVG_XML = create4;
        f create5 = create(h.APPLICATION_XHTML_XML_VALUE, charset2);
        APPLICATION_XHTML_XML = create5;
        f create6 = create(h.APPLICATION_XML_VALUE, charset2);
        APPLICATION_XML = create6;
        f create7 = create(h.MULTIPART_FORM_DATA_VALUE, charset2);
        MULTIPART_FORM_DATA = create7;
        f create8 = create(h.TEXT_HTML_VALUE, charset2);
        TEXT_HTML = create8;
        f create9 = create(h.TEXT_PLAIN_VALUE, charset2);
        TEXT_PLAIN = create9;
        f create10 = create(h.TEXT_XML_VALUE, charset2);
        TEXT_XML = create10;
        f[] contentTypes = {create, create2, create3, create4, create5, create6, create7, create8, create9, create10};
        HashMap<String, ContentType> map = new HashMap<>();
        for (f contentType : contentTypes) {
            map.put(contentType.getMimeType(), contentType);
        }
        c = Collections.unmodifiableMap(map);
    }

    f(String mimeType2, Charset charset2) {
        this.mimeType = mimeType2;
        this.charset = charset2;
        this.params = null;
    }

    f(String mimeType2, Charset charset2, u[] params2) {
        this.mimeType = mimeType2;
        this.charset = charset2;
        this.params = params2;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getParameter(String name) {
        a.e(name, "Parameter name");
        if (this.params == null) {
            return null;
        }
        for (u param : this.params) {
            if (param.getName().equalsIgnoreCase(name)) {
                return param.getValue();
            }
        }
        return null;
    }

    public String toString() {
        d buf = new d(64);
        buf.append(this.mimeType);
        if (this.params != null) {
            buf.append("; ");
            org.apache.http.message.f.b.g(buf, this.params, false);
        } else if (this.charset != null) {
            buf.append("; charset=");
            buf.append(this.charset.name());
        }
        return buf.toString();
    }

    private static boolean c(String s) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '\"' || ch == ',' || ch == ';') {
                return false;
            }
        }
        return true;
    }

    public static f create(String mimeType2, Charset charset2) {
        String normalizedMimeType = ((String) a.d(mimeType2, "MIME type")).toLowerCase(Locale.ROOT);
        a.a(c(normalizedMimeType), "MIME type may not contain reserved characters");
        return new f(normalizedMimeType, charset2);
    }

    public static f create(String mimeType2) {
        return create(mimeType2, (Charset) null);
    }

    public static f create(String mimeType2, String charset2) {
        return create(mimeType2, !j.b(charset2) ? Charset.forName(charset2) : null);
    }

    private static f b(e helem, boolean strict) {
        return a(helem.getName(), helem.getParameters(), strict);
    }

    private static f a(String mimeType2, u[] params2, boolean strict) {
        Charset charset2 = null;
        u[] arr$ = params2;
        int len$ = arr$.length;
        int i$ = 0;
        while (true) {
            if (i$ >= len$) {
                break;
            }
            u param = arr$[i$];
            if (param.getName().equalsIgnoreCase("charset")) {
                String s = param.getValue();
                if (!j.b(s)) {
                    try {
                        charset2 = Charset.forName(s);
                    } catch (UnsupportedCharsetException ex) {
                        if (strict) {
                            throw ex;
                        }
                    }
                }
            } else {
                i$++;
            }
        }
        return new f(mimeType2, charset2, (params2 == null || params2.length <= 0) ? null : params2);
    }

    public static f create(String mimeType2, u... params2) {
        a.a(c(((String) a.d(mimeType2, "MIME type")).toLowerCase(Locale.ROOT)), "MIME type may not contain reserved characters");
        return a(mimeType2, params2, true);
    }

    public static f parse(String s) {
        a.i(s, "Content type");
        d buf = new d(s.length());
        buf.append(s);
        e[] elements = g.b.b(buf, new v(0, s.length()));
        if (elements.length > 0) {
            return b(elements[0], true);
        }
        throw new ParseException("Invalid content type: " + s);
    }

    public static f get(org.apache.http.j entity) {
        org.apache.http.d header;
        if (!(entity == null || (header = entity.getContentType()) == null)) {
            e[] elements = header.getElements();
            if (elements.length > 0) {
                return b(elements[0], true);
            }
        }
        return null;
    }

    public static f getLenient(org.apache.http.j entity) {
        org.apache.http.d header;
        if (!(entity == null || (header = entity.getContentType()) == null)) {
            try {
                e[] elements = header.getElements();
                if (elements.length > 0) {
                    return b(elements[0], false);
                }
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    public static f getOrDefault(org.apache.http.j entity) {
        f contentType = get(entity);
        return contentType != null ? contentType : DEFAULT_TEXT;
    }

    public static f getLenientOrDefault(org.apache.http.j entity) {
        f contentType = get(entity);
        return contentType != null ? contentType : DEFAULT_TEXT;
    }

    public static f getByMimeType(String mimeType2) {
        if (mimeType2 == null) {
            return null;
        }
        return c.get(mimeType2);
    }

    public f withCharset(Charset charset2) {
        return create(getMimeType(), charset2);
    }

    public f withCharset(String charset2) {
        return create(getMimeType(), charset2);
    }

    public f withParameters(u... params2) {
        if (params2.length == 0) {
            return this;
        }
        Map<String, String> paramMap = new LinkedHashMap<>();
        if (this.params != null) {
            for (u param : this.params) {
                paramMap.put(param.getName(), param.getValue());
            }
        }
        for (u param2 : params2) {
            paramMap.put(param2.getName(), param2.getValue());
        }
        List<NameValuePair> newParams = new ArrayList<>(paramMap.size() + 1);
        if (this.charset != null && !paramMap.containsKey("charset")) {
            newParams.add(new m("charset", this.charset.name()));
        }
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            newParams.add(new m(entry.getKey(), entry.getValue()));
        }
        return a(getMimeType(), (u[]) newParams.toArray(new u[newParams.size()]), true);
    }
}
