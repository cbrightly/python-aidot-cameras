package org.glassfish.grizzly.http;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.http.util.CookieSerializerUtils;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.utils.Charsets;

public class Cookie implements Cloneable, Cacheable {
    public static final int UNSET = Integer.MIN_VALUE;
    private static final String tspecials = ",; ";
    protected String comment;
    protected String domain;
    protected boolean isHttpOnly;
    protected LazyCookieState lazyCookieState;
    protected int maxAge = -1;
    protected String name;
    protected String path;
    protected boolean secure;
    protected boolean usingLazyCookieState;
    protected String value;
    protected int version = Integer.MIN_VALUE;

    protected Cookie() {
    }

    public Cookie(String name2, String value2) {
        this.name = name2;
        this.value = value2;
    }

    public void setComment(String purpose) {
        this.comment = purpose;
    }

    public String getComment() {
        if (this.comment == null && this.usingLazyCookieState) {
            this.comment = this.version == 1 ? unescape(this.lazyCookieState.getComment().toString(Charsets.ASCII_CHARSET)) : null;
        }
        return this.comment;
    }

    public void setDomain(String pattern) {
        if (pattern != null) {
            this.domain = pattern.toLowerCase();
        }
    }

    public String getDomain() {
        String domainStr;
        if (this.domain == null && this.usingLazyCookieState && (domainStr = this.lazyCookieState.getDomain().toString(Charsets.ASCII_CHARSET)) != null) {
            this.domain = unescape(domainStr);
        }
        return this.domain;
    }

    public void setMaxAge(int expiry) {
        this.maxAge = expiry;
    }

    public int getMaxAge() {
        return this.maxAge;
    }

    public void setPath(String uri) {
        this.path = uri;
    }

    public String getPath() {
        if (this.path == null && this.usingLazyCookieState) {
            this.path = unescape(this.lazyCookieState.getPath().toString(Charsets.ASCII_CHARSET));
        }
        return this.path;
    }

    public void setSecure(boolean flag) {
        this.secure = flag;
    }

    public boolean isSecure() {
        return this.secure;
    }

    public String getName() {
        if (this.name == null && this.usingLazyCookieState) {
            this.name = this.lazyCookieState.getName().toString(Charsets.ASCII_CHARSET);
        }
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public void setValue(String newValue) {
        this.value = newValue;
    }

    public String getValue() {
        if (this.value == null && this.usingLazyCookieState) {
            this.value = unescape(this.lazyCookieState.getValue().toString(Charsets.ASCII_CHARSET));
        }
        return this.value;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int v) {
        if (v < 0 || v > 1) {
            throw new IllegalArgumentException("Illegal Cookie Version");
        }
        this.version = v;
    }

    public boolean isVersionSet() {
        return this.version != Integer.MIN_VALUE;
    }

    public boolean isHttpOnly() {
        return this.isHttpOnly;
    }

    public void setHttpOnly(boolean isHttpOnly2) {
        this.isHttpOnly = isHttpOnly2;
    }

    public String asServerCookieString() {
        StringBuilder sb = new StringBuilder();
        CookieSerializerUtils.serializeServerCookie(sb, this);
        return sb.toString();
    }

    public Buffer asServerCookieBuffer() {
        return asServerCookieBuffer((MemoryManager) null);
    }

    public Buffer asServerCookieBuffer(MemoryManager memoryManager) {
        if (memoryManager == null) {
            memoryManager = MemoryManager.DEFAULT_MEMORY_MANAGER;
        }
        Buffer buffer = memoryManager.allocate(4096);
        CookieSerializerUtils.serializeServerCookie(buffer, this);
        buffer.trim();
        return buffer;
    }

    public String asClientCookieString() {
        StringBuilder sb = new StringBuilder();
        CookieSerializerUtils.serializeClientCookies(sb, this);
        return sb.toString();
    }

    public Buffer asClientCookieBuffer() {
        return asClientCookieBuffer((MemoryManager) null);
    }

    public Buffer asClientCookieBuffer(MemoryManager memoryManager) {
        if (memoryManager == null) {
            memoryManager = MemoryManager.DEFAULT_MEMORY_MANAGER;
        }
        Buffer buffer = memoryManager.allocate(4096);
        CookieSerializerUtils.serializeClientCookies(buffer, this);
        buffer.trim();
        return buffer;
    }

    public LazyCookieState getLazyCookieState() {
        this.usingLazyCookieState = true;
        if (this.lazyCookieState == null) {
            this.lazyCookieState = new LazyCookieState();
        }
        return this.lazyCookieState;
    }

    public String getCookieHeaderName() {
        return getCookieHeaderName(this.version);
    }

    public static String getCookieHeaderName(int version2) {
        return version2 == 1 ? HttpHeaders.HEAD_KEY_SET_COOKIE : HttpHeaders.HEAD_KEY_SET_COOKIE;
    }

    /* access modifiers changed from: protected */
    public boolean lazyNameEquals(String name2) {
        return this.name.equals(name2);
    }

    /* access modifiers changed from: protected */
    public String unescape(String s) {
        if (s == null) {
            return null;
        }
        if (s.indexOf(92) == -1) {
            return s;
        }
        StringBuilder buf = new StringBuilder();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c != '\\') {
                buf.append(c);
            } else {
                i++;
                if (i < s.length()) {
                    buf.append(s.charAt(i));
                } else {
                    throw new IllegalArgumentException();
                }
            }
            i++;
        }
        return buf.toString();
    }

    private static boolean isToken(String value2) {
        int len = value2.length();
        for (int i = 0; i < len; i++) {
            char c = value2.charAt(i);
            if (c < ' ' || c >= 127 || tspecials.indexOf(c) != -1) {
                return false;
            }
        }
        return true;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void recycle() {
        this.name = null;
        this.value = null;
        this.comment = null;
        this.domain = null;
        this.maxAge = -1;
        this.path = null;
        this.secure = false;
        this.version = Integer.MIN_VALUE;
        this.isHttpOnly = false;
        if (this.usingLazyCookieState) {
            this.usingLazyCookieState = false;
            this.lazyCookieState.recycle();
        }
    }

    public String toString() {
        return "Cookie{" + "name='" + this.name + '\'' + ", value='" + this.value + '\'' + ", comment='" + this.comment + '\'' + ", domain='" + this.domain + '\'' + ", maxAge=" + this.maxAge + ", path='" + this.path + '\'' + ", secure=" + this.secure + ", version=" + this.version + ", isHttpOnly=" + this.isHttpOnly + ", usingLazyCookieState=" + this.usingLazyCookieState + '}';
    }
}
