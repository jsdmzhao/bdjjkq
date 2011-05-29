package com.googlecode.jtiger.core.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.googlecode.jtiger.modules.upload.webapp.FileAccessServlet;

/**
 * 验证URL地址
 * @author sjz.w
 */
public final class URLAvailability {
	/**
	 * 验证URL地址是否有效
	 * @param urlPath 需要验证的地址
	 * @return
	 */
	public static synchronized String isImgUrl(String urlPath) {
		String resPath = null;
		try {
			URL url = new URL(urlPath);
			HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
			if (httpUrl.getResponseCode() == HttpURLConnection.HTTP_OK) {
				resPath = httpUrl.getURL().toString();
				if(!FileAccessServlet.isPic(resPath)) {
				  return null;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resPath;
	}
}
