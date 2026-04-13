package chip.platform;

public interface KeyValueStoreManager {
    void delete(String str);

    String get(String str);

    void set(String str, String str2);
}
