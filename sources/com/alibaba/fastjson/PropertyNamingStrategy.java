package com.alibaba.fastjson;

public enum PropertyNamingStrategy {
    CamelCase,
    PascalCase,
    SnakeCase,
    KebabCase,
    NoChange;

    /* renamed from: com.alibaba.fastjson.PropertyNamingStrategy$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$alibaba$fastjson$PropertyNamingStrategy = null;

        static {
            int[] iArr = new int[PropertyNamingStrategy.values().length];
            $SwitchMap$com$alibaba$fastjson$PropertyNamingStrategy = iArr;
            try {
                iArr[PropertyNamingStrategy.SnakeCase.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$PropertyNamingStrategy[PropertyNamingStrategy.KebabCase.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$PropertyNamingStrategy[PropertyNamingStrategy.PascalCase.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$PropertyNamingStrategy[PropertyNamingStrategy.CamelCase.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$PropertyNamingStrategy[PropertyNamingStrategy.NoChange.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public String translate(String propertyName) {
        switch (AnonymousClass1.$SwitchMap$com$alibaba$fastjson$PropertyNamingStrategy[ordinal()]) {
            case 1:
                StringBuilder buf = new StringBuilder();
                for (int i = 0; i < propertyName.length(); i++) {
                    char ch = propertyName.charAt(i);
                    if (ch < 'A' || ch > 'Z') {
                        buf.append(ch);
                    } else {
                        char ch_ucase = (char) (ch + ' ');
                        if (i > 0) {
                            buf.append('_');
                        }
                        buf.append(ch_ucase);
                    }
                }
                return buf.toString();
            case 2:
                StringBuilder buf2 = new StringBuilder();
                for (int i2 = 0; i2 < propertyName.length(); i2++) {
                    char ch2 = propertyName.charAt(i2);
                    if (ch2 < 'A' || ch2 > 'Z') {
                        buf2.append(ch2);
                    } else {
                        char ch_ucase2 = (char) (ch2 + ' ');
                        if (i2 > 0) {
                            buf2.append('-');
                        }
                        buf2.append(ch_ucase2);
                    }
                }
                return buf2.toString();
            case 3:
                char ch3 = propertyName.charAt(0);
                if (ch3 < 'a' || ch3 > 'z') {
                    return propertyName;
                }
                char[] chars = propertyName.toCharArray();
                chars[0] = (char) (chars[0] - ' ');
                return new String(chars);
            case 4:
                char ch4 = propertyName.charAt(0);
                if (ch4 < 'A' || ch4 > 'Z') {
                    return propertyName;
                }
                char[] chars2 = propertyName.toCharArray();
                chars2[0] = (char) (chars2[0] + ' ');
                return new String(chars2);
            default:
                return propertyName;
        }
    }
}
