package com.gocorona.model.response.login;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.JsonUtil;
import simplifii.framework.utility.Preferences;

public class UserData implements Serializable {
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("metadata")
    @Expose
    private MetaData metadata;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private long mobile;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("masterRole")
    @Expose
    private String masterRole;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("registrationType")
    @Expose
    private Object registrationType;
    @SerializedName("deviceToken")
    @Expose
    private Object deviceToken;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("address")
    @Expose
    private AddressData address;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("cityId")
    @Expose
    private Integer cityId;
    @SerializedName("appUser")
    @Expose
    private AppUser appUser;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("appKey")
    @Expose
    private String appKey="pep_user";
    @SerializedName("countryCode")
    @Expose
    private String countryCode="+91";
    @SerializedName("constituency")
    @Expose
    private String constituency;

    public UserData() {
    }

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }



    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public MetaData getMetadata() {
        return metadata;
    }

    public void setMetadata(MetaData metadata) {
        this.metadata = metadata;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMasterRole() {
        return masterRole;
    }

    public void setMasterRole(String masterRole) {
        this.masterRole = masterRole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(Object registrationType) {
        this.registrationType = registrationType;
    }

    public Object getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(Object deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public AddressData getAddress() {
        return address;
    }

    public void setAddress(AddressData address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void save() {
        Preferences.saveData(AppConstants.PREF_KEYS.USER_DATA, JsonUtil.toJson(this));
    }

    public static UserData getInstance() {
        String sessionJson = Preferences.getData(AppConstants.PREF_KEYS.USER_DATA, "");
        if (!TextUtils.isEmpty(sessionJson)) {
            return new Gson().fromJson(sessionJson, UserData.class);
        }
        return null;
    }
}
