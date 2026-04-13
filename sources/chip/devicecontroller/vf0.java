package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class vf0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ vf0 a = new vf0();

    private /* synthetic */ vf0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BindingCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.BindingCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
