package androidx.work.impl.model;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
@Entity(foreignKeys = {@ForeignKey(childColumns = {"work_spec_id"}, entity = WorkSpec.class, onDelete = 5, onUpdate = 5, parentColumns = {"id"})}, indices = {@Index({"work_spec_id"})}, primaryKeys = {"tag", "work_spec_id"})
public class WorkTag {
    @ColumnInfo(name = "tag")
    @NonNull
    public final String tag;
    @ColumnInfo(name = "work_spec_id")
    @NonNull
    public final String workSpecId;

    public WorkTag(@NonNull String tag2, @NonNull String workSpecId2) {
        this.tag = tag2;
        this.workSpecId = workSpecId2;
    }
}
