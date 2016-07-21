package com.ir.android.networking.login;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ir.android.networking.basicimplementation.WLResource;
import com.ir.android.networking.basicimplementation.exceptions.InvocationFailedException;
import com.ir.android.networking.basicimplementation.exceptions.ProcessingFailedException;
import com.ir.android.networking.login.Models.ProfileAttribute;
import com.ir.android.networking.login.Models.UserGroup;
import com.worklight.utils.Base64;
import com.worklight.wlclient.api.WLResponse;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Henawey on 7/11/16.
 */
public class UserResource extends WLResource {

    private String username;
    private String password;

    private String userId;
    private String uid;
    private String preferredLanguage;
    private boolean userSpecifiedLanguage;
    private String timeZone;
    private Integer notificationsDisplayLimit;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String email;
    private String mobile;
    private String telephoneNumber;
    private String commonName;
    private String jpegPhoto;

    @JsonProperty("customProfileAttributes")
    private ArrayList<ProfileAttribute> profileAttributes;

    @JsonProperty("groups")
    private ArrayList<UserGroup> groups;

    private UserResource(Context context){
        super(context);
    }

    public UserResource(String username, String password,Context context) {
        this(context);
        this.username=username;
        this.password=password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public boolean isUserSpecifiedLanguage() {
        return userSpecifiedLanguage;
    }

    public void setUserSpecifiedLanguage(boolean userSpecifiedLanguage) {
        this.userSpecifiedLanguage = userSpecifiedLanguage;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Integer getNotificationsDisplayLimit() {
        return notificationsDisplayLimit;
    }

    public void setNotificationsDisplayLimit(Integer notificationsDisplayLimit) {
        this.notificationsDisplayLimit = notificationsDisplayLimit;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getJpegPhoto() {
        return jpegPhoto;
    }

    public void setJpegPhoto(String jpegPhoto) {
        this.jpegPhoto = jpegPhoto;
    }

    public ArrayList<ProfileAttribute> getProfileAttributes() {
        return profileAttributes;
    }

    public void setProfileAttributes(ArrayList<ProfileAttribute> profileAttributes) {
        this.profileAttributes = profileAttributes;
    }

    public ArrayList<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<UserGroup> groups) {
        this.groups = groups;
    }

    @Override
    public String getAdapterName() {
        return "Authentication";
    }

    @Override
    public String getProcedureName() {
        return "authenticateUser";
    }

    public static boolean isLoggedIn(Context context){
        String ltpaToken2=getLtpaToken2(context);
        return (ltpaToken2==null || ltpaToken2.isEmpty());
    }

    public static void logout(Context context) throws LogoutFailedException {

        try {
            final WLResource wlResource = new WLResource(context) {
                @Override
                public String getAdapterName() {
                    return "Authentication";
                }

                @Override
                public String getProcedureName() {
                    return "logoutUser";
                }

                @Override
                public void invoke() throws InvocationFailedException {
                    try {
                        WLResponse response=process();
                        if (response.getStatus()!=200){
                            throw new LogoutFailedException(response.getResponseText());
                        }
                    } catch (ProcessingFailedException e) {
                        throw new InvocationFailedException(e);
                    }
                }
            };
            wlResource.invoke();
            clearCache(context);
        }catch (InvocationFailedException e){
            throw new LogoutFailedException(e);
        }
    }

    @Override
    public void invoke() throws LoginFailedException{
        try {
            String authorizationInput =
                    Base64.encode((username + ":" + password).getBytes(), "UTF-8");
            addParameter(authorizationInput);
            WLResponse response=process();

            if(isSuccessed(response)){
                ObjectMapper objectMapper = new ObjectMapper();

                JSONObject responseJson=response.getResponseJSON();
                JSONObject userIdentity=responseJson.getJSONObject("userIdentity");
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readerForUpdating(this).readValue(userIdentity.toString());
            }else{
                throw new InvocationFailedException(response.getResponseText());
            }

        }catch (Exception e){
            throw new LoginFailedException(e);
        }
    }
}
