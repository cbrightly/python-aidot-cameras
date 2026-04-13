package org.json;

import io.netty.util.internal.StringUtil;

public class JSONML {
    private static Object parse(XMLTokener x, boolean arrayForm, JSONArray ja, boolean keepStrings) {
        loop0:
        while (x.more()) {
            Object token = x.nextContent();
            if (token == XML.LT) {
                Object token2 = x.nextToken();
                if (token2 instanceof Character) {
                    if (token2 == XML.SLASH) {
                        Object token3 = x.nextToken();
                        if (!(token3 instanceof String)) {
                            throw new JSONException("Expected a closing name instead of '" + token3 + "'.");
                        } else if (x.nextToken() == XML.GT) {
                            return token3;
                        } else {
                            throw x.syntaxError("Misshaped close tag");
                        }
                    } else if (token2 == XML.BANG) {
                        char c = x.next();
                        if (c == '-') {
                            if (x.next() == '-') {
                                x.skipPast("-->");
                            } else {
                                x.back();
                            }
                        } else if (c != '[') {
                            int i = 1;
                            do {
                                Object token4 = x.nextMeta();
                                if (token4 == null) {
                                    throw x.syntaxError("Missing '>' after '<!'.");
                                } else if (token4 == XML.LT) {
                                    i++;
                                    continue;
                                } else if (token4 == XML.GT) {
                                    i--;
                                    continue;
                                } else {
                                    continue;
                                }
                            } while (i > 0);
                        } else if (!x.nextToken().equals("CDATA") || x.next() != '[') {
                            throw x.syntaxError("Expected 'CDATA['");
                        } else if (ja != null) {
                            ja.put((Object) x.nextCDATA());
                        }
                    } else if (token2 == XML.QUEST) {
                        x.skipPast("?>");
                    } else {
                        throw x.syntaxError("Misshaped tag");
                    }
                } else if (token2 instanceof String) {
                    String tagName = (String) token2;
                    JSONArray newja = new JSONArray();
                    JSONObject newjo = new JSONObject();
                    if (arrayForm) {
                        newja.put((Object) tagName);
                        if (ja != null) {
                            ja.put((Object) newja);
                        }
                    } else {
                        newjo.put("tagName", (Object) tagName);
                        if (ja != null) {
                            ja.put((Object) newjo);
                        }
                    }
                    Object token5 = null;
                    while (true) {
                        if (token5 == null) {
                            token5 = x.nextToken();
                        }
                        if (token5 == null) {
                            throw x.syntaxError("Misshaped tag");
                        } else if (!(token5 instanceof String)) {
                            if (arrayForm && newjo.length() > 0) {
                                newja.put((Object) newjo);
                            }
                            if (token5 == XML.SLASH) {
                                if (x.nextToken() != XML.GT) {
                                    throw x.syntaxError("Misshaped tag");
                                } else if (ja == null) {
                                    if (arrayForm) {
                                        return newja;
                                    }
                                    return newjo;
                                }
                            } else if (token5 == XML.GT) {
                                String closeTag = (String) parse(x, arrayForm, newja, keepStrings);
                                if (closeTag == null) {
                                    continue;
                                } else if (closeTag.equals(tagName)) {
                                    if (!arrayForm && newja.length() > 0) {
                                        newjo.put("childNodes", (Object) newja);
                                    }
                                    if (ja == null) {
                                        if (arrayForm) {
                                            return newja;
                                        }
                                        return newjo;
                                    }
                                } else {
                                    throw x.syntaxError("Mismatched '" + tagName + "' and '" + closeTag + "'");
                                }
                            } else {
                                throw x.syntaxError("Misshaped tag");
                            }
                        } else {
                            String attribute = (String) token5;
                            if (arrayForm || (!"tagName".equals(attribute) && !"childNode".equals(attribute))) {
                                token5 = x.nextToken();
                                if (token5 == XML.EQ) {
                                    Object token6 = x.nextToken();
                                    if (token6 instanceof String) {
                                        String str = (String) token6;
                                        Object obj = str;
                                        if (!keepStrings) {
                                            obj = XML.stringToValue(str);
                                        }
                                        newjo.accumulate(attribute, obj);
                                        token5 = null;
                                    } else {
                                        throw x.syntaxError("Missing value");
                                    }
                                } else {
                                    newjo.accumulate(attribute, "");
                                }
                            }
                        }
                    }
                    throw x.syntaxError("Reserved attribute.");
                } else {
                    throw x.syntaxError("Bad tagName '" + token2 + "'.");
                }
            } else if (ja != null) {
                ja.put(token instanceof String ? keepStrings ? XML.unescape((String) token) : XML.stringToValue((String) token) : token);
            }
        }
        throw x.syntaxError("Bad XML");
    }

