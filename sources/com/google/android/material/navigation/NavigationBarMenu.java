package com.google.android.material.navigation;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class NavigationBarMenu extends MenuBuilder {
    private final int maxItemCount;
    @NonNull
    private final Class<?> viewClass;

    public NavigationBarMenu(@NonNull Context context, @NonNull Class<?> viewClass2, int maxItemCount2) {
        super(context);
        this.viewClass = viewClass2;
        this.maxItemCount = maxItemCount2;
    }

    public int getMaxItemCount() {
        return this.maxItemCount;
    }

    @NonNull
    public SubMenu addSubMenu(int group, int id, int categoryOrder, @NonNull CharSequence title) {
        throw new UnsupportedOperationException(this.viewClass.getSimpleName() + " does not support submenus");
    }

    /* access modifiers changed from: protected */
    @NonNull
    public MenuItem addInternal(int group, int id, int categoryOrder, @NonNull CharSequence title) {
        if (size() + 1 <= this.maxItemCount) {
            stopDispatchingItemsChanged();
            MenuItem item = super.addInternal(group, id, categoryOrder, title);
            if (item instanceof MenuItemImpl) {
                ((MenuItemImpl) item).setExclusiveCheckable(true);
            }
            startDispatchingItemsChanged();
            return item;
        }
        String viewClassName = this.viewClass.getSimpleName();
        throw new IllegalArgumentException("Maximum number of items supported by " + viewClassName + " is " + this.maxItemCount + ". Limit can be checked with " + viewClassName + "#getMaxItemCount()");
    }
}
