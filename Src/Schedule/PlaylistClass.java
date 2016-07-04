package com.example.mfusion.Schedule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by MIN RUI on 27/6/2016.
 */
public class PlaylistClass extends View {

    public PlaylistClass(Context context) {
        super(context);
    }

    public PlaylistClass(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlaylistClass(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public static abstract class NewPlaylist {

        public static final String listId = "Schedule_Id";
        public static final String listName = "sche_name";
        public static final String TABLE_NAME = "Playlist";
    }
}
