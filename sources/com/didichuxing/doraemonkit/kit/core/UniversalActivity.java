package com.didichuxing.doraemonkit.kit.core;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.alignruler.AlignRulerSettingFragment;
import com.didichuxing.doraemonkit.kit.blockmonitor.BlockMonitorFragment;
import com.didichuxing.doraemonkit.kit.colorpick.ColorPickerSettingFragment;
import com.didichuxing.doraemonkit.kit.crash.CrashCaptureMainFragment;
import com.didichuxing.doraemonkit.kit.dataclean.DataCleanFragment;
import com.didichuxing.doraemonkit.kit.fileexplorer.FileExplorerFragment;
import com.didichuxing.doraemonkit.kit.filemanager.FileTransferFragment;
import com.didichuxing.doraemonkit.kit.filemanager.FileTransformDocFragment;
import com.didichuxing.doraemonkit.kit.gpsmock.GpsMockFragment;
import com.didichuxing.doraemonkit.kit.health.HealthFragment;
import com.didichuxing.doraemonkit.kit.largepicture.LargePictureFragment;
import com.didichuxing.doraemonkit.kit.loginfo.LogInfoSettingFragment;
import com.didichuxing.doraemonkit.kit.methodtrace.MethodCostFragment;
import com.didichuxing.doraemonkit.kit.network.ui.MockTemplatePreviewFragment;
import com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment;
import com.didichuxing.doraemonkit.kit.network.ui.NetWorkMonitorFragment;
import com.didichuxing.doraemonkit.kit.parameter.cpu.CpuMainPageFragment;
import com.didichuxing.doraemonkit.kit.parameter.frameInfo.FrameInfoFragment;
import com.didichuxing.doraemonkit.kit.parameter.ram.RamMainPageFragment;
import com.didichuxing.doraemonkit.kit.sysinfo.SysInfoFragment;
import com.didichuxing.doraemonkit.kit.timecounter.AppStartInfoFragment;
import com.didichuxing.doraemonkit.kit.timecounter.TimeCounterFragment;
import com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment;
import com.didichuxing.doraemonkit.kit.toolpanel.DokitSettingFragment;
import com.didichuxing.doraemonkit.kit.weaknetwork.WeakNetworkFragment;
import com.didichuxing.doraemonkit.kit.webdoor.WebDoorDefaultFragment;
import com.didichuxing.doraemonkit.kit.webdoor.WebDoorFragment;
import java.util.Arrays;
import java.util.HashMap;
import kotlin.jvm.internal.d0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.Nullable;

/* compiled from: UniversalActivity.kt */
public class UniversalActivity extends BaseActivity {
    private HashMap _$_findViewCache;

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        k.b(intent, "intent");
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        int index = bundle.getInt(BundleKey.FRAGMENT_INDEX);
        if (index == 0) {
            finish();
            return;
        }
        Class fragmentClass = null;
        switch (index) {
            case 1:
                fragmentClass = SysInfoFragment.class;
                break;
            case 2:
                fragmentClass = FileExplorerFragment.class;
                break;
            case 3:
                fragmentClass = LogInfoSettingFragment.class;
                break;
            case 4:
                fragmentClass = ColorPickerSettingFragment.class;
                break;
            case 5:
                fragmentClass = FrameInfoFragment.class;
                break;
            case 6:
                fragmentClass = GpsMockFragment.class;
                break;
            case 7:
                fragmentClass = AlignRulerSettingFragment.class;
                break;
            case 8:
                fragmentClass = BlockMonitorFragment.class;
                break;
            case 9:
                fragmentClass = WebDoorFragment.class;
                break;
            case 10:
                fragmentClass = DataCleanFragment.class;
                break;
            case 11:
                fragmentClass = CrashCaptureMainFragment.class;
                break;
            case 13:
                fragmentClass = NetWorkMonitorFragment.class;
                break;
            case 14:
                fragmentClass = CpuMainPageFragment.class;
                break;
            case 15:
                fragmentClass = RamMainPageFragment.class;
                break;
            case 17:
                fragmentClass = TimeCounterFragment.class;
                break;
            case 18:
                fragmentClass = WebDoorDefaultFragment.class;
                break;
            case 21:
                fragmentClass = WeakNetworkFragment.class;
                break;
            case 22:
                fragmentClass = LargePictureFragment.class;
                break;
            case 23:
                fragmentClass = MethodCostFragment.class;
                break;
            case 25:
                fragmentClass = NetWorkMockFragment.class;
                break;
            case 26:
                fragmentClass = MockTemplatePreviewFragment.class;
                break;
            case 27:
                fragmentClass = HealthFragment.class;
                break;
            case 28:
                fragmentClass = AppStartInfoFragment.class;
                break;
            case 29:
                fragmentClass = DokitSettingFragment.class;
                break;
            case 30:
                fragmentClass = DokitManagerFragment.class;
                break;
            case 31:
                fragmentClass = FileTransferFragment.class;
                break;
            case 32:
                fragmentClass = FileTransformDocFragment.class;
                break;
        }
        if (fragmentClass == null) {
            finish();
            d0 d0Var = d0.a;
            String format = String.format("fragment index %s not found", Arrays.copyOf(new Object[]{Integer.valueOf(index)}, 1));
            k.b(format, "java.lang.String.format(format, *args)");
            Toast.makeText(this, format, 0).show();
            return;
        }
        showContent(fragmentClass, bundle);
    }
}
