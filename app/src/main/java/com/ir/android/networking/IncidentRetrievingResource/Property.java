package com.ir.android.networking.IncidentRetrievingResource;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Henawey on 7/12/16.
 */
public class Property {

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


    public Date getAssessmentDateTime() {
        return assessmentDateTime;
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

}
