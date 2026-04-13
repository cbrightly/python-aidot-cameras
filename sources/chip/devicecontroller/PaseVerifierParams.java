package chip.devicecontroller;

import java.util.Arrays;
import java.util.Objects;

public final class PaseVerifierParams {
    private final byte[] pakeVerifier;
    private final long setupPincode;

    public PaseVerifierParams(long setupPincode2, byte[] pakeVerifier2) {
        this.setupPincode = setupPincode2;
        this.pakeVerifier = (byte[]) pakeVerifier2.clone();
    }

    public long getSetupPincode() {
        return this.setupPincode;
    }

    public byte[] getPakeVerifier() {
        return (byte[]) this.pakeVerifier.clone();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PaseVerifierParams)) {
            return false;
        }
        PaseVerifierParams that = (PaseVerifierParams) other;
        if (this.setupPincode != that.setupPincode || !Arrays.equals(this.pakeVerifier, that.pakeVerifier)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (Objects.hash(new Object[]{Long.valueOf(this.setupPincode)}) * 31) + Arrays.hashCode(this.pakeVerifier);
    }
}
