package com.gocorona.model.dummy;


import com.gocorona.model.BaseApiResponse;

import java.util.List;

public class CommonResponse extends BaseApiResponse {
    List<CommonModel> data;

    public List<CommonModel> getData() {
        return data;
    }

    public void setData(List<CommonModel> data) {
        this.data = data;
    }
}
