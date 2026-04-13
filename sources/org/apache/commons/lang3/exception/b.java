package org.apache.commons.lang3.exception;

import com.google.maps.android.BuildConfig;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.a;

/* compiled from: DefaultExceptionContext */
public class b implements c, Serializable {
    private static final long serialVersionUID = 20110706;
    private final List<org.apache.commons.lang3.tuple.b<String, Object>> contextValues = new ArrayList();

    public b addContextValue(String label, Object value) {
        this.contextValues.add(new a(label, value));
        return this;
    }

    public b setContextValue(String label, Object value) {
        this.contextValues.removeIf(new a(label));
        addContextValue(label, value);
        return this;
    }

    public List<Object> getContextValues(String label) {
        List<Object> values = new ArrayList<>();
        Iterator<org.apache.commons.lang3.tuple.b<String, Object>> it = this.contextValues.iterator();
        while (it.hasNext()) {
            Pair<String, Object> pair = (org.apache.commons.lang3.tuple.b) it.next();
            if (org.apache.commons.lang3.a.a(label, (CharSequence) pair.getKey())) {
                values.add(pair.getValue());
            }
        }
        return values;
    }

    public Object getFirstContextValue(String label) {
        Iterator<org.apache.commons.lang3.tuple.b<String, Object>> it = this.contextValues.iterator();
        while (it.hasNext()) {
            Pair<String, Object> pair = (org.apache.commons.lang3.tuple.b) it.next();
            if (org.apache.commons.lang3.a.a(label, (CharSequence) pair.getKey())) {
                return pair.getValue();
            }
        }
        return null;
    }

    public Set<String> getContextLabels() {
        Set<String> labels = new HashSet<>();
        Iterator<org.apache.commons.lang3.tuple.b<String, Object>> it = this.contextValues.iterator();
        while (it.hasNext()) {
            labels.add(((org.apache.commons.lang3.tuple.b) it.next()).getKey());
        }
        return labels;
    }

    public List<org.apache.commons.lang3.tuple.b<String, Object>> getContextEntries() {
        return this.contextValues;
    }

    public String getFormattedExceptionMessage(String baseMessage) {
        String valueStr;
        StringBuilder buffer = new StringBuilder(256);
        if (baseMessage != null) {
            buffer.append(baseMessage);
        }
        if (!this.contextValues.isEmpty()) {
            if (buffer.length() > 0) {
                buffer.append(10);
            }
            buffer.append("Exception Context:\n");
            int i = 0;
            Iterator<org.apache.commons.lang3.tuple.b<String, Object>> it = this.contextValues.iterator();
            while (it.hasNext()) {
                Pair<String, Object> pair = (org.apache.commons.lang3.tuple.b) it.next();
                buffer.append("\t[");
                i++;
                buffer.append(i);
                buffer.append(':');
                buffer.append((String) pair.getKey());
                buffer.append("=");
                Object value = pair.getValue();
                if (value == null) {
                    buffer.append(BuildConfig.TRAVIS);
                } else {
                    try {
                        valueStr = value.toString();
                    } catch (Exception e) {
                        valueStr = "Exception thrown on toString(): " + d.a(e);
                    }
                    buffer.append(valueStr);
                }
                buffer.append("]\n");
            }
            buffer.append("---------------------------------");
        }
        return buffer.toString();
    }
}
