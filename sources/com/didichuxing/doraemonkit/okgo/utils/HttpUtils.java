package com.didichuxing.doraemonkit.okgo.utils;

import android.text.TextUtils;
import com.didichuxing.doraemonkit.okgo.DokitOkGo;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.didichuxing.doraemonkit.okgo.model.HttpParams;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import okhttp3.b0;
import okhttp3.c0;
import okhttp3.d0;
import okhttp3.s;
import okhttp3.u;
import okhttp3.x;
import okhttp3.y;

public class HttpUtils {
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0032 A[Catch:{ UnsupportedEncodingException -> 0x0079 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String createUrlFromParams(java.lang.String r9, java.util.Map<java.lang.String, java.util.List<java.lang.String>> r10) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            r0.<init>()     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            r0.append(r9)     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            r1 = 38
            int r1 = r9.indexOf(r1)     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            java.lang.String r2 = "&"
            if (r1 > 0) goto L_0x0021
            r1 = 63
            int r1 = r9.indexOf(r1)     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            if (r1 <= 0) goto L_0x001b
            goto L_0x0021
        L_0x001b:
            java.lang.String r1 = "?"
            r0.append(r1)     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            goto L_0x0024
        L_0x0021:
            r0.append(r2)     // Catch:{ UnsupportedEncodingException -> 0x0079 }
        L_0x0024:
            java.util.Set r1 = r10.entrySet()     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ UnsupportedEncodingException -> 0x0079 }
        L_0x002c:
            boolean r3 = r1.hasNext()     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            if (r3 == 0) goto L_0x006b
            java.lang.Object r3 = r1.next()     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            java.lang.Object r4 = r3.getValue()     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            java.util.List r4 = (java.util.List) r4     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            java.util.Iterator r5 = r4.iterator()     // Catch:{ UnsupportedEncodingException -> 0x0079 }
        L_0x0042:
            boolean r6 = r5.hasNext()     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            if (r6 == 0) goto L_0x006a
            java.lang.Object r6 = r5.next()     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            java.lang.String r7 = "UTF-8"
            java.lang.String r7 = java.net.URLEncoder.encode(r6, r7)     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            java.lang.Object r8 = r3.getKey()     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            r0.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            java.lang.String r8 = "="
            r0.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            r0.append(r7)     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            r0.append(r2)     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            goto L_0x0042
        L_0x006a:
            goto L_0x002c
        L_0x006b:
            int r1 = r0.length()     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            int r1 = r1 + -1
            r0.deleteCharAt(r1)     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            java.lang.String r1 = r0.toString()     // Catch:{ UnsupportedEncodingException -> 0x0079 }
            return r1
        L_0x0079:
            r0 = move-exception
            com.didichuxing.doraemonkit.okgo.utils.OkLogger.printStackTrace(r0)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.okgo.utils.HttpUtils.createUrlFromParams(java.lang.String, java.util.Map):java.lang.String");
    }

    public static b0.a appendHeaders(b0.a builder, HttpHeaders headers) {
        if (headers.headersMap.isEmpty()) {
            return builder;
        }
        u.a headerBuilder = new u.a();
        try {
            for (Map.Entry<String, String> entry : headers.headersMap.entrySet()) {
                headerBuilder.a(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
        }
        builder.h(headerBuilder.f());
        return builder;
    }

    public static c0 generateMultipartRequestBody(HttpParams params, boolean isMultipart) {
        if (!params.fileParamsMap.isEmpty() || isMultipart) {
            y.a multipartBodybuilder = new y.a().f(y.e);
            if (!params.urlParamsMap.isEmpty()) {
                for (Map.Entry<String, List<String>> entry : params.urlParamsMap.entrySet()) {
                    for (String value : entry.getValue()) {
                        multipartBodybuilder.a(entry.getKey(), value);
                    }
                }
            }
            for (Map.Entry<String, List<HttpParams.FileWrapper>> entry2 : params.fileParamsMap.entrySet()) {
                for (HttpParams.FileWrapper fileWrapper : entry2.getValue()) {
                    multipartBodybuilder.b(entry2.getKey(), fileWrapper.fileName, c0.create(fileWrapper.contentType, fileWrapper.file));
                }
            }
            return multipartBodybuilder.e();
        }
        s.a bodyBuilder = new s.a();
        for (String key : params.urlParamsMap.keySet()) {
            for (String value2 : params.urlParamsMap.get(key)) {
                bodyBuilder.b(key, value2);
            }
        }
        return bodyBuilder.c();
    }

    public static String getNetFileName(d0 response, String url) {
        String fileName = getHeaderFileName(response);
        if (TextUtils.isEmpty(fileName)) {
            fileName = getUrlFileName(url);
        }
        if (TextUtils.isEmpty(fileName)) {
            fileName = "unknownfile_" + System.currentTimeMillis();
        }
        try {
            return URLDecoder.decode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            OkLogger.printStackTrace(e);
            return fileName;
        }
    }

    private static String getHeaderFileName(d0 response) {
        String dispositionHeader = response.n(HttpHeaders.HEAD_KEY_CONTENT_DISPOSITION);
        if (dispositionHeader == null) {
            return null;
        }
        String dispositionHeader2 = dispositionHeader.replaceAll("\"", "");
        int indexOf = dispositionHeader2.indexOf("filename=");
        if (indexOf != -1) {
            return dispositionHeader2.substring("filename=".length() + indexOf, dispositionHeader2.length());
        }
        int indexOf2 = dispositionHeader2.indexOf("filename*=");
        if (indexOf2 == -1) {
            return null;
        }
        String fileName = dispositionHeader2.substring("filename*=".length() + indexOf2, dispositionHeader2.length());
        if (fileName.startsWith("UTF-8''")) {
            return fileName.substring("UTF-8''".length(), fileName.length());
        }
        return fileName;
    }

    private static String getUrlFileName(String url) {
        int endIndex;
        String[] strings = url.split("/");
        for (String string : strings) {
            if (string.contains("?") && (endIndex = string.indexOf("?")) != -1) {
                return string.substring(0, endIndex);
            }
        }
        if (strings.length > 0) {
            return strings[strings.length - 1];
        }
        return null;
    }

    public static boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }
        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (!file.isFile()) {
            return false;
        }
        boolean delete = file.delete();
        OkLogger.e("deleteFile:" + delete + " path:" + path);
        return delete;
    }

    public static x guessMimeType(String fileName) {
        String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName.replace("#", ""));
        if (contentType == null) {
            return HttpParams.MEDIA_TYPE_STREAM;
        }
        return x.h(contentType);
    }

    public static <T> T checkNotNull(T object, String message) {
        if (object != null) {
            return object;
        }
        throw new NullPointerException(message);
    }

    public static void runOnUiThread(Runnable runnable) {
        DokitOkGo.getInstance().getDelivery().post(runnable);
    }
}
