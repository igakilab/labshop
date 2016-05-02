labshop.setClientPassword = function(oldPasswd, newPasswd, fcallback){
	var localId = labshop.getClientSessionId();

	if( localId != undefined ){
		WebMemberConfigure.setPassword(localId, oldPasswd, newPasswd, {
			callback: function(ret){
				fcallback({isErr:false, isSuccess:ret});
			},
			errorHandler: function(msg){
				fcallback({isErr:true, errMsg:msg});
			}
		});
	}else{
		fcallback({isErr:false, errMsg:"セッションが開いていません"});
	}
}