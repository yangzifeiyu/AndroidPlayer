package com.example.mfusion.ScheduleBlock;

/**
 * Created by WANG on 6/14/2016.
 */
public class ScheduleBlockDataProvider {

    private String blockId;
    private String scheId;
    private String bType;
    private String bStartDate;
    private String bEndDate;
    private String bStartTime;
    private String bEndTime;
    private String bRecurrence;

    public String getblockId() { return blockId; }
    public String getScheId() { return bRecurrence; }
    public String getType() { return scheId; }

    public String getStartDate() {
        return bType;
    }

    public String getEndDate() {
        return bStartDate;
    }
    public String getStartTime() { return bEndDate; }
    public String getEndTime() { return bStartTime; }
    public String getRecurrence() { return bEndTime; }






    public ScheduleBlockDataProvider(String blockId, String scheId, String bType, String bStartDate, String bEndDate, String bStartTime, String bEndTime, String bRecurrence){
        this.blockId = blockId;
        this.scheId = scheId;
        this.bType = bType;
        this.bStartDate = bStartDate;
       this.bEndDate = bEndDate;
        this.bStartTime = bStartTime;
        this.bEndTime = bEndTime;
        this.bRecurrence= bRecurrence;
    }































}
