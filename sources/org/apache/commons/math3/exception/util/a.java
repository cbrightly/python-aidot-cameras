package org.apache.commons.math3.exception.util;

import java.util.ArrayList;
import java.util.List;

/* compiled from: ArgUtils */
public class a {
    public static Object[] a(Object[] array) {
        List<Object> list = new ArrayList<>();
        if (array != null) {
            for (Object o : array) {
                if (o instanceof Object[]) {
                    for (Object oR : a((Object[]) o)) {
                        list.add(oR);
                    }
                } else {
                    list.add(o);
                }
            }
        }
        return list.toArray();
    }
}
