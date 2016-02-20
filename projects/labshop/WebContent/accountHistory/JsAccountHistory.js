var JsAccountHistory = {};

JsAccountHistory.getHistory(year, month, mid, f_callback){
	WebAccountHistory.getHistory(year, month, mid, {
		callback: function(ret){
			var result = {};
			result.histories = ret;
			result.memberId = mid;
			result.sumPrice = 0;
			for(var i=0; i<ret.length; i++){
				result.sumPrice += ret.sellPrice;
			}
			JsAccountHistory.getMemberName(mid f_callback, result);
		},
		errorHandler: function(msg){
			var result = {}
			result.isErr = true;
			result.errMessage = msg;
			f_callback(result);
		}
	});
}

JsAccountHistory.getMemberName(mid, f_callback, ac_result){
	var result;
	if( ac_result === undefined ){
		result = {};
	}else{
		result = ac_result;
	}

	WebAccountHistory.getMemberName(mid, {
		callback: function(ret){
			result.memberId = mid;
			result.memberName = ret;
			result.isErr = false;
			f_callback(result);
		},
		errorHandler: function(msg){
			result.isErr = true;
			result.errMessage = msg;
			f_callback(result);
		}
	});
}

JsAccountHistory.createHistoryRow(historyAccountDataForm){
	var row = $("<tr></tr>");
	var tmp = historyAccountDataForm;
	var date_str = tmp.timestamp.getMonth() + "/" + tmp.timestamp.getDate();
	row.append($("<td></td>").text(date_str))
		.append($("<td></td>").text(tmp.itemName))
		.append($("<td></td>").text(tmp.sellPrice));
	return row;
}