package com.ibm.android.kit.utils.http;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.ibm.android.kit.utils.ConnectionUtility;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

public class HttpHandler {

	public static final int HTTP_ERROR_NO_NETWORK = -1000;
	public static final int HTTP_ERROR_IO = -1001;

	public static final String CONTENT_TYPE_XML = "application/xml";
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_TEXT_JSON = "text/JSON";
	public static final String CONTENT_TYPE_URL = "application/x-www-form-urlencoded";
	public static final String ACCEPT_ENCODING = "Accept-Encoding";

	private static final int HTTP_POST = 1;
	private static final int HTTP_GET = 2;
	private static final int HTTP_PUT = 3;

	private static final int DEFAULT_CONNECTION_TIMEOUT = (int) (100 * 60 * 2);

	private static final String POST_LOG_TAG = "##POST##";
	private static final String GET_LOG_TAG = "##GET##";
	private static final String PUT_LOG_TAG = "##PUT##";

	private HttpUriRequest request;
	private String contentType;
	private int connectionTimeout;

	private Context context;

	public HttpHandler(Context context) {

		this.context = context;
		contentType = CONTENT_TYPE_JSON;
		connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
	}

	private Response post(int requestId, String url, String contentType, JSONObject urlParams, JSONObject headerData,
			JSONObject urlEncodedData, String rawData, int timeout) {

		if (contentType == null)
			contentType = this.contentType;

		if (timeout == -1) {
			timeout = connectionTimeout;
		}

		HttpParams httpParameters = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

		if (urlParams != null) {
			url = buildUrlParams(url, urlParams);
		}

		request = new HttpPost(url);

		Log.i(POST_LOG_TAG, "[[[START REQUEST]]]");
		Log.i(POST_LOG_TAG, "[URL]: " + url);

		// parse header data
		if (headerData != null) {

			buildHeaderData((AbstractHttpMessage) request, headerData);
		}

		// parse url encoded data
		if (urlEncodedData != null && contentType == CONTENT_TYPE_URL) {

			buildUrlEncodedData((HttpEntityEnclosingRequest) request, urlEncodedData);
		}

		// build raw data
		if (rawData != null) {

			buildRawData((HttpEntityEnclosingRequest) request, rawData);
		}

		return sendRequest(HTTP_POST, requestId, httpClient);
	}

