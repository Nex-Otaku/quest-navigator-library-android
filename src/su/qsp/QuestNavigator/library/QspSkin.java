package su.qsp.QuestNavigator.library;

import org.json.JSONException;
import org.json.JSONObject;

public class QspSkin {
	/*
	 * Все переменные скина
	 */
	private int useHtml;
	private int disableScroll;
	private String upArrowImagePath;
	private String downArrowImagePath;
	private String mainBackImagePath;
	private String mainTopImagePath;
	private String sysMenuButtonImagePath;
	private int sysMenuButtonX;
	private int sysMenuButtonY;
	private int backColor;
	private int linkColor;
	private int fontColor;
	private String fontName;
	private int fontSize;
	private String styleSheet;
	private int disableShade;
	private int scrollSpeed;
	private int hideScrollMain;
	private int hideScrollActs;
	private int hideScrollVars;
	private int hideScrollObjs;
	private int hideScrollAny;
	private int hideScrollArrows;
	
	public int disableAutoRef;

	private String newLocEffect;
	private int newLocEffectTime;
	private int newLocEffectSeq;
	private String viewEffect;
	private int viewEffectTime;
	private String inputEffect;
	private int inputEffectTime;
	private String msgEffect;
	private int msgEffectTime;
	private String menuEffect;
	private int menuEffectTime;
	
	private String mainDescTextFormat;
	private int mainDescIntegratedActions;
	private String mainDescBackImagePath;
	private int mainDescX;
	private int mainDescY;
	private int mainDescW;
	private int mainDescH;
	
	private String varsDescTextFormat;
	private String varsDescBackImagePath;
	private int varsDescX;
	private int varsDescY;
	private int varsDescW;
	private int varsDescH;
	
	private String actsListItemFormat;
	private String actsListSelItemFormat;
	private String actsListBackImagePath;
	private int actsListX;
	private int actsListY;
	private int actsListW;
	private int actsListH;
	
	private String objsListItemFormat;
	private String objsListSelItemFormat;
	private String objsListBackImagePath;
	private int objsListX;
	private int objsListY;
	private int objsListW;
	private int objsListH;
	
	private int viewAlwaysShow;
	private int viewX;
	private int viewY;
	private int viewW;
	private int viewH;
	
	private int userInputX;
	private int userInputY;
	private int userInputW;
	private int userInputH;
	
	private String inputBackImagePath;
	private int inputX;
	private int inputY;
	private String inputTextFormat;
	private int inputTextX;
	private int inputTextY;
	private int inputTextW;
	private int inputTextH;
	private int inputBarX;
	private int inputBarY;
	private int inputBarW;
	private int inputBarH;
	private String inputOkImagePath;
	private int inputOkX;
	private int inputOkY;
	private String inputCancelImagePath;
	private int inputCancelX;
	private int inputCancelY;
	
	private int menuFixedSize;
	private int menuBorder;
	private int menuBorderColor;
	private int menuPadding;
	private String menuBackImagePath;
	private int menuX;
	private int menuY;
	private String menuListItemFormat;
	private String menuListSelItemFormat;
	private int menuListX;
	private int menuListY;
	private int menuListW;
	private int menuListH;
	
	private String msgBackImagePath;
	private int msgX;
	private int msgY;
	private String msgTextFormat;
	private int msgTextX;
	private int msgTextY;
	private int msgTextW;
	private int msgTextH;
	private String msgOkImagePath;
	private int msgOkX;
	private int msgOkY;
	
	private int showActs;
	private int showObjs;
	private int showVars;
	private int showInput;
	
	private boolean firstUpdate;
	public boolean isChanged;
	public boolean isMainBackChanged;
	public boolean isActionsStyleChanged;
	public boolean isHtmlModeChanged;
	public boolean isBaseVarsChanged;
	public boolean isSoftBaseVarsChanged;
	public boolean isMainDescChanged;
	public boolean isSoftMainDescChanged;
	public boolean isVarsDescChanged;
	public boolean isSoftVarsDescChanged;
	public boolean isActsListChanged;
	public boolean isSoftActsListChanged;
	public boolean isObjsListChanged;
	public boolean isSoftObjsListChanged;
	public boolean isViewChanged;
	public boolean isUserInputChanged;
	
