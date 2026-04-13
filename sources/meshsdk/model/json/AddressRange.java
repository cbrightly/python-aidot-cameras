package meshsdk.model.json;

import java.io.Serializable;

public class AddressRange implements Serializable {
    public int high;
    public int low;

    public AddressRange() {
    }

    public AddressRange(int low2, int high2) {
        this.low = low2;
        this.high = high2;
    }
}
