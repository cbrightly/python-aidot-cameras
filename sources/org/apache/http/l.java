package org.apache.http;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Locale;
import org.apache.http.util.a;
import org.apache.http.util.h;

/* compiled from: HttpHost */
public final class l implements Cloneable, Serializable {
    public static final String DEFAULT_SCHEME_NAME = "http";
    private static final long serialVersionUID = -7529410654042457626L;
    protected final InetAddress address;
    protected final String hostname;
    protected final String lcHostname;
    protected final int port;
    protected final String schemeName;

    public l(String hostname2, int port2, String scheme) {
        this.hostname = (String) a.c(hostname2, "Host name");
        Locale locale = Locale.ROOT;
        this.lcHostname = hostname2.toLowerCase(locale);
        if (scheme != null) {
            this.schemeName = scheme.toLowerCase(locale);
        } else {
            this.schemeName = DEFAULT_SCHEME_NAME;
        }
        this.port = port2;
        this.address = null;
    }

    public l(String hostname2, int port2) {
        this(hostname2, port2, (String) null);
    }

    public static l create(String s) {
        a.c(s, "HTTP Host");
        String text = s;
        String scheme = null;
        int schemeIdx = text.indexOf("://");
        if (schemeIdx > 0) {
            scheme = text.substring(0, schemeIdx);
            text = text.substring(schemeIdx + 3);
        }
        int port2 = -1;
        int portIdx = text.lastIndexOf(":");
        if (portIdx > 0) {
            try {
                port2 = Integer.parseInt(text.substring(portIdx + 1));
                text = text.substring(0, portIdx);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid HTTP host: " + text);
            }
        }
        return new l(text, port2, scheme);
    }

    public l(String hostname2) {
        this(hostname2, -1, (String) null);
    }

    public l(InetAddress address2, int port2, String scheme) {
        this((InetAddress) a.i(address2, "Inet address"), address2.getHostName(), port2, scheme);
    }

    public l(InetAddress address2, String hostname2, int port2, String scheme) {
        this.address = (InetAddress) a.i(address2, "Inet address");
        String str = (String) a.i(hostname2, "Hostname");
        this.hostname = str;
        Locale locale = Locale.ROOT;
        this.lcHostname = str.toLowerCase(locale);
        if (scheme != null) {
            this.schemeName = scheme.toLowerCase(locale);
        } else {
            this.schemeName = DEFAULT_SCHEME_NAME;
        }
        this.port = port2;
    }

    public l(InetAddress address2, int port2) {
        this(address2, port2, (String) null);
    }

    public l(InetAddress address2) {
        this(address2, -1, (String) null);
    }

    public l(l httphost) {
        a.i(httphost, "HTTP host");
        this.hostname = httphost.hostname;
        this.lcHostname = httphost.lcHostname;
        this.schemeName = httphost.schemeName;
        this.port = httphost.port;
        this.address = httphost.address;
    }

    public String getHostName() {
        return this.hostname;
    }

    public int getPort() {
        return this.port;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public InetAddress getAddress() {
        return this.address;
    }

    public String toURI() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.schemeName);
        buffer.append("://");
        buffer.append(this.hostname);
        if (this.port != -1) {
            buffer.append(':');
            buffer.append(Integer.toString(this.port));
        }
        return buffer.toString();
    }

    public String toHostString() {
        if (this.port == -1) {
            return this.hostname;
        }
        StringBuilder buffer = new StringBuilder(this.hostname.length() + 6);
        buffer.append(this.hostname);
        buffer.append(":");
        buffer.append(Integer.toString(this.port));
        return buffer.toString();
    }

    public String toString() {
        return toURI();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof l)) {
            return false;
        }
        l that = (l) obj;
        if (this.lcHostname.equals(that.lcHostname) && this.port == that.port && this.schemeName.equals(that.schemeName)) {
            InetAddress inetAddress = this.address;
            if (inetAddress == null) {
                if (that.address == null) {
                    return true;
                }
            } else if (inetAddress.equals(that.address)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int hash = h.d(h.c(h.d(17, this.lcHostname), this.port), this.schemeName);
        InetAddress inetAddress = this.address;
        if (inetAddress != null) {
            return h.d(hash, inetAddress);
        }
        return hash;
    }

    public Object clone() {
        return super.clone();
    }
}
