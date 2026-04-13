package net.sbbi.upnp.messages;

import io.netty.util.internal.StringUtil;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import net.sbbi.upnp.services.e;

/* compiled from: ActionMessage */
public class a {
    private static final Logger a = Logger.getLogger(a.class.getName());
    private static final Map b;
    private e c;
    private net.sbbi.upnp.services.a d;
    private List e;

    /* renamed from: net.sbbi.upnp.messages.a$a  reason: collision with other inner class name */
    /* compiled from: ActionMessage */
    public class C0454a {
        /* access modifiers changed from: private */
        public String a;
        /* access modifiers changed from: private */
        public String b;
    }

    static {
        HashMap hashMap = new HashMap();
        b = hashMap;
        hashMap.put(Character.valueOf(StringUtil.DOUBLE_QUOTE), "quot");
        hashMap.put('&', "amp");
        hashMap.put('<', "lt");
        hashMap.put('>', "gt");
        hashMap.put('\'', "apos");
    }

    protected a(e service, net.sbbi.upnp.services.a serviceAction) {
        this.c = service;
        this.d = serviceAction;
        if (serviceAction.b() != null) {
            this.e = new ArrayList();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x01c6, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x01cb, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x01cc, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x01d3, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x01d6, code lost:
        r16 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r18 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x01e3, code lost:
        r3 = new net.sbbi.upnp.messages.UPNPResponseException(899, r0.getMessage());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        r11.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x021b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x021c, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0220, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0221, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x01cb A[ExcHandler: all (r0v39 'th' javax.xml.parsers.ParserConfigurationException A[CUSTOM_DECLARE]), Splitter:B:14:0x01b5] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x01d3 A[ExcHandler: SAXException (r0v34 'saxEx' org.xml.sax.SAXException A[CUSTOM_DECLARE]), Splitter:B:14:0x01b5] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public net.sbbi.upnp.messages.c b() {
        /*
            r19 = this;
            r1 = r19
            r2 = 0
            r3 = 0
            r4 = 0
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r5 = 256(0x100, float:3.59E-43)
            r0.<init>(r5)
            r5 = r0
            java.lang.String r0 = "<?xml version=\"1.0\"?>\r\n"
            r5.append(r0)
            java.lang.String r0 = "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\""
            r5.append(r0)
            java.lang.String r0 = " s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
            r5.append(r0)
            java.lang.String r0 = "<s:Body>"
            r5.append(r0)
            java.lang.String r0 = "<u:"
            r5.append(r0)
            net.sbbi.upnp.services.a r0 = r1.d
            java.lang.String r0 = r0.e()
            r5.append(r0)
            java.lang.String r0 = " xmlns:u=\""
            r5.append(r0)
            net.sbbi.upnp.services.e r0 = r1.c
            java.lang.String r0 = r0.c()
            r5.append(r0)
            java.lang.String r0 = "\">"
            r5.append(r0)
            net.sbbi.upnp.services.a r0 = r1.d
            java.util.List r0 = r0.b()
            java.lang.String r6 = ">"
            if (r0 == 0) goto L_0x0085
            java.util.List r0 = r1.e
            java.util.Iterator r0 = r0.iterator()
        L_0x0052:
            boolean r7 = r0.hasNext()
            if (r7 != 0) goto L_0x0059
            goto L_0x0085
        L_0x0059:
            java.lang.Object r7 = r0.next()
            net.sbbi.upnp.messages.a$a r7 = (net.sbbi.upnp.messages.a.C0454a) r7
            java.lang.String r8 = "<"
            r5.append(r8)
            java.lang.String r8 = r7.a
            r5.append(r8)
            r5.append(r6)
            java.lang.String r8 = r7.b
            r5.append(r8)
            java.lang.String r8 = "</"
            r5.append(r8)
            java.lang.String r8 = r7.a
            r5.append(r8)
            r5.append(r6)
            goto L_0x0052
        L_0x0085:
            java.lang.String r0 = "</u:"
            r5.append(r0)
            net.sbbi.upnp.services.a r0 = r1.d
            java.lang.String r0 = r0.e()
            r5.append(r0)
            r5.append(r6)
            java.lang.String r0 = "</s:Body>"
            r5.append(r0)
            java.lang.String r0 = "</s:Envelope>"
            r5.append(r0)
            java.util.logging.Logger r0 = a
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "POST prepared for URL "
            r6.<init>(r7)
            net.sbbi.upnp.services.e r7 = r1.c
            java.net.URL r7 = r7.a()
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r0.fine(r6)
            java.net.URL r6 = new java.net.URL
            net.sbbi.upnp.services.e r7 = r1.c
            java.net.URL r7 = r7.a()
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            java.net.URLConnection r7 = r6.openConnection()
            java.net.HttpURLConnection r7 = (java.net.HttpURLConnection) r7
            r8 = 1
            r7.setDoInput(r8)
            r7.setDoOutput(r8)
            r9 = 0
            r7.setUseCaches(r9)
            java.lang.String r10 = "POST"
            r7.setRequestMethod(r10)
            java.net.HttpURLConnection.setFollowRedirects(r9)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = r6.getHost()
            java.lang.String r11 = java.lang.String.valueOf(r11)
            r10.<init>(r11)
            java.lang.String r11 = ":"
            r10.append(r11)
            int r11 = r6.getPort()
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            java.lang.String r11 = "HOST"
            r7.setRequestProperty(r11, r10)
            java.lang.String r10 = "CONTENT-TYPE"
            java.lang.String r11 = "text/xml; charset=\"utf-8\""
            r7.setRequestProperty(r10, r11)
            int r10 = r5.length()
            java.lang.String r10 = java.lang.Integer.toString(r10)
            java.lang.String r11 = "CONTENT-LENGTH"
            r7.setRequestProperty(r11, r10)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "\""
            r10.<init>(r11)
            net.sbbi.upnp.services.e r12 = r1.c
            java.lang.String r12 = r12.c()
            r10.append(r12)
            java.lang.String r12 = "#"
            r10.append(r12)
            net.sbbi.upnp.services.a r12 = r1.d
            java.lang.String r12 = r12.e()
            r10.append(r12)
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            java.lang.String r11 = "SOAPACTION"
            r7.setRequestProperty(r11, r10)
            java.io.OutputStream r10 = r7.getOutputStream()
            java.lang.String r11 = r5.toString()
            byte[] r11 = r11.getBytes()
            r10.write(r11)
            r10.flush()
            r10.close()
            r7.connect()
            r11 = 0
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r13 = "executing query :\n"
            r12.<init>(r13)
            r12.append(r5)
            java.lang.String r12 = r12.toString()
            r0.fine(r12)
            java.io.InputStream r0 = r7.getInputStream()     // Catch:{ IOException -> 0x0172 }
            r11 = r0
            goto L_0x0179
        L_0x0172:
            r0 = move-exception
            r12 = r0
            r0 = r12
            java.io.InputStream r11 = r7.getErrorStream()
        L_0x0179:
            if (r11 == 0) goto L_0x0252
            int r12 = r7.getResponseCode()
            java.lang.String r13 = r1.a(r11)
            java.util.logging.Logger r0 = a
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            java.lang.String r15 = "received response :\n"
            r14.<init>(r15)
            r14.append(r13)
            java.lang.String r14 = r14.toString()
            r0.fine(r14)
            javax.xml.parsers.SAXParserFactory r14 = javax.xml.parsers.SAXParserFactory.newInstance()
            r14.setValidating(r9)
            r14.setNamespaceAware(r8)
            net.sbbi.upnp.messages.b r0 = new net.sbbi.upnp.messages.b
            net.sbbi.upnp.services.a r8 = r1.d
            r0.<init>(r8)
            r8 = r0
            java.io.StringReader r0 = new java.io.StringReader
            r0.<init>(r13)
            r9 = r0
            org.xml.sax.InputSource r0 = new org.xml.sax.InputSource
            r0.<init>(r9)
            r15 = r0
            javax.xml.parsers.SAXParser r0 = r14.newSAXParser()     // Catch:{ ParserConfigurationException -> 0x0227, SAXException -> 0x01d3, all -> 0x01cb }
            r0.parse(r15, r8)     // Catch:{ ParserConfigurationException -> 0x01c6, SAXException -> 0x01d3, all -> 0x01cb }
            r11.close()     // Catch:{ IOException -> 0x01c1 }
        L_0x01c0:
            goto L_0x01c3
        L_0x01c1:
            r0 = move-exception
            goto L_0x01c0
        L_0x01c3:
            r16 = r2
            goto L_0x01ea
        L_0x01c6:
            r0 = move-exception
            r16 = r2
            goto L_0x022a
        L_0x01cb:
            r0 = move-exception
            r1 = r0
            r16 = r2
            r17 = r3
            goto L_0x024b
        L_0x01d3:
            r0 = move-exception
            net.sbbi.upnp.messages.UPNPResponseException r1 = new net.sbbi.upnp.messages.UPNPResponseException     // Catch:{ all -> 0x0220 }
            r16 = r2
            java.lang.String r2 = r0.getMessage()     // Catch:{ all -> 0x021b }
            r18 = r0
            r0 = 899(0x383, float:1.26E-42)
            r1.<init>(r0, r2)     // Catch:{ all -> 0x021b }
            r3 = r1
            r11.close()     // Catch:{ IOException -> 0x01e8 }
        L_0x01e7:
            goto L_0x01ea
        L_0x01e8:
            r0 = move-exception
            goto L_0x01e7
        L_0x01ea:
            if (r3 != 0) goto L_0x0218
            r0 = 200(0xc8, float:2.8E-43)
            if (r12 != r0) goto L_0x01f6
            net.sbbi.upnp.messages.c r2 = r8.a()
            goto L_0x0256
        L_0x01f6:
            r0 = 500(0x1f4, float:7.0E-43)
            if (r12 != r0) goto L_0x0201
            net.sbbi.upnp.messages.UPNPResponseException r3 = r8.b()
            r2 = r16
            goto L_0x0256
        L_0x0201:
            java.io.IOException r0 = new java.io.IOException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Unexpected server HTTP response:"
            r1.<init>(r2)
            r1.append(r12)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            r4 = r0
            r2 = r16
            goto L_0x0256
        L_0x0218:
            r2 = r16
            goto L_0x0256
        L_0x021b:
            r0 = move-exception
            r1 = r0
            r17 = r3
            goto L_0x024b
        L_0x0220:
            r0 = move-exception
            r16 = r2
            r1 = r0
            r17 = r3
            goto L_0x024b
        L_0x0227:
            r0 = move-exception
            r16 = r2
        L_0x022a:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException     // Catch:{ all -> 0x0247 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0247 }
            r17 = r3
            java.lang.String r3 = "ParserConfigurationException during SAX parser creation, please check your env settings:"
            r2.<init>(r3)     // Catch:{ all -> 0x0244 }
            java.lang.String r3 = r0.getMessage()     // Catch:{ all -> 0x0244 }
            r2.append(r3)     // Catch:{ all -> 0x0244 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0244 }
            r1.<init>(r2)     // Catch:{ all -> 0x0244 }
            throw r1     // Catch:{ all -> 0x0244 }
        L_0x0244:
            r0 = move-exception
            r1 = r0
            goto L_0x024b
        L_0x0247:
            r0 = move-exception
            r17 = r3
            r1 = r0
        L_0x024b:
            r11.close()     // Catch:{ IOException -> 0x024f }
        L_0x024e:
            goto L_0x0251
        L_0x024f:
            r0 = move-exception
            goto L_0x024e
        L_0x0251:
            throw r1
        L_0x0252:
            r16 = r2
            r17 = r3
        L_0x0256:
            r10.close()     // Catch:{ IOException -> 0x025a }
        L_0x0259:
            goto L_0x025c
        L_0x025a:
            r0 = move-exception
            goto L_0x0259
        L_0x025c:
            r7.disconnect()
            if (r3 != 0) goto L_0x0271
            if (r2 != 0) goto L_0x026d
            if (r4 != 0) goto L_0x026d
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Unable to receive a response from the UPNP device"
            r0.<init>(r1)
            r4 = r0
        L_0x026d:
            if (r4 != 0) goto L_0x0270
            return r2
        L_0x0270:
            throw r4
        L_0x0271:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sbbi.upnp.messages.a.b():net.sbbi.upnp.messages.c");
    }

    private String a(InputStream in) {
        byte[] buffer = new byte[256];
        StringBuffer content = new StringBuffer(256);
        while (true) {
            int read = in.read(buffer);
            int readen = read;
            if (read == -1) {
                break;
            }
            content.append(new String(buffer, 0, readen, "UTF-8"));
        }
        int len = content.length();
        while (content.charAt(len - 1) == 0) {
            len--;
            content.setLength(len);
        }
        return content.toString().trim();
    }
}
