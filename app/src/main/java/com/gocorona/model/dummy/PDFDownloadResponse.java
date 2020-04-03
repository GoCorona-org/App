package com.gocorona.model.dummy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PDFDownloadResponse  {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("Response")
    @Expose
    private String Response;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
