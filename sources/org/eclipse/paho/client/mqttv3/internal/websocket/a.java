package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.util.prefs.AbstractPreferences;

/* compiled from: Base64 */
public class a {
    private static final a a;
    private static final C0150a b;

    static {
        a aVar = new a();
        a = aVar;
        aVar.getClass();
        b = new C0150a();
    }

    public static String a(String s) {
        C0150a aVar = b;
        aVar.putByteArray("akey", s.getBytes());
        return aVar.a();
    }

    public static String b(byte[] b2) {
        C0150a aVar = b;
        aVar.putByteArray("aKey", b2);
        return aVar.a();
    }

    /* renamed from: org.eclipse.paho.client.mqttv3.internal.websocket.a$a  reason: collision with other inner class name */
    /* compiled from: Base64 */
    public class C0150a extends AbstractPreferences {
        private String a = null;

        public C0150a() {
            super((AbstractPreferences) null, "");
        }

        /* access modifiers changed from: protected */
        public void putSpi(String key, String value) {
            this.a = value;
        }

        public String a() {
            return this.a;
        }

        /* access modifiers changed from: protected */
        public String getSpi(String key) {
            return null;
        }

        /* access modifiers changed from: protected */
        public void removeSpi(String key) {
        }

        /* access modifiers changed from: protected */
        public void removeNodeSpi() {
        }

        /* access modifiers changed from: protected */
        public String[] keysSpi() {
            return null;
        }

        /* access modifiers changed from: protected */
        public String[] childrenNamesSpi() {
            return null;
        }

        /* access modifiers changed from: protected */
        public AbstractPreferences childSpi(String name) {
            return null;
        }

        /* access modifiers changed from: protected */
        public void syncSpi() {
        }

        /* access modifiers changed from: protected */
        public void flushSpi() {
        }
    }
}
