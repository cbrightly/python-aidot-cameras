package com.didichuxing.doraemonkit.kit.filemanager.action.file;

import com.blankj.utilcode.util.f;
import com.blankj.utilcode.util.i;
import com.blankj.utilcode.util.j;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.parsers.SAXParserFactory;
import kotlin.jvm.internal.k;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.helpers.DefaultHandler;

/* compiled from: SaveFileAction.kt */
public final class SaveFileAction {
    public static final SaveFileAction INSTANCE = new SaveFileAction();

    private SaveFileAction() {
    }

    @NotNull
    public final Map<String, Object> saveFileRes(@NotNull String content, @NotNull String filePath) {
        boolean isJsonObject;
        boolean isJsonArray;
        k.f(content, FirebaseAnalytics.Param.CONTENT);
        k.f(filePath, Progress.FILE_PATH);
        Map response = new LinkedHashMap();
        if (j.z(filePath)) {
            String fileExtension = j.n(filePath);
            k.b(fileExtension, "fileExtension");
            if (x.S(fileExtension, "xml", false, 2, (Object) null)) {
                if (isXml(content)) {
                    i.g(filePath, content, false);
                    response.put("code", 200);
                    response.put(FirebaseAnalytics.Param.SUCCESS, true);
                    response.put("message", FirebaseAnalytics.Param.SUCCESS);
                } else {
                    response.put("code", 0);
                    response.put(FirebaseAnalytics.Param.SUCCESS, false);
                    response.put("message", "is not xml");
                }
            }
            if (x.S(fileExtension, "json", false, 2, (Object) null)) {
                try {
                    new JSONObject(content);
                    isJsonObject = true;
                } catch (Exception e) {
                    isJsonObject = false;
                }
                try {
                    new JSONArray(content);
                    isJsonArray = true;
                } catch (Exception e2) {
                    isJsonArray = false;
                }
                if (isJsonObject || isJsonArray) {
                    i.g(filePath, content, false);
                    response.put("code", 200);
                    response.put(FirebaseAnalytics.Param.SUCCESS, true);
                    response.put("message", FirebaseAnalytics.Param.SUCCESS);
                } else {
                    response.put("code", 0);
                    response.put(FirebaseAnalytics.Param.SUCCESS, false);
                    response.put("message", "is not json");
                }
            } else {
                i.g(filePath, content, false);
                response.put("code", 200);
                response.put(FirebaseAnalytics.Param.SUCCESS, true);
                response.put("message", FirebaseAnalytics.Param.SUCCESS);
            }
        } else {
            response.put("code", 0);
            response.put(FirebaseAnalytics.Param.SUCCESS, false);
            response.put("message", "is not a file");
        }
        return response;
    }

    private final boolean isXml(String content) {
        try {
            SAXParserFactory.newInstance().newSAXParser().parse(f.j(content, "utf-8"), new DefaultHandler());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
