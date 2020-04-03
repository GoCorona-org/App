package simplifii.framework.asyncmanager;


import android.text.TextUtils;

import simplifii.framework.Network.ClientURLConnection;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

import java.io.Serializable;
import java.util.HashMap;

public class HttpParamObject implements Serializable{

    private String url = "";
    private HashMap<String, String> postParams = new HashMap<>();
    private HashMap<String, String> headers = new HashMap<String, String>();
    private Class classType;
    private String method = ClientURLConnection.GET_METHOD;
    private String json = "";
    private String contentType = "application/x-www-form-urlencoded;charset=UTF-8";
    public int taskCode;

    public HttpParamObject() {
        String token = Preferences.getData(AppConstants.USER_TOKEN, "");
        if (!TextUtils.isEmpty(token))
            addHeader("Authorization", token);
    }

    public String getContentType() {
        return contentType;
    }

  /*  public void setHeader() {
//        addHeader("authToken", Preferences.getData(AppConstants.PREF_KEYS.USER_AUTH_TOKEN, ""));
    }*/

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMethod() {
        return method;
    }

    public void setPutMethod() {
        this.method = ClientURLConnection.PUT_METHOD;
    }

    public void setPostMethod() {
        this.method = ClientURLConnection.POST_METHOD;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, String> getPostParams() {
        return postParams;
    }

    public void setPostParams(HashMap<String, String> postParams) {
        this.postParams = postParams;
    }

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public void addParameter(String name, String value) {
        postParams.put(name, value);
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }


    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }


    public void setJSONContentType() {
        contentType = "application/json";
    }

    public void setPatchMethod() {
        this.method = ClientURLConnection.PATCH_METHOD;
    }

    public void setDeleteMethod() {
        this.method = ClientURLConnection.DELETE_METHOD;
    }
}
