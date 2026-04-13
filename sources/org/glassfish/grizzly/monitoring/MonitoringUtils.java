package org.glassfish.grizzly.monitoring;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;

public class MonitoringUtils {
    private static final Logger LOGGER = Grizzly.logger(MonitoringUtils.class);

    public static Object loadJmxObject(String jmxObjectClassname, Object constructorParam) {
        return loadJmxObject(jmxObjectClassname, constructorParam, constructorParam.getClass());
    }

    public static Object loadJmxObject(String jmxObjectClassname, Object constructorParam, Class contructorParamType) {
        try {
            return loadClass(jmxObjectClassname).getDeclaredConstructor(new Class[]{contructorParamType}).newInstance(new Object[]{constructorParam});
        } catch (Exception e) {
            Logger logger = LOGGER;
            Level level = Level.FINE;
            logger.log(level, "Can not load JMX Object: " + jmxObjectClassname, e);
            return null;
        }
    }

    private static Class<?> loadClass(String classname) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = MonitoringUtils.class.getClassLoader();
        }
        return classLoader.loadClass(classname);
    }
}
