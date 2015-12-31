package com.simicart.core.common.loadimage;

import java.util.ArrayList;

import org.apache.http.NameValuePair;

import android.content.Context;

public interface RequestPropertiesCallback {
	public ArrayList<NameValuePair> getHeadersForRequest(Context context, String url);
}
