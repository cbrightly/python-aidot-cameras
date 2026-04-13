package com.leedarson.mqtt.libservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.leedarson.mqtt.libservice.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.util.Iterator;
import java.util.UUID;
import org.eclipse.paho.client.mqttv3.l;

/* compiled from: DatabaseMessageStore */
public class a implements b {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public SQLiteDatabase a = null;
    /* access modifiers changed from: private */
    public c b = null;
    private g c = null;

    /* compiled from: DatabaseMessageStore */
    public static class c extends SQLiteOpenHelper {
        public static ChangeQuickRedirect changeQuickRedirect;
        private g c = null;

        public c(g traceHandler, Context context) {
            super(context, "mqttAndroidService.db", (SQLiteDatabase.CursorFactory) null, 1);
            this.c = traceHandler;
        }

        public void onCreate(SQLiteDatabase database) {
            if (!PatchProxy.proxy(new Object[]{database}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_BRIGHT_GETBRIGHT_RESP, new Class[]{SQLiteDatabase.class}, Void.TYPE).isSupported) {
                g gVar = this.c;
                gVar.b("MQTTDatabaseHelper", "onCreate {" + "CREATE TABLE MqttArrivedMessageTable(messageId TEXT PRIMARY KEY, clientHandle TEXT, destinationName TEXT, payload BLOB, qos INTEGER, retained TEXT, duplicate TEXT, mtimestamp INTEGER);" + "}");
                try {
                    database.execSQL("CREATE TABLE MqttArrivedMessageTable(messageId TEXT PRIMARY KEY, clientHandle TEXT, destinationName TEXT, payload BLOB, qos INTEGER, retained TEXT, duplicate TEXT, mtimestamp INTEGER);");
                    this.c.b("MQTTDatabaseHelper", "created the table");
                } catch (SQLException e) {
                    this.c.c("MQTTDatabaseHelper", "onCreate", e);
                    throw e;
                }
            }
        }