	private QspLib qspLib;
	/*
	 * Значения по умолчанию 
	 */
	private static final int default_useHtml = 0;
	private static final int default_disableScroll = 0;
	private static final String default_upArrowImagePath = "";
	private static final String default_downArrowImagePath = "";
	private static final String default_mainBackImagePath = "";
	private static final String default_mainTopImagePath = "";
	private static final String default_sysMenuButtonImagePath = "";
	private static final int default_sysMenuButtonX = -1;
	private static final int default_sysMenuButtonY = -1;
	private static final int default_backColor = 0xE5E5E5;
	private static final int default_linkColor = 0xFF0000;
	private static final int default_fontColor = 0x000000;
	private static final String default_fontName = "_sans";
	private static final int default_fontSize = 18;
	private static final String default_styleSheet = "";
	private static final int default_disableShade = 0;
	private static final int default_scrollSpeed = 20;
	private static final int default_hideScrollMain = 0;
	private static final int default_hideScrollActs = 0;
	private static final int default_hideScrollVars = 0;
	private static final int default_hideScrollObjs = 0;
	private static final int default_hideScrollAny = 0;
	private static final int default_hideScrollArrows = 0;
	private static final int default_disableAutoRef = 0;
	// ----------------------
	private static final String default_newLocEffect = "";
	private static final int default_newLocEffectTime = 500;
	private static final int default_newLocEffectSeq = 0;
	private static final String default_viewEffect = "";
	private static final int default_viewEffectTime = 500;
	private static final String default_inputEffect = "";
	private static final int default_inputEffectTime = 500;
	private static final String default_msgEffect = "";
	private static final int default_msgEffectTime = 500;
	private static final String default_menuEffect = "";
	private static final int default_menuEffectTime = 500;
	// ----------------------
	private static final String default_mainDescTextFormat = "%TEXT%";
	private static final int default_mainDescIntegratedActions = 0;
	private static final String default_mainDescBackImagePath = "";
	private static final int default_mainDescX = 4;
	private static final int default_mainDescY = 4;
	private static final int default_mainDescW = 589;
	private static final int default_mainDescH = 389;
	// ----------------------
	private static final String default_varsDescTextFormat = "%TEXT%";
	private static final String default_varsDescBackImagePath = "";
	private static final int default_varsDescX = 596;
	private static final int default_varsDescY = 396;
	private static final int default_varsDescW = 200;
	private static final int default_varsDescH = 200;
	// ----------------------
	private static final String default_actsListItemFormat = "<table><tr><td><img src='%IMAGE%'/></td><td style='width:100%;'>%TEXT%</td></tr></table>";
	private static final String default_actsListSelItemFormat = "<table><tr><td><img src='%IMAGE%'/></td><td style='width:100%;color:#0000FF;'>%TEXT%</td></tr></table>";
	private static final String default_actsListBackImagePath = "";
	private static final int default_actsListX = 4;
	private static final int default_actsListY = 396;
	private static final int default_actsListW = 589;
	private static final int default_actsListH = 169;
	// ----------------------
	private static final String default_objsListItemFormat = "<table><tr><td><img src='%IMAGE%'/></td><td style='width:100%;'>%TEXT%</td></tr></table>";
	private static final String default_objsListSelItemFormat = "<table><tr><td><img src='%IMAGE%'/></td><td style='width:100%;color:#0000FF;'>%TEXT%</td></tr></table>";
	private static final String default_objsListBackImagePath = "";
	private static final int default_objsListX = 596;
	private static final int default_objsListY = 4;
	private static final int default_objsListW = 200;
	private static final int default_objsListH = 389;
	// ----------------------
	private static final int default_viewAlwaysShow = 0;
	private static final int default_viewX = 250;
	private static final int default_viewY = 150;
	private static final int default_viewW = 300;
	private static final int default_viewH = 300;
	// ----------------------
	private static final int default_userInputX = 4;
	private static final int default_userInputY = 568;
	private static final int default_userInputW = 589;
	private static final int default_userInputH = 28;
	// ----------------------
	private static final String default_inputBackImagePath = "";
	private static final int default_inputX = 200;
	private static final int default_inputY = 165;
	private static final String default_inputTextFormat = "%TEXT%";
	private static final int default_inputTextX = 4;
	private static final int default_inputTextY = 4;
	private static final int default_inputTextW = 392;
	private static final int default_inputTextH = 231;
	private static final int default_inputBarX = 4;
	private static final int default_inputBarY = 238;
	private static final int default_inputBarW = 312;
	private static final int default_inputBarH = 28;
	private static final String default_inputOkImagePath = "";
	private static final int default_inputOkX = 324;
	private static final int default_inputOkY = 239;
	private static final String default_inputCancelImagePath = "";
	private static final int default_inputCancelX = 362;
	private static final int default_inputCancelY = 239;
	// ----------------------
	private static final int default_menuFixedSize = 0;
	private static final int default_menuBorder = 1;
	private static final int default_menuBorderColor = 0x404040;
	private static final int default_menuPadding = 4;
	private static final String default_menuBackImagePath = "";
	private static final int default_menuX = -1;
	private static final int default_menuY = -1;
	private static final String default_menuListItemFormat = "<table><tr><td><img src='%IMAGE%'/></td><td style='width:100%;'>%TEXT%</td></tr></table>";
	private static final String default_menuListSelItemFormat = "<table><tr><td><img src='%IMAGE%'/></td><td style='width:100%;color:#0000FF;'>%TEXT%</td></tr></table>";
	private static final int default_menuListX = 4;
	private static final int default_menuListY = 4;
	private static final int default_menuListW = 153;
	private static final int default_menuListH = 123;
	// ----------------------
	private static final String default_msgBackImagePath = "";
	private static final int default_msgX = 200;
	private static final int default_msgY = 165;
	private static final String default_msgTextFormat = "%TEXT%";
	private static final int default_msgTextX = 4;
	private static final int default_msgTextY = 4;
	private static final int default_msgTextW = 392;
	private static final int default_msgTextH = 231;
	private static final String default_msgOkImagePath = "";
	private static final int default_msgOkX = 186;
	private static final int default_msgOkY = 239;
	// ----------------------
	private static final int default_showActs = 1;
	private static final int default_showObjs = 1;
	private static final int default_showVars = 1;
	private static final int default_showInput = 1;
	
	// Константы из файла библиотеки - jni/qsp/qsp.h
	private static final int QSP_WIN_ACTS = 0;
	private static final int QSP_WIN_OBJS = 1;
	private static final int QSP_WIN_VARS = 2;
	private static final int QSP_WIN_INPUT = 3;
	
	QspSkin(QspLib lib)
	{
		qspLib = lib;
		resetUpdate();
		resetSettings();
	}
	
