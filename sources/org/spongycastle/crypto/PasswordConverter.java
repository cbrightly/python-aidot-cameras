package org.spongycastle.crypto;

public enum PasswordConverter implements CharToByteConverter {
    ASCII {
        public String getType() {
            return "ASCII";
        }

        public byte[] convert(char[] password) {
            return PBEParametersGenerator.b(password);
        }
    },
    UTF8 {
        public String getType() {
            return "UTF8";
        }

        public byte[] convert(char[] password) {
            return PBEParametersGenerator.c(password);
        }
    },
    PKCS12 {
        public String getType() {
            return "PKCS12";
        }

        public byte[] convert(char[] password) {
            return PBEParametersGenerator.a(password);
        }
    }
}
