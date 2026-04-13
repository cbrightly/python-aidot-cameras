package com.google.maps.android.data.kml;

import com.google.maps.android.data.Geometry;
import java.util.ArrayList;
import java.util.Iterator;

public class KmlMultiTrack extends KmlMultiGeometry {
    public KmlMultiTrack(ArrayList<KmlTrack> tracks) {
        super(createGeometries(tracks));
    }

    private static ArrayList<Geometry> createGeometries(ArrayList<KmlTrack> tracks) {
        ArrayList<Geometry> geometries = new ArrayList<>();
        if (tracks != null) {
            Iterator<KmlTrack> it = tracks.iterator();
            while (it.hasNext()) {
                geometries.add(it.next());
            }
            return geometries;
        }
        throw new IllegalArgumentException("Tracks cannot be null");
    }
}
