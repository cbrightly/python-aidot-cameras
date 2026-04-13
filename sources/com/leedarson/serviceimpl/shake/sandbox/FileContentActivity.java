package com.leedarson.serviceimpl.shake.sandbox;

import android.widget.TextView;
import com.google.maps.android.BuildConfig;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.serviceimpl.shake.R$id;
import com.leedarson.serviceimpl.shake.R$layout;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.functions.e;
import io.reactivex.functions.f;
import io.reactivex.l;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileContentActivity extends BaseActivity {
    public static ChangeQuickRedirect changeQuickRedirect;
    TextView p0;

    public int O() {
        return R$layout.view_file_content;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8747, new Class[0], Void.TYPE).isSupported) {
            this.p0 = (TextView) findViewById(R$id.tv_file_content);
            X(new File(getIntent().getStringExtra("path")));
        }
    }

    public void R() {
    }

    private void X(File file) {
        if (!PatchProxy.proxy(new Object[]{file}, this, changeQuickRedirect, false, 8748, new Class[]{File.class}, Void.TYPE).isSupported) {
            l.F(file).b0(io.reactivex.schedulers.a.c()).G(new c()).J(io.reactivex.android.schedulers.a.a()).Y(new a(), new b());
        }
    }

    public class c implements f<File, String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public /* bridge */ /* synthetic */ Object apply(Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8754, new Class[]{Object.class}, Object.class);
            return proxy.isSupported ? proxy.result : a((File) obj);
        }

        public String a(File f) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{f}, this, changeQuickRedirect, false, 8753, new Class[]{File.class}, String.class);
            if (proxy.isSupported) {
                return (String) proxy.result;
            }
            return FileContentActivity.Y(f);
        }
    }

    public class a implements e<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8751, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((String) obj);
            }
        }

        public void a(String s) {
            if (!PatchProxy.proxy(new Object[]{s}, this, changeQuickRedirect, false, 8750, new Class[]{String.class}, Void.TYPE).isSupported) {
                FileContentActivity.this.p0.setText(s);
            }
        }
    }

    public class b implements e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8752, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
        }
    }

    public static String Y(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 8749, new Class[]{File.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (!file.exists()) {
            return BuildConfig.TRAVIS;
        }
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = br.readLine();
                String line = readLine;
                if (readLine != null) {
                    sb.append(line);
                } else {
                    br.close();
                    fr.close();
                    return sb.toString();
                }
            }
        } catch (IOException e) {
            timber.log.a.d(e);
            return BuildConfig.TRAVIS;
        }
    }
}
