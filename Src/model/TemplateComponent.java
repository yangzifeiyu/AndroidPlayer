package com.example.mfusion.model;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.mfusion.Utility.ScreenUtil;

/**
 * Created by JCYYYY on 2016/5/26.
 */
public class TemplateComponent implements Parcelable{
    private float left;
    private float top;
    private float right;
    private float bottom;

    //newwwwww
    public static final int TYPE_TEXT=1;
    public static final int TYPE_VIDEO=2;
    public static final int TYPE_IMAGE=3;


    private int type;
    private Uri sourceUri;
    private String sourceText;

    private Context context;
    private ScreenUtil su;

    private int parentWidth;
    private int parentHeight;

    public int getParentWidth() {
        return parentWidth;
    }

    public void setParentWidth(int parentWidth) {
        this.parentWidth = parentWidth;
    }

    public int getParentHeight() {
        return parentHeight;
    }

    public void setParentHeight(int parentHeight) {
        this.parentHeight = parentHeight;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Uri getSourceUri() {
        return sourceUri;
    }

    public void setSourceUri(Uri sourceUri) {
        this.sourceUri = sourceUri;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }
//----






    public float getLeft() {
        return left;
    }
    public int getResolvedLeft(){
        return (int)(left*parentWidth);
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }
    public int getResolvedTop(){
        return (int)(top*parentHeight);
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getRight() {
        return right;
    }
    public int getResolvedRight(){
        return (int)(right*parentWidth);
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getBottom() {
        return bottom;
    }
    public int getResolvedBottom(){
        return (int)(bottom*parentHeight);
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public TemplateComponent(Context context,float left, float top, float right, float bottom) {

        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.sourceText="";

        this.context=context;
        su=new ScreenUtil(context);
        parentWidth=su.getWidth();
        parentHeight=su.getHeight();
    }

    //------------prece
    protected TemplateComponent(Parcel in) {
        left = in.readFloat();
        top = in.readFloat();
        right = in.readFloat();
        bottom = in.readFloat();
        type = in.readInt();
        sourceUri = (Uri) in.readValue(Uri.class.getClassLoader());
        sourceText = in.readString();

        parentWidth = in.readInt();
        parentHeight = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(left);
        dest.writeFloat(top);
        dest.writeFloat(right);
        dest.writeFloat(bottom);
        dest.writeInt(type);
        dest.writeValue(sourceUri);
        dest.writeString(sourceText);

        dest.writeInt(parentWidth);
        dest.writeInt(parentHeight);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TemplateComponent> CREATOR = new Parcelable.Creator<TemplateComponent>() {
        @Override
        public TemplateComponent createFromParcel(Parcel in) {
            return new TemplateComponent(in);
        }

        @Override
        public TemplateComponent[] newArray(int size) {
            return new TemplateComponent[size];
        }
    };


}
