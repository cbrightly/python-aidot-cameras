package org.apache.httpcore.entity;

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
import org.apache.httpcore.NameValuePair;
import org.apache.httpcore.ParseException;
import org.apache.httpcore.b;
import org.apache.httpcore.f;
import org.apache.httpcore.j;
import org.apache.httpcore.message.l;
import org.apache.httpcore.u;
import org.apache.httpcore.util.a;
import org.apache.httpcore.util.d;
import org.apache.httpcore.util.i;

/* compiled from: ContentType */
public final class e implements Serializable {
    public static final e APPLICATION_ATOM_XML;
    public static final e APPLICATION_FORM_URLENCODED;
    public static final e APPLICATION_JSON;
    public static final e APPLICATION_OCTET_STREAM = create(h.APPLICATION_OCTET_STREAM_VALUE, (Charset) null);
    public static final e APPLICATION_SOAP_XML;
    public static final e APPLICATION_SVG_XML;
    public static final e APPLICATION_XHTML_XML;
    public static final e APPLICATION_XML;
    public static final e DEFAULT_BINARY = APPLICATION_OCTET_STREAM;
    public static final e DEFAULT_TEXT = TEXT_PLAIN;
    public static final e IMAGE_BMP;
    public static final e IMAGE_GIF;
    public static final e IMAGE_JPEG;
    public static final e IMAGE_PNG;
    public static final e IMAGE_SVG;
    public static final e IMAGE_TIFF;
    public static final e IMAGE_WEBP;
    public static final e MULTIPART_FORM_DATA;
    public static final e TEXT_HTML;
    public static final e TEXT_PLAIN;
    public static final e TEXT_XML;
    public static final e WILDCARD = create(h.ALL_VALUE, (Charset) null);
    private static final Map<String, e> c;
    private static final long serialVersionUID = -7768694718232371896L;
    private final Charset charset;
    private final String mimeType;
    private final u[] params;

    static {
        Charset charset2 = b.c;
        e create = create(h.APPLICATION_ATOM_XML_VALUE, charset2);
        APPLICATION_ATOM_XML = create;
        e create2 = create("application/x-www-form-urlencoded", charset2);
        APPLICATION_FORM_URLENCODED = create2;
        Charset charset3 = b.a;
        e create3 = create("application/json", charset3);
        APPLICATION_JSON = create3;
        APPLICATION_SOAP_XML = create("application/soap+xml", charset3);
        e create4 = create("application/svg+xml", charset2);
        APPLICATION_SVG_XML = create4;
        e create5 = create(h.APPLICATION_XHTML_XML_VALUE, charset2);
        APPLICATION_XHTML_XML = create5;
        e create6 = create(h.APPLICATION_XML_VALUE, charset2);
        APPLICATION_XML = create6;
        e create7 = create("image/bmp");
        IMAGE_BMP = create7;
        e create8 = create(h.IMAGE_GIF_VALUE);
        IMAGE_GIF = create8;
        e create9 = create("image/jpeg");
        IMAGE_JPEG = create9;
        e create10 = create("image/png");
        IMAGE_PNG = create10;
        e create11 = create("image/svg+xml");
        IMAGE_SVG = create11;
        e create12 = create("image/tiff");
        IMAGE_TIFF = create12;
        e create13 = create("image/webp");
        IMAGE_WEBP = create13;
        e create14 = create(h.MULTIPART_FORM_DATA_VALUE, charset2);
        MULTIPART_FORM_DATA = create14;
        e create15 = create(h.TEXT_HTML_VALUE, charset2);
        TEXT_HTML = create15;
        e eVar = create15;
        e create16 = create(h.TEXT_PLAIN_VALUE, charset2);
        TEXT_PLAIN = create16;
        e create17 = create(h.TEXT_XML_VALUE, charset2);
        TEXT_XML = create17;
        e[] contentTypes = {create, create2, create3, create4, create5, create6, create7, create8, create9, create10, create11, create12, create13, create14, eVar, create16, create17};
        HashMap<String, ContentType> map = new HashMap<>();
        for (e contentType : contentTypes) {
            map.put(contentType.getMimeType(), contentType);
        }
        c = Collections.unmodifiableMap(map);
    }

    e(String mimeType2, Charset charset2) {
        this.mimeType = mimeType2;
        this.charset = charset2;
        this.params = null;
    }

