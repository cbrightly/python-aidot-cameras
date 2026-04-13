package meshsdk.model;

import java.io.Serializable;

public class BridgingTable implements Serializable {
    public int address1;
    public int address2;
    public byte directions;
    public int netKeyIndex1;
    public int netKeyIndex2;

    public enum Direction {
        UNIDIRECTIONAL(1, "UNIDIRECTIONAL"),
        BIDIRECTIONAL(2, "BIDIRECTIONAL");
        
        public final String desc;
        public final int value;

        private Direction(int value2, String desc2) {
            this.value = value2;
            this.desc = desc2;
        }

        public static Direction getByValue(int value2) {
            for (Direction direction : values()) {
                if (direction.value == value2) {
                    return direction;
                }
            }
            return UNIDIRECTIONAL;
        }
    }
}
