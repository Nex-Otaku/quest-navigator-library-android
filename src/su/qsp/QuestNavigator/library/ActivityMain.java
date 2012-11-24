package su.qsp.QuestNavigator.library;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.WindowManager;

import org.apache.cordova.*;

public class ActivityMain extends DroidGap {

    public static final int SLOTS_MAX = 5;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        super.setIntegerProperty("splashscreen", R.drawable.splash);
        
        super.loadUrl("file:///android_asset/www/standalone_content/gamePG-android.html", 60000);
    }
}
