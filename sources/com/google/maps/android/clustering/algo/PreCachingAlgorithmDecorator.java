package com.google.maps.android.clustering.algo;

import androidx.collection.LruCache;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PreCachingAlgorithmDecorator<T extends ClusterItem> extends AbstractAlgorithm<T> {
    private final Algorithm<T> mAlgorithm;
    private final LruCache<Integer, Set<? extends Cluster<T>>> mCache = new LruCache<>(5);
    private final ReadWriteLock mCacheLock = new ReentrantReadWriteLock();
    private final Executor mExecutor = Executors.newCachedThreadPool();

    public PreCachingAlgorithmDecorator(Algorithm<T> algorithm) {
        this.mAlgorithm = algorithm;
    }

    public boolean addItem(T item) {
        boolean result = this.mAlgorithm.addItem(item);
        if (result) {
            clearCache();
        }
        return result;
    }

    public boolean addItems(Collection<T> items) {
        boolean result = this.mAlgorithm.addItems(items);
        if (result) {
            clearCache();
        }
        return result;
    }

    public void clearItems() {
        this.mAlgorithm.clearItems();
        clearCache();
    }

    public boolean removeItem(T item) {
        boolean result = this.mAlgorithm.removeItem(item);
        if (result) {
            clearCache();
        }
        return result;
    }

    public boolean removeItems(Collection<T> items) {
        boolean result = this.mAlgorithm.removeItems(items);
        if (result) {
            clearCache();
        }
        return result;
    }

    public boolean updateItem(T item) {
        boolean result = this.mAlgorithm.updateItem(item);
        if (result) {
            clearCache();
        }
        return result;
    }

    private void clearCache() {
        this.mCache.evictAll();
    }

    public Set<? extends Cluster<T>> getClusters(float zoom) {
        int discreteZoom = (int) zoom;
        Set<? extends Cluster<T>> results = getClustersInternal(discreteZoom);
        if (this.mCache.get(Integer.valueOf(discreteZoom + 1)) == null) {
            this.mExecutor.execute(new PrecacheRunnable(discreteZoom + 1));
        }
        if (this.mCache.get(Integer.valueOf(discreteZoom - 1)) == null) {
            this.mExecutor.execute(new PrecacheRunnable(discreteZoom - 1));
        }
        return results;
    }

    public Collection<T> getItems() {
        return this.mAlgorithm.getItems();
    }

    public void setMaxDistanceBetweenClusteredItems(int maxDistance) {
        this.mAlgorithm.setMaxDistanceBetweenClusteredItems(maxDistance);
        clearCache();
    }

    public int getMaxDistanceBetweenClusteredItems() {
        return this.mAlgorithm.getMaxDistanceBetweenClusteredItems();
    }

    /* access modifiers changed from: private */
    public Set<? extends Cluster<T>> getClustersInternal(int discreteZoom) {
        this.mCacheLock.readLock().lock();
        Set<? extends Cluster<T>> results = this.mCache.get(Integer.valueOf(discreteZoom));
        this.mCacheLock.readLock().unlock();
        if (results == null) {
            this.mCacheLock.writeLock().lock();
            results = this.mCache.get(Integer.valueOf(discreteZoom));
            if (results == null) {
                results = this.mAlgorithm.getClusters((float) discreteZoom);
                this.mCache.put(Integer.valueOf(discreteZoom), results);
            }
            this.mCacheLock.writeLock().unlock();
        }
        return results;
    }

    public class PrecacheRunnable implements Runnable {
        private final int mZoom;

        public PrecacheRunnable(int zoom) {
            this.mZoom = zoom;
        }

        public void run() {
            try {
                Thread.sleep((long) ((Math.random() * 500.0d) + 500.0d));
            } catch (InterruptedException e) {
            }
            Set unused = PreCachingAlgorithmDecorator.this.getClustersInternal(this.mZoom);
        }
    }
}
