package androidx.work.impl.model;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.collection.ArrayMap;
import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.work.Data;
import androidx.work.impl.model.WorkSpec;
import com.leedarson.bean.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public final class RawWorkInfoDao_Impl implements RawWorkInfoDao {
    /* access modifiers changed from: private */
    public final RoomDatabase __db;

    public RawWorkInfoDao_Impl(RoomDatabase __db2) {
        this.__db = __db2;
    }

    public List<WorkSpec.WorkInfoPojo> getWorkInfoPojos(SupportSQLiteQuery query) {
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, query, true, (CancellationSignal) null);
        try {
            int _cursorIndexOfId = CursorUtil.getColumnIndex(_cursor, "id");
            int _cursorIndexOfState = CursorUtil.getColumnIndex(_cursor, Constants.ACTION_STATE);
            int _cursorIndexOfOutput = CursorUtil.getColumnIndex(_cursor, "output");
            int _cursorIndexOfRunAttemptCount = CursorUtil.getColumnIndex(_cursor, "run_attempt_count");
            ArrayMap<String, ArrayList<String>> _collectionTags = new ArrayMap<>();
            ArrayMap<String, ArrayList<Data>> _collectionProgress = new ArrayMap<>();
            while (_cursor.moveToNext()) {
                if (!_cursor.isNull(_cursorIndexOfId)) {
                    String _tmpKey = _cursor.getString(_cursorIndexOfId);
                    if (_collectionTags.get(_tmpKey) == null) {
                        _collectionTags.put(_tmpKey, new ArrayList());
                    }
                }
                if (!_cursor.isNull(_cursorIndexOfId)) {
                    String _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
                    if (_collectionProgress.get(_tmpKey_1) == null) {
                        _collectionProgress.put(_tmpKey_1, new ArrayList());
                    }
                }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipWorkTagAsjavaLangString(_collectionTags);
            __fetchRelationshipWorkProgressAsandroidxWorkData(_collectionProgress);
            List<WorkSpec.WorkInfoPojo> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                ArrayList<String> _tmpTagsCollection_1 = null;
                if (!_cursor.isNull(_cursorIndexOfId)) {
                    _tmpTagsCollection_1 = _collectionTags.get(_cursor.getString(_cursorIndexOfId));
                }
                if (_tmpTagsCollection_1 == null) {
                    _tmpTagsCollection_1 = new ArrayList<>();
                }
                ArrayList<Data> _tmpProgressCollection_1 = null;
                if (!_cursor.isNull(_cursorIndexOfId)) {
                    _tmpProgressCollection_1 = _collectionProgress.get(_cursor.getString(_cursorIndexOfId));
                }
                if (_tmpProgressCollection_1 == null) {
                    _tmpProgressCollection_1 = new ArrayList<>();
                }
                WorkSpec.WorkInfoPojo _item = new WorkSpec.WorkInfoPojo();
                if (_cursorIndexOfId != -1) {
                    _item.id = _cursor.getString(_cursorIndexOfId);
                }
                if (_cursorIndexOfState != -1) {
                    _item.state = WorkTypeConverters.intToState(_cursor.getInt(_cursorIndexOfState));
                }
                if (_cursorIndexOfOutput != -1) {
                    _item.output = Data.fromByteArray(_cursor.getBlob(_cursorIndexOfOutput));
                }
                if (_cursorIndexOfRunAttemptCount != -1) {
                    _item.runAttemptCount = _cursor.getInt(_cursorIndexOfRunAttemptCount);
                }
                _item.tags = _tmpTagsCollection_1;
                _item.progress = _tmpProgressCollection_1;
                _result.add(_item);
            }
            return _result;
        } finally {
            _cursor.close();
        }
    }

    public LiveData<List<WorkSpec.WorkInfoPojo>> getWorkInfoPojosLiveData(SupportSQLiteQuery query) {
        final SupportSQLiteQuery _internalQuery = query;
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"WorkTag", "WorkProgress", "WorkSpec"}, false, new Callable<List<WorkSpec.WorkInfoPojo>>() {
            public List<WorkSpec.WorkInfoPojo> call() {
                Cursor _cursor = DBUtil.query(RawWorkInfoDao_Impl.this.__db, _internalQuery, true, (CancellationSignal) null);
                try {
                    int _cursorIndexOfId = CursorUtil.getColumnIndex(_cursor, "id");
                    int _cursorIndexOfState = CursorUtil.getColumnIndex(_cursor, Constants.ACTION_STATE);
                    int _cursorIndexOfOutput = CursorUtil.getColumnIndex(_cursor, "output");
                    int _cursorIndexOfRunAttemptCount = CursorUtil.getColumnIndex(_cursor, "run_attempt_count");
                    ArrayMap<String, ArrayList<String>> _collectionTags = new ArrayMap<>();
                    ArrayMap<String, ArrayList<Data>> _collectionProgress = new ArrayMap<>();
                    while (_cursor.moveToNext()) {
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            String _tmpKey = _cursor.getString(_cursorIndexOfId);
                            if (_collectionTags.get(_tmpKey) == null) {
                                _collectionTags.put(_tmpKey, new ArrayList());
                            }
                        }
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            String _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
                            if (_collectionProgress.get(_tmpKey_1) == null) {
                                _collectionProgress.put(_tmpKey_1, new ArrayList());
                            }
                        }
                    }
                    _cursor.moveToPosition(-1);
                    RawWorkInfoDao_Impl.this.__fetchRelationshipWorkTagAsjavaLangString(_collectionTags);
                    RawWorkInfoDao_Impl.this.__fetchRelationshipWorkProgressAsandroidxWorkData(_collectionProgress);
                    List<WorkSpec.WorkInfoPojo> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        ArrayList<String> _tmpTagsCollection_1 = null;
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            _tmpTagsCollection_1 = _collectionTags.get(_cursor.getString(_cursorIndexOfId));
                        }
                        if (_tmpTagsCollection_1 == null) {
                            _tmpTagsCollection_1 = new ArrayList<>();
                        }
                        ArrayList<Data> _tmpProgressCollection_1 = null;
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            _tmpProgressCollection_1 = _collectionProgress.get(_cursor.getString(_cursorIndexOfId));
                        }
                        if (_tmpProgressCollection_1 == null) {
                            _tmpProgressCollection_1 = new ArrayList<>();
                        }
                        WorkSpec.WorkInfoPojo _item = new WorkSpec.WorkInfoPojo();
                        if (_cursorIndexOfId != -1) {
                            _item.id = _cursor.getString(_cursorIndexOfId);
                        }
                        if (_cursorIndexOfState != -1) {
                            _item.state = WorkTypeConverters.intToState(_cursor.getInt(_cursorIndexOfState));
                        }
                        if (_cursorIndexOfOutput != -1) {
                            _item.output = Data.fromByteArray(_cursor.getBlob(_cursorIndexOfOutput));
                        }
                        if (_cursorIndexOfRunAttemptCount != -1) {
                            _item.runAttemptCount = _cursor.getInt(_cursorIndexOfRunAttemptCount);
                        }
                        _item.tags = _tmpTagsCollection_1;
                        _item.progress = _tmpProgressCollection_1;
                        _result.add(_item);
                    }
                    return _result;
                } finally {
                    _cursor.close();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void __fetchRelationshipWorkTagAsjavaLangString(ArrayMap<String, ArrayList<String>> _map) {
        ArrayList<String> _tmpRelation;
        Set<String> __mapKeySet = _map.keySet();
        if (!__mapKeySet.isEmpty()) {
            if (_map.size() > 999) {
                ArrayMap<String, ArrayList<String>> _tmpInnerMap = new ArrayMap<>(999);
                int _tmpIndex = 0;
                int _mapIndex = 0;
                int _limit = _map.size();
                while (_mapIndex < _limit) {
                    _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
                    _mapIndex++;
                    _tmpIndex++;
                    if (_tmpIndex == 999) {
                        __fetchRelationshipWorkTagAsjavaLangString(_tmpInnerMap);
                        _tmpInnerMap = new ArrayMap<>(999);
                        _tmpIndex = 0;
                    }
                }
                if (_tmpIndex > 0) {
                    __fetchRelationshipWorkTagAsjavaLangString(_tmpInnerMap);
                    return;
                }
                return;
            }
            StringBuilder _stringBuilder = StringUtil.newStringBuilder();
            _stringBuilder.append("SELECT `tag`,`work_spec_id` FROM `WorkTag` WHERE `work_spec_id` IN (");
            int _inputSize = __mapKeySet.size();
            StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
            _stringBuilder.append(")");
            RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_stringBuilder.toString(), _inputSize + 0);
            int _argIndex = 1;
            for (String _item : __mapKeySet) {
                if (_item == null) {
                    _stmt.bindNull(_argIndex);
                } else {
                    _stmt.bindString(_argIndex, _item);
                }
                _argIndex++;
            }
            Cursor _cursor = DBUtil.query(this.__db, _stmt, false, (CancellationSignal) null);
            try {
                int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "work_spec_id");
                if (_itemKeyIndex != -1) {
                    while (_cursor.moveToNext()) {
                        if (!_cursor.isNull(_itemKeyIndex) && (_tmpRelation = _map.get(_cursor.getString(_itemKeyIndex))) != null) {
                            _tmpRelation.add(_cursor.getString(0));
                        }
                    }
                    _cursor.close();
                }
            } finally {
                _cursor.close();
            }
        }
    }

    /* access modifiers changed from: private */
    public void __fetchRelationshipWorkProgressAsandroidxWorkData(ArrayMap<String, ArrayList<Data>> _map) {
        ArrayList<Data> _tmpRelation;
        Set<String> __mapKeySet = _map.keySet();
        if (!__mapKeySet.isEmpty()) {
            if (_map.size() > 999) {
                ArrayMap<String, ArrayList<Data>> _tmpInnerMap = new ArrayMap<>(999);
                int _tmpIndex = 0;
                int _mapIndex = 0;
                int _limit = _map.size();
                while (_mapIndex < _limit) {
                    _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
                    _mapIndex++;
                    _tmpIndex++;
                    if (_tmpIndex == 999) {
                        __fetchRelationshipWorkProgressAsandroidxWorkData(_tmpInnerMap);
                        _tmpInnerMap = new ArrayMap<>(999);
                        _tmpIndex = 0;
                    }
                }
                if (_tmpIndex > 0) {
                    __fetchRelationshipWorkProgressAsandroidxWorkData(_tmpInnerMap);
                    return;
                }
                return;
            }
            StringBuilder _stringBuilder = StringUtil.newStringBuilder();
            _stringBuilder.append("SELECT `progress`,`work_spec_id` FROM `WorkProgress` WHERE `work_spec_id` IN (");
            int _inputSize = __mapKeySet.size();
            StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
            _stringBuilder.append(")");
            RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_stringBuilder.toString(), _inputSize + 0);
            int _argIndex = 1;
            for (String _item : __mapKeySet) {
                if (_item == null) {
                    _stmt.bindNull(_argIndex);
                } else {
                    _stmt.bindString(_argIndex, _item);
                }
                _argIndex++;
            }
            Cursor _cursor = DBUtil.query(this.__db, _stmt, false, (CancellationSignal) null);
            try {
                int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "work_spec_id");
                if (_itemKeyIndex != -1) {
                    while (_cursor.moveToNext()) {
                        if (!_cursor.isNull(_itemKeyIndex) && (_tmpRelation = _map.get(_cursor.getString(_itemKeyIndex))) != null) {
                            _tmpRelation.add(Data.fromByteArray(_cursor.getBlob(0)));
                        }
                    }
                    _cursor.close();
                }
            } finally {
                _cursor.close();
            }
        }
    }
}
