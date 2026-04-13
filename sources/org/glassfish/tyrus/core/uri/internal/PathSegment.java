package org.glassfish.tyrus.core.uri.internal;

public interface PathSegment {
    MultivaluedMap<String, String> getMatrixParameters();

    String getPath();
}
