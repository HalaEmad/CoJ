package com.ir.android.networking.FeatureModels;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Henawey on 7/12/16.
 */
public class Properties {

    public Properties(){
        dynamicProperties=new ArrayList<>();
    }

    @JsonProperty("DatasourceId")
    private int datasourceId;

    @JsonProperty("TenantId")
    private int tenantId;

    @JsonProperty("Classification")
    private String classification;

    @JsonProperty("AnnotationId")
    private int annotationId;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("TimeZoneOffset")
    private long timeZoneOffset;

    @JsonProperty("EndDateTime")
    private Date endDateTime;

    @JsonProperty("DeleteFlag")
    private int deleteFlag;

    @JsonProperty("ObjectId")
    private int objectId;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("LastUpdateDateTime")
    private Date lastUpdateDateTime;

    @JsonProperty("StartDateTime")
    private Date startDateTime;

    @JsonProperty("AssessmentDateTime")
    private Date assessmentDateTime;

    private String callCategory;
    private String callType;
    private String address;
    private String submittedBy;
    private String submittedDateTime;
    private String expirationDateTime;
    private String status;
    private String severity;

    private ArrayList<DynamicProperty> dynamicProperties;

    public Date getAssessmentDateTime() {
        return assessmentDateTime;
    }


    @JsonAnySetter
    public void set(String name, String value) {
        if (name.toLowerCase().startsWith("property")) {
            DynamicProperty dynamicProperty = new DynamicProperty();
            dynamicProperty.setName(name);
            dynamicProperty.setValue(value);
            dynamicProperties.add(dynamicProperty);
        }
    }

    public void setAssessmentDateTime(Date assessmentDateTime) {
        this.assessmentDateTime = assessmentDateTime;
    }

    public int getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(int datasourceId) {
        this.datasourceId = datasourceId;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public int getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(int annotationId) {
        this.annotationId = annotationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimeZoneOffset() {
        return timeZoneOffset;
    }

    public void setTimeZoneOffset(long timeZoneOffset) {
        this.timeZoneOffset = timeZoneOffset;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    public void setLastUpdateDateTime(Date lastUpdateDateTime) {
        this.lastUpdateDateTime = lastUpdateDateTime;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getCallCategory() {
        return callCategory;
    }

    public void setCallCategory(String callCategory) {
        this.callCategory = callCategory;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public String getSubmittedDateTime() {
        return submittedDateTime;
    }

    public void setSubmittedDateTime(String submittedDateTime) {
        this.submittedDateTime = submittedDateTime;
    }

    public String getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(String expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public ArrayList<DynamicProperty> getDynamicProperties() {
        return dynamicProperties;
    }
}
