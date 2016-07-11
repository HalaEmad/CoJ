package com.ir.android.networking.login;

/**
 * Created by Henawey on 7/11/16.
 */
public class ProfileAttribute {

    private Integer id;
    private String name;
    private String i18nDescription;
    private String i18nLabel;
    private String i18nContextualHelp;
    private String i18nValidationMessage;
    private Integer required;
    private String dataType;
    private String displayPane;
    private String value;
    private String defaultValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getI18nDescription() {
        return i18nDescription;
    }

    public void setI18nDescription(String i18nDescription) {
        this.i18nDescription = i18nDescription;
    }

    public String getI18nLabel() {
        return i18nLabel;
    }

    public void setI18nLabel(String i18nLabel) {
        this.i18nLabel = i18nLabel;
    }

    public String getI18nContextualHelp() {
        return i18nContextualHelp;
    }

    public void setI18nContextualHelp(String i18nContextualHelp) {
        this.i18nContextualHelp = i18nContextualHelp;
    }

    public String getI18nValidationMessage() {
        return i18nValidationMessage;
    }

    public void setI18nValidationMessage(String i18nValidationMessage) {
        this.i18nValidationMessage = i18nValidationMessage;
    }

    public Integer getRequired() {
        return required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDisplayPane() {
        return displayPane;
    }

    public void setDisplayPane(String displayPane) {
        this.displayPane = displayPane;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
