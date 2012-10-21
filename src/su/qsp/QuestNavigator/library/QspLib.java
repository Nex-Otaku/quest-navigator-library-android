package su.qsp.QuestNavigator.library;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

import su.qsp.QuestNavigator.library.Utility;

public class QspLib extends Plugin {
	
	private Map<String, String> jsCallBacks = null;
	private String initLevelCallbackId = null;
	private boolean muted = false;
	
	private Context uiContext = null;
	private Activity mainActivity = null;
	private QspSkin skin = null;
	
	final private ReentrantLock musicLock = new ReentrantLock();
	final private ReentrantLock callbackLock = new ReentrantLock();
	final private ReentrantLock initLock = new ReentrantLock();
	
	final static public int QSP_TRUE = 1; 
	final static public int QSP_FALSE = 0; 

    public static final int JSON_STRING = 0;
    public static final int JSON_ARRAY = 1;
    public static final int JSON_OBJECT = 2;

    public static final int SLOTS_MAX = 5;
    
	public PluginResult execute(String action, JSONArray args, String callbackId) {
		// Контекст UI
	    PluginResult.Status status = PluginResult.Status.OK;
	    String result = "";

        if (action.equals("initLib")) {
        	initLib(args, callbackId);
        	PluginResult emptyResult = new PluginResult(PluginResult.Status.NO_RESULT);
        	emptyResult.setKeepCallback(true);
        	return emptyResult;
        } else if (action.equals("registerJsCallback")) {
        	registerJsCallback(args, callbackId);
        	PluginResult emptyResult = new PluginResult(PluginResult.Status.NO_RESULT);
        	emptyResult.setKeepCallback(true);
        	return emptyResult;
        } else if (action.equals("restartGame")) {
        	restartGame(args, callbackId);
        } else if (action.equals("executeAction")) {
        	executeAction(args, callbackId);
        } else if (action.equals("selectObject")) {
        	selectObject(args, callbackId);
        } else if (action.equals("msgResult")) {
        	msgResult(args, callbackId);
        } else if (action.equals("inputResult")) {
        	inputResult(args, callbackId);
        } else if (action.equals("userMenuResult")) {
        	userMenuResult(args, callbackId);
        } else if (action.equals("errorResult")) {
        	errorResult(args, callbackId);
        } else if (action.equals("loadGame")) {
        	loadGame(args, callbackId);
        } else if (action.equals("saveGame")) {
        	saveGame(args, callbackId);
        } else if (action.equals("saveSlotSelected")) {
        	saveSlotSelected(args, callbackId);
        } else if (action.equals("setMute")) {
        	setMute(args, callbackId);
        } else if (action.equals("moveTaskToBackground")) {
        	moveTaskToBackground(args, callbackId);
        } else {
        	return new PluginResult(PluginResult.Status.INVALID_ACTION);
        }
        return new PluginResult(status, result);
	}
	
	public boolean onOverrideUrlLoading(String url)
	{
		// Контекст UI
		if ((url == null) || (url.length() == 0))
			return false;
		try {
			url = URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
        	Utility.WriteLog("Failed to decode url in onOverrideUrlLoading: " + url);
			e.printStackTrace();
			return false;
		}
        if (url.toUpperCase().startsWith("EXEC:")) {
        	url = unescapeHtml(url.substring(5));
        	if (url.length() > 0)
        	{
        		final String code = url;
        		libThreadHandler.post(new Runnable() {
        			public void run() {
        	    		if (libraryThreadIsRunning)
        	    			return;
                    	libraryThreadIsRunning = true;
                    	
            	    	boolean bExec = QSPExecString(code, true);
            	    	CheckQspResult(bExec, "onOverrideUrlLoading: QSPExecString");
                    	
                		libraryThreadIsRunning = false;
        			}
        		});		
        	}
        	return true;
        }
		return false;
	}

    /**
     * Called when the system is about to start resuming a previous activity. 
     * 
     * @param multitasking		Flag indicating if multitasking is turned on for app
     */
    public void onPause(boolean multitasking) {
    	//Контекст UI
    	Utility.WriteLog("onPause\\");
    	
    	if (gameIsRunning)
    	{
        	Utility.WriteLog("onPause: pausing game");    	
    	    //Останавливаем таймер
    	    timerHandler.removeCallbacks(timerUpdateTask);
    	    
    	    //Приостанавливаем музыку
    	    PauseMusic(true);
    	}
    	
    	Utility.WriteLog("onPause/");  
    	super.onPause(multitasking);
    }

    /**
     * Called when the activity will start interacting with the user. 
     * 
     * @param multitasking		Flag indicating if multitasking is turned on for app
     */
    public void onResume(boolean multitasking) {
    	Utility.WriteLog("onResume\\");
    	//Контекст UI
    	super.onResume(multitasking);

        if (gameIsRunning)
    	{
    		//Запускаем таймер
            timerHandler.postDelayed(timerUpdateTask, timerInterval);

            //Запускаем музыку
    	    PauseMusic(false);
    	}
    	
    	Utility.WriteLog("onResume/");    	
    }

    /**
     * The final call you receive before your activity is destroyed. 
     */
    public void onDestroy() {
    	//Контекст UI
    	Utility.WriteLog("onDestroy\\");
    	FreeResources();
    	Utility.WriteLog("onDestroy/");  
    	super.onDestroy();
    }
    
    /****************************************************************************************************
     ****************************************************************************************************
     ****************************************************************************************************
     ****************************************************************************************************
     ****************************************************************************************************
    */
    
	private void initLib(JSONArray args, String callbackId)
	{
		// Контекст UI
    	Utility.WriteLog("[[initLib]]");
    	
    	// Сохраняем колбэк для последующего вызова, 
    	// он нужен нам чтобы обеспечить последовательное выполнение инициализации плагина
    	initLevelCallbackId = callbackId;
    	
    	// Это странно, но именно так PhoneGap дает нам доступ к контексту и основному активити
    	if (cordova == null)
    	{
        	Utility.WriteLog("ERROR - cordova is null in initLib");
        	return;
    	}
    	uiContext = cordova.getActivity();
    	mainActivity = cordova.getActivity();
    	
        callbackLock.lock();
        try {
        	jsCallBacks = new HashMap<String, String>();
        } finally {
        	callbackLock.unlock();
        }

		gameIsRunning = false;
		qspInited = false;
		muted = false;
		
	    // Устанавливаем флаг, который означает, что мы ждем ответа от JS диалога(на данный момент - только диалог слотов сохранений)
	    waitingForJS = false;
		
		curGameFile = "www/standalone_content/game.qsp";
		curSaveDir = mainActivity.getFilesDir().getPath().concat(File.separator);

        //Создаем список для звуков и музыки
        mediaPlayersList = new Vector<ContainerMusic>();
        
        //Создаем список для всплывающего меню
        menuList = new Vector<ContainerMenuItem>();

        //Создаем объект для таймера
        timerHandler = new Handler(Looper.getMainLooper());

        // Создаем скин
        skin = new QspSkin(this);
        
        //Запускаем поток библиотеки
        StartLibThread();
	}

