package org.spongycastle.crypto.tls;

import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;

public class AlertDescription {
    public static String a(short alertDescription) {
        switch (alertDescription) {
            case 0:
                return "close_notify";
            case 10:
                return "unexpected_message";
            case 20:
                return "bad_record_mac";
            case 21:
                return "decryption_failed";
            case 22:
                return "record_overflow";
            case 30:
                return "decompression_failure";
            case 40:
                return "handshake_failure";
            case 41:
                return "no_certificate";
            case 42:
                return "bad_certificate";
            case 43:
                return "unsupported_certificate";
            case 44:
                return "certificate_revoked";
            case 45:
                return "certificate_expired";
            case 46:
                return "certificate_unknown";
            case 47:
                return "illegal_parameter";
            case 48:
                return "unknown_ca";
            case 49:
                return "access_denied";
            case 50:
                return "decode_error";
            case 51:
                return "decrypt_error";
            case 60:
                return "export_restriction";
            case 70:
                return "protocol_version";
            case 71:
                return "insufficient_security";
            case 80:
                return "internal_error";
            case 86:
                return "inappropriate_fallback";
            case 90:
                return "user_canceled";
            case 100:
                return "no_renegotiation";
            case 110:
                return "unsupported_extension";
            case 111:
                return "certificate_unobtainable";
            case 112:
                return "unrecognized_name";
            case 113:
                return "bad_certificate_status_response";
            case 114:
                return "bad_certificate_hash_value";
            case 115:
                return "unknown_psk_identity";
            default:
                return LDNetUtil.NETWORKTYPE_INVALID;
        }
    }

    public static String b(short alertDescription) {
        return a(alertDescription) + "(" + alertDescription + ")";
    }
}
