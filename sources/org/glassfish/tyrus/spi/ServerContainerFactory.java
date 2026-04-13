package org.glassfish.tyrus.spi;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

public abstract class ServerContainerFactory {
    private static final String CONTAINTER_CLASS = "org.glassfish.tyrus.container.grizzly.server.GrizzlyServerContainer";

    public abstract ServerContainer createContainer(Map<String, Object> map);

    public static ServerContainer createServerContainer() {
        return createServerContainer(Collections.emptyMap());
    }

    public static ServerContainer createServerContainer(Map<String, Object> properties) {
        Class factoryClass;
        ServerContainerFactory factory = null;
        Iterator<S> it = ServiceLoader.load(ServerContainerFactory.class).iterator();
        if (it.hasNext()) {
            factory = it.next();
        }
        if (factory == null) {
            try {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                if (classLoader == null) {
                    factoryClass = Class.forName(CONTAINTER_CLASS);
                } else {
                    factoryClass = classLoader.loadClass(CONTAINTER_CLASS);
                }
                factory = (ServerContainerFactory) factoryClass.newInstance();
            } catch (ClassNotFoundException ce) {
                throw new RuntimeException(ce);
            } catch (InstantiationException ie) {
                throw new RuntimeException(ie);
            } catch (IllegalAccessException ie2) {
                throw new RuntimeException(ie2);
            }
        }
        return factory.createContainer(properties);
    }
}
