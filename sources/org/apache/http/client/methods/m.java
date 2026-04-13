package org.apache.http.client.methods;

import java.net.URI;
import org.apache.http.client.config.a;
import org.apache.http.message.n;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.v;
import org.apache.http.x;

/* compiled from: HttpRequestBase */
public abstract class m extends b implements p, d {
    private v x;
    private URI y;
    private a z;

    public abstract String getMethod();

    public void h(v version) {
        this.x = version;
    }

    public v getProtocolVersion() {
        v vVar = this.x;
        return vVar != null ? vVar : HttpProtocolParams.getVersion(getParams());
    }

    public URI t() {
        return this.y;
    }

    public x r() {
        String method = getMethod();
        v ver = getProtocolVersion();
        URI uriCopy = t();
        String uritext = null;
        if (uriCopy != null) {
            uritext = uriCopy.toASCIIString();
        }
        if (uritext == null || uritext.isEmpty()) {
            uritext = "/";
        }
        return new n(method, uritext, ver);
    }

    public a getConfig() {
        return this.z;
    }

    public void f(a config) {
        this.z = config;
    }

    public void k(URI uri) {
        this.y = uri;
    }

    public String toString() {
        return getMethod() + " " + t() + " " + getProtocolVersion();
    }
}
