package com.leedarson.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.d;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.adapter.MediaQuickAdapter;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.views.g;
import com.leedarson.bean.LocalMedia;
import com.leedarson.event.AlbumDelEndEvent;
import com.leedarson.event.AlbumNumEvent;
import com.leedarson.event.AlbumRefreshEvent;
import com.leedarson.event.AlbumSelectEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.ui.decorations.SpaceItemAlbumDecoration;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.autotrack.aop.FragmentTrackHelper;
import java.util.ArrayList;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.l;

public class MediaListFragment extends Fragment implements h, View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private TextView A4;
    private TextView B4;
    /* access modifiers changed from: private */
    public ArrayList<LocalMedia> C4 = new ArrayList<>();
    private float D4 = 0.5f;
    private LinearLayout E4;
    private View F4;
    private boolean G4;
    private boolean H4;
    int I4 = 0;
    private io.reactivex.disposables.b J4 = null;
    io.reactivex.processors.b<Boolean> K4 = io.reactivex.processors.b.Y();
    private ArrayList<LocalMedia> L4 = new ArrayList<>();
    private View a1;
    private TextView a2;
    /* access modifiers changed from: private */
    public int c = -1;
    private g d;
    public int f = Color.parseColor("#1F2429");
    private ImageView p0;
    private LinearLayout p1;
    private String p2;
    public boolean p3 = false;
    private Dialog p4 = null;
    private RecyclerView q;
    private MediaQuickAdapter x;
    private g y;
    private ImageView z;
    private TextView z4;

    @SensorsDataInstrumented
    public void onHiddenChanged(boolean z2) {
        super.onHiddenChanged(z2);
        FragmentTrackHelper.trackOnHiddenChanged(this, z2);
    }

    @SensorsDataInstrumented
    public void onPause() {
        super.onPause();
        FragmentTrackHelper.trackFragmentPause(this);
    }

    static /* synthetic */ void m1(MediaListFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11185, new Class[]{MediaListFragment.class}, Void.TYPE).isSupported) {
            x0.u1();
        }
    }

    static /* synthetic */ void o1(MediaListFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11186, new Class[]{MediaListFragment.class}, Void.TYPE).isSupported) {
            x0.z1();
        }
    }

    public static MediaListFragment y1(int position, boolean isSelectMode) {
        Object[] objArr = {new Integer(position), new Byte(isSelectMode ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 11159, new Class[]{Integer.TYPE, Boolean.TYPE}, MediaListFragment.class);
        if (proxy.isSupported) {
            return (MediaListFragment) proxy.result;
        }
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putBoolean("mode", isSelectMode);
        MediaListFragment fragment = new MediaListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private g r1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11160, new Class[0], g.class);
        if (proxy.isSupported) {
            return (g) proxy.result;
        }
        if (this.d == null) {
            this.d = new g(this, this);
        }
        return this.d;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 11161, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                this.c = getArguments().getInt("position");
                this.p3 = getArguments().getBoolean("mode");
            }
            org.greenrobot.eventbus.c.c().p(this);
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11162, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            r1().B();
            g gVar = this.y;
            if (gVar != null) {
                gVar.dismiss();
            }
            Dialog dialog = this.p4;
            if (dialog != null) {
                dialog.dismiss();
            }
            org.greenrobot.eventbus.c.c().r(this);
            io.reactivex.disposables.b bVar = this.J4;
            if (bVar != null && !bVar.isDisposed()) {
                this.J4.dispose();
            }
        }
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{inflater, container, bundle}, this, changeQuickRedirect2, false, 11163, new Class[]{LayoutInflater.class, ViewGroup.class, Bundle.class}, View.class);
        if (proxy.isSupported) {
            return (View) proxy.result;
        }
        this.p2 = SharePreferenceUtils.getPrefString(getContext(), "repositoryName", "");
        return inflater.inflate(R$layout.fragment_medialist, container, false);
    }

    @SensorsDataInstrumented
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (PatchProxy.proxy(new Object[]{view, savedInstanceState}, this, changeQuickRedirect, false, 11164, new Class[]{View.class, Bundle.class}, Void.TYPE).isSupported) {
            FragmentTrackHelper.onFragmentViewCreated(this, view, savedInstanceState);
            return;
        }
        View view2 = view;
        super.onViewCreated(view2, savedInstanceState);
        this.F4 = view2;
        this.G4 = true;
        q1();
        t1();
        FragmentTrackHelper.onFragmentViewCreated(this, view, savedInstanceState);
    }

    private void t1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11165, new Class[0], Void.TYPE).isSupported) {
            if (this.G4 && this.H4) {
                this.q = (RecyclerView) this.F4.findViewById(R$id.recycl);
                this.y = new g(getContext());
                this.z = (ImageView) this.F4.findViewById(R$id.checkbox);
                this.a1 = this.F4.findViewById(R$id.del_line);
                this.p1 = (LinearLayout) this.F4.findViewById(R$id.del_layout);
                TextView textView = (TextView) this.F4.findViewById(R$id.txt_all);
                this.a2 = textView;
                textView.setTextColor(this.f);
                this.a2.setOnClickListener(this);
                Dialog dialog = new Dialog(getContext(), R$style.Theme_dialog);
                this.p4 = dialog;
                dialog.setContentView(R$layout.del_dialog_layout);
                this.p4.setCanceledOnTouchOutside(false);
                this.z4 = (TextView) this.p4.findViewById(R$id.tip_content_tv);
                this.A4 = (TextView) this.p4.findViewById(R$id.left_btn_tv);
                this.B4 = (TextView) this.p4.findViewById(R$id.right_btn_tv);
                this.A4.setText(PubUtils.getString(getContext(), R$string.cancel));
                this.B4.setText(PubUtils.getString(getContext(), R$string.confirm));
                this.A4.setTextColor(this.f);
                this.B4.setTextColor(this.f);
                this.A4.setOnClickListener(this);
                this.B4.setOnClickListener(this);
                ImageView imageView = (ImageView) this.F4.findViewById(R$id.btn_del);
                this.p0 = imageView;
                imageView.setOnClickListener(this);
                this.p0.setAlpha(this.D4);
                this.E4 = (LinearLayout) this.F4.findViewById(R$id.ll_loading);
                this.p2 = SharePreferenceUtils.getPrefString(getContext(), "repositoryName", "");
                s1();
                this.q.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                this.x = new MediaQuickAdapter(R$layout.item_date, R$layout.item_page, this.C4);
                this.q.addItemDecoration(new SpaceItemAlbumDecoration(com.leedarson.view.a.a(getActivity(), 6.0f)));
                this.q.setAdapter(this.x);
                B1();
                this.x.setOnItemClickListener(new a());
                View view = this.F4;
                int i = R$id.rlCheckAll;
                if (view.findViewById(i) != null) {
                    this.F4.findViewById(i).setOnClickListener(new b());
                }
                this.z.setOnClickListener(new c());
                switch (this.c) {
                    case 0:
                        this.x.C(true);
                        break;
                    case 1:
                        this.x.C(false);
                        break;
                }
                r1().x(this.c);
            }
        }
    }

    public class a implements d {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void a(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter, @NonNull View view, int position) {
            Object[] objArr = {baseQuickAdapter, view, new Integer(position)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11187, new Class[]{BaseQuickAdapter.class, View.class, Integer.TYPE}, Void.TYPE).isSupported) {
                LocalMedia media = (LocalMedia) MediaListFragment.this.C4.get(position);
                if (!media.isHeader()) {
                    MediaListFragment mediaListFragment = MediaListFragment.this;
                    if (mediaListFragment.p3) {
                        ((LocalMedia) mediaListFragment.C4.get(position)).setChecked(!((LocalMedia) MediaListFragment.this.C4.get(position)).isChecked());
                        ImageView imageIcon = (ImageView) view.findViewById(R$id.img_check);
                        if (imageIcon != null) {
                            imageIcon.setSelected(((LocalMedia) MediaListFragment.this.C4.get(position)).isChecked());
                            imageIcon.setVisibility(0);
                        }
                        MediaListFragment.m1(MediaListFragment.this);
                    } else if (!com.leedarson.utils.b.a(view, 500)) {
                        if (MediaListFragment.this.c == 0) {
                            Intent intent = new Intent(MediaListFragment.this.getActivity(), AlbumVideoPlayActivity.class);
                            intent.putExtra("video_path", media.getPath());
                            MediaListFragment.this.getActivity().startActivity(intent);
                        } else if (MediaListFragment.this.c == 1) {
                            ArrayList<LocalMedia> phonts = new ArrayList<>();
                            ArrayList<String> urls = new ArrayList<>();
                            int cur_position = 0;
                            for (int i = 0; i < MediaListFragment.this.C4.size(); i++) {
                                if (!((LocalMedia) MediaListFragment.this.C4.get(i)).isHeader()) {
                                    phonts.add((LocalMedia) MediaListFragment.this.C4.get(i));
                                    urls.add(((LocalMedia) MediaListFragment.this.C4.get(i)).getPath());
                                }
                            }
                            for (int j = 0; j < urls.size(); j++) {
                                if (urls.get(j).equals(media.getPath())) {
                                    cur_position = j;
                                }
                            }
                            Intent intent2 = new Intent(MediaListFragment.this.getActivity(), AlbumPhotoActivity.class);
                            intent2.putExtra("position", cur_position);
                            intent2.putExtra("datas", urls);
                            MediaListFragment.this.getActivity().startActivity(intent2);
                        }
                    }
                }
            }
        }
    }

    public class b implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11188, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            MediaListFragment.o1(MediaListFragment.this);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class c implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11189, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            MediaListFragment.o1(MediaListFragment.this);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    @SensorsDataInstrumented
    public void setUserVisibleHint(boolean z2) {
        if (PatchProxy.proxy(new Object[]{new Byte(z2 ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 11166, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            FragmentTrackHelper.trackFragmentSetUserVisibleHint(this, z2);
            return;
        }
        boolean isVisibleToUser = z2;
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            this.H4 = true;
            t1();
        } else {
            this.H4 = false;
        }
        FragmentTrackHelper.trackFragmentSetUserVisibleHint(this, z2);
    }

    private void z1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11167, new Class[0], Void.TYPE).isSupported) {
            ImageView imageView = this.z;
            imageView.setSelected(!imageView.isSelected());
            for (int i = 0; i < this.C4.size(); i++) {
                this.C4.get(i).setChecked(this.z.isSelected());
            }
            u1();
            this.x.notifyDataSetChanged();
        }
    }

    @SensorsDataInstrumented
    public void onResume() {
        if (PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11168, new Class[0], Void.TYPE).isSupported) {
            FragmentTrackHelper.trackFragmentResume(this);
            return;
        }
        super.onResume();
        if (getActivity() != null) {
            PubUtils.setLanguage(getActivity());
        }
        if (this.x != null) {
            org.greenrobot.eventbus.c.c().l(new AlbumNumEvent(this.x.getData().size()));
        }
        FragmentTrackHelper.trackFragmentResume(this);
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onRefresh(AlbumRefreshEvent albumRefreshEvent) {
        if (!PatchProxy.proxy(new Object[]{albumRefreshEvent}, this, changeQuickRedirect, false, 11169, new Class[]{AlbumRefreshEvent.class}, Void.TYPE).isSupported) {
            this.p3 = false;
            B1();
            r1().x(this.c);
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onSelectToggle(AlbumSelectEvent albumSelectEvent) {
        if (!PatchProxy.proxy(new Object[]{albumSelectEvent}, this, changeQuickRedirect, false, 11170, new Class[]{AlbumSelectEvent.class}, Void.TYPE).isSupported) {
            this.p3 = true ^ this.p3;
            B1();
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onDeleteEnd(AlbumDelEndEvent albumDelEndEvent) {
        if (!PatchProxy.proxy(new Object[]{albumDelEndEvent}, this, changeQuickRedirect, false, 11171, new Class[]{AlbumDelEndEvent.class}, Void.TYPE).isSupported) {
            this.p3 = false;
            B1();
        }
    }

    private void u1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11172, new Class[0], Void.TYPE).isSupported) {
            Log.e("PageFragment", "isAllCheck: ");
            boolean isAllCheck = true;
            int i = 0;
            while (true) {
                if (i < this.C4.size()) {
                    if (!this.C4.get(i).isHeader() && !this.C4.get(i).isChecked()) {
                        isAllCheck = false;
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            if (isAllCheck) {
                this.z.setSelected(true);
            } else {
                this.z.setSelected(false);
            }
            this.I4 = 0;
            for (int i2 = 0; i2 < this.C4.size(); i2++) {
                if (!this.C4.get(i2).isHeader() && this.C4.get(i2).isChecked()) {
                    this.I4++;
                }
            }
            if (this.I4 > 0) {
                this.a2.setText(String.format(PubUtils.getString(getContext(), R$string.selected), new Object[]{Integer.valueOf(this.I4)}));
                this.a2.setTextColor(this.f);
                this.p0.setAlpha(1.0f);
                return;
            }
            this.a2.setText(String.format(PubUtils.getString(getContext(), R$string.selected), new Object[]{Integer.valueOf(this.I4)}));
            this.a2.setTextColor(this.f);
            this.p0.setAlpha(this.D4);
        }
    }

    private void B1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11173, new Class[0], Void.TYPE).isSupported) {
            this.x.B(this.p3);
            if (this.p3) {
                this.p1.setVisibility(0);
                this.a1.setVisibility(0);
                this.p0.setAlpha(this.D4);
            } else {
                this.p1.setVisibility(8);
                this.a1.setVisibility(8);
                this.I4 = 0;
                this.a2.setText(String.format(PubUtils.getString(getContext(), R$string.selected), new Object[]{Integer.valueOf(this.I4)}));
                this.a2.setTextColor(this.f);
                this.p0.setAlpha(this.D4);
            }
            if (!this.p3) {
                for (int i = 0; i < this.C4.size(); i++) {
                    this.C4.get(i).setChecked(false);
                }
                this.z.setSelected(false);
            }
        }
    }

    private void s1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11175, new Class[0], Void.TYPE).isSupported) {
            io.reactivex.disposables.b bVar = this.J4;
            if (bVar == null || bVar.isDisposed()) {
                this.J4 = this.K4.c(com.leedarson.base.http.observer.l.c()).I(new a(this), b.c);
            } else {
                this.J4.dispose();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: v1 */
    public /* synthetic */ void w1(Boolean aBoolean) {
        int i = 0;
        if (!PatchProxy.proxy(new Object[]{aBoolean}, this, changeQuickRedirect, false, 11184, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            LinearLayout linearLayout = this.E4;
            if (!aBoolean.booleanValue()) {
                i = 8;
            }
            linearLayout.setVisibility(i);
        }
    }

    static /* synthetic */ void x1(Throwable throwable) {
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11176, new Class[0], Void.TYPE).isSupported) {
            this.K4.onNext(true);
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11177, new Class[0], Void.TYPE).isSupported) {
            this.K4.onNext(false);
        }
    }

    public void E0(ArrayList<LocalMedia> mediaList) {
        if (!PatchProxy.proxy(new Object[]{mediaList}, this, changeQuickRedirect, false, 11178, new Class[]{ArrayList.class}, Void.TYPE).isSupported) {
            A1(mediaList);
        }
    }

    public void j1(ArrayList<LocalMedia> mediaList) {
        if (!PatchProxy.proxy(new Object[]{mediaList}, this, changeQuickRedirect, false, 11179, new Class[]{ArrayList.class}, Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().l(new AlbumDelEndEvent());
            A1(mediaList);
        }
    }

    private void A1(ArrayList<LocalMedia> mediaList) {
        int i = 0;
        if (!PatchProxy.proxy(new Object[]{mediaList}, this, changeQuickRedirect, false, 11180, new Class[]{ArrayList.class}, Void.TYPE).isSupported) {
            this.C4.clear();
            if (mediaList == null || mediaList.size() == 0) {
                this.x.notifyDataSetChanged();
                this.x.setEmptyView(R$layout.album_empty_layout);
            } else {
                this.C4.addAll(mediaList);
                this.x.notifyDataSetChanged();
            }
            org.greenrobot.eventbus.c c2 = org.greenrobot.eventbus.c.c();
            if (mediaList != null) {
                i = mediaList.size();
            }
            c2.l(new AlbumNumEvent(i));
            if (getActivity() != null) {
                ((AlbumActivity) getActivity()).k0();
            }
        }
    }

    @SensorsDataInstrumented
    public void onClick(View v) {
        if (PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 11181, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(v);
            return;
        }
        int viewId = v.getId();
        if (viewId == R$id.btn_del || viewId == R$id.img_del) {
            this.L4.clear();
            for (int i = 0; i < this.x.getData().size(); i++) {
                if (!((LocalMedia) this.x.getData().get(i)).isHeader() && ((LocalMedia) this.x.getData().get(i)).isChecked()) {
                    this.L4.add((LocalMedia) this.x.getData().get(i));
                }
            }
            if (this.L4.size() > 0) {
                this.z4.setText(PubUtils.getString(getContext(), R$string.delete_event_tip));
                Dialog dialog = this.p4;
                if (dialog != null) {
                    dialog.show();
                }
            }
        } else if (viewId == R$id.left_btn_tv) {
            Dialog dialog2 = this.p4;
            if (dialog2 != null) {
                dialog2.dismiss();
            }
        } else if (viewId == R$id.right_btn_tv) {
            Dialog dialog3 = this.p4;
            if (dialog3 != null) {
                dialog3.dismiss();
            }
            r1().v(this.c, this.L4);
        } else if (viewId == R$id.txt_all) {
            boolean isSelectedAll = !this.z.isSelected();
            this.z.setSelected(isSelectedAll);
            int totalSelectedCount = 0;
            for (int i2 = 0; i2 < this.C4.size(); i2++) {
                this.C4.get(i2).setChecked(this.z.isSelected());
                totalSelectedCount++;
            }
            if (!isSelectedAll) {
                this.I4 = 0;
                this.a2.setText(String.format(PubUtils.getString(getContext(), R$string.selected), new Object[]{Integer.valueOf(this.I4)}));
                this.a2.setTextColor(this.f);
                this.p0.setAlpha(this.D4);
            } else {
                u1();
            }
            this.x.notifyDataSetChanged();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(v);
    }

    private void q1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11182, new Class[0], Void.TYPE).isSupported) {
            try {
                String spColor = SharePreferenceUtils.getPrefString(BaseApplication.b(), "themeColor", "#1F2429");
                com.leedarson.base.logger.a.c("configThemeColor", "themeColor=" + spColor);
                this.f = Color.parseColor(spColor);
            } catch (Exception ex) {
                this.f = Color.parseColor("#1F2429");
                ex.printStackTrace();
            }
        }
    }

    public boolean p1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11183, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return this.C4.size() > 0;
    }
}
