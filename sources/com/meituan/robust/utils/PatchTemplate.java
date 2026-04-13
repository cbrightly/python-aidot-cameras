package com.meituan.robust.utils;

import com.meituan.robust.ChangeQuickRedirect;
import java.util.Map;
import java.util.WeakHashMap;

public class PatchTemplate implements ChangeQuickRedirect {
    public static final String MATCH_ALL_PARAMETER = "(\\w*\\.)*\\w*";
    private static final Map<Object, Object> keyToValueRelation = new WeakHashMap();

    public Object accessDispatch(String methodName, Object[] paramArrayOfObject) {
        return null;
    }

    public boolean isSupport(String methodName, Object[] paramArrayOfObject) {
        return true;
    }

    private static Object fixObj(Object booleanObj) {
        if (!(booleanObj instanceof Byte)) {
            return booleanObj;
        }
        return new Boolean(((Byte) booleanObj).byteValue() != 0);
    }
}
