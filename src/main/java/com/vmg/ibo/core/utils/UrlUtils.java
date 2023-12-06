package com.vmg.ibo.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlUtils {
	public static String encodeUrl(String url) {
		try {
			String encodeURL = URLEncoder.encode(url, "UTF-8");
			return encodeURL;
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}

	public static String decodeUrl(String url) {
		try {
			String prevURL = "";
			String decodeURL = url;
			while (!prevURL.equals(decodeURL)) {
				prevURL = decodeURL;
				decodeURL = URLDecoder.decode(decodeURL, "UTF-8");
			}
			return decodeURL;
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}

	public static String getHost(String url) {
		URI uri;
		try {
			uri = new URI(url);
			return uri.getHost();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return url;
	}

	public static String getServerAddress(String url) {
		String result = "";
		int port = 0;
		URI uri;
		try {
			uri = new URI(url);
			port = uri.getPort();
			if (port > 0) {
				result = uri.getHost() + ":" + uri.getPort();
			} else {
				result = uri.getHost();
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return result;
	}
}
