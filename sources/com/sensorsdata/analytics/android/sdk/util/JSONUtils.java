package com.sensorsdata.analytics.android.sdk.util;

import com.sensorsdata.analytics.android.sdk.SALog;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {
    public static String optionalStringKey(JSONObject o, String k) {
        if (!o.has(k) || o.isNull(k)) {
            return null;
        }
        return o.getString(k);
    }

    private static void addIndentBlank(StringBuilder sb, int indent) {
        int i = 0;
        while (i < indent) {
            try {
                sb.append(9);
                i++;
            } catch (Exception e) {
                SALog.printStackTrace(e);
                return;
            }
        }
    }

    public static String formatJson(String jsonStr) {
        if (jsonStr != null) {
            try {
                if (!"".equals(jsonStr)) {
                    StringBuilder sb = new StringBuilder();
                    char current = 0;
                    int indent = 0;
                    boolean isInQuotationMarks = false;
                    for (int i = 0; i < jsonStr.length(); i++) {
                        char last = current;
                        current = jsonStr.charAt(i);
                        switch (current) {
                            case '\"':
                                if (last != '\\') {
                                    isInQuotationMarks = !isInQuotationMarks;
                                }
                                sb.append(current);
                                break;
                            case ',':
                                sb.append(current);
                                if (last != '\\' && !isInQuotationMarks) {
                                    sb.append(10);
                                    addIndentBlank(sb, indent);
                                    break;
                                }
                            case '[':
                            case '{':
                                sb.append(current);
                                if (isInQuotationMarks) {
                                    break;
                                } else {
                                    sb.append(10);
                                    indent++;
                                    addIndentBlank(sb, indent);
                                    break;
                                }
                            case '\\':
                                break;
                            case ']':
                            case '}':
                                if (!isInQuotationMarks) {
                                    sb.append(10);
                                    indent--;
                                    addIndentBlank(sb, indent);
                                }
                                sb.append(current);
                                break;
                            default:
                                sb.append(current);
                                break;
                        }
                    }
                    return sb.toString();
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
                return "";
            }
        }
        return "";
    }

    public static Map<String, String> json2Map(JSONObject json) {
        if (json == null || json.length() <= 0) {
            return null;
        }
        Map<String, String> maps = new HashMap<>();
        Iterator<String> iterator = json.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            maps.put(key, json.optString(key));
        }
        return maps;
    }

    public static void mergeDistinctProperty(JSONObject source, JSONObject dest) {
        try {
            Iterator<String> superPropertiesIterator = source.keys();
            while (superPropertiesIterator.hasNext()) {
                String key = superPropertiesIterator.next();
                if (!dest.has(key)) {
                    Object value = source.get(key);
                    if (!(value instanceof Date) || "$time".equals(key)) {
                        dest.put(key, value);
                    } else {
                        dest.put(key, (Object) TimeUtils.formatDate((Date) value, Locale.CHINA));
                    }
                }
            }
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public static JSONObject cloneJsonObject(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        try {
            SADataHelper.assertPropertyTypes(jsonObject);
            JSONObject cloneProperties = new JSONObject(jsonObject.toString());
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = jsonObject.get(key);
                if (value instanceof Date) {
                    cloneProperties.put(key, (Object) new Date(((Date) value).getTime()));
                }
            }
            return cloneProperties;
        } catch (JSONException e) {
            return jsonObject;
        }
    }
}
