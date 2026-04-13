package net.sbbi.upnp.messages;

import net.sbbi.upnp.services.a;
import net.sbbi.upnp.services.e;

/* compiled from: UPNPMessageFactory */
public class d {
    private e a;

    private d(e service) {
        this.a = service;
    }

    public static d b(e service) {
        return new d(service);
    }

    public a a(String serviceActionName) {
        a serviceAction = this.a.d(serviceActionName);
        if (serviceAction != null) {
            return new a(this.a, serviceAction);
        }
        return null;
    }
}
