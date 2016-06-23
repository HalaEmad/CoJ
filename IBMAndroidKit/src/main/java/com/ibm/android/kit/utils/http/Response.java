/**
 * 
 */
package com.ibm.android.kit.utils.http;

/**
 * @author bassam
 *
 */
public class Response {

	public static final int HTTP_ERROR_OK = 200;
	public static final int HTTP_ERROR_REDIRECT = 300;
	public static final int HTTP_ERROR_UNAUTHORIZED = 401;

	public int requestId;

	public int errorCode;
	public String reply;

	public Response(int requestId, String reply, int errorCode) {

		this.requestId = requestId;
		this.reply = reply;
		this.errorCode = errorCode;
	}

	public Response(int requestId, int errorCode) {

		this.requestId = requestId;

		this.errorCode = errorCode;
	}
}
