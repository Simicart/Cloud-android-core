package com.simicart.core.base.network.request.multi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.util.Log;

import com.simicart.core.base.network.request.MySSLSocketFactory;
import com.simicart.core.base.network.request.multi.SimiRequest.Method;
import com.simicart.core.config.Config;

public class SimiHttpClientStack implements SimiHttpStack {

    protected HttpClient mClient;

    public SimiHttpClientStack() {
        mClient = getNewHttpClient();
        HttpConnectionParams.setConnectionTimeout(mClient.getParams(), 6000);
        HttpConnectionParams.setSoTimeout(mClient.getParams(), 60000);
        mClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,
                true);
    }

    protected DefaultHttpClient getNewHttpClient() {
        DefaultHttpClient httpClient = null;
        try {
            if (httpClient == null) {
                HttpParams params = new BasicHttpParams();
                HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("http", PlainSocketFactory
                        .getSocketFactory(), 80));

                KeyStore trustStore = KeyStore.getInstance(KeyStore
                        .getDefaultType());
                trustStore.load(null, null);
                SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
                sf.setHostnameVerifier((X509HostnameVerifier) SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
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

    @Override
    public HttpResponse performRequest(SimiRequest request)
            throws ClientProtocolException, IOException {
        HttpUriRequest httpRequest = createHttpRequest(request);
        httpRequest.setHeader("Connection", "Keep-Alive");
        httpRequest.setHeader("Token", Config.getInstance().getSecretKey());
        HashMap<String, String> headerAddtional = request.getHeaderAddtional();
        if (null != headerAddtional) {
            addHeaders(httpRequest, headerAddtional);
        }
        return mClient.execute(httpRequest);
    }

    protected HttpUriRequest createHttpRequest(SimiRequest request) {
        int typeMethod = request.getTypeMethod();
        String url_extended = request.getUrl();
        String url = Config.getInstance().getBaseUrl() + url_extended;
        Log.e("SimiHttpClientStack ", "URL " + url);
        switch (typeMethod) {
            case Method.GET:
                return new HttpGet(url);
            case Method.POST:
                HttpPost httpPost = new HttpPost(url);
                JSONObject postBody = request.getBody();
                Log.e("SimiHttpClientStack ", "PARAMS " + postBody);
                UrlEncodedFormEntity entity = createEntity(postBody);
                if (null != entity) {
                    httpPost.setEntity(entity);
                }
                return httpPost;

            case Method.DELETE:

                HttpDelete httpDelete = new HttpDelete(url);

                return httpDelete;

            case Method.HEAD:

                HttpHead httpHead = new HttpHead(url);

                return httpHead;

            case Method.OPTIONS:

                HttpOptions httpOptions = new HttpOptions(url);

                return httpOptions;

            case Method.TRACE:
                HttpTrace httpTrace = new HttpTrace(url);

                return httpTrace;

            default:
                HttpPost httpPost_default = new HttpPost(url);

                return httpPost_default;
        }

    }

    protected void addHeaders(HttpUriRequest httpRequest,
                              Map<String, String> headers) {
        for (String key : headers.keySet()) {
            httpRequest.setHeader(key, headers.get(key));
        }
    }

    protected UrlEncodedFormEntity createEntity(JSONObject json) {
        if (null == json) {
            return null;
        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("data", json.toString()));
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
                    nameValuePairs, "UTF-8");
            return entity;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}
