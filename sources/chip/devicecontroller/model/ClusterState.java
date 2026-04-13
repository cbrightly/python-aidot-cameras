package chip.devicecontroller.model;

import androidx.annotation.Nullable;
import com.google.maps.android.BuildConfig;
import java.util.Map;

public final class ClusterState {
    private Map<Long, AttributeState> attributes;
    private Map<Long, EventState> events;

    public ClusterState(Map<Long, AttributeState> attributes2, Map<Long, EventState> events2) {
        this.attributes = attributes2;
        this.events = events2;
    }

    public Map<Long, AttributeState> getAttributeStates() {
        return this.attributes;
    }

    public Map<Long, EventState> getEventStates() {
        return this.events;
    }

    @Nullable
    public AttributeState getAttributeState(long attributeId) {
        return this.attributes.get(Long.valueOf(attributeId));
    }

    @Nullable
    public EventState getEventState(long eventId) {
        return this.events.get(Long.valueOf(eventId));
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.attributes.forEach(new b(builder));
        this.events.forEach(new a(builder));
        return builder.toString();
    }

    static /* synthetic */ void lambda$toString$0(StringBuilder builder, Long attributeId, AttributeState attributeState) {
        builder.append("Attribute ");
        builder.append(attributeId);
        builder.append(": ");
        builder.append(attributeState.getValue() == null ? BuildConfig.TRAVIS : attributeState.getValue().toString());
        builder.append("\n");
    }

    static /* synthetic */ void lambda$toString$1(StringBuilder builder, Long eventId, EventState eventState) {
        builder.append("Event ");
        builder.append(eventId);
        builder.append(": ");
        builder.append(eventState.getValue() == null ? BuildConfig.TRAVIS : eventState.getValue().toString());
        builder.append("\n");
    }
}