	private void registerJsCallback(JSONArray args, String callbackId)
	{
		// Контекст UI
    	Utility.WriteLog("[[registerJsCallback]]");
        callbackLock.lock();
        try {
	    	if (jsCallBacks == null)
	    	{
	    		Utility.WriteLog("ERROR - you have to call initLib first!");
	        	callbackLock.unlock();
	    		return;
	    	}
	    	String callbackName = null;
			try {
				callbackName = args.getString(0);
			} catch (JSONException e) {
	    		Utility.WriteLog("ERROR - args in registerJsCallback!");
				e.printStackTrace();
	        	callbackLock.unlock();
				return;
			}
	    	if ((callbackName == null) || (callbackName.length() == 0))
	    	{
	    		Utility.WriteLog("ERROR - invalid callback name!");
	        	callbackLock.unlock();
	    		return;
	    	}
	    	// Сохраняем колбэк
	    	jsCallBacks.put(callbackName, callbackId);
        } finally {
        	callbackLock.unlock();
        	jsInitNext();
        }
	}

	private void restartGame(JSONArray args, String callbackId)
	{
		// Контекст UI
    	Utility.WriteLog("[[restartGame]]");

    	String gameFile = curGameFile;
    	StopGame(true);
    	runGame(gameFile);
	}

	private void executeAction(JSONArray args, String callbackId)
	{
		// Контекст UI
    	Utility.WriteLog("[[executeAction]]");

    	int position = -1;
		try {
			position = args.getInt(0);
		} catch (JSONException e) {
    		Utility.WriteLog("ERROR - args in executeAction!");
			e.printStackTrace();
			return;
		}
    	
		final int actionIndex = position;
		libThreadHandler.post(new Runnable() {
			public void run() {
	    		if (libraryThreadIsRunning)
	    			return;
            	libraryThreadIsRunning = true;
        		boolean result = QSPSetSelActionIndex(actionIndex, false);
				CheckQspResult(result, "executeAction: QSPSetSelActionIndex");
        		result = QSPExecuteSelActionCode(true);
				CheckQspResult(result, "executeAction: QSPExecuteSelActionCode");
        		libraryThreadIsRunning = false;
			}
		});		
	}

	private void selectObject(JSONArray args, String callbackId)
	{
		//STUB - нужно будет протестировать, код вроде рабочий
		// Контекст UI
    	Utility.WriteLog("[[selectObject]]");

    	int position = -1;
		try {
			position = args.getInt(0);
		} catch (JSONException e) {
    		Utility.WriteLog("ERROR - args in selectObject!");
			e.printStackTrace();
			return;
		}
    	
		final int objectIndex = position;
		libThreadHandler.post(new Runnable() {
			public void run() {
	    		if (libraryThreadIsRunning)
	    			return;
            	libraryThreadIsRunning = true;
        		boolean result = QSPSetSelObjectIndex(objectIndex, true);
				CheckQspResult(result, "selectObject: QSPSetSelObjectIndex");
        		libraryThreadIsRunning = false;
			}
		});		
	}

	private void msgResult(JSONArray args, String callbackId)
	{
		// Контекст UI
    	Utility.WriteLog("[[msgResult]]");
		dialogHasResult = true;
		Utility.WriteLog("msgResult: OK clicked, unparking library thread");
    	setThreadUnpark();
	}

	private void inputResult(JSONArray args, String callbackId)
	{
		// Контекст UI
    	Utility.WriteLog("[[inputResult]]");
		try {
	    	inputboxResult = args.getString(0);
		} catch (JSONException e) {
    		Utility.WriteLog("ERROR - args in inputResult!");
			e.printStackTrace();
			return;
		}

		dialogHasResult = true;
		Utility.WriteLog("inputResult: OK clicked, unparking library thread");
    	setThreadUnpark();
	}

	private void userMenuResult(JSONArray args, String callbackId)
	{
		// Контекст UI
    	Utility.WriteLog("[[userMenuResult]]");
    	int index = -1;
		try {
			index = args.getInt(0);
		} catch (JSONException e) {
    		Utility.WriteLog("ERROR - args in userMenuResult!");
			e.printStackTrace();
			return;
		}

       	menuResult = index;
		dialogHasResult = true;
		Utility.WriteLog("userMenuResult: menu item selected, unparking library thread");
       	setThreadUnpark();
	}

	private void errorResult(JSONArray args, String callbackId)
	{
		// Контекст UI
    	Utility.WriteLog("[[errorResult]]");
        dialogHasResult = true;
		Utility.WriteLog("errorResult: OK clicked, unparking library thread");
    	setThreadUnpark();
	}

	private void loadGame(JSONArray args, String callbackId)
	{
		// Контекст UI
    	Utility.WriteLog("[[loadGame]]");
        // Останавливаем таймер
	    timerHandler.removeCallbacks(timerUpdateTask);
        // Загружаем список файлов и отдаем в яваскрипт
    	JSONObject slots = getSaveSlots(true);
    	jsShowSaveSlotsDialog(slots);
	}

	private void saveGame(JSONArray args, String callbackId)
	{
		// Контекст UI
    	Utility.WriteLog("[[saveGame]]");
        // Останавливаем таймер
	    timerHandler.removeCallbacks(timerUpdateTask);
	    // Загружаем список файлов и отдаем в яваскрипт
    	JSONObject slots = getSaveSlots(false);
    	jsShowSaveSlotsDialog(slots);
	}

	private void saveSlotSelected(JSONArray args, String callbackId)
	{
		// Контекст UI
    	Utility.WriteLog("[[saveSlotSelected]]");
        waitingForJS = false;

        int index = -1;
        int mode = -1;
		try {
	    	index = args.getInt(0); // Номер слота
	    	mode = args.getInt(1); // 1 - open, 0 - save
		} catch (JSONException e) {
    		Utility.WriteLog("ERROR - args in saveSlotSelected!");
			e.printStackTrace();
			return;
		}
        
        if (index == -1)
        {
	        // Запускаем таймер
	        timerHandler.postDelayed(timerUpdateTask, timerInterval);
	        return;
        }
        
        if (mode == 1)
        	LoadSlot(index);
        else
        	SaveSlot(index);
	}

