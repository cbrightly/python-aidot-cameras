package androidx.core.content.pm;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.Person;
import androidx.core.content.LocusIdCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.net.UriCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShortcutInfoCompat {
    private static final String EXTRA_LOCUS_ID = "extraLocusId";
    private static final String EXTRA_LONG_LIVED = "extraLongLived";
    private static final String EXTRA_PERSON_ = "extraPerson_";
    private static final String EXTRA_PERSON_COUNT = "extraPersonCount";
    private static final String EXTRA_SLICE_URI = "extraSliceUri";
    ComponentName mActivity;
    Set<String> mCategories;
    Context mContext;
    CharSequence mDisabledMessage;
    int mDisabledReason;
    PersistableBundle mExtras;
    boolean mHasKeyFieldsOnly;
    IconCompat mIcon;
    String mId;
    Intent[] mIntents;
    boolean mIsAlwaysBadged;
    boolean mIsCached;
    boolean mIsDeclaredInManifest;
    boolean mIsDynamic;
    boolean mIsEnabled = true;
    boolean mIsImmutable;
    boolean mIsLongLived;
    boolean mIsPinned;
    CharSequence mLabel;
    long mLastChangedTimestamp;
    @Nullable
    LocusIdCompat mLocusId;
    CharSequence mLongLabel;
    String mPackageName;
    Person[] mPersons;
    int mRank;
    UserHandle mUser;

    ShortcutInfoCompat() {
    }

    @RequiresApi(25)
    public ShortcutInfo toShortcutInfo() {
        ShortcutInfo.Builder builder = new ShortcutInfo.Builder(this.mContext, this.mId).setShortLabel(this.mLabel).setIntents(this.mIntents);
        IconCompat iconCompat = this.mIcon;
        if (iconCompat != null) {
            builder.setIcon(iconCompat.toIcon(this.mContext));
        }
        if (!TextUtils.isEmpty(this.mLongLabel)) {
            builder.setLongLabel(this.mLongLabel);
        }
        if (!TextUtils.isEmpty(this.mDisabledMessage)) {
            builder.setDisabledMessage(this.mDisabledMessage);
        }
        ComponentName componentName = this.mActivity;
        if (componentName != null) {
            builder.setActivity(componentName);
        }
        Set<String> set = this.mCategories;
        if (set != null) {
            builder.setCategories(set);
        }
        builder.setRank(this.mRank);
        PersistableBundle persistableBundle = this.mExtras;
        if (persistableBundle != null) {
            builder.setExtras(persistableBundle);
        }
        if (Build.VERSION.SDK_INT >= 29) {
            Person[] personArr = this.mPersons;
            if (personArr != null && personArr.length > 0) {
                android.app.Person[] persons = new android.app.Person[personArr.length];
                for (int i = 0; i < persons.length; i++) {
                    persons[i] = this.mPersons[i].toAndroidPerson();
                }
                builder.setPersons(persons);
            }
            LocusIdCompat locusIdCompat = this.mLocusId;
            if (locusIdCompat != null) {
                builder.setLocusId(locusIdCompat.toLocusId());
            }
            builder.setLongLived(this.mIsLongLived);
        } else {
            builder.setExtras(buildLegacyExtrasBundle());
        }
        return builder.build();
    }

    @RequiresApi(22)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    private PersistableBundle buildLegacyExtrasBundle() {
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        Person[] personArr = this.mPersons;
        if (personArr != null && personArr.length > 0) {
            this.mExtras.putInt(EXTRA_PERSON_COUNT, personArr.length);
            for (int i = 0; i < this.mPersons.length; i++) {
                PersistableBundle persistableBundle = this.mExtras;
                persistableBundle.putPersistableBundle(EXTRA_PERSON_ + (i + 1), this.mPersons[i].toPersistableBundle());
            }
        }
        LocusIdCompat locusIdCompat = this.mLocusId;
        if (locusIdCompat != null) {
            this.mExtras.putString(EXTRA_LOCUS_ID, locusIdCompat.getId());
        }
        this.mExtras.putBoolean(EXTRA_LONG_LIVED, this.mIsLongLived);
        return this.mExtras;
    }

    /* access modifiers changed from: package-private */
    public Intent addToIntent(Intent outIntent) {
        Intent[] intentArr = this.mIntents;
        outIntent.putExtra("android.intent.extra.shortcut.INTENT", intentArr[intentArr.length - 1]).putExtra("android.intent.extra.shortcut.NAME", this.mLabel.toString());
        if (this.mIcon != null) {
            Drawable badge = null;
            if (this.mIsAlwaysBadged) {
                PackageManager pm = this.mContext.getPackageManager();
                ComponentName componentName = this.mActivity;
                if (componentName != null) {
                    try {
                        badge = pm.getActivityIcon(componentName);
                    } catch (PackageManager.NameNotFoundException e) {
                    }
                }
                if (badge == null) {
                    badge = this.mContext.getApplicationInfo().loadIcon(pm);
                }
            }
            this.mIcon.addToShortcutIntent(outIntent, badge, this.mContext);
        }
        return outIntent;
    }

    @NonNull
    public String getId() {
        return this.mId;
    }

    @NonNull
    public String getPackage() {
        return this.mPackageName;
    }

    @Nullable
    public ComponentName getActivity() {
        return this.mActivity;
    }

    @NonNull
    public CharSequence getShortLabel() {
        return this.mLabel;
    }

    @Nullable
    public CharSequence getLongLabel() {
        return this.mLongLabel;
    }

    @Nullable
    public CharSequence getDisabledMessage() {
        return this.mDisabledMessage;
    }

    public int getDisabledReason() {
        return this.mDisabledReason;
    }

    @NonNull
    public Intent getIntent() {
        Intent[] intentArr = this.mIntents;
        return intentArr[intentArr.length - 1];
    }

    @NonNull
    public Intent[] getIntents() {
        Intent[] intentArr = this.mIntents;
        return (Intent[]) Arrays.copyOf(intentArr, intentArr.length);
    }

    @Nullable
    public Set<String> getCategories() {
        return this.mCategories;
    }

    @Nullable
    public LocusIdCompat getLocusId() {
        return this.mLocusId;
    }

    public int getRank() {
        return this.mRank;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public IconCompat getIcon() {
        return this.mIcon;
    }

    @RequiresApi(25)
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @VisibleForTesting
    static Person[] getPersonsFromExtra(@NonNull PersistableBundle bundle) {
        if (bundle == null || !bundle.containsKey(EXTRA_PERSON_COUNT)) {
            return null;
        }
        int personsLength = bundle.getInt(EXTRA_PERSON_COUNT);
        Person[] persons = new Person[personsLength];
        for (int i = 0; i < personsLength; i++) {
            persons[i] = Person.fromPersistableBundle(bundle.getPersistableBundle(EXTRA_PERSON_ + (i + 1)));
        }
        return persons;
    }

    @RequiresApi(25)
    @VisibleForTesting
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    static boolean getLongLivedFromExtra(@Nullable PersistableBundle bundle) {
        if (bundle == null || !bundle.containsKey(EXTRA_LONG_LIVED)) {
            return false;
        }
        return bundle.getBoolean(EXTRA_LONG_LIVED);
    }

    @RequiresApi(25)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    static List<ShortcutInfoCompat> fromShortcuts(@NonNull Context context, @NonNull List<ShortcutInfo> shortcuts) {
        List<ShortcutInfoCompat> results = new ArrayList<>(shortcuts.size());
        for (ShortcutInfo s : shortcuts) {
            results.add(new Builder(context, s).build());
        }
        return results;
    }

    @Nullable
    public PersistableBundle getExtras() {
        return this.mExtras;
    }

    @Nullable
    public UserHandle getUserHandle() {
        return this.mUser;
    }

    public long getLastChangedTimestamp() {
        return this.mLastChangedTimestamp;
    }

    public boolean isCached() {
        return this.mIsCached;
    }

    public boolean isDynamic() {
        return this.mIsDynamic;
    }

    public boolean isPinned() {
        return this.mIsPinned;
    }

    public boolean isDeclaredInManifest() {
        return this.mIsDeclaredInManifest;
    }

    public boolean isImmutable() {
        return this.mIsImmutable;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public boolean hasKeyFieldsOnly() {
        return this.mHasKeyFieldsOnly;
    }

    @RequiresApi(25)
    @Nullable
    static LocusIdCompat getLocusId(@NonNull ShortcutInfo shortcutInfo) {
        if (Build.VERSION.SDK_INT < 29) {
            return getLocusIdFromExtra(shortcutInfo.getExtras());
        }
        if (shortcutInfo.getLocusId() == null) {
            return null;
        }
        return LocusIdCompat.toLocusIdCompat(shortcutInfo.getLocusId());
    }

    @RequiresApi(25)
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    private static LocusIdCompat getLocusIdFromExtra(@Nullable PersistableBundle bundle) {
        String locusId;
        if (bundle == null || (locusId = bundle.getString(EXTRA_LOCUS_ID)) == null) {
            return null;
        }
        return new LocusIdCompat(locusId);
    }

    public static class Builder {
        private Map<String, Map<String, List<String>>> mCapabilityBindingParams;
        private Set<String> mCapabilityBindings;
        private final ShortcutInfoCompat mInfo;
        private boolean mIsConversation;
        private Uri mSliceUri;

        public Builder(@NonNull Context context, @NonNull String id) {
            ShortcutInfoCompat shortcutInfoCompat = new ShortcutInfoCompat();
            this.mInfo = shortcutInfoCompat;
            shortcutInfoCompat.mContext = context;
            shortcutInfoCompat.mId = id;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Builder(@NonNull ShortcutInfoCompat shortcutInfo) {
            ShortcutInfoCompat shortcutInfoCompat = new ShortcutInfoCompat();
            this.mInfo = shortcutInfoCompat;
            shortcutInfoCompat.mContext = shortcutInfo.mContext;
            shortcutInfoCompat.mId = shortcutInfo.mId;
            shortcutInfoCompat.mPackageName = shortcutInfo.mPackageName;
            Intent[] intentArr = shortcutInfo.mIntents;
            shortcutInfoCompat.mIntents = (Intent[]) Arrays.copyOf(intentArr, intentArr.length);
            shortcutInfoCompat.mActivity = shortcutInfo.mActivity;
            shortcutInfoCompat.mLabel = shortcutInfo.mLabel;
            shortcutInfoCompat.mLongLabel = shortcutInfo.mLongLabel;
            shortcutInfoCompat.mDisabledMessage = shortcutInfo.mDisabledMessage;
            shortcutInfoCompat.mDisabledReason = shortcutInfo.mDisabledReason;
            shortcutInfoCompat.mIcon = shortcutInfo.mIcon;
            shortcutInfoCompat.mIsAlwaysBadged = shortcutInfo.mIsAlwaysBadged;
            shortcutInfoCompat.mUser = shortcutInfo.mUser;
            shortcutInfoCompat.mLastChangedTimestamp = shortcutInfo.mLastChangedTimestamp;
            shortcutInfoCompat.mIsCached = shortcutInfo.mIsCached;
            shortcutInfoCompat.mIsDynamic = shortcutInfo.mIsDynamic;
            shortcutInfoCompat.mIsPinned = shortcutInfo.mIsPinned;
            shortcutInfoCompat.mIsDeclaredInManifest = shortcutInfo.mIsDeclaredInManifest;
            shortcutInfoCompat.mIsImmutable = shortcutInfo.mIsImmutable;
            shortcutInfoCompat.mIsEnabled = shortcutInfo.mIsEnabled;
            shortcutInfoCompat.mLocusId = shortcutInfo.mLocusId;
            shortcutInfoCompat.mIsLongLived = shortcutInfo.mIsLongLived;
            shortcutInfoCompat.mHasKeyFieldsOnly = shortcutInfo.mHasKeyFieldsOnly;
            shortcutInfoCompat.mRank = shortcutInfo.mRank;
            Person[] personArr = shortcutInfo.mPersons;
            if (personArr != null) {
                shortcutInfoCompat.mPersons = (Person[]) Arrays.copyOf(personArr, personArr.length);
            }
            if (shortcutInfo.mCategories != null) {
                shortcutInfoCompat.mCategories = new HashSet(shortcutInfo.mCategories);
            }
            PersistableBundle persistableBundle = shortcutInfo.mExtras;
            if (persistableBundle != null) {
                shortcutInfoCompat.mExtras = persistableBundle;
            }
        }

        @RequiresApi(25)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Builder(@NonNull Context context, @NonNull ShortcutInfo shortcutInfo) {
            int i;
            ShortcutInfoCompat shortcutInfoCompat = new ShortcutInfoCompat();
            this.mInfo = shortcutInfoCompat;
            shortcutInfoCompat.mContext = context;
            shortcutInfoCompat.mId = shortcutInfo.getId();
            shortcutInfoCompat.mPackageName = shortcutInfo.getPackage();
            Intent[] intents = shortcutInfo.getIntents();
            shortcutInfoCompat.mIntents = (Intent[]) Arrays.copyOf(intents, intents.length);
            shortcutInfoCompat.mActivity = shortcutInfo.getActivity();
            shortcutInfoCompat.mLabel = shortcutInfo.getShortLabel();
            shortcutInfoCompat.mLongLabel = shortcutInfo.getLongLabel();
            shortcutInfoCompat.mDisabledMessage = shortcutInfo.getDisabledMessage();
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 28) {
                shortcutInfoCompat.mDisabledReason = shortcutInfo.getDisabledReason();
            } else {
                if (shortcutInfo.isEnabled()) {
                    i = 0;
                } else {
                    i = 3;
                }
                shortcutInfoCompat.mDisabledReason = i;
            }
            shortcutInfoCompat.mCategories = shortcutInfo.getCategories();
            shortcutInfoCompat.mPersons = ShortcutInfoCompat.getPersonsFromExtra(shortcutInfo.getExtras());
            shortcutInfoCompat.mUser = shortcutInfo.getUserHandle();
            shortcutInfoCompat.mLastChangedTimestamp = shortcutInfo.getLastChangedTimestamp();
            if (i2 >= 30) {
                shortcutInfoCompat.mIsCached = shortcutInfo.isCached();
            }
            shortcutInfoCompat.mIsDynamic = shortcutInfo.isDynamic();
            shortcutInfoCompat.mIsPinned = shortcutInfo.isPinned();
            shortcutInfoCompat.mIsDeclaredInManifest = shortcutInfo.isDeclaredInManifest();
            shortcutInfoCompat.mIsImmutable = shortcutInfo.isImmutable();
            shortcutInfoCompat.mIsEnabled = shortcutInfo.isEnabled();
            shortcutInfoCompat.mHasKeyFieldsOnly = shortcutInfo.hasKeyFieldsOnly();
            shortcutInfoCompat.mLocusId = ShortcutInfoCompat.getLocusId(shortcutInfo);
            shortcutInfoCompat.mRank = shortcutInfo.getRank();
            shortcutInfoCompat.mExtras = shortcutInfo.getExtras();
        }

        @NonNull
        public Builder setShortLabel(@NonNull CharSequence shortLabel) {
            this.mInfo.mLabel = shortLabel;
            return this;
        }

        @NonNull
        public Builder setLongLabel(@NonNull CharSequence longLabel) {
            this.mInfo.mLongLabel = longLabel;
            return this;
        }

        @NonNull
        public Builder setDisabledMessage(@NonNull CharSequence disabledMessage) {
            this.mInfo.mDisabledMessage = disabledMessage;
            return this;
        }

        @NonNull
        public Builder setIntent(@NonNull Intent intent) {
            return setIntents(new Intent[]{intent});
        }

        @NonNull
        public Builder setIntents(@NonNull Intent[] intents) {
            this.mInfo.mIntents = intents;
            return this;
        }

        @NonNull
        public Builder setIcon(IconCompat icon) {
            this.mInfo.mIcon = icon;
            return this;
        }

        @NonNull
        public Builder setLocusId(@Nullable LocusIdCompat locusId) {
            this.mInfo.mLocusId = locusId;
            return this;
        }

        @NonNull
        public Builder setIsConversation() {
            this.mIsConversation = true;
            return this;
        }

        @NonNull
        public Builder setActivity(@NonNull ComponentName activity) {
            this.mInfo.mActivity = activity;
            return this;
        }

        @NonNull
        public Builder setAlwaysBadged() {
            this.mInfo.mIsAlwaysBadged = true;
            return this;
        }

        @NonNull
        public Builder setPerson(@NonNull Person person) {
            return setPersons(new Person[]{person});
        }

        @NonNull
        public Builder setPersons(@NonNull Person[] persons) {
            this.mInfo.mPersons = persons;
            return this;
        }

        @NonNull
        public Builder setCategories(@NonNull Set<String> categories) {
            this.mInfo.mCategories = categories;
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setLongLived() {
            this.mInfo.mIsLongLived = true;
            return this;
        }

        @NonNull
        public Builder setLongLived(boolean longLived) {
            this.mInfo.mIsLongLived = longLived;
            return this;
        }

        @NonNull
        public Builder setRank(int rank) {
            this.mInfo.mRank = rank;
            return this;
        }

        @NonNull
        public Builder setExtras(@NonNull PersistableBundle extras) {
            this.mInfo.mExtras = extras;
            return this;
        }

        @SuppressLint({"MissingGetterMatchingBuilder"})
        @NonNull
        public Builder addCapabilityBinding(@NonNull String capability) {
            if (this.mCapabilityBindings == null) {
                this.mCapabilityBindings = new HashSet();
            }
            this.mCapabilityBindings.add(capability);
            return this;
        }

        @SuppressLint({"MissingGetterMatchingBuilder"})
        @NonNull
        public Builder addCapabilityBinding(@NonNull String capability, @NonNull String parameter, @NonNull List<String> parameterValues) {
            addCapabilityBinding(capability);
            if (!parameterValues.isEmpty()) {
                if (this.mCapabilityBindingParams == null) {
                    this.mCapabilityBindingParams = new HashMap();
                }
                if (this.mCapabilityBindingParams.get(capability) == null) {
                    this.mCapabilityBindingParams.put(capability, new HashMap());
                }
                this.mCapabilityBindingParams.get(capability).put(parameter, parameterValues);
            }
            return this;
        }

        @SuppressLint({"MissingGetterMatchingBuilder"})
        @NonNull
        public Builder setSliceUri(@NonNull Uri sliceUri) {
            this.mSliceUri = sliceUri;
            return this;
        }

        @SuppressLint({"UnsafeNewApiCall"})
        @NonNull
        public ShortcutInfoCompat build() {
            if (!TextUtils.isEmpty(this.mInfo.mLabel)) {
                ShortcutInfoCompat shortcutInfoCompat = this.mInfo;
                Intent[] intentArr = shortcutInfoCompat.mIntents;
                if (intentArr == null || intentArr.length == 0) {
                    throw new IllegalArgumentException("Shortcut must have an intent");
                }
                if (this.mIsConversation) {
                    if (shortcutInfoCompat.mLocusId == null) {
                        shortcutInfoCompat.mLocusId = new LocusIdCompat(shortcutInfoCompat.mId);
                    }
                    this.mInfo.mIsLongLived = true;
                }
                if (this.mCapabilityBindings != null) {
                    ShortcutInfoCompat shortcutInfoCompat2 = this.mInfo;
                    if (shortcutInfoCompat2.mCategories == null) {
                        shortcutInfoCompat2.mCategories = new HashSet();
                    }
                    this.mInfo.mCategories.addAll(this.mCapabilityBindings);
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    if (this.mCapabilityBindingParams != null) {
                        ShortcutInfoCompat shortcutInfoCompat3 = this.mInfo;
                        if (shortcutInfoCompat3.mExtras == null) {
                            shortcutInfoCompat3.mExtras = new PersistableBundle();
                        }
                        for (String capability : this.mCapabilityBindingParams.keySet()) {
                            Map<String, List<String>> params = this.mCapabilityBindingParams.get(capability);
                            this.mInfo.mExtras.putStringArray(capability, (String[]) params.keySet().toArray(new String[0]));
                            for (String paramName : params.keySet()) {
                                List<String> value = params.get(paramName);
                                PersistableBundle persistableBundle = this.mInfo.mExtras;
                                String str = capability + "/" + paramName;
                                String[] strArr = new String[0];
                                if (value != null) {
                                    strArr = (String[]) value.toArray(strArr);
                                }
                                persistableBundle.putStringArray(str, strArr);
                            }
                        }
                    }
                    if (this.mSliceUri != null) {
                        ShortcutInfoCompat shortcutInfoCompat4 = this.mInfo;
                        if (shortcutInfoCompat4.mExtras == null) {
                            shortcutInfoCompat4.mExtras = new PersistableBundle();
                        }
                        this.mInfo.mExtras.putString(ShortcutInfoCompat.EXTRA_SLICE_URI, UriCompat.toSafeString(this.mSliceUri));
                    }
                }
                return this.mInfo;
            }
            throw new IllegalArgumentException("Shortcut must have a non-empty label");
        }
    }
}
