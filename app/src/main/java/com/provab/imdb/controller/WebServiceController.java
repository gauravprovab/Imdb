package com.provab.imdb.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.provab.imdb.interfaces.WebInterface;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;


public class WebServiceController {

    Context context;
    ProgressDialog pDialog;
    WebInterface myInterface;

    public WebServiceController(Context context, Object obj) {
        this.context = context;
        this.myInterface = (WebInterface) obj;
    }

    public void sendRequest(String url, RequestParams params, final int flag) {

        pDialog=new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait...");


        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(120000);
        client.get(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                // TODO Auto-generated method stub
                pDialog.dismiss();
                String response = "";
                try {
                    response = new String(arg2, "UTF-8");
                    Log.e("response", response);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
                myInterface.getResponse(response, flag);
            }

            @Override

            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                // TODO Auto-generated method stub
                pDialog.dismiss();

            }

        });
    }


}