	private void setMute(JSONArray args, String callbackId)
	{
		// Контекст UI
    	Utility.WriteLog("[[setMute]]");
    	
		try {
	    	muted = args.getBoolean(0); // true - выключаем звук, false - включаем
		} catch (JSONException e) {
    		Utility.WriteLog("ERROR - args in setMute!");
			e.printStackTrace();
			return;
		}
    	
        musicLock.lock();
        try {
	    	for (int i=0; i<mediaPlayersList.size(); i++)
	    	{
	    		ContainerMusic it = mediaPlayersList.elementAt(i);    		
				float realVolume = GetRealVolume(it.volume);
				it.player.setVolume(realVolume, realVolume);
	    	}
	    } finally {
	    	musicLock.unlock();
	    }
	}

	private void moveTaskToBackground(JSONArray args, String callbackId)
	{
		// Контекст UI
    	Utility.WriteLog("[[moveTaskToBackground]]");
    	
    	Utility.WriteLog("App closed by user! Going to background");
    	mainActivity.moveTaskToBack(true);
	}
	
	/*
	 * 		Обертки яваскриптовых функций
	 */
	
	private void jsInitNext()
	{
		// Контекст UI
		// Продвигаемся на шаг дальше по инициализации плагина
    	Utility.WriteLog("<<jsInitNext>>");

    	PluginResult result = new PluginResult(PluginResult.Status.OK);
		result.setKeepCallback(true);
		if ((initLevelCallbackId == null) || (initLevelCallbackId.length() == 0))
		{
    		Utility.WriteLog("ERROR - no valid callback ID for qspInitNext!");
    		return;
		}
		success(result, initLevelCallbackId);
	}
	
	private void jsCallApi(String from, String to, Object obj, int cast)
	{
		// Контекст UI
		// Все функции вызываются одинаково, 
		// поэтому используем эту вспомогательную функцию, чтобы не загромождать код
    	Utility.WriteLog("<<" + from + ">>");

		PluginResult result = null;
		switch (cast)
		{
		case JSON_STRING:
			result = new PluginResult(PluginResult.Status.OK, (String)obj);
			break;
		case JSON_OBJECT:
			result = new PluginResult(PluginResult.Status.OK, (JSONObject)obj);
			break;
		case JSON_ARRAY:
			result = new PluginResult(PluginResult.Status.OK, (JSONArray)obj);
			break;
		}
		result.setKeepCallback(true);
		String callbackId = getCallbackId(to);
		if ((callbackId == null) || (callbackId.length() == 0))
		{
    		Utility.WriteLog("ERROR - no valid callback ID for " + to + "!");
    		return;
		}
		success(result, callbackId);
	}
	
	private void jsSetGroupedContent(JSONObject content)
	{
		// Контекст UI
		jsCallApi("jsSetGroupedContent", "qspSetGroupedContent", content, JSON_OBJECT);
    }

	private void jsQspMsg(String msg)
	{
		// Контекст UI
		jsCallApi("jsQspMsg", "qspMsg", msg, JSON_STRING);
    }

	private void jsQspView(String path)
	{
		// Контекст UI
		jsCallApi("jsQspView", "qspView", path, JSON_STRING);
    }

	private void jsQspInput(String text)
	{
		// Контекст UI
		jsCallApi("jsQspInput", "qspInput", text, JSON_STRING);
    }

	private void jsQspMenu(JSONArray menu)
	{
		// Контекст UI
		jsCallApi("jsQspMenu", "qspMenu", menu, JSON_ARRAY);
    }

	private void jsQspError(JSONObject error)
	{
		// Контекст UI
		jsCallApi("jsQspError", "qspError", error, JSON_OBJECT);
    }

	private void jsShowSaveSlotsDialog(JSONObject slots)
	{
		// Контекст UI
		jsCallApi("jsShowSaveSlotsDialog", "qspShowSaveSlotsDialog", slots, JSON_OBJECT);
    }
	
	private String getCallbackId(String name)
	{
		// Небольшая вспомогательная функция, 
		// чтобы не загромождать код "локами" 
        callbackLock.lock();
        String callbackId = null;
        try {
        	callbackId = jsCallBacks.get(name);
        } finally {
        	callbackLock.unlock();
        }
        return callbackId;
	}
	
    //******************************************************************************
    //******************************************************************************
    //****** / QSP  LIBRARY  REQUIRED  CALLBACKS \ *********************************
    //******************************************************************************
    //******************************************************************************
    private void RefreshInt(int isRedraw) 
    {
    	//Контекст библиотеки
        boolean needUpdate = skin.isSomethingChanged();
        skin.updateBaseVars();
        needUpdate = needUpdate || skin.isSomethingChanged();
        skin.updateMainScreen();
        needUpdate = needUpdate || skin.isSomethingChanged();
        
		JSONObject jsSkin = null;
        if (needUpdate)
            jsSkin = skin.getJsSkin();

        //основное описание
        String mainDesc = null;
        if ((QSPIsMainDescChanged() == true) || skin.isHtmlModeChanged)
        {
            mainDesc = skin.applyHtmlFixes(QSPGetMainDesc());
        }
    	
        //список действий
        JSONArray acts = null;
        if ((QSPIsActionsChanged() == true) || skin.isHtmlModeChanged)
        {
			int nActsCount = QSPGetActionsCount();
			acts = new JSONArray();
			for (int i = 0; i < nActsCount; i++)
			{
		      	ContainerJniResult actsResult = (ContainerJniResult) QSPGetActionData(i);
				JSONObject act = new JSONObject();
				try {
					act.put("image", actsResult.str2);
					act.put("desc", skin.applyHtmlFixes(actsResult.str1));
					acts.put(i, act);
				} catch (JSONException e) {
		    		Utility.WriteLog("ERROR - act or acts[] in RefreshInt!");
					e.printStackTrace();
				}
			}
		}
        
        //инвентарь
        JSONArray objs = null;
        if ((QSPIsObjectsChanged() == true) || skin.isHtmlModeChanged)
        {
            int nObjsCount = QSPGetObjectsCount();
            int nSelectedObject = QSPGetSelObjectIndex();
            objs = new JSONArray();
            for (int i = 0; i < nObjsCount; i++)
            {
	        	ContainerJniResult objsResult = (ContainerJniResult) QSPGetObjectData(i);
                
				JSONObject obj = new JSONObject();
				try {
					obj.put("image", objsResult.str2);
					obj.put("desc", skin.applyHtmlFixes(objsResult.str1));
					obj.put("selected", (i == nSelectedObject)? 1 : 0);
					objs.put(i, obj);
				} catch (JSONException e) {
		    		Utility.WriteLog("ERROR - obj or objs[] in RefreshInt!");
					e.printStackTrace();
				}
            }
        }
        
        //доп. описание
        String varsDesc = null;
        if ((QSPIsVarsDescChanged() == true) || skin.isHtmlModeChanged)
        {
            varsDesc = skin.applyHtmlFixes(QSPGetVarsDesc());
        }
        // Передаем собранные данные в яваскрипт
        if ((jsSkin != null) || (mainDesc != null) || (acts != null) || (objs != null) || (varsDesc != null))
        {
        	JSONObject groupedContent = new JSONObject();
        	try {
	            if (jsSkin != null)
	            {
	                groupedContent.put("skin", jsSkin);
	            }
	            if (mainDesc != null)
	            {
	                groupedContent.put("main", mainDesc);
	            }
	            if (acts != null)
	            {
	                groupedContent.put("acts", acts);
	            }
	            if (varsDesc != null)
	            {
	                groupedContent.put("vars", varsDesc);
	            }
	            if (objs != null)
	            {
	                groupedContent.put("objs", objs);
	            }
			} catch (JSONException e) {
	    		Utility.WriteLog("ERROR - groupedContent in RefreshInt!");
				e.printStackTrace();
			}
        	final JSONObject groupedContentObject = groupedContent;
			mainActivity.runOnUiThread(new Runnable() {
				public void run() {
					jsSetGroupedContent(groupedContentObject);
				}
			});
        }
        skin.resetUpdate();
    }
    
