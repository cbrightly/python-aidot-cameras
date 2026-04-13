package com.google.android.datatransport.cct;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.datatransport.Encoding;
import com.google.android.datatransport.runtime.EncodedDestination;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public final class CCTDestination implements EncodedDestination {
    private static final String DEFAULT_API_KEY;
    static final String DEFAULT_END_POINT;
    static final String DESTINATION_NAME = "cct";
    private static final String EXTRAS_DELIMITER = "\\";
    private static final String EXTRAS_VERSION_MARKER = "1$";
    public static final CCTDestination INSTANCE;
    static final String LEGACY_END_POINT;
    public static final CCTDestination LEGACY_INSTANCE;
    private static final Set<Encoding> SUPPORTED_ENCODINGS = Collections.unmodifiableSet(new HashSet(Arrays.asList(new Encoding[]{Encoding.of("proto"), Encoding.of("json")})));
    @Nullable
    private final String apiKey;
    @NonNull
    private final String endPoint;

    static {
        String mergeStrings = StringMerger.mergeStrings("hts/frbslgiggolai.o/0clgbthfra=snpoo", "tp:/ieaeogn.ogepscmvc/o/ac?omtjo_rt3");
        DEFAULT_END_POINT = mergeStrings;
        String mergeStrings2 = StringMerger.mergeStrings("hts/frbslgigp.ogepscmv/ieo/eaybtho", "tp:/ieaeogn-agolai.o/1frlglgc/aclg");
        LEGACY_END_POINT = mergeStrings2;
        String mergeStrings3 = StringMerger.mergeStrings("AzSCki82AwsLzKd5O8zo", "IayckHiZRO1EFl1aGoK");
        DEFAULT_API_KEY = mergeStrings3;
        INSTANCE = new CCTDestination(mergeStrings, (String) null);
        LEGACY_INSTANCE = new CCTDestination(mergeStrings2, mergeStrings3);
    }

    public CCTDestination(@NonNull String endPoint2, @Nullable String apiKey2) {
        this.endPoint = endPoint2;
        this.apiKey = apiKey2;
    }

    @NonNull
    public String getName() {
        return DESTINATION_NAME;
    }

    @Nullable
    public byte[] getExtras() {
        return asByteArray();
    }

    public Set<Encoding> getSupportedEncodings() {
        return SUPPORTED_ENCODINGS;
    }

    @Nullable
    public String getAPIKey() {
        return this.apiKey;
    }

    @NonNull
    public String getEndPoint() {
        return this.endPoint;
    }

    @Nullable
    public byte[] asByteArray() {
        String str = this.apiKey;
        if (str == null && this.endPoint == null) {
            return null;
        }
        Object[] objArr = new Object[4];
        objArr[0] = EXTRAS_VERSION_MARKER;
        objArr[1] = this.endPoint;
        objArr[2] = EXTRAS_DELIMITER;
        if (str == null) {
            str = "";
        }
        objArr[3] = str;
        return String.format("%s%s%s%s", objArr).getBytes(Charset.forName("UTF-8"));
    }

    @NonNull
    public static CCTDestination fromByteArray(@NonNull byte[] a) {
        String buffer = new String(a, Charset.forName("UTF-8"));
        if (buffer.startsWith(EXTRAS_VERSION_MARKER)) {
            String[] fields = buffer.substring(EXTRAS_VERSION_MARKER.length()).split(Pattern.quote(EXTRAS_DELIMITER), 2);
            if (fields.length == 2) {
                String endPoint2 = fields[0];
                if (!endPoint2.isEmpty()) {
                    String apiKey2 = fields[1];
                    return new CCTDestination(endPoint2, apiKey2.isEmpty() ? null : apiKey2);
                }
                throw new IllegalArgumentException("Missing endpoint in CCTDestination extras");
            }
            throw new IllegalArgumentException("Extra is not a valid encoded LegacyFlgDestination");
        }
        throw new IllegalArgumentException("Version marker missing from extras");
    }

    @NonNull
    static byte[] encodeString(@NonNull String s) {
        return s.getBytes(Charset.forName("UTF-8"));
    }

    @NonNull
    static String decodeExtras(@NonNull byte[] a) {
        return new String(a, Charset.forName("UTF-8"));
    }
}
