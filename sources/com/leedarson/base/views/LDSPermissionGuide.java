package com.leedarson.base.views;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.leedarson.base.utils.w;
import com.leedarson.base.views.LDSPermissitonGuideFragment;
import com.leedarson.module_base.R$drawable;
import com.leedarson.sdk.language.R$string;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.functions.e;
import pub.devrel.easypermissions.EasyPermissions;

public class LDSPermissionGuide {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static class GuideParam implements Parcelable {
        public static final Parcelable.Creator<GuideParam> CREATOR = new a();
        public static ChangeQuickRedirect changeQuickRedirect;
        public String c = "";
        public String d = "";
        public String f = "";
        public String q = "";
        public String x = "Go settings >";
        public int y = 0;
        public float z = 0.4f;

        public GuideParam() {
        }

        public GuideParam(Parcel in) {
            this.c = in.readString();
            this.d = in.readString();
            this.f = in.readString();
            this.q = in.readString();
            this.x = in.readString();
            this.y = in.readInt();
        }

        public class a implements Parcelable.Creator<GuideParam> {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 757, new Class[]{Parcel.class}, Object.class);
                return proxy.isSupported ? proxy.result : a(parcel);
            }

            public /* bridge */ /* synthetic */ Object[] newArray(int i) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 756, new Class[]{Integer.TYPE}, Object[].class);
                return proxy.isSupported ? (Object[]) proxy.result : b(i);
            }

            public GuideParam a(Parcel in) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 755, new Class[]{Parcel.class}, GuideParam.class);
                if (proxy.isSupported) {
                    return (GuideParam) proxy.result;
                }
                return new GuideParam(in);
            }

            public GuideParam[] b(int size) {
                return new GuideParam[size];
            }
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int i) {
            if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 754, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
                dest.writeString(this.c);
                dest.writeString(this.d);
                dest.writeString(this.f);
                dest.writeString(this.q);
                dest.writeString(this.x);
                dest.writeInt(this.y);
            }
        }
    }

    public static class CameraGuideParam extends GuideParam {
        public CameraGuideParam(Context context) {
            this.x = PubUtils.getString(context, R$string.camera_permission_go_setting);
            this.c = PubUtils.getString(context, R$string.camera_permission);
            this.d = PubUtils.getString(context, R$string.camera_guide_subtitle);
            this.f = PubUtils.getString(context, R$string.camera_guide_desc1);
            this.q = PubUtils.getString(context, R$string.camera_guide_desc2);
            this.y = R$drawable.ic_per_camera;
            this.z = 0.1f;
        }
    }

    public static class MicGuideParam extends GuideParam {
        public MicGuideParam(Context context) {
            this.x = PubUtils.getString(context, R$string.mic_permission_go_setting);
            this.c = PubUtils.getString(context, R$string.mic_permission);
            this.d = PubUtils.getString(context, R$string.mic_guide_subtitle);
            this.f = PubUtils.getString(context, R$string.mic_guide_desc1);
            this.q = PubUtils.getString(context, R$string.mic_guide_desc2);
            this.y = R$drawable.ic_per_mic;
        }
    }

    public static class AlbumGuideParam extends GuideParam {
        public AlbumGuideParam(Context context) {
            this.x = " " + PubUtils.getString(context, R$string.album_permission_go_setting) + " ";
            this.c = PubUtils.getString(context, R$string.album_permission);
            this.d = PubUtils.getString(context, R$string.album_guide_subtitle);
            this.f = PubUtils.getString(context, R$string.album_guide_desc1);
            this.q = PubUtils.getString(context, R$string.album_guide_desc2);
            this.y = R$drawable.ic_per_album;
        }
    }

    public static LDSPermissitonGuideFragment d(FragmentActivity activity, GuideParam param, LDSPermissitonGuideFragment.a listener) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{activity, param, listener}, (Object) null, changeQuickRedirect2, true, 746, new Class[]{FragmentActivity.class, GuideParam.class, LDSPermissitonGuideFragment.a.class}, LDSPermissitonGuideFragment.class);
        if (proxy.isSupported) {
            return (LDSPermissitonGuideFragment) proxy.result;
        }
        LDSPermissitonGuideFragment guideFragment = LDSPermissitonGuideFragment.n1(param);
        guideFragment.setOnItemClickListener(listener);
        FragmentManager fm = activity.getSupportFragmentManager();
        guideFragment.setCancelable(false);
        guideFragment.show(fm, param.c);
        return guideFragment;
    }

    public static void c(LDSPermissitonGuideFragment fragment) {
        if (!PatchProxy.proxy(new Object[]{fragment}, (Object) null, changeQuickRedirect, true, 747, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported && fragment != null && fragment.isAdded()) {
            fragment.dismissAllowingStateLoss();
        }
    }

    public static void b(LDSPermissitonGuideFragment fragment, Activity activity, String[] per, String denyKey, Runnable runnable) {
        Class[] clsArr = {LDSPermissitonGuideFragment.class, Activity.class, String[].class, String.class, Runnable.class};
        if (!PatchProxy.proxy(new Object[]{fragment, activity, per, denyKey, runnable}, (Object) null, changeQuickRedirect, true, 748, clsArr, Void.TYPE).isSupported) {
            Runnable runnable2 = runnable;
            if (EasyPermissions.a(activity, per)) {
                runnable2.run();
                return;
            }
            boolean canRequest = true;
            if (SharePreferenceUtils.getPrefBoolean(activity, denyKey, false)) {
                if (EasyPermissions.h(activity, per[0]) || EasyPermissions.h(activity, per[0])) {
                    canRequest = true;
                } else {
                    canRequest = false;
                }
            }
            if (canRequest) {
                new com.tbruyelle.rxpermissions2.b(activity).l(per).Y(new a(new int[]{0}, per, fragment, runnable2, activity, denyKey), new b());
                return;
            }
            if (fragment != null) {
                c(fragment);
            }
            w.K(activity);
        }
    }

    public class a implements e<com.tbruyelle.rxpermissions2.a> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int[] c;
        final /* synthetic */ String[] d;
        final /* synthetic */ LDSPermissitonGuideFragment f;
        final /* synthetic */ Runnable q;
        final /* synthetic */ Activity x;
        final /* synthetic */ String y;

        a(int[] iArr, String[] strArr, LDSPermissitonGuideFragment lDSPermissitonGuideFragment, Runnable runnable, Activity activity, String str) {
            this.c = iArr;
            this.d = strArr;
            this.f = lDSPermissitonGuideFragment;
            this.q = runnable;
            this.x = activity;
            this.y = str;
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 751, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((com.tbruyelle.rxpermissions2.a) obj);
            }
        }

        public void a(com.tbruyelle.rxpermissions2.a permission) {
            if (!PatchProxy.proxy(new Object[]{permission}, this, changeQuickRedirect, false, 750, new Class[]{com.tbruyelle.rxpermissions2.a.class}, Void.TYPE).isSupported) {
                int[] iArr = this.c;
                iArr[0] = iArr[0] + 1;
                if (permission.b) {
                    if (iArr[0] == this.d.length) {
                        LDSPermissitonGuideFragment lDSPermissitonGuideFragment = this.f;
                        if (lDSPermissitonGuideFragment != null) {
                            LDSPermissionGuide.c(lDSPermissitonGuideFragment);
                        }
                        Runnable runnable = this.q;
                        if (runnable != null) {
                            runnable.run();
                        }
                    }
                } else if (permission.c) {
                    timber.log.a.g("CZB").a("拒绝，下次还可询问", new Object[0]);
                    SharePreferenceUtils.setPrefBoolean(this.x, this.y, true);
                } else {
                    timber.log.a.g("CZB").a("已拒绝且不在提示", new Object[0]);
                    SharePreferenceUtils.setPrefBoolean(this.x, this.y, true);
                }
            }
        }
    }

    public class b implements e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 753, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 752, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                throwable.printStackTrace();
            }
        }
    }

    public static void a(Activity activity, String[] per, String denyKey, Runnable runnable) {
        if (!PatchProxy.proxy(new Object[]{activity, per, denyKey, runnable}, (Object) null, changeQuickRedirect, true, 749, new Class[]{Activity.class, String[].class, String.class, Runnable.class}, Void.TYPE).isSupported) {
            b((LDSPermissitonGuideFragment) null, activity, per, denyKey, runnable);
        }
    }
}
