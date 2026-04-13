package com.google.maps.android.data.geojson;

import android.content.Context;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.collections.GroundOverlayManager;
import com.google.maps.android.collections.MarkerManager;
import com.google.maps.android.collections.PolygonManager;
import com.google.maps.android.collections.PolylineManager;
import com.google.maps.android.data.Layer;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

public class GeoJsonLayer extends Layer {
    private LatLngBounds mBoundingBox;

    public interface GeoJsonOnFeatureClickListener extends Layer.OnFeatureClickListener {
    }

    public GeoJsonLayer(GoogleMap map, JSONObject geoJsonFile, MarkerManager markerManager, PolygonManager polygonManager, PolylineManager polylineManager, GroundOverlayManager groundOverlayManager) {
        if (geoJsonFile != null) {
            this.mBoundingBox = null;
            GeoJsonParser parser = new GeoJsonParser(geoJsonFile);
            this.mBoundingBox = parser.getBoundingBox();
            HashMap<GeoJsonFeature, Object> geoJsonFeatures = new HashMap<>();
            Iterator<GeoJsonFeature> it = parser.getFeatures().iterator();
            while (it.hasNext()) {
                geoJsonFeatures.put(it.next(), (Object) null);
            }
            storeRenderer(new GeoJsonRenderer(map, geoJsonFeatures, markerManager, polygonManager, polylineManager, groundOverlayManager));
            return;
        }
        throw new IllegalArgumentException("GeoJSON file cannot be null");
    }

    public GeoJsonLayer(GoogleMap map, int resourceId, Context context, MarkerManager markerManager, PolygonManager polygonManager, PolylineManager polylineManager, GroundOverlayManager groundOverlayManager) {
        this(map, createJsonFileObject(context.getResources().openRawResource(resourceId)), markerManager, polygonManager, polylineManager, groundOverlayManager);
    }

    public GeoJsonLayer(GoogleMap map, JSONObject geoJsonFile) {
        this(map, geoJsonFile, (MarkerManager) null, (PolygonManager) null, (PolylineManager) null, (GroundOverlayManager) null);
    }

    public GeoJsonLayer(GoogleMap map, int resourceId, Context context) {
        this(map, createJsonFileObject(context.getResources().openRawResource(resourceId)), (MarkerManager) null, (PolygonManager) null, (PolylineManager) null, (GroundOverlayManager) null);
    }

    private static JSONObject createJsonFileObject(InputStream stream) {
        StringBuilder result = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        while (true) {
            String readLine = reader.readLine();
            String line = readLine;
            if (readLine != null) {
                result.append(line);
            } else {
                reader.close();
                return new JSONObject(result.toString());
            }
        }
    }

    public void addLayerToMap() {
        super.addGeoJsonToMap();
    }

    public Iterable<GeoJsonFeature> getFeatures() {
        return super.getFeatures();
    }

    public void addFeature(GeoJsonFeature feature) {
        if (feature != null) {
            super.addFeature(feature);
            return;
        }
        throw new IllegalArgumentException("Feature cannot be null");
    }

    public void removeFeature(GeoJsonFeature feature) {
        if (feature != null) {
            super.removeFeature(feature);
            return;
        }
        throw new IllegalArgumentException("Feature cannot be null");
    }

    public LatLngBounds getBoundingBox() {
        return this.mBoundingBox;
    }

    public String toString() {
        return "Collection{" + "\n Bounding box=" + this.mBoundingBox + "\n}\n";
    }
}
