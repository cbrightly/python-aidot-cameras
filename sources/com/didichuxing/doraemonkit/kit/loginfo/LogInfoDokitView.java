package com.didichuxing.doraemonkit.kit.loginfo;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.b0;
import com.blankj.utilcode.util.d;
import com.blankj.utilcode.util.d0;
import com.blankj.utilcode.util.e0;
import com.blankj.utilcode.util.i;
import com.blankj.utilcode.util.j;
import com.blankj.utilcode.util.r;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.kit.core.UniversalActivity;
import com.didichuxing.doraemonkit.kit.loginfo.LogExportDialog;
import com.didichuxing.doraemonkit.kit.loginfo.LogInfoManager;
import com.didichuxing.doraemonkit.util.FileUtil;
import com.didichuxing.doraemonkit.widget.dialog.DialogListener;
import com.didichuxing.doraemonkit.widget.dialog.DialogProvider;
import com.didichuxing.doraemonkit.widget.dialog.UniversalDialogFragment;
import com.didichuxing.doraemonkit.widget.titlebar.LogTitleBar;
import com.google.android.material.badge.BadgeDrawable;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LogInfoDokitView extends AbsDokitView implements LogInfoManager.OnLogCatchListener {
    private static final int MAX_LOG_LINE_NUM = 10000;
    private static final String TAG = "LogInfoFloatPage";
    private static final int UPDATE_CHECK_INTERVAL = 200;
    /* access modifiers changed from: private */
    public int counter = 0;
    private boolean isMaximize = true;
    /* access modifiers changed from: private */
    public boolean mAutoScrollToBottom = true;
    private boolean mIsLoaded;
    /* access modifiers changed from: private */
    public EditText mLogFilter;
    private TextView mLogHint;
    /* access modifiers changed from: private */
    public LogItemAdapter mLogItemAdapter;
    /* access modifiers changed from: private */
    public RecyclerView mLogRv;
    private RelativeLayout mLogRvWrap;
    private RadioGroup mRadioGroup;

    public void onCreate(Context context) {
        LogInfoManager.getInstance().registerListener(this);
    }

    public View onCreateView(Context context, FrameLayout view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_log_info, (ViewGroup) null);
    }

    public void onViewCreated(FrameLayout view) {
        initView();
    }

    public void initView() {
        this.mLogHint = (TextView) findViewById(R.id.log_hint);
        this.mLogRvWrap = (RelativeLayout) findViewById(R.id.log_page);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.log_list);
        this.mLogRv = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LogItemAdapter logItemAdapter = new LogItemAdapter(getContext());
        this.mLogItemAdapter = logItemAdapter;
        this.mLogRv.setAdapter(logItemAdapter);
        EditText editText = (EditText) findViewById(R.id.log_filter);
        this.mLogFilter = editText;
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                LogInfoDokitView.this.mLogItemAdapter.getFilter().filter(s);
            }
        });
        ((LogTitleBar) findViewById(R.id.dokit_title_bar)).setListener(new LogTitleBar.OnTitleBarClickListener() {
            public void onRightClick() {
                LogInfoManager.getInstance().stop();
                LogInfoManager.getInstance().removeListener();
                LogInfoDokitView.this.detach();
            }

            public void onLeftClick() {
                LogInfoDokitView.this.minimize();
            }
        });
        this.mLogHint.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                LogInfoDokitView.this.maximize();
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        this.mRadioGroup = radioGroup;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SensorsDataInstrumented
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checkedId = i;
                RadioGroup radioGroup2 = radioGroup;
                if (checkedId == R.id.verbose) {
                    LogInfoDokitView.this.mLogItemAdapter.setLogLevelLimit(2);
                } else if (checkedId == R.id.debug) {
                    LogInfoDokitView.this.mLogItemAdapter.setLogLevelLimit(3);
                } else if (checkedId == R.id.info) {
                    LogInfoDokitView.this.mLogItemAdapter.setLogLevelLimit(4);
                } else if (checkedId == R.id.warn) {
                    LogInfoDokitView.this.mLogItemAdapter.setLogLevelLimit(5);
                } else if (checkedId == R.id.error) {
                    LogInfoDokitView.this.mLogItemAdapter.setLogLevelLimit(6);
                }
                LogInfoDokitView.this.mLogItemAdapter.getFilter().filter(LogInfoDokitView.this.mLogFilter.getText());
                SensorsDataAutoTrackHelper.trackRadioGroup(radioGroup, i);
            }
        });
        this.mLogRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LogInfoDokitView logInfoDokitView = LogInfoDokitView.this;
                boolean z = true;
                if (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition() != recyclerView.getAdapter().getItemCount() - 1) {
                    z = false;
                }
                boolean unused = logInfoDokitView.mAutoScrollToBottom = z;
            }
        });
        this.mRadioGroup.check(R.id.verbose);
        ((Button) findViewById(R.id.btn_top)).setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                if (LogInfoDokitView.this.mLogItemAdapter == null || LogInfoDokitView.this.mLogItemAdapter.getItemCount() == 0) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                LogInfoDokitView.this.mLogRv.scrollToPosition(0);
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        ((Button) findViewById(R.id.btn_bottom)).setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                if (LogInfoDokitView.this.mLogItemAdapter == null || LogInfoDokitView.this.mLogItemAdapter.getItemCount() == 0) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                LogInfoDokitView.this.mLogRv.scrollToPosition(LogInfoDokitView.this.mLogItemAdapter.getItemCount() - 1);
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        ((Button) findViewById(R.id.btn_export)).setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                if (LogInfoDokitView.this.mLogItemAdapter == null || LogInfoDokitView.this.mLogItemAdapter.getItemCount() == 0) {
                    e0.n("暂无日志信息可以导出");
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                LogExportDialog logExportDialog = new LogExportDialog(new Object(), (DialogListener) null);
                logExportDialog.setOnButtonClickListener(new LogExportDialog.OnButtonClickListener() {
                    public void onSaveClick(LogExportDialog dialog) {
                        LogInfoDokitView.this.export2File(100);
                        dialog.dismiss();
                    }

                    public void onShareClick(LogExportDialog dialog) {
                        LogInfoDokitView.this.export2File(101);
                        dialog.dismiss();
                    }
                });
                LogInfoDokitView.this.showDialog(logExportDialog);
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        ((Button) findViewById(R.id.btn_clean)).setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                if (LogInfoDokitView.this.mLogItemAdapter == null || LogInfoDokitView.this.mLogItemAdapter.getItemCount() == 0) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                int unused = LogInfoDokitView.this.counter = 0;
                LogInfoDokitView.this.mLogItemAdapter.clearLog();
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
    }

    /* access modifiers changed from: private */
    public void showDialog(DialogProvider provider) {
        if (getActivity() != null && (getActivity() instanceof FragmentActivity)) {
            UniversalDialogFragment dialog = new UniversalDialogFragment();
            provider.setHost(dialog);
            dialog.setProvider(provider);
            provider.show(((FragmentActivity) getActivity()).getSupportFragmentManager());
        }
    }

    /* access modifiers changed from: private */
    public void export2File(final int operateType) {
        e0.n("日志保存中,请稍后...");
        final String logPath = r.d() + File.separator + d.a() + "_" + d0.d(new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss")) + ".log";
        final File logFile = new File(logPath);
        b0.f(new b0.f<Boolean>() {
            public Boolean doInBackground() {
                try {
                    for (LogLine logLine : new ArrayList<>(LogInfoDokitView.this.mLogItemAdapter.getTrueValues())) {
                        i.e(logFile, logLine.getProcessId() + "      " + logLine.getTimestamp() + "   " + logLine.getTag() + "   " + logLine.getLogLevelText() + "   " + logLine.getLogOutput() + "\n", true);
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            public void onSuccess(Boolean result) {
                if (result.booleanValue()) {
                    e0.n("文件保存在:" + logPath);
                    if (operateType == 101) {
                        FileUtil.systemShare(DoraemonKit.APPLICATION, logFile);
                    }
                }
            }

            public void onCancel() {
                if (logFile.exists()) {
                    j.d(logFile);
                }
                e0.n("日志保存失败");
            }

            public void onFail(Throwable t) {
                if (logFile.exists()) {
                    j.d(logFile);
                }
                e0.n("日志保存失败");
            }
        });
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        params.flags = 32;
        int i = DokitViewLayoutParams.MATCH_PARENT;
        params.width = i;
        params.height = i;
    }

    public void onLogCatch(List<LogLine> logLines) {
        if (this.mLogRv != null && this.mLogItemAdapter != null) {
            if (!this.mIsLoaded) {
                this.mIsLoaded = true;
                findViewById(R.id.ll_loading).setVisibility(8);
                this.mLogRv.setVisibility(0);
            }
            if (logLines.size() == 1) {
                this.mLogItemAdapter.addWithFilter(logLines.get(0), this.mLogFilter.getText(), true);
            } else {
                for (LogLine line : logLines) {
                    this.mLogItemAdapter.addWithFilter(line, this.mLogFilter.getText(), false);
                }
                this.mLogItemAdapter.notifyDataSetChanged();
            }
            if (logLines.size() > 0) {
                LogLine line2 = logLines.get(logLines.size() - 1);
                TextView textView = this.mLogHint;
                textView.setText(line2.getTag() + ":" + line2.getLogOutput());
            }
            int i = this.counter + 1;
            this.counter = i;
            if (i % 200 == 0 && this.mLogItemAdapter.getTrueValues().size() > 10000) {
                this.mLogItemAdapter.removeFirst(this.mLogItemAdapter.getTrueValues().size() - 10000);
            }
            if (this.mAutoScrollToBottom != 0) {
                scrollToBottom();
            }
        }
    }

    private void scrollToBottom() {
        this.mLogRv.scrollToPosition(this.mLogItemAdapter.getItemCount() - 1);
    }

    private int getSelectLogLevel() {
        int checkedId = this.mRadioGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.verbose) {
            return 2;
        }
        if (checkedId == R.id.debug) {
            return 3;
        }
        if (checkedId == R.id.info) {
            return 4;
        }
        if (checkedId == R.id.warn) {
            return 5;
        }
        if (checkedId == R.id.error) {
            return 6;
        }
        return 2;
    }

    public void minimize() {
        this.isMaximize = false;
        if (isNormalMode()) {
            this.mLogHint.setVisibility(0);
            this.mLogRvWrap.setVisibility(8);
            FrameLayout.LayoutParams layoutParams = getNormalLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = -1;
                layoutParams.height = -2;
                layoutParams.gravity = BadgeDrawable.TOP_START;
                getRootView().setLayoutParams(layoutParams);
                return;
            }
            return;
        }
        this.mLogHint.setVisibility(0);
        this.mLogRvWrap.setVisibility(8);
        WindowManager.LayoutParams layoutParams2 = getSystemLayoutParams();
        if (layoutParams2 != null) {
            layoutParams2.flags = 8;
            layoutParams2.width = -1;
            layoutParams2.height = -2;
            layoutParams2.gravity = BadgeDrawable.TOP_START;
            this.mWindowManager.updateViewLayout(getRootView(), layoutParams2);
        }
    }

    /* access modifiers changed from: private */
    public void maximize() {
        this.isMaximize = true;
        if (isNormalMode()) {
            this.mLogHint.setVisibility(8);
            this.mLogRvWrap.setVisibility(0);
            FrameLayout.LayoutParams layoutParams = getNormalLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = -1;
                layoutParams.height = -1;
                layoutParams.gravity = BadgeDrawable.TOP_START;
                getRootView().setLayoutParams(layoutParams);
                return;
            }
            return;
        }
        this.mLogHint.setVisibility(8);
        this.mLogRvWrap.setVisibility(0);
        WindowManager.LayoutParams layoutParams2 = getSystemLayoutParams();
        if (layoutParams2 != null) {
            layoutParams2.flags = 32;
            layoutParams2.width = -1;
            layoutParams2.height = -1;
            layoutParams2.gravity = BadgeDrawable.TOP_START;
            this.mWindowManager.updateViewLayout(getRootView(), layoutParams2);
        }
    }

    public boolean onBackPressed() {
        if (!this.isMaximize) {
            return false;
        }
        minimize();
        return true;
    }

    public void onResume() {
        super.onResume();
        if (getActivity() != null && !getActivity().getClass().getSimpleName().equals(UniversalActivity.class.getSimpleName())) {
            minimize();
        }
        LogInfoManager.getInstance().registerListener(this);
    }

    public boolean shouldDealBackKey() {
        return true;
    }

    public boolean canDrag() {
        return false;
    }
}
