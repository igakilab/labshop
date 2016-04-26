/* labshop.session.js */

labshop.SESSION_COOKIE_KEY = "session_id";
labshop.SESSION_PATH = "/";
labshop.SESSION_DEFAULT_EXPIRES = 7;

labshop.setSessionCookie = function(id, isSetExpire){
	var cookieOption = {}

	cookieOption.path = labshop.SESSION_PATH;
	if( isSetExpire ){
		cookieOption.expires = labshop.SESSION_DEFAULT_EXPIRES;
	}

	$.cookie(labshop.SESSION_COOKIE_KEY, id, cookieOption);
}

labshop.getSessionCookie = function(){
	return $.cookie(labshop.SESSION_COOKIE_KEY);
}

labshop.clearSessionCookie = function(){
	return $.removeCookie(labshop.SESSION_COOKIE_KEY, {
		path: labshop.SESSION_PATH
	});
}


labshop.getClientSessionId = function(){
	var sid = labshop.getSessionCookie();
	if( sid == undefined || sid == "" ){
		return undefined;
	}else{
		return sid;
	}
}


labshop.openClientSession = function(mid, passwd, isHold, fcallback){
	WebSessionManager.openSession(mid, passwd, {
		callback: function(result){
			labshop.setSessionCookie(result.id, isHold);
			fcallback({isErr:false, session:result});
		},
		errorHandler: function(msg){
			fcallback({isErr:true, errMsg:msg});
		}
	});
}


labshop.closeClientSession = function(fcallback){
	var localId = labshop.getClientSessionId();

	if( localId != undefined ){
		labshop.clearSessionCookie();
	}else{
		return {isErr: true, errMsg: "クライアントにセッションがありません"};
	}

	WebSessionManager.closeSession(localId.value, {
		callback: function(ret){
			fcallback({isErr: false, result: ret});
		}
	});
}


labshop.getClientSessionState = function(fcallback){
	var localId = labshop.getClientSessionId();

	if( localId == undefined ){
		return {isErr: false, isOpened: false};
	}

	WebSessionManager.getSessionData(localId.value, {
		callback: function(ret){
			if( ret == null ){
				fcallback({isErr: false, isOpened: false});
			}else{
				fcallback({isErr: false, isOpened: true, session: ret});
			}
		},
		errorHandler: function(msg){
			fcallback({isErr: false, isOpened: false, errMsg: msg});
		}
	});
}