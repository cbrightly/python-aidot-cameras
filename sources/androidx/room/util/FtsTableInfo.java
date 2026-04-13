package androidx.room.util;

import android.database.Cursor;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class FtsTableInfo {
    private static final String[] FTS_OPTIONS = {"tokenize=", "compress=", "content=", "languageid=", "matchinfo=", "notindexed=", "order=", "prefix=", "uncompress="};
    public final Set<String> columns;
    public final String name;
    public final Set<String> options;

    public FtsTableInfo(String name2, Set<String> columns2, Set<String> options2) {
        this.name = name2;
        this.columns = columns2;
        this.options = options2;
    }

    public FtsTableInfo(String name2, Set<String> columns2, String createSql) {
        this.name = name2;
        this.columns = columns2;
        this.options = parseOptions(createSql);
    }

    public static FtsTableInfo read(SupportSQLiteDatabase database, String tableName) {
        return new FtsTableInfo(tableName, readColumns(database, tableName), readOptions(database, tableName));
    }

    private static Set<String> readColumns(SupportSQLiteDatabase database, String tableName) {
        Cursor cursor = database.query("PRAGMA table_info(`" + tableName + "`)");
        Set<String> columns2 = new HashSet<>();
        try {
            if (cursor.getColumnCount() > 0) {
                int nameIndex = cursor.getColumnIndex("name");
                while (cursor.moveToNext()) {
                    columns2.add(cursor.getString(nameIndex));
                }
            }
            return columns2;
        } finally {
            cursor.close();
        }
    }

    /* JADX INFO: finally extract failed */
    private static Set<String> readOptions(SupportSQLiteDatabase database, String tableName) {
        String sql = "";
        Cursor cursor = database.query("SELECT * FROM sqlite_master WHERE `name` = '" + tableName + "'");
        try {
            if (cursor.moveToFirst()) {
                sql = cursor.getString(cursor.getColumnIndexOrThrow("sql"));
            }
            cursor.close();
            return parseOptions(sql);
        } catch (Throwable th) {
            cursor.close();
            throw th;
        }
    }

    @VisibleForTesting
    static Set<String> parseOptions(String createStatement) {
        if (createStatement.isEmpty()) {
            return new HashSet();
        }
        String argsString = createStatement.substring(createStatement.indexOf(40) + 1, createStatement.lastIndexOf(41));
        List<String> args = new ArrayList<>();
        ArrayDeque<Character> quoteStack = new ArrayDeque<>();
        int lastDelimiterIndex = -1;
        for (int i = 0; i < argsString.length(); i++) {
            char c = argsString.charAt(i);
            switch (c) {
                case '\"':
                case '\'':
                case '`':
                    if (!quoteStack.isEmpty()) {
                        if (quoteStack.peek().charValue() != c) {
                            break;
                        } else {
                            quoteStack.pop();
                            break;
                        }
                    } else {
                        quoteStack.push(Character.valueOf(c));
                        break;
                    }
                case ',':
                    if (!quoteStack.isEmpty()) {
                        break;
                    } else {
                        args.add(argsString.substring(lastDelimiterIndex + 1, i).trim());
                        lastDelimiterIndex = i;
                        break;
                    }
                case '[':
                    if (!quoteStack.isEmpty()) {
                        break;
                    } else {
                        quoteStack.push(Character.valueOf(c));
                        break;
                    }
                case ']':
                    if (!quoteStack.isEmpty() && quoteStack.peek().charValue() == '[') {
                        quoteStack.pop();
                        break;
                    }
            }
        }
        args.add(argsString.substring(lastDelimiterIndex + 1).trim());
        HashSet<String> options2 = new HashSet<>();
        for (String arg : args) {
            for (String validOption : FTS_OPTIONS) {
                if (arg.startsWith(validOption)) {
                    options2.add(arg);
                }
            }
        }
        return options2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FtsTableInfo that = (FtsTableInfo) o;
        String str = this.name;
        if (str == null ? that.name != null : !str.equals(that.name)) {
            return false;
        }
        Set<String> set = this.columns;
        if (set == null ? that.columns != null : !set.equals(that.columns)) {
            return false;
        }
        Set<String> set2 = this.options;
        if (set2 != null) {
            return set2.equals(that.options);
        }
        if (that.options == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String str = this.name;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        Set<String> set = this.columns;
        int result = (hashCode + (set != null ? set.hashCode() : 0)) * 31;
        Set<String> set2 = this.options;
        if (set2 != null) {
            i = set2.hashCode();
        }
        return result + i;
    }

    public String toString() {
        return "FtsTableInfo{name='" + this.name + '\'' + ", columns=" + this.columns + ", options=" + this.options + '}';
    }
}
