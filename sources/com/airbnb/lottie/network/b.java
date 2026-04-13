package com.airbnb.lottie.network;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import java.net.HttpURLConnection;
import java.net.URL;
import org.glassfish.grizzly.http.server.Constants;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: DefaultLottieNetworkFetcher */
public class b implements f {
    @NonNull
    public d a(@NonNull String url) {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(Constants.GET);
        connection.connect();
        return new a(connection);
    }
}
