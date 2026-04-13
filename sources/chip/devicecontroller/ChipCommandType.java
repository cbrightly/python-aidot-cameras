package chip.devicecontroller;

public enum ChipCommandType {
    OFF(0),
    ON(1),
    TOGGLE(2),
    LEVEL(3);
    
    private int value;

    private ChipCommandType(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }
}
