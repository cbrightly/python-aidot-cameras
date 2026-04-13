package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class zs implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ zs a = new zs();

    private /* synthetic */ zs() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).readAttributeListAttribute((ChipClusters.ColorControlCluster.AttributeListAttributeCallback) obj);
    }
}
