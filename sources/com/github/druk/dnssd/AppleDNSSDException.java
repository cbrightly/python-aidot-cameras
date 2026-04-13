package com.github.druk.dnssd;

import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;

/* compiled from: InternalDNSSD */
public class AppleDNSSDException extends DNSSDException {
    protected int fErrorCode;

    public AppleDNSSDException(int errorCode) {
        this.fErrorCode = errorCode;
    }

    public int getErrorCode() {
        return this.fErrorCode;
    }

    public String getMessage() {
        String[] kMessages = {LDNetUtil.NETWORKTYPE_INVALID, "NO_SUCH_NAME", "NO_MEMORY", "BAD_PARAM", "BAD_REFERENCE", "BAD_STATE", "BAD_FLAGS", "UNSUPPORTED", "NOT_INITIALIZED", "NO_CACHE", "ALREADY_REGISTERED", "NAME_CONFLICT", "INVALID", "FIREWALL", "INCOMPATIBLE", "BAD_INTERFACE_INDEX", "REFUSED", "NOSUCHRECORD", "NOAUTH", "NOSUCHKEY", "NATTRAVERSAL", "DOUBLENAT", "BADTIME", "BADSIG", "BADKEY", "TRANSIENT", "SERVICENOTRUNNING", "NATPORTMAPPINGUNSUPPORTED", "NATPORTMAPPINGDISABLED"};
        int i = this.fErrorCode;
        if (i > -65537 || i <= DNSSDException.UNKNOWN - kMessages.length) {
            return super.getMessage() + "(" + String.valueOf(this.fErrorCode) + ")";
        }
        return "DNS-SD Error " + String.valueOf(this.fErrorCode) + ": " + kMessages[DNSSDException.UNKNOWN - this.fErrorCode];
    }
}
