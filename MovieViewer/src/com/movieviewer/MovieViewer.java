package com.movieviewer;

import com.movieviewer.bll.data.InternalDataStorage;
import com.movieviewer.bll.data.RuntimeDataHolder;

import android.app.Application;
import android.content.Context;

public class MovieViewer extends Application {

	public static Context context;
	public static RuntimeDataHolder runtimeDataHolder;
	public static InternalDataStorage internalDataStorage;
	public static boolean isOnline = true;

    @Override 
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        runtimeDataHolder = RuntimeDataHolder.getInstance();
        internalDataStorage = InternalDataStorage.getInstance();
    }
    
    public static void loadInternalDataToRuntimeDataHolder() {
    	internalDataStorage.loadInternalData(runtimeDataHolder);
    }
    
    public static void saveRantimeDataToInternalStorage() {
    	internalDataStorage.saveInternalData(runtimeDataHolder);
    }
}
