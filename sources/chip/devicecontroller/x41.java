package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class x41 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ x41 a = new x41();

    private /* synthetic */ x41() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.PowerSourceCluster) baseChipCluster).readWiredAssessedCurrentAttribute((ChipClusters.PowerSourceCluster.WiredAssessedCurrentAttributeCallback) obj);
    }
}
