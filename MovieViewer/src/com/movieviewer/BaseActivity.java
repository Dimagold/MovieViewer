package com.movieviewer;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;

import com.movieviewer.bll.network.RESTLoader;
import com.movieviewer.bll.network.RESTLoader.HTTPVerb;
import com.movieviewer.bll.network.RESTLoader.RESTResponce;
import com.movieviewer.bll.network.request.BaseRequest;


public abstract class BaseActivity extends Activity implements 
LoaderCallbacks<com.movieviewer.bll.network.RESTLoader.RESTResponce>{
	
	private static final String TAG = BaseActivity.class.getSimpleName();
	
	public static final int LOADER_GET_POPULAR_MOVIES = 0x1;	
	public static final int LOADER_GET_MOVIE_DETAIL = 0x2;
	
	private ActionBar actionBar;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	actionBar = this.getActionBar();
		if(actionBar != null) {
			actionBar.setDisplayUseLogoEnabled(false);
		}
    }
	

    public Bundle bundleForLoader(BaseRequest request, boolean emptyRequestParams) {
    	
    	Uri actionUri = Uri.parse(request.getURL());
    	
    	// request json data
    	Bundle params = new Bundle();
        params.putString(RESTLoader.REQUEST_PARAMS, !emptyRequestParams ? request.toJson() : "");
        
        // Bundle arguments for activity's loader
        Bundle args = new Bundle();
        args.putParcelable(RESTLoader.ARGS_URI, actionUri);
        args.putParcelable(RESTLoader.ARGS_PARAMS, params);
        
        return args;
    }
    
    public Bundle bundleForLoader(BaseRequest request, boolean emptyRequestParams, HTTPVerb verb) {
    	
    	Uri actionUri = Uri.parse(request.getURL());
    	
    	// request json data
    	Bundle params = new Bundle();
        params.putString(RESTLoader.REQUEST_PARAMS, !emptyRequestParams ? request.toJson() : "");
        
        // Bundle arguments for activity's loader
        Bundle args = new Bundle();
        args.putParcelable(RESTLoader.ARGS_URI, actionUri);
        args.putParcelable(RESTLoader.ARGS_PARAMS, params);
        args.putString("HTTP_VERB", verb.toString());
        
        return args;
    }
	
    public void onLoadFinished(Loader<RESTResponce> loader, RESTResponce data) {
    	int code = data.getCode();
        String json = data.getData();
        
        if (code == RESTLoader.StatusCode.OK && !json.equals("")) { 
        	onNewJsonResponse(loader, json);
        } else {
        	onNewJsonResponse(loader, null);
        }
    }
    
    public synchronized Loader<RESTResponce> onCreateLoader(int id, Bundle args) {
		if(args != null && args.containsKey(RESTLoader.ARGS_URI) &&  
				args.containsKey(RESTLoader.ARGS_PARAMS)) {
			
			Uri action = args.getParcelable(RESTLoader.ARGS_URI);
			Bundle params = args.getParcelable(RESTLoader.ARGS_PARAMS);
			String verb = args.getString("HTTP_VERB", HTTPVerb.POST.toString());
			
			return new RESTLoader(this, getVerbFromString(verb), action, params);
		}
		return null;
	}
    
    private HTTPVerb getVerbFromString(String verb) {
    	if(verb.toLowerCase().equals("post"))
    		return HTTPVerb.POST;
    	if(verb.toLowerCase().equals("get"))
    		return HTTPVerb.GET;
    	return HTTPVerb.POST;
    }
    
	public void onLoaderReset(Loader<RESTResponce> arg0) {
		// TODO Auto-generated method stub
	}
    
    public abstract void onNewJsonResponse(Loader<RESTResponce> loader, String jsonStr);
	
	public void showNoInternetMsgDialog(String message) {
		Dialog errorDialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message);
		builder.setPositiveButton(R.string.ok, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		errorDialog = builder.create();
		errorDialog.show();
	}
	
	public void showCloseAppDialog() {
		Dialog errorDialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getResources().getString(R.string.close_app_dialog_message));
		builder.setNegativeButton(R.string.no, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.setPositiveButton(R.string.yes, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				System.exit(0);
			}
		});
		errorDialog = builder.create();
		errorDialog.show();
	}
}
