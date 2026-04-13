package com.devs.vectorchildfinder;

import android.content.res.TypedArray;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: TypedArrayUtils */
public class c {
    public static boolean e(XmlPullParser parser, String attrName) {
        return parser.getAttributeValue("http://schemas.android.com/apk/res/android", attrName) != null;
    }

    public static float c(TypedArray a, XmlPullParser parser, String attrName, int resId, float defaultValue) {
        if (!e(parser, attrName)) {
            return defaultValue;
        }
        return a.getFloat(resId, defaultValue);
    }

    public static boolean a(TypedArray a, XmlPullParser parser, String attrName, int resId, boolean defaultValue) {
        if (!e(parser, attrName)) {
            return defaultValue;
        }
        return a.getBoolean(resId, defaultValue);
    }

    public static int d(TypedArray a, XmlPullParser parser, String attrName, int resId, int defaultValue) {
        if (!e(parser, attrName)) {
            return defaultValue;
        }
        return a.getInt(resId, defaultValue);
    }

    public static int b(TypedArray a, XmlPullParser parser, String attrName, int resId, int defaultValue) {
        if (!e(parser, attrName)) {
            return defaultValue;
        }
        return a.getColor(resId, defaultValue);
    }
}
