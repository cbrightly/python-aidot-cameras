package com.didichuxing.doraemonkit.util;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Iterator;

public class JsonUtil {
    private static final String TAG = "JsonUtil";
    private static final Gson gson = new GsonBuilder().create();

    public static String jsonFromObject(Object object) {
        if (object == null) {
            return "{}";
        }
        try {
            return gson.toJson(object);
        } catch (Throwable e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public static <T> T objectFromJson(String json, Class<T> klass) {
        if (TextUtils.isEmpty(json) || klass == null) {
            return null;
        }
        try {
            return gson.fromJson(json, klass);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> ArrayList<T> jsonToList(String json, Class<T> classOfT) {
        ArrayList<T> listOfT = new ArrayList<>();
        if (classOfT.isPrimitive()) {
            Iterator<JsonPrimitive> it = ((ArrayList) gson.fromJson(json, new TypeToken<ArrayList<JsonPrimitive>>() {
            }.getType())).iterator();
            while (it.hasNext()) {
                listOfT.add(gson.fromJson((JsonElement) it.next(), classOfT));
            }
        } else {
            Iterator<JsonObject> it2 = ((ArrayList) gson.fromJson(json, new TypeToken<ArrayList<JsonObject>>() {
            }.getType())).iterator();
            while (it2.hasNext()) {
                listOfT.add(gson.fromJson((JsonElement) it2.next(), classOfT));
            }
        }
        return listOfT;
    }

    public static boolean isEmpty(String json) {
        if (!TextUtils.isEmpty(json) && !TextUtils.equals(json, "{}")) {
            return false;
        }
        return true;
    }
}
