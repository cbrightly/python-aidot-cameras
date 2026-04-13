package com.leedarson.newui.pages.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.leedarson.R$layout;
import com.leedarson.bean.SDRecord;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SDCardEventEditAdapter.kt */
public final class SDCardEventEditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private ArrayList<SDRecord> a = new ArrayList<>();
    @Nullable
    private Context b;
    @NotNull
    private String c = "";
    @NotNull
    private String d = "";
    @Nullable
    private a e;

    /* compiled from: SDCardEventEditAdapter.kt */
    public interface a {
        void a(int i, @NotNull SDRecord sDRecord);

        void b(int i, @NotNull SDRecord sDRecord);
    }

    public final void b(@Nullable a value) {
        this.e = value;
    }

    public SDCardEventEditAdapter(@NotNull ArrayList<SDRecord> dataList, @NotNull Context context, @NotNull String eventStr, @NotNull String p2pId) {
        k.e(dataList, "dataList");
        k.e(context, "context");
        k.e(eventStr, "eventStr");
        k.e(p2pId, "p2pId");
        this.a = dataList;
        this.b = context;
        this.c = eventStr;
        this.d = p2pId;
    }

    @NotNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parent, new Integer(viewType)}, this, changeQuickRedirect, false, 4308, new Class[]{ViewGroup.class, Integer.TYPE}, RecyclerView.ViewHolder.class);
        if (proxy.isSupported) {
            return (RecyclerView.ViewHolder) proxy.result;
        }
        k.e(parent, "parent");
        switch (viewType) {
            case 0:
                View inflate = LayoutInflater.from(this.b).inflate(R$layout.item_sd_card_hour, (ViewGroup) null);
                k.d(inflate, "from(_context).inflate(R.layout.item_sd_card_hour, null)");
                return new SDEventEditViewHolder(inflate);
            case 1:
                View inflate2 = LayoutInflater.from(this.b).inflate(R$layout.item_sd_card_edit_group_title, (ViewGroup) null);
                k.d(inflate2, "from(_context).inflate(R.layout.item_sd_card_edit_group_title, null)");
                return new SDEventEditGroupTitleViewHolder(inflate2);
            default:
                View inflate3 = LayoutInflater.from(this.b).inflate(R$layout.item_sd_card_hour, (ViewGroup) null);
                k.d(inflate3, "from(_context).inflate(R.layout.item_sd_card_hour, null)");
                return new SDEventEditViewHolder(inflate3);
        }
    }

    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        if (!PatchProxy.proxy(new Object[]{holder, new Integer(position)}, this, changeQuickRedirect, false, 4309, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            k.e(holder, "holder");
            if (holder instanceof SDEventEditGroupTitleViewHolder) {
                SDRecord sDRecord = this.a.get(position);
                k.d(sDRecord, "_dataList[position]");
                ((SDEventEditGroupTitleViewHolder) holder).a(sDRecord, position, this.e);
            } else if (holder instanceof SDEventEditViewHolder) {
                SDRecord sDRecord2 = this.a.get(position);
                k.d(sDRecord2, "_dataList[position]");
                ((SDEventEditViewHolder) holder).a(sDRecord2, position, this.e, this.c, this.d);
            }
        }
    }

    public int getItemCount() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4310, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.a.size();
    }

    public int getItemViewType(int position) {
        Object[] objArr = {new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4311, new Class[]{cls}, cls);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.a.get(position).itemType;
    }

    @NotNull
    public final ArrayList<SDRecord> a() {
        return this.a;
    }
}
