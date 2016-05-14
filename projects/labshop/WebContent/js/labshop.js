
/* ラボショップの初期化*/
var labshop = {};

labshop.MASTER_SESSION_COOKIE_KEY = "session_id";

labshop.alertErrorHandle = function(msg){
	alert(msg);
};

labshop.assertError = function(result){
	if( result.isErr ){
		alert(result.errMsg);
		return true;
	}else{
		return false;
	}
};

labshop.assertUndefined = function(val, errmsg){
	if( val == undefined ){
		alert(errmsg);
		return true;
	}else{
		return false;
	}
}

labshop.errorObjectCallback = function(cbf, msg){
	cbf({isErr:true, errMsg:msg});
	return;
};


labshop.toMonthValue = function(year, month){
	return (year * 12) + (month - 1);
};

//deprecated
labshop.monthValueToMonthAndDate = function(val){
	return labshop.revertMonthValue(val);
};

labshop.revertMonthValue = function(val){
	return {
		year: Math.floor(val / 12),
		month: ((val % 12) + 1)
	};
}


labshop.getClientSessionId = function(){
	var sid = $.cookie(labshop.MASTER_SESSION_COOKIE_KEY);
	if( sid == undefined || sid == "" ){
		return undefined;
	}else{
		return sid;
	}
};

