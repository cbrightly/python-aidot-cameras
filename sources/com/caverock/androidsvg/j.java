package com.caverock.androidsvg;

import android.graphics.Matrix;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.a;
import com.caverock.androidsvg.e;
import com.caverock.androidsvg.g;
import com.leedarson.bean.Constants;
import io.netty.util.internal.StringUtil;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import meshsdk.BaseResp;
import meshsdk.model.GroupInfo;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;
import org.xmlpull.v1.XmlPullParser;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: SVGParser */
public class j {
    private g a = null;
    private g.j0 b = null;
    private boolean c = false;
    private int d;
    private boolean e = false;
    private h f = null;
    private StringBuilder g = null;
    private boolean h = false;
    private StringBuilder i = null;

    j() {
    }

    /* compiled from: SVGParser */
    public enum h {
        svg,
        a,
        circle,
        clipPath,
        defs,
        desc,
        ellipse,
        g,
        image,
        line,
        linearGradient,
        marker,
        mask,
        path,
        pattern,
        polygon,
        polyline,
        radialGradient,
        rect,
        solidColor,
        stop,
        style,
        SWITCH,
        symbol,
        text,
        textPath,
        title,
        tref,
        tspan,
        use,
        view,
        UNSUPPORTED;
        
        private static final Map<String, h> c = null;

        static {
            c = new HashMap();
            for (h elem : values()) {
                if (elem == SWITCH) {
                    c.put("switch", elem);
                } else if (elem != UNSUPPORTED) {
                    c.put(elem.name(), elem);
                }
            }
        }

        public static h fromString(String str) {
            h elem = c.get(str);
            if (elem != null) {
                return elem;
            }
            return UNSUPPORTED;
        }
    }

    /* compiled from: SVGParser */
    public enum g {
        CLASS,
        clip,
        clip_path,
        clipPathUnits,
        clip_rule,
        color,
        cx,
        cy,
        direction,
        dx,
        dy,
        fx,
        fy,
        d,
        display,
        fill,
        fill_rule,
        fill_opacity,
        font,
        font_family,
        font_size,
        font_weight,
        font_style,
        gradientTransform,
        gradientUnits,
        height,
        href,
        image_rendering,
        marker,
        marker_start,
        marker_mid,
        marker_end,
        markerHeight,
        markerUnits,
        markerWidth,
        mask,
        maskContentUnits,
        maskUnits,
        media,
        offset,
        opacity,
        orient,
        overflow,
        pathLength,
        patternContentUnits,
        patternTransform,
        patternUnits,
        points,
        preserveAspectRatio,
        r,
        refX,
        refY,
        requiredFeatures,
        requiredExtensions,
        requiredFormats,
        requiredFonts,
        rx,
        ry,
        solid_color,
        solid_opacity,
        spreadMethod,
        startOffset,
        stop_color,
        stop_opacity,
        stroke,
        stroke_dasharray,
        stroke_dashoffset,
        stroke_linecap,
        stroke_linejoin,
        stroke_miterlimit,
        stroke_opacity,
        stroke_width,
        style,
        systemLanguage,
        text_anchor,
        text_decoration,
        transform,
        type,
        vector_effect,
        version,
        viewBox,
        width,
        x,
        y,
        x1,
        y1,
        x2,
        y2,
        viewport_fill,
        viewport_fill_opacity,
        visibility,
        UNSUPPORTED;
        
        private static final Map<String, g> c = null;

        static {
            int i;
            c = new HashMap();
            for (g attr : values()) {
                if (attr == CLASS) {
                    c.put("class", attr);
                } else if (attr != UNSUPPORTED) {
                    c.put(attr.name().replace('_', '-'), attr);
                }
            }
        }

        public static g fromString(String str) {
            g attr = c.get(str);
            if (attr != null) {
                return attr;
            }
            return UNSUPPORTED;
        }
    }

    /* compiled from: SVGParser */
    public static class c {
        private static final Map<String, Integer> a;

        static {
            HashMap hashMap = new HashMap(47);
            a = hashMap;
            hashMap.put("aliceblue", -984833);
            hashMap.put("antiquewhite", -332841);
            hashMap.put("aqua", -16711681);
            hashMap.put("aquamarine", -8388652);
            hashMap.put("azure", -983041);
            hashMap.put("beige", -657956);
            hashMap.put("bisque", -6972);
            hashMap.put("black", Integer.valueOf(ViewCompat.MEASURED_STATE_MASK));
            hashMap.put("blanchedalmond", -5171);
            hashMap.put("blue", -16776961);
            hashMap.put("blueviolet", -7722014);
            hashMap.put("brown", -5952982);
            hashMap.put("burlywood", -2180985);
            hashMap.put("cadetblue", -10510688);
            hashMap.put("chartreuse", -8388864);
            hashMap.put("chocolate", -2987746);
            hashMap.put("coral", -32944);
            hashMap.put("cornflowerblue", -10185235);
            hashMap.put("cornsilk", -1828);
            hashMap.put("crimson", -2354116);
            hashMap.put("cyan", -16711681);
            hashMap.put("darkblue", -16777077);
            hashMap.put("darkcyan", -16741493);
            hashMap.put("darkgoldenrod", -4684277);
            hashMap.put("darkgray", -5658199);
            hashMap.put("darkgreen", -16751616);
            hashMap.put("darkgrey", -5658199);
            hashMap.put("darkkhaki", -4343957);
            hashMap.put("darkmagenta", -7667573);
            hashMap.put("darkolivegreen", -11179217);
            hashMap.put("darkorange", -29696);
            hashMap.put("darkorchid", -6737204);
            hashMap.put("darkred", -7667712);
            hashMap.put("darksalmon", -1468806);
            hashMap.put("darkseagreen", -7357297);
            hashMap.put("darkslateblue", -12042869);
            hashMap.put("darkslategray", -13676721);
            hashMap.put("darkslategrey", -13676721);
            hashMap.put("darkturquoise", -16724271);
            hashMap.put("darkviolet", -7077677);
            hashMap.put("deeppink", -60269);
            hashMap.put("deepskyblue", -16728065);
            hashMap.put("dimgray", -9868951);
            hashMap.put("dimgrey", -9868951);
            hashMap.put("dodgerblue", -14774017);
            hashMap.put("firebrick", -5103070);
            hashMap.put("floralwhite", -1296);
            hashMap.put("forestgreen", -14513374);
            hashMap.put("fuchsia", -65281);
            hashMap.put("gainsboro", -2302756);
            hashMap.put("ghostwhite", -460545);
            hashMap.put("gold", -10496);
            hashMap.put("goldenrod", -2448096);
            hashMap.put("gray", -8355712);
            hashMap.put("green", -16744448);
            hashMap.put("greenyellow", -5374161);
            hashMap.put("grey", -8355712);
            hashMap.put("honeydew", -983056);
            hashMap.put("hotpink", -38476);
            hashMap.put("indianred", -3318692);
            hashMap.put("indigo", -11861886);
            hashMap.put("ivory", -16);
            hashMap.put("khaki", -989556);
            hashMap.put("lavender", -1644806);
            hashMap.put("lavenderblush", -3851);
            hashMap.put("lawngreen", -8586240);
            hashMap.put("lemonchiffon", -1331);
            hashMap.put("lightblue", -5383962);
            hashMap.put("lightcoral", -1015680);
            hashMap.put("lightcyan", -2031617);
            hashMap.put("lightgoldenrodyellow", -329006);
            hashMap.put("lightgray", -2894893);
            hashMap.put("lightgreen", -7278960);
            hashMap.put("lightgrey", -2894893);
            hashMap.put("lightpink", -18751);
            hashMap.put("lightsalmon", -24454);
            hashMap.put("lightseagreen", -14634326);
            hashMap.put("lightskyblue", -7876870);
            hashMap.put("lightslategray", -8943463);
            hashMap.put("lightslategrey", -8943463);
            hashMap.put("lightsteelblue", -5192482);
            hashMap.put("lightyellow", -32);
            hashMap.put("lime", -16711936);
            hashMap.put("limegreen", -13447886);
            hashMap.put("linen", -331546);
            hashMap.put("magenta", -65281);
            hashMap.put("maroon", -8388608);
            hashMap.put("mediumaquamarine", -10039894);
            hashMap.put("mediumblue", -16777011);
            hashMap.put("mediumorchid", -4565549);
            hashMap.put("mediumpurple", -7114533);
            hashMap.put("mediumseagreen", -12799119);
            hashMap.put("mediumslateblue", -8689426);
            hashMap.put("mediumspringgreen", -16713062);
            hashMap.put("mediumturquoise", -12004916);
            hashMap.put("mediumvioletred", -3730043);
            hashMap.put("midnightblue", -15132304);
            hashMap.put("mintcream", -655366);
            hashMap.put("mistyrose", -6943);
            hashMap.put("moccasin", -6987);
            hashMap.put("navajowhite", -8531);
            hashMap.put("navy", -16777088);
            hashMap.put("oldlace", -133658);
            hashMap.put("olive", -8355840);
            hashMap.put("olivedrab", -9728477);
            hashMap.put("orange", -23296);
            hashMap.put("orangered", -47872);
            hashMap.put("orchid", -2461482);
            hashMap.put("palegoldenrod", -1120086);
            hashMap.put("palegreen", -6751336);
            hashMap.put("paleturquoise", -5247250);
            hashMap.put("palevioletred", -2396013);
            hashMap.put("papayawhip", -4139);
            hashMap.put("peachpuff", -9543);
            hashMap.put("peru", -3308225);
            hashMap.put("pink", -16181);
            hashMap.put("plum", -2252579);
            hashMap.put("powderblue", -5185306);
            hashMap.put("purple", -8388480);
            hashMap.put("rebeccapurple", -10079335);
            hashMap.put("red", Integer.valueOf(SupportMenu.CATEGORY_MASK));
            hashMap.put("rosybrown", -4419697);
            hashMap.put("royalblue", -12490271);
            hashMap.put("saddlebrown", -7650029);
            hashMap.put("salmon", -360334);
            hashMap.put("sandybrown", -744352);
            hashMap.put("seagreen", -13726889);
            hashMap.put("seashell", -2578);
            hashMap.put("sienna", -6270419);
            hashMap.put("silver", -4144960);
            hashMap.put("skyblue", -7876885);
            hashMap.put("slateblue", -9807155);
            hashMap.put("slategray", -9404272);
            hashMap.put("slategrey", -9404272);
            hashMap.put("snow", -1286);
            hashMap.put("springgreen", -16711809);
            hashMap.put("steelblue", -12156236);
            hashMap.put("tan", -2968436);
            hashMap.put("teal", -16744320);
            hashMap.put("thistle", -2572328);
            hashMap.put("tomato", -40121);
            hashMap.put("turquoise", -12525360);
            hashMap.put("violet", -1146130);
            hashMap.put("wheat", -663885);
            hashMap.put("white", -1);
            hashMap.put("whitesmoke", -657931);
            hashMap.put("yellow", Integer.valueOf(InputDeviceCompat.SOURCE_ANY));
            hashMap.put("yellowgreen", -6632142);
            hashMap.put("transparent", 0);
        }

        static Integer a(String colourName) {
            return a.get(colourName);
        }
    }

    /* compiled from: SVGParser */
    public static class d {
        private static final Map<String, g.p> a;

        static {
            HashMap hashMap = new HashMap(9);
            a = hashMap;
            g.d1 d1Var = g.d1.pt;
            hashMap.put("xx-small", new g.p(0.694f, d1Var));
            hashMap.put("x-small", new g.p(0.833f, d1Var));
            hashMap.put("small", new g.p(10.0f, d1Var));
            hashMap.put("medium", new g.p(12.0f, d1Var));
            hashMap.put("large", new g.p(14.4f, d1Var));
            hashMap.put("x-large", new g.p(17.3f, d1Var));
            hashMap.put("xx-large", new g.p(20.7f, d1Var));
            g.d1 d1Var2 = g.d1.percent;
            hashMap.put("smaller", new g.p(83.33f, d1Var2));
            hashMap.put("larger", new g.p(120.0f, d1Var2));
        }

        static g.p a(String fontSize) {
            return a.get(fontSize);
        }
    }

    /* compiled from: SVGParser */
    public static class e {
        private static final Map<String, Integer> a;

        static {
            HashMap hashMap = new HashMap(13);
            a = hashMap;
            Integer valueOf = Integer.valueOf(BaseResp.ERR_MSG_SEND_FAIL);
            hashMap.put(GroupInfo.TYPE_NORMAL, valueOf);
            hashMap.put("bold", 700);
            hashMap.put("bolder", 1);
            hashMap.put("lighter", -1);
            hashMap.put("100", 100);
            hashMap.put("200", 200);
            hashMap.put("300", Integer.valueOf(IjkMediaCodecInfo.RANK_SECURE));
            hashMap.put("400", valueOf);
            hashMap.put("500", 500);
            hashMap.put("600", 600);
            hashMap.put("700", 700);
            hashMap.put("800", 800);
            hashMap.put("900", 900);
        }

        static Integer a(String fontWeight) {
            return a.get(fontWeight);
        }
    }

    /* compiled from: SVGParser */
    public static class b {
        private static final Map<String, e.a> a;

        static {
            HashMap hashMap = new HashMap(10);
            a = hashMap;
            hashMap.put("none", e.a.none);
            hashMap.put("xMinYMin", e.a.xMinYMin);
            hashMap.put("xMidYMin", e.a.xMidYMin);
            hashMap.put("xMaxYMin", e.a.xMaxYMin);
            hashMap.put("xMinYMid", e.a.xMinYMid);
            hashMap.put("xMidYMid", e.a.xMidYMid);
            hashMap.put("xMaxYMid", e.a.xMaxYMid);
            hashMap.put("xMinYMax", e.a.xMinYMax);
            hashMap.put("xMidYMax", e.a.xMidYMax);
            hashMap.put("xMaxYMax", e.a.xMaxYMax);
        }

        static e.a a(String aspectRatio) {
            return a.get(aspectRatio);
        }
    }

    /* access modifiers changed from: package-private */
    public g z(InputStream is, boolean enableInternalEntities) {
        if (!is.markSupported()) {
            is = new BufferedInputStream(is);
        }
        try {
            is.mark(3);
            int firstTwoBytes = is.read() + (is.read() << 8);
            is.reset();
            if (firstTwoBytes == 35615) {
                is = new BufferedInputStream(new GZIPInputStream(is));
            }
        } catch (IOException e2) {
        }
        try {
            is.mark(4096);
            L0(is, enableInternalEntities);
            return this.a;
        } finally {
            try {
                is.close();
            } catch (IOException e3) {
                Log.e("SVGParser", "Exception thrown closing input stream");
            }
        }
    }

    /* renamed from: com.caverock.androidsvg.j$j  reason: collision with other inner class name */
    /* compiled from: SVGParser */
    public class C0054j implements Attributes {
        private XmlPullParser a;

        public C0054j(XmlPullParser parser) {
            this.a = parser;
        }

        public int getLength() {
            return this.a.getAttributeCount();
        }

