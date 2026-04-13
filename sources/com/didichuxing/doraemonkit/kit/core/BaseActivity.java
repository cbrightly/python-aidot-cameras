package com.didichuxing.doraemonkit.kit.core;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayDeque;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private ArrayDeque<BaseFragment> mFragments = new ArrayDeque<>();

    public void showContent(Class<? extends BaseFragment> target) {
        showContent(target, (Bundle) null);
    }

    public void showContent(Class<? extends BaseFragment> target, Bundle bundle) {
        try {
            BaseFragment fragment = (BaseFragment) target.newInstance();
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(16908290, (Fragment) fragment);
            this.mFragments.push(fragment);
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }

    public void onBackPressed() {
        if (this.mFragments.isEmpty()) {
            super.onBackPressed();
        } else if (!this.mFragments.getFirst().onBackPressed()) {
            this.mFragments.removeFirst();
            super.onBackPressed();
            if (this.mFragments.isEmpty()) {
                finish();
            }
        }
    }

    public void doBack(BaseFragment fragment) {
        if (this.mFragments.contains(fragment)) {
            this.mFragments.remove(fragment);
            getSupportFragmentManager().popBackStack();
            if (this.mFragments.isEmpty()) {
                finish();
            }
        }
    }
}
