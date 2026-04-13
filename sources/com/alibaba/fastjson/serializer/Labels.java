package com.alibaba.fastjson.serializer;

import java.util.Arrays;

public class Labels {

    public static class DefaultLabelFilter implements LabelFilter {
        private String[] excludes;
        private String[] includes;

        public DefaultLabelFilter(String[] includes2, String[] excludes2) {
            if (includes2 != null) {
                String[] strArr = new String[includes2.length];
                this.includes = strArr;
                System.arraycopy(includes2, 0, strArr, 0, includes2.length);
                Arrays.sort(this.includes);
            }
            if (excludes2 != null) {
                String[] strArr2 = new String[excludes2.length];
                this.excludes = strArr2;
                System.arraycopy(excludes2, 0, strArr2, 0, excludes2.length);
                Arrays.sort(this.excludes);
            }
        }

        public boolean apply(String label) {
            String[] strArr = this.excludes;
            if (strArr == null) {
                String[] strArr2 = this.includes;
                if (strArr2 == null || Arrays.binarySearch(strArr2, label) < 0) {
                    return false;
                }
                return true;
            } else if (Arrays.binarySearch(strArr, label) < 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static LabelFilter includes(String... views) {
        return new DefaultLabelFilter(views, (String[]) null);
    }

    public static LabelFilter excludes(String... views) {
        return new DefaultLabelFilter((String[]) null, views);
    }
}