    private void SetTimer(int msecs)
    {
    	//Контекст библиотеки
    	final int timeMsecs = msecs;
		mainActivity.runOnUiThread(new Runnable() {
			public void run() {
				timerInterval = timeMsecs;
			}
		});
    }

    private void ShowMessage(String message)
    {
    	//Контекст библиотеки
		if (libThread==null)
		{
			Utility.WriteLog("ShowMessage: failed, libThread is null");
			return;
		}

	    // Обновляем скин
        skin.updateBaseVars();
        skin.updateMsgDialog();
        skin.updateEffects();
	    // Если что-то изменилось, то передаем в яваскрипт
	    if (skin.isSomethingChanged() && (skin.disableAutoRef != 1))
	    {
	        Utility.WriteLog("Hey! Skin was changed! Updating it before showing MSG.");
	        RefreshInt(QSP_TRUE);
	    }
		
		String msgValue = "";
		if (message != null)
			msgValue = message;
		
		dialogHasResult = false;

    	final String msg = skin.applyHtmlFixes(msgValue);
		mainActivity.runOnUiThread(new Runnable() {
			public void run() {
				jsQspMsg(msg);
				Utility.WriteLog("ShowMessage(UI): dialog showed");
			}
		});
    	
		Utility.WriteLog("ShowMessage: parking library thread");
        while (!dialogHasResult) {
        	setThreadPark();
        }
        parkThread = null;
		Utility.WriteLog("ShowMessage: library thread unparked, finishing");
    }
    
    private void PlayFile(String file, int volume)
    {
    	//Контекст библиотеки
    	final String musicFile = Utility.QspPathTranslate(file);
    	final int musicVolume = volume;
    	mainActivity.runOnUiThread(new Runnable() {
    		public void run() {
    			PlayFileUI(musicFile, musicVolume);
    		}
    	});
    }
    
    private boolean IsPlayingFile(String file)
    {
    	//Контекст библиотеки
    	return IsPlayingFileUI(Utility.QspPathTranslate(file), false, 0);
    }

    private void CloseFile(String file)
    {
    	//Контекст библиотеки
    	final String musicFile = Utility.QspPathTranslate(file);
    	mainActivity.runOnUiThread(new Runnable() {
    		public void run() {
    			CloseFileUI(musicFile);
    		}
    	});
    }
    
    private void ShowPicture(String file)
    {
    	//Контекст библиотеки
    	if (file == null)
    		file = "";
    	
    	final String fileName = Utility.QspPathTranslate(file);
    	
		mainActivity.runOnUiThread(new Runnable() {
			public void run() {
				if (fileName.length() > 0)
				{
			    	String prefix = "";
			    	if (curGameDir != null)
			    		prefix = curGameDir;
			    	
			    	//Проверяем, существует ли файл.
			    	//Если нет - выходим
			        if (!Utility.CheckAssetExists(uiContext, prefix.concat(fileName), "ShowPicture"))
			        	return;
				}
		        // "Пустое" имя файла тоже имеет значение - так мы скрываем картинку
		        jsQspView(fileName);
			}
		});    	    	
    }
    
    private String InputBox(String prompt)
    {
    	//Контекст библиотеки
		if (libThread==null)
		{
			Utility.WriteLog("InputBox: failed, libThread is null");
			return "";
		}

	    // Обновляем скин
	    skin.updateBaseVars();
	    skin.updateInputDialog();
	    skin.updateEffects();
	    // Если что-то изменилось, то передаем в яваскрипт
	    if (skin.isSomethingChanged() && (skin.disableAutoRef != 1))
	    {
	        Utility.WriteLog("Hey! Skin was changed! Updating it before showing INPUT.");
	        RefreshInt(QSP_TRUE);
	    }
		
		String promptValue = "";
		if (prompt != null)
			promptValue = prompt;
		
		dialogHasResult = false;
		
    	final String inputboxTitle = skin.applyHtmlFixes(promptValue);
		mainActivity.runOnUiThread(new Runnable() {
			public void run() {
				inputboxResult = "";
				jsQspInput(inputboxTitle);
				Utility.WriteLog("InputBox(UI): dialog showed");
			}
		});
    	
		Utility.WriteLog("InputBox: parking library thread");
        while (!dialogHasResult) {
        	setThreadPark();
        }
        parkThread = null;
		Utility.WriteLog("InputBox: library thread unparked, finishing");
    	return inputboxResult;
    }
    
    private int GetMSCount()
    {
    	//Контекст библиотеки
    	return (int) (System.currentTimeMillis() - gameStartTime);
    }
    
    private void AddMenuItem(String name, String imgPath)
    {
    	//Контекст библиотеки
    	ContainerMenuItem item = new ContainerMenuItem();
    	item.imgPath = Utility.QspPathTranslate(imgPath);
    	item.name = name;
    	menuList.add(item);
    }
    
