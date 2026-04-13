package androidx.activity.result.contract;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;
import com.yanzhenjie.andserver.util.h;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public final class ActivityResultContracts {
    private ActivityResultContracts() {
    }

    public static final class StartActivityForResult extends ActivityResultContract<Intent, ActivityResult> {
        public static final String EXTRA_ACTIVITY_OPTIONS_BUNDLE = "androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE";

        @NonNull
        public Intent createIntent(@NonNull Context context, @NonNull Intent input) {
            return input;
        }

        @NonNull
        public ActivityResult parseResult(int resultCode, @Nullable Intent intent) {
            return new ActivityResult(resultCode, intent);
        }
    }

    public static final class StartIntentSenderForResult extends ActivityResultContract<IntentSenderRequest, ActivityResult> {
        public static final String ACTION_INTENT_SENDER_REQUEST = "androidx.activity.result.contract.action.INTENT_SENDER_REQUEST";
        public static final String EXTRA_INTENT_SENDER_REQUEST = "androidx.activity.result.contract.extra.INTENT_SENDER_REQUEST";
        public static final String EXTRA_SEND_INTENT_EXCEPTION = "androidx.activity.result.contract.extra.SEND_INTENT_EXCEPTION";

        @NonNull
        public Intent createIntent(@NonNull Context context, @NonNull IntentSenderRequest input) {
            return new Intent(ACTION_INTENT_SENDER_REQUEST).putExtra(EXTRA_INTENT_SENDER_REQUEST, input);
        }

        @NonNull
        public ActivityResult parseResult(int resultCode, @Nullable Intent intent) {
            return new ActivityResult(resultCode, intent);
        }
    }

    public static final class RequestMultiplePermissions extends ActivityResultContract<String[], Map<String, Boolean>> {
        public static final String ACTION_REQUEST_PERMISSIONS = "androidx.activity.result.contract.action.REQUEST_PERMISSIONS";
        public static final String EXTRA_PERMISSIONS = "androidx.activity.result.contract.extra.PERMISSIONS";
        public static final String EXTRA_PERMISSION_GRANT_RESULTS = "androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS";

        @NonNull
        public Intent createIntent(@NonNull Context context, @NonNull String[] input) {
            return createIntent(input);
        }

        @Nullable
        public ActivityResultContract.SynchronousResult<Map<String, Boolean>> getSynchronousResult(@NonNull Context context, @Nullable String[] input) {
            if (input == null || input.length == 0) {
                return new ActivityResultContract.SynchronousResult<>(Collections.emptyMap());
            }
            Map<String, Boolean> grantState = new ArrayMap<>();
            boolean allGranted = true;
            for (String permission : input) {
                boolean granted = ContextCompat.checkSelfPermission(context, permission) == 0;
                grantState.put(permission, Boolean.valueOf(granted));
                if (!granted) {
                    allGranted = false;
                }
            }
            if (allGranted) {
                return new ActivityResultContract.SynchronousResult<>(grantState);
            }
            return null;
        }

        @NonNull
        public Map<String, Boolean> parseResult(int resultCode, @Nullable Intent intent) {
            if (resultCode != -1) {
                return Collections.emptyMap();
            }
            if (intent == null) {
                return Collections.emptyMap();
            }
            String[] permissions = intent.getStringArrayExtra(EXTRA_PERMISSIONS);
            int[] grantResults = intent.getIntArrayExtra(EXTRA_PERMISSION_GRANT_RESULTS);
            if (grantResults == null || permissions == null) {
                return Collections.emptyMap();
            }
            Map<String, Boolean> result = new HashMap<>();
            int size = permissions.length;
            for (int i = 0; i < size; i++) {
                result.put(permissions[i], Boolean.valueOf(grantResults[i] == 0));
            }
            return result;
        }

        @NonNull
        static Intent createIntent(@NonNull String[] input) {
            return new Intent(ACTION_REQUEST_PERMISSIONS).putExtra(EXTRA_PERMISSIONS, input);
        }
    }

    public static final class RequestPermission extends ActivityResultContract<String, Boolean> {
        @NonNull
        public Intent createIntent(@NonNull Context context, @NonNull String input) {
            return RequestMultiplePermissions.createIntent(new String[]{input});
        }

        @NonNull
        public Boolean parseResult(int resultCode, @Nullable Intent intent) {
            int[] grantResults;
            boolean z = false;
            if (intent == null || resultCode != -1 || (grantResults = intent.getIntArrayExtra(RequestMultiplePermissions.EXTRA_PERMISSION_GRANT_RESULTS)) == null || grantResults.length == 0) {
                return false;
            }
            if (grantResults[0] == 0) {
                z = true;
            }
            return Boolean.valueOf(z);
        }

        @Nullable
        public ActivityResultContract.SynchronousResult<Boolean> getSynchronousResult(@NonNull Context context, @Nullable String input) {
            if (input == null) {
                return new ActivityResultContract.SynchronousResult<>(false);
            }
            if (ContextCompat.checkSelfPermission(context, input) == 0) {
                return new ActivityResultContract.SynchronousResult<>(true);
            }
            return null;
        }
    }

    public static class TakePicturePreview extends ActivityResultContract<Void, Bitmap> {
        @CallSuper
        @NonNull
        public Intent createIntent(@NonNull Context context, @Nullable Void input) {
            return new Intent("android.media.action.IMAGE_CAPTURE");
        }

        @Nullable
        public final ActivityResultContract.SynchronousResult<Bitmap> getSynchronousResult(@NonNull Context context, @Nullable Void input) {
            return null;
        }

        @Nullable
        public final Bitmap parseResult(int resultCode, @Nullable Intent intent) {
            if (intent == null || resultCode != -1) {
                return null;
            }
            return (Bitmap) intent.getParcelableExtra("data");
        }
    }

    public static class TakePicture extends ActivityResultContract<Uri, Boolean> {
        @CallSuper
        @NonNull
        public Intent createIntent(@NonNull Context context, @NonNull Uri input) {
            return new Intent("android.media.action.IMAGE_CAPTURE").putExtra("output", input);
        }

        @Nullable
        public final ActivityResultContract.SynchronousResult<Boolean> getSynchronousResult(@NonNull Context context, @NonNull Uri input) {
            return null;
        }

        @NonNull
        public final Boolean parseResult(int resultCode, @Nullable Intent intent) {
            return Boolean.valueOf(resultCode == -1);
        }
    }

    @Deprecated
    public static class TakeVideo extends ActivityResultContract<Uri, Bitmap> {
        @CallSuper
        @NonNull
        public Intent createIntent(@NonNull Context context, @NonNull Uri input) {
            return new Intent("android.media.action.VIDEO_CAPTURE").putExtra("output", input);
        }

        @Nullable
        public final ActivityResultContract.SynchronousResult<Bitmap> getSynchronousResult(@NonNull Context context, @NonNull Uri input) {
            return null;
        }

        @Nullable
        public final Bitmap parseResult(int resultCode, @Nullable Intent intent) {
            if (intent == null || resultCode != -1) {
                return null;
            }
            return (Bitmap) intent.getParcelableExtra("data");
        }
    }

    public static class CaptureVideo extends ActivityResultContract<Uri, Boolean> {
        @CallSuper
        @NonNull
        public Intent createIntent(@NonNull Context context, @NonNull Uri input) {
            return new Intent("android.media.action.VIDEO_CAPTURE").putExtra("output", input);
        }

        @Nullable
        public final ActivityResultContract.SynchronousResult<Boolean> getSynchronousResult(@NonNull Context context, @NonNull Uri input) {
            return null;
        }

        @NonNull
        public final Boolean parseResult(int resultCode, @Nullable Intent intent) {
            return Boolean.valueOf(resultCode == -1);
        }
    }

    public static final class PickContact extends ActivityResultContract<Void, Uri> {
        @NonNull
        public Intent createIntent(@NonNull Context context, @Nullable Void input) {
            return new Intent("android.intent.action.PICK").setType("vnd.android.cursor.dir/contact");
        }

        @Nullable
        public Uri parseResult(int resultCode, @Nullable Intent intent) {
            if (intent == null || resultCode != -1) {
                return null;
            }
            return intent.getData();
        }
    }

    public static class GetContent extends ActivityResultContract<String, Uri> {
        @CallSuper
        @NonNull
        public Intent createIntent(@NonNull Context context, @NonNull String input) {
            return new Intent("android.intent.action.GET_CONTENT").addCategory("android.intent.category.OPENABLE").setType(input);
        }

        @Nullable
        public final ActivityResultContract.SynchronousResult<Uri> getSynchronousResult(@NonNull Context context, @NonNull String input) {
            return null;
        }

        @Nullable
        public final Uri parseResult(int resultCode, @Nullable Intent intent) {
            if (intent == null || resultCode != -1) {
                return null;
            }
            return intent.getData();
        }
    }

    @RequiresApi(18)
    public static class GetMultipleContents extends ActivityResultContract<String, List<Uri>> {
        @CallSuper
        @NonNull
        public Intent createIntent(@NonNull Context context, @NonNull String input) {
            return new Intent("android.intent.action.GET_CONTENT").addCategory("android.intent.category.OPENABLE").setType(input).putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
        }

        @Nullable
        public final ActivityResultContract.SynchronousResult<List<Uri>> getSynchronousResult(@NonNull Context context, @NonNull String input) {
            return null;
        }

        @NonNull
        public final List<Uri> parseResult(int resultCode, @Nullable Intent intent) {
            if (intent == null || resultCode != -1) {
                return Collections.emptyList();
            }
            return getClipDataUris(intent);
        }

        @NonNull
        static List<Uri> getClipDataUris(@NonNull Intent intent) {
            LinkedHashSet<Uri> resultSet = new LinkedHashSet<>();
            if (intent.getData() != null) {
                resultSet.add(intent.getData());
            }
            ClipData clipData = intent.getClipData();
            if (clipData == null && resultSet.isEmpty()) {
                return Collections.emptyList();
            }
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri uri = clipData.getItemAt(i).getUri();
                    if (uri != null) {
                        resultSet.add(uri);
                    }
                }
            }
            return new ArrayList(resultSet);
        }
    }

    @RequiresApi(19)
    public static class OpenDocument extends ActivityResultContract<String[], Uri> {
        @CallSuper
        @NonNull
        public Intent createIntent(@NonNull Context context, @NonNull String[] input) {
            return new Intent("android.intent.action.OPEN_DOCUMENT").putExtra("android.intent.extra.MIME_TYPES", input).setType(h.ALL_VALUE);
        }

        @Nullable
        public final ActivityResultContract.SynchronousResult<Uri> getSynchronousResult(@NonNull Context context, @NonNull String[] input) {
            return null;
        }

        @Nullable
        public final Uri parseResult(int resultCode, @Nullable Intent intent) {
            if (intent == null || resultCode != -1) {
                return null;
            }
            return intent.getData();
        }
    }

    @RequiresApi(19)
    public static class OpenMultipleDocuments extends ActivityResultContract<String[], List<Uri>> {
        @CallSuper
        @NonNull
        public Intent createIntent(@NonNull Context context, @NonNull String[] input) {
            return new Intent("android.intent.action.OPEN_DOCUMENT").putExtra("android.intent.extra.MIME_TYPES", input).putExtra("android.intent.extra.ALLOW_MULTIPLE", true).setType(h.ALL_VALUE);
        }

        @Nullable
        public final ActivityResultContract.SynchronousResult<List<Uri>> getSynchronousResult(@NonNull Context context, @NonNull String[] input) {
            return null;
        }

        @NonNull
        public final List<Uri> parseResult(int resultCode, @Nullable Intent intent) {
            if (resultCode != -1 || intent == null) {
                return Collections.emptyList();
            }
            return GetMultipleContents.getClipDataUris(intent);
        }
    }

    @RequiresApi(21)
    public static class OpenDocumentTree extends ActivityResultContract<Uri, Uri> {
        @CallSuper
        @NonNull
        public Intent createIntent(@NonNull Context context, @Nullable Uri input) {
            Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
            if (Build.VERSION.SDK_INT >= 26 && input != null) {
                intent.putExtra("android.provider.extra.INITIAL_URI", input);
            }
            return intent;
        }

        @Nullable
        public final ActivityResultContract.SynchronousResult<Uri> getSynchronousResult(@NonNull Context context, @Nullable Uri input) {
            return null;
        }

        @Nullable
        public final Uri parseResult(int resultCode, @Nullable Intent intent) {
            if (intent == null || resultCode != -1) {
                return null;
            }
            return intent.getData();
        }
    }

    @RequiresApi(19)
    public static class CreateDocument extends ActivityResultContract<String, Uri> {
        @CallSuper
        @NonNull
        public Intent createIntent(@NonNull Context context, @NonNull String input) {
            return new Intent("android.intent.action.CREATE_DOCUMENT").setType(h.ALL_VALUE).putExtra("android.intent.extra.TITLE", input);
        }

        @Nullable
        public final ActivityResultContract.SynchronousResult<Uri> getSynchronousResult(@NonNull Context context, @NonNull String input) {
            return null;
        }

        @Nullable
        public final Uri parseResult(int resultCode, @Nullable Intent intent) {
            if (intent == null || resultCode != -1) {
                return null;
            }
            return intent.getData();
        }
    }
}
