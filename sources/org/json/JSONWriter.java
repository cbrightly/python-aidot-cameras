package org.json;

import com.google.maps.android.BuildConfig;
import com.meituan.robust.Constants;
import com.tencent.bugly.Bugly;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class JSONWriter {
    private static final int maxdepth = 200;
    private boolean comma = false;
    protected char mode = 'i';
    private final JSONObject[] stack = new JSONObject[200];
    private int top = 0;
    protected Appendable writer;

    public JSONWriter(Appendable w) {
        this.writer = w;
    }

    private JSONWriter append(String string) {
        if (string != null) {
            char c = this.mode;
            if (c == 'o' || c == 'a') {
                try {
                    if (this.comma && c == 'a') {
                        this.writer.append(StringUtil.COMMA);
                    }
                    this.writer.append(string);
                    if (this.mode == 'o') {
                        this.mode = 'k';
                    }
                    this.comma = true;
                    return this;
                } catch (IOException e) {
                    throw new JSONException((Throwable) e);
                }
            } else {
                throw new JSONException("Value out of sequence.");
            }
        } else {
            throw new JSONException("Null pointer");
        }
    }

    public JSONWriter array() {
        char c = this.mode;
        if (c == 'i' || c == 'o' || c == 'a') {
            push((JSONObject) null);
            append(Constants.ARRAY_TYPE);
            this.comma = false;
            return this;
        }
        throw new JSONException("Misplaced array.");
    }

    private JSONWriter end(char m, char c) {
        if (this.mode != m) {
            throw new JSONException(m == 'a' ? "Misplaced endArray." : "Misplaced endObject.");
        }
        pop(m);
        try {
            this.writer.append(c);
            this.comma = true;
            return this;
        } catch (IOException e) {
            throw new JSONException((Throwable) e);
        }
    }

    public JSONWriter endArray() {
        return end('a', ']');
    }

    public JSONWriter endObject() {
        return end('k', '}');
    }

    public JSONWriter key(String string) {
        if (string == null) {
            throw new JSONException("Null key.");
        } else if (this.mode == 'k') {
            try {
                JSONObject topObject = this.stack[this.top - 1];
                if (!topObject.has(string)) {
                    topObject.put(string, true);
                    if (this.comma) {
                        this.writer.append(StringUtil.COMMA);
                    }
                    this.writer.append(JSONObject.quote(string));
                    this.writer.append(':');
                    this.comma = false;
                    this.mode = 'o';
                    return this;
                }
                throw new JSONException("Duplicate key \"" + string + "\"");
            } catch (IOException e) {
                throw new JSONException((Throwable) e);
            }
        } else {
            throw new JSONException("Misplaced key.");
        }
    }

    public JSONWriter object() {
        if (this.mode == 'i') {
            this.mode = 'o';
        }
        char c = this.mode;
        if (c == 'o' || c == 'a') {
            append("{");
            push(new JSONObject());
            this.comma = false;
            return this;
        }
        throw new JSONException("Misplaced object.");
    }

    private void pop(char c) {
        int i = this.top;
        if (i > 0) {
            JSONObject[] jSONObjectArr = this.stack;
            char c2 = 'a';
            if ((jSONObjectArr[i + -1] == null ? 'a' : 'k') == c) {
                int i2 = i - 1;
                this.top = i2;
                if (i2 == 0) {
                    c2 = 'd';
                } else if (jSONObjectArr[i2 - 1] != null) {
                    c2 = 'k';
                }
                this.mode = c2;
                return;
            }
            throw new JSONException("Nesting error.");
        }
        throw new JSONException("Nesting error.");
    }

    private void push(JSONObject jo) {
        int i = this.top;
        if (i < 200) {
            this.stack[i] = jo;
            this.mode = jo == null ? 'a' : 'k';
            this.top = i + 1;
            return;
        }
        throw new JSONException("Nesting too deep.");
    }

    public static String valueToString(Object value) {
        if (value == null || value.equals((Object) null)) {
            return BuildConfig.TRAVIS;
        }
        if (value instanceof JSONString) {
            try {
                String object = ((JSONString) value).toJSONString();
                if (object != null) {
                    return object;
                }
                throw new JSONException("Bad value from toJSONString: " + object);
            } catch (Exception e) {
                throw new JSONException((Throwable) e);
            }
        } else if (value instanceof Number) {
            String numberAsString = JSONObject.numberToString((Number) value);
            if (JSONObject.NUMBER_PATTERN.matcher(numberAsString).matches()) {
                return numberAsString;
            }
            return JSONObject.quote(numberAsString);
        } else if ((value instanceof Boolean) || (value instanceof JSONObject) || (value instanceof JSONArray)) {
            return value.toString();
        } else {
            if (value instanceof Map) {
                return new JSONObject((Map) value).toString();
            }
            if (value instanceof Collection) {
                return new JSONArray((Collection) value).toString();
            }
            if (value.getClass().isArray()) {
                return new JSONArray(value).toString();
            }
            if (value instanceof Enum) {
                return JSONObject.quote(((Enum) value).name());
            }
            return JSONObject.quote(value.toString());
        }
    }

    public JSONWriter value(boolean b) {
        return append(b ? "true" : Bugly.SDK_IS_DEV);
    }

    public JSONWriter value(double d) {
        return value((Object) Double.valueOf(d));
    }

    public JSONWriter value(long l) {
        return append(Long.toString(l));
    }

    public JSONWriter value(Object object) {
        return append(valueToString(object));
    }
}
