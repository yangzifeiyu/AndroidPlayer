package com.example.mfusion.ScheduleBlock;

/**
 * Created by WANG on 6/17/2016.
 */
public class BlockItemDataProvider {


    private String Id;
    private String blockId;
    private String ItemId;


    public String getId() { return Id; }
    public String getBlockId() { return blockId; }
    public String getItemId() { return ItemId; }

    public BlockItemDataProvider(String Id, String blockId, String ItemId){
        this.Id = Id;
        this.blockId = blockId;
        this.ItemId = ItemId;

    }

}
