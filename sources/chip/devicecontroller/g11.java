package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class g11 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ g11 a = new g11();

    private /* synthetic */ g11() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ScenesCluster) baseChipCluster).readSceneCountAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