    private int ShowMenu()
    {
    	//Контекст библиотеки
		if (libThread==null)
		{
			Utility.WriteLog("ShowMenu: failed, libThread is null");
			return -1;
		}

	    // Обновляем скин
	    skin.updateMenuDialog();
	    skin.updateEffects();
	    // Если что-то изменилось, то передаем в яваскрипт
	    if (skin.isSomethingChanged() && (skin.disableAutoRef != 1))
	    {
	        Utility.WriteLog("Hey! Skin was changed! Updating it before showing user menu.");
	        RefreshInt(QSP_TRUE);
	    }
		
		dialogHasResult = false;
		menuResult = -1;

		final Vector<ContainerMenuItem> uiMenuList = menuList; 
    	
		mainActivity.runOnUiThread(new Runnable() {
			public void run() {
		        JSONArray jsonMenuList = new JSONArray();
				for (int i = 0; i < uiMenuList.size(); i++)
				{
					JSONObject jsonMenuItem = new JSONObject();
					try {
						jsonMenuItem.put("image", uiMenuList.elementAt(i).imgPath);
						jsonMenuItem.put("desc", skin.applyHtmlFixes(uiMenuList.elementAt(i).name));
						jsonMenuList.put(i, jsonMenuItem);
					} catch (JSONException e) {
			    		Utility.WriteLog("ERROR - jsonMenuItem or jsonMenuList[] in ShowMenu!");
						e.printStackTrace();
					}
				}
				jsQspMenu(jsonMenuList);
			    Utility.WriteLog("ShowMenu(UI): dialog showed");
			}
		});
    	
		Utility.WriteLog("ShowMenu: parking library thread");
        while (!dialogHasResult) {
        	setThreadPark();
        }
        parkThread = null;
		Utility.WriteLog("ShowMenu: library thread unparked, finishing");
    	
		return menuResult;
    }
    
    private void DeleteMenu()
    {
    	//Контекст библиотеки
    	menuList.clear();
    }
    
    private void Wait(int msecs)
    {
    	//Контекст библиотеки
    	try {
			Thread.sleep(msecs);
		} catch (InterruptedException e) {
			Utility.WriteLog("WAIT in library thread was interrupted");
			e.printStackTrace();
		}
    }
    //******************************************************************************
    //******************************************************************************
    //****** \ QSP  LIBRARY  REQUIRED  CALLBACKS / *********************************
    //******************************************************************************
    //******************************************************************************

	/*
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 */

	private Runnable timerUpdateTask = new Runnable() {
    	//Контекст UI
		public void run() {
			libThreadHandler.post(new Runnable() {
				public void run() {
					if (!gameIsRunning)
						return;
					if (libraryThreadIsRunning)
						return;
			    	libraryThreadIsRunning = true;
				   	boolean result = QSPExecCounter(true);
					CheckQspResult(result, "timerUpdateTask: QSPExecCounter");
					libraryThreadIsRunning = false;
				}
			});
			timerHandler.postDelayed(this, timerInterval);
		}
	};
    
	private void CheckQspResult(boolean successfull, String failMsg)
	{
    	//Контекст библиотеки
    	if (!successfull)
    	{
        	//Контекст библиотеки
    		Utility.WriteLog(failMsg + " failed");

    		ContainerJniResult error = (ContainerJniResult) QSPGetLastErrorData();
			error.str2 = QSPGetErrorDesc(error.int1);
	    	String locName = skin.applyHtmlFixes((error.str1 == null) ? "" : error.str1);
	    	String errDesc = skin.applyHtmlFixes((error.str2 == null) ? "" : error.str2);

    		if (libThread==null)
    		{
    			Utility.WriteLog("CheckQspResult: failed, libThread is null");
    			return;
    		}

    	    // Обновляем скин
            skin.updateBaseVars();
            skin.updateMsgDialog();
            skin.updateEffects();
    	    // Если что-то изменилось, то передаем в яваскрипт
    	    if (skin.isSomethingChanged() && (skin.disableAutoRef != 1))
    	    {
    	        Utility.WriteLog("Hey! Skin was changed! Updating it before showing error dialog.");
    	        RefreshInt(QSP_TRUE);
    	    }
    		
    		dialogHasResult = false;
    		
        	final JSONObject jsErrorContainer = new JSONObject();
        	try {
	                jsErrorContainer.put("desc", errDesc);
	                jsErrorContainer.put("loc", locName);
	                jsErrorContainer.put("actIndex", error.int2);
	                jsErrorContainer.put("line", error.int3);
			} catch (JSONException e) {
	    		Utility.WriteLog("ERROR - jsErrorContainer in CheckQspResult!");
				e.printStackTrace();
			}
    		
    		mainActivity.runOnUiThread(new Runnable() {
    			public void run() {
    				jsQspError(jsErrorContainer);
    				Utility.WriteLog("CheckQspResult(UI): error dialog showed");
    			}
    		});
        	
    		Utility.WriteLog("CheckQspResult: parking library thread");
            while (!dialogHasResult) {
            	setThreadPark();
            }
            parkThread = null;
    		Utility.WriteLog("CheckQspResult: library thread unparked, finishing");
    	}
	}
	
	/*
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 */

    private void FreeResources()
    {
    	//Контекст UI
    	
    	//Процедура "честного" высвобождения всех ресурсов - в т.ч. остановка потока библиотеки
    	//Сейчас не вызывается вообще, т.к. у нас нет соотв. пункта меню.

    	//Вызовется только при закрытии активити в обработчике onDestroy(завершение активити),
    	//но этого не произойдет при нормальной работе, т.к. кнопка Back не закрывает, а только
    	//останавливает активити. Произойдет, только если закончится доступная память и приложение
    	//будет принудительно выгружено системой.
    	
    	//Очищаем ВСЕ на выходе
    	if (qspInited)
    	{
        	Utility.WriteLog("onDestroy: stopping game");
    		StopGame(false);
    	}
    	//Останавливаем поток библиотеки
   		StopLibThread();
    }
    
