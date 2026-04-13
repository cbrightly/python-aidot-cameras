package org.spongycastle.util;

import java.util.Collection;

public interface Store<T> {
    Collection<T> a(Selector<T> selector);
}
