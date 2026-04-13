package chip.clusterinfo;

public class CommandParameterInfo {
    public String name;
    public Class<?> type;
    public Class<?> underlyingType;

    public CommandParameterInfo() {
    }

    public CommandParameterInfo(String name2, Class<?> type2, Class<?> underlyingType2) {
        this.name = name2;
        this.type = type2;
        this.underlyingType = underlyingType2;
    }
}
