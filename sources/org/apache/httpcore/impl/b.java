package org.apache.httpcore.impl;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import org.apache.httpcore.config.a;

/* compiled from: ConnSupport */
public final class b {
    public static CharsetDecoder a(a cconfig) {
        if (cconfig == null) {
            return null;
        }
        Charset charset = cconfig.c();
        CodingErrorAction malformed = cconfig.e();
        CodingErrorAction unmappable = cconfig.h();
        if (charset == null) {
            return null;
        }
        return charset.newDecoder().onMalformedInput(malformed != null ? malformed : CodingErrorAction.REPORT).onUnmappableCharacter(unmappable != null ? unmappable : CodingErrorAction.REPORT);
    }

    public static CharsetEncoder b(a cconfig) {
        Charset charset;
        if (cconfig == null || (charset = cconfig.c()) == null) {
            return null;
        }
        CodingErrorAction malformed = cconfig.e();
        CodingErrorAction unmappable = cconfig.h();
        return charset.newEncoder().onMalformedInput(malformed != null ? malformed : CodingErrorAction.REPORT).onUnmappableCharacter(unmappable != null ? unmappable : CodingErrorAction.REPORT);
    }
}
