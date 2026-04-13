package com.didichuxing.doraemonkit.kit.filemanager.action.file;

/* compiled from: UploadFileAction.kt */
public final class UploadFileAction {
    public static final UploadFileAction INSTANCE = new UploadFileAction();

    private UploadFileAction() {
    }

    /* Debug info: failed to restart local var, previous not found, register: 25 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: kotlin.jvm.internal.z} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: kotlin.jvm.internal.z} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v40, resolved type: io.ktor.http.content.g} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01a6  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002b  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object uploadFileRes(@org.jetbrains.annotations.NotNull io.ktor.http.content.g r26, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.util.Map<java.lang.String, java.lang.Object>> r27) {
        /*
            r25 = this;
            r1 = r25
            r0 = r26
            r2 = r27
            boolean r3 = r2 instanceof com.didichuxing.doraemonkit.kit.filemanager.action.file.UploadFileAction$uploadFileRes$1
            if (r3 == 0) goto L_0x0019
            r3 = r2
            com.didichuxing.doraemonkit.kit.filemanager.action.file.UploadFileAction$uploadFileRes$1 r3 = (com.didichuxing.doraemonkit.kit.filemanager.action.file.UploadFileAction$uploadFileRes$1) r3
            int r4 = r3.label
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            r6 = r4 & r5
            if (r6 == 0) goto L_0x0019
            int r4 = r4 - r5
            r3.label = r4
            goto L_0x001e
        L_0x0019:
            com.didichuxing.doraemonkit.kit.filemanager.action.file.UploadFileAction$uploadFileRes$1 r3 = new com.didichuxing.doraemonkit.kit.filemanager.action.file.UploadFileAction$uploadFileRes$1
            r3.<init>(r1, r2)
        L_0x001e:
            java.lang.Object r4 = r3.result
            java.lang.Object r5 = kotlin.coroutines.intrinsics.c.d()
            int r6 = r3.label
            r7 = 1
            r8 = 0
            switch(r6) {
                case 0: goto L_0x0050;
                case 1: goto L_0x0033;
                default: goto L_0x002b;
            }
        L_0x002b:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r1
        L_0x0033:
            r5 = r8
            r6 = r8
            java.lang.Object r9 = r3.L$3
            r6 = r9
            kotlin.jvm.internal.z r6 = (kotlin.jvm.internal.z) r6
            java.lang.Object r9 = r3.L$2
            r5 = r9
            kotlin.jvm.internal.z r5 = (kotlin.jvm.internal.z) r5
            java.lang.Object r9 = r3.L$1
            r0 = r9
            io.ktor.http.content.g r0 = (io.ktor.http.content.g) r0
            java.lang.Object r9 = r3.L$0
            com.didichuxing.doraemonkit.kit.filemanager.action.file.UploadFileAction r9 = (com.didichuxing.doraemonkit.kit.filemanager.action.file.UploadFileAction) r9
            kotlin.p.b(r4)
            r10 = r9
            r9 = r6
            r6 = r5
            r5 = r0
            goto L_0x0079
        L_0x0050:
            kotlin.p.b(r4)
            kotlin.jvm.internal.z r6 = new kotlin.jvm.internal.z
            r6.<init>()
            r6.element = r8
            kotlin.jvm.internal.z r9 = new kotlin.jvm.internal.z
            r9.<init>()
            r9.element = r8
            com.didichuxing.doraemonkit.kit.filemanager.action.file.UploadFileAction$uploadFileRes$2 r10 = new com.didichuxing.doraemonkit.kit.filemanager.action.file.UploadFileAction$uploadFileRes$2
            r10.<init>(r6, r9, r8)
            r3.L$0 = r1
            r3.L$1 = r0
            r3.L$2 = r6
            r3.L$3 = r9
            r3.label = r7
            java.lang.Object r10 = io.ktor.http.content.i.a(r0, r10, r3)
            if (r10 != r5) goto L_0x0077
            return r5
        L_0x0077:
            r5 = r0
            r10 = r1
        L_0x0079:
            java.util.LinkedHashMap r0 = new java.util.LinkedHashMap
            r0.<init>()
            r11 = r0
            T r0 = r6.element
            io.ktor.http.content.k$a r0 = (io.ktor.http.content.k.a) r0
            java.lang.String r12 = "message"
            java.lang.String r13 = "code"
            java.lang.String r14 = "success"
            if (r0 == 0) goto L_0x01a6
            r16 = r0
            r17 = 0
            com.didichuxing.doraemonkit.kit.filemanager.FileManagerUtil r0 = com.didichuxing.doraemonkit.kit.filemanager.FileManagerUtil.INSTANCE
            T r7 = r9.element
            io.ktor.http.content.k$b r7 = (io.ktor.http.content.k.b) r7
            if (r7 == 0) goto L_0x009d
            java.lang.String r7 = r7.e()
            goto L_0x009e
        L_0x009d:
            r7 = r8
        L_0x009e:
            java.lang.String r7 = r0.absoluteRootPath(r7)
            T r0 = r6.element
            io.ktor.http.content.k$a r0 = (io.ktor.http.content.k.a) r0
            if (r0 == 0) goto L_0x00ad
            java.lang.String r0 = r0.e()
            goto L_0x00ae
        L_0x00ad:
            r0 = r8
        L_0x00ae:
            r26 = r0
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r7)
            java.lang.String r15 = java.io.File.separator
            r8.append(r15)
            r15 = r26
            r8.append(r15)
            java.lang.String r8 = r8.toString()
            r0.<init>(r8)
            r8 = r0
            kotlin.jvm.functions.a r0 = io.ktor.http.content.h.a(r16)
            java.lang.Object r0 = r0.invoke()
            r1 = r0
            java.io.Closeable r1 = (java.io.Closeable) r1
            r0 = r1
            java.io.InputStream r0 = (java.io.InputStream) r0     // Catch:{ all -> 0x0195 }
            r26 = r0
            r18 = 0
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ all -> 0x0195 }
            r0.<init>(r8)     // Catch:{ all -> 0x0195 }
            boolean r2 = r0 instanceof java.io.BufferedOutputStream     // Catch:{ all -> 0x0195 }
            if (r2 == 0) goto L_0x00f9
            java.io.BufferedOutputStream r0 = (java.io.BufferedOutputStream) r0     // Catch:{ all -> 0x00ed }
            r2 = r0
            r20 = r3
            goto L_0x0102
        L_0x00ed:
            r0 = move-exception
            r2 = r0
            r20 = r3
            r21 = r4
            r26 = r5
            r22 = r7
            goto L_0x019f
        L_0x00f9:
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x0195 }
            r20 = r3
            r3 = 8192(0x2000, float:1.14794E-41)
            r2.<init>(r0, r3)     // Catch:{ all -> 0x018c }
        L_0x0102:
            r0 = r2
            r3 = 0
            r19 = r3
            r3 = r26
            if (r3 == 0) goto L_0x0124
            r21 = r4
            r4 = 2
            r26 = r5
            r22 = r7
            r5 = 0
            r7 = 0
            long r23 = kotlin.io.a.b(r3, r0, r5, r4, r7)     // Catch:{ all -> 0x011b }
            kotlin.coroutines.jvm.internal.b.d(r23)     // Catch:{ all -> 0x011b }
            goto L_0x012a
        L_0x011b:
            r0 = move-exception
            r4 = r0
            throw r4     // Catch:{ all -> 0x011e }
        L_0x011e:
            r0 = move-exception
            r5 = r0
            kotlin.io.b.a(r2, r4)     // Catch:{ all -> 0x0189 }
            throw r5     // Catch:{ all -> 0x0189 }
        L_0x0124:
            r21 = r4
            r26 = r5
            r22 = r7
        L_0x012a:
            r0 = 0
            kotlin.io.b.a(r2, r0)     // Catch:{ all -> 0x0189 }
            kotlin.io.b.a(r1, r0)
            T r0 = r6.element
            io.ktor.http.content.k$a r0 = (io.ktor.http.content.k.a) r0
            if (r0 == 0) goto L_0x013b
            r0.b()
        L_0x013b:
            T r0 = r9.element
            io.ktor.http.content.k$b r0 = (io.ktor.http.content.k.b) r0
            if (r0 == 0) goto L_0x0144
            r0.b()
        L_0x0144:
            boolean r0 = com.blankj.utilcode.util.j.y(r8)
            if (r0 == 0) goto L_0x015f
            r0 = 200(0xc8, float:2.8E-43)
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.b.c(r0)
            r11.put(r13, r0)
            r0 = 1
            java.lang.Boolean r0 = kotlin.coroutines.jvm.internal.b.a(r0)
            r11.put(r14, r0)
            r11.put(r12, r14)
            goto L_0x0186
        L_0x015f:
            r0 = 0
            java.lang.Integer r1 = kotlin.coroutines.jvm.internal.b.c(r0)
            r11.put(r13, r1)
            java.lang.Boolean r0 = kotlin.coroutines.jvm.internal.b.a(r0)
            r11.put(r14, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = r8.getAbsolutePath()
            r0.append(r1)
            java.lang.String r1 = " is not exists"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r11.put(r12, r0)
        L_0x0186:
            goto L_0x01c3
        L_0x0189:
            r0 = move-exception
            r2 = r0
            goto L_0x019f
        L_0x018c:
            r0 = move-exception
            r21 = r4
            r26 = r5
            r22 = r7
            r2 = r0
            goto L_0x019f
        L_0x0195:
            r0 = move-exception
            r20 = r3
            r21 = r4
            r26 = r5
            r22 = r7
            r2 = r0
        L_0x019f:
            throw r2     // Catch:{ all -> 0x01a0 }
        L_0x01a0:
            r0 = move-exception
            r3 = r0
            kotlin.io.b.a(r1, r2)
            throw r3
        L_0x01a6:
            r20 = r3
            r21 = r4
            r26 = r5
            r0 = r10
            r1 = 0
            r2 = 0
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.b.c(r2)
            r11.put(r13, r3)
            java.lang.Boolean r2 = kotlin.coroutines.jvm.internal.b.a(r2)
            r11.put(r14, r2)
            java.lang.String r2 = "filePart is null"
            r11.put(r12, r2)
        L_0x01c3:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.filemanager.action.file.UploadFileAction.uploadFileRes(io.ktor.http.content.g, kotlin.coroutines.d):java.lang.Object");
    }
}
