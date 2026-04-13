package org.json;

import com.didichuxing.doraemonkit.constant.SpInputType;
import com.meituan.robust.Constants;
import com.tencent.bugly.Bugly;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JSONArray implements Iterable<Object> {
    private final ArrayList<Object> myArrayList;

    public JSONArray() {
        this.myArrayList = new ArrayList<>();
    }

    public JSONArray(JSONTokener x) {
        this();
        if (x.nextClean() == '[') {
            char nextChar = x.nextClean();
            if (nextChar == 0) {
                throw x.syntaxError("Expected a ',' or ']'");
            } else if (nextChar != ']') {
                x.back();
                while (true) {
                    if (x.nextClean() == ',') {
                        x.back();
                        this.myArrayList.add(JSONObject.NULL);
                    } else {
                        x.back();
                        this.myArrayList.add(x.nextValue());
                    }
                    switch (x.nextClean()) {
                        case 0:
                            throw x.syntaxError("Expected a ',' or ']'");
                        case ',':
                            char nextChar2 = x.nextClean();
                            if (nextChar2 == 0) {
                                throw x.syntaxError("Expected a ',' or ']'");
                            } else if (nextChar2 != ']') {
                                x.back();
                            } else {
                                return;
                            }
                        case ']':
                            return;
                        default:
                            throw x.syntaxError("Expected a ',' or ']'");
                    }
                }
            }
        } else {
            throw x.syntaxError("A JSONArray text must start with '['");
        }
    }

    public JSONArray(String source) {
        this(new JSONTokener(source));
    }

    public JSONArray(Collection<?> collection) {
        if (collection == null) {
            this.myArrayList = new ArrayList<>();
            return;
        }
        this.myArrayList = new ArrayList<>(collection.size());
        for (Object o : collection) {
            this.myArrayList.add(JSONObject.wrap(o));
        }
    }

    public JSONArray(Object array) {
        this();
        if (array.getClass().isArray()) {
            int length = Array.getLength(array);
            this.myArrayList.ensureCapacity(length);
            for (int i = 0; i < length; i++) {
                put(JSONObject.wrap(Array.get(array, i)));
            }
            return;
        }
        throw new JSONException("JSONArray initial value should be a string or collection or array.");
    }

    public Iterator<Object> iterator() {
        return this.myArrayList.iterator();
    }

    public Object get(int index) {
        Object object = opt(index);
        if (object != null) {
            return object;
        }
        throw new JSONException("JSONArray[" + index + "] not found.");
    }

    public boolean getBoolean(int index) {
        Object object = get(index);
        if (object.equals(Boolean.FALSE)) {
            return false;
        }
        if ((object instanceof String) && ((String) object).equalsIgnoreCase(Bugly.SDK_IS_DEV)) {
            return false;
        }
        if (object.equals(Boolean.TRUE)) {
            return true;
        }
        if ((object instanceof String) && ((String) object).equalsIgnoreCase("true")) {
            return true;
        }
        throw wrongValueFormatException(index, "boolean", (Throwable) null);
    }

    public double getDouble(int index) {
        Object object = get(index);
        if (object instanceof Number) {
            return ((Number) object).doubleValue();
        }
        try {
            return Double.parseDouble(object.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(index, Constants.DOUBLE, e);
        }
    }

    public float getFloat(int index) {
        Object object = get(index);
        if (object instanceof Number) {
            return ((Float) object).floatValue();
        }
        try {
            return Float.parseFloat(object.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(index, "float", e);
        }
    }

    public Number getNumber(int index) {
        Object object = get(index);
        try {
            if (object instanceof Number) {
                return (Number) object;
            }
            return JSONObject.stringToNumber(object.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(index, "number", e);
        }
    }

    public <E extends Enum<E>> E getEnum(Class<E> clazz, int index) {
        E val = optEnum(clazz, index);
        if (val != null) {
            return val;
        }
        throw wrongValueFormatException(index, "enum of type " + JSONObject.quote(clazz.getSimpleName()), (Throwable) null);
    }

    public BigDecimal getBigDecimal(int index) {
        Object object = get(index);
        BigDecimal val = JSONObject.objectToBigDecimal(object, (BigDecimal) null);
        if (val != null) {
            return val;
        }
        throw wrongValueFormatException(index, "BigDecimal", object, (Throwable) null);
    }

    public BigInteger getBigInteger(int index) {
        Object object = get(index);
        BigInteger val = JSONObject.objectToBigInteger(object, (BigInteger) null);
        if (val != null) {
            return val;
        }
        throw wrongValueFormatException(index, "BigInteger", object, (Throwable) null);
    }

    public int getInt(int index) {
        Object object = get(index);
        if (object instanceof Number) {
            return ((Number) object).intValue();
        }
        try {
            return Integer.parseInt(object.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(index, Constants.INT, e);
        }
    }

    public JSONArray getJSONArray(int index) {
        Object object = get(index);
        if (object instanceof JSONArray) {
            return (JSONArray) object;
        }
        throw wrongValueFormatException(index, "JSONArray", (Throwable) null);
    }

    public JSONObject getJSONObject(int index) {
        Object object = get(index);
        if (object instanceof JSONObject) {
            return (JSONObject) object;
        }
        throw wrongValueFormatException(index, "JSONObject", (Throwable) null);
    }

    public long getLong(int index) {
        Object object = get(index);
        if (object instanceof Number) {
            return ((Number) object).longValue();
        }
        try {
            return Long.parseLong(object.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(index, Constants.LONG, e);
        }
    }

    public String getString(int index) {
        Object object = get(index);
        if (object instanceof String) {
            return (String) object;
        }
        throw wrongValueFormatException(index, SpInputType.STRING, (Throwable) null);
    }

    public boolean isNull(int index) {
        return JSONObject.NULL.equals(opt(index));
    }

    public String join(String separator) {
        int len = length();
        if (len == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(JSONObject.valueToString(this.myArrayList.get(0)));
        for (int i = 1; i < len; i++) {
            sb.append(separator);
            sb.append(JSONObject.valueToString(this.myArrayList.get(i)));
        }
        return sb.toString();
    }

    public int length() {
        return this.myArrayList.size();
    }

    public Object opt(int index) {
        if (index < 0 || index >= length()) {
            return null;
        }
        return this.myArrayList.get(index);
    }

    public boolean optBoolean(int index) {
        return optBoolean(index, false);
    }

    public boolean optBoolean(int index, boolean defaultValue) {
        try {
            return getBoolean(index);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public double optDouble(int index) {
        return optDouble(index, Double.NaN);
    }

    public double optDouble(int index, double defaultValue) {
        Number val = optNumber(index, (Number) null);
        if (val == null) {
            return defaultValue;
        }
        return val.doubleValue();
    }

    public float optFloat(int index) {
        return optFloat(index, Float.NaN);
    }

    public float optFloat(int index, float defaultValue) {
        Number val = optNumber(index, (Number) null);
        if (val == null) {
            return defaultValue;
        }
        return val.floatValue();
    }

    public int optInt(int index) {
        return optInt(index, 0);
    }

    public int optInt(int index, int defaultValue) {
        Number val = optNumber(index, (Number) null);
        if (val == null) {
            return defaultValue;
        }
        return val.intValue();
    }

    public <E extends Enum<E>> E optEnum(Class<E> clazz, int index) {
        return optEnum(clazz, index, (Enum) null);
    }

    public <E extends Enum<E>> E optEnum(Class<E> clazz, int index, E defaultValue) {
        try {
            E opt = opt(index);
            if (JSONObject.NULL.equals(opt)) {
                return defaultValue;
            }
            if (clazz.isAssignableFrom(opt.getClass())) {
                return (Enum) opt;
            }
            return Enum.valueOf(clazz, opt.toString());
        } catch (IllegalArgumentException e) {
            return defaultValue;
        } catch (NullPointerException e2) {
            return defaultValue;
        }
    }

    public BigInteger optBigInteger(int index, BigInteger defaultValue) {
        return JSONObject.objectToBigInteger(opt(index), defaultValue);
    }

    public BigDecimal optBigDecimal(int index, BigDecimal defaultValue) {
        return JSONObject.objectToBigDecimal(opt(index), defaultValue);
    }

    public JSONArray optJSONArray(int index) {
        Object o = opt(index);
        if (o instanceof JSONArray) {
            return (JSONArray) o;
        }
        return null;
    }

    public JSONObject optJSONObject(int index) {
        Object o = opt(index);
        if (o instanceof JSONObject) {
            return (JSONObject) o;
        }
        return null;
    }

    public long optLong(int index) {
        return optLong(index, 0);
    }

    public long optLong(int index, long defaultValue) {
        Number val = optNumber(index, (Number) null);
        if (val == null) {
            return defaultValue;
        }
        return val.longValue();
    }

    public Number optNumber(int index) {
        return optNumber(index, (Number) null);
    }

    public Number optNumber(int index, Number defaultValue) {
        Object val = opt(index);
        if (JSONObject.NULL.equals(val)) {
            return defaultValue;
        }
        if (val instanceof Number) {
            return (Number) val;
        }
        if (!(val instanceof String)) {
            return defaultValue;
        }
        try {
            return JSONObject.stringToNumber((String) val);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public String optString(int index) {
        return optString(index, "");
    }

    public String optString(int index, String defaultValue) {
        Object object = opt(index);
        if (JSONObject.NULL.equals(object)) {
            return defaultValue;
        }
        return object.toString();
    }

    public JSONArray put(boolean value) {
        return put((Object) value ? Boolean.TRUE : Boolean.FALSE);
    }

    public JSONArray put(Collection<?> value) {
        return put((Object) new JSONArray(value));
    }

    public JSONArray put(double value) {
        return put((Object) Double.valueOf(value));
    }

    public JSONArray put(float value) {
        return put((Object) Float.valueOf(value));
    }

    public JSONArray put(int value) {
        return put((Object) Integer.valueOf(value));
    }

    public JSONArray put(long value) {
        return put((Object) Long.valueOf(value));
    }

    public JSONArray put(Map<?, ?> value) {
        return put((Object) new JSONObject(value));
    }

    public JSONArray put(Object value) {
        JSONObject.testValidity(value);
        this.myArrayList.add(value);
        return this;
    }

    public JSONArray put(int index, boolean value) {
        return put(index, (Object) value ? Boolean.TRUE : Boolean.FALSE);
    }

    public JSONArray put(int index, Collection<?> value) {
        return put(index, (Object) new JSONArray(value));
    }

    public JSONArray put(int index, double value) {
        return put(index, (Object) Double.valueOf(value));
    }

    public JSONArray put(int index, float value) {
        return put(index, (Object) Float.valueOf(value));
    }

    public JSONArray put(int index, int value) {
        return put(index, (Object) Integer.valueOf(value));
    }

    public JSONArray put(int index, long value) {
        return put(index, (Object) Long.valueOf(value));
    }

    public JSONArray put(int index, Map<?, ?> value) {
        put(index, (Object) new JSONObject(value));
        return this;
    }

    public JSONArray put(int index, Object value) {
        if (index < 0) {
            throw new JSONException("JSONArray[" + index + "] not found.");
        } else if (index < length()) {
            JSONObject.testValidity(value);
            this.myArrayList.set(index, value);
            return this;
        } else if (index == length()) {
            return put(value);
        } else {
            this.myArrayList.ensureCapacity(index + 1);
            while (index != length()) {
                this.myArrayList.add(JSONObject.NULL);
            }
            return put(value);
        }
    }

    public Object query(String jsonPointer) {
        return query(new JSONPointer(jsonPointer));
    }

    public Object query(JSONPointer jsonPointer) {
        return jsonPointer.queryFrom(this);
    }

    public Object optQuery(String jsonPointer) {
        return optQuery(new JSONPointer(jsonPointer));
    }

    public Object optQuery(JSONPointer jsonPointer) {
        try {
            return jsonPointer.queryFrom(this);
        } catch (JSONPointerException e) {
            return null;
        }
    }

    public Object remove(int index) {
        if (index < 0 || index >= length()) {
            return null;
        }
        return this.myArrayList.remove(index);
    }

    public boolean similar(Object other) {
        int len;
        if (!(other instanceof JSONArray) || (len = length()) != ((JSONArray) other).length()) {
            return false;
        }
        for (int i = 0; i < len; i++) {
            Object valueThis = this.myArrayList.get(i);
            Object valueOther = ((JSONArray) other).myArrayList.get(i);
            if (valueThis != valueOther) {
                if (valueThis == null) {
                    return false;
                }
                if (valueThis instanceof JSONObject) {
                    if (!((JSONObject) valueThis).similar(valueOther)) {
                        return false;
                    }
                } else if (valueThis instanceof JSONArray) {
                    if (!((JSONArray) valueThis).similar(valueOther)) {
                        return false;
                    }
                } else if (!valueThis.equals(valueOther)) {
                    return false;
                }
            }
        }
        return true;
    }

    public JSONObject toJSONObject(JSONArray names) {
        if (names == null || names.isEmpty() || isEmpty()) {
            return null;
        }
        JSONObject jo = new JSONObject(names.length());
        for (int i = 0; i < names.length(); i++) {
            jo.put(names.getString(i), opt(i));
        }
        return jo;
    }

    public String toString() {
        try {
            return toString(0);
        } catch (Exception e) {
            return null;
        }
    }

    public String toString(int indentFactor) {
        String obj;
        StringWriter sw = new StringWriter();
        synchronized (sw.getBuffer()) {
            obj = write(sw, indentFactor, 0).toString();
        }
        return obj;
    }

    public Writer write(Writer writer) {
        return write(writer, 0, 0);
    }

    public Writer write(Writer writer, int indentFactor, int indent) {
        int i;
        boolean needsComma = false;
        try {
            int length = length();
            writer.write(91);
            if (length == 1) {
                JSONObject.writeValue(writer, this.myArrayList.get(0), indentFactor, indent);
            } else if (length != 0) {
                int newIndent = indent + indentFactor;
                i = 0;
                while (i < length) {
                    if (needsComma) {
                        writer.write(44);
                    }
                    if (indentFactor > 0) {
                        writer.write(10);
                    }
                    JSONObject.indent(writer, newIndent);
                    JSONObject.writeValue(writer, this.myArrayList.get(i), indentFactor, newIndent);
                    needsComma = true;
                    i++;
                }
                if (indentFactor > 0) {
                    writer.write(10);
                }
                JSONObject.indent(writer, indent);
            }
            writer.write(93);
            return writer;
        } catch (Exception e) {
            throw new JSONException("Unable to write JSONArray value at index: " + i, e);
        } catch (Exception e2) {
            throw new JSONException("Unable to write JSONArray value at index: 0", e2);
        } catch (IOException e3) {
            throw new JSONException((Throwable) e3);
        }
    }

    public List<Object> toList() {
        List<Object> results = new ArrayList<>(this.myArrayList.size());
        Iterator<Object> it = this.myArrayList.iterator();
        while (it.hasNext()) {
            Object element = it.next();
            if (element == null || JSONObject.NULL.equals(element)) {
                results.add((Object) null);
            } else if (element instanceof JSONArray) {
                results.add(((JSONArray) element).toList());
            } else if (element instanceof JSONObject) {
                results.add(((JSONObject) element).toMap());
            } else {
                results.add(element);
            }
        }
        return results;
    }

    public boolean isEmpty() {
        return this.myArrayList.isEmpty();
    }

    private static JSONException wrongValueFormatException(int idx, String valueType, Throwable cause) {
        return new JSONException("JSONArray[" + idx + "] is not a " + valueType + ".", cause);
    }

    private static JSONException wrongValueFormatException(int idx, String valueType, Object value, Throwable cause) {
        return new JSONException("JSONArray[" + idx + "] is not a " + valueType + " (" + value + ").", cause);
    }
}
