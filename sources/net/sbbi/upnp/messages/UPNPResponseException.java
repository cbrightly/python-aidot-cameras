package net.sbbi.upnp.messages;

public class UPNPResponseException extends Exception {
    private static final long serialVersionUID = 8313107558129180594L;
    protected int detailErrorCode;
    protected String detailErrorDescription;
    protected String faultCode;
    protected String faultString;

    public UPNPResponseException() {
    }

    public UPNPResponseException(int detailErrorCode2, String detailErrorDescription2) {
        this.detailErrorCode = detailErrorCode2;
        this.detailErrorDescription = detailErrorDescription2;
    }

    public String getFaultCode() {
        String str = this.faultCode;
        return str == null ? "Client" : str;
    }

    public String getFaultString() {
        String str = this.faultString;
        return str == null ? "UPnPError" : str;
    }

    public int getDetailErrorCode() {
        return this.detailErrorCode;
    }

    public String getDetailErrorDescription() {
        return this.detailErrorDescription;
    }

    public String getMessage() {
        return "Detailed error code :" + this.detailErrorCode + ", Detailed error description :" + this.detailErrorDescription;
    }

    public String getLocalizedMessage() {
        return getMessage();
    }
}
