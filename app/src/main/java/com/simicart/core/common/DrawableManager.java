package com.simicart.core.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.simicart.MainActivity;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.network.request.MySSLSocketFactory;
import com.simicart.core.common.loadimage.UrlImageViewCallback;
import com.simicart.core.common.loadimage.UrlImageViewHelper;
import com.simicart.core.common.universalimageloader.core.ImageLoader;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

public class DrawableManager {

	protected static LruCache<String, Bitmap> mMemoryCache;
	protected static DiskLruCache mDiskLruCache;
	protected static Object mDiskCackeLock = new Object();
	protected static boolean mDiskCacheStarting = true;
	protected static int DISK_CACHE_SIZE = 1024 * 1024 * 100;
	protected static String DISK_CACHE_SUBDIR = "thumbnails";
	protected static boolean isInitial = false;

	public static void init() {
		if (!isInitial) {
			if (null == mMemoryCache) {
				final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
				final int cacheSize = maxMemory / 8;
				mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
					@Override
					protected int sizeOf(String key, Bitmap bitmap) {
						return bitmap.getByteCount() / 1024;
					}
				};
			}
			File cacheDir = getDiskCacheDir();

			InitDiskCacheTask task = (new DrawableManager()).new InitDiskCacheTask();
			task.execute(cacheDir);

			isInitial = true;
		}
	}

	public class InitDiskCacheTask extends AsyncTask<File, Void, Void> {

		@Override
		protected Void doInBackground(File... params) {

			synchronized (mDiskCackeLock) {
				File cacheDir = params[0];

				try {
					mDiskLruCache = DiskLruCache.open(cacheDir, 1, 1,
							DISK_CACHE_SIZE);
					mDiskCacheStarting = false;
					mDiskCackeLock.notifyAll();
				} catch (IOException e) {
					Log.e("DrawableManager ", "InitDiskCacheTask IOEception "
							+ e.getMessage());
				}
			}

			return null;
		}

	}

	public static File getDiskCacheDir() {
		Context context = MainActivity.context;
		String uniqueName = DISK_CACHE_SUBDIR;
		final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable() ? context
				.getExternalCacheDir().getPath() : context.getCacheDir()
				.getPath();

		File cacheFile = new File(cachePath + File.separator + uniqueName);
		if (!cacheFile.exists()) {
			cacheFile.mkdirs();
		}
		return cacheFile;
	}

	public static void fetchDrawableDetailOnThread(final String urlString,
			final ImageView imageView) {
		UrlImageViewHelper.setUrlDrawable(imageView, urlString);
	}

	public static void fetchDrawableIConOnThread(final String urlString,
			final ImageView imageView, final Context context, final int color) {

		UrlImageViewHelper.setUrlDrawable(imageView, urlString, new UrlImageViewCallback() {
			@Override
			public void onLoaded(ImageView imageView, Bitmap bitmap, String url, boolean loadedFromCache) {
				Resources resource = SimiManager.getIntance()
						.getCurrentContext().getResources();
				Drawable drawable = null;
				if (bitmap != null) {
					drawable = new BitmapDrawable(resource, bitmap);
					drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
				} else {
					bitmap = BitmapFactory.decodeResource(resource, Rconfig
							.getInstance().drawable("default_icon"));
					bitmap = Bitmap.createScaledBitmap(bitmap, 80, 80, true);
					drawable = new BitmapDrawable(context.getResources(),
							bitmap);
					drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
				}
				if (null != drawable) {
					imageView.setImageDrawable(drawable);
				}
			}

			@Override
			public void onComplete() {

			}
		});
	}

	public static void fetchDrawableOnThread(final String urlString,
			final ImageView imageView) {
		UrlImageViewHelper.setUrlDrawable(imageView, urlString);
	}

	public static void fetchDrawableOnThreadForZTheme(final String urlString,
			final ImageView imageView) {
		UrlImageViewHelper.setUrlDrawable(imageView, urlString);
	}

	@SuppressWarnings("deprecation")
	public static void fetchDrawableOnThread(final String urlString,
			final TextView textview) {
	ImageView image = new ImageView(MainActivity.context);
		UrlImageViewHelper.setUrlDrawable(image, urlString, new UrlImageViewCallback() {
			@Override
			public void onLoaded(ImageView imageView, Bitmap bitmap, String url, boolean loadedFromCache) {
				if (bitmap != null) {
					Resources resource = SimiManager.getIntance()
							.getCurrentContext().getResources();
					Drawable drawable = new BitmapDrawable(resource, bitmap);
					textview.setBackgroundDrawable(drawable);
				} else {
					textview.setBackgroundResource(Rconfig.getInstance()
							.drawable("default_icon"));
				}
			}

			@Override
			public void onComplete() {

			}
		});
	}

	public static void fetchItemDrawableOnThread(final String urlString,
			final ImageView imageView) {
		UrlImageViewHelper.setUrlDrawable(imageView, urlString);
	}

	public static void getBitmap(final Handler handler, final String urlString) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				Bitmap bitmap = excutePostForBitMap(urlString);
				if (bitmap != null) {
					Message message = handler.obtainMessage(1, bitmap);
					handler.sendMessage(message);
				}
			}
		};
		thread.start();
	}

	public static Bitmap excutePostForBitMap(String url) {
		try {
			Bitmap bitMap = null;
			URL url_con = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) url_con
					.openConnection();
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
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				BufferedInputStream bs = new BufferedInputStream(is);
				byte[] buff = new byte[1024];
				int read = 0;
				while ((read = bs.read(buff)) > 0) {
					bos.write(buff, 0, read);
					buff = new byte[1024];
				}
				byte[] bytes = bos.toByteArray();
				try {
					bitMap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
				} catch (OutOfMemoryError e) {
					Log.e("Map", "Tile Loader (241) Out Of Memory Error " + e.getLocalizedMessage());
					System.gc();
				}
				// conn.disconnect();
				return bitMap;
			} else {
				Log.e("Drawable Manager ", "STATUS CODE " + status);
				return null;
			}
		} catch (Exception e) {
			Log.e("Drawable Manager ", e.toString());
			return null;
		}

	}

	public static DefaultHttpClient getNewHttpClient() {
		DefaultHttpClient httpClient = null;
		try {
			if (httpClient == null) {
				KeyStore trustStore = KeyStore.getInstance(KeyStore
						.getDefaultType());
				trustStore.load(null, null);
				SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
				sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				HttpParams params = new BasicHttpParams();
				HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
				HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
				SchemeRegistry registry = new SchemeRegistry();
				registry.register(new Scheme("http", PlainSocketFactory
						.getSocketFactory(), 80));
				registry.register(new Scheme("https", sf, 443));
				ClientConnectionManager ccm = new ThreadSafeClientConnManager(
						params, registry);
				httpClient = new DefaultHttpClient(ccm, params);
			}
			return httpClient;

		} catch (Exception e) {
			if (httpClient == null) {
				httpClient = new DefaultHttpClient();
			}
			return httpClient;
		}
	}

}
