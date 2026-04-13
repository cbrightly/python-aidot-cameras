package com.google.android.material.navigation;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.internal.ParcelableSparseArray;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class NavigationBarPresenter implements MenuPresenter {
    private int id;
    private MenuBuilder menu;
    private NavigationBarMenuView menuView;
    private boolean updateSuspended = false;

    public void setMenuView(@NonNull NavigationBarMenuView menuView2) {
        this.menuView = menuView2;
    }

    public void initForMenu(@NonNull Context context, @NonNull MenuBuilder menu2) {
        this.menu = menu2;
        this.menuView.initialize(menu2);
    }

    @Nullable
    public MenuView getMenuView(@Nullable ViewGroup root) {
        return this.menuView;
    }

    public void updateMenuView(boolean cleared) {
        if (!this.updateSuspended) {
            if (cleared) {
                this.menuView.buildMenuView();
            } else {
                this.menuView.updateMenuView();
            }
        }
    }

    public void setCallback(@Nullable MenuPresenter.Callback cb) {
    }

    public boolean onSubMenuSelected(@Nullable SubMenuBuilder subMenu) {
        return false;
    }

    public void onCloseMenu(@Nullable MenuBuilder menu2, boolean allMenusAreClosing) {
    }

    public boolean flagActionItems() {
        return false;
    }

    public boolean expandItemActionView(@Nullable MenuBuilder menu2, @Nullable MenuItemImpl item) {
        return false;
    }

    public boolean collapseItemActionView(@Nullable MenuBuilder menu2, @Nullable MenuItemImpl item) {
        return false;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public int getId() {
        return this.id;
    }

    @NonNull
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.selectedItemId = this.menuView.getSelectedItemId();
        savedState.badgeSavedStates = BadgeUtils.createParcelableBadgeStates(this.menuView.getBadgeDrawables());
        return savedState;
    }

    public void onRestoreInstanceState(@NonNull Parcelable state) {
        if (state instanceof SavedState) {
            this.menuView.tryRestoreSelectedItemId(((SavedState) state).selectedItemId);
            this.menuView.setBadgeDrawables(BadgeUtils.createBadgeDrawablesFromSavedStates(this.menuView.getContext(), ((SavedState) state).badgeSavedStates));
        }
    }

    public void setUpdateSuspended(boolean updateSuspended2) {
        this.updateSuspended = updateSuspended2;
    }

    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @NonNull
            public SavedState createFromParcel(@NonNull Parcel in) {
                return new SavedState(in);
            }

            @NonNull
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        @Nullable
        ParcelableSparseArray badgeSavedStates;
        int selectedItemId;

        SavedState() {
        }

        SavedState(@NonNull Parcel in) {
            this.selectedItemId = in.readInt();
            this.badgeSavedStates = (ParcelableSparseArray) in.readParcelable(getClass().getClassLoader());
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(@NonNull Parcel out, int flags) {
            out.writeInt(this.selectedItemId);
            out.writeParcelable(this.badgeSavedStates, 0);
        }
    }
}
