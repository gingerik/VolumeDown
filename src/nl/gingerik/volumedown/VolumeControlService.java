package nl.gingerik.volumedown;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class VolumeControlService extends Service {

	private Logger mLog;
	private SettingsContentObserver mSettingsContentObserver;

	@Override
	public void onCreate() {
		mLog = new Logger(this, VolumeControlService.class.getSimpleName());
		mLog.v("Starting VolumeDown Service");
		Toast.makeText(this, "Starting VolumeDown Service", Toast.LENGTH_SHORT)
				.show();
		super.onCreate();
		mSettingsContentObserver = new SettingsContentObserver(this,
				new Handler());
		this.getApplicationContext()
				.getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System.CONTENT_URI, true,
						mSettingsContentObserver);
	}

	@Override
	public void onDestroy() {
		mLog.v("Stopping VolumeDown Service");
		Toast.makeText(this, "Stopping VolumeDown Service", Toast.LENGTH_SHORT)
				.show();
		if (mSettingsContentObserver != null) {
			getApplicationContext().getContentResolver()
					.unregisterContentObserver(mSettingsContentObserver);
			mSettingsContentObserver = null;
		}
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mLog.v("VolumeDown Service received start command");
		return START_STICKY; // keep running
	}

}
