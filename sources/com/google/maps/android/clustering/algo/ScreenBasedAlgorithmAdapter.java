package com.google.maps.android.clustering.algo;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import java.util.Collection;
import java.util.Set;

public class ScreenBasedAlgorithmAdapter<T extends ClusterItem> extends AbstractAlgorithm<T> implements ScreenBasedAlgorithm<T> {
    private Algorithm<T> mAlgorithm;

    public ScreenBasedAlgorithmAdapter(Algorithm<T> algorithm) {
        this.mAlgorithm = algorithm;
    }

    public boolean shouldReclusterOnMapMovement() {
        return false;
    }

    public boolean addItem(T item) {
        return this.mAlgorithm.addItem(item);
    }

    public boolean addItems(Collection<T> items) {
        return this.mAlgorithm.addItems(items);
    }

    public void clearItems() {
        this.mAlgorithm.clearItems();
    }

    public boolean removeItem(T item) {
        return this.mAlgorithm.removeItem(item);
    }

    public boolean removeItems(Collection<T> items) {
        return this.mAlgorithm.removeItems(items);
    }

    public boolean updateItem(T item) {
        return this.mAlgorithm.updateItem(item);
    }

    public Set<? extends Cluster<T>> getClusters(float zoom) {
        return this.mAlgorithm.getClusters(zoom);
    }

    public Collection<T> getItems() {
        return this.mAlgorithm.getItems();
    }

    public void setMaxDistanceBetweenClusteredItems(int maxDistance) {
        this.mAlgorithm.setMaxDistanceBetweenClusteredItems(maxDistance);
    }

    public int getMaxDistanceBetweenClusteredItems() {
        return this.mAlgorithm.getMaxDistanceBetweenClusteredItems();
    }

    public void onCameraChange(CameraPosition cameraPosition) {
    }
}
