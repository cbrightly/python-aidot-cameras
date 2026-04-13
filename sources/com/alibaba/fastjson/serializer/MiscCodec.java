package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.IOUtils;
import com.google.android.libraries.places.api.model.PlaceTypes;
import io.netty.util.internal.StringUtil;
import java.io.File;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MiscCodec implements ObjectSerializer, ObjectDeserializer {
    private static boolean FILE_RELATIVE_PATH_SUPPORT;
    public static final MiscCodec instance = new MiscCodec();
    private static Method method_paths_get;
    private static boolean method_paths_get_error = false;

    static {
        FILE_RELATIVE_PATH_SUPPORT = false;
        FILE_RELATIVE_PATH_SUPPORT = "true".equals(IOUtils.getStringProperty("fastjson.deserializer.fileRelativePathSupport"));
    }

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        String pattern;
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
            return;
        }
        Class<?> objClass = object.getClass();
        if (objClass == SimpleDateFormat.class) {
            pattern = ((SimpleDateFormat) object).toPattern();
            if (out.isEnabled(SerializerFeature.WriteClassName) && object.getClass() != fieldType) {
                out.write(123);
                out.writeFieldName(JSON.DEFAULT_TYPE_KEY);
                serializer.write(object.getClass().getName());
                out.writeFieldValue((char) StringUtil.COMMA, "val", pattern);
                out.write(125);
                return;
            }
        } else if (objClass == Class.class) {
            pattern = ((Class) object).getName();
        } else if (objClass == InetSocketAddress.class) {
            InetSocketAddress address = (InetSocketAddress) object;
            InetAddress inetAddress = address.getAddress();
            out.write(123);
            if (inetAddress != null) {
                out.writeFieldName(PlaceTypes.ADDRESS);
                serializer.write((Object) inetAddress);
                out.write(44);
            }
            out.writeFieldName(IjkMediaPlayer.OnNativeInvokeListener.ARG_PORT);
            out.writeInt(address.getPort());
            out.write(125);
            return;
        } else if (object instanceof File) {
            pattern = ((File) object).getPath();
        } else if (object instanceof InetAddress) {
            pattern = ((InetAddress) object).getHostAddress();
        } else if (object instanceof TimeZone) {
            pattern = ((TimeZone) object).getID();
        } else if (object instanceof Currency) {
            pattern = ((Currency) object).getCurrencyCode();
        } else if (object instanceof JSONStreamAware) {
            ((JSONStreamAware) object).writeJSONString(out);
            return;
        } else if (object instanceof Iterator) {
            writeIterator(serializer, out, (Iterator) object);
            return;
        } else if (object instanceof Iterable) {
            writeIterator(serializer, out, ((Iterable) object).iterator());
            return;
        } else if (object instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) object;
            Object objKey = entry.getKey();
            Object objVal = entry.getValue();
            if (objKey instanceof String) {
                String key = (String) objKey;
                if (objVal instanceof String) {
                    out.writeFieldValueStringWithDoubleQuoteCheck('{', key, (String) objVal);
                } else {
                    out.write(123);
                    out.writeFieldName(key);
                    serializer.write(objVal);
                }
            } else {
                out.write(123);
                serializer.write(objKey);
                out.write(58);
                serializer.write(objVal);
            }
            out.write(125);
            return;
        } else if (object.getClass().getName().equals("net.sf.json.JSONNull")) {
            out.writeNull();
            return;
        } else if (object instanceof Node) {
            pattern = toString((Node) object);
        } else {
            throw new JSONException("not support class : " + objClass);
        }
        out.writeString(pattern);
    }

    private static String toString(Node node) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource domSource = new DOMSource(node);
            StringWriter out = new StringWriter();
            transformer.transform(domSource, new StreamResult(out));
            return out.toString();
        } catch (TransformerException e) {
            throw new JSONException("xml node to string error", e);
        }
    }

    /* access modifiers changed from: protected */
    public void writeIterator(JSONSerializer serializer, SerializeWriter out, Iterator<?> it) {
        int i = 0;
        out.write(91);
        while (it.hasNext()) {
            if (i != 0) {
                out.write(44);
            }
            serializer.write(it.next());
            i++;
        }
        out.write(93);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v80, resolved type: java.net.InetAddress} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r17, java.lang.reflect.Type r18, java.lang.Object r19) {
        /*
            r16 = this;
            r1 = r17
            r2 = r18
            java.lang.String r3 = "Path deserialize erorr"
            com.alibaba.fastjson.parser.JSONLexer r4 = r1.lexer
            java.lang.Class<java.net.InetSocketAddress> r0 = java.net.InetSocketAddress.class
            r5 = 13
            r6 = 16
            r7 = 0
            r8 = 2
            r9 = 17
            if (r2 != r0) goto L_0x007d
            int r0 = r4.token()
            r3 = 8
            if (r0 != r3) goto L_0x0020
            r4.nextToken()
            return r7
        L_0x0020:
            r0 = 12
            r1.accept(r0)
            r0 = 0
            r3 = 0
        L_0x0027:
            java.lang.String r7 = r4.stringVal()
            r4.nextToken(r9)
            java.lang.String r10 = "address"
            boolean r10 = r7.equals(r10)
            if (r10 == 0) goto L_0x0043
            r1.accept(r9)
            java.lang.Class<java.net.InetAddress> r10 = java.net.InetAddress.class
            java.lang.Object r10 = r1.parseObject(r10)
            r0 = r10
            java.net.InetAddress r0 = (java.net.InetAddress) r0
            goto L_0x006a
        L_0x0043:
            java.lang.String r10 = "port"
            boolean r10 = r7.equals(r10)
            if (r10 == 0) goto L_0x0064
            r1.accept(r9)
            int r10 = r4.token()
            if (r10 != r8) goto L_0x005c
            int r3 = r4.intValue()
            r4.nextToken()
            goto L_0x006a
        L_0x005c:
            com.alibaba.fastjson.JSONException r5 = new com.alibaba.fastjson.JSONException
            java.lang.String r6 = "port is not int"
            r5.<init>(r6)
            throw r5
        L_0x0064:
            r1.accept(r9)
            r17.parse()
        L_0x006a:
            int r10 = r4.token()
            if (r10 != r6) goto L_0x0074
            r4.nextToken()
            goto L_0x0027
        L_0x0074:
            r1.accept(r5)
            java.net.InetSocketAddress r5 = new java.net.InetSocketAddress
            r5.<init>(r0, r3)
            return r5
        L_0x007d:
            int r0 = r1.resolveStatus
            r10 = 0
            if (r0 != r8) goto L_0x00b9
            r1.resolveStatus = r10
            r1.accept(r6)
            int r0 = r4.token()
            r6 = 4
            java.lang.String r11 = "syntax error"
            if (r0 != r6) goto L_0x00b3
            java.lang.String r0 = r4.stringVal()
            java.lang.String r6 = "val"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00ad
            r4.nextToken()
            r1.accept(r9)
            java.lang.Object r0 = r17.parse()
            r1.accept(r5)
            r5 = r0
            goto L_0x00be
        L_0x00ad:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            r0.<init>(r11)
            throw r0
        L_0x00b3:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            r0.<init>(r11)
            throw r0
        L_0x00b9:
            java.lang.Object r0 = r17.parse()
            r5 = r0
        L_0x00be:
            if (r5 != 0) goto L_0x00c3
            r0 = 0
            r6 = r0
            goto L_0x00cb
        L_0x00c3:
            boolean r0 = r5 instanceof java.lang.String
            if (r0 == 0) goto L_0x022c
            r0 = r5
            java.lang.String r0 = (java.lang.String) r0
            r6 = r0
        L_0x00cb:
            if (r6 == 0) goto L_0x022b
            int r0 = r6.length()
            if (r0 != 0) goto L_0x00d5
            goto L_0x022b
        L_0x00d5:
            java.lang.Class<java.util.UUID> r0 = java.util.UUID.class
            if (r2 != r0) goto L_0x00de
            java.util.UUID r0 = java.util.UUID.fromString(r6)
            return r0
        L_0x00de:
            java.lang.Class<java.net.URI> r0 = java.net.URI.class
            if (r2 != r0) goto L_0x00e7
            java.net.URI r0 = java.net.URI.create(r6)
            return r0
        L_0x00e7:
            java.lang.Class<java.net.URL> r0 = java.net.URL.class
            if (r2 != r0) goto L_0x00fa
            java.net.URL r0 = new java.net.URL     // Catch:{ MalformedURLException -> 0x00f1 }
            r0.<init>(r6)     // Catch:{ MalformedURLException -> 0x00f1 }
            return r0
        L_0x00f1:
            r0 = move-exception
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException
            java.lang.String r7 = "create url error"
            r3.<init>(r7, r0)
            throw r3
        L_0x00fa:
            java.lang.Class<java.util.regex.Pattern> r0 = java.util.regex.Pattern.class
            if (r2 != r0) goto L_0x0103
            java.util.regex.Pattern r0 = java.util.regex.Pattern.compile(r6)
            return r0
        L_0x0103:
            java.lang.Class<java.util.Locale> r0 = java.util.Locale.class
            if (r2 != r0) goto L_0x010c
            java.util.Locale r0 = com.alibaba.fastjson.util.TypeUtils.toLocale(r6)
            return r0
        L_0x010c:
            java.lang.Class<java.text.SimpleDateFormat> r0 = java.text.SimpleDateFormat.class
            if (r2 != r0) goto L_0x0121
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.util.Locale r3 = r4.getLocale()
            r0.<init>(r6, r3)
            java.util.TimeZone r3 = r4.getTimeZone()
            r0.setTimeZone(r3)
            return r0
        L_0x0121:
            java.lang.Class<java.net.InetAddress> r0 = java.net.InetAddress.class
            if (r2 == r0) goto L_0x021b
            java.lang.Class<java.net.Inet4Address> r0 = java.net.Inet4Address.class
            if (r2 == r0) goto L_0x021b
            java.lang.Class<java.net.Inet6Address> r0 = java.net.Inet6Address.class
            if (r2 != r0) goto L_0x012f
            goto L_0x021b
        L_0x012f:
            java.lang.Class<java.io.File> r0 = java.io.File.class
            if (r2 != r0) goto L_0x014e
            java.lang.String r0 = ".."
            int r0 = r6.indexOf(r0)
            if (r0 < 0) goto L_0x0148
            boolean r0 = FILE_RELATIVE_PATH_SUPPORT
            if (r0 == 0) goto L_0x0140
            goto L_0x0148
        L_0x0140:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.String r3 = "file relative path not support."
            r0.<init>(r3)
            throw r0
        L_0x0148:
            java.io.File r0 = new java.io.File
            r0.<init>(r6)
            return r0
        L_0x014e:
            java.lang.Class<java.util.TimeZone> r0 = java.util.TimeZone.class
            if (r2 != r0) goto L_0x0157
            java.util.TimeZone r0 = java.util.TimeZone.getTimeZone(r6)
            return r0
        L_0x0157:
            boolean r0 = r2 instanceof java.lang.reflect.ParameterizedType
            if (r0 == 0) goto L_0x0162
            r0 = r2
            java.lang.reflect.ParameterizedType r0 = (java.lang.reflect.ParameterizedType) r0
            java.lang.reflect.Type r2 = r0.getRawType()
        L_0x0162:
            java.lang.Class<java.lang.Class> r0 = java.lang.Class.class
            if (r2 != r0) goto L_0x0173
            com.alibaba.fastjson.parser.ParserConfig r0 = r17.getConfig()
            java.lang.ClassLoader r0 = r0.getDefaultClassLoader()
            java.lang.Class r0 = com.alibaba.fastjson.util.TypeUtils.loadClass(r6, r0, r10)
            return r0
        L_0x0173:
            java.lang.Class<java.nio.charset.Charset> r0 = java.nio.charset.Charset.class
            if (r2 != r0) goto L_0x017c
            java.nio.charset.Charset r0 = java.nio.charset.Charset.forName(r6)
            return r0
        L_0x017c:
            java.lang.Class<java.util.Currency> r0 = java.util.Currency.class
            if (r2 != r0) goto L_0x0185
            java.util.Currency r0 = java.util.Currency.getInstance(r6)
            return r0
        L_0x0185:
            java.lang.Class<com.alibaba.fastjson.JSONPath> r0 = com.alibaba.fastjson.JSONPath.class
            if (r2 != r0) goto L_0x018f
            com.alibaba.fastjson.JSONPath r0 = new com.alibaba.fastjson.JSONPath
            r0.<init>(r6)
            return r0
        L_0x018f:
            boolean r0 = r2 instanceof java.lang.Class
            java.lang.String r9 = "MiscCodec not support "
            if (r0 == 0) goto L_0x0202
            r0 = r2
            java.lang.Class r0 = (java.lang.Class) r0
            java.lang.String r11 = r0.getName()
            java.lang.String r0 = "java.nio.file.Path"
            boolean r0 = r11.equals(r0)
            if (r0 == 0) goto L_0x01ed
            r12 = 1
            java.lang.reflect.Method r0 = method_paths_get     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
            if (r0 != 0) goto L_0x01c5
            boolean r0 = method_paths_get_error     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
            if (r0 != 0) goto L_0x01c5
            java.lang.String r0 = "java.nio.file.Paths"
            java.lang.Class r0 = com.alibaba.fastjson.util.TypeUtils.loadClass(r0)     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
            java.lang.String r13 = "get"
            java.lang.Class[] r14 = new java.lang.Class[r8]     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
            java.lang.Class<java.lang.String> r15 = java.lang.String.class
            r14[r10] = r15     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
            java.lang.Class<java.lang.String[]> r15 = java.lang.String[].class
            r14[r12] = r15     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
            java.lang.reflect.Method r13 = r0.getMethod(r13, r14)     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
            method_paths_get = r13     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
        L_0x01c5:
            java.lang.reflect.Method r0 = method_paths_get     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
            if (r0 == 0) goto L_0x01d6
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
            r8[r10] = r6     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
            java.lang.String[] r10 = new java.lang.String[r10]     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
            r8[r12] = r10     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
            java.lang.Object r0 = r0.invoke(r7, r8)     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
            return r0
        L_0x01d6:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
            r0.<init>(r3)     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
            throw r0     // Catch:{ NoSuchMethodException -> 0x01ea, IllegalAccessException -> 0x01e3, InvocationTargetException -> 0x01dc }
        L_0x01dc:
            r0 = move-exception
            com.alibaba.fastjson.JSONException r7 = new com.alibaba.fastjson.JSONException
            r7.<init>(r3, r0)
            throw r7
        L_0x01e3:
            r0 = move-exception
            com.alibaba.fastjson.JSONException r7 = new com.alibaba.fastjson.JSONException
            r7.<init>(r3, r0)
            throw r7
        L_0x01ea:
            r0 = move-exception
            method_paths_get_error = r12
        L_0x01ed:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r9)
            r3.append(r11)
            java.lang.String r3 = r3.toString()
            r0.<init>(r3)
            throw r0
        L_0x0202:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r9)
            java.lang.String r7 = r2.toString()
            r3.append(r7)
            java.lang.String r3 = r3.toString()
            r0.<init>(r3)
            throw r0
        L_0x021b:
            java.net.InetAddress r0 = java.net.InetAddress.getByName(r6)     // Catch:{ UnknownHostException -> 0x0220 }
            return r0
        L_0x0220:
            r0 = move-exception
            r3 = r0
            r0 = r3
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException
            java.lang.String r7 = "deserialize inet adress error"
            r3.<init>(r7, r0)
            throw r3
        L_0x022b:
            return r7
        L_0x022c:
            boolean r0 = r5 instanceof com.alibaba.fastjson.JSONObject
            if (r0 == 0) goto L_0x0267
            r0 = r5
            com.alibaba.fastjson.JSONObject r0 = (com.alibaba.fastjson.JSONObject) r0
            java.lang.Class<java.util.Currency> r3 = java.util.Currency.class
            if (r2 != r3) goto L_0x0251
            java.lang.String r3 = "currency"
            java.lang.String r3 = r0.getString(r3)
            if (r3 == 0) goto L_0x0244
            java.util.Currency r6 = java.util.Currency.getInstance(r3)
            return r6
        L_0x0244:
            java.lang.String r6 = "currencyCode"
            java.lang.String r6 = r0.getString(r6)
            if (r6 == 0) goto L_0x0251
            java.util.Currency r7 = java.util.Currency.getInstance(r6)
            return r7
        L_0x0251:
            java.lang.Class<java.util.Map$Entry> r3 = java.util.Map.Entry.class
            if (r2 != r3) goto L_0x0262
            java.util.Set r3 = r0.entrySet()
            java.util.Iterator r3 = r3.iterator()
            java.lang.Object r3 = r3.next()
            return r3
        L_0x0262:
            java.lang.Object r3 = r0.toJavaObject((java.lang.reflect.Type) r2)
            return r3
        L_0x0267:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.String r3 = "expect string"
            r0.<init>(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.MiscCodec.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object):java.lang.Object");
    }

    public int getFastMatchToken() {
        return 4;
    }
}
