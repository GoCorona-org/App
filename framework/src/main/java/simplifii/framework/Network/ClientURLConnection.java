/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplifii.framework.Network;


import simplifii.framework.utility.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * @author gauravKhanna
 */
public class ClientURLConnection {
    private static final int MAX_REDIRECT_ALLOWED = 1;
    private static final int CONNECT_TIMEOUT = 15000;
    private static final int READ_TIMEOUT = 30000;
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final int RESULT_OK = 200;
    public static final int REDIRECT = 302;
    private static final String urlNotSpecified = "URL is not specified!! Please Check !";
    public static final String URL_BASE_HTTPS = "https://";
    public static final String URL_BASE_HTTP = "http://";
    public static final String PUT_METHOD = "PUT";
    public static final String PATCH_METHOD = "PATCH";
    public static final String DELETE_METHOD = "DELETE";

    private StringBuilder parameters;
    protected String method;
    private String urlLink;

    protected HttpURLConnection httpURLConnection;
    protected HttpsURLConnection httpsURLConnection;
    private Map<String, String> keyValuePairToBeSend;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    protected String contentType = null;
    private Map<String, String> requestHeaders;

    private int redirectsHaveBeenMade = 0;

    private byte[] fileData = null;

    public ClientURLConnection(String url) {

        this.urlLink = url;
        init();
    }

    public ClientURLConnection(String url, String parameters) {
        this(url, parameters, GET_METHOD);

    }

    public ClientURLConnection(String url, String parameters, String method) {
        init();
        this.urlLink = url;
        this.method = method;
        this.parameters.append(parameters);

    }

    public ClientURLConnection() {
        // TODO Auto-generated constructor stub
        init();
    }

    public void setUrl(String url) {

        this.urlLink = url;

    }

    protected void init() {
        // contentType = "text/plain";
        contentType = "application/x-www-form-urlencoded;charset=UTF-8";

        parameters = new StringBuilder();
        //addkeyValuePairToBeSend("format", "json"); // as this is mandatory in
        // jobsearch api
        //addRequestHeader("Accept-Charset", "UTF-8");
        this.method = GET_METHOD;
    }

    public void setKeyValuePairMap(Map<String, String> keyValuePairMap) {
        this.keyValuePairToBeSend = keyValuePairMap;
    }

    public void setHttpMethod(String method) {

        this.method = method;

    }

    public void addkeyValuePairToBeSend(String key, String value) {
        parameters.append(key + "=" + value + "&");
    }

    /**
     * If your machine is proxy protected, then you have to give proxy
     * information here
     *
     * @param proxyName
     * @param proxyPort
     * @param userName
     * @param password
     */
    public void setProxyCredentials(String proxyName, String proxyPort,
                                    String userName, String password) {
        System.setProperty("http.proxyHost", proxyName);
        System.setProperty("http.proxyPort", proxyPort);
        Authenticator.setDefault(new myAuthenticator(userName, password));
    }

    /**
     * If you are accessing https based URL , then you must have its certificate
     * in your JRE Give path of that certificate and password here
     *
     * @param certificatePath
     * @param password
     */
    public void setCertificatePathAndPassword(String certificatePath,
                                              String password) {
        System.setProperty("javax.net.ssl.trustStore", certificatePath);
        System.setProperty("javax.net.ssl.trustStorePassword", password);
    }

    /**
     * If the resource you are accessing is password protected say, any servlet
     * or picture then only use this method
     *
     * @param userName
     * @param password
     */
    public void setUserNameAndPasswordForResource(String userName,
                                                  String password) {
        // this.authentication = userName + ":" + password;
        // Authenticator.setDefault(new myAuthenticator());
    }

    private NetworkResponse<InputStream> openConnectionWithURL() {
        NetworkResponse<InputStream> networkResponse = new NetworkResponse<InputStream>();
        try {
            URL url = new URL(urlLink);
            HttpURLConnection.setFollowRedirects(true);

            if (urlLink.startsWith(URL_BASE_HTTPS)) {

                httpsURLConnection = (HttpsURLConnection) url.openConnection();

                httpsURLConnection
                        .setHostnameVerifier(new MyHostNameVerifier());
                httpURLConnection = httpsURLConnection;
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);

            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            httpURLConnection.setRequestProperty("Content-Type", contentType);
            httpURLConnection.setInstanceFollowRedirects(true);

            if (requestHeaders != null) {
                for (String key : requestHeaders.keySet()) {
                    httpURLConnection.addRequestProperty(key,
                            requestHeaders.get(key));

                }

            }
            Logger.debug("opened connection with", urlLink);
            networkResponse.setSuccess(true);
            return networkResponse;
        } catch (MalformedURLException ex) {
            networkResponse.setReason(ex.getMessage());
        } catch (IOException ex) {
            networkResponse.setReason(ex.getMessage());
        } catch (Exception ex) {
            networkResponse.setReason(ex.getMessage());
        }
        networkResponse.setURLValid(false);
        Logger.debug("opened connection failed", urlLink);
        networkResponse.setSuccess(false);
        return networkResponse;
    }

    public void setRequestDataType(String type) {
        contentType = type;
    }

    public void addRequestHeader(String key, String value) {
        if (requestHeaders == null) {
            requestHeaders = new HashMap<String, String>();
        }
        requestHeaders.put(key, value);
    }

    public void addDataToSend(String data) {

        parameters.append(data);
    }

    // private String getEncodedParams() {
    // // use url encoder here, but it is encodeing keys and = as well
    // return parameters.toString().replaceAll("\\s+", "%20")
    // .replace("+", "%2B");
    // }

