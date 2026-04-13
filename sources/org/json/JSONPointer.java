package org.json;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JSONPointer {
    private static final String ENCODING = "utf-8";
    private final List<String> refTokens;

    public static class Builder {
        private final List<String> refTokens = new ArrayList();

        public JSONPointer build() {
            return new JSONPointer(this.refTokens);
        }

        public Builder append(String token) {
            if (token != null) {
                this.refTokens.add(token);
                return this;
            }
            throw new NullPointerException("token cannot be null");
        }

        public Builder append(int arrayIndex) {
            this.refTokens.add(String.valueOf(arrayIndex));
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public JSONPointer(String pointer) {
        String refs;
        if (pointer == null) {
            throw new NullPointerException("pointer cannot be null");
        } else if (pointer.isEmpty() || pointer.equals("#")) {
            this.refTokens = Collections.emptyList();
        } else {
            if (pointer.startsWith("#/")) {
                try {
                    refs = URLDecoder.decode(pointer.substring(2), ENCODING);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            } else if (pointer.startsWith("/")) {
                refs = pointer.substring(1);
            } else {
                throw new IllegalArgumentException("a JSON pointer should start with '/' or '#/'");
            }
            this.refTokens = new ArrayList();
            int slashIdx = -1;
            do {
                int prevSlashIdx = slashIdx + 1;
                slashIdx = refs.indexOf(47, prevSlashIdx);
                if (prevSlashIdx == slashIdx || prevSlashIdx == refs.length()) {
                    this.refTokens.add("");
                    continue;
                } else if (slashIdx >= 0) {
                    this.refTokens.add(unescape(refs.substring(prevSlashIdx, slashIdx)));
                    continue;
                } else {
                    this.refTokens.add(unescape(refs.substring(prevSlashIdx)));
                    continue;
                }
            } while (slashIdx >= 0);
        }
    }

    public JSONPointer(List<String> refTokens2) {
        this.refTokens = new ArrayList(refTokens2);
    }

    private static String unescape(String token) {
        return token.replace("~1", "/").replace("~0", "~").replace("\\\"", "\"").replace("\\\\", "\\");
    }

    public Object queryFrom(Object document) {
        if (this.refTokens.isEmpty()) {
            return document;
        }
        Object current = document;
        for (String token : this.refTokens) {
            if (current instanceof JSONObject) {
                current = ((JSONObject) current).opt(unescape(token));
            } else if (current instanceof JSONArray) {
                current = readByIndexToken(current, token);
            } else {
                throw new JSONPointerException(String.format("value [%s] is not an array or object therefore its key %s cannot be resolved", new Object[]{current, token}));
            }
        }
        return current;
    }

    private static Object readByIndexToken(Object current, String indexToken) {
        int index;
        try {
            index = Integer.parseInt(indexToken);
            JSONArray currentArr = (JSONArray) current;
            if (index < currentArr.length()) {
                return currentArr.get(index);
            }
            throw new JSONPointerException(String.format("index %s is out of bounds - the array has %d elements", new Object[]{indexToken, Integer.valueOf(currentArr.length())}));
        } catch (JSONException e) {
            throw new JSONPointerException("Error reading value at index position " + index, e);
        } catch (NumberFormatException e2) {
            throw new JSONPointerException(String.format("%s is not an array index", new Object[]{indexToken}), e2);
        }
    }

    public String toString() {
        StringBuilder rval = new StringBuilder("");
        for (String token : this.refTokens) {
            rval.append('/');
            rval.append(escape(token));
        }
        return rval.toString();
    }

    private static String escape(String token) {
        return token.replace("~", "~0").replace("/", "~1").replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public String toURIFragment() {
        try {
            StringBuilder rval = new StringBuilder("#");
            for (String token : this.refTokens) {
                rval.append('/');
                rval.append(URLEncoder.encode(token, ENCODING));
            }
            return rval.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
