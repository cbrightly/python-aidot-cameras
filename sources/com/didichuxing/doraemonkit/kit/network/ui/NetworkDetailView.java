package com.didichuxing.doraemonkit.kit.network.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.e0;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.network.bean.NetworkRecord;
import com.didichuxing.doraemonkit.kit.network.bean.Request;
import com.didichuxing.doraemonkit.kit.network.bean.Response;
import com.didichuxing.doraemonkit.kit.network.utils.ByteUtil;
import com.didichuxing.doraemonkit.widget.jsonviewer.JsonRecyclerView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class NetworkDetailView extends LinearLayout {
    /* access modifiers changed from: private */
    public TextView body;
    private TextView diverBody;
    /* access modifiers changed from: private */
    public TextView diverFormat;
    private TextView diverHeader;
    private TextView diverTime;
    private TextView header;
    /* access modifiers changed from: private */
    public JsonRecyclerView jsonView;
    /* access modifiers changed from: private */
    public ClipboardManager mClipboard;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
    private TextView method;
    private TextView size;
    private TextView time;
    private TextView url;

    public NetworkDetailView(Context context) {
        super(context);
        LinearLayout.inflate(context, R.layout.dk_view_network_request, this);
        this.mClipboard = (ClipboardManager) context.getSystemService("clipboard");
        this.url = (TextView) findViewById(R.id.tv_url);
        this.method = (TextView) findViewById(R.id.tv_method);
        this.size = (TextView) findViewById(R.id.tv_data_size);
        this.header = (TextView) findViewById(R.id.tv_header);
        this.body = (TextView) findViewById(R.id.tv_body);
        this.time = (TextView) findViewById(R.id.tv_time);
        this.diverTime = (TextView) findViewById(R.id.diver_time);
        this.diverHeader = (TextView) findViewById(R.id.diver_header);
        this.diverBody = (TextView) findViewById(R.id.diver_body);
        this.diverFormat = (TextView) findViewById(R.id.diver_format);
        this.jsonView = (JsonRecyclerView) findViewById(R.id.json_body);
        this.body.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                NetworkDetailView.this.mClipboard.setPrimaryClip(ClipData.newPlainText("Label", NetworkDetailView.this.body.getText()));
                Toast.makeText(NetworkDetailView.this.getContext(), "copy success", 0).show();
                return false;
            }
        });
    }

    public NetworkDetailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void bindRequest(NetworkRecord record) {
        String str = "NULL";
        this.diverTime.setText(R.string.dk_network_detail_title_request_time);
        this.diverHeader.setText(R.string.dk_network_detail_title_request_header);
        this.diverBody.setText(R.string.dk_network_detail_title_request_body);
        this.diverFormat.setVisibility(8);
        this.jsonView.setVisibility(8);
        this.body.setVisibility(0);
        if (record.mRequest != null) {
            Request request = record.mRequest;
            this.url.setText(request.url);
            this.method.setText(request.method);
            try {
                this.header.setText(URLDecoder.decode(request.headers, "utf-8"));
            } catch (Exception e) {
                this.header.setText(request.headers);
            }
            this.time.setText(this.mDateFormat.format(new Date(record.startTime)));
            this.size.setText(ByteUtil.getPrintSize(record.requestLength));
            try {
                this.body.setText(URLDecoder.decode(TextUtils.isEmpty(request.postData) ? str : request.postData, "utf-8"));
            } catch (Exception e2) {
                TextView textView = this.body;
                if (!TextUtils.isEmpty(request.postData)) {
                    str = request.postData;
                }
                textView.setText(str);
            }
        }
    }

    public void bindResponse(final NetworkRecord record) {
        this.diverTime.setText(R.string.dk_network_detail_title_response_time);
        this.diverHeader.setText(R.string.dk_network_detail_title_response_header);
        this.diverBody.setText(R.string.dk_network_detail_title_response_body);
        this.diverFormat.setVisibility(0);
        this.diverFormat.setText("unFormat");
        this.jsonView.setVisibility(0);
        this.jsonView.setTextSize(16.0f);
        this.jsonView.setScaleEnable(false);
        this.body.setVisibility(8);
        this.diverFormat.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                String strBody = "NULL";
                if (NetworkDetailView.this.body.getVisibility() == 0) {
                    if (!TextUtils.isEmpty(record.mResponseBody)) {
                        strBody = record.mResponseBody;
                    }
                    String strBody2 = strBody;
                    try {
                        new JSONObject(strBody2);
                        NetworkDetailView.this.jsonView.setVisibility(0);
                        NetworkDetailView.this.body.setVisibility(8);
                        NetworkDetailView.this.diverFormat.setText("unFormat");
                    } catch (JSONException e) {
                        NetworkDetailView.this.jsonView.setVisibility(8);
                        NetworkDetailView.this.body.setVisibility(0);
                        NetworkDetailView.this.body.setText(strBody2);
                        NetworkDetailView.this.diverFormat.setText(IjkMediaMeta.IJKM_KEY_FORMAT);
                        e0.n("format error");
                    }
                } else {
                    if (!TextUtils.isEmpty(record.mResponseBody)) {
                        strBody = record.mResponseBody;
                    }
                    NetworkDetailView.this.body.setText(strBody);
                    NetworkDetailView.this.diverFormat.setText(IjkMediaMeta.IJKM_KEY_FORMAT);
                    NetworkDetailView.this.jsonView.setVisibility(8);
                    NetworkDetailView.this.body.setVisibility(0);
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        if (record.mResponse != null) {
            Response response = record.mResponse;
            Request request = record.mRequest;
            this.url.setText(response.url);
            this.method.setText(request.method);
            this.header.setText(response.headers);
            this.time.setText(this.mDateFormat.format(new Date(record.endTime)));
            this.size.setText(ByteUtil.getPrintSize(record.responseLength));
            String strBody = TextUtils.isEmpty(record.mResponseBody) ? "NULL" : record.mResponseBody;
            try {
                new JSONObject(strBody);
                this.jsonView.bindJson(strBody);
            } catch (JSONException e) {
                e.printStackTrace();
                this.body.setVisibility(0);
                this.jsonView.setVisibility(8);
                this.diverFormat.setText(IjkMediaMeta.IJKM_KEY_FORMAT);
                this.body.setText(strBody);
            }
        }
    }
}
