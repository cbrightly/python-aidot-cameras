package meshsdk.model;

import java.io.Serializable;

public class PublishModel implements Serializable {
    public static final int CREDENTIAL_FLAG_DEFAULT = 1;
    public static final int RETRANSMIT_COUNT_DEFAULT = 5;
    public static final int RETRANSMIT_INTERVAL_STEP_DEFAULT = 2;
    public static final int RFU_DEFAULT = 12;
    public static final int TTL_DEFAULT = 255;
    public int address;
    public int credential;
    public int elementAddress;
    public int modelId;
    public int period;
    public int transmit;
    public int ttl;

    public PublishModel(int elementAddress2, int modelId2, int address2, int period2) {
        this(elementAddress2, modelId2, address2, period2, 255, 1, 21);
    }

    public PublishModel(int elementAddress2, int modelId2, int address2, int period2, int ttl2, int credential2, int transmit2) {
        this.elementAddress = elementAddress2;
        this.modelId = modelId2;
        this.address = address2;
        this.period = period2;
        this.ttl = ttl2;
        this.credential = credential2;
        this.transmit = transmit2;
    }

    public int getTransmitInterval() {
        return (this.transmit & 255) >> 3;
    }

    public int getTransmitCount() {
        return this.transmit & 255 & 7;
    }
}
