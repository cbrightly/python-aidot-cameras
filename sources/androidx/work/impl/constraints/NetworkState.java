package androidx.work.impl.constraints;

import androidx.annotation.NonNull;

public class NetworkState {
    private boolean mIsConnected;
    private boolean mIsMetered;
    private boolean mIsNotRoaming;
    private boolean mIsValidated;

    public NetworkState(boolean isConnected, boolean isValidated, boolean isMetered, boolean isNotRoaming) {
        this.mIsConnected = isConnected;
        this.mIsValidated = isValidated;
        this.mIsMetered = isMetered;
        this.mIsNotRoaming = isNotRoaming;
    }

    public boolean isConnected() {
        return this.mIsConnected;
    }

    public boolean isValidated() {
        return this.mIsValidated;
    }

    public boolean isMetered() {
        return this.mIsMetered;
    }

    public boolean isNotRoaming() {
        return this.mIsNotRoaming;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NetworkState)) {
            return false;
        }
        NetworkState other = (NetworkState) o;
        if (this.mIsConnected == other.mIsConnected && this.mIsValidated == other.mIsValidated && this.mIsMetered == other.mIsMetered && this.mIsNotRoaming == other.mIsNotRoaming) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = 0;
        if (this.mIsConnected) {
            result = 0 + 1;
        }
        if (this.mIsValidated) {
            result += 16;
        }
        if (this.mIsMetered) {
            result += 256;
        }
        if (this.mIsNotRoaming) {
            return result + 4096;
        }
        return result;
    }

    @NonNull
    public String toString() {
        return String.format("[ Connected=%b Validated=%b Metered=%b NotRoaming=%b ]", new Object[]{Boolean.valueOf(this.mIsConnected), Boolean.valueOf(this.mIsValidated), Boolean.valueOf(this.mIsMetered), Boolean.valueOf(this.mIsNotRoaming)});
    }
}