	private void resetSettings()
	{
	    useHtml = default_useHtml;
	    disableScroll = default_disableScroll;
	    upArrowImagePath = default_upArrowImagePath;
	    downArrowImagePath = default_downArrowImagePath;
	    mainBackImagePath = default_mainBackImagePath;
	    mainTopImagePath = default_mainTopImagePath;
	    sysMenuButtonImagePath = default_sysMenuButtonImagePath;
	    sysMenuButtonX = default_sysMenuButtonX;
	    sysMenuButtonY = default_sysMenuButtonY;
	    backColor = default_backColor;
	    linkColor = default_linkColor;
	    fontColor = default_fontColor;
	    fontName = default_fontName;
	    fontSize = default_fontSize;
	    styleSheet = default_styleSheet;
	    disableShade = default_disableShade;
	    scrollSpeed = default_scrollSpeed;
	    hideScrollMain = default_hideScrollMain;
	    hideScrollActs = default_hideScrollActs;
	    hideScrollVars = default_hideScrollVars;
	    hideScrollObjs = default_hideScrollObjs;
	    hideScrollAny = default_hideScrollAny;
	    hideScrollArrows = default_hideScrollArrows;
	    disableAutoRef = default_disableAutoRef;
	    // ----------------------
	    newLocEffect = default_newLocEffect;
	    newLocEffectTime = default_newLocEffectTime;
	    newLocEffectSeq = default_newLocEffectSeq;
	    viewEffect = default_viewEffect;
	    viewEffectTime = default_viewEffectTime;
	    inputEffect = default_inputEffect;
	    inputEffectTime = default_inputEffectTime;
	    msgEffect = default_msgEffect;
	    msgEffectTime = default_msgEffectTime;
	    menuEffect = default_menuEffect;
	    menuEffectTime = default_menuEffectTime;
	    // ----------------------
	    mainDescTextFormat = default_mainDescTextFormat;
	    mainDescIntegratedActions = default_mainDescIntegratedActions;
	    mainDescBackImagePath = default_mainDescBackImagePath;
	    mainDescX = default_mainDescX;
	    mainDescY = default_mainDescY;
	    mainDescW = default_mainDescW;
	    mainDescH = default_mainDescH;
	    // ----------------------
	    varsDescTextFormat = default_varsDescTextFormat;
	    varsDescBackImagePath = default_varsDescBackImagePath;
	    varsDescX = default_varsDescX;
	    varsDescY = default_varsDescY;
	    varsDescW = default_varsDescW;
	    varsDescH = default_varsDescH;
	    // ----------------------
	    actsListItemFormat = default_actsListItemFormat;
	    actsListSelItemFormat = default_actsListSelItemFormat;
	    actsListBackImagePath = default_actsListBackImagePath;
	    actsListX = default_actsListX;
	    actsListY = default_actsListY;
	    actsListW = default_actsListW;
	    actsListH = default_actsListH;
	    // ----------------------
	    objsListItemFormat = default_objsListItemFormat;
	    objsListSelItemFormat = default_objsListSelItemFormat;
	    objsListBackImagePath = default_objsListBackImagePath;
	    objsListX = default_objsListX;
	    objsListY = default_objsListY;
	    objsListW = default_objsListW;
	    objsListH = default_objsListH;
	    // ----------------------
	    viewAlwaysShow = default_viewAlwaysShow;
	    viewX = default_viewX;
	    viewY = default_viewY;
	    viewW = default_viewW;
	    viewH = default_viewH;
	    // ----------------------
	    userInputX = default_userInputX;
	    userInputY = default_userInputY;
	    userInputW = default_userInputW;
	    userInputH = default_userInputH;
	    // ----------------------
	    inputBackImagePath = default_inputBackImagePath;
	    inputX = default_inputX;
	    inputY = default_inputY;
	    inputTextFormat = default_inputTextFormat;
	    inputTextX = default_inputTextX;
	    inputTextY = default_inputTextY;
	    inputTextW = default_inputTextW;
	    inputTextH = default_inputTextH;
	    inputBarX = default_inputBarX;
	    inputBarY = default_inputBarY;
	    inputBarW = default_inputBarW;
	    inputBarH = default_inputBarH;
	    inputOkImagePath = default_inputOkImagePath;
	    inputOkX = default_inputOkX;
	    inputOkY = default_inputOkY;
	    inputCancelImagePath = default_inputCancelImagePath;
	    inputCancelX = default_inputCancelX;
	    inputCancelY = default_inputCancelY;
	    // ----------------------
	    menuFixedSize = default_menuFixedSize;
	    menuBorder = default_menuBorder;
	    menuBorderColor = default_menuBorderColor;
	    menuPadding = default_menuPadding;
	    menuBackImagePath = default_menuBackImagePath;
	    menuX = default_menuX;
	    menuY = default_menuY;
	    menuListItemFormat = default_menuListItemFormat;
	    menuListSelItemFormat = default_menuListSelItemFormat;
	    menuListX = default_menuListX;
	    menuListY = default_menuListY;
	    menuListW = default_menuListW;
	    menuListH = default_menuListH;
	    // ----------------------
	    msgBackImagePath = default_msgBackImagePath;
	    msgX = default_msgX;
	    msgY = default_msgY;
	    msgTextFormat = default_msgTextFormat;
	    msgTextX = default_msgTextX;
	    msgTextY = default_msgTextY;
	    msgTextW = default_msgTextW;
	    msgTextH = default_msgTextH;
	    msgOkImagePath = default_msgOkImagePath;
	    msgOkX = default_msgOkX;
	    msgOkY = default_msgOkY;
	    // ----------------------
	    showActs = default_showActs;
	    showObjs = default_showObjs;
	    showVars = default_showVars;
	    showInput = default_showInput;
	    
	    firstUpdate = true;
	}
	
	public void resetUpdate()
	{
	    firstUpdate = false;
	    isChanged = false;
	    isMainBackChanged = false;
	    isActionsStyleChanged = false;
	    isHtmlModeChanged = false;
	    isBaseVarsChanged = false;
	    isSoftBaseVarsChanged = false;
	    isMainDescChanged = false;
	    isSoftMainDescChanged = false;
	    isVarsDescChanged = false;
	    isSoftVarsDescChanged = false;
	    isActsListChanged = false;
	    isSoftActsListChanged = false;
	    isObjsListChanged = false;
	    isSoftObjsListChanged = false;
	    isViewChanged = false;
	    isUserInputChanged = false;
	}
	
