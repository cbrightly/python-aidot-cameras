package chip.devicecontroller.model;

import java.util.Locale;
import java.util.Objects;

public class ChipAttributePath {
    private ChipPathId attributeId;
    private ChipPathId clusterId;
    private ChipPathId endpointId;

    private ChipAttributePath(ChipPathId endpointId2, ChipPathId clusterId2, ChipPathId attributeId2) {
        this.endpointId = endpointId2;
        this.clusterId = clusterId2;
        this.attributeId = attributeId2;
    }

    public ChipPathId getEndpointId() {
        return this.endpointId;
    }

    public ChipPathId getClusterId() {
        return this.clusterId;
    }

    public ChipPathId getAttributeId() {
        return this.attributeId;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ChipAttributePath)) {
            return false;
        }
        ChipAttributePath that = (ChipAttributePath) object;
        if (!Objects.equals(this.endpointId, that.endpointId) || !Objects.equals(this.clusterId, that.clusterId) || !Objects.equals(this.attributeId, that.attributeId)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.endpointId, this.clusterId, this.attributeId});
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "Endpoint %s, cluster %s, attribute %s", new Object[]{this.endpointId, this.clusterId, this.attributeId});
    }

    public static ChipAttributePath newInstance(ChipPathId endpointId2, ChipPathId clusterId2, ChipPathId attributeId2) {
        return new ChipAttributePath(endpointId2, clusterId2, attributeId2);
    }

    public static ChipAttributePath newInstance(long endpointId2, long clusterId2, long attributeId2) {
        return new ChipAttributePath(ChipPathId.forId(endpointId2), ChipPathId.forId(clusterId2), ChipPathId.forId(attributeId2));
    }
}
