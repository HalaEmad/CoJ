/*
 * Licensed Materials - Property of IBM
 *
 * 5725-D69
 *
 * (C) Copyright IBM Corp. 2014 All rights reserved.
 *
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with
 * IBM Corp.
 *
 * DISCLAIMER OF WARRANTIES. The following code is sample code
 * created by IBM Corporation. This sample code is warranted to perform its
 * intended function only if used unmodified. If you modify
 * this code then it is considered provided "AS IS", without
 * warranty of any kind. Notwithstanding the foregoing, IBM
 * shall not be liable for any damages arising out of your use
 * of the sample code, even if they have been advised of the
 * possibility of such damages.
 */

/**
 * WL.Server.invokeHttp(parameters) accepts the following json object as an
 * argument: { // Mandatory method : 'get' , 'post', 'delete' , 'put' or 'head'
 * path: value, // Optional returnedContentType: any known mime-type or one of
 * "json", "css", "csv", "javascript", "plain", "xml", "html"
 * returnedContentEncoding : 'encoding', parameters: {name1: value1, ... },
 * headers: {name1: value1, ... }, cookies: {name1: value1, ... }, body: {
 * contentType: 'text/xml; charset=utf-8' or similar value, content: stringValue },
 * transformation: { type: 'default', or 'xslFile', xslFile: fileName } }
 */

var _Constants = {
	JSESSIONID : "JSESSIONID",
	SESSIONID : "com.ibm.ioc.sessionid",
	REALM : "IOCAuthRealm",
	LTPATOKEN : "LtpaToken2"
};

/**
 * Inserts the DataItem in the Datasource with datasourceId Applies the list of
 * properties to create the item
 * 
 * @returns status of the request
 */
var dataSourceId = '12';
var boundaries = '';
/*
 * itemID : Officer name uID:
 */
function updateOfficerLocation(uid, lat, longtude, basicauth, sessionId) {
	/*testing on one of the userId with name marius in once cases while testing
	disappeard ,and this is happened cause property_5 was deleted */
	
	var itemID = resolveUserID(uid, basicauth, sessionId);
	// get name from result
	var properties = JSON.parse("{\"NAME\": \"Resource Location - " + uid
			+ "\",\"LOCATION\": \"Point(" + lat + " " + longtude + ")\"}");
	var result;
	
	if (itemID != undefined) {
		result = updateDataItem(dataSourceId, itemID, properties, basicauth,
				sessionId);

	} else {
		result = addDataItem(dataSourceId, properties,basicauth, sessionId);
	}
	if (result && result.responseHeaders) {
		delete result.responseHeaders;
	}
	return result;

}

function resolveUserID(uid, basicauth, sessionId) {
	path = "/ibm/ioc/api/spatial-service/features/" + dataSourceId
			+ "?criterion=PROPERTY_5=" + uid;

	var input = {
		method : 'get',
		returnedContentType : 'json',
		path : path,
		headers : {
			Authorization : "Basic " + basicauth,
			Accept : "application/json"
		}
	};
	input.headers[_Constants.SESSIONID] = sessionId;
	var resultSet = WL.Server.invokeHttp(input);
	if (resultSet.features && resultSet.features.length == 1)
		return resultSet.features[0].id;
	else {
		return undefined;
	}
}

function addDataItem(datasourceId, properties, basicauth, sessionId) {

	WL.Logger.info("DataInjectionServiceAdapter: addDataItem");

	var strBody = JSON.stringify(properties);
	WL.Logger.info("DataInjectionServiceAdapter: addDataItem: Data Item JSON: "
			+ strBody);

	var input = {
		method : 'post',
		returnedContentType : 'application/json',
		path : "/ibm/ioc/api/data-injection-service/datablocks/" + datasourceId
				+ "/dataitems",
		headers : {
			Authorization : "Basic " + basicauth,
			Accept : "application/json"
		},
		body : {
			content : strBody,
			contentType : "application/json"
		}
	};

	// send the required data to pass thru the CSRF filter
	input.headers[_Constants.SESSIONID] = sessionId;
	input.headers["Cookie"] += "; " + _Constants.JSESSIONID + "=" + sessionId;

	return WL.Server.invokeHttp(input);
}

/**
 * Updates the DataItem with itemId in the Datasource with datasourceId Applies
 * the list of properties to update the item
 * 
 * @returns status of the request
 */
function updateDataItem(datasourceId, itemId, properties, basicauth, sessionId) {

	WL.Logger.info("DataInjectionServiceAdapter: updateDataItem: " + itemId
			+ " in Datasource: " + datasourceId);

	var strBody = JSON.stringify(properties);
	WL.Logger
			.info("DataInjectionServiceAdapter: updateDataItem: Data Item JSON: "
					+ strBody);

	var input = {
		method : 'put',
		returnedContentType : 'application/json',
		path : "/ibm/ioc/api/data-injection-service/datablocks/" + datasourceId
				+ "/dataitems/" + itemId,
		headers : {
			Authorization : "Basic " + basicauth,
			Accept : "application/json"
		},
		body : {
			content : strBody,
			contentType : "application/json"
		}
	};

	// send the required data to pass thru the CSRF filter
	input.headers[_Constants.SESSIONID] = sessionId;
	// input.headers["Cookie"] += "; "+_Constants.JSESSIONID + "=" + sessionId;

	return WL.Server.invokeHttp(input);
}

/**
 * Cancels the DataItem with itemId in the Datasource with datasourceId
 * 
 * @returns status of the request
 */
//function cancelDataItem(datasourceId, itemId, ltpaToken, sessionId) {
//
//	WL.Logger.info("DataInjectionServiceAdapter: cancelDataItem: " + itemId
//			+ " in Datasource: " + datasourceId);
//	var input = {
//		method : 'delete',
//		returnedContentType : 'application/json',
//		path : "/ibm/ioc/api/data-injection-service/datablocks/" + datasourceId
//				+ "/dataitems/" + itemId,
//		headers : {
//			Accept : "application/json",
//			Cookie : _Constants.LTPATOKEN + ltpaToken
//		}
//	};
//
//	// send the required data to pass thru the CSRF filter
//	input.headers[_Constants.SESSIONID] = sessionId;
//	input.headers["Cookie"] += "; " + _Constants.JSESSIONID + "=" + sessionId;
//
//	return WL.Server.invokeHttp(input);
//}