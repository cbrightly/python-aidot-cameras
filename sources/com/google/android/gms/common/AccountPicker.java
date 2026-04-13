package com.google.android.gms.common;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class AccountPicker {

    /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
    public static class AccountChooserOptions {
        /* access modifiers changed from: private */
        @Nullable
        public Account zza;
        /* access modifiers changed from: private */
        public boolean zzb;
        /* access modifiers changed from: private */
        @Nullable
        public ArrayList zzc;
        /* access modifiers changed from: private */
        @Nullable
        public ArrayList zzd;
        /* access modifiers changed from: private */
        public boolean zze;
        /* access modifiers changed from: private */
        @Nullable
        public String zzf;
        /* access modifiers changed from: private */
        @Nullable
        public Bundle zzg;
        /* access modifiers changed from: private */
        public boolean zzh;
        /* access modifiers changed from: private */
        public int zzi;
        /* access modifiers changed from: private */
        @Nullable
        public String zzj;
        /* access modifiers changed from: private */
        public boolean zzk;
        /* access modifiers changed from: private */
        @Nullable
        public zza zzl;
        /* access modifiers changed from: private */
        @Nullable
        public String zzm;
        /* access modifiers changed from: private */
        public boolean zzn;
        /* access modifiers changed from: private */
        public boolean zzo;

        /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
        public static class Builder {
            @Nullable
            private Account zza;
            @Nullable
            private ArrayList zzb;
            @Nullable
            private ArrayList zzc;
            private boolean zzd = false;
            @Nullable
            private String zze;
            @Nullable
            private Bundle zzf;

            @NonNull
            public AccountChooserOptions build() {
                Preconditions.checkArgument(true, "We only support hostedDomain filter for account chip styled account picker");
                Preconditions.checkArgument(true, "Consent is only valid for account chip styled account picker");
                AccountChooserOptions accountChooserOptions = new AccountChooserOptions();
                accountChooserOptions.zzd = this.zzc;
                accountChooserOptions.zzc = this.zzb;
                accountChooserOptions.zze = this.zzd;
                accountChooserOptions.zzl = null;
                accountChooserOptions.zzj = null;
                accountChooserOptions.zzg = this.zzf;
                accountChooserOptions.zza = this.zza;
                accountChooserOptions.zzb = false;
                accountChooserOptions.zzh = false;
                accountChooserOptions.zzm = null;
                accountChooserOptions.zzi = 0;
                accountChooserOptions.zzf = this.zze;
                accountChooserOptions.zzk = false;
                accountChooserOptions.zzn = false;
                accountChooserOptions.zzo = false;
                return accountChooserOptions;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setAllowableAccounts(@Nullable List<Account> allowableAccounts) {
                this.zzb = allowableAccounts == null ? null : new ArrayList(allowableAccounts);
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setAllowableAccountsTypes(@Nullable List<String> allowableAccountTypes) {
                this.zzc = allowableAccountTypes == null ? null : new ArrayList(allowableAccountTypes);
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setAlwaysShowAccountPicker(boolean z) {
                this.zzd = z;
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setOptionsForAddingAccount(@Nullable Bundle bundle) {
                this.zzf = bundle;
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setSelectedAccount(@Nullable Account account) {
                this.zza = account;
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setTitleOverrideText(@Nullable String str) {
                this.zze = str;
                return this;
            }
        }
    }

    private AccountPicker() {
    }

    @ResultIgnorabilityUnspecified
    @NonNull
    @Deprecated
    public static Intent newChooseAccountIntent(@Nullable Account selectedAccount, @Nullable ArrayList<Account> allowableAccounts, @Nullable String[] allowableAccountTypes, boolean alwaysPromptForAccount, @Nullable String descriptionOverrideText, @Nullable String addAccountAuthTokenType, @Nullable String[] addAccountRequiredFeatures, @Nullable Bundle addAccountOptions) {
        Intent intent = new Intent();
        Preconditions.checkArgument(true, "We only support hostedDomain filter for account chip styled account picker");
        intent.setAction("com.google.android.gms.common.account.CHOOSE_ACCOUNT");
        intent.setPackage("com.google.android.gms");
        intent.putExtra("allowableAccounts", allowableAccounts);
        intent.putExtra("allowableAccountTypes", allowableAccountTypes);
        intent.putExtra("addAccountOptions", addAccountOptions);
        intent.putExtra("selectedAccount", selectedAccount);
        intent.putExtra("alwaysPromptForAccount", alwaysPromptForAccount);
        intent.putExtra("descriptionTextOverride", descriptionOverrideText);
        intent.putExtra("authTokenType", addAccountAuthTokenType);
        intent.putExtra("addAccountRequiredFeatures", addAccountRequiredFeatures);
        intent.putExtra("setGmsCoreAccount", false);
        intent.putExtra("overrideTheme", 0);
        intent.putExtra("overrideCustomTheme", 0);
        intent.putExtra("hostedDomainFilter", (String) null);
        return intent;
    }

    @NonNull
    public static Intent newChooseAccountIntent(@NonNull AccountChooserOptions options) {
        Intent intent = new Intent();
        boolean unused = options.zzk;
        String unused2 = options.zzj;
        Preconditions.checkArgument(true, "We only support hostedDomain filter for account chip styled account picker");
        zza unused3 = options.zzl;
        Preconditions.checkArgument(true, "Consent is only valid for account chip styled account picker");
        boolean unused4 = options.zzb;
        Preconditions.checkArgument(true, "Making the selected account non-clickable is only supported for the THEME_DAY_NIGHT_GOOGLE_MATERIAL2, THEME_LIGHT_GOOGLE_MATERIAL3, THEME_DARK_GOOGLE_MATERIAL3 or THEME_DAY_NIGHT_GOOGLE_MATERIAL3 themes");
        boolean unused5 = options.zzk;
        intent.setAction("com.google.android.gms.common.account.CHOOSE_ACCOUNT");
        intent.setPackage("com.google.android.gms");
        intent.putExtra("allowableAccounts", options.zzc);
        if (options.zzd != null) {
            intent.putExtra("allowableAccountTypes", (String[]) options.zzd.toArray(new String[0]));
        }
        intent.putExtra("addAccountOptions", options.zzg);
        intent.putExtra("selectedAccount", options.zza);
        boolean unused6 = options.zzb;
        intent.putExtra("selectedAccountIsNotClickable", false);
        intent.putExtra("alwaysPromptForAccount", options.zze);
        intent.putExtra("descriptionTextOverride", options.zzf);
        boolean unused7 = options.zzh;
        intent.putExtra("setGmsCoreAccount", false);
        String unused8 = options.zzm;
        intent.putExtra("realClientPackage", (String) null);
        int unused9 = options.zzi;
        intent.putExtra("overrideTheme", 0);
        boolean unused10 = options.zzk;
        intent.putExtra("overrideCustomTheme", 0);
        String unused11 = options.zzj;
        intent.putExtra("hostedDomainFilter", (String) null);
        Bundle bundle = new Bundle();
        boolean unused12 = options.zzk;
        zza unused13 = options.zzl;
        boolean unused14 = options.zzn;
        boolean unused15 = options.zzo;
        if (!bundle.isEmpty()) {
            intent.putExtra("first_party_options_bundle", bundle);
        }
        return intent;
    }
}
