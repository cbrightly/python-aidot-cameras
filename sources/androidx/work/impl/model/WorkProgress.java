package androidx.work.impl.model;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.work.Data;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
@Entity(foreignKeys = {@ForeignKey(childColumns = {"work_spec_id"}, entity = WorkSpec.class, onDelete = 5, onUpdate = 5, parentColumns = {"id"})})
public class WorkProgress {
    @ColumnInfo(name = "progress")
    @NonNull
    public final Data mProgress;
    @ColumnInfo(name = "work_spec_id")
    @NonNull
    @PrimaryKey
    public final String mWorkSpecId;

    public WorkProgress(@NonNull String workSpecId, @NonNull Data progress) {
        this.mWorkSpecId = workSpecId;
        this.mProgress = progress;
    }
}
