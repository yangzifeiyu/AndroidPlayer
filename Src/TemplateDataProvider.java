package com.example.mfusion.Template;

/**
 * Created by WANG on 6/15/2016.
 */
public class TemplateDataProvider {


    private String tempId;
    private String tempName;
    private String tempWidth;
    private String tempHeight;
    private String tempBackColor;
    private String tempBackImage;

    public String getTempId() { return tempId; }
    public String getTempName() { return tempName; }
    public String getTempWidth() { return tempWidth; }
    public String getTempHeight() {
        return tempHeight;
    }
    public String getTempBackColor() {
        return tempBackColor;
    }
    public String getTempBackImage() { return tempBackImage; }






    public TemplateDataProvider(String tempId, String tempName, String tempWidth, String tempHeight, String tempBackColor, String tempBackImage){
        this.tempId = tempId;
        this.tempName = tempName;
        this.tempWidth = tempWidth;
        this.tempHeight = tempHeight;
        this.tempBackColor = tempBackColor;
        this.tempBackImage = tempBackImage;

    }






















}
