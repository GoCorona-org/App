package com.gocorona.model.dummy;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.gocorona.model.BaseAdapterModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import simplifii.framework.utility.AppConstants;

public class CommonModel extends BaseAdapterModel implements Comparable, Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("dailougeType")
    @Expose
    private int dailougeType;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("isdCode")
    @Expose
    private Integer isd;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("countryCode")
    @Expose
    private String countryCode;

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @SerializedName("dial_code")
    @Expose
    private String dial_code;

    @SerializedName("metadata")
    @Expose
    private Object metadata;

    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("constituency_name")
    @Expose
    private String constituency_name;
    @SerializedName("political_party")
    @Expose
    private String political_party;
    @SerializedName("votes")
    @Expose
    private String votes;

    public CommonModel() {
    }

    protected CommonModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        dailougeType = in.readInt();
        code = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            isd = null;
        } else {
            isd = in.readInt();
        }
        createdAt = in.readString();
        countryCode = in.readString();
        updatedAt = in.readString();
        dial_code = in.readString();
        pincode = in.readString();
        constituency_name = in.readString();
        political_party = in.readString();
        votes = in.readString();
    }

    public static final Creator<CommonModel> CREATOR = new Creator<CommonModel>() {
        @Override
        public CommonModel createFromParcel(Parcel in) {
            return new CommonModel(in);
        }

        @Override
        public CommonModel[] newArray(int size) {
            return new CommonModel[size];
        }
    };

    public String getConstituency_name() {
        return constituency_name;
    }

    public void setConstituency_name(String constituency_name) {
        this.constituency_name = constituency_name;
    }

    public String getPolitical_party() {
        return political_party;
    }

    public void setPolitical_party(String political_party) {
        this.political_party = political_party;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getIsd() {
        return isd;
    }

    public void setIsd(Integer isd) {
        this.isd = isd;
    }

    public int getDailougeType() {
        return dailougeType;
    }

    public void setDailougeType(int dailougeType) {
        this.dailougeType = dailougeType;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDial_code() {
        return dial_code;
    }

    public void setDial_code(String dial_code) {
        this.dial_code = dial_code;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return name.compareTo(((CommonModel) o).getName());
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public int getViewType() {
        return AppConstants.VIEW_CODES.STATE_IN;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeInt(dailougeType);
        parcel.writeString(code);
        parcel.writeString(name);
        if (isd == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(isd);
        }
        parcel.writeString(createdAt);
        parcel.writeString(countryCode);
        parcel.writeString(updatedAt);
        parcel.writeString(dial_code);
        parcel.writeString(pincode);
        parcel.writeString(constituency_name);
        parcel.writeString(political_party);
        parcel.writeString(votes);
    }
}
