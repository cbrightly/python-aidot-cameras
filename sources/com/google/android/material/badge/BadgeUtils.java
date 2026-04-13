package com.google.android.material.badge;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.internal.ParcelableSparseArray;
import com.google.android.material.internal.ToolbarUtils;

@ExperimentalBadgeUtils
public class BadgeUtils {
    private static final String LOG_TAG = "BadgeUtils";
    public static final boolean USE_COMPAT_PARENT = (Build.VERSION.SDK_INT < 18);

    private BadgeUtils() {
    }

    public static void updateBadgeBounds(@NonNull Rect rect, float centerX, float centerY, float halfWidth, float halfHeight) {
        rect.set((int) (centerX - halfWidth), (int) (centerY - halfHeight), (int) (centerX + halfWidth), (int) (centerY + halfHeight));
    }

    public static void attachBadgeDrawable(@NonNull BadgeDrawable badgeDrawable, @NonNull View anchor) {
        attachBadgeDrawable(badgeDrawable, anchor, (FrameLayout) null);
    }

    public static void attachBadgeDrawable(@NonNull BadgeDrawable badgeDrawable, @NonNull View anchor, @Nullable FrameLayout customBadgeParent) {
        setBadgeDrawableBounds(badgeDrawable, anchor, customBadgeParent);
        if (badgeDrawable.getCustomBadgeParent() != null) {
            badgeDrawable.getCustomBadgeParent().setForeground(badgeDrawable);
        } else if (!USE_COMPAT_PARENT) {
            anchor.getOverlay().add(badgeDrawable);
        } else {
            throw new IllegalArgumentException("Trying to reference null customBadgeParent");
        }
    }

    public static void attachBadgeDrawable(@NonNull BadgeDrawable badgeDrawable, @NonNull Toolbar toolbar, @IdRes int menuItemId) {
        attachBadgeDrawable(badgeDrawable, toolbar, menuItemId, (FrameLayout) null);
    }

    public static void attachBadgeDrawable(@NonNull final BadgeDrawable badgeDrawable, @NonNull final Toolbar toolbar, @IdRes final int menuItemId, @Nullable final FrameLayout customBadgeParent) {
        toolbar.post(new Runnable() {
            public void run() {
                ActionMenuItemView menuItemView = ToolbarUtils.getActionMenuItemView(toolbar, menuItemId);
                if (menuItemView != null) {
                    BadgeUtils.setToolbarOffset(badgeDrawable, toolbar.getResources());
                    BadgeUtils.attachBadgeDrawable(badgeDrawable, (View) menuItemView, customBadgeParent);
                }
            }
        });
    }

    public static void detachBadgeDrawable(@Nullable BadgeDrawable badgeDrawable, @NonNull View anchor) {
        if (badgeDrawable != null) {
            if (USE_COMPAT_PARENT || badgeDrawable.getCustomBadgeParent() != null) {
                badgeDrawable.getCustomBadgeParent().setForeground((Drawable) null);
            } else {
                anchor.getOverlay().remove(badgeDrawable);
            }
        }
    }

    public static void detachBadgeDrawable(@Nullable BadgeDrawable badgeDrawable, @NonNull Toolbar toolbar, @IdRes int menuItemId) {
        if (badgeDrawable != null) {
            ActionMenuItemView menuItemView = ToolbarUtils.getActionMenuItemView(toolbar, menuItemId);
            if (menuItemView != null) {
                removeToolbarOffset(badgeDrawable);
                detachBadgeDrawable(badgeDrawable, menuItemView);
                return;
            }
            Log.w(LOG_TAG, "Trying to remove badge from a null menuItemView: " + menuItemId);
        }
    }

    @VisibleForTesting
    static void setToolbarOffset(BadgeDrawable badgeDrawable, Resources resources) {
        badgeDrawable.setAdditionalHorizontalOffset(resources.getDimensionPixelOffset(R.dimen.mtrl_badge_toolbar_action_menu_item_horizontal_offset));
        badgeDrawable.setAdditionalVerticalOffset(resources.getDimensionPixelOffset(R.dimen.mtrl_badge_toolbar_action_menu_item_vertical_offset));
    }

    @VisibleForTesting
    static void removeToolbarOffset(BadgeDrawable badgeDrawable) {
        badgeDrawable.setAdditionalHorizontalOffset(0);
        badgeDrawable.setAdditionalVerticalOffset(0);
    }

    public static void setBadgeDrawableBounds(@NonNull BadgeDrawable badgeDrawable, @NonNull View anchor, @Nullable FrameLayout compatBadgeParent) {
        Rect badgeBounds = new Rect();
        anchor.getDrawingRect(badgeBounds);
        badgeDrawable.setBounds(badgeBounds);
        badgeDrawable.updateBadgeCoordinates(anchor, compatBadgeParent);
    }

    @NonNull
    public static ParcelableSparseArray createParcelableBadgeStates(@NonNull SparseArray<BadgeDrawable> badgeDrawables) {
        ParcelableSparseArray badgeStates = new ParcelableSparseArray();
        int i = 0;
        while (i < badgeDrawables.size()) {
            int key = badgeDrawables.keyAt(i);
            BadgeDrawable badgeDrawable = badgeDrawables.valueAt(i);
            if (badgeDrawable != null) {
                badgeStates.put(key, badgeDrawable.getSavedState());
                i++;
            } else {
                throw new IllegalArgumentException("badgeDrawable cannot be null");
            }
        }
        return badgeStates;
    }

    @NonNull
    public static SparseArray<BadgeDrawable> createBadgeDrawablesFromSavedStates(Context context, @NonNull ParcelableSparseArray badgeStates) {
        SparseArray<BadgeDrawable> badgeDrawables = new SparseArray<>(badgeStates.size());
        int i = 0;
        while (i < badgeStates.size()) {
            int key = badgeStates.keyAt(i);
            BadgeDrawable.SavedState savedState = (BadgeDrawable.SavedState) badgeStates.valueAt(i);
            if (savedState != null) {
                badgeDrawables.put(key, BadgeDrawable.createFromSavedState(context, savedState));
                i++;
            } else {
                throw new IllegalArgumentException("BadgeDrawable's savedState cannot be null");
            }
        }
        return badgeDrawables;
    }
}
