package meshsdk.util;

import android.text.TextUtils;
import com.leedarson.log.elk.a;
import com.leedarson.log.reporter.b;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.util.ProcedureCollector;
import org.json.JSONArray;

public class ProvisionCollector {
    private static final int NUM = 2000;
    /* access modifiers changed from: private */
    public ArrayList<ProcedureCollector.LogBean> collectList = new ArrayList<>();
    private boolean enable;
    public String mac;

    public ProvisionCollector(String mac2) {
        this.mac = mac2;
    }

    public void setEnable(boolean enable2) {
        this.enable = enable2;
        if (enable2) {
            this.collectList.clear();
        }
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void addLog(String s, String tag, long threadId) {
        if (this.enable) {
            this.collectList.add(ProcedureCollector.createBean(s, tag, threadId));
        }
    }

    public void endCollectAndClear() {
        this.enable = false;
        clear();
    }

    public void endCollectThenReport() {
        this.enable = false;
        reportELK(1);
    }

    public void clear() {
        this.collectList.clear();
    }

    public void reportELK(final int type) {
        MeshLog.e("*****  reportELK  ***** mac:" + this.mac + ",size:" + this.collectList.size());
        SIGMesh.getInstance().executorTask(new Runnable() {
            public void run() {
                int end;
                int start;
                int count = ProvisionCollector.this.collectList.size() % 2000 == 0 ? ProvisionCollector.this.collectList.size() / 2000 : (ProvisionCollector.this.collectList.size() / 2000) + 1;
                for (int i = 0; i < count; i++) {
                    if (i == count - 1) {
                        start = i * 2000;
                        end = ProvisionCollector.this.collectList.size() - (i * 2000);
                    } else {
                        start = i * 2000;
                        end = (i + 1) * 2000;
                    }
                    JSONArray array = new JSONArray();
                    for (int k = start; k < end; k++) {
                        StringBuffer sb = new StringBuffer();
                        try {
                            ProcedureCollector.LogBean bean = (ProcedureCollector.LogBean) ProvisionCollector.this.collectList.get(k);
                            sb.append(bean.time);
                            sb.append(" ");
                            sb.append(ProcedureCollector.mainTid);
                            sb.append("/");
                            sb.append(bean.threadId);
                            sb.append(" ");
                            sb.append(" ");
                            sb.append("/TAG:");
                            sb.append(!TextUtils.isEmpty(bean.tag) ? bean.tag : "");
                            sb.append(" ");
                            sb.append(bean.content);
                            array.put((Object) sb.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    final CountDownLatch countDownLatch = new CountDownLatch(count);
                    a builder = a.y(this).x(MeshConstants.TRACE_ID_ADD_DEVICES).o(ProvisionCollector.this.collectList.size() == 1 ? "info" : "silly").t("LdsBleMesh").p(MeshConstants.TRACE_ID_ADD_DEVICES).r(array).b(type);
                    if (type == 1) {
                        ((b) builder.a()).k(new b.c() {
                            public void onSuccess() {
                                countDownLatch.countDown();
                            }

                            public void onFail() {
                                countDownLatch.countDown();
                            }
                        });
                        try {
                            countDownLatch.await(10, TimeUnit.SECONDS);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                    } else {
                        builder.a().b();
                    }
                }
                ProvisionCollector.this.clear();
            }
        });
    }
}
