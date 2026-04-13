package com.didichuxing.doraemonkit.kit.network.room_db;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.google.android.gms.actions.SearchIntents;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;

public final class MockApiDao_Impl implements MockApiDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter __insertionAdapterOfMockInterceptApiBean;
    private final EntityInsertionAdapter __insertionAdapterOfMockTemplateApiBean;
    private final EntityDeletionOrUpdateAdapter __updateAdapterOfMockInterceptApiBean;
    private final EntityDeletionOrUpdateAdapter __updateAdapterOfMockTemplateApiBean;

    public MockApiDao_Impl(RoomDatabase __db2) {
        this.__db = __db2;
        this.__insertionAdapterOfMockInterceptApiBean = new EntityInsertionAdapter<MockInterceptApiBean>(__db2) {
            public String createQuery() {
                return "INSERT OR ROLLBACK INTO `mock_intercept_api`(`id`,`mock_api_name`,`path`,`method`,`query`,`body`,`fromType`,`selected_scene_name`,`selected_scene_id`,`is_open`) VALUES (?,?,?,?,?,?,?,?,?,?)";
            }

            public void bind(SupportSQLiteStatement stmt, MockInterceptApiBean value) {
                if (value.getId() == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, value.getId());
                }
                if (value.getMockApiName() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getMockApiName());
                }
                if (value.getPath() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getPath());
                }
                if (value.getMethod() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getMethod());
                }
                if (value.getQuery() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getQuery());
                }
                if (value.getBody() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getBody());
                }
                if (value.getFromType() == null) {
                    stmt.bindNull(7);
                } else {
                    stmt.bindString(7, value.getFromType());
                }
                if (value.getSelectedSceneName() == null) {
                    stmt.bindNull(8);
                } else {
                    stmt.bindString(8, value.getSelectedSceneName());
                }
                if (value.getSelectedSceneId() == null) {
                    stmt.bindNull(9);
                } else {
                    stmt.bindString(9, value.getSelectedSceneId());
                }
                stmt.bindLong(10, (long) value.isOpen());
            }
        };
        this.__insertionAdapterOfMockTemplateApiBean = new EntityInsertionAdapter<MockTemplateApiBean>(__db2) {
            public String createQuery() {
                return "INSERT OR ROLLBACK INTO `mock_template_api`(`id`,`mock_api_name`,`path`,`method`,`query`,`body`,`fromType`,`is_open`,`str_response`,`response_from`) VALUES (?,?,?,?,?,?,?,?,?,?)";
            }

            public void bind(SupportSQLiteStatement stmt, MockTemplateApiBean value) {
                if (value.getId() == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, value.getId());
                }
                if (value.getMockApiName() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getMockApiName());
                }
                if (value.getPath() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getPath());
                }
                if (value.getMethod() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getMethod());
                }
                if (value.getQuery() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getQuery());
                }
                if (value.getBody() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getBody());
                }
                if (value.getFromType() == null) {
                    stmt.bindNull(7);
                } else {
                    stmt.bindString(7, value.getFromType());
                }
                stmt.bindLong(8, (long) value.isOpen());
                if (value.getStrResponse() == null) {
                    stmt.bindNull(9);
                } else {
                    stmt.bindString(9, value.getStrResponse());
                }
                stmt.bindLong(10, (long) value.getResponseFrom());
            }
        };
        this.__updateAdapterOfMockInterceptApiBean = new EntityDeletionOrUpdateAdapter<MockInterceptApiBean>(__db2) {
            public String createQuery() {
                return "UPDATE OR ROLLBACK `mock_intercept_api` SET `id` = ?,`mock_api_name` = ?,`path` = ?,`method` = ?,`query` = ?,`body` = ?,`fromType` = ?,`selected_scene_name` = ?,`selected_scene_id` = ?,`is_open` = ? WHERE `id` = ?";
            }

            public void bind(SupportSQLiteStatement stmt, MockInterceptApiBean value) {
                if (value.getId() == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, value.getId());
                }
                if (value.getMockApiName() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getMockApiName());
                }
                if (value.getPath() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getPath());
                }
                if (value.getMethod() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getMethod());
                }
                if (value.getQuery() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getQuery());
                }
                if (value.getBody() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getBody());
                }
                if (value.getFromType() == null) {
                    stmt.bindNull(7);
                } else {
                    stmt.bindString(7, value.getFromType());
                }
                if (value.getSelectedSceneName() == null) {
                    stmt.bindNull(8);
                } else {
                    stmt.bindString(8, value.getSelectedSceneName());
                }
                if (value.getSelectedSceneId() == null) {
                    stmt.bindNull(9);
                } else {
                    stmt.bindString(9, value.getSelectedSceneId());
                }
                stmt.bindLong(10, (long) value.isOpen());
                if (value.getId() == null) {
                    stmt.bindNull(11);
                } else {
                    stmt.bindString(11, value.getId());
                }
            }
        };
        this.__updateAdapterOfMockTemplateApiBean = new EntityDeletionOrUpdateAdapter<MockTemplateApiBean>(__db2) {
            public String createQuery() {
                return "UPDATE OR ROLLBACK `mock_template_api` SET `id` = ?,`mock_api_name` = ?,`path` = ?,`method` = ?,`query` = ?,`body` = ?,`fromType` = ?,`is_open` = ?,`str_response` = ?,`response_from` = ? WHERE `id` = ?";
            }

            public void bind(SupportSQLiteStatement stmt, MockTemplateApiBean value) {
                if (value.getId() == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, value.getId());
                }
                if (value.getMockApiName() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getMockApiName());
                }
                if (value.getPath() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getPath());
                }
                if (value.getMethod() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getMethod());
                }
                if (value.getQuery() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getQuery());
                }
                if (value.getBody() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getBody());
                }
                if (value.getFromType() == null) {
                    stmt.bindNull(7);
                } else {
                    stmt.bindString(7, value.getFromType());
                }
                stmt.bindLong(8, (long) value.isOpen());
                if (value.getStrResponse() == null) {
                    stmt.bindNull(9);
                } else {
                    stmt.bindString(9, value.getStrResponse());
                }
                stmt.bindLong(10, (long) value.getResponseFrom());
                if (value.getId() == null) {
                    stmt.bindNull(11);
                } else {
                    stmt.bindString(11, value.getId());
                }
            }
        };
    }

    public void insertAllInterceptApi(List<MockInterceptApiBean> mockApis) {
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfMockInterceptApiBean.insert(mockApis);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void insertAllTemplateApi(List<MockTemplateApiBean> mockApis) {
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfMockTemplateApiBean.insert(mockApis);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public int updateInterceptApi(MockInterceptApiBean mockApi) {
        this.__db.beginTransaction();
        try {
            int _total = 0 + this.__updateAdapterOfMockInterceptApiBean.handle(mockApi);
            this.__db.setTransactionSuccessful();
            return _total;
        } finally {
            this.__db.endTransaction();
        }
    }

    public int updateTemplateApi(MockTemplateApiBean mockApi) {
        this.__db.beginTransaction();
        try {
            int _total = 0 + this.__updateAdapterOfMockTemplateApiBean.handle(mockApi);
            this.__db.setTransactionSuccessful();
            return _total;
        } finally {
            this.__db.endTransaction();
        }
    }

    public List<MockInterceptApiBean> getAllInterceptApi() {
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM mock_intercept_api", 0);
        Cursor _cursor = this.__db.query(_statement);
        try {
            int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
            int _cursorIndexOfMockApiName = _cursor.getColumnIndexOrThrow("mock_api_name");
            int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
            int _cursorIndexOfMethod = _cursor.getColumnIndexOrThrow(FirebaseAnalytics.Param.METHOD);
            int _cursorIndexOfQuery = _cursor.getColumnIndexOrThrow(SearchIntents.EXTRA_QUERY);
            int _cursorIndexOfBody = _cursor.getColumnIndexOrThrow("body");
            int _cursorIndexOfFromType = _cursor.getColumnIndexOrThrow("fromType");
            int _cursorIndexOfSelectedSceneName = _cursor.getColumnIndexOrThrow("selected_scene_name");
            int _cursorIndexOfSelectedSceneId = _cursor.getColumnIndexOrThrow("selected_scene_id");
            int _cursorIndexOfIsOpen = _cursor.getColumnIndexOrThrow("is_open");
            List<MockInterceptApiBean> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                MockInterceptApiBean _item = new MockInterceptApiBean();
                int _cursorIndexOfId2 = _cursorIndexOfId;
                String _tmpId = _cursor.getString(_cursorIndexOfId);
                _item.setId(_tmpId);
                String str = _tmpId;
                String _tmpMockApiName = _cursor.getString(_cursorIndexOfMockApiName);
                _item.setMockApiName(_tmpMockApiName);
                String str2 = _tmpMockApiName;
                String _tmpPath = _cursor.getString(_cursorIndexOfPath);
                _item.setPath(_tmpPath);
                String str3 = _tmpPath;
                String _tmpPath2 = _cursor.getString(_cursorIndexOfMethod);
                _item.setMethod(_tmpPath2);
                String str4 = _tmpPath2;
                String _tmpMethod = _cursor.getString(_cursorIndexOfQuery);
                _item.setQuery(_tmpMethod);
                String str5 = _tmpMethod;
                String _tmpBody = _cursor.getString(_cursorIndexOfBody);
                _item.setBody(_tmpBody);
                String str6 = _tmpBody;
                String _tmpBody2 = _cursor.getString(_cursorIndexOfFromType);
                _item.setFromType(_tmpBody2);
                String str7 = _tmpBody2;
                String _tmpFromType = _cursor.getString(_cursorIndexOfSelectedSceneName);
                _item.setSelectedSceneName(_tmpFromType);
                String str8 = _tmpFromType;
                String _tmpSelectedSceneId = _cursor.getString(_cursorIndexOfSelectedSceneId);
                _item.setSelectedSceneId(_tmpSelectedSceneId);
                String str9 = _tmpSelectedSceneId;
                _item.setOpen(_cursor.getInt(_cursorIndexOfIsOpen) != 0);
                _result.add(_item);
                _cursorIndexOfId = _cursorIndexOfId2;
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public List<MockTemplateApiBean> getAllTemplateApi() {
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM mock_template_api", 0);
        Cursor _cursor = this.__db.query(_statement);
        try {
            int _tmpResponseFrom = _cursor.getColumnIndexOrThrow("id");
            int _cursorIndexOfMockApiName = _cursor.getColumnIndexOrThrow("mock_api_name");
            int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
            int _cursorIndexOfMethod = _cursor.getColumnIndexOrThrow(FirebaseAnalytics.Param.METHOD);
            int _cursorIndexOfQuery = _cursor.getColumnIndexOrThrow(SearchIntents.EXTRA_QUERY);
            int _cursorIndexOfBody = _cursor.getColumnIndexOrThrow("body");
            int _cursorIndexOfFromType = _cursor.getColumnIndexOrThrow("fromType");
            int _cursorIndexOfIsOpen = _cursor.getColumnIndexOrThrow("is_open");
            int _cursorIndexOfStrResponse = _cursor.getColumnIndexOrThrow("str_response");
            int _cursorIndexOfResponseFrom = _cursor.getColumnIndexOrThrow("response_from");
            List<MockTemplateApiBean> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                MockTemplateApiBean _item = new MockTemplateApiBean();
                int _cursorIndexOfId = _tmpResponseFrom;
                String _tmpId = _cursor.getString(_tmpResponseFrom);
                _item.setId(_tmpId);
                String str = _tmpId;
                String _tmpMockApiName = _cursor.getString(_cursorIndexOfMockApiName);
                _item.setMockApiName(_tmpMockApiName);
                String str2 = _tmpMockApiName;
                String _tmpPath = _cursor.getString(_cursorIndexOfPath);
                _item.setPath(_tmpPath);
                String str3 = _tmpPath;
                String _tmpPath2 = _cursor.getString(_cursorIndexOfMethod);
                _item.setMethod(_tmpPath2);
                String str4 = _tmpPath2;
                String _tmpMethod = _cursor.getString(_cursorIndexOfQuery);
                _item.setQuery(_tmpMethod);
                String str5 = _tmpMethod;
                String _tmpBody = _cursor.getString(_cursorIndexOfBody);
                _item.setBody(_tmpBody);
                String str6 = _tmpBody;
                String _tmpBody2 = _cursor.getString(_cursorIndexOfFromType);
                _item.setFromType(_tmpBody2);
                String str7 = _tmpBody2;
                boolean _tmpIsOpen = _cursor.getInt(_cursorIndexOfIsOpen) != 0;
                _item.setOpen(_tmpIsOpen);
                boolean z = _tmpIsOpen;
                String _tmpStrResponse = _cursor.getString(_cursorIndexOfStrResponse);
                _item.setStrResponse(_tmpStrResponse);
                String str8 = _tmpStrResponse;
                _item.setResponseFrom(_cursor.getInt(_cursorIndexOfResponseFrom));
                _result.add(_item);
                _tmpResponseFrom = _cursorIndexOfId;
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public List<MockInterceptApiBean> findInterceptApiByPath(String path) {
        String str = path;
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM mock_intercept_api WHERE path LIKE ?", 1);
        if (str == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, str);
        }
        Cursor _cursor = this.__db.query(_statement);
        try {
            int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
            int _cursorIndexOfMockApiName = _cursor.getColumnIndexOrThrow("mock_api_name");
            int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
            int _cursorIndexOfMethod = _cursor.getColumnIndexOrThrow(FirebaseAnalytics.Param.METHOD);
            int _cursorIndexOfQuery = _cursor.getColumnIndexOrThrow(SearchIntents.EXTRA_QUERY);
            int _cursorIndexOfBody = _cursor.getColumnIndexOrThrow("body");
            int _cursorIndexOfFromType = _cursor.getColumnIndexOrThrow("fromType");
            int _cursorIndexOfSelectedSceneName = _cursor.getColumnIndexOrThrow("selected_scene_name");
            int _cursorIndexOfSelectedSceneId = _cursor.getColumnIndexOrThrow("selected_scene_id");
            int _cursorIndexOfIsOpen = _cursor.getColumnIndexOrThrow("is_open");
            Object obj = "SELECT * FROM mock_intercept_api WHERE path LIKE ?";
            try {
                List<MockInterceptApiBean> _result = new ArrayList<>(_cursor.getCount());
                while (_cursor.moveToNext()) {
                    MockInterceptApiBean _item = new MockInterceptApiBean();
                    int _cursorIndexOfId2 = _cursorIndexOfId;
                    String _tmpId = _cursor.getString(_cursorIndexOfId);
                    _item.setId(_tmpId);
                    String str2 = _tmpId;
                    String _tmpMockApiName = _cursor.getString(_cursorIndexOfMockApiName);
                    _item.setMockApiName(_tmpMockApiName);
                    String str3 = _tmpMockApiName;
                    String _tmpPath = _cursor.getString(_cursorIndexOfPath);
                    _item.setPath(_tmpPath);
                    String str4 = _tmpPath;
                    String _tmpPath2 = _cursor.getString(_cursorIndexOfMethod);
                    _item.setMethod(_tmpPath2);
                    String str5 = _tmpPath2;
                    String _tmpMethod = _cursor.getString(_cursorIndexOfQuery);
                    _item.setQuery(_tmpMethod);
                    String str6 = _tmpMethod;
                    String _tmpBody = _cursor.getString(_cursorIndexOfBody);
                    _item.setBody(_tmpBody);
                    String str7 = _tmpBody;
                    String _tmpBody2 = _cursor.getString(_cursorIndexOfFromType);
                    _item.setFromType(_tmpBody2);
                    String str8 = _tmpBody2;
                    String _tmpFromType = _cursor.getString(_cursorIndexOfSelectedSceneName);
                    _item.setSelectedSceneName(_tmpFromType);
                    String str9 = _tmpFromType;
                    String _tmpSelectedSceneId = _cursor.getString(_cursorIndexOfSelectedSceneId);
                    _item.setSelectedSceneId(_tmpSelectedSceneId);
                    String str10 = _tmpSelectedSceneId;
                    _item.setOpen(_cursor.getInt(_cursorIndexOfIsOpen) != 0);
                    _result.add(_item);
                    _cursorIndexOfId = _cursorIndexOfId2;
                }
                _cursor.close();
                _statement.release();
                return _result;
            } catch (Throwable th) {
                th = th;
                _cursor.close();
                _statement.release();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            Object obj2 = "SELECT * FROM mock_intercept_api WHERE path LIKE ?";
            _cursor.close();
            _statement.release();
            throw th;
        }
    }

    public MockInterceptApiBean findInterceptApiById(String id) {
        MockInterceptApiBean _result;
        String str = id;
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM mock_intercept_api WHERE id = ? LIMIT 1", 1);
        if (str == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, str);
        }
        Cursor _cursor = this.__db.query(_statement);
        try {
            int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
            int _cursorIndexOfMockApiName = _cursor.getColumnIndexOrThrow("mock_api_name");
            int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
            int _cursorIndexOfMethod = _cursor.getColumnIndexOrThrow(FirebaseAnalytics.Param.METHOD);
            int _cursorIndexOfQuery = _cursor.getColumnIndexOrThrow(SearchIntents.EXTRA_QUERY);
            int _cursorIndexOfBody = _cursor.getColumnIndexOrThrow("body");
            int _cursorIndexOfFromType = _cursor.getColumnIndexOrThrow("fromType");
            int _cursorIndexOfSelectedSceneName = _cursor.getColumnIndexOrThrow("selected_scene_name");
            int _cursorIndexOfSelectedSceneId = _cursor.getColumnIndexOrThrow("selected_scene_id");
            int _cursorIndexOfIsOpen = _cursor.getColumnIndexOrThrow("is_open");
            if (_cursor.moveToFirst()) {
                MockInterceptApiBean _result2 = new MockInterceptApiBean();
                String _tmpId = _cursor.getString(_cursorIndexOfId);
                int i = _cursorIndexOfId;
                MockInterceptApiBean _result3 = _result2;
                String _tmpId2 = _tmpId;
                _result3.setId(_tmpId2);
                String str2 = _tmpId2;
                String _tmpMockApiName = _cursor.getString(_cursorIndexOfMockApiName);
                _result3.setMockApiName(_tmpMockApiName);
                String str3 = _tmpMockApiName;
                String _tmpPath = _cursor.getString(_cursorIndexOfPath);
                _result3.setPath(_tmpPath);
                String str4 = _tmpPath;
                String _tmpPath2 = _cursor.getString(_cursorIndexOfMethod);
                _result3.setMethod(_tmpPath2);
                String str5 = _tmpPath2;
                String _tmpMethod = _cursor.getString(_cursorIndexOfQuery);
                _result3.setQuery(_tmpMethod);
                String str6 = _tmpMethod;
                String _tmpBody = _cursor.getString(_cursorIndexOfBody);
                _result3.setBody(_tmpBody);
                String str7 = _tmpBody;
                String _tmpBody2 = _cursor.getString(_cursorIndexOfFromType);
                _result3.setFromType(_tmpBody2);
                String str8 = _tmpBody2;
                String _tmpFromType = _cursor.getString(_cursorIndexOfSelectedSceneName);
                _result3.setSelectedSceneName(_tmpFromType);
                String str9 = _tmpFromType;
                String _tmpSelectedSceneId = _cursor.getString(_cursorIndexOfSelectedSceneId);
                _result3.setSelectedSceneId(_tmpSelectedSceneId);
                String str10 = _tmpSelectedSceneId;
                _result3.setOpen(_cursor.getInt(_cursorIndexOfIsOpen) != 0);
                _result = _result3;
            } else {
                _result = null;
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public List<MockTemplateApiBean> findTemplateApiByPath(String path) {
        String str = path;
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM mock_template_api WHERE path LIKE ?", 1);
        if (str == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, str);
        }
        Cursor _cursor = this.__db.query(_statement);
        try {
            int _tmpResponseFrom = _cursor.getColumnIndexOrThrow("id");
            int _cursorIndexOfMockApiName = _cursor.getColumnIndexOrThrow("mock_api_name");
            int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
            int _cursorIndexOfMethod = _cursor.getColumnIndexOrThrow(FirebaseAnalytics.Param.METHOD);
            int _cursorIndexOfQuery = _cursor.getColumnIndexOrThrow(SearchIntents.EXTRA_QUERY);
            int _cursorIndexOfBody = _cursor.getColumnIndexOrThrow("body");
            int _cursorIndexOfFromType = _cursor.getColumnIndexOrThrow("fromType");
            int _cursorIndexOfIsOpen = _cursor.getColumnIndexOrThrow("is_open");
            int _cursorIndexOfStrResponse = _cursor.getColumnIndexOrThrow("str_response");
            int _cursorIndexOfResponseFrom = _cursor.getColumnIndexOrThrow("response_from");
            Object obj = "SELECT * FROM mock_template_api WHERE path LIKE ?";
            try {
                List<MockTemplateApiBean> _result = new ArrayList<>(_cursor.getCount());
                while (_cursor.moveToNext()) {
                    MockTemplateApiBean _item = new MockTemplateApiBean();
                    int _cursorIndexOfId = _tmpResponseFrom;
                    String _tmpId = _cursor.getString(_tmpResponseFrom);
                    _item.setId(_tmpId);
                    String str2 = _tmpId;
                    String _tmpMockApiName = _cursor.getString(_cursorIndexOfMockApiName);
                    _item.setMockApiName(_tmpMockApiName);
                    String str3 = _tmpMockApiName;
                    String _tmpPath = _cursor.getString(_cursorIndexOfPath);
                    _item.setPath(_tmpPath);
                    String str4 = _tmpPath;
                    String _tmpPath2 = _cursor.getString(_cursorIndexOfMethod);
                    _item.setMethod(_tmpPath2);
                    String str5 = _tmpPath2;
                    String _tmpMethod = _cursor.getString(_cursorIndexOfQuery);
                    _item.setQuery(_tmpMethod);
                    String str6 = _tmpMethod;
                    String _tmpBody = _cursor.getString(_cursorIndexOfBody);
                    _item.setBody(_tmpBody);
                    String str7 = _tmpBody;
                    String _tmpBody2 = _cursor.getString(_cursorIndexOfFromType);
                    _item.setFromType(_tmpBody2);
                    String str8 = _tmpBody2;
                    boolean _tmpIsOpen = _cursor.getInt(_cursorIndexOfIsOpen) != 0;
                    _item.setOpen(_tmpIsOpen);
                    boolean z = _tmpIsOpen;
                    String _tmpStrResponse = _cursor.getString(_cursorIndexOfStrResponse);
                    _item.setStrResponse(_tmpStrResponse);
                    String str9 = _tmpStrResponse;
                    _item.setResponseFrom(_cursor.getInt(_cursorIndexOfResponseFrom));
                    _result.add(_item);
                    _tmpResponseFrom = _cursorIndexOfId;
                }
                _cursor.close();
                _statement.release();
                return _result;
            } catch (Throwable th) {
                th = th;
                _cursor.close();
                _statement.release();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            Object obj2 = "SELECT * FROM mock_template_api WHERE path LIKE ?";
            _cursor.close();
            _statement.release();
            throw th;
        }
    }

    public MockTemplateApiBean findTemplateApiById(String id) {
        MockTemplateApiBean _result;
        String str = id;
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM mock_template_api WHERE id = ? LIMIT 1", 1);
        if (str == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, str);
        }
        Cursor _cursor = this.__db.query(_statement);
        try {
            int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
            int _cursorIndexOfMockApiName = _cursor.getColumnIndexOrThrow("mock_api_name");
            int _cursorIndexOfPath = _cursor.getColumnIndexOrThrow("path");
            int _cursorIndexOfMethod = _cursor.getColumnIndexOrThrow(FirebaseAnalytics.Param.METHOD);
            int _cursorIndexOfQuery = _cursor.getColumnIndexOrThrow(SearchIntents.EXTRA_QUERY);
            int _cursorIndexOfBody = _cursor.getColumnIndexOrThrow("body");
            int _cursorIndexOfFromType = _cursor.getColumnIndexOrThrow("fromType");
            int _cursorIndexOfIsOpen = _cursor.getColumnIndexOrThrow("is_open");
            int _cursorIndexOfStrResponse = _cursor.getColumnIndexOrThrow("str_response");
            int _cursorIndexOfResponseFrom = _cursor.getColumnIndexOrThrow("response_from");
            if (_cursor.moveToFirst()) {
                MockTemplateApiBean _result2 = new MockTemplateApiBean();
                String _tmpId = _cursor.getString(_cursorIndexOfId);
                int i = _cursorIndexOfId;
                MockTemplateApiBean _result3 = _result2;
                String _tmpId2 = _tmpId;
                _result3.setId(_tmpId2);
                String str2 = _tmpId2;
                String _tmpMockApiName = _cursor.getString(_cursorIndexOfMockApiName);
                _result3.setMockApiName(_tmpMockApiName);
                String str3 = _tmpMockApiName;
                String _tmpPath = _cursor.getString(_cursorIndexOfPath);
                _result3.setPath(_tmpPath);
                String str4 = _tmpPath;
                String _tmpPath2 = _cursor.getString(_cursorIndexOfMethod);
                _result3.setMethod(_tmpPath2);
                String str5 = _tmpPath2;
                String _tmpMethod = _cursor.getString(_cursorIndexOfQuery);
                _result3.setQuery(_tmpMethod);
                String str6 = _tmpMethod;
                String _tmpBody = _cursor.getString(_cursorIndexOfBody);
                _result3.setBody(_tmpBody);
                String str7 = _tmpBody;
                String _tmpBody2 = _cursor.getString(_cursorIndexOfFromType);
                _result3.setFromType(_tmpBody2);
                String str8 = _tmpBody2;
                boolean _tmpIsOpen = _cursor.getInt(_cursorIndexOfIsOpen) != 0;
                _result3.setOpen(_tmpIsOpen);
                boolean z = _tmpIsOpen;
                String _tmpStrResponse = _cursor.getString(_cursorIndexOfStrResponse);
                _result3.setStrResponse(_tmpStrResponse);
                String str9 = _tmpStrResponse;
                _result3.setResponseFrom(_cursor.getInt(_cursorIndexOfResponseFrom));
                _result = _result3;
            } else {
                _result = null;
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }
}
