package com.demoprj;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Mounil on 21/02/2016.
 */
public class SendSms extends AsyncTask<Void, Void, Void> {
    String message = "";
    String number = "";

    public SendSms(Context context, String message,String number) {
        this.message = message;
        this.number = number;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (!message.equals("") && !number.equals("")) {
            String query = "";
            try {
                query = URLEncoder.encode(message, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String link = "http://smshorizon.co.in/api/sendsms.php?user=pranay9803&apikey=NKlJDobd6oTn2CpCQYMB&mobile="+number+"&message="+query+"&senderid=MYTEXT&type=txt";
            URL url = null;
            InputStream in = null;
            int resCode = -1;
            try {
                url = new URL(link);
                URLConnection urlConn = url.openConnection();
                if (!(urlConn instanceof HttpURLConnection)) {
                    throw new IOException("URL is not an Http URL");
                }
                HttpURLConnection httpConn = (HttpURLConnection) urlConn;
                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setRequestMethod("GET");
                httpConn.connect();
                resCode = httpConn.getResponseCode();

                if (resCode == HttpURLConnection.HTTP_OK) {
                    in = httpConn.getInputStream();
                    BufferedReader reader =new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    String webPage = "",data="";

                    while ((data = reader.readLine()) != null){
                        webPage += data + "\n";
                    }
                    Log.e("",in+"");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
