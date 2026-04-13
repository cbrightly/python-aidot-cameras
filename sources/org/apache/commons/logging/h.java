package org.apache.commons.logging;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.maps.android.BuildConfig;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import io.netty.util.internal.StringUtil;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.AccessController;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/* compiled from: LogFactory */
public abstract class h {
    private static PrintStream a;
    private static final String b;
    private static final ClassLoader c;
    protected static Hashtable d;
    protected static volatile h e = null;
    static /* synthetic */ Class f;

    public abstract void C(String str, Object obj);

    public abstract a l(Class cls);

    public abstract a m(String str);

    static {
        String classLoaderName;
        a = null;
        d = null;
        Class cls = f;
        if (cls == null) {
            cls = c("org.apache.commons.logging.LogFactory");
            f = cls;
        }
        ClassLoader h = h(cls);
        c = h;
        ClassLoader classLoader = h;
        if (h == null) {
            classLoaderName = "BOOTLOADER";
        } else {
            try {
                classLoaderName = B(classLoader);
            } catch (SecurityException e2) {
                classLoaderName = LDNetUtil.NETWORKTYPE_INVALID;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[LogFactory from ");
        stringBuffer.append(classLoaderName);
        stringBuffer.append("] ");
        b = stringBuffer.toString();
        a = v();
        Class cls2 = f;
        if (cls2 == null) {
            cls2 = c("org.apache.commons.logging.LogFactory");
            f = cls2;
        }
        x(cls2);
        d = e();
        if (w()) {
            y("BOOTSTRAP COMPLETED");
        }
    }

    protected h() {
    }

    private static final Hashtable e() {
        String storeImplementationClass;
        Hashtable result = null;
        try {
            storeImplementationClass = s("org.apache.commons.logging.LogFactory.HashtableImpl", (String) null);
        } catch (SecurityException e2) {
            storeImplementationClass = null;
        }
        if (storeImplementationClass == null) {
            storeImplementationClass = "org.apache.commons.logging.impl.WeakHashtable";
        }
        try {
            result = (Hashtable) Class.forName(storeImplementationClass).newInstance();
        } catch (Throwable t) {
            t(t);
            if (!"org.apache.commons.logging.impl.WeakHashtable".equals(storeImplementationClass)) {
                if (w()) {
                    y("[ERROR] LogFactory: Load of custom hashtable failed");
                } else {
                    System.err.println("[ERROR] LogFactory: Load of custom hashtable failed");
                }
            }
        }
        if (result == null) {
            return new Hashtable();
        }
        return result;
    }

    private static String D(String src) {
        if (src == null) {
            return null;
        }
        return src.trim();
    }

    protected static void t(Throwable t) {
        if (t instanceof ThreadDeath) {
            throw ((ThreadDeath) t);
        } else if (t instanceof VirtualMachineError) {
            throw ((VirtualMachineError) t);
        }
    }

    public static h k() {
        BufferedReader rd;
        String useTCCLStr;
        ClassLoader contextClassLoader = j();
        if (contextClassLoader == null && w()) {
            y("Context classloader is null.");
        }
        h factory = g(contextClassLoader);
        if (factory != null) {
            return factory;
        }
        if (w()) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("[LOOKUP] LogFactory implementation requested for the first time for context classloader ");
            stringBuffer.append(B(contextClassLoader));
            y(stringBuffer.toString());
            z("[LOOKUP] ", contextClassLoader);
        }
        Properties props = i(contextClassLoader, "commons-logging.properties");
        ClassLoader baseClassLoader = contextClassLoader;
        if (!(props == null || (useTCCLStr = props.getProperty("use_tccl")) == null || Boolean.valueOf(useTCCLStr).booleanValue())) {
            baseClassLoader = c;
        }
        if (w()) {
            y("[LOOKUP] Looking for system property [org.apache.commons.logging.LogFactory] to define the LogFactory subclass to use...");
        }
        try {
            String factoryClass = s("org.apache.commons.logging.LogFactory", (String) null);
            if (factoryClass != null) {
                if (w()) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("[LOOKUP] Creating an instance of LogFactory class '");
                    stringBuffer2.append(factoryClass);
                    stringBuffer2.append("' as specified by system property ");
                    stringBuffer2.append("org.apache.commons.logging.LogFactory");
                    y(stringBuffer2.toString());
                }
                factory = A(factoryClass, baseClassLoader, contextClassLoader);
            } else if (w()) {
                y("[LOOKUP] No system property [org.apache.commons.logging.LogFactory] defined.");
            }
        } catch (SecurityException e2) {
            if (w()) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("[LOOKUP] A security exception occurred while trying to create an instance of the custom factory class: [");
                stringBuffer3.append(D(e2.getMessage()));
                stringBuffer3.append("]. Trying alternative implementations...");
                y(stringBuffer3.toString());
            }
        } catch (RuntimeException e3) {
            if (w()) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("[LOOKUP] An exception occurred while trying to create an instance of the custom factory class: [");
                stringBuffer4.append(D(e3.getMessage()));
                stringBuffer4.append("] as specified by a system property.");
                y(stringBuffer4.toString());
            }
            throw e3;
        }
        if (factory == null) {
            if (w()) {
                y("[LOOKUP] Looking for a resource file of name [META-INF/services/org.apache.commons.logging.LogFactory] to define the LogFactory subclass to use...");
            }
            try {
                InputStream is = q(contextClassLoader, "META-INF/services/org.apache.commons.logging.LogFactory");
                if (is != null) {
                    try {
                        rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    } catch (UnsupportedEncodingException e4) {
                        rd = new BufferedReader(new InputStreamReader(is));
                    }
                    String factoryClassName = rd.readLine();
                    rd.close();
                    if (factoryClassName != null && !"".equals(factoryClassName)) {
                        if (w()) {
                            StringBuffer stringBuffer5 = new StringBuffer();
                            stringBuffer5.append("[LOOKUP]  Creating an instance of LogFactory class ");
                            stringBuffer5.append(factoryClassName);
                            stringBuffer5.append(" as specified by file '");
                            stringBuffer5.append("META-INF/services/org.apache.commons.logging.LogFactory");
                            stringBuffer5.append("' which was present in the path of the context classloader.");
                            y(stringBuffer5.toString());
                        }
                        factory = A(factoryClassName, baseClassLoader, contextClassLoader);
                    }
                } else if (w()) {
                    y("[LOOKUP] No resource file with name 'META-INF/services/org.apache.commons.logging.LogFactory' found.");
                }
            } catch (Exception ex) {
                if (w()) {
                    StringBuffer stringBuffer6 = new StringBuffer();
                    stringBuffer6.append("[LOOKUP] A security exception occurred while trying to create an instance of the custom factory class: [");
                    stringBuffer6.append(D(ex.getMessage()));
                    stringBuffer6.append("]. Trying alternative implementations...");
                    y(stringBuffer6.toString());
                }
            }
        }
        if (factory == null) {
            if (props != null) {
                if (w()) {
                    y("[LOOKUP] Looking in properties file for entry with key 'org.apache.commons.logging.LogFactory' to define the LogFactory subclass to use...");
                }
                String factoryClass2 = props.getProperty("org.apache.commons.logging.LogFactory");
                if (factoryClass2 != null) {
                    if (w()) {
                        StringBuffer stringBuffer7 = new StringBuffer();
                        stringBuffer7.append("[LOOKUP] Properties file specifies LogFactory subclass '");
                        stringBuffer7.append(factoryClass2);
                        stringBuffer7.append("'");
                        y(stringBuffer7.toString());
                    }
                    factory = A(factoryClass2, baseClassLoader, contextClassLoader);
                } else if (w()) {
                    y("[LOOKUP] Properties file has no entry specifying LogFactory subclass.");
                }
            } else if (w()) {
                y("[LOOKUP] No properties file available to determine LogFactory subclass from..");
            }
        }
        if (factory == null) {
            if (w()) {
                y("[LOOKUP] Loading the default LogFactory implementation 'org.apache.commons.logging.impl.LogFactoryImpl' via the same classloader that loaded this LogFactory class (ie not looking in the context classloader).");
            }
            factory = A("org.apache.commons.logging.impl.LogFactoryImpl", c, contextClassLoader);
        }
        if (factory != null) {
            b(contextClassLoader, factory);
            if (props != null) {
                Enumeration names = props.propertyNames();
                while (names.hasMoreElements()) {
                    String name = (String) names.nextElement();
                    factory.C(name, props.getProperty(name));
                }
            }
        }
        return factory;
    }

    public static a n(Class clazz) {
        return k().l(clazz);
    }

    public static a o(String name) {
        return k().m(name);
    }

    protected static ClassLoader h(Class clazz) {
        try {
            return clazz.getClassLoader();
        } catch (SecurityException ex) {
            if (w()) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Unable to get classloader for class '");
                stringBuffer.append(clazz);
                stringBuffer.append("' due to security restrictions - ");
                stringBuffer.append(ex.getMessage());
                y(stringBuffer.toString());
            }
            throw ex;
        }
    }

    private static ClassLoader j() {
        return (ClassLoader) AccessController.doPrivileged(new b());
    }

    protected static ClassLoader f() {
        try {
            return Thread.currentThread().getContextClassLoader();
        } catch (SecurityException e2) {
            return null;
        }
    }

    private static h g(ClassLoader contextClassLoader) {
        if (contextClassLoader == null) {
            return e;
        }
        return (h) d.get(contextClassLoader);
    }

    private static void b(ClassLoader classLoader, h factory) {
        if (factory == null) {
            return;
        }
        if (classLoader == null) {
            e = factory;
        } else {
            d.put(classLoader, factory);
        }
    }

    protected static h A(String factoryClass, ClassLoader classLoader, ClassLoader contextClassLoader) {
        Object result = AccessController.doPrivileged(new c(factoryClass, classLoader));
        if (result instanceof LogConfigurationException) {
            LogConfigurationException ex = (LogConfigurationException) result;
            if (w()) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("An error occurred while loading the factory class:");
                stringBuffer.append(ex.getMessage());
                y(stringBuffer.toString());
            }
            throw ex;
        }
        if (w()) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Created object ");
            stringBuffer2.append(B(result));
            stringBuffer2.append(" to manage classloader ");
            stringBuffer2.append(B(contextClassLoader));
            y(stringBuffer2.toString());
        }
        return (h) result;
    }

    protected static Object d(String factoryClass, ClassLoader classLoader) {
        if (classLoader != null) {
            try {
                Class logFactoryClass = classLoader.loadClass(factoryClass);
                Class cls = f;
                if (cls == null) {
                    cls = c("org.apache.commons.logging.LogFactory");
                    f = cls;
                }
                if (cls.isAssignableFrom(logFactoryClass)) {
                    if (w()) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Loaded class ");
                        stringBuffer.append(logFactoryClass.getName());
                        stringBuffer.append(" from classloader ");
                        stringBuffer.append(B(classLoader));
                        y(stringBuffer.toString());
                    }
                } else if (w()) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Factory class ");
                    stringBuffer2.append(logFactoryClass.getName());
                    stringBuffer2.append(" loaded from classloader ");
                    stringBuffer2.append(B(logFactoryClass.getClassLoader()));
                    stringBuffer2.append(" does not extend '");
                    Class cls2 = f;
                    if (cls2 == null) {
                        cls2 = c("org.apache.commons.logging.LogFactory");
                        f = cls2;
                    }
                    stringBuffer2.append(cls2.getName());
                    stringBuffer2.append("' as loaded by this classloader.");
                    y(stringBuffer2.toString());
                    z("[BAD CL TREE] ", classLoader);
                }
                return (h) logFactoryClass.newInstance();
            } catch (ClassNotFoundException ex) {
                if (classLoader == c) {
                    if (w()) {
                        StringBuffer stringBuffer3 = new StringBuffer();
                        stringBuffer3.append("Unable to locate any class called '");
                        stringBuffer3.append(factoryClass);
                        stringBuffer3.append("' via classloader ");
                        stringBuffer3.append(B(classLoader));
                        y(stringBuffer3.toString());
                    }
                    throw ex;
                }
            } catch (NoClassDefFoundError e2) {
                if (classLoader == c) {
                    if (w()) {
                        StringBuffer stringBuffer4 = new StringBuffer();
                        stringBuffer4.append("Class '");
                        stringBuffer4.append(factoryClass);
                        stringBuffer4.append("' cannot be loaded");
                        stringBuffer4.append(" via classloader ");
                        stringBuffer4.append(B(classLoader));
                        stringBuffer4.append(" - it depends on some other class that cannot be found.");
                        y(stringBuffer4.toString());
                    }
                    throw e2;
                }
            } catch (ClassCastException e3) {
                if (classLoader == c) {
                    boolean implementsLogFactory = u((Class) null);
                    StringBuffer msg = new StringBuffer();
                    msg.append("The application has specified that a custom LogFactory implementation ");
                    msg.append("should be used but Class '");
                    msg.append(factoryClass);
                    msg.append("' cannot be converted to '");
                    Class cls3 = f;
                    if (cls3 == null) {
                        cls3 = c("org.apache.commons.logging.LogFactory");
                        f = cls3;
                    }
                    msg.append(cls3.getName());
                    msg.append("'. ");
                    if (implementsLogFactory) {
                        msg.append("The conflict is caused by the presence of multiple LogFactory classes ");
                        msg.append("in incompatible classloaders. ");
                        msg.append("Background can be found in http://commons.apache.org/logging/tech.html. ");
                        msg.append("If you have not explicitly specified a custom LogFactory then it is likely ");
                        msg.append("that the container has set one without your knowledge. ");
                        msg.append("In this case, consider using the commons-logging-adapters.jar file or ");
                        msg.append("specifying the standard LogFactory from the command line. ");
                    } else {
                        msg.append("Please check the custom implementation. ");
                    }
                    msg.append("Help can be found @http://commons.apache.org/logging/troubleshooting.html.");
                    if (w()) {
                        y(msg.toString());
                    }
                    throw new ClassCastException(msg.toString());
                }
            } catch (Exception e4) {
                if (w()) {
                    y("Unable to create LogFactory instance.");
                }
                if (0 != 0) {
                    Class cls4 = f;
                    if (cls4 == null) {
                        cls4 = c("org.apache.commons.logging.LogFactory");
                        f = cls4;
                    }
                    if (!cls4.isAssignableFrom((Class) null)) {
                        return new LogConfigurationException("The chosen LogFactory implementation does not extend LogFactory. Please check your configuration.", e4);
                    }
                }
                return new LogConfigurationException((Throwable) e4);
            }
        }
        if (w()) {
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("Unable to load factory class via classloader ");
            stringBuffer5.append(B(classLoader));
            stringBuffer5.append(" - trying the classloader associated with this LogFactory.");
            y(stringBuffer5.toString());
        }
        return (h) Class.forName(factoryClass).newInstance();
    }

    static /* synthetic */ Class c(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    private static boolean u(Class logFactoryClass) {
        boolean implementsLogFactory = false;
        if (logFactoryClass != null) {
            try {
                ClassLoader logFactoryClassLoader = logFactoryClass.getClassLoader();
                if (logFactoryClassLoader == null) {
                    y("[CUSTOM LOG FACTORY] was loaded by the boot classloader");
                } else {
                    z("[CUSTOM LOG FACTORY] ", logFactoryClassLoader);
                    implementsLogFactory = Class.forName("org.apache.commons.logging.h", false, logFactoryClassLoader).isAssignableFrom(logFactoryClass);
                    if (implementsLogFactory) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("[CUSTOM LOG FACTORY] ");
                        stringBuffer.append(logFactoryClass.getName());
                        stringBuffer.append(" implements LogFactory but was loaded by an incompatible classloader.");
                        y(stringBuffer.toString());
                    } else {
                        StringBuffer stringBuffer2 = new StringBuffer();
                        stringBuffer2.append("[CUSTOM LOG FACTORY] ");
                        stringBuffer2.append(logFactoryClass.getName());
                        stringBuffer2.append(" does not implement LogFactory.");
                        y(stringBuffer2.toString());
                    }
                }
            } catch (SecurityException e2) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("[CUSTOM LOG FACTORY] SecurityException thrown whilst trying to determine whether the compatibility was caused by a classloader conflict: ");
                stringBuffer3.append(e2.getMessage());
                y(stringBuffer3.toString());
            } catch (LinkageError e3) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("[CUSTOM LOG FACTORY] LinkageError thrown whilst trying to determine whether the compatibility was caused by a classloader conflict: ");
                stringBuffer4.append(e3.getMessage());
                y(stringBuffer4.toString());
            } catch (ClassNotFoundException e4) {
                y("[CUSTOM LOG FACTORY] LogFactory class cannot be loaded by classloader which loaded the custom LogFactory implementation. Is the custom factory in the right classloader?");
            }
        }
        return implementsLogFactory;
    }

    private static InputStream q(ClassLoader loader, String name) {
        return (InputStream) AccessController.doPrivileged(new d(loader, name));
    }

    private static Enumeration r(ClassLoader loader, String name) {
        return (Enumeration) AccessController.doPrivileged(new e(loader, name));
    }

    private static Properties p(URL url) {
        return (Properties) AccessController.doPrivileged(new f(url));
    }

    private static final Properties i(ClassLoader classLoader, String fileName) {
        Properties props = null;
        double priority = 0.0d;
        URL propsUrl = null;
        try {
            Enumeration urls = r(classLoader, fileName);
            if (urls == null) {
                return null;
            }
            while (urls.hasMoreElements()) {
                URL url = (URL) urls.nextElement();
                Properties newProps = p(url);
                if (newProps != null) {
                    if (props == null) {
                        propsUrl = url;
                        props = newProps;
                        String priorityStr = props.getProperty(Progress.PRIORITY);
                        priority = 0.0d;
                        if (priorityStr != null) {
                            priority = Double.parseDouble(priorityStr);
                        }
                        if (w()) {
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("[LOOKUP] Properties file found at '");
                            stringBuffer.append(url);
                            stringBuffer.append("'");
                            stringBuffer.append(" with priority ");
                            stringBuffer.append(priority);
                            y(stringBuffer.toString());
                        }
                    } else {
                        String newPriorityStr = newProps.getProperty(Progress.PRIORITY);
                        double newPriority = 0.0d;
                        if (newPriorityStr != null) {
                            newPriority = Double.parseDouble(newPriorityStr);
                        }
                        if (newPriority > priority) {
                            if (w()) {
                                StringBuffer stringBuffer2 = new StringBuffer();
                                stringBuffer2.append("[LOOKUP] Properties file at '");
                                stringBuffer2.append(url);
                                stringBuffer2.append("'");
                                stringBuffer2.append(" with priority ");
                                stringBuffer2.append(newPriority);
                                stringBuffer2.append(" overrides file at '");
                                stringBuffer2.append(propsUrl);
                                stringBuffer2.append("'");
                                stringBuffer2.append(" with priority ");
                                stringBuffer2.append(priority);
                                y(stringBuffer2.toString());
                            }
                            propsUrl = url;
                            props = newProps;
                            priority = newPriority;
                        } else if (w()) {
                            StringBuffer stringBuffer3 = new StringBuffer();
                            stringBuffer3.append("[LOOKUP] Properties file at '");
                            stringBuffer3.append(url);
                            stringBuffer3.append("'");
                            stringBuffer3.append(" with priority ");
                            stringBuffer3.append(newPriority);
                            stringBuffer3.append(" does not override file at '");
                            stringBuffer3.append(propsUrl);
                            stringBuffer3.append("'");
                            stringBuffer3.append(" with priority ");
                            stringBuffer3.append(priority);
                            y(stringBuffer3.toString());
                        }
                    }
                }
            }
            if (w()) {
                if (props == null) {
                    StringBuffer stringBuffer4 = new StringBuffer();
                    stringBuffer4.append("[LOOKUP] No properties file of name '");
                    stringBuffer4.append(fileName);
                    stringBuffer4.append("' found.");
                    y(stringBuffer4.toString());
                } else {
                    StringBuffer stringBuffer5 = new StringBuffer();
                    stringBuffer5.append("[LOOKUP] Properties file of name '");
                    stringBuffer5.append(fileName);
                    stringBuffer5.append("' found at '");
                    stringBuffer5.append(propsUrl);
                    stringBuffer5.append(StringUtil.DOUBLE_QUOTE);
                    y(stringBuffer5.toString());
                }
            }
            return props;
        } catch (SecurityException e2) {
            if (w()) {
                y("SecurityException thrown while trying to find/read config files.");
            }
        }
    }

    private static String s(String key, String def) {
        return (String) AccessController.doPrivileged(new g(key, def));
    }

    private static PrintStream v() {
        try {
            String dest = s("org.apache.commons.logging.diagnostics.dest", (String) null);
            if (dest == null) {
                return null;
            }
            if (dest.equals("STDOUT")) {
                return System.out;
            }
            if (dest.equals("STDERR")) {
                return System.err;
            }
            try {
                return new PrintStream(new FileOutputStream(dest, true));
            } catch (IOException e2) {
                return null;
            }
        } catch (SecurityException e3) {
            return null;
        }
    }

    protected static boolean w() {
        return a != null;
    }

    /* access modifiers changed from: private */
    public static final void y(String msg) {
        PrintStream printStream = a;
        if (printStream != null) {
            printStream.print(b);
            a.println(msg);
            a.flush();
        }
    }

    private static void x(Class clazz) {
        if (w()) {
            try {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("[ENV] Extension directories (java.ext.dir): ");
                stringBuffer.append(System.getProperty("java.ext.dir"));
                y(stringBuffer.toString());
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("[ENV] Application classpath (java.class.path): ");
                stringBuffer2.append(System.getProperty("java.class.path"));
                y(stringBuffer2.toString());
            } catch (SecurityException e2) {
                y("[ENV] Security setting prevent interrogation of system classpaths.");
            }
            String className = clazz.getName();
            try {
                ClassLoader classLoader = h(clazz);
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("[ENV] Class ");
                stringBuffer3.append(className);
                stringBuffer3.append(" was loaded via classloader ");
                stringBuffer3.append(B(classLoader));
                y(stringBuffer3.toString());
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("[ENV] Ancestry of classloader which loaded ");
                stringBuffer4.append(className);
                stringBuffer4.append(" is ");
                z(stringBuffer4.toString(), classLoader);
            } catch (SecurityException e3) {
                StringBuffer stringBuffer5 = new StringBuffer();
                stringBuffer5.append("[ENV] Security forbids determining the classloader for ");
                stringBuffer5.append(className);
                y(stringBuffer5.toString());
            }
        }
    }

    private static void z(String prefix, ClassLoader classLoader) {
        if (w()) {
            if (classLoader != null) {
                String classLoaderString = classLoader.toString();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(prefix);
                stringBuffer.append(B(classLoader));
                stringBuffer.append(" == '");
                stringBuffer.append(classLoaderString);
                stringBuffer.append("'");
                y(stringBuffer.toString());
            }
            try {
                ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                if (classLoader != null) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append(prefix);
                    stringBuffer2.append("ClassLoader tree:");
                    StringBuffer buf = new StringBuffer(stringBuffer2.toString());
                    do {
                        buf.append(B(classLoader));
                        if (classLoader == systemClassLoader) {
                            buf.append(" (SYSTEM) ");
                        }
                        try {
                            classLoader = classLoader.getParent();
                            buf.append(" --> ");
                        } catch (SecurityException e2) {
                            buf.append(" --> SECRET");
                        }
                    } while (classLoader != null);
                    buf.append("BOOT");
                    y(buf.toString());
                }
            } catch (SecurityException e3) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append(prefix);
                stringBuffer3.append("Security forbids determining the system classloader.");
                y(stringBuffer3.toString());
            }
        }
    }

    public static String B(Object o) {
        if (o == null) {
            return BuildConfig.TRAVIS;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(o.getClass().getName());
        stringBuffer.append("@");
        stringBuffer.append(System.identityHashCode(o));
        return stringBuffer.toString();
    }
}
