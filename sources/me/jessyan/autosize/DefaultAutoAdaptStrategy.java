package me.jessyan.autosize;

import android.app.Activity;
import java.util.Locale;
import me.jessyan.autosize.external.ExternalAdaptInfo;
import me.jessyan.autosize.internal.CancelAdapt;
import me.jessyan.autosize.internal.CustomAdapt;
import me.jessyan.autosize.utils.AutoSizeLog;

public class DefaultAutoAdaptStrategy implements AutoAdaptStrategy {
    public void applyAdapt(Object target, Activity activity) {
        if (AutoSizeConfig.getInstance().getExternalAdaptManager().isRun()) {
            if (AutoSizeConfig.getInstance().getExternalAdaptManager().isCancelAdapt(target.getClass())) {
                AutoSizeLog.w(String.format(Locale.ENGLISH, "%s canceled the adaptation!", new Object[]{target.getClass().getName()}));
                AutoSize.cancelAdapt(activity);
                return;
            }
            ExternalAdaptInfo info = AutoSizeConfig.getInstance().getExternalAdaptManager().getExternalAdaptInfoOfActivity(target.getClass());
            if (info != null) {
                AutoSizeLog.d(String.format(Locale.ENGLISH, "%s used %s for adaptation!", new Object[]{target.getClass().getName(), ExternalAdaptInfo.class.getName()}));
                AutoSize.autoConvertDensityOfExternalAdaptInfo(activity, info);
                return;
            }
        }
        if (target instanceof CancelAdapt) {
            AutoSizeLog.w(String.format(Locale.ENGLISH, "%s canceled the adaptation!", new Object[]{target.getClass().getName()}));
            AutoSize.cancelAdapt(activity);
        } else if (target instanceof CustomAdapt) {
            AutoSizeLog.d(String.format(Locale.ENGLISH, "%s implemented by %s!", new Object[]{target.getClass().getName(), CustomAdapt.class.getName()}));
            AutoSize.autoConvertDensityOfCustomAdapt(activity, (CustomAdapt) target);
        } else {
            AutoSizeLog.d(String.format(Locale.ENGLISH, "%s used the global configuration.", new Object[]{target.getClass().getName()}));
            AutoSize.autoConvertDensityOfGlobal(activity);
        }
    }
}
