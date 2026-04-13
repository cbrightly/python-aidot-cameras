package net.sbbi.upnp.impls;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sbbi.upnp.devices.b;
import net.sbbi.upnp.devices.c;
import net.sbbi.upnp.messages.UPNPResponseException;
import net.sbbi.upnp.messages.d;
import net.sbbi.upnp.services.e;
import org.glassfish.grizzly.http.server.NetworkListener;

/* compiled from: InternetGatewayDevice */
public class a {
    private static final Logger a = Logger.getLogger(a.class.getName());
    private c b;
    private d c;

    private a(c igd, boolean WANIPConnection, boolean WANPPPConnection) {
        this.b = igd;
        b myIGDWANConnDevice = igd.a("urn:schemas-upnp-org:device:WANConnectionDevice:1");
        if (myIGDWANConnDevice != null) {
            e wanIPSrv = myIGDWANConnDevice.k("urn:schemas-upnp-org:service:WANIPConnection:1");
            e wanPPPSrv = myIGDWANConnDevice.k("urn:schemas-upnp-org:service:WANPPPConnection:1");
            if (WANIPConnection && WANPPPConnection && wanIPSrv == null && wanPPPSrv == null) {
                throw new UnsupportedOperationException("Unable to find any urn:schemas-upnp-org:service:WANIPConnection:1 or urn:schemas-upnp-org:service:WANPPPConnection:1 service");
            } else if (WANIPConnection && !WANPPPConnection && wanIPSrv == null) {
                throw new UnsupportedOperationException("Unable to find any urn:schemas-upnp-org:service:WANIPConnection:1 service");
            } else if (!WANIPConnection && WANPPPConnection && wanPPPSrv == null) {
                throw new UnsupportedOperationException("Unable to find any urn:schemas-upnp-org:service:WANPPPConnection:1 service");
            } else if (wanIPSrv != null && wanPPPSrv == null) {
                this.c = d.b(wanIPSrv);
            } else if (wanPPPSrv == null || wanIPSrv != null) {
                if (d(wanIPSrv)) {
                    this.c = d.b(wanIPSrv);
                } else if (d(wanPPPSrv)) {
                    this.c = d.b(wanPPPSrv);
                }
                if (this.c == null) {
                    a.warning("Unable to detect active WANIPConnection, dfaulting to urn:schemas-upnp-org:service:WANIPConnection:1");
                    this.c = d.b(wanIPSrv);
                }
            } else {
                this.c = d.b(wanPPPSrv);
            }
        } else {
            throw new UnsupportedOperationException("device urn:schemas-upnp-org:device:WANConnectionDevice:1 not supported by IGD device " + igd.g());
        }
    }

    private boolean d(e srv) {
        String ipToParse = null;
        try {
            ipToParse = d.b(srv).a("GetExternalIPAddress").b().b("NewExternalIPAddress");
        } catch (UPNPResponseException e) {
        } catch (IOException ex) {
            a.log(Level.WARNING, "IOException occured during device detection", ex);
        }
        if (ipToParse != null && ipToParse.length() > 0 && !ipToParse.equals(NetworkListener.DEFAULT_NETWORK_HOST)) {
            try {
                if (InetAddress.getByName(ipToParse) != null) {
                    return true;
                }
                return false;
            } catch (UnknownHostException e2) {
            }
        }
        return false;
    }

    public c b() {
        return this.b;
    }

    public static a[] a(int timeout) {
        return c(timeout, 4, 3, true, true, (NetworkInterface) null);
    }

    private static a[] c(int timeout, int ttl, int mx, boolean WANIPConnection, boolean WANPPPConnection, NetworkInterface ni) {
        c[] devices;
        if (timeout == -1) {
            devices = net.sbbi.upnp.a.b(1500, ttl, mx, "urn:schemas-upnp-org:device:InternetGatewayDevice:1", ni);
        } else {
            devices = net.sbbi.upnp.a.b(timeout, ttl, mx, "urn:schemas-upnp-org:device:InternetGatewayDevice:1", ni);
        }
        if (devices == null) {
            return null;
        }
        Set<a> valid = new HashSet<>();
        for (int i = 0; i < devices.length; i++) {
            try {
                valid.add(new a(devices[i], WANIPConnection, WANPPPConnection));
            } catch (UnsupportedOperationException ex) {
                Logger logger = a;
                logger.fine("UnsupportedOperationException during discovery " + ex.getMessage());
            }
        }
        if (valid.size() == 0) {
            return null;
        }
        a[] rtrVal = new a[valid.size()];
        int i2 = 0;
        for (a aVar : valid) {
            rtrVal[i2] = aVar;
            i2++;
        }
        return rtrVal;
    }
}
