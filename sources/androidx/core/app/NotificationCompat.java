package androidx.core.app;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Person;
import android.app.RemoteInput;
import android.content.Context;
import android.content.LocusId;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.RemoteViews;
import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.R;
import androidx.core.app.Person;
import androidx.core.content.LocusIdCompat;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.text.BidiFormatter;
import androidx.core.view.ViewCompat;
import com.didichuxing.doraemonkit.widget.JustifyTextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NotificationCompat {
    public static final int BADGE_ICON_LARGE = 2;
    public static final int BADGE_ICON_NONE = 0;
    public static final int BADGE_ICON_SMALL = 1;
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_CALL = "call";
    public static final String CATEGORY_EMAIL = "email";
    public static final String CATEGORY_ERROR = "err";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_LOCATION_SHARING = "location_sharing";
    public static final String CATEGORY_MESSAGE = "msg";
    public static final String CATEGORY_MISSED_CALL = "missed_call";
    public static final String CATEGORY_NAVIGATION = "navigation";
    public static final String CATEGORY_PROGRESS = "progress";
    public static final String CATEGORY_PROMO = "promo";
    public static final String CATEGORY_RECOMMENDATION = "recommendation";
    public static final String CATEGORY_REMINDER = "reminder";
    public static final String CATEGORY_SERVICE = "service";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_STOPWATCH = "stopwatch";
    public static final String CATEGORY_SYSTEM = "sys";
    public static final String CATEGORY_TRANSPORT = "transport";
    public static final String CATEGORY_WORKOUT = "workout";
    @ColorInt
    public static final int COLOR_DEFAULT = 0;
    public static final int DEFAULT_ALL = -1;
    public static final int DEFAULT_LIGHTS = 4;
    public static final int DEFAULT_SOUND = 1;
    public static final int DEFAULT_VIBRATE = 2;
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_AUDIO_CONTENTS_URI = "android.audioContents";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_BIG_TEXT = "android.bigText";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_CHANNEL_GROUP_ID = "android.intent.extra.CHANNEL_GROUP_ID";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_CHANNEL_ID = "android.intent.extra.CHANNEL_ID";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_CHRONOMETER_COUNT_DOWN = "android.chronometerCountDown";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_COLORIZED = "android.colorized";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_COMPACT_ACTIONS = "android.compactActions";
    public static final String EXTRA_COMPAT_TEMPLATE = "androidx.core.app.extra.COMPAT_TEMPLATE";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_CONVERSATION_TITLE = "android.conversationTitle";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_HIDDEN_CONVERSATION_TITLE = "android.hiddenConversationTitle";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_HISTORIC_MESSAGES = "android.messages.historic";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_INFO_TEXT = "android.infoText";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_IS_GROUP_CONVERSATION = "android.isGroupConversation";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_LARGE_ICON = "android.largeIcon";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_MEDIA_SESSION = "android.mediaSession";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_MESSAGES = "android.messages";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_MESSAGING_STYLE_USER = "android.messagingStyleUser";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_NOTIFICATION_ID = "android.intent.extra.NOTIFICATION_ID";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_NOTIFICATION_TAG = "android.intent.extra.NOTIFICATION_TAG";
    @SuppressLint({"ActionValue"})
    @Deprecated
    public static final String EXTRA_PEOPLE = "android.people";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PEOPLE_LIST = "android.people.list";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PICTURE = "android.picture";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PROGRESS = "android.progress";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SHOW_WHEN = "android.showWhen";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SMALL_ICON = "android.icon";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SUB_TEXT = "android.subText";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_TEMPLATE = "android.template";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_TEXT = "android.text";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_TEXT_LINES = "android.textLines";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_TITLE = "android.title";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_TITLE_BIG = "android.title.big";
    public static final int FLAG_AUTO_CANCEL = 16;
    public static final int FLAG_BUBBLE = 4096;
    public static final int FLAG_FOREGROUND_SERVICE = 64;
    public static final int FLAG_GROUP_SUMMARY = 512;
    @Deprecated
    public static final int FLAG_HIGH_PRIORITY = 128;
    public static final int FLAG_INSISTENT = 4;
    public static final int FLAG_LOCAL_ONLY = 256;
    public static final int FLAG_NO_CLEAR = 32;
    public static final int FLAG_ONGOING_EVENT = 2;
    public static final int FLAG_ONLY_ALERT_ONCE = 8;
    public static final int FLAG_SHOW_LIGHTS = 1;
    public static final int GROUP_ALERT_ALL = 0;
    public static final int GROUP_ALERT_CHILDREN = 2;
    public static final int GROUP_ALERT_SUMMARY = 1;
    public static final String GROUP_KEY_SILENT = "silent";
    @SuppressLint({"ActionValue"})
    public static final String INTENT_CATEGORY_NOTIFICATION_PREFERENCES = "android.intent.category.NOTIFICATION_PREFERENCES";
    public static final int PRIORITY_DEFAULT = 0;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = -1;
    public static final int PRIORITY_MAX = 2;
    public static final int PRIORITY_MIN = -2;
    public static final int STREAM_DEFAULT = -1;
    public static final int VISIBILITY_PRIVATE = 0;
    public static final int VISIBILITY_PUBLIC = 1;
    public static final int VISIBILITY_SECRET = -1;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BadgeIconType {
    }

    public interface Extender {
        @NonNull
        Builder extend(@NonNull Builder builder);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GroupAlertBehavior {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NotificationVisibility {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StreamType {
    }

    public static class Builder {
        private static final int MAX_CHARSEQUENCE_LENGTH = 5120;
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public ArrayList<Action> mActions;
        boolean mAllowSystemGeneratedContextualActions;
        int mBadgeIcon;
        RemoteViews mBigContentView;
        BubbleMetadata mBubbleMetadata;
        String mCategory;
        String mChannelId;
        boolean mChronometerCountDown;
        int mColor;
        boolean mColorized;
        boolean mColorizedSet;
        CharSequence mContentInfo;
        PendingIntent mContentIntent;
        CharSequence mContentText;
        CharSequence mContentTitle;
        RemoteViews mContentView;
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Context mContext;
        Bundle mExtras;
        PendingIntent mFullScreenIntent;
        int mGroupAlertBehavior;
        String mGroupKey;
        boolean mGroupSummary;
        RemoteViews mHeadsUpContentView;
        ArrayList<Action> mInvisibleActions;
        Bitmap mLargeIcon;
        boolean mLocalOnly;
        LocusIdCompat mLocusId;
        Notification mNotification;
        int mNumber;
        @Deprecated
        public ArrayList<String> mPeople;
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public ArrayList<Person> mPersonList;
        int mPriority;
        int mProgress;
        boolean mProgressIndeterminate;
        int mProgressMax;
        Notification mPublicVersion;
        CharSequence[] mRemoteInputHistory;
        CharSequence mSettingsText;
        String mShortcutId;
        boolean mShowWhen;
        boolean mSilent;
        Icon mSmallIcon;
        String mSortKey;
        Style mStyle;
        CharSequence mSubText;
        RemoteViews mTickerView;
        long mTimeout;
        boolean mUseChronometer;
        int mVisibility;

        @RequiresApi(19)
        public Builder(@NonNull Context context, @NonNull Notification notification) {
            this(context, NotificationCompat.getChannelId(notification));
            ArrayList<Person> peopleList;
            Bundle extras = notification.extras;
            Style style = Style.extractStyleFromNotification(notification);
            setContentTitle(NotificationCompat.getContentTitle(notification)).setContentText(NotificationCompat.getContentText(notification)).setContentInfo(NotificationCompat.getContentInfo(notification)).setSubText(NotificationCompat.getSubText(notification)).setSettingsText(NotificationCompat.getSettingsText(notification)).setStyle(style).setContentIntent(notification.contentIntent).setGroup(NotificationCompat.getGroup(notification)).setGroupSummary(NotificationCompat.isGroupSummary(notification)).setLocusId(NotificationCompat.getLocusId(notification)).setWhen(notification.when).setShowWhen(NotificationCompat.getShowWhen(notification)).setUsesChronometer(NotificationCompat.getUsesChronometer(notification)).setAutoCancel(NotificationCompat.getAutoCancel(notification)).setOnlyAlertOnce(NotificationCompat.getOnlyAlertOnce(notification)).setOngoing(NotificationCompat.getOngoing(notification)).setLocalOnly(NotificationCompat.getLocalOnly(notification)).setLargeIcon(notification.largeIcon).setBadgeIconType(NotificationCompat.getBadgeIconType(notification)).setCategory(NotificationCompat.getCategory(notification)).setBubbleMetadata(NotificationCompat.getBubbleMetadata(notification)).setNumber(notification.number).setTicker(notification.tickerText).setContentIntent(notification.contentIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(notification.fullScreenIntent, NotificationCompat.getHighPriority(notification)).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setDefaults(notification.defaults).setPriority(notification.priority).setColor(NotificationCompat.getColor(notification)).setVisibility(NotificationCompat.getVisibility(notification)).setPublicVersion(NotificationCompat.getPublicVersion(notification)).setSortKey(NotificationCompat.getSortKey(notification)).setTimeoutAfter(NotificationCompat.getTimeoutAfter(notification)).setShortcutId(NotificationCompat.getShortcutId(notification)).setProgress(extras.getInt(NotificationCompat.EXTRA_PROGRESS_MAX), extras.getInt(NotificationCompat.EXTRA_PROGRESS), extras.getBoolean(NotificationCompat.EXTRA_PROGRESS_INDETERMINATE)).setAllowSystemGeneratedContextualActions(NotificationCompat.getAllowSystemGeneratedContextualActions(notification)).setSmallIcon(notification.icon, notification.iconLevel).addExtras(getExtrasWithoutDuplicateData(notification, style));
            if (Build.VERSION.SDK_INT >= 23) {
                this.mSmallIcon = notification.getSmallIcon();
            }
            Notification.Action[] actionArr = notification.actions;
            if (!(actionArr == null || actionArr.length == 0)) {
                for (Notification.Action action : actionArr) {
                    addAction(Action.Builder.fromAndroidAction(action).build());
                }
            }
            if (Build.VERSION.SDK_INT >= 21) {
                List<Action> invisibleActions = NotificationCompat.getInvisibleActions(notification);
                if (!invisibleActions.isEmpty()) {
                    for (Action invisibleAction : invisibleActions) {
                        addInvisibleAction(invisibleAction);
                    }
                }
            }
            String[] people = notification.extras.getStringArray(NotificationCompat.EXTRA_PEOPLE);
            if (!(people == null || people.length == 0)) {
                for (String person : people) {
                    addPerson(person);
                }
            }
            if (Build.VERSION.SDK_INT >= 28 && (peopleList = notification.extras.getParcelableArrayList(NotificationCompat.EXTRA_PEOPLE_LIST)) != null && !peopleList.isEmpty()) {
                Iterator<Person> it = peopleList.iterator();
                while (it.hasNext()) {
                    addPerson(Person.fromAndroidPerson(it.next()));
                }
            }
            int i = Build.VERSION.SDK_INT;
            if (i >= 24 && extras.containsKey(NotificationCompat.EXTRA_CHRONOMETER_COUNT_DOWN)) {
                setChronometerCountDown(extras.getBoolean(NotificationCompat.EXTRA_CHRONOMETER_COUNT_DOWN));
            }
            if (i >= 26 && extras.containsKey(NotificationCompat.EXTRA_COLORIZED)) {
                setColorized(extras.getBoolean(NotificationCompat.EXTRA_COLORIZED));
            }
        }

        @RequiresApi(19)
        @Nullable
        private static Bundle getExtrasWithoutDuplicateData(@NonNull Notification notification, @Nullable Style style) {
            if (notification.extras == null) {
                return null;
            }
            Bundle newExtras = new Bundle(notification.extras);
            newExtras.remove(NotificationCompat.EXTRA_TITLE);
            newExtras.remove(NotificationCompat.EXTRA_TEXT);
            newExtras.remove(NotificationCompat.EXTRA_INFO_TEXT);
            newExtras.remove(NotificationCompat.EXTRA_SUB_TEXT);
            newExtras.remove(NotificationCompat.EXTRA_CHANNEL_ID);
            newExtras.remove(NotificationCompat.EXTRA_CHANNEL_GROUP_ID);
            newExtras.remove(NotificationCompat.EXTRA_SHOW_WHEN);
            newExtras.remove(NotificationCompat.EXTRA_PROGRESS);
            newExtras.remove(NotificationCompat.EXTRA_PROGRESS_MAX);
            newExtras.remove(NotificationCompat.EXTRA_PROGRESS_INDETERMINATE);
            newExtras.remove(NotificationCompat.EXTRA_CHRONOMETER_COUNT_DOWN);
            newExtras.remove(NotificationCompat.EXTRA_COLORIZED);
            newExtras.remove(NotificationCompat.EXTRA_PEOPLE_LIST);
            newExtras.remove(NotificationCompat.EXTRA_PEOPLE);
            newExtras.remove(NotificationCompatExtras.EXTRA_SORT_KEY);
            newExtras.remove(NotificationCompatExtras.EXTRA_GROUP_KEY);
            newExtras.remove(NotificationCompatExtras.EXTRA_GROUP_SUMMARY);
            newExtras.remove(NotificationCompatExtras.EXTRA_LOCAL_ONLY);
            newExtras.remove(NotificationCompatExtras.EXTRA_ACTION_EXTRAS);
            Bundle carExtenderExtras = newExtras.getBundle("android.car.EXTENSIONS");
            if (carExtenderExtras != null) {
                Bundle carExtenderExtras2 = new Bundle(carExtenderExtras);
                carExtenderExtras2.remove("invisible_actions");
                newExtras.putBundle("android.car.EXTENSIONS", carExtenderExtras2);
            }
            if (style != null) {
                style.clearCompatExtraKeys(newExtras);
            }
            return newExtras;
        }

        public Builder(@NonNull Context context, @NonNull String channelId) {
            this.mActions = new ArrayList<>();
            this.mPersonList = new ArrayList<>();
            this.mInvisibleActions = new ArrayList<>();
            this.mShowWhen = true;
            this.mLocalOnly = false;
            this.mColor = 0;
            this.mVisibility = 0;
            this.mBadgeIcon = 0;
            this.mGroupAlertBehavior = 0;
            Notification notification = new Notification();
            this.mNotification = notification;
            this.mContext = context;
            this.mChannelId = channelId;
            notification.when = System.currentTimeMillis();
            this.mNotification.audioStreamType = -1;
            this.mPriority = 0;
            this.mPeople = new ArrayList<>();
            this.mAllowSystemGeneratedContextualActions = true;
        }

        @Deprecated
        public Builder(@NonNull Context context) {
            this(context, (String) null);
        }

        @NonNull
        public Builder setWhen(long when) {
            this.mNotification.when = when;
            return this;
        }

        @NonNull
        public Builder setShowWhen(boolean show) {
            this.mShowWhen = show;
            return this;
        }

        @RequiresApi(23)
        @NonNull
        public Builder setSmallIcon(@NonNull IconCompat icon) {
            this.mSmallIcon = icon.toIcon(this.mContext);
            return this;
        }

        @NonNull
        public Builder setUsesChronometer(boolean b) {
            this.mUseChronometer = b;
            return this;
        }

        @RequiresApi(24)
        @NonNull
        public Builder setChronometerCountDown(boolean countsDown) {
            this.mChronometerCountDown = countsDown;
            getExtras().putBoolean(NotificationCompat.EXTRA_CHRONOMETER_COUNT_DOWN, countsDown);
            return this;
        }

        @NonNull
        public Builder setSmallIcon(int icon) {
            this.mNotification.icon = icon;
            return this;
        }

        @NonNull
        public Builder setSmallIcon(int icon, int level) {
            Notification notification = this.mNotification;
            notification.icon = icon;
            notification.iconLevel = level;
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setNotificationSilent() {
            this.mSilent = true;
            return this;
        }

        @NonNull
        public Builder setSilent(boolean silent) {
            this.mSilent = silent;
            return this;
        }

        @NonNull
        public Builder setContentTitle(@Nullable CharSequence title) {
            this.mContentTitle = limitCharSequenceLength(title);
            return this;
        }

        @NonNull
        public Builder setContentText(@Nullable CharSequence text) {
            this.mContentText = limitCharSequenceLength(text);
            return this;
        }

        @NonNull
        public Builder setSubText(@Nullable CharSequence text) {
            this.mSubText = limitCharSequenceLength(text);
            return this;
        }

        @NonNull
        public Builder setSettingsText(@Nullable CharSequence text) {
            this.mSettingsText = limitCharSequenceLength(text);
            return this;
        }

        @NonNull
        public Builder setRemoteInputHistory(@Nullable CharSequence[] text) {
            this.mRemoteInputHistory = text;
            return this;
        }

        @NonNull
        public Builder setNumber(int number) {
            this.mNumber = number;
            return this;
        }

        @NonNull
        public Builder setContentInfo(@Nullable CharSequence info) {
            this.mContentInfo = limitCharSequenceLength(info);
            return this;
        }

        @NonNull
        public Builder setProgress(int max, int progress, boolean indeterminate) {
            this.mProgressMax = max;
            this.mProgress = progress;
            this.mProgressIndeterminate = indeterminate;
            return this;
        }

        @NonNull
        public Builder setContent(@Nullable RemoteViews views) {
            this.mNotification.contentView = views;
            return this;
        }

        @NonNull
        public Builder setContentIntent(@Nullable PendingIntent intent) {
            this.mContentIntent = intent;
            return this;
        }

        @NonNull
        public Builder setDeleteIntent(@Nullable PendingIntent intent) {
            this.mNotification.deleteIntent = intent;
            return this;
        }

        @NonNull
        public Builder setFullScreenIntent(@Nullable PendingIntent intent, boolean highPriority) {
            this.mFullScreenIntent = intent;
            setFlag(128, highPriority);
            return this;
        }

        @NonNull
        public Builder setTicker(@Nullable CharSequence tickerText) {
            this.mNotification.tickerText = limitCharSequenceLength(tickerText);
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setTicker(@Nullable CharSequence tickerText, @Nullable RemoteViews views) {
            this.mNotification.tickerText = limitCharSequenceLength(tickerText);
            this.mTickerView = views;
            return this;
        }

        @NonNull
        public Builder setLargeIcon(@Nullable Bitmap icon) {
            this.mLargeIcon = reduceLargeIconSize(icon);
            return this;
        }

        @Nullable
        private Bitmap reduceLargeIconSize(@Nullable Bitmap icon) {
            if (icon == null || Build.VERSION.SDK_INT >= 27) {
                return icon;
            }
            Resources res = this.mContext.getResources();
            int maxWidth = res.getDimensionPixelSize(R.dimen.compat_notification_large_icon_max_width);
            int maxHeight = res.getDimensionPixelSize(R.dimen.compat_notification_large_icon_max_height);
            if (icon.getWidth() <= maxWidth && icon.getHeight() <= maxHeight) {
                return icon;
            }
            double scale = Math.min(((double) maxWidth) / ((double) Math.max(1, icon.getWidth())), ((double) maxHeight) / ((double) Math.max(1, icon.getHeight())));
            return Bitmap.createScaledBitmap(icon, (int) Math.ceil(((double) icon.getWidth()) * scale), (int) Math.ceil(((double) icon.getHeight()) * scale), true);
        }

        @NonNull
        public Builder setSound(@Nullable Uri sound) {
            Notification notification = this.mNotification;
            notification.sound = sound;
            notification.audioStreamType = -1;
            if (Build.VERSION.SDK_INT >= 21) {
                notification.audioAttributes = new AudioAttributes.Builder().setContentType(4).setUsage(5).build();
            }
            return this;
        }

        @NonNull
        public Builder setSound(@Nullable Uri sound, int streamType) {
            Notification notification = this.mNotification;
            notification.sound = sound;
            notification.audioStreamType = streamType;
            if (Build.VERSION.SDK_INT >= 21) {
                notification.audioAttributes = new AudioAttributes.Builder().setContentType(4).setLegacyStreamType(streamType).build();
            }
            return this;
        }

        @NonNull
        public Builder setVibrate(@Nullable long[] pattern) {
            this.mNotification.vibrate = pattern;
            return this;
        }

        @NonNull
        public Builder setLights(@ColorInt int argb, int onMs, int offMs) {
            Notification notification = this.mNotification;
            notification.ledARGB = argb;
            notification.ledOnMS = onMs;
            notification.ledOffMS = offMs;
            int i = 1;
            boolean showLights = (onMs == 0 || offMs == 0) ? false : true;
            int i2 = notification.flags & -2;
            if (!showLights) {
                i = 0;
            }
            notification.flags = i | i2;
            return this;
        }

        @NonNull
        public Builder setOngoing(boolean ongoing) {
            setFlag(2, ongoing);
            return this;
        }

        @NonNull
        public Builder setColorized(boolean colorize) {
            this.mColorized = colorize;
            this.mColorizedSet = true;
            return this;
        }

        @NonNull
        public Builder setOnlyAlertOnce(boolean onlyAlertOnce) {
            setFlag(8, onlyAlertOnce);
            return this;
        }

        @NonNull
        public Builder setAutoCancel(boolean autoCancel) {
            setFlag(16, autoCancel);
            return this;
        }

        @NonNull
        public Builder setLocalOnly(boolean b) {
            this.mLocalOnly = b;
            return this;
        }

        @NonNull
        public Builder setCategory(@Nullable String category) {
            this.mCategory = category;
            return this;
        }

        @NonNull
        public Builder setDefaults(int defaults) {
            Notification notification = this.mNotification;
            notification.defaults = defaults;
            if ((defaults & 4) != 0) {
                notification.flags |= 1;
            }
            return this;
        }

        private void setFlag(int mask, boolean value) {
            if (value) {
                this.mNotification.flags |= mask;
                return;
            }
            this.mNotification.flags &= ~mask;
        }

        @NonNull
        public Builder setPriority(int pri) {
            this.mPriority = pri;
            return this;
        }

        @NonNull
        @Deprecated
        public Builder addPerson(@Nullable String uri) {
            if (uri != null && !uri.isEmpty()) {
                this.mPeople.add(uri);
            }
            return this;
        }

        @NonNull
        public Builder addPerson(@Nullable Person person) {
            if (person != null) {
                this.mPersonList.add(person);
            }
            return this;
        }

        @NonNull
        public Builder clearPeople() {
            this.mPersonList.clear();
            this.mPeople.clear();
            return this;
        }

        @NonNull
        public Builder setGroup(@Nullable String groupKey) {
            this.mGroupKey = groupKey;
            return this;
        }

        @NonNull
        public Builder setGroupSummary(boolean isGroupSummary) {
            this.mGroupSummary = isGroupSummary;
            return this;
        }

        @NonNull
        public Builder setSortKey(@Nullable String sortKey) {
            this.mSortKey = sortKey;
            return this;
        }

        @NonNull
        public Builder addExtras(@Nullable Bundle extras) {
            if (extras != null) {
                Bundle bundle = this.mExtras;
                if (bundle == null) {
                    this.mExtras = new Bundle(extras);
                } else {
                    bundle.putAll(extras);
                }
            }
            return this;
        }

        @NonNull
        public Builder setExtras(@Nullable Bundle extras) {
            this.mExtras = extras;
            return this;
        }

        @NonNull
        public Bundle getExtras() {
            if (this.mExtras == null) {
                this.mExtras = new Bundle();
            }
            return this.mExtras;
        }

        @NonNull
        public Builder addAction(int icon, @Nullable CharSequence title, @Nullable PendingIntent intent) {
            this.mActions.add(new Action(icon, title, intent));
            return this;
        }

        @NonNull
        public Builder addAction(@Nullable Action action) {
            if (action != null) {
                this.mActions.add(action);
            }
            return this;
        }

        @NonNull
        public Builder clearActions() {
            this.mActions.clear();
            return this;
        }

        @RequiresApi(21)
        @NonNull
        public Builder addInvisibleAction(int icon, @Nullable CharSequence title, @Nullable PendingIntent intent) {
            this.mInvisibleActions.add(new Action(icon, title, intent));
            return this;
        }

        @RequiresApi(21)
        @NonNull
        public Builder addInvisibleAction(@Nullable Action action) {
            if (action != null) {
                this.mInvisibleActions.add(action);
            }
            return this;
        }

        @NonNull
        public Builder clearInvisibleActions() {
            this.mInvisibleActions.clear();
            Bundle carExtenderBundle = this.mExtras.getBundle("android.car.EXTENSIONS");
            if (carExtenderBundle != null) {
                Bundle carExtenderBundle2 = new Bundle(carExtenderBundle);
                carExtenderBundle2.remove("invisible_actions");
                this.mExtras.putBundle("android.car.EXTENSIONS", carExtenderBundle2);
            }
            return this;
        }

        @NonNull
        public Builder setStyle(@Nullable Style style) {
            if (this.mStyle != style) {
                this.mStyle = style;
                if (style != null) {
                    style.setBuilder(this);
                }
            }
            return this;
        }

        @NonNull
        public Builder setColor(@ColorInt int argb) {
            this.mColor = argb;
            return this;
        }

        @NonNull
        public Builder setVisibility(int visibility) {
            this.mVisibility = visibility;
            return this;
        }

        @NonNull
        public Builder setPublicVersion(@Nullable Notification n) {
            this.mPublicVersion = n;
            return this;
        }

        private boolean useExistingRemoteView() {
            Style style = this.mStyle;
            return style == null || !style.displayCustomViewInline();
        }

        @SuppressLint({"BuilderSetStyle"})
        @Nullable
        public RemoteViews createContentView() {
            RemoteViews styleView;
            if (this.mContentView != null && useExistingRemoteView()) {
                return this.mContentView;
            }
            NotificationCompatBuilder compatBuilder = new NotificationCompatBuilder(this);
            Style style = this.mStyle;
            if (style != null && (styleView = style.makeContentView(compatBuilder)) != null) {
                return styleView;
            }
            Notification notification = compatBuilder.build();
            if (Build.VERSION.SDK_INT >= 24) {
                return Notification.Builder.recoverBuilder(this.mContext, notification).createContentView();
            }
            return notification.contentView;
        }

        @SuppressLint({"BuilderSetStyle"})
        @Nullable
        public RemoteViews createBigContentView() {
            RemoteViews styleView;
            int i = Build.VERSION.SDK_INT;
            if (i < 16) {
                return null;
            }
            if (this.mBigContentView != null && useExistingRemoteView()) {
                return this.mBigContentView;
            }
            NotificationCompatBuilder compatBuilder = new NotificationCompatBuilder(this);
            Style style = this.mStyle;
            if (style != null && (styleView = style.makeBigContentView(compatBuilder)) != null) {
                return styleView;
            }
            Notification notification = compatBuilder.build();
            if (i >= 24) {
                return Notification.Builder.recoverBuilder(this.mContext, notification).createBigContentView();
            }
            return notification.bigContentView;
        }

        @SuppressLint({"BuilderSetStyle"})
        @Nullable
        public RemoteViews createHeadsUpContentView() {
            RemoteViews styleView;
            int i = Build.VERSION.SDK_INT;
            if (i < 21) {
                return null;
            }
            if (this.mHeadsUpContentView != null && useExistingRemoteView()) {
                return this.mHeadsUpContentView;
            }
            NotificationCompatBuilder compatBuilder = new NotificationCompatBuilder(this);
            Style style = this.mStyle;
            if (style != null && (styleView = style.makeHeadsUpContentView(compatBuilder)) != null) {
                return styleView;
            }
            Notification notification = compatBuilder.build();
            if (i >= 24) {
                return Notification.Builder.recoverBuilder(this.mContext, notification).createHeadsUpContentView();
            }
            return notification.headsUpContentView;
        }

        @NonNull
        public Builder setCustomContentView(@Nullable RemoteViews contentView) {
            this.mContentView = contentView;
            return this;
        }

        @NonNull
        public Builder setCustomBigContentView(@Nullable RemoteViews contentView) {
            this.mBigContentView = contentView;
            return this;
        }

        @NonNull
        public Builder setCustomHeadsUpContentView(@Nullable RemoteViews contentView) {
            this.mHeadsUpContentView = contentView;
            return this;
        }

        @NonNull
        public Builder setChannelId(@NonNull String channelId) {
            this.mChannelId = channelId;
            return this;
        }

        @NonNull
        public Builder setTimeoutAfter(long durationMs) {
            this.mTimeout = durationMs;
            return this;
        }

        @NonNull
        public Builder setShortcutId(@Nullable String shortcutId) {
            this.mShortcutId = shortcutId;
            return this;
        }

        @NonNull
        public Builder setShortcutInfo(@Nullable ShortcutInfoCompat shortcutInfo) {
            if (shortcutInfo == null) {
                return this;
            }
            this.mShortcutId = shortcutInfo.getId();
            if (this.mLocusId == null) {
                if (shortcutInfo.getLocusId() != null) {
                    this.mLocusId = shortcutInfo.getLocusId();
                } else if (shortcutInfo.getId() != null) {
                    this.mLocusId = new LocusIdCompat(shortcutInfo.getId());
                }
            }
            if (this.mContentTitle == null) {
                setContentTitle(shortcutInfo.getShortLabel());
            }
            return this;
        }

        @NonNull
        public Builder setLocusId(@Nullable LocusIdCompat locusId) {
            this.mLocusId = locusId;
            return this;
        }

        @NonNull
        public Builder setBadgeIconType(int icon) {
            this.mBadgeIcon = icon;
            return this;
        }

        @NonNull
        public Builder setGroupAlertBehavior(int groupAlertBehavior) {
            this.mGroupAlertBehavior = groupAlertBehavior;
            return this;
        }

        @NonNull
        public Builder setBubbleMetadata(@Nullable BubbleMetadata data) {
            this.mBubbleMetadata = data;
            return this;
        }

        @NonNull
        public Builder extend(@NonNull Extender extender) {
            extender.extend(this);
            return this;
        }

        @NonNull
        public Builder setAllowSystemGeneratedContextualActions(boolean allowed) {
            this.mAllowSystemGeneratedContextualActions = allowed;
            return this;
        }

        @NonNull
        @Deprecated
        public Notification getNotification() {
            return build();
        }

        @NonNull
        public Notification build() {
            return new NotificationCompatBuilder(this).build();
        }

        @Nullable
        protected static CharSequence limitCharSequenceLength(@Nullable CharSequence cs) {
            if (cs != null && cs.length() > MAX_CHARSEQUENCE_LENGTH) {
                return cs.subSequence(0, MAX_CHARSEQUENCE_LENGTH);
            }
            return cs;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews getContentView() {
            return this.mContentView;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews getBigContentView() {
            return this.mBigContentView;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews getHeadsUpContentView() {
            return this.mHeadsUpContentView;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public long getWhenIfShowing() {
            if (this.mShowWhen) {
                return this.mNotification.when;
            }
            return 0;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public int getPriority() {
            return this.mPriority;
        }

        @ColorInt
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public int getColor() {
            return this.mColor;
        }

        @Nullable
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public BubbleMetadata getBubbleMetadata() {
            return this.mBubbleMetadata;
        }
    }

    public static abstract class Style {
        CharSequence mBigContentTitle;
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected Builder mBuilder;
        CharSequence mSummaryText;
        boolean mSummaryTextSet = false;

        public void setBuilder(@Nullable Builder builder) {
            if (this.mBuilder != builder) {
                this.mBuilder = builder;
                if (builder != null) {
                    builder.setStyle(this);
                }
            }
        }

        @Nullable
        public Notification build() {
            Builder builder = this.mBuilder;
            if (builder != null) {
                return builder.build();
            }
            return null;
        }

        /* access modifiers changed from: protected */
        @Nullable
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public String getClassName() {
            return null;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public boolean displayCustomViewInline() {
            return false;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor builder) {
            return null;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor builder) {
            return null;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor builder) {
            return null;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void addCompatExtras(@NonNull Bundle extras) {
            if (this.mSummaryTextSet) {
                extras.putCharSequence(NotificationCompat.EXTRA_SUMMARY_TEXT, this.mSummaryText);
            }
            CharSequence charSequence = this.mBigContentTitle;
            if (charSequence != null) {
                extras.putCharSequence(NotificationCompat.EXTRA_TITLE_BIG, charSequence);
            }
            String className = getClassName();
            if (className != null) {
                extras.putString(NotificationCompat.EXTRA_COMPAT_TEMPLATE, className);
            }
        }

        /* access modifiers changed from: protected */
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void restoreFromCompatExtras(@NonNull Bundle extras) {
            if (extras.containsKey(NotificationCompat.EXTRA_SUMMARY_TEXT)) {
                this.mSummaryText = extras.getCharSequence(NotificationCompat.EXTRA_SUMMARY_TEXT);
                this.mSummaryTextSet = true;
            }
            this.mBigContentTitle = extras.getCharSequence(NotificationCompat.EXTRA_TITLE_BIG);
        }

        /* access modifiers changed from: protected */
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void clearCompatExtraKeys(@NonNull Bundle extras) {
            extras.remove(NotificationCompat.EXTRA_SUMMARY_TEXT);
            extras.remove(NotificationCompat.EXTRA_TITLE_BIG);
            extras.remove(NotificationCompat.EXTRA_COMPAT_TEMPLATE);
        }

        @Nullable
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public static Style extractStyleFromNotification(@NonNull Notification notification) {
            Bundle extras = NotificationCompat.getExtras(notification);
            if (extras == null) {
                return null;
            }
            return constructStyleForExtras(extras);
        }

        @Nullable
        private static Style constructCompatStyleByPlatformName(@Nullable String platformTemplateClass) {
            int i;
            if (platformTemplateClass != null && (i = Build.VERSION.SDK_INT) >= 16) {
                if (platformTemplateClass.equals(Notification.BigPictureStyle.class.getName())) {
                    return new BigPictureStyle();
                }
                if (platformTemplateClass.equals(Notification.BigTextStyle.class.getName())) {
                    return new BigTextStyle();
                }
                if (platformTemplateClass.equals(Notification.InboxStyle.class.getName())) {
                    return new InboxStyle();
                }
                if (i >= 24) {
                    if (platformTemplateClass.equals(Notification.MessagingStyle.class.getName())) {
                        return new MessagingStyle();
                    }
                    if (platformTemplateClass.equals(Notification.DecoratedCustomViewStyle.class.getName())) {
                        return new DecoratedCustomViewStyle();
                    }
                }
            }
            return null;
        }

        @Nullable
        static Style constructCompatStyleByName(@Nullable String templateClass) {
            if (templateClass == null) {
                return null;
            }
            char c = 65535;
            switch (templateClass.hashCode()) {
                case -716705180:
                    if (templateClass.equals("androidx.core.app.NotificationCompat$DecoratedCustomViewStyle")) {
                        c = 3;
                        break;
                    }
                    break;
                case -171946061:
                    if (templateClass.equals("androidx.core.app.NotificationCompat$BigPictureStyle")) {
                        c = 1;
                        break;
                    }
                    break;
                case 912942987:
                    if (templateClass.equals("androidx.core.app.NotificationCompat$InboxStyle")) {
                        c = 2;
                        break;
                    }
                    break;
                case 919595044:
                    if (templateClass.equals("androidx.core.app.NotificationCompat$BigTextStyle")) {
                        c = 0;
                        break;
                    }
                    break;
                case 2090799565:
                    if (templateClass.equals("androidx.core.app.NotificationCompat$MessagingStyle")) {
                        c = 4;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    return new BigTextStyle();
                case 1:
                    return new BigPictureStyle();
                case 2:
                    return new InboxStyle();
                case 3:
                    return new DecoratedCustomViewStyle();
                case 4:
                    return new MessagingStyle();
                default:
                    return null;
            }
        }

        @Nullable
        static Style constructCompatStyleForBundle(@NonNull Bundle extras) {
            Style style = constructCompatStyleByName(extras.getString(NotificationCompat.EXTRA_COMPAT_TEMPLATE));
            if (style != null) {
                return style;
            }
            if (extras.containsKey(NotificationCompat.EXTRA_SELF_DISPLAY_NAME) || extras.containsKey(NotificationCompat.EXTRA_MESSAGING_STYLE_USER)) {
                return new MessagingStyle();
            }
            if (extras.containsKey(NotificationCompat.EXTRA_PICTURE)) {
                return new BigPictureStyle();
            }
            if (extras.containsKey(NotificationCompat.EXTRA_BIG_TEXT)) {
                return new BigTextStyle();
            }
            if (extras.containsKey(NotificationCompat.EXTRA_TEXT_LINES)) {
                return new InboxStyle();
            }
            return constructCompatStyleByPlatformName(extras.getString(NotificationCompat.EXTRA_TEMPLATE));
        }

        @Nullable
        static Style constructStyleForExtras(@NonNull Bundle extras) {
            Style style = constructCompatStyleForBundle(extras);
            if (style == null) {
                return null;
            }
            try {
                style.restoreFromCompatExtras(extras);
                return style;
            } catch (ClassCastException e) {
                return null;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:66:0x019d  */
        /* JADX WARNING: Removed duplicated region for block: B:69:0x01bb  */
        /* JADX WARNING: Removed duplicated region for block: B:72:0x01c9  */
        /* JADX WARNING: Removed duplicated region for block: B:85:0x021a  */
        /* JADX WARNING: Removed duplicated region for block: B:86:0x021c  */
        /* JADX WARNING: Removed duplicated region for block: B:89:0x0226  */
        @androidx.annotation.NonNull
        @androidx.annotation.RestrictTo({androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.widget.RemoteViews applyStandardTemplate(boolean r20, int r21, boolean r22) {
            /*
                r19 = this;
                r0 = r19
                androidx.core.app.NotificationCompat$Builder r1 = r0.mBuilder
                android.content.Context r1 = r1.mContext
                android.content.res.Resources r1 = r1.getResources()
                android.widget.RemoteViews r2 = new android.widget.RemoteViews
                androidx.core.app.NotificationCompat$Builder r3 = r0.mBuilder
                android.content.Context r3 = r3.mContext
                java.lang.String r3 = r3.getPackageName()
                r4 = r21
                r2.<init>(r3, r4)
                r3 = 0
                r5 = 0
                androidx.core.app.NotificationCompat$Builder r6 = r0.mBuilder
                int r6 = r6.getPriority()
                r7 = -1
                r12 = 0
                if (r6 >= r7) goto L_0x0027
                r6 = 1
                goto L_0x0028
            L_0x0027:
                r6 = r12
            L_0x0028:
                r13 = r6
                int r14 = android.os.Build.VERSION.SDK_INT
                r6 = 21
                r15 = 16
                if (r14 < r15) goto L_0x0055
                if (r14 >= r6) goto L_0x0055
                java.lang.String r8 = "setBackgroundResource"
                if (r13 == 0) goto L_0x0047
                int r9 = androidx.core.R.id.notification_background
                int r10 = androidx.core.R.drawable.notification_bg_low
                r2.setInt(r9, r8, r10)
                int r9 = androidx.core.R.id.icon
                int r10 = androidx.core.R.drawable.notification_template_icon_low_bg
                r2.setInt(r9, r8, r10)
                goto L_0x0055
            L_0x0047:
                int r9 = androidx.core.R.id.notification_background
                int r10 = androidx.core.R.drawable.notification_bg
                r2.setInt(r9, r8, r10)
                int r9 = androidx.core.R.id.icon
                int r10 = androidx.core.R.drawable.notification_template_icon_bg
                r2.setInt(r9, r8, r10)
            L_0x0055:
                androidx.core.app.NotificationCompat$Builder r8 = r0.mBuilder
                android.graphics.Bitmap r9 = r8.mLargeIcon
                r10 = 8
                if (r9 == 0) goto L_0x00b6
                if (r14 < r15) goto L_0x006c
                int r8 = androidx.core.R.id.icon
                r2.setViewVisibility(r8, r12)
                androidx.core.app.NotificationCompat$Builder r9 = r0.mBuilder
                android.graphics.Bitmap r9 = r9.mLargeIcon
                r2.setImageViewBitmap(r8, r9)
                goto L_0x0071
            L_0x006c:
                int r8 = androidx.core.R.id.icon
                r2.setViewVisibility(r8, r10)
            L_0x0071:
                if (r20 == 0) goto L_0x00f7
                androidx.core.app.NotificationCompat$Builder r8 = r0.mBuilder
                android.app.Notification r8 = r8.mNotification
                int r8 = r8.icon
                if (r8 == 0) goto L_0x00f7
                int r8 = androidx.core.R.dimen.notification_right_icon_size
                int r8 = r1.getDimensionPixelSize(r8)
                int r9 = androidx.core.R.dimen.notification_small_icon_background_padding
                int r9 = r1.getDimensionPixelSize(r9)
                int r9 = r9 * 2
                int r9 = r8 - r9
                if (r14 < r6) goto L_0x00a1
                androidx.core.app.NotificationCompat$Builder r7 = r0.mBuilder
                android.app.Notification r11 = r7.mNotification
                int r11 = r11.icon
                int r7 = r7.getColor()
                android.graphics.Bitmap r7 = r0.createIconWithBackground(r11, r8, r9, r7)
                int r11 = androidx.core.R.id.right_icon
                r2.setImageViewBitmap(r11, r7)
                goto L_0x00b0
            L_0x00a1:
                int r11 = androidx.core.R.id.right_icon
                androidx.core.app.NotificationCompat$Builder r15 = r0.mBuilder
                android.app.Notification r15 = r15.mNotification
                int r15 = r15.icon
                android.graphics.Bitmap r7 = r0.createColoredBitmap((int) r15, (int) r7)
                r2.setImageViewBitmap(r11, r7)
            L_0x00b0:
                int r7 = androidx.core.R.id.right_icon
                r2.setViewVisibility(r7, r12)
                goto L_0x00f7
            L_0x00b6:
                if (r20 == 0) goto L_0x00f7
                android.app.Notification r8 = r8.mNotification
                int r8 = r8.icon
                if (r8 == 0) goto L_0x00f7
                int r8 = androidx.core.R.id.icon
                r2.setViewVisibility(r8, r12)
                if (r14 < r6) goto L_0x00ea
                int r7 = androidx.core.R.dimen.notification_large_icon_width
                int r7 = r1.getDimensionPixelSize(r7)
                int r9 = androidx.core.R.dimen.notification_big_circle_margin
                int r9 = r1.getDimensionPixelSize(r9)
                int r7 = r7 - r9
                int r9 = androidx.core.R.dimen.notification_small_icon_size_as_large
                int r9 = r1.getDimensionPixelSize(r9)
                androidx.core.app.NotificationCompat$Builder r11 = r0.mBuilder
                android.app.Notification r15 = r11.mNotification
                int r15 = r15.icon
                int r11 = r11.getColor()
                android.graphics.Bitmap r11 = r0.createIconWithBackground(r15, r7, r9, r11)
                r2.setImageViewBitmap(r8, r11)
                goto L_0x00f7
            L_0x00ea:
                androidx.core.app.NotificationCompat$Builder r9 = r0.mBuilder
                android.app.Notification r9 = r9.mNotification
                int r9 = r9.icon
                android.graphics.Bitmap r7 = r0.createColoredBitmap((int) r9, (int) r7)
                r2.setImageViewBitmap(r8, r7)
            L_0x00f7:
                androidx.core.app.NotificationCompat$Builder r7 = r0.mBuilder
                java.lang.CharSequence r7 = r7.mContentTitle
                if (r7 == 0) goto L_0x0102
                int r8 = androidx.core.R.id.title
                r2.setTextViewText(r8, r7)
            L_0x0102:
                androidx.core.app.NotificationCompat$Builder r7 = r0.mBuilder
                java.lang.CharSequence r7 = r7.mContentText
                if (r7 == 0) goto L_0x010e
                int r8 = androidx.core.R.id.text
                r2.setTextViewText(r8, r7)
                r3 = 1
            L_0x010e:
                if (r14 >= r6) goto L_0x0118
                androidx.core.app.NotificationCompat$Builder r6 = r0.mBuilder
                android.graphics.Bitmap r6 = r6.mLargeIcon
                if (r6 == 0) goto L_0x0118
                r6 = 1
                goto L_0x0119
            L_0x0118:
                r6 = r12
            L_0x0119:
                androidx.core.app.NotificationCompat$Builder r7 = r0.mBuilder
                java.lang.CharSequence r8 = r7.mContentInfo
                if (r8 == 0) goto L_0x012b
                int r7 = androidx.core.R.id.info
                r2.setTextViewText(r7, r8)
                r2.setViewVisibility(r7, r12)
                r3 = 1
                r6 = 1
                r11 = r6
                goto L_0x016a
            L_0x012b:
                int r7 = r7.mNumber
                if (r7 <= 0) goto L_0x0162
                int r7 = androidx.core.R.integer.status_bar_notification_info_maxnum
                int r7 = r1.getInteger(r7)
                androidx.core.app.NotificationCompat$Builder r8 = r0.mBuilder
                int r8 = r8.mNumber
                if (r8 <= r7) goto L_0x0147
                int r8 = androidx.core.R.id.info
                int r9 = androidx.core.R.string.status_bar_notification_info_overflow
                java.lang.String r9 = r1.getString(r9)
                r2.setTextViewText(r8, r9)
                goto L_0x0159
            L_0x0147:
                java.text.NumberFormat r8 = java.text.NumberFormat.getIntegerInstance()
                int r9 = androidx.core.R.id.info
                androidx.core.app.NotificationCompat$Builder r11 = r0.mBuilder
                int r11 = r11.mNumber
                long r10 = (long) r11
                java.lang.String r10 = r8.format(r10)
                r2.setTextViewText(r9, r10)
            L_0x0159:
                int r8 = androidx.core.R.id.info
                r2.setViewVisibility(r8, r12)
                r3 = 1
                r6 = 1
                r11 = r6
                goto L_0x016a
            L_0x0162:
                int r7 = androidx.core.R.id.info
                r8 = 8
                r2.setViewVisibility(r7, r8)
                r11 = r6
            L_0x016a:
                androidx.core.app.NotificationCompat$Builder r6 = r0.mBuilder
                java.lang.CharSequence r6 = r6.mSubText
                if (r6 == 0) goto L_0x0194
                r7 = 16
                if (r14 < r7) goto L_0x0194
                int r7 = androidx.core.R.id.text
                r2.setTextViewText(r7, r6)
                androidx.core.app.NotificationCompat$Builder r6 = r0.mBuilder
                java.lang.CharSequence r6 = r6.mContentText
                if (r6 == 0) goto L_0x018c
                int r7 = androidx.core.R.id.text2
                r2.setTextViewText(r7, r6)
                r2.setViewVisibility(r7, r12)
                r5 = 1
                r15 = r5
                r10 = 8
                goto L_0x0197
            L_0x018c:
                int r6 = androidx.core.R.id.text2
                r10 = 8
                r2.setViewVisibility(r6, r10)
                goto L_0x0196
            L_0x0194:
                r10 = 8
            L_0x0196:
                r15 = r5
            L_0x0197:
                if (r15 == 0) goto L_0x01bb
                r5 = 16
                if (r14 < r5) goto L_0x01bb
                if (r22 == 0) goto L_0x01ab
                int r5 = androidx.core.R.dimen.notification_subtext_size
                int r5 = r1.getDimensionPixelSize(r5)
                float r5 = (float) r5
                int r6 = androidx.core.R.id.text
                r2.setTextViewTextSize(r6, r12, r5)
            L_0x01ab:
                int r6 = androidx.core.R.id.line1
                r7 = 0
                r8 = 0
                r9 = 0
                r17 = 0
                r5 = r2
                r18 = r10
                r10 = r17
                r5.setViewPadding(r6, r7, r8, r9, r10)
                goto L_0x01bd
            L_0x01bb:
                r18 = r10
            L_0x01bd:
                androidx.core.app.NotificationCompat$Builder r5 = r0.mBuilder
                long r5 = r5.getWhenIfShowing()
                r7 = 0
                int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                if (r5 == 0) goto L_0x0216
                androidx.core.app.NotificationCompat$Builder r5 = r0.mBuilder
                boolean r5 = r5.mUseChronometer
                if (r5 == 0) goto L_0x0204
                r5 = 16
                if (r14 < r5) goto L_0x0204
                int r5 = androidx.core.R.id.chronometer
                r2.setViewVisibility(r5, r12)
                androidx.core.app.NotificationCompat$Builder r6 = r0.mBuilder
                long r6 = r6.getWhenIfShowing()
                long r8 = android.os.SystemClock.elapsedRealtime()
                long r16 = java.lang.System.currentTimeMillis()
                long r8 = r8 - r16
                long r6 = r6 + r8
                java.lang.String r8 = "setBase"
                r2.setLong(r5, r8, r6)
                java.lang.String r6 = "setStarted"
                r7 = 1
                r2.setBoolean(r5, r6, r7)
                androidx.core.app.NotificationCompat$Builder r6 = r0.mBuilder
                boolean r6 = r6.mChronometerCountDown
                if (r6 == 0) goto L_0x0215
                r7 = 24
                if (r14 < r7) goto L_0x0215
                r2.setChronometerCountDown(r5, r6)
                goto L_0x0215
            L_0x0204:
                int r5 = androidx.core.R.id.time
                r2.setViewVisibility(r5, r12)
                androidx.core.app.NotificationCompat$Builder r6 = r0.mBuilder
                long r6 = r6.getWhenIfShowing()
                java.lang.String r8 = "setTime"
                r2.setLong(r5, r8, r6)
            L_0x0215:
                r11 = 1
            L_0x0216:
                int r5 = androidx.core.R.id.right_side
                if (r11 == 0) goto L_0x021c
                r10 = r12
                goto L_0x021e
            L_0x021c:
                r10 = r18
            L_0x021e:
                r2.setViewVisibility(r5, r10)
                int r5 = androidx.core.R.id.line3
                if (r3 == 0) goto L_0x0226
                goto L_0x0228
            L_0x0226:
                r12 = r18
            L_0x0228:
                r2.setViewVisibility(r5, r12)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.app.NotificationCompat.Style.applyStandardTemplate(boolean, int, boolean):android.widget.RemoteViews");
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Bitmap createColoredBitmap(int iconId, int color) {
            return createColoredBitmap(iconId, color, 0);
        }

        /* access modifiers changed from: package-private */
        public Bitmap createColoredBitmap(@NonNull IconCompat icon, int color) {
            return createColoredBitmap(icon, color, 0);
        }

        private Bitmap createColoredBitmap(int iconId, int color, int size) {
            return createColoredBitmap(IconCompat.createWithResource(this.mBuilder.mContext, iconId), color, size);
        }

        private Bitmap createColoredBitmap(@NonNull IconCompat icon, int color, int size) {
            Drawable drawable = icon.loadDrawable(this.mBuilder.mContext);
            int width = size == 0 ? drawable.getIntrinsicWidth() : size;
            int height = size == 0 ? drawable.getIntrinsicHeight() : size;
            Bitmap resultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            drawable.setBounds(0, 0, width, height);
            if (color != 0) {
                drawable.mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            }
            drawable.draw(new Canvas(resultBitmap));
            return resultBitmap;
        }

        private Bitmap createIconWithBackground(int iconId, int size, int iconSize, int color) {
            Bitmap coloredBitmap = createColoredBitmap(R.drawable.notification_icon_background, color == 0 ? 0 : color, size);
            Canvas canvas = new Canvas(coloredBitmap);
            Drawable icon = this.mBuilder.mContext.getResources().getDrawable(iconId).mutate();
            icon.setFilterBitmap(true);
            int inset = (size - iconSize) / 2;
            icon.setBounds(inset, inset, iconSize + inset, iconSize + inset);
            icon.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_ATOP));
            icon.draw(canvas);
            return coloredBitmap;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void buildIntoRemoteViews(RemoteViews outerView, RemoteViews innerView) {
            hideNormalContent(outerView);
            int i = R.id.notification_main_column;
            outerView.removeAllViews(i);
            outerView.addView(i, innerView.clone());
            outerView.setViewVisibility(i, 0);
            if (Build.VERSION.SDK_INT >= 21) {
                outerView.setViewPadding(R.id.notification_main_column_container, 0, calculateTopPadding(), 0, 0);
            }
        }

        private void hideNormalContent(RemoteViews outerView) {
            outerView.setViewVisibility(R.id.title, 8);
            outerView.setViewVisibility(R.id.text2, 8);
            outerView.setViewVisibility(R.id.text, 8);
        }

        private int calculateTopPadding() {
            Resources resources = this.mBuilder.mContext.getResources();
            int padding = resources.getDimensionPixelSize(R.dimen.notification_top_pad);
            int largePadding = resources.getDimensionPixelSize(R.dimen.notification_top_pad_large_text);
            float largeFactor = (constrain(resources.getConfiguration().fontScale, 1.0f, 1.3f) - 1.0f) / 0.29999995f;
            return Math.round(((1.0f - largeFactor) * ((float) padding)) + (((float) largePadding) * largeFactor));
        }

        private static float constrain(float amount, float low, float high) {
            if (amount < low) {
                return low;
            }
            return amount > high ? high : amount;
        }
    }

    public static class BigPictureStyle extends Style {
        private static final String TEMPLATE_CLASS_NAME = "androidx.core.app.NotificationCompat$BigPictureStyle";
        private IconCompat mBigLargeIcon;
        private boolean mBigLargeIconSet;
        private Bitmap mPicture;

        public BigPictureStyle() {
        }

        public BigPictureStyle(@Nullable Builder builder) {
            setBuilder(builder);
        }

        @NonNull
        public BigPictureStyle setBigContentTitle(@Nullable CharSequence title) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(title);
            return this;
        }

        @NonNull
        public BigPictureStyle setSummaryText(@Nullable CharSequence cs) {
            this.mSummaryText = Builder.limitCharSequenceLength(cs);
            this.mSummaryTextSet = true;
            return this;
        }

        @NonNull
        public BigPictureStyle bigPicture(@Nullable Bitmap b) {
            this.mPicture = b;
            return this;
        }

        @NonNull
        public BigPictureStyle bigLargeIcon(@Nullable Bitmap b) {
            this.mBigLargeIcon = b == null ? null : IconCompat.createWithBitmap(b);
            this.mBigLargeIconSet = true;
            return this;
        }

        /* access modifiers changed from: protected */
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public String getClassName() {
            return TEMPLATE_CLASS_NAME;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            int i = Build.VERSION.SDK_INT;
            if (i >= 16) {
                Notification.BigPictureStyle style = new Notification.BigPictureStyle(builder.getBuilder()).setBigContentTitle(this.mBigContentTitle).bigPicture(this.mPicture);
                if (this.mBigLargeIconSet) {
                    IconCompat iconCompat = this.mBigLargeIcon;
                    if (iconCompat == null) {
                        Api16Impl.setBigLargeIcon(style, (Bitmap) null);
                    } else if (i >= 23) {
                        Context context = null;
                        if (builder instanceof NotificationCompatBuilder) {
                            context = ((NotificationCompatBuilder) builder).getContext();
                        }
                        Api23Impl.setBigLargeIcon(style, this.mBigLargeIcon.toIcon(context));
                    } else if (iconCompat.getType() == 1) {
                        Api16Impl.setBigLargeIcon(style, this.mBigLargeIcon.getBitmap());
                    } else {
                        Api16Impl.setBigLargeIcon(style, (Bitmap) null);
                    }
                }
                if (this.mSummaryTextSet) {
                    Api16Impl.setSummaryText(style, this.mSummaryText);
                }
            }
        }

        /* access modifiers changed from: protected */
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void restoreFromCompatExtras(@NonNull Bundle extras) {
            super.restoreFromCompatExtras(extras);
            if (extras.containsKey(NotificationCompat.EXTRA_LARGE_ICON_BIG)) {
                this.mBigLargeIcon = asIconCompat(extras.getParcelable(NotificationCompat.EXTRA_LARGE_ICON_BIG));
                this.mBigLargeIconSet = true;
            }
            this.mPicture = (Bitmap) extras.getParcelable(NotificationCompat.EXTRA_PICTURE);
        }

        @Nullable
        private static IconCompat asIconCompat(@Nullable Parcelable bitmapOrIcon) {
            if (bitmapOrIcon == null) {
                return null;
            }
            if (Build.VERSION.SDK_INT >= 23 && (bitmapOrIcon instanceof Icon)) {
                return IconCompat.createFromIcon((Icon) bitmapOrIcon);
            }
            if (bitmapOrIcon instanceof Bitmap) {
                return IconCompat.createWithBitmap((Bitmap) bitmapOrIcon);
            }
            return null;
        }

        /* access modifiers changed from: protected */
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void clearCompatExtraKeys(@NonNull Bundle extras) {
            super.clearCompatExtraKeys(extras);
            extras.remove(NotificationCompat.EXTRA_LARGE_ICON_BIG);
            extras.remove(NotificationCompat.EXTRA_PICTURE);
        }

        @RequiresApi(16)
        public static class Api16Impl {
            private Api16Impl() {
            }

            @RequiresApi(16)
            static void setBigLargeIcon(Notification.BigPictureStyle style, Bitmap icon) {
                style.bigLargeIcon(icon);
            }

            @RequiresApi(16)
            static void setSummaryText(Notification.BigPictureStyle style, CharSequence text) {
                style.setSummaryText(text);
            }
        }

        @RequiresApi(23)
        public static class Api23Impl {
            private Api23Impl() {
            }

            @RequiresApi(23)
            static void setBigLargeIcon(Notification.BigPictureStyle style, Icon icon) {
                style.bigLargeIcon(icon);
            }
        }
    }

    public static class BigTextStyle extends Style {
        private static final String TEMPLATE_CLASS_NAME = "androidx.core.app.NotificationCompat$BigTextStyle";
        private CharSequence mBigText;

        public BigTextStyle() {
        }

        public BigTextStyle(@Nullable Builder builder) {
            setBuilder(builder);
        }

        @NonNull
        public BigTextStyle setBigContentTitle(@Nullable CharSequence title) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(title);
            return this;
        }

        @NonNull
        public BigTextStyle setSummaryText(@Nullable CharSequence cs) {
            this.mSummaryText = Builder.limitCharSequenceLength(cs);
            this.mSummaryTextSet = true;
            return this;
        }

        @NonNull
        public BigTextStyle bigText(@Nullable CharSequence cs) {
            this.mBigText = Builder.limitCharSequenceLength(cs);
            return this;
        }

        /* access modifiers changed from: protected */
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public String getClassName() {
            return TEMPLATE_CLASS_NAME;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            if (Build.VERSION.SDK_INT >= 16) {
                Notification.BigTextStyle style = new Notification.BigTextStyle(builder.getBuilder()).setBigContentTitle(this.mBigContentTitle).bigText(this.mBigText);
                if (this.mSummaryTextSet) {
                    style.setSummaryText(this.mSummaryText);
                }
            }
        }

        /* access modifiers changed from: protected */
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void restoreFromCompatExtras(@NonNull Bundle extras) {
            super.restoreFromCompatExtras(extras);
            this.mBigText = extras.getCharSequence(NotificationCompat.EXTRA_BIG_TEXT);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void addCompatExtras(@NonNull Bundle extras) {
            super.addCompatExtras(extras);
            if (Build.VERSION.SDK_INT < 21) {
                extras.putCharSequence(NotificationCompat.EXTRA_BIG_TEXT, this.mBigText);
            }
        }

        /* access modifiers changed from: protected */
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void clearCompatExtraKeys(@NonNull Bundle extras) {
            super.clearCompatExtraKeys(extras);
            extras.remove(NotificationCompat.EXTRA_BIG_TEXT);
        }
    }

    public static class MessagingStyle extends Style {
        public static final int MAXIMUM_RETAINED_MESSAGES = 25;
        private static final String TEMPLATE_CLASS_NAME = "androidx.core.app.NotificationCompat$MessagingStyle";
        @Nullable
        private CharSequence mConversationTitle;
        private final List<Message> mHistoricMessages = new ArrayList();
        @Nullable
        private Boolean mIsGroupConversation;
        private final List<Message> mMessages = new ArrayList();
        private Person mUser;

        MessagingStyle() {
        }

        @Deprecated
        public MessagingStyle(@NonNull CharSequence userDisplayName) {
            this.mUser = new Person.Builder().setName(userDisplayName).build();
        }

        public MessagingStyle(@NonNull Person user) {
            if (!TextUtils.isEmpty(user.getName())) {
                this.mUser = user;
                return;
            }
            throw new IllegalArgumentException("User's name must not be empty.");
        }

        @Deprecated
        @Nullable
        public CharSequence getUserDisplayName() {
            return this.mUser.getName();
        }

        @NonNull
        public Person getUser() {
            return this.mUser;
        }

        @NonNull
        public MessagingStyle setConversationTitle(@Nullable CharSequence conversationTitle) {
            this.mConversationTitle = conversationTitle;
            return this;
        }

        @Nullable
        public CharSequence getConversationTitle() {
            return this.mConversationTitle;
        }

        @NonNull
        @Deprecated
        public MessagingStyle addMessage(@Nullable CharSequence text, long timestamp, @Nullable CharSequence sender) {
            this.mMessages.add(new Message(text, timestamp, new Person.Builder().setName(sender).build()));
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(0);
            }
            return this;
        }

        @NonNull
        public MessagingStyle addMessage(@Nullable CharSequence text, long timestamp, @Nullable Person person) {
            addMessage(new Message(text, timestamp, person));
            return this;
        }

        @NonNull
        public MessagingStyle addMessage(@Nullable Message message) {
            if (message != null) {
                this.mMessages.add(message);
                if (this.mMessages.size() > 25) {
                    this.mMessages.remove(0);
                }
            }
            return this;
        }

        @NonNull
        public MessagingStyle addHistoricMessage(@Nullable Message message) {
            if (message != null) {
                this.mHistoricMessages.add(message);
                if (this.mHistoricMessages.size() > 25) {
                    this.mHistoricMessages.remove(0);
                }
            }
            return this;
        }

        @NonNull
        public List<Message> getMessages() {
            return this.mMessages;
        }

        @NonNull
        public List<Message> getHistoricMessages() {
            return this.mHistoricMessages;
        }

        @NonNull
        public MessagingStyle setGroupConversation(boolean isGroupConversation) {
            this.mIsGroupConversation = Boolean.valueOf(isGroupConversation);
            return this;
        }

        public boolean isGroupConversation() {
            Builder builder = this.mBuilder;
            if (builder == null || builder.mContext.getApplicationInfo().targetSdkVersion >= 28 || this.mIsGroupConversation != null) {
                Boolean bool = this.mIsGroupConversation;
                if (bool != null) {
                    return bool.booleanValue();
                }
                return false;
            } else if (this.mConversationTitle != null) {
                return true;
            } else {
                return false;
            }
        }

        @Nullable
        public static MessagingStyle extractMessagingStyleFromNotification(@NonNull Notification notification) {
            Style style = Style.extractStyleFromNotification(notification);
            if (style instanceof MessagingStyle) {
                return (MessagingStyle) style;
            }
            return null;
        }

        /* access modifiers changed from: protected */
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public String getClassName() {
            return TEMPLATE_CLASS_NAME;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            CharSequence charSequence;
            Notification.MessagingStyle frameworkStyle;
            setGroupConversation(isGroupConversation());
            int i = Build.VERSION.SDK_INT;
            if (i >= 24) {
                if (i >= 28) {
                    frameworkStyle = new Notification.MessagingStyle(this.mUser.toAndroidPerson());
                } else {
                    frameworkStyle = new Notification.MessagingStyle(this.mUser.getName());
                }
                for (Message message : this.mMessages) {
                    frameworkStyle.addMessage(message.toAndroidMessage());
                }
                if (Build.VERSION.SDK_INT >= 26) {
                    for (Message historicMessage : this.mHistoricMessages) {
                        frameworkStyle.addHistoricMessage(historicMessage.toAndroidMessage());
                    }
                }
                if (this.mIsGroupConversation.booleanValue() || Build.VERSION.SDK_INT >= 28) {
                    frameworkStyle.setConversationTitle(this.mConversationTitle);
                }
                if (Build.VERSION.SDK_INT >= 28) {
                    frameworkStyle.setGroupConversation(this.mIsGroupConversation.booleanValue());
                }
                frameworkStyle.setBuilder(builder.getBuilder());
                return;
            }
            Message latestIncomingMessage = findLatestIncomingMessage();
            if (this.mConversationTitle != null && this.mIsGroupConversation.booleanValue()) {
                builder.getBuilder().setContentTitle(this.mConversationTitle);
            } else if (latestIncomingMessage != null) {
                builder.getBuilder().setContentTitle("");
                if (latestIncomingMessage.getPerson() != null) {
                    builder.getBuilder().setContentTitle(latestIncomingMessage.getPerson().getName());
                }
            }
            if (latestIncomingMessage != null) {
                Notification.Builder builder2 = builder.getBuilder();
                if (this.mConversationTitle != null) {
                    charSequence = makeMessageLine(latestIncomingMessage);
                } else {
                    charSequence = latestIncomingMessage.getText();
                }
                builder2.setContentText(charSequence);
            }
            if (i >= 16) {
                SpannableStringBuilder completeMessage = new SpannableStringBuilder();
                boolean showNames = this.mConversationTitle != null || hasMessagesWithoutSender();
                for (int i2 = this.mMessages.size() - 1; i2 >= 0; i2--) {
                    Message message2 = this.mMessages.get(i2);
                    CharSequence line = showNames ? makeMessageLine(message2) : message2.getText();
                    if (i2 != this.mMessages.size() - 1) {
                        completeMessage.insert(0, "\n");
                    }
                    completeMessage.insert(0, line);
                }
                new Notification.BigTextStyle(builder.getBuilder()).setBigContentTitle((CharSequence) null).bigText(completeMessage);
            }
        }

        @Nullable
        private Message findLatestIncomingMessage() {
            for (int i = this.mMessages.size() - 1; i >= 0; i--) {
                Message message = this.mMessages.get(i);
                if (message.getPerson() != null && !TextUtils.isEmpty(message.getPerson().getName())) {
                    return message;
                }
            }
            if (this.mMessages.isEmpty()) {
                return null;
            }
            List<Message> list = this.mMessages;
            return list.get(list.size() - 1);
        }

        private boolean hasMessagesWithoutSender() {
            for (int i = this.mMessages.size() - 1; i >= 0; i--) {
                Message message = this.mMessages.get(i);
                if (message.getPerson() != null && message.getPerson().getName() == null) {
                    return true;
                }
            }
            return false;
        }

        private CharSequence makeMessageLine(@NonNull Message message) {
            int i;
            BidiFormatter bidi = BidiFormatter.getInstance();
            SpannableStringBuilder sb = new SpannableStringBuilder();
            boolean afterLollipop = Build.VERSION.SDK_INT >= 21;
            int color = afterLollipop ? ViewCompat.MEASURED_STATE_MASK : -1;
            CharSequence text = "";
            CharSequence replyName = message.getPerson() == null ? text : message.getPerson().getName();
            if (TextUtils.isEmpty(replyName)) {
                replyName = this.mUser.getName();
                if (!afterLollipop || this.mBuilder.getColor() == 0) {
                    i = color;
                } else {
                    i = this.mBuilder.getColor();
                }
                color = i;
            }
            CharSequence senderText = bidi.unicodeWrap(replyName);
            sb.append(senderText);
            sb.setSpan(makeFontColorSpan(color), sb.length() - senderText.length(), sb.length(), 33);
            if (message.getText() != null) {
                text = message.getText();
            }
            sb.append(JustifyTextView.TWO_CHINESE_BLANK).append(bidi.unicodeWrap(text));
            return sb;
        }

        @NonNull
        private TextAppearanceSpan makeFontColorSpan(int color) {
            return new TextAppearanceSpan((String) null, 0, 0, ColorStateList.valueOf(color), (ColorStateList) null);
        }

        public void addCompatExtras(@NonNull Bundle extras) {
            super.addCompatExtras(extras);
            extras.putCharSequence(NotificationCompat.EXTRA_SELF_DISPLAY_NAME, this.mUser.getName());
            extras.putBundle(NotificationCompat.EXTRA_MESSAGING_STYLE_USER, this.mUser.toBundle());
            extras.putCharSequence(NotificationCompat.EXTRA_HIDDEN_CONVERSATION_TITLE, this.mConversationTitle);
            if (this.mConversationTitle != null && this.mIsGroupConversation.booleanValue()) {
                extras.putCharSequence(NotificationCompat.EXTRA_CONVERSATION_TITLE, this.mConversationTitle);
            }
            if (!this.mMessages.isEmpty()) {
                extras.putParcelableArray(NotificationCompat.EXTRA_MESSAGES, Message.getBundleArrayForMessages(this.mMessages));
            }
            if (!this.mHistoricMessages.isEmpty()) {
                extras.putParcelableArray(NotificationCompat.EXTRA_HISTORIC_MESSAGES, Message.getBundleArrayForMessages(this.mHistoricMessages));
            }
            Boolean bool = this.mIsGroupConversation;
            if (bool != null) {
                extras.putBoolean(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION, bool.booleanValue());
            }
        }

        /* access modifiers changed from: protected */
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void restoreFromCompatExtras(@NonNull Bundle extras) {
            super.restoreFromCompatExtras(extras);
            this.mMessages.clear();
            if (extras.containsKey(NotificationCompat.EXTRA_MESSAGING_STYLE_USER)) {
                this.mUser = Person.fromBundle(extras.getBundle(NotificationCompat.EXTRA_MESSAGING_STYLE_USER));
            } else {
                this.mUser = new Person.Builder().setName(extras.getString(NotificationCompat.EXTRA_SELF_DISPLAY_NAME)).build();
            }
            CharSequence charSequence = extras.getCharSequence(NotificationCompat.EXTRA_CONVERSATION_TITLE);
            this.mConversationTitle = charSequence;
            if (charSequence == null) {
                this.mConversationTitle = extras.getCharSequence(NotificationCompat.EXTRA_HIDDEN_CONVERSATION_TITLE);
            }
            Parcelable[] messages = extras.getParcelableArray(NotificationCompat.EXTRA_MESSAGES);
            if (messages != null) {
                this.mMessages.addAll(Message.getMessagesFromBundleArray(messages));
            }
            Parcelable[] historicMessages = extras.getParcelableArray(NotificationCompat.EXTRA_HISTORIC_MESSAGES);
            if (historicMessages != null) {
                this.mHistoricMessages.addAll(Message.getMessagesFromBundleArray(historicMessages));
            }
            if (extras.containsKey(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION)) {
                this.mIsGroupConversation = Boolean.valueOf(extras.getBoolean(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION));
            }
        }

        /* access modifiers changed from: protected */
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void clearCompatExtraKeys(@NonNull Bundle extras) {
            super.clearCompatExtraKeys(extras);
            extras.remove(NotificationCompat.EXTRA_MESSAGING_STYLE_USER);
            extras.remove(NotificationCompat.EXTRA_SELF_DISPLAY_NAME);
            extras.remove(NotificationCompat.EXTRA_CONVERSATION_TITLE);
            extras.remove(NotificationCompat.EXTRA_HIDDEN_CONVERSATION_TITLE);
            extras.remove(NotificationCompat.EXTRA_MESSAGES);
            extras.remove(NotificationCompat.EXTRA_HISTORIC_MESSAGES);
            extras.remove(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION);
        }

        public static final class Message {
            static final String KEY_DATA_MIME_TYPE = "type";
            static final String KEY_DATA_URI = "uri";
            static final String KEY_EXTRAS_BUNDLE = "extras";
            static final String KEY_NOTIFICATION_PERSON = "sender_person";
            static final String KEY_PERSON = "person";
            static final String KEY_SENDER = "sender";
            static final String KEY_TEXT = "text";
            static final String KEY_TIMESTAMP = "time";
            @Nullable
            private String mDataMimeType;
            @Nullable
            private Uri mDataUri;
            private Bundle mExtras;
            @Nullable
            private final Person mPerson;
            private final CharSequence mText;
            private final long mTimestamp;

            public Message(@Nullable CharSequence text, long timestamp, @Nullable Person person) {
                this.mExtras = new Bundle();
                this.mText = text;
                this.mTimestamp = timestamp;
                this.mPerson = person;
            }

            @Deprecated
            public Message(@Nullable CharSequence text, long timestamp, @Nullable CharSequence sender) {
                this(text, timestamp, new Person.Builder().setName(sender).build());
            }

            @NonNull
            public Message setData(@Nullable String dataMimeType, @Nullable Uri dataUri) {
                this.mDataMimeType = dataMimeType;
                this.mDataUri = dataUri;
                return this;
            }

            @Nullable
            public CharSequence getText() {
                return this.mText;
            }

            public long getTimestamp() {
                return this.mTimestamp;
            }

            @NonNull
            public Bundle getExtras() {
                return this.mExtras;
            }

            @Deprecated
            @Nullable
            public CharSequence getSender() {
                Person person = this.mPerson;
                if (person == null) {
                    return null;
                }
                return person.getName();
            }

            @Nullable
            public Person getPerson() {
                return this.mPerson;
            }

            @Nullable
            public String getDataMimeType() {
                return this.mDataMimeType;
            }

            @Nullable
            public Uri getDataUri() {
                return this.mDataUri;
            }

            @NonNull
            private Bundle toBundle() {
                Bundle bundle = new Bundle();
                CharSequence charSequence = this.mText;
                if (charSequence != null) {
                    bundle.putCharSequence(KEY_TEXT, charSequence);
                }
                bundle.putLong(KEY_TIMESTAMP, this.mTimestamp);
                Person person = this.mPerson;
                if (person != null) {
                    bundle.putCharSequence(KEY_SENDER, person.getName());
                    if (Build.VERSION.SDK_INT >= 28) {
                        bundle.putParcelable(KEY_NOTIFICATION_PERSON, this.mPerson.toAndroidPerson());
                    } else {
                        bundle.putBundle(KEY_PERSON, this.mPerson.toBundle());
                    }
                }
                String str = this.mDataMimeType;
                if (str != null) {
                    bundle.putString("type", str);
                }
                Uri uri = this.mDataUri;
                if (uri != null) {
                    bundle.putParcelable(KEY_DATA_URI, uri);
                }
                Bundle bundle2 = this.mExtras;
                if (bundle2 != null) {
                    bundle.putBundle(KEY_EXTRAS_BUNDLE, bundle2);
                }
                return bundle;
            }

            @NonNull
            static Bundle[] getBundleArrayForMessages(@NonNull List<Message> messages) {
                Bundle[] bundles = new Bundle[messages.size()];
                int N = messages.size();
                for (int i = 0; i < N; i++) {
                    bundles[i] = messages.get(i).toBundle();
                }
                return bundles;
            }

            @NonNull
            static List<Message> getMessagesFromBundleArray(@NonNull Parcelable[] bundles) {
                Message message;
                List<Message> messages = new ArrayList<>(bundles.length);
                for (int i = 0; i < bundles.length; i++) {
                    if ((bundles[i] instanceof Bundle) && (message = getMessageFromBundle(bundles[i])) != null) {
                        messages.add(message);
                    }
                }
                return messages;
            }

            @Nullable
            static Message getMessageFromBundle(@NonNull Bundle bundle) {
                try {
                    if (bundle.containsKey(KEY_TEXT)) {
                        if (bundle.containsKey(KEY_TIMESTAMP)) {
                            Person person = null;
                            if (bundle.containsKey(KEY_PERSON)) {
                                person = Person.fromBundle(bundle.getBundle(KEY_PERSON));
                            } else if (bundle.containsKey(KEY_NOTIFICATION_PERSON) && Build.VERSION.SDK_INT >= 28) {
                                person = Person.fromAndroidPerson((android.app.Person) bundle.getParcelable(KEY_NOTIFICATION_PERSON));
                            } else if (bundle.containsKey(KEY_SENDER)) {
                                person = new Person.Builder().setName(bundle.getCharSequence(KEY_SENDER)).build();
                            }
                            Message message = new Message(bundle.getCharSequence(KEY_TEXT), bundle.getLong(KEY_TIMESTAMP), person);
                            if (bundle.containsKey("type") && bundle.containsKey(KEY_DATA_URI)) {
                                message.setData(bundle.getString("type"), (Uri) bundle.getParcelable(KEY_DATA_URI));
                            }
                            if (bundle.containsKey(KEY_EXTRAS_BUNDLE)) {
                                message.getExtras().putAll(bundle.getBundle(KEY_EXTRAS_BUNDLE));
                            }
                            return message;
                        }
                    }
                    return null;
                } catch (ClassCastException e) {
                    return null;
                }
            }

            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: android.app.Person} */
            /* JADX WARNING: type inference failed for: r2v0 */
            /* JADX WARNING: type inference failed for: r2v3, types: [java.lang.CharSequence] */
            /* JADX WARNING: type inference failed for: r2v7 */
            /* JADX WARNING: type inference failed for: r2v8 */
            /* access modifiers changed from: package-private */
            /* JADX WARNING: Multi-variable type inference failed */
            @androidx.annotation.RequiresApi(24)
            @androidx.annotation.NonNull
            @androidx.annotation.RestrictTo({androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public android.app.Notification.MessagingStyle.Message toAndroidMessage() {
                /*
                    r6 = this;
                    androidx.core.app.Person r0 = r6.getPerson()
                    int r1 = android.os.Build.VERSION.SDK_INT
                    r2 = 0
                    r3 = 28
                    if (r1 < r3) goto L_0x0020
                    android.app.Notification$MessagingStyle$Message r1 = new android.app.Notification$MessagingStyle$Message
                    java.lang.CharSequence r3 = r6.getText()
                    long r4 = r6.getTimestamp()
                    if (r0 != 0) goto L_0x0018
                    goto L_0x001c
                L_0x0018:
                    android.app.Person r2 = r0.toAndroidPerson()
                L_0x001c:
                    r1.<init>(r3, r4, r2)
                    goto L_0x0034
                L_0x0020:
                    android.app.Notification$MessagingStyle$Message r1 = new android.app.Notification$MessagingStyle$Message
                    java.lang.CharSequence r3 = r6.getText()
                    long r4 = r6.getTimestamp()
                    if (r0 != 0) goto L_0x002d
                    goto L_0x0031
                L_0x002d:
                    java.lang.CharSequence r2 = r0.getName()
                L_0x0031:
                    r1.<init>(r3, r4, r2)
                L_0x0034:
                    java.lang.String r2 = r6.getDataMimeType()
                    if (r2 == 0) goto L_0x0045
                    java.lang.String r2 = r6.getDataMimeType()
                    android.net.Uri r3 = r6.getDataUri()
                    r1.setData(r2, r3)
                L_0x0045:
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.core.app.NotificationCompat.MessagingStyle.Message.toAndroidMessage():android.app.Notification$MessagingStyle$Message");
            }
        }
    }

    public static class InboxStyle extends Style {
        private static final String TEMPLATE_CLASS_NAME = "androidx.core.app.NotificationCompat$InboxStyle";
        private ArrayList<CharSequence> mTexts = new ArrayList<>();

        public InboxStyle() {
        }

        public InboxStyle(@Nullable Builder builder) {
            setBuilder(builder);
        }

        @NonNull
        public InboxStyle setBigContentTitle(@Nullable CharSequence title) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(title);
            return this;
        }

        @NonNull
        public InboxStyle setSummaryText(@Nullable CharSequence cs) {
            this.mSummaryText = Builder.limitCharSequenceLength(cs);
            this.mSummaryTextSet = true;
            return this;
        }

        @NonNull
        public InboxStyle addLine(@Nullable CharSequence cs) {
            if (cs != null) {
                this.mTexts.add(Builder.limitCharSequenceLength(cs));
            }
            return this;
        }

        /* access modifiers changed from: protected */
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public String getClassName() {
            return TEMPLATE_CLASS_NAME;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            if (Build.VERSION.SDK_INT >= 16) {
                Notification.InboxStyle style = new Notification.InboxStyle(builder.getBuilder()).setBigContentTitle(this.mBigContentTitle);
                if (this.mSummaryTextSet) {
                    style.setSummaryText(this.mSummaryText);
                }
                Iterator<CharSequence> it = this.mTexts.iterator();
                while (it.hasNext()) {
                    style.addLine(it.next());
                }
            }
        }

        /* access modifiers changed from: protected */
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void restoreFromCompatExtras(@NonNull Bundle extras) {
            super.restoreFromCompatExtras(extras);
            this.mTexts.clear();
            if (extras.containsKey(NotificationCompat.EXTRA_TEXT_LINES)) {
                Collections.addAll(this.mTexts, extras.getCharSequenceArray(NotificationCompat.EXTRA_TEXT_LINES));
            }
        }

        /* access modifiers changed from: protected */
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void clearCompatExtraKeys(@NonNull Bundle extras) {
            super.clearCompatExtraKeys(extras);
            extras.remove(NotificationCompat.EXTRA_TEXT_LINES);
        }
    }

    public static class DecoratedCustomViewStyle extends Style {
        private static final int MAX_ACTION_BUTTONS = 3;
        private static final String TEMPLATE_CLASS_NAME = "androidx.core.app.NotificationCompat$DecoratedCustomViewStyle";

        /* access modifiers changed from: protected */
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public String getClassName() {
            return TEMPLATE_CLASS_NAME;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public boolean displayCustomViewInline() {
            return true;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            if (Build.VERSION.SDK_INT >= 24) {
                builder.getBuilder().setStyle(new Notification.DecoratedCustomViewStyle());
            }
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor builder) {
            if (Build.VERSION.SDK_INT < 24 && this.mBuilder.getContentView() != null) {
                return createRemoteViews(this.mBuilder.getContentView(), false);
            }
            return null;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor builder) {
            RemoteViews innerView;
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews bigContentView = this.mBuilder.getBigContentView();
            if (bigContentView != null) {
                innerView = bigContentView;
            } else {
                innerView = this.mBuilder.getContentView();
            }
            if (innerView == null) {
                return null;
            }
            return createRemoteViews(innerView, true);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor builder) {
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews headsUp = this.mBuilder.getHeadsUpContentView();
            RemoteViews innerView = headsUp != null ? headsUp : this.mBuilder.getContentView();
            if (headsUp == null) {
                return null;
            }
            return createRemoteViews(innerView, true);
        }

        private RemoteViews createRemoteViews(RemoteViews innerView, boolean showActions) {
            int numActions;
            int actionVisibility = 0;
            RemoteViews remoteViews = applyStandardTemplate(true, R.layout.notification_template_custom_big, false);
            remoteViews.removeAllViews(R.id.actions);
            boolean actionsVisible = false;
            List<Action> nonContextualActions = getNonContextualActions(this.mBuilder.mActions);
            if (showActions && nonContextualActions != null && (numActions = Math.min(nonContextualActions.size(), 3)) > 0) {
                actionsVisible = true;
                for (int i = 0; i < numActions; i++) {
                    remoteViews.addView(R.id.actions, generateActionButton(nonContextualActions.get(i)));
                }
            }
            if (!actionsVisible) {
                actionVisibility = 8;
            }
            remoteViews.setViewVisibility(R.id.actions, actionVisibility);
            remoteViews.setViewVisibility(R.id.action_divider, actionVisibility);
            buildIntoRemoteViews(remoteViews, innerView);
            return remoteViews;
        }

        private static List<Action> getNonContextualActions(List<Action> actions) {
            if (actions == null) {
                return null;
            }
            List<Action> nonContextualActions = new ArrayList<>();
            for (Action action : actions) {
                if (!action.isContextual()) {
                    nonContextualActions.add(action);
                }
            }
            return nonContextualActions;
        }

        private RemoteViews generateActionButton(Action action) {
            int i;
            boolean tombstone = action.actionIntent == null;
            String packageName = this.mBuilder.mContext.getPackageName();
            if (tombstone) {
                i = R.layout.notification_action_tombstone;
            } else {
                i = R.layout.notification_action;
            }
            RemoteViews button = new RemoteViews(packageName, i);
            IconCompat icon = action.getIconCompat();
            if (icon != null) {
                button.setImageViewBitmap(R.id.action_image, createColoredBitmap(icon, this.mBuilder.mContext.getResources().getColor(R.color.notification_action_color_filter)));
            }
            button.setTextViewText(R.id.action_text, action.title);
            if (!tombstone) {
                button.setOnClickPendingIntent(R.id.action_container, action.actionIntent);
            }
            if (Build.VERSION.SDK_INT >= 15) {
                button.setContentDescription(R.id.action_container, action.title);
            }
            return button;
        }
    }

    public static class Action {
        static final String EXTRA_SEMANTIC_ACTION = "android.support.action.semanticAction";
        static final String EXTRA_SHOWS_USER_INTERFACE = "android.support.action.showsUserInterface";
        public static final int SEMANTIC_ACTION_ARCHIVE = 5;
        public static final int SEMANTIC_ACTION_CALL = 10;
        public static final int SEMANTIC_ACTION_DELETE = 4;
        public static final int SEMANTIC_ACTION_MARK_AS_READ = 2;
        public static final int SEMANTIC_ACTION_MARK_AS_UNREAD = 3;
        public static final int SEMANTIC_ACTION_MUTE = 6;
        public static final int SEMANTIC_ACTION_NONE = 0;
        public static final int SEMANTIC_ACTION_REPLY = 1;
        public static final int SEMANTIC_ACTION_THUMBS_DOWN = 9;
        public static final int SEMANTIC_ACTION_THUMBS_UP = 8;
        public static final int SEMANTIC_ACTION_UNMUTE = 7;
        public PendingIntent actionIntent;
        @Deprecated
        public int icon;
        private boolean mAllowGeneratedReplies;
        private final RemoteInput[] mDataOnlyRemoteInputs;
        final Bundle mExtras;
        @Nullable
        private IconCompat mIcon;
        private final boolean mIsContextual;
        private final RemoteInput[] mRemoteInputs;
        private final int mSemanticAction;
        boolean mShowsUserInterface;
        public CharSequence title;

        public interface Extender {
            @NonNull
            Builder extend(@NonNull Builder builder);
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface SemanticAction {
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public Action(int icon2, @Nullable CharSequence title2, @Nullable PendingIntent intent) {
            this(icon2 != 0 ? IconCompat.createWithResource((Resources) null, "", icon2) : null, title2, intent);
        }

        public Action(@Nullable IconCompat icon2, @Nullable CharSequence title2, @Nullable PendingIntent intent) {
            this(icon2, title2, intent, new Bundle(), (RemoteInput[]) null, (RemoteInput[]) null, true, 0, true, false);
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        Action(int icon2, @Nullable CharSequence title2, @Nullable PendingIntent intent, @Nullable Bundle extras, @Nullable RemoteInput[] remoteInputs, @Nullable RemoteInput[] dataOnlyRemoteInputs, boolean allowGeneratedReplies, int semanticAction, boolean showsUserInterface, boolean isContextual) {
            this(icon2 != 0 ? IconCompat.createWithResource((Resources) null, "", icon2) : null, title2, intent, extras, remoteInputs, dataOnlyRemoteInputs, allowGeneratedReplies, semanticAction, showsUserInterface, isContextual);
        }

        Action(@Nullable IconCompat icon2, @Nullable CharSequence title2, @Nullable PendingIntent intent, @Nullable Bundle extras, @Nullable RemoteInput[] remoteInputs, @Nullable RemoteInput[] dataOnlyRemoteInputs, boolean allowGeneratedReplies, int semanticAction, boolean showsUserInterface, boolean isContextual) {
            this.mShowsUserInterface = true;
            this.mIcon = icon2;
            if (icon2 != null && icon2.getType() == 2) {
                this.icon = icon2.getResId();
            }
            this.title = Builder.limitCharSequenceLength(title2);
            this.actionIntent = intent;
            this.mExtras = extras != null ? extras : new Bundle();
            this.mRemoteInputs = remoteInputs;
            this.mDataOnlyRemoteInputs = dataOnlyRemoteInputs;
            this.mAllowGeneratedReplies = allowGeneratedReplies;
            this.mSemanticAction = semanticAction;
            this.mShowsUserInterface = showsUserInterface;
            this.mIsContextual = isContextual;
        }

        @Deprecated
        public int getIcon() {
            return this.icon;
        }

        @Nullable
        public IconCompat getIconCompat() {
            int i;
            if (this.mIcon == null && (i = this.icon) != 0) {
                this.mIcon = IconCompat.createWithResource((Resources) null, "", i);
            }
            return this.mIcon;
        }

        @Nullable
        public CharSequence getTitle() {
            return this.title;
        }

        @Nullable
        public PendingIntent getActionIntent() {
            return this.actionIntent;
        }

        @NonNull
        public Bundle getExtras() {
            return this.mExtras;
        }

        public boolean getAllowGeneratedReplies() {
            return this.mAllowGeneratedReplies;
        }

        @Nullable
        public RemoteInput[] getRemoteInputs() {
            return this.mRemoteInputs;
        }

        public int getSemanticAction() {
            return this.mSemanticAction;
        }

        public boolean isContextual() {
            return this.mIsContextual;
        }

        @Nullable
        public RemoteInput[] getDataOnlyRemoteInputs() {
            return this.mDataOnlyRemoteInputs;
        }

        public boolean getShowsUserInterface() {
            return this.mShowsUserInterface;
        }

        public static final class Builder {
            private boolean mAllowGeneratedReplies;
            private final Bundle mExtras;
            private final IconCompat mIcon;
            private final PendingIntent mIntent;
            private boolean mIsContextual;
            private ArrayList<RemoteInput> mRemoteInputs;
            private int mSemanticAction;
            private boolean mShowsUserInterface;
            private final CharSequence mTitle;

            @RequiresApi(19)
            @NonNull
            @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
            public static Builder fromAndroidAction(@NonNull Notification.Action action) {
                Builder builder;
                RemoteInput[] remoteInputs;
                int i = Build.VERSION.SDK_INT;
                if (i < 23 || action.getIcon() == null) {
                    builder = new Builder(action.icon, action.title, action.actionIntent);
                } else {
                    builder = new Builder(IconCompat.createFromIcon(action.getIcon()), action.title, action.actionIntent);
                }
                if (!(i < 20 || (remoteInputs = action.getRemoteInputs()) == null || remoteInputs.length == 0)) {
                    for (RemoteInput remoteInput : remoteInputs) {
                        builder.addRemoteInput(RemoteInput.fromPlatform(remoteInput));
                    }
                }
                int i2 = Build.VERSION.SDK_INT;
                if (i2 >= 24) {
                    builder.mAllowGeneratedReplies = action.getAllowGeneratedReplies();
                }
                if (i2 >= 28) {
                    builder.setSemanticAction(action.getSemanticAction());
                }
                if (i2 >= 29) {
                    builder.setContextual(action.isContextual());
                }
                return builder;
            }

            public Builder(@Nullable IconCompat icon, @Nullable CharSequence title, @Nullable PendingIntent intent) {
                this(icon, title, intent, new Bundle(), (RemoteInput[]) null, true, 0, true, false);
            }

            /* JADX INFO: this call moved to the top of the method (can break code semantics) */
            public Builder(int icon, @Nullable CharSequence title, @Nullable PendingIntent intent) {
                this(icon != 0 ? IconCompat.createWithResource((Resources) null, "", icon) : null, title, intent, new Bundle(), (RemoteInput[]) null, true, 0, true, false);
            }

            public Builder(@NonNull Action action) {
                this(action.getIconCompat(), action.title, action.actionIntent, new Bundle(action.mExtras), action.getRemoteInputs(), action.getAllowGeneratedReplies(), action.getSemanticAction(), action.mShowsUserInterface, action.isContextual());
            }

            private Builder(@Nullable IconCompat icon, @Nullable CharSequence title, @Nullable PendingIntent intent, @NonNull Bundle extras, @Nullable RemoteInput[] remoteInputs, boolean allowGeneratedReplies, int semanticAction, boolean showsUserInterface, boolean isContextual) {
                ArrayList<RemoteInput> arrayList;
                this.mAllowGeneratedReplies = true;
                this.mShowsUserInterface = true;
                this.mIcon = icon;
                this.mTitle = Builder.limitCharSequenceLength(title);
                this.mIntent = intent;
                this.mExtras = extras;
                if (remoteInputs == null) {
                    arrayList = null;
                } else {
                    arrayList = new ArrayList<>(Arrays.asList(remoteInputs));
                }
                this.mRemoteInputs = arrayList;
                this.mAllowGeneratedReplies = allowGeneratedReplies;
                this.mSemanticAction = semanticAction;
                this.mShowsUserInterface = showsUserInterface;
                this.mIsContextual = isContextual;
            }

            @NonNull
            public Builder addExtras(@Nullable Bundle extras) {
                if (extras != null) {
                    this.mExtras.putAll(extras);
                }
                return this;
            }

            @NonNull
            public Bundle getExtras() {
                return this.mExtras;
            }

            @NonNull
            public Builder addRemoteInput(@Nullable RemoteInput remoteInput) {
                if (this.mRemoteInputs == null) {
                    this.mRemoteInputs = new ArrayList<>();
                }
                if (remoteInput != null) {
                    this.mRemoteInputs.add(remoteInput);
                }
                return this;
            }

            @NonNull
            public Builder setAllowGeneratedReplies(boolean allowGeneratedReplies) {
                this.mAllowGeneratedReplies = allowGeneratedReplies;
                return this;
            }

            @NonNull
            public Builder setSemanticAction(int semanticAction) {
                this.mSemanticAction = semanticAction;
                return this;
            }

            @NonNull
            public Builder setContextual(boolean isContextual) {
                this.mIsContextual = isContextual;
                return this;
            }

            @NonNull
            public Builder setShowsUserInterface(boolean showsUserInterface) {
                this.mShowsUserInterface = showsUserInterface;
                return this;
            }

            @NonNull
            public Builder extend(@NonNull Extender extender) {
                extender.extend(this);
                return this;
            }

            private void checkContextualActionNullFields() {
                if (this.mIsContextual && this.mIntent == null) {
                    throw new NullPointerException("Contextual Actions must contain a valid PendingIntent");
                }
            }

            /* JADX WARNING: type inference failed for: r2v6, types: [java.lang.Object[]] */
            /* JADX WARNING: Multi-variable type inference failed */
            @androidx.annotation.NonNull
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public androidx.core.app.NotificationCompat.Action build() {
                /*
                    r15 = this;
                    r15.checkContextualActionNullFields()
                    java.util.ArrayList r0 = new java.util.ArrayList
                    r0.<init>()
                    java.util.ArrayList r1 = new java.util.ArrayList
                    r1.<init>()
                    java.util.ArrayList<androidx.core.app.RemoteInput> r2 = r15.mRemoteInputs
                    if (r2 == 0) goto L_0x002f
                    java.util.Iterator r2 = r2.iterator()
                L_0x0015:
                    boolean r3 = r2.hasNext()
                    if (r3 == 0) goto L_0x002f
                    java.lang.Object r3 = r2.next()
                    androidx.core.app.RemoteInput r3 = (androidx.core.app.RemoteInput) r3
                    boolean r4 = r3.isDataOnly()
                    if (r4 == 0) goto L_0x002b
                    r0.add(r3)
                    goto L_0x002e
                L_0x002b:
                    r1.add(r3)
                L_0x002e:
                    goto L_0x0015
                L_0x002f:
                    boolean r2 = r0.isEmpty()
                    r3 = 0
                    if (r2 == 0) goto L_0x0038
                    r10 = r3
                    goto L_0x0045
                L_0x0038:
                    int r2 = r0.size()
                    androidx.core.app.RemoteInput[] r2 = new androidx.core.app.RemoteInput[r2]
                    java.lang.Object[] r2 = r0.toArray(r2)
                    androidx.core.app.RemoteInput[] r2 = (androidx.core.app.RemoteInput[]) r2
                    r10 = r2
                L_0x0045:
                    boolean r2 = r1.isEmpty()
                    if (r2 == 0) goto L_0x004c
                    goto L_0x0059
                L_0x004c:
                    int r2 = r1.size()
                    androidx.core.app.RemoteInput[] r2 = new androidx.core.app.RemoteInput[r2]
                    java.lang.Object[] r2 = r1.toArray(r2)
                    r3 = r2
                    androidx.core.app.RemoteInput[] r3 = (androidx.core.app.RemoteInput[]) r3
                L_0x0059:
                    r9 = r3
                    androidx.core.app.NotificationCompat$Action r2 = new androidx.core.app.NotificationCompat$Action
                    androidx.core.graphics.drawable.IconCompat r5 = r15.mIcon
                    java.lang.CharSequence r6 = r15.mTitle
                    android.app.PendingIntent r7 = r15.mIntent
                    android.os.Bundle r8 = r15.mExtras
                    boolean r11 = r15.mAllowGeneratedReplies
                    int r12 = r15.mSemanticAction
                    boolean r13 = r15.mShowsUserInterface
                    boolean r14 = r15.mIsContextual
                    r4 = r2
                    r4.<init>((androidx.core.graphics.drawable.IconCompat) r5, (java.lang.CharSequence) r6, (android.app.PendingIntent) r7, (android.os.Bundle) r8, (androidx.core.app.RemoteInput[]) r9, (androidx.core.app.RemoteInput[]) r10, (boolean) r11, (int) r12, (boolean) r13, (boolean) r14)
                    return r2
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.core.app.NotificationCompat.Action.Builder.build():androidx.core.app.NotificationCompat$Action");
            }
        }

        public static final class WearableExtender implements Extender {
            private static final int DEFAULT_FLAGS = 1;
            private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
            private static final int FLAG_AVAILABLE_OFFLINE = 1;
            private static final int FLAG_HINT_DISPLAY_INLINE = 4;
            private static final int FLAG_HINT_LAUNCHES_ACTIVITY = 2;
            private static final String KEY_CANCEL_LABEL = "cancelLabel";
            private static final String KEY_CONFIRM_LABEL = "confirmLabel";
            private static final String KEY_FLAGS = "flags";
            private static final String KEY_IN_PROGRESS_LABEL = "inProgressLabel";
            private CharSequence mCancelLabel;
            private CharSequence mConfirmLabel;
            private int mFlags = 1;
            private CharSequence mInProgressLabel;

            public WearableExtender() {
            }

            public WearableExtender(@NonNull Action action) {
                Bundle wearableBundle = action.getExtras().getBundle(EXTRA_WEARABLE_EXTENSIONS);
                if (wearableBundle != null) {
                    this.mFlags = wearableBundle.getInt(KEY_FLAGS, 1);
                    this.mInProgressLabel = wearableBundle.getCharSequence(KEY_IN_PROGRESS_LABEL);
                    this.mConfirmLabel = wearableBundle.getCharSequence(KEY_CONFIRM_LABEL);
                    this.mCancelLabel = wearableBundle.getCharSequence(KEY_CANCEL_LABEL);
                }
            }

            @NonNull
            public Builder extend(@NonNull Builder builder) {
                Bundle wearableBundle = new Bundle();
                int i = this.mFlags;
                if (i != 1) {
                    wearableBundle.putInt(KEY_FLAGS, i);
                }
                CharSequence charSequence = this.mInProgressLabel;
                if (charSequence != null) {
                    wearableBundle.putCharSequence(KEY_IN_PROGRESS_LABEL, charSequence);
                }
                CharSequence charSequence2 = this.mConfirmLabel;
                if (charSequence2 != null) {
                    wearableBundle.putCharSequence(KEY_CONFIRM_LABEL, charSequence2);
                }
                CharSequence charSequence3 = this.mCancelLabel;
                if (charSequence3 != null) {
                    wearableBundle.putCharSequence(KEY_CANCEL_LABEL, charSequence3);
                }
                builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, wearableBundle);
                return builder;
            }

            @NonNull
            public WearableExtender clone() {
                WearableExtender that = new WearableExtender();
                that.mFlags = this.mFlags;
                that.mInProgressLabel = this.mInProgressLabel;
                that.mConfirmLabel = this.mConfirmLabel;
                that.mCancelLabel = this.mCancelLabel;
                return that;
            }

            @NonNull
            public WearableExtender setAvailableOffline(boolean availableOffline) {
                setFlag(1, availableOffline);
                return this;
            }

            public boolean isAvailableOffline() {
                return (this.mFlags & 1) != 0;
            }

            private void setFlag(int mask, boolean value) {
                if (value) {
                    this.mFlags |= mask;
                } else {
                    this.mFlags &= ~mask;
                }
            }

            @NonNull
            @Deprecated
            public WearableExtender setInProgressLabel(@Nullable CharSequence label) {
                this.mInProgressLabel = label;
                return this;
            }

            @Deprecated
            @Nullable
            public CharSequence getInProgressLabel() {
                return this.mInProgressLabel;
            }

            @NonNull
            @Deprecated
            public WearableExtender setConfirmLabel(@Nullable CharSequence label) {
                this.mConfirmLabel = label;
                return this;
            }

            @Deprecated
            @Nullable
            public CharSequence getConfirmLabel() {
                return this.mConfirmLabel;
            }

            @NonNull
            @Deprecated
            public WearableExtender setCancelLabel(@Nullable CharSequence label) {
                this.mCancelLabel = label;
                return this;
            }

            @Deprecated
            @Nullable
            public CharSequence getCancelLabel() {
                return this.mCancelLabel;
            }

            @NonNull
            public WearableExtender setHintLaunchesActivity(boolean hintLaunchesActivity) {
                setFlag(2, hintLaunchesActivity);
                return this;
            }

            public boolean getHintLaunchesActivity() {
                return (this.mFlags & 2) != 0;
            }

            @NonNull
            public WearableExtender setHintDisplayActionInline(boolean hintDisplayInline) {
                setFlag(4, hintDisplayInline);
                return this;
            }

            public boolean getHintDisplayActionInline() {
                return (this.mFlags & 4) != 0;
            }
        }
    }

    public static final class WearableExtender implements Extender {
        private static final int DEFAULT_CONTENT_ICON_GRAVITY = 8388613;
        private static final int DEFAULT_FLAGS = 1;
        private static final int DEFAULT_GRAVITY = 80;
        private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
        private static final int FLAG_BIG_PICTURE_AMBIENT = 32;
        private static final int FLAG_CONTENT_INTENT_AVAILABLE_OFFLINE = 1;
        private static final int FLAG_HINT_AVOID_BACKGROUND_CLIPPING = 16;
        private static final int FLAG_HINT_CONTENT_INTENT_LAUNCHES_ACTIVITY = 64;
        private static final int FLAG_HINT_HIDE_ICON = 2;
        private static final int FLAG_HINT_SHOW_BACKGROUND_ONLY = 4;
        private static final int FLAG_START_SCROLL_BOTTOM = 8;
        private static final String KEY_ACTIONS = "actions";
        private static final String KEY_BACKGROUND = "background";
        private static final String KEY_BRIDGE_TAG = "bridgeTag";
        private static final String KEY_CONTENT_ACTION_INDEX = "contentActionIndex";
        private static final String KEY_CONTENT_ICON = "contentIcon";
        private static final String KEY_CONTENT_ICON_GRAVITY = "contentIconGravity";
        private static final String KEY_CUSTOM_CONTENT_HEIGHT = "customContentHeight";
        private static final String KEY_CUSTOM_SIZE_PRESET = "customSizePreset";
        private static final String KEY_DISMISSAL_ID = "dismissalId";
        private static final String KEY_DISPLAY_INTENT = "displayIntent";
        private static final String KEY_FLAGS = "flags";
        private static final String KEY_GRAVITY = "gravity";
        private static final String KEY_HINT_SCREEN_TIMEOUT = "hintScreenTimeout";
        private static final String KEY_PAGES = "pages";
        @Deprecated
        public static final int SCREEN_TIMEOUT_LONG = -1;
        @Deprecated
        public static final int SCREEN_TIMEOUT_SHORT = 0;
        @Deprecated
        public static final int SIZE_DEFAULT = 0;
        @Deprecated
        public static final int SIZE_FULL_SCREEN = 5;
        @Deprecated
        public static final int SIZE_LARGE = 4;
        @Deprecated
        public static final int SIZE_MEDIUM = 3;
        @Deprecated
        public static final int SIZE_SMALL = 2;
        @Deprecated
        public static final int SIZE_XSMALL = 1;
        public static final int UNSET_ACTION_INDEX = -1;
        private ArrayList<Action> mActions = new ArrayList<>();
        private Bitmap mBackground;
        private String mBridgeTag;
        private int mContentActionIndex = -1;
        private int mContentIcon;
        private int mContentIconGravity = 8388613;
        private int mCustomContentHeight;
        private int mCustomSizePreset = 0;
        private String mDismissalId;
        private PendingIntent mDisplayIntent;
        private int mFlags = 1;
        private int mGravity = 80;
        private int mHintScreenTimeout;
        private ArrayList<Notification> mPages = new ArrayList<>();

        public WearableExtender() {
        }

        public WearableExtender(@NonNull Notification notification) {
            Bundle wearableBundle;
            Bundle extras = NotificationCompat.getExtras(notification);
            if (extras != null) {
                wearableBundle = extras.getBundle(EXTRA_WEARABLE_EXTENSIONS);
            } else {
                wearableBundle = null;
            }
            if (wearableBundle != null) {
                ArrayList<Parcelable> parcelables = wearableBundle.getParcelableArrayList(KEY_ACTIONS);
                if (Build.VERSION.SDK_INT >= 16 && parcelables != null) {
                    Action[] actions = new Action[parcelables.size()];
                    for (int i = 0; i < actions.length; i++) {
                        int i2 = Build.VERSION.SDK_INT;
                        if (i2 >= 20) {
                            actions[i] = NotificationCompat.getActionCompatFromAction((Notification.Action) parcelables.get(i));
                        } else if (i2 >= 16) {
                            actions[i] = NotificationCompatJellybean.getActionFromBundle((Bundle) parcelables.get(i));
                        }
                    }
                    Collections.addAll(this.mActions, actions);
                }
                this.mFlags = wearableBundle.getInt(KEY_FLAGS, 1);
                this.mDisplayIntent = (PendingIntent) wearableBundle.getParcelable(KEY_DISPLAY_INTENT);
                Notification[] pages = NotificationCompat.getNotificationArrayFromBundle(wearableBundle, KEY_PAGES);
                if (pages != null) {
                    Collections.addAll(this.mPages, pages);
                }
                this.mBackground = (Bitmap) wearableBundle.getParcelable(KEY_BACKGROUND);
                this.mContentIcon = wearableBundle.getInt(KEY_CONTENT_ICON);
                this.mContentIconGravity = wearableBundle.getInt(KEY_CONTENT_ICON_GRAVITY, 8388613);
                this.mContentActionIndex = wearableBundle.getInt(KEY_CONTENT_ACTION_INDEX, -1);
                this.mCustomSizePreset = wearableBundle.getInt(KEY_CUSTOM_SIZE_PRESET, 0);
                this.mCustomContentHeight = wearableBundle.getInt(KEY_CUSTOM_CONTENT_HEIGHT);
                this.mGravity = wearableBundle.getInt(KEY_GRAVITY, 80);
                this.mHintScreenTimeout = wearableBundle.getInt(KEY_HINT_SCREEN_TIMEOUT);
                this.mDismissalId = wearableBundle.getString(KEY_DISMISSAL_ID);
                this.mBridgeTag = wearableBundle.getString(KEY_BRIDGE_TAG);
            }
        }

        @NonNull
        public Builder extend(@NonNull Builder builder) {
            Bundle wearableBundle = new Bundle();
            if (!this.mActions.isEmpty()) {
                if (Build.VERSION.SDK_INT >= 16) {
                    ArrayList<Parcelable> parcelables = new ArrayList<>(this.mActions.size());
                    Iterator<Action> it = this.mActions.iterator();
                    while (it.hasNext()) {
                        Action action = it.next();
                        int i = Build.VERSION.SDK_INT;
                        if (i >= 20) {
                            parcelables.add(getActionFromActionCompat(action));
                        } else if (i >= 16) {
                            parcelables.add(NotificationCompatJellybean.getBundleForAction(action));
                        }
                    }
                    wearableBundle.putParcelableArrayList(KEY_ACTIONS, parcelables);
                } else {
                    wearableBundle.putParcelableArrayList(KEY_ACTIONS, (ArrayList) null);
                }
            }
            int i2 = this.mFlags;
            if (i2 != 1) {
                wearableBundle.putInt(KEY_FLAGS, i2);
            }
            PendingIntent pendingIntent = this.mDisplayIntent;
            if (pendingIntent != null) {
                wearableBundle.putParcelable(KEY_DISPLAY_INTENT, pendingIntent);
            }
            if (!this.mPages.isEmpty()) {
                ArrayList<Notification> arrayList = this.mPages;
                wearableBundle.putParcelableArray(KEY_PAGES, (Parcelable[]) arrayList.toArray(new Notification[arrayList.size()]));
            }
            Bitmap bitmap = this.mBackground;
            if (bitmap != null) {
                wearableBundle.putParcelable(KEY_BACKGROUND, bitmap);
            }
            int i3 = this.mContentIcon;
            if (i3 != 0) {
                wearableBundle.putInt(KEY_CONTENT_ICON, i3);
            }
            int i4 = this.mContentIconGravity;
            if (i4 != 8388613) {
                wearableBundle.putInt(KEY_CONTENT_ICON_GRAVITY, i4);
            }
            int i5 = this.mContentActionIndex;
            if (i5 != -1) {
                wearableBundle.putInt(KEY_CONTENT_ACTION_INDEX, i5);
            }
            int i6 = this.mCustomSizePreset;
            if (i6 != 0) {
                wearableBundle.putInt(KEY_CUSTOM_SIZE_PRESET, i6);
            }
            int i7 = this.mCustomContentHeight;
            if (i7 != 0) {
                wearableBundle.putInt(KEY_CUSTOM_CONTENT_HEIGHT, i7);
            }
            int i8 = this.mGravity;
            if (i8 != 80) {
                wearableBundle.putInt(KEY_GRAVITY, i8);
            }
            int i9 = this.mHintScreenTimeout;
            if (i9 != 0) {
                wearableBundle.putInt(KEY_HINT_SCREEN_TIMEOUT, i9);
            }
            String str = this.mDismissalId;
            if (str != null) {
                wearableBundle.putString(KEY_DISMISSAL_ID, str);
            }
            String str2 = this.mBridgeTag;
            if (str2 != null) {
                wearableBundle.putString(KEY_BRIDGE_TAG, str2);
            }
            builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, wearableBundle);
            return builder;
        }

        @RequiresApi(20)
        private static Notification.Action getActionFromActionCompat(Action actionCompat) {
            Notification.Action.Builder actionBuilder;
            Bundle actionExtras;
            int i = Build.VERSION.SDK_INT;
            if (i >= 23) {
                IconCompat iconCompat = actionCompat.getIconCompat();
                actionBuilder = new Notification.Action.Builder(iconCompat == null ? null : iconCompat.toIcon(), actionCompat.getTitle(), actionCompat.getActionIntent());
            } else {
                IconCompat icon = actionCompat.getIconCompat();
                int iconResId = 0;
                if (icon != null && icon.getType() == 2) {
                    iconResId = icon.getResId();
                }
                actionBuilder = new Notification.Action.Builder(iconResId, actionCompat.getTitle(), actionCompat.getActionIntent());
            }
            if (actionCompat.getExtras() != null) {
                actionExtras = new Bundle(actionCompat.getExtras());
            } else {
                actionExtras = new Bundle();
            }
            actionExtras.putBoolean("android.support.allowGeneratedReplies", actionCompat.getAllowGeneratedReplies());
            if (i >= 24) {
                actionBuilder.setAllowGeneratedReplies(actionCompat.getAllowGeneratedReplies());
            }
            actionBuilder.addExtras(actionExtras);
            RemoteInput[] remoteInputCompats = actionCompat.getRemoteInputs();
            if (remoteInputCompats != null) {
                for (RemoteInput remoteInput : RemoteInput.fromCompat(remoteInputCompats)) {
                    actionBuilder.addRemoteInput(remoteInput);
                }
            }
            return actionBuilder.build();
        }

        @NonNull
        public WearableExtender clone() {
            WearableExtender that = new WearableExtender();
            that.mActions = new ArrayList<>(this.mActions);
            that.mFlags = this.mFlags;
            that.mDisplayIntent = this.mDisplayIntent;
            that.mPages = new ArrayList<>(this.mPages);
            that.mBackground = this.mBackground;
            that.mContentIcon = this.mContentIcon;
            that.mContentIconGravity = this.mContentIconGravity;
            that.mContentActionIndex = this.mContentActionIndex;
            that.mCustomSizePreset = this.mCustomSizePreset;
            that.mCustomContentHeight = this.mCustomContentHeight;
            that.mGravity = this.mGravity;
            that.mHintScreenTimeout = this.mHintScreenTimeout;
            that.mDismissalId = this.mDismissalId;
            that.mBridgeTag = this.mBridgeTag;
            return that;
        }

        @NonNull
        public WearableExtender addAction(@NonNull Action action) {
            this.mActions.add(action);
            return this;
        }

        @NonNull
        public WearableExtender addActions(@NonNull List<Action> actions) {
            this.mActions.addAll(actions);
            return this;
        }

        @NonNull
        public WearableExtender clearActions() {
            this.mActions.clear();
            return this;
        }

        @NonNull
        public List<Action> getActions() {
            return this.mActions;
        }

        @NonNull
        @Deprecated
        public WearableExtender setDisplayIntent(@Nullable PendingIntent intent) {
            this.mDisplayIntent = intent;
            return this;
        }

        @Deprecated
        @Nullable
        public PendingIntent getDisplayIntent() {
            return this.mDisplayIntent;
        }

        @NonNull
        @Deprecated
        public WearableExtender addPage(@NonNull Notification page) {
            this.mPages.add(page);
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender addPages(@NonNull List<Notification> pages) {
            this.mPages.addAll(pages);
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender clearPages() {
            this.mPages.clear();
            return this;
        }

        @NonNull
        @Deprecated
        public List<Notification> getPages() {
            return this.mPages;
        }

        @NonNull
        @Deprecated
        public WearableExtender setBackground(@Nullable Bitmap background) {
            this.mBackground = background;
            return this;
        }

        @Deprecated
        @Nullable
        public Bitmap getBackground() {
            return this.mBackground;
        }

        @NonNull
        @Deprecated
        public WearableExtender setContentIcon(int icon) {
            this.mContentIcon = icon;
            return this;
        }

        @Deprecated
        public int getContentIcon() {
            return this.mContentIcon;
        }

        @NonNull
        @Deprecated
        public WearableExtender setContentIconGravity(int contentIconGravity) {
            this.mContentIconGravity = contentIconGravity;
            return this;
        }

        @Deprecated
        public int getContentIconGravity() {
            return this.mContentIconGravity;
        }

        @NonNull
        public WearableExtender setContentAction(int actionIndex) {
            this.mContentActionIndex = actionIndex;
            return this;
        }

        public int getContentAction() {
            return this.mContentActionIndex;
        }

        @NonNull
        @Deprecated
        public WearableExtender setGravity(int gravity) {
            this.mGravity = gravity;
            return this;
        }

        @Deprecated
        public int getGravity() {
            return this.mGravity;
        }

        @NonNull
        @Deprecated
        public WearableExtender setCustomSizePreset(int sizePreset) {
            this.mCustomSizePreset = sizePreset;
            return this;
        }

        @Deprecated
        public int getCustomSizePreset() {
            return this.mCustomSizePreset;
        }

        @NonNull
        @Deprecated
        public WearableExtender setCustomContentHeight(int height) {
            this.mCustomContentHeight = height;
            return this;
        }

        @Deprecated
        public int getCustomContentHeight() {
            return this.mCustomContentHeight;
        }

        @NonNull
        public WearableExtender setStartScrollBottom(boolean startScrollBottom) {
            setFlag(8, startScrollBottom);
            return this;
        }

        public boolean getStartScrollBottom() {
            return (this.mFlags & 8) != 0;
        }

        @NonNull
        public WearableExtender setContentIntentAvailableOffline(boolean contentIntentAvailableOffline) {
            setFlag(1, contentIntentAvailableOffline);
            return this;
        }

        public boolean getContentIntentAvailableOffline() {
            return (this.mFlags & 1) != 0;
        }

        @NonNull
        @Deprecated
        public WearableExtender setHintHideIcon(boolean hintHideIcon) {
            setFlag(2, hintHideIcon);
            return this;
        }

        @Deprecated
        public boolean getHintHideIcon() {
            return (this.mFlags & 2) != 0;
        }

        @NonNull
        @Deprecated
        public WearableExtender setHintShowBackgroundOnly(boolean hintShowBackgroundOnly) {
            setFlag(4, hintShowBackgroundOnly);
            return this;
        }

        @Deprecated
        public boolean getHintShowBackgroundOnly() {
            return (this.mFlags & 4) != 0;
        }

        @NonNull
        @Deprecated
        public WearableExtender setHintAvoidBackgroundClipping(boolean hintAvoidBackgroundClipping) {
            setFlag(16, hintAvoidBackgroundClipping);
            return this;
        }

        @Deprecated
        public boolean getHintAvoidBackgroundClipping() {
            return (this.mFlags & 16) != 0;
        }

        @NonNull
        @Deprecated
        public WearableExtender setHintScreenTimeout(int timeout) {
            this.mHintScreenTimeout = timeout;
            return this;
        }

        @Deprecated
        public int getHintScreenTimeout() {
            return this.mHintScreenTimeout;
        }

        @NonNull
        @Deprecated
        public WearableExtender setHintAmbientBigPicture(boolean hintAmbientBigPicture) {
            setFlag(32, hintAmbientBigPicture);
            return this;
        }

        @Deprecated
        public boolean getHintAmbientBigPicture() {
            return (this.mFlags & 32) != 0;
        }

        @NonNull
        public WearableExtender setHintContentIntentLaunchesActivity(boolean hintContentIntentLaunchesActivity) {
            setFlag(64, hintContentIntentLaunchesActivity);
            return this;
        }

        public boolean getHintContentIntentLaunchesActivity() {
            return (this.mFlags & 64) != 0;
        }

        @NonNull
        public WearableExtender setDismissalId(@Nullable String dismissalId) {
            this.mDismissalId = dismissalId;
            return this;
        }

        @Nullable
        public String getDismissalId() {
            return this.mDismissalId;
        }

        @NonNull
        public WearableExtender setBridgeTag(@Nullable String bridgeTag) {
            this.mBridgeTag = bridgeTag;
            return this;
        }

        @Nullable
        public String getBridgeTag() {
            return this.mBridgeTag;
        }

        private void setFlag(int mask, boolean value) {
            if (value) {
                this.mFlags |= mask;
            } else {
                this.mFlags &= ~mask;
            }
        }
    }

    public static final class CarExtender implements Extender {
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        static final String EXTRA_CAR_EXTENDER = "android.car.EXTENSIONS";
        private static final String EXTRA_COLOR = "app_color";
        private static final String EXTRA_CONVERSATION = "car_conversation";
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        static final String EXTRA_INVISIBLE_ACTIONS = "invisible_actions";
        private static final String EXTRA_LARGE_ICON = "large_icon";
        private static final String KEY_AUTHOR = "author";
        private static final String KEY_MESSAGES = "messages";
        private static final String KEY_ON_READ = "on_read";
        private static final String KEY_ON_REPLY = "on_reply";
        private static final String KEY_PARTICIPANTS = "participants";
        private static final String KEY_REMOTE_INPUT = "remote_input";
        private static final String KEY_TEXT = "text";
        private static final String KEY_TIMESTAMP = "timestamp";
        private int mColor = 0;
        private Bitmap mLargeIcon;
        private UnreadConversation mUnreadConversation;

        public CarExtender() {
        }

        public CarExtender(@NonNull Notification notification) {
            if (Build.VERSION.SDK_INT >= 21) {
                Bundle carBundle = NotificationCompat.getExtras(notification) == null ? null : NotificationCompat.getExtras(notification).getBundle(EXTRA_CAR_EXTENDER);
                if (carBundle != null) {
                    this.mLargeIcon = (Bitmap) carBundle.getParcelable(EXTRA_LARGE_ICON);
                    this.mColor = carBundle.getInt(EXTRA_COLOR, 0);
                    this.mUnreadConversation = getUnreadConversationFromBundle(carBundle.getBundle(EXTRA_CONVERSATION));
                }
            }
        }

        @RequiresApi(21)
        private static UnreadConversation getUnreadConversationFromBundle(@Nullable Bundle b) {
            RemoteInput remoteInputCompat;
            int i;
            Bundle bundle = b;
            if (bundle == null) {
                return null;
            }
            Parcelable[] parcelableMessages = bundle.getParcelableArray(KEY_MESSAGES);
            String[] messages = null;
            if (parcelableMessages != null) {
                String[] tmp = new String[parcelableMessages.length];
                boolean success = true;
                int i2 = 0;
                while (true) {
                    if (i2 >= tmp.length) {
                        break;
                    } else if (!(parcelableMessages[i2] instanceof Bundle)) {
                        success = false;
                        break;
                    } else {
                        tmp[i2] = ((Bundle) parcelableMessages[i2]).getString(KEY_TEXT);
                        if (tmp[i2] == null) {
                            success = false;
                            break;
                        }
                        i2++;
                    }
                }
                if (!success) {
                    return null;
                }
                messages = tmp;
            }
            PendingIntent onRead = (PendingIntent) bundle.getParcelable(KEY_ON_READ);
            PendingIntent onReply = (PendingIntent) bundle.getParcelable(KEY_ON_REPLY);
            RemoteInput remoteInput = (RemoteInput) bundle.getParcelable(KEY_REMOTE_INPUT);
            String[] participants = bundle.getStringArray(KEY_PARTICIPANTS);
            if (participants == null || participants.length != 1) {
                return null;
            }
            if (remoteInput != null) {
                String resultKey = remoteInput.getResultKey();
                CharSequence label = remoteInput.getLabel();
                CharSequence[] choices = remoteInput.getChoices();
                boolean allowFreeFormInput = remoteInput.getAllowFreeFormInput();
                if (Build.VERSION.SDK_INT >= 29) {
                    i = remoteInput.getEditChoicesBeforeSending();
                } else {
                    i = 0;
                }
                remoteInputCompat = new RemoteInput(resultKey, label, choices, allowFreeFormInput, i, remoteInput.getExtras(), (Set<String>) null);
            } else {
                remoteInputCompat = null;
            }
            return new UnreadConversation(messages, remoteInputCompat, onReply, onRead, participants, bundle.getLong(KEY_TIMESTAMP));
        }

        @RequiresApi(21)
        private static Bundle getBundleForUnreadConversation(@NonNull UnreadConversation uc) {
            Bundle b = new Bundle();
            String author = null;
            if (uc.getParticipants() != null && uc.getParticipants().length > 1) {
                author = uc.getParticipants()[0];
            }
            Parcelable[] messages = new Parcelable[uc.getMessages().length];
            for (int i = 0; i < messages.length; i++) {
                Bundle m = new Bundle();
                m.putString(KEY_TEXT, uc.getMessages()[i]);
                m.putString(KEY_AUTHOR, author);
                messages[i] = m;
            }
            b.putParcelableArray(KEY_MESSAGES, messages);
            RemoteInput remoteInputCompat = uc.getRemoteInput();
            if (remoteInputCompat != null) {
                b.putParcelable(KEY_REMOTE_INPUT, new RemoteInput.Builder(remoteInputCompat.getResultKey()).setLabel(remoteInputCompat.getLabel()).setChoices(remoteInputCompat.getChoices()).setAllowFreeFormInput(remoteInputCompat.getAllowFreeFormInput()).addExtras(remoteInputCompat.getExtras()).build());
            }
            b.putParcelable(KEY_ON_REPLY, uc.getReplyPendingIntent());
            b.putParcelable(KEY_ON_READ, uc.getReadPendingIntent());
            b.putStringArray(KEY_PARTICIPANTS, uc.getParticipants());
            b.putLong(KEY_TIMESTAMP, uc.getLatestTimestamp());
            return b;
        }

        @NonNull
        public Builder extend(@NonNull Builder builder) {
            if (Build.VERSION.SDK_INT < 21) {
                return builder;
            }
            Bundle carExtensions = new Bundle();
            Bitmap bitmap = this.mLargeIcon;
            if (bitmap != null) {
                carExtensions.putParcelable(EXTRA_LARGE_ICON, bitmap);
            }
            int i = this.mColor;
            if (i != 0) {
                carExtensions.putInt(EXTRA_COLOR, i);
            }
            UnreadConversation unreadConversation = this.mUnreadConversation;
            if (unreadConversation != null) {
                carExtensions.putBundle(EXTRA_CONVERSATION, getBundleForUnreadConversation(unreadConversation));
            }
            builder.getExtras().putBundle(EXTRA_CAR_EXTENDER, carExtensions);
            return builder;
        }

        @NonNull
        public CarExtender setColor(@ColorInt int color) {
            this.mColor = color;
            return this;
        }

        @ColorInt
        public int getColor() {
            return this.mColor;
        }

        @NonNull
        public CarExtender setLargeIcon(@Nullable Bitmap largeIcon) {
            this.mLargeIcon = largeIcon;
            return this;
        }

        @Nullable
        public Bitmap getLargeIcon() {
            return this.mLargeIcon;
        }

        @NonNull
        @Deprecated
        public CarExtender setUnreadConversation(@Nullable UnreadConversation unreadConversation) {
            this.mUnreadConversation = unreadConversation;
            return this;
        }

        @Deprecated
        @Nullable
        public UnreadConversation getUnreadConversation() {
            return this.mUnreadConversation;
        }

        @Deprecated
        public static class UnreadConversation {
            private final long mLatestTimestamp;
            private final String[] mMessages;
            private final String[] mParticipants;
            private final PendingIntent mReadPendingIntent;
            private final RemoteInput mRemoteInput;
            private final PendingIntent mReplyPendingIntent;

            UnreadConversation(@Nullable String[] messages, @Nullable RemoteInput remoteInput, @Nullable PendingIntent replyPendingIntent, @Nullable PendingIntent readPendingIntent, @Nullable String[] participants, long latestTimestamp) {
                this.mMessages = messages;
                this.mRemoteInput = remoteInput;
                this.mReadPendingIntent = readPendingIntent;
                this.mReplyPendingIntent = replyPendingIntent;
                this.mParticipants = participants;
                this.mLatestTimestamp = latestTimestamp;
            }

            @Nullable
            public String[] getMessages() {
                return this.mMessages;
            }

            @Nullable
            public RemoteInput getRemoteInput() {
                return this.mRemoteInput;
            }

            @Nullable
            public PendingIntent getReplyPendingIntent() {
                return this.mReplyPendingIntent;
            }

            @Nullable
            public PendingIntent getReadPendingIntent() {
                return this.mReadPendingIntent;
            }

            @Nullable
            public String[] getParticipants() {
                return this.mParticipants;
            }

            @Nullable
            public String getParticipant() {
                String[] strArr = this.mParticipants;
                if (strArr.length > 0) {
                    return strArr[0];
                }
                return null;
            }

            public long getLatestTimestamp() {
                return this.mLatestTimestamp;
            }

            public static class Builder {
                private long mLatestTimestamp;
                private final List<String> mMessages = new ArrayList();
                private final String mParticipant;
                private PendingIntent mReadPendingIntent;
                private RemoteInput mRemoteInput;
                private PendingIntent mReplyPendingIntent;

                public Builder(@NonNull String name) {
                    this.mParticipant = name;
                }

                @NonNull
                public Builder addMessage(@Nullable String message) {
                    if (message != null) {
                        this.mMessages.add(message);
                    }
                    return this;
                }

                @NonNull
                public Builder setReplyAction(@Nullable PendingIntent pendingIntent, @Nullable RemoteInput remoteInput) {
                    this.mRemoteInput = remoteInput;
                    this.mReplyPendingIntent = pendingIntent;
                    return this;
                }

                @NonNull
                public Builder setReadPendingIntent(@Nullable PendingIntent pendingIntent) {
                    this.mReadPendingIntent = pendingIntent;
                    return this;
                }

                @NonNull
                public Builder setLatestTimestamp(long timestamp) {
                    this.mLatestTimestamp = timestamp;
                    return this;
                }

                @NonNull
                public UnreadConversation build() {
                    List<String> list = this.mMessages;
                    String[] participants = {this.mParticipant};
                    return new UnreadConversation((String[]) list.toArray(new String[list.size()]), this.mRemoteInput, this.mReplyPendingIntent, this.mReadPendingIntent, participants, this.mLatestTimestamp);
                }
            }
        }
    }

    public static final class BubbleMetadata {
        private static final int FLAG_AUTO_EXPAND_BUBBLE = 1;
        private static final int FLAG_SUPPRESS_NOTIFICATION = 2;
        private PendingIntent mDeleteIntent;
        private int mDesiredHeight;
        @DimenRes
        private int mDesiredHeightResId;
        private int mFlags;
        private IconCompat mIcon;
        private PendingIntent mPendingIntent;
        private String mShortcutId;

        private BubbleMetadata(@Nullable PendingIntent expandIntent, @Nullable PendingIntent deleteIntent, @Nullable IconCompat icon, int height, @DimenRes int heightResId, int flags, @Nullable String shortcutId) {
            this.mPendingIntent = expandIntent;
            this.mIcon = icon;
            this.mDesiredHeight = height;
            this.mDesiredHeightResId = heightResId;
            this.mDeleteIntent = deleteIntent;
            this.mFlags = flags;
            this.mShortcutId = shortcutId;
        }

        @SuppressLint({"InvalidNullConversion"})
        @Nullable
        public PendingIntent getIntent() {
            return this.mPendingIntent;
        }

        @Nullable
        public String getShortcutId() {
            return this.mShortcutId;
        }

        @Nullable
        public PendingIntent getDeleteIntent() {
            return this.mDeleteIntent;
        }

        @SuppressLint({"InvalidNullConversion"})
        @Nullable
        public IconCompat getIcon() {
            return this.mIcon;
        }

        @Dimension(unit = 0)
        public int getDesiredHeight() {
            return this.mDesiredHeight;
        }

        @DimenRes
        public int getDesiredHeightResId() {
            return this.mDesiredHeightResId;
        }

        public boolean getAutoExpandBubble() {
            return (this.mFlags & 1) != 0;
        }

        public boolean isNotificationSuppressed() {
            return (this.mFlags & 2) != 0;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void setFlags(int flags) {
            this.mFlags = flags;
        }

        @Nullable
        public static Notification.BubbleMetadata toPlatform(@Nullable BubbleMetadata compatMetadata) {
            if (compatMetadata == null) {
                return null;
            }
            int i = Build.VERSION.SDK_INT;
            if (i >= 30) {
                return Api30Impl.toPlatform(compatMetadata);
            }
            if (i == 29) {
                return Api29Impl.toPlatform(compatMetadata);
            }
            return null;
        }

        @Nullable
        public static BubbleMetadata fromPlatform(@Nullable Notification.BubbleMetadata platformMetadata) {
            if (platformMetadata == null) {
                return null;
            }
            int i = Build.VERSION.SDK_INT;
            if (i >= 30) {
                return Api30Impl.fromPlatform(platformMetadata);
            }
            if (i == 29) {
                return Api29Impl.fromPlatform(platformMetadata);
            }
            return null;
        }

        public static final class Builder {
            private PendingIntent mDeleteIntent;
            private int mDesiredHeight;
            @DimenRes
            private int mDesiredHeightResId;
            private int mFlags;
            private IconCompat mIcon;
            private PendingIntent mPendingIntent;
            private String mShortcutId;

            @Deprecated
            public Builder() {
            }

            @RequiresApi(30)
            public Builder(@NonNull String shortcutId) {
                if (!TextUtils.isEmpty(shortcutId)) {
                    this.mShortcutId = shortcutId;
                    return;
                }
                throw new NullPointerException("Bubble requires a non-null shortcut id");
            }

            public Builder(@NonNull PendingIntent intent, @NonNull IconCompat icon) {
                if (intent == null) {
                    throw new NullPointerException("Bubble requires non-null pending intent");
                } else if (icon != null) {
                    this.mPendingIntent = intent;
                    this.mIcon = icon;
                } else {
                    throw new NullPointerException("Bubbles require non-null icon");
                }
            }

            @NonNull
            public Builder setIntent(@NonNull PendingIntent intent) {
                if (this.mShortcutId != null) {
                    throw new IllegalStateException("Created as a shortcut bubble, cannot set a PendingIntent. Consider using BubbleMetadata.Builder(PendingIntent,Icon) instead.");
                } else if (intent != null) {
                    this.mPendingIntent = intent;
                    return this;
                } else {
                    throw new NullPointerException("Bubble requires non-null pending intent");
                }
            }

            @NonNull
            public Builder setIcon(@NonNull IconCompat icon) {
                if (this.mShortcutId != null) {
                    throw new IllegalStateException("Created as a shortcut bubble, cannot set an Icon. Consider using BubbleMetadata.Builder(PendingIntent,Icon) instead.");
                } else if (icon != null) {
                    this.mIcon = icon;
                    return this;
                } else {
                    throw new NullPointerException("Bubbles require non-null icon");
                }
            }

            @NonNull
            public Builder setDesiredHeight(@Dimension(unit = 0) int height) {
                this.mDesiredHeight = Math.max(height, 0);
                this.mDesiredHeightResId = 0;
                return this;
            }

            @NonNull
            public Builder setDesiredHeightResId(@DimenRes int heightResId) {
                this.mDesiredHeightResId = heightResId;
                this.mDesiredHeight = 0;
                return this;
            }

            @NonNull
            public Builder setAutoExpandBubble(boolean shouldExpand) {
                setFlag(1, shouldExpand);
                return this;
            }

            @NonNull
            public Builder setSuppressNotification(boolean shouldSuppressNotif) {
                setFlag(2, shouldSuppressNotif);
                return this;
            }

            @NonNull
            public Builder setDeleteIntent(@Nullable PendingIntent deleteIntent) {
                this.mDeleteIntent = deleteIntent;
                return this;
            }

            @SuppressLint({"SyntheticAccessor"})
            @NonNull
            public BubbleMetadata build() {
                String str = this.mShortcutId;
                if (str == null && this.mPendingIntent == null) {
                    throw new NullPointerException("Must supply pending intent or shortcut to bubble");
                } else if (str == null && this.mIcon == null) {
                    throw new NullPointerException("Must supply an icon or shortcut for the bubble");
                } else {
                    BubbleMetadata data = new BubbleMetadata(this.mPendingIntent, this.mDeleteIntent, this.mIcon, this.mDesiredHeight, this.mDesiredHeightResId, this.mFlags, str);
                    data.setFlags(this.mFlags);
                    return data;
                }
            }

            @NonNull
            private Builder setFlag(int mask, boolean value) {
                if (value) {
                    this.mFlags |= mask;
                } else {
                    this.mFlags &= ~mask;
                }
                return this;
            }
        }

        @RequiresApi(29)
        public static class Api29Impl {
            private Api29Impl() {
            }

            @RequiresApi(29)
            @Nullable
            static Notification.BubbleMetadata toPlatform(@Nullable BubbleMetadata compatMetadata) {
                if (compatMetadata == null || compatMetadata.getIntent() == null) {
                    return null;
                }
                Notification.BubbleMetadata.Builder platformMetadataBuilder = new Notification.BubbleMetadata.Builder().setIcon(compatMetadata.getIcon().toIcon()).setIntent(compatMetadata.getIntent()).setDeleteIntent(compatMetadata.getDeleteIntent()).setAutoExpandBubble(compatMetadata.getAutoExpandBubble()).setSuppressNotification(compatMetadata.isNotificationSuppressed());
                if (compatMetadata.getDesiredHeight() != 0) {
                    platformMetadataBuilder.setDesiredHeight(compatMetadata.getDesiredHeight());
                }
                if (compatMetadata.getDesiredHeightResId() != 0) {
                    platformMetadataBuilder.setDesiredHeightResId(compatMetadata.getDesiredHeightResId());
                }
                return platformMetadataBuilder.build();
            }

            @RequiresApi(29)
            @Nullable
            static BubbleMetadata fromPlatform(@Nullable Notification.BubbleMetadata platformMetadata) {
                if (platformMetadata == null || platformMetadata.getIntent() == null) {
                    return null;
                }
                Builder compatBuilder = new Builder(platformMetadata.getIntent(), IconCompat.createFromIcon(platformMetadata.getIcon())).setAutoExpandBubble(platformMetadata.getAutoExpandBubble()).setDeleteIntent(platformMetadata.getDeleteIntent()).setSuppressNotification(platformMetadata.isNotificationSuppressed());
                if (platformMetadata.getDesiredHeight() != 0) {
                    compatBuilder.setDesiredHeight(platformMetadata.getDesiredHeight());
                }
                if (platformMetadata.getDesiredHeightResId() != 0) {
                    compatBuilder.setDesiredHeightResId(platformMetadata.getDesiredHeightResId());
                }
                return compatBuilder.build();
            }
        }

        @RequiresApi(30)
        public static class Api30Impl {
            private Api30Impl() {
            }

            @RequiresApi(30)
            @Nullable
            static Notification.BubbleMetadata toPlatform(@Nullable BubbleMetadata compatMetadata) {
                Notification.BubbleMetadata.Builder platformMetadataBuilder;
                if (compatMetadata == null) {
                    return null;
                }
                if (compatMetadata.getShortcutId() != null) {
                    platformMetadataBuilder = new Notification.BubbleMetadata.Builder(compatMetadata.getShortcutId());
                } else {
                    platformMetadataBuilder = new Notification.BubbleMetadata.Builder(compatMetadata.getIntent(), compatMetadata.getIcon().toIcon());
                }
                platformMetadataBuilder.setDeleteIntent(compatMetadata.getDeleteIntent()).setAutoExpandBubble(compatMetadata.getAutoExpandBubble()).setSuppressNotification(compatMetadata.isNotificationSuppressed());
                if (compatMetadata.getDesiredHeight() != 0) {
                    platformMetadataBuilder.setDesiredHeight(compatMetadata.getDesiredHeight());
                }
                if (compatMetadata.getDesiredHeightResId() != 0) {
                    platformMetadataBuilder.setDesiredHeightResId(compatMetadata.getDesiredHeightResId());
                }
                return platformMetadataBuilder.build();
            }

            @RequiresApi(30)
            @Nullable
            static BubbleMetadata fromPlatform(@Nullable Notification.BubbleMetadata platformMetadata) {
                Builder compatBuilder;
                if (platformMetadata == null) {
                    return null;
                }
                if (platformMetadata.getShortcutId() != null) {
                    compatBuilder = new Builder(platformMetadata.getShortcutId());
                } else {
                    compatBuilder = new Builder(platformMetadata.getIntent(), IconCompat.createFromIcon(platformMetadata.getIcon()));
                }
                compatBuilder.setAutoExpandBubble(platformMetadata.getAutoExpandBubble()).setDeleteIntent(platformMetadata.getDeleteIntent()).setSuppressNotification(platformMetadata.isNotificationSuppressed());
                if (platformMetadata.getDesiredHeight() != 0) {
                    compatBuilder.setDesiredHeight(platformMetadata.getDesiredHeight());
                }
                if (platformMetadata.getDesiredHeightResId() != 0) {
                    compatBuilder.setDesiredHeightResId(platformMetadata.getDesiredHeightResId());
                }
                return compatBuilder.build();
            }
        }
    }

    @NonNull
    static Notification[] getNotificationArrayFromBundle(@NonNull Bundle bundle, @NonNull String key) {
        Parcelable[] array = bundle.getParcelableArray(key);
        if ((array instanceof Notification[]) || array == null) {
            return (Notification[]) array;
        }
        Notification[] typedArray = new Notification[array.length];
        for (int i = 0; i < array.length; i++) {
            typedArray[i] = (Notification) array[i];
        }
        bundle.putParcelableArray(key, typedArray);
        return typedArray;
    }

    @Nullable
    public static Bundle getExtras(@NonNull Notification notification) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 19) {
            return notification.extras;
        }
        if (i >= 16) {
            return NotificationCompatJellybean.getExtras(notification);
        }
        return null;
    }

    public static int getActionCount(@NonNull Notification notification) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 19) {
            Notification.Action[] actionArr = notification.actions;
            if (actionArr != null) {
                return actionArr.length;
            }
            return 0;
        } else if (i >= 16) {
            return NotificationCompatJellybean.getActionCount(notification);
        } else {
            return 0;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: android.os.Bundle} */
    /* JADX WARNING: Multi-variable type inference failed */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static androidx.core.app.NotificationCompat.Action getAction(@androidx.annotation.NonNull android.app.Notification r6, int r7) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 20
            if (r0 < r1) goto L_0x000f
            android.app.Notification$Action[] r0 = r6.actions
            r0 = r0[r7]
            androidx.core.app.NotificationCompat$Action r0 = getActionCompatFromAction(r0)
            return r0
        L_0x000f:
            r1 = 19
            if (r0 < r1) goto L_0x0034
            android.app.Notification$Action[] r0 = r6.actions
            r0 = r0[r7]
            r1 = 0
            android.os.Bundle r2 = r6.extras
            java.lang.String r3 = "android.support.actionExtras"
            android.util.SparseArray r2 = r2.getSparseParcelableArray(r3)
            if (r2 == 0) goto L_0x0029
            java.lang.Object r3 = r2.get(r7)
            r1 = r3
            android.os.Bundle r1 = (android.os.Bundle) r1
        L_0x0029:
            int r3 = r0.icon
            java.lang.CharSequence r4 = r0.title
            android.app.PendingIntent r5 = r0.actionIntent
            androidx.core.app.NotificationCompat$Action r3 = androidx.core.app.NotificationCompatJellybean.readAction(r3, r4, r5, r1)
            return r3
        L_0x0034:
            r1 = 16
            if (r0 < r1) goto L_0x003d
            androidx.core.app.NotificationCompat$Action r0 = androidx.core.app.NotificationCompatJellybean.getAction(r6, r7)
            return r0
        L_0x003d:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.app.NotificationCompat.getAction(android.app.Notification, int):androidx.core.app.NotificationCompat$Action");
    }

    @Nullable
    public static BubbleMetadata getBubbleMetadata(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 29) {
            return BubbleMetadata.fromPlatform(notification.getBubbleMetadata());
        }
        return null;
    }

    @RequiresApi(20)
    @NonNull
    static Action getActionCompatFromAction(@NonNull Notification.Action action) {
        RemoteInput[] remoteInputs;
        boolean allowGeneratedReplies;
        int semanticAction;
        int i;
        int i2;
        Notification.Action action2 = action;
        RemoteInput[] srcArray = action.getRemoteInputs();
        boolean z = false;
        if (srcArray == null) {
            remoteInputs = null;
        } else {
            remoteInputs = new RemoteInput[srcArray.length];
            for (int i3 = 0; i3 < srcArray.length; i3++) {
                RemoteInput src = srcArray[i3];
                String resultKey = src.getResultKey();
                CharSequence label = src.getLabel();
                CharSequence[] choices = src.getChoices();
                boolean allowFreeFormInput = src.getAllowFreeFormInput();
                if (Build.VERSION.SDK_INT >= 29) {
                    i2 = src.getEditChoicesBeforeSending();
                } else {
                    i2 = 0;
                }
                remoteInputs[i3] = new RemoteInput(resultKey, label, choices, allowFreeFormInput, i2, src.getExtras(), (Set<String>) null);
            }
        }
        int i4 = Build.VERSION.SDK_INT;
        if (i4 >= 24) {
            allowGeneratedReplies = action.getExtras().getBoolean("android.support.allowGeneratedReplies") || action.getAllowGeneratedReplies();
        } else {
            allowGeneratedReplies = action.getExtras().getBoolean("android.support.allowGeneratedReplies");
        }
        boolean showsUserInterface = action.getExtras().getBoolean("android.support.action.showsUserInterface", true);
        if (i4 >= 28) {
            semanticAction = action.getSemanticAction();
        } else {
            semanticAction = action.getExtras().getInt("android.support.action.semanticAction", 0);
        }
        if (i4 >= 29) {
            z = action.isContextual();
        }
        boolean isContextual = z;
        if (i4 < 23) {
            return new Action(action2.icon, action2.title, action2.actionIntent, action.getExtras(), remoteInputs, (RemoteInput[]) null, allowGeneratedReplies, semanticAction, showsUserInterface, isContextual);
        } else if (action.getIcon() == null && (i = action2.icon) != 0) {
            return new Action(i, action2.title, action2.actionIntent, action.getExtras(), remoteInputs, (RemoteInput[]) null, allowGeneratedReplies, semanticAction, showsUserInterface, isContextual);
        } else {
            return new Action(action.getIcon() == null ? null : IconCompat.createFromIconOrNullIfZeroResId(action.getIcon()), action2.title, action2.actionIntent, action.getExtras(), remoteInputs, (RemoteInput[]) null, allowGeneratedReplies, semanticAction, showsUserInterface, isContextual);
        }
    }

    @RequiresApi(21)
    @NonNull
    public static List<Action> getInvisibleActions(@NonNull Notification notification) {
        Bundle carExtenderBundle;
        Bundle listBundle;
        ArrayList<Action> result = new ArrayList<>();
        if (!(Build.VERSION.SDK_INT < 19 || (carExtenderBundle = notification.extras.getBundle("android.car.EXTENSIONS")) == null || (listBundle = carExtenderBundle.getBundle("invisible_actions")) == null)) {
            for (int i = 0; i < listBundle.size(); i++) {
                result.add(NotificationCompatJellybean.getActionFromBundle(listBundle.getBundle(Integer.toString(i))));
            }
        }
        return result;
    }

    @NonNull
    public static List<Person> getPeople(@NonNull Notification notification) {
        String[] peopleArray;
        ArrayList<Person> result = new ArrayList<>();
        int i = Build.VERSION.SDK_INT;
        if (i >= 28) {
            ArrayList<android.app.Person> peopleList = notification.extras.getParcelableArrayList(EXTRA_PEOPLE_LIST);
            if (peopleList != null && !peopleList.isEmpty()) {
                Iterator<android.app.Person> it = peopleList.iterator();
                while (it.hasNext()) {
                    result.add(Person.fromAndroidPerson(it.next()));
                }
            }
        } else if (!(i < 19 || (peopleArray = notification.extras.getStringArray(EXTRA_PEOPLE)) == null || peopleArray.length == 0)) {
            for (String personUri : peopleArray) {
                result.add(new Person.Builder().setUri(personUri).build());
            }
        }
        return result;
    }

    @RequiresApi(19)
    @Nullable
    public static CharSequence getContentTitle(@NonNull Notification notification) {
        return notification.extras.getCharSequence(EXTRA_TITLE);
    }

    @RequiresApi(19)
    @Nullable
    public static CharSequence getContentText(@NonNull Notification notification) {
        return notification.extras.getCharSequence(EXTRA_TEXT);
    }

    @RequiresApi(19)
    @Nullable
    public static CharSequence getContentInfo(@NonNull Notification notification) {
        return notification.extras.getCharSequence(EXTRA_INFO_TEXT);
    }

    @RequiresApi(19)
    @Nullable
    public static CharSequence getSubText(@NonNull Notification notification) {
        return notification.extras.getCharSequence(EXTRA_SUB_TEXT);
    }

    @Nullable
    public static String getCategory(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 21) {
            return notification.category;
        }
        return null;
    }

    public static boolean getLocalOnly(@NonNull Notification notification) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 20) {
            if ((notification.flags & 256) != 0) {
                return true;
            }
            return false;
        } else if (i >= 19) {
            return notification.extras.getBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY);
        } else {
            if (i >= 16) {
                return NotificationCompatJellybean.getExtras(notification).getBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY);
            }
            return false;
        }
    }

    @Nullable
    public static String getGroup(@NonNull Notification notification) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 20) {
            return notification.getGroup();
        }
        if (i >= 19) {
            return notification.extras.getString(NotificationCompatExtras.EXTRA_GROUP_KEY);
        }
        if (i >= 16) {
            return NotificationCompatJellybean.getExtras(notification).getString(NotificationCompatExtras.EXTRA_GROUP_KEY);
        }
        return null;
    }

    @RequiresApi(19)
    public static boolean getShowWhen(@NonNull Notification notification) {
        return notification.extras.getBoolean(EXTRA_SHOW_WHEN);
    }

    @RequiresApi(19)
    public static boolean getUsesChronometer(@NonNull Notification notification) {
        return notification.extras.getBoolean(EXTRA_SHOW_CHRONOMETER);
    }

    public static boolean getOnlyAlertOnce(@NonNull Notification notification) {
        return (notification.flags & 8) != 0;
    }

    public static boolean getAutoCancel(@NonNull Notification notification) {
        return (notification.flags & 16) != 0;
    }

    public static boolean getOngoing(@NonNull Notification notification) {
        return (notification.flags & 2) != 0;
    }

    public static int getColor(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 21) {
            return notification.color;
        }
        return 0;
    }

    public static int getVisibility(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 21) {
            return notification.visibility;
        }
        return 0;
    }

    @Nullable
    public static Notification getPublicVersion(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 21) {
            return notification.publicVersion;
        }
        return null;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    static boolean getHighPriority(@NonNull Notification notification) {
        return (notification.flags & 128) != 0;
    }

    public static boolean isGroupSummary(@NonNull Notification notification) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 20) {
            if ((notification.flags & 512) != 0) {
                return true;
            }
            return false;
        } else if (i >= 19) {
            return notification.extras.getBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY);
        } else {
            if (i >= 16) {
                return NotificationCompatJellybean.getExtras(notification).getBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY);
            }
            return false;
        }
    }

    @Nullable
    public static String getSortKey(@NonNull Notification notification) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 20) {
            return notification.getSortKey();
        }
        if (i >= 19) {
            return notification.extras.getString(NotificationCompatExtras.EXTRA_SORT_KEY);
        }
        if (i >= 16) {
            return NotificationCompatJellybean.getExtras(notification).getString(NotificationCompatExtras.EXTRA_SORT_KEY);
        }
        return null;
    }

    @Nullable
    public static String getChannelId(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getChannelId();
        }
        return null;
    }

    public static long getTimeoutAfter(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getTimeoutAfter();
        }
        return 0;
    }

    public static int getBadgeIconType(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getBadgeIconType();
        }
        return 0;
    }

    @Nullable
    public static String getShortcutId(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getShortcutId();
        }
        return null;
    }

    @Nullable
    public static CharSequence getSettingsText(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getSettingsText();
        }
        return null;
    }

    @Nullable
    public static LocusIdCompat getLocusId(@NonNull Notification notification) {
        LocusId locusId;
        if (Build.VERSION.SDK_INT < 29 || (locusId = notification.getLocusId()) == null) {
            return null;
        }
        return LocusIdCompat.toLocusIdCompat(locusId);
    }

    public static int getGroupAlertBehavior(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getGroupAlertBehavior();
        }
        return 0;
    }

    public static boolean getAllowSystemGeneratedContextualActions(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 29) {
            return notification.getAllowSystemGeneratedContextualActions();
        }
        return false;
    }
}
