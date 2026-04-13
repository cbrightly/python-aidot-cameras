package androidx.room.util;

import android.database.Cursor;
import androidx.annotation.RestrictTo;
import androidx.sqlite.db.SupportSQLiteDatabase;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class ViewInfo {
    public final String name;
    public final String sql;

    public ViewInfo(String name2, String sql2) {
        this.name = name2;
        this.sql = sql2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ViewInfo viewInfo = (ViewInfo) o;
        String str = this.name;
        if (str == null ? viewInfo.name == null : str.equals(viewInfo.name)) {
            String str2 = this.sql;
            if (str2 != null) {
                if (str2.equals(viewInfo.sql)) {
                    return true;
                }
            } else if (viewInfo.sql == null) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String str = this.name;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.sql;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "ViewInfo{name='" + this.name + '\'' + ", sql='" + this.sql + '\'' + '}';
    }

    public static ViewInfo read(SupportSQLiteDatabase database, String viewName) {
        Cursor cursor = database.query("SELECT name, sql FROM sqlite_master WHERE type = 'view' AND name = '" + viewName + "'");
        try {
            if (cursor.moveToFirst()) {
                return new ViewInfo(cursor.getString(0), cursor.getString(1));
            }
            ViewInfo viewInfo = new ViewInfo(viewName, (String) null);
            cursor.close();
            return viewInfo;
        } finally {
            cursor.close();
        }
    }
}
