package chip.devicecontroller.model;

import java.util.Locale;
import java.util.Objects;

public class ChipEventPath {
    private ChipPathId clusterId;
    private ChipPathId endpointId;
    private ChipPathId eventId;

    private ChipEventPath(ChipPathId endpointId2, ChipPathId clusterId2, ChipPathId eventId2) {
        this.endpointId = endpointId2;
        this.clusterId = clusterId2;
        this.eventId = eventId2;
    }

    public ChipPathId getEndpointId() {
        return this.endpointId;
    }

    public ChipPathId getClusterId() {
        return this.clusterId;
    }

    public ChipPathId getEventId() {
        return this.eventId;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ChipEventPath)) {
            return false;
        }
        ChipEventPath that = (ChipEventPath) object;
        if (!Objects.equals(this.endpointId, that.endpointId) || !Objects.equals(this.clusterId, that.clusterId) || !Objects.equals(this.eventId, that.eventId)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.endpointId, this.clusterId, this.eventId});
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "Endpoint %s, cluster %s, event %s", new Object[]{this.endpointId, this.clusterId, this.eventId});
    }

    public static ChipEventPath newInstance(ChipPathId endpointId2, ChipPathId clusterId2, ChipPathId eventId2) {
        return new ChipEventPath(endpointId2, clusterId2, eventId2);
    }

    public static ChipEventPath newInstance(long endpointId2, long clusterId2, long eventId2) {
        return new ChipEventPath(ChipPathId.forId(endpointId2), ChipPathId.forId(clusterId2), ChipPathId.forId(eventId2));
    }
}
