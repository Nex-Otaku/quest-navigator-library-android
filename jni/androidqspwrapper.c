#include <qsp/qsp.h>
#include <qsp/bindings/android/android.h>
#include <jni.h>
#include <string.h>
#include <android/log.h>

jobject qspCallbackObject;
JNIEnv* qspCallbackEnv;

void Java_su_qsp_QuestNavigator_library_QspLib_QSPInit(JNIEnv * env, jobject this)
{
	//__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NDK:LC: [%s]", szLogThis);
	qspCallbackObject = (*env)->NewGlobalRef(env, this);
	qspCallbackEnv = env;
	QSPInit();
}

void Java_su_qsp_QuestNavigator_library_QspLib_QSPDeInit(JNIEnv * env, jobject this)
{
	QSPDeInit();
	(*env)->DeleteGlobalRef(env, qspCallbackObject);
	qspCallbackObject = NULL;
	qspCallbackEnv = NULL;
}

jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPIsInCallBack(JNIEnv * env, jobject this)
{
	return QSPIsInCallBack();
}

void Java_su_qsp_QuestNavigator_library_QspLib_QSPEnableDebugMode(JNIEnv * env, jobject this, jboolean isDebug)
{
	QSPEnableDebugMode((QSP_BOOL) isDebug);
}

jobject Java_su_qsp_QuestNavigator_library_QspLib_QSPGetCurStateData(JNIEnv * env, jobject this)
{
	//!!!STUB
	//QSPGetCurStateData(jstring *loc, (int *)actIndex, (int *)line);
	return NULL;
}

jstring Java_su_qsp_QuestNavigator_library_QspLib_QSPGetVersion(JNIEnv * env, jobject this)
{
	char * sz = qspW2C(QSPGetVersion());
	jstring result = (*env)->NewStringUTF(env, sz);
	if (sz!=NULL)
		free(sz);
	return result;
}


