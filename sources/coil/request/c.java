package coil.request;

/* compiled from: CachePolicy.kt */
public enum c {
    ENABLED(true, true),
    READ_ONLY(true, false),
    WRITE_ONLY(false, true),
    DISABLED(false, false);
    
    private final boolean readEnabled;
    private final boolean writeEnabled;

    private c(boolean readEnabled2, boolean writeEnabled2) {
        this.readEnabled = readEnabled2;
        this.writeEnabled = writeEnabled2;
    }

    public final boolean getReadEnabled() {
        return this.readEnabled;
    }

    public final boolean getWriteEnabled() {
        return this.writeEnabled;
    }
}
