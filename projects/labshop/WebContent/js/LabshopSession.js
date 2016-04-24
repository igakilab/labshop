var LabshopSession = {};

LabshopSession.defaultSessionExpires = 7;

LabshopSession.sessionCookieSet = function(session){
	$.cookie("sessionId", session.id, {
		expires : LabshopSession.defaultSessionExpires,
		path : "/",
	});
}

LabshopSession.sessionCookieGet = function(){
	return $.cookie("sessionId");
}

LabshopSession.sessionCookieClear = function(){
	$.removeCookie("sessionId", {path: "/"});
}

LabshopSession.openClientSession = function(mid, passwd, f_callback){
	WebSessionManager.openSession(mid, passwd, {
		callback : function(ret){
			LabshopSession.sessionCookieSet(ret);
			f_callback({isErr:false, session:ret});
		},
		errorHandler : function(msg){
			f_callback({isErr:true, errMsg:msg});
		}
	});
}

LabshopSession.closeClientSession = function(f_callback){
	var sid = this.sessionCookieGet();
	WebSessionManager.closeSession(sid, {
		callback : function(ret){
			LabshopSession.sessionCookieClear();
			f_callback({isErr:false, result : ret});
		}
	});
}

LabshopSession.getClientSessionStatus = function(f_callback){
	var localId = LabshopSession.getClientSessionId();
	if( localId.exist ){
		WebSessionManager.getSessionData(localId.sessionId, {
			callback: function(ret){
				if( ret == null ){
					f_callback({isErr:false, isOpened: false, session: null});
				}else{
					f_callback({isErr:false, isOpened: true, session: ret});
				}
			}
		});
	}else{
		f_callback({isErr:false, isOpened: false, session: null});
	}
}

LabshopSession.isClientSessionOpened = function(f_callback){
	var localId = LabshopSession.getClientSessionId();
	if( localId.exist ){
		WebSessionManager.isSessionOpened(
			localId.sessionId, {
				callback: function(ret){
					f_callback({isErr:false, result: ret});
				}
			});
	}else{
		f_callback({isErr:false, result:ret});
	}
}

LabshopSession.getClientSessionId = function(){
	var cookieData = $.cookie("sessionId");

	if( cookieData == undefined || cookieData == "" ){
		return {exist: false};
	}else{
		return {exist: true, sessionId: cookieData};
	}
}