///* Количество полных обновлений локаций */
jint Java_su_qsp_QuestNavigator_library_QspLib_QSPGetFullRefreshCount(JNIEnv * env, jobject this)
{
	return QSPGetFullRefreshCount();
}
///* ------------------------------------------------------------ */
///* Полный путь к загруженному файлу игры */
jstring Java_su_qsp_QuestNavigator_library_QspLib_QSPGetQstFullPath(JNIEnv * env, jobject this)
{
	char * sz = qspW2C(QSPGetQstFullPath());
	jstring result = (*env)->NewStringUTF(env, sz);
	if (sz!=NULL)
		free(sz);
	return result;
}
///* ------------------------------------------------------------ */
///* Название текущей локации */
jstring Java_su_qsp_QuestNavigator_library_QspLib_QSPGetCurLoc(JNIEnv * env, jobject this)
{
	char * sz = qspW2C(QSPGetCurLoc());
	jstring result = (*env)->NewStringUTF(env, sz);
	if (sz!=NULL)
		free(sz);
	return result;
}
///* ------------------------------------------------------------ */
///* Основное описание локации */
//
///* Текст основного окна описания локации */
jstring Java_su_qsp_QuestNavigator_library_QspLib_QSPGetMainDesc(JNIEnv * env, jobject this)
{
	char * sz = qspW2C(QSPGetMainDesc());
	jstring result = (*env)->NewStringUTF(env, sz);
	if (sz!=NULL)
		free(sz);
	return result;
}
///* Возможность изменения текста основного описания */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPIsMainDescChanged(JNIEnv * env, jobject this)
{
	return QSPIsMainDescChanged();
}
///* ------------------------------------------------------------ */
///* Дополнительное описание локации */
//
///* Текст дополнительного окна описания локации */
jstring Java_su_qsp_QuestNavigator_library_QspLib_QSPGetVarsDesc(JNIEnv * env, jobject this)
{
	char * sz = qspW2C(QSPGetVarsDesc());
	jstring result = (*env)->NewStringUTF(env, sz);
	if (sz!=NULL)
		free(sz);
	return result;
}
///* Возможность изменения текста дополнительного описания */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPIsVarsDescChanged(JNIEnv * env, jobject this)
{
	return QSPIsVarsDescChanged();
}
///* ------------------------------------------------------------ */
///* Получить значение указанного выражения */
//(const QSP_CHAR *expr, QSP_BOOL *isString, int *numVal, QSP_CHAR *strVal, int strValBufSize)
jobject Java_su_qsp_QuestNavigator_library_QspLib_QSPGetExprValue(JNIEnv * env, jobject this)
{
	//!!!STUB
	//{
	//	QSPVariant v;
	//	if (qspIsExitOnError && qspErrorNum) return QSP_FALSE;
	//	qspResetError();
	//	if (qspIsDisableCodeExec) return QSP_FALSE;
	//	v = qspExprValue((QSP_CHAR *)expr);
	//	if (qspErrorNum) return QSP_FALSE;
	//	*isString = v.IsStr;
	//	if (v.IsStr)
	//	{
	//		qspStrNCopy(strVal, QSP_STR(v), strValBufSize - 1);
	//		free(QSP_STR(v));
	//		strVal[strValBufSize - 1] = 0;
	//	}
	//	else
	//		*numVal = QSP_NUM(v);
	//	return QSP_TRUE;
	//}
	return NULL;
}
///* ------------------------------------------------------------ */
///* Текст строки ввода */
void Java_su_qsp_QuestNavigator_library_QspLib_QSPSetInputStrText(JNIEnv * env, jobject this, jstring val)
{
    const char *str = (*env)->GetStringUTFChars(env, val, NULL);
    if (str == NULL)
        return;
    QSP_CHAR * strConverted = qspC2W(str);

    QSPSetInputStrText(strConverted);

    (*env)->ReleaseStringUTFChars(env, val, str);
}
///* ------------------------------------------------------------ */
///* Список действий */
//
///* Количество действий */
jint Java_su_qsp_QuestNavigator_library_QspLib_QSPGetActionsCount(JNIEnv * env, jobject this)
{
	return QSPGetActionsCount();
}
///* Данные действия с указанным индексом */
jobject Java_su_qsp_QuestNavigator_library_QspLib_QSPGetActionData(JNIEnv * env, jobject this, jint ind)
{
	char * qspImgFileName;
	char * qspActName;
	QSPGetActionData(ind, &qspImgFileName, &qspActName);

	char * sz = qspW2C(qspActName);
	char * isz = qspW2C(qspImgFileName);
	jstring actName = (*env)->NewStringUTF(env, sz);
	jstring actImg = (*env)->NewStringUTF(env, isz);
	if (sz!=NULL)
		free(sz);
	if (isz!=NULL)
		free(isz);
	jclass clazz = (*env)->FindClass (env, "su/qsp/QuestNavigator/library/ContainerJniResult");
	if (clazz == 0)
			return 0;
	jobject obj = (*env)->AllocObject (env, clazz);
	jfieldID fid = (*env)->GetFieldID (env, clazz, "str1", "Ljava/lang/String;");
	jfieldID fid2 = (*env)->GetFieldID (env, clazz, "str2", "Ljava/lang/String;");
	(*env)->DeleteLocalRef( env, clazz );
	if (fid == 0 || fid2 == 0)
			return 0;
	(*env)->SetObjectField (env, obj, fid, actName);
	(*env)->SetObjectField (env, obj, fid2, actImg);
	
	return obj;
}
///* Выполнение кода выбранного действия */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPExecuteSelActionCode(JNIEnv * env, jobject this, jboolean isRefresh)
{
	return QSPExecuteSelActionCode((QSP_BOOL)isRefresh);
}
///* Установить индекс выбранного действия */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPSetSelActionIndex(JNIEnv * env, jobject this, jint ind, jboolean isRefresh)
{
	return QSPSetSelActionIndex(ind, (QSP_BOOL)isRefresh);
}
///* Получить индекс выбранного действия */
jint Java_su_qsp_QuestNavigator_library_QspLib_QSPGetSelActionIndex(JNIEnv * env, jobject this)
{
	return QSPGetSelActionIndex();
}
///* Возможность изменения списка действий */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPIsActionsChanged(JNIEnv * env, jobject this)
{
	return QSPIsActionsChanged();
}
///* ------------------------------------------------------------ */
///* Список объектов */
//
///* Количество объектов */
jint Java_su_qsp_QuestNavigator_library_QspLib_QSPGetObjectsCount(JNIEnv * env, jobject this)
{
	return QSPGetObjectsCount();
}
///* Данные объекта с указанным индексом */
jobject Java_su_qsp_QuestNavigator_library_QspLib_QSPGetObjectData(JNIEnv * env, jobject this, jint ind)
{
	char * qspImgFileName;
	char * qspObjName;
	QSPGetObjectData(ind, &qspImgFileName, &qspObjName);

	char * sz = qspW2C(qspObjName);
	jstring objName = (*env)->NewStringUTF(env, sz);
	if (sz!=NULL)
		free(sz);

	char * isz = qspW2C(qspImgFileName);
	jstring objImg = (*env)->NewStringUTF(env, isz);
	if (isz!=NULL)
		free(isz);

	jclass clazz = (*env)->FindClass (env, "su/qsp/QuestNavigator/library/ContainerJniResult");
	if (clazz == 0)
			return 0;
	jobject obj = (*env)->AllocObject (env, clazz);
	jfieldID fid = (*env)->GetFieldID (env, clazz, "str1", "Ljava/lang/String;");
	jfieldID fid2 = (*env)->GetFieldID (env, clazz, "str2", "Ljava/lang/String;");
	(*env)->DeleteLocalRef( env, clazz );
	if (fid == 0 || fid2 == 0)
			return 0;
	// Set the major field to the operating system's major version.
	(*env)->SetObjectField (env, obj, fid, objName);
	(*env)->SetObjectField (env, obj, fid2, objImg);

	return obj;
}
///* Установить индекс выбранного объекта */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPSetSelObjectIndex(JNIEnv * env, jobject this, jint ind, jboolean isRefresh)
{
	return QSPSetSelObjectIndex(ind, (QSP_BOOL) isRefresh);
}
///* Получить индекс выбранного объекта */
jint Java_su_qsp_QuestNavigator_library_QspLib_QSPGetSelObjectIndex(JNIEnv * env, jobject this)
{
	return QSPGetSelObjectIndex();
}
///* Возможность изменения списка объектов */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPIsObjectsChanged(JNIEnv * env, jobject this)
{
	return QSPIsObjectsChanged();
}
///* ------------------------------------------------------------ */
///* Показ / скрытие окон */
void Java_su_qsp_QuestNavigator_library_QspLib_QSPShowWindow(JNIEnv * env, jobject this, jint type, jboolean isShow)
{
	QSPShowWindow(type, (QSP_BOOL)isShow);
}
///* ------------------------------------------------------------ */
///* Переменные */
//
///* Получить количество элементов массива */
//QSP_BOOL QSPGetVarValuesCount(const QSP_CHAR *name, int *count)
jobject Java_su_qsp_QuestNavigator_library_QspLib_QSPGetVarValuesCount(JNIEnv * env, jobject this, jstring name)
{
	//!!!STUB
	//{
	//	QSPVar *var;
	//	if (qspIsExitOnError && qspErrorNum) return QSP_FALSE;
	//	qspResetError();
	//	var = qspVarReference((QSP_CHAR *)name, QSP_FALSE);
	//	if (qspErrorNum) return QSP_FALSE;
	//	*count = var->ValsCount;
	//	return QSP_TRUE;
	//}
	return NULL;
}
///* Получить значения указанного элемента массива */
jobject Java_su_qsp_QuestNavigator_library_QspLib_QSPGetVarValues(JNIEnv * env, jobject this, jstring name, jint ind, jint defaultNumVal, jstring defaultStrVal)
{
	//Convert array name to QSP string
    const char *str = (*env)->GetStringUTFChars(env, name, NULL);
    if (str == NULL)
        return NULL;
    QSP_CHAR * strConverted = qspC2W(str);

    //Call QSP function
	int numVal = (int)defaultNumVal;
    const char* tmpDefaultStrVal = (*env)->GetStringUTFChars(env, defaultStrVal, NULL);
	QSP_CHAR* strVal = qspC2W(tmpDefaultStrVal);
    
	QSP_BOOL result = QSPGetVarValues(strConverted, (int)ind, &numVal, &strVal);

	// Attempt to find the JniResult class.
	jclass clazz = (*env)->FindClass (env, "su/qsp/QuestNavigator/library/ContainerJniResult");
	// If this class does not exist then return null.
	if (clazz == 0)
		return NULL;
	jobject obj = (*env)->AllocObject (env, clazz);

	jfieldID fid = (*env)->GetFieldID (env, clazz, "success", "Z");
	if (fid == 0)
		return NULL;
	if (result == QSP_TRUE)
	{
		(*env)->SetBooleanField (env, obj, fid, JNI_TRUE);

		char * sz = qspW2C(strVal);
		jstring jstringVal = (*env)->NewStringUTF(env, sz);
		if (sz!=NULL)
			free(sz);

		fid = (*env)->GetFieldID (env, clazz, "str1", "Ljava/lang/String;");
		if (fid == 0)
			return NULL;
		(*env)->SetObjectField (env, obj, fid, jstringVal);

		jfieldID fid = (*env)->GetFieldID (env, clazz, "int1", "I");
		if (fid == 0)
			return NULL;
		(*env)->SetIntField (env, obj, fid, numVal);
	}
	else
	{
		(*env)->SetBooleanField (env, obj, fid, JNI_FALSE);
	}

	(*env)->DeleteLocalRef( env, clazz );
	(*env)->ReleaseStringUTFChars(env, name, str);
	(*env)->ReleaseStringUTFChars(env, defaultStrVal, tmpDefaultStrVal);
	return obj;
}
///* Получить максимальное количество переменных */
jint Java_su_qsp_QuestNavigator_library_QspLib_QSPGetMaxVarsCount(JNIEnv * env, jobject this)
{
	return QSPGetMaxVarsCount();
}
///* Получить имя переменной с указанным индексом */
//QSP_BOOL QSPGetVarNameByIndex(int index, QSP_CHAR **name)
jobject Java_su_qsp_QuestNavigator_library_QspLib_QSPGetVarNameByIndex(JNIEnv * env, jobject this, jint index)
{
	//!!!STUB
//{
//	if (index < 0 || index >= QSP_VARSCOUNT || !qspVars[index].Name) return QSP_FALSE;
//	*name = qspVars[index].Name;
//	return QSP_TRUE;
//}
	return NULL;
}
///* ------------------------------------------------------------ */
///* Выполнение кода */
//
///* Выполнение строки кода */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPExecString(JNIEnv * env, jobject this, jstring s, jboolean isRefresh)
{
    const char *str = (*env)->GetStringUTFChars(env, s, NULL);
    if (str == NULL)
        return JNI_FALSE;
    QSP_CHAR * strConverted = qspC2W(str);

    jboolean result = QSPExecString(strConverted, (QSP_BOOL)isRefresh);

    (*env)->ReleaseStringUTFChars(env, s, str);
    return result;
}
///* Выполнение кода указанной локации */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPExecLocationCode(JNIEnv * env, jobject this, jstring name, jboolean isRefresh)
{
    const char *str = (*env)->GetStringUTFChars(env, name, NULL);
    if (str == NULL)
        return JNI_FALSE;
    QSP_CHAR * strConverted = qspC2W(str);

	jboolean result = QSPExecLocationCode(strConverted, (QSP_BOOL)isRefresh);

    (*env)->ReleaseStringUTFChars(env, name, str);
    return result;
}
///* Выполнение кода локации-счетчика */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPExecCounter(JNIEnv * env, jobject this, jboolean isRefresh)
{
	return QSPExecCounter((QSP_BOOL)isRefresh);
}
///* Выполнение кода локации-обработчика строки ввода */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPExecUserInput(JNIEnv * env, jobject this, jboolean isRefresh)
{
	return QSPExecUserInput((QSP_BOOL)isRefresh);
}
///* ------------------------------------------------------------ */
///* Ошибки */
//
///* Получить информацию о последней ошибке */
jobject Java_su_qsp_QuestNavigator_library_QspLib_QSPGetLastErrorData(JNIEnv * env, jobject this)
{
	jclass clazz = (*env)->FindClass (env, "su/qsp/QuestNavigator/library/ContainerJniResult");
	if (clazz == 0)
			return NULL;
	jfieldID fid = (*env)->GetFieldID (env, clazz, "str1", "Ljava/lang/String;");
	jfieldID fid2 = (*env)->GetFieldID (env, clazz, "int1", "I");
	jfieldID fid3 = (*env)->GetFieldID (env, clazz, "int2", "I");
	jfieldID fid4 = (*env)->GetFieldID (env, clazz, "int3", "I");
	if (fid == 0 || fid2 == 0 || fid3 == 0 || fid4 == 0)
			return NULL;
	jobject obj = (*env)->AllocObject (env, clazz);
	(*env)->DeleteLocalRef( env, clazz );

	int errorNum;
	char *locName;
	int index;
	int line;

	QSPGetLastErrorData(&errorNum, &locName, &index, &line);

	char * sz = qspW2C(locName);
	jstring jLocName = (*env)->NewStringUTF(env, sz);
	if (sz!=NULL)
		free(sz);

	(*env)->SetObjectField (env, obj, fid, jLocName);
	(*env)->SetIntField (env, obj, fid2, errorNum);
	(*env)->SetIntField (env, obj, fid3, index);
	(*env)->SetIntField (env, obj, fid4, line);
	return obj;
}
///* Получить описание ошибки по ее номеру */
jstring Java_su_qsp_QuestNavigator_library_QspLib_QSPGetErrorDesc(JNIEnv * env, jobject this, jint errorNum)
{
	char * sz = qspW2C(QSPGetErrorDesc(errorNum));
	jstring result = (*env)->NewStringUTF(env, sz);
	if (sz!=NULL)
		free(sz);
	return result;
}
///* ------------------------------------------------------------ */
///* Управление игрой */
//
///* Загрузка новой игры из файла */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPLoadGameWorld(JNIEnv * env, jobject this, jstring fileName )
{
    const char *str = (*env)->GetStringUTFChars(env, fileName, NULL);
    if (str == NULL)
        return JNI_FALSE;

    jboolean result = QSPLoadGameWorld(str);

    (*env)->ReleaseStringUTFChars(env, fileName, str);
    return result;
}
///* Загрузка новой игры из памяти */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPLoadGameWorldFromData(JNIEnv * env, jobject this, jbyteArray data, jint dataSize, jstring fileName )
{
	//converting data
	jbyte* jbuf = malloc(dataSize * sizeof (jbyte));
	if (jbuf == NULL)
		return JNI_FALSE;

	(*env)->GetByteArrayRegion(env, data, 0, dataSize, jbuf);
	int size = dataSize;
	char* mydata = (char*)jbuf;

    /* assume the prompt string and user input has less than 128
        characters */
	int fileNameLen = (*env)->GetStringLength(env, fileName) + 1;
    char buf[fileNameLen];
    const jbyte *str;
    str = (*env)->GetStringUTFChars(env, fileName, NULL);
    if (str == NULL) {
	    free(jbuf);
        return JNI_FALSE; /* OutOfMemoryError already thrown */
    }

    jboolean result = QSPLoadGameWorldFromData(mydata, size, str);
    (*env)->ReleaseStringUTFChars(env, fileName, str);

    free(jbuf);
	return result;
}
///* Сохранение состояния в файл */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPSaveGame(JNIEnv * env, jobject this, jstring fileName, jboolean isRefresh)
{
    const char *str = (*env)->GetStringUTFChars(env, fileName, NULL);
    if (str == NULL)
        return JNI_FALSE;

    jboolean result = QSPSaveGame(str, (QSP_BOOL)isRefresh);

    (*env)->ReleaseStringUTFChars(env, fileName, str);
    return result;
}
///* Сохранение состояния в память */
jbyteArray Java_su_qsp_QuestNavigator_library_QspLib_QSPSaveGameAsData(JNIEnv * env, jobject this, jboolean isRefresh)
{
	void * buffer = NULL;
	int	bufferSize = 0;
	if (QSPSaveGameAsData(&buffer, &bufferSize, (QSP_BOOL)isRefresh) == QSP_FALSE)
		return NULL;

	jbyteArray result;
	result = (*env)->NewByteArray(env, bufferSize);
	if (result == NULL)
		return NULL;

	(*env)->SetByteArrayRegion(env, result, 0, bufferSize, buffer);

	return result;
}
///* Загрузка состояния из файла */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPOpenSavedGame(JNIEnv * env, jobject this, jstring fileName, jboolean isRefresh)
{
    const char *str = (*env)->GetStringUTFChars(env, fileName, NULL);
    if (str == NULL)
        return JNI_FALSE;

    jboolean result = QSPOpenSavedGame(str, (QSP_BOOL)isRefresh);

    (*env)->ReleaseStringUTFChars(env, fileName, str);
    return result;
}
///* Загрузка состояния из памяти */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPOpenSavedGameFromData(JNIEnv * env, jobject this, jbyteArray data, jint dataSize, jboolean isRefresh)
{
	//converting data
	jbyte* jbuf = malloc(dataSize * sizeof (jbyte));
	if (jbuf == NULL)
		return JNI_FALSE;

	(*env)->GetByteArrayRegion(env, data, 0, dataSize, jbuf);
	int size = dataSize;
	void* mydata = (void*)jbuf;

    jboolean result = QSPOpenSavedGameFromData(mydata, size, (QSP_BOOL)isRefresh) == QSP_TRUE;

    free(jbuf);
	return result;
}
///* Перезапуск игры */
jboolean Java_su_qsp_QuestNavigator_library_QspLib_QSPRestartGame(JNIEnv * env, jobject this, jboolean isRefresh)
{
	return QSPRestartGame((QSP_BOOL)isRefresh);
}
///* ------------------------------------------------------------ */
///* Установка CALLBACK'ов */
//void QSPSetCallBack(int type, QSP_CALLBACK func)
//{
//	qspSetCallBack(type, func);
//}

