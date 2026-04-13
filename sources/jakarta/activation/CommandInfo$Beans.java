package jakarta.activation;

import java.lang.reflect.Method;

public final class CommandInfo$Beans {
    static final Method instantiateMethod;

    private CommandInfo$Beans() {
    }

    static {
        Method m;
        try {
            m = Class.forName("java.beans.Beans").getDeclaredMethod("instantiate", new Class[]{ClassLoader.class, String.class});
        } catch (ClassNotFoundException e) {
            m = null;
        } catch (NoSuchMethodException e2) {
            m = null;
        }
        instantiateMethod = m;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: java.lang.ClassLoader} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.Object instantiate(java.lang.ClassLoader r7, java.lang.String r8) {
        /*
            java.lang.reflect.Method r0 = instantiateMethod
            r1 = 2
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L_0x001a
            r4 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ InvocationTargetException -> 0x0016, IllegalAccessException -> 0x0013 }
            r1[r3] = r7     // Catch:{ InvocationTargetException -> 0x0016, IllegalAccessException -> 0x0013 }
            r1[r2] = r8     // Catch:{ InvocationTargetException -> 0x0016, IllegalAccessException -> 0x0013 }
            java.lang.Object r0 = r0.invoke(r4, r1)     // Catch:{ InvocationTargetException -> 0x0016, IllegalAccessException -> 0x0013 }
            return r0
        L_0x0013:
            r0 = move-exception
            goto L_0x0018
        L_0x0016:
            r0 = move-exception
        L_0x0018:
            return r4
        L_0x001a:
            java.lang.SecurityManager r0 = java.lang.System.getSecurityManager()
            if (r0 == 0) goto L_0x0051
            r4 = 47
            r5 = 46
            java.lang.String r4 = r8.replace(r4, r5)
            java.lang.String r6 = "["
            boolean r6 = r4.startsWith(r6)
            if (r6 == 0) goto L_0x0043
            r6 = 91
            int r6 = r4.lastIndexOf(r6)
            int r6 = r6 + r1
            if (r6 <= r2) goto L_0x0043
            int r1 = r4.length()
            if (r6 >= r1) goto L_0x0043
            java.lang.String r4 = r4.substring(r6)
        L_0x0043:
            int r1 = r4.lastIndexOf(r5)
            r5 = -1
            if (r1 == r5) goto L_0x0051
            java.lang.String r5 = r4.substring(r3, r1)
            r0.checkPackageAccess(r5)
        L_0x0051:
            if (r7 != 0) goto L_0x005f
            jakarta.activation.CommandInfo$Beans$1 r1 = new jakarta.activation.CommandInfo$Beans$1
            r1.<init>()
            java.lang.Object r1 = java.security.AccessController.doPrivileged(r1)
            r7 = r1
            java.lang.ClassLoader r7 = (java.lang.ClassLoader) r7
        L_0x005f:
            java.lang.Class r1 = java.lang.Class.forName(r8, r2, r7)
            java.lang.Class[] r2 = new java.lang.Class[r3]     // Catch:{ Exception -> 0x0070 }
            java.lang.reflect.Constructor r2 = r1.getDeclaredConstructor(r2)     // Catch:{ Exception -> 0x0070 }
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0070 }
            java.lang.Object r2 = r2.newInstance(r3)     // Catch:{ Exception -> 0x0070 }
            return r2
        L_0x0070:
            r2 = move-exception
            java.lang.ClassNotFoundException r3 = new java.lang.ClassNotFoundException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r1)
            java.lang.String r5 = ": "
            r4.append(r5)
            r4.append(r2)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4, r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: jakarta.activation.CommandInfo$Beans.instantiate(java.lang.ClassLoader, java.lang.String):java.lang.Object");
    }
}
