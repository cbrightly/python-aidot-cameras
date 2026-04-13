package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class lm implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ lm a = new lm();

    private /* synthetic */ lm() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.OperationalCredentialsCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.OperationalCredentialsCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
