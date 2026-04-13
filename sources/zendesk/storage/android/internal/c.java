package zendesk.storage.android.internal;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ComplexStorage.kt */
public final class c implements zendesk.storage.android.c {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final String b;
    @NotNull
    private final File c;
    /* access modifiers changed from: private */
    @NotNull
    public final zendesk.storage.android.b d;
    @NotNull
    private final d e;

    public c(@NotNull String namespace, @NotNull File directory, @NotNull zendesk.storage.android.b serializer, @NotNull d fileOperators) {
        k.e(namespace, "namespace");
        k.e(directory, "directory");
        k.e(serializer, "serializer");
        k.e(fileOperators, "fileOperators");
        this.b = namespace;
        this.c = directory;
        this.d = serializer;
        this.e = fileOperators;
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
    }

    @Nullable
    public <T> T b(@NotNull String key, @NotNull Class<T> type) {
        k.e(key, CacheEntity.KEY);
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        File file = d(key);
        if (!file.exists()) {
            zendesk.logger.a.h("ComplexStorage", "There is no stored data for the given key", new Object[0]);
            return null;
        }
        return this.d.a(this.e.a(file, b.INSTANCE), type);
    }

    /* compiled from: ComplexStorage.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<FileReader, String> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        @NotNull
        public final String invoke(@NotNull FileReader $this$reader) {
            k.e($this$reader, "$this$reader");
            return kotlin.io.k.c($this$reader);
        }
    }

    public <T> void a(@NotNull String key, @Nullable T value, @NotNull Class<T> type) {
        k.e(key, CacheEntity.KEY);
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        if (value == null) {
            d(key).delete();
        } else {
            this.e.b(d(key), new C0555c(this, value, type));
        }
    }

    /* renamed from: zendesk.storage.android.internal.c$c  reason: collision with other inner class name */
    /* compiled from: ComplexStorage.kt */
    public static final class C0555c extends l implements kotlin.jvm.functions.l<FileWriter, x> {
        final /* synthetic */ Class<T> $type;
        final /* synthetic */ T $value;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0555c(c cVar, T t, Class<T> cls) {
            super(1);
            this.this$0 = cVar;
            this.$value = t;
            this.$type = cls;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((FileWriter) p1);
            return x.a;
        }

        public final void invoke(@NotNull FileWriter $this$writer) {
            k.e($this$writer, "$this$writer");
            $this$writer.write(this.this$0.d.b(this.$value, this.$type));
        }
    }

    public void clear() {
        e(this.c);
    }

    public final void e(@NotNull File file) {
        k.e(file, "file");
        if (!file.isDirectory()) {
            file.delete();
            return;
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File p0 : listFiles) {
                e(p0);
            }
        }
        file.delete();
    }

    @NotNull
    public final File d(@NotNull String name) {
        k.e(name, "name");
        if (!this.c.isDirectory()) {
            this.c.mkdirs();
            return new File(this.c.getPath(), name);
        }
        File[] listFiles = this.c.listFiles();
        File file = null;
        if (listFiles != null) {
            int length = listFiles.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                File it = listFiles[i];
                if (k.a(it.getName(), name)) {
                    file = it;
                    break;
                }
                i++;
            }
        }
        return file == null ? new File(this.c.getPath(), name) : file;
    }

    /* compiled from: ComplexStorage.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
