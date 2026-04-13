package androidx.core.view;

import android.content.ClipData;
import android.content.ClipDescription;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;
import androidx.core.util.Predicate;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public final class ContentInfoCompat {
    public static final int FLAG_CONVERT_TO_PLAIN_TEXT = 1;
    public static final int SOURCE_APP = 0;
    public static final int SOURCE_CLIPBOARD = 1;
    public static final int SOURCE_DRAG_AND_DROP = 3;
    public static final int SOURCE_INPUT_METHOD = 2;
    @NonNull
    final ClipData mClip;
    @Nullable
    final Bundle mExtras;
    final int mFlags;
    @Nullable
    final Uri mLinkUri;
    final int mSource;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Source {
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    static String sourceToString(int source) {
        switch (source) {
            case 0:
                return "SOURCE_APP";
            case 1:
                return "SOURCE_CLIPBOARD";
            case 2:
                return "SOURCE_INPUT_METHOD";
            case 3:
                return "SOURCE_DRAG_AND_DROP";
            default:
                return String.valueOf(source);
        }
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    static String flagsToString(int flags) {
        if ((flags & 1) != 0) {
            return "FLAG_CONVERT_TO_PLAIN_TEXT";
        }
        return String.valueOf(flags);
    }

    ContentInfoCompat(Builder b) {
        this.mClip = (ClipData) Preconditions.checkNotNull(b.mClip);
        this.mSource = Preconditions.checkArgumentInRange(b.mSource, 0, 3, "source");
        this.mFlags = Preconditions.checkFlagsArgument(b.mFlags, 1);
        this.mLinkUri = b.mLinkUri;
        this.mExtras = b.mExtras;
    }

    @NonNull
    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("ContentInfoCompat{clip=");
        sb.append(this.mClip.getDescription());
        sb.append(", source=");
        sb.append(sourceToString(this.mSource));
        sb.append(", flags=");
        sb.append(flagsToString(this.mFlags));
        String str2 = "";
        if (this.mLinkUri == null) {
            str = str2;
        } else {
            str = ", hasLinkUri(" + this.mLinkUri.toString().length() + ")";
        }
        sb.append(str);
        if (this.mExtras != null) {
            str2 = ", hasExtras";
        }
        sb.append(str2);
        sb.append("}");
        return sb.toString();
    }

    @NonNull
    public ClipData getClip() {
        return this.mClip;
    }

    public int getSource() {
        return this.mSource;
    }

    public int getFlags() {
        return this.mFlags;
    }

    @Nullable
    public Uri getLinkUri() {
        return this.mLinkUri;
    }

    @Nullable
    public Bundle getExtras() {
        return this.mExtras;
    }

    @NonNull
    public Pair<ContentInfoCompat, ContentInfoCompat> partition(@NonNull Predicate<ClipData.Item> itemPredicate) {
        ContentInfoCompat contentInfoCompat = null;
        if (this.mClip.getItemCount() == 1) {
            boolean matched = itemPredicate.test(this.mClip.getItemAt(0));
            ContentInfoCompat contentInfoCompat2 = matched ? this : null;
            if (!matched) {
                contentInfoCompat = this;
            }
            return Pair.create(contentInfoCompat2, contentInfoCompat);
        }
        ArrayList<ClipData.Item> acceptedItems = new ArrayList<>();
        ArrayList<ClipData.Item> remainingItems = new ArrayList<>();
        for (int i = 0; i < this.mClip.getItemCount(); i++) {
            ClipData.Item item = this.mClip.getItemAt(i);
            if (itemPredicate.test(item)) {
                acceptedItems.add(item);
            } else {
                remainingItems.add(item);
            }
        }
        if (acceptedItems.isEmpty() != 0) {
            return Pair.create((Object) null, this);
        }
        if (remainingItems.isEmpty()) {
            return Pair.create(this, (Object) null);
        }
        return Pair.create(new Builder(this).setClip(buildClipData(this.mClip.getDescription(), acceptedItems)).build(), new Builder(this).setClip(buildClipData(this.mClip.getDescription(), remainingItems)).build());
    }

    private static ClipData buildClipData(ClipDescription description, List<ClipData.Item> items) {
        ClipData clip = new ClipData(new ClipDescription(description), items.get(0));
        for (int i = 1; i < items.size(); i++) {
            clip.addItem(items.get(i));
        }
        return clip;
    }

    public static final class Builder {
        @NonNull
        ClipData mClip;
        @Nullable
        Bundle mExtras;
        int mFlags;
        @Nullable
        Uri mLinkUri;
        int mSource;

        public Builder(@NonNull ContentInfoCompat other) {
            this.mClip = other.mClip;
            this.mSource = other.mSource;
            this.mFlags = other.mFlags;
            this.mLinkUri = other.mLinkUri;
            this.mExtras = other.mExtras;
        }

        public Builder(@NonNull ClipData clip, int source) {
            this.mClip = clip;
            this.mSource = source;
        }

        @NonNull
        public Builder setClip(@NonNull ClipData clip) {
            this.mClip = clip;
            return this;
        }

        @NonNull
        public Builder setSource(int source) {
            this.mSource = source;
            return this;
        }

        @NonNull
        public Builder setFlags(int flags) {
            this.mFlags = flags;
            return this;
        }

        @NonNull
        public Builder setLinkUri(@Nullable Uri linkUri) {
            this.mLinkUri = linkUri;
            return this;
        }

        @NonNull
        public Builder setExtras(@Nullable Bundle extras) {
            this.mExtras = extras;
            return this;
        }

        @NonNull
        public ContentInfoCompat build() {
            return new ContentInfoCompat(this);
        }
    }
}
