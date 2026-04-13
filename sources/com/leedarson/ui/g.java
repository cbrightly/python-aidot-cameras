package com.leedarson.ui;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.leedarson.R$string;
import com.leedarson.bean.LocalMedia;
import com.leedarson.smartcamera.utils.e;
import com.leedarson.smartcamera.utils.f;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: MediaListPresenter */
public class g extends com.leedarson.base.presenters.a<h, MediaListFragment> {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public int f;
    ExecutorService g = Executors.newCachedThreadPool();

    static /* synthetic */ ArrayList r(g x0, Context x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 11199, new Class[]{g.class, Context.class}, ArrayList.class);
        return proxy.isSupported ? (ArrayList) proxy.result : x0.A(x1);
    }

    static /* synthetic */ ArrayList s(g x0, Context x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 11200, new Class[]{g.class, Context.class}, ArrayList.class);
        return proxy.isSupported ? (ArrayList) proxy.result : x0.z(x1);
    }

    static /* synthetic */ void t(g x0, Context x1, String x2) {
        Class[] clsArr = {g.class, Context.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 11201, clsArr, Void.TYPE).isSupported) {
            x0.w(x1, x2);
        }
    }

    public g(h view, MediaListFragment fragment) {
        super(view, fragment);
    }

    public void x(int position) {
        if (!PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 11191, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            new b().executeOnExecutor(this.g, new Integer[]{Integer.valueOf(position)});
        }
    }

    private ArrayList<LocalMedia> z(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 11192, new Class[]{Context.class}, ArrayList.class);
        if (proxy.isSupported) {
            return (ArrayList) proxy.result;
        }
        Context context2 = context;
        String path = f.b(context2);
        return y(context2.getContentResolver().query(MediaStore.Files.getContentUri("external"), (String[]) null, "_data like ? AND( mime_type= ? or mime_type= ?)", new String[]{path + "%", "image/jpeg", "image/png"}, "date_modified DESC"), true);
    }

    private ArrayList<LocalMedia> A(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 11193, new Class[]{Context.class}, ArrayList.class);
        if (proxy.isSupported) {
            return (ArrayList) proxy.result;
        }
        Context context2 = context;
        String path = f.b(context2);
        return y(context2.getContentResolver().query(MediaStore.Files.getContentUri("external"), (String[]) null, "_data like ? AND mime_type= ? ", new String[]{path + "%", "video/mp4"}, "date_modified DESC"), false);
    }

    private void w(Context context, String filePath) {
        if (!PatchProxy.proxy(new Object[]{context, filePath}, this, changeQuickRedirect, false, 11194, new Class[]{Context.class, String.class}, Void.TYPE).isSupported) {
            int delete = context.getContentResolver().delete(MediaStore.Files.getContentUri("external"), "_data = ? ", new String[]{filePath});
            try {
                ((MediaListFragment) l()).getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(filePath))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<LocalMedia> y(Cursor cursor, boolean z) {
        int idColumn;
        boolean isImg;
        g gVar;
        Uri contentUri;
        long id;
        MediaListFragment fragment;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{cursor, new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 11195, new Class[]{Cursor.class, Boolean.TYPE}, ArrayList.class);
        if (proxy.isSupported) {
            return (ArrayList) proxy.result;
        }
        g gVar2 = this;
        boolean isImg2 = z;
        Cursor cursor2 = cursor;
        ArrayList<LocalMedia> mediaList = new ArrayList<>();
        if (cursor2 == null) {
            return mediaList;
        }
        try {
            MediaListFragment fragment2 = (MediaListFragment) gVar2.l();
            if (fragment2 != null) {
                Locale locale = fragment2.getResources().getConfiguration().locale;
                DateFormat simpleDateFormat = DateFormat.getDateInstance(1, locale);
                Date currentDate = new Date(System.currentTimeMillis());
                String muteDateStr = "";
                if (isImg2) {
                    try {
                        idColumn = cursor2.getColumnIndexOrThrow("_id");
                    } catch (Exception e) {
                        e = e;
                        g gVar3 = gVar2;
                        boolean z2 = isImg2;
                        try {
                            e.printStackTrace();
                            cursor2.close();
                            return mediaList;
                        } catch (Throwable th) {
                            th = th;
                            cursor2.close();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        g gVar4 = gVar2;
                        boolean z3 = isImg2;
                        cursor2.close();
                        throw th;
                    }
                } else {
                    idColumn = cursor2.getColumnIndexOrThrow("_id");
                }
                while (cursor2.moveToNext()) {
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setPath(cursor2.getString(cursor2.getColumnIndexOrThrow("_data")));
                    long modifyTimed = (long) cursor2.getInt(cursor2.getColumnIndexOrThrow("date_modified"));
                    localMedia.setModifyTime(modifyTimed);
                    long id2 = cursor2.getLong(idColumn);
                    if (isImg2) {
                        try {
                            gVar = gVar2;
                            isImg = isImg2;
                            id = id2;
                            try {
                                contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                            } catch (Exception e2) {
                                e = e2;
                                e.printStackTrace();
                                cursor2.close();
                                return mediaList;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            boolean z4 = isImg2;
                            g gVar5 = gVar2;
                            e.printStackTrace();
                            cursor2.close();
                            return mediaList;
                        } catch (Throwable th3) {
                            th = th3;
                            boolean z5 = isImg2;
                            g gVar6 = gVar2;
                            cursor2.close();
                            throw th;
                        }
                    } else {
                        gVar = gVar2;
                        isImg = isImg2;
                        id = id2;
                        contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
                    }
                    long j = id;
                    StringBuilder sb = new StringBuilder();
                    Locale locale2 = locale;
                    sb.append("parseCursor: ");
                    sb.append(contentUri.toString());
                    e.c("MediaListPresenter", sb.toString());
                    localMedia.setContentUri(contentUri);
                    int idColumn2 = idColumn;
                    LocalMedia localMedia2 = localMedia;
                    Date date = new Date(modifyTimed * 1000);
                    String mediaDateStr = simpleDateFormat.format(date);
                    if ("".equals(muteDateStr)) {
                        LocalMedia dateItem = new LocalMedia();
                        if (simpleDateFormat.format(currentDate).equals(mediaDateStr)) {
                            Date date2 = date;
                            fragment = fragment2;
                            dateItem.setDateStr(fragment2.getResources().getString(R$string.today));
                        } else {
                            fragment = fragment2;
                            Date date3 = date;
                            dateItem.setDateStr(mediaDateStr);
                        }
                        dateItem.setHeader(true);
                        dateItem.setModifyTime(modifyTimed);
                        mediaList.add(dateItem);
                        muteDateStr = mediaDateStr;
                    } else {
                        fragment = fragment2;
                        Date date4 = date;
                        if (!muteDateStr.equals(mediaDateStr)) {
                            LocalMedia dateItem2 = new LocalMedia();
                            dateItem2.setDateStr(mediaDateStr);
                            dateItem2.setHeader(true);
                            dateItem2.setModifyTime(modifyTimed);
                            mediaList.add(dateItem2);
                            muteDateStr = mediaDateStr;
                        }
                    }
                    int size = cursor2.getInt(cursor2.getColumnIndexOrThrow("_size"));
                    localMedia2.setSize(size);
                    int duration = cursor2.getInt(cursor2.getColumnIndexOrThrow(TypedValues.TransitionType.S_DURATION));
                    localMedia2.setDuration(duration);
                    if (duration == 0) {
                        int i = size;
                        if (localMedia2.getPath().toUpperCase().contains(".MP4")) {
                            g gVar7 = gVar;
                            try {
                                localMedia2.setDuration(gVar7.C(localMedia2.getPath()));
                                gVar = gVar7;
                                File file = new File(localMedia2.getPath());
                                if (file.exists() && file.length() == 0 && localMedia2.getDuration() == 0) {
                                    idColumn = idColumn2;
                                    gVar2 = gVar;
                                    isImg2 = isImg;
                                    locale = locale2;
                                    fragment2 = fragment;
                                }
                            } catch (Exception e4) {
                                e = e4;
                                g gVar8 = gVar7;
                                e.printStackTrace();
                                cursor2.close();
                                return mediaList;
                            } catch (Throwable th4) {
                                th = th4;
                                g gVar9 = gVar7;
                                cursor2.close();
                                throw th;
                            }
                        }
                    } else {
                        int i2 = size;
                    }
                    mediaList.add(localMedia2);
                    idColumn = idColumn2;
                    gVar2 = gVar;
                    isImg2 = isImg;
                    locale = locale2;
                    fragment2 = fragment;
                }
                MediaListFragment mediaListFragment = fragment2;
                g gVar10 = gVar2;
                boolean z6 = isImg2;
                Locale locale3 = locale;
                int i3 = idColumn;
            } else {
                g gVar11 = gVar2;
                boolean z7 = isImg2;
            }
        } catch (Exception e5) {
            e = e5;
            g gVar12 = gVar2;
            boolean z8 = isImg2;
            e.printStackTrace();
            cursor2.close();
            return mediaList;
        } catch (Throwable th5) {
            th = th5;
            g gVar13 = gVar2;
            boolean z9 = isImg2;
            cursor2.close();
            throw th;
        }
        cursor2.close();
        return mediaList;
    }

    private int C(String videoPath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{videoPath}, this, changeQuickRedirect, false, 11196, new Class[]{String.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(videoPath);
            return (int) Long.parseLong(mmr.extractMetadata(9));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void B() {
        ExecutorService executorService;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11197, new Class[0], Void.TYPE).isSupported && (executorService = this.g) != null) {
            executorService.shutdown();
        }
    }

    public void v(int fragmentPositon, ArrayList<LocalMedia> data) {
        if (!PatchProxy.proxy(new Object[]{new Integer(fragmentPositon), data}, this, changeQuickRedirect, false, 11198, new Class[]{Integer.TYPE, ArrayList.class}, Void.TYPE).isSupported) {
            this.f = fragmentPositon;
            if (Build.VERSION.SDK_INT >= 30) {
                List<Uri> uriList = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    uriList.add(data.get(i).getContentUri());
                }
                MediaListFragment fragment = (MediaListFragment) l();
                if (fragment != null) {
                    try {
                        fragment.getActivity().startIntentSenderForResult(MediaStore.createDeleteRequest(fragment.getContext().getContentResolver(), uriList).getIntentSender(), 123, (Intent) null, 0, 0, 0);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                new a().executeOnExecutor(this.g, new ArrayList[]{data});
            }
        }
    }

    /* compiled from: MediaListPresenter */
    public class b extends AsyncTask<Integer, Void, ArrayList<LocalMedia>> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{objArr}, this, changeQuickRedirect, false, 11209, new Class[]{Object[].class}, Object.class);
            return proxy.isSupported ? proxy.result : a((Integer[]) objArr);
        }

        public /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 11208, new Class[]{Object.class}, Void.TYPE).isSupported) {
                b((ArrayList) obj);
            }
        }

        public ArrayList<LocalMedia> a(Integer... integers) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{integers}, this, changeQuickRedirect, false, 11206, new Class[]{Integer[].class}, ArrayList.class);
            if (proxy.isSupported) {
                return (ArrayList) proxy.result;
            }
            MediaListFragment fragment = (MediaListFragment) g.this.l();
            if (fragment == null) {
                return null;
            }
            ((h) g.this.m()).b();
            int position = integers[0].intValue();
            if (position == 0) {
                return g.r(g.this, fragment.getContext());
            }
            if (position == 1) {
                return g.s(g.this, fragment.getContext());
            }
            return null;
        }

        public void b(ArrayList<LocalMedia> localMedia) {
            if (!PatchProxy.proxy(new Object[]{localMedia}, this, changeQuickRedirect, false, 11207, new Class[]{ArrayList.class}, Void.TYPE).isSupported) {
                super.onPostExecute(localMedia);
                ((h) g.this.m()).a();
                if (localMedia != null) {
                    ((h) g.this.m()).E0(localMedia);
                }
            }
        }
    }

    /* compiled from: MediaListPresenter */
    public class a extends AsyncTask<ArrayList<LocalMedia>, Void, ArrayList<LocalMedia>> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{objArr}, this, changeQuickRedirect, false, 11205, new Class[]{Object[].class}, Object.class);
            return proxy.isSupported ? proxy.result : a((ArrayList[]) objArr);
        }

        public /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 11204, new Class[]{Object.class}, Void.TYPE).isSupported) {
                b((ArrayList) obj);
            }
        }

        public ArrayList<LocalMedia> a(ArrayList<LocalMedia>... arrayLists) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{arrayLists}, this, changeQuickRedirect, false, 11202, new Class[]{ArrayList[].class}, ArrayList.class);
            if (proxy.isSupported) {
                return (ArrayList) proxy.result;
            }
            MediaListFragment fragment = (MediaListFragment) g.this.l();
            if (fragment == null) {
                return null;
            }
            ((h) g.this.m()).b();
            ArrayList<LocalMedia> list = arrayLists[0];
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isChecked()) {
                    g.t(g.this, fragment.getContext(), list.get(i).getPath());
                }
            }
            if (g.this.f == 0) {
                return g.r(g.this, fragment.getContext());
            }
            if (g.this.f == 1) {
                return g.s(g.this, fragment.getContext());
            }
            return null;
        }

        public void b(ArrayList<LocalMedia> localMedia) {
            if (!PatchProxy.proxy(new Object[]{localMedia}, this, changeQuickRedirect, false, 11203, new Class[]{ArrayList.class}, Void.TYPE).isSupported) {
                super.onPostExecute(localMedia);
                ((h) g.this.m()).a();
                if (localMedia != null) {
                    ((h) g.this.m()).j1(localMedia);
                }
            }
        }
    }
}
