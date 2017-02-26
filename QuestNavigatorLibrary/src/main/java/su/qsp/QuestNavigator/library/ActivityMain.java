package su.qsp.QuestNavigator.library;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import org.apache.cordova.*;

public class ActivityMain extends CordovaActivity {

    public static final int SLOTS_MAX = 5;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        super.loadUrl(launchUrl);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
    	//Контекст UI
    	//Ловим кнопку "Back", и не закрываем активити, а только
    	//отправляем в бэкграунд (как будто нажали "Home")
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    	Utility.WriteLog("App closed by user! Going to background");
	    	moveTaskToBack(true);
        	return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
