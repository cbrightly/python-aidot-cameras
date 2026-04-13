package com.didichuxing.doraemonkit.kit.network.room_db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MockInterceptApiBean.class, MockTemplateApiBean.class}, exportSchema = false, version = 2)
public abstract class DokitDatabase extends RoomDatabase {
    /* access modifiers changed from: package-private */
    public abstract MockApiDao mockApiDao();
}
