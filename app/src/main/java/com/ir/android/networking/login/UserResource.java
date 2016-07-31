package com.ir.android.networking.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ir.android.networking.basicimplementation.WLResource;
import com.ir.android.networking.basicimplementation.exceptions.InvocationFailedException;
import com.ir.android.networking.login.Models.ProfileAttribute;
import com.ir.android.networking.login.Models.UserGroup;
import com.worklight.utils.Base64;
import com.worklight.wlclient.api.WLResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Henawey on 7/11/16.
 */
public class UserResource extends WLResource {

    private final static String SHARED_PREFERNCES_NAME = "UserResource";
    private final static String UID_SHARED_PREFERNCES_NAME = "uid";
    private final static String BASE64_SHARED_PREFERNCES_NAME = "Base64";
    private final static String LTPA_TOKEN2_SHARED_PREFERNCES_NAME = "LtpaToken2";
    private final static String JSESSION_ID_SHARED_PREFERNCES_NAME = "com.ibm.ioc.sessionid";

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
        return "LoginServiceAdapter";
    }

    @Override
    public String getProcedureName() {
        return "authenticateUser";
    }

    public static boolean isLoggedIn(Context context){
        String currentUserID=getCurrentUserID(context);
        return (currentUserID==null || currentUserID.isEmpty());
    }

    public static String getCurrentUserID(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERNCES_NAME, context.MODE_PRIVATE);
        String uid = prefs.getString(UID_SHARED_PREFERNCES_NAME, null);

        return uid;
    }

    public static String getBase64(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERNCES_NAME, context.MODE_PRIVATE);
        String base64 = prefs.getString(BASE64_SHARED_PREFERNCES_NAME, null);

        return base64;
    }

    public static String getLtpaToken2(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERNCES_NAME, context.MODE_PRIVATE);
        String ltpaToken2 = prefs.getString(LTPA_TOKEN2_SHARED_PREFERNCES_NAME, null);

        return ltpaToken2;
    }

    public static String getJSessionID(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERNCES_NAME, context.MODE_PRIVATE);
        String jsessionID = prefs.getString(JSESSION_ID_SHARED_PREFERNCES_NAME, null);

        return jsessionID;
    }

    private static void clearCache(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERNCES_NAME, context.MODE_PRIVATE).edit();
        editor.remove(LTPA_TOKEN2_SHARED_PREFERNCES_NAME);
        editor.remove(JSESSION_ID_SHARED_PREFERNCES_NAME);
        editor.remove(UID_SHARED_PREFERNCES_NAME);
        editor.apply();
    }

    public static void logout(Context context) throws LogoutFailedException {

        try {
            final WLResource wlResource = new WLResource(context) {
                @Override
                public String getAdapterName() {
                    return "LoginServiceAdapter";
                }

                @Override
                public String getProcedureName() {
                    return "logout";
                }

                @Override
                public void invoke() throws InvocationFailedException {
                    try {
                        WLResponse response=process();
                        if (isSuccessed(response)){

                            clearCache(getContext());
                        }else{
                            throw new LogoutFailedException(response.getResponseText());
                        }
                    } catch (Exception e) {
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

    private void saveJSessionID(WLResponse response) throws JSONException {
        SharedPreferences.Editor editor = getContext().getSharedPreferences(SHARED_PREFERNCES_NAME, Context.MODE_PRIVATE).edit();
        JSONObject jsonObject = response.getResponseJSON();

        if (jsonObject.has("responseHeaders")) {
            JSONObject responseHeaders = jsonObject.getJSONObject("responseHeaders");
            if (responseHeaders.has(JSESSION_ID_SHARED_PREFERNCES_NAME)){
                String jsessionID = responseHeaders.getString(JSESSION_ID_SHARED_PREFERNCES_NAME);
                editor.putString(JSESSION_ID_SHARED_PREFERNCES_NAME, jsessionID);
                editor.commit();
            }
        }
    }
    private void setCookies(WLResponse response) throws JSONException {
        SharedPreferences.Editor editor = getContext().getSharedPreferences(SHARED_PREFERNCES_NAME, Context.MODE_PRIVATE).edit();


        JSONObject jsonObject = response.getResponseJSON();

        if (jsonObject.has("responseHeaders")) {
            JSONObject responseHeaders = jsonObject.getJSONObject("responseHeaders");
            if (responseHeaders.has("Set-Cookie")) {
                String cookies = responseHeaders.getString("Set-Cookie");

                HashMap<String,String> parsedCookies=parseCookies(cookies);

                editor.putString(LTPA_TOKEN2_SHARED_PREFERNCES_NAME, parsedCookies.get(LTPA_TOKEN2_SHARED_PREFERNCES_NAME));
                editor.commit();
            }
        }
    }
    private HashMap<String,String> parseCookies(String setCookieHeaders) {
        HashMap<String,String> parsedCookies=new HashMap<>();

        String[] splitCookies = setCookieHeaders.split(";");
        for (String cookie : splitCookies) {

            String[] cookieParts = cookie.split("=");
            if (cookieParts.length == 2) {
                parsedCookies.put(cookieParts[0],cookieParts[1]);
            }
        }

        return parsedCookies;
    }

    @Override
    public void invoke() throws LoginFailedException{
        try {
            String authorizationInput = generateBase64();

            addParameter(authorizationInput);
            WLResponse response=process();

            if(isSuccessed(response)){

                saveJSessionID(response);

                setCookies(response);

                ObjectMapper objectMapper = new ObjectMapper();

                JSONObject responseJson=response.getResponseJSON();
                JSONArray dataArray=responseJson.getJSONArray("array");
                JSONObject userIdentity=dataArray.getJSONObject(0);

                String uid = userIdentity.getString("uid");

                SharedPreferences.Editor editor=getContext().getSharedPreferences(SHARED_PREFERNCES_NAME,Context.MODE_PRIVATE).edit();
                editor.putString(UID_SHARED_PREFERNCES_NAME, uid);
                editor.commit();

                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readerForUpdating(this).readValue(userIdentity.toString());
            }else{
                throw new InvocationFailedException(response.getResponseText());
            }

        }catch (Exception e){

            LoginFailedException loginFailedException=new LoginFailedException(e);

            try {
                logout(getContext());
            }catch (Exception e1){
                loginFailedException =new LoginFailedException(loginFailedException);
            }
            throw new LoginFailedException(loginFailedException);
        }
    }

    @NonNull
    private String generateBase64() throws UnsupportedEncodingException {
        String authorizationInput =
                Base64.encode((username + ":" + password).getBytes(), "UTF-8");
        SharedPreferences.Editor editor=getContext().getSharedPreferences(SHARED_PREFERNCES_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(BASE64_SHARED_PREFERNCES_NAME, authorizationInput);
        editor.commit();
        return authorizationInput;
    }
}
