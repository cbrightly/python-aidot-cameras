package org.glassfish.grizzly;

import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.ICloseType;

public interface CloseListener<T extends Closeable, C extends ICloseType> {
    void onClosed(T t, C c);
}
