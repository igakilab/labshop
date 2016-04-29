/* labshop accuntlistfinder
 * REQUIRED: WebAccountListFinder
 * REQUIRED: labshop.js
 * REQUIRED: labshop.session.js
*/

/* query
 * {
 *   memberIds: (int[])
 *   itemIdx: (int[])
 *   startMonthValue: int
 *   endMonthValue: int
 */

labshop.toMonthValue(year, month){
	return (year * 12) + (month - 1);
};

labshop.monthValueToMonthAndDate(val){
	return {
		year: (val / 12;
		month: ((val % 12) + 1)
	};
}

labshop.getAccountList(query, fcallback){
	var localId = labshop.getClientSessionId();
	if( localId == undefined ){
		fcallback({isErr:true, errMsg:"セッションが開いていません"});
		return;
	}

	WebAccountListFinder.getAccountList(localId, query, {
		callback: function(ret){
			fcallback({isErr:false, list:ret});
		},
		errorHandler: function(msg){
			fcallback({isErr:true, errMsg:msg});
		}
	});
};

labshop.adminGetAccountList(query, fcallback){
	var localId = labshop.getClientSessionId();
	if( localId == undefined ){
		fcallback({isErr:true, errMsg:"セッションが開いていません"});
		return;
	}

	WebAccountListFinder.adminGetAccountList(localId, query, {
		callback: function(ret){
			fcallback({isErr:false, list:ret});
		},
		errorHandler: function(msg){
			fcallback({isErr:true, errMsg:msg});
		}
	});
};

labshop.getClientMonthlyAccountList(year, month, fcallback){
	labshop.getClientSessionState(function(ret){
		if( ret.isErr ){alert(ret.errMsg);return;}

		var query = {};
		query.memberId = [ret.session.memberId];
		query.startMonthValue = labshop.toMonthValue(year, month);
		query.endMonthValue = labshop.toMonthValue(year, month);

		labshop.getAccountList(query, fcallback);
	}
};