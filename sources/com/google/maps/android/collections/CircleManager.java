package com.google.maps.android.collections;

import androidx.annotation.NonNull;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.maps.android.collections.MapObjectManager;

public class CircleManager extends MapObjectManager<Circle, Collection> implements GoogleMap.OnCircleClickListener {
    public /* bridge */ /* synthetic */ MapObjectManager.Collection getCollection(String str) {
        return super.getCollection(str);
    }

    public /* bridge */ /* synthetic */ MapObjectManager.Collection newCollection(String str) {
        return super.newCollection(str);
    }

    public /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    public CircleManager(@NonNull GoogleMap map) {
        super(map);
    }

    /* access modifiers changed from: package-private */
    public void setListenersOnUiThread() {
        GoogleMap googleMap = this.mMap;
        if (googleMap != null) {
            googleMap.setOnCircleClickListener(this);
        }
    }

    public Collection newCollection() {
        return new Collection();
    }

    /* access modifiers changed from: protected */
    public void removeObjectFromMap(Circle object) {
        object.remove();
    }

    public void onCircleClick(Circle circle) {
        Collection collection = (Collection) this.mAllObjects.get(circle);
        if (collection != null && collection.mCircleClickListener != null) {
            collection.mCircleClickListener.onCircleClick(circle);
        }
    }

    public class Collection extends MapObjectManager.Collection {
        /* access modifiers changed from: private */
        public GoogleMap.OnCircleClickListener mCircleClickListener;

        public Collection() {
            super();
        }

        public Circle addCircle(CircleOptions opts) {
            Circle circle = CircleManager.this.mMap.addCircle(opts);
            super.add(circle);
            return circle;
        }

        public void addAll(java.util.Collection<CircleOptions> opts) {
            for (CircleOptions opt : opts) {
                addCircle(opt);
            }
        }

        public void addAll(java.util.Collection<CircleOptions> opts, boolean defaultVisible) {
            for (CircleOptions opt : opts) {
                addCircle(opt).setVisible(defaultVisible);
            }
        }

        public void showAll() {
            for (Circle circle : getCircles()) {
                circle.setVisible(true);
            }
        }

        public void hideAll() {
            for (Circle circle : getCircles()) {
                circle.setVisible(false);
            }
        }

        public boolean remove(Circle circle) {
            return super.remove(circle);
        }

        public java.util.Collection<Circle> getCircles() {
            return getObjects();
        }

        public void setOnCircleClickListener(GoogleMap.OnCircleClickListener circleClickListener) {
            this.mCircleClickListener = circleClickListener;
        }
    }
}
