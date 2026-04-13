package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class jh implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ jh a = new jh();

    private /* synthetic */ jh() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.UserLabelCluster) baseChipCluster).readLabelListAttribute((ChipClusters.UserLabelCluster.LabelListAttributeCallback) obj);
    }
}