	private void runGame(String fileName)
    {
    	//Контекст UI
		if (!Utility.CheckAssetExists(uiContext, fileName, "runGame"))
			return;
    		
    	if (libThreadHandler == null)
    	{
    		Utility.WriteLog("runGame: failed, libThreadHandler is null");
    		return;
    	}

		if (libraryThreadIsRunning)
		{
    		Utility.WriteLog("runGame: failed, library thread is already running");
			return;
		}
    	
        final boolean inited = qspInited;
    	qspInited = true;
    	final String gameFileName = fileName;
    	curGameFile = gameFileName;
        curGameDir = gameFileName.substring(0, gameFileName.lastIndexOf(File.separator, gameFileName.length() - 1) + 1);
        
        libThreadHandler.post(new Runnable() {
    		public void run() {
    	        InputStream fIn = null;
    	        int size = 0;

    	    	AssetManager assetManager = uiContext.getAssets();
    	    	if (assetManager == null)
    	    	{
    	    		Utility.WriteLog("runGame(in library thread): failed, assetManager is null");
    	    		return;
    	    	}
    			try {
    				fIn = assetManager.open(gameFileName);
    				size = fIn.available();
    			} catch (Exception e) {
    				e.printStackTrace();
    	        	Utility.ShowError(uiContext, "Не удалось получить доступ к файлу");
    	        	try {
						fIn.close();
					} catch (IOException e1) {
	    	        	Utility.ShowError(uiContext, "Не удалось освободить дескриптор файла");
						e1.printStackTrace();
					}
    	        	return;
    			}
    	        
    			byte[] inputBuffer = new byte[size];
    			try {
	    			// Fill the Buffer with data from the file
	    			fIn.read(inputBuffer);
	    			fIn.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    	        	Utility.ShowError(uiContext, "Не удалось прочесть файл");
    	        	return;
    			}

        		if (!inited)
        			QSPInit();
    			final boolean gameLoaded = QSPLoadGameWorldFromData(inputBuffer, size, gameFileName);
				CheckQspResult(gameLoaded, "runGame: QSPLoadGameWorldFromData");
    			
    	        if (gameLoaded)
    	        {
	    			mainActivity.runOnUiThread(new Runnable() {
	    				public void run() {
    	    	            //Запускаем таймер
    	    	            timerInterval = 500;
    	    	            timerStartTime = System.currentTimeMillis();
    	    	            timerHandler.removeCallbacks(timerUpdateTask);
    	    	            timerHandler.postDelayed(timerUpdateTask, timerInterval);
    	    	            
    	    	            //Запускаем счетчик миллисекунд
    	    	            gameStartTime = System.currentTimeMillis();

    	    	            //Все готово, запускаем игру
    	    	            libThreadHandler.post(new Runnable() {
    	    	        		public void run() {
    	    	                	libraryThreadIsRunning = true;
    	    	        			boolean result = QSPRestartGame(true);
									CheckQspResult(result, "runGame: QSPRestartGame");
    	    	                	libraryThreadIsRunning = false;
    	    	        		}
    	    	            });
    	    	            
    	    	            gameIsRunning = true;
	    				}
	    			});
	    		}
    		}
    	});
    }
    
    private void StopGame(boolean restart)
    {
    	//Контекст UI
		if (gameIsRunning)
		{
            //останавливаем таймер
    	    timerHandler.removeCallbacks(timerUpdateTask);

    	    //останавливаем музыку
            CloseFileUI(null);
            
            gameIsRunning = false;
		}
		curGameDir = "";
		curGameFile = "";

		//Очищаем библиотеку
		if (restart || libraryThreadIsRunning)
			return;

		qspInited = false;
        libThreadHandler.post(new Runnable() {
    		public void run() {
            	libraryThreadIsRunning = true;
        		QSPDeInit();
            	libraryThreadIsRunning = false;
    		}
        });
    }
    
    private JSONObject getSaveSlots(boolean open)
    {
    	//Контекст UI
    	JSONArray jsSlots = new JSONArray();
		for (int i=0; i<SLOTS_MAX; i++)
		{
			String title;
			String Slotname = String.valueOf(i + 1).concat(".sav");
			File checkSlot = new File(curSaveDir.concat(Slotname));
			if (checkSlot.exists())
				title = String.valueOf(i + 1);
			else
				title = "-empty-";
			try {
				jsSlots.put(i, title);
			} catch (JSONException e) {
	    		Utility.WriteLog("ERROR - jsSlots[] in getSaveSlots!");
				e.printStackTrace();
			}
		}
    	
    	JSONObject jsSlotsContainer = new JSONObject();
    	try {
    		jsSlotsContainer.put("open", open ? 1 : 0);
    		jsSlotsContainer.put("slots", jsSlots);
		} catch (JSONException e) {
    		Utility.WriteLog("ERROR - jsSlotsContainer in getSaveSlots!");
			e.printStackTrace();
		}
    	return jsSlotsContainer;
    }
    
    private void LoadSlot(int index)
    {
    	//Контекст UI
    	String path = curSaveDir.concat(String.valueOf(index)).concat(".sav");
    	File f = new File(path);
    	if (!f.exists())
    	{
    		Utility.WriteLog("LoadSlot: failed, file not found");
    		return;
    	}

        FileInputStream fIn = null;
        int size = 0;
		try {
			fIn = new FileInputStream(f);
		} catch (FileNotFoundException e) {
        	Utility.ShowError(uiContext, "Не удалось открыть файл");
        	e.printStackTrace();
    		return;
		}
		try {
			size = fIn.available();
		} catch (IOException e) {
        	Utility.ShowError(uiContext, "Не удалось получить доступ к файлу");
			e.printStackTrace();
			try {
				fIn.close();
			} catch (IOException e1) {
	        	Utility.ShowError(uiContext, "Не удалось освободить дескриптор файла");
				e1.printStackTrace();
			}
    		return;
		}
        
		final byte[] inputBuffer = new byte[size];		
		try {
			fIn.read(inputBuffer);
			fIn.close();
		} catch (IOException e) {
			e.printStackTrace();
    		return;
		}
		final int bufferSize = size;

	    //Выключаем музыку
	    CloseFileUI(null);
	    
		libThreadHandler.post(new Runnable() {
			public void run() {
	    		if (libraryThreadIsRunning)
	    		{
	        		Utility.WriteLog("LoadSlot: failed, library is already running");
	    			return;
	    		}
            	libraryThreadIsRunning = true;
            	
            	boolean result = QSPOpenSavedGameFromData(inputBuffer, bufferSize, true);
				CheckQspResult(result, "LoadSlot: QSPOpenSavedGameFromData");
            	
        		libraryThreadIsRunning = false;

        		if (!result)
	    		{
	        		Utility.WriteLog("LoadSlot: failed, cannot load data from byte buffer");
	    			return;
	    		}

        		mainActivity.runOnUiThread(new Runnable() {
	    			public void run() {
	    	    		//Запускаем таймер
	    	            timerHandler.postDelayed(timerUpdateTask, timerInterval);
	    			}
    	    	});
			}
		});
    }
    
