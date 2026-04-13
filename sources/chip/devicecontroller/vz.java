package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class vz implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ vz a = new vz();

    private /* synthetic */ vz() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.UnitLocalizationCluster) baseChipCluster).readAttributeListAttribute((ChipClusters.UnitLocalizationCluster.AttributeListAttributeCallback) obj);
    }
}
