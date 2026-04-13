package chip.platform;

import java.util.Map;

public class ChipMdnsCallbackImpl implements ChipMdnsCallback {
    public native void handleServiceBrowse(String[] strArr, String str, long j, long j2);

    public native void handleServiceResolve(String str, String str2, String str3, String str4, int i, Map<String, byte[]> map, long j, long j2);

    public String[] getTextEntryKeys(Map<String, byte[]> textEntries) {
        return (String[]) textEntries.keySet().toArray(new String[textEntries.size()]);
    }

    public byte[] getTextEntryData(Map<String, byte[]> textEntries, String key) {
        return textEntries.get(key);
    }
}