	private String getNewStrValue(String curVal, String varName, String defValue)
	{
	    String val = "";
		ContainerJniResult strResult = (ContainerJniResult) qspLib.QSPGetVarValues(varName, 0, 0, "");
		if (strResult == null)
		{
			Utility.WriteLog("ERROR: getNewStrValue failed for: " + varName);
			return defValue;
		}
		if (!strResult.success)
			return defValue;
		val = strResult.str1;
		// В Java нельзя просто написать (s1 != s2) для строк, нужно обязательно сравнивать строки через equal()
		if (!val.equals(curVal)) 
		{
			isChanged = true;
			return val;
		}
		return curVal;
	}

	private int getNewNumValue(int curVal, String varName, int defValue)
	{
	    int val = 0;
		ContainerJniResult numResult = (ContainerJniResult) qspLib.QSPGetVarValues(varName, 0, 0, "");
		if (numResult == null)
		{
			Utility.WriteLog("ERROR: getNewStrValue failed for: " + varName);
			return defValue;
		}
		if (!numResult.success)
			return defValue;
		val = numResult.int1;
		if (val != curVal)
		{
			isChanged = true;
			return val;
		}
		return curVal;
	}

	public void updateBaseVars()
	{
	    // Soft
	    isChanged = false;
	    scrollSpeed = getNewNumValue(scrollSpeed, "SCROLL_SPEED", default_scrollSpeed);
	    if (isChanged) isSoftBaseVarsChanged = true;
	    // Forced
	    isChanged = false;
	    upArrowImagePath = getNewStrValue(upArrowImagePath, "UP_ARROW_IMAGE", default_upArrowImagePath);
	    downArrowImagePath = getNewStrValue(downArrowImagePath, "DOWN_ARROW_IMAGE", default_downArrowImagePath);
	    hideScrollMain = getNewNumValue(hideScrollMain, "HIDE_MAIN_SCROLL", default_hideScrollMain);
	    hideScrollActs = getNewNumValue(hideScrollActs, "HIDE_ACTS_SCROLL", default_hideScrollActs);
	    hideScrollVars = getNewNumValue(hideScrollVars, "HIDE_STAT_SCROLL", default_hideScrollVars);
	    hideScrollObjs = getNewNumValue(hideScrollObjs, "HIDE_OBJS_SCROLL", default_hideScrollObjs);
	    hideScrollAny = getNewNumValue(hideScrollAny, "HIDE_ALL_SCROLLS", default_hideScrollAny);
	    hideScrollArrows = getNewNumValue(hideScrollArrows, "HIDE_SCROLL_ARROWS", default_hideScrollArrows);
	    linkColor = getNewNumValue(linkColor, "LCOLOR", default_linkColor);
	    fontColor = getNewNumValue(fontColor, "FCOLOR", default_fontColor);
	    fontName = getNewStrValue(fontName, "FNAME", default_fontName);
	    fontSize = getNewNumValue(fontSize, "FSIZE", default_fontSize);
	    styleSheet = getNewStrValue(styleSheet, "STYLESHEET", default_styleSheet);
	    if (isChanged) isBaseVarsChanged = true;
	    // -----------
	    isChanged = false;
	    backColor = getNewNumValue(backColor, "BCOLOR", default_backColor);
	    if (isChanged) isMainBackChanged = true;
	    // -----------
	    isChanged = false;
	    useHtml = getNewNumValue(useHtml, "USEHTML", default_useHtml);
	    if (isChanged) isHtmlModeChanged = true;
	    // Others
	    disableShade = getNewNumValue(disableShade, "DISABLESHADE", default_disableShade);
	    disableScroll = getNewNumValue(disableScroll, "DISABLESCROLL", default_disableScroll);
	    disableAutoRef = getNewNumValue(disableAutoRef, "DISABLEAUTOREF", default_disableAutoRef);
	}

	public void updateEffects()
	{
	    newLocEffect = getNewStrValue(newLocEffect, "NEWLOC_EFFECT", default_newLocEffect);
	    newLocEffectTime = getNewNumValue(newLocEffectTime, "NEWLOC_EFFECT_TIME", default_newLocEffectTime);
	    newLocEffectSeq = getNewNumValue(newLocEffectSeq, "NEWLOC_EFFECT_SEQ", default_newLocEffectSeq);
	    viewEffect = getNewStrValue(viewEffect, "VIEW_EFFECT", default_viewEffect);
	    viewEffectTime = getNewNumValue(viewEffectTime, "VIEW_EFFECT_TIME", default_viewEffectTime);
	    inputEffect = getNewStrValue(inputEffect, "INPUT_EFFECT", default_inputEffect);
	    inputEffectTime = getNewNumValue(inputEffectTime, "INPUT_EFFECT_TIME", default_inputEffectTime);
	    msgEffect = getNewStrValue(msgEffect, "MSG_EFFECT", default_msgEffect);
	    msgEffectTime = getNewNumValue(msgEffectTime, "MSG_EFFECT_TIME", default_msgEffectTime);
	    menuEffect = getNewStrValue(menuEffect, "MENU_EFFECT", default_menuEffect);
	    menuEffectTime = getNewNumValue(menuEffectTime, "MENU_EFFECT_TIME", default_menuEffectTime);
	}

