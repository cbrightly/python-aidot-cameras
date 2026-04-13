package net.sbbi.upnp;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/* compiled from: HttpResponse */
public class d {
    private String a;
    private Map b;
    private String c;

    protected d(String rawHttpResponse) {
        if (rawHttpResponse == null || rawHttpResponse.trim().length() == 0) {
            throw new IllegalArgumentException("Empty HTTP response message");
        }
        boolean bodyParsing = false;
        StringBuffer bodyParsed = new StringBuffer();
        this.b = new HashMap();
        String[] lines = rawHttpResponse.split("\\r\\n");
        this.a = lines[0].trim();
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            if (line.length() == 0) {
                bodyParsing = true;
            } else if (bodyParsing) {
                bodyParsed.append(line);
                bodyParsed.append("\r\n");
            } else if (line.length() <= 0) {
                continue;
            } else {
                int delim = line.indexOf(58);
                if (delim != -1) {
                    this.b.put(line.substring(0, delim).toUpperCase(), line.substring(delim + 1).trim());
                } else {
                    throw new IllegalArgumentException("Invalid HTTP message header :" + line);
                }
            }
        }
        if (bodyParsing) {
            this.c = bodyParsed.toString();
        }
    }

    public String c() {
        return this.a;
    }

    public String a(String fieldName, String elementName) {
        int index;
        String fieldNameValue = b(fieldName);
        if (fieldName != null) {
            StringTokenizer tokenizer = new StringTokenizer(fieldNameValue.trim(), ",");
            while (tokenizer.countTokens() > 0) {
                String nextToken = tokenizer.nextToken().trim();
                if (nextToken.startsWith(elementName) && (index = nextToken.indexOf("=")) != -1) {
                    return nextToken.substring(index + 1).trim();
                }
            }
        }
        throw new IllegalArgumentException("HTTP element field " + elementName + " is not present");
    }

    public String b(String fieldName) {
        String field = (String) this.b.get(fieldName.toUpperCase());
        if (field != null) {
            return field;
        }
        throw new IllegalArgumentException("HTTP field " + fieldName + " is not present");
    }
}
