package smarthome.ui;

import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.android.arouter.facade.template.g;
import com.alibaba.android.arouter.launcher.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class GuidePagerActivity$$ARouter$$Autowired implements g {
    public static ChangeQuickRedirect changeQuickRedirect;
    private SerializationService serializationService;

    public void inject(Object target) {
        if (!PatchProxy.proxy(new Object[]{target}, this, changeQuickRedirect, false, 14158, new Class[]{Object.class}, Void.TYPE).isSupported) {
            this.serializationService = (SerializationService) a.c().g(SerializationService.class);
            GuidePagerActivity substitute = (GuidePagerActivity) target;
            substitute.G4 = substitute.getIntent().getExtras() == null ? substitute.G4 : substitute.getIntent().getExtras().getString("data", substitute.G4);
        }
    }
}
