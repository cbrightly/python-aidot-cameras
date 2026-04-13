package com.leedarson.serviceinterface;

import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;
import com.google.gson.JsonArray;

public interface DatabaseService extends c {
    void closeDatabase();

    void execSQL(String str);

    void handleData(String str, String str2, String str3);

    /* synthetic */ void init(Context context);

    void preCreateDb();

    JsonArray rawQuery(String str);
}
