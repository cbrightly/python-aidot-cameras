package org.eclipse.paho.client.mqttv3.internal;

import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.paho.client.mqttv3.j;
import org.eclipse.paho.client.mqttv3.logging.a;
import org.eclipse.paho.client.mqttv3.logging.b;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/* compiled from: NetworkModuleService */
public class p {
    private static a a;
    private static final ServiceLoader<org.eclipse.paho.client.mqttv3.spi.a> b;
    private static final Pattern c = Pattern.compile("((.+)@)?([^:]*)(:(\\d+))?");

    static {
        Class<p> cls = p.class;
        a = b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", cls.getSimpleName());
        b = ServiceLoader.load(org.eclipse.paho.client.mqttv3.spi.a.class, cls.getClassLoader());
    }

    private p() {
    }

    public static void d(String brokerUri) {
        try {
            URI uri = new URI(brokerUri);
            String scheme = uri.getScheme();
            if (scheme == null || scheme.isEmpty()) {
                throw new IllegalArgumentException("missing scheme in broker URI: " + brokerUri);
            }
            String scheme2 = scheme.toLowerCase();
            ServiceLoader<org.eclipse.paho.client.mqttv3.spi.a> serviceLoader = b;
            synchronized (serviceLoader) {
                Iterator<org.eclipse.paho.client.mqttv3.spi.a> it = serviceLoader.iterator();
                while (it.hasNext()) {
                    org.eclipse.paho.client.mqttv3.spi.a factory = it.next();
                    if (factory.b().contains(scheme2)) {
                        factory.a(uri);
                        return;
                    }
                }
                throw new IllegalArgumentException("no NetworkModule installed for scheme \"" + scheme2 + "\" of URI \"" + brokerUri + "\"");
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Can't parse string to URI \"" + brokerUri + "\"", e);
        }
    }

    public static o b(String address, j options, String clientId) {
        try {
            URI brokerUri = new URI(address);
            a(brokerUri);
            String scheme = brokerUri.getScheme().toLowerCase();
            ServiceLoader<org.eclipse.paho.client.mqttv3.spi.a> serviceLoader = b;
            synchronized (serviceLoader) {
                Iterator<org.eclipse.paho.client.mqttv3.spi.a> it = serviceLoader.iterator();
                while (it.hasNext()) {
                    org.eclipse.paho.client.mqttv3.spi.a factory = it.next();
                    if (factory.b().contains(scheme)) {
                        o c2 = factory.c(brokerUri, options, clientId);
                        return c2;
                    }
                }
                throw new IllegalArgumentException(brokerUri.toString());
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(address, e);
        }
    }

    public static void a(URI toPatch) {
        if (toPatch != null && toPatch.getHost() == null && toPatch.getAuthority() != null && !toPatch.getAuthority().isEmpty()) {
            Matcher matcher = c.matcher(toPatch.getAuthority());
            if (matcher.find()) {
                c(toPatch, "userInfo", matcher.group(2));
                c(toPatch, SerializableCookie.HOST, matcher.group(3));
                String portString = matcher.group(5);
                c(toPatch, IjkMediaPlayer.OnNativeInvokeListener.ARG_PORT, Integer.valueOf(portString != null ? Integer.parseInt(portString) : -1));
            }
        }
    }

    private static void c(URI toManipulate, String fieldName, Object newValue) {
        try {
            Field field = URI.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(toManipulate, newValue);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            a aVar = a;
            String name = p.class.getName();
            Object[] objArr = {toManipulate.toString()};
            aVar.warning(name, "setURIField", "115", objArr, e);
        }
    }
}
