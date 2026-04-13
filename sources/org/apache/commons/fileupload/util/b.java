package org.apache.commons.fileupload.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.fileupload.c;

/* compiled from: FileItemHeadersImpl */
public class b implements c, Serializable {
    private static final long serialVersionUID = -4455695752627032559L;
    private final Map<String, List<String>> headerNameToValueListMap = new LinkedHashMap();

    public String getHeader(String name) {
        List<String> headerValueList = this.headerNameToValueListMap.get(name.toLowerCase(Locale.ENGLISH));
        if (headerValueList == null) {
            return null;
        }
        return headerValueList.get(0);
    }

    public Iterator<String> getHeaderNames() {
        return this.headerNameToValueListMap.keySet().iterator();
    }

    public Iterator<String> getHeaders(String name) {
        List<String> headerValueList = this.headerNameToValueListMap.get(name.toLowerCase(Locale.ENGLISH));
        if (headerValueList == null) {
            headerValueList = Collections.emptyList();
        }
        return headerValueList.iterator();
    }

    public synchronized void addHeader(String name, String value) {
        String nameLower = name.toLowerCase(Locale.ENGLISH);
        List<String> headerValueList = this.headerNameToValueListMap.get(nameLower);
        if (headerValueList == null) {
            headerValueList = new ArrayList<>();
            this.headerNameToValueListMap.put(nameLower, headerValueList);
        }
        headerValueList.add(value);
    }
}
