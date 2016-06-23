var _Constants = {
   JSESSIONID : "JSESSIONID",
   SESSIONID : "com.ibm.ioc.sessionid",
   REALM : "IOCAuthRealm",
   LTPATOKEN : "LtpaToken2"
};
/*auth will be the encoded value base64 of username and password like hala:1234
so user name is hala & password is 1234
*/
function authenticateUser(auth) {
var input = {
method : 'get',
returnedContentType : 'application/json', 
path : "/ibm/ioc/api/login-service/users", 
headers : {
         Authorization : "Basic " + auth,
         Accept : "application/json"
      }
};
// get the JSESSIONID out of the request header 
var request = WL.Server.getClientRequest();
var cookieHeader = request.getHeader("Cookie"); var cookies = cookieHeader.split(";");
var jSessionId = null;
for ( var i = 0; i < cookies.length; i++) {
var cookie = cookies[i];
if (cookie.indexOf(_Constants.JSESSIONID) >= 0) {
         var valuePattern = /[=].*/;
         var value = "" + valuePattern.exec(cookie);
         jSessionId = value.trim().substr(1);
         break;
} }
if (jSessionId) {
input.headers[_Constants.SESSIONID] = jSessionId; 
input.headers["Cookie"] = _Constants.JSESSIONID + "=" + jSessionId;
   }
   var result = WL.Server.invokeHttp(input);
// if successful, set active user
if (result.statusCode && result.statusCode == 200 && result.array) {
var user = JSON.parse(JSON.stringify(result.array[0])); // clone user.base64 = auth;
user.sessionId = null;
user.ltpaToken = null;
if (result.responseHeaders) {
var responseHeaders = result.responseHeaders;
// process the headers
user.sessionId = responseHeaders[_Constants.SESSIONID];
// process the cookies
var setCookieHeader = responseHeaders["Set-Cookie"]; 
var cookieData = setCookieHeader.split(";");

for ( var i = 0; i < cookieData.length; i++) {
              var cookie = cookieData[i];
              if (cookie.indexOf(_Constants.LTPATOKEN) > -1) {
var valuePattern = /[=].*/;
var value = "" + valuePattern.exec(cookie); user.ltpaToken = value.trim().substr(1); // strip the // leading "="
break;
} }
}
         var userIdentity = {
            userId : user.uid,
            attributes : user
};
WL.Server.setActiveUser(_Constants.REALM, userIdentity); return {
      statusMsg: 'authentication Success',
      statusCode : result.statusCode,
      userIdentity : userIdentity
};
} else {
WL.Server.setActiveUser(_Constants.REALM, null); return {
statusMsg: 'authentication failed, check logs for detailed error', statusCode : result.statusCode,
userIdentity : {}
}; }
}