package com.amazonaws.amplify.pushnotification.modules;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RNPushNotificationBigPic extends AsyncTask<Bundle, Void, Bitmap> {
    private Bundle bundleMessage;
    private Context ctx;

    public RNPushNotificationBigPic(Context context) {
        super();
        this.ctx = context;
    }
    @Override
    protected Bitmap doInBackground(Bundle... params) {
        bundleMessage = params[0];
        String bigPic = params[0].getString("pinpoint.notification.imageUrl");
        if (bigPic != null) {
            try {
                URL url = new URL(bigPic);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        Application applicationContext = (Application) ctx.getApplicationContext();
        new RNPushNotificationHelper(applicationContext).sendToNotificationCentre(bundleMessage, result);
    }
}
