package com.example.mfusion.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;

public class AlarmReceiver2 extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Toast.makeText(arg0, "Alarm received for wakeup!", Toast.LENGTH_LONG).show();
		PowerManager pm = (PowerManager)arg0.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.ON_AFTER_RELEASE, "Your App Tag");
		wakelock.acquire();
		wakelock.release();

	}

}
