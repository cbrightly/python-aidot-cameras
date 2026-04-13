package chip.devicecontroller;

public final class CSRInfo {
    private byte[] csr;
    private byte[] elements;
    private byte[] elementsSignature;
    private byte[] nonce;

    public CSRInfo(byte[] nonce2, byte[] elements2, byte[] elementsSignature2, byte[] csr2) {
        this.nonce = nonce2;
        this.elements = elements2;
        this.elementsSignature = elementsSignature2;
        this.csr = csr2;
    }

    public byte[] getNonce() {
        return this.nonce;
    }

    public byte[] getElements() {
        return this.elements;
    }

    public byte[] getElementsSignature() {
        return this.elementsSignature;
    }

    public byte[] getCSR() {
        return this.csr;
    }
}
