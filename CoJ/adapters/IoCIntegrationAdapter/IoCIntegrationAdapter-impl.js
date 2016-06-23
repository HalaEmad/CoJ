/*This procedure is responsible for getting the
 *  list of incidents that got created on the IoC server
 *  
 */

function getEvents(dataSourceId, boundaries, token) {
path = "/ibm/ioc/api/spatial-service/features/" + dataSourceId;

var user = WL.Server.getActiveUser(_Constants.REALM); httpHeaders = {
         Accept : "application/json",
Cookie : _Constants.LTPATOKEN + "=" + user.attributes.ltpaToken };
httpHeaders[_Constants.SESSIONID] = user.attributes.sessionId; httpHeaders["Cookie"] += "; " + _Constants.JSESSIONID + "="
            + user.attributes.sessionId;
         var input = {
            method : 'get',
            returnedContentType : 'json',
            path : path,
            headers : httpHeaders,
            parameters: {
              boundaries: boundaries,
              criterion: "deleteFlag=0"
            }
};
         return WL.Server.invokeHttp(input);
   }

/*This procedure will be responsible for updating incidents info*/

function reportEvent(startDate, endDate, lastUpdate, location, name, severity,
		sentBy, timezone, id, dataSourceId, token) {
		      var body = {
		        STARTDATETIME : startDate,
		        ENDDATETIME : endDate,
		        lastUpdateTime : lastUpdate,
		        LOCATION : location,
		        NAME : name,
		        PROPERTY_2 : id,
		        PROPERTY_3 : sentBy,
		        PROPERTY_4 : severity,
		        TIMEZONEOFFSET : timezone
		      };
		      var strBody = JSON.stringify(body);
		path = "/ibm/ioc/api/data-injection-service/datablocks/" + dataSourceId + "/dataitems";
		var user = WL.Server.getActiveUser(_Constants.REALM); httpHeaders = {
		        Accept : "application/json",
		Cookie : _Constants.LTPATOKEN + "=" + user.attributes.ltpaToken };
		httpHeaders[_Constants.SESSIONID] = user.attributes.sessionId; httpHeaders["Cookie"] += "; " + _Constants.JSESSIONID + "="
		           + user.attributes.sessionId;
		      var input = {
		        method : 'post',
		        returnedContentType : 'json',
		        path : path,
		        headers : httpHeaders,
		        body : {
		           content : strBody,
		           contentType : "application/json"
		        }
		};
		      return WL.Server.invokeHttp(input);
		   }