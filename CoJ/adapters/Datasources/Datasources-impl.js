/*This procedure is responsible for getting the
 *  list of incidents that got created on the IoC server
 *  
 */
var _Constants = {
	JSESSIONID : "JSESSIONID",
	SESSIONID : "com.ibm.ioc.sessionid",
	REALM : "IOCAuthRealm",
	LTPATOKEN : "LtpaToken2"
};
var boundaries='';
function getEvents(dataSourceId, basicauth,sessionId) {
	path = "/ibm/ioc/api/spatial-service/features/" + dataSourceId;

	//httpHeaders[_Constants.SESSIONID] = sessionId;
	var input = {
		method : 'get',
		returnedContentType : 'json',
		path : path,
		headers : {
			Authorization : "Basic " + basicauth,
			Accept : "application/json"
		}
//		parameters : {
//			boundaries : boundaries,
//			criterion : "deleteFlag=0"
//		}
	};
	input.headers[_Constants.SESSIONID] = sessionId;
	return WL.Server.invokeHttp(input);
}
/* This procedure will be responsible for updating incidents info */

// function reportEvent(startDate, endDate, lastUpdate, location, name,
// severity,
// sentBy, timezone, id, dataSourceId, token) {
// var body = {
// STARTDATETIME : startDate,
// ENDDATETIME : endDate,
// lastUpdateTime : lastUpdate,
// LOCATION : location,
// NAME : name,
// PROPERTY_2 : id,
// PROPERTY_3 : sentBy,
// PROPERTY_4 : severity,
// TIMEZONEOFFSET : timezone
// };
// var strBody = JSON.stringify(body);
// path = "/ibm/ioc/api/data-injection-service/datablocks/" + dataSourceId +
// "/dataitems";
// var user = WL.Server.getActiveUser(_Constants.REALM); httpHeaders = {
// Accept : "application/json",
// /*Cookie : _Constants.LTPATOKEN + "=" + user.attributes.ltpaToken */};
// httpHeaders[_Constants.SESSIONID] =
// "0000JYOauZ2xrhUKB_p_5HtNNzC:-1:1821ft3bp";//user.attributes.sessionId;
// httpHeaders["Cookie"] += "; " + _Constants.JSESSIONID + "="
// + "0000JYOauZ2xrhUKB_p_5HtNNzC:-1:1821ft3bp";//user.attributes.sessionId;
// var input = {
// method : 'post',
// returnedContentType : 'json',
// path : path,
// headers : httpHeaders,
// body : {
// content : strBody,
// contentType : "application/json"
// }
// };
// return WL.Server.invokeHttp(input);
// }
/* Mapping datasources params */

function dataSourceMapping(dataSourceId) {

	path = "/ibm/ioc/api/datasource-service/datasources/" + dataSourceId;
	var user = WL.Server.getActiveUser(_Constants.REALM);
	httpHeaders = {
		Accept : "application/json",
	Cookie : _Constants.LTPATOKEN + "=" + user.attributes.ltpaToken};
	httpHeaders[_Constants.SESSIONID] = user.attributes.sessionId;
	var input = {
		method : 'post',
		returnedContentType : 'json',
		path : path,
		headers : httpHeaders
	};
	return WL.Server.invokeHttp(input);
}