package org.apache.httpcore;

import java.util.Iterator;

/* compiled from: TokenIterator */
public interface z extends Iterator<Object> {
    boolean hasNext();

    String nextToken();
}