    public static JSONArray toJSONArray(String string) {
        return (JSONArray) parse(new XMLTokener(string), true, (JSONArray) null, false);
    }

    public static JSONArray toJSONArray(String string, boolean keepStrings) {
        return (JSONArray) parse(new XMLTokener(string), true, (JSONArray) null, keepStrings);
    }

    public static JSONArray toJSONArray(XMLTokener x, boolean keepStrings) {
        return (JSONArray) parse(x, true, (JSONArray) null, keepStrings);
    }

    public static JSONArray toJSONArray(XMLTokener x) {
        return (JSONArray) parse(x, true, (JSONArray) null, false);
    }

    public static JSONObject toJSONObject(String string) {
        return (JSONObject) parse(new XMLTokener(string), false, (JSONArray) null, false);
    }

    public static JSONObject toJSONObject(String string, boolean keepStrings) {
        return (JSONObject) parse(new XMLTokener(string), false, (JSONArray) null, keepStrings);
    }

    public static JSONObject toJSONObject(XMLTokener x) {
        return (JSONObject) parse(x, false, (JSONArray) null, false);
    }

    public static JSONObject toJSONObject(XMLTokener x, boolean keepStrings) {
        return (JSONObject) parse(x, false, (JSONArray) null, keepStrings);
    }

    public static String toString(JSONArray ja) {
        int i;
        StringBuilder sb = new StringBuilder();
        String tagName = ja.getString(0);
        XML.noSpace(tagName);
        String tagName2 = XML.escape(tagName);
        sb.append('<');
        sb.append(tagName2);
        Object object = ja.opt(1);
        if (object instanceof JSONObject) {
            i = 2;
            JSONObject jo = (JSONObject) object;
            for (String key : jo.keySet()) {
                Object value = jo.opt(key);
                XML.noSpace(key);
                if (value != null) {
                    sb.append(' ');
                    sb.append(XML.escape(key));
                    sb.append('=');
                    sb.append(StringUtil.DOUBLE_QUOTE);
                    sb.append(XML.escape(value.toString()));
                    sb.append(StringUtil.DOUBLE_QUOTE);
                }
            }
        } else {
            i = 1;
        }
        int length = ja.length();
        if (i >= length) {
            sb.append('/');
            sb.append('>');
        } else {
            sb.append('>');
            do {
                Object object2 = ja.get(i);
                i++;
                if (object2 != null) {
                    if (object2 instanceof String) {
                        sb.append(XML.escape(object2.toString()));
                        continue;
                    } else if (object2 instanceof JSONObject) {
                        sb.append(toString((JSONObject) object2));
                        continue;
                    } else if (object2 instanceof JSONArray) {
                        sb.append(toString((JSONArray) object2));
                        continue;
                    } else {
                        sb.append(object2.toString());
                        continue;
                    }
                }
            } while (i < length);
            sb.append('<');
            sb.append('/');
            sb.append(tagName2);
            sb.append('>');
        }
        return sb.toString();
    }

    public static String toString(JSONObject jo) {
        StringBuilder sb = new StringBuilder();
        String tagName = jo.optString("tagName");
        if (tagName == null) {
            return XML.escape(jo.toString());
        }
        XML.noSpace(tagName);
        String tagName2 = XML.escape(tagName);
        sb.append('<');
        sb.append(tagName2);
        for (String key : jo.keySet()) {
            if (!"tagName".equals(key) && !"childNodes".equals(key)) {
                XML.noSpace(key);
                Object value = jo.opt(key);
                if (value != null) {
                    sb.append(' ');
                    sb.append(XML.escape(key));
                    sb.append('=');
                    sb.append(StringUtil.DOUBLE_QUOTE);
                    sb.append(XML.escape(value.toString()));
                    sb.append(StringUtil.DOUBLE_QUOTE);
                }
            }
        }
        JSONArray ja = jo.optJSONArray("childNodes");
        if (ja == null) {
            sb.append('/');
            sb.append('>');
        } else {
            sb.append('>');
            int length = ja.length();
            for (int i = 0; i < length; i++) {
                Object object = ja.get(i);
                if (object != null) {
                    if (object instanceof String) {
                        sb.append(XML.escape(object.toString()));
                    } else if (object instanceof JSONObject) {
                        sb.append(toString((JSONObject) object));
                    } else if (object instanceof JSONArray) {
                        sb.append(toString((JSONArray) object));
                    } else {
                        sb.append(object.toString());
                    }
                }
            }
            sb.append('<');
            sb.append('/');
            sb.append(tagName2);
            sb.append('>');
        }
        return sb.toString();
    }
}
