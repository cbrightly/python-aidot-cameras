package com.google.maps.android.collections;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.collections.MapObjectManager;

public class PolygonManager extends MapObjectManager<Polygon, Collection> implements GoogleMap.OnPolygonClickListener {
    public /* bridge */ /* synthetic */ MapObjectManager.Collection getCollection(String str) {
        return super.getCollection(str);
    }

    public /* bridge */ /* synthetic */ MapObjectManager.Collection newCollection(String str) {
        return super.newCollection(str);
    }

    public /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    public PolygonManager(GoogleMap map) {
        super(map);
    }

    /* access modifiers changed from: package-private */
    public void setListenersOnUiThread() {
        GoogleMap googleMap = this.mMap;
        if (googleMap != null) {
            googleMap.setOnPolygonClickListener(this);
        }
    }

    public Collection newCollection() {
        return new Collection();
    }

    /* access modifiers changed from: protected */
    public void removeObjectFromMap(Polygon object) {
        object.remove();
    }

    public void onPolygonClick(Polygon polygon) {
        Collection collection = (Collection) this.mAllObjects.get(polygon);
        if (collection != null && collection.mPolygonClickListener != null) {
            collection.mPolygonClickListener.onPolygonClick(polygon);
        }
    }

    public class Collection extends MapObjectManager.Collection {
        /* access modifiers changed from: private */
        public GoogleMap.OnPolygonClickListener mPolygonClickListener;

        public Collection() {
            super();
        }

        public Polygon addPolygon(PolygonOptions opts) {
            Polygon polygon = PolygonManager.this.mMap.addPolygon(opts);
            super.add(polygon);
            return polygon;
        }

        public void addAll(java.util.Collection<PolygonOptions> opts) {
            for (PolygonOptions opt : opts) {
                addPolygon(opt);
            }
        }

        public void addAll(java.util.Collection<PolygonOptions> opts, boolean defaultVisible) {
            for (PolygonOptions opt : opts) {
                addPolygon(opt).setVisible(defaultVisible);
            }
        }

        public void showAll() {
            for (Polygon polygon : getPolygons()) {
                polygon.setVisible(true);
            }
        }

        public void hideAll() {
            for (Polygon polygon : getPolygons()) {
                polygon.setVisible(false);
            }
        }

        public boolean remove(Polygon polygon) {
            return super.remove(polygon);
        }

        public java.util.Collection<Polygon> getPolygons() {
            return getObjects();
        }

        public void setOnPolygonClickListener(GoogleMap.OnPolygonClickListener polygonClickListener) {
            this.mPolygonClickListener = polygonClickListener;
        }
    }
}
