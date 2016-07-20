package com.ir.android.networking.FeatureModels;

/**
 * Created by Henawey on 7/15/16.
 */
public class Date {

    private String text;
    private long value;
    private int offset;



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public java.util.Date toDate(){
        return new java.util.Date(value);
    }
}
