/* dependency
 * labshop.js
*/

labshop.getPropertyList = function(fcallback){
	var localId = labshop.getClientSessionId();
	if( localId == undefined )fcallback({isErr:true, errMsg:"セッションがありません"});

	WebAdminLabshopProperties.getPropertyList(localId, {
		callback: function(re){
			fcallback({isErr:false, list:re});
		},
		errorHandler: function(msg){
			fcallback({isErr:false, errMsg:msg});
		}
	});
};


labshop.getProperty = function(k0, fcallback){
	var localId = labshop.getClientSessionId();
	if( localId == undefined )fcallback({isErr:true, errMsg:"セッションがありません"});

	WebAdminLabshopProperties.getProperty(localId, k0, {
		callback: function(re){
			fcallback({isErr:false, key:k0, value:re});
		},
		errorHandler: function(msg){
			fcallback({isErr:true, errMsg:msg});
		}
	});
}


labshop.setProperty = function(k0, v0, fcallback){
	var localId = labshop.getClientSessionId();
	if( localId == undefined )fcallback({isErr:true, errMsg:"セッションがありません"});

	WebAdminLabshopProperties.setProperty(localId, k0, v0);
}