package com.google.maps.android.clustering.algo;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.algo.NonHierarchicalDistanceBasedAlgorithm;
import com.google.maps.android.geometry.Bounds;
import com.google.maps.android.geometry.Point;
import com.google.maps.android.projection.SphericalMercatorProjection;
import com.google.maps.android.quadtree.PointQuadTree;
import java.util.ArrayList;
import java.util.Collection;

public class NonHierarchicalViewBasedAlgorithm<T extends ClusterItem> extends NonHierarchicalDistanceBasedAlgorithm<T> implements ScreenBasedAlgorithm<T> {
    private static final SphericalMercatorProjection PROJECTION = new SphericalMercatorProjection(1.0d);
    private LatLng mMapCenter;
    private int mViewHeight;
    private int mViewWidth;

    public NonHierarchicalViewBasedAlgorithm(int screenWidth, int screenHeight) {
        this.mViewWidth = screenWidth;
        this.mViewHeight = screenHeight;
    }

    public void onCameraChange(CameraPosition cameraPosition) {
        this.mMapCenter = cameraPosition.target;
    }

    /* access modifiers changed from: protected */
    public Collection<NonHierarchicalDistanceBasedAlgorithm.QuadItem<T>> getClusteringItems(PointQuadTree<NonHierarchicalDistanceBasedAlgorithm.QuadItem<T>> quadTree, float zoom) {
        PointQuadTree<NonHierarchicalDistanceBasedAlgorithm.QuadItem<T>> pointQuadTree = quadTree;
        Bounds visibleBounds = getVisibleBounds(zoom);
        Collection<NonHierarchicalDistanceBasedAlgorithm.QuadItem<T>> items = new ArrayList<>();
        double d = visibleBounds.minX;
        if (d < 0.0d) {
            items.addAll(pointQuadTree.search(new Bounds(d + 1.0d, 1.0d, visibleBounds.minY, visibleBounds.maxY)));
            visibleBounds = new Bounds(0.0d, visibleBounds.maxX, visibleBounds.minY, visibleBounds.maxY);
        }
        double d2 = visibleBounds.maxX;
        if (d2 > 1.0d) {
            items.addAll(pointQuadTree.search(new Bounds(0.0d, d2 - 1.0d, visibleBounds.minY, visibleBounds.maxY)));
            visibleBounds = new Bounds(visibleBounds.minX, 1.0d, visibleBounds.minY, visibleBounds.maxY);
        }
        items.addAll(pointQuadTree.search(visibleBounds));
        return items;
    }

    public boolean shouldReclusterOnMapMovement() {
        return true;
    }

    public void updateViewSize(int width, int height) {
        this.mViewWidth = width;
        this.mViewHeight = height;
    }

    private Bounds getVisibleBounds(float zoom) {
        float f = zoom;
        LatLng latLng = this.mMapCenter;
        if (latLng == null) {
            return new Bounds(0.0d, 0.0d, 0.0d, 0.0d);
        }
        Point p = PROJECTION.toPoint(latLng);
        double halfWidthSpan = ((((double) this.mViewWidth) / Math.pow(2.0d, (double) f)) / 256.0d) / 2.0d;
        double halfHeightSpan = ((((double) this.mViewHeight) / Math.pow(2.0d, (double) f)) / 256.0d) / 2.0d;
        double d = p.x;
        double d2 = d - halfWidthSpan;
        double d3 = d + halfWidthSpan;
        double d4 = p.y;
        return new Bounds(d2, d3, d4 - halfHeightSpan, d4 + halfHeightSpan);
    }
}
