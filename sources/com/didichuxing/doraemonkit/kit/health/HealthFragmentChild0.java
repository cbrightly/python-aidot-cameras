package com.didichuxing.doraemonkit.kit.health;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.blankj.utilcode.util.d;
import com.blankj.utilcode.util.e0;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.GlobalConfig;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.okgo.model.Response;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.didichuxing.doraemonkit.util.LogHelper;
import com.didichuxing.doraemonkit.widget.dialog.DialogListener;
import com.didichuxing.doraemonkit.widget.dialog.DialogProvider;
import com.didichuxing.doraemonkit.widget.dialog.UniversalDialogFragment;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import meshsdk.cache.CacheHandler;

public class HealthFragmentChild0 extends BaseFragment {
    ImageView mController;
    TextView mTitle;
    UserInfoDialogProvider mUserInfoDialogProvider;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_health_child0;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            this.mTitle = (TextView) findViewById(R.id.tv_title);
            this.mController = (ImageView) findViewById(R.id.iv_btn);
            if (DokitConstant.APP_HEALTH_RUNNING) {
                this.mTitle.setVisibility(0);
                this.mController.setImageResource(R.mipmap.dk_health_stop);
            } else {
                this.mTitle.setVisibility(4);
                this.mController.setImageResource(R.mipmap.dk_health_start);
            }
            this.mUserInfoDialogProvider = new UserInfoDialogProvider((Object) null, new DialogListener() {
                public boolean onPositive() {
                    UserInfoDialogProvider userInfoDialogProvider = HealthFragmentChild0.this.mUserInfoDialogProvider;
                    if (userInfoDialogProvider != null) {
                        return userInfoDialogProvider.uploadAppHealthInfo(new UploadAppHealthCallback() {
                            public void onSuccess(Response<String> response) {
                                String str = HealthFragmentChild0.this.TAG;
                                LogHelper.i(str, "上传成功===>" + response.body());
                                e0.n(DokitUtil.getString(R.string.dk_health_upload_successed));
                                GlobalConfig.setAppHealth(false);
                                DokitConstant.APP_HEALTH_RUNNING = false;
                                HealthFragmentChild0.this.mTitle.setVisibility(4);
                                HealthFragmentChild0.this.mController.setImageResource(R.mipmap.dk_health_start);
                                AppHealthInfoUtil.getInstance().stop();
                                AppHealthInfoUtil.getInstance().release();
                            }

                            public void onError(Response<String> response) {
                                String str = HealthFragmentChild0.this.TAG;
                                LogHelper.e(str, "error response===>" + response.message());
                                e0.n(DokitUtil.getString(R.string.dk_health_upload_failed));
                            }
                        });
                    }
                    return true;
                }

                public boolean onNegative() {
                    return true;
                }

                public void onCancel() {
                    e0.n(DokitUtil.getString(R.string.dk_health_upload_droped));
                    GlobalConfig.setAppHealth(false);
                    DokitConstant.APP_HEALTH_RUNNING = false;
                    HealthFragmentChild0.this.mTitle.setVisibility(4);
                    HealthFragmentChild0.this.mController.setImageResource(R.mipmap.dk_health_start);
                    AppHealthInfoUtil.getInstance().stop();
                    AppHealthInfoUtil.getInstance().release();
                }
            });
            this.mController.setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    if (HealthFragmentChild0.this.getActivity() == null) {
                        SensorsDataAutoTrackHelper.trackViewOnClick(view);
                        return;
                    }
                    if (DokitConstant.APP_HEALTH_RUNNING) {
                        HealthFragmentChild0 healthFragmentChild0 = HealthFragmentChild0.this;
                        UserInfoDialogProvider userInfoDialogProvider = healthFragmentChild0.mUserInfoDialogProvider;
                        if (userInfoDialogProvider != null) {
                            healthFragmentChild0.showDialog(userInfoDialogProvider);
                        }
                    } else {
                        new AlertDialog.Builder(HealthFragmentChild0.this.getActivity()).setTitle((CharSequence) DokitUtil.getString(R.string.dk_health_upload_title)).setMessage((CharSequence) DokitUtil.getString(R.string.dk_health_upload_message)).setCancelable(false).setPositiveButton((CharSequence) "ok", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                            @SensorsDataInstrumented
                            public void onClick(DialogInterface dialog, int i) {
                                int i2 = i;
                                dialog.dismiss();
                                if (HealthFragmentChild0.this.mController != null) {
                                    e0.n(DokitUtil.getString(R.string.dk_health_funcation_start));
                                    GlobalConfig.setAppHealth(true);
                                    DokitConstant.APP_HEALTH_RUNNING = true;
                                    HealthFragmentChild0.this.mController.postDelayed(new Runnable() {
                                        public void run() {
                                            d.q();
                                            Process.killProcess(Process.myPid());
                                            System.exit(1);
                                        }
                                    }, CacheHandler.delayTime);
                                }
                                SensorsDataAutoTrackHelper.trackDialog(dialog, i);
                            }
                        }).setNegativeButton((CharSequence) "cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                            @SensorsDataInstrumented
                            public void onClick(DialogInterface dialog, int i) {
                                int i2 = i;
                                dialog.dismiss();
                                SensorsDataAutoTrackHelper.trackDialog(dialog, i);
                            }
                        }).show();
                    }
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
        }
    }

    public void showDialog(DialogProvider provider) {
        UniversalDialogFragment dialog = new UniversalDialogFragment();
        provider.setHost(dialog);
        dialog.setProvider(provider);
        provider.show(getChildFragmentManager());
    }
}
