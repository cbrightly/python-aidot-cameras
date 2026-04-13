package com.leedarson.smartcamera.kvswebrtc.utils;

import android.os.Build;
import android.text.TextUtils;
import com.amazonaws.util.BinaryUtils;
import com.amazonaws.util.DateUtils;
import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.glassfish.grizzly.http.server.Constants;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/* compiled from: AwsV4Signer */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static URI l(URI uri, String accessKey, String str, String sessionToken, URI uri2, String str2) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{uri, accessKey, str, sessionToken, uri2, str2}, (Object) null, changeQuickRedirect, true, 10088, new Class[]{URI.class, cls, cls, cls, URI.class, cls}, URI.class);
        if (proxy.isSupported) {
            return (URI) proxy.result;
        }
        URI uri3 = uri;
        String secretKey = str;
        URI wssUri = uri2;
        String region = str2;
        long dateMilli = new Date().getTime();
        String amzDate = j(dateMilli);
        String datestamp = g(dateMilli);
        String canonicalQuerystring = f(a(uri3, accessKey, sessionToken, region, amzDate, datestamp));
        String str3 = secretKey;
        String signature = BinaryUtils.toHex(k(m(amzDate, c(region, datestamp), d(uri3, canonicalQuerystring)), i(secretKey, datestamp, region, "kinesisvideo")));
        StringBuilder sb = new StringBuilder();
        sb.append(canonicalQuerystring);
        String str4 = region;
        sb.append("&");
        sb.append("X-Amz-Signature");
        sb.append("=");
        sb.append(signature);
        try {
            return new URI(wssUri.getScheme(), wssUri.getRawAuthority(), e(uri3), sb.toString(), (String) null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String, String> a(URI uri, String accessKey, String str, String region, String amzDate, String datestamp) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{uri, accessKey, str, region, amzDate, datestamp}, (Object) null, changeQuickRedirect, true, 10089, new Class[]{URI.class, cls, cls, cls, cls, cls}, Map.class);
        if (proxy.isSupported) {
            return (Map) proxy.result;
        }
        URI uri2 = uri;
        String sessionToken = str;
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("X-Amz-Algorithm", "AWS4-HMAC-SHA256");
        queryParams.put("X-Amz-Credential", n(accessKey + "/" + c(region, datestamp)));
        queryParams.put("X-Amz-Date", amzDate);
        queryParams.put("X-Amz-Expires", "299");
        queryParams.put("X-Amz-SignedHeaders", SerializableCookie.HOST);
        if (!TextUtils.isEmpty(sessionToken)) {
            queryParams.put("X-Amz-Security-Token", n(sessionToken));
        }
        if (!TextUtils.isEmpty(uri2.getQuery())) {
            for (String param : uri2.getQuery().split("&")) {
                int index = param.indexOf(61);
                if (index > 0) {
                    queryParams.put(param.substring(0, index), n(param.substring(index + 1)));
                }
            }
        }
        return queryParams;
    }

    private static String c(String region, String datestamp) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{region, datestamp}, (Object) null, changeQuickRedirect2, true, 10090, new Class[]{cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            return new StringJoiner("/").add(datestamp).add(region).add("kinesisvideo").add("aws4_request").toString();
        }
        return datestamp + "/" + region + "/" + "kinesisvideo" + "/" + "aws4_request";
    }

    static String d(URI uri, String canonicalQuerystring) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{uri, canonicalQuerystring}, (Object) null, changeQuickRedirect, true, 10091, new Class[]{URI.class, String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String payloadHash = h("");
        String canonicalUri = e(uri);
        String canonicalHeaders = "host:" + uri.getHost() + "\n";
        if (Build.VERSION.SDK_INT >= 24) {
            return new StringJoiner("\n").add(Constants.GET).add(canonicalUri).add(canonicalQuerystring).add(canonicalHeaders).add(SerializableCookie.HOST).add(payloadHash).toString();
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(Constants.GET);
        stringBuffer.append("\n");
        stringBuffer.append(canonicalUri);
        stringBuffer.append("\n");
        stringBuffer.append(canonicalQuerystring);
        stringBuffer.append("\n");
        stringBuffer.append(canonicalHeaders);
        stringBuffer.append("\n");
        stringBuffer.append(SerializableCookie.HOST);
        stringBuffer.append("\n");
        stringBuffer.append(payloadHash);
        return stringBuffer.toString();
    }

    private static String e(URI uri) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{uri}, (Object) null, changeQuickRedirect, true, 10092, new Class[]{URI.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return TextUtils.isEmpty(uri.getPath()) ? "/" : uri.getPath();
    }

    static String m(String amzDate, String credentialScope, String canonicalRequest) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{amzDate, credentialScope, canonicalRequest}, (Object) null, changeQuickRedirect2, true, 10093, new Class[]{cls, cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String can = h(canonicalRequest);
        if (Build.VERSION.SDK_INT >= 24) {
            return new StringJoiner("\n").add("AWS4-HMAC-SHA256").add(amzDate).add(credentialScope).add(can).toString();
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("AWS4-HMAC-SHA256");
        stringBuffer.append("\n");
        stringBuffer.append(amzDate);
        stringBuffer.append("\n");
        stringBuffer.append(credentialScope);
        stringBuffer.append("\n");
        stringBuffer.append(can);
        return stringBuffer.toString();
    }

    private static String n(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, (Object) null, changeQuickRedirect, true, 10094, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            return URLEncoder.encode(str, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    static byte[] k(String data, byte[] key) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data, key}, (Object) null, changeQuickRedirect2, true, 10095, new Class[]{String.class, byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));
            return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    static byte[] i(String key, String dateStamp, String regionName, String serviceName) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{key, dateStamp, regionName, serviceName}, (Object) null, changeQuickRedirect, true, 10096, new Class[]{cls, cls, cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return k("aws4_request", k(serviceName, k(regionName, k(dateStamp, ("AWS4" + key).getBytes(StandardCharsets.UTF_8)))));
    }

    private static String j(long dateMilli) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Long(dateMilli)}, (Object) null, changeQuickRedirect, true, 10097, new Class[]{Long.TYPE}, String.class);
        return proxy.isSupported ? (String) proxy.result : DateUtils.format(DateUtils.COMPRESSED_DATE_PATTERN, new Date(dateMilli));
    }

    private static String g(long dateMilli) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Long(dateMilli)}, (Object) null, changeQuickRedirect, true, 10098, new Class[]{Long.TYPE}, String.class);
        return proxy.isSupported ? (String) proxy.result : DateUtils.format("yyyyMMdd", new Date(dateMilli));
    }

    static String f(Map<String, String> queryParamsMap) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{queryParamsMap}, (Object) null, changeQuickRedirect, true, 10099, new Class[]{Map.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        List<String> queryKeys = new ArrayList<>(queryParamsMap.keySet());
        Collections.sort(queryKeys);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < queryKeys.size(); i++) {
            builder.append(queryKeys.get(i));
            builder.append("=");
            builder.append(queryParamsMap.get(queryKeys.get(i)));
            if (queryKeys.size() - 1 > i) {
                builder.append("&");
            }
        }
        return builder.toString();
    }

    public static String h(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, (Object) null, changeQuickRedirect, true, IMediaPlayer.MEDIA_INFO_MEDIA_ACCURATE_SEEK_COMPLETE, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            return b(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    private static String b(byte[] bytes) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bytes}, (Object) null, changeQuickRedirect, true, 10101, new Class[]{byte[].class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bytes) {
            String temp = Integer.toHexString(b & 255);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