	public void updateMainScreen()
	{
	    isChanged = false;
	    mainBackImagePath = getNewStrValue(mainBackImagePath, "BACKIMAGE", default_mainBackImagePath);
	    mainTopImagePath = getNewStrValue(mainTopImagePath, "TOPIMAGE", default_mainTopImagePath);
	    sysMenuButtonImagePath = getNewStrValue(sysMenuButtonImagePath, "SYSMENU_BUTTON_IMAGE", default_sysMenuButtonImagePath);
	    sysMenuButtonX = getNewNumValue(sysMenuButtonX, "SYSMENU_BUTTON_X", default_sysMenuButtonX);
	    sysMenuButtonY = getNewNumValue(sysMenuButtonY, "SYSMENU_BUTTON_Y", default_sysMenuButtonY);
	    if (isChanged) isMainBackChanged = true;
	    // ----------------------
	    isChanged = false;
	    mainDescIntegratedActions = getNewNumValue(mainDescIntegratedActions, "INTEGRATED_ACTIONS", default_mainDescIntegratedActions);
	    if (isChanged) isActionsStyleChanged = true;
	    // ----------------------
	    // Soft
	    isChanged = false;
	    mainDescBackImagePath = getNewStrValue(mainDescBackImagePath, "MAINDESC_BACKIMAGE", default_mainDescBackImagePath);
	    mainDescX = getNewNumValue(mainDescX, "MAINDESC_X", default_mainDescX);
	    mainDescY = getNewNumValue(mainDescY, "MAINDESC_Y", default_mainDescY);
	    mainDescH = getNewNumValue(mainDescH, "MAINDESC_H", default_mainDescH);
	    if (isChanged) isSoftMainDescChanged = true;
	    // Forced
	    isChanged = false;
	    mainDescTextFormat = getNewStrValue(mainDescTextFormat, "MAIN_FORMAT", default_mainDescTextFormat);
	    mainDescW = getNewNumValue(mainDescW, "MAINDESC_W", default_mainDescW);
	    if (isChanged) isMainDescChanged = true;
	    // ----------------------
	    // Soft
	    isChanged = false;
	    varsDescBackImagePath = getNewStrValue(varsDescBackImagePath, "STATDESC_BACKIMAGE", default_varsDescBackImagePath);
	    varsDescX = getNewNumValue(varsDescX, "STATDESC_X", default_varsDescX);
	    varsDescY = getNewNumValue(varsDescY, "STATDESC_Y", default_varsDescY);
	    varsDescH = getNewNumValue(varsDescH, "STATDESC_H", default_varsDescH);
	    if (isChanged) isSoftVarsDescChanged = true;
	    // Forced
	    isChanged = false;
	    varsDescTextFormat = getNewStrValue(varsDescTextFormat, "STAT_FORMAT", default_varsDescTextFormat);
	    varsDescW = getNewNumValue(varsDescW, "STATDESC_W", default_varsDescW);
	    if (isChanged) isVarsDescChanged = true;
	    // ----------------------
	    // Soft
	    isChanged = false;
	    actsListBackImagePath = getNewStrValue(actsListBackImagePath, "ACTIONS_BACKIMAGE", default_actsListBackImagePath);
	    actsListX = getNewNumValue(actsListX, "ACTIONS_X", default_actsListX);
	    actsListY = getNewNumValue(actsListY, "ACTIONS_Y", default_actsListY);
	    actsListH = getNewNumValue(actsListH, "ACTIONS_H", default_actsListH);
	    if (isChanged) isSoftActsListChanged = true;
	    // Forced
	    isChanged = false;
	    actsListItemFormat = getNewStrValue(actsListItemFormat, "ACTION_FORMAT", default_actsListItemFormat);
	    actsListSelItemFormat = getNewStrValue(actsListSelItemFormat, "SEL_ACTION_FORMAT", default_actsListSelItemFormat);
	    actsListW = getNewNumValue(actsListW, "ACTIONS_W", default_actsListW);
	    if (isChanged) isActsListChanged = true;
	    // ----------------------
	    // Soft
	    isChanged = false;
	    objsListBackImagePath = getNewStrValue(objsListBackImagePath, "OBJECTS_BACKIMAGE", default_objsListBackImagePath);
	    objsListX = getNewNumValue(objsListX, "OBJECTS_X", default_objsListX);
	    objsListY = getNewNumValue(objsListY, "OBJECTS_Y", default_objsListY);
	    objsListH = getNewNumValue(objsListH, "OBJECTS_H", default_objsListH);
	    if (isChanged) isSoftObjsListChanged = true;
	    // Forced
	    isChanged = false;
	    objsListItemFormat = getNewStrValue(objsListItemFormat, "OBJECT_FORMAT", default_objsListItemFormat);
	    objsListSelItemFormat = getNewStrValue(objsListSelItemFormat, "SEL_OBJECT_FORMAT", default_objsListSelItemFormat);
	    objsListW = getNewNumValue(objsListW, "OBJECTS_W", default_objsListW);
	    if (isChanged) isObjsListChanged = true;
	    // ----------------------
	    isChanged = false;
	    viewX = getNewNumValue(viewX, "VIEW_X", default_viewX);
	    viewY = getNewNumValue(viewY, "VIEW_Y", default_viewY);
	    viewW = getNewNumValue(viewW, "VIEW_W", default_viewW);
	    viewH = getNewNumValue(viewH, "VIEW_H", default_viewH);
	    if (isChanged) isViewChanged = true;
	    // ----------------------
	    isChanged = false;
	    userInputX = getNewNumValue(userInputX, "USERINPUT_X", default_userInputX);
	    userInputY = getNewNumValue(userInputY, "USERINPUT_Y", default_userInputY);
	    userInputW = getNewNumValue(userInputW, "USERINPUT_W", default_userInputW);
	    userInputH = getNewNumValue(userInputH, "USERINPUT_H", default_userInputH);
	    if (isChanged) isUserInputChanged = true;
	    // Others
	    viewAlwaysShow = getNewNumValue(viewAlwaysShow, "ALWAYS_SHOW_VIEW", default_viewAlwaysShow);
	}

