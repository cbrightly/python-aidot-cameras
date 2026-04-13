package net.sbbi.upnp;

import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sbbi.upnp.devices.c;

/* compiled from: Discovery */
public class a {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger(a.class.getName());

    public static c[] b(int timeOut, int ttl, int mx, String searchTarget, NetworkInterface ni) {
        return c(timeOut, ttl, mx, searchTarget, ni);
    }

    private static c[] c(int timeOut, int ttl, int mx, String searchTarget, NetworkInterface ni) {
        if (searchTarget == null || searchTarget.trim().length() == 0) {
            throw new IllegalArgumentException("Illegal searchTarget");
        }
        Map devices = new HashMap();
        c handler = new C0453a(devices);
        b.a().c(handler, searchTarget);
        if (ni == null) {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                Enumeration adrs = e.nextElement().getInetAddresses();
                while (adrs.hasMoreElements()) {
                    InetAddress adr = adrs.nextElement();
                    if ((adr instanceof Inet4Address) && !adr.isLoopbackAddress()) {
                        d(adr, ttl, mx, searchTarget);
                    }
                }
            }
        } else {
            Enumeration adrs2 = ni.getInetAddresses();
            while (adrs2.hasMoreElements()) {
                InetAddress adr2 = adrs2.nextElement();
                if ((adr2 instanceof Inet4Address) && !adr2.isLoopbackAddress()) {
                    d(adr2, ttl, mx, searchTarget);
                }
            }
        }
        try {
            Thread.sleep((long) timeOut);
        } catch (InterruptedException e2) {
        }
        b.a().g(handler, searchTarget);
        if (devices.size() == 0) {
            return null;
        }
        int j = 0;
        c[] rootDevices = new c[devices.size()];
        for (c cVar : devices.values()) {
            rootDevices[j] = cVar;
            j++;
        }
        return rootDevices;
    }

    /* renamed from: net.sbbi.upnp.a$a  reason: collision with other inner class name */
    /* compiled from: Discovery */
    public class C0453a implements c {
        private final /* synthetic */ Map a;

        C0453a(Map map) {
            this.a = map;
        }

        public void a(String usn, String udn, String nt, String maxAge, URL location, String firmware) {
            synchronized (this.a) {
                if (!this.a.containsKey(usn)) {
                    try {
                        this.a.put(usn, new c(location, maxAge, firmware, usn, udn));
                    } catch (Exception ex) {
                        Logger a2 = a.a;
                        Level level = Level.SEVERE;
                        a2.log(level, "Error occured during upnp root device object creation from location " + location, ex);
                    }
                }
            }
        }
    }

    public static void d(InetAddress src, int ttl, int mx, String searchTarget) {
        int bindPort = 1901;
        String port = System.getProperty("net.sbbi.upnp.Discovery.bindPort");
        if (port != null) {
            bindPort = Integer.parseInt(port);
        }
        InetSocketAddress adr = new InetSocketAddress(InetAddress.getByName("239.255.255.250"), 1900);
        MulticastSocket skt = new MulticastSocket((SocketAddress) null);
        skt.bind(new InetSocketAddress(src, bindPort));
        skt.setTimeToLive(ttl);
        StringBuffer packet = new StringBuffer();
        packet.append("M-SEARCH * HTTP/1.1\r\n");
        packet.append("HOST: 239.255.255.250:1900\r\n");
        packet.append("MAN: \"ssdp:discover\"\r\n");
        packet.append("MX: ");
        packet.append(mx);
        packet.append("\r\n");
        packet.append("ST: ");
        packet.append(searchTarget);
        packet.append("\r\n");
        packet.append("\r\n");
        Logger logger = a;
        logger.fine("Sending discovery message on 239.255.255.250:1900 multicast address form ip " + src.getHostAddress() + ":\n" + packet.toString());
        byte[] pk = packet.toString().getBytes();
        skt.send(new DatagramPacket(pk, pk.length, adr));
        skt.disconnect();
        skt.close();
    }
}
