package androidx.room;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.io.File;

public class SQLiteCopyOpenHelperFactory implements SupportSQLiteOpenHelper.Factory {
    @Nullable
    private final String mCopyFromAssetPath;
    @Nullable
    private final File mCopyFromFile;
    @NonNull
    private final SupportSQLiteOpenHelper.Factory mDelegate;

    SQLiteCopyOpenHelperFactory(@Nullable String copyFromAssetPath, @Nullable File copyFromFile, @NonNull SupportSQLiteOpenHelper.Factory factory) {
        this.mCopyFromAssetPath = copyFromAssetPath;
        this.mCopyFromFile = copyFromFile;
        this.mDelegate = factory;
    }

    public SupportSQLiteOpenHelper create(SupportSQLiteOpenHelper.Configuration configuration) {
        return new SQLiteCopyOpenHelper(configuration.context, this.mCopyFromAssetPath, this.mCopyFromFile, configuration.callback.version, this.mDelegate.create(configuration));
    }
}
