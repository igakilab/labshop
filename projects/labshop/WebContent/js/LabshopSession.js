var LabshopSession = {};

LabshopSession.defaultSessionExpires = 7;

LabshopSession.sessionCookieSet = function(session){
	$.cookie("sessionId", session.id, {
		expires : this.defaultSessionExpires,
		path : "/",
	});
}

LabshopSession.sessionCookieGet = function(){
	return $.cookie("sessionId");
}

LabshopSession.sessionCookieClear = function(){
	$.cookie("sessionId", "");
}

LabshopSession.openSession = function(mid, passwd, f_callback){
	WebSessionManager.openSession(mid, passwd, {
		callback : function(ret){
			this.sessionCookieSet(ret);
			f_callback({isErr:false, session:ret});
		},
		errorHandler : function(msg){
			f_callback({isErr:true, errMsg:msg});
		}
	});
}

LabshopSession.closeSession = function(f_callback){
	var sid = this.sessionCookieGet();
	WebSessionManager.closeSession(sid, {
		callback : function(ret){
			LabshopSession.sessionCookieClear();
			f_callback({isErr:false, result : ret});
		}
	});
}