package com.google.android.datatransport.runtime;

import com.google.android.datatransport.runtime.firebase.transport.ClientMetrics;
import com.google.android.datatransport.runtime.firebase.transport.GlobalMetrics;
import com.google.android.datatransport.runtime.firebase.transport.LogEventDropped;
import com.google.android.datatransport.runtime.firebase.transport.LogSourceMetrics;
import com.google.android.datatransport.runtime.firebase.transport.StorageMetrics;
import com.google.android.datatransport.runtime.firebase.transport.TimeWindow;
import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import com.google.firebase.encoders.config.Configurator;
import com.google.firebase.encoders.config.EncoderConfig;
import com.google.firebase.encoders.proto.AtProtobuf;

public final class AutoProtoEncoderDoNotUseEncoder implements Configurator {
    public static final int CODEGEN_VERSION = 2;
    public static final Configurator CONFIG = new AutoProtoEncoderDoNotUseEncoder();

    private AutoProtoEncoderDoNotUseEncoder() {
    }

    public void configure(EncoderConfig<?> cfg) {
        cfg.registerEncoder((Class<U>) ProtoEncoderDoNotUse.class, (ObjectEncoder<? super U>) ProtoEncoderDoNotUseEncoder.INSTANCE);
        cfg.registerEncoder((Class<U>) ClientMetrics.class, (ObjectEncoder<? super U>) ClientMetricsEncoder.INSTANCE);
        cfg.registerEncoder((Class<U>) TimeWindow.class, (ObjectEncoder<? super U>) TimeWindowEncoder.INSTANCE);
        cfg.registerEncoder((Class<U>) LogSourceMetrics.class, (ObjectEncoder<? super U>) LogSourceMetricsEncoder.INSTANCE);
        cfg.registerEncoder((Class<U>) LogEventDropped.class, (ObjectEncoder<? super U>) LogEventDroppedEncoder.INSTANCE);
        cfg.registerEncoder((Class<U>) GlobalMetrics.class, (ObjectEncoder<? super U>) GlobalMetricsEncoder.INSTANCE);
        cfg.registerEncoder((Class<U>) StorageMetrics.class, (ObjectEncoder<? super U>) StorageMetricsEncoder.INSTANCE);
    }

    public static final class ProtoEncoderDoNotUseEncoder implements ObjectEncoder<ProtoEncoderDoNotUse> {
        private static final FieldDescriptor CLIENTMETRICS_DESCRIPTOR = FieldDescriptor.of("clientMetrics");
        static final ProtoEncoderDoNotUseEncoder INSTANCE = new ProtoEncoderDoNotUseEncoder();

        private ProtoEncoderDoNotUseEncoder() {
        }

        public void encode(ProtoEncoderDoNotUse value, ObjectEncoderContext ctx) {
            ctx.add(CLIENTMETRICS_DESCRIPTOR, (Object) value.getClientMetrics());
        }
    }

    public static final class ClientMetricsEncoder implements ObjectEncoder<ClientMetrics> {
        private static final FieldDescriptor APPNAMESPACE_DESCRIPTOR = FieldDescriptor.builder("appNamespace").withProperty(AtProtobuf.builder().tag(4).build()).build();
        private static final FieldDescriptor GLOBALMETRICS_DESCRIPTOR = FieldDescriptor.builder("globalMetrics").withProperty(AtProtobuf.builder().tag(3).build()).build();
        static final ClientMetricsEncoder INSTANCE = new ClientMetricsEncoder();
        private static final FieldDescriptor LOGSOURCEMETRICS_DESCRIPTOR = FieldDescriptor.builder("logSourceMetrics").withProperty(AtProtobuf.builder().tag(2).build()).build();
        private static final FieldDescriptor WINDOW_DESCRIPTOR = FieldDescriptor.builder("window").withProperty(AtProtobuf.builder().tag(1).build()).build();

        private ClientMetricsEncoder() {
        }

        public void encode(ClientMetrics value, ObjectEncoderContext ctx) {
            ctx.add(WINDOW_DESCRIPTOR, (Object) value.getWindowInternal());
            ctx.add(LOGSOURCEMETRICS_DESCRIPTOR, (Object) value.getLogSourceMetricsList());
            ctx.add(GLOBALMETRICS_DESCRIPTOR, (Object) value.getGlobalMetricsInternal());
            ctx.add(APPNAMESPACE_DESCRIPTOR, (Object) value.getAppNamespace());
        }
    }

