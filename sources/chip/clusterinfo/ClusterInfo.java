package chip.clusterinfo;

import chip.devicecontroller.ChipClusters;
import java.util.Map;

public class ClusterInfo {
    private final Map<String, InteractionInfo> commands;
    private final ClusterConstructor createClusterFunction;

    @FunctionalInterface
    public interface ClusterConstructor {
        ChipClusters.BaseChipCluster create(Long l, int i);
    }

    public ClusterInfo(ClusterConstructor createClusterFunction2, Map<String, InteractionInfo> commands2) {
        this.createClusterFunction = createClusterFunction2;
        this.commands = commands2;
    }

    public ClusterConstructor getCreateClusterFunction() {
        return this.createClusterFunction;
    }

    public Map<String, InteractionInfo> getCommands() {
        return this.commands;
    }

    public void combineCommands(Map<String, InteractionInfo> newCommands) {
        this.commands.putAll(newCommands);
    }
}
