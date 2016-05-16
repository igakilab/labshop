labshop.getMonthlyMemberSalesChart = function(monthVal, fcallback){
	var localId = labshop.getClientSessionId();
	if( localId == undefined ){
		labshop.errorObjectCallback(fcallback, "セッションが開いていません");return;
	}

	WebAdminAccountAggregate.getMonthlyMemberSalesChart(localId, monthVal, {
		callback: function(ret){
			ret.isErr = false;
			fcallback(ret);
		},
		errorHandler: function(msg){
			errorObjectCallback(fcallback, msg);
		}
	});
};


labshop.getMonthlyMemberPriceList = function(monthVal, isAllMember, fcallback){
	WebAdminAccountAggregate.getMonthlyMemberPriceList(monthVal, isAllMember, {
		callback: function(ret){
			var sum = 0;
			for(var i=0; i<ret.length; i++){
				sum += ret[i].price;
			}
			fcallback({isErr: false, list: ret, sumPrice: sum});
		},
		errorHandler: function(msg){
			fcallback({isErr: true, errMsg: msg});
		}
	});
};

labshop.getMonthlyItemSalesList = function(monthVal, f_callback){
	var localId = labshop.getClientSessionId();
	if( labshop.assertUndefined(localId, "セッションが開いていません") )return;

	WebAdminAccountAggregate.getMonthlyItemSalesList(localId, monthVal, {
		callback: function(ret){
			var sum = 0;
			for(var i=0; i<ret.length; i++) sum += ret[i].price;
			f_callback({isErr: false, list: ret, sumPrice: sum});
		},
		errorHandler: function(msg){
			f_callback({isErr: true, errMsg: msg});
		}
	});
};


labshop.getMonthlyMemberItemAccounts = function(monthVal, mid, iid, f_callback){
	var localId = labshop.getClientSessionId();
	if( labshop.assertUndefined(localId, "セッションが開いていません") )return;

	WebAdminAccountAggregate.getMonthlyMemberItemAccounts(localId, monthVal, mid, iid, {
		callback: function(ret){
			var sum = 0;
			for(var i=0; i<ret.length; i++){
				sum += ret[i].sellPrice;
			}
			f_callback({isErr: false, list: ret, sumPrice: sum});
		},
		errorHandler: function(msg){
			f_callback({isErr: true, errMsg: msg});
		}
	});
}

labshop.getMonthlyMemberAccounts = function(monthVal, id, f_callback){
	var localId = labshop.getClientSessionId();
	if( labshop.assertUndefined(localId, "セッションが開いていません") )return;

	WebAdminAccountAggregate.getMonthlyMemberAccounts(localId, monthVal, id, {
		callback: function(ret){
			var sum = 0;
			for(var i=0; i<ret.length; i++){
				sum += ret[i].sellPrice;
			}
			f_callback({isErr: false, list: ret, sumPrice: sum});
		},
		errorHandler: function(msg){
			f_callback({isErr: true, errMsg: msg});
		}
	});
};

labshop.getMonthlyItemAccounts = function(monthVal, id, f_callback){
	var localId = labshop.getClientSessionId();
	if( labshop.assertUndefined(localId, "セッションが開いていません") )return;

	WebAdminAccountAggregate.getMonthlyItemAccounts(localId, monthVal, id, {
		callback: function(ret){
			var sum = 0;
			for(var i=0; i<ret.length; i++) sum += ret[i].sellPrice;
			var qty = ret.length;
			f_callback({isErr: false, list: ret, sumPrice: sum, quantity: qty});
		},
		errorHandler: function(msg){
			f_callback({isErr: true, errMsg: msg});
		}
	});
};
