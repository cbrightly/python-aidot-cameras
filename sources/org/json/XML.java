package org.json;

import com.google.maps.android.BuildConfig;
import com.tencent.bugly.Bugly;
import io.netty.util.internal.StringUtil;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;

public class XML {
    public static final Character AMP = '&';
    public static final Character APOS = '\'';
    public static final Character BANG = '!';
    public static final Character EQ = '=';
    public static final Character GT = '>';
    public static final Character LT = '<';
    public static final String NULL_ATTR = "xsi:nil";
    public static final Character QUEST = '?';
    public static final Character QUOT = Character.valueOf(StringUtil.DOUBLE_QUOTE);
    public static final Character SLASH = '/';

    private static Iterable<Integer> codePointIterator(final String string) {
        return new Iterable<Integer>() {
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    private int length;
                    private int nextIndex = 0;

                    {
                        this.length = string.length();
                    }

                    public boolean hasNext() {
                        return this.nextIndex < this.length;
                    }

                    public Integer next() {
                        int result = string.codePointAt(this.nextIndex);
                        this.nextIndex += Character.charCount(result);
                        return Integer.valueOf(result);
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    public static String escape(String string) {
        StringBuilder sb = new StringBuilder(string.length());
        for (Integer intValue : codePointIterator(string)) {
            int cp = intValue.intValue();
            switch (cp) {
                case 34:
                    sb.append("&quot;");
                    break;
                case 38:
                    sb.append("&amp;");
                    break;
                case 39:
                    sb.append("&apos;");
                    break;
                case 60:
                    sb.append("&lt;");
                    break;
                case 62:
                    sb.append("&gt;");
                    break;
                default:
                    if (!mustEscape(cp)) {
                        sb.appendCodePoint(cp);
                        break;
                    } else {
                        sb.append("&#x");
                        sb.append(Integer.toHexString(cp));
                        sb.append(';');
                        break;
                    }
            }
        }
        return sb.toString();
    }

    private static boolean mustEscape(int cp) {
        return !(!Character.isISOControl(cp) || cp == 9 || cp == 10 || cp == 13) || ((cp < 32 || cp > 55295) && ((cp < 57344 || cp > 65533) && (cp < 65536 || cp > 1114111)));
    }

    public static String unescape(String string) {
        StringBuilder sb = new StringBuilder(string.length());
        int i = 0;
        int length = string.length();
        while (i < length) {
            char c = string.charAt(i);
            if (c == '&') {
                int semic = string.indexOf(59, i);
                if (semic > i) {
                    String entity = string.substring(i + 1, semic);
                    sb.append(XMLTokener.unescapeEntity(entity));
                    i += entity.length() + 1;
                } else {
                    sb.append(c);
                }
            } else {
                sb.append(c);
            }
            i++;
        }
        return sb.toString();
    }

    public static void noSpace(String string) {
        int length = string.length();
        if (length != 0) {
            int i = 0;
            while (i < length) {
                if (!Character.isWhitespace(string.charAt(i))) {
                    i++;
                } else {
                    throw new JSONException("'" + string + "' contains a space character.");
                }
            }
            return;
        }
        throw new JSONException("Empty string.");
    }

    private static boolean parse(XMLTokener x, JSONObject context, String name, XMLParserConfiguration config) {
        Object obj;
        Object obj2;
        Object token = x.nextToken();
        if (token == BANG) {
            char c = x.next();
            if (c == '-') {
                if (x.next() == '-') {
                    x.skipPast("-->");
                    return false;
                }
                x.back();
            } else if (c == '[') {
                if (!"CDATA".equals(x.nextToken()) || x.next() != '[') {
                    throw x.syntaxError("Expected 'CDATA['");
                }
                String string = x.nextCDATA();
                if (string.length() > 0) {
                    context.accumulate(config.cDataTagName, string);
                }
                return false;
            }
            int i = 1;
            do {
                Object token2 = x.nextMeta();
                if (token2 == null) {
                    throw x.syntaxError("Missing '>' after '<!'.");
                } else if (token2 == LT) {
                    i++;
                    continue;
                } else if (token2 == GT) {
                    i--;
                    continue;
                } else {
                    continue;
                }
            } while (i > 0);
            return false;
        } else if (token == QUEST) {
            x.skipPast("?>");
            return false;
        } else if (token == SLASH) {
            Object token3 = x.nextToken();
            if (name == null) {
                throw x.syntaxError("Mismatched close tag " + token3);
            } else if (!token3.equals(name)) {
                throw x.syntaxError("Mismatched " + name + " and " + token3);
            } else if (x.nextToken() == GT) {
                return true;
            } else {
                throw x.syntaxError("Misshaped close tag");
            }
        } else if (!(token instanceof Character)) {
            String tagName = (String) token;
            Object token4 = null;
            JSONObject jsonObject = new JSONObject();
            boolean nilAttributeFound = false;
            while (true) {
                if (token4 == null) {
                    token4 = x.nextToken();
                }
                if (token4 instanceof String) {
                    String string2 = (String) token4;
                    token4 = x.nextToken();
                    if (token4 == EQ) {
                        Object token5 = x.nextToken();
                        if (token5 instanceof String) {
                            if (config.convertNilAttributeToNull && NULL_ATTR.equals(string2) && Boolean.parseBoolean((String) token5)) {
                                nilAttributeFound = true;
                            } else if (!nilAttributeFound) {
                                if (config.keepStrings) {
                                    obj2 = (String) token5;
                                } else {
                                    obj2 = stringToValue((String) token5);
                                }
                                jsonObject.accumulate(string2, obj2);
                            }
                            token4 = null;
                        } else {
                            throw x.syntaxError("Missing value");
                        }
                    } else {
                        jsonObject.accumulate(string2, "");
                    }
                } else if (token4 == SLASH) {
                    if (x.nextToken() == GT) {
                        if (nilAttributeFound) {
                            context.accumulate(tagName, JSONObject.NULL);
                        } else if (jsonObject.length() > 0) {
                            context.accumulate(tagName, jsonObject);
                        } else {
                            context.accumulate(tagName, "");
                        }
                        return false;
                    }
                    throw x.syntaxError("Misshaped tag");
                } else if (token4 == GT) {
                    while (true) {
                        Object token6 = x.nextContent();
                        if (token6 == null) {
                            if (tagName == null) {
                                return false;
                            }
                            throw x.syntaxError("Unclosed tag " + tagName);
                        } else if (token6 instanceof String) {
                            String string3 = (String) token6;
                            if (string3.length() > 0) {
                                String str = config.cDataTagName;
                                if (config.keepStrings) {
                                    obj = string3;
                                } else {
                                    obj = stringToValue(string3);
                                }
                                jsonObject.accumulate(str, obj);
                            }
                        } else if (token6 == LT && parse(x, jsonObject, tagName, config)) {
                            if (jsonObject.length() == 0) {
                                context.accumulate(tagName, "");
                            } else if (jsonObject.length() != 1 || jsonObject.opt(config.cDataTagName) == null) {
                                context.accumulate(tagName, jsonObject);
                            } else {
                                context.accumulate(tagName, jsonObject.opt(config.cDataTagName));
                            }
                            return false;
                        }
                    }
                } else {
                    throw x.syntaxError("Misshaped tag");
                }
            }
        } else {
            throw x.syntaxError("Misshaped tag");
        }
    }

    public static Object stringToValue(String string) {
        if (string.equals("")) {
            return string;
        }
        if (string.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (string.equalsIgnoreCase(Bugly.SDK_IS_DEV)) {
            return Boolean.FALSE;
        }
        if (string.equalsIgnoreCase(BuildConfig.TRAVIS)) {
            return JSONObject.NULL;
        }
        char initial = string.charAt(0);
        if ((initial >= '0' && initial <= '9') || initial == '-') {
            try {
                if (string.indexOf(46) <= -1 && string.indexOf(101) <= -1 && string.indexOf(69) <= -1) {
                    if (!"-0".equals(string)) {
                        Long myLong = Long.valueOf(string);
                        if (string.equals(myLong.toString())) {
                            if (myLong.longValue() == ((long) myLong.intValue())) {
                                return Integer.valueOf(myLong.intValue());
                            }
                            return myLong;
                        }
                    }
                }
                Double d = Double.valueOf(string);
                if (d.isInfinite() || d.isNaN()) {
                    return string;
                }
                return d;
            } catch (Exception e) {
            }
        }
        return string;
    }

    public static JSONObject toJSONObject(String string) {
        return toJSONObject(string, XMLParserConfiguration.ORIGINAL);
    }

    public static JSONObject toJSONObject(Reader reader) {
        return toJSONObject(reader, XMLParserConfiguration.ORIGINAL);
    }

    public static JSONObject toJSONObject(Reader reader, boolean keepStrings) {
        if (keepStrings) {
            return toJSONObject(reader, XMLParserConfiguration.KEEP_STRINGS);
        }
        return toJSONObject(reader, XMLParserConfiguration.ORIGINAL);
    }

    public static JSONObject toJSONObject(Reader reader, XMLParserConfiguration config) {
        JSONObject jo = new JSONObject();
        XMLTokener x = new XMLTokener(reader);
        while (x.more()) {
            x.skipPast("<");
            if (x.more()) {
                parse(x, jo, (String) null, config);
            }
        }
        return jo;
    }

    public static JSONObject toJSONObject(String string, boolean keepStrings) {
        return toJSONObject((Reader) new StringReader(string), keepStrings);
    }

    public static JSONObject toJSONObject(String string, XMLParserConfiguration config) {
        return toJSONObject((Reader) new StringReader(string), config);
    }

    public static String toString(Object object) {
        return toString(object, (String) null, XMLParserConfiguration.ORIGINAL);
    }

    public static String toString(Object object, String tagName) {
        return toString(object, tagName, XMLParserConfiguration.ORIGINAL);
    }

    public static String toString(Object object, String tagName, XMLParserConfiguration config) {
        JSONArray ja;
        Object obj = object;
        String str = tagName;
        XMLParserConfiguration xMLParserConfiguration = config;
        StringBuilder sb = new StringBuilder();
        if (obj instanceof JSONObject) {
            char c = '<';
            if (str != null) {
                sb.append('<');
                sb.append(str);
                sb.append('>');
            }
            JSONObject jo = (JSONObject) obj;
            for (String key : jo.keySet()) {
                Object value = jo.opt(key);
                if (value == null) {
                    value = "";
                } else if (value.getClass().isArray()) {
                    value = new JSONArray(value);
                }
                if (key.equals(xMLParserConfiguration.cDataTagName)) {
                    if (value instanceof JSONArray) {
                        JSONArray ja2 = (JSONArray) value;
                        int jaLength = ja2.length();
                        for (int i = 0; i < jaLength; i++) {
                            if (i > 0) {
                                sb.append(10);
                            }
                            sb.append(escape(ja2.opt(i).toString()));
                        }
                    } else {
                        sb.append(escape(value.toString()));
                    }
                } else if (value instanceof JSONArray) {
                    JSONArray ja3 = (JSONArray) value;
                    int jaLength2 = ja3.length();
                    int i2 = 0;
                    while (i2 < jaLength2) {
                        Object val = ja3.opt(i2);
                        if (val instanceof JSONArray) {
                            sb.append(c);
                            sb.append(key);
                            sb.append('>');
                            sb.append(toString(val, (String) null, xMLParserConfiguration));
                            sb.append("</");
                            sb.append(key);
                            sb.append('>');
                        } else {
                            sb.append(toString(val, key, xMLParserConfiguration));
                        }
                        i2++;
                        c = '<';
                    }
                    c = '<';
                } else if ("".equals(value)) {
                    c = '<';
                    sb.append('<');
                    sb.append(key);
                    sb.append("/>");
                } else {
                    c = '<';
                    sb.append(toString(value, key, xMLParserConfiguration));
                }
            }
            if (str != null) {
                sb.append("</");
                sb.append(str);
                sb.append('>');
            }
            return sb.toString();
        } else if (obj == null || (!(obj instanceof JSONArray) && !object.getClass().isArray())) {
            String string = obj == null ? BuildConfig.TRAVIS : escape(object.toString());
            if (str == null) {
                return "\"" + string + "\"";
            } else if (string.length() == 0) {
                return "<" + str + "/>";
            } else {
                return "<" + str + ">" + string + "</" + str + ">";
            }
        } else {
            if (object.getClass().isArray()) {
                ja = new JSONArray(obj);
            } else {
                ja = (JSONArray) obj;
            }
            int jaLength3 = ja.length();
            for (int i3 = 0; i3 < jaLength3; i3++) {
                sb.append(toString(ja.opt(i3), str == null ? "array" : str, xMLParserConfiguration));
            }
            return sb.toString();
        }
    }
}