    public static final class TimeWindowEncoder implements ObjectEncoder<TimeWindow> {
        private static final FieldDescriptor ENDMS_DESCRIPTOR = FieldDescriptor.builder("endMs").withProperty(AtProtobuf.builder().tag(2).build()).build();
        static final TimeWindowEncoder INSTANCE = new TimeWindowEncoder();
        private static final FieldDescriptor STARTMS_DESCRIPTOR = FieldDescriptor.builder("startMs").withProperty(AtProtobuf.builder().tag(1).build()).build();

        private TimeWindowEncoder() {
        }

        public void encode(TimeWindow value, ObjectEncoderContext ctx) {
            ctx.add(STARTMS_DESCRIPTOR, value.getStartMs());
            ctx.add(ENDMS_DESCRIPTOR, value.getEndMs());
        }
    }

    public static final class LogSourceMetricsEncoder implements ObjectEncoder<LogSourceMetrics> {
        static final LogSourceMetricsEncoder INSTANCE = new LogSourceMetricsEncoder();
        private static final FieldDescriptor LOGEVENTDROPPED_DESCRIPTOR = FieldDescriptor.builder("logEventDropped").withProperty(AtProtobuf.builder().tag(2).build()).build();
        private static final FieldDescriptor LOGSOURCE_DESCRIPTOR = FieldDescriptor.builder("logSource").withProperty(AtProtobuf.builder().tag(1).build()).build();

        private LogSourceMetricsEncoder() {
        }

        public void encode(LogSourceMetrics value, ObjectEncoderContext ctx) {
            ctx.add(LOGSOURCE_DESCRIPTOR, (Object) value.getLogSource());
            ctx.add(LOGEVENTDROPPED_DESCRIPTOR, (Object) value.getLogEventDroppedList());
        }
    }

    public static final class LogEventDroppedEncoder implements ObjectEncoder<LogEventDropped> {
        private static final FieldDescriptor EVENTSDROPPEDCOUNT_DESCRIPTOR = FieldDescriptor.builder("eventsDroppedCount").withProperty(AtProtobuf.builder().tag(1).build()).build();
        static final LogEventDroppedEncoder INSTANCE = new LogEventDroppedEncoder();
        private static final FieldDescriptor REASON_DESCRIPTOR = FieldDescriptor.builder("reason").withProperty(AtProtobuf.builder().tag(3).build()).build();

        private LogEventDroppedEncoder() {
        }

        public void encode(LogEventDropped value, ObjectEncoderContext ctx) {
            ctx.add(EVENTSDROPPEDCOUNT_DESCRIPTOR, value.getEventsDroppedCount());
            ctx.add(REASON_DESCRIPTOR, (Object) value.getReason());
        }
    }

    public static final class GlobalMetricsEncoder implements ObjectEncoder<GlobalMetrics> {
        static final GlobalMetricsEncoder INSTANCE = new GlobalMetricsEncoder();
        private static final FieldDescriptor STORAGEMETRICS_DESCRIPTOR = FieldDescriptor.builder("storageMetrics").withProperty(AtProtobuf.builder().tag(1).build()).build();

        private GlobalMetricsEncoder() {
        }

        public void encode(GlobalMetrics value, ObjectEncoderContext ctx) {
            ctx.add(STORAGEMETRICS_DESCRIPTOR, (Object) value.getStorageMetricsInternal());
        }
    }

    public static final class StorageMetricsEncoder implements ObjectEncoder<StorageMetrics> {
        private static final FieldDescriptor CURRENTCACHESIZEBYTES_DESCRIPTOR = FieldDescriptor.builder("currentCacheSizeBytes").withProperty(AtProtobuf.builder().tag(1).build()).build();
        static final StorageMetricsEncoder INSTANCE = new StorageMetricsEncoder();
        private static final FieldDescriptor MAXCACHESIZEBYTES_DESCRIPTOR = FieldDescriptor.builder("maxCacheSizeBytes").withProperty(AtProtobuf.builder().tag(2).build()).build();

        private StorageMetricsEncoder() {
        }

        public void encode(StorageMetrics value, ObjectEncoderContext ctx) {
            ctx.add(CURRENTCACHESIZEBYTES_DESCRIPTOR, value.getCurrentCacheSizeBytes());
            ctx.add(MAXCACHESIZEBYTES_DESCRIPTOR, value.getMaxCacheSizeBytes());
        }
    }
}
