package androidx.room.util;

import android.database.Cursor;
import android.database.MatrixCursor;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class CursorUtil {
    @NonNull
    public static Cursor copyAndClose(@NonNull Cursor c) {
        try {
            MatrixCursor matrixCursor = new MatrixCursor(c.getColumnNames(), c.getCount());
            while (c.moveToNext()) {
                Object[] row = new Object[c.getColumnCount()];
                for (int i = 0; i < c.getColumnCount(); i++) {
                    switch (c.getType(i)) {
                        case 0:
                            row[i] = null;
                            break;
                        case 1:
                            row[i] = Long.valueOf(c.getLong(i));
                            break;
                        case 2:
                            row[i] = Double.valueOf(c.getDouble(i));
                            break;
                        case 3:
                            row[i] = c.getString(i);
                            break;
                        case 4:
                            row[i] = c.getBlob(i);
                            break;
                        default:
                            throw new IllegalStateException();
                    }
                }
                matrixCursor.addRow(row);
            }
            return matrixCursor;
        } finally {
            c.close();
        }
    }

    public static int getColumnIndex(@NonNull Cursor c, @NonNull String name) {
        int index = c.getColumnIndex(name);
        if (index >= 0) {
            return index;
        }
        return c.getColumnIndex("`" + name + "`");
    }

    public static int getColumnIndexOrThrow(@NonNull Cursor c, @NonNull String name) {
        int index = c.getColumnIndex(name);
        if (index >= 0) {
            return index;
        }
        return c.getColumnIndexOrThrow("`" + name + "`");
    }

    private CursorUtil() {
    }
}
