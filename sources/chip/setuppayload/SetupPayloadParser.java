package chip.setuppayload;

import java.util.Locale;

public class SetupPayloadParser {
    private native SetupPayload fetchPayloadFromManualEntryCode(String str);

    private native SetupPayload fetchPayloadFromQrCode(String str);

    public native String getManualEntryCodeFromPayload(SetupPayload setupPayload);

    public native String getQrCodeFromPayload(SetupPayload setupPayload);

    public SetupPayload parseQrCode(String qrCodeString) {
        return fetchPayloadFromQrCode(qrCodeString);
    }

    public SetupPayload parseManualEntryCode(String entryCodeString) {
        return fetchPayloadFromManualEntryCode(entryCodeString);
    }

    static {
        System.loadLibrary("SetupPayloadParser");
    }

    public static class UnrecognizedQrCodeException extends Exception {
        private static final long serialVersionUID = 1;

        public UnrecognizedQrCodeException(String qrCode) {
            super(String.format(Locale.US, "Invalid QR code string: %s", new Object[]{qrCode}), (Throwable) null);
        }
    }

    public static class InvalidEntryCodeFormatException extends Exception {
        private static final long serialVersionUID = 1;

        public InvalidEntryCodeFormatException(String entryCode) {
            super(String.format(Locale.US, "Invalid format for entry code string: %s", new Object[]{entryCode}), (Throwable) null);
        }
    }

    public static class SetupPayloadException extends Exception {
        private static final long serialVersionUID = 1;
        public int errorCode;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public SetupPayloadException(int r5, java.lang.String r6) {
            /*
                r4 = this;
                if (r6 == 0) goto L_0x0004
                r0 = r6
                goto L_0x0016
            L_0x0004:
                java.util.Locale r0 = java.util.Locale.US
                r1 = 1
                java.lang.Object[] r1 = new java.lang.Object[r1]
                r2 = 0
                java.lang.Integer r3 = java.lang.Integer.valueOf(r5)
                r1[r2] = r3
                java.lang.String r2 = "Error Code %d"
                java.lang.String r0 = java.lang.String.format(r0, r2, r1)
            L_0x0016:
                r4.<init>(r0)
                r4.errorCode = r5
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: chip.setuppayload.SetupPayloadParser.SetupPayloadException.<init>(int, java.lang.String):void");
        }
    }
}
