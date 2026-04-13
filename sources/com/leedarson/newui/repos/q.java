package com.leedarson.newui.repos;

import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.views.LoadingWaveBoxView;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;

/* compiled from: LiveConnectStateStepsCreateFactory */
public class q {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static ArrayList<LoadingWaveBoxView.LoadingStepBean> a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 4460, new Class[0], ArrayList.class);
        if (proxy.isSupported) {
            return (ArrayList) proxy.result;
        }
        ArrayList<LoadingWaveBoxView.LoadingStepBean> steps = new ArrayList<>();
        LoadingWaveBoxView.LoadingStepBean connectStep = new LoadingWaveBoxView.LoadingStepBean();
        connectStep.stepIndex = 0;
        connectStep.description = "(1/3) " + PubUtils.getString(BaseApplication.b(), R$string.lds_live_connect_step_one);
        LoadingWaveBoxView.LoadingStepBean connectStepTwo = new LoadingWaveBoxView.LoadingStepBean();
        connectStepTwo.stepIndex = 0;
        connectStepTwo.description = "(2/3) " + PubUtils.getString(BaseApplication.b(), R$string.lds_live_connect_step_two);
        LoadingWaveBoxView.LoadingStepBean connectStepThree = new LoadingWaveBoxView.LoadingStepBean();
        connectStepThree.stepIndex = 0;
        connectStepThree.description = "(3/3) " + PubUtils.getString(BaseApplication.b(), R$string.lds_live_connect_step_three);
        steps.add(connectStep);
        steps.add(connectStepTwo);
        steps.add(connectStepThree);
        return steps;
    }
}
