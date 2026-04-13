package com.google.maps.android.clustering;

import android.content.Context;
import android.os.AsyncTask;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.algo.Algorithm;
import com.google.maps.android.clustering.algo.NonHierarchicalDistanceBasedAlgorithm;
import com.google.maps.android.clustering.algo.PreCachingAlgorithmDecorator;
import com.google.maps.android.clustering.algo.ScreenBasedAlgorithm;
import com.google.maps.android.clustering.algo.ScreenBasedAlgorithmAdapter;
import com.google.maps.android.clustering.view.ClusterRenderer;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.collections.MarkerManager;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ClusterManager<T extends ClusterItem> implements GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {
    private ScreenBasedAlgorithm<T> mAlgorithm;
    private final MarkerManager.Collection mClusterMarkers;
    private ClusterManager<T>.ClusterTask mClusterTask;
    private final ReadWriteLock mClusterTaskLock;
    private GoogleMap mMap;
    private final MarkerManager mMarkerManager;
    private final MarkerManager.Collection mMarkers;
    private OnClusterClickListener<T> mOnClusterClickListener;
    private OnClusterInfoWindowClickListener<T> mOnClusterInfoWindowClickListener;
    private OnClusterInfoWindowLongClickListener<T> mOnClusterInfoWindowLongClickListener;
    private OnClusterItemClickListener<T> mOnClusterItemClickListener;
    private OnClusterItemInfoWindowClickListener<T> mOnClusterItemInfoWindowClickListener;
    private OnClusterItemInfoWindowLongClickListener<T> mOnClusterItemInfoWindowLongClickListener;
    private CameraPosition mPreviousCameraPosition;
    /* access modifiers changed from: private */
    public ClusterRenderer<T> mRenderer;

    public interface OnClusterClickListener<T extends ClusterItem> {
        boolean onClusterClick(Cluster<T> cluster);
    }

    public interface OnClusterInfoWindowClickListener<T extends ClusterItem> {
        void onClusterInfoWindowClick(Cluster<T> cluster);
    }

    public interface OnClusterInfoWindowLongClickListener<T extends ClusterItem> {
        void onClusterInfoWindowLongClick(Cluster<T> cluster);
    }

    public interface OnClusterItemClickListener<T extends ClusterItem> {
        boolean onClusterItemClick(T t);
    }

    public interface OnClusterItemInfoWindowClickListener<T extends ClusterItem> {
        void onClusterItemInfoWindowClick(T t);
    }

    public interface OnClusterItemInfoWindowLongClickListener<T extends ClusterItem> {
        void onClusterItemInfoWindowLongClick(T t);
    }

    public ClusterManager(Context context, GoogleMap map) {
        this(context, map, new MarkerManager(map));
    }

    public ClusterManager(Context context, GoogleMap map, MarkerManager markerManager) {
        this.mClusterTaskLock = new ReentrantReadWriteLock();
        this.mMap = map;
        this.mMarkerManager = markerManager;
        this.mClusterMarkers = markerManager.newCollection();
        this.mMarkers = markerManager.newCollection();
        this.mRenderer = new DefaultClusterRenderer(context, map, this);
        this.mAlgorithm = new ScreenBasedAlgorithmAdapter(new PreCachingAlgorithmDecorator(new NonHierarchicalDistanceBasedAlgorithm()));
        this.mClusterTask = new ClusterTask();
        this.mRenderer.onAdd();
    }

    public MarkerManager.Collection getMarkerCollection() {
        return this.mMarkers;
    }

    public MarkerManager.Collection getClusterMarkerCollection() {
        return this.mClusterMarkers;
    }

    public MarkerManager getMarkerManager() {
        return this.mMarkerManager;
    }

    public void setRenderer(ClusterRenderer<T> renderer) {
        this.mRenderer.setOnClusterClickListener((OnClusterClickListener) null);
        this.mRenderer.setOnClusterItemClickListener((OnClusterItemClickListener) null);
        this.mClusterMarkers.clear();
        this.mMarkers.clear();
        this.mRenderer.onRemove();
        this.mRenderer = renderer;
        renderer.onAdd();
        this.mRenderer.setOnClusterClickListener(this.mOnClusterClickListener);
        this.mRenderer.setOnClusterInfoWindowClickListener(this.mOnClusterInfoWindowClickListener);
        this.mRenderer.setOnClusterInfoWindowLongClickListener(this.mOnClusterInfoWindowLongClickListener);
        this.mRenderer.setOnClusterItemClickListener(this.mOnClusterItemClickListener);
        this.mRenderer.setOnClusterItemInfoWindowClickListener(this.mOnClusterItemInfoWindowClickListener);
        this.mRenderer.setOnClusterItemInfoWindowLongClickListener(this.mOnClusterItemInfoWindowLongClickListener);
        cluster();
    }

    public void setAlgorithm(Algorithm<T> algorithm) {
        if (algorithm instanceof ScreenBasedAlgorithm) {
            setAlgorithm((ScreenBasedAlgorithm) algorithm);
        } else {
            setAlgorithm(new ScreenBasedAlgorithmAdapter(algorithm));
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.maps.android.clustering.algo.ScreenBasedAlgorithm<T>, code=com.google.maps.android.clustering.algo.Algorithm<T>, for r3v0, types: [com.google.maps.android.clustering.algo.Algorithm, com.google.maps.android.clustering.algo.ScreenBasedAlgorithm<T>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setAlgorithm(com.google.maps.android.clustering.algo.Algorithm<T> r3) {
        /*
            r2 = this;
            r3.lock()
            com.google.maps.android.clustering.algo.Algorithm r0 = r2.getAlgorithm()     // Catch:{ all -> 0x003a }
            r2.mAlgorithm = r3     // Catch:{ all -> 0x003a }
            if (r0 == 0) goto L_0x001f
            r0.lock()     // Catch:{ all -> 0x003a }
            java.util.Collection r1 = r0.getItems()     // Catch:{ all -> 0x0019 }
            r3.addItems(r1)     // Catch:{ all -> 0x0019 }
            r0.unlock()     // Catch:{ all -> 0x003a }
            goto L_0x001f
        L_0x0019:
            r1 = move-exception
            r0.unlock()     // Catch:{ all -> 0x003a }
            throw r1     // Catch:{ all -> 0x003a }
        L_0x001f:
            r3.unlock()
            com.google.maps.android.clustering.algo.ScreenBasedAlgorithm<T> r0 = r2.mAlgorithm
            boolean r0 = r0.shouldReclusterOnMapMovement()
            if (r0 == 0) goto L_0x0036
            com.google.maps.android.clustering.algo.ScreenBasedAlgorithm<T> r0 = r2.mAlgorithm
            com.google.android.gms.maps.GoogleMap r1 = r2.mMap
            com.google.android.gms.maps.model.CameraPosition r1 = r1.getCameraPosition()
            r0.onCameraChange(r1)
        L_0x0036:
            r2.cluster()
            return
        L_0x003a:
            r0 = move-exception
            r3.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.maps.android.clustering.ClusterManager.setAlgorithm(com.google.maps.android.clustering.algo.ScreenBasedAlgorithm):void");
    }

    public void setAnimation(boolean animate) {
        this.mRenderer.setAnimation(animate);
    }

    public ClusterRenderer<T> getRenderer() {
        return this.mRenderer;
    }

    public Algorithm<T> getAlgorithm() {
        return this.mAlgorithm;
    }

    public void clearItems() {
        Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            algorithm.clearItems();
        } finally {
            algorithm.unlock();
        }
    }

    public boolean addItems(Collection<T> items) {
        Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            return algorithm.addItems(items);
        } finally {
            algorithm.unlock();
        }
    }

    public boolean addItem(T myItem) {
        Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            return algorithm.addItem(myItem);
        } finally {
            algorithm.unlock();
        }
    }

    public boolean removeItems(Collection<T> items) {
        Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            return algorithm.removeItems(items);
        } finally {
            algorithm.unlock();
        }
    }

    public boolean removeItem(T item) {
        Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            return algorithm.removeItem(item);
        } finally {
            algorithm.unlock();
        }
    }

    public boolean updateItem(T item) {
        Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            return algorithm.updateItem(item);
        } finally {
            algorithm.unlock();
        }
    }

    public void cluster() {
        this.mClusterTaskLock.writeLock().lock();
        try {
            this.mClusterTask.cancel(true);
            ClusterManager<T>.ClusterTask clusterTask = new ClusterTask();
            this.mClusterTask = clusterTask;
            clusterTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Float[]{Float.valueOf(this.mMap.getCameraPosition().zoom)});
        } finally {
            this.mClusterTaskLock.writeLock().unlock();
        }
    }

    public void onCameraIdle() {
        ClusterRenderer<T> clusterRenderer = this.mRenderer;
        if (clusterRenderer instanceof GoogleMap.OnCameraIdleListener) {
            ((GoogleMap.OnCameraIdleListener) clusterRenderer).onCameraIdle();
        }
        this.mAlgorithm.onCameraChange(this.mMap.getCameraPosition());
        if (this.mAlgorithm.shouldReclusterOnMapMovement()) {
            cluster();
            return;
        }
        CameraPosition cameraPosition = this.mPreviousCameraPosition;
        if (cameraPosition == null || cameraPosition.zoom != this.mMap.getCameraPosition().zoom) {
            this.mPreviousCameraPosition = this.mMap.getCameraPosition();
            cluster();
        }
    }

    public boolean onMarkerClick(Marker marker) {
        return getMarkerManager().onMarkerClick(marker);
    }

    public void onInfoWindowClick(Marker marker) {
        getMarkerManager().onInfoWindowClick(marker);
    }

    public class ClusterTask extends AsyncTask<Float, Void, Set<? extends Cluster<T>>> {
        private ClusterTask() {
        }

        /* access modifiers changed from: protected */
        public Set<? extends Cluster<T>> doInBackground(Float... zoom) {
            Algorithm<T> algorithm = ClusterManager.this.getAlgorithm();
            algorithm.lock();
            try {
                return algorithm.getClusters(zoom[0].floatValue());
            } finally {
                algorithm.unlock();
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Set<? extends Cluster<T>> clusters) {
            ClusterManager.this.mRenderer.onClustersChanged(clusters);
        }
    }

    public void setOnClusterClickListener(OnClusterClickListener<T> listener) {
        this.mOnClusterClickListener = listener;
        this.mRenderer.setOnClusterClickListener(listener);
    }

    public void setOnClusterInfoWindowClickListener(OnClusterInfoWindowClickListener<T> listener) {
        this.mOnClusterInfoWindowClickListener = listener;
        this.mRenderer.setOnClusterInfoWindowClickListener(listener);
    }

    public void setOnClusterInfoWindowLongClickListener(OnClusterInfoWindowLongClickListener<T> listener) {
        this.mOnClusterInfoWindowLongClickListener = listener;
        this.mRenderer.setOnClusterInfoWindowLongClickListener(listener);
    }

    public void setOnClusterItemClickListener(OnClusterItemClickListener<T> listener) {
        this.mOnClusterItemClickListener = listener;
        this.mRenderer.setOnClusterItemClickListener(listener);
    }

    public void setOnClusterItemInfoWindowClickListener(OnClusterItemInfoWindowClickListener<T> listener) {
        this.mOnClusterItemInfoWindowClickListener = listener;
        this.mRenderer.setOnClusterItemInfoWindowClickListener(listener);
    }

    public void setOnClusterItemInfoWindowLongClickListener(OnClusterItemInfoWindowLongClickListener<T> listener) {
        this.mOnClusterItemInfoWindowLongClickListener = listener;
        this.mRenderer.setOnClusterItemInfoWindowLongClickListener(listener);
    }
}
