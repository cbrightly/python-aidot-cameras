package org.glassfish.grizzly.nio;

public interface SelectorHandlerTask {
    void cancel();

    boolean run(SelectorRunner selectorRunner);
}