/* -------------------------------------------------------------- */
// Подвязываем нативные функции к классу QspLib, чтобы JVM не сканировал все библиотеки подряд. 
// Это делать необязательно, но так будет работать чуть быстрее и не будет лишней ерунды в логе.

jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    JNIEnv *env;
    //__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "JNI_OnLoad called");
	if ((*vm)->GetEnv(vm, (void**) &env, JNI_VERSION_1_6) != JNI_OK) {
		//__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "Failed to get the environment using GetEnv()");
        return -1;
    }
    JNINativeMethod methods[] = {
        {
            "QSPInit",
            "()V",
            (void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPInit
        },
        {
            "QSPDeInit",
            "()V",
            (void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPDeInit
        },
		{
			"QSPIsInCallBack",
			"()Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPIsInCallBack
		},
		{
			"QSPEnableDebugMode",
			"(Z)V",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPEnableDebugMode
		},
		/* STUB
		{
			"QSPGetCurStateData",
			"()Ljava/lang/Object;",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetCurStateData
		},*/
		{
			"QSPGetVersion",
			"()Ljava/lang/String;",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetVersion
		},
		{
			"QSPGetFullRefreshCount",
			"()I",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetFullRefreshCount
		},
		{
			"QSPGetQstFullPath",
			"()Ljava/lang/String;",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetQstFullPath
		},
		{
			"QSPGetCurLoc",
			"()Ljava/lang/String;",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetCurLoc
		},
		{
			"QSPGetMainDesc",
			"()Ljava/lang/String;",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetMainDesc
		},
		{
			"QSPIsMainDescChanged",
			"()Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPIsMainDescChanged
		},
		{
			"QSPGetVarsDesc",
			"()Ljava/lang/String;",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetVarsDesc
		},
		{
			"QSPIsVarsDescChanged",
			"()Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPIsVarsDescChanged
		},
		/* STUB
		{
			"QSPGetExprValue",
			"()Ljava/lang/Object;",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetExprValue
		},*/
		{
			"QSPSetInputStrText",
			"(Ljava/lang/String;)V",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPSetInputStrText
		},
		{
			"QSPGetActionsCount",
			"()I",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetActionsCount
		},
		{
			"QSPGetActionData",
			"(I)Ljava/lang/Object;",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetActionData
		},
		{
			"QSPExecuteSelActionCode",
			"(Z)Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPExecuteSelActionCode
		},
		{
			"QSPSetSelActionIndex",
			"(IZ)Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPSetSelActionIndex
		},
		{
			"QSPGetSelActionIndex",
			"()I",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetSelActionIndex
		},
		{
			"QSPIsActionsChanged",
			"()Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPIsActionsChanged
		},
		{
			"QSPGetObjectsCount",
			"()I",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetObjectsCount
		},
		{
			"QSPGetObjectData",
			"(I)Ljava/lang/Object;",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetObjectData
		},
		{
			"QSPSetSelObjectIndex",
			"(IZ)Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPSetSelObjectIndex
		},
		{
			"QSPGetSelObjectIndex",
			"()I",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetSelObjectIndex
		},
		{
			"QSPIsObjectsChanged",
			"()Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPIsObjectsChanged
		},
		{
			"QSPShowWindow",
			"(IZ)V",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPShowWindow
		},
		/* STUB
		{
			"QSPGetVarValuesCount",
			"(Ljava/lang/String;)Ljava/lang/Object;",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetVarValuesCount
		},*/
		{
			"QSPGetVarValues",
			"(Ljava/lang/String;IILjava/lang/String;)Ljava/lang/Object;",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetVarValues
		},
		{
			"QSPGetMaxVarsCount",
			"()I",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetMaxVarsCount
		},
		/* STUB
		{
			"QSPGetVarNameByIndex",
			"(I)Ljava/lang/Object;",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetVarNameByIndex
		},*/
		{
			"QSPExecString",
			"(Ljava/lang/String;Z)Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPExecString
		},
		{
			"QSPExecLocationCode",
			"(Ljava/lang/String;Z)Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPExecLocationCode
		},
		{
			"QSPExecCounter",
			"(Z)Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPExecCounter
		},
		{
			"QSPExecUserInput",
			"(Z)Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPExecUserInput
		},
		{
			"QSPGetLastErrorData",
			"()Ljava/lang/Object;",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetLastErrorData
		},
		{
			"QSPGetErrorDesc",
			"(I)Ljava/lang/String;",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPGetErrorDesc
		},
		{
			"QSPLoadGameWorld",
			"(Ljava/lang/String;)Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPLoadGameWorld
		},
		{
			"QSPLoadGameWorldFromData",
			"([BILjava/lang/String;)Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPLoadGameWorldFromData
		},
		{
			"QSPSaveGame",
			"(Ljava/lang/String;Z)Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPSaveGame
		},
		{
			"QSPSaveGameAsData",
			"(Z)[B",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPSaveGameAsData
		},
		{
			"QSPOpenSavedGame",
			"(Ljava/lang/String;Z)Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPOpenSavedGame
		},
		{
			"QSPOpenSavedGameFromData",
			"([BIZ)Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPOpenSavedGameFromData
		},
		{
			"QSPRestartGame",
			"(Z)Z",
			(void *) Java_su_qsp_QuestNavigator_library_QspLib_QSPRestartGame
		}
    };
	int numMethods = sizeof(methods)/sizeof(methods[0]);
	
	
    jclass clazz;

    clazz = (*env)->FindClass(env, "su/qsp/QuestNavigator/library/QspLib");
    if (clazz == NULL) {
		//__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "Native registration unable to find class");
        return -1;
    }
    if ((*env)->RegisterNatives(env, clazz, methods, numMethods) < 0) {
		//__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "RegisterNatives failed");
        return -1;
    }

    return JNI_VERSION_1_6;
}
