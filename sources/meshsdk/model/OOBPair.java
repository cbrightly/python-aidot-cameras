package meshsdk.model;

import java.io.Serializable;

public class OOBPair implements Serializable {
    public static final int IMPORT_MODE_FILE = 1;
    public static final int IMPORT_MODE_MANUAL = 0;
    public byte[] deviceUUID;
    public int importMode;
    public byte[] oob;
    public long timestamp;
}
