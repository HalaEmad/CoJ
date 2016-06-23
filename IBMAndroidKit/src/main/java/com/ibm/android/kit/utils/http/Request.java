package com.ibm.android.kit.utils.http;

import org.json.JSONObject;

public class Request {

	public static final int TYPE_GET = 0;
	public static final int TYPE_POST = 1;
	public static final int TYPE_PUT = 3;

	static final int DEFAULT_REQUEST_TIMEOUT = (int) (20 * 1000);

	public int type;

	public String url;

	public JSONObject headerData = new JSONObject();

	public int timeout = DEFAULT_REQUEST_TIMEOUT;

	public int requestId;

	public String contentType = HttpHandler.CONTENT_TYPE_JSON;

	public JSONObject urlEncodedData;

	public JSONObject urlParams = new JSONObject();

	public String rawData;

	public long timeStamp;

	public Request() {

	}

	public Request(int type, String url, String data, int requestId) {

		this.type = type;

		this.url = url;

		this.rawData = data;

		this.timeout = DEFAULT_REQUEST_TIMEOUT;

		this.requestId = requestId;
	}

	public Request(int type, String url, String data, int timeout, int requestId) {

		this.type = type;

		this.url = url;

		this.rawData = data;

		this.timeout = timeout;

		this.requestId = requestId;
	}

	public Request(Request request) {

		this.type = request.type;

		this.url = request.url;

		this.timeout = request.timeout;

		this.requestId = request.requestId;

		this.contentType = request.contentType;

		this.headerData = request.headerData;

		this.rawData = request.rawData;

		this.urlEncodedData = request.urlEncodedData;

		this.urlParams = request.urlParams;

		this.timeStamp = request.timeStamp;
	}

	@Override
	public String toString() {
		return "type[" + type + "], url[" + url + "], timeout[" + timeout + "], requestId[" + requestId
				+ "], contentType[" + contentType + "], urlParams[" + urlParams + "], headerData[" + headerData
				+ "], rawData[" + rawData + "], urlEncoded[" + urlEncodedData + "], timestamp[" + timeStamp + "]";
	}

}
