package net.sbbi.upnp.messages;

import java.util.logging.Logger;
import net.sbbi.upnp.services.a;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/* compiled from: ActionMessageResponseParser */
public class b extends DefaultHandler {
    private static final Logger a = Logger.getLogger(b.class.getName());
    private a b;
    private String c;
    private boolean d = false;
    private UPNPResponseException e;
    private boolean f = false;
    private boolean g = false;
    private boolean h = false;
    private boolean i = false;
    private boolean j = false;
    private c k;
    private net.sbbi.upnp.services.b l;
    private StringBuffer m = new StringBuffer();

    protected b(a serviceAction) {
        this.b = serviceAction;
        this.c = String.valueOf(serviceAction.e()) + "Response";
    }

    /* access modifiers changed from: protected */
    public UPNPResponseException b() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public c a() {
        return this.k;
    }

    public void characters(char[] ch, int start, int length) {
        if (this.j) {
            if (this.l != null) {
                this.m.append(ch, start, length);
            }
        } else if (this.f) {
            this.e.faultCode = new String(ch, start, length);
            this.f = false;
        } else if (this.g) {
            this.e.faultString = new String(ch, start, length);
            this.g = false;
        } else if (this.h) {
            String code = new String(ch, start, length);
            try {
                this.e.detailErrorCode = Integer.parseInt(code);
            } catch (Throwable th) {
                Logger logger = a;
                logger.fine("Error during returned error code " + code + " parsing");
            }
            this.h = false;
        } else if (this.i) {
            this.e.detailErrorDescription = new String(ch, start, length);
            this.i = false;
        }
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (this.j) {
            net.sbbi.upnp.services.b arg = this.b.a(localName);
            if (arg == null || arg.a() != "out") {
                this.l = null;
                return;
            }
            this.l = arg;
            this.k.a(arg, (String) null);
        } else if (this.d) {
            if (localName.equals("faultcode")) {
                this.f = true;
            } else if (localName.equals("faultstring")) {
                this.g = true;
            } else if (localName.equals("errorCode")) {
                this.h = true;
            } else if (localName.equals("errorDescription")) {
                this.i = true;
            }
        } else if (localName.equals("Fault")) {
            this.e = new UPNPResponseException();
            this.d = true;
        } else if (localName.equals(this.c)) {
            this.j = true;
            this.k = new c();
        }
    }

    public void endElement(String uri, String localName, String qName) {
        net.sbbi.upnp.services.b bVar = this.l;
        if (bVar != null && bVar.b().equals(localName)) {
            this.k.a(this.l, this.m.toString());
            this.l = null;
            this.m = new StringBuffer();
        } else if (localName.equals(this.c)) {
            this.j = false;
        }
    }
}