	public void updateInputDialog()
	{
	    inputBackImagePath = getNewStrValue(inputBackImagePath, "INPUT_BACKIMAGE", default_inputBackImagePath);
	    inputX = getNewNumValue(inputX, "INPUT_X", default_inputX);
	    inputY = getNewNumValue(inputY, "INPUT_Y", default_inputY);
	    inputTextFormat = getNewStrValue(inputTextFormat, "INPUT_FORMAT", default_inputTextFormat);
	    inputTextX = getNewNumValue(inputTextX, "INPUT_TEXT_X", default_inputTextX);
	    inputTextY = getNewNumValue(inputTextY, "INPUT_TEXT_Y", default_inputTextY);
	    inputTextW = getNewNumValue(inputTextW, "INPUT_TEXT_W", default_inputTextW);
	    inputTextH = getNewNumValue(inputTextH, "INPUT_TEXT_H", default_inputTextH);
	    inputBarX = getNewNumValue(inputBarX, "INPUT_BAR_X", default_inputBarX);
	    inputBarY = getNewNumValue(inputBarY, "INPUT_BAR_Y", default_inputBarY);
	    inputBarW = getNewNumValue(inputBarW, "INPUT_BAR_W", default_inputBarW);
	    inputBarH = getNewNumValue(inputBarH, "INPUT_BAR_H", default_inputBarH);
	    inputOkImagePath = getNewStrValue(inputOkImagePath, "INPUT_OK_IMAGE", default_inputOkImagePath);
	    inputOkX = getNewNumValue(inputOkX, "INPUT_OK_X", default_inputOkX);
	    inputOkY = getNewNumValue(inputOkY, "INPUT_OK_Y", default_inputOkY);
	    inputCancelImagePath = getNewStrValue(inputCancelImagePath, "INPUT_CANCEL_IMAGE", default_inputCancelImagePath);
	    inputCancelX = getNewNumValue(inputCancelX, "INPUT_CANCEL_X", default_inputCancelX);
	    inputCancelY = getNewNumValue(inputCancelY, "INPUT_CANCEL_Y", default_inputCancelY);
	}

	public void updateMsgDialog()
	{
	    msgBackImagePath = getNewStrValue(msgBackImagePath, "MSG_BACKIMAGE", default_msgBackImagePath);
	    msgX = getNewNumValue(msgX, "MSG_X", default_msgX);
	    msgY = getNewNumValue(msgY, "MSG_Y", default_msgY);
	    msgTextFormat = getNewStrValue(msgTextFormat, "MSG_FORMAT", default_msgTextFormat);
	    msgTextX = getNewNumValue(msgTextX, "MSG_TEXT_X", default_msgTextX);
	    msgTextY = getNewNumValue(msgTextY, "MSG_TEXT_Y", default_msgTextY);
	    msgTextW = getNewNumValue(msgTextW, "MSG_TEXT_W", default_msgTextW);
	    msgTextH = getNewNumValue(msgTextH, "MSG_TEXT_H", default_msgTextH);
	    msgOkImagePath = getNewStrValue(msgOkImagePath, "MSG_OK_IMAGE", default_msgOkImagePath);
	    msgOkX = getNewNumValue(msgOkX, "MSG_OK_X", default_msgOkX);
	    msgOkY = getNewNumValue(msgOkY, "MSG_OK_Y", default_msgOkY);
	}

	public void updateMenuDialog()
	{
	    menuFixedSize = getNewNumValue(menuFixedSize, "FIXED_SIZE_MENU", default_menuFixedSize);
	    menuBorder = getNewNumValue(menuBorder, "MENU_BORDER", default_menuBorder);
	    menuBorderColor = getNewNumValue(menuBorderColor, "MENU_BORDER_COLOR", default_menuBorderColor);
	    menuPadding = getNewNumValue(menuPadding, "MENU_PADDING", default_menuPadding);
	    menuBackImagePath = getNewStrValue(menuBackImagePath, "MENU_BACKIMAGE", default_menuBackImagePath);
	    menuX = getNewNumValue(menuX, "MENU_X", default_menuX);
	    menuY = getNewNumValue(menuY, "MENU_Y", default_menuY);
	    menuListItemFormat = getNewStrValue(menuListItemFormat, "MENU_FORMAT", default_menuListItemFormat);
	    menuListSelItemFormat = getNewStrValue(menuListSelItemFormat, "SEL_MENU_FORMAT", default_menuListSelItemFormat);
	    menuListX = getNewNumValue(menuListX, "MENU_LIST_X", default_menuListX);
	    menuListY = getNewNumValue(menuListY, "MENU_LIST_Y", default_menuListY);
	    menuListW = getNewNumValue(menuListW, "MENU_LIST_W", default_menuListW);
	    menuListH = getNewNumValue(menuListH, "MENU_LIST_H", default_menuListH);
	}

