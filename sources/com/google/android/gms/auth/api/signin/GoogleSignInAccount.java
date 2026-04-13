package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArraySet;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.AccountType;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SafeParcelable.Class(creator = "GoogleSignInAccountCreator")
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public class GoogleSignInAccount extends AbstractSafeParcelable implements ReflectedParcelable {
    @NonNull
    public static final Parcelable.Creator<GoogleSignInAccount> CREATOR = new zab();
    @NonNull
    @VisibleForTesting
    public static final Clock zaa = DefaultClock.getInstance();
    @SafeParcelable.VersionField(id = 1)
    final int zab;
    @SafeParcelable.Field(id = 10)
    final List zac;
    @SafeParcelable.Field(getter = "getId", id = 2)
    @Nullable
    private final String zad;
    @SafeParcelable.Field(getter = "getIdToken", id = 3)
    @Nullable
    private final String zae;
    @SafeParcelable.Field(getter = "getEmail", id = 4)
    @Nullable
    private final String zaf;
    @SafeParcelable.Field(getter = "getDisplayName", id = 5)
    @Nullable
    private final String zag;
    @SafeParcelable.Field(getter = "getPhotoUrl", id = 6)
    @Nullable
    private final Uri zah;
    @SafeParcelable.Field(getter = "getServerAuthCode", id = 7)
    @Nullable
    private String zai;
    @SafeParcelable.Field(getter = "getExpirationTimeSecs", id = 8)
    private final long zaj;
    @SafeParcelable.Field(getter = "getObfuscatedIdentifier", id = 9)
    private final String zak;
    @SafeParcelable.Field(getter = "getGivenName", id = 11)
    @Nullable
    private final String zal;
    @SafeParcelable.Field(getter = "getFamilyName", id = 12)
    @Nullable
    private final String zam;
    private final Set zan = new HashSet();

    @SafeParcelable.Constructor
    GoogleSignInAccount(@SafeParcelable.Param(id = 1) int i, @SafeParcelable.Param(id = 2) @Nullable String str, @SafeParcelable.Param(id = 3) @Nullable String str2, @SafeParcelable.Param(id = 4) @Nullable String str3, @SafeParcelable.Param(id = 5) @Nullable String str4, @SafeParcelable.Param(id = 6) @Nullable Uri uri, @SafeParcelable.Param(id = 7) @Nullable String str5, @SafeParcelable.Param(id = 8) long j, @SafeParcelable.Param(id = 9) String str6, @SafeParcelable.Param(id = 10) List list, @SafeParcelable.Param(id = 11) @Nullable String str7, @SafeParcelable.Param(id = 12) @Nullable String str8) {
        this.zab = i;
        this.zad = str;
        this.zae = str2;
        this.zaf = str3;
        this.zag = str4;
        this.zah = uri;
        this.zai = str5;
        this.zaj = j;
        this.zak = str6;
        this.zac = list;
        this.zal = str7;
        this.zam = str8;
    }

    @NonNull
    @KeepForSdk
    public static GoogleSignInAccount createDefault() {
        return zae(new Account("<<default account>>", AccountType.GOOGLE), new HashSet());
    }

    @NonNull
    @KeepForSdk
    public static GoogleSignInAccount fromAccount(@NonNull Account account) {
        return zae(account, new ArraySet());
    }

    @NonNull
    public static GoogleSignInAccount zaa(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable Uri uri, @Nullable Long l, @NonNull String str7, @NonNull Set set) {
        return new GoogleSignInAccount(3, str, str2, str3, str4, uri, (String) null, l.longValue(), Preconditions.checkNotEmpty(str7), new ArrayList((Collection) Preconditions.checkNotNull(set)), str5, str6);
    }

    @Nullable
    public static GoogleSignInAccount zab(@Nullable String str) {
        Uri uri;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7 = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        String optString = jSONObject.optString("photoUrl");
        if (!TextUtils.isEmpty(optString)) {
            uri = Uri.parse(optString);
        } else {
            uri = null;
        }
        long parseLong = Long.parseLong(jSONObject.getString("expirationTime"));
        HashSet hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("grantedScopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        String optString2 = jSONObject.optString("id");
        if (jSONObject.has("tokenId")) {
            str2 = jSONObject.optString("tokenId");
        } else {
            str2 = null;
        }
        if (jSONObject.has("email")) {
            str3 = jSONObject.optString("email");
        } else {
            str3 = null;
        }
        if (jSONObject.has("displayName")) {
            str4 = jSONObject.optString("displayName");
        } else {
            str4 = null;
        }
        if (jSONObject.has("givenName")) {
            str5 = jSONObject.optString("givenName");
        } else {
            str5 = null;
        }
        if (jSONObject.has("familyName")) {
            str6 = jSONObject.optString("familyName");
        } else {
            str6 = null;
        }
        GoogleSignInAccount zaa2 = zaa(optString2, str2, str3, str4, str5, str6, uri, Long.valueOf(parseLong), jSONObject.getString("obfuscatedIdentifier"), hashSet);
        if (jSONObject.has("serverAuthCode")) {
            str7 = jSONObject.optString("serverAuthCode");
        }
        zaa2.zai = str7;
        return zaa2;
    }

    private static GoogleSignInAccount zae(Account account, Set set) {
        return zaa((String) null, (String) null, account.name, (String) null, (String) null, (String) null, (Uri) null, 0L, account.name, set);
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GoogleSignInAccount)) {
            return false;
        }
        GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount) obj;
        return googleSignInAccount.zak.equals(this.zak) && googleSignInAccount.getRequestedScopes().equals(getRequestedScopes());
    }

    @Nullable
    public Account getAccount() {
        String str = this.zaf;
        if (str == null) {
            return null;
        }
        return new Account(str, AccountType.GOOGLE);
    }

    @Nullable
    public String getDisplayName() {
        return this.zag;
    }

    @Nullable
    public String getEmail() {
        return this.zaf;
    }

    @Nullable
    public String getFamilyName() {
        return this.zam;
    }

    @Nullable
    public String getGivenName() {
        return this.zal;
    }

    @NonNull
    public Set<Scope> getGrantedScopes() {
        return new HashSet(this.zac);
    }

    @Nullable
    public String getId() {
        return this.zad;
    }

    @Nullable
    public String getIdToken() {
        return this.zae;
    }

    @Nullable
    public Uri getPhotoUrl() {
        return this.zah;
    }

    @NonNull
    @KeepForSdk
    public Set<Scope> getRequestedScopes() {
        HashSet hashSet = new HashSet(this.zac);
        hashSet.addAll(this.zan);
        return hashSet;
    }

    @Nullable
    public String getServerAuthCode() {
        return this.zai;
    }

    public int hashCode() {
        return ((this.zak.hashCode() + 527) * 31) + getRequestedScopes().hashCode();
    }

    @KeepForSdk
    public boolean isExpired() {
        return zaa.currentTimeMillis() / 1000 >= this.zaj + -300;
    }

    @NonNull
    @KeepForSdk
    @CanIgnoreReturnValue
    public GoogleSignInAccount requestExtraScopes(@NonNull Scope... scopes) {
        if (scopes != null) {
            Collections.addAll(this.zan, scopes);
        }
        return this;
    }

    public void writeToParcel(@NonNull Parcel out, int flags) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeInt(out, 1, this.zab);
        SafeParcelWriter.writeString(out, 2, getId(), false);
        SafeParcelWriter.writeString(out, 3, getIdToken(), false);
        SafeParcelWriter.writeString(out, 4, getEmail(), false);
        SafeParcelWriter.writeString(out, 5, getDisplayName(), false);
        SafeParcelWriter.writeParcelable(out, 6, getPhotoUrl(), flags, false);
        SafeParcelWriter.writeString(out, 7, getServerAuthCode(), false);
        SafeParcelWriter.writeLong(out, 8, this.zaj);
        SafeParcelWriter.writeString(out, 9, this.zak, false);
        SafeParcelWriter.writeTypedList(out, 10, this.zac, false);
        SafeParcelWriter.writeString(out, 11, getGivenName(), false);
        SafeParcelWriter.writeString(out, 12, getFamilyName(), false);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }

    @NonNull
    public final String zac() {
        return this.zak;
    }

    @NonNull
    public final String zad() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (getId() != null) {
                jSONObject.put("id", (Object) getId());
            }
            if (getIdToken() != null) {
                jSONObject.put("tokenId", (Object) getIdToken());
            }
            if (getEmail() != null) {
                jSONObject.put("email", (Object) getEmail());
            }
            if (getDisplayName() != null) {
                jSONObject.put("displayName", (Object) getDisplayName());
            }
            if (getGivenName() != null) {
                jSONObject.put("givenName", (Object) getGivenName());
            }
            if (getFamilyName() != null) {
                jSONObject.put("familyName", (Object) getFamilyName());
            }
            Uri photoUrl = getPhotoUrl();
            if (photoUrl != null) {
                jSONObject.put("photoUrl", (Object) photoUrl.toString());
            }
            if (getServerAuthCode() != null) {
                jSONObject.put("serverAuthCode", (Object) getServerAuthCode());
            }
            jSONObject.put("expirationTime", this.zaj);
            jSONObject.put("obfuscatedIdentifier", (Object) this.zak);
            JSONArray jSONArray = new JSONArray();
            List list = this.zac;
            Scope[] scopeArr = (Scope[]) list.toArray(new Scope[list.size()]);
            Arrays.sort(scopeArr, zaa.zaa);
            for (Scope scopeUri : scopeArr) {
                jSONArray.put((Object) scopeUri.getScopeUri());
            }
            jSONObject.put("grantedScopes", (Object) jSONArray);
            jSONObject.remove("serverAuthCode");
            return jSONObject.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
