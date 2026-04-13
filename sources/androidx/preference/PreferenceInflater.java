package androidx.preference;

import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.InflateException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class PreferenceInflater {
    private static final HashMap<String, Constructor> CONSTRUCTOR_MAP = new HashMap<>();
    private static final Class<?>[] CONSTRUCTOR_SIGNATURE = {Context.class, AttributeSet.class};
    private static final String EXTRA_TAG_NAME = "extra";
    private static final String INTENT_TAG_NAME = "intent";
    private final Object[] mConstructorArgs = new Object[2];
    private final Context mContext;
    private String[] mDefaultPackages;
    private PreferenceManager mPreferenceManager;

    public PreferenceInflater(Context context, PreferenceManager preferenceManager) {
        this.mContext = context;
        init(preferenceManager);
    }

    private void init(PreferenceManager preferenceManager) {
        this.mPreferenceManager = preferenceManager;
        setDefaultPackages(new String[]{Preference.class.getPackage().getName() + ".", SwitchPreference.class.getPackage().getName() + "."});
    }

    public void setDefaultPackages(String[] defaultPackage) {
        this.mDefaultPackages = defaultPackage;
    }

    public String[] getDefaultPackages() {
        return this.mDefaultPackages;
    }

    public Context getContext() {
        return this.mContext;
    }

    public Preference inflate(int resource, @Nullable PreferenceGroup root) {
        XmlResourceParser parser = getContext().getResources().getXml(resource);
        try {
            return inflate((XmlPullParser) parser, root);
        } finally {
            parser.close();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001a A[Catch:{ InflateException -> 0x0080, XmlPullParserException -> 0x0071, IOException -> 0x004a }] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x002f A[SYNTHETIC, Splitter:B:14:0x002f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.preference.Preference inflate(org.xmlpull.v1.XmlPullParser r7, @androidx.annotation.Nullable androidx.preference.PreferenceGroup r8) {
        /*
            r6 = this;
            java.lang.Object[] r0 = r6.mConstructorArgs
            monitor-enter(r0)
            android.util.AttributeSet r1 = android.util.Xml.asAttributeSet(r7)     // Catch:{ all -> 0x0083 }
            java.lang.Object[] r2 = r6.mConstructorArgs     // Catch:{ all -> 0x0083 }
            r3 = 0
            android.content.Context r4 = r6.mContext     // Catch:{ all -> 0x0083 }
            r2[r3] = r4     // Catch:{ all -> 0x0083 }
        L_0x000e:
            int r2 = r7.next()     // Catch:{ InflateException -> 0x0080, XmlPullParserException -> 0x0071, IOException -> 0x004a }
            r3 = 2
            if (r2 == r3) goto L_0x0018
            r4 = 1
            if (r2 != r4) goto L_0x000e
        L_0x0018:
            if (r2 != r3) goto L_0x002f
            java.lang.String r3 = r7.getName()     // Catch:{ InflateException -> 0x0080, XmlPullParserException -> 0x0071, IOException -> 0x004a }
            androidx.preference.Preference r3 = r6.createItemFromTag(r3, r1)     // Catch:{ InflateException -> 0x0080, XmlPullParserException -> 0x0071, IOException -> 0x004a }
            r4 = r3
            androidx.preference.PreferenceGroup r4 = (androidx.preference.PreferenceGroup) r4     // Catch:{ InflateException -> 0x0080, XmlPullParserException -> 0x0071, IOException -> 0x004a }
            androidx.preference.PreferenceGroup r4 = r6.onMergeRoots(r8, r4)     // Catch:{ InflateException -> 0x0080, XmlPullParserException -> 0x0071, IOException -> 0x004a }
            r6.rInflate(r7, r4, r1)     // Catch:{ InflateException -> 0x0080, XmlPullParserException -> 0x0071, IOException -> 0x004a }
            monitor-exit(r0)     // Catch:{ all -> 0x0083 }
            return r4
        L_0x002f:
            android.view.InflateException r3 = new android.view.InflateException     // Catch:{ InflateException -> 0x0080, XmlPullParserException -> 0x0071, IOException -> 0x004a }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ InflateException -> 0x0080, XmlPullParserException -> 0x0071, IOException -> 0x004a }
            r4.<init>()     // Catch:{ InflateException -> 0x0080, XmlPullParserException -> 0x0071, IOException -> 0x004a }
            java.lang.String r5 = r7.getPositionDescription()     // Catch:{ InflateException -> 0x0080, XmlPullParserException -> 0x0071, IOException -> 0x004a }
            r4.append(r5)     // Catch:{ InflateException -> 0x0080, XmlPullParserException -> 0x0071, IOException -> 0x004a }
            java.lang.String r5 = ": No start tag found!"
            r4.append(r5)     // Catch:{ InflateException -> 0x0080, XmlPullParserException -> 0x0071, IOException -> 0x004a }
            java.lang.String r4 = r4.toString()     // Catch:{ InflateException -> 0x0080, XmlPullParserException -> 0x0071, IOException -> 0x004a }
            r3.<init>(r4)     // Catch:{ InflateException -> 0x0080, XmlPullParserException -> 0x0071, IOException -> 0x004a }
            throw r3     // Catch:{ InflateException -> 0x0080, XmlPullParserException -> 0x0071, IOException -> 0x004a }
        L_0x004a:
            r2 = move-exception
            android.view.InflateException r3 = new android.view.InflateException     // Catch:{ all -> 0x0083 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0083 }
            r4.<init>()     // Catch:{ all -> 0x0083 }
            java.lang.String r5 = r7.getPositionDescription()     // Catch:{ all -> 0x0083 }
            r4.append(r5)     // Catch:{ all -> 0x0083 }
            java.lang.String r5 = ": "
            r4.append(r5)     // Catch:{ all -> 0x0083 }
            java.lang.String r5 = r2.getMessage()     // Catch:{ all -> 0x0083 }
            r4.append(r5)     // Catch:{ all -> 0x0083 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0083 }
            r3.<init>(r4)     // Catch:{ all -> 0x0083 }
            r3.initCause(r2)     // Catch:{ all -> 0x0083 }
            throw r3     // Catch:{ all -> 0x0083 }
        L_0x0071:
            r2 = move-exception
            android.view.InflateException r3 = new android.view.InflateException     // Catch:{ all -> 0x0083 }
            java.lang.String r4 = r2.getMessage()     // Catch:{ all -> 0x0083 }
            r3.<init>(r4)     // Catch:{ all -> 0x0083 }
            r3.initCause(r2)     // Catch:{ all -> 0x0083 }
            throw r3     // Catch:{ all -> 0x0083 }
        L_0x0080:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0083 }
        L_0x0083:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0083 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.preference.PreferenceInflater.inflate(org.xmlpull.v1.XmlPullParser, androidx.preference.PreferenceGroup):androidx.preference.Preference");
    }

    @NonNull
    private PreferenceGroup onMergeRoots(PreferenceGroup givenRoot, @NonNull PreferenceGroup xmlRoot) {
        if (givenRoot != null) {
            return givenRoot;
        }
        xmlRoot.onAttachedToHierarchy(this.mPreferenceManager);
        return xmlRoot;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0037, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0038, code lost:
        r6 = r10;
        r8 = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007d, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x007e, code lost:
        r1 = new android.view.InflateException(r14.getPositionDescription() + ": Error inflating class " + r12);
        r1.initCause(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x009d, code lost:
        throw r1;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x007d A[ExcHandler: Exception (r2v3 'e' java.lang.Exception A[CUSTOM_DECLARE]), Splitter:B:2:0x000d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private androidx.preference.Preference createItem(@androidx.annotation.NonNull java.lang.String r12, @androidx.annotation.Nullable java.lang.String[] r13, android.util.AttributeSet r14) {
        /*
            r11 = this;
            java.util.HashMap<java.lang.String, java.lang.reflect.Constructor> r0 = CONSTRUCTOR_MAP
            java.lang.Object r0 = r0.get(r12)
            java.lang.reflect.Constructor r0 = (java.lang.reflect.Constructor) r0
            java.lang.String r1 = ": Error inflating class "
            r2 = 1
            if (r0 != 0) goto L_0x0072
            android.content.Context r3 = r11.mContext     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            java.lang.ClassLoader r3 = r3.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            r4 = 0
            r5 = 0
            if (r13 == 0) goto L_0x005e
            int r6 = r13.length     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            if (r6 != 0) goto L_0x001b
            goto L_0x005e
        L_0x001b:
            r6 = 0
            int r7 = r13.length     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            r8 = r5
        L_0x001e:
            if (r8 >= r7) goto L_0x003c
            r9 = r13[r8]     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x0037, Exception -> 0x007d }
            r10.<init>()     // Catch:{ ClassNotFoundException -> 0x0037, Exception -> 0x007d }
            r10.append(r9)     // Catch:{ ClassNotFoundException -> 0x0037, Exception -> 0x007d }
            r10.append(r12)     // Catch:{ ClassNotFoundException -> 0x0037, Exception -> 0x007d }
            java.lang.String r10 = r10.toString()     // Catch:{ ClassNotFoundException -> 0x0037, Exception -> 0x007d }
            java.lang.Class r5 = java.lang.Class.forName(r10, r5, r3)     // Catch:{ ClassNotFoundException -> 0x0037, Exception -> 0x007d }
            r4 = r5
            goto L_0x003c
        L_0x0037:
            r10 = move-exception
            r6 = r10
            int r8 = r8 + 1
            goto L_0x001e
        L_0x003c:
            if (r4 != 0) goto L_0x0063
            if (r6 != 0) goto L_0x005c
            android.view.InflateException r2 = new android.view.InflateException     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            r5.<init>()     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            java.lang.String r7 = r14.getPositionDescription()     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            r5.append(r7)     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            r5.append(r1)     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            r5.append(r12)     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            java.lang.String r5 = r5.toString()     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            r2.<init>(r5)     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            throw r2     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
        L_0x005c:
            throw r6     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
        L_0x005e:
            java.lang.Class r5 = java.lang.Class.forName(r12, r5, r3)     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            r4 = r5
        L_0x0063:
            java.lang.Class<?>[] r5 = CONSTRUCTOR_SIGNATURE     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            java.lang.reflect.Constructor r5 = r4.getConstructor(r5)     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            r0 = r5
            r0.setAccessible(r2)     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            java.util.HashMap<java.lang.String, java.lang.reflect.Constructor> r5 = CONSTRUCTOR_MAP     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            r5.put(r12, r0)     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
        L_0x0072:
            java.lang.Object[] r3 = r11.mConstructorArgs     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            r3[r2] = r14     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            java.lang.Object r2 = r0.newInstance(r3)     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            androidx.preference.Preference r2 = (androidx.preference.Preference) r2     // Catch:{ ClassNotFoundException -> 0x009e, Exception -> 0x007d }
            return r2
        L_0x007d:
            r2 = move-exception
            android.view.InflateException r3 = new android.view.InflateException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = r14.getPositionDescription()
            r4.append(r5)
            r4.append(r1)
            r4.append(r12)
            java.lang.String r1 = r4.toString()
            r3.<init>(r1)
            r1 = r3
            r1.initCause(r2)
            throw r1
        L_0x009e:
            r1 = move-exception
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.preference.PreferenceInflater.createItem(java.lang.String, java.lang.String[], android.util.AttributeSet):androidx.preference.Preference");
    }

    /* access modifiers changed from: protected */
    public Preference onCreateItem(String name, AttributeSet attrs) {
        return createItem(name, this.mDefaultPackages, attrs);
    }

    private Preference createItemFromTag(String name, AttributeSet attrs) {
        try {
            if (-1 == name.indexOf(46)) {
                return onCreateItem(name, attrs);
            }
            return createItem(name, (String[]) null, attrs);
        } catch (InflateException e) {
            throw e;
        } catch (ClassNotFoundException e2) {
            InflateException ie = new InflateException(attrs.getPositionDescription() + ": Error inflating class (not found)" + name);
            ie.initCause(e2);
            throw ie;
        } catch (Exception e3) {
            InflateException ie2 = new InflateException(attrs.getPositionDescription() + ": Error inflating class " + name);
            ie2.initCause(e3);
            throw ie2;
        }
    }

    private void rInflate(XmlPullParser parser, Preference parent, AttributeSet attrs) {
        int depth = parser.getDepth();
        while (true) {
            int next = parser.next();
            int type = next;
            if ((next == 3 && parser.getDepth() <= depth) || type == 1) {
                return;
            }
            if (type == 2) {
                String name = parser.getName();
                if (INTENT_TAG_NAME.equals(name)) {
                    try {
                        parent.setIntent(Intent.parseIntent(getContext().getResources(), parser, attrs));
                    } catch (IOException e) {
                        XmlPullParserException ex = new XmlPullParserException("Error parsing preference");
                        ex.initCause(e);
                        throw ex;
                    }
                } else if (EXTRA_TAG_NAME.equals(name)) {
                    getContext().getResources().parseBundleExtra(EXTRA_TAG_NAME, attrs, parent.getExtras());
                    try {
                        skipCurrentTag(parser);
                    } catch (IOException e2) {
                        XmlPullParserException ex2 = new XmlPullParserException("Error parsing preference");
                        ex2.initCause(e2);
                        throw ex2;
                    }
                } else {
                    Preference item = createItemFromTag(name, attrs);
                    ((PreferenceGroup) parent).addItemFromInflater(item);
                    rInflate(parser, item, attrs);
                }
            }
        }
    }

    private static void skipCurrentTag(XmlPullParser parser) {
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type == 1) {
                return;
            }
            if (type == 3 && parser.getDepth() <= outerDepth) {
                return;
            }
        }
    }
}
