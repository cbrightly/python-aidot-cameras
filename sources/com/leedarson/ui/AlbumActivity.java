package com.leedarson.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.leedarson.R$anim;
import com.leedarson.R$color;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.base.utils.d;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.event.AlbumDelEndEvent;
import com.leedarson.event.AlbumNumEvent;
import com.leedarson.event.AlbumRefreshEvent;
import com.leedarson.event.AlbumSelectEvent;
import com.leedarson.serviceinterface.event.PushCloseIpcActivityEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.leedarson.utils.b;
import com.leedarson.view.TabLayoutMediator;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.HashMap;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.c;
import org.greenrobot.eventbus.l;
import pub.devrel.easypermissions.EasyPermissions;

public class AlbumActivity extends BaseFragmentActivity implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public TextView A4;
    private TextView B4;
    private TextView C4;
    /* access modifiers changed from: private */
    public boolean D4 = false;
    public String E4;
    private int F4 = Color.parseColor("#1F2429");
    /* access modifiers changed from: private */
    public int G4 = Color.parseColor("#8C9399");
    /* access modifiers changed from: private */
    public int H4 = 0;
    /* access modifiers changed from: private */
    public HashMap<Integer, MediaListFragment> I4 = new HashMap<>();
    private RelativeLayout a2;
    private TabLayout p2;
    private ViewPager2 p3;
    /* access modifiers changed from: private */
    public ArrayList<String> p4;
    private ImageView z4;

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Object[] objArr = {new Integer(requestCode), new Integer(resultCode), data};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10796, new Class[]{cls, cls, Intent.class}, Void.TYPE).isSupported) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 123 && resultCode == -1) {
                l0();
                c.c().l(new AlbumRefreshEvent());
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        String[] perms;
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 10797, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            int i = R$string.ipc_album;
            setTitle(PubUtils.getString(this, i));
            super.onCreate(savedInstanceState);
            if (Build.VERSION.SDK_INT >= 33) {
                perms = new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"};
            } else {
                perms = new String[]{"android.permission.READ_EXTERNAL_STORAGE"};
            }
            if (!EasyPermissions.a(this, perms)) {
                finish();
            }
            getWindow().clearFlags(1024);
            this.E4 = SharePreferenceUtils.getPrefString(this, "repositoryName", "");
            com.leedarson.base.logger.a.a(this, "currentReposityryName=" + this.E4);
            O();
            TextView textView = (TextView) findViewById(R$id.tv_title);
            this.B4 = textView;
            if (textView != null) {
                textView.setText(PubUtils.getString(this, i));
            }
            setTitle("IPC Album Video List");
            if (!c.c().j(this)) {
                c.c().p(this);
            }
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            this.a2 = (RelativeLayout) findViewById(R$id.album_layout_title);
            init();
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10798, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            PubUtils.setLanguage(this);
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10799, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            c.c().r(this);
        }
    }

    public int S() {
        return R$layout.activity_album;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10800, new Class[0], Void.TYPE).isSupported) {
            this.p2 = (TabLayout) findViewById(R$id.tablayout);
            this.p3 = (ViewPager2) findViewById(R$id.viewpager);
            ImageView imageView = (ImageView) findViewById(R$id.btn_back);
            this.z4 = imageView;
            imageView.setOnClickListener(this);
            TextView textView = (TextView) findViewById(R$id.btn_confirm);
            this.A4 = textView;
            textView.setText(PubUtils.getString(this, R$string.select));
            this.A4.setOnClickListener(this);
            this.A4.setTextColor(this.F4);
            TextView textView2 = (TextView) findViewById(R$id.tv_title_left);
            this.C4 = textView2;
            textView2.setText(PubUtils.getString(this, R$string.cancel));
            this.C4.setOnClickListener(this);
            this.C4.setTextColor(this.F4);
            ArrayList<String> arrayList = new ArrayList<>();
            this.p4 = arrayList;
            arrayList.add(PubUtils.getString(this, R$string.videos));
            this.p4.add(PubUtils.getString(this, R$string.photos));
            this.p3.setOffscreenPageLimit(2);
            this.p3.setAdapter(new ViewPagerFragmentStateAdapter(this));
            this.p2.setSelectedTabIndicatorColor(this.F4);
            new TabLayoutMediator(this.p2, this.p3, new a()).a();
            this.p3.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void onPageSelected(int position) {
                    int i = 0;
                    if (!PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 10810, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                        super.onPageSelected(position);
                        AlbumActivity.this.setTitle(position == 0 ? "IPC Album Video List" : "IPC Album Photo List");
                        int unused = AlbumActivity.this.H4 = position;
                        if (AlbumActivity.this.I4.get(Integer.valueOf(position)) != null) {
                            TextView g0 = AlbumActivity.this.A4;
                            if (!((MediaListFragment) AlbumActivity.this.I4.get(Integer.valueOf(position))).p1()) {
                                i = 8;
                            }
                            g0.setVisibility(i);
                        }
                    }
                }
            });
        }
    }

    public class a implements TabLayoutMediator.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            if (!PatchProxy.proxy(new Object[]{tab, new Integer(position)}, this, changeQuickRedirect, false, 10809, new Class[]{TabLayout.Tab.class, Integer.TYPE}, Void.TYPE).isSupported) {
                LDSTextView tabView = (LDSTextView) LayoutInflater.from(AlbumActivity.this).inflate(R$layout.album_tab_title_text_view, (ViewGroup) null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(d.b(AlbumActivity.this, 60.0f), -2);
                tabView.setGravity(17);
                tabView.setLayoutParams(layoutParams);
                ColorStateList colorStateList = new ColorStateList(new int[][]{new int[]{16842913}, new int[0]}, new int[]{AlbumActivity.this.getResources().getColor(R$color.text_primary_color), AlbumActivity.this.G4});
                tabView.setText((CharSequence) AlbumActivity.this.p4.get(position));
                tabView.setTextColor(colorStateList);
                tabView.setLDSTypeface(1);
                tab.setCustomView((View) tabView);
            }
        }
    }

    public void T() {
    }

    private void O() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10801, new Class[0], Void.TYPE).isSupported) {
            try {
                String spColor = SharePreferenceUtils.getPrefString(BaseApplication.b(), "themeColor", "#1F2429");
                com.leedarson.base.logger.a.c("configThemeColor", "themeColor=" + spColor);
                this.F4 = Color.parseColor(spColor);
            } catch (Exception ex) {
                this.F4 = Color.parseColor("#1F2429");
                ex.printStackTrace();
            }
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onDeleteEnd(AlbumDelEndEvent albumDelEndEvent) {
        if (!PatchProxy.proxy(new Object[]{albumDelEndEvent}, this, changeQuickRedirect, false, 10802, new Class[]{AlbumDelEndEvent.class}, Void.TYPE).isSupported) {
            l0();
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onGetMediaNum(AlbumNumEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 10803, new Class[]{AlbumNumEvent.class}, Void.TYPE).isSupported) {
            if (event != null && event.num > 0 && !this.D4) {
                this.A4.setVisibility(0);
            }
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 10804, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        if (b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int viewId = v.getId();
        if (viewId == R$id.btn_back) {
            finish();
        } else if (viewId == R$id.btn_confirm) {
            c.c().l(new AlbumSelectEvent());
            l0();
        } else if (viewId == R$id.tv_title_left) {
            c.c().l(new AlbumSelectEvent());
            l0();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void l0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10805, new Class[0], Void.TYPE).isSupported) {
            boolean z = !this.D4;
            this.D4 = z;
            if (z) {
                this.A4.setText(PubUtils.getString(this, R$string.cancel));
                return;
            }
            this.C4.setVisibility(8);
            this.A4.setVisibility(0);
            this.z4.setVisibility(0);
            this.A4.setText(PubUtils.getString(this, R$string.select));
        }
    }

    public class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {
        public static ChangeQuickRedirect changeQuickRedirect;

        public ViewPagerFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public int getItemCount() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10811, new Class[0], Integer.TYPE);
            return proxy.isSupported ? ((Integer) proxy.result).intValue() : AlbumActivity.this.p4.size();
        }

        @NonNull
        public Fragment createFragment(int position) {
            Object[] objArr = {new Integer(position)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 10812, new Class[]{Integer.TYPE}, Fragment.class);
            if (proxy.isSupported) {
                return (Fragment) proxy.result;
            }
            MediaListFragment tempFragment = MediaListFragment.y1(position, AlbumActivity.this.D4);
            AlbumActivity.this.I4.put(Integer.valueOf(position), tempFragment);
            tempFragment.setUserVisibleHint(true);
            return tempFragment;
        }
    }

    public void finish() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10806, new Class[0], Void.TYPE).isSupported) {
            super.finish();
            overridePendingTransition(R$anim.ipc_slide_in_left, R$anim.ipc_slide_out_right);
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onPushCloseEvent(PushCloseIpcActivityEvent pushCloseIpcActivityEvent) {
        if (!PatchProxy.proxy(new Object[]{pushCloseIpcActivityEvent}, this, changeQuickRedirect, false, 10807, new Class[]{PushCloseIpcActivityEvent.class}, Void.TYPE).isSupported) {
            finish();
        }
    }

    public void k0() {
        int i = 0;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10808, new Class[0], Void.TYPE).isSupported) {
            if (this.I4.get(Integer.valueOf(this.H4)) != null) {
                TextView textView = this.A4;
                if (!this.I4.get(Integer.valueOf(this.H4)).p1()) {
                    i = 8;
                }
                textView.setVisibility(i);
            }
        }
    }
}