	public JSONObject getJsSkin()
	{
	    //Создаем объект "скин" для передачи в яваскрипт
		JSONObject jsSkin = new JSONObject();
		try {
		    jsSkin.put("useHtml", useHtml);
		    jsSkin.put("disableScroll", Integer.valueOf(disableScroll));
		    jsSkin.put("upArrowImagePath", upArrowImagePath);
		    jsSkin.put("downArrowImagePath", downArrowImagePath);
		    jsSkin.put("mainBackImagePath", mainBackImagePath);
		    jsSkin.put("mainTopImagePath", mainTopImagePath);
		    jsSkin.put("sysMenuButtonImagePath", sysMenuButtonImagePath);
		    jsSkin.put("sysMenuButtonX", Integer.valueOf(sysMenuButtonX));
		    jsSkin.put("sysMenuButtonY", Integer.valueOf(sysMenuButtonY));
		    jsSkin.put("backColor", makeHtmlColor(backColor));
		    jsSkin.put("linkColor", makeHtmlColor(linkColor));
		    jsSkin.put("fontColor", makeHtmlColor(fontColor));
		    jsSkin.put("fontName", fontName);
		    jsSkin.put("fontSize", Integer.valueOf(fontSize));
	//	    jsSkin.put("styleSheet", styleSheet);
		    jsSkin.put("disableShade", Integer.valueOf(disableShade));
		    jsSkin.put("scrollSpeed", Integer.valueOf(scrollSpeed));
		    jsSkin.put("hideScrollMain", Integer.valueOf(hideScrollMain));
		    jsSkin.put("hideScrollActs", Integer.valueOf(hideScrollActs));
		    jsSkin.put("hideScrollVars", Integer.valueOf(hideScrollVars));
		    jsSkin.put("hideScrollObjs", Integer.valueOf(hideScrollObjs));
		    jsSkin.put("hideScrollAny", Integer.valueOf(hideScrollAny));
		    jsSkin.put("hideScrollArrows", Integer.valueOf(hideScrollArrows));
		    jsSkin.put("disableAutoRef", Integer.valueOf(disableAutoRef));
		                                       // ----------------------
		    jsSkin.put("newLocEffect", newLocEffect);
		    jsSkin.put("newLocEffectTime", Integer.valueOf(newLocEffectTime));
		    jsSkin.put("newLocEffectSeq", Integer.valueOf(newLocEffectSeq));
		    jsSkin.put("viewEffect", viewEffect);
		    jsSkin.put("viewEffectTime", Integer.valueOf(viewEffectTime));
		    jsSkin.put("inputEffect", inputEffect);
		    jsSkin.put("inputEffectTime", Integer.valueOf(inputEffectTime));
		    jsSkin.put("msgEffect", msgEffect);
		    jsSkin.put("msgEffectTime", Integer.valueOf(msgEffectTime));
		    jsSkin.put("menuEffect", menuEffect);
		    jsSkin.put("menuEffectTime", Integer.valueOf(menuEffectTime));
		                                       // ----------------------
		    jsSkin.put("mainDescTextFormat", applyHtmlFixes(mainDescTextFormat));
		    jsSkin.put("mainDescIntegratedActions", Integer.valueOf(mainDescIntegratedActions));
		    jsSkin.put("mainDescBackImagePath", mainDescBackImagePath);
		    jsSkin.put("mainDescX", Integer.valueOf(mainDescX));
		    jsSkin.put("mainDescY", Integer.valueOf(mainDescY));
		    jsSkin.put("mainDescW", Integer.valueOf(mainDescW));
		    jsSkin.put("mainDescH", Integer.valueOf(mainDescH));
		                                       // ----------------------
		    jsSkin.put("varsDescTextFormat", applyHtmlFixes(varsDescTextFormat));
		    jsSkin.put("varsDescBackImagePath", varsDescBackImagePath);
		    jsSkin.put("varsDescX", Integer.valueOf(varsDescX));
		    jsSkin.put("varsDescY", Integer.valueOf(varsDescY));
		    jsSkin.put("varsDescW", Integer.valueOf(varsDescW));
		    jsSkin.put("varsDescH", Integer.valueOf(varsDescH));
		                                       // ----------------------
		    jsSkin.put("actsListItemFormat", applyHtmlFixes(actsListItemFormat));
		    jsSkin.put("actsListSelItemFormat", applyHtmlFixes(actsListSelItemFormat));
		    jsSkin.put("actsListBackImagePath", actsListBackImagePath);
		    jsSkin.put("actsListX", Integer.valueOf(actsListX));
		    jsSkin.put("actsListY", Integer.valueOf(actsListY));
		    jsSkin.put("actsListW", Integer.valueOf(actsListW));
		    jsSkin.put("actsListH", Integer.valueOf(actsListH));
		                                       // ----------------------
		    jsSkin.put("objsListItemFormat", applyHtmlFixes(objsListItemFormat));
		    jsSkin.put("objsListSelItemFormat", applyHtmlFixes(objsListSelItemFormat));
		    jsSkin.put("objsListBackImagePath", objsListBackImagePath);
		    jsSkin.put("objsListX", Integer.valueOf(objsListX));
		    jsSkin.put("objsListY", Integer.valueOf(objsListY));
		    jsSkin.put("objsListW", Integer.valueOf(objsListW));
		    jsSkin.put("objsListH", Integer.valueOf(objsListH));
		                                       // ----------------------
		    jsSkin.put("viewAlwaysShow", Integer.valueOf(viewAlwaysShow));
		    jsSkin.put("viewX", Integer.valueOf(viewX));
		    jsSkin.put("viewY", Integer.valueOf(viewY));
		    jsSkin.put("viewW", Integer.valueOf(viewW));
		    jsSkin.put("viewH", Integer.valueOf(viewH));
		                                       // ----------------------
		    jsSkin.put("userInputX", Integer.valueOf(userInputX));
		    jsSkin.put("userInputY", Integer.valueOf(userInputY));
		    jsSkin.put("userInputW", Integer.valueOf(userInputW));
		    jsSkin.put("userInputH", Integer.valueOf(userInputH));
		                                       // ----------------------
		    jsSkin.put("inputBackImagePath", inputBackImagePath);
		    jsSkin.put("inputX", Integer.valueOf(inputX));
		    jsSkin.put("inputY", Integer.valueOf(inputY));
		    jsSkin.put("inputTextFormat", applyHtmlFixes(inputTextFormat));
		    jsSkin.put("inputTextX", Integer.valueOf(inputTextX));
		    jsSkin.put("inputTextY", Integer.valueOf(inputTextY));
		    jsSkin.put("inputTextW", Integer.valueOf(inputTextW));
		    jsSkin.put("inputTextH", Integer.valueOf(inputTextH));
		    jsSkin.put("inputBarX", Integer.valueOf(inputBarX));
		    jsSkin.put("inputBarY", Integer.valueOf(inputBarY));
		    jsSkin.put("inputBarW", Integer.valueOf(inputBarW));
		    jsSkin.put("inputBarH", Integer.valueOf(inputBarH));
		    jsSkin.put("inputOkImagePath", inputOkImagePath);
		    jsSkin.put("inputOkX", Integer.valueOf(inputOkX));
		    jsSkin.put("inputOkY", Integer.valueOf(inputOkY));
		    jsSkin.put("inputCancelImagePath", inputCancelImagePath);
		    jsSkin.put("inputCancelX", Integer.valueOf(inputCancelX));
		    jsSkin.put("inputCancelY", Integer.valueOf(inputCancelY));
		                                       // ----------------------
		    jsSkin.put("menuFixedSize", Integer.valueOf(menuFixedSize));
		    jsSkin.put("menuBorder", Integer.valueOf(menuBorder));
		    jsSkin.put("menuBorderColor", makeHtmlColor(menuBorderColor));
		    jsSkin.put("menuPadding", Integer.valueOf(menuPadding));
		    jsSkin.put("menuBackImagePath", menuBackImagePath);
		    jsSkin.put("menuX", Integer.valueOf(menuX));
		    jsSkin.put("menuY", Integer.valueOf(menuY));
		    jsSkin.put("menuListItemFormat", applyHtmlFixes(menuListItemFormat));
		    jsSkin.put("menuListSelItemFormat", applyHtmlFixes(menuListSelItemFormat));
		    jsSkin.put("menuListX", Integer.valueOf(menuListX));
		    jsSkin.put("menuListY", Integer.valueOf(menuListY));
		    jsSkin.put("menuListW", Integer.valueOf(menuListW));
		    jsSkin.put("menuListH", Integer.valueOf(menuListH));
		                                       // ----------------------
		    jsSkin.put("msgBackImagePath", msgBackImagePath);
		    jsSkin.put("msgX", Integer.valueOf(msgX));
		    jsSkin.put("msgY", Integer.valueOf(msgY));
		    jsSkin.put("msgTextFormat", applyHtmlFixes(msgTextFormat));
		    jsSkin.put("msgTextX", Integer.valueOf(msgTextX));
		    jsSkin.put("msgTextY", Integer.valueOf(msgTextY));
		    jsSkin.put("msgTextW", Integer.valueOf(msgTextW));
		    jsSkin.put("msgTextH", Integer.valueOf(msgTextH));
		    jsSkin.put("msgOkImagePath", msgOkImagePath);
		    jsSkin.put("msgOkX", Integer.valueOf(msgOkX));
		    jsSkin.put("msgOkY", Integer.valueOf(msgOkY));
		    // ----------------------
		    jsSkin.put("showActs", Integer.valueOf(showActs));
		    jsSkin.put("showObjs", Integer.valueOf(showObjs));
		    jsSkin.put("showVars", Integer.valueOf(showVars));
		    jsSkin.put("showInput", Integer.valueOf(showInput));
		    
		    jsSkin.put("contentPath", "");
		} catch (JSONException e) {
    		Utility.WriteLog("ERROR - jsSkin in getJsSkin!");
			e.printStackTrace();
		}
	    
	    
	    //Создаем временный файл, в котором хранится CSS, заданный автором игры в переменной $STYLESHEET
		// пока что это нам не нужно
		/*
	    if (styleSheet.length > 0)
	    {
	        NSError* err;
	        NSString* tempPath = [NSTemporaryDirectory() stringByStandardizingPath];
	        NSString* cssPath = [tempPath stringByAppendingPathComponent:@"temp.css"];
	        BOOL ioResult = [styleSheet writeToFile:cssPath atomically:true encoding:NSUTF8StringEncoding error:&err];
	        if (ioResult)
	        {
	            [jsSkin setValue:cssPath forKey:@"styleSheetFile"];
	        }
	        else
	        {
	            NSLog(@"ERROR writing CSS file to %@\n%@", cssPath, [err localizedFailureReason]);
	        }
	    }
	    */

	    return jsSkin;
	}