    e(String mimeType2, Charset charset2, u[] params2) {
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
        a.d(name, "Parameter name");
        u[] uVarArr = this.params;
        if (uVarArr == null) {
            return null;
        }
        for (u param : uVarArr) {
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
            org.apache.httpcore.message.d.b.e(buf, this.params, false);
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

    public static e create(String mimeType2, Charset charset2) {
        String normalizedMimeType = ((String) a.c(mimeType2, "MIME type")).toLowerCase(Locale.ROOT);
        a.a(c(normalizedMimeType), "MIME type may not contain reserved characters");
        return new e(normalizedMimeType, charset2);
    }

    public static e create(String mimeType2) {
        return create(mimeType2, (Charset) null);
    }

    public static e create(String mimeType2, String charset2) {
        return create(mimeType2, !i.a(charset2) ? Charset.forName(charset2) : null);
    }

    private static e b(f helem, boolean strict) {
        return a(helem.getName(), helem.getParameters(), strict);
    }

    private static e a(String mimeType2, u[] params2, boolean strict) {
        Charset charset2 = null;
        int length = params2.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            u param = params2[i];
            if (param.getName().equalsIgnoreCase("charset")) {
                String s = param.getValue();
                if (!i.a(s)) {
                    try {
                        charset2 = Charset.forName(s);
                    } catch (UnsupportedCharsetException ex) {
                        if (strict) {
                            throw ex;
                        }
                    }
                }
            } else {
                i++;
            }
        }
        return new e(mimeType2, charset2, params2.length > 0 ? params2 : null);
    }

    public static e create(String mimeType2, u... params2) {
        a.a(c(((String) a.c(mimeType2, "MIME type")).toLowerCase(Locale.ROOT)), "MIME type may not contain reserved characters");
        return a(mimeType2, params2, true);
    }

    public static e parse(String s) {
        a.g(s, "Content type");
        d buf = new d(s.length());
        buf.append(s);
        f[] elements = org.apache.httpcore.message.e.b.a(buf, new org.apache.httpcore.message.u(0, s.length()));
        if (elements.length > 0) {
            return b(elements[0], true);
        }
        throw new ParseException("Invalid content type: " + s);
    }

    public static e get(j entity) {
        org.apache.httpcore.e header;
        if (!(entity == null || (header = entity.getContentType()) == null)) {
            f[] elements = header.getElements();
            if (elements.length > 0) {
                return b(elements[0], true);
            }
        }
        return null;
    }

    public static e getLenient(j entity) {
        org.apache.httpcore.e header;
        if (!(entity == null || (header = entity.getContentType()) == null)) {
            try {
                f[] elements = header.getElements();
                if (elements.length > 0) {
                    return b(elements[0], false);
                }
            } catch (ParseException e) {
            }
        }
        return null;
    }

    public static e getOrDefault(j entity) {
        e contentType = get(entity);
        return contentType != null ? contentType : DEFAULT_TEXT;
    }

    public static e getLenientOrDefault(j entity) {
        e contentType = get(entity);
        return contentType != null ? contentType : DEFAULT_TEXT;
    }

    public static e getByMimeType(String mimeType2) {
        if (mimeType2 == null) {
            return null;
        }
        return c.get(mimeType2);
    }

    public e withCharset(Charset charset2) {
        return create(getMimeType(), charset2);
    }

    public e withCharset(String charset2) {
        return create(getMimeType(), charset2);
    }

    public e withParameters(u... params2) {
        if (params2.length == 0) {
            return this;
        }
        Map<String, String> paramMap = new LinkedHashMap<>();
        u[] uVarArr = this.params;
        if (uVarArr != null) {
            for (u param : uVarArr) {
                paramMap.put(param.getName(), param.getValue());
            }
        }
        for (u param2 : params2) {
            paramMap.put(param2.getName(), param2.getValue());
        }
        List<NameValuePair> newParams = new ArrayList<>(paramMap.size() + 1);
        if (this.charset != null && !paramMap.containsKey("charset")) {
            newParams.add(new l("charset", this.charset.name()));
        }
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            newParams.add(new l(entry.getKey(), entry.getValue()));
        }
        return a(getMimeType(), (u[]) newParams.toArray(new u[newParams.size()]), true);
    }
}
