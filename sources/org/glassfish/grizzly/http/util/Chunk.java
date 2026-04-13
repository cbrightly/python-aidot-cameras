package org.glassfish.grizzly.http.util;

public interface Chunk {
    void delete(int i, int i2);

    int getEnd();

    int getLength();

    int getStart();

    int indexOf(char c, int i);

    int indexOf(String str, int i);

    void setEnd(int i);

    void setStart(int i);

    String toString(int i, int i2);
}
