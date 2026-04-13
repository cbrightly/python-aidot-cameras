package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class bm0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ bm0 a = new bm0();

    private /* synthetic */ bm0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.FixedLabelCluster) baseChipCluster).readLabelListAttribute((ChipClusters.FixedLabelCluster.LabelListAttributeCallback) obj);
    }
}
