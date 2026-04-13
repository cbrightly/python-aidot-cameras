package com.google.maps.android.data.kml;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.collections.GroundOverlayManager;
import com.google.maps.android.collections.MarkerManager;
import com.google.maps.android.collections.PolygonManager;
import com.google.maps.android.collections.PolylineManager;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.Geometry;
import com.google.maps.android.data.MultiGeometry;
import com.google.maps.android.data.Renderer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class KmlRenderer extends Renderer {
    private static final String LOG_TAG = "KmlRenderer";
    /* access modifiers changed from: private */
    public ArrayList<KmlContainer> mContainers;
    private boolean mGroundOverlayImagesDownloaded = false;
    private final Set<String> mGroundOverlayUrls = new HashSet();
    private boolean mMarkerIconsDownloaded = false;

    KmlRenderer(GoogleMap map, Context context, MarkerManager markerManager, PolygonManager polygonManager, PolylineManager polylineManager, GroundOverlayManager groundOverlayManager, @Nullable Renderer.ImagesCache imagesCache) {
        super(map, context, markerManager, polygonManager, polylineManager, groundOverlayManager, imagesCache);
    }

    private void removePlacemarks(HashMap<? extends Feature, Object> placemarks) {
        removeFeatures(placemarks);
    }

    static boolean getContainerVisibility(KmlContainer kmlContainer, boolean isParentContainerVisible) {
        boolean isChildContainerVisible = true;
        if (kmlContainer.hasProperty("visibility") && Integer.parseInt(kmlContainer.getProperty("visibility")) == 0) {
            isChildContainerVisible = false;
        }
        return isParentContainerVisible && isChildContainerVisible;
    }

    private void removeContainers(Iterable<KmlContainer> containers) {
        for (KmlContainer container : containers) {
            removePlacemarks(container.getPlacemarksHashMap());
            removeGroundOverlays(container.getGroundOverlayHashMap());
            removeContainers(container.getContainers());
        }
    }

    public void addLayerToMap() {
        setLayerVisibility(true);
        this.mContainers = getContainerList();
        putStyles();
        assignStyleMap(getStyleMaps(), getStylesRenderer());
        addGroundOverlays(getGroundOverlayMap(), this.mContainers);
        addContainerGroupToMap(this.mContainers, true);
        addPlacemarksToMap(getAllFeatures());
        if (!this.mGroundOverlayImagesDownloaded) {
            downloadGroundOverlays();
        }
        if (!this.mMarkerIconsDownloaded) {
            downloadMarkerIcons();
        }
        checkClearBitmapCache();
    }

    /* access modifiers changed from: package-private */
    public void storeKmlData(HashMap<String, KmlStyle> styles, HashMap<String, String> styleMaps, HashMap<KmlPlacemark, Object> features, ArrayList<KmlContainer> folders, HashMap<KmlGroundOverlay, GroundOverlay> groundOverlays) {
        storeData(styles, styleMaps, features, folders, groundOverlays);
    }

    /* access modifiers changed from: package-private */
    public void storeKmzData(HashMap<String, KmlStyle> styles, HashMap<String, String> styleMaps, HashMap<KmlPlacemark, Object> features, ArrayList<KmlContainer> folders, HashMap<KmlGroundOverlay, GroundOverlay> groundOverlays, HashMap<String, Bitmap> images) {
        storeData(styles, styleMaps, features, folders, groundOverlays);
        for (Map.Entry<String, Bitmap> entry : images.entrySet()) {
            cacheBitmap(entry.getKey(), entry.getValue());
        }
    }

    public void setMap(GoogleMap map) {
        removeLayerFromMap();
        super.setMap(map);
        addLayerToMap();
    }

    /* access modifiers changed from: package-private */
    public boolean hasKmlPlacemarks() {
        return hasFeatures();
    }

    /* access modifiers changed from: package-private */
    public Iterable<? extends Feature> getKmlPlacemarks() {
        return getFeatures();
    }

    public boolean hasNestedContainers() {
        return this.mContainers.size() > 0;
    }

    public Iterable<KmlContainer> getNestedContainers() {
        return this.mContainers;
    }

    public Iterable<KmlGroundOverlay> getGroundOverlays() {
        return getGroundOverlayMap().keySet();
    }

    public void removeLayerFromMap() {
        removePlacemarks(getAllFeatures());
        removeGroundOverlays(getGroundOverlayMap());
        if (hasNestedContainers()) {
            removeContainers(getNestedContainers());
        }
        setLayerVisibility(false);
        clearStylesRenderer();
    }

    private void addPlacemarksToMap(HashMap<? extends Feature, Object> placemarks) {
        for (Feature kmlPlacemark : placemarks.keySet()) {
            addFeature(kmlPlacemark);
        }
    }

    private void addContainerGroupToMap(Iterable<KmlContainer> kmlContainers, boolean containerVisibility) {
        for (KmlContainer container : kmlContainers) {
            boolean isContainerVisible = getContainerVisibility(container, containerVisibility);
            if (container.getStyles() != null) {
                putStyles(container.getStyles());
            }
            if (container.getStyleMap() != null) {
                super.assignStyleMap(container.getStyleMap(), getStylesRenderer());
            }
            addContainerObjectToMap(container, isContainerVisible);
            if (container.hasContainers()) {
                addContainerGroupToMap(container.getContainers(), isContainerVisible);
            }
        }
    }

    private void addContainerObjectToMap(KmlContainer kmlContainer, boolean isContainerVisible) {
        for (Feature placemark : kmlContainer.getPlacemarks()) {
            boolean isObjectVisible = isContainerVisible && Renderer.getPlacemarkVisibility(placemark);
            if (placemark.getGeometry() != null) {
                Object mapObject = addKmlPlacemarkToMap((KmlPlacemark) placemark, placemark.getGeometry(), getPlacemarkStyle(placemark.getId()), ((KmlPlacemark) placemark).getInlineStyle(), isObjectVisible);
                kmlContainer.setPlacemark((KmlPlacemark) placemark, mapObject);
                putContainerFeature(mapObject, placemark);
            }
        }
    }

    private void downloadMarkerIcons() {
        this.mMarkerIconsDownloaded = true;
        Iterator<String> iterator = getMarkerIconUrls().iterator();
        while (iterator.hasNext()) {
            new MarkerIconImageDownload(iterator.next()).execute(new String[0]);
            iterator.remove();
        }
    }

    /* access modifiers changed from: private */
    public void addIconToMarkers(String iconUrl, HashMap<KmlPlacemark, Object> placemarks) {
        for (Feature placemark : placemarks.keySet()) {
            addIconToGeometry(iconUrl, getStylesRenderer().get(placemark.getId()), ((KmlPlacemark) placemark).getInlineStyle(), placemark.getGeometry(), placemarks.get(placemark));
        }
    }

    private void addIconToGeometry(String iconUrl, KmlStyle urlStyle, KmlStyle inlineStyle, Geometry geometry, Object object) {
        if (geometry != null) {
            if ("Point".equals(geometry.getGeometryType())) {
                addIconToMarker(iconUrl, urlStyle, inlineStyle, (Marker) object);
            } else if ("MultiGeometry".equals(geometry.getGeometryType())) {
                addIconToMultiGeometry(iconUrl, urlStyle, inlineStyle, (MultiGeometry) geometry, (List) object);
            }
        }
    }

    private void addIconToMultiGeometry(String iconUrl, KmlStyle urlStyle, KmlStyle inlineStyle, MultiGeometry multiGeometry, List<Object> objects) {
        Iterator<Geometry> geometries = multiGeometry.getGeometryObject().iterator();
        Iterator<Object> objItr = objects.iterator();
        while (geometries.hasNext() && objItr.hasNext()) {
            addIconToGeometry(iconUrl, urlStyle, inlineStyle, geometries.next(), objItr.next());
        }
    }

    private void addIconToMarker(String iconUrl, KmlStyle urlStyle, KmlStyle inlineStyle, Marker marker) {
        boolean isPlacemarkStyleIcon = true;
        boolean isInlineStyleIcon = inlineStyle != null && iconUrl.equals(inlineStyle.getIconUrl());
        if (urlStyle == null || !iconUrl.equals(urlStyle.getIconUrl())) {
            isPlacemarkStyleIcon = false;
        }
        if (isInlineStyleIcon) {
            scaleBitmap(inlineStyle, marker);
        } else if (isPlacemarkStyleIcon) {
            scaleBitmap(urlStyle, marker);
        }
    }

    private void scaleBitmap(KmlStyle style, Marker marker) {
        marker.setIcon(getCachedMarkerImage(style.getIconUrl(), style.getIconScale()));
    }

    /* access modifiers changed from: private */
    public void addContainerGroupIconsToMarkers(String iconUrl, Iterable<KmlContainer> kmlContainers) {
        for (KmlContainer container : kmlContainers) {
            addIconToMarkers(iconUrl, container.getPlacemarksHashMap());
            if (container.hasContainers()) {
                addContainerGroupIconsToMarkers(iconUrl, container.getContainers());
            }
        }
    }

    private void addGroundOverlays(HashMap<KmlGroundOverlay, GroundOverlay> groundOverlays, Iterable<KmlContainer> kmlContainers) {
        addGroundOverlays(groundOverlays);
        for (KmlContainer container : kmlContainers) {
            addGroundOverlays(container.getGroundOverlayHashMap(), container.getContainers());
        }
    }

    private void addGroundOverlays(HashMap<KmlGroundOverlay, GroundOverlay> groundOverlays) {
        for (KmlGroundOverlay groundOverlay : groundOverlays.keySet()) {
            String groundOverlayUrl = groundOverlay.getImageUrl();
            if (!(groundOverlayUrl == null || groundOverlay.getLatLngBox() == null)) {
                if (getCachedGroundOverlayImage(groundOverlayUrl) != null) {
                    addGroundOverlayToMap(groundOverlayUrl, getGroundOverlayMap(), true);
                } else {
                    this.mGroundOverlayUrls.add(groundOverlayUrl);
                }
            }
        }
    }

    private void downloadGroundOverlays() {
        this.mGroundOverlayImagesDownloaded = true;
        Iterator<String> iterator = this.mGroundOverlayUrls.iterator();
        while (iterator.hasNext()) {
            new GroundOverlayImageDownload(iterator.next()).execute(new String[0]);
            iterator.remove();
        }
    }

    /* access modifiers changed from: private */
    public void addGroundOverlayToMap(String groundOverlayUrl, HashMap<KmlGroundOverlay, GroundOverlay> groundOverlays, boolean containerVisibility) {
        BitmapDescriptor groundOverlayBitmap = getCachedGroundOverlayImage(groundOverlayUrl);
        for (KmlGroundOverlay kmlGroundOverlay : groundOverlays.keySet()) {
            if (kmlGroundOverlay.getImageUrl().equals(groundOverlayUrl)) {
                GroundOverlay mapGroundOverlay = attachGroundOverlay(kmlGroundOverlay.getGroundOverlayOptions().image(groundOverlayBitmap));
                if (!containerVisibility) {
                    mapGroundOverlay.setVisible(false);
                }
                groundOverlays.put(kmlGroundOverlay, mapGroundOverlay);
            }
        }
    }

    /* access modifiers changed from: private */
    public void addGroundOverlayInContainerGroups(String groundOverlayUrl, Iterable<KmlContainer> kmlContainers, boolean containerVisibility) {
        for (KmlContainer container : kmlContainers) {
            boolean isContainerVisible = getContainerVisibility(container, containerVisibility);
            addGroundOverlayToMap(groundOverlayUrl, container.getGroundOverlayHashMap(), isContainerVisible);
            if (container.hasContainers()) {
                addGroundOverlayInContainerGroups(groundOverlayUrl, container.getContainers(), isContainerVisible);
            }
        }
    }

    public class MarkerIconImageDownload extends AsyncTask<String, Void, Bitmap> {
        private final String mIconUrl;

        public MarkerIconImageDownload(String iconUrl) {
            this.mIconUrl = iconUrl;
            KmlRenderer.this.downloadStarted();
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(String... params) {
            try {
                return KmlRenderer.this.getBitmapFromUrl(this.mIconUrl);
            } catch (MalformedURLException e) {
                return BitmapFactory.decodeFile(this.mIconUrl);
            } catch (IOException e2) {
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) {
                Log.e(KmlRenderer.LOG_TAG, "Image at this URL could not be found " + this.mIconUrl);
            } else {
                KmlRenderer.this.cacheBitmap(this.mIconUrl, bitmap);
                if (KmlRenderer.this.isLayerOnMap()) {
                    KmlRenderer kmlRenderer = KmlRenderer.this;
                    kmlRenderer.addIconToMarkers(this.mIconUrl, kmlRenderer.getAllFeatures());
                    KmlRenderer kmlRenderer2 = KmlRenderer.this;
                    kmlRenderer2.addContainerGroupIconsToMarkers(this.mIconUrl, kmlRenderer2.mContainers);
                }
            }
            KmlRenderer.this.downloadFinished();
        }
    }

    public class GroundOverlayImageDownload extends AsyncTask<String, Void, Bitmap> {
        private final String mGroundOverlayUrl;

        public GroundOverlayImageDownload(String groundOverlayUrl) {
            this.mGroundOverlayUrl = groundOverlayUrl;
            KmlRenderer.this.downloadStarted();
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(String... params) {
            try {
                return KmlRenderer.this.getBitmapFromUrl(this.mGroundOverlayUrl);
            } catch (MalformedURLException e) {
                return BitmapFactory.decodeFile(this.mGroundOverlayUrl);
            } catch (IOException e2) {
                Log.e(KmlRenderer.LOG_TAG, "Image [" + this.mGroundOverlayUrl + "] download issue", e2);
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) {
                Log.e(KmlRenderer.LOG_TAG, "Image at this URL could not be found " + this.mGroundOverlayUrl);
            } else {
                KmlRenderer.this.cacheBitmap(this.mGroundOverlayUrl, bitmap);
                if (KmlRenderer.this.isLayerOnMap()) {
                    KmlRenderer kmlRenderer = KmlRenderer.this;
                    kmlRenderer.addGroundOverlayToMap(this.mGroundOverlayUrl, kmlRenderer.getGroundOverlayMap(), true);
                    KmlRenderer kmlRenderer2 = KmlRenderer.this;
                    kmlRenderer2.addGroundOverlayInContainerGroups(this.mGroundOverlayUrl, kmlRenderer2.mContainers, true);
                }
            }
            KmlRenderer.this.downloadFinished();
        }
    }

    /* access modifiers changed from: private */
    public Bitmap getBitmapFromUrl(String url) {
        return BitmapFactory.decodeStream(openConnectionCheckRedirects(new URL(url).openConnection()));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0015, code lost:
        r3 = (java.net.HttpURLConnection) r11;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.io.InputStream openConnectionCheckRedirects(java.net.URLConnection r11) {
        /*
            r10 = this;
            r0 = 0
        L_0x0001:
            boolean r1 = r11 instanceof java.net.HttpURLConnection
            if (r1 == 0) goto L_0x000c
            r1 = r11
            java.net.HttpURLConnection r1 = (java.net.HttpURLConnection) r1
            r2 = 0
            r1.setInstanceFollowRedirects(r2)
        L_0x000c:
            java.io.InputStream r1 = r11.getInputStream()
            r2 = 0
            boolean r3 = r11 instanceof java.net.HttpURLConnection
            if (r3 == 0) goto L_0x006f
            r3 = r11
            java.net.HttpURLConnection r3 = (java.net.HttpURLConnection) r3
            int r4 = r3.getResponseCode()
            r5 = 300(0x12c, float:4.2E-43)
            if (r4 < r5) goto L_0x006f
            r5 = 307(0x133, float:4.3E-43)
            if (r4 > r5) goto L_0x006f
            r5 = 306(0x132, float:4.29E-43)
            if (r4 == r5) goto L_0x006f
            r5 = 304(0x130, float:4.26E-43)
            if (r4 == r5) goto L_0x006f
            java.net.URL r5 = r3.getURL()
            java.lang.String r6 = "Location"
            java.lang.String r6 = r3.getHeaderField(r6)
            r7 = 0
            if (r6 == 0) goto L_0x003f
            java.net.URL r8 = new java.net.URL
            r8.<init>(r5, r6)
            r7 = r8
        L_0x003f:
            r3.disconnect()
            if (r7 == 0) goto L_0x0067
            java.lang.String r8 = r7.getProtocol()
            java.lang.String r9 = "http"
            boolean r8 = r8.equals(r9)
            if (r8 != 0) goto L_0x005c
            java.lang.String r8 = r7.getProtocol()
            java.lang.String r9 = "https"
            boolean r8 = r8.equals(r9)
            if (r8 == 0) goto L_0x0067
        L_0x005c:
            r8 = 5
            if (r0 >= r8) goto L_0x0067
            r2 = 1
            java.net.URLConnection r11 = r7.openConnection()
            int r0 = r0 + 1
            goto L_0x006f
        L_0x0067:
            java.lang.SecurityException r8 = new java.lang.SecurityException
            java.lang.String r9 = "illegal URL redirect"
            r8.<init>(r9)
            throw r8
        L_0x006f:
            if (r2 != 0) goto L_0x0072
            return r1
        L_0x0072:
            goto L_0x0001
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.maps.android.data.kml.KmlRenderer.openConnectionCheckRedirects(java.net.URLConnection):java.io.InputStream");
    }
}
