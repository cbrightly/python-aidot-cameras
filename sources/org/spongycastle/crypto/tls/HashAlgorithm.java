package org.spongycastle.crypto.tls;

import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;

public class HashAlgorithm {
    public static String a(short hashAlgorithm) {
        switch (hashAlgorithm) {
            case 0:
                return "none";
            case 1:
                return "md5";
            case 2:
                return "sha1";
            case 3:
                return "sha224";
            case 4:
                return "sha256";
            case 5:
                return "sha384";
            case 6:
                return "sha512";
            default:
                return LDNetUtil.NETWORKTYPE_INVALID;
        }
    }

    public static String b(short hashAlgorithm) {
        return a(hashAlgorithm) + "(" + hashAlgorithm + ")";
    }

    public static boolean c(short hashAlgorithm) {
        return 224 <= hashAlgorithm && hashAlgorithm <= 255;
    }
}
