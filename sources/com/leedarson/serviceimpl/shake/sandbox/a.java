package com.leedarson.serviceimpl.shake.sandbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.leedarson.serviceimpl.shake.R$drawable;
import com.leedarson.serviceimpl.shake.R$id;
import com.leedarson.serviceimpl.shake.R$layout;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/* compiled from: FileAdapter */
public class a extends BaseAdapter {
    public static ChangeQuickRedirect changeQuickRedirect;
    private ArrayList<File> c = new ArrayList<>();
    private Context d;

    public a(Context context, ArrayList<File> fileList) {
        this.c = fileList;
        this.d = context;
    }

    public int getCount() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8743, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.c.size();
    }

    public Object getItem(int position) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 8744, new Class[]{Integer.TYPE}, Object.class);
        return proxy.isSupported ? proxy.result : this.c.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        C0161a holder;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(position), convertView, viewGroup}, this, changeQuickRedirect, false, 8745, new Class[]{Integer.TYPE, View.class, ViewGroup.class}, View.class);
        if (proxy.isSupported) {
            return (View) proxy.result;
        }
        if (convertView == null) {
            holder = new C0161a();
            convertView = LayoutInflater.from(this.d).inflate(R$layout.item_file, (ViewGroup) null);
            holder.b = (ImageView) convertView.findViewById(R$id.iv_file_icon);
            holder.a = (TextView) convertView.findViewById(R$id.tv_file_name);
            holder.c = (TextView) convertView.findViewById(R$id.tv_file_update_time);
            convertView.setTag(holder);
        } else {
            holder = (C0161a) convertView.getTag();
        }
        File file = this.c.get(position);
        holder.c.setText(a(file));
        holder.a.setText(file.getName());
        holder.b.setImageResource(file.isDirectory() ? R$drawable.dir : R$drawable.file);
        return convertView;
    }

    /* renamed from: com.leedarson.serviceimpl.shake.sandbox.a$a  reason: collision with other inner class name */
    /* compiled from: FileAdapter */
    public class C0161a {
        public TextView a;
        public ImageView b;
        public TextView c;

        C0161a() {
        }
    }

    private String a(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, this, changeQuickRedirect, false, 8746, new Class[]{File.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified()));
    }
}
