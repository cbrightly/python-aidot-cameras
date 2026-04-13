package okhttp3.internal.http;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.l0;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import okhttp3.d0;
import okhttp3.h;
import okhttp3.internal.b;
import okhttp3.m;
import okhttp3.n;
import okhttp3.u;
import okhttp3.v;
import okio.c;
import okio.f;
import org.glassfish.grizzly.http.server.Constants;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpHeaders.kt */
public final class e {
    private static final f a;
    private static final f b;

    static {
        f.a aVar = f.Companion;
        a = aVar.d("\"\\");
        b = aVar.d("\t ,=");
    }

    @NotNull
    public static final List<h> b(@NotNull u $this$parseChallenges, @NotNull String headerName) {
        k.f($this$parseChallenges, "$this$parseChallenges");
        k.f(headerName, "headerName");
        List result = new ArrayList();
        int size = $this$parseChallenges.size();
        for (int h = 0; h < size; h++) {
            if (w.y(headerName, $this$parseChallenges.b(h), true)) {
                try {
                    d(new c().writeUtf8($this$parseChallenges.h(h)), result);
                } catch (EOFException e) {
                    okhttp3.internal.platform.h.c.g().k("Unable to parse challenge", 5, e);
                }
            }
        }
        return result;
    }

    private static final void d(@NotNull c $this$readChallengeHeader, List<h> result) {
        String parameterValue;
        String peek = null;
        while (true) {
            if (peek == null) {
                h($this$readChallengeHeader);
                peek = f($this$readChallengeHeader);
                if (peek == null) {
                    return;
                }
            }
            String schemeName = peek;
            boolean commaPrefixed = h($this$readChallengeHeader);
            peek = f($this$readChallengeHeader);
            if (peek != null) {
                byte b2 = (byte) 61;
                int eqCount = b.I($this$readChallengeHeader, b2);
                boolean commaSuffixed = h($this$readChallengeHeader);
                if (commaPrefixed || (!commaSuffixed && !$this$readChallengeHeader.r0())) {
                    Map parameters = new LinkedHashMap();
                    int eqCount2 = eqCount + b.I($this$readChallengeHeader, b2);
                    while (true) {
                        if (peek == null) {
                            peek = f($this$readChallengeHeader);
                            if (h($this$readChallengeHeader)) {
                                break;
                            }
                            eqCount2 = b.I($this$readChallengeHeader, b2);
                        }
                        if (eqCount2 == 0) {
                            break;
                        } else if (eqCount2 <= 1 && !h($this$readChallengeHeader)) {
                            if (i($this$readChallengeHeader, (byte) 34)) {
                                parameterValue = e($this$readChallengeHeader);
                            } else {
                                parameterValue = f($this$readChallengeHeader);
                            }
                            if (parameterValue != null) {
                                String replaced = (String) parameters.put(peek, parameterValue);
                                peek = null;
                                if (replaced == null) {
                                    if (!h($this$readChallengeHeader) && !$this$readChallengeHeader.r0()) {
                                        return;
                                    }
                                } else {
                                    return;
                                }
                            } else {
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                    result.add(new h(schemeName, parameters));
                } else {
                    Map singletonMap = Collections.singletonMap((Object) null, peek + w.D("=", eqCount));
                    k.b(singletonMap, "Collections.singletonMap…ek + \"=\".repeat(eqCount))");
                    result.add(new h(schemeName, singletonMap));
                    peek = null;
                }
            } else if ($this$readChallengeHeader.r0()) {
                result.add(new h(schemeName, l0.f()));
                return;
            } else {
                return;
            }
        }
    }

    private static final boolean h(@NotNull c $this$skipCommasAndWhitespace) {
        boolean commaFound = false;
        while (!$this$skipCommasAndWhitespace.r0()) {
            switch ($this$skipCommasAndWhitespace.n(0)) {
                case 9:
                case 32:
                    $this$skipCommasAndWhitespace.readByte();
                    continue;
                case 44:
                    $this$skipCommasAndWhitespace.readByte();
                    commaFound = true;
                    continue;
            }
            return commaFound;
        }
        return commaFound;
    }

    private static final boolean i(@NotNull c $this$startsWith, byte prefix) {
        return !$this$startsWith.r0() && $this$startsWith.n(0) == prefix;
    }

    private static final String e(@NotNull c $this$readQuotedString) {
        byte b2 = (byte) 34;
        if ($this$readQuotedString.readByte() == b2) {
            c result = new c();
            while (true) {
                long i = $this$readQuotedString.N(a);
                if (i == -1) {
                    return null;
                }
                if ($this$readQuotedString.n(i) == b2) {
                    result.write($this$readQuotedString, i);
                    $this$readQuotedString.readByte();
                    return result.a1();
                } else if ($this$readQuotedString.e1() == i + 1) {
                    return null;
                } else {
                    result.write($this$readQuotedString, i);
                    $this$readQuotedString.readByte();
                    result.write($this$readQuotedString, 1);
                }
            }
        } else {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    private static final String f(@NotNull c $this$readToken) {
        long tokenSize = $this$readToken.N(b);
        if (tokenSize == -1) {
            tokenSize = $this$readToken.e1();
        }
        if (tokenSize != 0) {
            return $this$readToken.b1(tokenSize);
        }
        return null;
    }

    public static final void g(@NotNull n $this$receiveHeaders, @NotNull v url, @NotNull u headers) {
        k.f($this$receiveHeaders, "$this$receiveHeaders");
        k.f(url, "url");
        k.f(headers, "headers");
        if ($this$receiveHeaders != n.a) {
            List cookies = m.e.e(url, headers);
            if (!cookies.isEmpty()) {
                $this$receiveHeaders.saveFromResponse(url, cookies);
            }
        }
    }

    public static final boolean c(@NotNull d0 $this$promisesBody) {
        k.f($this$promisesBody, "$this$promisesBody");
        if (k.a($this$promisesBody.J().h(), Constants.HEAD)) {
            return false;
        }
        int responseCode = $this$promisesBody.j();
        if (((responseCode >= 100 && responseCode < 200) || responseCode == 204 || responseCode == 304) && b.s($this$promisesBody) == -1 && !w.y("chunked", d0.r($this$promisesBody, Constants.TRANSFERENCODING, (String) null, 2, (Object) null), true)) {
            return false;
        }
        return true;
    }

    public static final boolean a(@NotNull d0 response) {
        k.f(response, "response");
        return c(response);
    }
}
