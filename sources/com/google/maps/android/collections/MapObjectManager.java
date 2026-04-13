package com.google.maps.android.collections;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.collections.MapObjectManager.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class MapObjectManager<O, C extends Collection> {
    protected final Map<O, C> mAllObjects = new HashMap();
    protected final GoogleMap mMap;
    private final Map<String, C> mNamedCollections = new HashMap();

    public abstract C newCollection();

    /* access modifiers changed from: protected */
    public abstract void removeObjectFromMap(O o);

    /* access modifiers changed from: package-private */
    public abstract void setListenersOnUiThread();

    public MapObjectManager(@NonNull GoogleMap map) {
        this.mMap = map;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                MapObjectManager.this.setListenersOnUiThread();
            }
        });
    }

    public C newCollection(String id) {
        if (this.mNamedCollections.get(id) == null) {
            C collection = newCollection();
            this.mNamedCollections.put(id, collection);
            return collection;
        }
        throw new IllegalArgumentException("collection id is not unique: " + id);
    }

    public C getCollection(String id) {
        return (Collection) this.mNamedCollections.get(id);
    }

    public boolean remove(O object) {
        C collection = (Collection) this.mAllObjects.get(object);
        return collection != null && collection.remove(object);
    }

    public class Collection {
        private final Set<O> mObjects = new LinkedHashSet();

        public Collection() {
        }

        /* access modifiers changed from: protected */
        public void add(O object) {
            this.mObjects.add(object);
            MapObjectManager.this.mAllObjects.put(object, this);
        }

        /* access modifiers changed from: protected */
        public boolean remove(O object) {
            if (!this.mObjects.remove(object)) {
                return false;
            }
            MapObjectManager.this.mAllObjects.remove(object);
            MapObjectManager.this.removeObjectFromMap(object);
            return true;
        }

        public void clear() {
            for (O object : this.mObjects) {
                MapObjectManager.this.removeObjectFromMap(object);
                MapObjectManager.this.mAllObjects.remove(object);
            }
            this.mObjects.clear();
        }

        /* access modifiers changed from: protected */
        public java.util.Collection<O> getObjects() {
            return Collections.unmodifiableCollection(this.mObjects);
        }
    }
}
