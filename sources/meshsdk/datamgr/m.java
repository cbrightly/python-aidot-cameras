package meshsdk.datamgr;

import com.leedarson.serviceimpl.reporters.k;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class m implements e {
    public final /* synthetic */ String c;

    public /* synthetic */ m(String str) {
        this.c = str;
    }

    public final void accept(Object obj) {
        k.a("updateMeshKey,houseId:" + this.c + ",获取house接口获取mesh网络信息失败，不提交当前network信息，避免提错");
    }
}
