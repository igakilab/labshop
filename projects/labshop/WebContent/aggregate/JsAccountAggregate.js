var JsAccountAggregate = {};

JsAccountAggregate.getMonthlyMemberPriceList(year, month, f_callback){
	WebAccountAggregate.getMonthlyMemberPriceList(year, month, {
		callback: function(ret){
			var sum = 0;
			for(var i=0; i<ret.length; i++){
				sum += ret[i].price;
			}
			f_callback({isErr: false, list: ret, sumPrice: sum});
		},
		errorHandler: function(msg){
			f_callback({isErr: true, errMsg: msg});
		}
	});
}


JsAccountAggregate.getMonthlyMemberAccount(year, month, id, f_callback){
	WebAccountAggregate.getMonthlyMemberAccount(year, month, id, {
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
