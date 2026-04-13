package com.google.maps.android.data.kml;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import androidx.annotation.RawRes;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.collections.GroundOverlayManager;
import com.google.maps.android.collections.MarkerManager;
import com.google.maps.android.collections.PolygonManager;
import com.google.maps.android.collections.PolylineManager;
import com.google.maps.android.data.Layer;
import com.google.maps.android.data.Renderer;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class KmlLayer extends Layer {
    public KmlLayer(GoogleMap map, int resourceId, Context context) {
        this(map, context.getResources().openRawResource(resourceId), context, new MarkerManager(map), new PolygonManager(map), new PolylineManager(map), new GroundOverlayManager(map), (Renderer.ImagesCache) null);
    }

    public KmlLayer(GoogleMap map, InputStream stream, Context context) {
        this(map, stream, context, new MarkerManager(map), new PolygonManager(map), new PolylineManager(map), new GroundOverlayManager(map), (Renderer.ImagesCache) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public KmlLayer(GoogleMap map, @RawRes int resourceId, Context context, MarkerManager markerManager, PolygonManager polygonManager, PolylineManager polylineManager, GroundOverlayManager groundOverlayManager, Renderer.ImagesCache cache) {
        this(map, context.getResources().openRawResource(resourceId), context, markerManager, polygonManager, polylineManager, groundOverlayManager, cache);
        int i = resourceId;
    }

    public KmlLayer(GoogleMap map, InputStream stream, Context context, MarkerManager markerManager, PolygonManager polygonManager, PolylineManager polylineManager, GroundOverlayManager groundOverlayManager, Renderer.ImagesCache cache) {
        InputStream inputStream = stream;
        if (inputStream != null) {
            KmlRenderer renderer = new KmlRenderer(map, context, markerManager, polygonManager, polylineManager, groundOverlayManager, cache);
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            bis.mark(1024);
            ZipInputStream zip = new ZipInputStream(bis);
            KmlParser parser = null;
            try {
                ZipEntry entry = zip.getNextEntry();
                if (entry != null) {
                    HashMap<String, Bitmap> images = new HashMap<>();
                    ZipEntry entry2 = entry;
                    while (entry2 != null) {
                        if (parser != null || !entry2.getName().toLowerCase().endsWith(".kml")) {
                            Bitmap bitmap = BitmapFactory.decodeStream(zip);
                            if (bitmap != null) {
                                images.put(entry2.getName(), bitmap);
                            } else {
                                Log.w("KmlLayer", "Unsupported KMZ contents file type: " + entry2.getName());
                            }
                        } else {
                            parser = parseKml(zip);
                        }
                        entry2 = zip.getNextEntry();
                    }
                    if (parser != null) {
                        renderer.storeKmzData(parser.getStyles(), parser.getStyleMaps(), parser.getPlacemarks(), parser.getContainers(), parser.getGroundOverlays(), images);
                        ZipEntry zipEntry = entry2;
                    } else {
                        throw new IllegalArgumentException("KML not found in InputStream");
                    }
                } else {
                    bis.reset();
                    KmlParser parser2 = parseKml(bis);
                    renderer.storeKmlData(parser2.getStyles(), parser2.getStyleMaps(), parser2.getPlacemarks(), parser2.getContainers(), parser2.getGroundOverlays());
                }
                try {
                    storeRenderer(renderer);
                    stream.close();
                    bis.close();
                    zip.close();
                } catch (Throwable th) {
                    th = th;
                    stream.close();
                    bis.close();
                    zip.close();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                stream.close();
                bis.close();
                zip.close();
                throw th;
            }
        } else {
            throw new IllegalArgumentException("KML InputStream cannot be null");
        }
    }

    private static KmlParser parseKml(InputStream stream) {
        KmlParser parser = new KmlParser(createXmlParser(stream));
        parser.parseKml();
        return parser;
    }

    private static XmlPullParser createXmlParser(InputStream stream) {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(stream, (String) null);
        return parser;
    }

    public void addLayerToMap() {
        super.addKMLToMap();
    }

    public boolean hasPlacemarks() {
        return hasFeatures();
    }

    public Iterable<KmlPlacemark> getPlacemarks() {
        return getFeatures();
    }

    public boolean hasContainers() {
        return super.hasContainers();
    }

    public Iterable<KmlContainer> getContainers() {
        return super.getContainers();
    }

    public Iterable<KmlGroundOverlay> getGroundOverlays() {
        return super.getGroundOverlays();
    }
}
