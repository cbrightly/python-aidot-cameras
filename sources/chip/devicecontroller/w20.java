package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class w20 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ w20 a = new w20();

    private /* synthetic */ w20() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ScenesCluster) baseChipCluster).readSceneValidAttribute((ChipClusters.BooleanAttributeCallback) obj);
    }
}
