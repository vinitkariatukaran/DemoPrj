package com.demoprj.BaseRequest;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.entity.ContentType;
import ch.boye.httpclientandroidlib.entity.mime.MultipartEntityBuilder;


/**
 * Cr8 Event - Compubits Solutions
 * Request class to be used when a call to Cr8 Event Backend is needed.
 * Created by Felipe Conde <felipe@compubits.com>
 * On 16/04/14.
 */

public class baseModel<T> extends Request<T> {

    Class<T> clazz;
    Type type;
    Response.Listener<T> listener;
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    Map<String, String> requestParams = new HashMap<String, String>();
    String mLoginToken;
    private String filePartName;
    private File file;
    private HttpEntity httpEntity;

    /**
     * Constructor to create a new request to CR8Event backend API
     *
     * @param method          The HTTP request Method.GET, Method.POST, Method.PUT, Method.DELETE <p> {@link Method} <p/>
     * @param url             The url to perform request
     * @param clazz           The model to be transformed from JSON response
     * @param type            Use this when JSON response is an array of model, otherwise it should be null
     * @param successListener The success listener
     * @param errorListener   The error listener
     */
    public baseModel(int method, String url, Class<T> clazz, Type type, Response.Listener<T> successListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.type = type;
        this.listener = successListener;
        //save tag as url so task can be canceled
        setTag(url);
        //Set Timeout
        setRetryPolicy(new DefaultRetryPolicy(2000, 1, 1.0f));
        //never cache the requests
        setShouldCache(false);
    }

    /**
     * Constructor to create a new request to CR8Event backend API
     *
     * @param method          The HTTP request Method.GET, Method.POST, Method.PUT, Method.DELETE <p> {@link Method} <p/>
     * @param url             The url to perform request
     * @param clazz           The model to be transformed from JSON response
     * @param successListener The success listener
     * @param errorListener   The error listener
     */
    public baseModel(int method, String url, Class<T> clazz, Response.Listener<T> successListener, Response.ErrorListener errorListener) {
        this(method, url, clazz, null, successListener, errorListener);
    }

    /**
     * Constructor to create a new request to CR8Event backend API
     *
     * @param method          The HTTP request Method.GET, Method.POST, Method.PUT, Method.DELETE <p> {@link Method} <p/>
     * @param url             The url to perform request
     * @param requestParams   The requestParams to be send to backend via post or put
     * @param clazz           The model to be transformed from JSON response
     * @param successListener The success listener
     * @param errorListener   The error listener
     */
    public baseModel(int method, String url, Map<String, String> requestParams, Class<T> clazz, Response.Listener<T> successListener, Response.ErrorListener errorListener) {
        this(method, url, clazz, null, successListener, errorListener);
        this.requestParams = requestParams;
    }

    /**
     * Constructor to create a new request to CR8Event backend API
     */
    public baseModel(String url, Map<String, String> requestParams, Class<T> clazz, Response.Listener<T> successListener, Response.ErrorListener errorListener, File file, String filePartName) {
        this(Method.POST, url, clazz, null, successListener, errorListener);
        this.requestParams = requestParams;
        this.file = file;
        this.filePartName = filePartName;

        httpEntity = buildMultipartEntity();
    }

    /**
     * Constructor to create a new request to CR8Event backend API. The default method is GET
     *
     * @param url             The url to perform request
     * @param clazz           The model to be transformed from JSON response
     * @param successListener The success listener
     * @param errorListener   The error listener
     */
    public baseModel(String url, Class<T> clazz, Response.Listener<T> successListener, Response.ErrorListener errorListener) {
        this(Method.GET, url, clazz, null, successListener, errorListener);
    }

    /**
     * Constructor to create a new request to CR8Event backend API.
     * The default method is GET.
     * The JSON request should return an array
     *
     * @param url             The url to perform request
     * @param type            The array type of the JSON response
     * @param successListener The success listener
     * @param errorListener   The error listener
     */
    public baseModel(String url, Type type, Response.Listener<T> successListener, Response.ErrorListener errorListener) {
        this(Method.GET, url, null, type, successListener, errorListener);
    }

    private HttpEntity buildMultipartEntity() {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        if (file != null) {
            builder.addBinaryBody(filePartName, file, ContentType.create("image/jpeg"), file.getName());
        }
        for (String key : requestParams.keySet()) {
            String value = requestParams.get(key);
            if (value != null)
                builder.addTextBody(key, value);
        }
        return builder.build();
    }


    @Override
    public String getBodyContentType() {
        if (httpEntity != null) {
            return httpEntity.getContentType().getValue();
        } else {
            return super.getBodyContentType();
        }
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (httpEntity != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                httpEntity.writeTo(bos);
            } catch (IOException e) {
                VolleyLog.e("IOException writing to ByteArrayOutputStream");
            }
            return bos.toByteArray();
        } else {
            return super.getBody();
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            Log.e("Error response json",new String(response.data, HttpHeaderParser.parseCharset(response.headers)));
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            T result;
            if (clazz != null) {
                result = gson.fromJson(json, clazz);
                VolleyLog.d(json);
            } else if(type !=null) {
                result = gson.fromJson(json, type);
            }else{
//                result =json;
                result = (T) json;
            }
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        if (listener != null)
            listener.onResponse(response);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        HashMap<String, String> params = new HashMap<String, String>();
        //add any param that might be passed
        params.putAll(this.requestParams);
        return params;
    }
}
