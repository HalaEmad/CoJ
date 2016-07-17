package com.ir.android.networking.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ir.android.networking.basicimplementation.WLResource;
import com.ir.android.networking.basicimplementation.exceptions.SavingFailedException;
import com.worklight.utils.Base64;
import com.worklight.wlclient.api.WLResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Henawey on 7/11/16.
 */
public class UserResource extends WLResource {

    private String username;
    private String password;

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

    public static void logout(Context context){
        clearCache(context);
    }

    @Override
    public void save() throws SavingFailedException {

    }

    @Override
    public void retrieve() throws LoginFailedException{
        try {
            String authorizationInput =
                    Base64.encode((username + ":" + password).getBytes(), "UTF-8");
            addParameter(authorizationInput);
            WLResponse response=process();
            if(response.getStatus()== 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                JSONArray jsonArray=new JSONArray(response.getResponseText());
                objectMapper.readerForUpdating(this).readValue(jsonArray.getString(0));
            }else{
                throw new LoginFailedException(response.getResponseText());
            }

        }catch (Exception e){
            //TODO:remove stub

            if((username.equals("marius") && password.equals("ibmioc16")) || (username.equals("wpsadmin") && password.equals("ChangeM3N0Wx") )) {
                try {
                    InputStream inputStream = getContext().getAssets().open("Login-Success.octet-stream");

                    BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder string = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        string.append(line).append('\n');
                    }


                    //read from stub
                    ObjectMapper objectMapper = new ObjectMapper();
                    JSONObject jsonObject = new JSONObject(string.toString());
                    String array = jsonObject.getString("text");
                    JSONArray jsonArray = new JSONArray(array);


                    objectMapper.readerForUpdating(this).readValue(jsonArray.getString(0));

                    String SHARED_PREFERNCES_NAME = "WLResource";
                    String LTPA_TOKEN2_SHARED_PREFERNCES_NAME = "ltpaToken2";

                    SharedPreferences.Editor editor = getContext().getSharedPreferences(SHARED_PREFERNCES_NAME, Context.MODE_PRIVATE).edit();
                    editor.putString(LTPA_TOKEN2_SHARED_PREFERNCES_NAME, "xi++41ykzLsTDQIG5qvrx6IOsH+MgjS0gtfw7u0mgvbFx3glcbAQN9+Vf7geCacx0f5B9qL0v0Hh5e1Xrp43VH5cyVJEtZjQhSpIEZmZUzQRcl0mCy46TSJFsvsEAoB8ohtRuMNzw2L2dMI/TZeWx6K1i4cycRgZbPE4isTkXw0RzUmWeOcdqEXxeo8134FlS5gLeS3SBjNdD+KJjzGI44a8f3pR8l5gq6ullO/vPysjxkbGWDd55IhbzUaD65L47MWTk0FO3fqsh+ebo5v0brLJHw2RbqdOVUs5IKxGQQ+y8KbYQ+n7Dz1HqpNuIea2l7z2JFPiakMR1oZFD2JF2EvBAnDWeLrVwGK694atbhCc8zZGVA/3DbUQKhyTK1rFNOZ9BV9HMtm5/ajHoVYur65EhzaqVQ5E6Lq2QA4iqaNefL+JFs+vove12ZR0H9UmN2p1e8InJAxZkg/vQPhCqPt6bxFERuajmDez9PDFkEd8Xh+0mpCnE9hTOG3Wts1OBJxBhbb1p5BBLO97R/zynymoR2iO5WYlPiPJ9Y2TYY9Rl26TKMTkLNebpJcI2RXOh+0lmoWNU35tymlugI8eYGqxAavtDf1mJI+PA/lqNQY5NP6AIrj4F0wDXu5Cvxe+XowbLbDqipE8ZnlXGOBQbABHqnZ35vJPEEo4Zj4/V2YWbyJyGWD+kdl6543WhCHQ");
                    editor.commit();
                    return;
                } catch (Exception e1) {
                    /*it's stub please don't handle this*/
                    e1.printStackTrace();
                }
            }else{
                try {
                    InputStream inputStream = getContext().getAssets().open("Login-Failure.octet-stream");

                    BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder string = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        string.append(line).append('\n');
                    }


                    //read from stub
                    ObjectMapper objectMapper = new ObjectMapper();
                    JSONObject jsonObject = new JSONObject(string.toString());
                    String array = jsonObject.getString("text");
                    JSONArray jsonArray = new JSONArray(array);


                    objectMapper.readerForUpdating(this).readValue(jsonArray.getString(0));

                    return;
                } catch (Exception e1) {
                    /*it's stub please don't handle this*/
                    e1.printStackTrace();
                }
            }
    //Stub end
            throw new LoginFailedException(e);
        }
    }
}
