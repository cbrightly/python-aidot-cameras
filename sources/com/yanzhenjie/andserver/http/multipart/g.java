package com.yanzhenjie.andserver.http.multipart;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.yanzhenjie.andserver.error.MaxUploadSizeExceededException;
import com.yanzhenjie.andserver.error.MultipartException;
import com.yanzhenjie.andserver.http.c;
import com.yanzhenjie.andserver.util.MultiValueMap;
import com.yanzhenjie.andserver.util.h;
import com.yanzhenjie.andserver.util.j;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.b;
import org.apache.commons.fileupload.f;

/* compiled from: StandardMultipartResolver */
public class g implements d {
    private b a;
    private f b;

    public g() {
        b bVar = new b();
        this.a = bVar;
        this.b = new f(bVar);
    }

    public void a(long allFileMaxSize) {
        this.b.w(allFileMaxSize);
    }

    public void d(long fileMaxSize) {
        this.b.u(fileMaxSize);
    }

    public void g(int maxInMemorySize) {
        this.a.d(maxInMemorySize);
    }

    public void e(File uploadTempDir) {
        if (uploadTempDir.exists() || uploadTempDir.mkdirs()) {
            this.a.c(uploadTempDir);
            return;
        }
        throw new IllegalArgumentException("Given uploadTempDir [" + uploadTempDir + "] could not be created.");
    }

    public boolean f(c request) {
        com.yanzhenjie.andserver.http.f body;
        if (request.getMethod().allowBody() && (body = request.z()) != null && FileUploadBase.p(new a(body))) {
            return true;
        }
        return false;
    }

    public c b(c request) {
        if (request instanceof c) {
            return (c) request;
        }
        a result = l(request);
        return new f(request, result.b(), result.c(), result.a());
    }

    public void c(c request) {
        if (request != null) {
            try {
                Iterator<b> it = request.b().values().iterator();
                while (it.hasNext()) {
                    Iterator<MultipartFile> it2 = ((List) it.next()).iterator();
                    while (it2.hasNext()) {
                        b file = (b) it2.next();
                        if (file instanceof e) {
                            ((e) file).getFileItem().delete();
                        }
                    }
                }
            } catch (Throwable th) {
                Log.w("AndServer", "Failed to perform multipart cleanup for servlet request.");
            }
        }
    }

    private a l(c request) {
        String encoding = i(request);
        f fileUpload = m(encoding);
        try {
            com.yanzhenjie.andserver.http.f body = request.z();
            com.yanzhenjie.andserver.util.a.c(body, "The body cannot be null.");
            return k(fileUpload.t(new a(body)), encoding);
        } catch (FileUploadBase.SizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.o(), ex);
        } catch (FileUploadBase.FileSizeLimitExceededException ex2) {
            throw new MaxUploadSizeExceededException(fileUpload.k(), ex2);
        } catch (FileUploadException ex3) {
            throw new MultipartException("Failed to parse multipart servlet request.", ex3);
        }
    }

    @NonNull
    private String i(c request) {
        h mimeType = request.getContentType();
        if (mimeType == null) {
            return org.apache.commons.io.a.a("utf-8").name();
        }
        Charset charset = mimeType.getCharset();
        return charset == null ? org.apache.commons.io.a.a("utf-8").name() : charset.name();
    }

    private f m(@NonNull String encoding) {
        f actualFileUpload = this.b;
        if (encoding.equalsIgnoreCase(this.b.l())) {
            return actualFileUpload;
        }
        f actualFileUpload2 = new f(this.a);
        actualFileUpload2.w(this.b.o());
        actualFileUpload2.u(this.b.k());
        actualFileUpload2.v(encoding);
        return actualFileUpload2;
    }

    /* access modifiers changed from: protected */
    public a k(List<org.apache.commons.fileupload.a> fileItems, String encoding) {
        String value;
        MultiValueMap<String, MultipartFile> multipartFiles = new com.yanzhenjie.andserver.util.g<>();
        MultiValueMap<String, String> multipartParameters = new com.yanzhenjie.andserver.util.g<>();
        Map<String, String> multipartContentTypes = new HashMap<>();
        for (org.apache.commons.fileupload.a fileItem : fileItems) {
            if (fileItem.c()) {
                String partEncoding = j(fileItem.getContentType(), encoding);
                if (partEncoding != null) {
                    try {
                        value = fileItem.getString(partEncoding);
                    } catch (UnsupportedEncodingException e) {
                        value = fileItem.a();
                    }
                } else {
                    value = fileItem.a();
                }
                List<String> curParam = (List) multipartParameters.get(fileItem.b());
                if (curParam == null) {
                    List<String> curParam2 = new LinkedList<>();
                    curParam2.add(value);
                    multipartParameters.put(fileItem.b(), curParam2);
                } else {
                    curParam.add(value);
                }
                multipartContentTypes.put(fileItem.b(), fileItem.getContentType());
            } else {
                e file = h(fileItem);
                multipartFiles.add(file.getName(), file);
            }
        }
        return new a(multipartFiles, multipartParameters, multipartContentTypes);
    }

    /* access modifiers changed from: protected */
    public e h(org.apache.commons.fileupload.a fileItem) {
        return new e(fileItem);
    }

    private String j(String contentTypeHeader, String defaultEncoding) {
        if (TextUtils.isEmpty(contentTypeHeader)) {
            return defaultEncoding;
        }
        Charset charset = h.parseMediaType(contentTypeHeader).getCharset();
        return charset != null ? charset.name() : defaultEncoding;
    }

    /* compiled from: StandardMultipartResolver */
    public static class a {
        private final j<String, b> a;
        private final j<String, String> b;
        private final Map<String, String> c;

        public a(j<String, b> mpFiles, j<String, String> mpParams, Map<String, String> mpParamContentTypes) {
            this.a = mpFiles;
            this.b = mpParams;
            this.c = mpParamContentTypes;
        }

        public j<String, b> b() {
            return this.a;
        }

        public j<String, String> c() {
            return this.b;
        }

        public Map<String, String> a() {
            return this.c;
        }
    }
}
