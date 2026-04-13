package chip.clusterinfo;

import chip.devicecontroller.ChipClusters;
import java.util.Map;
import java.util.function.Supplier;

public class InteractionInfo {
    private Supplier<DelegatedClusterCallback> commandCallbackSupplier;
    public ClusterCommandFunction commandFunction;
    private Map<String, CommandParameterInfo> commandParameters;

    @FunctionalInterface
    public interface ClusterCommandFunction {
        void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map<String, Object> map);
    }

    public InteractionInfo(ClusterCommandFunction commandFunction2, Supplier<DelegatedClusterCallback> commandCallbackSupplier2, Map<String, CommandParameterInfo> commandParameters2) {
        this.commandFunction = commandFunction2;
        this.commandCallbackSupplier = commandCallbackSupplier2;
        this.commandParameters = commandParameters2;
    }

    public ClusterCommandFunction getCommandFunction() {
        return this.commandFunction;
    }

    public Supplier<DelegatedClusterCallback> getCommandCallbackSupplier() {
        return this.commandCallbackSupplier;
    }

    public Map<String, CommandParameterInfo> getCommandParameters() {
        return this.commandParameters;
    }
}
