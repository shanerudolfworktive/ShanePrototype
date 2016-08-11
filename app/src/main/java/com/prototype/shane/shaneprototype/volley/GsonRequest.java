package com.prototype.shane.shaneprototype.volley;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.prototype.shane.shaneprototype.util.SimpleCountDownTimer;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shane on 7/9/16.
 */
public abstract class GsonRequest<T> extends Request<T> {
    private static final String LOG_TAG = "GsonRequest";
    public static final String NAME_CACHE_SHARED_PREFERENCE = "GSON_REQUEST_CACHE";
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private Context context;
    private  boolean shouldCacheResponse;
    private  boolean shouldGetCachedResponseFirst;
    private  boolean shouldOnlyNotifyFromCache;
    private boolean hasDeliverResponseFromCache;
    private String body;
    private static final int TIMEOUT_DURATION = 30000;

    private SimpleCountDownTimer simpleCountDownTimer;

    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers, String body){
        super(method, url, null);
        this.clazz = clazz;
        this.headers = headers;
        this.body = body;
        setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_DURATION * 3, 0, 0));
    }

    public GsonRequest(String url, Class<T> clazz, Map<String, String> headers, String body){
        this(Method.POST, url, clazz, headers, body);
    }

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequest(String url, Class<T> clazz, Map<String, String> headers) {
        super(Method.GET, url, null);
        this.clazz = clazz;
        this.headers = headers;
        setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_DURATION * 3, 0, 0));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    public byte[] getBody() {
        return body.getBytes();
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    /**
     *  we make these two methods abstract instead of passing in listeners like Google did (http://developer.android.com/training/volley/request-custom.html)
     *  is because we might want to call sendRequest() to resend the request when encounter network errors.
     */
    @Override
    protected void deliverResponse(T response){
        if(simpleCountDownTimer != null) simpleCountDownTimer.cancel();
        if (shouldOnlyNotifyFromCache && hasDeliverResponseFromCache) return;
        deliverResponse(response, false); //deliver the fetch result (not from the cache)
    }

    abstract protected void deliverResponse(T response, boolean isFromCache);

    //Override this to get error call back
    abstract public void deliverError(VolleyError error, T cachedResponse);


    @Override
    public void deliverError(VolleyError error){
        if(simpleCountDownTimer != null) simpleCountDownTimer.cancel();
        if (shouldOnlyNotifyFromCache && hasDeliverResponseFromCache) return;
        deliverError(error, getCachedResult(context));
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    "UTF-8");
            //cache the result if
            if (shouldCacheResponse) context.getSharedPreferences(NAME_CACHE_SHARED_PREFERENCE, Context.MODE_PRIVATE).edit().putString(getUrl(), json).commit();
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    /**
     * @param shouldGetCachedResponseFirst true, the first delivered response will be the cached result if there are any.
     *                                     false, the cached result will not deliver. Only the fetched response will be delivered.
     */
    public GsonRequest<T> setShouldGetCachedResponseFirst(boolean shouldGetCachedResponseFirst){
        this.shouldGetCachedResponseFirst = shouldGetCachedResponseFirst;
        return this;
    }

    public GsonRequest<T> setShouldCacheResponse(boolean shouldCacheResponse){
        this.shouldCacheResponse = shouldCacheResponse;
        return this;
    }

    /**
     * @param shouldOnlyNotifyFromCache true, it will only deliver the old cached Result and update cache silently.
     *                                  If there isn't a result already, it will deliver the fetched result.
     */
    public GsonRequest<T> setShouldOnlyNotifyFromCache(boolean shouldOnlyNotifyFromCache){
        this.shouldOnlyNotifyFromCache = shouldOnlyNotifyFromCache;
        return this;
    }

    public GsonRequest<T> sendRequest(Activity activity){
        if (simpleCountDownTimer == null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    simpleCountDownTimer = new SimpleCountDownTimer(TIMEOUT_DURATION) {
                        @Override
                        public void onFinish() {
                            Log.d(LOG_TAG, "volley self timeout = " + getUrl());
                            cancel();
                            deliverError(new TimeoutError(), getCachedResult(GsonRequest.this.context));
                        }
                    };
                }
            });
        }
        return sendRequest(activity.getApplicationContext());
    }

    public GsonRequest<T> sendRequest(Context context){
        Log.d(LOG_TAG, "url = " + getUrl());
        hasDeliverResponseFromCache = false;
        if (shouldGetCachedResponseFirst){
            T cachedResult = getCachedResult(context);
            if (cachedResult != null) {
                hasDeliverResponseFromCache = true;
                deliverResponse(cachedResult, true);//deliver the cached result
            }
        }
        this.context = context.getApplicationContext();
        VolleyHelper.getInstance(context).addToRequestQueue(this);
        if (simpleCountDownTimer != null) simpleCountDownTimer.start();
        return this;
    }

    public T getCachedResult(Context context){
        String cachedJson = context.getSharedPreferences(NAME_CACHE_SHARED_PREFERENCE, Context.MODE_PRIVATE).getString(getUrl(), null);
        if (cachedJson == null) return null;
        return new Gson().fromJson(cachedJson, clazz);
    }

    public void clearCache(Context context){
        context.getSharedPreferences(NAME_CACHE_SHARED_PREFERENCE, Context.MODE_PRIVATE).edit().remove(getUrl()).commit();
    }

    //this can be a static call
    public static void clearAllCache(Context context){
        context.getSharedPreferences(NAME_CACHE_SHARED_PREFERENCE, Context.MODE_PRIVATE).edit().clear().commit();
    }

    public static void clearCache(Context context, String url){
        context.getSharedPreferences(NAME_CACHE_SHARED_PREFERENCE, Context.MODE_PRIVATE).edit().remove(url).commit();
    }

    public static void populateCache(Context context, String url, HashMap<String, String> headers){
        new GsonRequest<Object>(url, Object.class, headers){
            @Override
            protected void deliverResponse(Object response, boolean isFromCache) {}
            @Override
            public void deliverError(VolleyError error, Object cacheData) {}
        }.setShouldCacheResponse(true).sendRequest(context);
    }

    @Override
    public void cancel() {
        super.cancel();
        if(simpleCountDownTimer != null) simpleCountDownTimer.cancel();
    }
}