package com.didichuxing.doraemonkit.kit.filemanager.sqlite.util;

import kotlin.jvm.internal.k;
import kotlin.text.c;
import org.jetbrains.annotations.NotNull;

/* compiled from: DBUtil.kt */
public final class DBUtil {
    public static final DBUtil INSTANCE = new DBUtil();
    private static final int MAX_BLOB_LENGTH = 512;

    private DBUtil() {
    }

    @NotNull
    public final String blob2String(@NotNull byte[] blob) {
        k.f(blob, "blob");
        if (blob.length > 512) {
            return "{blob}";
        }
        try {
            return new String(blob, c.e);
        } catch (Exception e) {
            return "{blob}";
        }
    }
}
