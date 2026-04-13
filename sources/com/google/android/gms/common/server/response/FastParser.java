package com.google.android.gms.common.server.response;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import io.netty.util.internal.StringUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@ShowFirstParty
@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public class FastParser<T extends FastJsonResponse> {
    private static final char[] zaa = {'u', 'l', 'l'};
    private static final char[] zab = {'r', 'u', 'e'};
    private static final char[] zac = {'r', 'u', 'e', StringUtil.DOUBLE_QUOTE};
    private static final char[] zad = {'a', 'l', 's', 'e'};
    private static final char[] zae = {'a', 'l', 's', 'e', StringUtil.DOUBLE_QUOTE};
    private static final char[] zaf = {10};
    private static final zai zag = new zaa();
    private static final zai zah = new zab();
    private static final zai zai = new zac();
    private static final zai zaj = new zad();
    private static final zai zak = new zae();
    private static final zai zal = new zaf();
    private static final zai zam = new zag();
    private static final zai zan = new zah();
    private final char[] zao = new char[1];
    private final char[] zap = new char[32];
    private final char[] zaq = new char[1024];
    private final StringBuilder zar = new StringBuilder(32);
    private final StringBuilder zas = new StringBuilder(1024);
    private final Stack zat = new Stack();

    @ShowFirstParty
    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public static class ParseException extends Exception {
        public ParseException(@NonNull String str) {
            super(str);
        }

        public ParseException(@NonNull String str, @NonNull Throwable th) {
            super("Error instantiating inner object", th);
        }

        public ParseException(@NonNull Throwable th) {
            super(th);
        }
    }

    private static final String zaA(BufferedReader bufferedReader, char[] cArr, StringBuilder sb, @Nullable char[] cArr2) {
        sb.setLength(0);
        bufferedReader.mark(cArr.length);
        boolean z = false;
        boolean z2 = false;
        loop0:
        while (true) {
            int read = bufferedReader.read(cArr);
            if (read != -1) {
                for (int i = 0; i < read; i++) {
                    char c = cArr[i];
                    if (Character.isISOControl(c)) {
                        if (cArr2 == null) {
                            break loop0;
                        }
                        int i2 = 0;
                        while (i2 <= 0) {
                            if (cArr2[i2] != c) {
                                i2++;
                            }
                        }
                        break loop0;
                    }
                    if (c == '\"') {
                        if (!z) {
                            sb.append(cArr, 0, i);
                            bufferedReader.reset();
                            bufferedReader.skip((long) (i + 1));
                            return z2 ? JsonUtils.unescapeString(sb.toString()) : sb.toString();
                        }
                    } else if (c == '\\') {
                        z = !z;
                        z2 = true;
                    }
                    z = false;
                }
                sb.append(cArr, 0, read);
                bufferedReader.mark(cArr.length);
            } else {
                throw new ParseException("Unexpected EOF while parsing string");
            }
        }
        throw new ParseException("Unexpected control character while reading string");
    }

    private final char zai(BufferedReader bufferedReader) {
        if (bufferedReader.read(this.zao) == -1) {
            return 0;
        }
        while (Character.isWhitespace(this.zao[0])) {
            if (bufferedReader.read(this.zao) == -1) {
                return 0;
            }
        }
        return this.zao[0];
    }

    /* access modifiers changed from: private */
    public final double zaj(BufferedReader bufferedReader) {
        int zam2 = zam(bufferedReader, this.zaq);
        if (zam2 == 0) {
            return 0.0d;
        }
        return Double.parseDouble(new String(this.zaq, 0, zam2));
    }

    /* access modifiers changed from: private */
    public final float zak(BufferedReader bufferedReader) {
        int zam2 = zam(bufferedReader, this.zaq);
        if (zam2 == 0) {
            return 0.0f;
        }
        return Float.parseFloat(new String(this.zaq, 0, zam2));
    }

    /* access modifiers changed from: private */
    public final int zal(BufferedReader bufferedReader) {
        int i;
        int i2;
        int i3;
        int i4;
        int zam2 = zam(bufferedReader, this.zaq);
        if (zam2 == 0) {
            return 0;
        }
        char[] cArr = this.zaq;
        if (zam2 > 0) {
            char c = cArr[0];
            if (c == '-') {
                i = Integer.MIN_VALUE;
            } else {
                i = -2147483647;
            }
            if (c == '-') {
                i2 = 1;
            } else {
                i2 = 0;
            }
            if (i2 < zam2) {
                i4 = i2 + 1;
                int digit = Character.digit(cArr[i2], 10);
                if (digit >= 0) {
                    i3 = -digit;
                } else {
                    throw new ParseException("Unexpected non-digit character");
                }
            } else {
                i3 = 0;
                i4 = i2;
            }
            while (i4 < zam2) {
                int i5 = i4 + 1;
                int digit2 = Character.digit(cArr[i4], 10);
                if (digit2 < 0) {
                    throw new ParseException("Unexpected non-digit character");
                } else if (i3 >= -214748364) {
                    int i6 = i3 * 10;
                    if (i6 >= i + digit2) {
                        i3 = i6 - digit2;
                        i4 = i5;
                    } else {
                        throw new ParseException("Number too large");
                    }
                } else {
                    throw new ParseException("Number too large");
                }
            }
            if (i2 == 0) {
                return -i3;
            }
            if (i4 > 1) {
                return i3;
            }
            throw new ParseException("No digits to parse");
        }
        throw new ParseException("No number to parse");
    }

    @ResultIgnorabilityUnspecified
    private final int zam(BufferedReader bufferedReader, char[] cArr) {
        int i;
        char zai2 = zai(bufferedReader);
        if (zai2 == 0) {
            throw new ParseException("Unexpected EOF");
        } else if (zai2 == ',') {
            throw new ParseException("Missing value");
        } else if (zai2 == 'n') {
            zax(bufferedReader, zaa);
            return 0;
        } else {
            bufferedReader.mark(1024);
            if (zai2 == '\"') {
                i = 0;
                boolean z = false;
                while (i < 1024 && bufferedReader.read(cArr, i, 1) != -1) {
                    char c = cArr[i];
                    if (!Character.isISOControl(c)) {
                        if (c != '\"') {
                            z = c == '\\' ? !z : false;
                        } else if (z) {
                            z = false;
                        } else {
                            bufferedReader.reset();
                            bufferedReader.skip((long) (i + 1));
                            return i;
                        }
                        i++;
                    } else {
                        throw new ParseException("Unexpected control character while reading string");
                    }
                }
            } else {
                cArr[0] = zai2;
                int i2 = 1;
                while (i < 1024 && bufferedReader.read(cArr, i, 1) != -1) {
                    char c2 = cArr[i];
                    if (c2 == '}' || c2 == ',' || Character.isWhitespace(c2) || cArr[i] == ']') {
                        bufferedReader.reset();
                        bufferedReader.skip((long) (i - 1));
                        cArr[i] = 0;
                        return i;
                    }
                    i2 = i + 1;
                }
            }
            if (i == 1024) {
                throw new ParseException("Absurdly long value");
            }
            throw new ParseException("Unexpected EOF");
        }
    }

    /* access modifiers changed from: private */
    public final long zan(BufferedReader bufferedReader) {
        long j;
        long j2;
        int i;
        int zam2 = zam(bufferedReader, this.zaq);
        if (zam2 == 0) {
            return 0;
        }
        char[] cArr = this.zaq;
        if (zam2 > 0) {
            int i2 = 0;
            char c = cArr[0];
            if (c == '-') {
                j = Long.MIN_VALUE;
            } else {
                j = -9223372036854775807L;
            }
            if (c == '-') {
                i2 = 1;
            }
            if (i2 < zam2) {
                i = i2 + 1;
                int digit = Character.digit(cArr[i2], 10);
                if (digit >= 0) {
                    j2 = (long) (-digit);
                } else {
                    throw new ParseException("Unexpected non-digit character");
                }
            } else {
                j2 = 0;
                i = i2;
            }
            while (i < zam2) {
                int i3 = i + 1;
                int digit2 = Character.digit(cArr[i], 10);
                if (digit2 < 0) {
                    throw new ParseException("Unexpected non-digit character");
                } else if (j2 >= -922337203685477580L) {
                    long j3 = j2 * 10;
                    int i4 = zam2;
                    long j4 = (long) digit2;
                    if (j3 >= j + j4) {
                        j2 = j3 - j4;
                        zam2 = i4;
                        i = i3;
                    } else {
                        throw new ParseException("Number too large");
                    }
                } else {
                    throw new ParseException("Number too large");
                }
            }
            if (i2 == 0) {
                return -j2;
            }
            if (i > 1) {
                return j2;
            }
            throw new ParseException("No digits to parse");
        }
        throw new ParseException("No number to parse");
    }

    /* access modifiers changed from: private */
    @Nullable
    public final String zao(BufferedReader bufferedReader) {
        return zap(bufferedReader, this.zap, this.zar, (char[]) null);
    }

    @Nullable
    private final String zap(BufferedReader bufferedReader, char[] cArr, StringBuilder sb, @Nullable char[] cArr2) {
        switch (zai(bufferedReader)) {
            case '\"':
                return zaA(bufferedReader, cArr, sb, cArr2);
            case 'n':
                zax(bufferedReader, zaa);
                return null;
            default:
                throw new ParseException("Expected string");
        }
    }

    @ResultIgnorabilityUnspecified
    @Nullable
    private final String zaq(BufferedReader bufferedReader) {
        this.zat.push(2);
        char zai2 = zai(bufferedReader);
        switch (zai2) {
            case '\"':
                this.zat.push(3);
                String zaA = zaA(bufferedReader, this.zap, this.zar, (char[]) null);
                zaw(3);
                if (zai(bufferedReader) == ':') {
                    return zaA;
                }
                throw new ParseException("Expected key/value separator");
            case ']':
                zaw(2);
                zaw(1);
                zaw(5);
                return null;
            case '}':
                zaw(2);
                return null;
            default:
                throw new ParseException("Unexpected token: " + zai2);
        }
    }

    @Nullable
    private final String zar(BufferedReader bufferedReader) {
        bufferedReader.mark(1024);
        int i = 1;
        switch (zai(bufferedReader)) {
            case '\"':
                if (bufferedReader.read(this.zao) != -1) {
                    char c = this.zao[0];
                    boolean z = false;
                    do {
                        if (c == '\"') {
                            if (!z) {
                                break;
                            } else {
                                c = '\"';
                                z = true;
                            }
                        }
                        if (c == '\\') {
                            z = !z;
                        } else {
                            z = false;
                        }
                        if (bufferedReader.read(this.zao) != -1) {
                            c = this.zao[0];
                        } else {
                            throw new ParseException("Unexpected EOF while parsing string");
                        }
                    } while (!Character.isISOControl(c));
                    throw new ParseException("Unexpected control character while reading string");
                }
                throw new ParseException("Unexpected EOF while parsing string");
            case ',':
                throw new ParseException("Missing value");
            case '[':
                this.zat.push(5);
                bufferedReader.mark(32);
                if (zai(bufferedReader) != ']') {
                    bufferedReader.reset();
                    boolean z2 = false;
                    boolean z3 = false;
                    while (i > 0) {
                        char zai2 = zai(bufferedReader);
                        if (zai2 == 0) {
                            throw new ParseException("Unexpected EOF while parsing array");
                        } else if (!Character.isISOControl(zai2)) {
                            if (zai2 == '\"') {
                                if (!z3) {
                                    z2 = !z2;
                                }
                                zai2 = '\"';
                            }
                            if (zai2 == '[') {
                                if (!z2) {
                                    i++;
                                }
                                zai2 = '[';
                            }
                            if (zai2 == ']' && !z2) {
                                i--;
                            }
                            z3 = (zai2 != '\\' || !z2) ? false : !z3;
                        } else {
                            throw new ParseException("Unexpected control character while reading array");
                        }
                    }
                    zaw(5);
                    break;
                } else {
                    zaw(5);
                    break;
                }
            case '{':
                this.zat.push(1);
                bufferedReader.mark(32);
                char zai3 = zai(bufferedReader);
                if (zai3 == '}') {
                    zaw(1);
                    break;
                } else if (zai3 == '\"') {
                    bufferedReader.reset();
                    zaq(bufferedReader);
                    do {
                    } while (zar(bufferedReader) != null);
                    zaw(1);
                    break;
                } else {
                    throw new ParseException("Unexpected token " + zai3);
                }
            default:
                bufferedReader.reset();
                zam(bufferedReader, this.zaq);
                break;
        }
        char zai4 = zai(bufferedReader);
        switch (zai4) {
            case ',':
                zaw(2);
                return zaq(bufferedReader);
            case '}':
                zaw(2);
                return null;
            default:
                throw new ParseException("Unexpected token " + zai4);
        }
    }

    /* access modifiers changed from: private */
    @Nullable
    public final BigDecimal zas(BufferedReader bufferedReader) {
        int zam2 = zam(bufferedReader, this.zaq);
        if (zam2 == 0) {
            return null;
        }
        return new BigDecimal(new String(this.zaq, 0, zam2));
    }

    /* access modifiers changed from: private */
    @Nullable
    public final BigInteger zat(BufferedReader bufferedReader) {
        int zam2 = zam(bufferedReader, this.zaq);
        if (zam2 == 0) {
            return null;
        }
        return new BigInteger(new String(this.zaq, 0, zam2));
    }

    @Nullable
    private final ArrayList zau(BufferedReader bufferedReader, zai zai2) {
        char zai3 = zai(bufferedReader);
        if (zai3 == 'n') {
            zax(bufferedReader, zaa);
            return null;
        } else if (zai3 == '[') {
            this.zat.push(5);
            ArrayList arrayList = new ArrayList();
            while (true) {
                bufferedReader.mark(1024);
                switch (zai(bufferedReader)) {
                    case 0:
                        throw new ParseException("Unexpected EOF");
                    case ',':
                        break;
                    case ']':
                        zaw(5);
                        return arrayList;
                    default:
                        bufferedReader.reset();
                        arrayList.add(zai2.zaa(this, bufferedReader));
                        break;
                }
            }
        } else {
            throw new ParseException("Expected start of array");
        }
    }

    @Nullable
    private final ArrayList zav(BufferedReader bufferedReader, FastJsonResponse.Field field) {
        ArrayList arrayList = new ArrayList();
        char zai2 = zai(bufferedReader);
        switch (zai2) {
            case ']':
                zaw(5);
                return arrayList;
            case 'n':
                zax(bufferedReader, zaa);
                zaw(5);
                return null;
            case '{':
                this.zat.push(1);
                while (true) {
                    try {
                        FastJsonResponse zad2 = field.zad();
                        if (!zaz(bufferedReader, zad2)) {
                            return arrayList;
                        }
                        arrayList.add(zad2);
                        char zai3 = zai(bufferedReader);
                        switch (zai3) {
                            case ',':
                                if (zai(bufferedReader) == '{') {
                                    this.zat.push(1);
                                } else {
                                    throw new ParseException("Expected start of next object in array");
                                }
                            case ']':
                                zaw(5);
                                return arrayList;
                            default:
                                throw new ParseException("Unexpected token: " + zai3);
                        }
                    } catch (InstantiationException e) {
                        throw new ParseException("Error instantiating inner object", e);
                    } catch (IllegalAccessException e2) {
                        throw new ParseException("Error instantiating inner object", e2);
                    }
                }
            default:
                throw new ParseException("Unexpected token: " + zai2);
        }
    }

    private final void zaw(int i) {
        if (!this.zat.isEmpty()) {
            int intValue = ((Integer) this.zat.pop()).intValue();
            if (intValue != i) {
                throw new ParseException("Expected state " + i + " but had " + intValue);
            }
            return;
        }
        throw new ParseException("Expected state " + i + " but had empty stack");
    }

    private final void zax(BufferedReader bufferedReader, char[] cArr) {
        int i = 0;
        while (true) {
            int length = cArr.length;
            if (i < length) {
                int read = bufferedReader.read(this.zap, 0, length - i);
                if (read != -1) {
                    int i2 = 0;
                    while (i2 < read) {
                        if (cArr[i2 + i] == this.zap[i2]) {
                            i2++;
                        } else {
                            throw new ParseException("Unexpected character");
                        }
                    }
                    i += read;
                } else {
                    throw new ParseException("Unexpected EOF");
                }
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public final boolean zay(BufferedReader bufferedReader, boolean z) {
        char[] cArr;
        char[] cArr2;
        char zai2 = zai(bufferedReader);
        switch (zai2) {
            case '\"':
                if (!z) {
                    return zay(bufferedReader, true);
                }
                throw new ParseException("No boolean value found in string");
            case 'f':
                if (z) {
                    cArr = zae;
                } else {
                    cArr = zad;
                }
                zax(bufferedReader, cArr);
                return false;
            case 'n':
                zax(bufferedReader, zaa);
                return false;
            case 't':
                if (z) {
                    cArr2 = zac;
                } else {
                    cArr2 = zab;
                }
                zax(bufferedReader, cArr2);
                return true;
            default:
                throw new ParseException("Unexpected token: " + zai2);
        }
    }

    @ResultIgnorabilityUnspecified
    private final boolean zaz(BufferedReader bufferedReader, FastJsonResponse fastJsonResponse) {
        HashMap hashMap;
        Map<String, FastJsonResponse.Field<?, ?>> fieldMappings = fastJsonResponse.getFieldMappings();
        String zaq2 = zaq(bufferedReader);
        if (zaq2 != null) {
            while (zaq2 != null) {
                FastJsonResponse.Field field = fieldMappings.get(zaq2);
                if (field == null) {
                    zaq2 = zar(bufferedReader);
                } else {
                    this.zat.push(4);
                    int i = field.zaa;
                    switch (i) {
                        case 0:
                            if (!field.zab) {
                                fastJsonResponse.zau(field, zal(bufferedReader));
                                break;
                            } else {
                                fastJsonResponse.zav(field, zau(bufferedReader, zag));
                                break;
                            }
                        case 1:
                            if (!field.zab) {
                                fastJsonResponse.zae(field, zat(bufferedReader));
                                break;
                            } else {
                                fastJsonResponse.zag(field, zau(bufferedReader, zam));
                                break;
                            }
                        case 2:
                            if (!field.zab) {
                                fastJsonResponse.zax(field, zan(bufferedReader));
                                break;
                            } else {
                                fastJsonResponse.zay(field, zau(bufferedReader, zah));
                                break;
                            }
                        case 3:
                            if (!field.zab) {
                                fastJsonResponse.zaq(field, zak(bufferedReader));
                                break;
                            } else {
                                fastJsonResponse.zas(field, zau(bufferedReader, zai));
                                break;
                            }
                        case 4:
                            if (!field.zab) {
                                fastJsonResponse.zam(field, zaj(bufferedReader));
                                break;
                            } else {
                                fastJsonResponse.zao(field, zau(bufferedReader, zaj));
                                break;
                            }
                        case 5:
                            if (!field.zab) {
                                fastJsonResponse.zaa(field, zas(bufferedReader));
                                break;
                            } else {
                                fastJsonResponse.zac(field, zau(bufferedReader, zan));
                                break;
                            }
                        case 6:
                            if (!field.zab) {
                                fastJsonResponse.zai(field, zay(bufferedReader, false));
                                break;
                            } else {
                                fastJsonResponse.zaj(field, zau(bufferedReader, zak));
                                break;
                            }
                        case 7:
                            if (!field.zab) {
                                fastJsonResponse.zaA(field, zao(bufferedReader));
                                break;
                            } else {
                                fastJsonResponse.zaC(field, zau(bufferedReader, zal));
                                break;
                            }
                        case 8:
                            fastJsonResponse.zal(field, Base64Utils.decode(zap(bufferedReader, this.zaq, this.zas, zaf)));
                            break;
                        case 9:
                            fastJsonResponse.zal(field, Base64Utils.decodeUrlSafe(zap(bufferedReader, this.zaq, this.zas, zaf)));
                            break;
                        case 10:
                            char zai2 = zai(bufferedReader);
                            if (zai2 == 'n') {
                                zax(bufferedReader, zaa);
                                hashMap = null;
                            } else if (zai2 == '{') {
                                this.zat.push(1);
                                hashMap = new HashMap();
                                while (true) {
                                    switch (zai(bufferedReader)) {
                                        case 0:
                                            throw new ParseException("Unexpected EOF");
                                        case '\"':
                                            String zaA = zaA(bufferedReader, this.zap, this.zar, (char[]) null);
                                            if (zai(bufferedReader) != ':') {
                                                throw new ParseException("No map value found for key ".concat(String.valueOf(zaA)));
                                            } else if (zai(bufferedReader) == '\"') {
                                                hashMap.put(zaA, zaA(bufferedReader, this.zap, this.zar, (char[]) null));
                                                char zai3 = zai(bufferedReader);
                                                if (zai3 == ',') {
                                                    break;
                                                } else if (zai3 == '}') {
                                                    zaw(1);
                                                    break;
                                                } else {
                                                    throw new ParseException("Unexpected character while parsing string map: " + zai3);
                                                }
                                            } else {
                                                throw new ParseException("Expected String value for key ".concat(String.valueOf(zaA)));
                                            }
                                        case '}':
                                            zaw(1);
                                            break;
                                    }
                                }
                            } else {
                                throw new ParseException("Expected start of a map object");
                            }
                            fastJsonResponse.zaB(field, hashMap);
                            break;
                        case 11:
                            if (field.zab) {
                                char zai4 = zai(bufferedReader);
                                if (zai4 == 'n') {
                                    zax(bufferedReader, zaa);
                                    fastJsonResponse.addConcreteTypeArrayInternal(field, field.zae, (ArrayList) null);
                                    break;
                                } else {
                                    this.zat.push(5);
                                    if (zai4 == '[') {
                                        fastJsonResponse.addConcreteTypeArrayInternal(field, field.zae, zav(bufferedReader, field));
                                        break;
                                    } else {
                                        throw new ParseException("Expected array start");
                                    }
                                }
                            } else {
                                char zai5 = zai(bufferedReader);
                                if (zai5 == 'n') {
                                    zax(bufferedReader, zaa);
                                    fastJsonResponse.addConcreteTypeInternal(field, field.zae, null);
                                    break;
                                } else {
                                    this.zat.push(1);
                                    if (zai5 == '{') {
                                        try {
                                            FastJsonResponse zad2 = field.zad();
                                            zaz(bufferedReader, zad2);
                                            fastJsonResponse.addConcreteTypeInternal(field, field.zae, zad2);
                                            break;
                                        } catch (InstantiationException e) {
                                            throw new ParseException("Error instantiating inner object", e);
                                        } catch (IllegalAccessException e2) {
                                            throw new ParseException("Error instantiating inner object", e2);
                                        }
                                    } else {
                                        throw new ParseException("Expected start of object");
                                    }
                                }
                            }
                        default:
                            throw new ParseException("Invalid field type " + i);
                    }
                    zaw(4);
                    zaw(2);
                    char zai6 = zai(bufferedReader);
                    switch (zai6) {
                        case ',':
                            zaq2 = zaq(bufferedReader);
                            break;
                        case '}':
                            zaq2 = null;
                            break;
                        default:
                            throw new ParseException("Expected end of object or field separator, but found: " + zai6);
                    }
                }
            }
            zaw(1);
            return true;
        }
        zaw(1);
        return false;
    }

    @KeepForSdk
    public void parse(@NonNull InputStream is, @NonNull T response) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is), 1024);
        try {
            this.zat.push(0);
            char zai2 = zai(bufferedReader);
            switch (zai2) {
                case 0:
                    throw new ParseException("No data to parse");
                case '[':
                    this.zat.push(5);
                    Map<String, FastJsonResponse.Field<?, ?>> fieldMappings = response.getFieldMappings();
                    if (fieldMappings.size() == 1) {
                        FastJsonResponse.Field field = (FastJsonResponse.Field) fieldMappings.entrySet().iterator().next().getValue();
                        response.addConcreteTypeArrayInternal(field, field.zae, zav(bufferedReader, field));
                        break;
                    } else {
                        throw new ParseException("Object array response class must have a single Field");
                    }
                case '{':
                    this.zat.push(1);
                    zaz(bufferedReader, response);
                    break;
                default:
                    throw new ParseException("Unexpected token: " + zai2);
            }
            zaw(0);
            try {
                bufferedReader.close();
            } catch (IOException e) {
                Log.w("FastParser", "Failed to close reader while parsing.");
            }
        } catch (IOException e2) {
            throw new ParseException((Throwable) e2);
        } catch (Throwable th) {
            try {
                bufferedReader.close();
            } catch (IOException e3) {
                Log.w("FastParser", "Failed to close reader while parsing.");
            }
            throw th;
        }
    }
}
