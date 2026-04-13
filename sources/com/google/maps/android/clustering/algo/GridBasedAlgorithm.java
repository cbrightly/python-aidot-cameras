package com.google.maps.android.clustering.algo;

import androidx.collection.LongSparseArray;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.geometry.Point;
import com.google.maps.android.projection.SphericalMercatorProjection;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GridBasedAlgorithm<T extends ClusterItem> extends AbstractAlgorithm<T> {
    private static final int DEFAULT_GRID_SIZE = 100;
    private int mGridSize = 100;
    private final Set<T> mItems = Collections.synchronizedSet(new HashSet());

    public boolean addItem(T item) {
        return this.mItems.add(item);
    }

    public boolean addItems(Collection<T> items) {
        return this.mItems.addAll(items);
    }

    public void clearItems() {
        this.mItems.clear();
    }

    public boolean removeItem(T item) {
        return this.mItems.remove(item);
    }

    public boolean removeItems(Collection<T> items) {
        return this.mItems.removeAll(items);
    }

    public boolean updateItem(T item) {
        boolean result;
        synchronized (this.mItems) {
            result = removeItem(item);
            if (result) {
                result = addItem(item);
            }
        }
        return result;
    }

    public void setMaxDistanceBetweenClusteredItems(int maxDistance) {
        this.mGridSize = maxDistance;
    }

    public int getMaxDistanceBetweenClusteredItems() {
        return this.mGridSize;
    }

    public Set<? extends Cluster<T>> getClusters(float zoom) {
        long numCells;
        long numCells2 = (long) Math.ceil((Math.pow(2.0d, (double) zoom) * 256.0d) / ((double) this.mGridSize));
        SphericalMercatorProjection proj = new SphericalMercatorProjection((double) numCells2);
        HashSet<Cluster<T>> clusters = new HashSet<>();
        LongSparseArray longSparseArray = new LongSparseArray();
        synchronized (this.mItems) {
            try {
                Iterator<T> it = this.mItems.iterator();
                while (it.hasNext()) {
                    ClusterItem clusterItem = (ClusterItem) it.next();
                    Point p = proj.toPoint(clusterItem.getPosition());
                    Iterator<T> it2 = it;
                    Point p2 = p;
                    long coord = getCoord(numCells2, p.x, p.y);
                    StaticCluster<T> cluster = (StaticCluster) longSparseArray.get(coord);
                    if (cluster == null) {
                        numCells = numCells2;
                        cluster = new StaticCluster<>(proj.toLatLng(new Point(Math.floor(p2.x) + 0.5d, Math.floor(p2.y) + 0.5d)));
                        longSparseArray.put(coord, cluster);
                        clusters.add(cluster);
                    } else {
                        numCells = numCells2;
                    }
                    cluster.add(clusterItem);
                    float f = zoom;
                    it = it2;
                    numCells2 = numCells;
                }
                return clusters;
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        }
    }

    public Collection<T> getItems() {
        return this.mItems;
    }

    private static long getCoord(long numCells, double x, double y) {
        return (long) ((((double) numCells) * Math.floor(x)) + Math.floor(y));
    }
}
