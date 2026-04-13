package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class aw0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ aw0 a = new aw0();

    private /* synthetic */ aw0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).readWhitePointXAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
