package org.junit.runner;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: Description */
public class a implements Serializable {
    public static final a EMPTY = new a((Class<?>) null, "No Tests", new Annotation[0]);
    public static final a TEST_MECHANISM = new a((Class<?>) null, "Test mechanism", new Annotation[0]);
    private static final Pattern c = Pattern.compile("([\\s\\S]*)\\((.*)\\)");
    private static final long serialVersionUID = 1;
    private final Annotation[] fAnnotations;
    private final Collection<a> fChildren;
    private final String fDisplayName;
    private volatile Class<?> fTestClass;
    private final Serializable fUniqueId;

    public static a createSuiteDescription(String name, Annotation... annotations) {
        return new a((Class<?>) null, name, annotations);
    }

    public static a createSuiteDescription(String name, Serializable uniqueId, Annotation... annotations) {
        return new a((Class<?>) null, name, uniqueId, annotations);
    }

    public static a createTestDescription(String className, String name, Annotation... annotations) {
        return new a((Class<?>) null, a(name, className), annotations);
    }

    public static a createTestDescription(Class<?> clazz, String name, Annotation... annotations) {
        return new a(clazz, a(name, clazz.getName()), annotations);
    }

    public static a createTestDescription(Class<?> clazz, String name) {
        return new a(clazz, a(name, clazz.getName()), new Annotation[0]);
    }

    public static a createTestDescription(String className, String name, Serializable uniqueId) {
        return new a((Class<?>) null, a(name, className), uniqueId, new Annotation[0]);
    }

    private static String a(String name, String className) {
        return String.format("%s(%s)", new Object[]{name, className});
    }

    public static a createSuiteDescription(Class<?> testClass) {
        return new a(testClass, testClass.getName(), testClass.getAnnotations());
    }

    private a(Class<?> clazz, String displayName, Annotation... annotations) {
        this(clazz, displayName, displayName, annotations);
    }

    private a(Class<?> testClass, String displayName, Serializable uniqueId, Annotation... annotations) {
        this.fChildren = new ConcurrentLinkedQueue();
        if (displayName == null || displayName.length() == 0) {
            throw new IllegalArgumentException("The display name must not be empty.");
        } else if (uniqueId != null) {
            this.fTestClass = testClass;
            this.fDisplayName = displayName;
            this.fUniqueId = uniqueId;
            this.fAnnotations = annotations;
        } else {
            throw new IllegalArgumentException("The unique id must not be null.");
        }
    }

    public String getDisplayName() {
        return this.fDisplayName;
    }

    public void addChild(a description) {
        this.fChildren.add(description);
    }

    public ArrayList<a> getChildren() {
        return new ArrayList<>(this.fChildren);
    }

    public boolean isSuite() {
        return !isTest();
    }

    public boolean isTest() {
        return this.fChildren.isEmpty();
    }

    public int testCount() {
        if (isTest()) {
            return 1;
        }
        int result = 0;
        for (a child : this.fChildren) {
            result += child.testCount();
        }
        return result;
    }

    public int hashCode() {
        return this.fUniqueId.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof a)) {
            return false;
        }
        return this.fUniqueId.equals(((a) obj).fUniqueId);
    }

    public String toString() {
        return getDisplayName();
    }

    public boolean isEmpty() {
        return equals(EMPTY);
    }

    public a childlessCopy() {
        return new a(this.fTestClass, this.fDisplayName, this.fAnnotations);
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        for (Annotation each : this.fAnnotations) {
            if (each.annotationType().equals(annotationType)) {
                return (Annotation) annotationType.cast(each);
            }
        }
        return null;
    }

    public Collection<Annotation> getAnnotations() {
        return Arrays.asList(this.fAnnotations);
    }

    public Class<?> getTestClass() {
        if (this.fTestClass != null) {
            return this.fTestClass;
        }
        String name = getClassName();
        if (name == null) {
            return null;
        }
        try {
            this.fTestClass = Class.forName(name, false, getClass().getClassLoader());
            return this.fTestClass;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public String getClassName() {
        return this.fTestClass != null ? this.fTestClass.getName() : b(2, toString());
    }

    public String getMethodName() {
        return b(1, (String) null);
    }

    private String b(int group, String defaultString) {
        Matcher matcher = c.matcher(toString());
        return matcher.matches() ? matcher.group(group) : defaultString;
    }
}
