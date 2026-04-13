package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class qs0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ qs0 a = new qs0();

    private /* synthetic */ qs0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).readPrimary2YAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
