package com.didichuxing.doraemonkit.kit.network.core;

import android.annotation.SuppressLint;
import java.util.ArrayList;
import org.slf4j.e;

public class MimeMatcher<T> {
    private final ArrayList<MimeMatcher<T>.MimeMatcherRule> mRuleMap = new ArrayList<>();

    public void addRule(String ruleExpression, T resultIfMatched) {
        this.mRuleMap.add(new MimeMatcherRule(ruleExpression, resultIfMatched));
    }

    public void clear() {
        this.mRuleMap.clear();
    }

    public T match(String mimeT) {
        int ruleMapN = this.mRuleMap.size();
        for (int i = 0; i < ruleMapN; i++) {
            MimeMatcher<T>.MimeMatcherRule rule = this.mRuleMap.get(i);
            if (rule.match(mimeT)) {
                return rule.getResultIfMatched();
            }
        }
        return null;
    }

    @SuppressLint({"BadMethodUse-java.lang.String.length"})
    public class MimeMatcherRule {
        private final boolean mHasWildcard;
        private final String mMatchPrefix;
        private final T mResultIfMatched;

        public MimeMatcherRule(String ruleExpression, T resultIfMatched) {
            if (ruleExpression.endsWith(e.ANY_MARKER)) {
                this.mHasWildcard = true;
                this.mMatchPrefix = ruleExpression.substring(0, ruleExpression.length() - 1);
            } else {
                this.mHasWildcard = false;
                this.mMatchPrefix = ruleExpression;
            }
            if (!this.mMatchPrefix.contains(e.ANY_MARKER)) {
                this.mResultIfMatched = resultIfMatched;
                return;
            }
            throw new IllegalArgumentException("Multiple wildcards present in rule expression " + ruleExpression);
        }

        public boolean match(String mimeType) {
            if (!mimeType.startsWith(this.mMatchPrefix)) {
                return false;
            }
            if (this.mHasWildcard || mimeType.length() == this.mMatchPrefix.length()) {
                return true;
            }
            return false;
        }

        public T getResultIfMatched() {
            return this.mResultIfMatched;
        }
    }
}
