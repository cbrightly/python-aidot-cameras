package com.didichuxing.doraemonkit.okgo.request.base;

import android.text.TextUtils;
import com.didichuxing.doraemonkit.okgo.model.HttpParams;
import com.didichuxing.doraemonkit.okgo.request.base.BodyRequest;
import com.didichuxing.doraemonkit.okgo.utils.HttpUtils;
import com.didichuxing.doraemonkit.okgo.utils.OkLogger;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import okhttp3.b0;
import okhttp3.c0;
import okhttp3.x;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class BodyRequest<T, R extends BodyRequest> extends Request<T, R> implements HasBody<R> {
    private static final long serialVersionUID = -6459175248476927501L;
    protected byte[] bs;
    protected String content;
    protected transient File file;
    protected boolean isMultipart = false;
    protected boolean isSpliceUrl = false;
    protected transient x mediaType;
    protected c0 requestBody;

    public BodyRequest(String url) {
        super(url);
    }

    public R isMultipart(boolean isMultipart2) {
        this.isMultipart = isMultipart2;
        return this;
    }

    public R isSpliceUrl(boolean isSpliceUrl2) {
        this.isSpliceUrl = isSpliceUrl2;
        return this;
    }

    public R params(String key, File file2) {
        this.params.put(key, file2);
        return this;
    }

    public R addFileParams(String key, List<File> files) {
        this.params.putFileParams(key, files);
        return this;
    }

    public R addFileWrapperParams(String key, List<HttpParams.FileWrapper> fileWrappers) {
        this.params.putFileWrapperParams(key, fileWrappers);
        return this;
    }

    public R params(String key, File file2, String fileName) {
        this.params.put(key, file2, fileName);
        return this;
    }

    public R params(String key, File file2, String fileName, x contentType) {
        this.params.put(key, file2, fileName, contentType);
        return this;
    }

    public R upRequestBody(c0 requestBody2) {
        this.requestBody = requestBody2;
        return this;
    }

    public R upString(String string) {
        this.content = string;
        this.mediaType = HttpParams.MEDIA_TYPE_PLAIN;
        return this;
    }

    public R upString(String string, x mediaType2) {
        this.content = string;
        this.mediaType = mediaType2;
        return this;
    }

    public R upJson(String json) {
        this.content = json;
        this.mediaType = HttpParams.MEDIA_TYPE_JSON;
        return this;
    }

    public R upJson(JSONObject jsonObject) {
        this.content = jsonObject.toString();
        this.mediaType = HttpParams.MEDIA_TYPE_JSON;
        return this;
    }

    public R upJson(JSONArray jsonArray) {
        this.content = jsonArray.toString();
        this.mediaType = HttpParams.MEDIA_TYPE_JSON;
        return this;
    }

    public R upBytes(byte[] bs2) {
        this.bs = bs2;
        this.mediaType = HttpParams.MEDIA_TYPE_STREAM;
        return this;
    }

    public R upBytes(byte[] bs2, x mediaType2) {
        this.bs = bs2;
        this.mediaType = mediaType2;
        return this;
    }

    public R upFile(File file2) {
        this.file = file2;
        this.mediaType = HttpUtils.guessMimeType(file2.getName());
        return this;
    }

    public R upFile(File file2, x mediaType2) {
        this.file = file2;
        this.mediaType = mediaType2;
        return this;
    }

    public c0 generateRequestBody() {
        x xVar;
        x xVar2;
        x xVar3;
        if (this.isSpliceUrl) {
            this.url = HttpUtils.createUrlFromParams(this.baseUrl, this.params.urlParamsMap);
        }
        c0 c0Var = this.requestBody;
        if (c0Var != null) {
            return c0Var;
        }
        String str = this.content;
        if (str != null && (xVar3 = this.mediaType) != null) {
            return c0.create(xVar3, str);
        }
        byte[] bArr = this.bs;
        if (bArr != null && (xVar2 = this.mediaType) != null) {
            return c0.create(xVar2, bArr);
        }
        File file2 = this.file;
        if (file2 == null || (xVar = this.mediaType) == null) {
            return HttpUtils.generateMultipartRequestBody(this.params, this.isMultipart);
        }
        return c0.create(xVar, file2);
    }

    /* access modifiers changed from: protected */
    public b0.a generateRequestBuilder(c0 requestBody2) {
        try {
            headers("Content-Length", String.valueOf(requestBody2.contentLength()));
        } catch (IOException e) {
            OkLogger.printStackTrace(e);
        }
        return HttpUtils.appendHeaders(new b0.a(), this.headers);
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        x xVar = this.mediaType;
        out.writeObject(xVar == null ? "" : xVar.toString());
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        String mediaTypeString = (String) in.readObject();
        if (!TextUtils.isEmpty(mediaTypeString)) {
            this.mediaType = x.h(mediaTypeString);
        }
    }
}
