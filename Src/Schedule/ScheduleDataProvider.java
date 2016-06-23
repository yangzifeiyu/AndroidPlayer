package com.example.mfusion.Schedule;

/**
 * Created by WANG on 6/10/2016.
 */
public class ScheduleDataProvider {
    private String ScheduleId;
    private String scheName;
    private String scheIdleItem;
    private String schePlayMode;

    public String getScheduleId() {
        return ScheduleId;
    }

    public String getScheName() {
        return scheName;
    }

    public String getScheIdleItem() {
        return scheIdleItem;
    }

    public String getSchePlayMode() {
        return schePlayMode;
    }

    public ScheduleDataProvider(String ScheduleId, String scheName, String scheIdleItem, String schePlayMode){
        this.ScheduleId = ScheduleId;
        this.scheName = scheName;
        this.scheIdleItem = scheIdleItem;
        this.schePlayMode = schePlayMode;


    }















}
