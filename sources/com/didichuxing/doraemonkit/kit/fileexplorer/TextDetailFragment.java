package com.didichuxing.doraemonkit.kit.fileexplorer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.util.LogHelper;
import com.didichuxing.doraemonkit.widget.titlebar.TitleBar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class TextDetailFragment extends BaseFragment {
    private static final String TAG = "TextDetailFragment";
    private RecyclerView mContent;
    /* access modifiers changed from: private */
    public TextContentAdapter mContentAdapter;
    private File mFile;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TitleBar) findViewById(R.id.title_bar)).setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            public void onLeftClick() {
                TextDetailFragment.this.finish();
            }

            public void onRightClick() {
            }
        });
        initContent();
        Bundle data = getArguments();
        if (data != null) {
            this.mFile = (File) data.getSerializable(BundleKey.FILE_KEY);
        }
        readFile(this.mFile);
    }

    public void initContent() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.text_list);
        this.mContent = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TextContentAdapter textContentAdapter = new TextContentAdapter(getContext());
        this.mContentAdapter = textContentAdapter;
        this.mContent.setAdapter(textContentAdapter);
    }

    private void readFile(File file) {
        if (this.mFile != null) {
            new FileReadTask(this).execute(new File[]{file});
        }
    }

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_text_detail;
    }

    public static class FileReadTask extends AsyncTask<File, String, Void> {
        private WeakReference<TextDetailFragment> mReference;

        public FileReadTask(TextDetailFragment fragment) {
            this.mReference = new WeakReference<>(fragment);
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(File... files) {
            try {
                FileReader fileReader = new FileReader(files[0]);
                BufferedReader br = new BufferedReader(fileReader);
                while (true) {
                    String readLine = br.readLine();
                    String textLine = readLine;
                    if (readLine != null) {
                        publishProgress(new String[]{textLine});
                    } else {
                        br.close();
                        fileReader.close();
                        return null;
                    }
                }
            } catch (IOException e) {
                LogHelper.e(TextDetailFragment.TAG, e.toString());
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if (this.mReference.get() != null) {
                ((TextDetailFragment) this.mReference.get()).mContentAdapter.append(values[0]);
            }
        }
    }
}
