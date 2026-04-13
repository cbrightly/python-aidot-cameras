package com.google.maps.android.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.R;
import com.google.maps.android.collections.GroundOverlayManager;
import com.google.maps.android.collections.MarkerManager;
import com.google.maps.android.collections.PolygonManager;
import com.google.maps.android.collections.PolylineManager;
import com.google.maps.android.data.Layer;
import com.google.maps.android.data.geojson.BiMultiMap;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLineString;
import com.google.maps.android.data.geojson.GeoJsonLineStringStyle;
import com.google.maps.android.data.geojson.GeoJsonMultiLineString;
import com.google.maps.android.data.geojson.GeoJsonMultiPoint;
import com.google.maps.android.data.geojson.GeoJsonMultiPolygon;
import com.google.maps.android.data.geojson.GeoJsonPoint;
import com.google.maps.android.data.geojson.GeoJsonPointStyle;
import com.google.maps.android.data.geojson.GeoJsonPolygon;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlGroundOverlay;
import com.google.maps.android.data.kml.KmlMultiGeometry;
import com.google.maps.android.data.kml.KmlPlacemark;
import com.google.maps.android.data.kml.KmlPoint;
import com.google.maps.android.data.kml.KmlPolygon;
import com.google.maps.android.data.kml.KmlStyle;
import com.google.maps.android.data.kml.KmlUtil;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Renderer {
    private static final Object FEATURE_NOT_ON_MAP = null;
    private static final int MARKER_ICON_SIZE = 32;
    private static final DecimalFormat sScaleFormat = new DecimalFormat("#.####");
    private final BiMultiMap<Feature> mContainerFeatures;
    private ArrayList<KmlContainer> mContainers;
    /* access modifiers changed from: private */
    public Context mContext;
    private final GeoJsonLineStringStyle mDefaultLineStringStyle;
    private final GeoJsonPointStyle mDefaultPointStyle;
    private final GeoJsonPolygonStyle mDefaultPolygonStyle;
    private final BiMultiMap<Feature> mFeatures;
    private HashMap<KmlGroundOverlay, GroundOverlay> mGroundOverlayMap;
    private final GroundOverlayManager.Collection mGroundOverlays;
    private ImagesCache mImagesCache;
    private boolean mLayerOnMap;
    private GoogleMap mMap;
    private final Set<String> mMarkerIconUrls;
    private final MarkerManager.Collection mMarkers;
    private int mNumActiveDownloads;
    private final PolygonManager.Collection mPolygons;
    private final PolylineManager.Collection mPolylines;
    private HashMap<String, String> mStyleMaps;
    private HashMap<String, KmlStyle> mStyles;
    private HashMap<String, KmlStyle> mStylesRenderer;

    public static final class ImagesCache {
        final Map<String, Bitmap> bitmapCache = new HashMap();
        final Map<String, BitmapDescriptor> groundOverlayImagesCache = new HashMap();
        final Map<String, Map<String, BitmapDescriptor>> markerImagesCache = new HashMap();
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public Renderer(GoogleMap map, Context context, MarkerManager markerManager, PolygonManager polygonManager, PolylineManager polylineManager, GroundOverlayManager groundOverlayManager, @Nullable ImagesCache imagesCache) {
        this(map, new HashSet(), (GeoJsonPointStyle) null, (GeoJsonLineStringStyle) null, (GeoJsonPolygonStyle) null, new BiMultiMap(), markerManager, polygonManager, polylineManager, groundOverlayManager);
        this.mContext = context;
        this.mStylesRenderer = new HashMap<>();
        this.mImagesCache = imagesCache == null ? new ImagesCache() : imagesCache;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public Renderer(GoogleMap map, HashMap<? extends Feature, Object> features, MarkerManager markerManager, PolygonManager polygonManager, PolylineManager polylineManager, GroundOverlayManager groundOverlayManager) {
        this(map, (Set<String>) null, new GeoJsonPointStyle(), new GeoJsonLineStringStyle(), new GeoJsonPolygonStyle(), (BiMultiMap<Feature>) null, markerManager, polygonManager, polylineManager, groundOverlayManager);
        HashMap<? extends Feature, Object> hashMap = features;
        this.mFeatures.putAll(features);
        this.mImagesCache = null;
    }

    private Renderer(GoogleMap map, Set<String> markerIconUrls, GeoJsonPointStyle defaultPointStyle, GeoJsonLineStringStyle defaultLineStringStyle, GeoJsonPolygonStyle defaultPolygonStyle, BiMultiMap<Feature> containerFeatures, MarkerManager markerManager, PolygonManager polygonManager, PolylineManager polylineManager, GroundOverlayManager groundOverlayManager) {
        this.mFeatures = new BiMultiMap<>();
        this.mNumActiveDownloads = 0;
        this.mMap = map;
        this.mLayerOnMap = false;
        this.mMarkerIconUrls = markerIconUrls;
        this.mDefaultPointStyle = defaultPointStyle;
        this.mDefaultLineStringStyle = defaultLineStringStyle;
        this.mDefaultPolygonStyle = defaultPolygonStyle;
        this.mContainerFeatures = containerFeatures;
        if (map != null) {
            this.mMarkers = (markerManager == null ? new MarkerManager(map) : markerManager).newCollection();
            this.mPolygons = (polygonManager == null ? new PolygonManager(map) : polygonManager).newCollection();
            this.mPolylines = (polylineManager == null ? new PolylineManager(map) : polylineManager).newCollection();
            this.mGroundOverlays = (groundOverlayManager == null ? new GroundOverlayManager(map) : groundOverlayManager).newCollection();
            return;
        }
        this.mMarkers = null;
        this.mPolygons = null;
        this.mPolylines = null;
        this.mGroundOverlays = null;
    }

    public boolean isLayerOnMap() {
        return this.mLayerOnMap;
    }

    /* access modifiers changed from: protected */
    public void setLayerVisibility(boolean layerOnMap) {
        this.mLayerOnMap = layerOnMap;
    }

    public GoogleMap getMap() {
        return this.mMap;
    }

    public void setMap(GoogleMap map) {
        this.mMap = map;
    }

    /* access modifiers changed from: protected */
    public void putContainerFeature(Object mapObject, Feature placemark) {
        this.mContainerFeatures.put(placemark, mapObject);
    }

    public Set<Feature> getFeatures() {
        return this.mFeatures.keySet();
    }

    /* access modifiers changed from: package-private */
    public Feature getFeature(Object mapObject) {
        return this.mFeatures.getKey(mapObject);
    }

    /* access modifiers changed from: package-private */
    public Feature getContainerFeature(Object mapObject) {
        BiMultiMap<Feature> biMultiMap = this.mContainerFeatures;
        if (biMultiMap != null) {
            return biMultiMap.getKey(mapObject);
        }
        return null;
    }

    public Collection<Object> getValues() {
        return this.mFeatures.values();
    }

    /* access modifiers changed from: protected */
    public HashMap<? extends Feature, Object> getAllFeatures() {
        return this.mFeatures;
    }

    /* access modifiers changed from: protected */
    public Set<String> getMarkerIconUrls() {
        return this.mMarkerIconUrls;
    }

    /* access modifiers changed from: protected */
    public HashMap<String, KmlStyle> getStylesRenderer() {
        return this.mStylesRenderer;
    }

    /* access modifiers changed from: protected */
    public HashMap<String, String> getStyleMaps() {
        return this.mStyleMaps;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: com.google.android.gms.maps.model.BitmapDescriptor} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.gms.maps.model.BitmapDescriptor getCachedMarkerImage(java.lang.String r5, double r6) {
        /*
            r4 = this;
            java.text.DecimalFormat r0 = sScaleFormat
            java.lang.String r0 = r0.format(r6)
            com.google.maps.android.data.Renderer$ImagesCache r1 = r4.mImagesCache
            java.util.Map<java.lang.String, java.util.Map<java.lang.String, com.google.android.gms.maps.model.BitmapDescriptor>> r1 = r1.markerImagesCache
            java.lang.Object r1 = r1.get(r5)
            java.util.Map r1 = (java.util.Map) r1
            r2 = 0
            if (r1 == 0) goto L_0x001a
            java.lang.Object r3 = r1.get(r0)
            r2 = r3
            com.google.android.gms.maps.model.BitmapDescriptor r2 = (com.google.android.gms.maps.model.BitmapDescriptor) r2
        L_0x001a:
            if (r2 != 0) goto L_0x002f
            com.google.maps.android.data.Renderer$ImagesCache r3 = r4.mImagesCache
            java.util.Map<java.lang.String, android.graphics.Bitmap> r3 = r3.bitmapCache
            java.lang.Object r3 = r3.get(r5)
            android.graphics.Bitmap r3 = (android.graphics.Bitmap) r3
            if (r3 == 0) goto L_0x002f
            com.google.android.gms.maps.model.BitmapDescriptor r2 = r4.scaleIcon(r3, r6)
            r4.putMarkerImagesCache(r5, r0, r2)
        L_0x002f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.maps.android.data.Renderer.getCachedMarkerImage(java.lang.String, double):com.google.android.gms.maps.model.BitmapDescriptor");
    }

    private BitmapDescriptor scaleIcon(Bitmap unscaledBitmap, double scale) {
        int height;
        int width;
        int minSize = (int) (((double) (32.0f * this.mContext.getResources().getDisplayMetrics().density)) * scale);
        int unscaledWidth = unscaledBitmap.getWidth();
        int unscaledHeight = unscaledBitmap.getHeight();
        if (unscaledWidth < unscaledHeight) {
            width = minSize;
            height = (int) (((float) (minSize * unscaledHeight)) / ((float) unscaledWidth));
        } else if (unscaledWidth > unscaledHeight) {
            width = (int) (((float) (minSize * unscaledWidth)) / ((float) unscaledHeight));
            height = minSize;
        } else {
            width = minSize;
            height = minSize;
        }
        return BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(unscaledBitmap, width, height, false));
    }

    /* access modifiers changed from: protected */
    public BitmapDescriptor getCachedGroundOverlayImage(String url) {
        Bitmap bitmap;
        BitmapDescriptor bitmapDescriptor = this.mImagesCache.groundOverlayImagesCache.get(url);
        if (bitmapDescriptor != null || (bitmap = this.mImagesCache.bitmapCache.get(url)) == null) {
            return bitmapDescriptor;
        }
        BitmapDescriptor bitmapDescriptor2 = BitmapDescriptorFactory.fromBitmap(bitmap);
        this.mImagesCache.groundOverlayImagesCache.put(url, bitmapDescriptor2);
        return bitmapDescriptor2;
    }

    public HashMap<KmlGroundOverlay, GroundOverlay> getGroundOverlayMap() {
        return this.mGroundOverlayMap;
    }

    /* access modifiers changed from: protected */
    public ArrayList<KmlContainer> getContainerList() {
        return this.mContainers;
    }

    /* access modifiers changed from: protected */
    public KmlStyle getPlacemarkStyle(String styleId) {
        KmlStyle style = this.mStylesRenderer.get((Object) null);
        if (this.mStylesRenderer.get(styleId) != null) {
            return this.mStylesRenderer.get(styleId);
        }
        return style;
    }

    /* access modifiers changed from: package-private */
    public GeoJsonPointStyle getDefaultPointStyle() {
        return this.mDefaultPointStyle;
    }

    /* access modifiers changed from: package-private */
    public GeoJsonLineStringStyle getDefaultLineStringStyle() {
        return this.mDefaultLineStringStyle;
    }

    /* access modifiers changed from: package-private */
    public GeoJsonPolygonStyle getDefaultPolygonStyle() {
        return this.mDefaultPolygonStyle;
    }

    /* access modifiers changed from: protected */
    public void putFeatures(Feature feature, Object object) {
        this.mFeatures.put(feature, object);
    }

    /* access modifiers changed from: protected */
    public void putStyles() {
        this.mStylesRenderer.putAll(this.mStyles);
    }

    /* access modifiers changed from: protected */
    public void putStyles(HashMap<String, KmlStyle> styles) {
        this.mStylesRenderer.putAll(styles);
    }

    private void putMarkerImagesCache(String url, String scale, BitmapDescriptor bitmapDescriptor) {
        Map<String, BitmapDescriptor> bitmaps = this.mImagesCache.markerImagesCache.get(url);
        if (bitmaps == null) {
            bitmaps = new HashMap<>();
            this.mImagesCache.markerImagesCache.put(url, bitmaps);
        }
        bitmaps.put(scale, bitmapDescriptor);
    }

    /* access modifiers changed from: protected */
    public void cacheBitmap(String url, Bitmap bitmap) {
        this.mImagesCache.bitmapCache.put(url, bitmap);
    }

    /* access modifiers changed from: protected */
    public void downloadStarted() {
        this.mNumActiveDownloads++;
    }

    /* access modifiers changed from: protected */
    public void downloadFinished() {
        this.mNumActiveDownloads--;
        checkClearBitmapCache();
    }

    /* access modifiers changed from: protected */
    public void checkClearBitmapCache() {
        ImagesCache imagesCache;
        if (this.mNumActiveDownloads == 0 && (imagesCache = this.mImagesCache) != null && !imagesCache.bitmapCache.isEmpty()) {
            this.mImagesCache.bitmapCache.clear();
        }
    }

    /* access modifiers changed from: protected */
    public boolean hasFeatures() {
        return this.mFeatures.size() > 0;
    }

    /* access modifiers changed from: protected */
    public void removeFeatures(HashMap<? extends Feature, Object> features) {
        removeFeatures((Collection) features.values());
    }

    private void removeFeatures(Collection features) {
        for (Object mapObject : features) {
            if (mapObject instanceof Collection) {
                removeFeatures((Collection) mapObject);
            } else if (mapObject instanceof Marker) {
                this.mMarkers.remove((Marker) mapObject);
            } else if (mapObject instanceof Polyline) {
                this.mPolylines.remove((Polyline) mapObject);
            } else if (mapObject instanceof Polygon) {
                this.mPolygons.remove((Polygon) mapObject);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void removeGroundOverlays(HashMap<KmlGroundOverlay, GroundOverlay> groundOverlays) {
        for (GroundOverlay groundOverlay : groundOverlays.values()) {
            if (groundOverlay != null) {
                this.mGroundOverlays.remove(groundOverlay);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void removeFeature(Feature feature) {
        if (this.mFeatures.containsKey(feature)) {
            removeFromMap(this.mFeatures.remove(feature));
        }
    }

    private void setFeatureDefaultStyles(GeoJsonFeature feature) {
        if (feature.getPointStyle() == null) {
            feature.setPointStyle(this.mDefaultPointStyle);
        }
        if (feature.getLineStringStyle() == null) {
            feature.setLineStringStyle(this.mDefaultLineStringStyle);
        }
        if (feature.getPolygonStyle() == null) {
            feature.setPolygonStyle(this.mDefaultPolygonStyle);
        }
    }

    /* access modifiers changed from: protected */
    public void clearStylesRenderer() {
        this.mStylesRenderer.clear();
    }

    /* access modifiers changed from: protected */
    public void storeData(HashMap<String, KmlStyle> styles, HashMap<String, String> styleMaps, HashMap<KmlPlacemark, Object> features, ArrayList<KmlContainer> folders, HashMap<KmlGroundOverlay, GroundOverlay> groundOverlays) {
        this.mStyles = styles;
        this.mStyleMaps = styleMaps;
        this.mFeatures.putAll(features);
        this.mContainers = folders;
        this.mGroundOverlayMap = groundOverlays;
    }

    /* access modifiers changed from: protected */
    public void addFeature(Feature feature) {
        Object mapObject = FEATURE_NOT_ON_MAP;
        if (feature instanceof GeoJsonFeature) {
            setFeatureDefaultStyles((GeoJsonFeature) feature);
        }
        if (this.mLayerOnMap) {
            if (this.mFeatures.containsKey(feature)) {
                removeFromMap(this.mFeatures.get(feature));
            }
            if (feature.hasGeometry()) {
                if (feature instanceof KmlPlacemark) {
                    boolean isPlacemarkVisible = getPlacemarkVisibility(feature);
                    String placemarkId = feature.getId();
                    mapObject = addKmlPlacemarkToMap((KmlPlacemark) feature, feature.getGeometry(), getPlacemarkStyle(placemarkId), ((KmlPlacemark) feature).getInlineStyle(), isPlacemarkVisible);
                } else {
                    mapObject = addGeoJsonFeatureToMap(feature, feature.getGeometry());
                }
            }
        }
        this.mFeatures.put(feature, mapObject);
    }

    /* access modifiers changed from: protected */
    public void removeFromMap(Object mapObject) {
        if (mapObject instanceof Marker) {
            this.mMarkers.remove((Marker) mapObject);
        } else if (mapObject instanceof Polyline) {
            this.mPolylines.remove((Polyline) mapObject);
        } else if (mapObject instanceof Polygon) {
            this.mPolygons.remove((Polygon) mapObject);
        } else if (mapObject instanceof GroundOverlay) {
            this.mGroundOverlays.remove((GroundOverlay) mapObject);
        } else if (mapObject instanceof ArrayList) {
            Iterator it = ((ArrayList) mapObject).iterator();
            while (it.hasNext()) {
                removeFromMap(it.next());
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object addGeoJsonFeatureToMap(com.google.maps.android.data.Feature r4, com.google.maps.android.data.Geometry r5) {
        /*
            r3 = this;
            java.lang.String r0 = r5.getGeometryType()
            int r1 = r0.hashCode()
            switch(r1) {
                case -2116761119: goto L_0x0048;
                case -1065891849: goto L_0x003e;
                case -627102946: goto L_0x0034;
                case 77292912: goto L_0x002a;
                case 1267133722: goto L_0x0020;
                case 1806700869: goto L_0x0016;
                case 1950410960: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x0052
        L_0x000c:
            java.lang.String r1 = "GeometryCollection"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 6
            goto L_0x0053
        L_0x0016:
            java.lang.String r1 = "LineString"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 1
            goto L_0x0053
        L_0x0020:
            java.lang.String r1 = "Polygon"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 2
            goto L_0x0053
        L_0x002a:
            java.lang.String r1 = "Point"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 0
            goto L_0x0053
        L_0x0034:
            java.lang.String r1 = "MultiLineString"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 4
            goto L_0x0053
        L_0x003e:
            java.lang.String r1 = "MultiPoint"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 3
            goto L_0x0053
        L_0x0048:
            java.lang.String r1 = "MultiPolygon"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 5
            goto L_0x0053
        L_0x0052:
            r1 = -1
        L_0x0053:
            switch(r1) {
                case 0: goto L_0x00d4;
                case 1: goto L_0x00b4;
                case 2: goto L_0x0094;
                case 3: goto L_0x0085;
                case 4: goto L_0x0076;
                case 5: goto L_0x0067;
                case 6: goto L_0x0058;
                default: goto L_0x0056;
            }
        L_0x0056:
            r1 = 0
            return r1
        L_0x0058:
            r1 = r4
            com.google.maps.android.data.geojson.GeoJsonFeature r1 = (com.google.maps.android.data.geojson.GeoJsonFeature) r1
            r2 = r5
            com.google.maps.android.data.geojson.GeoJsonGeometryCollection r2 = (com.google.maps.android.data.geojson.GeoJsonGeometryCollection) r2
            java.util.List r2 = r2.getGeometries()
            java.util.ArrayList r1 = r3.addGeometryCollectionToMap(r1, r2)
            return r1
        L_0x0067:
            r1 = r4
            com.google.maps.android.data.geojson.GeoJsonFeature r1 = (com.google.maps.android.data.geojson.GeoJsonFeature) r1
            com.google.maps.android.data.geojson.GeoJsonPolygonStyle r1 = r1.getPolygonStyle()
            r2 = r5
            com.google.maps.android.data.geojson.GeoJsonMultiPolygon r2 = (com.google.maps.android.data.geojson.GeoJsonMultiPolygon) r2
            java.util.ArrayList r1 = r3.addMultiPolygonToMap(r1, r2)
            return r1
        L_0x0076:
            r1 = r4
            com.google.maps.android.data.geojson.GeoJsonFeature r1 = (com.google.maps.android.data.geojson.GeoJsonFeature) r1
            com.google.maps.android.data.geojson.GeoJsonLineStringStyle r1 = r1.getLineStringStyle()
            r2 = r5
            com.google.maps.android.data.geojson.GeoJsonMultiLineString r2 = (com.google.maps.android.data.geojson.GeoJsonMultiLineString) r2
            java.util.ArrayList r1 = r3.addMultiLineStringToMap(r1, r2)
            return r1
        L_0x0085:
            r1 = r4
            com.google.maps.android.data.geojson.GeoJsonFeature r1 = (com.google.maps.android.data.geojson.GeoJsonFeature) r1
            com.google.maps.android.data.geojson.GeoJsonPointStyle r1 = r1.getPointStyle()
            r2 = r5
            com.google.maps.android.data.geojson.GeoJsonMultiPoint r2 = (com.google.maps.android.data.geojson.GeoJsonMultiPoint) r2
            java.util.ArrayList r1 = r3.addMultiPointToMap(r1, r2)
            return r1
        L_0x0094:
            r1 = 0
            boolean r2 = r4 instanceof com.google.maps.android.data.geojson.GeoJsonFeature
            if (r2 == 0) goto L_0x00a1
            r2 = r4
            com.google.maps.android.data.geojson.GeoJsonFeature r2 = (com.google.maps.android.data.geojson.GeoJsonFeature) r2
            com.google.android.gms.maps.model.PolygonOptions r1 = r2.getPolygonOptions()
            goto L_0x00ac
        L_0x00a1:
            boolean r2 = r4 instanceof com.google.maps.android.data.kml.KmlPlacemark
            if (r2 == 0) goto L_0x00ac
            r2 = r4
            com.google.maps.android.data.kml.KmlPlacemark r2 = (com.google.maps.android.data.kml.KmlPlacemark) r2
            com.google.android.gms.maps.model.PolygonOptions r1 = r2.getPolygonOptions()
        L_0x00ac:
            r2 = r5
            com.google.maps.android.data.DataPolygon r2 = (com.google.maps.android.data.DataPolygon) r2
            com.google.android.gms.maps.model.Polygon r2 = r3.addPolygonToMap(r1, r2)
            return r2
        L_0x00b4:
            r1 = 0
            boolean r2 = r4 instanceof com.google.maps.android.data.geojson.GeoJsonFeature
            if (r2 == 0) goto L_0x00c1
            r2 = r4
            com.google.maps.android.data.geojson.GeoJsonFeature r2 = (com.google.maps.android.data.geojson.GeoJsonFeature) r2
            com.google.android.gms.maps.model.PolylineOptions r1 = r2.getPolylineOptions()
            goto L_0x00cc
        L_0x00c1:
            boolean r2 = r4 instanceof com.google.maps.android.data.kml.KmlPlacemark
            if (r2 == 0) goto L_0x00cc
            r2 = r4
            com.google.maps.android.data.kml.KmlPlacemark r2 = (com.google.maps.android.data.kml.KmlPlacemark) r2
            com.google.android.gms.maps.model.PolylineOptions r1 = r2.getPolylineOptions()
        L_0x00cc:
            r2 = r5
            com.google.maps.android.data.geojson.GeoJsonLineString r2 = (com.google.maps.android.data.geojson.GeoJsonLineString) r2
            com.google.android.gms.maps.model.Polyline r2 = r3.addLineStringToMap(r1, r2)
            return r2
        L_0x00d4:
            r1 = 0
            boolean r2 = r4 instanceof com.google.maps.android.data.geojson.GeoJsonFeature
            if (r2 == 0) goto L_0x00e1
            r2 = r4
            com.google.maps.android.data.geojson.GeoJsonFeature r2 = (com.google.maps.android.data.geojson.GeoJsonFeature) r2
            com.google.android.gms.maps.model.MarkerOptions r1 = r2.getMarkerOptions()
            goto L_0x00ec
        L_0x00e1:
            boolean r2 = r4 instanceof com.google.maps.android.data.kml.KmlPlacemark
            if (r2 == 0) goto L_0x00ec
            r2 = r4
            com.google.maps.android.data.kml.KmlPlacemark r2 = (com.google.maps.android.data.kml.KmlPlacemark) r2
            com.google.android.gms.maps.model.MarkerOptions r1 = r2.getMarkerOptions()
        L_0x00ec:
            r2 = r5
            com.google.maps.android.data.geojson.GeoJsonPoint r2 = (com.google.maps.android.data.geojson.GeoJsonPoint) r2
            com.google.android.gms.maps.model.Marker r2 = r3.addPointToMap(r1, r2)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.maps.android.data.Renderer.addGeoJsonFeatureToMap(com.google.maps.android.data.Feature, com.google.maps.android.data.Geometry):java.lang.Object");
    }

    /* access modifiers changed from: protected */
    public Object addKmlPlacemarkToMap(KmlPlacemark placemark, Geometry geometry, KmlStyle style, KmlStyle inlineStyle, boolean isVisible) {
        String geometryType = geometry.getGeometryType();
        boolean hasDrawOrder = placemark.hasProperty("drawOrder");
        float drawOrder = 0.0f;
        if (hasDrawOrder) {
            try {
                drawOrder = Float.parseFloat(placemark.getProperty("drawOrder"));
            } catch (NumberFormatException e) {
                hasDrawOrder = false;
            }
        }
        char c = 65535;
        switch (geometryType.hashCode()) {
            case 77292912:
                if (geometryType.equals("Point")) {
                    c = 0;
                    break;
                }
                break;
            case 89139371:
                if (geometryType.equals("MultiGeometry")) {
                    c = 3;
                    break;
                }
                break;
            case 1267133722:
                if (geometryType.equals(KmlPolygon.GEOMETRY_TYPE)) {
                    c = 2;
                    break;
                }
                break;
            case 1806700869:
                if (geometryType.equals("LineString")) {
                    c = 1;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                MarkerOptions markerOptions = style.getMarkerOptions();
                if (inlineStyle != null) {
                    setInlinePointStyle(markerOptions, inlineStyle, style);
                } else if (style.getIconUrl() != null) {
                    addMarkerIcons(style.getIconUrl(), style.getIconScale(), markerOptions);
                }
                Marker marker = addPointToMap(markerOptions, (KmlPoint) geometry);
                marker.setVisible(isVisible);
                setMarkerInfoWindow(style, marker, placemark);
                if (hasDrawOrder) {
                    marker.setZIndex(drawOrder);
                }
                return marker;
            case 1:
                PolylineOptions polylineOptions = style.getPolylineOptions();
                if (inlineStyle != null) {
                    setInlineLineStringStyle(polylineOptions, inlineStyle);
                } else if (style.isLineRandomColorMode()) {
                    polylineOptions.color(KmlStyle.computeRandomColor(polylineOptions.getColor()));
                }
                Polyline polyline = addLineStringToMap(polylineOptions, (LineString) geometry);
                polyline.setVisible(isVisible);
                if (hasDrawOrder) {
                    polyline.setZIndex(drawOrder);
                }
                return polyline;
            case 2:
                PolygonOptions polygonOptions = style.getPolygonOptions();
                if (inlineStyle != null) {
                    setInlinePolygonStyle(polygonOptions, inlineStyle);
                } else if (style.isPolyRandomColorMode()) {
                    polygonOptions.fillColor(KmlStyle.computeRandomColor(polygonOptions.getFillColor()));
                }
                Polygon polygon = addPolygonToMap(polygonOptions, (DataPolygon) geometry);
                polygon.setVisible(isVisible);
                if (hasDrawOrder) {
                    polygon.setZIndex(drawOrder);
                }
                return polygon;
            case 3:
                return addMultiGeometryToMap(placemark, (KmlMultiGeometry) geometry, style, inlineStyle, isVisible);
            default:
                return null;
        }
    }

    private Marker addPointToMap(MarkerOptions markerOptions, Point point) {
        markerOptions.position(point.getGeometryObject());
        return this.mMarkers.addMarker(markerOptions);
    }

    private void setInlinePointStyle(MarkerOptions markerOptions, KmlStyle inlineStyle, KmlStyle defaultStyle) {
        double scale;
        MarkerOptions inlineMarkerOptions = inlineStyle.getMarkerOptions();
        if (inlineStyle.isStyleSet("heading")) {
            markerOptions.rotation(inlineMarkerOptions.getRotation());
        }
        if (inlineStyle.isStyleSet("hotSpot")) {
            markerOptions.anchor(inlineMarkerOptions.getAnchorU(), inlineMarkerOptions.getAnchorV());
        }
        if (inlineStyle.isStyleSet("markerColor")) {
            markerOptions.icon(inlineMarkerOptions.getIcon());
        }
        if (inlineStyle.isStyleSet("iconScale")) {
            scale = inlineStyle.getIconScale();
        } else if (defaultStyle.isStyleSet("iconScale")) {
            scale = defaultStyle.getIconScale();
        } else {
            scale = 1.0d;
        }
        if (inlineStyle.isStyleSet("iconUrl")) {
            addMarkerIcons(inlineStyle.getIconUrl(), scale, markerOptions);
        } else if (defaultStyle.getIconUrl() != null) {
            addMarkerIcons(defaultStyle.getIconUrl(), scale, markerOptions);
        }
    }

    private Polyline addLineStringToMap(PolylineOptions polylineOptions, LineString lineString) {
        polylineOptions.addAll(lineString.getGeometryObject());
        Polyline addedPolyline = this.mPolylines.addPolyline(polylineOptions);
        addedPolyline.setClickable(polylineOptions.isClickable());
        return addedPolyline;
    }

    private void setInlineLineStringStyle(PolylineOptions polylineOptions, KmlStyle inlineStyle) {
        PolylineOptions inlinePolylineOptions = inlineStyle.getPolylineOptions();
        if (inlineStyle.isStyleSet("outlineColor")) {
            polylineOptions.color(inlinePolylineOptions.getColor());
        }
        if (inlineStyle.isStyleSet("width")) {
            polylineOptions.width(inlinePolylineOptions.getWidth());
        }
        if (inlineStyle.isLineRandomColorMode()) {
            polylineOptions.color(KmlStyle.computeRandomColor(inlinePolylineOptions.getColor()));
        }
    }

    private Polygon addPolygonToMap(PolygonOptions polygonOptions, DataPolygon polygon) {
        polygonOptions.addAll(polygon.getOuterBoundaryCoordinates());
        for (List<LatLng> innerBoundary : polygon.getInnerBoundaryCoordinates()) {
            polygonOptions.addHole(innerBoundary);
        }
        Polygon addedPolygon = this.mPolygons.addPolygon(polygonOptions);
        addedPolygon.setClickable(polygonOptions.isClickable());
        return addedPolygon;
    }

    private void setInlinePolygonStyle(PolygonOptions polygonOptions, KmlStyle inlineStyle) {
        PolygonOptions inlinePolygonOptions = inlineStyle.getPolygonOptions();
        if (inlineStyle.hasFill() && inlineStyle.isStyleSet("fillColor")) {
            polygonOptions.fillColor(inlinePolygonOptions.getFillColor());
        }
        if (inlineStyle.hasOutline()) {
            if (inlineStyle.isStyleSet("outlineColor")) {
                polygonOptions.strokeColor(inlinePolygonOptions.getStrokeColor());
            }
            if (inlineStyle.isStyleSet("width")) {
                polygonOptions.strokeWidth(inlinePolygonOptions.getStrokeWidth());
            }
        }
        if (inlineStyle.isPolyRandomColorMode()) {
            polygonOptions.fillColor(KmlStyle.computeRandomColor(inlinePolygonOptions.getFillColor()));
        }
    }

    private ArrayList<Object> addGeometryCollectionToMap(GeoJsonFeature feature, List<Geometry> geoJsonGeometries) {
        ArrayList<Object> geometries = new ArrayList<>();
        for (Geometry geometry : geoJsonGeometries) {
            geometries.add(addGeoJsonFeatureToMap(feature, geometry));
        }
        return geometries;
    }

    protected static boolean getPlacemarkVisibility(Feature feature) {
        if (!feature.hasProperty("visibility") || Integer.parseInt(feature.getProperty("visibility")) != 0) {
            return true;
        }
        return false;
    }

    public void assignStyleMap(HashMap<String, String> styleMap, HashMap<String, KmlStyle> styles) {
        for (String styleMapKey : styleMap.keySet()) {
            String styleMapValue = styleMap.get(styleMapKey);
            if (styles.containsKey(styleMapValue)) {
                styles.put(styleMapKey, styles.get(styleMapValue));
            }
        }
    }

    private ArrayList<Object> addMultiGeometryToMap(KmlPlacemark placemark, KmlMultiGeometry multiGeometry, KmlStyle urlStyle, KmlStyle inlineStyle, boolean isContainerVisible) {
        ArrayList<Object> mapObjects = new ArrayList<>();
        Iterator<Geometry> it = multiGeometry.getGeometryObject().iterator();
        while (it.hasNext()) {
            mapObjects.add(addKmlPlacemarkToMap(placemark, it.next(), urlStyle, inlineStyle, isContainerVisible));
        }
        return mapObjects;
    }

    private ArrayList<Marker> addMultiPointToMap(GeoJsonPointStyle pointStyle, GeoJsonMultiPoint multiPoint) {
        ArrayList<Marker> markers = new ArrayList<>();
        for (GeoJsonPoint geoJsonPoint : multiPoint.getPoints()) {
            markers.add(addPointToMap(pointStyle.toMarkerOptions(), geoJsonPoint));
        }
        return markers;
    }

    private ArrayList<Polyline> addMultiLineStringToMap(GeoJsonLineStringStyle lineStringStyle, GeoJsonMultiLineString multiLineString) {
        ArrayList<Polyline> polylines = new ArrayList<>();
        for (GeoJsonLineString geoJsonLineString : multiLineString.getLineStrings()) {
            polylines.add(addLineStringToMap(lineStringStyle.toPolylineOptions(), geoJsonLineString));
        }
        return polylines;
    }

    private ArrayList<Polygon> addMultiPolygonToMap(GeoJsonPolygonStyle polygonStyle, GeoJsonMultiPolygon multiPolygon) {
        ArrayList<Polygon> polygons = new ArrayList<>();
        for (GeoJsonPolygon geoJsonPolygon : multiPolygon.getPolygons()) {
            polygons.add(addPolygonToMap(polygonStyle.toPolygonOptions(), geoJsonPolygon));
        }
        return polygons;
    }

    private void addMarkerIcons(String styleUrl, double scale, MarkerOptions markerOptions) {
        BitmapDescriptor bitmap = getCachedMarkerImage(styleUrl, scale);
        if (bitmap != null) {
            markerOptions.icon(bitmap);
        } else {
            this.mMarkerIconUrls.add(styleUrl);
        }
    }

    /* access modifiers changed from: protected */
    public GroundOverlay attachGroundOverlay(GroundOverlayOptions groundOverlayOptions) {
        return this.mGroundOverlays.addGroundOverlay(groundOverlayOptions);
    }

    private void setMarkerInfoWindow(KmlStyle style, Marker marker, KmlPlacemark placemark) {
        boolean hasName = placemark.hasProperty("name");
        boolean hasDescription = placemark.hasProperty("description");
        boolean hasBalloonOptions = style.hasBalloonStyle();
        boolean hasBalloonText = style.getBalloonOptions().containsKey("text");
        if (hasBalloonOptions && hasBalloonText) {
            marker.setTitle(KmlUtil.substituteProperties(style.getBalloonOptions().get("text"), placemark));
            createInfoWindow();
        } else if (hasBalloonOptions && hasName) {
            marker.setTitle(placemark.getProperty("name"));
            createInfoWindow();
        } else if (hasName && hasDescription) {
            marker.setTitle(placemark.getProperty("name"));
            marker.setSnippet(placemark.getProperty("description"));
            createInfoWindow();
        } else if (hasDescription) {
            marker.setTitle(placemark.getProperty("description"));
            createInfoWindow();
        } else if (hasName) {
            marker.setTitle(placemark.getProperty("name"));
            createInfoWindow();
        }
    }

    private void createInfoWindow() {
        this.mMarkers.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            public View getInfoContents(Marker arg0) {
                View view = LayoutInflater.from(Renderer.this.mContext).inflate(R.layout.amu_info_window, (ViewGroup) null);
                TextView infoWindowText = (TextView) view.findViewById(R.id.window);
                if (arg0.getSnippet() != null) {
                    infoWindowText.setText(Html.fromHtml(arg0.getTitle() + "<br>" + arg0.getSnippet()));
                } else {
                    infoWindowText.setText(Html.fromHtml(arg0.getTitle()));
                }
                return view;
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void setOnFeatureClickListener(final Layer.OnFeatureClickListener listener) {
        this.mPolygons.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            public void onPolygonClick(Polygon polygon) {
                if (Renderer.this.getFeature(polygon) != null) {
                    listener.onFeatureClick(Renderer.this.getFeature(polygon));
                } else if (Renderer.this.getContainerFeature(polygon) != null) {
                    listener.onFeatureClick(Renderer.this.getContainerFeature(polygon));
                } else {
                    Layer.OnFeatureClickListener onFeatureClickListener = listener;
                    Renderer renderer = Renderer.this;
                    onFeatureClickListener.onFeatureClick(renderer.getFeature(renderer.multiObjectHandler(polygon)));
                }
            }
        });
        this.mMarkers.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                if (Renderer.this.getFeature(marker) != null) {
                    listener.onFeatureClick(Renderer.this.getFeature(marker));
                    return false;
                } else if (Renderer.this.getContainerFeature(marker) != null) {
                    listener.onFeatureClick(Renderer.this.getContainerFeature(marker));
                    return false;
                } else {
                    Layer.OnFeatureClickListener onFeatureClickListener = listener;
                    Renderer renderer = Renderer.this;
                    onFeatureClickListener.onFeatureClick(renderer.getFeature(renderer.multiObjectHandler(marker)));
                    return false;
                }
            }
        });
        this.mPolylines.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            public void onPolylineClick(Polyline polyline) {
                if (Renderer.this.getFeature(polyline) != null) {
                    listener.onFeatureClick(Renderer.this.getFeature(polyline));
                } else if (Renderer.this.getContainerFeature(polyline) != null) {
                    listener.onFeatureClick(Renderer.this.getContainerFeature(polyline));
                } else {
                    Layer.OnFeatureClickListener onFeatureClickListener = listener;
                    Renderer renderer = Renderer.this;
                    onFeatureClickListener.onFeatureClick(renderer.getFeature(renderer.multiObjectHandler(polyline)));
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public ArrayList<?> multiObjectHandler(Object mapObject) {
        for (Object value : getValues()) {
            if (value.getClass().getSimpleName().equals("ArrayList")) {
                ArrayList<?> mapObjects = (ArrayList) value;
                if (mapObjects.contains(mapObject)) {
                    return mapObjects;
                }
            }
        }
        return null;
    }
}
