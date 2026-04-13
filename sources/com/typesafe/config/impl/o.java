package com.typesafe.config.impl;

import com.google.maps.android.BuildConfig;
import com.tencent.bugly.Bugly;
import com.typesafe.config.impl.m;
import com.typesafe.config.k;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: DefaultTransformer */
public final class o {
    static AbstractConfigValue a(AbstractConfigValue value, k requested) {
        k valueType = value.valueType();
        k kVar = k.STRING;
        if (valueType == kVar) {
            String s = (String) value.unwrapped();
            switch (b.a[requested.ordinal()]) {
                case 1:
                    try {
                        return new i(value.origin(), Long.valueOf(Long.parseLong(s)).longValue(), s);
                    } catch (NumberFormatException e) {
                        try {
                            return new e(value.origin(), Double.valueOf(Double.parseDouble(s)).doubleValue(), s);
                        } catch (NumberFormatException e2) {
                            break;
                        }
                    }
                case 2:
                    if (s.equals(BuildConfig.TRAVIS)) {
                        return new j(value.origin());
                    }
                    break;
                case 3:
                    if (s.equals("true") || s.equals("yes") || s.equals("on")) {
                        return new b(value.origin(), true);
                    }
                    if (s.equals(Bugly.SDK_IS_DEV) || s.equals("no") || s.equals("off")) {
                        return new b(value.origin(), false);
                    }
                    break;
            }
        } else if (requested == kVar) {
            switch (b.a[value.valueType().ordinal()]) {
                case 1:
                case 3:
                    return new m.a(value.origin(), value.transformToString());
            }
        } else if (requested == k.LIST && value.valueType() == k.OBJECT) {
            a o = (a) value;
            Map<Integer, AbstractConfigValue> values = new HashMap<>();
            for (String key : o.keySet()) {
                try {
                    int i = Integer.parseInt(key, 10);
                    if (i >= 0) {
                        values.put(Integer.valueOf(i), o.get((Object) key));
                    }
                } catch (NumberFormatException e3) {
                }
            }
            if (!values.isEmpty()) {
                ArrayList<Map.Entry<Integer, AbstractConfigValue>> entryList = new ArrayList<>(values.entrySet());
                Collections.sort(entryList, new a());
                ArrayList<AbstractConfigValue> list = new ArrayList<>();
                Iterator<Map.Entry<Integer, AbstractConfigValue>> it = entryList.iterator();
                while (it.hasNext()) {
                    list.add(it.next().getValue());
                }
                return new d0(value.origin(), list);
            }
        }
        return value;
    }

    /* compiled from: DefaultTransformer */
    public static /* synthetic */ class b {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[k.values().length];
            a = iArr;
            try {
                iArr[k.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[k.NULL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[k.BOOLEAN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[k.LIST.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[k.OBJECT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[k.STRING.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    /* compiled from: DefaultTransformer */
    public static final class a implements Comparator<Map.Entry<Integer, AbstractConfigValue>> {
        a() {
        }

        /* renamed from: a */
        public int compare(Map.Entry<Integer, AbstractConfigValue> a, Map.Entry<Integer, AbstractConfigValue> b) {
            return Integer.compare(a.getKey().intValue(), b.getKey().intValue());
        }
    }
}
