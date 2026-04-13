package com.didichuxing.doraemonkit.kit.fileexplorer;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.widget.videoview.MyVideoView;
import java.io.File;

public class VideoPlayFragment extends BaseFragment {
    private File mFile;
    private MyVideoView mVideoView;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_video_play;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        if (data != null) {
            this.mFile = (File) data.getSerializable(BundleKey.FILE_KEY);
        }
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyVideoView myVideoView = (MyVideoView) findViewById(R.id.video_view);
        this.mVideoView = myVideoView;
        myVideoView.register(getActivity());
        this.mVideoView.setVideoPath(this.mFile.getPath());
    }

    public void onPause() {
        super.onPause();
        this.mVideoView.onPause();
    }

    public void onResume() {
        super.onResume();
        this.mVideoView.onResume();
    }
}
