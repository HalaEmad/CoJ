/**
 * 
 */
package com.ibm.android.kit.network;

import android.content.Context;
import android.util.Log;

import com.ibm.android.kit.models.IResult;
import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.utils.file.AssetFile;
import com.ibm.android.kit.utils.http.HttpHandler;
import com.ibm.android.kit.utils.http.Request;
import com.ibm.android.kit.utils.http.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author bassam
 *
 */
public abstract class Req implements IResult {

	protected Context context;

	protected abstract int requestType();

	protected abstract boolean fromAssets();

	protected abstract String assetFileName();

	protected abstract Result parseResponse(Response response) throws JSONException;

	protected abstract String rawData() throws JSONException;

	protected abstract String url();

	public Req(Context context) {
		this.context = context;
	}

	protected String relativeUrl() {
		return "android.php";
	}

	protected JSONObject urlParams() throws JSONException {
		return null;
	}

	protected JSONObject headerData() throws JSONException {
		return null;
	}

	protected JSONObject urlEncodedData() throws JSONException {
		return null;
	}

	public Result execute() {

		Response response = null;
		Result result = null;

		try {

			Request request = new Request();
			request.type = requestType();
			request.requestId = id();
			request.url = url();
			request.headerData = headerData();
			request.urlParams = urlParams();
			request.urlEncodedData = urlEncodedData();
			request.contentType = contentType();
			request.rawData = rawData();
			request.timeout = timeout();

			Log.d("##REQ##", request.toString());

			if (!fromAssets()) {

				// send request
				HttpHandler httpHandler = new HttpHandler(context);
				response = httpHandler.sendRequest(request);

			} else {

				// assets
				try {
					AssetFile asset = new AssetFile(context);
					String json = asset.read(assetFileName());
					response = new Response(id(), json, Response.HTTP_ERROR_OK);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			if (response != null) {
				// Check for network and parsing errors
				switch (response.errorCode) {
				case HttpHandler.HTTP_ERROR_NO_NETWORK:
					result = new Result(ERROR_NO_NETWORK);
					break;
				case HttpHandler.HTTP_ERROR_IO:
					result = new Result(ERROR_NOT_OK);
					break;
				default:
					result = parseResponse(response);
				}
			} else {
				result = new Result(ERROR_NOT_OK);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			result = new Result(ERROR_NOT_OK);
		}

		return result;
	}

	protected int timeout() {
		return 20 * 1000;
	}

	protected String contentType() {

		return HttpHandler.CONTENT_TYPE_JSON;
	}

	protected int id() {
		return 0;
	}

}
