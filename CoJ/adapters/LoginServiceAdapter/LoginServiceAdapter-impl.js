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
 *  WL.Server.invokeHttp(parameters) accepts the following json object as an argument:
 *  
 *  {
 *  	// Mandatory 
 *  	method : 'get' , 'post', 'delete' , 'put' or 'head' 
 *  	path: value,
 *  	
 *  	// Optional 
 *  	returnedContentType: any known mime-type or one of "json", "css", "csv", "javascript", "plain", "xml", "html"  
 *  	returnedContentEncoding : 'encoding', 
 *  	parameters: {name1: value1, ... }, 
 *  	headers: {name1: value1, ... }, 
 *  	cookies: {name1: value1, ... }, 
 *  	body: { 
 *  		contentType: 'text/xml; charset=utf-8' or similar value, 
 *  		content: stringValue 
 *  	}, 
 *  	transformation: { 
 *  		type: 'default', or 'xslFile', 
 *  		xslFile: fileName 
 *  	} 
 *  } 
 */

var _Constants = { 
	JSESSIONID: "JSESSIONID",
	SESSIONID: "com.ibm.ioc.sessionid",
	REALM: "IOCAuthRealm",
	LTPATOKEN: "LtpaToken2"
};

var CN = "LoginServiceAdapter";

function log(level, method, msg) {
	WL.Logger[level](CN + "::" + method + " -> " + msg);
}


/**
 * @returns json list of matching users (will be at most one)
 */
function authenticateUser(base64) {
	var F = "login";
	log("info", F, "ENTER");
	log("info", F, "arg: base64 - " + base64);
	logout();
	var input = {
	    method: 'get',
	    returnedContentType: 'application/json',
	    path: "/ibm/ioc/api/login-service/users",
	    headers: {
	    	Authorization: "Basic " + base64,
	    	Accept: "application/json"
	    }
	};
	
	// get the JSESSIONID out of the request header
	var request = WL.Server.getClientRequest();
	var cookieHeader = request.getHeader("Cookie");
	var cookies = cookieHeader.split(";");
	var jSessionId = null;
	for (var i=0; i<cookies.length; i++) {
		var cookie = cookies[i];
		if (cookie.indexOf(_Constants.JSESSIONID) >= 0) {
			// var keyPattern = /.*?[=]/;
			// var key = "" + keyPattern.exec(cookie);
			var valuePattern = /[=].*/;
			var value = "" + valuePattern.exec(cookie);
			jSessionId = value.trim().substr(1);	// strip the leading "="			
			break;
		}
	}
	
	// send the required data to pass through the CSRF filter
	if (jSessionId) {
		input.headers[_Constants.SESSIONID] = jSessionId;
		input.headers["Cookie"] = _Constants.JSESSIONID + "=" + jSessionId;
	}
	var result = WL.Server.invokeHttp(input);
	return result;
	// Fatal error calling server
	if (!result.isSuccessful) {
		log("error", F, "Fatal error: result " + JSON.stringify(result));
		log("info", F, "EXIT");
		return result;
	}

	// if successful, set active user
	if (result.statusCode && result.statusCode == 200 && result.array) {
		var user = JSON.parse(JSON.stringify(result.array[0]));		// clone
		user.base64 = base64;
		user.sessionId = null;
		user.ltpaToken = null;
		
		if (result.responseHeaders) {
			var responseHeaders = result.responseHeaders;
			
			// process the headers
			user.sessionId = responseHeaders[_Constants.SESSIONID];
			
			// process the cookies
			var setCookieHeader = responseHeaders["Set-Cookie"];
			var cookieData = setCookieHeader.split(";");
			for (var i=0; i<cookieData.length; i++) {
				var cookie = cookieData[i];
				if (cookie.indexOf(_Constants.LTPATOKEN) > -1) {
					// var keyPattern = /.*?[=]/;
					// var key = "" + keyPattern.exec(cookie);
					var valuePattern = /[=].*/;
					var value = "" + valuePattern.exec(cookie);
					user.ltpaToken = value.trim().substr(1);	// strip the leading "="
					break;
				}
			}
		}
		
		var userIdentity = {
			userId: user.uid,
			attributes: user
		};
	
		result = {statusCode: result.statusCode, userIdentity: userIdentity};
		log("info", F, "result - " + JSON.stringify(result));
		
		//Make sure not to setActiveUser if already set to this same active user
		//	Otherwise, throws exception: 
		//FWLSE0099E: An error occurred while invoking procedure  [project ioc_citizencollaboration_mobile]LoginServiceAdapter/loginFWLSE0100E:  parameters: [project ioc_citizencollaboration_mobile]
		//Cannot change identity of an already logged in user in realm 'IOCAuthRealm'. The application must logout first.
		var currentActiveUser = WL.Server.getActiveUser(_Constants.REALM); 
		log("info", F, "Got current active user: " + JSON.stringify(currentActiveUser));
		if (currentActiveUser) {
			if (currentActiveUser.userId === user.uid) {
				log("info", F, "User already currently active.  No action to take.");
			}
			else {
				log("info", F, "Different user currently active.  Setting " + user.uid + " as active WL Server user for IOC realm");
				WL.Server.setActiveUser(_Constants.REALM, userIdentity);
			}
		}
		else {
			log("info", F, "No user currently active.  Setting " + user.uid + " as active WL Server user for IOC realm");
			WL.Server.setActiveUser(_Constants.REALM, userIdentity);
		}	
	}
	else {
		WL.Server.setActiveUser(_Constants.REALM, null);
		result = {statusCode: result.statusCode, userIdentity: {}};
		log("error", F, "result - " + JSON.stringify(result));
	}
	
	log("info", F, "EXIT");
	return result;
}

function logout() {
	var F = "logout";
	log("info", F, "ENTER");
	WL.Server.setActiveUser(_Constants.REALM, null);
	log("info", F, "EXIT");
}

/**
 * Register a new user to the system
 * new user need to have following properties:
 * {	"username":"skm",
		"email":"skm@skm.com",
		"password":"skm123",
		"firstName":"Joe",
		"lastName":"Smith",
		"city":"my city",
		"country":"US",
		"preferredLanguage":"en"
	}
	
 * The Self registration service
 * { username, email, password, firstName, lastName }
 * @returns
 */
function register(newUserInfo) {
	var F = "register";
	log("info", F, "ENTER");
	log("info", F, "newUserInfo: " + JSON.stringify(newUserInfo));
	
	var input = {
		    method: 'post',
		    returnedContentType : 'json',  
		    path: "/ibm/ioc/registration-service/registrants",
		    headers: {
		    	"Accept": "application/json",
		    	"Content-Type": "application/json",
		    	"Cookie" : "JSESSIONID=1"
		    },
		    body: {
		    	contentType:'application/json; charset=UTF-8',
                content: JSON.stringify(newUserInfo)
		    }
		};
	
	var result = WL.Server.invokeHttp(input);
	log("info", F, "result ---> " + JSON.stringify(result));
	
	// Fatal error calling server
	if (!result.isSuccessful) {
		log("info", F, "Bad result");
		log("info", F, "EXIT");
		return result;
	}

	// if successful, set active user
	if (result.statusCode && result.statusCode == 200) {
		log("info", F, "OK, new user created");
		log("info", F, "EXIT");
		return result;
		
	// Unsuccessful result
	} else if (result.statusCode && result.statusCode == 500) {
		log("info", F, "Bad result, statusCode = 500");
		//result = {statusCode: result.statusCode};
	} else {
		log("info", F, "Bad result, unknown error");
		//result = {statusCode: result.statusCode};
	}

	log("info", F, "EXIT");
	return result;
}
