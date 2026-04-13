package chip.setuppayload;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SetupPayload {
    public int commissioningFlow;
    public Set<DiscoveryCapability> discoveryCapabilities;
    public int discriminator;
    public int hasShortDiscriminator;
    public Map<Integer, OptionalQRCodeInfo> optionalQRCodeInfo;
    public int productId;
    public long setupPinCode;
    public int vendorId;
    public int version;

    public SetupPayload() {
        this.hasShortDiscriminator = 0;
        this.optionalQRCodeInfo = new HashMap();
    }

    public SetupPayload(int version2, int vendorId2, int productId2, int commissioningFlow2, Set<DiscoveryCapability> discoveryCapabilities2, int discriminator2, long setupPinCode2) {
        this.hasShortDiscriminator = 0;
        this.version = version2;
        this.vendorId = vendorId2;
        this.productId = productId2;
        this.commissioningFlow = commissioningFlow2;
        this.discoveryCapabilities = discoveryCapabilities2;
        this.discriminator = discriminator2;
        this.setupPinCode = setupPinCode2;
        this.optionalQRCodeInfo = new HashMap();
    }

    public void addOptionalQRCodeInfo(OptionalQRCodeInfo info) {
        this.optionalQRCodeInfo.put(Integer.valueOf(info.tag), info);
    }
}
