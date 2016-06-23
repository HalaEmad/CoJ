package com.ibm.android.kit.models;

public interface IResult {

	/**
	 * server errors
	 */
	public static final int ERROR_OK = 1;
	public static final int ERROR_NOT_OK = -1;

	/**
	 * internal errors
	 */
	public static final int ERROR_NO_NETWORK = -1001;
}
