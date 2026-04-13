package net.sbbi.upnp;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.bugly.Bugly;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.NetworkListener;

/* compiled from: DiscoveryListener */
public class b implements Runnable {
    private static final Logger c = Logger.getLogger(b.class.getName());
    private static boolean d;
    private static final b f = new b();
    private DatagramPacket a1;
    private MulticastSocket p0;
    private Map q = new HashMap();
    private final Object x = new Object();
    private boolean y = false;
    private boolean z = true;

    static {
        d = true;
        String prop = System.getProperty("net.sbbi.upnp.ddos.matchip");
        if (prop != null && prop.equals(Bugly.SDK_IS_DEV)) {
            d = false;
        }
    }

    private b() {
    }

    public static final b a() {
        return f;
    }

    public void c(c resultsHandler, String searchTarget) {
        synchronized (this.x) {
            if (!this.y) {
                d();
            }
            Set handlers = (Set) this.q.get(searchTarget);
            if (handlers == null) {
                handlers = new HashSet();
                this.q.put(searchTarget, handlers);
            }
            handlers.add(resultsHandler);
        }
    }

    public void g(c resultsHandler, String searchTarget) {
        synchronized (this.x) {
            Set handlers = (Set) this.q.get(searchTarget);
            if (handlers != null) {
                handlers.remove(resultsHandler);
                if (handlers.size() == 0) {
                    this.q.remove(searchTarget);
                }
            }
            if (this.q.size() == 0) {
                f();
            }
        }
    }

    private void d() {
        synchronized (f) {
            if (!this.y) {
                e();
                Thread deamon = new Thread(this, "DiscoveryListener daemon");
                deamon.setDaemon(this.z);
                deamon.start();
                while (!this.y) {
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    private void f() {
        synchronized (f) {
            this.y = false;
        }
    }

    private void e() {
        int bindPort = 1901;
        String port = System.getProperty("net.sbbi.upnp.Discovery.bindPort");
        if (port != null) {
            bindPort = Integer.parseInt(port);
        }
        MulticastSocket multicastSocket = new MulticastSocket((SocketAddress) null);
        this.p0 = multicastSocket;
        multicastSocket.bind(new InetSocketAddress(InetAddress.getByName(NetworkListener.DEFAULT_NETWORK_HOST), bindPort));
        this.p0.setTimeToLive(4);
        this.p0.setSoTimeout(250);
        this.p0.joinGroup(InetAddress.getByName("239.255.255.250"));
        byte[] buf = new byte[2048];
        this.a1 = new DatagramPacket(buf, buf.length);
    }

    public void run() {
        if (Thread.currentThread().getName().equals("DiscoveryListener daemon")) {
            this.y = true;
            while (this.y) {
                try {
                    b();
                } catch (SocketTimeoutException e) {
                } catch (IOException ioEx) {
                    c.log(Level.SEVERE, "IO Exception during UPNP DiscoveryListener messages listening thread", ioEx);
                } catch (Exception ex) {
                    c.log(Level.SEVERE, "Fatal Error during UPNP DiscoveryListener messages listening thread, thread will exit", ex);
                    this.y = false;
                }
            }
            try {
                this.p0.leaveGroup(InetAddress.getByName("239.255.255.250"));
                this.p0.close();
            } catch (Exception e2) {
            }
        } else {
            throw new RuntimeException("No right to call this method");
        }
    }

    private void b() {
        Object obj;
        Object obj2;
        this.p0.receive(this.a1);
        InetAddress from = this.a1.getAddress();
        String received = new String(this.a1.getData(), this.a1.getOffset(), this.a1.getLength());
        try {
            d msg = new d(received);
            String header = msg.c();
            if (header == null || !header.startsWith("HTTP/1.1 200 OK") || msg.b("st") == null) {
                Logger logger = c;
                logger.fine("Skipping uncompliant HTTP message " + received);
                return;
            }
            String deviceDescrLoc = msg.b(FirebaseAnalytics.Param.LOCATION);
            if (deviceDescrLoc == null || deviceDescrLoc.trim().length() == 0) {
                c.fine("Skipping SSDP message, missing HTTP header 'location' field");
                return;
            }
            URL loc = new URL(deviceDescrLoc);
            if (d) {
                InetAddress locHost = InetAddress.getByName(loc.getHost());
                if (!from.equals(locHost)) {
                    Logger logger2 = c;
                    logger2.warning("Discovery message sender IP " + from + " does not match device description IP " + locHost + " skipping device, set the net.sbbi.upnp.ddos.matchip system property" + " to false to avoid this check");
                    return;
                }
            }
            Logger logger3 = c;
            logger3.fine("Processing " + deviceDescrLoc + " device description location");
            String st = msg.b("st");
            if (st == null || st.trim().length() == 0) {
                logger3.fine("Skipping SSDP message, missing HTTP header 'st' field");
                return;
            }
            String usn = msg.b("usn");
            if (usn == null || usn.trim().length() == 0) {
                logger3.fine("Skipping SSDP message, missing HTTP header 'usn' field");
                return;
            }
            String maxAge = msg.a(HttpHeaders.HEAD_KEY_CACHE_CONTROL, "max-age");
            if (maxAge == null || maxAge.trim().length() == 0) {
                logger3.fine("Skipping SSDP message, missing HTTP header 'max-age' field");
                return;
            }
            String server = msg.b("server");
            if (server == null || server.trim().length() == 0) {
                logger3.fine("Skipping SSDP message, missing HTTP header 'server' field");
                return;
            }
            String udn = usn;
            int index = udn.indexOf("::");
            if (index != -1) {
                udn = udn.substring(0, index);
            }
            String udn2 = udn;
            Object obj3 = this.x;
            synchronized (obj3) {
                try {
                    Set<c> handlers = (Set) this.q.get(st);
                    if (handlers != null) {
                        for (c handler : handlers) {
                            obj = obj3;
                            int index2 = index;
                            handler.a(usn, udn2, st, maxAge, loc, server);
                            obj3 = obj;
                            index = index2;
                        }
                        obj2 = obj3;
                        int i = index;
                    } else {
                        obj2 = obj3;
                        int i2 = index;
                    }
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            }
        } catch (IllegalArgumentException e) {
            Logger logger4 = c;
            logger4.fine("Skipping uncompliant HTTP message " + received);
        }
    }
}
