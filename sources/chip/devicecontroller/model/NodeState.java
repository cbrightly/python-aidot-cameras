package chip.devicecontroller.model;

import androidx.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public final class NodeState {
    private Map<Integer, EndpointState> endpoints;

    public NodeState(Map<Integer, EndpointState> endpoints2) {
        this.endpoints = endpoints2;
    }

    public Map<Integer, EndpointState> getEndpointStates() {
        return this.endpoints;
    }

    private void addAttribute(int endpointId, long clusterId, long attributeId, AttributeState attributeStateToAdd) {
        EndpointState endpointState = getEndpointState(endpointId);
        if (endpointState == null) {
            endpointState = new EndpointState(new HashMap());
            getEndpointStates().put(Integer.valueOf(endpointId), endpointState);
        }
        ClusterState clusterState = endpointState.getClusterState(clusterId);
        if (clusterState == null) {
            clusterState = new ClusterState(new HashMap(), new HashMap());
            endpointState.getClusterStates().put(Long.valueOf(clusterId), clusterState);
        }
        clusterState.getAttributeStates().put(Long.valueOf(attributeId), attributeStateToAdd);
    }

    private void addEvent(int endpointId, long clusterId, long eventId, EventState eventStateToAdd) {
        EndpointState endpointState = getEndpointState(endpointId);
        if (endpointState == null) {
            endpointState = new EndpointState(new HashMap());
            getEndpointStates().put(Integer.valueOf(endpointId), endpointState);
        }
        ClusterState clusterState = endpointState.getClusterState(clusterId);
        if (clusterState == null) {
            clusterState = new ClusterState(new HashMap(), new HashMap());
            endpointState.getClusterStates().put(Long.valueOf(clusterId), clusterState);
        }
        clusterState.getEventStates().put(Long.valueOf(eventId), eventStateToAdd);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.endpoints.forEach(new d(builder));
        return builder.toString();
    }

    static /* synthetic */ void lambda$toString$0(StringBuilder builder, Integer endpointId, EndpointState endpointState) {
        builder.append("Endpoint ");
        builder.append(endpointId);
        builder.append(": {\n");
        builder.append(endpointState.toString());
        builder.append("}\n");
    }

    @Nullable
    public EndpointState getEndpointState(int endpointId) {
        return this.endpoints.get(Integer.valueOf(endpointId));
    }
}
