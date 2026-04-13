package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable;
import com.google.android.gms.auth.api.signin.internal.HashAccumulator;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.AccountType;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SafeParcelable.Class(creator = "GoogleSignInOptionsCreator")
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public class GoogleSignInOptions extends AbstractSafeParcelable implements Api.ApiOptions.Optional, ReflectedParcelable {
    @NonNull
    public static final Parcelable.Creator<GoogleSignInOptions> CREATOR = new zae();
    @NonNull
    public static final GoogleSignInOptions DEFAULT_GAMES_SIGN_IN;
    @NonNull
    public static final GoogleSignInOptions DEFAULT_SIGN_IN;
    @NonNull
    @VisibleForTesting
    public static final Scope zaa = new Scope(Scopes.PROFILE);
    @NonNull
    @VisibleForTesting
    public static final Scope zab = new Scope("email");
    @NonNull
    @VisibleForTesting
    public static final Scope zac = new Scope(Scopes.OPEN_ID);
    @NonNull
    @VisibleForTesting
    public static final Scope zad;
    @NonNull
    @VisibleForTesting
    public static final Scope zae = new Scope(Scopes.GAMES);
    private static final Comparator zag = new zac();
    @SafeParcelable.VersionField(id = 1)
    final int zaf;
    /* access modifiers changed from: private */
    @SafeParcelable.Field(getter = "getScopes", id = 2)
    public final ArrayList zah;
    /* access modifiers changed from: private */
    @SafeParcelable.Field(getter = "getAccount", id = 3)
    @Nullable
    public Account zai;
    /* access modifiers changed from: private */
    @SafeParcelable.Field(getter = "isIdTokenRequested", id = 4)
    public boolean zaj;
    /* access modifiers changed from: private */
    @SafeParcelable.Field(getter = "isServerAuthCodeRequested", id = 5)
    public final boolean zak;
    /* access modifiers changed from: private */
    @SafeParcelable.Field(getter = "isForceCodeForRefreshToken", id = 6)
    public final boolean zal;
    /* access modifiers changed from: private */
    @SafeParcelable.Field(getter = "getServerClientId", id = 7)
    @Nullable
    public String zam;
    /* access modifiers changed from: private */
    @SafeParcelable.Field(getter = "getHostedDomain", id = 8)
    @Nullable
    public String zan;
    /* access modifiers changed from: private */
    @SafeParcelable.Field(getter = "getExtensions", id = 9)
    public ArrayList zao;
    /* access modifiers changed from: private */
    @SafeParcelable.Field(getter = "getLogSessionId", id = 10)
    @Nullable
    public String zap;
    private Map zaq;

    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public static final class Builder {
        private Set zaa = new HashSet();
        private boolean zab;
        private boolean zac;
        private boolean zad;
        @Nullable
        private String zae;
        @Nullable
        private Account zaf;
        @Nullable
        private String zag;
        private Map zah = new HashMap();
        @Nullable
        private String zai;

        public Builder() {
        }

        private final String zaa(String str) {
            Preconditions.checkNotEmpty(str);
            String str2 = this.zae;
            boolean z = true;
            if (str2 != null && !str2.equals(str)) {
                z = false;
            }
            Preconditions.checkArgument(z, "two different server client ids provided");
            return str;
        }

        @NonNull
        @CanIgnoreReturnValue
        public Builder addExtension(@NonNull GoogleSignInOptionsExtension extension) {
            if (!this.zah.containsKey(Integer.valueOf(extension.getExtensionType()))) {
                List<Scope> impliedScopes = extension.getImpliedScopes();
                if (impliedScopes != null) {
                    this.zaa.addAll(impliedScopes);
                }
                this.zah.put(Integer.valueOf(extension.getExtensionType()), new GoogleSignInOptionsExtensionParcelable(extension));
                return this;
            }
            throw new IllegalStateException("Only one extension per type may be added");
        }

        @NonNull
        public GoogleSignInOptions build() {
            if (this.zaa.contains(GoogleSignInOptions.zae)) {
                Set set = this.zaa;
                Scope scope = GoogleSignInOptions.zad;
                if (set.contains(scope)) {
                    this.zaa.remove(scope);
                }
            }
            if (this.zad && (this.zaf == null || !this.zaa.isEmpty())) {
                requestId();
            }
            return new GoogleSignInOptions(3, new ArrayList(this.zaa), this.zaf, this.zad, this.zab, this.zac, this.zae, this.zag, this.zah, this.zai, (zad) null);
        }

        @NonNull
        @CanIgnoreReturnValue
        public Builder requestEmail() {
            this.zaa.add(GoogleSignInOptions.zab);
            return this;
        }

        @NonNull
        @CanIgnoreReturnValue
        public Builder requestId() {
            this.zaa.add(GoogleSignInOptions.zac);
            return this;
        }

        @NonNull
        @CanIgnoreReturnValue
        public Builder requestIdToken(@NonNull String serverClientId) {
            this.zad = true;
            zaa(serverClientId);
            this.zae = serverClientId;
            return this;
        }

        @NonNull
        @CanIgnoreReturnValue
        public Builder requestProfile() {
            this.zaa.add(GoogleSignInOptions.zaa);
            return this;
        }

        @NonNull
        @CanIgnoreReturnValue
        public Builder requestScopes(@NonNull Scope scope, @NonNull Scope... scopes) {
            this.zaa.add(scope);
            this.zaa.addAll(Arrays.asList(scopes));
            return this;
        }

        @NonNull
        @CanIgnoreReturnValue
        public Builder requestServerAuthCode(@NonNull String serverClientId) {
            requestServerAuthCode(serverClientId, false);
            return this;
        }

        @NonNull
        @CanIgnoreReturnValue
        public Builder setAccountName(@NonNull String accountName) {
            this.zaf = new Account(Preconditions.checkNotEmpty(accountName), AccountType.GOOGLE);
            return this;
        }

        @NonNull
        @CanIgnoreReturnValue
        public Builder setHostedDomain(@NonNull String hostedDomain) {
            this.zag = Preconditions.checkNotEmpty(hostedDomain);
            return this;
        }

        @NonNull
        @KeepForSdk
        @CanIgnoreReturnValue
        public Builder setLogSessionId(@NonNull String str) {
            this.zai = str;
            return this;
        }

        @NonNull
        @CanIgnoreReturnValue
        public Builder requestServerAuthCode(@NonNull String serverClientId, boolean forceCodeForRefreshToken) {
            this.zab = true;
            zaa(serverClientId);
            this.zae = serverClientId;
            this.zac = forceCodeForRefreshToken;
            return this;
        }

        public Builder(@NonNull GoogleSignInOptions googleSignInOptions) {
            Preconditions.checkNotNull(googleSignInOptions);
            this.zaa = new HashSet(googleSignInOptions.zah);
            this.zab = googleSignInOptions.zak;
            this.zac = googleSignInOptions.zal;
            this.zad = googleSignInOptions.zaj;
            this.zae = googleSignInOptions.zam;
            this.zaf = googleSignInOptions.zai;
            this.zag = googleSignInOptions.zan;
            this.zah = GoogleSignInOptions.zam(googleSignInOptions.zao);
            this.zai = googleSignInOptions.zap;
        }
    }

    static {
        Scope scope = new Scope(Scopes.GAMES_LITE);
        zad = scope;
        Builder builder = new Builder();
        builder.requestId();
        builder.requestProfile();
        DEFAULT_SIGN_IN = builder.build();
        Builder builder2 = new Builder();
        builder2.requestScopes(scope, new Scope[0]);
        DEFAULT_GAMES_SIGN_IN = builder2.build();
    }

    @SafeParcelable.Constructor
    GoogleSignInOptions(@SafeParcelable.Param(id = 1) int i, @SafeParcelable.Param(id = 2) ArrayList arrayList, @SafeParcelable.Param(id = 3) @Nullable Account account, @SafeParcelable.Param(id = 4) boolean z, @SafeParcelable.Param(id = 5) boolean z2, @SafeParcelable.Param(id = 6) boolean z3, @SafeParcelable.Param(id = 7) @Nullable String str, @SafeParcelable.Param(id = 8) @Nullable String str2, @SafeParcelable.Param(id = 9) ArrayList arrayList2, @SafeParcelable.Param(id = 10) @Nullable String str3) {
        this(i, arrayList, account, z, z2, z3, str, str2, zam(arrayList2), str3);
    }

    /* synthetic */ GoogleSignInOptions(int i, ArrayList arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, Map map, String str3, zad zad2) {
        this(3, arrayList, account, z, z2, z3, str, str2, map, str3);
    }

    @Nullable
    public static GoogleSignInOptions zab(@Nullable String str) {
        String str2;
        Account account;
        String str3;
        String str4 = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        HashSet hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("scopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        if (jSONObject.has("accountName")) {
            str2 = jSONObject.optString("accountName");
        } else {
            str2 = null;
        }
        if (!TextUtils.isEmpty(str2)) {
            account = new Account(str2, AccountType.GOOGLE);
        } else {
            account = null;
        }
        ArrayList arrayList = new ArrayList(hashSet);
        boolean z = jSONObject.getBoolean("idTokenRequested");
        boolean z2 = jSONObject.getBoolean("serverAuthRequested");
        boolean z3 = jSONObject.getBoolean("forceCodeForRefreshToken");
        if (jSONObject.has("serverClientId")) {
            str3 = jSONObject.optString("serverClientId");
        } else {
            str3 = null;
        }
        if (jSONObject.has("hostedDomain")) {
            str4 = jSONObject.optString("hostedDomain");
        }
        return new GoogleSignInOptions(3, arrayList, account, z, z2, z3, str3, str4, (Map) new HashMap(), (String) null);
    }

    /* access modifiers changed from: private */
    public static Map zam(@Nullable List list) {
        HashMap hashMap = new HashMap();
        if (list == null) {
            return hashMap;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            GoogleSignInOptionsExtensionParcelable googleSignInOptionsExtensionParcelable = (GoogleSignInOptionsExtensionParcelable) it.next();
            hashMap.put(Integer.valueOf(googleSignInOptionsExtensionParcelable.getType()), googleSignInOptionsExtensionParcelable);
        }
        return hashMap;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003e, code lost:
        if (r4.getAccount() == null) goto L_0x0040;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(@androidx.annotation.Nullable java.lang.Object r4) {
        /*
            r3 = this;
            r0 = 0
            if (r4 != 0) goto L_0x0005
            return r0
        L_0x0005:
            com.google.android.gms.auth.api.signin.GoogleSignInOptions r4 = (com.google.android.gms.auth.api.signin.GoogleSignInOptions) r4     // Catch:{ ClassCastException -> 0x0095 }
            java.util.ArrayList r1 = r3.zao     // Catch:{ ClassCastException -> 0x0095 }
            int r1 = r1.size()     // Catch:{ ClassCastException -> 0x0095 }
            if (r1 > 0) goto L_0x0094
            java.util.ArrayList r1 = r4.zao     // Catch:{ ClassCastException -> 0x0095 }
            int r1 = r1.size()     // Catch:{ ClassCastException -> 0x0095 }
            if (r1 <= 0) goto L_0x0019
            goto L_0x0094
        L_0x0019:
            java.util.ArrayList r1 = r3.zah     // Catch:{ ClassCastException -> 0x0095 }
            int r1 = r1.size()     // Catch:{ ClassCastException -> 0x0095 }
            java.util.ArrayList r2 = r4.getScopes()     // Catch:{ ClassCastException -> 0x0095 }
            int r2 = r2.size()     // Catch:{ ClassCastException -> 0x0095 }
            if (r1 != r2) goto L_0x0093
            java.util.ArrayList r1 = r3.zah     // Catch:{ ClassCastException -> 0x0095 }
            java.util.ArrayList r2 = r4.getScopes()     // Catch:{ ClassCastException -> 0x0095 }
            boolean r1 = r1.containsAll(r2)     // Catch:{ ClassCastException -> 0x0095 }
            if (r1 != 0) goto L_0x0036
            goto L_0x0093
        L_0x0036:
            android.accounts.Account r1 = r3.zai     // Catch:{ ClassCastException -> 0x0095 }
            if (r1 != 0) goto L_0x0041
            android.accounts.Account r1 = r4.getAccount()     // Catch:{ ClassCastException -> 0x0095 }
            if (r1 != 0) goto L_0x0092
        L_0x0040:
            goto L_0x004c
        L_0x0041:
            android.accounts.Account r2 = r4.getAccount()     // Catch:{ ClassCastException -> 0x0095 }
            boolean r1 = r1.equals(r2)     // Catch:{ ClassCastException -> 0x0095 }
            if (r1 == 0) goto L_0x0092
            goto L_0x0040
        L_0x004c:
            java.lang.String r1 = r3.zam     // Catch:{ ClassCastException -> 0x0095 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ ClassCastException -> 0x0095 }
            if (r1 == 0) goto L_0x005f
            java.lang.String r1 = r4.getServerClientId()     // Catch:{ ClassCastException -> 0x0095 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ ClassCastException -> 0x0095 }
            if (r1 == 0) goto L_0x0092
            goto L_0x006c
        L_0x005f:
            java.lang.String r1 = r3.zam     // Catch:{ ClassCastException -> 0x0095 }
            java.lang.String r2 = r4.getServerClientId()     // Catch:{ ClassCastException -> 0x0095 }
            boolean r1 = r1.equals(r2)     // Catch:{ ClassCastException -> 0x0095 }
            if (r1 != 0) goto L_0x006c
            goto L_0x0092
        L_0x006c:
            boolean r1 = r3.zal     // Catch:{ ClassCastException -> 0x0095 }
            boolean r2 = r4.isForceCodeForRefreshToken()     // Catch:{ ClassCastException -> 0x0095 }
            if (r1 != r2) goto L_0x0092
            boolean r1 = r3.zaj     // Catch:{ ClassCastException -> 0x0095 }
            boolean r2 = r4.isIdTokenRequested()     // Catch:{ ClassCastException -> 0x0095 }
            if (r1 != r2) goto L_0x0092
            boolean r1 = r3.zak     // Catch:{ ClassCastException -> 0x0095 }
            boolean r2 = r4.isServerAuthCodeRequested()     // Catch:{ ClassCastException -> 0x0095 }
            if (r1 != r2) goto L_0x0092
            java.lang.String r1 = r3.zap     // Catch:{ ClassCastException -> 0x0095 }
            java.lang.String r4 = r4.getLogSessionId()     // Catch:{ ClassCastException -> 0x0095 }
            boolean r4 = android.text.TextUtils.equals(r1, r4)     // Catch:{ ClassCastException -> 0x0095 }
            if (r4 == 0) goto L_0x0092
            r4 = 1
            return r4
        L_0x0092:
            return r0
        L_0x0093:
            return r0
        L_0x0094:
            return r0
        L_0x0095:
            r4 = move-exception
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.GoogleSignInOptions.equals(java.lang.Object):boolean");
    }

    @KeepForSdk
    @Nullable
    public Account getAccount() {
        return this.zai;
    }

    @NonNull
    @KeepForSdk
    public ArrayList<GoogleSignInOptionsExtensionParcelable> getExtensions() {
        return this.zao;
    }

    @KeepForSdk
    @Nullable
    public String getLogSessionId() {
        return this.zap;
    }

    @NonNull
    public Scope[] getScopeArray() {
        ArrayList arrayList = this.zah;
        return (Scope[]) arrayList.toArray(new Scope[arrayList.size()]);
    }

    @NonNull
    @KeepForSdk
    public ArrayList<Scope> getScopes() {
        return new ArrayList<>(this.zah);
    }

    @KeepForSdk
    @Nullable
    public String getServerClientId() {
        return this.zam;
    }

    public int hashCode() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = this.zah;
        int size = arrayList2.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(((Scope) arrayList2.get(i)).getScopeUri());
        }
        Collections.sort(arrayList);
        HashAccumulator hashAccumulator = new HashAccumulator();
        hashAccumulator.addObject(arrayList);
        hashAccumulator.addObject(this.zai);
        hashAccumulator.addObject(this.zam);
        hashAccumulator.zaa(this.zal);
        hashAccumulator.zaa(this.zaj);
        hashAccumulator.zaa(this.zak);
        hashAccumulator.addObject(this.zap);
        return hashAccumulator.hash();
    }

    @KeepForSdk
    public boolean isForceCodeForRefreshToken() {
        return this.zal;
    }

    @KeepForSdk
    public boolean isIdTokenRequested() {
        return this.zaj;
    }

    @KeepForSdk
    public boolean isServerAuthCodeRequested() {
        return this.zak;
    }

    public void writeToParcel(@NonNull Parcel out, int flags) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeInt(out, 1, this.zaf);
        SafeParcelWriter.writeTypedList(out, 2, getScopes(), false);
        SafeParcelWriter.writeParcelable(out, 3, getAccount(), flags, false);
        SafeParcelWriter.writeBoolean(out, 4, isIdTokenRequested());
        SafeParcelWriter.writeBoolean(out, 5, isServerAuthCodeRequested());
        SafeParcelWriter.writeBoolean(out, 6, isForceCodeForRefreshToken());
        SafeParcelWriter.writeString(out, 7, getServerClientId(), false);
        SafeParcelWriter.writeString(out, 8, this.zan, false);
        SafeParcelWriter.writeTypedList(out, 9, getExtensions(), false);
        SafeParcelWriter.writeString(out, 10, getLogSessionId(), false);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }

    @NonNull
    public final String zaf() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.zah, zag);
            Iterator it = this.zah.iterator();
            while (it.hasNext()) {
                jSONArray.put((Object) ((Scope) it.next()).getScopeUri());
            }
            jSONObject.put("scopes", (Object) jSONArray);
            Account account = this.zai;
            if (account != null) {
                jSONObject.put("accountName", (Object) account.name);
            }
            jSONObject.put("idTokenRequested", this.zaj);
            jSONObject.put("forceCodeForRefreshToken", this.zal);
            jSONObject.put("serverAuthRequested", this.zak);
            if (!TextUtils.isEmpty(this.zam)) {
                jSONObject.put("serverClientId", (Object) this.zam);
            }
            if (!TextUtils.isEmpty(this.zan)) {
                jSONObject.put("hostedDomain", (Object) this.zan);
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private GoogleSignInOptions(int i, ArrayList arrayList, @Nullable Account account, boolean z, boolean z2, boolean z3, @Nullable String str, @Nullable String str2, Map map, @Nullable String str3) {
        this.zaf = i;
        this.zah = arrayList;
        this.zai = account;
        this.zaj = z;
        this.zak = z2;
        this.zal = z3;
        this.zam = str;
        this.zan = str2;
        this.zao = new ArrayList(map.values());
        this.zaq = map;
        this.zap = str3;
    }
}
