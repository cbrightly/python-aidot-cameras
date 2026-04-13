package smarthome.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bumptech.glide.b;
import com.leedarson.base.R$drawable;
import com.leedarson.base.R$id;
import com.leedarson.base.R$layout;
import com.leedarson.base.ui.BaseFragment;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import timber.log.a;

public class GuideFragment extends BaseFragment {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public ImageView a1;
    /* access modifiers changed from: private */
    public int[] a2 = {R$drawable.guide_info1, R$drawable.guide_info2, R$drawable.guide_info3, R$drawable.guide_info4};
    /* access modifiers changed from: private */
    public RelativeLayout p1;

    public static GuideFragment L1(int sectionNumber) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(sectionNumber)}, (Object) null, changeQuickRedirect, true, 14147, new Class[]{Integer.TYPE}, GuideFragment.class);
        if (proxy.isSupported) {
            return (GuideFragment) proxy.result;
        }
        a.b g = timber.log.a.g("guide_number");
        g.c("newInstance:sectionNumber " + sectionNumber, new Object[0]);
        GuideFragment fragment = new GuideFragment();
        Bundle args = new Bundle();
        args.putInt("guide_number", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{inflater, container, bundle}, this, changeQuickRedirect2, false, 14148, new Class[]{LayoutInflater.class, ViewGroup.class, Bundle.class}, View.class);
        if (proxy.isSupported) {
            return (View) proxy.result;
        }
        View view = inflater.inflate(R$layout.fragment_page, container, false);
        a.b g = timber.log.a.g("GuideFragment");
        g.h("onCreateView: " + view.getWidth() + "===" + view.getHeight(), new Object[0]);
        this.p1 = (RelativeLayout) view.findViewById(R$id.page_layout);
        this.a1 = (ImageView) view.findViewById(R$id.fragment_pager_img);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new a(view));
        return view;
    }

    public class a implements ViewTreeObserver.OnGlobalLayoutListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ View c;

        a(View view) {
            this.c = view;
        }

        public void onGlobalLayout() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14149, new Class[0], Void.TYPE).isSupported) {
                this.c.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int bgHeight = this.c.getHeight();
                int bgWidth = (int) ((((double) bgHeight) / 1058.0d) * 496.0d);
                int imgW = bgWidth - 30;
                ViewGroup.LayoutParams layoutParams = GuideFragment.this.p1.getLayoutParams();
                layoutParams.width = bgWidth;
                layoutParams.height = bgHeight;
                GuideFragment.this.p1.setLayoutParams(layoutParams);
                ViewGroup.LayoutParams viewLayoutParams = GuideFragment.this.a1.getLayoutParams();
                viewLayoutParams.width = imgW;
                viewLayoutParams.height = (int) (((double) imgW) * 1.7767857142857142d);
                GuideFragment.this.a1.setLayoutParams(viewLayoutParams);
                try {
                    b.u(GuideFragment.this.getActivity()).p(Integer.valueOf(GuideFragment.this.a2[GuideFragment.this.getArguments().getInt("guide_number") - 1])).H0(GuideFragment.this.a1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int r1() {
        return 0;
    }

    public void t1(View view) {
    }
}
