package org.apache.commons.lang3.exception;

import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.tuple.b;

/* compiled from: ExceptionContext */
public interface c {
    c addContextValue(String str, Object obj);

    List<b<String, Object>> getContextEntries();

    Set<String> getContextLabels();

    List<Object> getContextValues(String str);

    Object getFirstContextValue(String str);

    String getFormattedExceptionMessage(String str);

    c setContextValue(String str, Object obj);
}
