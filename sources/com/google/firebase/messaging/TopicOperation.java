package com.google.firebase.messaging;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.common.internal.Objects;
import java.util.regex.Pattern;

public final class TopicOperation {
    private static final String OLD_TOPIC_PREFIX = "/topics/";
    @VisibleForTesting
    static final String OPERATION_PAIR_DIVIDER = "!";
    private static final String TOPIC_NAME_PATTERN = "[a-zA-Z0-9-_.~%]{1,900}";
    private static final Pattern TOPIC_NAME_REGEXP = Pattern.compile(TOPIC_NAME_PATTERN);
    private final String operation;
    private final String serializedString;
    private final String topic;

    private TopicOperation(String operation2, String topic2) {
        this.topic = normalizeTopicOrThrow(topic2, operation2);
        this.operation = operation2;
        this.serializedString = operation2 + OPERATION_PAIR_DIVIDER + topic2;
    }

    @NonNull
    private static String normalizeTopicOrThrow(String topic2, String methodName) {
        if (topic2 != null && topic2.startsWith(OLD_TOPIC_PREFIX)) {
            Log.w(Constants.TAG, String.format("Format /topics/topic-name is deprecated. Only 'topic-name' should be used in %s.", new Object[]{methodName}));
            topic2 = topic2.substring(OLD_TOPIC_PREFIX.length());
        }
        if (topic2 != null && TOPIC_NAME_REGEXP.matcher(topic2).matches()) {
            return topic2;
        }
        throw new IllegalArgumentException(String.format("Invalid topic name: %s does not match the allowed format %s.", new Object[]{topic2, TOPIC_NAME_PATTERN}));
    }

    public static TopicOperation subscribe(@NonNull String topic2) {
        return new TopicOperation(ExifInterface.LATITUDE_SOUTH, topic2);
    }

    public static TopicOperation unsubscribe(@NonNull String topic2) {
        return new TopicOperation("U", topic2);
    }

    @Nullable
    static TopicOperation from(String entry) {
        if (TextUtils.isEmpty(entry)) {
            return null;
        }
        String[] splits = entry.split(OPERATION_PAIR_DIVIDER, -1);
        if (splits.length != 2) {
            return null;
        }
        return new TopicOperation(splits[0], splits[1]);
    }

    public String getTopic() {
        return this.topic;
    }

    public String getOperation() {
        return this.operation;
    }

    public String serialize() {
        return this.serializedString;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof TopicOperation)) {
            return false;
        }
        TopicOperation that = (TopicOperation) obj;
        if (!this.topic.equals(that.topic) || !this.operation.equals(that.operation)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.operation, this.topic);
    }
}
