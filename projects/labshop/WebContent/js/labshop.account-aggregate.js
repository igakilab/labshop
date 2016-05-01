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


labshop.getMonthlyMemberPriceList = function(monthVal, fcallback){
	var localId = labshop.getClientSessionId();
	if( labshop.assertUndefined(localId, "セッションが開いていません") )return;

	WebAdminAccountAggregate.getMonthlyMemberPriceList(localId, monthVal, {
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

labshop.getMonthlyItemSalesList = function(monthVal, id, f_callback){
	var localId = labshop.getClientSessionId();
	if( labshop.assertUndefined(localId, "セッションが開いていません") )return;

	WebAdminAccountAggregate.getMonthlyItemSalesList(localId, monthVal, id, {
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


labshop.getMonthlyMemberItemAccount = function(monthVal, mid, iid, fcallback){
	var localId = labshop.getClientSessionId();
	if( labshop.assertUndefined(localId, "セッションが開いていません") )return;

	WebAdminAccountAggregate.getMonthlyMemberAccount(localId, monthVal, mid, iid, {
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

labshop.getMonthlyMemberAccount = function(monthVal, id, f_callback){
	var localId = labshop.getClientSessionId();
	if( labshop.assertUndefined(localId, "セッションが開いていません") )return;

	WebAdminAccountAggregate.getMonthlyMemberAccount(localId, monthVal, id, {
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

labshop.getMonthlyItemAccount = function(year, month, id, f_callback){
	var localId = labshop.getClientSessionId();
	if( labshop.assertUndefined(localId, "セッションが開いていません") )return;

	WebAdminAccountAggregate.getMonthlyMemberAccount(localId, monthVal, id, {
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
