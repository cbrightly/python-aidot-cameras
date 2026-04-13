package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class f01 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ f01 a = new f01();

    private /* synthetic */ f01() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TargetNavigatorCluster) baseChipCluster).readCurrentTargetAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
