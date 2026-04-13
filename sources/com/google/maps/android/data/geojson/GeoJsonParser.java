package com.google.maps.android.data.geojson;

import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.data.Geometry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class GeoJsonParser {
    private static final String BOUNDING_BOX = "bbox";
    private static final String FEATURE = "Feature";
    private static final String FEATURE_COLLECTION = "FeatureCollection";
    private static final String FEATURE_COLLECTION_ARRAY = "features";
    private static final String FEATURE_GEOMETRY = "geometry";
    private static final String FEATURE_ID = "id";
    private static final String GEOMETRY_COLLECTION = "GeometryCollection";
    private static final String GEOMETRY_COLLECTION_ARRAY = "geometries";
    private static final String GEOMETRY_COORDINATES_ARRAY = "coordinates";
    private static final String LINESTRING = "LineString";
    private static final String LOG_TAG = "GeoJsonParser";
    private static final String MULTILINESTRING = "MultiLineString";
    private static final String MULTIPOINT = "MultiPoint";
    private static final String MULTIPOLYGON = "MultiPolygon";
    private static final String POINT = "Point";
    private static final String POLYGON = "Polygon";
    private static final String PROPERTIES = "properties";
    private LatLngBounds mBoundingBox = null;
    private final ArrayList<GeoJsonFeature> mGeoJsonFeatures = new ArrayList<>();
    private final JSONObject mGeoJsonFile;

    public static class LatLngAlt {
        public final Double altitude;
        public final LatLng latLng;

        LatLngAlt(LatLng latLng2, Double altitude2) {
            this.latLng = latLng2;
            this.altitude = altitude2;
        }
    }

    public GeoJsonParser(JSONObject geoJsonFile) {
        this.mGeoJsonFile = geoJsonFile;
        parseGeoJson();
    }

    private static boolean isGeometry(String type) {
        return type.matches("Point|MultiPoint|LineString|MultiLineString|Polygon|MultiPolygon|GeometryCollection");
    }

    private static GeoJsonFeature parseFeature(JSONObject geoJsonFeature) {
        String id = null;
        LatLngBounds boundingBox = null;
        Geometry geometry = null;
        HashMap<String, String> properties = new HashMap<>();
        try {
            if (geoJsonFeature.has(FEATURE_ID)) {
                id = geoJsonFeature.getString(FEATURE_ID);
            }
            if (geoJsonFeature.has(BOUNDING_BOX)) {
                boundingBox = parseBoundingBox(geoJsonFeature.getJSONArray(BOUNDING_BOX));
            }
            if (geoJsonFeature.has(FEATURE_GEOMETRY) && !geoJsonFeature.isNull(FEATURE_GEOMETRY)) {
                geometry = parseGeometry(geoJsonFeature.getJSONObject(FEATURE_GEOMETRY));
            }
            if (geoJsonFeature.has(PROPERTIES) && !geoJsonFeature.isNull(PROPERTIES)) {
                properties = parseProperties(geoJsonFeature.getJSONObject(PROPERTIES));
            }
            return new GeoJsonFeature(geometry, id, properties, boundingBox);
        } catch (JSONException e) {
            Log.w(LOG_TAG, "Feature could not be successfully parsed " + geoJsonFeature.toString());
            return null;
        }
    }

    private static LatLngBounds parseBoundingBox(JSONArray coordinates) {
        return new LatLngBounds(new LatLng(coordinates.getDouble(1), coordinates.getDouble(0)), new LatLng(coordinates.getDouble(3), coordinates.getDouble(2)));
    }

    public static Geometry parseGeometry(JSONObject geoJsonGeometry) {
        JSONArray geometryArray;
        try {
            String geometryType = geoJsonGeometry.getString(IjkMediaMeta.IJKM_KEY_TYPE);
            if (geometryType.equals(GEOMETRY_COLLECTION)) {
                geometryArray = geoJsonGeometry.getJSONArray(GEOMETRY_COLLECTION_ARRAY);
            } else if (!isGeometry(geometryType)) {
                return null;
            } else {
                geometryArray = geoJsonGeometry.getJSONArray(GEOMETRY_COORDINATES_ARRAY);
            }
            return createGeometry(geometryType, geometryArray);
        } catch (JSONException e) {
            return null;
        }
    }

    private static GeoJsonFeature parseGeometryToFeature(JSONObject geoJsonGeometry) {
        Geometry geometry = parseGeometry(geoJsonGeometry);
        if (geometry != null) {
            return new GeoJsonFeature(geometry, (String) null, new HashMap(), (LatLngBounds) null);
        }
        Log.w(LOG_TAG, "Geometry could not be parsed");
        return null;
    }

    private static HashMap<String, String> parseProperties(JSONObject properties) {
        HashMap<String, String> propertiesMap = new HashMap<>();
        Iterator propertyKeys = properties.keys();
        while (propertyKeys.hasNext()) {
            String key = propertyKeys.next();
            propertiesMap.put(key, properties.isNull(key) ? null : properties.getString(key));
        }
        return propertiesMap;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.google.maps.android.data.Geometry createGeometry(java.lang.String r1, org.json.JSONArray r2) {
        /*
            int r0 = r1.hashCode()
            switch(r0) {
                case -2116761119: goto L_0x0044;
                case -1065891849: goto L_0x003a;
                case -627102946: goto L_0x0030;
                case 77292912: goto L_0x0026;
                case 1267133722: goto L_0x001c;
                case 1806700869: goto L_0x0012;
                case 1950410960: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x004e
        L_0x0008:
            java.lang.String r0 = "GeometryCollection"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 6
            goto L_0x004f
        L_0x0012:
            java.lang.String r0 = "LineString"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 2
            goto L_0x004f
        L_0x001c:
            java.lang.String r0 = "Polygon"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 4
            goto L_0x004f
        L_0x0026:
            java.lang.String r0 = "Point"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x004f
        L_0x0030:
            java.lang.String r0 = "MultiLineString"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 3
            goto L_0x004f
        L_0x003a:
            java.lang.String r0 = "MultiPoint"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x004f
        L_0x0044:
            java.lang.String r0 = "MultiPolygon"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 5
            goto L_0x004f
        L_0x004e:
            r0 = -1
        L_0x004f:
            switch(r0) {
                case 0: goto L_0x0072;
                case 1: goto L_0x006d;
                case 2: goto L_0x0068;
                case 3: goto L_0x0063;
                case 4: goto L_0x005e;
                case 5: goto L_0x0059;
                case 6: goto L_0x0054;
                default: goto L_0x0052;
            }
        L_0x0052:
            r0 = 0
            return r0
        L_0x0054:
            com.google.maps.android.data.geojson.GeoJsonGeometryCollection r0 = createGeometryCollection(r2)
            return r0
        L_0x0059:
            com.google.maps.android.data.geojson.GeoJsonMultiPolygon r0 = createMultiPolygon(r2)
            return r0
        L_0x005e:
            com.google.maps.android.data.geojson.GeoJsonPolygon r0 = createPolygon(r2)
            return r0
        L_0x0063:
            com.google.maps.android.data.geojson.GeoJsonMultiLineString r0 = createMultiLineString(r2)
            return r0
        L_0x0068:
            com.google.maps.android.data.geojson.GeoJsonLineString r0 = createLineString(r2)
            return r0
        L_0x006d:
            com.google.maps.android.data.geojson.GeoJsonMultiPoint r0 = createMultiPoint(r2)
            return r0
        L_0x0072:
            com.google.maps.android.data.geojson.GeoJsonPoint r0 = createPoint(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.maps.android.data.geojson.GeoJsonParser.createGeometry(java.lang.String, org.json.JSONArray):com.google.maps.android.data.Geometry");
    }

    private static GeoJsonPoint createPoint(JSONArray coordinates) {
        LatLngAlt latLngAlt = parseCoordinate(coordinates);
        return new GeoJsonPoint(latLngAlt.latLng, latLngAlt.altitude);
    }

    private static GeoJsonMultiPoint createMultiPoint(JSONArray coordinates) {
        ArrayList<GeoJsonPoint> geoJsonPoints = new ArrayList<>();
        for (int i = 0; i < coordinates.length(); i++) {
            geoJsonPoints.add(createPoint(coordinates.getJSONArray(i)));
        }
        return new GeoJsonMultiPoint(geoJsonPoints);
    }

    private static GeoJsonLineString createLineString(JSONArray coordinates) {
        ArrayList<LatLngAlt> latLngAlts = parseCoordinatesArray(coordinates);
        ArrayList<LatLng> latLngs = new ArrayList<>();
        ArrayList<Double> altitudes = new ArrayList<>();
        Iterator<LatLngAlt> it = latLngAlts.iterator();
        while (it.hasNext()) {
            LatLngAlt latLngAlt = it.next();
            latLngs.add(latLngAlt.latLng);
            Double d = latLngAlt.altitude;
            if (d != null) {
                altitudes.add(d);
            }
        }
        return new GeoJsonLineString(latLngs, altitudes);
    }

    private static GeoJsonMultiLineString createMultiLineString(JSONArray coordinates) {
        ArrayList<GeoJsonLineString> geoJsonLineStrings = new ArrayList<>();
        for (int i = 0; i < coordinates.length(); i++) {
            geoJsonLineStrings.add(createLineString(coordinates.getJSONArray(i)));
        }
        return new GeoJsonMultiLineString(geoJsonLineStrings);
    }

    private static GeoJsonPolygon createPolygon(JSONArray coordinates) {
        return new GeoJsonPolygon(parseCoordinatesArrays(coordinates));
    }

    private static GeoJsonMultiPolygon createMultiPolygon(JSONArray coordinates) {
        ArrayList<GeoJsonPolygon> geoJsonPolygons = new ArrayList<>();
        for (int i = 0; i < coordinates.length(); i++) {
            geoJsonPolygons.add(createPolygon(coordinates.getJSONArray(i)));
        }
        return new GeoJsonMultiPolygon(geoJsonPolygons);
    }

    private static GeoJsonGeometryCollection createGeometryCollection(JSONArray geometries) {
        ArrayList<Geometry> geometryCollectionElements = new ArrayList<>();
        for (int i = 0; i < geometries.length(); i++) {
            Geometry geometry = parseGeometry(geometries.getJSONObject(i));
            if (geometry != null) {
                geometryCollectionElements.add(geometry);
            }
        }
        return new GeoJsonGeometryCollection(geometryCollectionElements);
    }

    private static LatLngAlt parseCoordinate(JSONArray coordinates) {
        return new LatLngAlt(new LatLng(coordinates.getDouble(1), coordinates.getDouble(0)), coordinates.length() < 3 ? null : Double.valueOf(coordinates.getDouble(2)));
    }

    private static ArrayList<LatLngAlt> parseCoordinatesArray(JSONArray coordinates) {
        ArrayList<LatLngAlt> coordinatesArray = new ArrayList<>();
        for (int i = 0; i < coordinates.length(); i++) {
            coordinatesArray.add(parseCoordinate(coordinates.getJSONArray(i)));
        }
        return coordinatesArray;
    }

    private static ArrayList<ArrayList<LatLng>> parseCoordinatesArrays(JSONArray coordinates) {
        ArrayList<ArrayList<LatLng>> coordinatesArray = new ArrayList<>();
        for (int i = 0; i < coordinates.length(); i++) {
            ArrayList<LatLngAlt> latLngAlts = parseCoordinatesArray(coordinates.getJSONArray(i));
            ArrayList<LatLng> latLngs = new ArrayList<>();
            Iterator<LatLngAlt> it = latLngAlts.iterator();
            while (it.hasNext()) {
                latLngs.add(it.next().latLng);
            }
            coordinatesArray.add(latLngs);
        }
        return coordinatesArray;
    }

    private void parseGeoJson() {
        try {
            String type = this.mGeoJsonFile.getString(IjkMediaMeta.IJKM_KEY_TYPE);
            if (type.equals(FEATURE)) {
                GeoJsonFeature feature = parseFeature(this.mGeoJsonFile);
                if (feature != null) {
                    this.mGeoJsonFeatures.add(feature);
                }
            } else if (type.equals(FEATURE_COLLECTION)) {
                this.mGeoJsonFeatures.addAll(parseFeatureCollection(this.mGeoJsonFile));
            } else if (isGeometry(type)) {
                GeoJsonFeature feature2 = parseGeometryToFeature(this.mGeoJsonFile);
                if (feature2 != null) {
                    this.mGeoJsonFeatures.add(feature2);
                }
            } else {
                Log.w(LOG_TAG, "GeoJSON file could not be parsed.");
            }
        } catch (JSONException e) {
            Log.w(LOG_TAG, "GeoJSON file could not be parsed.");
        }
    }

    private ArrayList<GeoJsonFeature> parseFeatureCollection(JSONObject geoJsonFeatureCollection) {
        ArrayList<GeoJsonFeature> features = new ArrayList<>();
        try {
            JSONArray geoJsonFeatures = geoJsonFeatureCollection.getJSONArray(FEATURE_COLLECTION_ARRAY);
            if (geoJsonFeatureCollection.has(BOUNDING_BOX)) {
                this.mBoundingBox = parseBoundingBox(geoJsonFeatureCollection.getJSONArray(BOUNDING_BOX));
            }
            for (int i = 0; i < geoJsonFeatures.length(); i++) {
                try {
                    JSONObject feature = geoJsonFeatures.getJSONObject(i);
                    if (feature.getString(IjkMediaMeta.IJKM_KEY_TYPE).equals(FEATURE)) {
                        GeoJsonFeature parsedFeature = parseFeature(feature);
                        if (parsedFeature != null) {
                            features.add(parsedFeature);
                        } else {
                            Log.w(LOG_TAG, "Index of Feature in Feature Collection that could not be created: " + i);
                        }
                    }
                } catch (JSONException e) {
                    Log.w(LOG_TAG, "Index of Feature in Feature Collection that could not be created: " + i);
                }
            }
            return features;
        } catch (JSONException e2) {
            Log.w(LOG_TAG, "Feature Collection could not be created.");
            return features;
        }
    }

    public ArrayList<GeoJsonFeature> getFeatures() {
        return this.mGeoJsonFeatures;
    }

    public LatLngBounds getBoundingBox() {
        return this.mBoundingBox;
    }
}
