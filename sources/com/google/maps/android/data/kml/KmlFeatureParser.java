package com.google.maps.android.data.kml;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.motion.widget.Key;
import com.amazonaws.util.DateUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.data.Geometry;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class KmlFeatureParser {
    private static final int ALTITUDE_INDEX = 2;
    private static final String BOUNDARY_REGEX = "outerBoundaryIs|innerBoundaryIs";
    private static final String COMPASS_REGEX = "north|south|east|west";
    private static final String EXTENDED_DATA = "ExtendedData";
    private static final String GEOMETRY_REGEX = "Point|LineString|Polygon|MultiGeometry|Track|MultiTrack";
    private static final int LATITUDE_INDEX = 1;
    private static final String LAT_LNG_ALT_SEPARATOR = ",";
    private static final int LONGITUDE_INDEX = 0;
    private static final String PROPERTY_REGEX = "name|description|drawOrder|visibility|open|address|phoneNumber";
    private static final String STYLE_TAG = "Style";
    private static final String STYLE_URL_TAG = "styleUrl";

    KmlFeatureParser() {
    }

    public static class LatLngAlt {
        public final Double altitude;
        public final LatLng latLng;

        LatLngAlt(LatLng latLng2, Double altitude2) {
            this.latLng = latLng2;
            this.altitude = altitude2;
        }
    }

    static KmlPlacemark createPlacemark(XmlPullParser parser) {
        String styleId = null;
        KmlStyle inlineStyle = null;
        HashMap<String, String> properties = new HashMap<>();
        Geometry geometry = null;
        int eventType = parser.getEventType();
        while (true) {
            if (eventType == 3 && parser.getName().equals("Placemark")) {
                return new KmlPlacemark(geometry, styleId, inlineStyle, properties);
            }
            if (eventType == 2) {
                if (parser.getName().equals(STYLE_URL_TAG)) {
                    styleId = parser.nextText();
                } else if (parser.getName().matches(GEOMETRY_REGEX)) {
                    geometry = createGeometry(parser, parser.getName());
                } else if (parser.getName().matches(PROPERTY_REGEX)) {
                    properties.put(parser.getName(), parser.nextText());
                } else if (parser.getName().equals(EXTENDED_DATA)) {
                    properties.putAll(setExtendedDataProperties(parser));
                } else if (parser.getName().equals(STYLE_TAG)) {
                    inlineStyle = KmlStyleParser.createStyle(parser);
                }
            }
            eventType = parser.next();
        }
    }

    static KmlGroundOverlay createGroundOverlay(XmlPullParser parser) {
        float drawOrder = 0.0f;
        float rotation = 0.0f;
        int visibility = 1;
        String imageUrl = null;
        HashMap<String, String> properties = new HashMap<>();
        HashMap hashMap = new HashMap();
        int eventType = parser.getEventType();
        while (true) {
            if (eventType != 3 || !parser.getName().equals("GroundOverlay")) {
                if (eventType == 2) {
                    if (parser.getName().equals("Icon")) {
                        imageUrl = getImageUrl(parser);
                    } else if (parser.getName().equals("drawOrder")) {
                        drawOrder = Float.parseFloat(parser.nextText());
                    } else if (parser.getName().equals("visibility")) {
                        visibility = Integer.parseInt(parser.nextText());
                    } else if (parser.getName().equals(EXTENDED_DATA)) {
                        properties.putAll(setExtendedDataProperties(parser));
                    } else if (parser.getName().equals(Key.ROTATION)) {
                        rotation = getRotation(parser);
                    } else if (parser.getName().matches(PROPERTY_REGEX) || parser.getName().equals(TypedValues.Custom.S_COLOR)) {
                        properties.put(parser.getName(), parser.nextText());
                    } else if (parser.getName().matches(COMPASS_REGEX)) {
                        hashMap.put(parser.getName(), Double.valueOf(Double.parseDouble(parser.nextText())));
                    }
                }
                eventType = parser.next();
            } else {
                return new KmlGroundOverlay(imageUrl, createLatLngBounds((Double) hashMap.get("north"), (Double) hashMap.get("south"), (Double) hashMap.get("east"), (Double) hashMap.get("west")), drawOrder, visibility, properties, rotation);
            }
        }
    }

    private static float getRotation(XmlPullParser parser) {
        return -Float.parseFloat(parser.nextText());
    }

    private static String getImageUrl(XmlPullParser parser) {
        int eventType = parser.getEventType();
        while (true) {
            if (eventType == 3 && parser.getName().equals("Icon")) {
                return null;
            }
            if (eventType == 2 && parser.getName().equals("href")) {
                return parser.nextText();
            }
            eventType = parser.next();
        }
    }

    private static Geometry createGeometry(XmlPullParser parser, String geometryType) {
        int eventType = parser.getEventType();
        while (true) {
            if (eventType == 3 && parser.getName().equals(geometryType)) {
                return null;
            }
            if (eventType == 2) {
                if (parser.getName().equals("Point")) {
                    return createPoint(parser);
                }
                if (parser.getName().equals("LineString")) {
                    return createLineString(parser);
                }
                if (parser.getName().equals("Track")) {
                    return createTrack(parser);
                }
                if (parser.getName().equals(KmlPolygon.GEOMETRY_TYPE)) {
                    return createPolygon(parser);
                }
                if (parser.getName().equals("MultiGeometry")) {
                    return createMultiGeometry(parser);
                }
                if (parser.getName().equals("MultiTrack")) {
                    return createMultiTrack(parser);
                }
            }
            eventType = parser.next();
        }
    }

    private static HashMap<String, String> setExtendedDataProperties(XmlPullParser parser) {
        HashMap<String, String> properties = new HashMap<>();
        String propertyKey = null;
        int eventType = parser.getEventType();
        while (true) {
            if (eventType == 3 && parser.getName().equals(EXTENDED_DATA)) {
                return properties;
            }
            if (eventType == 2) {
                if (parser.getName().equals("Data")) {
                    propertyKey = parser.getAttributeValue((String) null, "name");
                } else if (parser.getName().equals("value") && propertyKey != null) {
                    properties.put(propertyKey, parser.nextText());
                    propertyKey = null;
                }
            }
            eventType = parser.next();
        }
    }

    private static KmlPoint createPoint(XmlPullParser parser) {
        LatLngAlt latLngAlt = null;
        int eventType = parser.getEventType();
        while (true) {
            if (eventType == 3 && parser.getName().equals("Point")) {
                return new KmlPoint(latLngAlt.latLng, latLngAlt.altitude);
            }
            if (eventType == 2 && parser.getName().equals("coordinates")) {
                latLngAlt = convertToLatLngAlt(parser.nextText());
            }
            eventType = parser.next();
        }
    }

    private static KmlLineString createLineString(XmlPullParser parser) {
        ArrayList<LatLng> coordinates = new ArrayList<>();
        ArrayList<Double> altitudes = new ArrayList<>();
        int eventType = parser.getEventType();
        while (true) {
            if (eventType == 3 && parser.getName().equals("LineString")) {
                return new KmlLineString(coordinates, altitudes);
            }
            if (eventType == 2 && parser.getName().equals("coordinates")) {
                for (LatLngAlt latLngAlt : convertToLatLngAltArray(parser.nextText())) {
                    coordinates.add(latLngAlt.latLng);
                    Double d = latLngAlt.altitude;
                    if (d != null) {
                        altitudes.add(d);
                    }
                }
            }
            eventType = parser.next();
        }
    }

    private static KmlTrack createTrack(XmlPullParser parser) {
        DateFormat iso8601 = new SimpleDateFormat(DateUtils.ALTERNATE_ISO8601_DATE_PATTERN, Locale.getDefault());
        iso8601.setTimeZone(TimeZone.getTimeZone("UTC"));
        ArrayList<LatLng> latLngs = new ArrayList<>();
        ArrayList<Double> altitudes = new ArrayList<>();
        ArrayList<Long> timestamps = new ArrayList<>();
        HashMap<String, String> properties = new HashMap<>();
        int eventType = parser.getEventType();
        while (true) {
            if (eventType == 3 && parser.getName().equals("Track")) {
                return new KmlTrack(latLngs, altitudes, timestamps, properties);
            }
            if (eventType == 2) {
                if (parser.getName().equals("coord")) {
                    LatLngAlt latLngAlt = convertToLatLngAlt(parser.nextText(), " ");
                    latLngs.add(latLngAlt.latLng);
                    Double d = latLngAlt.altitude;
                    if (d != null) {
                        altitudes.add(d);
                    }
                } else if (parser.getName().equals("when")) {
                    try {
                        timestamps.add(Long.valueOf(iso8601.parse(parser.nextText()).getTime()));
                    } catch (ParseException e) {
                        throw new XmlPullParserException("Invalid date", parser, e);
                    }
                } else if (parser.getName().equals(EXTENDED_DATA)) {
                    properties.putAll(setExtendedDataProperties(parser));
                }
            }
            eventType = parser.next();
        }
    }

    private static KmlPolygon createPolygon(XmlPullParser parser) {
        boolean isOuterBoundary = false;
        List<LatLng> outerBoundary = new ArrayList<>();
        List<List<LatLng>> innerBoundaries = new ArrayList<>();
        int eventType = parser.getEventType();
        while (true) {
            if (eventType == 3 && parser.getName().equals(KmlPolygon.GEOMETRY_TYPE)) {
                return new KmlPolygon(outerBoundary, innerBoundaries);
            }
            if (eventType == 2) {
                if (parser.getName().matches(BOUNDARY_REGEX)) {
                    isOuterBoundary = parser.getName().equals("outerBoundaryIs");
                } else if (parser.getName().equals("coordinates")) {
                    if (isOuterBoundary) {
                        outerBoundary = convertToLatLngArray(parser.nextText());
                    } else {
                        innerBoundaries.add(convertToLatLngArray(parser.nextText()));
                    }
                }
            }
            eventType = parser.next();
        }
    }

    private static KmlMultiGeometry createMultiGeometry(XmlPullParser parser) {
        ArrayList<Geometry> geometries = new ArrayList<>();
        int eventType = parser.next();
        while (true) {
            if (eventType == 3 && parser.getName().equals("MultiGeometry")) {
                return new KmlMultiGeometry(geometries);
            }
            if (eventType == 2 && parser.getName().matches(GEOMETRY_REGEX)) {
                geometries.add(createGeometry(parser, parser.getName()));
            }
            eventType = parser.next();
        }
    }

    private static KmlMultiTrack createMultiTrack(XmlPullParser parser) {
        ArrayList<KmlTrack> tracks = new ArrayList<>();
        int eventType = parser.next();
        while (true) {
            if (eventType == 3 && parser.getName().equals("MultiTrack")) {
                return new KmlMultiTrack(tracks);
            }
            if (eventType == 2 && parser.getName().matches("Track")) {
                tracks.add(createTrack(parser));
            }
            eventType = parser.next();
        }
    }

    private static ArrayList<LatLng> convertToLatLngArray(String coordinatesString) {
        ArrayList<LatLngAlt> latLngAltsArray = convertToLatLngAltArray(coordinatesString);
        ArrayList<LatLng> coordinatesArray = new ArrayList<>();
        Iterator<LatLngAlt> it = latLngAltsArray.iterator();
        while (it.hasNext()) {
            coordinatesArray.add(it.next().latLng);
        }
        return coordinatesArray;
    }

    private static ArrayList<LatLngAlt> convertToLatLngAltArray(String coordinatesString) {
        ArrayList<LatLngAlt> latLngAltsArray = new ArrayList<>();
        for (String coordinate : coordinatesString.trim().split("(\\s+)")) {
            latLngAltsArray.add(convertToLatLngAlt(coordinate));
        }
        return latLngAltsArray;
    }

    private static LatLngAlt convertToLatLngAlt(String coordinateString) {
        return convertToLatLngAlt(coordinateString, LAT_LNG_ALT_SEPARATOR);
    }

    private static LatLngAlt convertToLatLngAlt(String coordinateString, String separator) {
        String[] coordinate = coordinateString.split(separator);
        return new LatLngAlt(new LatLng(Double.parseDouble(coordinate[1]), Double.parseDouble(coordinate[0])), coordinate.length > 2 ? Double.valueOf(Double.parseDouble(coordinate[2])) : null);
    }

    private static LatLngBounds createLatLngBounds(Double north, Double south, Double east, Double west) {
        return new LatLngBounds(new LatLng(south.doubleValue(), west.doubleValue()), new LatLng(north.doubleValue(), east.doubleValue()));
    }
}
