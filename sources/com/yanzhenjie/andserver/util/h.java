package com.yanzhenjie.andserver.util;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.yanzhenjie.andserver.error.InvalidMediaTypeException;
import com.yanzhenjie.andserver.error.InvalidMimeTypeException;
import com.yanzhenjie.andserver.util.i;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/* compiled from: MediaType */
public class h extends i implements Serializable {
    public static final h ALL = valueOf(ALL_VALUE);
    public static final String ALL_VALUE = "*/*";
    public static final h APPLICATION_ATOM_XML = valueOf(APPLICATION_ATOM_XML_VALUE);
    public static final String APPLICATION_ATOM_XML_VALUE = "application/atom+xml";
    public static final h APPLICATION_FORM_URLENCODED = valueOf("application/x-www-form-urlencoded");
    public static final String APPLICATION_FORM_URLENCODED_VALUE = "application/x-www-form-urlencoded";
    public static final h APPLICATION_JSON = valueOf("application/json");
    public static final h APPLICATION_JSON_UTF8 = valueOf("application/json;charset=UTF-8");
    public static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";
    public static final String APPLICATION_JSON_VALUE = "application/json";
    public static final h APPLICATION_OCTET_STREAM = valueOf(APPLICATION_OCTET_STREAM_VALUE);
    public static final String APPLICATION_OCTET_STREAM_VALUE = "application/octet-stream";
    public static final h APPLICATION_PDF = valueOf(APPLICATION_PDF_VALUE);
    public static final String APPLICATION_PDF_VALUE = "application/pdf";
    public static final h APPLICATION_RSS_XML = valueOf(APPLICATION_RSS_XML_VALUE);
    public static final String APPLICATION_RSS_XML_VALUE = "application/rss+xml";
    public static final h APPLICATION_XHTML_XML = valueOf(APPLICATION_XHTML_XML_VALUE);
    public static final String APPLICATION_XHTML_XML_VALUE = "application/xhtml+xml";
    public static final h APPLICATION_XML = valueOf(APPLICATION_XML_VALUE);
    public static final h APPLICATION_XML_UTF8 = valueOf(APPLICATION_XML_UTF8_VALUE);
    public static final String APPLICATION_XML_UTF8_VALUE = "application/xml;charset=UTF-8";
    public static final String APPLICATION_XML_VALUE = "application/xml";
    public static final h IMAGE_GIF = valueOf(IMAGE_GIF_VALUE);
    public static final String IMAGE_GIF_VALUE = "image/gif";
    public static final h IMAGE_JPEG = valueOf("image/jpeg");
    public static final String IMAGE_JPEG_VALUE = "image/jpeg";
    public static final h IMAGE_PNG = valueOf("image/png");
    public static final String IMAGE_PNG_VALUE = "image/png";
    public static final h MULTIPART_FORM_DATA = valueOf(MULTIPART_FORM_DATA_VALUE);
    public static final String MULTIPART_FORM_DATA_VALUE = "multipart/form-data";
    public static final Comparator<h> QUALITY_VALUE_COMPARATOR = new a();
    public static final Comparator<h> SPECIFICITY_COMPARATOR = new b();
    public static final h TEXT_EVENT_STREAM = valueOf(TEXT_EVENT_STREAM_VALUE);
    public static final String TEXT_EVENT_STREAM_VALUE = "text/event-stream";
    public static final h TEXT_HTML = valueOf(TEXT_HTML_VALUE);
    public static final String TEXT_HTML_VALUE = "text/html";
    public static final h TEXT_MARKDOWN = valueOf(TEXT_MARKDOWN_VALUE);
    public static final String TEXT_MARKDOWN_VALUE = "text/markdown";
    public static final h TEXT_PLAIN = valueOf(TEXT_PLAIN_VALUE);
    public static final String TEXT_PLAIN_VALUE = "text/plain";
    public static final h TEXT_XML = valueOf(TEXT_XML_VALUE);
    public static final String TEXT_XML_VALUE = "text/xml";

    public h(String type) {
        super(type);
    }

    public h(String type, String subtype) {
        super(type, subtype, (Map<String, String>) Collections.emptyMap());
    }

    public h(String type, String subtype, Charset charset) {
        super(type, subtype, charset);
    }

    public h(String type, String subtype, double qualityValue) {
        this(type, subtype, (Map<String, String>) Collections.singletonMap("q", Double.toString(qualityValue)));
    }

    public h(h other, Charset charset) {
        super((i) other, charset);
    }

    public h(h other, Map<String, String> parameters) {
        super(other.getType(), other.getSubtype(), parameters);
    }

    public h(String type, String subtype, Map<String, String> parameters) {
        super(type, subtype, parameters);
    }

    /* access modifiers changed from: protected */
    public void checkParameters(String attribute, String value) {
        super.checkParameters(attribute, value);
        if ("q".equals(attribute)) {
            String value2 = unquote(value);
            double d = Double.parseDouble(value2);
            a.b(d >= 0.0d && d <= 1.0d, "Invalid quality value '" + value2 + "': should be between 0.0 and 1.0");
        }
    }

    public double getQualityValue() {
        String qualityFactory = getParameter("q");
        if (qualityFactory != null) {
            return Double.parseDouble(unquote(qualityFactory));
        }
        return 1.0d;
    }

