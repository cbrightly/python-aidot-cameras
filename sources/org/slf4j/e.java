package org.slf4j;

import java.io.Serializable;
import java.util.Iterator;

/* compiled from: Marker */
public interface e extends Serializable {
    public static final String ANY_MARKER = "*";
    public static final String ANY_NON_NULL_MARKER = "+";

    void add(e eVar);

    boolean contains(String str);

    boolean contains(e eVar);

    boolean equals(Object obj);

    String getName();

    boolean hasChildren();

    boolean hasReferences();

    int hashCode();

    Iterator<e> iterator();

    boolean remove(e eVar);
}
