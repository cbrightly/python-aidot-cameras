package com.bumptech.glide.load.data;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.ContactsContract;
import androidx.annotation.NonNull;
import java.io.FileNotFoundException;
import java.io.InputStream;

/* compiled from: StreamLocalUriFetcher */
public class n extends l<InputStream> {
    private static final UriMatcher q;

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        q = uriMatcher;
        uriMatcher.addURI("com.android.contacts", "contacts/lookup/*/#", 1);
        uriMatcher.addURI("com.android.contacts", "contacts/lookup/*", 1);
        uriMatcher.addURI("com.android.contacts", "contacts/#/photo", 2);
        uriMatcher.addURI("com.android.contacts", "contacts/#", 3);
        uriMatcher.addURI("com.android.contacts", "contacts/#/display_photo", 4);
        uriMatcher.addURI("com.android.contacts", "phone_lookup/*", 5);
    }

    public n(ContentResolver resolver, Uri uri) {
        super(resolver, uri);
    }

    /* access modifiers changed from: protected */
    /* renamed from: g */
    public InputStream e(Uri uri, ContentResolver contentResolver) {
        InputStream inputStream = h(uri, contentResolver);
        if (inputStream != null) {
            return inputStream;
        }
        throw new FileNotFoundException("InputStream is null for " + uri);
    }

    private InputStream h(Uri uri, ContentResolver contentResolver) {
        switch (q.match(uri)) {
            case 1:
            case 5:
                Uri uri2 = ContactsContract.Contacts.lookupContact(contentResolver, uri);
                if (uri2 != null) {
                    return i(contentResolver, uri2);
                }
                throw new FileNotFoundException("Contact cannot be found");
            case 3:
                return i(contentResolver, uri);
            default:
                return contentResolver.openInputStream(uri);
        }
    }

    private InputStream i(ContentResolver contentResolver, Uri contactUri) {
        return ContactsContract.Contacts.openContactPhotoInputStream(contentResolver, contactUri, true);
    }

    /* access modifiers changed from: protected */
    /* renamed from: f */
    public void c(InputStream data) {
        data.close();
    }

    @NonNull
    public Class<InputStream> a() {
        return InputStream.class;
    }
}