	@SuppressWarnings("unchecked")
	private String buildUrlParams(String url, JSONObject urlParams) {

		Uri.Builder builder = Uri.parse(url).buildUpon();
		try {
			if (urlParams != null) {
				Iterator<String> keys = urlParams.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					builder.appendQueryParameter(key, (String) urlParams.get(key));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return builder.build().toString();
	}

	private Response get(int requestId, String url, String contentType, JSONObject urlParams, JSONObject headerData,
			int timeout) {

		if (contentType == null)
			contentType = this.contentType;

		if (timeout == -1) {
			timeout = connectionTimeout;
		}

		HttpParams httpParameters = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);

		// get HTTP client
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

		if (urlParams != null) {
			url = buildUrlParams(url, urlParams);
		}

		request = new HttpGet(url);

		Log.i(GET_LOG_TAG, "[[[START REQUEST]]]");
		Log.i(GET_LOG_TAG, "[URL]: " + url);

		// set content type
		request.setHeader("Content-Type", contentType);

		// parse header data
		if (headerData != null) {

			buildHeaderData((AbstractHttpMessage) request, headerData);
		}

		return sendRequest(HTTP_GET, requestId, httpClient);
	}

	private Response put(int requestId, String url, String contentType, JSONObject urlParams, JSONObject headerData,
			JSONObject urlEncodedData, String rawData, int timeout) {

		if (contentType == null)
			contentType = this.contentType;

		if (timeout == -1) {
			timeout = connectionTimeout;
		}

		HttpParams httpParameters = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

		if (urlParams != null) {
			url = buildUrlParams(url, urlParams);
		}

		request = new HttpPut(url);

		Log.i(PUT_LOG_TAG, "[[[START REQUEST]]]");
		Log.i(PUT_LOG_TAG, "[URL]: " + url);

		// parse header data
		if (headerData != null) {

			buildHeaderData((AbstractHttpMessage) request, headerData);
		}

		// parse url encoded data
		if (urlEncodedData != null && contentType == CONTENT_TYPE_URL) {

			buildUrlEncodedData((HttpEntityEnclosingRequest) request, urlEncodedData);
		}

		// build raw data
		if (rawData != null) {

			buildRawData((HttpEntityEnclosingRequest) request, rawData);
		}

		return sendRequest(HTTP_PUT, requestId, httpClient);
	}

	@SuppressWarnings("unchecked")
	private void buildHeaderData(AbstractHttpMessage req, JSONObject headerData) {

		try {
			if (headerData != null) {
				Iterator<String> keys = headerData.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					req.setHeader(key, (String) headerData.get(key));

					if (req instanceof HttpPost) {
						Log.i(POST_LOG_TAG, "[HEADER]: KEY: " + key + ", VALUE: " + headerData.get(key));
					} else if (req instanceof HttpGet) {
						Log.i(GET_LOG_TAG, "[HEADER]: KEY: " + key + ", VALUE: " + headerData.get(key));
					} else if (req instanceof HttpPut) {
						Log.i(PUT_LOG_TAG, "[HEADER]: KEY: " + key + ", VALUE: " + headerData.get(key));
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void buildUrlEncodedData(HttpEntityEnclosingRequest req, JSONObject urlEncodedData) {

		if (urlEncodedData != null) {

			StringEntity stringEntity = null;
			try {

				String urlData = "";

				Iterator<String> keys = urlEncodedData.keys();
				while (keys.hasNext()) {

					String key = keys.next();
					String pairData = key + "=" + urlEncodedData.get(key);
					// pairData = URLEncoder.encode(pairData, "UTF-8");
					urlData += pairData;
					if (keys.hasNext()) {
						urlData += "&";
					}

					if (req instanceof HttpPost) {
						Log.i(POST_LOG_TAG, "[URL ENCODED DATA]: KEY: " + key + ", VALUE: " + urlEncodedData.get(key));
					} else if (req instanceof HttpGet) {
						Log.i(GET_LOG_TAG, "[URL ENCODED DATA]: KEY: " + key + ", VALUE: " + urlEncodedData.get(key));
					} else if (req instanceof HttpPut) {
						Log.i(PUT_LOG_TAG, "[URL ENCODED DATA]: KEY: " + key + ", VALUE: " + urlEncodedData.get(key));
					}
				}

				stringEntity = new StringEntity(urlData, HTTP.UTF_8);
				stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE_URL));

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			req.setEntity(stringEntity);
		}
	}

	private void buildRawData(HttpEntityEnclosingRequest req, String rawData) {

		StringEntity stringEntity = null;
		try {

			stringEntity = new StringEntity(rawData, HTTP.UTF_8);
			stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, contentType));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (req instanceof HttpPost) {
			Log.i(POST_LOG_TAG, "[PAYLOAD]: " + rawData);
		} else if (req instanceof HttpGet) {
			Log.i(GET_LOG_TAG, "[PAYLOAD]: " + rawData);
		} else if (req instanceof HttpPut) {
			Log.i(PUT_LOG_TAG, "[PAYLOAD]: " + rawData);
		}

		req.setEntity(stringEntity);
	}

	public Response sendRequest(Request request) {

		if (!ConnectionUtility.isNetworkConnectionExist(context)) {
			return new Response(request.requestId, HTTP_ERROR_NO_NETWORK);
		}

		if (request != null) {
			switch (request.type) {
			case Request.TYPE_GET:
				return get(request.requestId, request.url, request.contentType, request.urlParams, request.headerData,
						request.timeout);

			case Request.TYPE_POST:
				return post(request.requestId, request.url, request.contentType, request.urlParams, request.headerData,
						request.urlEncodedData, request.rawData, request.timeout);

			case Request.TYPE_PUT:
				return put(request.requestId, request.url, request.contentType, request.urlParams, request.headerData,
						request.urlEncodedData, request.rawData, request.timeout);
			}
		}

		return new Response(request.requestId, HTTP_ERROR_IO);
	}

	private Response sendRequest(int type, int requestId, DefaultHttpClient httpClient) {

		try {
			HttpResponse response = httpClient.execute(request);

			return parseResponse(type, requestId, response);

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

		return new Response(requestId, HTTP_ERROR_IO);
	}

	private Response parseResponse(int type, int requestId, HttpResponse response) {

		String reply = null;
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		HttpEntity entity = response.getEntity();

		try {

			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content, "UTF-8"));

			String line;
			StringBuilder builder = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			reply = builder.toString();

		} catch (Exception e) {

			e.printStackTrace();
		}

		String tag = "";
		switch (type) {
		case HTTP_POST:
			tag = POST_LOG_TAG;
			break;

		case HTTP_GET:
			tag = GET_LOG_TAG;
			break;

		case HTTP_PUT:
			tag = PUT_LOG_TAG;
			break;
		}
		Log.i(tag, "[STATUS CODE]: " + statusCode);
		Log.i(tag, "[RESPONSE]: " + reply);
		Log.i(tag, "[[[END REQUEST]]]");

		return new Response(requestId, reply, statusCode);
	}

	/**
	 * abort current http request
	 */
	public void abort() {

		new Thread() {

			@Override
			public void run() {

				if (request != null && !request.isAborted()) {
					request.abort();
					Log.d("##ABORT###", "request aborted");
				}
			}
		}.start();

	}

	public static boolean isHttpErrorSuccess(int error) {

		if (error >= Response.HTTP_ERROR_OK && error < Response.HTTP_ERROR_REDIRECT) {
			return true;
		}

		return false;
	}
}
