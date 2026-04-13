package chip.devicecontroller.model;

import java.util.Objects;

public class ChipPathId {
    private long id;
    private IdType type;

    public enum IdType {
        CONCRETE,
        WILDCARD
    }

    private ChipPathId(long id2, IdType type2) {
        this.id = id2;
        this.type = type2;
    }

    public long getId() {
        return this.id;
    }

    public IdType getType() {
        return this.type;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ChipPathId)) {
            return false;
        }
        ChipPathId that = (ChipPathId) object;
        if (this.type == that.type && this.id == that.id) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Long.valueOf(this.id), this.type});
    }

    public String toString() {
        return this.type == IdType.WILDCARD ? "WILDCARD" : String.valueOf(this.id);
    }

    public static ChipPathId forId(long id2) {
        return new ChipPathId(id2, IdType.CONCRETE);
    }

    public static ChipPathId forWildcard() {
        return new ChipPathId(-1, IdType.WILDCARD);
    }
}