    public boolean includes(h other) {
        return super.includes(other);
    }

    public boolean isCompatibleWith(h other) {
        return super.isCompatibleWith(other);
    }

    public h copyQualityValue(h mediaType) {
        if (!mediaType.getParameters().containsKey("q")) {
            return this;
        }
        Map<String, String> params = new LinkedHashMap<>(getParameters());
        params.put("q", mediaType.getParameters().get("q"));
        return new h(this, params);
    }

    public h removeQualityValue() {
        if (!getParameters().containsKey("q")) {
            return this;
        }
        Map<String, String> params = new LinkedHashMap<>(getParameters());
        params.remove("q");
        return new h(this, params);
    }

    public static h valueOf(String value) {
        return parseMediaType(value);
    }

    public static h parseMediaType(String mediaType) {
        try {
            i type = i.valueOf(mediaType);
            try {
                return new h(type.getType(), type.getSubtype(), type.getParameters());
            } catch (IllegalArgumentException ex) {
                throw new InvalidMediaTypeException(mediaType, ex.getMessage());
            }
        } catch (InvalidMimeTypeException ex2) {
            throw new InvalidMediaTypeException(ex2);
        }
    }

    public static List<h> parseMediaTypes(String mediaTypes) {
        if (TextUtils.isEmpty(mediaTypes)) {
            return Collections.emptyList();
        }
        StringTokenizer st = new StringTokenizer(mediaTypes, ",");
        List<String> tokens = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken().trim();
            if (token.length() > 0) {
                tokens.add(token);
            }
        }
        List<MediaType> result = new ArrayList<>(tokens.size());
        for (String token2 : tokens) {
            result.add(parseMediaType(token2));
        }
        return result;
    }

    public static List<h> parseMediaTypes(List<String> mediaTypes) {
        if (mediaTypes == null || mediaTypes.isEmpty()) {
            return Collections.emptyList();
        }
        if (mediaTypes.size() == 1) {
            return parseMediaTypes(mediaTypes.get(0));
        }
        List<MediaType> result = new ArrayList<>(8);
        for (String mediaType : mediaTypes) {
            result.addAll(parseMediaTypes(mediaType));
        }
        return result;
    }

    public static void sortBySpecificity(List<h> mediaTypes) {
        a.c(mediaTypes, "'mediaTypes' must not be null");
        if (mediaTypes.size() > 1) {
            Collections.sort(mediaTypes, SPECIFICITY_COMPARATOR);
        }
    }

    public static void sortByQualityValue(List<h> mediaTypes) {
        a.c(mediaTypes, "'mediaTypes' must not be null");
        if (mediaTypes.size() > 1) {
            Collections.sort(mediaTypes, QUALITY_VALUE_COMPARATOR);
        }
    }

    public static void sortBySpecificityAndQuality(List<h> mediaTypes) {
        a.c(mediaTypes, "'mediaTypes' must not be null");
        if (mediaTypes.size() > 1) {
            Collections.sort(mediaTypes, new com.yanzhenjie.andserver.util.comparator.a(SPECIFICITY_COMPARATOR, QUALITY_VALUE_COMPARATOR));
        }
    }

    public static h getFileMediaType(String fileName) {
        String extension = getUrlExtension(fileName);
        if (!MimeTypeMap.getSingleton().hasExtension(extension)) {
            return APPLICATION_OCTET_STREAM;
        }
        try {
            return parseMediaType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension));
        } catch (Exception e) {
            return APPLICATION_OCTET_STREAM;
        }
    }

    public static String getUrlExtension(String url) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        return TextUtils.isEmpty(extension) ? "" : extension;
    }

    /* compiled from: MediaType */
    public static final class a implements Comparator<h> {
        a() {
        }

        /* renamed from: a */
        public int compare(h mediaType1, h mediaType2) {
            int qualityComparison = Double.compare(mediaType2.getQualityValue(), mediaType1.getQualityValue());
            if (qualityComparison != 0) {
                return qualityComparison;
            }
            if (mediaType1.isWildcardType() && !mediaType2.isWildcardType()) {
                return 1;
            }
            if (mediaType2.isWildcardType() && !mediaType1.isWildcardType()) {
                return -1;
            }
            if (!mediaType1.getType().equals(mediaType2.getType())) {
                return 0;
            }
            if (mediaType1.isWildcardSubtype() && !mediaType2.isWildcardSubtype()) {
                return 1;
            }
            if (mediaType2.isWildcardSubtype() && !mediaType1.isWildcardSubtype()) {
                return -1;
            }
            if (!mediaType1.getSubtype().equals(mediaType2.getSubtype())) {
                return 0;
            }
            int paramsSize1 = mediaType1.getParameters().size();
            int paramsSize2 = mediaType2.getParameters().size();
            if (paramsSize2 < paramsSize1) {
                return -1;
            }
            if (paramsSize2 == paramsSize1) {
                return 0;
            }
            return 1;
        }
    }

    /* compiled from: MediaType */
    public static final class b extends i.a<h> {
        b() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: c */
        public int b(h mediaType1, h mediaType2) {
            int qualityComparison = Double.compare(mediaType2.getQualityValue(), mediaType1.getQualityValue());
            if (qualityComparison != 0) {
                return qualityComparison;
            }
            return super.b(mediaType1, mediaType2);
        }
    }
}
