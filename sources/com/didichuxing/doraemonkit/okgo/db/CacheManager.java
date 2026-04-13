package com.didichuxing.doraemonkit.okgo.db;

import android.content.ContentValues;
import android.database.Cursor;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.List;

public class CacheManager extends BaseDao<CacheEntity<?>> {
    public static CacheManager getInstance() {
        return CacheManagerHolder.instance;
    }

    public static class CacheManagerHolder {
        /* access modifiers changed from: private */
        public static final CacheManager instance = new CacheManager();

        private CacheManagerHolder() {
        }
    }

    private CacheManager() {
        super(new DBHelper());
    }

    public CacheEntity<?> parseCursorToBean(Cursor cursor) {
        return CacheEntity.parseCursorToBean(cursor);
    }

    public ContentValues getContentValues(CacheEntity<?> cacheEntity) {
        return CacheEntity.getContentValues(cacheEntity);
    }

    public String getTableName() {
        return "cache";
    }

    public void unInit() {
    }

    public CacheEntity<?> get(String key) {
        if (key == null) {
            return null;
        }
        List<CacheEntity<?>> cacheEntities = query("key=?", new String[]{key});
        if (cacheEntities.size() > 0) {
            return cacheEntities.get(0);
        }
        return null;
    }

    public boolean remove(String key) {
        if (key == null) {
            return false;
        }
        return delete("key=?", new String[]{key});
    }

    public <T> CacheEntity<T> get(String key, Class<T> cls) {
        return get(key);
    }

    public List<CacheEntity<?>> getAll() {
        return queryAll();
    }

    public <T> CacheEntity<T> replace(String key, CacheEntity<T> entity) {
        entity.setKey(key);
        replace(entity);
        return entity;
    }

    public boolean clear() {
        return deleteAll();
    }
}