        public void onUpgrade(SQLiteDatabase db, int i, int i2) {
            Object[] objArr = {db, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, AVIOCTRLDEFs.IOTYPE_BRIGHT_SETBRIGHT_REQ, new Class[]{SQLiteDatabase.class, cls, cls}, Void.TYPE).isSupported) {
                this.c.b("MQTTDatabaseHelper", "onUpgrade");
                try {
                    db.execSQL("DROP TABLE IF EXISTS MqttArrivedMessageTable");
                    onCreate(db);
                    this.c.b("MQTTDatabaseHelper", "onUpgrade complete");
                } catch (SQLException e) {
                    this.c.c("MQTTDatabaseHelper", "onUpgrade", e);
                    throw e;
                }
            }
        }
    }

    public a(MqttService service, Context context) {
        this.c = service;
        this.b = new c(this.c, context);
        this.c.b("DatabaseMessageStore", "DatabaseMessageStore<init> complete");
    }

    public String d(String clientHandle, String topic, l message) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{clientHandle, topic, message}, this, changeQuickRedirect2, false, 1528, new Class[]{cls, cls, l.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        this.a = this.b.getWritableDatabase();
        g gVar = this.c;
        gVar.b("DatabaseMessageStore", "storeArrived{" + clientHandle + "}, {" + message.toString() + "}");
        byte[] payload = message.c();
        int qos = message.d();
        boolean retained = message.f();
        boolean duplicate = message.e();
        ContentValues values = new ContentValues();
        String id = UUID.randomUUID().toString();
        values.put("messageId", id);
        values.put("clientHandle", clientHandle);
        values.put("destinationName", topic);
        values.put("payload", payload);
        values.put("qos", Integer.valueOf(qos));
        values.put("retained", Boolean.valueOf(retained));
        values.put("duplicate", Boolean.valueOf(duplicate));
        values.put("mtimestamp", Long.valueOf(System.currentTimeMillis()));
        try {
            this.a.insertOrThrow("MqttArrivedMessageTable", (String) null, values);
            int count = h(clientHandle);
            g gVar2 = this.c;
            gVar2.b("DatabaseMessageStore", "storeArrived: inserted message with id of {" + id + "} - Number of messages in database for this clientHandle = " + count);
            return id;
        } catch (SQLException e) {
            this.c.c("DatabaseMessageStore", "onUpgrade", e);
            throw e;
        }
    }

    private int h(String clientHandle) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{clientHandle}, this, changeQuickRedirect, false, 1529, new Class[]{String.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int count = 0;
        SQLiteDatabase sQLiteDatabase = this.a;
        Cursor c2 = sQLiteDatabase.query("MqttArrivedMessageTable", new String[]{"messageId"}, "clientHandle=?", new String[]{clientHandle}, (String) null, (String) null, (String) null);
        if (c2.moveToFirst()) {
            count = c2.getInt(0);
        }
        c2.close();
        return count;
    }

    public boolean b(String clientHandle, String id) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{clientHandle, id}, this, changeQuickRedirect, false, 1530, new Class[]{cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        this.a = this.b.getWritableDatabase();
        g gVar = this.c;
        gVar.b("DatabaseMessageStore", "discardArrived{" + clientHandle + "}, {" + id + "}");
        try {
            int rows = this.a.delete("MqttArrivedMessageTable", "messageId=? AND clientHandle=?", new String[]{id, clientHandle});
            if (rows != 1) {
                g gVar2 = this.c;
                gVar2.a("DatabaseMessageStore", "discardArrived - Error deleting message {" + id + "} from database: Rows affected = " + rows);
                return false;
            }
            int count = h(clientHandle);
            g gVar3 = this.c;
            gVar3.b("DatabaseMessageStore", "discardArrived - Message deleted successfully. - messages in db for this clientHandle " + count);
            return true;
        } catch (SQLException e) {
            this.c.c("DatabaseMessageStore", "discardArrived", e);
            throw e;
        }
    }

    /* renamed from: com.leedarson.mqtt.libservice.a$a  reason: collision with other inner class name */
    /* compiled from: DatabaseMessageStore */
    public class C0098a implements Iterator<b.a> {
        public static ChangeQuickRedirect changeQuickRedirect;
        private Cursor c;
        private boolean d;
        private final String[] f;
        final /* synthetic */ String q;
        final /* synthetic */ a x;

        C0098a(a this$0, String str) {
            a aVar = this$0;
            String str2 = str;
            this.x = aVar;
            this.q = str2;
            String[] strArr = {str2};
            this.f = strArr;
            SQLiteDatabase unused = aVar.a = this$0.b.getWritableDatabase();
            if (str2 == null) {
                this.c = this$0.a.query("MqttArrivedMessageTable", (String[]) null, (String) null, (String[]) null, (String) null, (String) null, "mtimestamp ASC");
            } else {
                this.c = this$0.a.query("MqttArrivedMessageTable", (String[]) null, "clientHandle=?", strArr, (String) null, (String) null, "mtimestamp ASC");
            }
            this.d = this.c.moveToFirst();
        }

        public /* bridge */ /* synthetic */ Object next() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_BRIGHT_GETBRIGHT_REQ, new Class[0], Object.class);
            return proxy.isSupported ? proxy.result : b();
        }

        public boolean hasNext() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1534, new Class[0], Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (!this.d) {
                this.c.close();
            }
            return this.d;
        }

        public b.a b() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1535, new Class[0], b.a.class);
            if (proxy.isSupported) {
                return (b.a) proxy.result;
            }
            Cursor cursor = this.c;
            String messageId = cursor.getString(cursor.getColumnIndex("messageId"));
            Cursor cursor2 = this.c;
            String clientHandle = cursor2.getString(cursor2.getColumnIndex("clientHandle"));
            Cursor cursor3 = this.c;
            String topic = cursor3.getString(cursor3.getColumnIndex("destinationName"));
            Cursor cursor4 = this.c;
            byte[] payload = cursor4.getBlob(cursor4.getColumnIndex("payload"));
            Cursor cursor5 = this.c;
            int qos = cursor5.getInt(cursor5.getColumnIndex("qos"));
            Cursor cursor6 = this.c;
            boolean retained = Boolean.parseBoolean(cursor6.getString(cursor6.getColumnIndex("retained")));
            Cursor cursor7 = this.c;
            boolean dup = Boolean.parseBoolean(cursor7.getString(cursor7.getColumnIndex("duplicate")));
            d message = new d(payload);
            message.k(qos);
            message.l(retained);
            message.g(dup);
            this.d = this.c.moveToNext();
            return new b(messageId, clientHandle, topic, message);
        }

        public void remove() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_CRUISEMODE_CRUISE_START, new Class[0], Void.TYPE).isSupported) {
                throw new UnsupportedOperationException();
            }
        }

        public void finalize() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_CRUISEMODE_CRUISE_STOP, new Class[0], Void.TYPE).isSupported) {
                this.c.close();
                super.finalize();
            }
        }
    }

    public Iterator<b.a> a(String clientHandle) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{clientHandle}, this, changeQuickRedirect, false, 1531, new Class[]{String.class}, Iterator.class);
        return proxy.isSupported ? (Iterator) proxy.result : new C0098a(this, clientHandle);
    }

    public void c(String clientHandle) {
        int rows;
        if (!PatchProxy.proxy(new Object[]{clientHandle}, this, changeQuickRedirect, false, 1532, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.a = this.b.getWritableDatabase();
            String[] selectionArgs = {clientHandle};
            if (clientHandle == null) {
                this.c.b("DatabaseMessageStore", "clearArrivedMessages: clearing the table");
                rows = this.a.delete("MqttArrivedMessageTable", (String) null, (String[]) null);
            } else {
                g gVar = this.c;
                gVar.b("DatabaseMessageStore", "clearArrivedMessages: clearing the table of " + clientHandle + " messages");
                rows = this.a.delete("MqttArrivedMessageTable", "clientHandle=?", selectionArgs);
            }
            g gVar2 = this.c;
            gVar2.b("DatabaseMessageStore", "clearArrivedMessages: rows affected = " + rows);
        }
    }

    /* compiled from: DatabaseMessageStore */
    public class b implements b.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        private String a;
        private String b;
        private l c;

        b(String messageId, String clientHandle, String topic, l message) {
            this.a = messageId;
            this.b = topic;
            this.c = message;
        }

        public String c() {
            return this.a;
        }

        public String d() {
            return this.b;
        }

        public l a() {
            return this.c;
        }
    }

    /* compiled from: DatabaseMessageStore */
    public class d extends l {
        public static ChangeQuickRedirect changeQuickRedirect;

        public d(byte[] payload) {
            super(payload);
        }

        public void g(boolean dup) {
            Object[] objArr = {new Byte(dup ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, AVIOCTRLDEFs.IOTYPE_BRIGHT_SETBRIGHT_RESP, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                super.g(dup);
            }
        }
    }

    public void close() {
        SQLiteDatabase sQLiteDatabase;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1533, new Class[0], Void.TYPE).isSupported && (sQLiteDatabase = this.a) != null) {
            sQLiteDatabase.close();
        }
    }
}
