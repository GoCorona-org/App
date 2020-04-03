package simplifii.framework.asyncmanager;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import simplifii.framework.Network.ClientURLConnection;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.utility.JsonUtil;

/**
 * Created by robin on 9/27/16.
 */

public class OKHttpService extends GenericService {
    private static final String TAG = "HttpRestService";

    @Override
    public Object getData(Object... params) throws JSONException, SQLException, NullPointerException, RestException, ClassCastException, IOException  {
        if (params != null && params.length > 0) {
            HttpParamObject param = (HttpParamObject) params[0];
            OkHttpClient client = new OkHttpClient();

            final MediaType mediaType = MediaType.parse(param.getContentType());

            if ("GET".equalsIgnoreCase(param.getMethod())) {
                HttpUrl.Builder urlBuilder = HttpUrl.parse(param.getUrl()).newBuilder();
                for (Map.Entry<String, String> entry : param.getPostParams().entrySet()) {
                    urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
                }
                String url = urlBuilder.build().toString();

                param.setUrl(url);
            }
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder()
                    .url(param.getUrl());

            if (ClientURLConnection.GET_METHOD.equalsIgnoreCase(param.getMethod())) {
                builder = builder.get();
            } else if (ClientURLConnection.POST_METHOD.equalsIgnoreCase(param.getMethod())) {
                if (TextUtils.isEmpty(param.getJson())) {
                    // It will contain post param string
                    param.setJson(getPostParamsString(param));
                }
                final RequestBody body = RequestBody.create(mediaType, param.getJson());
                builder = builder.post(body);
            } else if (ClientURLConnection.PUT_METHOD.equalsIgnoreCase(param.getMethod())) {
                final RequestBody body = RequestBody.create(mediaType, param.getJson());
                builder = builder.put(body);
            }else if (ClientURLConnection.DELETE_METHOD.equalsIgnoreCase(param.getMethod())) {
                final RequestBody body = RequestBody.create(mediaType, param.getJson());
                builder = builder.delete(body);
            } else if(ClientURLConnection.PATCH_METHOD.equals(param.getMethod())){
                if (TextUtils.isEmpty(param.getJson())) {
                    // It will contain post param string
                    param.setJson(getPostParamsString(param));
                }
                final RequestBody body = RequestBody.create(mediaType, param.getJson());
                builder = builder.patch(body);
            }


            // Add Headers
            for (Map.Entry<String, String> pair : param.getHeaders().entrySet()) {
                builder.addHeader(pair.getKey(), pair.getValue());
            }

//            builder.(60, TimeUnit.SECONDS)
//                    .readTimeout(60, TimeUnit.SECONDS)
//                    .build();

            client = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)

                    .build();
            /*.connectionSpecs(Arrays.asList(
                    ConnectionSpec.CLEARTEXT,
                    ConnectionSpec.MODERN_TLS,
                    ConnectionSpec.COMPATIBLE_TLS))*/

            final Response response = client.newCall(builder.build()).execute();

            if (response.isSuccessful()) {
                return parseJson(response.body().string(), param);
            } else {
                throw new RestException(response.code(), getResponseReason(response.body().string()));
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private String getResponseReason(String body) {
        try {
            JSONObject obj = new JSONObject(body);
            if (obj.has("message")) {
                return obj.getString("message");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Please hang on! We are facing some technical issues";
    }

    protected String getPostParamsString(HttpParamObject obj) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : obj.getPostParams().entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    protected Object parseJson(String jsonString, HttpParamObject paramObject) {

        Log.d(TAG, "Request url:" + paramObject.getUrl());
        Log.d(TAG, "JSON Response:" + jsonString);
        if (null == paramObject.getClassType()) {
            return jsonString;
        }

        // For custom parsing
        Class classType = paramObject.getClassType();
        Method m = null;

        Object o = null;
        try {
            m = classType.getDeclaredMethod("parseJson", String.class);
            o = m.invoke(null, jsonString);
            return o;
        } catch (Exception e) {
//            e.printStackTrace();
            Log.e(TAG, "CustomParser not found");
        }

        // Parse with Gson
        return JsonUtil.parseJson(jsonString, paramObject.getClassType());

    }
}
