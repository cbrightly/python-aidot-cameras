package zendesk.faye.internal;

import com.meituan.robust.BuildConfig;
import com.meituan.robust.Constants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import meshsdk.ConfigUtil;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zendesk.faye.b;

/* compiled from: Bayeux.kt */
public final class a {
    @NotNull
    public static final a a = new a();
    @NotNull
    private static final List<String> b = q.j("long-polling", "callback-polling", "iframe", UpgradeRequest.WEBSOCKET);

    private a() {
    }

    @NotNull
    public final String c(@NotNull List<String> supportedConnTypes, @NotNull b bayeuxOptionalFields) {
        k.e(supportedConnTypes, "supportedConnTypes");
        k.e(bayeuxOptionalFields, "bayeuxOptionalFields");
        try {
            JSONArray connTypes = new JSONArray();
            JSONArray array = connTypes;
            Iterable<Object> $this$map$iv = supportedConnTypes.isEmpty() ^ true ? supportedConnTypes : null;
            if ($this$map$iv == null) {
                $this$map$iv = b;
            }
            Collection destination$iv$iv = new ArrayList(r.r($this$map$iv, 10));
            for (Object p0 : $this$map$iv) {
                destination$iv$iv.add(array.put(p0));
            }
            JSONObject json = new JSONObject().put("channel", (Object) "/meta/handshake").put("minimumVersion", (Object) "1.0beta").put(ConfigUtil.VERSION_FILE, (Object) BuildConfig.VERSION_NAME).put("supportedConnectionTypes", (Object) connTypes);
            k.d(json, "json");
            g(json, "ext", bayeuxOptionalFields.a());
            json.put("id", (Object) bayeuxOptionalFields.b());
            String jSONObject = json.toString();
            k.d(jSONObject, "{\n            val connTy…json.toString()\n        }");
            return jSONObject;
        } catch (JSONException e) {
            zendesk.logger.a.h("Bayeux", "handshake - malformed json", new Object[0]);
            return "";
        }
    }

    @NotNull
    public final String a(@NotNull String clientId, @NotNull b bayeuxOptionalFields) {
        k.e(clientId, "clientId");
        k.e(bayeuxOptionalFields, "bayeuxOptionalFields");
        try {
            JSONObject json = new JSONObject().put("channel", (Object) "/meta/connect").put("clientId", (Object) clientId).put("connectionType", (Object) UpgradeRequest.WEBSOCKET);
            k.d(json, "json");
            g(json, "ext", bayeuxOptionalFields.a());
            json.put("id", (Object) bayeuxOptionalFields.b());
            String jSONObject = json.toString();
            k.d(jSONObject, "{\n            val json =…json.toString()\n        }");
            return jSONObject;
        } catch (JSONException e) {
            zendesk.logger.a.h("Bayeux", "connect - malformed json", new Object[0]);
            return "";
        }
    }

    @NotNull
    public final String b(@NotNull String clientId, @NotNull b bayeuxOptionalFields) {
        k.e(clientId, "clientId");
        k.e(bayeuxOptionalFields, "bayeuxOptionalFields");
        try {
            JSONObject json = new JSONObject().put("channel", (Object) "/meta/disconnect").put("clientId", (Object) clientId);
            k.d(json, "json");
            g(json, "ext", bayeuxOptionalFields.a());
            json.put("id", (Object) bayeuxOptionalFields.b());
            String jSONObject = json.toString();
            k.d(jSONObject, "{\n            val json =…json.toString()\n        }");
            return jSONObject;
        } catch (JSONException e) {
            zendesk.logger.a.h("Bayeux", "disconnect - malformed json", new Object[0]);
            return "";
        }
    }

