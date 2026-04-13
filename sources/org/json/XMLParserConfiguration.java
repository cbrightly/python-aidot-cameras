package org.json;

import com.google.firebase.analytics.FirebaseAnalytics;

public class XMLParserConfiguration {
    public static final XMLParserConfiguration KEEP_STRINGS = new XMLParserConfiguration(true);
    public static final XMLParserConfiguration ORIGINAL = new XMLParserConfiguration();
    public final String cDataTagName;
    public final boolean convertNilAttributeToNull;
    public final boolean keepStrings;

    public XMLParserConfiguration() {
        this(false, FirebaseAnalytics.Param.CONTENT, false);
    }

    public XMLParserConfiguration(boolean keepStrings2) {
        this(keepStrings2, FirebaseAnalytics.Param.CONTENT, false);
    }

    public XMLParserConfiguration(String cDataTagName2) {
        this(false, cDataTagName2, false);
    }

    public XMLParserConfiguration(boolean keepStrings2, String cDataTagName2) {
        this.keepStrings = keepStrings2;
        this.cDataTagName = cDataTagName2;
        this.convertNilAttributeToNull = false;
    }

    public XMLParserConfiguration(boolean keepStrings2, String cDataTagName2, boolean convertNilAttributeToNull2) {
        this.keepStrings = keepStrings2;
        this.cDataTagName = cDataTagName2;
        this.convertNilAttributeToNull = convertNilAttributeToNull2;
    }
}
