package net.sbbi.upnp.devices;

import java.net.URL;
import java.util.List;
import java.util.logging.Logger;
import net.sbbi.upnp.services.e;

/* compiled from: UPNPDevice */
public class b {
    private static final Logger a = Logger.getLogger(b.class.getName());
    protected String b;
    protected String c;
    protected String d;
    protected URL e;
    protected URL f;
    protected String g;
    protected String h;
    protected String i;
    protected String j;
    protected String k;
    protected String l;
    protected String m;
    protected long n;
    protected List o;
    protected List p;
    protected List q;
    protected b r;

    public URL e() {
        return this.e;
    }

    public String f() {
        return this.g;
    }

    public String g() {
        return this.h;
    }

    public String h() {
        return this.i;
    }

    public String i() {
        return this.j;
    }

    public String j() {
        return this.k;
    }

    public String l() {
        return this.l;
    }

    public String b() {
        return this.b;
    }

    public String c() {
        return this.c;
    }

    public String d() {
        return this.d;
    }

    public b a(String deviceURI) {
        Logger logger = a;
        logger.fine("searching for device URI:" + deviceURI);
        if (b().equals(deviceURI)) {
            return this;
        }
        List<b> list = this.q;
        if (list == null) {
            return null;
        }
        for (b device : list) {
            b found = device.a(deviceURI);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public e k(String serviceURI) {
        if (this.p == null) {
            return null;
        }
        Logger logger = a;
        logger.fine("searching for service URI:" + serviceURI);
        for (e service : this.p) {
            if (service.c().equals(serviceURI)) {
                return service;
            }
        }
        return null;
    }

    public String toString() {
        return b();
    }
}
