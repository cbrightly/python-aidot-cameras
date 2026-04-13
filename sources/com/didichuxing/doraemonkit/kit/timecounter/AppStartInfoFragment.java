package com.didichuxing.doraemonkit.kit.timecounter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.b0;
import com.blankj.utilcode.util.d;
import com.blankj.utilcode.util.e0;
import com.blankj.utilcode.util.i;
import com.blankj.utilcode.util.j;
import com.blankj.utilcode.util.r;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.aop.method_stack.MethodStackUtil;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.util.FileUtil;
import com.didichuxing.doraemonkit.widget.titlebar.TitleBar;
import java.io.File;

public class AppStartInfoFragment extends BaseFragment {
    TextView mInfo;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_app_start_info;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        ((TitleBar) findViewById(R.id.title_bar)).setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            public void onLeftClick() {
                AppStartInfoFragment.this.finish();
            }

            public void onRightClick() {
                AppStartInfoFragment appStartInfoFragment = AppStartInfoFragment.this;
                appStartInfoFragment.export2File(appStartInfoFragment.mInfo.getText().toString());
            }
        });
        this.mInfo = (TextView) findViewById(R.id.app_start_info);
        StringBuilder builder = new StringBuilder();
        if (TextUtils.isEmpty(MethodStackUtil.STR_APP_ATTACH_BASECONTEXT)) {
            builder.append("只有配置slowMethod的strategy=0模式下才能统计到启动函数调用栈");
        } else {
            builder.append(MethodStackUtil.STR_APP_ATTACH_BASECONTEXT);
            builder.append("\n");
            builder.append(MethodStackUtil.STR_APP_ON_CREATE);
        }
        this.mInfo.setText(builder.toString());
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /* access modifiers changed from: private */
    public void export2File(final String info) {
        if (TextUtils.isEmpty(info)) {
            e0.n("启动信息为空");
            return;
        }
        e0.n("启动信息保存中,请稍后...");
        final String logPath = r.d() + File.separator + d.a() + "_app_launch.log";
        final File logFile = new File(logPath);
        b0.f(new b0.f<Boolean>() {
            public Boolean doInBackground() {
                try {
                    i.e(logFile, info, false);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            public void onSuccess(Boolean result) {
                if (result.booleanValue()) {
                    e0.n("启动信息文件保存在:" + logPath);
                    FileUtil.systemShare(DoraemonKit.APPLICATION, logFile);
                }
            }

            public void onCancel() {
                if (logFile.exists()) {
                    j.d(logFile);
                }
                e0.n("启动信息保存失败");
            }

            public void onFail(Throwable t) {
                if (logFile.exists()) {
                    j.d(logFile);
                }
                e0.n("启动信息保存失败");
            }
        });
    }
}
