package com.google.maps.android.collections;

import android.view.View;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.collections.MapObjectManager;

public class MarkerManager extends MapObjectManager<Marker, Collection> implements GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener, GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowLongClickListener {
    public /* bridge */ /* synthetic */ MapObjectManager.Collection getCollection(String str) {
        return super.getCollection(str);
    }

    public /* bridge */ /* synthetic */ MapObjectManager.Collection newCollection(String str) {
        return super.newCollection(str);
    }

    public /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    public MarkerManager(GoogleMap map) {
        super(map);
    }

    /* access modifiers changed from: package-private */
    public void setListenersOnUiThread() {
        GoogleMap googleMap = this.mMap;
        if (googleMap != null) {
            googleMap.setOnInfoWindowClickListener(this);
            this.mMap.setOnInfoWindowLongClickListener(this);
            this.mMap.setOnMarkerClickListener(this);
            this.mMap.setOnMarkerDragListener(this);
            this.mMap.setInfoWindowAdapter(this);
        }
    }

    public Collection newCollection() {
        return new Collection();
    }

    public View getInfoWindow(Marker marker) {
        Collection collection = (Collection) this.mAllObjects.get(marker);
        if (collection == null || collection.mInfoWindowAdapter == null) {
            return null;
        }
        return collection.mInfoWindowAdapter.getInfoWindow(marker);
    }

    public View getInfoContents(Marker marker) {
        Collection collection = (Collection) this.mAllObjects.get(marker);
        if (collection == null || collection.mInfoWindowAdapter == null) {
            return null;
        }
        return collection.mInfoWindowAdapter.getInfoContents(marker);
    }

    public void onInfoWindowClick(Marker marker) {
        Collection collection = (Collection) this.mAllObjects.get(marker);
        if (collection != null && collection.mInfoWindowClickListener != null) {
            collection.mInfoWindowClickListener.onInfoWindowClick(marker);
        }
    }

    public void onInfoWindowLongClick(Marker marker) {
        Collection collection = (Collection) this.mAllObjects.get(marker);
        if (collection != null && collection.mInfoWindowLongClickListener != null) {
            collection.mInfoWindowLongClickListener.onInfoWindowLongClick(marker);
        }
    }

    public boolean onMarkerClick(Marker marker) {
        Collection collection = (Collection) this.mAllObjects.get(marker);
        if (collection == null || collection.mMarkerClickListener == null) {
            return false;
        }
        return collection.mMarkerClickListener.onMarkerClick(marker);
    }

    public void onMarkerDragStart(Marker marker) {
        Collection collection = (Collection) this.mAllObjects.get(marker);
        if (collection != null && collection.mMarkerDragListener != null) {
            collection.mMarkerDragListener.onMarkerDragStart(marker);
        }
    }

    public void onMarkerDrag(Marker marker) {
        Collection collection = (Collection) this.mAllObjects.get(marker);
        if (collection != null && collection.mMarkerDragListener != null) {
            collection.mMarkerDragListener.onMarkerDrag(marker);
        }
    }

    public void onMarkerDragEnd(Marker marker) {
        Collection collection = (Collection) this.mAllObjects.get(marker);
        if (collection != null && collection.mMarkerDragListener != null) {
            collection.mMarkerDragListener.onMarkerDragEnd(marker);
        }
    }

    /* access modifiers changed from: protected */
    public void removeObjectFromMap(Marker object) {
        object.remove();
    }

    public class Collection extends MapObjectManager.Collection {
        /* access modifiers changed from: private */
        public GoogleMap.InfoWindowAdapter mInfoWindowAdapter;
        /* access modifiers changed from: private */
        public GoogleMap.OnInfoWindowClickListener mInfoWindowClickListener;
        /* access modifiers changed from: private */
        public GoogleMap.OnInfoWindowLongClickListener mInfoWindowLongClickListener;
        /* access modifiers changed from: private */
        public GoogleMap.OnMarkerClickListener mMarkerClickListener;
        /* access modifiers changed from: private */
        public GoogleMap.OnMarkerDragListener mMarkerDragListener;

        public Collection() {
            super();
        }

        public Marker addMarker(MarkerOptions opts) {
            Marker marker = MarkerManager.this.mMap.addMarker(opts);
            super.add(marker);
            return marker;
        }

        public void addAll(java.util.Collection<MarkerOptions> opts) {
            for (MarkerOptions opt : opts) {
                addMarker(opt);
            }
        }

        public void addAll(java.util.Collection<MarkerOptions> opts, boolean defaultVisible) {
            for (MarkerOptions opt : opts) {
                addMarker(opt).setVisible(defaultVisible);
            }
        }

        public void showAll() {
            for (Marker marker : getMarkers()) {
                marker.setVisible(true);
            }
        }

        public void hideAll() {
            for (Marker marker : getMarkers()) {
                marker.setVisible(false);
            }
        }

        public boolean remove(Marker marker) {
            return super.remove(marker);
        }

        public java.util.Collection<Marker> getMarkers() {
            return getObjects();
        }

        public void setOnInfoWindowClickListener(GoogleMap.OnInfoWindowClickListener infoWindowClickListener) {
            this.mInfoWindowClickListener = infoWindowClickListener;
        }

        public void setOnInfoWindowLongClickListener(GoogleMap.OnInfoWindowLongClickListener infoWindowLongClickListener) {
            this.mInfoWindowLongClickListener = infoWindowLongClickListener;
        }

        public void setOnMarkerClickListener(GoogleMap.OnMarkerClickListener markerClickListener) {
            this.mMarkerClickListener = markerClickListener;
        }

        public void setOnMarkerDragListener(GoogleMap.OnMarkerDragListener markerDragListener) {
            this.mMarkerDragListener = markerDragListener;
        }

        public void setInfoWindowAdapter(GoogleMap.InfoWindowAdapter infoWindowAdapter) {
            this.mInfoWindowAdapter = infoWindowAdapter;
        }
    }
}
