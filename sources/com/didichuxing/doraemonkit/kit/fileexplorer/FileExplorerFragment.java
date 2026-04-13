package com.didichuxing.doraemonkit.kit.fileexplorer;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.fileexplorer.FileExplorerChooseDialog;
import com.didichuxing.doraemonkit.kit.fileexplorer.FileInfoAdapter;
import com.didichuxing.doraemonkit.util.FileUtil;
import com.didichuxing.doraemonkit.widget.dialog.DialogListener;
import com.didichuxing.doraemonkit.widget.dialog.DialogProvider;
import com.didichuxing.doraemonkit.widget.titlebar.TitleBar;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FileExplorerFragment extends BaseFragment {
    private static final String TAG = "FileExplorerFragment";
    /* access modifiers changed from: private */
    public File mCurDir;
    private FileInfoAdapter mFileInfoAdapter;
    private RecyclerView mFileList;
    /* access modifiers changed from: private */
    public TitleBar mTitleBar;

    private void initFileInfoList() {
        TitleBar titleBar = (TitleBar) findViewById(R.id.title_bar);
        this.mTitleBar = titleBar;
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            public void onLeftClick() {
                FileExplorerFragment.this.onBackPressed();
            }

            public void onRightClick() {
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.file_list);
        this.mFileList = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FileInfoAdapter fileInfoAdapter = new FileInfoAdapter(getContext());
        this.mFileInfoAdapter = fileInfoAdapter;
        fileInfoAdapter.setOnViewClickListener(new FileInfoAdapter.OnViewClickListener() {
            public void onViewClick(View v, FileInfo fileInfo) {
                if (fileInfo.file.isFile()) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(BundleKey.FILE_KEY, fileInfo.file);
                    if (FileUtil.isImage(fileInfo.file)) {
                        FileExplorerFragment.this.showContent(ImageDetailFragment.class, bundle);
                    } else if (FileUtil.isDB(fileInfo.file)) {
                        FileExplorerFragment.this.showContent(DatabaseDetailFragment.class, bundle);
                    } else if (FileUtil.isVideo(fileInfo.file)) {
                        FileExplorerFragment.this.showContent(VideoPlayFragment.class, bundle);
                    } else if (FileUtil.isSp(fileInfo.file)) {
                        FileExplorerFragment.this.showContent(SpFragment.class, bundle);
                    } else {
                        FileExplorerFragment.this.showContent(TextDetailFragment.class, bundle);
                    }
                } else {
                    File unused = FileExplorerFragment.this.mCurDir = fileInfo.file;
                    FileExplorerFragment.this.mTitleBar.setTitle(FileExplorerFragment.this.mCurDir.getName());
                    FileExplorerFragment fileExplorerFragment = FileExplorerFragment.this;
                    fileExplorerFragment.setAdapterData(fileExplorerFragment.getFileInfos(fileExplorerFragment.mCurDir));
                }
            }
        });
        this.mFileInfoAdapter.setOnViewLongClickListener(new FileInfoAdapter.OnViewLongClickListener() {
            public boolean onViewLongClick(View v, final FileInfo fileInfo) {
                FileExplorerChooseDialog dialog = new FileExplorerChooseDialog(fileInfo.file, (DialogListener) null);
                dialog.setOnButtonClickListener(new FileExplorerChooseDialog.OnButtonClickListener() {
                    public void onDeleteClick(FileExplorerChooseDialog dialog) {
                        FileUtil.deleteDirectory(fileInfo.file);
                        dialog.dismiss();
                        if (FileExplorerFragment.this.mCurDir != null) {
                            FileExplorerFragment.this.mTitleBar.setTitle(FileExplorerFragment.this.mCurDir.getName());
                            FileExplorerFragment fileExplorerFragment = FileExplorerFragment.this;
                            fileExplorerFragment.setAdapterData(fileExplorerFragment.getFileInfos(fileExplorerFragment.mCurDir));
                        }
                    }

                    public void onShareClick(FileExplorerChooseDialog dialog) {
                        FileUtil.systemShare(FileExplorerFragment.this.getContext(), fileInfo.file);
                        dialog.dismiss();
                    }
                });
                FileExplorerFragment.this.showDialog((DialogProvider) dialog);
                return true;
            }
        });
        setAdapterData(initRootFileInfos(getContext()));
        this.mFileList.setAdapter(this.mFileInfoAdapter);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mCurDir = null;
        initFileInfoList();
    }

    /* access modifiers changed from: private */
    public List<FileInfo> getFileInfos(File dir) {
        List<FileInfo> fileInfos = new ArrayList<>();
        if (dir.listFiles() == null) {
            return fileInfos;
        }
        for (File file : dir.listFiles()) {
            fileInfos.add(new FileInfo(file));
        }
        return fileInfos;
    }

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_file_explorer;
    }

    /* access modifiers changed from: protected */
    public boolean onBackPressed() {
        if (this.mCurDir == null) {
            finish();
            return true;
        } else if (isRootFile(getContext(), this.mCurDir)) {
            this.mTitleBar.setTitle(R.string.dk_kit_file_explorer);
            setAdapterData(initRootFileInfos(getContext()));
            this.mCurDir = null;
            return true;
        } else {
            File parentFile = this.mCurDir.getParentFile();
            this.mCurDir = parentFile;
            this.mTitleBar.setTitle(parentFile.getName());
            setAdapterData(getFileInfos(this.mCurDir));
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void setAdapterData(List<FileInfo> fileInfos) {
        if (fileInfos.isEmpty()) {
            this.mFileInfoAdapter.clear();
        } else {
            this.mFileInfoAdapter.setData(fileInfos);
        }
    }

    private List<FileInfo> initRootFileInfos(Context context) {
        List<File> rootFiles = getRootFiles();
        if (rootFiles == null) {
            return initDefaultRootFileInfos(context);
        }
        List<FileInfo> fileInfos = new ArrayList<>();
        for (File file : rootFiles) {
            fileInfos.add(new FileInfo(file));
        }
        return fileInfos;
    }

    private List<File> getRootFiles() {
        File dir;
        if (getArguments() == null || (dir = (File) getArguments().getSerializable(BundleKey.DIR_KEY)) == null || !dir.exists()) {
            return null;
        }
        return Arrays.asList(dir.listFiles());
    }

    private List<FileInfo> initDefaultRootFileInfos(Context context) {
        List<FileInfo> fileInfos = new ArrayList<>();
        fileInfos.add(new FileInfo(context.getFilesDir().getParentFile()));
        fileInfos.add(new FileInfo(context.getExternalCacheDir()));
        fileInfos.add(new FileInfo(context.getExternalFilesDir((String) null)));
        return fileInfos;
    }

    private boolean isRootFile(Context context, File file) {
        if (file == null) {
            return false;
        }
        List<File> rootFiles = getRootFiles();
        if (rootFiles != null) {
            Iterator<File> it = rootFiles.iterator();
            if (it.hasNext()) {
                return file.equals(it.next());
            }
        }
        if (file.equals(context.getExternalCacheDir()) || file.equals(context.getExternalFilesDir((String) null)) || file.equals(context.getFilesDir().getParentFile())) {
            return true;
        }
        return false;
    }
}