    private void SaveSlot(int index)
    {
    	//Контекст UI
    	final String path = curSaveDir.concat(String.valueOf(index)).concat(".sav");

		libThreadHandler.post(new Runnable() {
			public void run() {
	    		if (libraryThreadIsRunning)
	    		{
	        		Utility.WriteLog("SaveSlot: failed, library is already running");
	    			return;
	    		}
            	libraryThreadIsRunning = true;
            	
	    		final String saveFilePath = path;
	        	final byte[] dataToSave = QSPSaveGameAsData(false);
	        	
	        	if (dataToSave == null)
	    		{
					CheckQspResult(false, "SaveSlot: QSPSaveGameAsData");
	        		Utility.WriteLog("SaveSlot: failed, cannot create save data");
	    			return;
	    		}
	    		
	        	mainActivity.runOnUiThread(new Runnable() {
	    			public void run() {
	    				
	    		    	File f = new File(saveFilePath);
	    				
	            		FileOutputStream fileOutput = null;
						try {
							fileOutput = new FileOutputStream(f);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
							return;
						}
            			try {
							fileOutput.write(dataToSave, 0, dataToSave.length);
							fileOutput.close();
						} catch (IOException e) {
							e.printStackTrace();
							return;
						}
	    				
	    	    		//Запускаем таймер
	    	            timerHandler.postDelayed(timerUpdateTask, timerInterval);
	    			}
    	    	});
            	
        		libraryThreadIsRunning = false;
			}
		});
    }
    
    private void PlayFileUI(String file, int volume)
    {
    	//Контекст UI
    	if (file == null || file.length() == 0)
    		return;
    	
    	//Проверяем, проигрывается ли уже этот файл.
    	//Если проигрывается, ничего не делаем.
    	if (IsPlayingFileUI(file, true, volume))
    		return;

    	// Добавляем к имени файла полный путь
    	String prefix = "";
    	if (curGameDir != null)
    		prefix = curGameDir;
    	String assetFilePath = prefix.concat(file);

    	//Проверяем, существует ли файл.
		//Если нет, ничего не делаем.
        if (!Utility.CheckAssetExists(uiContext, assetFilePath, "PlayFileUI"))
        	return;

    	AssetManager assetManager = uiContext.getAssets();
    	if (assetManager == null)
    	{
    		Utility.WriteLog("PlayFileUI: failed, assetManager is null");
    		return;
    	}
    	AssetFileDescriptor afd = null;
    	try {
			afd = assetManager.openFd(assetFilePath);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	if (afd == null)
    		return;
        
    	MediaPlayer mediaPlayer = new MediaPlayer();
	    try {
			mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	    try {
			mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		final String fileName = file;
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
		        musicLock.lock();
		        try {
			    	for (int i=0; i<mediaPlayersList.size(); i++)
			    	{
			    		ContainerMusic it = mediaPlayersList.elementAt(i);    		
			    		if (it.path.equals(fileName))
			    		{
			    			mediaPlayersList.remove(it);
			    			break;
			    		}
			    	}
		        } finally {
		        	musicLock.unlock();
		        }
			}
		});
		
		float realVolume = GetRealVolume(volume);
		mediaPlayer.setVolume(realVolume, realVolume);
	    mediaPlayer.start();
	    ContainerMusic ContainerMusic = new ContainerMusic();
	    ContainerMusic.path = file;
	    ContainerMusic.volume = volume;
	    ContainerMusic.player = mediaPlayer;
        musicLock.lock();
        try {
        	mediaPlayersList.add(ContainerMusic);
        } finally {
        	musicLock.unlock();
        }
    }
    
    private float GetRealVolume(int volume)
    {
    	// Контекст UI
    	float result = 0;
    	if (!muted)
    		result = ((float) volume) / 100;
    	else 
    		result = 0;
    	return result;
    }

    private boolean IsPlayingFileUI(String file, boolean setVolume, int volume)
    {
    	//Контекст UI
    	if (file == null || file.length() == 0)
    		return false;
    	boolean foundPlaying = false; 
        musicLock.lock();
        try {
	    	for (int i=0; i<mediaPlayersList.size(); i++)
	    	{
	    		ContainerMusic it = mediaPlayersList.elementAt(i);
	    		if (it.path.equals(file))
	    		{
	    			if (setVolume)
	    			{
	    				it.volume = volume;
	    				float realVolume = GetRealVolume(volume);
	    				it.player.setVolume(realVolume, realVolume);
	    			}
	    			foundPlaying = true;
	    			break;
	    		}
	    	}
        } finally {
        	musicLock.unlock();
        }
    	return foundPlaying;
    }
    
    private void CloseFileUI(String file)
    {
    	//Контекст UI
    	//Если вместо имени файла пришел null, значит закрываем все файлы(CLOSE ALL)
    	boolean bCloseAll = false;
    	if (file == null)
    		bCloseAll = true;
    	else if (file.length() == 0)
    		return;
        musicLock.lock();
        try {
	    	for (int i=0; i<mediaPlayersList.size(); i++)
	    	{
	    		ContainerMusic it = mediaPlayersList.elementAt(i);    		
	    		if (bCloseAll || it.path.equals(file))
	    		{
	    			if (it.player.isPlaying())
	    				it.player.stop();
	    			it.player.release();
	    			if (!bCloseAll)
	    			{
	    				mediaPlayersList.remove(it);
	    				break;
	    			}
	    		}
	    	}
    		if (bCloseAll)
    			mediaPlayersList.clear();
        } finally {
        	musicLock.unlock();
        }
    }
    
    private void PauseMusic(boolean pause)
    {
    	//Контекст UI
    	//pause == true : приостанавливаем
    	//pause == false : запускаем
        musicLock.lock();
        try {
	    	for (int i=0; i<mediaPlayersList.size(); i++)
	    	{
	    		
	    		ContainerMusic it = mediaPlayersList.elementAt(i);    		
    			if (pause)
    			{
    				if (it.player.isPlaying())
    					it.player.pause();
    			}
    			else
    			{
    				float realVolume = GetRealVolume(it.volume);
    				it.player.setVolume(realVolume, realVolume);
    				it.player.start();
    			}
	    	}
	    } finally {
	    	musicLock.unlock();
	    }
    }
	
	/*
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 * *********************************************************************************
	 */

    // Утилиты

    private String unescapeHtml(String text)
    {
    	if (text == null)
    		return "";
        text = text.replace("&quot;", "\"");
        text = text.replace("&#39;", "'");
        text = text.replace("&lt;", "<");
        text = text.replace("&gt;", ">");
        text = text.replace("&amp;", "&");
        return text;
    }
    
