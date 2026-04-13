package com.google.maps.android.clustering.algo;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.geometry.Bounds;
import com.google.maps.android.geometry.Point;
import com.google.maps.android.projection.SphericalMercatorProjection;
import com.google.maps.android.quadtree.PointQuadTree;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class NonHierarchicalDistanceBasedAlgorithm<T extends ClusterItem> extends AbstractAlgorithm<T> {
    private static final int DEFAULT_MAX_DISTANCE_AT_ZOOM = 100;
    /* access modifiers changed from: private */
    public static final SphericalMercatorProjection PROJECTION = new SphericalMercatorProjection(1.0d);
    private final Collection<QuadItem<T>> mItems = new LinkedHashSet();
    private int mMaxDistance = 100;
    private final PointQuadTree<QuadItem<T>> mQuadTree = new PointQuadTree(0.0d, 1.0d, 0.0d, 1.0d);

    public boolean addItem(T item) {
        boolean result;
        QuadItem<T> quadItem = new QuadItem<>(item);
        synchronized (this.mQuadTree) {
            result = this.mItems.add(quadItem);
            if (result) {
                this.mQuadTree.add(quadItem);
            }
        }
        return result;
    }

    public boolean addItems(Collection<T> items) {
        boolean result = false;
        for (T item : items) {
            if (addItem(item)) {
                result = true;
            }
        }
        return result;
    }

    public void clearItems() {
        synchronized (this.mQuadTree) {
            this.mItems.clear();
            this.mQuadTree.clear();
        }
    }

    public boolean removeItem(T item) {
        boolean result;
        QuadItem<T> quadItem = new QuadItem<>(item);
        synchronized (this.mQuadTree) {
            result = this.mItems.remove(quadItem);
            if (result) {
                this.mQuadTree.remove(quadItem);
            }
        }
        return result;
    }

    public boolean removeItems(Collection<T> items) {
        boolean result = false;
        synchronized (this.mQuadTree) {
            for (T item : items) {
                QuadItem<T> quadItem = new QuadItem<>(item);
                if (this.mItems.remove(quadItem)) {
                    this.mQuadTree.remove(quadItem);
                    result = true;
                }
            }
        }
        return result;
    }

    public boolean updateItem(T item) {
        boolean result;
        synchronized (this.mQuadTree) {
            result = removeItem(item);
            if (result) {
                result = addItem(item);
            }
        }
        return result;
    }

    public Set<? extends Cluster<T>> getClusters(float zoom) {
        double zoomSpecificSpan;
        NonHierarchicalDistanceBasedAlgorithm nonHierarchicalDistanceBasedAlgorithm = this;
        float f = zoom;
        int discreteZoom = (int) f;
        double zoomSpecificSpan2 = (((double) nonHierarchicalDistanceBasedAlgorithm.mMaxDistance) / Math.pow(2.0d, (double) discreteZoom)) / 256.0d;
        Set<QuadItem<T>> visitedCandidates = new HashSet<>();
        Set<Cluster<T>> results = new HashSet<>();
        Map<QuadItem<T>, Double> distanceToCluster = new HashMap<>();
        Map<QuadItem<T>, StaticCluster<T>> itemToCluster = new HashMap<>();
        synchronized (nonHierarchicalDistanceBasedAlgorithm.mQuadTree) {
            try {
                Iterator<QuadItem<QuadItem<T>>> it = nonHierarchicalDistanceBasedAlgorithm.getClusteringItems(nonHierarchicalDistanceBasedAlgorithm.mQuadTree, f).iterator();
                while (it.hasNext()) {
                    QuadItem<T> candidate = it.next();
                    if (!visitedCandidates.contains(candidate)) {
                        Collection<QuadItem<T>> clusterItems = nonHierarchicalDistanceBasedAlgorithm.mQuadTree.search(nonHierarchicalDistanceBasedAlgorithm.createBoundsFromSpan(candidate.getPoint(), zoomSpecificSpan2));
                        if (clusterItems.size() == 1) {
                            try {
                                results.add(candidate);
                                visitedCandidates.add(candidate);
                                distanceToCluster.put(candidate, Double.valueOf(0.0d));
                            } catch (Throwable th) {
                                th = th;
                                int i = discreteZoom;
                                double d = zoomSpecificSpan2;
                            }
                        } else {
                            StaticCluster<T> cluster = new StaticCluster<>(candidate.mClusterItem.getPosition());
                            results.add(cluster);
                            for (QuadItem next : clusterItems) {
                                Iterator<QuadItem<QuadItem<T>>> it2 = it;
                                Double existingDistance = distanceToCluster.get(next);
                                int discreteZoom2 = discreteZoom;
                                try {
                                    double distance = nonHierarchicalDistanceBasedAlgorithm.distanceSquared(next.getPoint(), candidate.getPoint());
                                    if (existingDistance == null) {
                                        zoomSpecificSpan = zoomSpecificSpan2;
                                    } else if (existingDistance.doubleValue() < distance) {
                                        float f2 = zoom;
                                        it = it2;
                                        discreteZoom = discreteZoom2;
                                    } else {
                                        zoomSpecificSpan = zoomSpecificSpan2;
                                        itemToCluster.get(next).remove(next.mClusterItem);
                                    }
                                    distanceToCluster.put(next, Double.valueOf(distance));
                                    cluster.add(next.mClusterItem);
                                    itemToCluster.put(next, cluster);
                                    nonHierarchicalDistanceBasedAlgorithm = this;
                                    float f3 = zoom;
                                    it = it2;
                                    discreteZoom = discreteZoom2;
                                    zoomSpecificSpan2 = zoomSpecificSpan;
                                } catch (Throwable th2) {
                                    th = th2;
                                    throw th;
                                }
                            }
                            visitedCandidates.addAll(clusterItems);
                            nonHierarchicalDistanceBasedAlgorithm = this;
                            float f4 = zoom;
                            it = it;
                            discreteZoom = discreteZoom;
                            zoomSpecificSpan2 = zoomSpecificSpan2;
                        }
                    }
                }
                double d2 = zoomSpecificSpan2;
                return results;
            } catch (Throwable th3) {
                th = th3;
                int i2 = discreteZoom;
                double d3 = zoomSpecificSpan2;
                throw th;
            }
        }
    }

    /* access modifiers changed from: protected */
    public Collection<QuadItem<T>> getClusteringItems(PointQuadTree<QuadItem<T>> pointQuadTree, float zoom) {
        return this.mItems;
    }

    public Collection<T> getItems() {
        Set<T> items = new LinkedHashSet<>();
        synchronized (this.mQuadTree) {
            for (QuadItem<T> quadItem : this.mItems) {
                items.add(quadItem.mClusterItem);
            }
        }
        return items;
    }

    public void setMaxDistanceBetweenClusteredItems(int maxDistance) {
        this.mMaxDistance = maxDistance;
    }

    public int getMaxDistanceBetweenClusteredItems() {
        return this.mMaxDistance;
    }

    private double distanceSquared(Point a, Point b) {
        double d = a.x;
        double d2 = b.x;
        double d3 = (d - d2) * (d - d2);
        double d4 = a.y;
        double d5 = b.y;
        return d3 + ((d4 - d5) * (d4 - d5));
    }

    private Bounds createBoundsFromSpan(Point p, double span) {
        Point point = p;
        double halfSpan = span / 2.0d;
        double d = point.x;
        double d2 = d - halfSpan;
        double d3 = d + halfSpan;
        double d4 = point.y;
        return new Bounds(d2, d3, d4 - halfSpan, d4 + halfSpan);
    }

    public static class QuadItem<T extends ClusterItem> implements PointQuadTree.Item, Cluster<T> {
        /* access modifiers changed from: private */
        public final T mClusterItem;
        private final Point mPoint;
        private final LatLng mPosition;
        private Set<T> singletonSet;

        private QuadItem(T item) {
            this.mClusterItem = item;
            LatLng position = item.getPosition();
            this.mPosition = position;
            this.mPoint = NonHierarchicalDistanceBasedAlgorithm.PROJECTION.toPoint(position);
            this.singletonSet = Collections.singleton(item);
        }

        public Point getPoint() {
            return this.mPoint;
        }

        public LatLng getPosition() {
            return this.mPosition;
        }

        public Set<T> getItems() {
            return this.singletonSet;
        }

        public int getSize() {
            return 1;
        }

        public int hashCode() {
            return this.mClusterItem.hashCode();
        }

        public boolean equals(Object other) {
            if (!(other instanceof QuadItem)) {
                return false;
            }
            return ((QuadItem) other).mClusterItem.equals(this.mClusterItem);
        }
    }
}
