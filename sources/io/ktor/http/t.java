package io.ktor.http;

import kotlin.text.x;

/* compiled from: HttpHeaders.kt */
public final class t {
    /* access modifiers changed from: private */
    public static final boolean b(char ch) {
        return x.R("\"(),/:;<=>?@[\\]{}", ch, false, 2, (Object) null);
    }
}
