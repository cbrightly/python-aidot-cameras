package com.squareup.okhttp.internal;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.squareup.okhttp.q;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import okio.c;
import okio.e0;
import okio.f;

/* compiled from: Util */
public final class j {
    public static final byte[] a = new byte[0];
    public static final String[] b = new String[0];
    public static final Charset c = Charset.forName("UTF-8");

    public static void a(long arrayLength, long offset, long count) {
        if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public static boolean h(Object a2, Object b2) {
        return a2 == b2 || (a2 != null && a2.equals(b2));
    }

    public static void c(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception e) {
            }
        }
    }

    public static void d(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (AssertionError e) {
                if (!o(e)) {
                    throw e;
                }
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception e2) {
            }
        }
    }

    public static void b(Closeable a2, Closeable b2) {
        Throwable thrown = null;
        try {
            a2.close();
        } catch (Throwable e) {
            thrown = e;
        }
        try {
            b2.close();
        } catch (Throwable e2) {
            if (thrown == null) {
                thrown = e2;
            }
        }
        if (thrown != null) {
            if (thrown instanceof IOException) {
                throw ((IOException) thrown);
            } else if (thrown instanceof RuntimeException) {
                throw ((RuntimeException) thrown);
            } else if (thrown instanceof Error) {
                throw ((Error) thrown);
            } else {
                throw new AssertionError(thrown);
            }
        }
    }

    public static boolean g(e0 source, int timeout, TimeUnit timeUnit) {
        try {
            return r(source, timeout, timeUnit);
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean r(e0 source, int duration, TimeUnit timeUnit) {
        long now = System.nanoTime();
        long originalDuration = source.timeout().e() ? source.timeout().c() - now : Long.MAX_VALUE;
        source.timeout().d(Math.min(originalDuration, timeUnit.toNanos((long) duration)) + now);
        try {
            c skipBuffer = new c();
            while (source.read(skipBuffer, 2048) != -1) {
                skipBuffer.clear();
            }
            if (originalDuration == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                source.timeout().a();
            } else {
                source.timeout().d(now + originalDuration);
            }
            return true;
        } catch (InterruptedIOException e) {
            if (originalDuration == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                source.timeout().a();
            } else {
                source.timeout().d(now + originalDuration);
            }
            return false;
        } catch (Throwable th) {
            if (originalDuration == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                source.timeout().a();
            } else {
                source.timeout().d(now + originalDuration);
            }
            throw th;
        }
    }

    public static String p(String s) {
        try {
            return f.of(MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"))).hex();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    public static f q(f s) {
        try {
            return f.of(MessageDigest.getInstance("SHA-1").digest(s.toByteArray()));
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    public static <T> List<T> j(List<T> list) {
        return Collections.unmodifiableList(new ArrayList(list));
    }

    public static <T> List<T> k(T... elements) {
        return Collections.unmodifiableList(Arrays.asList((Object[]) elements.clone()));
    }

    public static <K, V> Map<K, V> l(Map<K, V> map) {
        return Collections.unmodifiableMap(new LinkedHashMap(map));
    }

    /* compiled from: Util */
    public static final class a implements ThreadFactory {
        final /* synthetic */ String c;
        final /* synthetic */ boolean d;

        a(String str, boolean z) {
            this.c = str;
            this.d = z;
        }

        public Thread newThread(Runnable runnable) {
            Thread result = new Thread(runnable, this.c);
            result.setDaemon(this.d);
            return result;
        }
    }

    public static ThreadFactory s(String name, boolean daemon) {
        return new a(name, daemon);
    }

    public static <T> T[] n(Class<T> arrayType, T[] first, T[] second) {
        List<T> result = m(first, second);
        return result.toArray((Object[]) Array.newInstance(arrayType, result.size()));
    }

    private static <T> List<T> m(T[] first, T[] second) {
        List<T> result = new ArrayList<>();
        for (T a2 : first) {
            int length = second.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                T b2 = second[i];
                if (a2.equals(b2)) {
                    result.add(b2);
                    break;
                }
                i++;
            }
        }
        return result;
    }

    public static String i(q url) {
        if (url.A() == q.i(url.E())) {
            return url.q();
        }
        return url.q() + ":" + url.A();
    }

    public static boolean o(AssertionError e) {
        return (e.getCause() == null || e.getMessage() == null || !e.getMessage().contains("getsockname failed")) ? false : true;
    }

    public static boolean f(String[] array, String value) {
        return Arrays.asList(array).contains(value);
    }

    public static String[] e(String[] array, String value) {
        String[] result = new String[(array.length + 1)];
        System.arraycopy(array, 0, result, 0, array.length);
        result[result.length - 1] = value;
        return result;
    }
}
