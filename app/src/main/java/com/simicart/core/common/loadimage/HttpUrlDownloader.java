package com.simicart.core.common.loadimage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.simicart.core.config.Config;

public class HttpUrlDownloader implements UrlDownloader {
    private RequestPropertiesCallback mRequestPropertiesCallback;

    public RequestPropertiesCallback getRequestPropertiesCallback() {
        return mRequestPropertiesCallback;
    }

    public void setRequestPropertiesCallback(final RequestPropertiesCallback callback) {
        mRequestPropertiesCallback = callback;
    }


    @Override
    public void download(final Context context, final String url, final String filename, final UrlDownloaderCallback callback, final Runnable completion) {
        final AsyncTask<Void, Void, Void> downloader = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... params) {
				try {
					URL url_con = new URL(url);
					HttpURLConnection conn = (HttpURLConnection) url_con
							.openConnection();
					conn.setInstanceFollowRedirects(true);
					conn.setReadTimeout(10000 /* milliseconds */);
					conn.setConnectTimeout(15000 /* milliseconds */);
					conn.setRequestMethod("GET");
					String token = "Bearer " + Config.getInstance()
							.getSecretKey();
					conn.setRequestProperty("Authorization", token);
					conn.setDoInput(true);
					conn.setDoOutput(false);
					conn.connect();
					int status = conn.getResponseCode();
					if (status < 300) {
						InputStream is = conn.getInputStream();
						callback.onDownloadComplete(HttpUrlDownloader.this, is, null);
						return null;
					} else {
						Log.e("Drawable Manager ", "STATUS CODE " + status);
						return null;
					}
				} catch (Exception e) {
					Log.e("Drawable Manager ", e.toString());
					return null;
				}

			}

            @Override
            protected void onPostExecute(final Void result) {
                completion.run();
            }
        };

        UrlImageViewHelper.executeTask(downloader);
    }

    @Override
    public boolean allowCache() {
        return true;
    }
    
    @Override
    public boolean canDownloadUrl(String url) {
        return url.startsWith("http");
    }
    

}
