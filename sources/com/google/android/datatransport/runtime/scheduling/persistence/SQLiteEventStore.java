package com.google.android.datatransport.runtime.scheduling.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.SystemClock;
import android.util.Base64;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.android.datatransport.Encoding;
import com.google.android.datatransport.Priority;
import com.google.android.datatransport.runtime.EncodedPayload;
import com.google.android.datatransport.runtime.EventInternal;
import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.firebase.transport.ClientMetrics;
import com.google.android.datatransport.runtime.firebase.transport.GlobalMetrics;
import com.google.android.datatransport.runtime.firebase.transport.LogEventDropped;
import com.google.android.datatransport.runtime.firebase.transport.LogSourceMetrics;
import com.google.android.datatransport.runtime.firebase.transport.StorageMetrics;
import com.google.android.datatransport.runtime.firebase.transport.TimeWindow;
import com.google.android.datatransport.runtime.logging.Logging;
import com.google.android.datatransport.runtime.synchronization.SynchronizationException;
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;
import com.google.android.datatransport.runtime.time.Clock;
import com.google.android.datatransport.runtime.time.Monotonic;
import com.google.android.datatransport.runtime.time.WallTime;
import com.google.android.datatransport.runtime.util.PriorityMapping;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.inject.a;

@WorkerThread
public class SQLiteEventStore implements EventStore, SynchronizationGuard, ClientHealthMetricsStore {
    private static final int LOCK_RETRY_BACK_OFF_MILLIS = 50;
    private static final String LOG_TAG = "SQLiteEventStore";
    static final int MAX_RETRIES = 16;
    private static final Encoding PROTOBUF_ENCODING = Encoding.of("proto");
    private final EventStoreConfig config;
    private final Clock monotonicClock;
    private final a<String> packageName;
    private final SchemaManager schemaManager;
    private final Clock wallClock;

    public interface Function<T, U> {
        U apply(T t);
    }

    public interface Producer<T> {
        T produce();
    }

    public /* synthetic */ Object a(Cursor cursor) {
        lambda$cleanUp$11(cursor);
        return null;
    }

    public /* synthetic */ Object m(List list, TransportContext transportContext, Cursor cursor) {
        lambda$loadEvents$14(list, transportContext, cursor);
        return null;
    }

    public /* synthetic */ Object o(Cursor cursor) {
        lambda$recordFailure$3(cursor);
        return null;
    }

    public /* synthetic */ Object r(String str, String str2, SQLiteDatabase sQLiteDatabase) {
        lambda$recordFailure$4(str, str2, sQLiteDatabase);
        return null;
    }

    public /* synthetic */ Object s(SQLiteDatabase sQLiteDatabase) {
        lambda$resetClientMetrics$23(sQLiteDatabase);
        return null;
    }

