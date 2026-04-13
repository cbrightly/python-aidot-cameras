package com.didichuxing.doraemonkit.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import com.blankj.utilcode.util.f0;

public final class ClipboardUtils {
    private ClipboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void copyText(CharSequence text) {
        ((ClipboardManager) f0.a().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("text", text));
    }

    public static CharSequence getText() {
        ClipData clip = ((ClipboardManager) f0.a().getSystemService("clipboard")).getPrimaryClip();
        if (clip == null || clip.getItemCount() <= 0) {
            return null;
        }
        return clip.getItemAt(0).coerceToText(f0.a());
    }

    public static void copyUri(Uri uri) {
        ((ClipboardManager) f0.a().getSystemService("clipboard")).setPrimaryClip(ClipData.newUri(f0.a().getContentResolver(), "uri", uri));
    }

    public static Uri getUri() {
        ClipData clip = ((ClipboardManager) f0.a().getSystemService("clipboard")).getPrimaryClip();
        if (clip == null || clip.getItemCount() <= 0) {
            return null;
        }
        return clip.getItemAt(0).getUri();
    }

    public static void copyIntent(Intent intent) {
        ((ClipboardManager) f0.a().getSystemService("clipboard")).setPrimaryClip(ClipData.newIntent("intent", intent));
    }

    public static Intent getIntent() {
        ClipData clip = ((ClipboardManager) f0.a().getSystemService("clipboard")).getPrimaryClip();
        if (clip == null || clip.getItemCount() <= 0) {
            return null;
        }
        return clip.getItemAt(0).getIntent();
    }
}
