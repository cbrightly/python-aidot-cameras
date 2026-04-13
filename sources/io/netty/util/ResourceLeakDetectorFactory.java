package io.netty.util;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedAction;

public abstract class ResourceLeakDetectorFactory {
    private static volatile ResourceLeakDetectorFactory factoryInstance = new DefaultResourceLeakDetectorFactory();
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) ResourceLeakDetectorFactory.class);

    @Deprecated
    public abstract <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> cls, int i, long j);

    public static ResourceLeakDetectorFactory instance() {
        return factoryInstance;
    }

    public static void setResourceLeakDetectorFactory(ResourceLeakDetectorFactory factory) {
        factoryInstance = (ResourceLeakDetectorFactory) ObjectUtil.checkNotNull(factory, "factory");
    }

    public final <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> resource) {
        return newResourceLeakDetector(resource, 128);
    }

    public <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> resource, int samplingInterval) {
        return newResourceLeakDetector(resource, 128, DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
    }

    public static final class DefaultResourceLeakDetectorFactory extends ResourceLeakDetectorFactory {
        private final Constructor<?> customClassConstructor;
        private final Constructor<?> obsoleteCustomClassConstructor;

        DefaultResourceLeakDetectorFactory() {
            String customLeakDetector;
            try {
                customLeakDetector = (String) AccessController.doPrivileged(new PrivilegedAction<String>() {
                    public String run() {
                        return SystemPropertyUtil.get("io.netty.customResourceLeakDetector");
                    }
                });
            } catch (Throwable cause) {
                ResourceLeakDetectorFactory.logger.error("Could not access System property: io.netty.customResourceLeakDetector", cause);
                customLeakDetector = null;
            }
            if (customLeakDetector == null) {
                this.customClassConstructor = null;
                this.obsoleteCustomClassConstructor = null;
                return;
            }
            this.obsoleteCustomClassConstructor = obsoleteCustomClassConstructor(customLeakDetector);
            this.customClassConstructor = customClassConstructor(customLeakDetector);
        }

        private static Constructor<?> obsoleteCustomClassConstructor(String customLeakDetector) {
            try {
                Class<?> detectorClass = Class.forName(customLeakDetector, true, PlatformDependent.getSystemClassLoader());
                if (ResourceLeakDetector.class.isAssignableFrom(detectorClass)) {
                    return detectorClass.getConstructor(new Class[]{Class.class, Integer.TYPE, Long.TYPE});
                }
                ResourceLeakDetectorFactory.logger.error("Class {} does not inherit from ResourceLeakDetector.", (Object) customLeakDetector);
                return null;
            } catch (Throwable t) {
                ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector class provided: {}", customLeakDetector, t);
                return null;
            }
        }

        private static Constructor<?> customClassConstructor(String customLeakDetector) {
            try {
                Class<?> detectorClass = Class.forName(customLeakDetector, true, PlatformDependent.getSystemClassLoader());
                if (ResourceLeakDetector.class.isAssignableFrom(detectorClass)) {
                    return detectorClass.getConstructor(new Class[]{Class.class, Integer.TYPE});
                }
                ResourceLeakDetectorFactory.logger.error("Class {} does not inherit from ResourceLeakDetector.", (Object) customLeakDetector);
                return null;
            } catch (Throwable t) {
                ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector class provided: {}", customLeakDetector, t);
                return null;
            }
        }

        public <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> resource, int samplingInterval, long maxActive) {
            Constructor<?> constructor = this.obsoleteCustomClassConstructor;
            if (constructor != null) {
                try {
                    ResourceLeakDetector<T> leakDetector = (ResourceLeakDetector) constructor.newInstance(new Object[]{resource, Integer.valueOf(samplingInterval), Long.valueOf(maxActive)});
                    ResourceLeakDetectorFactory.logger.debug("Loaded custom ResourceLeakDetector: {}", (Object) this.obsoleteCustomClassConstructor.getDeclaringClass().getName());
                    return leakDetector;
                } catch (Throwable t) {
                    ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector provided: {} with the given resource: {}", this.obsoleteCustomClassConstructor.getDeclaringClass().getName(), resource, t);
                }
            }
            ResourceLeakDetector<T> resourceLeakDetector = new ResourceLeakDetector<>((Class<?>) resource, samplingInterval, maxActive);
            ResourceLeakDetectorFactory.logger.debug("Loaded default ResourceLeakDetector: {}", (Object) resourceLeakDetector);
            return resourceLeakDetector;
        }

        public <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> resource, int samplingInterval) {
            Constructor<?> constructor = this.customClassConstructor;
            if (constructor != null) {
                try {
                    ResourceLeakDetector<T> leakDetector = (ResourceLeakDetector) constructor.newInstance(new Object[]{resource, Integer.valueOf(samplingInterval)});
                    ResourceLeakDetectorFactory.logger.debug("Loaded custom ResourceLeakDetector: {}", (Object) this.customClassConstructor.getDeclaringClass().getName());
                    return leakDetector;
                } catch (Throwable t) {
                    ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector provided: {} with the given resource: {}", this.customClassConstructor.getDeclaringClass().getName(), resource, t);
                }
            }
            ResourceLeakDetector<T> resourceLeakDetector = new ResourceLeakDetector<>(resource, samplingInterval);
            ResourceLeakDetectorFactory.logger.debug("Loaded default ResourceLeakDetector: {}", (Object) resourceLeakDetector);
            return resourceLeakDetector;
        }
    }
}
