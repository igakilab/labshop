
/* ラボショップの初期化*/
var labshop = {};

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
}

labshop.errorObjectCallback(cbf, msg){
	cbf({isErr:true, errMsg:msg});
	return;
}


labshop.toMonthValue = function(year, month){
	return (year * 12) + (month - 1);
};

labshop.monthValueToMonthAndDate = function(val){
	return {
		year: Math.floor(val / 12),
		month: ((val % 12) + 1)
	};
};


