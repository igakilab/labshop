/* labshop account-register.js */

labshop.attemptAccountCsvText = function(text, fcallback){
	WebAdminCsvAccountRegister.attemptAccountCsvText(text, {
		callback: function(ret){
			fcallback({isErr:false, list:ret});
		},
		errorHandler: function(msg){
			fcallback({isErr:true, errMsg:msg});
		}
	});
};


labshop.registAccountCsvText = function(text, fcallback){
	var localId = labshop.getClientSessionId();

	WebAdminCsvAccountRegister.registAccountCsvText(localId, text, {
		callback: function(ret){
			fcallback({isErr:false, isSuccess:ret});
		},
		errorHandler: function(msg){
			fcallback({isErr:true, errMsg:msg});
		}
	});
};