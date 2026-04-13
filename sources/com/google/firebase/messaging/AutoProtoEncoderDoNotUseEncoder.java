package com.google.firebase.messaging;

import androidx.core.app.NotificationCompat;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import com.google.firebase.encoders.config.Configurator;
import com.google.firebase.encoders.config.EncoderConfig;
import com.google.firebase.encoders.proto.AtProtobuf;
import com.google.firebase.messaging.reporting.MessagingClientEvent;
import com.google.firebase.messaging.reporting.MessagingClientEventExtension;

public final class AutoProtoEncoderDoNotUseEncoder implements Configurator {
    public static final int CODEGEN_VERSION = 2;
    public static final Configurator CONFIG = new AutoProtoEncoderDoNotUseEncoder();

    private AutoProtoEncoderDoNotUseEncoder() {
    }

    public void configure(EncoderConfig<?> cfg) {
        cfg.registerEncoder((Class<U>) ProtoEncoderDoNotUse.class, (ObjectEncoder<? super U>) ProtoEncoderDoNotUseEncoder.INSTANCE);
        cfg.registerEncoder((Class<U>) MessagingClientEventExtension.class, (ObjectEncoder<? super U>) MessagingClientEventExtensionEncoder.INSTANCE);
        cfg.registerEncoder((Class<U>) MessagingClientEvent.class, (ObjectEncoder<? super U>) MessagingClientEventEncoder.INSTANCE);
    }

    public static final class ProtoEncoderDoNotUseEncoder implements ObjectEncoder<ProtoEncoderDoNotUse> {
        static final ProtoEncoderDoNotUseEncoder INSTANCE = new ProtoEncoderDoNotUseEncoder();
        private static final FieldDescriptor MESSAGINGCLIENTEVENTEXTENSION_DESCRIPTOR = FieldDescriptor.of("messagingClientEventExtension");

        private ProtoEncoderDoNotUseEncoder() {
        }

        public void encode(ProtoEncoderDoNotUse value, ObjectEncoderContext ctx) {
            ctx.add(MESSAGINGCLIENTEVENTEXTENSION_DESCRIPTOR, (Object) value.getMessagingClientEventExtension());
        }
    }

    public static final class MessagingClientEventExtensionEncoder implements ObjectEncoder<MessagingClientEventExtension> {
        static final MessagingClientEventExtensionEncoder INSTANCE = new MessagingClientEventExtensionEncoder();
        private static final FieldDescriptor MESSAGINGCLIENTEVENT_DESCRIPTOR = FieldDescriptor.builder("messagingClientEvent").withProperty(AtProtobuf.builder().tag(1).build()).build();

        private MessagingClientEventExtensionEncoder() {
        }

        public void encode(MessagingClientEventExtension value, ObjectEncoderContext ctx) {
            ctx.add(MESSAGINGCLIENTEVENT_DESCRIPTOR, (Object) value.getMessagingClientEventInternal());
        }
    }

    public static final class MessagingClientEventEncoder implements ObjectEncoder<MessagingClientEvent> {
        private static final FieldDescriptor ANALYTICSLABEL_DESCRIPTOR = FieldDescriptor.builder("analyticsLabel").withProperty(AtProtobuf.builder().tag(13).build()).build();
        private static final FieldDescriptor BULKID_DESCRIPTOR = FieldDescriptor.builder("bulkId").withProperty(AtProtobuf.builder().tag(11).build()).build();
        private static final FieldDescriptor CAMPAIGNID_DESCRIPTOR = FieldDescriptor.builder("campaignId").withProperty(AtProtobuf.builder().tag(14).build()).build();
        private static final FieldDescriptor COLLAPSEKEY_DESCRIPTOR = FieldDescriptor.builder("collapseKey").withProperty(AtProtobuf.builder().tag(7).build()).build();
        private static final FieldDescriptor COMPOSERLABEL_DESCRIPTOR = FieldDescriptor.builder("composerLabel").withProperty(AtProtobuf.builder().tag(15).build()).build();
        private static final FieldDescriptor EVENT_DESCRIPTOR = FieldDescriptor.builder(NotificationCompat.CATEGORY_EVENT).withProperty(AtProtobuf.builder().tag(12).build()).build();
        static final MessagingClientEventEncoder INSTANCE = new MessagingClientEventEncoder();
        private static final FieldDescriptor INSTANCEID_DESCRIPTOR = FieldDescriptor.builder("instanceId").withProperty(AtProtobuf.builder().tag(3).build()).build();
        private static final FieldDescriptor MESSAGEID_DESCRIPTOR = FieldDescriptor.builder("messageId").withProperty(AtProtobuf.builder().tag(2).build()).build();
        private static final FieldDescriptor MESSAGETYPE_DESCRIPTOR = FieldDescriptor.builder("messageType").withProperty(AtProtobuf.builder().tag(4).build()).build();
        private static final FieldDescriptor PACKAGENAME_DESCRIPTOR = FieldDescriptor.builder("packageName").withProperty(AtProtobuf.builder().tag(6).build()).build();
        private static final FieldDescriptor PRIORITY_DESCRIPTOR = FieldDescriptor.builder(Progress.PRIORITY).withProperty(AtProtobuf.builder().tag(8).build()).build();
        private static final FieldDescriptor PROJECTNUMBER_DESCRIPTOR = FieldDescriptor.builder("projectNumber").withProperty(AtProtobuf.builder().tag(1).build()).build();
        private static final FieldDescriptor SDKPLATFORM_DESCRIPTOR = FieldDescriptor.builder("sdkPlatform").withProperty(AtProtobuf.builder().tag(5).build()).build();
        private static final FieldDescriptor TOPIC_DESCRIPTOR = FieldDescriptor.builder("topic").withProperty(AtProtobuf.builder().tag(10).build()).build();
        private static final FieldDescriptor TTL_DESCRIPTOR = FieldDescriptor.builder("ttl").withProperty(AtProtobuf.builder().tag(9).build()).build();

        private MessagingClientEventEncoder() {
        }

        public void encode(MessagingClientEvent value, ObjectEncoderContext ctx) {
            ctx.add(PROJECTNUMBER_DESCRIPTOR, value.getProjectNumber());
            ctx.add(MESSAGEID_DESCRIPTOR, (Object) value.getMessageId());
            ctx.add(INSTANCEID_DESCRIPTOR, (Object) value.getInstanceId());
            ctx.add(MESSAGETYPE_DESCRIPTOR, (Object) value.getMessageType());
            ctx.add(SDKPLATFORM_DESCRIPTOR, (Object) value.getSdkPlatform());
            ctx.add(PACKAGENAME_DESCRIPTOR, (Object) value.getPackageName());
            ctx.add(COLLAPSEKEY_DESCRIPTOR, (Object) value.getCollapseKey());
            ctx.add(PRIORITY_DESCRIPTOR, value.getPriority());
            ctx.add(TTL_DESCRIPTOR, value.getTtl());
            ctx.add(TOPIC_DESCRIPTOR, (Object) value.getTopic());
            ctx.add(BULKID_DESCRIPTOR, value.getBulkId());
            ctx.add(EVENT_DESCRIPTOR, (Object) value.getEvent());
            ctx.add(ANALYTICSLABEL_DESCRIPTOR, (Object) value.getAnalyticsLabel());
            ctx.add(CAMPAIGNID_DESCRIPTOR, value.getCampaignId());
            ctx.add(COMPOSERLABEL_DESCRIPTOR, (Object) value.getComposerLabel());
        }
    }
}
