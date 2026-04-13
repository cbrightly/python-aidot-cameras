package meshsdk.model;

import org.glassfish.grizzly.http.server.util.MappingData;

public enum PrivateDevice {
    PANEL(529, 7, "panel", new byte[]{17, 2, 7, 0, 50, 55, 105, 0, 7, 0, 0, 0, 17, 2, 0, 0, 2, 0, 3, 0, 4, 0, 5, 0, 0, -2, 1, -2, 2, -2, 0, -1, 1, -1, 0, 18, 1, 18, 0, MappingData.PATH, 3, 18, 4, 18, 6, 18, 7, 18, 17, 2, 0, 0, 17, 2, 1, 0, 0, 0, 5, 1, 0, MappingData.PATH, 3, 18, 4, 18, 6, 18, 7, 18, 17, 2, 0, 0, 0, 0, 5, 1, 0, MappingData.PATH, 3, 18, 4, 18, 6, 18, 7, 18, 17, 2, 0, 0}),
    CT(529, 1, "ct", new byte[]{17, 2, 1, 0, 50, 55, 105, 0, 7, 0, 0, 0, 25, 1, 0, 0, 2, 0, 3, 0, 4, 0, 5, 0, 0, -2, 1, -2, 2, -2, 0, -1, 1, -1, 0, 18, 1, 18, 0, MappingData.PATH, 2, MappingData.PATH, 4, MappingData.PATH, 6, MappingData.PATH, 7, MappingData.PATH, 3, 18, 4, 18, 6, 18, 7, 18, 0, 19, 1, 19, 3, 19, 4, 19, 17, 2, 0, 0, 0, 0, 2, 0, 2, MappingData.PATH, 6, 19}),
    HSL(529, 2, "hsl", new byte[]{17, 2, 2, 0, 51, 49, 105, 0, 7, 0, 0, 0, 17, 1, 0, 0, 2, 0, 3, 0, 4, 0, 0, -2, 1, -2, 0, -1, 1, -1, 0, MappingData.PATH, 2, MappingData.PATH, 4, MappingData.PATH, 6, MappingData.PATH, 7, MappingData.PATH, 0, 19, 1, 19, 7, 19, 8, 19, 17, 2, 0, 0, 0, 0, 2, 0, 2, MappingData.PATH, 10, 19, 0, 0, 2, 0, 2, MappingData.PATH, 11, 19});
    
    private final byte[] cpsData;
    private final String name;
    private final int pid;
    private final int vid;

    private PrivateDevice(int vid2, int pid2, String name2, byte[] cpsData2) {
        this.vid = vid2;
        this.pid = pid2;
        this.name = name2;
        this.cpsData = cpsData2;
    }

    public int getVid() {
        return this.vid;
    }

    public int getPid() {
        return this.pid;
    }

    public String getName() {
        return this.name;
    }

    public byte[] getCpsData() {
        return this.cpsData;
    }

    public static PrivateDevice filter(byte[] deviceUUID) {
        if (deviceUUID.length < 3) {
            return null;
        }
        int vid2 = (deviceUUID[0] & 255) + ((deviceUUID[1] & 255) << 8);
        int pid2 = deviceUUID[2] & 255;
        for (PrivateDevice device : values()) {
            if (device.vid == vid2 && device.pid == pid2) {
                return device;
            }
        }
        return null;
    }
}
