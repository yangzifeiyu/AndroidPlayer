package com.example.mfusion.TemplateComponent;

/**
 * Created by WANG on 6/15/2016.
 */
public class TemplateCompoDataProvider {

    private String componentID;
    private String tempId;
    private String compName;
    private String compType;
    private String compX;
    private String compY;
    private String compWidth;
    private String compHeight;
    private String compZindex;
    private String compBackColor;
    private String compProperty;

    public String getComponentID() { return componentID; }
    public String getTempId() { return tempId; }
    public String getCompName() { return compName; }
    public String getCompType() {return compType;}
    public String getCompX() { return compX; }
    public String getCompY() { return compY; }
    public String getCompWidth() { return compWidth; }
    public String getCompHeight() { return compHeight; }
    public String getCompZindex() { return compZindex; }
    public String getCompBackColor() { return compBackColor; }
    public String getCompProperty() {
        return compProperty;
    }


    public TemplateCompoDataProvider(String componentID, String tempId, String compName, String compType, String compX, String compY, String compWidth, String compHeight, String compZindex, String compBackColor, String compProperty){
        this.componentID = componentID;
        this.tempId = tempId;
        this.compName = compName;
        this.compType = compType;
        this.compX = compX;
        this.compY = compY;
        this.compWidth = compWidth;
        this.compHeight= compHeight;
        this.compZindex = compZindex;
        this.compBackColor = compBackColor;
        this.compProperty= compProperty;
}





}



