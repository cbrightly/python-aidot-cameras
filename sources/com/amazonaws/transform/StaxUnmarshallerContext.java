package com.amazonaws.transform;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

public class StaxUnmarshallerContext {
    private int currentEventType;
    private final Map<String, String> headers;
    private Map<String, String> metadata;
    private List<MetadataExpression> metadataExpressions;
    public final Deque<String> stack;
    private String stackString;
    private final XmlPullParser xpp;

    public StaxUnmarshallerContext(XmlPullParser xpp2) {
        this(xpp2, (Map<String, String>) null);
    }

    public StaxUnmarshallerContext(XmlPullParser xpp2, Map<String, String> headers2) {
        this.stack = new LinkedList();
        this.stackString = "";
        this.metadata = new HashMap();
        this.metadataExpressions = new ArrayList();
        this.xpp = xpp2;
        this.headers = headers2;
    }

    public String getHeader(String header) {
        Map<String, String> map = this.headers;
        if (map == null) {
            return null;
        }
        return map.get(header);
    }

    public String readText() {
        String s = this.xpp.nextText();
        if (this.xpp.getEventType() != 3) {
            this.xpp.next();
        }
        this.currentEventType = this.xpp.getEventType();
        updateContext();
        return s;
    }

    public int getCurrentDepth() {
        return this.stack.size();
    }

    public boolean testExpression(String expression) {
        return testExpression(expression, getCurrentDepth());
    }

    public boolean testExpression(String expression, int startingStackDepth) {
        if (".".equals(expression)) {
            return true;
        }
        int index = -1;
        while (true) {
            int indexOf = expression.indexOf("/", index + 1);
            index = indexOf;
            if (indexOf <= -1) {
                break;
            } else if (expression.charAt(index + 1) != '@') {
                startingStackDepth++;
            }
        }
        if (getCurrentDepth() == startingStackDepth) {
            String str = this.stackString;
            if (str.endsWith("/" + expression)) {
                return true;
            }
        }
        return false;
    }

    public boolean isStartOfDocument() {
        return this.currentEventType == 0;
    }

    public int nextEvent() {
        int next = this.xpp.next();
        this.currentEventType = next;
        if (next == 4) {
            this.currentEventType = this.xpp.next();
        }
        updateContext();
        if (this.currentEventType == 2) {
            Iterator<MetadataExpression> it = this.metadataExpressions.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MetadataExpression metadataExpression = it.next();
                if (testExpression(metadataExpression.expression, metadataExpression.targetDepth)) {
                    this.metadata.put(metadataExpression.key, readText());
                    break;
                }
            }
        }
        return this.currentEventType;
    }

    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    public void registerMetadataExpression(String expression, int targetDepth, String storageKey) {
        this.metadataExpressions.add(new MetadataExpression(expression, targetDepth, storageKey));
    }

    public static class MetadataExpression {
        public String expression;
        public String key;
        public int targetDepth;

        public MetadataExpression(String expression2, int targetDepth2, String key2) {
            this.expression = expression2;
            this.targetDepth = targetDepth2;
            this.key = key2;
        }
    }

    private void updateContext() {
        int i = this.currentEventType;
        if (i == 2) {
            String str = this.stackString + "/" + this.xpp.getName();
            this.stackString = str;
            this.stack.push(str);
        } else if (i == 3) {
            this.stack.pop();
            this.stackString = this.stack.isEmpty() ? "" : this.stack.peek();
        }
    }
}
