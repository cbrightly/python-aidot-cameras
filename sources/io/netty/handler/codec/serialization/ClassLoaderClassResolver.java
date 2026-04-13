package io.netty.handler.codec.serialization;

public class ClassLoaderClassResolver implements ClassResolver {
    private final ClassLoader classLoader;

    ClassLoaderClassResolver(ClassLoader classLoader2) {
        this.classLoader = classLoader2;
    }

    public Class<?> resolve(String className) {
        try {
            return this.classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            return Class.forName(className, false, this.classLoader);
        }
    }
}
