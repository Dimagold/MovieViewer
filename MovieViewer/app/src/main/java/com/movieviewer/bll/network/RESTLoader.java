package com.movieviewer.bll.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class RESTLoader extends AsyncTaskLoader<RESTLoader.RESTResponce>{

	public static final String ARGS_URI = "args_uri";
	public static final String ARGS_PARAMS = "args_params";
	public static final String REQUEST_PARAMS = "request_params";
	
	public static final String TAG = RESTLoader.class.getSimpleName();
	
	
	public static enum HTTPVerb {
		GET,
		POST,
		PUT,
		DELETE
	};
	
	/**
	 * HTTP response status codes
	 * @author Dmitry Goldenberg
	 * @version 0.1
	 * @since 11/11/14
	 */
	public static class StatusCode {
		public static final int OK = 200;
	}
	
	/**
	 * RESTResponce class represents HTTP response message 
	 * relevant to client. It includes a string json data
	 * and HTTP status code
	 * 
	 * @author Dmitry Goldenberg
	 * @version 0.1
	 * @since 11/11/14
	 */
	public static class RESTResponce {
		private String data;
		private int code;
		
		public RESTResponce() {}
		
		/**
		 * @param data - Json data by string object
		 * @param code - HTTP response status code
		 */
		public RESTResponce(String data, int code) {
            this.data = data;
            this.code = code;
        }
        
        public String getData() {
            return data;
        }
        
        public int getCode() {
            return code;
        }
	}
	
	private HTTPVerb verb;
	private Uri action;
	private Bundle params;
	private RESTResponce restResponce;
	private long lastLoad;
	
	public RESTLoader(Context context) {
		super(context);
	}
	
	public RESTLoader(Context context, HTTPVerb verb, Uri action) {
        super(context);
        
        this.verb   = verb;
        this.action = action;
    }
    
    public RESTLoader(Context context, HTTPVerb verb, Uri action, Bundle params) {
        super(context);
        
        this.verb   = verb;
        this.action = action;
        this.params = params;
    }

	@Override
	public RESTResponce loadInBackground() {
		try {
			if(action == null) {
				return new RESTResponce();
			}
			
			HttpRequestBase request = null;		
			
			/*JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put("SocialNetworkUID", "2342354235423");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Create the POST object and add the parameters
			HttpPost httpPost = new HttpPost("http://62.219.142.200:8090/UsersRestCommunicationService.svc/CheckNewUser");
			StringEntity entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(httpPost);
			HttpEntity data = response.getEntity();
			String strData = EntityUtils.toString(data);
			
			Log.i("RESPONSE", response.toString());*/
			
			switch(verb) {
			   case POST:
				   request = new HttpPost();
				   request.setURI(new URI(action.toString()));
				   HttpPost postRequest = (HttpPost) request;
				   
				   if(params != null) {
					   String jsonData = params.getString(REQUEST_PARAMS);
					   //UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramsToList(params));
					   StringEntity entity = new StringEntity(jsonData, HTTP.UTF_8);
					   entity.setContentType("application/json");
					   postRequest.setEntity(entity);
				   }
			       break;
			   case GET:
				   request = new HttpGet();
                   attachUriWithQuery(request, action, params);
			       break;
			   case PUT:
				   break;
			   case DELETE:
				   break;
			   default:
					break;
			}
			
			if(request != null) {
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(request);
				HttpEntity responseEntity = response.getEntity();
				StatusLine responseStatus = response.getStatusLine();
				int statusCode = responseStatus != null ? responseStatus.getStatusCode() : null;
				
				RESTResponce restResponse = new RESTResponce(
						( responseEntity != null ) ? EntityUtils.toString(responseEntity) : null, 
								statusCode);
			    return restResponse;
			}
			return new RESTResponce();
		} catch (URISyntaxException e) {
            Log.e(TAG, "URI syntax was incorrect. "+ verb.toString() +": "+ action.toString(), e);
            return new RESTResponce();
        }
        catch (UnsupportedEncodingException e) {
            Log.e(TAG, "A UrlEncodedFormEntity was created with an unsupported encoding.", e);
            return new RESTResponce();
        }
        catch (ClientProtocolException e) {
            Log.e(TAG, "There was a problem when sending the request.", e);
            return new RESTResponce();
        }
        catch (IOException e) {
            Log.e(TAG, "There was a problem when sending the request.", e);
            return new RESTResponce();
        }
	}
	
	@Override
	public void deliverResult(RESTResponce data) {
        // Here we cache our response.
		restResponce = data;
	    super.deliverResult(data);
    }
	    
	@Override
	protected void onStartLoading() {
	    if (restResponce != null) {
   	        // We have a cached result, so we can just
 	        // return right away.
            super.deliverResult(restResponce);
	    }
	        
	    // If our response is null or we have hung onto it for a long time,
	    // then we perform a force load.
        if (restResponce == null || System.currentTimeMillis() - lastLoad >= 600) forceLoad();
	        lastLoad = System.currentTimeMillis();
	}
	    
	@Override
	protected void onStopLoading() {
        // This prevents the AsyncTask backing this
		// loader from completing if it is currently running.
	    cancelLoad();
    }
	    
	@Override
	protected void onReset() {
	    super.onReset();
	        
	    // Stop the Loader if it is currently running.
        onStopLoading();
	        
	    // Get rid of our cache if it exists.
	    restResponce = null;
	        
	    // Reset our stale timer.
	    lastLoad = 0;
	}
	
	private static void attachUriWithQuery(HttpRequestBase request, Uri uri, Bundle params) {
	        try {
	            if (params == null) {
	                // No params were given or they have already been
	                // attached to the Uri.
	                request.setURI(new URI(uri.toString()));
	            }
	            else {
	                Uri.Builder uriBuilder = uri.buildUpon();
	                
	                // Loop through our params and append them to the Uri.
	                for (BasicNameValuePair param : paramsToList(params)) {
	                    uriBuilder.appendQueryParameter(param.getName(), param.getValue());
	                }
	                
	                uri = uriBuilder.build();
	                request.setURI(new URI(uri.toString()));
	            }
	        }
	        catch (URISyntaxException e) {
	            Log.e(TAG, "URI syntax was incorrect: "+ uri.toString());
	        }
	    }
	    
	private static List<BasicNameValuePair> paramsToList(Bundle params) {
        ArrayList<BasicNameValuePair> formList = new ArrayList<BasicNameValuePair>(params.size());
        
        for (String key : params.keySet()) {
            Object value = params.get(key);
            
            // We can only put Strings in a form entity, so we call the toString()
            // method to enforce. We also probably don't need to check for null here
            // but we do anyway because Bundle.get() can return null.
            if (value != null) formList.add(new BasicNameValuePair(key, value.toString()));
        }
        
        return formList;
    }
}
