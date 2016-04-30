labshop.getMonthlyMemberSalesChart(monthVal, fcallback){
	var localId = labshop.getClientSessionId();
	if( localId ){errorObjectCallback(fcallback, "セッションが開いていません");return;}

	WebAdminAccountAggregate.getMonthlyMemberSalesChart(localId, monthVal, {
		callback: function(ret){
			ret.isErr = false;
			fcallback(ret);
		},
		errorHandler: function(msg){
			errorObjectCallback(fcallback, msg);
		}
	});
}