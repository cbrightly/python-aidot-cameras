package com.didichuxing.doraemonkit.kit.filemanager.sqlite;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.filemanager.sqlite.bean.RowFiledInfo;
import com.didichuxing.doraemonkit.kit.filemanager.sqlite.bean.TableFieldInfo;
import com.didichuxing.doraemonkit.kit.filemanager.sqlite.dao.SQLiteDB;
import com.didichuxing.doraemonkit.kit.filemanager.sqlite.factory.DBFactory;
import com.didichuxing.doraemonkit.kit.filemanager.sqlite.factory.EncryptDBFactory;
import com.didichuxing.doraemonkit.kit.filemanager.sqlite.factory.NormalDBFactory;
import com.didichuxing.doraemonkit.kit.filemanager.sqlite.util.DBUtil;
import com.google.maps.android.BuildConfig;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;

/* compiled from: DBManager.kt */
public final class DBManager {
    public static final DBManager INSTANCE = new DBManager();
    @NotNull
    private static final String TAG = TAG;
    private static final Map<String, SQLiteDB> sqliteDBs = new LinkedHashMap();

    private DBManager() {
    }

    @NotNull
    public final String getTAG() {
        return TAG;
    }

    private final SQLiteDB openDB(String databasePath, String databaseName) {
        String password;
        DBFactory normalDBFactory = new NormalDBFactory();
        DokitConstant dokitConstant = DokitConstant.INSTANCE;
        if (dokitConstant.getDATABASE_PASS().isEmpty()) {
            password = null;
        } else {
            password = dokitConstant.getDATABASE_PASS().get(databaseName);
            if (password != null) {
                String str = password;
                normalDBFactory = new EncryptDBFactory();
            }
        }
        Map<String, SQLiteDB> map = sqliteDBs;
        if (map.containsKey(databasePath)) {
            return map.get("databasePath");
        }
        Application application = DoraemonKit.APPLICATION;
        if (application == null) {
            k.n();
        }
        Context applicationContext = application.getApplicationContext();
        k.b(applicationContext, "DoraemonKit.APPLICATION!!.applicationContext");
        map.put("databasePath", normalDBFactory.create(applicationContext, databasePath, password));
        return map.get("databasePath");
    }

    @NotNull
    public final List<String> getAllTableName(@NotNull String databasePath, @NotNull String databaseName) {
        Cursor cursor;
        k.f(databasePath, "databasePath");
        k.f(databaseName, "databaseName");
        SQLiteDB openDB = openDB(databasePath, databaseName);
        List tables = new ArrayList();
        if (!(openDB == null || (cursor = openDB.rawQuery("SELECT name FROM sqlite_master WHERE type='table' OR type='view' ORDER BY name COLLATE NOCASE", (String[]) null)) == null)) {
            Cursor it = cursor;
            if (it.moveToFirst()) {
                while (!it.isAfterLast()) {
                    String name = it.getString(0);
                    k.b(name, "name");
                    tables.add(name);
                    it.moveToNext();
                }
            }
            it.close();
        }
        return tables;
    }

    @NotNull
    public final Map<String, Object> getTableData(@NotNull String databasePath, @NotNull String databaseName, @NotNull String tableName) {
        k.f(databasePath, "databasePath");
        k.f(databaseName, "databaseName");
        k.f(tableName, "tableName");
        SQLiteDB openDB = openDB(databasePath, databaseName);
        Map params = new LinkedHashMap();
        if (openDB != null) {
            SQLiteDB db = openDB;
            DBManager dBManager = INSTANCE;
            List tableFieldInfos = dBManager.getTableFieldInfos(db, tableName);
            List tableRows = dBManager.getTableRows(db, tableName, tableFieldInfos);
            params.put("fieldInfo", tableFieldInfos);
            params.put("rows", tableRows);
        }
        return params;
    }

    public final long insertRow(@NotNull String databasePath, @NotNull String databaseName, @NotNull String tableName, @NotNull List<RowFiledInfo> rowDatas) {
        String str = tableName;
        k.f(databasePath, "databasePath");
        k.f(databaseName, "databaseName");
        k.f(str, "tableName");
        k.f(rowDatas, "rowDatas");
        SQLiteDB openDB = openDB(databasePath, databaseName);
        if (rowDatas.isEmpty() || openDB == null) {
            return -1;
        }
        SQLiteDB db = openDB;
        ContentValues contentValues = new ContentValues();
        for (RowFiledInfo rowInfo : rowDatas) {
            String value = rowInfo.getValue();
            if (value == null || w.A(value)) {
                contentValues.put(rowInfo.getTitle(), BuildConfig.TRAVIS);
            } else {
                contentValues.put(rowInfo.getTitle(), rowInfo.getValue());
            }
        }
        return db.insert('[' + str + ']', (String) null, contentValues);
    }