    protected void initiatePostMethodRequest(String paramToSend)
            throws IOException {
        httpURLConnection.setRequestMethod(POST_METHOD);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        DataOutputStream dataOutputStream = new DataOutputStream(
                httpURLConnection.getOutputStream());
        sendDataInPostMethod(dataOutputStream, paramToSend);

        dataOutputStream.flush();
        dataOutputStream.close();
    }

    protected void sendDataInPostMethod(DataOutputStream dataOutputStream,
                                        String paramToSend) throws IOException {
        dataOutputStream.writeBytes(paramToSend);
    }

    public NetworkResponse<InputStream> getInputStream() {
        NetworkResponse<InputStream> networkResponse = null;
        if (urlLink != null) {
            try {

                // String paramToSend = getEncodedParams();
                if(GET_METHOD.equals(this.method)){
                    urlLink += ("?" + parameters.toString());
                    networkResponse = openConnectionWithURL();
                } else {
                    networkResponse = openConnectionWithURL();
                    initiateRequest(parameters.toString());
                }
//                if (POST_METHOD.equals(this.method)) {
//                    networkResponse = openConnectionWithURL();
//                    if (networkResponse.isSuccess()) {
//                        initiatePostMethodRequest(parameters.toString());
//                    }
//
//                } else {
//                    urlLink += ("?" + parameters.toString());
//                    networkResponse = openConnectionWithURL();
//                }
                if (networkResponse.isSuccess()) {

                    // HttpURLConnection.setFollowRedirects(true);
                    httpURLConnection.connect();
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == REDIRECT
                            && this.redirectsHaveBeenMade++ < MAX_REDIRECT_ALLOWED) {
                        String location = httpURLConnection
                                .getHeaderField("Location");
                        if (location != null) {
                            this.urlLink = location + "&format=json";
                            this.parameters = new StringBuilder();
                            Logger.error("client url connection redirecting",
                                    location);
                            return getInputStream();

                        }
                    }
                    Logger.debug("ResponseCode FromServer: ", responseCode + "");
                    Logger.debug("Message From Server : ",
                            httpURLConnection.getResponseMessage());

                    networkResponse.setResponseCode(responseCode);
                    Logger.debug("Message From Server : ",
                            httpURLConnection.getResponseMessage());

                    if (responseCode == RESULT_OK) {
                        networkResponse.setSuccess(true);

                        networkResponse.setData(httpURLConnection
                                .getInputStream());
                        return networkResponse;
                    }
                }

            } catch (SocketTimeoutException ex) {
                networkResponse.setTimeout(true);
                networkResponse.setReason(ex.getMessage());

            } catch (UnknownHostException ex) {
                networkResponse.setConnectedToNetwork(false);
                networkResponse.setReason(ex.getMessage());
            } catch (IOException ex) {
                networkResponse.setReason(ex.getMessage());
            } catch (Exception ex) {
                networkResponse.setReason(ex.getMessage());
            }

        } else {
            networkResponse.setReason(urlNotSpecified);

        }

        networkResponse.setSuccess(false);

        return networkResponse;
    }

    private void initiateRequest(String paramToSend) throws IOException {
        httpURLConnection.setRequestMethod(this.method);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        DataOutputStream dataOutputStream = new DataOutputStream(
                httpURLConnection.getOutputStream());
        sendDataInPostMethod(dataOutputStream, paramToSend);

        dataOutputStream.flush();
        dataOutputStream.close();
    }

    public NetworkResponse<String> getData() {
        NetworkResponse<String> stringNetworkResponse = new NetworkResponse<String>();
        if (parameters.toString() == null || parameters.toString().equals("")) {
            stringNetworkResponse.setUrlLInk(urlLink);
            Logger.debug("Null OR Empty", "" + urlLink);

        } else {
            Logger.debug("Not Null OR nonEmpty", "" + urlLink);
            stringNetworkResponse.setUrlLInk(urlLink
                    + ("?" + parameters.toString()));
        }

        long startTime = System.currentTimeMillis();
        NetworkResponse<InputStream> networkResponse = getInputStream();

        if (networkResponse.isSuccess()) {
            stringNetworkResponse.setResponseCode(networkResponse
                    .getResponseCode());
            InputStream is = null;
            try {
                is = networkResponse.getData();

                Logger.error("got input stream after",
                        "" + (System.currentTimeMillis() - startTime));

                BufferedReader buReader = new BufferedReader(
                        new InputStreamReader(is, "utf-8"), 50000);
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = buReader.readLine()) != null) {
                    Logger.info("Line", line);
                    response.append(line);
                }

                stringNetworkResponse.setSuccess(true).setData(
                        response.toString());

                Logger.warn("network call time taken",
                        "" + ((System.currentTimeMillis() - startTime)));
                return stringNetworkResponse;
            } catch (IOException e) {
                stringNetworkResponse.setReason(e.getMessage());
            } catch (Exception ex) {
                stringNetworkResponse.setReason(ex.getMessage());
            } finally {
                if (is != null)
                    try {
                        is.close();
                    } catch (IOException e) {
                        stringNetworkResponse.setReason(e.getMessage());
                    }
            }

        } else {
            stringNetworkResponse.setConnectedToNetwork(networkResponse
                    .isConnectedToNetwork());
            stringNetworkResponse.setReason(networkResponse.getReason());
            stringNetworkResponse.setTimeout(networkResponse.isTimeout());
            stringNetworkResponse.setURLValid(networkResponse.isURLValid());
        }
        stringNetworkResponse.setSuccess(false);
        return stringNetworkResponse;
    }

    private static class MyHostNameVerifier implements HostnameVerifier {

        public boolean verify(String string, SSLSession ssls) {
            return true;
        }
    }

}
