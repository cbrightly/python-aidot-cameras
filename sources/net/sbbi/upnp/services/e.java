package net.sbbi.upnp.services;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.xml.xpath.XPathException;
import net.sbbi.upnp.devices.b;
import net.sbbi.upnp.devices.c;
import net.sbbi.upnp.xpath.a;
import org.w3c.dom.Node;

/* compiled from: UPNPService */
public class e {
    protected String a;
    protected String b;
    private int c;
    private int d;
    protected URL e;
    protected URL f;
    protected URL g;
    protected b h;
    protected Map i;
    protected Map j;
    private String k;
    private boolean l = false;

    public e(a serviceCtx, URL baseDeviceURL, b serviceOwnerDevice) {
        this.h = serviceOwnerDevice;
        this.a = serviceCtx.d("serviceType");
        this.b = serviceCtx.d("serviceId");
        this.e = c.r(serviceCtx.d("SCPDURL"), baseDeviceURL);
        this.f = c.r(serviceCtx.d("controlURL"), baseDeviceURL);
        this.g = c.r(serviceCtx.d("eventSubURL"), baseDeviceURL);
        this.k = serviceOwnerDevice.l().concat("::").concat(this.a);
    }

    public String c() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public URL a() {
        return this.f;
    }

    public a d(String actionName) {
        e();
        return (a) this.i.get(actionName);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x00c4 A[SYNTHETIC, Splitter:B:20:0x00c4] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x01b1 A[Catch:{ all -> 0x01d5 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void f() {
        /*
            r26 = this;
            r1 = r26
            java.lang.String r2 = "in"
            java.lang.String r3 = "]/name"
            java.lang.String r4 = "action["
            java.lang.String r5 = "argument["
            javax.xml.parsers.DocumentBuilderFactory r0 = javax.xml.parsers.DocumentBuilderFactory.newInstance()     // Catch:{ all -> 0x01d5 }
            r6 = r0
            javax.xml.parsers.DocumentBuilder r0 = r6.newDocumentBuilder()     // Catch:{ all -> 0x01d5 }
            r7 = r0
            net.sbbi.upnp.xpath.b r0 = new net.sbbi.upnp.xpath.b     // Catch:{ all -> 0x01d5 }
            java.net.URL r8 = r1.e     // Catch:{ all -> 0x01d5 }
            java.io.InputStream r8 = r8.openStream()     // Catch:{ all -> 0x01d5 }
            r0.<init>(r8)     // Catch:{ all -> 0x01d5 }
            org.w3c.dom.Document r0 = r7.parse(r0)     // Catch:{ all -> 0x01d5 }
            r8 = r0
            net.sbbi.upnp.xpath.a r0 = new net.sbbi.upnp.xpath.a     // Catch:{ all -> 0x01d5 }
            r0.<init>(r8)     // Catch:{ all -> 0x01d5 }
            r9 = r0
            java.lang.String r0 = "scpd"
            org.w3c.dom.Node r0 = r9.b(r0)     // Catch:{ all -> 0x01d5 }
            r10 = r0
            net.sbbi.upnp.xpath.a r0 = r9.c(r10)     // Catch:{ all -> 0x01d5 }
            r11 = r0
            java.lang.String r0 = "specVersion/major"
            java.lang.String r0 = r11.d(r0)     // Catch:{ all -> 0x01d5 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ all -> 0x01d5 }
            r1.c = r0     // Catch:{ all -> 0x01d5 }
            java.lang.String r0 = "specVersion/minor"
            java.lang.String r0 = r11.d(r0)     // Catch:{ all -> 0x01d5 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ all -> 0x01d5 }
            r1.d = r0     // Catch:{ all -> 0x01d5 }
            r1.g(r11)     // Catch:{ all -> 0x01d5 }
            java.lang.String r0 = "actionList"
            org.w3c.dom.Node r0 = r11.b(r0)     // Catch:{ all -> 0x01d5 }
            r12 = r0
            net.sbbi.upnp.xpath.a r0 = r9.c(r12)     // Catch:{ all -> 0x01d5 }
            r13 = r0
            java.lang.String r0 = "count( action )"
            java.lang.Double r0 = r13.a(r0)     // Catch:{ all -> 0x01d5 }
            r14 = r0
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ all -> 0x01d5 }
            r0.<init>()     // Catch:{ all -> 0x01d5 }
            r1.i = r0     // Catch:{ all -> 0x01d5 }
            r0 = 1
            r15 = r0
        L_0x0070:
            int r0 = r14.intValue()     // Catch:{ all -> 0x01d5 }
            if (r15 <= r0) goto L_0x007b
            r0 = 1
            r1.l = r0     // Catch:{ all -> 0x01d5 }
            return
        L_0x007b:
            net.sbbi.upnp.services.a r0 = new net.sbbi.upnp.services.a     // Catch:{ all -> 0x01d5 }
            r0.<init>()     // Catch:{ all -> 0x01d5 }
            r16 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d5 }
            r0.<init>(r4)     // Catch:{ all -> 0x01d5 }
            r0.append(r15)     // Catch:{ all -> 0x01d5 }
            r0.append(r3)     // Catch:{ all -> 0x01d5 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x01d5 }
            java.lang.String r0 = r13.d(r0)     // Catch:{ all -> 0x01d5 }
            r17 = r6
            r6 = r16
            r6.a = r0     // Catch:{ all -> 0x01d5 }
            r6.b = r1     // Catch:{ all -> 0x01d5 }
            r16 = 0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ XPathException -> 0x00bd }
            r0.<init>(r4)     // Catch:{ XPathException -> 0x00bd }
            r0.append(r15)     // Catch:{ XPathException -> 0x00bd }
            r18 = r4
            java.lang.String r4 = "]/argumentList"
            r0.append(r4)     // Catch:{ XPathException -> 0x00bb }
            java.lang.String r0 = r0.toString()     // Catch:{ XPathException -> 0x00bb }
            org.w3c.dom.Node r0 = r13.b(r0)     // Catch:{ XPathException -> 0x00bb }
            r16 = r0
            r0 = r16
            goto L_0x00c2
        L_0x00bb:
            r0 = move-exception
            goto L_0x00c0
        L_0x00bd:
            r0 = move-exception
            r18 = r4
        L_0x00c0:
            r0 = r16
        L_0x00c2:
            if (r0 == 0) goto L_0x01b1
            net.sbbi.upnp.xpath.a r4 = r13.c(r0)     // Catch:{ all -> 0x01d5 }
            r16 = r0
            java.lang.String r0 = "count( argument )"
            java.lang.Double r0 = r4.a(r0)     // Catch:{ all -> 0x01d5 }
            java.util.ArrayList r19 = new java.util.ArrayList     // Catch:{ all -> 0x01d5 }
            r19.<init>()     // Catch:{ all -> 0x01d5 }
            r20 = r19
            r19 = 1
            r21 = r7
            r7 = r19
        L_0x00dd:
            r19 = r8
            int r8 = r0.intValue()     // Catch:{ all -> 0x01d5 }
            if (r7 <= r8) goto L_0x00fe
            int r7 = r0.intValue()     // Catch:{ all -> 0x01d5 }
            if (r7 <= 0) goto L_0x00f6
            r8 = r20
            r6.f(r8)     // Catch:{ all -> 0x01d5 }
            r23 = r3
            r22 = r9
            goto L_0x01bb
        L_0x00f6:
            r8 = r20
            r23 = r3
            r22 = r9
            goto L_0x01bb
        L_0x00fe:
            r8 = r20
            net.sbbi.upnp.services.b r20 = new net.sbbi.upnp.services.b     // Catch:{ all -> 0x01d5 }
            r20.<init>()     // Catch:{ all -> 0x01d5 }
            r22 = r20
            r20 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d5 }
            r0.<init>(r5)     // Catch:{ all -> 0x01d5 }
            r0.append(r7)     // Catch:{ all -> 0x01d5 }
            r0.append(r3)     // Catch:{ all -> 0x01d5 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x01d5 }
            java.lang.String r0 = r4.d(r0)     // Catch:{ all -> 0x01d5 }
            r23 = r3
            r3 = r22
            r3.b = r0     // Catch:{ all -> 0x01d5 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d5 }
            r0.<init>(r5)     // Catch:{ all -> 0x01d5 }
            r0.append(r7)     // Catch:{ all -> 0x01d5 }
            r22 = r9
            java.lang.String r9 = "]/direction"
            r0.append(r9)     // Catch:{ all -> 0x01d5 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x01d5 }
            java.lang.String r0 = r4.d(r0)     // Catch:{ all -> 0x01d5 }
            boolean r9 = r0.equals(r2)     // Catch:{ all -> 0x01d5 }
            if (r9 == 0) goto L_0x0141
            r9 = r2
            goto L_0x0144
        L_0x0141:
            java.lang.String r9 = "out"
        L_0x0144:
            r3.c = r9     // Catch:{ all -> 0x01d5 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d5 }
            r9.<init>(r5)     // Catch:{ all -> 0x01d5 }
            r9.append(r7)     // Catch:{ all -> 0x01d5 }
            r24 = r0
            java.lang.String r0 = "]/relatedStateVariable"
            r9.append(r0)     // Catch:{ all -> 0x01d5 }
            java.lang.String r0 = r9.toString()     // Catch:{ all -> 0x01d5 }
            java.lang.String r0 = r4.d(r0)     // Catch:{ all -> 0x01d5 }
            java.util.Map r9 = r1.j     // Catch:{ all -> 0x01d5 }
            java.lang.Object r9 = r9.get(r0)     // Catch:{ all -> 0x01d5 }
            net.sbbi.upnp.services.c r9 = (net.sbbi.upnp.services.c) r9     // Catch:{ all -> 0x01d5 }
            if (r9 == 0) goto L_0x017b
            r3.a = r9     // Catch:{ all -> 0x01d5 }
            r8.add(r3)     // Catch:{ all -> 0x01d5 }
            int r7 = r7 + 1
            r0 = r20
            r9 = r22
            r3 = r23
            r20 = r8
            r8 = r19
            goto L_0x00dd
        L_0x017b:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x01d5 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d5 }
            r25 = r4
            java.lang.String r4 = "Unable to find any state variable named "
            r5.<init>(r4)     // Catch:{ all -> 0x01d5 }
            r5.append(r0)     // Catch:{ all -> 0x01d5 }
            java.lang.String r4 = " for service "
            r5.append(r4)     // Catch:{ all -> 0x01d5 }
            java.lang.String r4 = r26.b()     // Catch:{ all -> 0x01d5 }
            r5.append(r4)     // Catch:{ all -> 0x01d5 }
            java.lang.String r4 = " action "
            r5.append(r4)     // Catch:{ all -> 0x01d5 }
            java.lang.String r4 = r6.a     // Catch:{ all -> 0x01d5 }
            r5.append(r4)     // Catch:{ all -> 0x01d5 }
            java.lang.String r4 = " argument "
            r5.append(r4)     // Catch:{ all -> 0x01d5 }
            java.lang.String r4 = r3.b     // Catch:{ all -> 0x01d5 }
            r5.append(r4)     // Catch:{ all -> 0x01d5 }
            java.lang.String r4 = r5.toString()     // Catch:{ all -> 0x01d5 }
            r2.<init>(r4)     // Catch:{ all -> 0x01d5 }
            throw r2     // Catch:{ all -> 0x01d5 }
        L_0x01b1:
            r16 = r0
            r23 = r3
            r21 = r7
            r19 = r8
            r22 = r9
        L_0x01bb:
            java.util.Map r0 = r1.i     // Catch:{ all -> 0x01d5 }
            java.lang.String r3 = r6.e()     // Catch:{ all -> 0x01d5 }
            r0.put(r3, r6)     // Catch:{ all -> 0x01d5 }
            int r15 = r15 + 1
            r6 = r17
            r4 = r18
            r8 = r19
            r7 = r21
            r9 = r22
            r3 = r23
            goto L_0x0070
        L_0x01d5:
            r0 = move-exception
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Error during lazy SCDP file parsing at "
            r3.<init>(r4)
            java.net.URL r4 = r1.e
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sbbi.upnp.services.e.f():void");
    }

    private void g(a rootContext) {
        String sendEventsLcl;
        a serviceStateTableCtx = rootContext.c(rootContext.b("serviceStateTable"));
        Double arraySize = serviceStateTableCtx.a("count( stateVariable )");
        this.j = new HashMap();
        for (int i2 = 1; i2 <= arraySize.intValue(); i2++) {
            c srvStateVar = new c();
            try {
                sendEventsLcl = serviceStateTableCtx.d("stateVariable[" + i2 + "]/@sendEvents");
            } catch (XPathException e2) {
                sendEventsLcl = "yes";
            }
            srvStateVar.G = this;
            srvStateVar.z = !sendEventsLcl.equalsIgnoreCase("no");
            srvStateVar.y = serviceStateTableCtx.d("stateVariable[" + i2 + "]/name");
            srvStateVar.A = serviceStateTableCtx.d("stateVariable[" + i2 + "]/dataType");
            try {
                srvStateVar.B = serviceStateTableCtx.d("stateVariable[" + i2 + "]/defaultValue");
            } catch (XPathException e3) {
            }
            Node allowedValuesPtr = null;
            try {
                allowedValuesPtr = serviceStateTableCtx.b("stateVariable[" + i2 + "]/allowedValueList");
            } catch (XPathException e4) {
            }
            if (allowedValuesPtr != null) {
                a allowedValuesCtx = serviceStateTableCtx.c(allowedValuesPtr);
                Double arraySizeAllowed = allowedValuesCtx.a("count( allowedValue )");
                srvStateVar.F = new HashSet();
                for (int z = 1; z <= arraySizeAllowed.intValue(); z++) {
                    srvStateVar.F.add(allowedValuesCtx.d("allowedValue[" + z + "]"));
                }
            }
            Node allowedValueRangePtr = null;
            try {
                allowedValueRangePtr = serviceStateTableCtx.b("stateVariable[" + i2 + "]/allowedValueRange");
            } catch (XPathException e5) {
            }
            if (allowedValueRangePtr != null) {
                srvStateVar.C = serviceStateTableCtx.d("stateVariable[" + i2 + "]/allowedValueRange/minimum");
                srvStateVar.D = serviceStateTableCtx.d("stateVariable[" + i2 + "]/allowedValueRange/maximum");
                try {
                    srvStateVar.E = serviceStateTableCtx.d("stateVariable[" + i2 + "]/allowedValueRange/step");
                } catch (XPathException e6) {
                }
            }
            this.j.put(srvStateVar.a(), srvStateVar);
        }
    }

    private void e() {
        if (!this.l) {
            synchronized (this) {
                if (!this.l) {
                    f();
                }
            }
        }
    }
}
