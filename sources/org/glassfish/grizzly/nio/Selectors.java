package org.glassfish.grizzly.nio;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;

public final class Selectors {
    public static Selector newSelector(SelectorProvider provider) {
        try {
            return provider.openSelector();
        } catch (NullPointerException e) {
            int i = 0;
            while (i < 5) {
                try {
                    return provider.openSelector();
                } catch (NullPointerException e2) {
                    i++;
                }
            }
            throw new IOException("Can not open Selector due to NPE");
        }
    }
}
