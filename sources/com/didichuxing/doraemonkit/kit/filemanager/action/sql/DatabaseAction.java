package com.didichuxing.doraemonkit.kit.filemanager.action.sql;

import android.database.sqlite.SQLiteDatabaseCorruptException;
import com.didichuxing.doraemonkit.kit.filemanager.sqlite.DBManager;
import com.didichuxing.doraemonkit.kit.filemanager.sqlite.bean.RowFiledInfo;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: DatabaseAction.kt */
public final class DatabaseAction {
    public static final DatabaseAction INSTANCE = new DatabaseAction();

    private DatabaseAction() {
    }

    @NotNull
    public final Map<String, Object> allTablesRes(@NotNull String filePath, @NotNull String fileName) {
        k.f(filePath, Progress.FILE_PATH);
        k.f(fileName, Progress.FILE_NAME);
        Map response = new LinkedHashMap();
        try {
            List tables = DBManager.INSTANCE.getAllTableName(filePath, fileName);
            response.put("code", 200);
            response.put("data", tables);
        } catch (SQLiteDatabaseCorruptException e) {
            response.put("code", 0);
            response.put("data", new ArrayList());
            response.put("message", String.valueOf(e.getMessage()));
        }
        return response;
    }

    @NotNull
    public final Map<String, Object> tableDatasRes(@NotNull String filePath, @NotNull String fileName, @NotNull String tableName) {
        k.f(filePath, Progress.FILE_PATH);
        k.f(fileName, Progress.FILE_NAME);
        k.f(tableName, "tableName");
        Map response = new LinkedHashMap();
        Map tables = DBManager.INSTANCE.getTableData(filePath, fileName, tableName);
        response.put("code", 200);
        response.put("data", tables);
        return response;
    }

    @NotNull
    public final Map<String, Object> insertRowRes(@NotNull String filePath, @NotNull String fileName, @NotNull String tableName, @NotNull List<RowFiledInfo> rowDatas) {
        k.f(filePath, Progress.FILE_PATH);
        k.f(fileName, Progress.FILE_NAME);
        k.f(tableName, "tableName");
        k.f(rowDatas, "rowDatas");
        Map response = new LinkedHashMap();
        long insertRow = DBManager.INSTANCE.insertRow(filePath, fileName, tableName, rowDatas);
        if (insertRow <= 0) {
            response.put("code", 0);
            response.put(FirebaseAnalytics.Param.SUCCESS, false);
            response.put("message", "insertRow=" + insertRow);
        } else {
            response.put("code", 200);
            response.put(FirebaseAnalytics.Param.SUCCESS, true);
            response.put("message", "insertRow=" + insertRow);
        }
        return response;
    }

    @NotNull
    public final Map<String, Object> updateRowRes(@NotNull String filePath, @NotNull String fileName, @NotNull String tableName, @NotNull List<RowFiledInfo> rowDatas) {
        k.f(filePath, Progress.FILE_PATH);
        k.f(fileName, Progress.FILE_NAME);
        k.f(tableName, "tableName");
        k.f(rowDatas, "rowDatas");
        Map response = new LinkedHashMap();
        int updateRow = DBManager.INSTANCE.updateRow(filePath, fileName, tableName, rowDatas);
        if (updateRow <= 0) {
            response.put("code", 0);
            response.put(FirebaseAnalytics.Param.SUCCESS, false);
            response.put("message", "updateRow=" + updateRow);
        } else {
            response.put("code", 200);
            response.put(FirebaseAnalytics.Param.SUCCESS, true);
            response.put("message", "updateRow=" + updateRow);
        }
        return response;
    }

    @NotNull
    public final Map<String, Object> deleteRowRes(@NotNull String filePath, @NotNull String fileName, @NotNull String tableName, @NotNull List<RowFiledInfo> rowDatas) {
        k.f(filePath, Progress.FILE_PATH);
        k.f(fileName, Progress.FILE_NAME);
        k.f(tableName, "tableName");
        k.f(rowDatas, "rowDatas");
        Map response = new LinkedHashMap();
        int deleteRow = DBManager.INSTANCE.deleteRow(filePath, fileName, tableName, rowDatas);
        if (deleteRow <= 0) {
            response.put("code", 0);
            response.put(FirebaseAnalytics.Param.SUCCESS, false);
            response.put("message", "deleteRow=" + deleteRow);
        } else {
            response.put("code", 200);
            response.put(FirebaseAnalytics.Param.SUCCESS, true);
            response.put("message", "deleteRow=" + deleteRow);
        }
        return response;
    }
}
