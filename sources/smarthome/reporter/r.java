package smarthome.reporter;

import android.content.Context;
import com.leedarson.log.tracker.BaseStepBean;
import com.leedarson.log.tracker.a;
import java.util.HashMap;

/* compiled from: WebviewEventTracker */
public class r extends a {
    public r(Context context) {
        super(context);
    }

    public HashMap<String, Object> f() {
        return null;
    }

    public void o(HashMap firstFields, BaseStepBean bean) {
        super.o(firstFields, bean);
        firstFields.put("code", Integer.valueOf(bean.getCode()));
    }
}
