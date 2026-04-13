package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class zk0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ zk0 a = new zk0();

    private /* synthetic */ zk0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.PumpConfigurationAndControlCluster) baseChipCluster).readMaxFlowAttribute((ChipClusters.PumpConfigurationAndControlCluster.MaxFlowAttributeCallback) obj);
    }
}