    @NotNull
    public final String h(@NotNull String clientId, @NotNull String channel, @NotNull b bayeuxOptionalFields) {
        k.e(clientId, "clientId");
        k.e(channel, "channel");
        k.e(bayeuxOptionalFields, "bayeuxOptionalFields");
        try {
            JSONObject json = new JSONObject().put("channel", (Object) "/meta/subscribe").put("clientId", (Object) clientId).put("subscription", (Object) channel);
            k.d(json, "json");
            g(json, "ext", bayeuxOptionalFields.a());
            json.put("id", (Object) bayeuxOptionalFields.b());
            String jSONObject = json.toString();
            k.d(jSONObject, "{\n            val json =…json.toString()\n        }");
            return jSONObject;
        } catch (JSONException e) {
            zendesk.logger.a.h("Bayeux", "subscribe - malformed json", new Object[0]);
            return "";
        }
    }

    @NotNull
    public final String i(@NotNull String clientId, @NotNull String channel, @NotNull b bayeuxOptionalFields) {
        k.e(clientId, "clientId");
        k.e(channel, "channel");
        k.e(bayeuxOptionalFields, "bayeuxOptionalFields");
        try {
            JSONObject json = new JSONObject().put("channel", (Object) "/meta/unsubscribe").put("clientId", (Object) clientId).put("subscription", (Object) channel);
            k.d(json, "json");
            g(json, "ext", bayeuxOptionalFields.a());
            json.put("id", (Object) bayeuxOptionalFields.b());
            String jSONObject = json.toString();
            k.d(jSONObject, "{\n            val json =…json.toString()\n        }");
            return jSONObject;
        } catch (JSONException e) {
            zendesk.logger.a.h("Bayeux", "unsubscribe - malformed json", new Object[0]);
            return "";
        }
    }

    @NotNull
    public final String f(@NotNull String channel, @NotNull String data, @Nullable String clientId, @NotNull b bayeuxOptionalFields) {
        k.e(channel, "channel");
        k.e(data, "data");
        k.e(bayeuxOptionalFields, "bayeuxOptionalFields");
        try {
            JSONObject json = new JSONObject().put("channel", (Object) channel);
            if (clientId != null) {
                json.put("clientId", (Object) clientId);
            }
            k.d(json, "json");
            g(json, "data", data);
            g(json, "ext", bayeuxOptionalFields.a());
            json.put("id", (Object) bayeuxOptionalFields.b());
            String jSONObject = json.toString();
            k.d(jSONObject, "{\n            val json =…json.toString()\n        }");
            return jSONObject;
        } catch (JSONException e) {
            zendesk.logger.a.h("Bayeux", "publish - malformed json", new Object[0]);
            return "";
        }
    }

    private final void g(JSONObject jsonObject, String fieldName, String fieldValue) {
        JSONObject it;
        JSONArray it2;
        if (fieldValue == null) {
            zendesk.logger.a.h("Bayeux", "putField - value for field with name " + fieldName + " was null, skipping", new Object[0]);
            return;
        }
        JSONObject it3 = e(fieldValue);
        if (it3 == null) {
            it = null;
        } else {
            it = jsonObject.put(fieldName, (Object) it3);
        }
        if (it == null && (it2 = d(fieldValue)) != null) {
            jsonObject.put(fieldName, (Object) it2);
        }
    }

    private final JSONObject e(String value) {
        if (w.N(value, "{", false, 2, (Object) null)) {
            try {
                return new JSONObject(value);
            } catch (JSONException e) {
                zendesk.logger.a.h("Bayeux", k.l("isJsonObject - Invalid Json Object received: ", value), new Object[0]);
                return null;
            }
        } else {
            zendesk.logger.a.h("Bayeux", k.l("isJsonObject - Received value is not a Json Object: ", value), new Object[0]);
            return null;
        }
    }

    private final JSONArray d(String value) {
        if (w.N(value, Constants.ARRAY_TYPE, false, 2, (Object) null)) {
            try {
                return new JSONArray(value);
            } catch (JSONException e) {
                zendesk.logger.a.h("Bayeux", k.l("isJsonArray - Invalid Json Array received: ", value), new Object[0]);
                return null;
            }
        } else {
            zendesk.logger.a.h("Bayeux", k.l("isJsonArray - Received value is not a Json Array: ", value), new Object[0]);
            return null;
        }
    }
}
