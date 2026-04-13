package org.glassfish.grizzly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.glassfish.grizzly.threadpool.DefaultWorkerThread;

public final class ThreadCache {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    /* access modifiers changed from: private */
    public static final ObjectCacheElement[] INITIAL_OBJECT_ARRAY = new ObjectCacheElement[16];
    private static final ThreadLocal<ObjectCache> genericCacheAttr = new ThreadLocal<>();
    private static int indexCounter;
    private static final Map<String, CachedTypeIndex> typeIndexMap = new HashMap();

    public static synchronized <E> CachedTypeIndex<E> obtainIndex(Class<E> clazz, int size) {
        CachedTypeIndex<E> obtainIndex;
        synchronized (ThreadCache.class) {
            obtainIndex = obtainIndex(clazz.getName(), clazz, size);
        }
        return obtainIndex;
    }

    public static synchronized <E> CachedTypeIndex<E> obtainIndex(String name, Class<E> clazz, int size) {
        CachedTypeIndex<E> typeIndex;
        synchronized (ThreadCache.class) {
            Map<String, CachedTypeIndex> map = typeIndexMap;
            typeIndex = map.get(name);
            if (typeIndex == null) {
                int i = indexCounter;
                indexCounter = i + 1;
                typeIndex = new CachedTypeIndex<>(i, name, clazz, size);
                map.put(name, typeIndex);
            }
        }
        return typeIndex;
    }

    public static <E> boolean putToCache(CachedTypeIndex<E> index, E o) {
        return putToCache(Thread.currentThread(), index, o);
    }

    public static <E> boolean putToCache(Thread currentThread, CachedTypeIndex<E> index, E o) {
        if (currentThread instanceof DefaultWorkerThread) {
            return ((DefaultWorkerThread) currentThread).putToCache(index, o);
        }
        ThreadLocal<ObjectCache> threadLocal = genericCacheAttr;
        ObjectCache genericCache = threadLocal.get();
        if (genericCache == null) {
            genericCache = new ObjectCache();
            threadLocal.set(genericCache);
        }
        return genericCache.put(index, o);
    }

    public static <E> E getFromCache(CachedTypeIndex<E> index) {
        return getFromCache(Thread.currentThread(), index);
    }

    public static <E> E getFromCache(Thread currentThread, CachedTypeIndex<E> index) {
        if (currentThread != Thread.currentThread()) {
            throw new AssertionError();
        } else if (currentThread instanceof DefaultWorkerThread) {
            return ((DefaultWorkerThread) currentThread).getFromCache(index);
        } else {
            ObjectCache genericCache = genericCacheAttr.get();
            if (genericCache != null) {
                return genericCache.get(index);
            }
            return null;
        }
    }

    public static <E> E takeFromCache(CachedTypeIndex<E> index) {
        return takeFromCache(Thread.currentThread(), index);
    }

    public static <E> E takeFromCache(Thread currentThread, CachedTypeIndex<E> index) {
        if (currentThread instanceof DefaultWorkerThread) {
            return ((DefaultWorkerThread) currentThread).takeFromCache(index);
        }
        ObjectCache genericCache = genericCacheAttr.get();
        if (genericCache != null) {
            return genericCache.take(index);
        }
        return null;
    }

    public static final class ObjectCache {
        private ObjectCacheElement[] objectCacheElements;

        public boolean put(CachedTypeIndex index, Object o) {
            if (this.objectCacheElements != null) {
                int index2 = index.getIndex();
                ObjectCacheElement[] objectCacheElementArr = this.objectCacheElements;
                if (index2 < objectCacheElementArr.length) {
                    ObjectCacheElement objectCache = objectCacheElementArr[index.getIndex()];
                    if (objectCache == null) {
                        objectCache = new ObjectCacheElement(index.size);
                        this.objectCacheElements[index.getIndex()] = objectCache;
                    }
                    return objectCache.put(o);
                }
            }
            ObjectCacheElement[] arrayToGrow = this.objectCacheElements;
            if (arrayToGrow == null) {
                arrayToGrow = ThreadCache.INITIAL_OBJECT_ARRAY;
            }
            this.objectCacheElements = (ObjectCacheElement[]) Arrays.copyOf(arrayToGrow, Math.max(index.getIndex() + 1, ((arrayToGrow.length * 3) / 2) + 1));
            ObjectCacheElement objectCache2 = new ObjectCacheElement(index.getSize());
            this.objectCacheElements[index.getIndex()] = objectCache2;
            return objectCache2.put(o);
        }

        public <E> E get(CachedTypeIndex<E> index) {
            ObjectCacheElement objectCache;
            if (this.objectCacheElements != null) {
                int index2 = index.getIndex();
                int idx = index2;
                ObjectCacheElement[] objectCacheElementArr = this.objectCacheElements;
                if (index2 >= objectCacheElementArr.length || (objectCache = objectCacheElementArr[idx]) == null) {
                    return null;
                }
                return objectCache.get();
            }
            return null;
        }

        public <E> E take(CachedTypeIndex<E> index) {
            ObjectCacheElement objectCache;
            if (this.objectCacheElements != null) {
                int index2 = index.getIndex();
                int idx = index2;
                ObjectCacheElement[] objectCacheElementArr = this.objectCacheElements;
                if (index2 >= objectCacheElementArr.length || (objectCache = objectCacheElementArr[idx]) == null) {
                    return null;
                }
                return objectCache.take();
            }
            return null;
        }
    }

    public static final class ObjectCacheElement {
        private final Object[] cache;
        private int index;
        private final int size;

        public ObjectCacheElement(int size2) {
            this.size = size2;
            this.cache = new Object[size2];
        }

        public boolean put(Object o) {
            int i = this.index;
            if (i >= this.size) {
                return false;
            }
            Object[] objArr = this.cache;
            this.index = i + 1;
            objArr[i] = o;
            return true;
        }

        public Object get() {
            int i = this.index;
            if (i > 0) {
                return this.cache[i - 1];
            }
            return null;
        }

        public Object take() {
            int i = this.index;
            if (i <= 0) {
                return null;
            }
            int i2 = i - 1;
            this.index = i2;
            Object[] objArr = this.cache;
            Object o = objArr[i2];
            objArr[i2] = null;
            return o;
        }
    }

    public static final class CachedTypeIndex<E> {
        private final Class clazz;
        private final int index;
        private final String name;
        /* access modifiers changed from: private */
        public final int size;

        public CachedTypeIndex(int index2, String name2, Class<E> clazz2, int size2) {
            this.index = index2;
            this.name = name2;
            this.clazz = clazz2;
            this.size = size2;
        }

        public int getIndex() {
            return this.index;
        }

        public String getName() {
            return this.name;
        }

        public Class getClazz() {
            return this.clazz;
        }

        public int getSize() {
            return this.size;
        }
    }
}
