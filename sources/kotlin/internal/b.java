package kotlin.internal;

import kotlin.jvm.internal.k;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: PlatformImplementations.kt */
public final class b {
    @NotNull
    public static final a a;

    static {
        a aVar;
        Object newInstance;
        Object newInstance2;
        Class<a> cls = a.class;
        int version = a();
        if (version >= 65544) {
            try {
                newInstance2 = Class.forName("kotlin.internal.jdk8.a").newInstance();
                k.d(newInstance2, "Class.forName(\"kotlin.in…entations\").newInstance()");
                if (newInstance2 != null) {
                    aVar = (a) newInstance2;
                    a = aVar;
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
            } catch (ClassCastException e) {
                ClassLoader classLoader = newInstance2.getClass().getClassLoader();
                ClassLoader classLoader2 = cls.getClassLoader();
                Throwable initCause = new ClassCastException("Instance classloader: " + classLoader + ", base type classloader: " + classLoader2).initCause(e);
                k.d(initCause, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
                throw initCause;
            } catch (ClassNotFoundException e2) {
                try {
                    Object newInstance3 = Class.forName("kotlin.internal.JRE8PlatformImplementations").newInstance();
                    k.d(newInstance3, "Class.forName(\"kotlin.in…entations\").newInstance()");
                    if (newInstance3 != null) {
                        try {
                            aVar = (a) newInstance3;
                        } catch (ClassCastException e3) {
                            ClassLoader classLoader3 = newInstance3.getClass().getClassLoader();
                            ClassLoader classLoader4 = cls.getClassLoader();
                            Throwable initCause2 = new ClassCastException("Instance classloader: " + classLoader3 + ", base type classloader: " + classLoader4).initCause(e3);
                            k.d(initCause2, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
                            throw initCause2;
                        }
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                    }
                } catch (ClassNotFoundException e4) {
                }
            }
        }
        if (version >= 65543) {
            try {
                newInstance = Class.forName("kotlin.internal.jdk7.a").newInstance();
                k.d(newInstance, "Class.forName(\"kotlin.in…entations\").newInstance()");
                if (newInstance != null) {
                    aVar = (a) newInstance;
                    a = aVar;
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
            } catch (ClassCastException e5) {
                ClassLoader classLoader5 = newInstance.getClass().getClassLoader();
                ClassLoader classLoader6 = cls.getClassLoader();
                Throwable initCause3 = new ClassCastException("Instance classloader: " + classLoader5 + ", base type classloader: " + classLoader6).initCause(e5);
                k.d(initCause3, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
                throw initCause3;
            } catch (ClassNotFoundException e6) {
                try {
                    Object newInstance4 = Class.forName("kotlin.internal.JRE7PlatformImplementations").newInstance();
                    k.d(newInstance4, "Class.forName(\"kotlin.in…entations\").newInstance()");
                    if (newInstance4 != null) {
                        try {
                            aVar = (a) newInstance4;
                        } catch (ClassCastException e7) {
                            ClassLoader classLoader7 = newInstance4.getClass().getClassLoader();
                            ClassLoader classLoader8 = cls.getClassLoader();
                            Throwable initCause4 = new ClassCastException("Instance classloader: " + classLoader7 + ", base type classloader: " + classLoader8).initCause(e7);
                            k.d(initCause4, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
                            throw initCause4;
                        }
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                    }
                } catch (ClassNotFoundException e8) {
                }
            }
        }
        aVar = new a();
        a = aVar;
    }

    private static final int a() {
        String version = System.getProperty("java.specification.version");
        if (version == null) {
            return 65542;
        }
        int firstDot = x.e0(version, '.', 0, false, 6, (Object) null);
        if (firstDot < 0) {
            try {
                return Integer.parseInt(version) * 65536;
            } catch (NumberFormatException e) {
                return 65542;
            }
        } else {
            int secondDot = x.e0(version, '.', firstDot + 1, false, 4, (Object) null);
            if (secondDot < 0) {
                secondDot = version.length();
            }
            String firstPart = version.substring(0, firstDot);
            k.d(firstPart, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            String secondPart = version.substring(firstDot + 1, secondDot);
            k.d(secondPart, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            try {
                return (Integer.parseInt(firstPart) * 65536) + Integer.parseInt(secondPart);
            } catch (NumberFormatException e2) {
                return 65542;
            }
        }
    }
}