    public final int updateRow(@NotNull String databasePath, @NotNull String databaseName, @NotNull String tableName, @NotNull List<RowFiledInfo> rowDatas) {
        SQLiteDB openDB;
        String str;
        String str2 = tableName;
        k.f(databasePath, "databasePath");
        k.f(databaseName, "databaseName");
        k.f(str2, "tableName");
        k.f(rowDatas, "rowDatas");
        SQLiteDB openDB2 = openDB(databasePath, databaseName);
        if (rowDatas.isEmpty() || openDB2 == null) {
            return -1;
        }
        SQLiteDB db = openDB2;
        ContentValues contentValues = new ContentValues();
        String str3 = "";
        ArrayList thisCollection$iv = new ArrayList();
        for (RowFiledInfo rowInfo : rowDatas) {
            boolean isPrimary = rowInfo.isPrimary();
            String str4 = BuildConfig.TRAVIS;
            if (isPrimary) {
                openDB = openDB2;
                if (w.A(str3)) {
                    str = rowInfo.getTitle() + " = ? ";
                } else {
                    str = str3 + " and " + rowInfo.getTitle() + " = ? ";
                }
                String value = rowInfo.getValue();
                if (!(value == null || w.A(value))) {
                    str4 = rowInfo.getValue();
                }
                thisCollection$iv.add(str4);
                str3 = str;
            } else {
                openDB = openDB2;
                String value2 = rowInfo.getValue();
                if (value2 == null || w.A(value2)) {
                    contentValues.put(rowInfo.getTitle(), str4);
                } else {
                    contentValues.put(rowInfo.getTitle(), rowInfo.getValue());
                }
            }
            String str5 = databasePath;
            String str6 = databaseName;
            openDB2 = openDB;
        }
        Object[] array = thisCollection$iv.toArray(new String[0]);
        if (array != null) {
            return db.update('[' + str2 + ']', contentValues, str3, (String[]) array);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    public final int deleteRow(@NotNull String databasePath, @NotNull String databaseName, @NotNull String tableName, @NotNull List<RowFiledInfo> rowDatas) {
        SQLiteDB openDB;
        String str;
        String str2;
        String str3 = tableName;
        k.f(databasePath, "databasePath");
        k.f(databaseName, "databaseName");
        k.f(str3, "tableName");
        k.f(rowDatas, "rowDatas");
        SQLiteDB openDB2 = openDB(databasePath, databaseName);
        if (rowDatas.isEmpty() || openDB2 == null) {
            return -1;
        }
        SQLiteDB db = openDB2;
        String str4 = "";
        ArrayList thisCollection$iv = new ArrayList();
        for (RowFiledInfo rowInfo : rowDatas) {
            if (rowInfo.isPrimary()) {
                if (w.A(str4)) {
                    openDB = openDB2;
                    str = rowInfo.getTitle() + " = ? ";
                } else {
                    openDB = openDB2;
                    str = str4 + " and " + rowInfo.getTitle() + " = ? ";
                }
                String value = rowInfo.getValue();
                if (value == null || w.A(value)) {
                    str2 = BuildConfig.TRAVIS;
                } else {
                    str2 = rowInfo.getValue();
                }
                thisCollection$iv.add(str2);
                str4 = str;
            } else {
                openDB = openDB2;
            }
            String str5 = databasePath;
            openDB2 = openDB;
        }
        Object[] array = thisCollection$iv.toArray(new String[0]);
        if (array != null) {
            return db.delete('[' + str3 + ']', str4, (String[]) array);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    private final List<TableFieldInfo> getTableFieldInfos(SQLiteDB openDB, String tableName) {
        List tableFields = new ArrayList();
        Cursor cursor = openDB.rawQuery("PRAGMA table_info([" + tableName + "])", (String[]) null);
        if (cursor != null) {
            Cursor it = cursor;
            if (it.getCount() > 0) {
                it.moveToFirst();
                do {
                    TableFieldInfo tableFieldInfo = new TableFieldInfo("", false);
                    int columnCount = it.getColumnCount();
                    for (int index = 0; index < columnCount; index++) {
                        String columnName = it.getColumnName(index);
                        if (columnName != null) {
                            switch (columnName.hashCode()) {
                                case 3579:
                                    if (!columnName.equals("pk")) {
                                        break;
                                    } else {
                                        boolean z = true;
                                        if (cursor.getInt(index) != 1) {
                                            z = false;
                                        }
                                        tableFieldInfo.setPrimary(z);
                                        break;
                                    }
                                case 3373707:
                                    if (!columnName.equals("name")) {
                                        break;
                                    } else {
                                        String string = it.getString(index);
                                        k.b(string, "it.getString(index)");
                                        tableFieldInfo.setTitle(string);
                                        break;
                                    }
                            }
                        }
                    }
                    tableFields.add(tableFieldInfo);
                } while (it.moveToNext());
            }
            it.close();
        }
        return tableFields;
    }

    private final List<Map<String, String>> getTableRows(SQLiteDB sqLiteDB, String tableName, List<TableFieldInfo> tableFieldInfos) {
        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM  " + tableName, (String[]) null);
        List rows = new ArrayList();
        if (cursor != null) {
            Cursor it = cursor;
            if (it.getCount() > 0) {
                it.moveToFirst();
                do {
                    Map row = new LinkedHashMap();
                    int columnCount = it.getColumnCount();
                    for (int index = 0; index < columnCount; index++) {
                        switch (it.getType(index)) {
                            case 1:
                                row.put(tableFieldInfos.get(index).getTitle(), String.valueOf(cursor.getLong(index)));
                                break;
                            case 2:
                                row.put(tableFieldInfos.get(index).getTitle(), String.valueOf(cursor.getDouble(index)));
                                break;
                            case 3:
                                row.put(tableFieldInfos.get(index).getTitle(), cursor.getString(index));
                                break;
                            case 4:
                                String title = tableFieldInfos.get(index).getTitle();
                                DBUtil dBUtil = DBUtil.INSTANCE;
                                byte[] blob = cursor.getBlob(index);
                                k.b(blob, "cursor.getBlob(index)");
                                row.put(title, dBUtil.blob2String(blob));
                                break;
                            default:
                                row.put(tableFieldInfos.get(index).getTitle(), cursor.getString(index));
                                break;
                        }
                    }
                    rows.add(row);
                } while (it.moveToNext());
            }
            it.close();
        }
        return rows;
    }
}
