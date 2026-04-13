package com.sensorsdata.analytics.android.sdk.util;

import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SADataHelper {
    private static final Pattern KEY_PATTERN = Pattern.compile("^((?!^distinct_id$|^original_id$|^time$|^properties$|^id$|^first_id$|^second_id$|^users$|^events$|^event$|^user_id$|^date$|^datetime$|^user_tag.*|^user_group.*)[a-zA-Z_$][a-zA-Z\\d_$]*)$", 2);
    private static final int MAX_LENGTH_100 = 100;
    public static final int MAX_LENGTH_1024 = 1024;
    private static final String TAG = "SA.SADataHelper";

    public static void assertPropertyTypes(JSONObject properties) {
        if (properties != null) {
            Iterator<String> iterator = properties.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                try {
                    if (!assertPropertyKey(key)) {
                        iterator.remove();
                    } else {
                        Object value = properties.get(key);
                        if (value == JSONObject.NULL) {
                            SALog.i(TAG, "Property value is empty");
                            iterator.remove();
                        } else {
                            if (value instanceof List) {
                                List<?> list = (List) value;
                                int size = list.size();
                                JSONArray array = new JSONArray();
                                for (int i = 0; i < size; i++) {
                                    array.put((Object) list.get(i));
                                }
                                value = array;
                                properties.put(key, value);
                            }
                            if (!(value instanceof CharSequence)) {
                                if (!(value instanceof Number) && !(value instanceof JSONArray) && !(value instanceof Boolean)) {
                                    if (!(value instanceof Date)) {
                                        throw new InvalidDataException("The property value must be an instance of CharSequence/Number/Boolean/JSONArray/Date/List<String>. [key='" + key + "', value='" + value.toString() + "', class='" + value.getClass().getCanonicalName() + "']");
                                    }
                                }
                            }
                            if (value instanceof JSONArray) {
                                JSONArray array2 = (JSONArray) value;
                                int size2 = array2.length();
                                int i2 = 0;
                                while (i2 < size2) {
                                    if (array2.get(i2) instanceof CharSequence) {
                                        i2++;
                                    } else {
                                        throw new InvalidDataException("The array property value must be an instance of List<String> or JSONArray only contains String. [key='" + key + "', value='" + value.toString() + "']");
                                    }
                                }
                            } else if ((value instanceof String) && ((String) value).length() > 1024) {
                                SALog.i(TAG, value + " length is longer than " + 1024);
                            }
                        }
                    }
                } catch (JSONException e) {
                    throw new InvalidDataException("Unexpected property key. [key='" + key + "']");
                }
            }
        }
    }

    public static void assertEventName(String key) {
        if (TextUtils.isEmpty(key)) {
            SALog.i(TAG, "EventName is empty");
        } else if (key.length() > 100) {
            SALog.i(TAG, key + "'s length is longer than " + 100);
        } else if (!KEY_PATTERN.matcher(key).matches()) {
            SALog.i(TAG, key + " is invalid");
        }
    }

    public static boolean assertPropertyKey(String key) {
        if (TextUtils.isEmpty(key)) {
            SALog.i(TAG, "Property key is empty");
            return false;
        } else if (!KEY_PATTERN.matcher(key).matches()) {
            SALog.i(TAG, key + " is invalid");
            return false;
        } else if (key.length() <= 100) {
            return true;
        } else {
            SALog.i(TAG, key + "'s length is longer than " + 100);
            return true;
        }
    }

    public static void assertItemId(String key) {
        if (key == null) {
            SALog.i(TAG, "ItemId is empty");
        } else if (key.length() > 1024) {
            SALog.i(TAG, key + "'s length is longer than " + 1024);
        }
    }

    public static void assertDistinctId(String value) {
        if (TextUtils.isEmpty(value)) {
            throw new InvalidDataException("Id is empty");
        } else if (value.length() > 1024) {
            SALog.i(TAG, value + "'s length is longer than " + 1024);
        }
    }

    public static String assertPropertyValue(String property) {
        if (property == null) {
            SALog.i(TAG, "Property value is empty");
            return property;
        }
        if (property.length() > 1024) {
            SALog.i(TAG, property + "'s length is longer than " + 1024);
        }
        return property;
    }

    public static JSONObject appendLibMethodAutoTrack(JSONObject jsonObject) {
        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }
        try {
            jsonObject.put("$lib_method", (Object) "autoTrack");
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
        return jsonObject;
    }
}
