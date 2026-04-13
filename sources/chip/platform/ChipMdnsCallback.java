package chip.platform;

import java.util.Map;

public interface ChipMdnsCallback {
    void handleServiceBrowse(String[] strArr, String str, long j, long j2);

    void handleServiceResolve(String str, String str2, String str3, String str4, int i, Map<String, byte[]> map, long j, long j2);
}
