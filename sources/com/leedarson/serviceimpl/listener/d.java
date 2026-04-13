package com.leedarson.serviceimpl.listener;

import com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ProvisionCallback.kt */
public interface d {
    void a(long j, @Nullable String str);

    void b();

    void c(int i, @Nullable String str, @Nullable Exception exc);

    void d(long j);

    void e(int i, @NotNull String str, @NotNull String str2);

    void f(@NotNull CHIPDeviceInfo cHIPDeviceInfo);

    void g(@NotNull String str);
}
