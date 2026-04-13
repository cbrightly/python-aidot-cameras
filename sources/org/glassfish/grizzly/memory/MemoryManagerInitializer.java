package org.glassfish.grizzly.memory;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;

public class MemoryManagerInitializer {
    private static final String DMM_PROP_NAME = "org.glassfish.grizzly.DEFAULT_MEMORY_MANAGER";
    private static final Logger LOGGER = Grizzly.logger(MemoryManagerInitializer.class);

    MemoryManagerInitializer() {
    }

    static MemoryManager initManager() {
        MemoryManager mm = initMemoryManagerViaFactory();
        return mm != null ? mm : initMemoryManagerFallback();
    }

    private static MemoryManager initMemoryManagerViaFactory() {
        DefaultMemoryManagerFactory mmf;
        String dmmfClassName = System.getProperty(DefaultMemoryManagerFactory.DMMF_PROP_NAME);
        if (dmmfClassName == null || (mmf = (DefaultMemoryManagerFactory) newInstance(dmmfClassName)) == null) {
            return null;
        }
        return mmf.createMemoryManager();
    }

    private static MemoryManager initMemoryManagerFallback() {
        MemoryManager mm = (MemoryManager) newInstance(System.getProperty(DMM_PROP_NAME));
        return mm != null ? mm : new HeapMemoryManager();
    }

    private static <T> T newInstance(String className) {
        if (className == null) {
            return null;
        }
        try {
            return Class.forName(className, true, MemoryManager.class.getClassLoader()).newInstance();
        } catch (Exception e) {
            Logger logger = LOGGER;
            Level level = Level.SEVERE;
            if (logger.isLoggable(level)) {
                logger.log(level, "Unable to load or create a new instance of Class {0}.  Cause: {1}", new Object[]{className, e.getMessage()});
            }
            Level level2 = Level.FINE;
            if (logger.isLoggable(level2)) {
                logger.log(level2, e.toString(), e);
            }
            return null;
        }
    }
}
