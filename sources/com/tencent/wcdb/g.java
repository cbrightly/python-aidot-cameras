package com.tencent.wcdb;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.database.k;
import java.text.Collator;

/* compiled from: DatabaseUtils */
public final class g {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static Collator b = null;

    public static boolean i(Object a2, Object b2) {
        return a2 == b2 || (a2 != null && a2.equals(b2));
    }

    public static int f(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof byte[]) {
            return 4;
        }
        if ((obj instanceof Float) || (obj instanceof Double)) {
            return 2;
        }
        if ((obj instanceof Long) || (obj instanceof Integer) || (obj instanceof Short) || (obj instanceof Byte)) {
            return 1;
        }
        return 3;
    }

    public static void a(StringBuilder sb, String sqlString) {
        sb.append('\'');
        if (sqlString.indexOf(39) != -1) {
            int length = sqlString.length();
            for (int i = 0; i < length; i++) {
                char c = sqlString.charAt(i);
                if (c == '\'') {
                    sb.append('\'');
                }
                sb.append(c);
            }
        } else {
            sb.append(sqlString);
        }
        sb.append('\'');
    }

    public static String j(String value) {
        StringBuilder escaper = new StringBuilder();
        a(escaper, value);
        return escaper.toString();
    }

    public static int b(int cursorPosition, int cursorWindowCapacity) {
        return Math.max(cursorPosition - (cursorWindowCapacity / 3), 0);
    }

    public static long g(SQLiteDatabase db, String query, String[] selectionArgs) {
        k prog = db.j(query);
        try {
            return h(prog, selectionArgs);
        } finally {
            prog.close();
        }
    }

    public static long h(k prog, String[] selectionArgs) {
        prog.j(selectionArgs);
        return prog.simpleQueryForLong();
    }

    private static int c(String sql) {
        int result = 0;
        for (int i = 0; i < 3; i++) {
            int c = sql.charAt(i);
            if (c >= 97 && c <= 122) {
                c = (c - 97) + 65;
            } else if (c >= 128) {
                return 0;
            }
            result |= (c & NeedPermissionEvent.PER_IPC_SPEAK_PERM) << (i * 8);
        }
        return result;
    }

    public static int e(String sql) {
        String sql2 = sql.trim();
        if (sql2.length() < 3) {
            return 99;
        }
        switch (c(sql2)) {
            case 4279873:
            case 5522756:
                return 9;
            case 4280912:
                return 7;
            case 4476485:
            case 5066563:
                return 5;
            case 4477013:
            case 4998468:
            case 5260626:
            case 5459529:
                return 2;
            case 4543043:
            case 5198404:
            case 5524545:
                return 8;
            case 4670786:
                return 4;
            case 4998483:
                return 1;
            case 5001042:
                return 6;
            case 5526593:
                return 3;
            default:
                return 99;
        }
    }

    public static int d(String[] columnNames) {
        int length = columnNames.length;
        for (int i = 0; i < length; i++) {
            if (columnNames[i].equals("_id")) {
                return i;
            }
        }
        return -1;
    }
}
