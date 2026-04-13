package org.apache.http.conn.routing;

import java.net.InetAddress;
import org.apache.http.l;

/* compiled from: RouteInfo */
public interface e {

    /* compiled from: RouteInfo */
    public enum a {
        PLAIN,
        LAYERED
    }

    /* compiled from: RouteInfo */
    public enum b {
        PLAIN,
        TUNNELLED
    }

    int a();

    boolean b();

    l c();

    l d(int i);

    l e();

    boolean f();

    InetAddress getLocalAddress();

    boolean isSecure();
}