        public String getURI(int index) {
            return this.a.getAttributeNamespace(index);
        }

        public String getLocalName(int index) {
            return this.a.getAttributeName(index);
        }

        public String getQName(int index) {
            String qName = this.a.getAttributeName(index);
            if (this.a.getAttributePrefix(index) == null) {
                return qName;
            }
            return this.a.getAttributePrefix(index) + ':' + qName;
        }

        public String getValue(int index) {
            return this.a.getAttributeValue(index);
        }

        public String getType(int index) {
            return null;
        }

        public int getIndex(String uri, String localName) {
            return -1;
        }

        public int getIndex(String qName) {
            return -1;
        }

        public String getType(String uri, String localName) {
            return null;
        }

        public String getType(String qName) {
            return null;
        }

        public String getValue(String uri, String localName) {
            return null;
        }

        public String getValue(String qName) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0100, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0108, code lost:
        throw new com.caverock.androidsvg.SVGParseException("Stream error", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0109, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0111, code lost:
        throw new com.caverock.androidsvg.SVGParseException("XML parser problem", r0);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0109 A[ExcHandler: XmlPullParserException (r0v0 'e' org.xmlpull.v1.XmlPullParserException A[CUSTOM_DECLARE]), Splitter:B:0:0x0000] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void L0(java.io.InputStream r10, boolean r11) {
        /*
            r9 = this;
            org.xmlpull.v1.XmlPullParser r0 = android.util.Xml.newPullParser()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            com.caverock.androidsvg.j$j r1 = new com.caverock.androidsvg.j$j     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r1.<init>(r0)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.lang.String r2 = "http://xmlpull.org/v1/doc/features.html#process-docdecl"
            r3 = 0
            r0.setFeature(r2, r3)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.lang.String r2 = "http://xmlpull.org/v1/doc/features.html#process-namespaces"
            r4 = 1
            r0.setFeature(r2, r4)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r2 = 0
            r0.setInput(r10, r2)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            int r2 = r0.getEventType()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
        L_0x001d:
            if (r2 == r4) goto L_0x00fb
            r5 = 58
            java.lang.String r6 = "SVGParser"
            switch(r2) {
                case 0: goto L_0x00f0;
                case 1: goto L_0x0026;
                case 2: goto L_0x00c3;
                case 3: goto L_0x0096;
                case 4: goto L_0x0087;
                case 5: goto L_0x007f;
                case 6: goto L_0x0026;
                case 7: goto L_0x0026;
                case 8: goto L_0x0051;
                case 9: goto L_0x0026;
                case 10: goto L_0x0028;
                default: goto L_0x0026;
            }
        L_0x0026:
            goto L_0x00f4
        L_0x0028:
            if (r11 == 0) goto L_0x00f4
            com.caverock.androidsvg.g r5 = r9.a     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            com.caverock.androidsvg.g$f0 r5 = r5.p()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            if (r5 != 0) goto L_0x00f4
            java.lang.String r5 = r0.getText()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.lang.String r7 = "<!ENTITY "
            boolean r5 = r5.contains(r7)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            if (r5 == 0) goto L_0x00f4
            java.lang.String r3 = "Switching to SAX parser to process entities"
            android.util.Log.d(r6, r3)     // Catch:{ IOException -> 0x004a, XmlPullParserException -> 0x0109 }
            r10.reset()     // Catch:{ IOException -> 0x004a, XmlPullParserException -> 0x0109 }
            r9.K0(r10)     // Catch:{ IOException -> 0x004a, XmlPullParserException -> 0x0109 }
            goto L_0x0050
        L_0x004a:
            r3 = move-exception
            java.lang.String r4 = "Detected internal entity definitions, but could not parse them."
            android.util.Log.w(r6, r4)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
        L_0x0050:
            return
        L_0x0051:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r5.<init>()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.lang.String r7 = "PROC INSTR: "
            r5.append(r7)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.lang.String r7 = r0.getText()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r5.append(r7)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.lang.String r5 = r5.toString()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            android.util.Log.d(r6, r5)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            com.caverock.androidsvg.j$i r5 = new com.caverock.androidsvg.j$i     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.lang.String r6 = r0.getText()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r5.<init>(r6)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.lang.String r6 = r5.r()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.util.Map r7 = r9.x0(r5)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r9.r(r6, r7)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            goto L_0x00f4
        L_0x007f:
            java.lang.String r5 = r0.getText()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r9.c1(r5)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            goto L_0x00f4
        L_0x0087:
            r5 = 2
            int[] r5 = new int[r5]     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            char[] r6 = r0.getTextCharacters(r5)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r7 = r5[r3]     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r8 = r5[r4]     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r9.e1(r6, r7, r8)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            goto L_0x00f4
        L_0x0096:
            java.lang.String r6 = r0.getName()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.lang.String r7 = r0.getPrefix()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            if (r7 == 0) goto L_0x00b7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.lang.String r8 = r0.getPrefix()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r7.append(r8)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r7.append(r5)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r7.append(r6)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.lang.String r5 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r6 = r5
        L_0x00b7:
            java.lang.String r5 = r0.getNamespace()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.lang.String r7 = r0.getName()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r9.p(r5, r7, r6)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            goto L_0x00f4
        L_0x00c3:
            java.lang.String r6 = r0.getName()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.lang.String r7 = r0.getPrefix()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            if (r7 == 0) goto L_0x00e4
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.lang.String r8 = r0.getPrefix()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r7.append(r8)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r7.append(r5)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r7.append(r6)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.lang.String r5 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r6 = r5
        L_0x00e4:
            java.lang.String r5 = r0.getNamespace()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            java.lang.String r7 = r0.getName()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r9.X0(r5, r7, r6, r1)     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            goto L_0x00f4
        L_0x00f0:
            r9.W0()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
        L_0x00f4:
            int r5 = r0.nextToken()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            r2 = r5
            goto L_0x001d
        L_0x00fb:
            r9.o()     // Catch:{ XmlPullParserException -> 0x0109, IOException -> 0x0100 }
            return
        L_0x0100:
            r0 = move-exception
            com.caverock.androidsvg.SVGParseException r1 = new com.caverock.androidsvg.SVGParseException
            java.lang.String r2 = "Stream error"
            r1.<init>(r2, r0)
            throw r1
        L_0x0109:
            r0 = move-exception
            com.caverock.androidsvg.SVGParseException r1 = new com.caverock.androidsvg.SVGParseException
            java.lang.String r2 = "XML parser problem"
            r1.<init>(r2, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.j.L0(java.io.InputStream, boolean):void");
    }

    private void K0(InputStream is) {
        Log.d("SVGParser", "Falling back to SAX parser");
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            spf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            XMLReader xr = spf.newSAXParser().getXMLReader();
            f handler = new f(this, (a) null);
            xr.setContentHandler(handler);
            xr.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
            xr.parse(new InputSource(is));
        } catch (ParserConfigurationException e2) {
            throw new SVGParseException("XML parser problem", e2);
        } catch (SAXException e3) {
            throw new SVGParseException("SVG parse error", e3);
        } catch (IOException e4) {
            throw new SVGParseException("Stream error", e4);
        }
    }

    /* compiled from: SVGParser */
    public class f extends DefaultHandler2 {
        private f() {
        }

        /* synthetic */ f(j x0, a x1) {
            this();
        }

        public void startDocument() {
            j.this.W0();
        }

        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            j.this.X0(uri, localName, qName, attributes);
        }

        public void characters(char[] ch, int start, int length) {
            j.this.c1(new String(ch, start, length));
        }

        public void endElement(String uri, String localName, String qName) {
            j.this.p(uri, localName, qName);
        }

        public void endDocument() {
            j.this.o();
        }

        public void processingInstruction(String target, String data) {
            j.this.r(target, j.this.x0(new i(data)));
        }
    }

    /* access modifiers changed from: private */
    public void W0() {
        this.a = new g();
    }

    /* access modifiers changed from: private */
    public void X0(String uri, String localName, String qName, Attributes attributes) {
        if (this.c) {
            this.d++;
        } else if ("http://www.w3.org/2000/svg".equals(uri) || "".equals(uri)) {
            h elem = h.fromString(localName.length() > 0 ? localName : qName);
            switch (a.a[elem.ordinal()]) {
                case 1:
                    a1(attributes);
                    return;
                case 2:
                case 3:
                    q(attributes);
                    return;
                case 4:
                    m(attributes);
                    return;
                case 5:
                    i1(attributes);
                    return;
                case 6:
                    O0(attributes);
                    return;
                case 7:
                    U0(attributes);
                    return;
                case 8:
                    i(attributes);
                    return;
                case 9:
                    n(attributes);
                    return;
                case 10:
                    v(attributes);
                    return;
                case 11:
                    R0(attributes);
                    return;
                case 12:
                    Q0(attributes);
                    return;
                case 13:
                    d1(attributes);
                    return;
                case 14:
                    h1(attributes);
                    return;
                case 15:
                    g1(attributes);
                    return;
                case 16:
                    k1(attributes);
                    return;
                case 17:
                    b1(attributes);
                    return;
                case 18:
                    x(attributes);
                    return;
                case 19:
                    w(attributes);
                    return;
                case 20:
                    T0(attributes);
                    return;
                case 21:
                    Y0(attributes);
                    return;
                case 22:
                case 23:
                    this.e = true;
                    this.f = elem;
                    return;
                case 24:
                    k(attributes);
                    return;
                case 25:
                    f1(attributes);
                    return;
                case 26:
                    P0(attributes);
                    return;
                case 27:
                    u(attributes);
                    return;
                case 28:
                    j1(attributes);
                    return;
                case 29:
                    y(attributes);
                    return;
                case 30:
                    Z0(attributes);
                    return;
                case 31:
                    V0(attributes);
                    return;
                default:
                    this.c = true;
                    this.d = 1;
                    return;
            }
        }
    }

    /* access modifiers changed from: private */
    public void c1(String characters) {
        if (!this.c) {
            if (this.e) {
                if (this.g == null) {
                    this.g = new StringBuilder(characters.length());
                }
                this.g.append(characters);
            } else if (this.h) {
                if (this.i == null) {
                    this.i = new StringBuilder(characters.length());
                }
                this.i.append(characters);
            } else if (this.b instanceof g.y0) {
                h(characters);
            }
        }
    }

    private void e1(char[] ch, int start, int length) {
        if (!this.c) {
            if (this.e) {
                if (this.g == null) {
                    this.g = new StringBuilder(length);
                }
                this.g.append(ch, start, length);
            } else if (this.h) {
                if (this.i == null) {
                    this.i = new StringBuilder(length);
                }
                this.i.append(ch, start, length);
            } else if (this.b instanceof g.y0) {
                h(new String(ch, start, length));
            }
        }
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void h(java.lang.String r7) {
        /*
            r6 = this;
            com.caverock.androidsvg.g$j0 r0 = r6.b
            com.caverock.androidsvg.g$h0 r0 = (com.caverock.androidsvg.g.h0) r0
            java.util.List<com.caverock.androidsvg.g$n0> r1 = r0.i
            int r1 = r1.size()
            if (r1 != 0) goto L_0x000e
            r2 = 0
            goto L_0x0018
        L_0x000e:
            java.util.List<com.caverock.androidsvg.g$n0> r2 = r0.i
            int r3 = r1 + -1
            java.lang.Object r2 = r2.get(r3)
            com.caverock.androidsvg.g$n0 r2 = (com.caverock.androidsvg.g.n0) r2
        L_0x0018:
            boolean r3 = r2 instanceof com.caverock.androidsvg.g.c1
            if (r3 == 0) goto L_0x0033
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r4 = r2
            com.caverock.androidsvg.g$c1 r4 = (com.caverock.androidsvg.g.c1) r4
            java.lang.String r5 = r4.c
            r3.append(r5)
            r3.append(r7)
            java.lang.String r3 = r3.toString()
            r4.c = r3
            goto L_0x003d
        L_0x0033:
            com.caverock.androidsvg.g$j0 r3 = r6.b
            com.caverock.androidsvg.g$c1 r4 = new com.caverock.androidsvg.g$c1
            r4.<init>(r7)
            r3.g(r4)
        L_0x003d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.j.h(java.lang.String):void");
    }

    /* access modifiers changed from: private */
    public void p(String uri, String localName, String qName) {
        if (this.c) {
            int i2 = this.d - 1;
            this.d = i2;
            if (i2 == 0) {
                this.c = false;
                return;
            }
        }
        if ("http://www.w3.org/2000/svg".equals(uri) || "".equals(uri)) {
            switch (a.a[h.fromString(localName.length() > 0 ? localName : qName).ordinal()]) {
                case 1:
                case 2:
                case 4:
                case 5:
                case 13:
                case 14:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 31:
                    this.b = ((g.n0) this.b).b;
                    return;
                case 22:
                case 23:
                    this.e = false;
                    StringBuilder sb = this.g;
                    if (sb != null) {
                        h hVar = this.f;
                        if (hVar == h.title) {
                            this.a.B(sb.toString());
                        } else if (hVar == h.desc) {
                            this.a.w(sb.toString());
                        }
                        this.g.setLength(0);
                        return;
                    }
                    return;
                case 30:
                    StringBuilder sb2 = this.i;
                    if (sb2 != null) {
                        this.h = false;
                        Z(sb2.toString());
                        this.i.setLength(0);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: private */
    public void o() {
    }

    /* access modifiers changed from: private */
    public void r(String instruction, Map<String, String> attributes) {
        String attr;
        if (instruction.equals("xml-stylesheet") && g.k() != null) {
            if (attributes.get(IjkMediaMeta.IJKM_KEY_TYPE) != null && !"text/css".equals(attributes.get(IjkMediaMeta.IJKM_KEY_TYPE))) {
                return;
            }
            if ((attributes.get("alternate") == null || "no".equals(attributes.get("alternate"))) && (attr = attributes.get("href")) != null) {
                g.k().b(attr);
                throw null;
            }
        }
    }

    /* access modifiers changed from: private */
    public Map<String, String> x0(i scan) {
        HashMap<String, String> attributes = new HashMap<>();
        scan.A();
        String attrName = scan.s('=');
        while (attrName != null) {
            scan.f('=');
            attributes.put(attrName, scan.q());
            scan.A();
            attrName = scan.s('=');
        }
        return attributes;
    }

    private void l(String format, Object... args) {
    }

    private void a1(Attributes attributes) {
        l("<svg>", new Object[0]);
        g.f0 obj = new g.f0();
        obj.a = this.a;
        obj.b = this.b;
        D(obj, attributes);
        S(obj, attributes);
        C(obj, attributes);
        Y(obj, attributes);
        Q(obj, attributes);
        g.j0 j0Var = this.b;
        if (j0Var == null) {
            this.a.A(obj);
        } else {
            j0Var.g(obj);
        }
        this.b = obj;
    }

    private void Q(g.f0 obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 1:
                    obj.q = o0(val);
                    break;
                case 2:
                    obj.r = o0(val);
                    break;
                case 3:
                    g.p o0 = o0(val);
                    obj.s = o0;
                    if (!o0.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <svg> element. width cannot be negative");
                    }
                case 4:
                    g.p o02 = o0(val);
                    obj.t = o02;
                    if (!o02.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <svg> element. height cannot be negative");
                    }
                case 5:
                    obj.u = val;
                    break;
            }
        }
    }

    /* compiled from: SVGParser */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[g.values().length];
            b = iArr;
            try {
                iArr[g.x.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                b[g.y.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                b[g.width.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                b[g.height.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                b[g.version.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                b[g.href.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                b[g.preserveAspectRatio.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                b[g.d.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                b[g.pathLength.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                b[g.rx.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                b[g.ry.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                b[g.cx.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                b[g.cy.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                b[g.r.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                b[g.x1.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                b[g.y1.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                b[g.x2.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                b[g.y2.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                b[g.dx.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
            try {
                b[g.dy.ordinal()] = 20;
            } catch (NoSuchFieldError e20) {
            }
            try {
                b[g.requiredFeatures.ordinal()] = 21;
            } catch (NoSuchFieldError e21) {
            }
            try {
                b[g.requiredExtensions.ordinal()] = 22;
            } catch (NoSuchFieldError e22) {
            }
            try {
                b[g.systemLanguage.ordinal()] = 23;
            } catch (NoSuchFieldError e23) {
            }
            try {
                b[g.requiredFormats.ordinal()] = 24;
            } catch (NoSuchFieldError e24) {
            }
            try {
                b[g.requiredFonts.ordinal()] = 25;
            } catch (NoSuchFieldError e25) {
            }
            try {
                b[g.refX.ordinal()] = 26;
            } catch (NoSuchFieldError e26) {
            }
            try {
                b[g.refY.ordinal()] = 27;
            } catch (NoSuchFieldError e27) {
            }
            try {
                b[g.markerWidth.ordinal()] = 28;
            } catch (NoSuchFieldError e28) {
            }
            try {
                b[g.markerHeight.ordinal()] = 29;
            } catch (NoSuchFieldError e29) {
            }
            try {
                b[g.markerUnits.ordinal()] = 30;
            } catch (NoSuchFieldError e30) {
            }
            try {
                b[g.orient.ordinal()] = 31;
            } catch (NoSuchFieldError e31) {
            }
            try {
                b[g.gradientUnits.ordinal()] = 32;
            } catch (NoSuchFieldError e32) {
            }
            try {
                b[g.gradientTransform.ordinal()] = 33;
            } catch (NoSuchFieldError e33) {
            }
            try {
                b[g.spreadMethod.ordinal()] = 34;
            } catch (NoSuchFieldError e34) {
            }
            try {
                b[g.fx.ordinal()] = 35;
            } catch (NoSuchFieldError e35) {
            }
            try {
                b[g.fy.ordinal()] = 36;
            } catch (NoSuchFieldError e36) {
            }
            try {
                b[g.offset.ordinal()] = 37;
            } catch (NoSuchFieldError e37) {
            }
            try {
                b[g.clipPathUnits.ordinal()] = 38;
            } catch (NoSuchFieldError e38) {
            }
            try {
                b[g.startOffset.ordinal()] = 39;
            } catch (NoSuchFieldError e39) {
            }
            try {
                b[g.patternUnits.ordinal()] = 40;
            } catch (NoSuchFieldError e40) {
            }
            try {
                b[g.patternContentUnits.ordinal()] = 41;
            } catch (NoSuchFieldError e41) {
            }
            try {
                b[g.patternTransform.ordinal()] = 42;
            } catch (NoSuchFieldError e42) {
            }
            try {
                b[g.maskUnits.ordinal()] = 43;
            } catch (NoSuchFieldError e43) {
            }
            try {
                b[g.maskContentUnits.ordinal()] = 44;
            } catch (NoSuchFieldError e44) {
            }
            try {
                b[g.style.ordinal()] = 45;
            } catch (NoSuchFieldError e45) {
            }
            try {
                b[g.CLASS.ordinal()] = 46;
            } catch (NoSuchFieldError e46) {
            }
            try {
                b[g.fill.ordinal()] = 47;
            } catch (NoSuchFieldError e47) {
            }
            try {
                b[g.fill_rule.ordinal()] = 48;
            } catch (NoSuchFieldError e48) {
            }
            try {
                b[g.fill_opacity.ordinal()] = 49;
            } catch (NoSuchFieldError e49) {
            }
            try {
                b[g.stroke.ordinal()] = 50;
            } catch (NoSuchFieldError e50) {
            }
            try {
                b[g.stroke_opacity.ordinal()] = 51;
            } catch (NoSuchFieldError e51) {
            }
            try {
                b[g.stroke_width.ordinal()] = 52;
            } catch (NoSuchFieldError e52) {
            }
            try {
                b[g.stroke_linecap.ordinal()] = 53;
            } catch (NoSuchFieldError e53) {
            }
            try {
                b[g.stroke_linejoin.ordinal()] = 54;
            } catch (NoSuchFieldError e54) {
            }
            try {
                b[g.stroke_miterlimit.ordinal()] = 55;
            } catch (NoSuchFieldError e55) {
            }
            try {
                b[g.stroke_dasharray.ordinal()] = 56;
            } catch (NoSuchFieldError e56) {
            }
            try {
                b[g.stroke_dashoffset.ordinal()] = 57;
            } catch (NoSuchFieldError e57) {
            }
            try {
                b[g.opacity.ordinal()] = 58;
            } catch (NoSuchFieldError e58) {
            }
            try {
                b[g.color.ordinal()] = 59;
            } catch (NoSuchFieldError e59) {
            }
            try {
                b[g.font.ordinal()] = 60;
            } catch (NoSuchFieldError e60) {
            }
            try {
                b[g.font_family.ordinal()] = 61;
            } catch (NoSuchFieldError e61) {
            }
            try {
                b[g.font_size.ordinal()] = 62;
            } catch (NoSuchFieldError e62) {
            }
            try {
                b[g.font_weight.ordinal()] = 63;
            } catch (NoSuchFieldError e63) {
            }
            try {
                b[g.font_style.ordinal()] = 64;
            } catch (NoSuchFieldError e64) {
            }
            try {
                b[g.text_decoration.ordinal()] = 65;
            } catch (NoSuchFieldError e65) {
            }
            try {
                b[g.direction.ordinal()] = 66;
            } catch (NoSuchFieldError e66) {
            }
            try {
                b[g.text_anchor.ordinal()] = 67;
            } catch (NoSuchFieldError e67) {
            }
            try {
                b[g.overflow.ordinal()] = 68;
            } catch (NoSuchFieldError e68) {
            }
            try {
                b[g.marker.ordinal()] = 69;
            } catch (NoSuchFieldError e69) {
            }
            try {
                b[g.marker_start.ordinal()] = 70;
            } catch (NoSuchFieldError e70) {
            }
            try {
                b[g.marker_mid.ordinal()] = 71;
            } catch (NoSuchFieldError e71) {
            }
            try {
                b[g.marker_end.ordinal()] = 72;
            } catch (NoSuchFieldError e72) {
            }
            try {
                b[g.display.ordinal()] = 73;
            } catch (NoSuchFieldError e73) {
            }
            try {
                b[g.visibility.ordinal()] = 74;
            } catch (NoSuchFieldError e74) {
            }
            try {
                b[g.stop_color.ordinal()] = 75;
            } catch (NoSuchFieldError e75) {
            }
            try {
                b[g.stop_opacity.ordinal()] = 76;
            } catch (NoSuchFieldError e76) {
            }
            try {
                b[g.clip.ordinal()] = 77;
            } catch (NoSuchFieldError e77) {
            }
            try {
                b[g.clip_path.ordinal()] = 78;
            } catch (NoSuchFieldError e78) {
            }
            try {
                b[g.clip_rule.ordinal()] = 79;
            } catch (NoSuchFieldError e79) {
            }
            try {
                b[g.mask.ordinal()] = 80;
            } catch (NoSuchFieldError e80) {
            }
            try {
                b[g.solid_color.ordinal()] = 81;
            } catch (NoSuchFieldError e81) {
            }
            try {
                b[g.solid_opacity.ordinal()] = 82;
            } catch (NoSuchFieldError e82) {
            }
            try {
                b[g.viewport_fill.ordinal()] = 83;
            } catch (NoSuchFieldError e83) {
            }
            try {
                b[g.viewport_fill_opacity.ordinal()] = 84;
            } catch (NoSuchFieldError e84) {
            }
            try {
                b[g.vector_effect.ordinal()] = 85;
            } catch (NoSuchFieldError e85) {
            }
            try {
                b[g.image_rendering.ordinal()] = 86;
            } catch (NoSuchFieldError e86) {
            }
            try {
                b[g.viewBox.ordinal()] = 87;
            } catch (NoSuchFieldError e87) {
            }
            try {
                b[g.type.ordinal()] = 88;
            } catch (NoSuchFieldError e88) {
            }
            try {
                b[g.media.ordinal()] = 89;
            } catch (NoSuchFieldError e89) {
            }
            int[] iArr2 = new int[h.values().length];
            a = iArr2;
            try {
                iArr2[h.svg.ordinal()] = 1;
            } catch (NoSuchFieldError e90) {
            }
            try {
                a[h.g.ordinal()] = 2;
            } catch (NoSuchFieldError e91) {
            }
            try {
                a[h.a.ordinal()] = 3;
            } catch (NoSuchFieldError e92) {
            }
            try {
                a[h.defs.ordinal()] = 4;
            } catch (NoSuchFieldError e93) {
            }
            try {
                a[h.use.ordinal()] = 5;
            } catch (NoSuchFieldError e94) {
            }
            try {
                a[h.path.ordinal()] = 6;
            } catch (NoSuchFieldError e95) {
            }
            try {
                a[h.rect.ordinal()] = 7;
            } catch (NoSuchFieldError e96) {
            }
            try {
                a[h.circle.ordinal()] = 8;
            } catch (NoSuchFieldError e97) {
            }
            try {
                a[h.ellipse.ordinal()] = 9;
            } catch (NoSuchFieldError e98) {
            }
            try {
                a[h.line.ordinal()] = 10;
            } catch (NoSuchFieldError e99) {
            }
            try {
                a[h.polyline.ordinal()] = 11;
            } catch (NoSuchFieldError e100) {
            }
            try {
                a[h.polygon.ordinal()] = 12;
            } catch (NoSuchFieldError e101) {
            }
            try {
                a[h.text.ordinal()] = 13;
            } catch (NoSuchFieldError e102) {
            }
            try {
                a[h.tspan.ordinal()] = 14;
            } catch (NoSuchFieldError e103) {
            }
            try {
                a[h.tref.ordinal()] = 15;
            } catch (NoSuchFieldError e104) {
            }
            try {
                a[h.SWITCH.ordinal()] = 16;
            } catch (NoSuchFieldError e105) {
            }
            try {
                a[h.symbol.ordinal()] = 17;
            } catch (NoSuchFieldError e106) {
            }
            try {
                a[h.marker.ordinal()] = 18;
            } catch (NoSuchFieldError e107) {
            }
            try {
                a[h.linearGradient.ordinal()] = 19;
            } catch (NoSuchFieldError e108) {
            }
            try {
                a[h.radialGradient.ordinal()] = 20;
            } catch (NoSuchFieldError e109) {
            }
            try {
                a[h.stop.ordinal()] = 21;
            } catch (NoSuchFieldError e110) {
            }
            try {
                a[h.title.ordinal()] = 22;
            } catch (NoSuchFieldError e111) {
            }
            try {
                a[h.desc.ordinal()] = 23;
            } catch (NoSuchFieldError e112) {
            }
            try {
                a[h.clipPath.ordinal()] = 24;
            } catch (NoSuchFieldError e113) {
            }
            try {
                a[h.textPath.ordinal()] = 25;
            } catch (NoSuchFieldError e114) {
            }
            try {
                a[h.pattern.ordinal()] = 26;
            } catch (NoSuchFieldError e115) {
            }
            try {
                a[h.image.ordinal()] = 27;
            } catch (NoSuchFieldError e116) {
            }
            try {
                a[h.view.ordinal()] = 28;
            } catch (NoSuchFieldError e117) {
            }
            try {
                a[h.mask.ordinal()] = 29;
            } catch (NoSuchFieldError e118) {
            }
            try {
                a[h.style.ordinal()] = 30;
            } catch (NoSuchFieldError e119) {
            }
            try {
                a[h.solidColor.ordinal()] = 31;
            } catch (NoSuchFieldError e120) {
            }
        }
    }

    private void q(Attributes attributes) {
        l("<g>", new Object[0]);
        if (this.b != null) {
            g.m obj = new g.m();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            W(obj, attributes);
            C(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void m(Attributes attributes) {
        l("<defs>", new Object[0]);
        if (this.b != null) {
            g.h obj = new g.h();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            W(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void i1(Attributes attributes) {
        l("<use>", new Object[0]);
        if (this.b != null) {
            g.e1 obj = new g.e1();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            W(obj, attributes);
            C(obj, attributes);
            X(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void X(g.e1 obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 1:
                    obj.q = o0(val);
                    break;
                case 2:
                    obj.r = o0(val);
                    break;
                case 3:
                    g.p o0 = o0(val);
                    obj.s = o0;
                    if (!o0.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <use> element. width cannot be negative");
                    }
                case 4:
                    g.p o02 = o0(val);
                    obj.t = o02;
                    if (!o02.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <use> element. height cannot be negative");
                    }
                case 6:
                    if (!"".equals(attributes.getURI(i2)) && !"http://www.w3.org/1999/xlink".equals(attributes.getURI(i2))) {
                        break;
                    } else {
                        obj.p = val;
                        break;
                    }
            }
        }
    }

    private void u(Attributes attributes) {
        l("<image>", new Object[0]);
        if (this.b != null) {
            g.o obj = new g.o();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            W(obj, attributes);
            C(obj, attributes);
            G(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void G(g.o obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 1:
                    obj.q = o0(val);
                    break;
                case 2:
                    obj.r = o0(val);
                    break;
                case 3:
                    g.p o0 = o0(val);
                    obj.s = o0;
                    if (!o0.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <use> element. width cannot be negative");
                    }
                case 4:
                    g.p o02 = o0(val);
                    obj.t = o02;
                    if (!o02.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <use> element. height cannot be negative");
                    }
                case 6:
                    if (!"".equals(attributes.getURI(i2)) && !"http://www.w3.org/1999/xlink".equals(attributes.getURI(i2))) {
                        break;
                    } else {
                        obj.p = val;
                        break;
                    }
                    break;
                case 7:
                    w0(obj, val);
                    break;
            }
        }
    }

    private void O0(Attributes attributes) {
        l("<path>", new Object[0]);
        if (this.b != null) {
            g.v obj = new g.v();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            W(obj, attributes);
            C(obj, attributes);
            L(obj, attributes);
            this.b.g(obj);
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void L(g.v obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 8:
                    obj.o = u0(val);
                    break;
                case 9:
                    Float valueOf = Float.valueOf(f0(val));
                    obj.p = valueOf;
                    if (valueOf.floatValue() >= 0.0f) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <path> element. pathLength cannot be negative");
                    }
            }
        }
    }

    private void U0(Attributes attributes) {
        l("<rect>", new Object[0]);
        if (this.b != null) {
            g.b0 obj = new g.b0();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            W(obj, attributes);
            C(obj, attributes);
            P(obj, attributes);
            this.b.g(obj);
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void P(g.b0 obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 1:
                    obj.o = o0(val);
                    break;
                case 2:
                    obj.p = o0(val);
                    break;
                case 3:
                    g.p o0 = o0(val);
                    obj.q = o0;
                    if (!o0.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <rect> element. width cannot be negative");
                    }
                case 4:
                    g.p o02 = o0(val);
                    obj.r = o02;
                    if (!o02.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <rect> element. height cannot be negative");
                    }
                case 10:
                    g.p o03 = o0(val);
                    obj.s = o03;
                    if (!o03.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <rect> element. rx cannot be negative");
                    }
                case 11:
                    g.p o04 = o0(val);
                    obj.t = o04;
                    if (!o04.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <rect> element. ry cannot be negative");
                    }
            }
        }
    }

    private void i(Attributes attributes) {
        l("<circle>", new Object[0]);
        if (this.b != null) {
            g.d obj = new g.d();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            W(obj, attributes);
            C(obj, attributes);
            A(obj, attributes);
            this.b.g(obj);
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void A(g.d obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 12:
                    obj.o = o0(val);
                    break;
                case 13:
                    obj.p = o0(val);
                    break;
                case 14:
                    g.p o0 = o0(val);
                    obj.q = o0;
                    if (!o0.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <circle> element. r cannot be negative");
                    }
            }
        }
    }

    private void n(Attributes attributes) {
        l("<ellipse>", new Object[0]);
        if (this.b != null) {
            g.i obj = new g.i();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            W(obj, attributes);
            C(obj, attributes);
            E(obj, attributes);
            this.b.g(obj);
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void E(g.i obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 10:
                    g.p o0 = o0(val);
                    obj.q = o0;
                    if (!o0.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <ellipse> element. rx cannot be negative");
                    }
                case 11:
                    g.p o02 = o0(val);
                    obj.r = o02;
                    if (!o02.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <ellipse> element. ry cannot be negative");
                    }
                case 12:
                    obj.o = o0(val);
                    break;
                case 13:
                    obj.p = o0(val);
                    break;
            }
        }
    }

    private void v(Attributes attributes) {
        l("<line>", new Object[0]);
        if (this.b != null) {
            g.q obj = new g.q();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            W(obj, attributes);
            C(obj, attributes);
            H(obj, attributes);
            this.b.g(obj);
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void H(g.q obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 15:
                    obj.o = o0(val);
                    break;
                case 16:
                    obj.p = o0(val);
                    break;
                case 17:
                    obj.q = o0(val);
                    break;
                case 18:
                    obj.r = o0(val);
                    break;
            }
        }
    }

    private void R0(Attributes attributes) {
        l("<polyline>", new Object[0]);
        if (this.b != null) {
            g.z obj = new g.z();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            W(obj, attributes);
            C(obj, attributes);
            N(obj, attributes, "polyline");
            this.b.g(obj);
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void N(g.z obj, Attributes attributes, String tag) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            if (g.fromString(attributes.getLocalName(i2)) == g.points) {
                i scan = new i(attributes.getValue(i2));
                List<Float> points = new ArrayList<>();
                scan.A();
                while (!scan.h()) {
                    float x = scan.n();
                    if (!Float.isNaN(x)) {
                        scan.z();
                        float y = scan.n();
                        if (!Float.isNaN(y)) {
                            scan.z();
                            points.add(Float.valueOf(x));
                            points.add(Float.valueOf(y));
                        } else {
                            throw new SVGParseException("Invalid <" + tag + "> points attribute. There should be an even number of coordinates.");
                        }
                    } else {
                        throw new SVGParseException("Invalid <" + tag + "> points attribute. Non-coordinate content found in list.");
                    }
                }
                obj.o = new float[points.size()];
                int j = 0;
                for (Float floatValue : points) {
                    obj.o[j] = floatValue.floatValue();
                    j++;
                }
            }
        }
    }

    private void Q0(Attributes attributes) {
        l("<polygon>", new Object[0]);
        if (this.b != null) {
            g.a0 obj = new g.a0();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            W(obj, attributes);
            C(obj, attributes);
            N(obj, attributes, "polygon");
            this.b.g(obj);
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void d1(Attributes attributes) {
        l("<text>", new Object[0]);
        if (this.b != null) {
            g.w0 obj = new g.w0();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            W(obj, attributes);
            C(obj, attributes);
            V(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void V(g.a1 obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 1:
                    obj.o = p0(val);
                    break;
                case 2:
                    obj.p = p0(val);
                    break;
                case 19:
                    obj.q = p0(val);
                    break;
                case 20:
                    obj.r = p0(val);
                    break;
            }
        }
    }

    private void h1(Attributes attributes) {
        l("<tspan>", new Object[0]);
        g.j0 j0Var = this.b;
        if (j0Var == null) {
            throw new SVGParseException("Invalid document. Root element must be <svg>");
        } else if (j0Var instanceof g.y0) {
            g.v0 obj = new g.v0();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            C(obj, attributes);
            V(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            g.j0 j0Var2 = obj.b;
            if (j0Var2 instanceof g.b1) {
                obj.n((g.b1) j0Var2);
            } else {
                obj.n(((g.x0) j0Var2).d());
            }
        } else {
            throw new SVGParseException("Invalid document. <tspan> elements are only valid inside <text> or other <tspan> elements.");
        }
    }

    private void g1(Attributes attributes) {
        l("<tref>", new Object[0]);
        g.j0 j0Var = this.b;
        if (j0Var == null) {
            throw new SVGParseException("Invalid document. Root element must be <svg>");
        } else if (j0Var instanceof g.y0) {
            g.u0 obj = new g.u0();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            C(obj, attributes);
            T(obj, attributes);
            this.b.g(obj);
            g.j0 j0Var2 = obj.b;
            if (j0Var2 instanceof g.b1) {
                obj.n((g.b1) j0Var2);
            } else {
                obj.n(((g.x0) j0Var2).d());
            }
        } else {
            throw new SVGParseException("Invalid document. <tref> elements are only valid inside <text> or <tspan> elements.");
        }
    }

    private void T(g.u0 obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 6:
                    if (!"".equals(attributes.getURI(i2)) && !"http://www.w3.org/1999/xlink".equals(attributes.getURI(i2))) {
                        break;
                    } else {
                        obj.o = val;
                        break;
                    }
                    break;
            }
        }
    }

    private void k1(Attributes attributes) {
        l("<switch>", new Object[0]);
        if (this.b != null) {
            g.s0 obj = new g.s0();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            W(obj, attributes);
            C(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void C(g.g0 obj, Attributes attributes) {
        Set<String> fontSet;
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 21:
                    obj.e(z0(val));
                    break;
                case 22:
                    obj.i(val);
                    break;
                case 23:
                    obj.f(F0(val));
                    break;
                case 24:
                    obj.h(A0(val));
                    break;
                case 25:
                    List<String> fonts = i0(val);
                    if (fonts == null) {
                        fontSet = new HashSet<>(0);
                    }
                    obj.c(fontSet);
                    break;
            }
        }
    }

    private void b1(Attributes attributes) {
        l("<symbol>", new Object[0]);
        if (this.b != null) {
            g.t0 obj = new g.t0();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            C(obj, attributes);
            Y(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void x(Attributes attributes) {
        l("<marker>", new Object[0]);
        if (this.b != null) {
            g.r obj = new g.r();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            C(obj, attributes);
            Y(obj, attributes);
            J(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void J(g.r obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 26:
                    obj.r = o0(val);
                    break;
                case 27:
                    obj.s = o0(val);
                    break;
                case 28:
                    g.p o0 = o0(val);
                    obj.t = o0;
                    if (!o0.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <marker> element. markerWidth cannot be negative");
                    }
                case 29:
                    g.p o02 = o0(val);
                    obj.u = o02;
                    if (!o02.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <marker> element. markerHeight cannot be negative");
                    }
                case 30:
                    if ("strokeWidth".equals(val)) {
                        obj.q = false;
                        break;
                    } else if ("userSpaceOnUse".equals(val)) {
                        obj.q = true;
                        break;
                    } else {
                        throw new SVGParseException("Invalid value for attribute markerUnits");
                    }
                case 31:
                    if (!"auto".equals(val)) {
                        obj.v = Float.valueOf(f0(val));
                        break;
                    } else {
                        obj.v = Float.valueOf(Float.NaN);
                        break;
                    }
            }
        }
    }

    private void w(Attributes attributes) {
        l("<linearGradient>", new Object[0]);
        if (this.b != null) {
            g.m0 obj = new g.m0();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            F(obj, attributes);
            I(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void F(g.j obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 6:
                    if (!"".equals(attributes.getURI(i2)) && !"http://www.w3.org/1999/xlink".equals(attributes.getURI(i2))) {
                        break;
                    } else {
                        obj.l = val;
                        break;
                    }
                    break;
                case 32:
                    if ("objectBoundingBox".equals(val)) {
                        obj.i = false;
                        break;
                    } else if ("userSpaceOnUse".equals(val)) {
                        obj.i = true;
                        break;
                    } else {
                        throw new SVGParseException("Invalid value for attribute gradientUnits");
                    }
                case 33:
                    obj.j = J0(val);
                    break;
                case 34:
                    try {
                        obj.k = g.k.valueOf(val);
                        break;
                    } catch (IllegalArgumentException e2) {
                        throw new SVGParseException("Invalid spreadMethod attribute. \"" + val + "\" is not a valid value.");
                    }
            }
        }
    }

    private void I(g.m0 obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 15:
                    obj.m = o0(val);
                    break;
                case 16:
                    obj.n = o0(val);
                    break;
                case 17:
                    obj.o = o0(val);
                    break;
                case 18:
                    obj.p = o0(val);
                    break;
            }
        }
    }

    private void T0(Attributes attributes) {
        l("<radialGradient>", new Object[0]);
        if (this.b != null) {
            g.q0 obj = new g.q0();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            F(obj, attributes);
            O(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void O(g.q0 obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 12:
                    obj.m = o0(val);
                    break;
                case 13:
                    obj.n = o0(val);
                    break;
                case 14:
                    g.p o0 = o0(val);
                    obj.o = o0;
                    if (!o0.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <radialGradient> element. r cannot be negative");
                    }
                case 35:
                    obj.p = o0(val);
                    break;
                case 36:
                    obj.q = o0(val);
                    break;
            }
        }
    }

    private void Y0(Attributes attributes) {
        l("<stop>", new Object[0]);
        g.j0 j0Var = this.b;
        if (j0Var == null) {
            throw new SVGParseException("Invalid document. Root element must be <svg>");
        } else if (j0Var instanceof g.j) {
            g.d0 obj = new g.d0();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            R(obj, attributes);
            this.b.g(obj);
            this.b = obj;
        } else {
            throw new SVGParseException("Invalid document. <stop> elements are only valid inside <linearGradient> or <radialGradient> elements.");
        }
    }

    private void R(g.d0 obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 37:
                    obj.h = n0(val);
                    break;
            }
        }
    }

    private Float n0(String val) {
        if (val.length() != 0) {
            int end = val.length();
            boolean isPercent = false;
            if (val.charAt(val.length() - 1) == '%') {
                end--;
                isPercent = true;
            }
            try {
                float scalar = g0(val, 0, end);
                float f2 = 100.0f;
                if (isPercent) {
                    scalar /= 100.0f;
                }
                if (scalar < 0.0f) {
                    f2 = 0.0f;
                } else if (scalar <= 100.0f) {
                    f2 = scalar;
                }
                return Float.valueOf(f2);
            } catch (NumberFormatException e2) {
                throw new SVGParseException("Invalid offset value in <stop>: " + val, e2);
            }
        } else {
            throw new SVGParseException("Invalid offset value in <stop> (empty string)");
        }
    }

    private void V0(Attributes attributes) {
        l("<solidColor>", new Object[0]);
        if (this.b != null) {
            g.c0 obj = new g.c0();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void k(Attributes attributes) {
        l("<clipPath>", new Object[0]);
        if (this.b != null) {
            g.e obj = new g.e();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            W(obj, attributes);
            C(obj, attributes);
            B(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void B(g.e obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 38:
                    if ("objectBoundingBox".equals(val)) {
                        obj.p = false;
                        break;
                    } else if ("userSpaceOnUse".equals(val)) {
                        obj.p = true;
                        break;
                    } else {
                        throw new SVGParseException("Invalid value for attribute clipPathUnits");
                    }
            }
        }
    }

    private void f1(Attributes attributes) {
        l("<textPath>", new Object[0]);
        if (this.b != null) {
            g.z0 obj = new g.z0();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            C(obj, attributes);
            U(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            g.j0 j0Var = obj.b;
            if (j0Var instanceof g.b1) {
                obj.n((g.b1) j0Var);
            } else {
                obj.n(((g.x0) j0Var).d());
            }
        } else {
            throw new SVGParseException("Invalid document. Root element must be <svg>");
        }
    }

    private void U(g.z0 obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 6:
                    if (!"".equals(attributes.getURI(i2)) && !"http://www.w3.org/1999/xlink".equals(attributes.getURI(i2))) {
                        break;
                    } else {
                        obj.o = val;
                        break;
                    }
                    break;
                case 39:
                    obj.p = o0(val);
                    break;
            }
        }
    }

    private void P0(Attributes attributes) {
        l("<pattern>", new Object[0]);
        if (this.b != null) {
            g.y obj = new g.y();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            C(obj, attributes);
            Y(obj, attributes);
            M(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void M(g.y obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 1:
                    obj.t = o0(val);
                    break;
                case 2:
                    obj.u = o0(val);
                    break;
                case 3:
                    g.p o0 = o0(val);
                    obj.v = o0;
                    if (!o0.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <pattern> element. width cannot be negative");
                    }
                case 4:
                    g.p o02 = o0(val);
                    obj.w = o02;
                    if (!o02.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <pattern> element. height cannot be negative");
                    }
                case 6:
                    if (!"".equals(attributes.getURI(i2)) && !"http://www.w3.org/1999/xlink".equals(attributes.getURI(i2))) {
                        break;
                    } else {
                        obj.x = val;
                        break;
                    }
                    break;
                case 40:
                    if ("objectBoundingBox".equals(val)) {
                        obj.q = false;
                        break;
                    } else if ("userSpaceOnUse".equals(val)) {
                        obj.q = true;
                        break;
                    } else {
                        throw new SVGParseException("Invalid value for attribute patternUnits");
                    }
                case 41:
                    if ("objectBoundingBox".equals(val)) {
                        obj.r = false;
                        break;
                    } else if ("userSpaceOnUse".equals(val)) {
                        obj.r = true;
                        break;
                    } else {
                        throw new SVGParseException("Invalid value for attribute patternContentUnits");
                    }
                case 42:
                    obj.s = J0(val);
                    break;
            }
        }
    }

    private void j1(Attributes attributes) {
        l("<view>", new Object[0]);
        if (this.b != null) {
            g.f1 obj = new g.f1();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            C(obj, attributes);
            Y(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void y(Attributes attributes) {
        l("<mask>", new Object[0]);
        if (this.b != null) {
            g.s obj = new g.s();
            obj.a = this.a;
            obj.b = this.b;
            D(obj, attributes);
            S(obj, attributes);
            C(obj, attributes);
            K(obj, attributes);
            this.b.g(obj);
            this.b = obj;
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void K(g.s obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 1:
                    obj.q = o0(val);
                    break;
                case 2:
                    obj.r = o0(val);
                    break;
                case 3:
                    g.p o0 = o0(val);
                    obj.s = o0;
                    if (!o0.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <mask> element. width cannot be negative");
                    }
                case 4:
                    g.p o02 = o0(val);
                    obj.t = o02;
                    if (!o02.h()) {
                        break;
                    } else {
                        throw new SVGParseException("Invalid <mask> element. height cannot be negative");
                    }
                case 43:
                    if ("objectBoundingBox".equals(val)) {
                        obj.o = false;
                        break;
                    } else if ("userSpaceOnUse".equals(val)) {
                        obj.o = true;
                        break;
                    } else {
                        throw new SVGParseException("Invalid value for attribute maskUnits");
                    }
                case 44:
                    if ("objectBoundingBox".equals(val)) {
                        obj.p = false;
                        break;
                    } else if ("userSpaceOnUse".equals(val)) {
                        obj.p = true;
                        break;
                    } else {
                        throw new SVGParseException("Invalid value for attribute maskContentUnits");
                    }
            }
        }
    }

    /* compiled from: SVGParser */
    public static class i {
        String a;
        int b = 0;
        int c = 0;
        private d d = new d();

        i(String input) {
            String trim = input.trim();
            this.a = trim;
            this.c = trim.length();
        }

        /* access modifiers changed from: package-private */
        public boolean h() {
            return this.b == this.c;
        }

        /* access modifiers changed from: package-private */
        public boolean k(int c2) {
            return c2 == 32 || c2 == 10 || c2 == 13 || c2 == 9;
        }

        /* access modifiers changed from: package-private */
        public void A() {
            while (true) {
                int i = this.b;
                if (i < this.c && k(this.a.charAt(i))) {
                    this.b++;
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public boolean j(int c2) {
            return c2 == 10 || c2 == 13;
        }

        /* access modifiers changed from: package-private */
        public boolean z() {
            A();
            int i = this.b;
            if (i == this.c || this.a.charAt(i) != ',') {
                return false;
            }
            this.b++;
            A();
            return true;
        }

        /* access modifiers changed from: package-private */
        public float n() {
            float val = this.d.b(this.a, this.b, this.c);
            if (!Float.isNaN(val)) {
                this.b = this.d.a();
            }
            return val;
        }

        /* access modifiers changed from: package-private */
        public float x() {
            z();
            float val = this.d.b(this.a, this.b, this.c);
            if (!Float.isNaN(val)) {
                this.b = this.d.a();
            }
            return val;
        }

        /* access modifiers changed from: package-private */
        public float d(float lastRead) {
            if (Float.isNaN(lastRead)) {
                return Float.NaN;
            }
            z();
            return n();
        }

        /* access modifiers changed from: package-private */
        public float e(Boolean lastRead) {
            if (lastRead == null) {
                return Float.NaN;
            }
            z();
            return n();
        }

        /* access modifiers changed from: package-private */
        public Integer l() {
            int i = this.b;
            if (i == this.c) {
                return null;
            }
            String str = this.a;
            this.b = i + 1;
            return Integer.valueOf(str.charAt(i));
        }

        /* access modifiers changed from: package-private */
        public g.p p() {
            float scalar = n();
            if (Float.isNaN(scalar)) {
                return null;
            }
            g.d1 unit = v();
            if (unit == null) {
                return new g.p(scalar, g.d1.px);
            }
            return new g.p(scalar, unit);
        }

        /* access modifiers changed from: package-private */
        public Boolean m() {
            int i = this.b;
            if (i == this.c) {
                return null;
            }
            char ch = this.a.charAt(i);
            if (ch != '0' && ch != '1') {
                return null;
            }
            boolean z = true;
            this.b++;
            if (ch != '1') {
                z = false;
            }
            return Boolean.valueOf(z);
        }

        /* access modifiers changed from: package-private */
        public Boolean c(Object lastRead) {
            if (lastRead == null) {
                return null;
            }
            z();
            return m();
        }

        /* access modifiers changed from: package-private */
        public boolean f(char ch) {
            int i = this.b;
            boolean found = i < this.c && this.a.charAt(i) == ch;
            if (found) {
                this.b++;
            }
            return found;
        }

        /* access modifiers changed from: package-private */
        public boolean g(String str) {
            int len = str.length();
            int i = this.b;
            boolean found = i <= this.c - len && this.a.substring(i, i + len).equals(str);
            if (found) {
                this.b += len;
            }
            return found;
        }

        /* access modifiers changed from: package-private */
        public int a() {
            int i = this.b;
            int i2 = this.c;
            if (i == i2) {
                return -1;
            }
            int i3 = i + 1;
            this.b = i3;
            if (i3 < i2) {
                return this.a.charAt(i3);
            }
            return -1;
        }

        /* access modifiers changed from: package-private */
        public String r() {
            return t(' ', false);
        }

        /* access modifiers changed from: package-private */
        public String s(char terminator) {
            return t(terminator, false);
        }

        /* access modifiers changed from: package-private */
        public String u(char terminator) {
            return t(terminator, true);
        }

        /* access modifiers changed from: package-private */
        public String t(char terminator, boolean allowWhitespace) {
            if (h()) {
                return null;
            }
            int ch = this.a.charAt(this.b);
            if ((!allowWhitespace && k(ch)) || ch == terminator) {
                return null;
            }
            int start = this.b;
            int ch2 = a();
            while (ch2 != -1 && ch2 != terminator && (allowWhitespace || !k(ch2))) {
                ch2 = a();
            }
            return this.a.substring(start, this.b);
        }

        /* access modifiers changed from: package-private */
        public String w() {
            if (h()) {
                return null;
            }
            int start = this.b;
            int ch = this.a.charAt(this.b);
            if ((ch < 65 || ch > 90) && (ch < 97 || ch > 122)) {
                this.b = start;
                return null;
            }
            int ch2 = a();
            while (true) {
                if ((ch2 >= 65 && ch2 <= 90) || (ch2 >= 97 && ch2 <= 122)) {
                    ch2 = a();
                }
            }
            return this.a.substring(start, this.b);
        }

        /* access modifiers changed from: package-private */
        public String o() {
            if (h()) {
                return null;
            }
            int start = this.b;
            int ch = this.a.charAt(this.b);
            while (true) {
                if ((ch < 97 || ch > 122) && (ch < 65 || ch > 90)) {
                    int end = this.b;
                } else {
                    ch = a();
                }
            }
            int end2 = this.b;
            while (k(ch)) {
                ch = a();
            }
            if (ch == 40) {
                this.b++;
                return this.a.substring(start, end2);
            }
            this.b = start;
            return null;
        }

        /* access modifiers changed from: package-private */
        public String b() {
            int start = this.b;
            while (!h() && !k(this.a.charAt(this.b))) {
                this.b++;
            }
            String str = this.a.substring(start, this.b);
            this.b = start;
            return str;
        }

        /* access modifiers changed from: package-private */
        public g.d1 v() {
            if (h()) {
                return null;
            }
            if (this.a.charAt(this.b) == 37) {
                this.b++;
                return g.d1.percent;
            }
            int i = this.b;
            if (i > this.c - 2) {
                return null;
            }
            try {
                g.d1 result = g.d1.valueOf(this.a.substring(i, i + 2).toLowerCase(Locale.US));
                this.b += 2;
                return result;
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean i() {
            int i = this.b;
            if (i == this.c) {
                return false;
            }
            char ch = this.a.charAt(i);
            if ((ch < 'a' || ch > 'z') && (ch < 'A' || ch > 'Z')) {
                return false;
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public String q() {
            if (h()) {
                return null;
            }
            int start = this.b;
            int charAt = this.a.charAt(this.b);
            int endQuote = charAt;
            if (charAt != '\'' && charAt != '\"') {
                return null;
            }
            int ch = a();
            while (ch != -1 && ch != endQuote) {
                ch = a();
            }
            if (ch == -1) {
                this.b = start;
                return null;
            }
            int i = this.b + 1;
            this.b = i;
            return this.a.substring(start + 1, i - 1);
        }

        /* access modifiers changed from: package-private */
        public String y() {
            if (h()) {
                return null;
            }
            int start = this.b;
            this.b = this.c;
            return this.a.substring(start);
        }
    }

    private void D(g.l0 obj, Attributes attributes) {
        int i2 = 0;
        while (i2 < attributes.getLength()) {
            String qname = attributes.getQName(i2);
            if (qname.equals("id") || qname.equals("xml:id")) {
                obj.c = attributes.getValue(i2).trim();
                return;
            } else if (qname.equals("xml:space")) {
                String val = attributes.getValue(i2).trim();
                if ("default".equals(val)) {
                    obj.d = Boolean.FALSE;
                    return;
                } else if ("preserve".equals(val)) {
                    obj.d = Boolean.TRUE;
                    return;
                } else {
                    throw new SVGParseException("Invalid value for \"xml:space\" attribute: " + val);
                }
            } else {
                i2++;
            }
        }
    }

    private void S(g.l0 obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            if (val.length() != 0) {
                switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                    case 45:
                        E0(obj, val);
                        break;
                    case 46:
                        obj.g = a.f(val);
                        break;
                    default:
                        if (obj.e == null) {
                            obj.e = new g.e0();
                        }
                        S0(obj.e, attributes.getLocalName(i2), attributes.getValue(i2).trim());
                        break;
                }
            }
        }
    }

    private static void E0(g.l0 obj, String style) {
        i scan = new i(style.replaceAll("/\\*.*?\\*/", ""));
        while (true) {
            String propertyName = scan.s(':');
            scan.A();
            if (scan.f(':')) {
                scan.A();
                String propertyValue = scan.u(';');
                if (propertyValue != null) {
                    scan.A();
                    if (scan.h() || scan.f(';')) {
                        if (obj.f == null) {
                            obj.f = new g.e0();
                        }
                        S0(obj.f, propertyName, propertyValue);
                        scan.A();
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    static void S0(g.e0 style, String localName, String val) {
        if (val.length() != 0 && !val.equals("inherit")) {
            switch (a.b[g.fromString(localName).ordinal()]) {
                case 47:
                    g.o0 t0 = t0(val);
                    style.d = t0;
                    if (t0 != null) {
                        style.c |= 1;
                        return;
                    }
                    return;
                case 48:
                    g.e0.a e0 = e0(val);
                    style.f = e0;
                    if (e0 != null) {
                        style.c |= 2;
                        return;
                    }
                    return;
                case 49:
                    Float r0 = r0(val);
                    style.q = r0;
                    if (r0 != null) {
                        style.c |= 4;
                        return;
                    }
                    return;
                case 50:
                    g.o0 t02 = t0(val);
                    style.x = t02;
                    if (t02 != null) {
                        style.c |= 8;
                        return;
                    }
                    return;
                case 51:
                    Float r02 = r0(val);
                    style.y = r02;
                    if (r02 != null) {
                        style.c |= 16;
                        return;
                    }
                    return;
                case 52:
                    try {
                        style.z = o0(val);
                        style.c |= 32;
                        return;
                    } catch (SVGParseException e2) {
                        return;
                    }
                case 53:
                    g.e0.c C0 = C0(val);
                    style.p0 = C0;
                    if (C0 != null) {
                        style.c |= 64;
                        return;
                    }
                    return;
                case 54:
                    g.e0.d D0 = D0(val);
                    style.a1 = D0;
                    if (D0 != null) {
                        style.c |= 128;
                        return;
                    }
                    return;
                case 55:
                    try {
                        style.p1 = Float.valueOf(f0(val));
                        style.c |= 256;
                        return;
                    } catch (SVGParseException e3) {
                        return;
                    }
                case 56:
                    if ("none".equals(val)) {
                        style.a2 = null;
                        style.c |= 512;
                        return;
                    }
                    g.p[] B0 = B0(val);
                    style.a2 = B0;
                    if (B0 != null) {
                        style.c |= 512;
                        return;
                    }
                    return;
                case 57:
                    try {
                        style.p2 = o0(val);
                        style.c |= 1024;
                        return;
                    } catch (SVGParseException e4) {
                        return;
                    }
                case 58:
                    style.p3 = r0(val);
                    style.c |= 2048;
                    return;
                case 59:
                    try {
                        style.p4 = b0(val);
                        style.c |= 4096;
                        return;
                    } catch (SVGParseException e5) {
                        return;
                    }
                case 60:
                    h0(style, val);
                    return;
                case 61:
                    List<String> i0 = i0(val);
                    style.z4 = i0;
                    if (i0 != null) {
                        style.c |= 8192;
                        return;
                    }
                    return;
                case 62:
                    g.p j0 = j0(val);
                    style.A4 = j0;
                    if (j0 != null) {
                        style.c |= 16384;
                        return;
                    }
                    return;
                case 63:
                    Integer l0 = l0(val);
                    style.B4 = l0;
                    if (l0 != null) {
                        style.c |= 32768;
                        return;
                    }
                    return;
                case 64:
                    g.e0.b k0 = k0(val);
                    style.C4 = k0;
                    if (k0 != null) {
                        style.c |= 65536;
                        return;
                    }
                    return;
                case 65:
                    g.e0.C0051g H0 = H0(val);
                    style.D4 = H0;
                    if (H0 != null) {
                        style.c |= 131072;
                        return;
                    }
                    return;
                case 66:
                    g.e0.h I0 = I0(val);
                    style.E4 = I0;
                    if (I0 != null) {
                        style.c |= 68719476736L;
                        return;
                    }
                    return;
                case 67:
                    g.e0.f G0 = G0(val);
                    style.F4 = G0;
                    if (G0 != null) {
                        style.c |= PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
                        return;
                    }
                    return;
                case 68:
                    Boolean s0 = s0(val);
                    style.G4 = s0;
                    if (s0 != null) {
                        style.c |= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED;
                        return;
                    }
                    return;
                case 69:
                    String m0 = m0(val, localName);
                    style.I4 = m0;
                    style.J4 = m0;
                    style.K4 = m0;
                    style.c |= 14680064;
                    return;
                case 70:
                    style.I4 = m0(val, localName);
                    style.c |= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE;
                    return;
                case 71:
                    style.J4 = m0(val, localName);
                    style.c |= 4194304;
                    return;
                case 72:
                    style.K4 = m0(val, localName);
                    style.c |= 8388608;
                    return;
                case 73:
                    if (val.indexOf(124) < 0) {
                        if ("|inline|block|list-item|run-in|compact|marker|table|inline-table|table-row-group|table-header-group|table-footer-group|table-row|table-column-group|table-column|table-cell|table-caption|none|".contains('|' + val + '|')) {
                            style.L4 = Boolean.valueOf(!val.equals("none"));
                            style.c |= 16777216;
                            return;
                        }
                        return;
                    }
                    return;
                case 74:
                    if (val.indexOf(124) < 0) {
                        if ("|visible|hidden|collapse|".contains('|' + val + '|')) {
                            style.M4 = Boolean.valueOf(val.equals("visible"));
                            style.c |= 33554432;
                            return;
                        }
                        return;
                    }
                    return;
                case 75:
                    if (val.equals("currentColor")) {
                        style.N4 = g.C0052g.a();
                    } else {
                        try {
                            style.N4 = b0(val);
                        } catch (SVGParseException e6) {
                            Log.w("SVGParser", e6.getMessage());
                            return;
                        }
                    }
                    style.c |= 67108864;
                    return;
                case 76:
                    style.O4 = r0(val);
                    style.c |= 134217728;
                    return;
                case 77:
                    g.c a0 = a0(val);
                    style.H4 = a0;
                    if (a0 != null) {
                        style.c |= 1048576;
                        return;
                    }
                    return;
                case 78:
                    style.P4 = m0(val, localName);
                    style.c |= 268435456;
                    return;
                case 79:
                    style.Q4 = e0(val);
                    style.c |= IjkMediaMeta.AV_CH_STEREO_LEFT;
                    return;
                case 80:
                    style.R4 = m0(val, localName);
                    style.c |= IjkMediaMeta.AV_CH_STEREO_RIGHT;
                    return;
                case 81:
                    if (val.equals("currentColor")) {
                        style.S4 = g.C0052g.a();
                    } else {
                        try {
                            style.S4 = b0(val);
                        } catch (SVGParseException e7) {
                            Log.w("SVGParser", e7.getMessage());
                            return;
                        }
                    }
                    style.c |= IjkMediaMeta.AV_CH_WIDE_LEFT;
                    return;
                case 82:
                    style.T4 = r0(val);
                    style.c |= IjkMediaMeta.AV_CH_WIDE_RIGHT;
                    return;
                case 83:
                    if (val.equals("currentColor")) {
                        style.U4 = g.C0052g.a();
                    } else {
                        try {
                            style.U4 = b0(val);
                        } catch (SVGParseException e8) {
                            Log.w("SVGParser", e8.getMessage());
                            return;
                        }
                    }
                    style.c |= IjkMediaMeta.AV_CH_SURROUND_DIRECT_LEFT;
                    return;
                case 84:
                    style.V4 = r0(val);
                    style.c |= IjkMediaMeta.AV_CH_SURROUND_DIRECT_RIGHT;
                    return;
                case 85:
                    g.e0.i M0 = M0(val);
                    style.W4 = M0;
                    if (M0 != null) {
                        style.c |= IjkMediaMeta.AV_CH_LOW_FREQUENCY_2;
                        return;
                    }
                    return;
                case 86:
                    g.e0.e y0 = y0(val);
                    style.X4 = y0;
                    if (y0 != null) {
                        style.c |= 137438953472L;
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private void Y(g.r0 obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            String val = attributes.getValue(i2).trim();
            switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                case 7:
                    w0(obj, val);
                    break;
                case 87:
                    obj.p = N0(val);
                    break;
            }
        }
    }

    private void W(g.n obj, Attributes attributes) {
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            if (g.fromString(attributes.getLocalName(i2)) == g.transform) {
                obj.j(J0(attributes.getValue(i2)));
            }
        }
    }

    private Matrix J0(String val) {
        String str = val;
        Matrix matrix = new Matrix();
        i scan = new i(str);
        scan.A();
        while (!scan.h()) {
            String cmd = scan.o();
            if (cmd != null) {
                char c2 = 65535;
                switch (cmd.hashCode()) {
                    case -1081239615:
                        if (cmd.equals("matrix")) {
                            c2 = 0;
                            break;
                        }
                        break;
                    case -925180581:
                        if (cmd.equals(Constants.ACTION_ROTATE)) {
                            c2 = 3;
                            break;
                        }
                        break;
                    case 109250890:
                        if (cmd.equals("scale")) {
                            c2 = 2;
                            break;
                        }
                        break;
                    case 109493390:
                        if (cmd.equals("skewX")) {
                            c2 = 4;
                            break;
                        }
                        break;
                    case 109493391:
                        if (cmd.equals("skewY")) {
                            c2 = 5;
                            break;
                        }
                        break;
                    case 1052832078:
                        if (cmd.equals("translate")) {
                            c2 = 1;
                            break;
                        }
                        break;
                }
                switch (c2) {
                    case 0:
                        scan.A();
                        float a2 = scan.n();
                        scan.z();
                        float b2 = scan.n();
                        scan.z();
                        float c3 = scan.n();
                        scan.z();
                        float d2 = scan.n();
                        scan.z();
                        float e2 = scan.n();
                        scan.z();
                        float f2 = scan.n();
                        scan.A();
                        if (!Float.isNaN(f2) && scan.f(')')) {
                            Matrix m = new Matrix();
                            m.setValues(new float[]{a2, c3, e2, b2, d2, f2, 0.0f, 0.0f, 1.0f});
                            matrix.preConcat(m);
                            break;
                        } else {
                            throw new SVGParseException("Invalid transform list: " + str);
                        }
                    case 1:
                        scan.A();
                        float tx = scan.n();
                        float ty = scan.x();
                        scan.A();
                        if (!Float.isNaN(tx) && scan.f(')')) {
                            if (!Float.isNaN(ty)) {
                                matrix.preTranslate(tx, ty);
                                break;
                            } else {
                                matrix.preTranslate(tx, 0.0f);
                                break;
                            }
                        } else {
                            throw new SVGParseException("Invalid transform list: " + str);
                        }
                    case 2:
                        scan.A();
                        float sx = scan.n();
                        float sy = scan.x();
                        scan.A();
                        if (!Float.isNaN(sx) && scan.f(')')) {
                            if (!Float.isNaN(sy)) {
                                matrix.preScale(sx, sy);
                                break;
                            } else {
                                matrix.preScale(sx, sx);
                                break;
                            }
                        } else {
                            throw new SVGParseException("Invalid transform list: " + str);
                        }
                        break;
                    case 3:
                        scan.A();
                        float ang = scan.n();
                        float cx = scan.x();
                        float cy = scan.x();
                        scan.A();
                        if (Float.isNaN(ang) || !scan.f(')')) {
                            throw new SVGParseException("Invalid transform list: " + str);
                        } else if (Float.isNaN(cx)) {
                            matrix.preRotate(ang);
                            break;
                        } else if (!Float.isNaN(cy)) {
                            matrix.preRotate(ang, cx, cy);
                            break;
                        } else {
                            throw new SVGParseException("Invalid transform list: " + str);
                        }
                    case 4:
                        scan.A();
                        float ang2 = scan.n();
                        scan.A();
                        if (!Float.isNaN(ang2) && scan.f(')')) {
                            matrix.preSkew((float) Math.tan(Math.toRadians((double) ang2)), 0.0f);
                            break;
                        } else {
                            throw new SVGParseException("Invalid transform list: " + str);
                        }
                        break;
                    case 5:
                        scan.A();
                        float ang3 = scan.n();
                        scan.A();
                        if (!Float.isNaN(ang3) && scan.f(')')) {
                            matrix.preSkew(0.0f, (float) Math.tan(Math.toRadians((double) ang3)));
                            break;
                        } else {
                            throw new SVGParseException("Invalid transform list: " + str);
                        }
                    default:
                        throw new SVGParseException("Invalid transform list fn: " + cmd + ")");
                }
                if (scan.h()) {
                    return matrix;
                }
                scan.z();
            } else {
                throw new SVGParseException("Bad transform function encountered in transform list: " + str);
            }
        }
        return matrix;
    }

    static g.p o0(String val) {
        if (val.length() != 0) {
            int end = val.length();
            g.d1 unit = g.d1.px;
            char lastChar = val.charAt(end - 1);
            if (lastChar == '%') {
                end--;
                unit = g.d1.percent;
            } else if (end > 2 && Character.isLetter(lastChar) && Character.isLetter(val.charAt(end - 2))) {
                end -= 2;
                try {
                    unit = g.d1.valueOf(val.substring(end).toLowerCase(Locale.US));
                } catch (IllegalArgumentException e2) {
                    throw new SVGParseException("Invalid length unit specifier: " + val);
                }
            }
            try {
                return new g.p(g0(val, 0, end), unit);
            } catch (NumberFormatException e3) {
                throw new SVGParseException("Invalid length value: " + val, e3);
            }
        } else {
            throw new SVGParseException("Invalid length value (empty string)");
        }
    }

    private static List<g.p> p0(String val) {
        if (val.length() != 0) {
            List<SVG.Length> coords = new ArrayList<>(1);
            i scan = new i(val);
            scan.A();
            while (!scan.h()) {
                float scalar = scan.n();
                if (!Float.isNaN(scalar)) {
                    g.d1 unit = scan.v();
                    if (unit == null) {
                        unit = g.d1.px;
                    }
                    coords.add(new g.p(scalar, unit));
                    scan.z();
                } else {
                    throw new SVGParseException("Invalid length list value: " + scan.b());
                }
            }
            return coords;
        }
        throw new SVGParseException("Invalid length list (empty string)");
    }

    private static float f0(String val) {
        int len = val.length();
        if (len != 0) {
            return g0(val, 0, len);
        }
        throw new SVGParseException("Invalid float value (empty string)");
    }

    private static float g0(String val, int offset, int len) {
        float num = new d().b(val, offset, len);
        if (!Float.isNaN(num)) {
            return num;
        }
        throw new SVGParseException("Invalid float value: " + val);
    }

    private static Float r0(String val) {
        try {
            float o = f0(val);
            float f2 = 0.0f;
            if (o >= 0.0f) {
                f2 = o > 1.0f ? 1.0f : o;
            }
            return Float.valueOf(f2);
        } catch (SVGParseException e2) {
            return null;
        }
    }

    private static g.b N0(String val) {
        i scan = new i(val);
        scan.A();
        float minX = scan.n();
        scan.z();
        float minY = scan.n();
        scan.z();
        float width = scan.n();
        scan.z();
        float height = scan.n();
        if (Float.isNaN(minX) || Float.isNaN(minY) || Float.isNaN(width) || Float.isNaN(height)) {
            throw new SVGParseException("Invalid viewBox definition - should have four numbers");
        } else if (width < 0.0f) {
            throw new SVGParseException("Invalid viewBox. width cannot be negative");
        } else if (height >= 0.0f) {
            return new g.b(minX, minY, width, height);
        } else {
            throw new SVGParseException("Invalid viewBox. height cannot be negative");
        }
    }

    private static void w0(g.p0 obj, String val) {
        obj.o = v0(val);
    }

    static e v0(String val) {
        i scan = new i(val);
        scan.A();
        String word = scan.r();
        if ("defer".equals(word)) {
            scan.A();
            word = scan.r();
        }
        e.a align = b.a(word);
        e.b scale = null;
        scan.A();
        if (!scan.h()) {
            String meetOrSlice = scan.r();
            char c2 = 65535;
            switch (meetOrSlice.hashCode()) {
                case 3347527:
                    if (meetOrSlice.equals("meet")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 109526418:
                    if (meetOrSlice.equals("slice")) {
                        c2 = 1;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    scale = e.b.meet;
                    break;
                case 1:
                    scale = e.b.slice;
                    break;
                default:
                    throw new SVGParseException("Invalid preserveAspectRatio definition: " + val);
            }
        }
        return new e(align, scale);
    }

    private static g.o0 t0(String val) {
        if (!val.startsWith("url(")) {
            return d0(val);
        }
        int closeBracket = val.indexOf(")");
        if (closeBracket == -1) {
            return new g.u(val.substring(4).trim(), (g.o0) null);
        }
        String href = val.substring(4, closeBracket).trim();
        g.o0 fallback = null;
        String val2 = val.substring(closeBracket + 1).trim();
        if (val2.length() > 0) {
            fallback = d0(val2);
        }
        return new g.u(href, fallback);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.caverock.androidsvg.g.o0 d0(java.lang.String r2) {
        /*
            int r0 = r2.hashCode()
            switch(r0) {
                case 3387192: goto L_0x0012;
                case 1442907498: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x001c
        L_0x0008:
            java.lang.String r0 = "currentColor"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x001d
        L_0x0012:
            java.lang.String r0 = "none"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x001d
        L_0x001c:
            r0 = -1
        L_0x001d:
            switch(r0) {
                case 0: goto L_0x002a;
                case 1: goto L_0x0025;
                default: goto L_0x0020;
            }
        L_0x0020:
            com.caverock.androidsvg.g$f r0 = b0(r2)     // Catch:{ SVGParseException -> 0x002e }
            goto L_0x002d
        L_0x0025:
            com.caverock.androidsvg.g$g r0 = com.caverock.androidsvg.g.C0052g.a()
            return r0
        L_0x002a:
            com.caverock.androidsvg.g$f r0 = com.caverock.androidsvg.g.f.d
            return r0
        L_0x002d:
            return r0
        L_0x002e:
            r0 = move-exception
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.j.d0(java.lang.String):com.caverock.androidsvg.g$o0");
    }

    private static g.f b0(String val) {
        if (val.charAt(0) == '#') {
            c ip = c.b(val, 1, val.length());
            if (ip != null) {
                switch (ip.a()) {
                    case 4:
                        int h1 = ip.d();
                        int h12 = h1 & 3840;
                        int h2 = h1 & 240;
                        int h3 = h1 & 15;
                        return new g.f(-16777216 | (h12 << 12) | (h12 << 8) | (h2 << 8) | (h2 << 4) | (h3 << 4) | h3);
                    case 5:
                        int fourhex = ip.d();
                        int h13 = 61440 & fourhex;
                        int h22 = fourhex & 3840;
                        int h32 = fourhex & 240;
                        int h4 = fourhex & 15;
                        return new g.f((h4 << 28) | (h4 << 24) | (h13 << 8) | (h13 << 4) | (h22 << 4) | h22 | h32 | (h32 >> 4));
                    case 7:
                        return new g.f(-16777216 | ip.d());
                    case 9:
                        return new g.f((ip.d() << 24) | (ip.d() >>> 8));
                    default:
                        throw new SVGParseException("Bad hex colour value: " + val);
                }
            } else {
                throw new SVGParseException("Bad hex colour value: " + val);
            }
        } else {
            String valLowerCase = val.toLowerCase(Locale.US);
            boolean isRGBA = valLowerCase.startsWith("rgba(");
            int i2 = 5;
            if (isRGBA || valLowerCase.startsWith("rgb(")) {
                if (!isRGBA) {
                    i2 = 4;
                }
                i scan = new i(val.substring(i2));
                scan.A();
                float red = scan.n();
                if (!Float.isNaN(red) && scan.f('%')) {
                    red = (red * 256.0f) / 100.0f;
                }
                float green = scan.d(red);
                if (!Float.isNaN(green) && scan.f('%')) {
                    green = (green * 256.0f) / 100.0f;
                }
                float blue = scan.d(green);
                if (!Float.isNaN(blue) && scan.f('%')) {
                    blue = (blue * 256.0f) / 100.0f;
                }
                if (isRGBA) {
                    float alpha = scan.d(blue);
                    scan.A();
                    if (!Float.isNaN(alpha) && scan.f(')')) {
                        return new g.f((j(256.0f * alpha) << 24) | (j(red) << 16) | (j(green) << 8) | j(blue));
                    }
                    throw new SVGParseException("Bad rgba() colour value: " + val);
                }
                scan.A();
                if (!Float.isNaN(blue) && scan.f(')')) {
                    return new g.f(-16777216 | (j(red) << 16) | (j(green) << 8) | j(blue));
                }
                throw new SVGParseException("Bad rgb() colour value: " + val);
            }
            boolean isHSLA = valLowerCase.startsWith("hsla(");
            if (!isHSLA && !valLowerCase.startsWith("hsl(")) {
                return c0(valLowerCase);
            }
            if (!isHSLA) {
                i2 = 4;
            }
            i scan2 = new i(val.substring(i2));
            scan2.A();
            float hue = scan2.n();
            float saturation = scan2.d(hue);
            if (!Float.isNaN(saturation)) {
                scan2.f('%');
            }
            float lightness = scan2.d(saturation);
            if (!Float.isNaN(lightness)) {
                scan2.f('%');
            }
            if (isHSLA) {
                float alpha2 = scan2.d(lightness);
                scan2.A();
                if (!Float.isNaN(alpha2) && scan2.f(')')) {
                    return new g.f((j(256.0f * alpha2) << 24) | s(hue, saturation, lightness));
                }
                throw new SVGParseException("Bad hsla() colour value: " + val);
            }
            scan2.A();
            if (!Float.isNaN(lightness) && scan2.f(')')) {
                return new g.f(-16777216 | s(hue, saturation, lightness));
            }
            throw new SVGParseException("Bad hsl() colour value: " + val);
        }
    }

    private static int j(float val) {
        if (val < 0.0f) {
            return 0;
        }
        if (val > 255.0f) {
            return 255;
        }
        return Math.round(val);
    }

    private static int s(float hue, float sat, float light) {
        float t2;
        float f2 = 0.0f;
        float hue2 = (hue >= 0.0f ? hue % 360.0f : (hue % 360.0f) + 360.0f) / 60.0f;
        float sat2 = sat / 100.0f;
        float light2 = light / 100.0f;
        float sat3 = sat2 < 0.0f ? 0.0f : sat2 > 1.0f ? 1.0f : sat2;
        if (light2 >= 0.0f) {
            f2 = light2 > 1.0f ? 1.0f : light2;
        }
        float light3 = f2;
        if (light3 <= 0.5f) {
            t2 = (1.0f + sat3) * light3;
        } else {
            t2 = (light3 + sat3) - (light3 * sat3);
        }
        float t1 = (light3 * 2.0f) - t2;
        return j(256.0f * t(t1, t2, hue2 - 2.0f)) | (j(t(t1, t2, hue2 + 2.0f) * 256.0f) << 16) | (j(t(t1, t2, hue2) * 256.0f) << 8);
    }

    private static float t(float t1, float t2, float hue) {
        if (hue < 0.0f) {
            hue += 6.0f;
        }
        if (hue >= 6.0f) {
            hue -= 6.0f;
        }
        if (hue < 1.0f) {
            return ((t2 - t1) * hue) + t1;
        }
        if (hue < 3.0f) {
            return t2;
        }
        if (hue < 4.0f) {
            return ((t2 - t1) * (4.0f - hue)) + t1;
        }
        return t1;
    }

    private static g.f c0(String nameLowerCase) {
        Integer col = c.a(nameLowerCase);
        if (col != null) {
            return new g.f(col.intValue());
        }
        throw new SVGParseException("Invalid colour keyword: " + nameLowerCase);
    }

    private static void h0(g.e0 style, String val) {
        String item;
        Integer fontWeight = null;
        g.e0.b fontStyle = null;
        String fontVariant = null;
        if ("|caption|icon|menu|message-box|small-caption|status-bar|".contains('|' + val + '|')) {
            i scan = new i(val);
            while (true) {
                item = scan.s('/');
                scan.A();
                if (item != null) {
                    if (fontWeight == null || fontStyle == null) {
                        if (!item.equals(GroupInfo.TYPE_NORMAL) && ((fontWeight != null || (fontWeight = e.a(item)) == null) && (fontStyle != null || (fontStyle = k0(item)) == null))) {
                            if (fontVariant != null || !item.equals("small-caps")) {
                                break;
                            }
                            fontVariant = item;
                        }
                    } else {
                        break;
                    }
                } else {
                    return;
                }
            }
            g.p fontSize = j0(item);
            if (scan.f('/')) {
                scan.A();
                String item2 = scan.r();
                if (item2 != null) {
                    try {
                        o0(item2);
                    } catch (SVGParseException e2) {
                        return;
                    }
                }
                scan.A();
            }
            style.z4 = i0(scan.y());
            style.A4 = fontSize;
            style.B4 = Integer.valueOf(fontWeight == null ? BaseResp.ERR_MSG_SEND_FAIL : fontWeight.intValue());
            style.C4 = fontStyle == null ? g.e0.b.Normal : fontStyle;
            style.c |= 122880;
        }
    }

    private static List<String> i0(String val) {
        List<String> fonts = null;
        i scan = new i(val);
        do {
            String item = scan.q();
            if (item == null) {
                item = scan.u(StringUtil.COMMA);
            }
            if (item == null) {
                break;
            }
            if (fonts == null) {
                fonts = new ArrayList<>();
            }
            fonts.add(item);
            scan.z();
        } while (!scan.h());
        return fonts;
    }

    private static g.p j0(String val) {
        try {
            g.p size = d.a(val);
            if (size == null) {
                return o0(val);
            }
            return size;
        } catch (SVGParseException e2) {
            return null;
        }
    }

    private static Integer l0(String val) {
        return e.a(val);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.caverock.androidsvg.g.e0.b k0(java.lang.String r1) {
        /*
            int r0 = r1.hashCode()
            switch(r0) {
                case -1657669071: goto L_0x001c;
                case -1178781136: goto L_0x0012;
                case -1039745817: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0026
        L_0x0008:
            java.lang.String r0 = "normal"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x0027
        L_0x0012:
            java.lang.String r0 = "italic"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x0027
        L_0x001c:
            java.lang.String r0 = "oblique"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 2
            goto L_0x0027
        L_0x0026:
            r0 = -1
        L_0x0027:
            switch(r0) {
                case 0: goto L_0x0032;
                case 1: goto L_0x002f;
                case 2: goto L_0x002c;
                default: goto L_0x002a;
            }
        L_0x002a:
            r0 = 0
            return r0
        L_0x002c:
            com.caverock.androidsvg.g$e0$b r0 = com.caverock.androidsvg.g.e0.b.Oblique
            return r0
        L_0x002f:
            com.caverock.androidsvg.g$e0$b r0 = com.caverock.androidsvg.g.e0.b.Normal
            return r0
        L_0x0032:
            com.caverock.androidsvg.g$e0$b r0 = com.caverock.androidsvg.g.e0.b.Italic
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.j.k0(java.lang.String):com.caverock.androidsvg.g$e0$b");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.caverock.androidsvg.g.e0.C0051g H0(java.lang.String r1) {
        /*
            int r0 = r1.hashCode()
            switch(r0) {
                case -1171789332: goto L_0x0031;
                case -1026963764: goto L_0x0026;
                case 3387192: goto L_0x001c;
                case 93826908: goto L_0x0012;
                case 529818312: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x003b
        L_0x0008:
            java.lang.String r0 = "overline"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 2
            goto L_0x003c
        L_0x0012:
            java.lang.String r0 = "blink"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 4
            goto L_0x003c
        L_0x001c:
            java.lang.String r0 = "none"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x003c
        L_0x0026:
            java.lang.String r0 = "underline"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x003c
        L_0x0031:
            java.lang.String r0 = "line-through"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 3
            goto L_0x003c
        L_0x003b:
            r0 = -1
        L_0x003c:
            switch(r0) {
                case 0: goto L_0x004d;
                case 1: goto L_0x004a;
                case 2: goto L_0x0047;
                case 3: goto L_0x0044;
                case 4: goto L_0x0041;
                default: goto L_0x003f;
            }
        L_0x003f:
            r0 = 0
            return r0
        L_0x0041:
            com.caverock.androidsvg.g$e0$g r0 = com.caverock.androidsvg.g.e0.C0051g.Blink
            return r0
        L_0x0044:
            com.caverock.androidsvg.g$e0$g r0 = com.caverock.androidsvg.g.e0.C0051g.LineThrough
            return r0
        L_0x0047:
            com.caverock.androidsvg.g$e0$g r0 = com.caverock.androidsvg.g.e0.C0051g.Overline
            return r0
        L_0x004a:
            com.caverock.androidsvg.g$e0$g r0 = com.caverock.androidsvg.g.e0.C0051g.Underline
            return r0
        L_0x004d:
            com.caverock.androidsvg.g$e0$g r0 = com.caverock.androidsvg.g.e0.C0051g.None
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.j.H0(java.lang.String):com.caverock.androidsvg.g$e0$g");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.caverock.androidsvg.g.e0.h I0(java.lang.String r1) {
        /*
            int r0 = r1.hashCode()
            switch(r0) {
                case 107498: goto L_0x0012;
                case 113258: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x001c
        L_0x0008:
            java.lang.String r0 = "rtl"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x001d
        L_0x0012:
            java.lang.String r0 = "ltr"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x001d
        L_0x001c:
            r0 = -1
        L_0x001d:
            switch(r0) {
                case 0: goto L_0x0025;
                case 1: goto L_0x0022;
                default: goto L_0x0020;
            }
        L_0x0020:
            r0 = 0
            return r0
        L_0x0022:
            com.caverock.androidsvg.g$e0$h r0 = com.caverock.androidsvg.g.e0.h.RTL
            return r0
        L_0x0025:
            com.caverock.androidsvg.g$e0$h r0 = com.caverock.androidsvg.g.e0.h.LTR
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.j.I0(java.lang.String):com.caverock.androidsvg.g$e0$h");
    }

    private static g.e0.a e0(String val) {
        if ("nonzero".equals(val)) {
            return g.e0.a.NonZero;
        }
        if ("evenodd".equals(val)) {
            return g.e0.a.EvenOdd;
        }
        return null;
    }

    private static g.e0.c C0(String val) {
        if ("butt".equals(val)) {
            return g.e0.c.Butt;
        }
        if ("round".equals(val)) {
            return g.e0.c.Round;
        }
        if ("square".equals(val)) {
            return g.e0.c.Square;
        }
        return null;
    }

    private static g.e0.d D0(String val) {
        if ("miter".equals(val)) {
            return g.e0.d.Miter;
        }
        if ("round".equals(val)) {
            return g.e0.d.Round;
        }
        if ("bevel".equals(val)) {
            return g.e0.d.Bevel;
        }
        return null;
    }

    private static g.p[] B0(String val) {
        g.p dash;
        i scan = new i(val);
        scan.A();
        if (scan.h() || (dash = scan.p()) == null || dash.h()) {
            return null;
        }
        float sum = dash.a();
        List<SVG.Length> dashes = new ArrayList<>();
        dashes.add(dash);
        while (!scan.h()) {
            scan.z();
            g.p dash2 = scan.p();
            if (dash2 == null || dash2.h()) {
                return null;
            }
            dashes.add(dash2);
            sum += dash2.a();
        }
        if (sum == 0.0f) {
            return null;
        }
        return (g.p[]) dashes.toArray(new g.p[dashes.size()]);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.caverock.androidsvg.g.e0.f G0(java.lang.String r1) {
        /*
            int r0 = r1.hashCode()
            switch(r0) {
                case -1074341483: goto L_0x001d;
                case 100571: goto L_0x0013;
                case 109757538: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0027
        L_0x0008:
            java.lang.String r0 = "start"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x0028
        L_0x0013:
            java.lang.String r0 = "end"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 2
            goto L_0x0028
        L_0x001d:
            java.lang.String r0 = "middle"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x0028
        L_0x0027:
            r0 = -1
        L_0x0028:
            switch(r0) {
                case 0: goto L_0x0033;
                case 1: goto L_0x0030;
                case 2: goto L_0x002d;
                default: goto L_0x002b;
            }
        L_0x002b:
            r0 = 0
            return r0
        L_0x002d:
            com.caverock.androidsvg.g$e0$f r0 = com.caverock.androidsvg.g.e0.f.End
            return r0
        L_0x0030:
            com.caverock.androidsvg.g$e0$f r0 = com.caverock.androidsvg.g.e0.f.Middle
            return r0
        L_0x0033:
            com.caverock.androidsvg.g$e0$f r0 = com.caverock.androidsvg.g.e0.f.Start
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.j.G0(java.lang.String):com.caverock.androidsvg.g$e0$f");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Boolean s0(java.lang.String r1) {
        /*
            int r0 = r1.hashCode()
            switch(r0) {
                case -1217487446: goto L_0x0027;
                case -907680051: goto L_0x001d;
                case 3005871: goto L_0x0013;
                case 466743410: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0031
        L_0x0008:
            java.lang.String r0 = "visible"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x0032
        L_0x0013:
            java.lang.String r0 = "auto"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x0032
        L_0x001d:
            java.lang.String r0 = "scroll"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 3
            goto L_0x0032
        L_0x0027:
            java.lang.String r0 = "hidden"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 2
            goto L_0x0032
        L_0x0031:
            r0 = -1
        L_0x0032:
            switch(r0) {
                case 0: goto L_0x003a;
                case 1: goto L_0x003a;
                case 2: goto L_0x0037;
                case 3: goto L_0x0037;
                default: goto L_0x0035;
            }
        L_0x0035:
            r0 = 0
            return r0
        L_0x0037:
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            return r0
        L_0x003a:
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.j.s0(java.lang.String):java.lang.Boolean");
    }

    private static g.c a0(String val) {
        if ("auto".equals(val) || !val.startsWith("rect(")) {
            return null;
        }
        i scan = new i(val.substring(5));
        scan.A();
        g.p top = q0(scan);
        scan.z();
        g.p right = q0(scan);
        scan.z();
        g.p bottom = q0(scan);
        scan.z();
        g.p left = q0(scan);
        scan.A();
        if (scan.f(')') || scan.h()) {
            return new g.c(top, right, bottom, left);
        }
        return null;
    }

    private static g.p q0(i scan) {
        if (scan.g("auto")) {
            return new g.p(0.0f);
        }
        return scan.p();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.caverock.androidsvg.g.e0.i M0(java.lang.String r1) {
        /*
            int r0 = r1.hashCode()
            switch(r0) {
                case 3387192: goto L_0x0012;
                case 1629199934: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x001c
        L_0x0008:
            java.lang.String r0 = "non-scaling-stroke"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x001d
        L_0x0012:
            java.lang.String r0 = "none"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x001d
        L_0x001c:
            r0 = -1
        L_0x001d:
            switch(r0) {
                case 0: goto L_0x0025;
                case 1: goto L_0x0022;
                default: goto L_0x0020;
            }
        L_0x0020:
            r0 = 0
            return r0
        L_0x0022:
            com.caverock.androidsvg.g$e0$i r0 = com.caverock.androidsvg.g.e0.i.NonScalingStroke
            return r0
        L_0x0025:
            com.caverock.androidsvg.g$e0$i r0 = com.caverock.androidsvg.g.e0.i.None
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.j.M0(java.lang.String):com.caverock.androidsvg.g$e0$i");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.caverock.androidsvg.g.e0.e y0(java.lang.String r1) {
        /*
            int r0 = r1.hashCode()
            switch(r0) {
                case -933002398: goto L_0x001c;
                case 3005871: goto L_0x0012;
                case 362741610: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0026
        L_0x0008:
            java.lang.String r0 = "optimizeSpeed"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 2
            goto L_0x0027
        L_0x0012:
            java.lang.String r0 = "auto"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x0027
        L_0x001c:
            java.lang.String r0 = "optimizeQuality"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x0027
        L_0x0026:
            r0 = -1
        L_0x0027:
            switch(r0) {
                case 0: goto L_0x0032;
                case 1: goto L_0x002f;
                case 2: goto L_0x002c;
                default: goto L_0x002a;
            }
        L_0x002a:
            r0 = 0
            return r0
        L_0x002c:
            com.caverock.androidsvg.g$e0$e r0 = com.caverock.androidsvg.g.e0.e.optimizeSpeed
            return r0
        L_0x002f:
            com.caverock.androidsvg.g$e0$e r0 = com.caverock.androidsvg.g.e0.e.optimizeQuality
            return r0
        L_0x0032:
            com.caverock.androidsvg.g$e0$e r0 = com.caverock.androidsvg.g.e0.e.auto
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.j.y0(java.lang.String):com.caverock.androidsvg.g$e0$e");
    }

    private static g.w u0(String val) {
        float lastMoveY;
        int i2;
        int pathCommand;
        float y;
        float x;
        float y2;
        float x2;
        float y22;
        float x22;
        float y3;
        float y4;
        float x3;
        float y5;
        float x23;
        float y6;
        i scan = new i(val);
        float currentX = 0.0f;
        float currentY = 0.0f;
        float x24 = 0.0f;
        float lastMoveY2 = 0.0f;
        float lastControlX = 0.0f;
        float lastControlY = 0.0f;
        g.w path = new g.w();
        if (scan.h()) {
            return path;
        }
        int pathCommand2 = scan.l().intValue();
        int i3 = 109;
        if (pathCommand2 != 77 && pathCommand2 != 109) {
            return path;
        }
        int pathCommand3 = pathCommand2;
        while (true) {
            scan.A();
            int pathCommand4 = 108;
            switch (pathCommand3) {
                case 65:
                case 97:
                    float lastMoveX = x24;
                    lastMoveY = lastMoveY2;
                    pathCommand = pathCommand3;
                    i2 = i3;
                    float rx = scan.n();
                    float ry = scan.d(rx);
                    float xAxisRotation = scan.d(ry);
                    Boolean largeArcFlag = scan.c(Float.valueOf(xAxisRotation));
                    Boolean sweepFlag = scan.c(largeArcFlag);
                    float x4 = scan.e(sweepFlag);
                    float y7 = scan.d(x4);
                    if (!Float.isNaN(y7) && rx >= 0.0f) {
                        if (ry >= 0.0f) {
                            if (pathCommand == 97) {
                                x = x4 + currentX;
                                y = y7 + currentY;
                            } else {
                                x = x4;
                                y = y7;
                            }
                            Boolean bool = sweepFlag;
                            Boolean bool2 = largeArcFlag;
                            float f2 = ry;
                            path.d(rx, ry, xAxisRotation, largeArcFlag.booleanValue(), sweepFlag.booleanValue(), x, y);
                            lastControlX = x;
                            currentX = x;
                            lastControlY = y;
                            currentY = y;
                            pathCommand3 = pathCommand;
                            x24 = lastMoveX;
                            break;
                        } else {
                            Boolean bool3 = sweepFlag;
                            Boolean bool4 = largeArcFlag;
                            float f3 = ry;
                            break;
                        }
                    } else {
                        Boolean bool5 = sweepFlag;
                        Boolean bool6 = largeArcFlag;
                        float f4 = ry;
                        break;
                    }
                    break;
                case 67:
                case 99:
                    float lastMoveX2 = x24;
                    lastMoveY = lastMoveY2;
                    int pathCommand5 = pathCommand3;
                    i2 = i3;
                    float x1 = scan.n();
                    float y1 = scan.d(x1);
                    float x25 = scan.d(y1);
                    float y23 = scan.d(x25);
                    float x5 = scan.d(y23);
                    float y8 = scan.d(x5);
                    if (!Float.isNaN(y8)) {
                        if (pathCommand5 == 99) {
                            x1 += currentX;
                            x22 = x25 + currentX;
                            y22 = y23 + currentY;
                            x2 = x5 + currentX;
                            y2 = y8 + currentY;
                            y3 = y1 + currentY;
                        } else {
                            x22 = x25;
                            y22 = y23;
                            x2 = x5;
                            y2 = y8;
                            y3 = y1;
                        }
                        path.c(x1, y3, x22, y22, x2, y2);
                        lastControlX = x22;
                        lastControlY = y22;
                        currentX = x2;
                        currentY = y2;
                        pathCommand3 = pathCommand5;
                        x24 = lastMoveX2;
                        break;
                    } else {
                        Log.e("SVGParser", "Bad path coords for " + ((char) pathCommand5) + " path segment");
                        return path;
                    }
                case 72:
                case 104:
                    float lastMoveX3 = x24;
                    lastMoveY = lastMoveY2;
                    int pathCommand6 = pathCommand3;
                    i2 = i3;
                    float x6 = scan.n();
                    if (!Float.isNaN(x6)) {
                        if (pathCommand6 == 104) {
                            x6 += currentX;
                        }
                        path.e(x6, currentY);
                        lastControlX = x6;
                        currentX = x6;
                        pathCommand3 = pathCommand6;
                        x24 = lastMoveX3;
                        break;
                    } else {
                        Log.e("SVGParser", "Bad path coords for " + ((char) pathCommand6) + " path segment");
                        return path;
                    }
                case 76:
                case 108:
                    float lastMoveX4 = x24;
                    lastMoveY = lastMoveY2;
                    int pathCommand7 = pathCommand3;
                    i2 = i3;
                    float x7 = scan.n();
                    float y9 = scan.d(x7);
                    if (!Float.isNaN(y9)) {
                        if (pathCommand7 == 108) {
                            x7 += currentX;
                            y9 += currentY;
                        }
                        path.e(x7, y9);
                        lastControlX = x7;
                        currentX = x7;
                        lastControlY = y9;
                        currentY = y9;
                        pathCommand3 = pathCommand7;
                        x24 = lastMoveX4;
                        break;
                    } else {
                        Log.e("SVGParser", "Bad path coords for " + ((char) pathCommand7) + " path segment");
                        return path;
                    }
                case 77:
                case 109:
                    float lastMoveX5 = x24;
                    float f5 = lastMoveY2;
                    int pathCommand8 = pathCommand3;
                    i2 = i3;
                    float x8 = scan.n();
                    float y10 = scan.d(x8);
                    if (!Float.isNaN(y10)) {
                        if (pathCommand8 == i2 && !path.i()) {
                            x8 += currentX;
                            y10 += currentY;
                        }
                        path.b(x8, y10);
                        lastControlX = x8;
                        float lastMoveX6 = x8;
                        currentX = x8;
                        lastControlY = y10;
                        float lastMoveY3 = y10;
                        currentY = y10;
                        if (pathCommand8 != i2) {
                            pathCommand4 = 76;
                        }
                        pathCommand3 = pathCommand4;
                        x24 = lastMoveX6;
                        lastMoveY = lastMoveY3;
                        break;
                    } else {
                        Log.e("SVGParser", "Bad path coords for " + ((char) pathCommand8) + " path segment");
                        return path;
                    }
                case 81:
                case 113:
                    float lastMoveX7 = x24;
                    lastMoveY = lastMoveY2;
                    int pathCommand9 = pathCommand3;
                    i2 = i3;
                    float x12 = scan.n();
                    float y12 = scan.d(x12);
                    float x9 = scan.d(y12);
                    float y11 = scan.d(x9);
                    if (!Float.isNaN(y11)) {
                        if (pathCommand9 == 113) {
                            x9 += currentX;
                            y11 += currentY;
                            x12 += currentX;
                            y12 += currentY;
                        }
                        path.a(x12, y12, x9, y11);
                        lastControlX = x12;
                        lastControlY = y12;
                        currentX = x9;
                        currentY = y11;
                        pathCommand3 = pathCommand9;
                        x24 = lastMoveX7;
                        break;
                    } else {
                        Log.e("SVGParser", "Bad path coords for " + ((char) pathCommand9) + " path segment");
                        return path;
                    }
                case 83:
                case 115:
                    float x13 = (currentX * 2.0f) - lastControlX;
                    float y13 = (2.0f * currentY) - lastControlY;
                    float x26 = scan.n();
                    float y24 = scan.d(x26);
                    float x10 = scan.d(y24);
                    float y14 = scan.d(x10);
                    if (!Float.isNaN(y14)) {
                        float lastMoveX8 = x24;
                        if (pathCommand3 == 115) {
                            x23 = x26 + currentX;
                            x3 = x10 + currentX;
                            y4 = y14 + currentY;
                            y5 = y24 + currentY;
                        } else {
                            x23 = x26;
                            x3 = x10;
                            y4 = y14;
                            y5 = y24;
                        }
                        float f6 = x13;
                        lastMoveY = lastMoveY2;
                        i2 = 109;
                        path.c(x13, y13, x23, y5, x3, y4);
                        lastControlX = x23;
                        lastControlY = y5;
                        currentX = x3;
                        currentY = y4;
                        pathCommand3 = pathCommand3;
                        x24 = lastMoveX8;
                        break;
                    } else {
                        float f7 = x24;
                        Log.e("SVGParser", "Bad path coords for " + ((char) pathCommand3) + " path segment");
                        return path;
                    }
                case 84:
                case 116:
                    float x14 = (currentX * 2.0f) - lastControlX;
                    float y15 = (2.0f * currentY) - lastControlY;
                    float x11 = scan.n();
                    float y16 = scan.d(x11);
                    if (!Float.isNaN(y16)) {
                        if (pathCommand3 == 116) {
                            x11 += currentX;
                            y6 = y16 + currentY;
                        } else {
                            y6 = y16;
                        }
                        path.a(x14, y15, x11, y6);
                        lastControlX = x14;
                        lastControlY = y15;
                        currentX = x11;
                        currentY = y6;
                        lastMoveY = lastMoveY2;
                        i2 = 109;
                        break;
                    } else {
                        Log.e("SVGParser", "Bad path coords for " + ((char) pathCommand3) + " path segment");
                        return path;
                    }
                case 86:
                case 118:
                    float y17 = scan.n();
                    if (!Float.isNaN(y17)) {
                        if (pathCommand3 == 118) {
                            y17 += currentY;
                        }
                        path.e(currentX, y17);
                        lastControlY = y17;
                        currentY = y17;
                        lastMoveY = lastMoveY2;
                        i2 = i3;
                        break;
                    } else {
                        Log.e("SVGParser", "Bad path coords for " + ((char) pathCommand3) + " path segment");
                        return path;
                    }
                case 90:
                case 122:
                    path.close();
                    lastControlX = x24;
                    currentX = x24;
                    lastControlY = lastMoveY2;
                    currentY = lastMoveY2;
                    lastMoveY = lastMoveY2;
                    i2 = i3;
                    break;
                default:
                    return path;
            }
            scan.z();
            if (scan.h()) {
                return path;
            }
            if (scan.i()) {
                pathCommand3 = scan.l().intValue();
                String str = val;
                i3 = i2;
                lastMoveY2 = lastMoveY;
            } else {
                String str2 = val;
                i3 = i2;
                lastMoveY2 = lastMoveY;
            }
        }
        Log.e("SVGParser", "Bad path coords for " + ((char) pathCommand) + " path segment");
        return path;
    }

    private static Set<String> z0(String val) {
        i scan = new i(val);
        HashSet<String> result = new HashSet<>();
        while (!scan.h()) {
            String feature = scan.r();
            if (feature.startsWith("http://www.w3.org/TR/SVG11/feature#")) {
                result.add(feature.substring("http://www.w3.org/TR/SVG11/feature#".length()));
            } else {
                result.add("UNSUPPORTED");
            }
            scan.A();
        }
        return result;
    }

    private static Set<String> F0(String val) {
        i scan = new i(val);
        HashSet<String> result = new HashSet<>();
        while (!scan.h()) {
            String language = scan.r();
            int hyphenPos = language.indexOf(45);
            if (hyphenPos != -1) {
                language = language.substring(0, hyphenPos);
            }
            result.add(new Locale(language, "", "").getLanguage());
            scan.A();
        }
        return result;
    }

    private static Set<String> A0(String val) {
        i scan = new i(val);
        HashSet<String> result = new HashSet<>();
        while (!scan.h()) {
            result.add(scan.r());
            scan.A();
        }
        return result;
    }

    private static String m0(String val, String attrName) {
        if (val.equals("none") || !val.startsWith("url(")) {
            return null;
        }
        if (val.endsWith(")")) {
            return val.substring(4, val.length() - 1).trim();
        }
        return val.substring(4).trim();
    }

    private void Z0(Attributes attributes) {
        l("<style>", new Object[0]);
        if (this.b != null) {
            boolean isTextCSS = true;
            String media = "all";
            for (int i2 = 0; i2 < attributes.getLength(); i2++) {
                String val = attributes.getValue(i2).trim();
                switch (a.b[g.fromString(attributes.getLocalName(i2)).ordinal()]) {
                    case 88:
                        isTextCSS = val.equals("text/css");
                        break;
                    case 89:
                        media = val;
                        break;
                }
            }
            if (!isTextCSS || !a.b(media, a.f.screen)) {
                this.c = true;
                this.d = 1;
                return;
            }
            this.h = true;
            return;
        }
        throw new SVGParseException("Invalid document. Root element must be <svg>");
    }

    private void Z(String sheet) {
        this.a.a(new a(a.f.screen, a.u.Document).d(sheet));
    }
}
