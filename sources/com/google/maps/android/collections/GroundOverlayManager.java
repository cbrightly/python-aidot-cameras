package com.google.maps.android.collections;

import androidx.annotation.NonNull;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.maps.android.collections.MapObjectManager;

public class GroundOverlayManager extends MapObjectManager<GroundOverlay, Collection> implements GoogleMap.OnGroundOverlayClickListener {
    public /* bridge */ /* synthetic */ MapObjectManager.Collection getCollection(String str) {
        return super.getCollection(str);
    }

    public /* bridge */ /* synthetic */ MapObjectManager.Collection newCollection(String str) {
        return super.newCollection(str);
    }

    public /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    public GroundOverlayManager(@NonNull GoogleMap map) {
        super(map);
    }

    /* access modifiers changed from: package-private */
    public void setListenersOnUiThread() {
        GoogleMap googleMap = this.mMap;
        if (googleMap != null) {
            googleMap.setOnGroundOverlayClickListener(this);
        }
    }

    public Collection newCollection() {
        return new Collection();
    }

    /* access modifiers changed from: protected */
    public void removeObjectFromMap(GroundOverlay object) {
        object.remove();
    }

    public void onGroundOverlayClick(GroundOverlay groundOverlay) {
        Collection collection = (Collection) this.mAllObjects.get(groundOverlay);
        if (collection != null && collection.mGroundOverlayClickListener != null) {
            collection.mGroundOverlayClickListener.onGroundOverlayClick(groundOverlay);
        }
    }

    public class Collection extends MapObjectManager.Collection {
        /* access modifiers changed from: private */
        public GoogleMap.OnGroundOverlayClickListener mGroundOverlayClickListener;

        public Collection() {
            super();
        }

        public GroundOverlay addGroundOverlay(GroundOverlayOptions opts) {
            GroundOverlay groundOverlay = GroundOverlayManager.this.mMap.addGroundOverlay(opts);
            super.add(groundOverlay);
            return groundOverlay;
        }

        public void addAll(java.util.Collection<GroundOverlayOptions> opts) {
            for (GroundOverlayOptions opt : opts) {
                addGroundOverlay(opt);
            }
        }

        public void addAll(java.util.Collection<GroundOverlayOptions> opts, boolean defaultVisible) {
            for (GroundOverlayOptions opt : opts) {
                addGroundOverlay(opt).setVisible(defaultVisible);
            }
        }

        public void showAll() {
            for (GroundOverlay groundOverlay : getGroundOverlays()) {
                groundOverlay.setVisible(true);
            }
        }

        public void hideAll() {
            for (GroundOverlay groundOverlay : getGroundOverlays()) {
                groundOverlay.setVisible(false);
            }
        }

        public boolean remove(GroundOverlay groundOverlay) {
            return super.remove(groundOverlay);
        }

        public java.util.Collection<GroundOverlay> getGroundOverlays() {
            return getObjects();
        }

        public void setOnGroundOverlayClickListener(GoogleMap.OnGroundOverlayClickListener groundOverlayClickListener) {
            this.mGroundOverlayClickListener = groundOverlayClickListener;
        }
    }
}