    SQLiteEventStore(@WallTime Clock wallClock2, @Monotonic Clock clock, EventStoreConfig config2, SchemaManager schemaManager2, a<String> packageName2) {
        this.schemaManager = schemaManager2;
        this.wallClock = wallClock2;
        this.monotonicClock = clock;
        this.config = config2;
        this.packageName = packageName2;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public SQLiteDatabase getDb() {
        SchemaManager schemaManager2 = this.schemaManager;
        Objects.requireNonNull(schemaManager2);
        return (SQLiteDatabase) retryIfDbLocked(new g0(schemaManager2), a.a);
    }

    static /* synthetic */ SQLiteDatabase lambda$getDb$0(Throwable ex) {
        throw new SynchronizationException("Timed out while trying to open db.", ex);
    }

    @Nullable
    public PersistedEvent persist(TransportContext transportContext, EventInternal event) {
        Logging.d(LOG_TAG, "Storing event with priority=%s, name=%s for destination %s", transportContext.getPriority(), event.getTransportName(), transportContext.getBackendName());
        long newRowId = ((Long) inTransaction(new t(this, event, transportContext))).longValue();
        if (newRowId < 1) {
            return null;
        }
        return PersistedEvent.create(newRowId, transportContext, event);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$persist$1 */
    public /* synthetic */ Long n(EventInternal event, TransportContext transportContext, SQLiteDatabase db) {
        long newEventId;
        SQLiteDatabase sQLiteDatabase = db;
        if (isStorageAtLimit()) {
            recordLogEventDropped(1, LogEventDropped.Reason.CACHE_FULL, event.getTransportName());
            return -1L;
        }
        long contextId = ensureTransportContext(sQLiteDatabase, transportContext);
        int maxBlobSizePerRow = this.config.getMaxBlobByteSizePerRow();
        byte[] payloadBytes = event.getEncodedPayload().getBytes();
        boolean inline = payloadBytes.length <= maxBlobSizePerRow;
        ContentValues values = new ContentValues();
        values.put("context_id", Long.valueOf(contextId));
        values.put("transport_name", event.getTransportName());
        values.put("timestamp_ms", Long.valueOf(event.getEventMillis()));
        values.put("uptime_ms", Long.valueOf(event.getUptimeMillis()));
        values.put("payload_encoding", event.getEncodedPayload().getEncoding().getName());
        values.put("code", event.getCode());
        values.put("num_attempts", 0);
        values.put("inline", Boolean.valueOf(inline));
        values.put("payload", inline ? payloadBytes : new byte[0]);
        long newEventId2 = sQLiteDatabase.insert("events", (String) null, values);
        if (!inline) {
            newEventId = newEventId2;
            int numChunks = (int) Math.ceil(((double) payloadBytes.length) / ((double) maxBlobSizePerRow));
            for (int chunk = 1; chunk <= numChunks; chunk++) {
                byte[] chunkBytes = Arrays.copyOfRange(payloadBytes, (chunk - 1) * maxBlobSizePerRow, Math.min(chunk * maxBlobSizePerRow, payloadBytes.length));
                ContentValues payloadValues = new ContentValues();
                payloadValues.put("event_id", Long.valueOf(newEventId));
                payloadValues.put("sequence_num", Integer.valueOf(chunk));
                payloadValues.put("bytes", chunkBytes);
                sQLiteDatabase.insert("event_payloads", (String) null, payloadValues);
            }
        } else {
            newEventId = newEventId2;
        }
        for (Map.Entry<String, String> entry : event.getMetadata().entrySet()) {
            ContentValues metadata = new ContentValues();
            metadata.put("event_id", Long.valueOf(newEventId));
            metadata.put("name", entry.getKey());
            metadata.put("value", entry.getValue());
            sQLiteDatabase.insert("event_metadata", (String) null, metadata);
        }
        return Long.valueOf(newEventId);
    }

    private long ensureTransportContext(SQLiteDatabase db, TransportContext transportContext) {
        Long existingId = getTransportContextId(db, transportContext);
        if (existingId != null) {
            return existingId.longValue();
        }
        ContentValues record = new ContentValues();
        record.put("backend_name", transportContext.getBackendName());
        record.put(Progress.PRIORITY, Integer.valueOf(PriorityMapping.toInt(transportContext.getPriority())));
        record.put("next_request_ms", 0);
        if (transportContext.getExtras() != null) {
            record.put("extras", Base64.encodeToString(transportContext.getExtras(), 0));
        }
        return db.insert("transport_contexts", (String) null, record);
    }

    @Nullable
    private Long getTransportContextId(SQLiteDatabase db, TransportContext transportContext) {
        StringBuilder selection = new StringBuilder("backend_name = ? and priority = ?");
        ArrayList<String> selectionArgs = new ArrayList<>(Arrays.asList(new String[]{transportContext.getBackendName(), String.valueOf(PriorityMapping.toInt(transportContext.getPriority()))}));
        if (transportContext.getExtras() != null) {
            selection.append(" and extras = ?");
            selectionArgs.add(Base64.encodeToString(transportContext.getExtras(), 0));
        } else {
            selection.append(" and extras is null");
        }
        return (Long) tryWithCursor(db.query("transport_contexts", new String[]{"_id"}, selection.toString(), (String[]) selectionArgs.toArray(new String[0]), (String) null, (String) null, (String) null), r.a);
    }

    static /* synthetic */ Long lambda$getTransportContextId$2(Cursor cursor) {
        if (!cursor.moveToNext()) {
            return null;
        }
        return Long.valueOf(cursor.getLong(0));
    }

    public void recordFailure(Iterable<PersistedEvent> events) {
        if (events.iterator().hasNext()) {
            inTransaction(new s(this, "UPDATE events SET num_attempts = num_attempts + 1 WHERE _id in " + toIdList(events), "SELECT COUNT(*), transport_name FROM events WHERE num_attempts >= 16 GROUP BY transport_name"));
        }
    }

    private /* synthetic */ Object lambda$recordFailure$4(String incrementAttemptNumQuery, String countMaxAttemptsEventsQuery, SQLiteDatabase db) {
        db.compileStatement(incrementAttemptNumQuery).execute();
        tryWithCursor(db.rawQuery(countMaxAttemptsEventsQuery, (String[]) null), new w(this));
        db.compileStatement("DELETE FROM events WHERE num_attempts >= 16").execute();
        return null;
    }

    private /* synthetic */ Object lambda$recordFailure$3(Cursor cursor) {
        while (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            recordLogEventDropped((long) count, LogEventDropped.Reason.MAX_RETRIES_REACHED, cursor.getString(1));
        }
        return null;
    }

    public void recordSuccess(Iterable<PersistedEvent> events) {
        if (events.iterator().hasNext()) {
            getDb().compileStatement("DELETE FROM events WHERE _id in " + toIdList(events)).execute();
        }
    }

    private static String toIdList(Iterable<PersistedEvent> events) {
        StringBuilder idList = new StringBuilder("(");
        Iterator<PersistedEvent> iterator = events.iterator();
        while (iterator.hasNext()) {
            idList.append(iterator.next().getId());
            if (iterator.hasNext()) {
                idList.append(StringUtil.COMMA);
            }
        }
        idList.append(')');
        return idList.toString();
    }

    public long getNextCallTime(TransportContext transportContext) {
        return ((Long) tryWithCursor(getDb().rawQuery("SELECT next_request_ms FROM transport_contexts WHERE backend_name = ? and priority = ?", new String[]{transportContext.getBackendName(), String.valueOf(PriorityMapping.toInt(transportContext.getPriority()))}), n.a)).longValue();
    }

    static /* synthetic */ Long lambda$getNextCallTime$5(Cursor cursor) {
        if (cursor.moveToNext()) {
            return Long.valueOf(cursor.getLong(0));
        }
        return 0L;
    }

    public boolean hasPendingEventsFor(TransportContext transportContext) {
        return ((Boolean) inTransaction(new l(this, transportContext))).booleanValue();
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$hasPendingEventsFor$6 */
    public /* synthetic */ Boolean g(TransportContext transportContext, SQLiteDatabase db) {
        Long contextId = getTransportContextId(db, transportContext);
        if (contextId == null) {
            return false;
        }
        return (Boolean) tryWithCursor(getDb().rawQuery("SELECT 1 FROM events WHERE context_id = ? LIMIT 1", new String[]{contextId.toString()}), f0.a);
    }

    public void recordNextCallTime(TransportContext transportContext, long timestampMs) {
        inTransaction(new f(timestampMs, transportContext));
    }

    static /* synthetic */ Object lambda$recordNextCallTime$7(long timestampMs, TransportContext transportContext, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("next_request_ms", Long.valueOf(timestampMs));
        if (db.update("transport_contexts", values, "backend_name = ? and priority = ?", new String[]{transportContext.getBackendName(), String.valueOf(PriorityMapping.toInt(transportContext.getPriority()))}) < 1) {
            values.put("backend_name", transportContext.getBackendName());
            values.put(Progress.PRIORITY, Integer.valueOf(PriorityMapping.toInt(transportContext.getPriority())));
            db.insert("transport_contexts", (String) null, values);
        }
        return null;
    }

    public Iterable<PersistedEvent> loadBatch(TransportContext transportContext) {
        return (Iterable) inTransaction(new d(this, transportContext));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$loadBatch$8 */
    public /* synthetic */ List i(TransportContext transportContext, SQLiteDatabase db) {
        List<PersistedEvent> events = loadEvents(db, transportContext, this.config.getLoadBatchSize());
        for (Priority p : Priority.values()) {
            if (p != transportContext.getPriority()) {
                int space = this.config.getLoadBatchSize() - events.size();
                if (space <= 0) {
                    break;
                }
                events.addAll(loadEvents(db, transportContext.withPriority(p), space));
            }
        }
        return join(events, loadMetadata(db, events));
    }

    public Iterable<TransportContext> loadActiveContexts() {
        return (Iterable) inTransaction(y.a);
    }

    static /* synthetic */ List lambda$loadActiveContexts$10(SQLiteDatabase db) {
        return (List) tryWithCursor(db.rawQuery("SELECT distinct t._id, t.backend_name, t.priority, t.extras FROM transport_contexts AS t, events AS e WHERE e.context_id = t._id", new String[0]), i.a);
    }

    static /* synthetic */ List lambda$loadActiveContexts$9(Cursor cursor) {
        List<TransportContext> results = new ArrayList<>();
        while (cursor.moveToNext()) {
            results.add(TransportContext.builder().setBackendName(cursor.getString(1)).setPriority(PriorityMapping.valueOf(cursor.getInt(2))).setExtras(maybeBase64Decode(cursor.getString(3))).build());
        }
        return results;
    }

    public int cleanUp() {
        return ((Integer) inTransaction(new x(this, this.wallClock.getTime() - this.config.getEventCleanUpAge()))).intValue();
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$cleanUp$12 */
    public /* synthetic */ Integer c(long oneWeekAgo, SQLiteDatabase db) {
        String[] selectionArgs = {String.valueOf(oneWeekAgo)};
        tryWithCursor(db.rawQuery("SELECT COUNT(*), transport_name FROM events WHERE timestamp_ms < ? GROUP BY transport_name", selectionArgs), new k(this));
        return Integer.valueOf(db.delete("events", "timestamp_ms < ?", selectionArgs));
    }

    private /* synthetic */ Object lambda$cleanUp$11(Cursor cursor) {
        while (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            recordLogEventDropped((long) count, LogEventDropped.Reason.MESSAGE_TOO_OLD, cursor.getString(1));
        }
        return null;
    }

    public void close() {
        this.schemaManager.close();
    }

    @RestrictTo({RestrictTo.Scope.TESTS})
    public void clearDb() {
        inTransaction(h.a);
    }

    static /* synthetic */ Object lambda$clearDb$13(SQLiteDatabase db) {
        db.delete("events", (String) null, new String[0]);
        db.delete("transport_contexts", (String) null, new String[0]);
        return null;
    }

    private static byte[] maybeBase64Decode(@Nullable String value) {
        if (value == null) {
            return null;
        }
        return Base64.decode(value, 0);
    }

    private List<PersistedEvent> loadEvents(SQLiteDatabase db, TransportContext transportContext, int limit) {
        List<PersistedEvent> events = new ArrayList<>();
        Long contextId = getTransportContextId(db, transportContext);
        if (contextId == null) {
            return events;
        }
        SQLiteDatabase sQLiteDatabase = db;
        tryWithCursor(sQLiteDatabase.query("events", new String[]{"_id", "transport_name", "timestamp_ms", "uptime_ms", "payload_encoding", "payload", "code", "inline"}, "context_id = ?", new String[]{contextId.toString()}, (String) null, (String) null, (String) null, String.valueOf(limit)), new p(this, events, transportContext));
        return events;
    }

    private /* synthetic */ Object lambda$loadEvents$14(List events, TransportContext transportContext, Cursor cursor) {
        while (cursor.moveToNext()) {
            boolean inline = false;
            long id = cursor.getLong(0);
            if (cursor.getInt(7) != 0) {
                inline = true;
            }
            EventInternal.Builder event = EventInternal.builder().setTransportName(cursor.getString(1)).setEventMillis(cursor.getLong(2)).setUptimeMillis(cursor.getLong(3));
            if (inline) {
                event.setEncodedPayload(new EncodedPayload(toEncoding(cursor.getString(4)), cursor.getBlob(5)));
            } else {
                event.setEncodedPayload(new EncodedPayload(toEncoding(cursor.getString(4)), readPayload(id)));
            }
            if (!cursor.isNull(6)) {
                event.setCode(Integer.valueOf(cursor.getInt(6)));
            }
            events.add(PersistedEvent.create(id, transportContext, event.build()));
        }
        return null;
    }

    private byte[] readPayload(long eventId) {
        return (byte[]) tryWithCursor(getDb().query("event_payloads", new String[]{"bytes"}, "event_id = ?", new String[]{String.valueOf(eventId)}, (String) null, (String) null, "sequence_num"), g.a);
    }

    static /* synthetic */ byte[] lambda$readPayload$15(Cursor cursor) {
        List<byte[]> chunks = new ArrayList<>();
        int totalLength = 0;
        while (cursor.moveToNext()) {
            byte[] chunk = cursor.getBlob(0);
            chunks.add(chunk);
            totalLength += chunk.length;
        }
        byte[] payloadBytes = new byte[totalLength];
        int offset = 0;
        for (int i = 0; i < chunks.size(); i++) {
            byte[] chunk2 = chunks.get(i);
            System.arraycopy(chunk2, 0, payloadBytes, offset, chunk2.length);
            offset += chunk2.length;
        }
        return payloadBytes;
    }

    private static Encoding toEncoding(@Nullable String value) {
        if (value == null) {
            return PROTOBUF_ENCODING;
        }
        return Encoding.of(value);
    }

    private Map<Long, Set<Metadata>> loadMetadata(SQLiteDatabase db, List<PersistedEvent> events) {
        Map<Long, Set<Metadata>> metadataIndex = new HashMap<>();
        StringBuilder whereClause = new StringBuilder("event_id IN (");
        for (int i = 0; i < events.size(); i++) {
            whereClause.append(events.get(i).getId());
            if (i < events.size() - 1) {
                whereClause.append(StringUtil.COMMA);
            }
        }
        whereClause.append(')');
        tryWithCursor(db.query("event_metadata", new String[]{"event_id", "name", "value"}, whereClause.toString(), (String[]) null, (String) null, (String) null, (String) null), new u(metadataIndex));
        return metadataIndex;
    }

    static /* synthetic */ Object lambda$loadMetadata$16(Map metadataIndex, Cursor cursor) {
        while (cursor.moveToNext()) {
            long eventId = cursor.getLong(0);
            Set<Metadata> currentSet = (Set) metadataIndex.get(Long.valueOf(eventId));
            if (currentSet == null) {
                currentSet = new HashSet<>();
                metadataIndex.put(Long.valueOf(eventId), currentSet);
            }
            currentSet.add(new Metadata(cursor.getString(1), cursor.getString(2)));
        }
        return null;
    }

    private List<PersistedEvent> join(List<PersistedEvent> events, Map<Long, Set<Metadata>> metadataIndex) {
        ListIterator<PersistedEvent> iterator = events.listIterator();
        while (iterator.hasNext()) {
            PersistedEvent current = iterator.next();
            if (metadataIndex.containsKey(Long.valueOf(current.getId()))) {
                EventInternal.Builder newEvent = current.getEvent().toBuilder();
                for (Metadata metadata : metadataIndex.get(Long.valueOf(current.getId()))) {
                    newEvent.addMetadata(metadata.key, metadata.value);
                }
                iterator.set(PersistedEvent.create(current.getId(), current.getTransportContext(), newEvent.build()));
            }
        }
        return events;
    }

    private <T> T retryIfDbLocked(Producer<T> retriable, Function<Throwable, T> failureHandler) {
        long startTime = this.monotonicClock.getTime();
        while (true) {
            try {
                return retriable.produce();
            } catch (SQLiteDatabaseLockedException ex) {
                if (this.monotonicClock.getTime() >= ((long) this.config.getCriticalSectionEnterTimeoutMs()) + startTime) {
                    return failureHandler.apply(ex);
                }
                SystemClock.sleep(50);
            }
        }
    }

    public void recordLogEventDropped(long eventsDroppedCount, LogEventDropped.Reason reason, String logSource) {
        inTransaction(new j(logSource, reason, eventsDroppedCount));
    }

    static /* synthetic */ Object lambda$recordLogEventDropped$18(String logSource, LogEventDropped.Reason reason, long eventsDroppedCount, SQLiteDatabase db) {
        if (!((Boolean) tryWithCursor(db.rawQuery("SELECT 1 FROM log_event_dropped WHERE log_source = ? AND reason = ?", new String[]{logSource, Integer.toString(reason.getNumber())}), z.a)).booleanValue()) {
            ContentValues metrics = new ContentValues();
            metrics.put("log_source", logSource);
            metrics.put("reason", Integer.valueOf(reason.getNumber()));
            metrics.put("events_dropped_count", Long.valueOf(eventsDroppedCount));
            db.insert("log_event_dropped", (String) null, metrics);
        } else {
            db.execSQL("UPDATE log_event_dropped SET events_dropped_count = events_dropped_count + " + eventsDroppedCount + " WHERE log_source = ? AND reason = ?", new String[]{logSource, Integer.toString(reason.getNumber())});
        }
        return null;
    }

    static /* synthetic */ Boolean lambda$recordLogEventDropped$17(Cursor cursor) {
        return Boolean.valueOf(cursor.getCount() > 0);
    }

    private LogEventDropped.Reason convertToReason(int number) {
        LogEventDropped.Reason reason = LogEventDropped.Reason.REASON_UNKNOWN;
        if (number == reason.getNumber()) {
            return reason;
        }
        LogEventDropped.Reason reason2 = LogEventDropped.Reason.MESSAGE_TOO_OLD;
        if (number == reason2.getNumber()) {
            return reason2;
        }
        LogEventDropped.Reason reason3 = LogEventDropped.Reason.CACHE_FULL;
        if (number == reason3.getNumber()) {
            return reason3;
        }
        LogEventDropped.Reason reason4 = LogEventDropped.Reason.PAYLOAD_TOO_BIG;
        if (number == reason4.getNumber()) {
            return reason4;
        }
        LogEventDropped.Reason reason5 = LogEventDropped.Reason.MAX_RETRIES_REACHED;
        if (number == reason5.getNumber()) {
            return reason5;
        }
        LogEventDropped.Reason reason6 = LogEventDropped.Reason.INVALID_PAYLOD;
        if (number == reason6.getNumber()) {
            return reason6;
        }
        LogEventDropped.Reason reason7 = LogEventDropped.Reason.SERVER_ERROR;
        if (number == reason7.getNumber()) {
            return reason7;
        }
        Logging.d(LOG_TAG, "%n is not valid. No matched LogEventDropped-Reason found. Treated it as REASON_UNKNOWN", (Object) Integer.valueOf(number));
        return reason;
    }

    public ClientMetrics loadClientMetrics() {
        return (ClientMetrics) inTransaction(new o(this, "SELECT log_source, reason, events_dropped_count FROM log_event_dropped", new HashMap<>(), ClientMetrics.newBuilder()));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$loadClientMetrics$20 */
    public /* synthetic */ ClientMetrics l(String query, Map metricsMap, ClientMetrics.Builder clientMetricsBuilder, SQLiteDatabase db) {
        return (ClientMetrics) tryWithCursor(db.rawQuery(query, new String[0]), new v(this, metricsMap, clientMetricsBuilder));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$loadClientMetrics$19 */
    public /* synthetic */ ClientMetrics j(Map metricsMap, ClientMetrics.Builder clientMetricsBuilder, Cursor cursor) {
        while (cursor.moveToNext()) {
            String logSource = cursor.getString(0);
            LogEventDropped.Reason reason = convertToReason(cursor.getInt(1));
            long eventsDroppedCount = cursor.getLong(2);
            if (!metricsMap.containsKey(logSource)) {
                metricsMap.put(logSource, new ArrayList());
            }
            ((List) metricsMap.get(logSource)).add(LogEventDropped.newBuilder().setReason(reason).setEventsDroppedCount(eventsDroppedCount).build());
        }
        populateLogSourcesMetrics(clientMetricsBuilder, metricsMap);
        clientMetricsBuilder.setWindow(getTimeWindow());
        clientMetricsBuilder.setGlobalMetrics(getGlobalMetrics());
        clientMetricsBuilder.setAppNamespace(this.packageName.get());
        return clientMetricsBuilder.build();
    }

    private void populateLogSourcesMetrics(ClientMetrics.Builder clientMetricsBuilder, Map<String, List<LogEventDropped>> metricsMap) {
        for (Map.Entry<String, List<LogEventDropped>> entry : metricsMap.entrySet()) {
            clientMetricsBuilder.addLogSourceMetrics(LogSourceMetrics.newBuilder().setLogSource(entry.getKey()).setLogEventDroppedList(entry.getValue()).build());
        }
    }

    private TimeWindow getTimeWindow() {
        return (TimeWindow) inTransaction(new m(this.wallClock.getTime()));
    }

    static /* synthetic */ TimeWindow lambda$getTimeWindow$22(long currentTime, SQLiteDatabase db) {
        return (TimeWindow) tryWithCursor(db.rawQuery("SELECT last_metrics_upload_ms FROM global_log_event_state LIMIT 1", new String[0]), new c(currentTime));
    }

    private GlobalMetrics getGlobalMetrics() {
        return GlobalMetrics.newBuilder().setStorageMetrics(StorageMetrics.newBuilder().setCurrentCacheSizeBytes(getByteSize()).setMaxCacheSizeBytes(EventStoreConfig.DEFAULT.getMaxStorageSizeInBytes()).build()).build();
    }

    public void resetClientMetrics() {
        inTransaction(new q(this));
    }

    private /* synthetic */ Object lambda$resetClientMetrics$23(SQLiteDatabase db) {
        db.compileStatement("DELETE FROM log_event_dropped").execute();
        db.compileStatement("UPDATE global_log_event_state SET last_metrics_upload_ms=" + this.wallClock.getTime()).execute();
        return null;
    }

    private void ensureBeginTransaction(SQLiteDatabase db) {
        retryIfDbLocked(new e(db), b.a);
    }

    static /* synthetic */ Object lambda$ensureBeginTransaction$25(Throwable ex) {
        throw new SynchronizationException("Timed out while trying to acquire the lock.", ex);
    }

    public <T> T runCriticalSection(SynchronizationGuard.CriticalSection<T> criticalSection) {
        SQLiteDatabase db = getDb();
        ensureBeginTransaction(db);
        try {
            T result = criticalSection.execute();
            db.setTransactionSuccessful();
            return result;
        } finally {
            db.endTransaction();
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public <T> T inTransaction(Function<SQLiteDatabase, T> function) {
        SQLiteDatabase db = getDb();
        db.beginTransaction();
        try {
            T result = function.apply(db);
            db.setTransactionSuccessful();
            return result;
        } finally {
            db.endTransaction();
        }
    }

    public static class Metadata {
        final String key;
        final String value;

        private Metadata(String key2, String value2) {
            this.key = key2;
            this.value = value2;
        }
    }

    private boolean isStorageAtLimit() {
        return getPageCount() * getPageSize() >= this.config.getMaxStorageSizeInBytes();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public long getByteSize() {
        return getPageCount() * getPageSize();
    }

    private long getPageSize() {
        return getDb().compileStatement("PRAGMA page_size").simpleQueryForLong();
    }

    private long getPageCount() {
        return getDb().compileStatement("PRAGMA page_count").simpleQueryForLong();
    }

    @VisibleForTesting
    static <T> T tryWithCursor(Cursor c, Function<Cursor, T> function) {
        try {
            return function.apply(c);
        } finally {
            c.close();
        }
    }
}
