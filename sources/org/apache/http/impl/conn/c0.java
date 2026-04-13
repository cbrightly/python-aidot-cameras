package org.apache.http.impl.conn;

import java.net.InetAddress;
import org.apache.http.conn.i;

/* compiled from: SystemDefaultDnsResolver */
public class c0 implements i {
    public static final c0 a = new c0();

    public InetAddress[] resolve(String host) {
        return InetAddress.getAllByName(host);
    }
}
