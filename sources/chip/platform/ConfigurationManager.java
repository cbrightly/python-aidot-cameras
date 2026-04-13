package chip.platform;

public interface ConfigurationManager {
    public static final String kConfigKey_CountryCode = "country-code";
    public static final String kConfigKey_FailSafeArmed = "fail-safe-armed";
    public static final String kConfigKey_HardwareVersion = "hardware-ver";
    public static final String kConfigKey_HardwareVersionString = "hardware-ver-str";
    public static final String kConfigKey_LastUsedEpochKeyId = "last-ek-id";
    public static final String kConfigKey_ManufacturingDate = "mfg-date";
    public static final String kConfigKey_MfrDeviceCert = "device-cert";
    public static final String kConfigKey_MfrDeviceICACerts = "device-ca-certs";
    public static final String kConfigKey_MfrDeviceId = "device-id";
    public static final String kConfigKey_MfrDevicePrivateKey = "device-key";
    public static final String kConfigKey_PairedAccountId = "account-id";
    public static final String kConfigKey_PartNumber = "part-number";
    public static final String kConfigKey_ProductId = "product-id";
    public static final String kConfigKey_ProductLabel = "product-label";
    public static final String kConfigKey_ProductName = "product-name";
    public static final String kConfigKey_ProductURL = "product-url";
    public static final String kConfigKey_RegulatoryLocation = "regulatory-location";
    public static final String kConfigKey_SerialNum = "serial-num";
    public static final String kConfigKey_ServiceConfig = "service-config";
    public static final String kConfigKey_ServiceId = "service-id";
    public static final String kConfigKey_SetupDiscriminator = "discriminator";
    public static final String kConfigKey_SetupPinCode = "pin-code";
    public static final String kConfigKey_SoftwareVersion = "software-version";
    public static final String kConfigKey_SoftwareVersionString = "software-version-str";
    public static final String kConfigKey_Spake2pIterationCount = "iteration-count";
    public static final String kConfigKey_Spake2pSalt = "salt";
    public static final String kConfigKey_Spake2pVerifier = "verifier";
    public static final String kConfigKey_UniqueId = "uniqueId";
    public static final String kConfigKey_WiFiStationSecType = "sta-sec-type";
    public static final String kConfigNamespace_ChipConfig = "chip-config";
    public static final String kConfigNamespace_ChipCounters = "chip-counters";
    public static final String kConfigNamespace_ChipFactory = "chip-factory";

    void clearConfigValue(String str, String str2);

    boolean configValueExists(String str, String str2);

    byte[] readConfigValueBin(String str, String str2);

    long readConfigValueLong(String str, String str2);

    String readConfigValueStr(String str, String str2);

    void writeConfigValueBin(String str, String str2, byte[] bArr);

    void writeConfigValueLong(String str, String str2, long j);

    void writeConfigValueStr(String str, String str2, String str3);
}
