package chip.platform;

public interface ServiceResolver {
    void publish(String str, String str2, String str3, int i, String[] strArr, byte[][] bArr, String[] strArr2);

    void removeServices();

    void resolve(String str, String str2, long j, long j2, ChipMdnsCallback chipMdnsCallback);
}