	public boolean isSomethingChanged()
	{
	    return firstUpdate ||
	    isChanged ||
	    isMainBackChanged ||
	    isActionsStyleChanged ||
	    isHtmlModeChanged ||
	    isBaseVarsChanged ||
	    isSoftBaseVarsChanged ||
	    isMainDescChanged ||
	    isSoftMainDescChanged ||	
	    isVarsDescChanged ||
	    isSoftVarsDescChanged ||
	    isActsListChanged ||
	    isSoftActsListChanged ||
	    isObjsListChanged ||
	    isSoftObjsListChanged ||
	    isViewChanged ||
	    isUserInputChanged;
	}

	public void showWindow(int type, int isShow)
	{
	    //Контекст библиотеки
	    
	    // Одно из окон требуется скрыть либо показать
	    switch (type)
	    {
	        case QSP_WIN_ACTS:
	            if (showActs != isShow)
	            {
	                showActs = isShow;
	                isActsListChanged = true;
	            }
	            break;
	        case QSP_WIN_OBJS:
	            if (showObjs != isShow)
	            {
	                showObjs = isShow;
	                isObjsListChanged = true;
	            }
	            break;
	        case QSP_WIN_VARS:
	            if (showVars != isShow)
	            {
	                showVars = isShow;
	                isVarsDescChanged = true;
	            }
	            break;
	        case QSP_WIN_INPUT:
	            if (showInput != isShow)
	            {
	                showInput = isShow;
	                isUserInputChanged = true;
	            }
	            break;
	    }
	}

	public String applyHtmlFixes(String text)
	{
	    // Контекст библиотеки
		if (text == null)
			return "";
	    if (useHtml != 0)
	    {
	        text = text.replaceAll("\n", "<br />");

	        // THIS IS ONLY FOR LEGACY
	        // В Байтовском AeroQSP неправильно эскейпились кавычки и апострофы, и не эскейпились амперсанды(&).
	        // Для совместимости с играми, написанными под Байтовский AeroQSP, делаем замену.
	        text = text.replace("&", "&amp;");
	        text = text.replace("&amp;quot;", "&quot;");
	        text = text.replace("&amp;nbsp;", "&nbsp;");
	        text = text.replace("&amp;lt;", "&lt;");
	        text = text.replace("&amp;gt;", "&gt;");
	        text = text.replace("&amp;amp;", "&amp;");
	        text = text.replace("\\\"", "&quot;");
	        text = text.replace("\\'", "&#39;");
	        return text;
	    }
	    else
	        return text;
	}

	String makeHtmlColor(int color)
	{
	    // ABGR -> RGB
	    int reversedColor = ((color & 0xFF) << 16) | (color & 0xFF00) | ((color & 0xFF0000) >> 16);
	    return String.format("#%06X", reversedColor);
	}
}
