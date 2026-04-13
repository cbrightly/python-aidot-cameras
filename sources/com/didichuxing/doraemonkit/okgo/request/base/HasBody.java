package com.didichuxing.doraemonkit.okgo.request.base;

import com.didichuxing.doraemonkit.okgo.model.HttpParams;
import java.io.File;
import java.util.List;
import okhttp3.c0;
import okhttp3.x;
import org.json.JSONArray;
import org.json.JSONObject;

public interface HasBody<R> {
    R addFileParams(String str, List<File> list);

    R addFileWrapperParams(String str, List<HttpParams.FileWrapper> list);

    R isMultipart(boolean z);

    R isSpliceUrl(boolean z);

    R params(String str, File file);

    R params(String str, File file, String str2);

    R params(String str, File file, String str2, x xVar);

    R upBytes(byte[] bArr);

    R upBytes(byte[] bArr, x xVar);

    R upFile(File file);

    R upFile(File file, x xVar);

    R upJson(String str);

    R upJson(JSONArray jSONArray);

    R upJson(JSONObject jSONObject);

    R upRequestBody(c0 c0Var);

    R upString(String str);

    R upString(String str, x xVar);
}
