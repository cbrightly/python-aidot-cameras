package net.sbbi.upnp.devices;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.xml.xpath.XPathException;
import net.sbbi.upnp.services.e;
import net.sbbi.upnp.xpath.a;
import org.w3c.dom.Node;

/* compiled from: UPNPRootDevice */
public class c extends b {
    private static final Logger s = Logger.getLogger(c.class.getName());
    private String A;
    private String B;
    private int t;
    private int u;
    private URL v;
    private long w;
    private long x;
    private URL y;
    private String z;

    public c(URL deviceDefLoc, String maxAge, String vendorFirmware, String discoveryUSN, String discoveryUDN) {
        this(deviceDefLoc, maxAge);
        this.z = vendorFirmware;
        this.A = discoveryUSN;
        this.B = discoveryUDN;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0137, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0138, code lost:
        s.log(java.util.logging.Level.SEVERE, "Exception while navigating Device Descripttion with XPath!", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0142, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0143, code lost:
        s.log(java.util.logging.Level.SEVERE, "Exception while parsing Device Descripttion xml!", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x014d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x014e, code lost:
        s.log(java.util.logging.Level.SEVERE, "Exception while accessing Device Descripttion!", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0158, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0159, code lost:
        s.log(java.util.logging.Level.SEVERE, "Exception while initializing XML parser!", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0142 A[ExcHandler: SAXException (r0v2 'ex' org.xml.sax.SAXException A[CUSTOM_DECLARE]), Splitter:B:1:0x0003] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x014d A[ExcHandler: IOException (r0v1 'ex' java.io.IOException A[CUSTOM_DECLARE]), Splitter:B:1:0x0003] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0158 A[ExcHandler: ParserConfigurationException (r0v0 'ex' javax.xml.parsers.ParserConfigurationException A[CUSTOM_DECLARE]), Splitter:B:1:0x0003] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public c(java.net.URL r14, java.lang.String r15) {
        /*
            r13 = this;
            r13.<init>()
            r13.y = r14     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            javax.xml.parsers.DocumentBuilderFactory r0 = javax.xml.parsers.DocumentBuilderFactory.newInstance()     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            javax.xml.parsers.DocumentBuilder r1 = r0.newDocumentBuilder()     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            net.sbbi.upnp.xpath.b r2 = new net.sbbi.upnp.xpath.b     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.io.InputStream r3 = r14.openStream()     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r2.<init>(r3)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            org.w3c.dom.Document r2 = r1.parse(r2)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            int r3 = java.lang.Integer.parseInt(r15)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            int r3 = r3 * 1000
            long r3 = (long) r3     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r13.w = r3     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r13.x = r3     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            net.sbbi.upnp.xpath.a r3 = new net.sbbi.upnp.xpath.a     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r3.<init>(r2)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r4 = "root"
            org.w3c.dom.Node r4 = r3.b(r4)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            net.sbbi.upnp.xpath.a r5 = r3.c(r4)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r6 = "specVersion/major"
            java.lang.String r6 = r5.d(r6)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            int r6 = java.lang.Integer.parseInt(r6)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r13.t = r6     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r6 = "specVersion/minor"
            java.lang.String r6 = r5.d(r6)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            int r6 = java.lang.Integer.parseInt(r6)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r13.u = r6     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            int r7 = r13.t     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r8 = 1
            if (r7 != r8) goto L_0x0112
            if (r6 != 0) goto L_0x0112
            r6 = 1
            r7 = 0
            java.lang.String r8 = "URLBase"
            java.lang.String r8 = r5.d(r8)     // Catch:{ XPathException -> 0x00a9, MalformedURLException -> 0x008d, ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142 }
            r7 = r8
            if (r7 == 0) goto L_0x00aa
            java.lang.String r8 = r7.trim()     // Catch:{ XPathException -> 0x00a9, MalformedURLException -> 0x008d, ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142 }
            int r8 = r8.length()     // Catch:{ XPathException -> 0x00a9, MalformedURLException -> 0x008d, ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142 }
            if (r8 <= 0) goto L_0x00aa
            java.net.URL r8 = new java.net.URL     // Catch:{ XPathException -> 0x00a9, MalformedURLException -> 0x008d, ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142 }
            r8.<init>(r7)     // Catch:{ XPathException -> 0x00a9, MalformedURLException -> 0x008d, ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142 }
            r13.v = r8     // Catch:{ XPathException -> 0x00a9, MalformedURLException -> 0x008d, ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142 }
            java.util.logging.Logger r8 = s     // Catch:{ XPathException -> 0x00a9, MalformedURLException -> 0x008d, ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ XPathException -> 0x00a9, MalformedURLException -> 0x008d, ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142 }
            java.lang.String r10 = "device URLBase "
            r9.<init>(r10)     // Catch:{ XPathException -> 0x00a9, MalformedURLException -> 0x008d, ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142 }
            java.net.URL r10 = r13.v     // Catch:{ XPathException -> 0x00a9, MalformedURLException -> 0x008d, ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142 }
            r9.append(r10)     // Catch:{ XPathException -> 0x00a9, MalformedURLException -> 0x008d, ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142 }
            java.lang.String r9 = r9.toString()     // Catch:{ XPathException -> 0x00a9, MalformedURLException -> 0x008d, ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142 }
            r8.fine(r9)     // Catch:{ XPathException -> 0x00a9, MalformedURLException -> 0x008d, ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142 }
            r6 = 0
            goto L_0x00aa
        L_0x008d:
            r8 = move-exception
            java.util.logging.Logger r9 = s     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.util.logging.Level r10 = java.util.logging.Level.WARNING     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r12 = "Error occured during device baseURL "
            r11.<init>(r12)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r11.append(r7)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r12 = " parsing, building it from device default location"
            r11.append(r12)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r11 = r11.toString()     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r9.log(r10, r11, r8)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            goto L_0x00aa
        L_0x00a9:
            r8 = move-exception
        L_0x00aa:
            if (r6 == 0) goto L_0x0101
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r9 = r14.getProtocol()     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r8.<init>(r9)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r9 = "://"
            r8.append(r9)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r9 = r14.getHost()     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r8.append(r9)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r9 = ":"
            r8.append(r9)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            int r9 = r14.getPort()     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r8.append(r9)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r8 = r8.toString()     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r9 = r14.getPath()     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            if (r9 == 0) goto L_0x00fa
            r10 = 47
            int r10 = r9.lastIndexOf(r10)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r11 = -1
            if (r10 == r11) goto L_0x00fa
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r12 = java.lang.String.valueOf(r8)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r11.<init>(r12)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r12 = 0
            java.lang.String r12 = r9.substring(r12, r10)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r11.append(r12)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r11 = r11.toString()     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r8 = r11
        L_0x00fa:
            java.net.URL r10 = new java.net.URL     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r10.<init>(r8)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r13.v = r10     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
        L_0x0101:
            java.lang.String r8 = "device"
            org.w3c.dom.Node r8 = r5.b(r8)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            net.sbbi.upnp.xpath.a r9 = r5.c(r8)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r10 = 0
            java.net.URL r11 = r13.v     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r13.m(r13, r10, r9, r11)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            goto L_0x0162
        L_0x0112:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r8 = "Unsupported device version ("
            r7.<init>(r8)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            int r8 = r13.t     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r7.append(r8)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r8 = "."
            r7.append(r8)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            int r8 = r13.u     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r7.append(r8)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r8 = ")"
            r7.append(r8)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            java.lang.String r7 = r7.toString()     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            r6.<init>(r7)     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
            throw r6     // Catch:{ ParserConfigurationException -> 0x0158, IOException -> 0x014d, SAXException -> 0x0142, XPathException -> 0x0137 }
        L_0x0137:
            r0 = move-exception
            java.util.logging.Logger r1 = s
            java.util.logging.Level r2 = java.util.logging.Level.SEVERE
            java.lang.String r3 = "Exception while navigating Device Descripttion with XPath!"
            r1.log(r2, r3, r0)
            goto L_0x0162
        L_0x0142:
            r0 = move-exception
            java.util.logging.Logger r1 = s
            java.util.logging.Level r2 = java.util.logging.Level.SEVERE
            java.lang.String r3 = "Exception while parsing Device Descripttion xml!"
            r1.log(r2, r3, r0)
            goto L_0x0162
        L_0x014d:
            r0 = move-exception
            java.util.logging.Logger r1 = s
            java.util.logging.Level r2 = java.util.logging.Level.SEVERE
            java.lang.String r3 = "Exception while accessing Device Descripttion!"
            r1.log(r2, r3, r0)
            goto L_0x0162
        L_0x0158:
            r0 = move-exception
            java.util.logging.Logger r1 = s
            java.util.logging.Level r2 = java.util.logging.Level.SEVERE
            java.lang.String r3 = "Exception while initializing XML parser!"
            r1.log(r2, r3, r0)
        L_0x0162:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sbbi.upnp.devices.c.<init>(java.net.URL, java.lang.String):void");
    }

    private void m(b device, b parent, a deviceCtx, URL baseURL) {
        b bVar = device;
        a aVar = deviceCtx;
        bVar.b = p(aVar, "deviceType");
        Logger logger = s;
        logger.fine("parsing device " + bVar.b);
        bVar.c = p(aVar, "friendlyName");
        bVar.d = q(aVar, "manufacturer");
        String base = q(aVar, "manufacturerURL");
        if (base != null) {
            try {
                bVar.e = new URL(base);
            } catch (MalformedURLException e) {
            }
        }
        try {
            bVar.f = r(q(aVar, "presentationURL"), this.v);
        } catch (MalformedURLException e2) {
        }
        bVar.g = q(aVar, "modelDescription");
        bVar.h = p(aVar, "modelName");
        bVar.i = q(aVar, "modelNumber");
        bVar.j = q(aVar, "modelURL");
        bVar.k = q(aVar, "serialNumber");
        bVar.l = p(aVar, "UDN");
        bVar.m = this.l.concat("::").concat(this.b);
        String tmp = q(aVar, "UPC");
        if (tmp != null) {
            try {
                bVar.n = Long.parseLong(tmp);
            } catch (Exception e3) {
            }
        }
        bVar.r = parent;
        o(bVar, aVar);
        n(bVar, aVar, this.v);
        try {
            Node deviceListPtr = aVar.b("deviceList");
            a deviceListCtx = aVar.c(deviceListPtr);
            Double arraySize = deviceListCtx.a("count( device )");
            bVar.q = new ArrayList();
            Logger logger2 = s;
            logger2.fine("child devices count is " + arraySize);
            int i = 1;
            while (i <= arraySize.intValue()) {
                a childDeviceCtx = deviceListCtx.c(deviceListCtx.b("device[" + i + "]"));
                b childDevice = new b();
                m(childDevice, bVar, childDeviceCtx, baseURL);
                Logger logger3 = s;
                logger3.fine("adding child device " + childDevice.b());
                bVar.q.add(childDevice);
                i++;
                deviceListPtr = deviceListPtr;
            }
        } catch (XPathException e4) {
            URL url = baseURL;
        }
    }

    private String p(a ctx, String ctxFieldName) {
        String value = ctx.d(ctxFieldName);
        if (value == null || value.length() != 0) {
            return value;
        }
        throw new XPathException("Mandatory field " + ctxFieldName + " not provided, uncompliant UPNP device !!");
    }

    private String q(a ctx, String ctxFieldName) {
        try {
            String value = ctx.d(ctxFieldName);
            if (value == null || value.length() != 0) {
                return value;
            }
            return null;
        } catch (XPathException e) {
            return null;
        }
    }

    private void o(b device, a deviceCtx) {
        a serviceListCtx = deviceCtx.c(deviceCtx.b("serviceList"));
        Double arraySize = serviceListCtx.a("count( service )");
        Logger logger = s;
        logger.fine("device services count is " + arraySize);
        device.p = new ArrayList();
        for (int i = 1; i <= arraySize.intValue(); i++) {
            a serviceCtx = serviceListCtx.c(serviceListCtx.b("service[" + i + "]"));
            URL base = this.v;
            if (base == null) {
                base = this.y;
            }
            device.p.add(new e(serviceCtx, base, this));
        }
    }

    private void n(b device, a deviceCtx, URL baseURL) {
        try {
            a iconListCtx = deviceCtx.c(deviceCtx.b("iconList"));
            Double arraySize = iconListCtx.a("count( icon )");
            Logger logger = s;
            logger.fine("device icons count is " + arraySize);
            device.o = new ArrayList();
            for (int i = 1; i <= arraySize.intValue(); i++) {
                a ico = new a();
                ico.a = iconListCtx.d("icon[" + i + "]/mimetype");
                ico.b = Integer.parseInt(iconListCtx.d("icon[" + i + "]/width"));
                ico.c = Integer.parseInt(iconListCtx.d("icon[" + i + "]/height"));
                ico.d = Integer.parseInt(iconListCtx.d("icon[" + i + "]/depth"));
                ico.e = r(iconListCtx.d("icon[" + i + "]/url"), baseURL);
                Logger logger2 = s;
                logger2.fine("icon URL is " + ico.e);
                device.o.add(ico);
            }
        } catch (XPathException e) {
        }
    }

    public static final URL r(String url, URL baseURL) {
        if (url == null || url.trim().length() == 0) {
            return null;
        }
        try {
            return new URL(url);
        } catch (MalformedURLException malEx) {
            if (baseURL != null) {
                String url2 = url.replace('\\', '/');
                if (url2.charAt(0) != '/') {
                    String externalForm = baseURL.toExternalForm();
                    if (!externalForm.endsWith("/")) {
                        externalForm = String.valueOf(externalForm) + "/";
                    }
                    return new URL(String.valueOf(externalForm) + url2);
                }
                return new URL(String.valueOf(String.valueOf(baseURL.getProtocol()) + "://" + baseURL.getHost() + ":" + baseURL.getPort()) + url2);
            }
            throw malEx;
        }
    }
}
