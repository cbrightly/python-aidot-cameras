package com.didichuxing.doraemonkit.kit.fileexplorer;

public class SpBean {
    public Class clazz;
    public String key;
    public Object value;

    private SpBean() {
    }

    public SpBean(String key2, Object value2) {
        this.key = key2;
        this.value = value2;
        this.clazz = value2.getClass();
    }

    public Object toDefaultClass(String string) {
        setDefaultClass(string);
        return this.value;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setDefaultClass(java.lang.String r3) {
        /*
            r2 = this;
            java.lang.Class r0 = r2.clazz
            java.lang.String r0 = r0.getSimpleName()
            int r1 = r0.hashCode()
            switch(r1) {
                case -1808118735: goto L_0x002c;
                case -672261858: goto L_0x0022;
                case 2374300: goto L_0x0018;
                case 67973692: goto L_0x000e;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x0036
        L_0x000e:
            java.lang.String r1 = "Float"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 0
            goto L_0x0037
        L_0x0018:
            java.lang.String r1 = "Long"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 3
            goto L_0x0037
        L_0x0022:
            java.lang.String r1 = "Integer"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 1
            goto L_0x0037
        L_0x002c:
            java.lang.String r1 = "String"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 2
            goto L_0x0037
        L_0x0036:
            r0 = -1
        L_0x0037:
            switch(r0) {
                case 0: goto L_0x0050;
                case 1: goto L_0x0049;
                case 2: goto L_0x0042;
                case 3: goto L_0x003b;
                default: goto L_0x003a;
            }
        L_0x003a:
            goto L_0x0057
        L_0x003b:
            java.lang.Long r0 = java.lang.Long.valueOf(r3)
            r2.value = r0
            goto L_0x0057
        L_0x0042:
            java.lang.String r0 = java.lang.String.valueOf(r3)
            r2.value = r0
            goto L_0x0057
        L_0x0049:
            java.lang.Integer r0 = java.lang.Integer.valueOf(r3)
            r2.value = r0
            goto L_0x0057
        L_0x0050:
            java.lang.Float r0 = java.lang.Float.valueOf(r3)
            r2.value = r0
        L_0x0057:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.fileexplorer.SpBean.setDefaultClass(java.lang.String):void");
    }
}
