package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class qr implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ qr a = new qr();

    private /* synthetic */ qr() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BasicCluster) baseChipCluster).readProductLabelAttribute((ChipClusters.CharStringAttributeCallback) obj);
    }
}
