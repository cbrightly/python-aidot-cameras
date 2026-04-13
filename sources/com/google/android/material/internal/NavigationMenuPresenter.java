package com.google.android.material.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Dimension;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import com.google.android.material.R;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class NavigationMenuPresenter implements MenuPresenter {
    private static final String STATE_ADAPTER = "android:menu:adapter";
    private static final String STATE_HEADER = "android:menu:header";
    private static final String STATE_HIERARCHY = "android:menu:list";
    NavigationMenuAdapter adapter;
    private MenuPresenter.Callback callback;
    boolean hasCustomItemIconSize;
    LinearLayout headerLayout;
    ColorStateList iconTintList;
    private int id;
    boolean isBehindStatusBar = true;
    Drawable itemBackground;
    int itemHorizontalPadding;
    int itemIconPadding;
    int itemIconSize;
    /* access modifiers changed from: private */
    public int itemMaxLines;
    LayoutInflater layoutInflater;
    MenuBuilder menu;
    private NavigationMenuView menuView;
    final View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            NavigationMenuPresenter.this.setUpdateSuspended(true);
            MenuItemImpl item = ((NavigationMenuItemView) view).getItemData();
            NavigationMenuPresenter navigationMenuPresenter = NavigationMenuPresenter.this;
            boolean result = navigationMenuPresenter.menu.performItemAction(item, navigationMenuPresenter, 0);
            boolean checkStateChanged = false;
            if (item != null && item.isCheckable() && result) {
                NavigationMenuPresenter.this.adapter.setCheckedItem(item);
                checkStateChanged = true;
            }
            NavigationMenuPresenter.this.setUpdateSuspended(false);
            if (checkStateChanged) {
                NavigationMenuPresenter.this.updateMenuView(false);
            }
        }
    };
    private int overScrollMode = -1;
    int paddingSeparator;
    private int paddingTopDefault;
    int textAppearance;
    boolean textAppearanceSet;
    ColorStateList textColor;

    public interface NavigationMenuItem {
    }

    public void initForMenu(@NonNull Context context, @NonNull MenuBuilder menu2) {
        this.layoutInflater = LayoutInflater.from(context);
        this.menu = menu2;
        this.paddingSeparator = context.getResources().getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
    }

    public MenuView getMenuView(ViewGroup root) {
        if (this.menuView == null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) this.layoutInflater.inflate(R.layout.design_navigation_menu, root, false);
            this.menuView = navigationMenuView;
            navigationMenuView.setAccessibilityDelegateCompat(new NavigationMenuViewAccessibilityDelegate(this.menuView));
            if (this.adapter == null) {
                this.adapter = new NavigationMenuAdapter();
            }
            int i = this.overScrollMode;
            if (i != -1) {
                this.menuView.setOverScrollMode(i);
            }
            this.headerLayout = (LinearLayout) this.layoutInflater.inflate(R.layout.design_navigation_item_header, this.menuView, false);
            this.menuView.setAdapter(this.adapter);
        }
        return this.menuView;
    }

    public void updateMenuView(boolean cleared) {
        NavigationMenuAdapter navigationMenuAdapter = this.adapter;
        if (navigationMenuAdapter != null) {
            navigationMenuAdapter.update();
        }
    }

    public void setCallback(MenuPresenter.Callback cb) {
        this.callback = cb;
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
        return false;
    }

    public void onCloseMenu(MenuBuilder menu2, boolean allMenusAreClosing) {
        MenuPresenter.Callback callback2 = this.callback;
        if (callback2 != null) {
            callback2.onCloseMenu(menu2, allMenusAreClosing);
        }
    }

    public boolean flagActionItems() {
        return false;
    }

    public boolean expandItemActionView(MenuBuilder menu2, MenuItemImpl item) {
        return false;
    }

    public boolean collapseItemActionView(MenuBuilder menu2, MenuItemImpl item) {
        return false;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    @NonNull
    public Parcelable onSaveInstanceState() {
        Bundle state = new Bundle();
        if (this.menuView != null) {
            SparseArray<Parcelable> hierarchy = new SparseArray<>();
            this.menuView.saveHierarchyState(hierarchy);
            state.putSparseParcelableArray("android:menu:list", hierarchy);
        }
        NavigationMenuAdapter navigationMenuAdapter = this.adapter;
        if (navigationMenuAdapter != null) {
            state.putBundle(STATE_ADAPTER, navigationMenuAdapter.createInstanceState());
        }
        if (this.headerLayout != null) {
            SparseArray<Parcelable> header = new SparseArray<>();
            this.headerLayout.saveHierarchyState(header);
            state.putSparseParcelableArray(STATE_HEADER, header);
        }
        return state;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle state = (Bundle) parcelable;
            SparseArray<Parcelable> hierarchy = state.getSparseParcelableArray("android:menu:list");
            if (hierarchy != null) {
                this.menuView.restoreHierarchyState(hierarchy);
            }
            Bundle adapterState = state.getBundle(STATE_ADAPTER);
            if (adapterState != null) {
                this.adapter.restoreInstanceState(adapterState);
            }
            SparseArray<Parcelable> header = state.getSparseParcelableArray(STATE_HEADER);
            if (header != null) {
                this.headerLayout.restoreHierarchyState(header);
            }
        }
    }

    public void setCheckedItem(@NonNull MenuItemImpl item) {
        this.adapter.setCheckedItem(item);
    }

    @Nullable
    public MenuItemImpl getCheckedItem() {
        return this.adapter.getCheckedItem();
    }

    public View inflateHeaderView(@LayoutRes int res) {
        View view = this.layoutInflater.inflate(res, this.headerLayout, false);
        addHeaderView(view);
        return view;
    }

    public void addHeaderView(@NonNull View view) {
        this.headerLayout.addView(view);
        NavigationMenuView navigationMenuView = this.menuView;
        navigationMenuView.setPadding(0, 0, 0, navigationMenuView.getPaddingBottom());
    }

    public void removeHeaderView(@NonNull View view) {
        this.headerLayout.removeView(view);
        if (this.headerLayout.getChildCount() == 0) {
            NavigationMenuView navigationMenuView = this.menuView;
            navigationMenuView.setPadding(0, this.paddingTopDefault, 0, navigationMenuView.getPaddingBottom());
        }
    }

    public int getHeaderCount() {
        return this.headerLayout.getChildCount();
    }

    public View getHeaderView(int index) {
        return this.headerLayout.getChildAt(index);
    }

    @Nullable
    public ColorStateList getItemTintList() {
        return this.iconTintList;
    }

    public void setItemIconTintList(@Nullable ColorStateList tint) {
        this.iconTintList = tint;
        updateMenuView(false);
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.textColor;
    }

    public void setItemTextColor(@Nullable ColorStateList textColor2) {
        this.textColor = textColor2;
        updateMenuView(false);
    }

    public void setItemTextAppearance(@StyleRes int resId) {
        this.textAppearance = resId;
        this.textAppearanceSet = true;
        updateMenuView(false);
    }

    @Nullable
    public Drawable getItemBackground() {
        return this.itemBackground;
    }

    public void setItemBackground(@Nullable Drawable itemBackground2) {
        this.itemBackground = itemBackground2;
        updateMenuView(false);
    }

    public int getItemHorizontalPadding() {
        return this.itemHorizontalPadding;
    }

    public void setItemHorizontalPadding(int itemHorizontalPadding2) {
        this.itemHorizontalPadding = itemHorizontalPadding2;
        updateMenuView(false);
    }

    public int getItemIconPadding() {
        return this.itemIconPadding;
    }

    public void setItemIconPadding(int itemIconPadding2) {
        this.itemIconPadding = itemIconPadding2;
        updateMenuView(false);
    }

    public void setItemMaxLines(int itemMaxLines2) {
        this.itemMaxLines = itemMaxLines2;
        updateMenuView(false);
    }

    public int getItemMaxLines() {
        return this.itemMaxLines;
    }

    public void setItemIconSize(@Dimension int itemIconSize2) {
        if (this.itemIconSize != itemIconSize2) {
            this.itemIconSize = itemIconSize2;
            this.hasCustomItemIconSize = true;
            updateMenuView(false);
        }
    }

    public void setUpdateSuspended(boolean updateSuspended) {
        NavigationMenuAdapter navigationMenuAdapter = this.adapter;
        if (navigationMenuAdapter != null) {
            navigationMenuAdapter.setUpdateSuspended(updateSuspended);
        }
    }

    public void setBehindStatusBar(boolean behindStatusBar) {
        if (this.isBehindStatusBar != behindStatusBar) {
            this.isBehindStatusBar = behindStatusBar;
            updateTopPadding();
        }
    }

    public boolean isBehindStatusBar() {
        return this.isBehindStatusBar;
    }

    private void updateTopPadding() {
        int topPadding = 0;
        if (this.headerLayout.getChildCount() == 0 && this.isBehindStatusBar) {
            topPadding = this.paddingTopDefault;
        }
        NavigationMenuView navigationMenuView = this.menuView;
        navigationMenuView.setPadding(0, topPadding, 0, navigationMenuView.getPaddingBottom());
    }

    public void dispatchApplyWindowInsets(@NonNull WindowInsetsCompat insets) {
        int top = insets.getSystemWindowInsetTop();
        if (this.paddingTopDefault != top) {
            this.paddingTopDefault = top;
            updateTopPadding();
        }
        NavigationMenuView navigationMenuView = this.menuView;
        navigationMenuView.setPadding(0, navigationMenuView.getPaddingTop(), 0, insets.getSystemWindowInsetBottom());
        ViewCompat.dispatchApplyWindowInsets(this.headerLayout, insets);
    }

    public void setOverScrollMode(int overScrollMode2) {
        this.overScrollMode = overScrollMode2;
        NavigationMenuView navigationMenuView = this.menuView;
        if (navigationMenuView != null) {
            navigationMenuView.setOverScrollMode(overScrollMode2);
        }
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class NormalViewHolder extends ViewHolder {
        public NormalViewHolder(@NonNull LayoutInflater inflater, ViewGroup parent, View.OnClickListener listener) {
            super(inflater.inflate(R.layout.design_navigation_item, parent, false));
            this.itemView.setOnClickListener(listener);
        }
    }

    public static class SubheaderViewHolder extends ViewHolder {
        public SubheaderViewHolder(@NonNull LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.design_navigation_item_subheader, parent, false));
        }
    }

    public static class SeparatorViewHolder extends ViewHolder {
        public SeparatorViewHolder(@NonNull LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.design_navigation_item_separator, parent, false));
        }
    }

    public static class HeaderViewHolder extends ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class NavigationMenuAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String STATE_ACTION_VIEWS = "android:menu:action_views";
        private static final String STATE_CHECKED_ITEM = "android:menu:checked";
        private static final int VIEW_TYPE_HEADER = 3;
        private static final int VIEW_TYPE_NORMAL = 0;
        private static final int VIEW_TYPE_SEPARATOR = 2;
        private static final int VIEW_TYPE_SUBHEADER = 1;
        private MenuItemImpl checkedItem;
        private final ArrayList<NavigationMenuItem> items = new ArrayList<>();
        private boolean updateSuspended;

        NavigationMenuAdapter() {
            prepareMenuItems();
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public int getItemCount() {
            return this.items.size();
        }

        public int getItemViewType(int position) {
            NavigationMenuItem item = this.items.get(position);
            if (item instanceof NavigationMenuSeparatorItem) {
                return 2;
            }
            if (item instanceof NavigationMenuHeaderItem) {
                return 3;
            }
            if (!(item instanceof NavigationMenuTextItem)) {
                throw new RuntimeException("Unknown item type.");
            } else if (((NavigationMenuTextItem) item).getMenuItem().hasSubMenu()) {
                return 1;
            } else {
                return 0;
            }
        }

        @Nullable
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case 0:
                    NavigationMenuPresenter navigationMenuPresenter = NavigationMenuPresenter.this;
                    return new NormalViewHolder(navigationMenuPresenter.layoutInflater, parent, navigationMenuPresenter.onClickListener);
                case 1:
                    return new SubheaderViewHolder(NavigationMenuPresenter.this.layoutInflater, parent);
                case 2:
                    return new SeparatorViewHolder(NavigationMenuPresenter.this.layoutInflater, parent);
                case 3:
                    return new HeaderViewHolder(NavigationMenuPresenter.this.headerLayout);
                default:
                    return null;
            }
        }

        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            switch (getItemViewType(position)) {
                case 0:
                    NavigationMenuItemView itemView = (NavigationMenuItemView) holder.itemView;
                    itemView.setIconTintList(NavigationMenuPresenter.this.iconTintList);
                    NavigationMenuPresenter navigationMenuPresenter = NavigationMenuPresenter.this;
                    if (navigationMenuPresenter.textAppearanceSet) {
                        itemView.setTextAppearance(navigationMenuPresenter.textAppearance);
                    }
                    ColorStateList colorStateList = NavigationMenuPresenter.this.textColor;
                    if (colorStateList != null) {
                        itemView.setTextColor(colorStateList);
                    }
                    Drawable drawable = NavigationMenuPresenter.this.itemBackground;
                    ViewCompat.setBackground(itemView, drawable != null ? drawable.getConstantState().newDrawable() : null);
                    NavigationMenuTextItem item = (NavigationMenuTextItem) this.items.get(position);
                    itemView.setNeedsEmptyIcon(item.needsEmptyIcon);
                    itemView.setHorizontalPadding(NavigationMenuPresenter.this.itemHorizontalPadding);
                    itemView.setIconPadding(NavigationMenuPresenter.this.itemIconPadding);
                    NavigationMenuPresenter navigationMenuPresenter2 = NavigationMenuPresenter.this;
                    if (navigationMenuPresenter2.hasCustomItemIconSize) {
                        itemView.setIconSize(navigationMenuPresenter2.itemIconSize);
                    }
                    itemView.setMaxLines(NavigationMenuPresenter.this.itemMaxLines);
                    itemView.initialize(item.getMenuItem(), 0);
                    return;
                case 1:
                    ((TextView) holder.itemView).setText(((NavigationMenuTextItem) this.items.get(position)).getMenuItem().getTitle());
                    return;
                case 2:
                    NavigationMenuSeparatorItem item2 = (NavigationMenuSeparatorItem) this.items.get(position);
                    holder.itemView.setPadding(0, item2.getPaddingTop(), 0, item2.getPaddingBottom());
                    return;
                default:
                    return;
            }
        }

        public void onViewRecycled(ViewHolder holder) {
            if (holder instanceof NormalViewHolder) {
                ((NavigationMenuItemView) holder.itemView).recycle();
            }
        }

        public void update() {
            prepareMenuItems();
            notifyDataSetChanged();
        }

        private void prepareMenuItems() {
            if (!this.updateSuspended) {
                this.updateSuspended = true;
                this.items.clear();
                this.items.add(new NavigationMenuHeaderItem());
                int currentGroupId = -1;
                int currentGroupStart = 0;
                boolean currentGroupHasIcon = false;
                int i = 0;
                int totalSize = NavigationMenuPresenter.this.menu.getVisibleItems().size();
                while (true) {
                    boolean z = false;
                    if (i < totalSize) {
                        MenuItemImpl item = NavigationMenuPresenter.this.menu.getVisibleItems().get(i);
                        if (item.isChecked()) {
                            setCheckedItem(item);
                        }
                        if (item.isCheckable()) {
                            item.setExclusiveCheckable(false);
                        }
                        if (item.hasSubMenu()) {
                            SubMenu subMenu = item.getSubMenu();
                            if (subMenu.hasVisibleItems()) {
                                if (i != 0) {
                                    this.items.add(new NavigationMenuSeparatorItem(NavigationMenuPresenter.this.paddingSeparator, 0));
                                }
                                this.items.add(new NavigationMenuTextItem(item));
                                boolean subMenuHasIcon = false;
                                int subMenuStart = this.items.size();
                                int size = subMenu.size();
                                for (int j = 0; j < size; j++) {
                                    MenuItemImpl subMenuItem = (MenuItemImpl) subMenu.getItem(j);
                                    if (subMenuItem.isVisible()) {
                                        if (!subMenuHasIcon && subMenuItem.getIcon() != null) {
                                            subMenuHasIcon = true;
                                        }
                                        if (subMenuItem.isCheckable()) {
                                            subMenuItem.setExclusiveCheckable(false);
                                        }
                                        if (item.isChecked()) {
                                            setCheckedItem(item);
                                        }
                                        this.items.add(new NavigationMenuTextItem(subMenuItem));
                                    }
                                }
                                if (subMenuHasIcon) {
                                    appendTransparentIconIfMissing(subMenuStart, this.items.size());
                                }
                            }
                        } else {
                            int groupId = item.getGroupId();
                            if (groupId != currentGroupId) {
                                currentGroupStart = this.items.size();
                                if (item.getIcon() != null) {
                                    z = true;
                                }
                                currentGroupHasIcon = z;
                                if (i != 0) {
                                    currentGroupStart++;
                                    ArrayList<NavigationMenuItem> arrayList = this.items;
                                    int i2 = NavigationMenuPresenter.this.paddingSeparator;
                                    arrayList.add(new NavigationMenuSeparatorItem(i2, i2));
                                }
                            } else if (!currentGroupHasIcon && item.getIcon() != null) {
                                currentGroupHasIcon = true;
                                appendTransparentIconIfMissing(currentGroupStart, this.items.size());
                            }
                            NavigationMenuTextItem textItem = new NavigationMenuTextItem(item);
                            textItem.needsEmptyIcon = currentGroupHasIcon;
                            this.items.add(textItem);
                            currentGroupId = groupId;
                        }
                        i++;
                    } else {
                        this.updateSuspended = false;
                        return;
                    }
                }
            }
        }

        private void appendTransparentIconIfMissing(int startIndex, int endIndex) {
            for (int i = startIndex; i < endIndex; i++) {
                ((NavigationMenuTextItem) this.items.get(i)).needsEmptyIcon = true;
            }
        }

        public void setCheckedItem(@NonNull MenuItemImpl checkedItem2) {
            if (this.checkedItem != checkedItem2 && checkedItem2.isCheckable()) {
                MenuItemImpl menuItemImpl = this.checkedItem;
                if (menuItemImpl != null) {
                    menuItemImpl.setChecked(false);
                }
                this.checkedItem = checkedItem2;
                checkedItem2.setChecked(true);
            }
        }

        public MenuItemImpl getCheckedItem() {
            return this.checkedItem;
        }

        @NonNull
        public Bundle createInstanceState() {
            Bundle state = new Bundle();
            MenuItemImpl menuItemImpl = this.checkedItem;
            if (menuItemImpl != null) {
                state.putInt(STATE_CHECKED_ITEM, menuItemImpl.getItemId());
            }
            SparseArray<ParcelableSparseArray> actionViewStates = new SparseArray<>();
            int size = this.items.size();
            for (int i = 0; i < size; i++) {
                NavigationMenuItem navigationMenuItem = this.items.get(i);
                if (navigationMenuItem instanceof NavigationMenuTextItem) {
                    MenuItemImpl item = ((NavigationMenuTextItem) navigationMenuItem).getMenuItem();
                    View actionView = item != null ? item.getActionView() : null;
                    if (actionView != null) {
                        ParcelableSparseArray container = new ParcelableSparseArray();
                        actionView.saveHierarchyState(container);
                        actionViewStates.put(item.getItemId(), container);
                    }
                }
            }
            state.putSparseParcelableArray(STATE_ACTION_VIEWS, actionViewStates);
            return state;
        }

        public void restoreInstanceState(@NonNull Bundle state) {
            MenuItemImpl item;
            View actionView;
            ParcelableSparseArray container;
            MenuItemImpl menuItem;
            int checkedItem2 = state.getInt(STATE_CHECKED_ITEM, 0);
            if (checkedItem2 != 0) {
                this.updateSuspended = true;
                int i = 0;
                int size = this.items.size();
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    NavigationMenuItem item2 = this.items.get(i);
                    if ((item2 instanceof NavigationMenuTextItem) && (menuItem = ((NavigationMenuTextItem) item2).getMenuItem()) != null && menuItem.getItemId() == checkedItem2) {
                        setCheckedItem(menuItem);
                        break;
                    }
                    i++;
                }
                this.updateSuspended = false;
                prepareMenuItems();
            }
            SparseArray<ParcelableSparseArray> actionViewStates = state.getSparseParcelableArray(STATE_ACTION_VIEWS);
            if (actionViewStates != null) {
                int size2 = this.items.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    NavigationMenuItem navigationMenuItem = this.items.get(i2);
                    if (!(!(navigationMenuItem instanceof NavigationMenuTextItem) || (item = ((NavigationMenuTextItem) navigationMenuItem).getMenuItem()) == null || (actionView = item.getActionView()) == null || (container = actionViewStates.get(item.getItemId())) == null)) {
                        actionView.restoreHierarchyState(container);
                    }
                }
            }
        }

        public void setUpdateSuspended(boolean updateSuspended2) {
            this.updateSuspended = updateSuspended2;
        }

        /* access modifiers changed from: package-private */
        public int getRowCount() {
            int itemCount = NavigationMenuPresenter.this.headerLayout.getChildCount() == 0 ? 0 : 1;
            for (int i = 0; i < NavigationMenuPresenter.this.adapter.getItemCount(); i++) {
                if (NavigationMenuPresenter.this.adapter.getItemViewType(i) == 0) {
                    itemCount++;
                }
            }
            return itemCount;
        }
    }

    public static class NavigationMenuTextItem implements NavigationMenuItem {
        private final MenuItemImpl menuItem;
        boolean needsEmptyIcon;

        NavigationMenuTextItem(MenuItemImpl item) {
            this.menuItem = item;
        }

        public MenuItemImpl getMenuItem() {
            return this.menuItem;
        }
    }

    public static class NavigationMenuSeparatorItem implements NavigationMenuItem {
        private final int paddingBottom;
        private final int paddingTop;

        public NavigationMenuSeparatorItem(int paddingTop2, int paddingBottom2) {
            this.paddingTop = paddingTop2;
            this.paddingBottom = paddingBottom2;
        }

        public int getPaddingTop() {
            return this.paddingTop;
        }

        public int getPaddingBottom() {
            return this.paddingBottom;
        }
    }

    public static class NavigationMenuHeaderItem implements NavigationMenuItem {
        NavigationMenuHeaderItem() {
        }
    }

    public class NavigationMenuViewAccessibilityDelegate extends RecyclerViewAccessibilityDelegate {
        NavigationMenuViewAccessibilityDelegate(@NonNull RecyclerView recyclerView) {
            super(recyclerView);
        }

        public void onInitializeAccessibilityNodeInfo(View host, @NonNull AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(NavigationMenuPresenter.this.adapter.getRowCount(), 0, false));
        }
    }
}
