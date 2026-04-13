package androidx.viewpager2.adapter;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public final class FragmentViewHolder extends RecyclerView.ViewHolder {
    private FragmentViewHolder(@NonNull FrameLayout container) {
        super(container);
    }

    @NonNull
    static FragmentViewHolder create(@NonNull ViewGroup parent) {
        FrameLayout container = new FrameLayout(parent.getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        container.setId(ViewCompat.generateViewId());
        container.setSaveEnabled(false);
        return new FragmentViewHolder(container);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public FrameLayout getContainer() {
        return (FrameLayout) this.itemView;
    }
}
