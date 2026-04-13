package com.leedarson.base.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.leedarson.base.utils.d;
import com.leedarson.base.views.LDSPermissionGuide;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.module_base.R$drawable;
import com.leedarson.module_base.R$id;
import com.leedarson.module_base.R$layout;
import com.leedarson.module_base.R$style;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.autotrack.aop.FragmentTrackHelper;

public class LDSPermissitonGuideFragment extends DialogFragment implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private View a1;
    private a a2;
    private LDSTextView c;
    private LDSTextView d;
    private LDSTextView f;
    private View p0;
    private View p1;
    private LDSPermissionGuide.GuideParam p2;
    private LDSTextView q;
    private LDSTextView x;
    private ImageView y;
    private View z;

    public interface a {
        void onActionClick(LDSPermissitonGuideFragment lDSPermissitonGuideFragment);

        void onCloseClick();
    }

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

    @SensorsDataInstrumented
    public void onResume() {
        super.onResume();
        FragmentTrackHelper.trackFragmentResume(this);
    }

    @SensorsDataInstrumented
    public void setUserVisibleHint(boolean z2) {
        super.setUserVisibleHint(z2);
        FragmentTrackHelper.trackFragmentSetUserVisibleHint(this, z2);
    }

    private View l1(Context context, LayoutInflater inflater) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, inflater}, this, changeQuickRedirect, false, 758, new Class[]{Context.class, LayoutInflater.class}, View.class);
        if (proxy.isSupported) {
            return (View) proxy.result;
        }
        View rootView = inflater.inflate(R$layout.view_lds_per_guide, (ViewGroup) null, false);
        this.p1 = rootView.findViewById(R$id.landscape_placeholder);
        this.a1 = rootView.findViewById(R$id.ll_guide_body);
        this.c = (LDSTextView) rootView.findViewById(R$id.tv_guide_title);
        this.d = (LDSTextView) rootView.findViewById(R$id.tv_guide_subtitle);
        this.f = (LDSTextView) rootView.findViewById(R$id.tv_guide_desc1);
        this.q = (LDSTextView) rootView.findViewById(R$id.tv_guide_desc2);
        this.y = (ImageView) rootView.findViewById(R$id.iv_guide_icon);
        this.z = rootView.findViewById(R$id.tv_guide_close);
        this.p0 = rootView.findViewById(R$id.ll_guide_go_setting);
        LDSTextView lDSTextView = (LDSTextView) rootView.findViewById(R$id.tv_guide_action);
        this.x = lDSTextView;
        lDSTextView.getPaint().setFlags(8);
        this.x.getPaint().setAntiAlias(true);
        this.p0.setOnClickListener(this);
        this.z.setOnClickListener(this);
        return rootView;
    }

    public void setOnItemClickListener(a onItemClickListener) {
        this.a2 = onItemClickListener;
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        a aVar;
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 759, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        if (v == this.z) {
            dismiss();
            a aVar2 = this.a2;
            if (aVar2 != null) {
                aVar2.onCloseClick();
            }
        } else if (v == this.p0 && (aVar = this.a2) != null) {
            aVar.onActionClick(this);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{inflater, viewGroup, bundle}, this, changeQuickRedirect2, false, 760, new Class[]{LayoutInflater.class, ViewGroup.class, Bundle.class}, View.class);
        if (proxy.isSupported) {
            return (View) proxy.result;
        }
        Bundle bundle2 = getArguments();
        if (bundle2 != null) {
            this.p2 = (LDSPermissionGuide.GuideParam) bundle2.getParcelable("params");
        }
        return l1(inflater.getContext(), inflater);
    }

    @SensorsDataInstrumented
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Class[] clsArr = {View.class, Bundle.class};
        if (PatchProxy.proxy(new Object[]{view, savedInstanceState}, this, changeQuickRedirect, false, 761, clsArr, Void.TYPE).isSupported) {
            FragmentTrackHelper.onFragmentViewCreated(this, view, savedInstanceState);
            return;
        }
        View view2 = view;
        super.onViewCreated(view2, savedInstanceState);
        p1(this.p2);
        o1();
        m1(view2);
        FragmentTrackHelper.onFragmentViewCreated(this, view, savedInstanceState);
    }

    private void m1(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 762, new Class[]{View.class}, Void.TYPE).isSupported) {
            getDialog().setCanceledOnTouchOutside(false);
            Window window = getDialog().getWindow();
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            if (getResources().getConfiguration().orientation == 1) {
                lp.width = -1;
                lp.height = (d.d(getContext()) / 4) * 3;
                this.a1.setBackgroundColor(-1);
                this.p1.setVisibility(8);
            } else {
                lp.width = d.d(getContext());
                lp.height = d.d(getContext());
                this.a1.setBackgroundResource(R$drawable.bg_per_guide_window_landscape);
                this.p1.setVisibility(0);
            }
            lp.gravity = 80;
            lp.windowAnimations = R$style.BottomDialogAnimation;
            LDSPermissionGuide.GuideParam guideParam = this.p2;
            if (guideParam != null) {
                lp.dimAmount = guideParam.z;
            }
            window.setAttributes(lp);
            window.setBackgroundDrawable(new ColorDrawable());
        }
    }

    public void p1(LDSPermissionGuide.GuideParam param) {
        if (!PatchProxy.proxy(new Object[]{param}, this, changeQuickRedirect, false, 763, new Class[]{LDSPermissionGuide.GuideParam.class}, Void.TYPE).isSupported) {
            this.p2 = param;
            if (param != null) {
                this.c.setText(param.c);
                this.d.setText(param.d);
                this.f.setText(param.f);
                this.q.setText(param.q);
                this.x.setText(param.x);
                int i = param.y;
                if (i != 0) {
                    this.y.setImageResource(i);
                }
            }
            getView().requestLayout();
        }
    }

    private void o1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 764, new Class[0], Void.TYPE).isSupported) {
            String repositoryName = SharePreferenceUtils.getPrefString(getActivity(), "repositoryName", "");
            if ("M071-AiDot".equals(repositoryName)) {
                this.x.setTextColor(Color.parseColor("#FC8E35"));
            } else if ("M071-Linkind".equals(repositoryName)) {
                this.x.setTextColor(Color.parseColor("#00E49C"));
            }
        }
    }

    public static LDSPermissitonGuideFragment n1(LDSPermissionGuide.GuideParam param) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{param}, (Object) null, changeQuickRedirect, true, 765, new Class[]{LDSPermissionGuide.GuideParam.class}, LDSPermissitonGuideFragment.class);
        if (proxy.isSupported) {
            return (LDSPermissitonGuideFragment) proxy.result;
        }
        LDSPermissitonGuideFragment guideView = new LDSPermissitonGuideFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("params", param);
        guideView.setArguments(bundle);
        return guideView;
    }
}
