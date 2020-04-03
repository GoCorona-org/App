package simplifii.framework.Network;

import android.util.Log;


import simplifii.framework.utility.Logger;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class RestClient {

    // constructor
    public RestClient() {

    }

    private static InputStream OpenHttpConnection(String urlString)
            throws IOException {

        Log.d("palval", "OpenHttpConnection");
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");

        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
//			httpConn.setAllowUserInteraction(false);
//			httpConn.setInstanceFollowRedirects(true);
//			httpConn.setConnectTimeout(60000);


            httpConn.setRequestMethod("GET");

            httpConn.setRequestProperty("auth_token", "gajdjad");

            httpConn.connect();

            response = httpConn.getResponseCode();
            Logger.error("Connected to Stream", "YES");
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }

        } catch (Exception ex) {
            Logger.error("Internet Connecting Exception", "Unable To Connect");
        }
        return in;
    }

    public static String getJSONFromUrl(String url) {

        InputStream in = null;
        JSONObject jObj = null;
        String json = "";
        // Making HTTP request
        try {

            in = OpenHttpConnection(url);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    in, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            in.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        return json;

    }
}