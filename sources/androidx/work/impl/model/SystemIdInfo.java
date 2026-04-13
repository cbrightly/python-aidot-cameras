package androidx.work.impl.model;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
@Entity(foreignKeys = {@ForeignKey(childColumns = {"work_spec_id"}, entity = WorkSpec.class, onDelete = 5, onUpdate = 5, parentColumns = {"id"})})
public class SystemIdInfo {
    @ColumnInfo(name = "system_id")
    public final int systemId;
    @ColumnInfo(name = "work_spec_id")
    @NonNull
    @PrimaryKey
    public final String workSpecId;

    public SystemIdInfo(@NonNull String workSpecId2, int systemId2) {
        this.workSpecId = workSpecId2;
        this.systemId = systemId2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemIdInfo)) {
            return false;
        }
        SystemIdInfo that = (SystemIdInfo) o;
        if (this.systemId != that.systemId) {
            return false;
        }
        return this.workSpecId.equals(that.workSpecId);
    }

    public int hashCode() {
        return (this.workSpecId.hashCode() * 31) + this.systemId;
    }
}
