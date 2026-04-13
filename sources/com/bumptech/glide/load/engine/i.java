package com.bumptech.glide.load.engine;

/* compiled from: DiskCacheStrategy */
public abstract class i {
    public static final i a = new a();
    public static final i b = new b();
    public static final i c = new c();
    public static final i d = new d();
    public static final i e = new e();

    public abstract boolean a();

    public abstract boolean b();

    public abstract boolean c(com.bumptech.glide.load.a aVar);

    public abstract boolean d(boolean z, com.bumptech.glide.load.a aVar, com.bumptech.glide.load.c cVar);

    /* compiled from: DiskCacheStrategy */
    public class a extends i {
        a() {
        }

        public boolean c(com.bumptech.glide.load.a dataSource) {
            return dataSource == com.bumptech.glide.load.a.REMOTE;
        }

        public boolean d(boolean isFromAlternateCacheKey, com.bumptech.glide.load.a dataSource, com.bumptech.glide.load.c encodeStrategy) {
            return (dataSource == com.bumptech.glide.load.a.RESOURCE_DISK_CACHE || dataSource == com.bumptech.glide.load.a.MEMORY_CACHE) ? false : true;
        }

        public boolean b() {
            return true;
        }

        public boolean a() {
            return true;
        }
    }

    /* compiled from: DiskCacheStrategy */
    public class b extends i {
        b() {
        }

        public boolean c(com.bumptech.glide.load.a dataSource) {
            return false;
        }

        public boolean d(boolean isFromAlternateCacheKey, com.bumptech.glide.load.a dataSource, com.bumptech.glide.load.c encodeStrategy) {
            return false;
        }

        public boolean b() {
            return false;
        }

        public boolean a() {
            return false;
        }
    }

    /* compiled from: DiskCacheStrategy */
    public class c extends i {
        c() {
        }

        public boolean c(com.bumptech.glide.load.a dataSource) {
            return (dataSource == com.bumptech.glide.load.a.DATA_DISK_CACHE || dataSource == com.bumptech.glide.load.a.MEMORY_CACHE) ? false : true;
        }

        public boolean d(boolean isFromAlternateCacheKey, com.bumptech.glide.load.a dataSource, com.bumptech.glide.load.c encodeStrategy) {
            return false;
        }

        public boolean b() {
            return false;
        }

        public boolean a() {
            return true;
        }
    }

    /* compiled from: DiskCacheStrategy */
    public class d extends i {
        d() {
        }

        public boolean c(com.bumptech.glide.load.a dataSource) {
            return false;
        }

        public boolean d(boolean isFromAlternateCacheKey, com.bumptech.glide.load.a dataSource, com.bumptech.glide.load.c encodeStrategy) {
            return (dataSource == com.bumptech.glide.load.a.RESOURCE_DISK_CACHE || dataSource == com.bumptech.glide.load.a.MEMORY_CACHE) ? false : true;
        }

        public boolean b() {
            return true;
        }

        public boolean a() {
            return false;
        }
    }

    /* compiled from: DiskCacheStrategy */
    public class e extends i {
        e() {
        }

        public boolean c(com.bumptech.glide.load.a dataSource) {
            return dataSource == com.bumptech.glide.load.a.REMOTE;
        }

        public boolean d(boolean isFromAlternateCacheKey, com.bumptech.glide.load.a dataSource, com.bumptech.glide.load.c encodeStrategy) {
            return ((isFromAlternateCacheKey && dataSource == com.bumptech.glide.load.a.DATA_DISK_CACHE) || dataSource == com.bumptech.glide.load.a.LOCAL) && encodeStrategy == com.bumptech.glide.load.c.TRANSFORMED;
        }

        public boolean b() {
            return true;
        }

        public boolean a() {
            return true;
        }
    }
}
