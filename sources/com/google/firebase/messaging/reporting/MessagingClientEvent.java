package com.google.firebase.messaging.reporting;

import com.google.firebase.encoders.proto.ProtoEnum;
import com.google.firebase.encoders.proto.Protobuf;

public final class MessagingClientEvent {
    private static final MessagingClientEvent DEFAULT_INSTANCE = new Builder().build();
    private final String analytics_label_;
    private final long bulk_id_;
    private final long campaign_id_;
    private final String collapse_key_;
    private final String composer_label_;
    private final Event event_;
    private final String instance_id_;
    private final String message_id_;
    private final MessageType message_type_;
    private final String package_name_;
    private final int priority_;
    private final long project_number_;
    private final SDKPlatform sdk_platform_;
    private final String topic_;
    private final int ttl_;

    MessagingClientEvent(long project_number_2, String message_id_2, String instance_id_2, MessageType message_type_2, SDKPlatform sdk_platform_2, String package_name_2, String collapse_key_2, int priority_2, int ttl_2, String topic_2, long bulk_id_2, Event event_2, String analytics_label_2, long campaign_id_2, String composer_label_2) {
        this.project_number_ = project_number_2;
        this.message_id_ = message_id_2;
        this.instance_id_ = instance_id_2;
        this.message_type_ = message_type_2;
        this.sdk_platform_ = sdk_platform_2;
        this.package_name_ = package_name_2;
        this.collapse_key_ = collapse_key_2;
        this.priority_ = priority_2;
        this.ttl_ = ttl_2;
        this.topic_ = topic_2;
        this.bulk_id_ = bulk_id_2;
        this.event_ = event_2;
        this.analytics_label_ = analytics_label_2;
        this.campaign_id_ = campaign_id_2;
        this.composer_label_ = composer_label_2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Protobuf(tag = 1)
    public long getProjectNumber() {
        return this.project_number_;
    }

    @Protobuf(tag = 2)
    public String getMessageId() {
        return this.message_id_;
    }

    @Protobuf(tag = 3)
    public String getInstanceId() {
        return this.instance_id_;
    }

    @Protobuf(tag = 4)
    public MessageType getMessageType() {
        return this.message_type_;
    }

    @Protobuf(tag = 5)
    public SDKPlatform getSdkPlatform() {
        return this.sdk_platform_;
    }

    @Protobuf(tag = 6)
    public String getPackageName() {
        return this.package_name_;
    }

    @Protobuf(tag = 7)
    public String getCollapseKey() {
        return this.collapse_key_;
    }

    @Protobuf(tag = 8)
    public int getPriority() {
        return this.priority_;
    }

    @Protobuf(tag = 9)
    public int getTtl() {
        return this.ttl_;
    }

    @Protobuf(tag = 10)
    public String getTopic() {
        return this.topic_;
    }

    @Protobuf(tag = 11)
    public long getBulkId() {
        return this.bulk_id_;
    }

    @Protobuf(tag = 12)
    public Event getEvent() {
        return this.event_;
    }

    @Protobuf(tag = 13)
    public String getAnalyticsLabel() {
        return this.analytics_label_;
    }

    @Protobuf(tag = 14)
    public long getCampaignId() {
        return this.campaign_id_;
    }

    @Protobuf(tag = 15)
    public String getComposerLabel() {
        return this.composer_label_;
    }

    public static MessagingClientEvent getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder {
        private String analytics_label_ = "";
        private long bulk_id_ = 0;
        private long campaign_id_ = 0;
        private String collapse_key_ = "";
        private String composer_label_ = "";
        private Event event_ = Event.UNKNOWN_EVENT;
        private String instance_id_ = "";
        private String message_id_ = "";
        private MessageType message_type_ = MessageType.UNKNOWN;
        private String package_name_ = "";
        private int priority_ = 0;
        private long project_number_ = 0;
        private SDKPlatform sdk_platform_ = SDKPlatform.UNKNOWN_OS;
        private String topic_ = "";
        private int ttl_ = 0;

        Builder() {
        }

        public MessagingClientEvent build() {
            return new MessagingClientEvent(this.project_number_, this.message_id_, this.instance_id_, this.message_type_, this.sdk_platform_, this.package_name_, this.collapse_key_, this.priority_, this.ttl_, this.topic_, this.bulk_id_, this.event_, this.analytics_label_, this.campaign_id_, this.composer_label_);
        }

        public Builder setProjectNumber(long project_number_2) {
            this.project_number_ = project_number_2;
            return this;
        }

        public Builder setMessageId(String message_id_2) {
            this.message_id_ = message_id_2;
            return this;
        }

        public Builder setInstanceId(String instance_id_2) {
            this.instance_id_ = instance_id_2;
            return this;
        }

        public Builder setMessageType(MessageType message_type_2) {
            this.message_type_ = message_type_2;
            return this;
        }

        public Builder setSdkPlatform(SDKPlatform sdk_platform_2) {
            this.sdk_platform_ = sdk_platform_2;
            return this;
        }

        public Builder setPackageName(String package_name_2) {
            this.package_name_ = package_name_2;
            return this;
        }

        public Builder setCollapseKey(String collapse_key_2) {
            this.collapse_key_ = collapse_key_2;
            return this;
        }

        public Builder setPriority(int priority_2) {
            this.priority_ = priority_2;
            return this;
        }

        public Builder setTtl(int ttl_2) {
            this.ttl_ = ttl_2;
            return this;
        }

        public Builder setTopic(String topic_2) {
            this.topic_ = topic_2;
            return this;
        }

        public Builder setBulkId(long bulk_id_2) {
            this.bulk_id_ = bulk_id_2;
            return this;
        }

        public Builder setEvent(Event event_2) {
            this.event_ = event_2;
            return this;
        }

        public Builder setAnalyticsLabel(String analytics_label_2) {
            this.analytics_label_ = analytics_label_2;
            return this;
        }

        public Builder setCampaignId(long campaign_id_2) {
            this.campaign_id_ = campaign_id_2;
            return this;
        }

        public Builder setComposerLabel(String composer_label_2) {
            this.composer_label_ = composer_label_2;
            return this;
        }
    }

    public enum MessageType implements ProtoEnum {
        UNKNOWN(0),
        DATA_MESSAGE(1),
        TOPIC(2),
        DISPLAY_NOTIFICATION(3);
        
        private final int number_;

        private MessageType(int number_2) {
            this.number_ = number_2;
        }

        public int getNumber() {
            return this.number_;
        }
    }

    public enum SDKPlatform implements ProtoEnum {
        UNKNOWN_OS(0),
        ANDROID(1),
        IOS(2),
        WEB(3);
        
        private final int number_;

        private SDKPlatform(int number_2) {
            this.number_ = number_2;
        }

        public int getNumber() {
            return this.number_;
        }
    }

    public enum Event implements ProtoEnum {
        UNKNOWN_EVENT(0),
        MESSAGE_DELIVERED(1),
        MESSAGE_OPEN(2);
        
        private final int number_;

        private Event(int number_2) {
            this.number_ = number_2;
        }

        public int getNumber() {
            return this.number_;
        }
    }
}
