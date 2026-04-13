package androidx.work.impl.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
@Entity
public class Preference {
    @ColumnInfo(name = "key")
    @NonNull
    @PrimaryKey
    public String mKey;
    @ColumnInfo(name = "long_value")
    @Nullable
    public Long mValue;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public Preference(@NonNull String key, boolean value) {
        this(key, value ? 1 : 0);
    }

    public Preference(@NonNull String key, long value) {
        this.mKey = key;
        this.mValue = Long.valueOf(value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Preference)) {
            return false;
        }
        Preference that = (Preference) o;
        if (!this.mKey.equals(that.mKey)) {
            return false;
        }
        Long l = this.mValue;
        if (l != null) {
            return l.equals(that.mValue);
        }
        if (that.mValue == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode = this.mKey.hashCode() * 31;
        Long l = this.mValue;
        return hashCode + (l != null ? l.hashCode() : 0);
    }
}
