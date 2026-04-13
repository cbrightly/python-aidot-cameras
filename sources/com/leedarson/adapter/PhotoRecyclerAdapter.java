package com.leedarson.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.b;
import com.bumptech.glide.h;
import com.bumptech.glide.util.i;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.views.photoview.AlbumPhotoView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.List;

public class PhotoRecyclerAdapter extends RecyclerView.Adapter<ListViewHolder> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final List<String> a;
    private final int b;
    private Context c;

    public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (!PatchProxy.proxy(new Object[]{viewHolder, new Integer(i)}, this, changeQuickRedirect, false, 98, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            b((ListViewHolder) viewHolder, i);
        }
    }

    @NonNull
    public /* bridge */ /* synthetic */ RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{viewGroup, new Integer(i)}, this, changeQuickRedirect, false, 99, new Class[]{ViewGroup.class, Integer.TYPE}, RecyclerView.ViewHolder.class);
        return proxy.isSupported ? (RecyclerView.ViewHolder) proxy.result : c(viewGroup, i);
    }

    public PhotoRecyclerAdapter(Context context, List<String> data) {
        this.a = data;
        setHasStableIds(true);
        this.b = a(context);
        this.c = context;
    }

    @NonNull
    public ListViewHolder c(@NonNull ViewGroup viewGroup, int i) {
        Object[] objArr = {viewGroup, new Integer(i)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 94, new Class[]{ViewGroup.class, Integer.TYPE}, ListViewHolder.class);
        if (proxy.isSupported) {
            return (ListViewHolder) proxy.result;
        }
        return new ListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.photo_recycler_item, viewGroup, false));
    }

    public void b(@NonNull ListViewHolder viewHolder, int position) {
        if (!PatchProxy.proxy(new Object[]{viewHolder, new Integer(position)}, this, changeQuickRedirect, false, 95, new Class[]{ListViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            ((h) b.t(this.c.getApplicationContext()).i().k()).M0(this.a.get(position)).H0(viewHolder.a);
        }
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getItemCount() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 96, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.a.size();
    }

    public int getItemViewType(int position) {
        return 0;
    }

    private static int a(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 97, new Class[]{Context.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        Display display = ((WindowManager) i.d((WindowManager) context.getSystemService("window"))).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static final class ListViewHolder extends RecyclerView.ViewHolder {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public final AlbumPhotoView a;

        ListViewHolder(View itemView) {
            super(itemView);
            this.a = (AlbumPhotoView) itemView.findViewById(R$id.image);
        }
    }
}
