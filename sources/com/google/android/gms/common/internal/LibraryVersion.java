package com.google.android.gms.common.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.concurrent.ConcurrentHashMap;

@KeepForSdk
@Deprecated
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class LibraryVersion {
    private static final GmsLogger zza = new GmsLogger("LibraryVersion", "");
    private static final LibraryVersion zzb = new LibraryVersion();
    private final ConcurrentHashMap zzc = new ConcurrentHashMap();

    @VisibleForTesting
    protected LibraryVersion() {
    }

    @NonNull
    @KeepForSdk
    public static LibraryVersion getInstance() {
        return zzb;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v11, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: java.io.InputStream} */
    /* JADX WARNING: type inference failed for: r3v13 */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00ae  */
    @androidx.annotation.NonNull
    @com.google.android.gms.common.annotation.KeepForSdk
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getVersion(@androidx.annotation.NonNull java.lang.String r9) {
        /*
            r8 = this;
            java.lang.String r0 = "Failed to get app version for libraryName: "
            java.lang.String r1 = "LibraryVersion"
            java.lang.String r2 = "Please provide a valid libraryName"
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r9, r2)
            java.util.concurrent.ConcurrentHashMap r2 = r8.zzc
            boolean r2 = r2.containsKey(r9)
            if (r2 == 0) goto L_0x001a
            java.util.concurrent.ConcurrentHashMap r0 = r8.zzc
            java.lang.Object r9 = r0.get(r9)
            java.lang.String r9 = (java.lang.String) r9
            return r9
        L_0x001a:
            java.util.Properties r2 = new java.util.Properties
            r2.<init>()
            r3 = 0
            java.lang.String r4 = "/%s.properties"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ IOException -> 0x007c, all -> 0x007a }
            r6 = 0
            r5[r6] = r9     // Catch:{ IOException -> 0x007c, all -> 0x007a }
            java.lang.String r4 = java.lang.String.format(r4, r5)     // Catch:{ IOException -> 0x007c, all -> 0x007a }
            java.lang.Class<com.google.android.gms.common.internal.LibraryVersion> r5 = com.google.android.gms.common.internal.LibraryVersion.class
            java.io.InputStream r4 = r5.getResourceAsStream(r4)     // Catch:{ IOException -> 0x007c, all -> 0x007a }
            if (r4 == 0) goto L_0x0058
            r2.load(r4)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            java.lang.String r5 = "version"
            java.lang.String r3 = r2.getProperty(r5, r3)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            com.google.android.gms.common.internal.GmsLogger r2 = zza     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            r5.<init>()     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            r5.append(r9)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            java.lang.String r6 = " version is "
            r5.append(r6)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            r5.append(r3)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            r2.v(r1, r5)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            goto L_0x006c
        L_0x0058:
            com.google.android.gms.common.internal.GmsLogger r2 = zza     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            r5.<init>()     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            r5.append(r0)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            r5.append(r9)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            r2.w(r1, r5)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
        L_0x006c:
            if (r4 == 0) goto L_0x009a
            com.google.android.gms.common.util.IOUtils.closeQuietly((java.io.Closeable) r4)
            goto L_0x009a
        L_0x0072:
            r9 = move-exception
            r3 = r4
            goto L_0x00ac
        L_0x0075:
            r2 = move-exception
            r7 = r4
            r4 = r3
            r3 = r7
            goto L_0x007e
        L_0x007a:
            r9 = move-exception
            goto L_0x00ac
        L_0x007c:
            r2 = move-exception
            r4 = r3
        L_0x007e:
            com.google.android.gms.common.internal.GmsLogger r5 = zza     // Catch:{ all -> 0x00ab }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ab }
            r6.<init>()     // Catch:{ all -> 0x00ab }
            r6.append(r0)     // Catch:{ all -> 0x00ab }
            r6.append(r9)     // Catch:{ all -> 0x00ab }
            java.lang.String r0 = r6.toString()     // Catch:{ all -> 0x00ab }
            r5.e(r1, r0, r2)     // Catch:{ all -> 0x00ab }
            if (r3 == 0) goto L_0x0098
            com.google.android.gms.common.util.IOUtils.closeQuietly((java.io.Closeable) r3)
            goto L_0x0099
        L_0x0098:
        L_0x0099:
            r3 = r4
        L_0x009a:
            if (r3 != 0) goto L_0x00a5
            com.google.android.gms.common.internal.GmsLogger r0 = zza
            java.lang.String r2 = ".properties file is dropped during release process. Failure to read app version is expected during Google internal testing where locally-built libraries are used"
            r0.d(r1, r2)
            java.lang.String r3 = "UNKNOWN"
        L_0x00a5:
            java.util.concurrent.ConcurrentHashMap r0 = r8.zzc
            r0.put(r9, r3)
            return r3
        L_0x00ab:
            r9 = move-exception
        L_0x00ac:
            if (r3 == 0) goto L_0x00b1
            com.google.android.gms.common.util.IOUtils.closeQuietly((java.io.Closeable) r3)
        L_0x00b1:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.LibraryVersion.getVersion(java.lang.String):java.lang.String");
    }
}
