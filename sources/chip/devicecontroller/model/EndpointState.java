package chip.devicecontroller.model;

import androidx.annotation.Nullable;
import java.util.Map;

public final class EndpointState {
    private Map<Long, ClusterState> clusters;

    public EndpointState(Map<Long, ClusterState> clusters2) {
        this.clusters = clusters2;
    }

    public Map<Long, ClusterState> getClusterStates() {
        return this.clusters;
    }

    @Nullable
    public ClusterState getClusterState(long clusterId) {
        return this.clusters.get(Long.valueOf(clusterId));
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.clusters.forEach(new c(builder));
        return builder.toString();
    }

    static /* synthetic */ void lambda$toString$0(StringBuilder builder, Long clusterId, ClusterState clusterState) {
        builder.append("Cluster ");
        builder.append(clusterId);
        builder.append(": {\n");
        builder.append(clusterState.toString());
        builder.append("}\n");
    }
}
