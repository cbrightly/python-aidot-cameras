package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class qq0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ qq0 a = new qq0();

    private /* synthetic */ qq0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.AccountLoginCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.AccountLoginCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