    //******************************************************************************
    //******************************************************************************
    //****** / THREADS \ ***********************************************************
    //******************************************************************************
    //******************************************************************************
    /** паркует-останавливает указанный тред, и сохраняет на него указатель в parkThread */
    protected void setThreadPark()    {
    	Utility.WriteLog("setThreadPark: enter ");    	
    	//Контекст библиотеки
    	if (libThread == null)
    	{
    		Utility.WriteLog("setThreadPark: failed, libthread is null");
    		return;
    	}
        parkThread = libThread;
        LockSupport.park();
    	Utility.WriteLog("setThreadPark: success ");    	
    }
    
    /** возобновляет работу треда сохраненного в указателе parkThread */
    protected boolean setThreadUnpark()    {
    	Utility.WriteLog("setThreadUnPark: enter ");
    	//Контекст UI
        if (parkThread!=null && parkThread.isAlive()) {
            LockSupport.unpark(parkThread);
        	Utility.WriteLog("setThreadUnPark: success ");    	
            return true;
        }
    	Utility.WriteLog("setThreadUnPark: failed, ");
    	if (parkThread==null)
        	Utility.WriteLog("parkThread is null ");
    	else
        	Utility.WriteLog("parkThread is dead ");
        return false;
    }
    
    protected void StartLibThread()
    {
    	Utility.WriteLog("StartLibThread: enter ");    	
    	//Контекст UI
    	if (libThread != null)
    	{
        	Utility.WriteLog("StartLibThread: failed, libThread is not null");    	
    		return;
    	}
    	//Запускаем поток библиотеки
    	Thread t = new Thread() {
            public void run() {
    			Looper.prepare();
    			libThreadHandler = new Handler();
    			Utility.WriteLog("LibThread runnable: libThreadHandler is set");    	

    			// Сообщаем яваскрипту, что библиотека запущена
    			// и можно продолжить инициализацию
    	        libThreadHandler.post(new Runnable() {
    	    		public void run() {
    	        		mainActivity.runOnUiThread(new Runnable() {
    	        			public void run() {
    	    	            	jsInitNext();
    	        			}
    	        		});
    	    		}
    	        });
    			
    			Looper.loop();
            	Utility.WriteLog("LibThread runnable: library thread exited");
            }
        };
        libThread = t;
        t.start();
    	Utility.WriteLog("StartLibThread: success");
    }
    
    protected void StopLibThread()
    {
    	Utility.WriteLog("StopLibThread: enter");    	
    	//Контекст UI
    	//Останавливаем поток библиотеки
       	libThreadHandler.getLooper().quit();
		libThread = null;
    	Utility.WriteLog("StopLibThread: success");    	
    }
    //******************************************************************************
    //******************************************************************************
    //****** \ THREADS / ***********************************************************
    //******************************************************************************
    //******************************************************************************

    //Хэндлер для потока библиотеки
    private Handler libThreadHandler;
    
    //Поток библиотеки
    private Thread libThread;
    private Thread parkThread;
    
    //Запущен ли поток библиотеки
    boolean					libraryThreadIsRunning = false;
 
    //Есть ответ от MessageBox, InputBox либо Menu
    private boolean 		dialogHasResult;
    String					inputboxResult;
    int						menuResult;
    AlertDialog				inputboxDialog;
    
    // мы ждем ответа от JS диалога(на данный момент - только диалог слотов сохранений)
    private boolean 		waitingForJS;

    
    String 					curGameDir;
    String					curGameFile;
    String					curSaveDir;
    Vector<ContainerMusic>	mediaPlayersList;
    Handler					timerHandler;
	long					timerStartTime;
	long					gameStartTime;
	int						timerInterval;
	boolean					gameIsRunning;
	boolean					qspInited;
	Vector<ContainerMenuItem>		menuList;
    
    
    //control
    public native void 		QSPInit();
    public native void 		QSPDeInit();
    public native boolean 	QSPIsInCallBack();
    public native void 		QSPEnableDebugMode(boolean isDebug);
    public native Object 	QSPGetCurStateData();//!!!STUB
    public native String 	QSPGetVersion();
    public native int 		QSPGetFullRefreshCount();
    public native String 	QSPGetQstFullPath();
    public native String 	QSPGetCurLoc();
    public native String 	QSPGetMainDesc();
    public native boolean 	QSPIsMainDescChanged();
    public native String 	QSPGetVarsDesc();
    public native boolean 	QSPIsVarsDescChanged();
    public native Object 	QSPGetExprValue();//!!!STUB
    public native void 		QSPSetInputStrText(String val);
    public native int 		QSPGetActionsCount();
    public native Object 	QSPGetActionData(int ind);
    public native boolean 	QSPExecuteSelActionCode(boolean isRefresh);
    public native boolean 	QSPSetSelActionIndex(int ind, boolean isRefresh);
    public native int 		QSPGetSelActionIndex();
    public native boolean 	QSPIsActionsChanged();
    public native int 		QSPGetObjectsCount();
    public native Object 	QSPGetObjectData(int ind);
    public native boolean 	QSPSetSelObjectIndex(int ind, boolean isRefresh);
    public native int 		QSPGetSelObjectIndex();
    public native boolean 	QSPIsObjectsChanged();
    public native void 		QSPShowWindow(int type, boolean isShow);
    public native Object 	QSPGetVarValuesCount(String name);//!!!STUB
    public native Object 	QSPGetVarValues(String name, int ind, int numVal, String strVal);
    public native int 		QSPGetMaxVarsCount();
    public native Object 	QSPGetVarNameByIndex(int index);//!!!STUB
    public native boolean 	QSPExecString(String s, boolean isRefresh);
    public native boolean 	QSPExecLocationCode(String name, boolean isRefresh);
    public native boolean 	QSPExecCounter(boolean isRefresh);
    public native boolean 	QSPExecUserInput(boolean isRefresh);
    public native Object	QSPGetLastErrorData();
    public native String 	QSPGetErrorDesc(int errorNum);
    public native boolean 	QSPLoadGameWorld(String fileName);
    public native boolean 	QSPLoadGameWorldFromData(byte data[], int dataSize, String fileName);
    public native boolean 	QSPSaveGame(String fileName, boolean isRefresh);
    public native byte[] 	QSPSaveGameAsData(boolean isRefresh);
    public native boolean 	QSPOpenSavedGame(String fileName, boolean isRefresh);
    public native boolean 	QSPOpenSavedGameFromData(byte data[], int dataSize, boolean isRefresh);
    public native boolean 	QSPRestartGame(boolean isRefresh);
    //public native void QSPSetCallBack(int type, QSP_CALLBACK func) 

    static {
    	Utility.WriteLog("Loading QSP native library...");
	    System.loadLibrary("ndkqsp");
    	Utility.WriteLog("QSP native library is loaded now");
	}
}


