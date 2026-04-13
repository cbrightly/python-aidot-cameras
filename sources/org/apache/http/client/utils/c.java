package org.apache.http.client.utils;

import com.meituan.robust.Constants;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.conn.util.b;
import org.apache.http.u;

/* compiled from: URIBuilder */
public class c {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private int g;
    private String h;
    private String i;
    private String j;
    private List<u> k;
    private String l;
    private Charset m;
    private String n;
    private String o;

    public c(URI uri) {
        d(uri);
    }

    public c o(Charset charset) {
        this.m = charset;
        return this;
    }

    private List<u> n(String query, Charset charset) {
        if (query == null || query.isEmpty()) {
            return null;
        }
        return e.h(query, charset);
    }

    public URI b() {
        return new URI(c());
    }

    private String c() {
        StringBuilder sb = new StringBuilder();
        String str = this.a;
        if (str != null) {
            sb.append(str);
            sb.append(':');
        }
        String str2 = this.b;
        if (str2 != null) {
            sb.append(str2);
        } else {
            if (this.c != null) {
                sb.append("//");
                sb.append(this.c);
            } else if (this.f != null) {
                sb.append("//");
                String str3 = this.e;
                if (str3 != null) {
                    sb.append(str3);
                    sb.append("@");
                } else {
                    String str4 = this.d;
                    if (str4 != null) {
                        sb.append(h(str4));
                        sb.append("@");
                    }
                }
                if (b.b(this.f)) {
                    sb.append(Constants.ARRAY_TYPE);
                    sb.append(this.f);
                    sb.append("]");
                } else {
                    sb.append(this.f);
                }
                if (this.g >= 0) {
                    sb.append(":");
                    sb.append(this.g);
                }
            }
            String str5 = this.i;
            if (str5 != null) {
                sb.append(m(str5));
            } else {
                String str6 = this.h;
                if (str6 != null) {
                    sb.append(e(m(str6)));
                }
            }
            if (this.j != null) {
                sb.append("?");
                sb.append(this.j);
            } else if (this.k != null) {
                sb.append("?");
                sb.append(g(this.k));
            } else if (this.l != null) {
                sb.append("?");
                sb.append(f(this.l));
            }
        }
        if (this.o != null) {
            sb.append("#");
            sb.append(this.o);
        } else if (this.n != null) {
            sb.append("#");
            sb.append(f(this.n));
        }
        return sb.toString();
    }

    private void d(URI uri) {
        this.a = uri.getScheme();
        this.b = uri.getRawSchemeSpecificPart();
        this.c = uri.getRawAuthority();
        this.f = uri.getHost();
        this.g = uri.getPort();
        this.e = uri.getRawUserInfo();
        this.d = uri.getUserInfo();
        this.i = uri.getRawPath();
        this.h = uri.getPath();
        this.j = uri.getRawQuery();
        String rawQuery = uri.getRawQuery();
        Charset charset = this.m;
        if (charset == null) {
            charset = org.apache.http.b.a;
        }
        this.k = n(rawQuery, charset);
        this.o = uri.getRawFragment();
        this.n = uri.getFragment();
    }

    private String h(String userInfo) {
        Charset charset = this.m;
        if (charset == null) {
            charset = org.apache.http.b.a;
        }
        return e.d(userInfo, charset);
    }

    private String e(String path) {
        Charset charset = this.m;
        if (charset == null) {
            charset = org.apache.http.b.a;
        }
        return e.b(path, charset);
    }

    private String g(List<u> params) {
        Charset charset = this.m;
        if (charset == null) {
            charset = org.apache.http.b.a;
        }
        return e.g(params, charset);
    }

    private String f(String fragment) {
        Charset charset = this.m;
        if (charset == null) {
            charset = org.apache.http.b.a;
        }
        return e.c(fragment, charset);
    }

    public c t(String scheme) {
        this.a = scheme;
        return this;
    }

    public c u(String userInfo) {
        this.d = userInfo;
        this.b = null;
        this.c = null;
        this.e = null;
        return this;
    }

    public c q(String host) {
        this.f = host;
        this.b = null;
        this.c = null;
        return this;
    }

    public c s(int port) {
        this.g = port < 0 ? -1 : port;
        this.b = null;
        this.c = null;
        return this;
    }

    public c r(String path) {
        this.h = path;
        this.b = null;
        this.i = null;
        return this;
    }

    public c a(List<u> nvps) {
        if (this.k == null) {
            this.k = new ArrayList();
        }
        this.k.addAll(nvps);
        this.j = null;
        this.b = null;
        this.l = null;
        return this;
    }

    public c p(String fragment) {
        this.n = fragment;
        this.o = null;
        return this;
    }

    public String k() {
        return this.a;
    }

    public String l() {
        return this.d;
    }

    public String i() {
        return this.f;
    }

    public String j() {
        return this.h;
    }

    public String toString() {
        return c();
    }

    private static String m(String path) {
        String s = path;
        if (s == null) {
            return "/";
        }
        int n2 = 0;
        while (n2 < s.length() && s.charAt(n2) == '/') {
            n2++;
        }
        if (n2 > 1) {
            s = s.substring(n2 - 1);
        }
        if (s.startsWith("/")) {
            return s;
        }
        return "/" + s;
    }
}
