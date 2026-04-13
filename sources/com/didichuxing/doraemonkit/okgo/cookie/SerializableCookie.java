package com.didichuxing.doraemonkit.okgo.cookie;

import android.content.ContentValues;
import android.database.Cursor;
import com.didichuxing.doraemonkit.okgo.utils.OkLogger;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;
import okhttp3.m;

public class SerializableCookie implements Serializable {
    public static final String COOKIE = "cookie";
    public static final String DOMAIN = "domain";
    public static final String HOST = "host";
    public static final String NAME = "name";
    private static final long serialVersionUID = 6374381323722046732L;
    private transient m clientCookie;
    private transient m cookie;
    public String domain;
    public String host;
    public String name;

    public SerializableCookie(String host2, m cookie2) {
        this.cookie = cookie2;
        this.host = host2;
        this.name = cookie2.i();
        this.domain = cookie2.e();
    }

    public m getCookie() {
        m bestCookie = this.cookie;
        if (this.clientCookie != null) {
            return this.clientCookie;
        }
        return bestCookie;
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        out.writeObject(this.cookie.i());
        out.writeObject(this.cookie.n());
        out.writeLong(this.cookie.f());
        out.writeObject(this.cookie.e());
        out.writeObject(this.cookie.j());
        out.writeBoolean(this.cookie.l());
        out.writeBoolean(this.cookie.h());
        out.writeBoolean(this.cookie.g());
        out.writeBoolean(this.cookie.k());
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        long expiresAt = in.readLong();
        String domain2 = (String) in.readObject();
        String path = (String) in.readObject();
        boolean secure = in.readBoolean();
        boolean httpOnly = in.readBoolean();
        boolean hostOnly = in.readBoolean();
        boolean readBoolean = in.readBoolean();
        m.a builder = new m.a().g((String) in.readObject()).j((String) in.readObject()).d(expiresAt);
        m.a builder2 = (hostOnly ? builder.e(domain2) : builder.b(domain2)).h(path);
        m.a builder3 = secure ? builder2.i() : builder2;
        this.clientCookie = (httpOnly ? builder3.f() : builder3).a();
    }

    public static SerializableCookie parseCursorToBean(Cursor cursor) {
        return new SerializableCookie(cursor.getString(cursor.getColumnIndex(HOST)), bytesToCookie(cursor.getBlob(cursor.getColumnIndex(COOKIE))));
    }

    public static ContentValues getContentValues(SerializableCookie serializableCookie) {
        ContentValues values = new ContentValues();
        values.put(HOST, serializableCookie.host);
        values.put("name", serializableCookie.name);
        values.put(DOMAIN, serializableCookie.domain);
        values.put(COOKIE, cookieToBytes(serializableCookie.host, serializableCookie.getCookie()));
        return values;
    }

    public static String encodeCookie(String host2, m cookie2) {
        if (cookie2 == null) {
            return null;
        }
        return byteArrayToHexString(cookieToBytes(host2, cookie2));
    }

    public static byte[] cookieToBytes(String host2, m cookie2) {
        SerializableCookie serializableCookie = new SerializableCookie(host2, cookie2);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(os).writeObject(serializableCookie);
            return os.toByteArray();
        } catch (IOException e) {
            OkLogger.printStackTrace(e);
            return null;
        }
    }

    public static m decodeCookie(String cookieString) {
        return bytesToCookie(hexStringToByteArray(cookieString));
    }

    public static m bytesToCookie(byte[] bytes) {
        try {
            return ((SerializableCookie) new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject()).getCookie();
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
            return null;
        }
    }

    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 255;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[(len / 2)];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SerializableCookie that = (SerializableCookie) o;
        String str = this.host;
        if (str == null ? that.host != null : !str.equals(that.host)) {
            return false;
        }
        String str2 = this.name;
        if (str2 == null ? that.name != null : !str2.equals(that.name)) {
            return false;
        }
        String str3 = this.domain;
        if (str3 != null) {
            return str3.equals(that.domain);
        }
        if (that.domain == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String str = this.host;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.name;
        int result = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.domain;
        if (str3 != null) {
            i = str3.hashCode();
        }
        return result + i;
    }
}
