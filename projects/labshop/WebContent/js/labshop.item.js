labshop.getItemData = function(id, fcallback){
	WebItemManager.getItem(id, {
		callback: function(retval){
			fcallback({isErr:false, item:retval});
		},
		errorHandler: function(msg){
			labshop.errorObjectCallback(fcallback, msg);
		}
	});
};

labshop.getOnSaleItemList = function(fcallback){
	WebItemManager.getOnSaleItemList({
		callback: function(retval){
			fcallback({isErr:false, list:retval});
		},
		errorHandler: function(msg){
			labshop.errorObjectCallback(fcallback, msg);
		}
	});
};

labshop.getAllItemList = function(fcallback){
	WebItemManager.getItemList({
		callback: function(retval){
			fcallback({isErr:false, list:retval});
		},
		errorHandler: function(msg){
			labshop.errorObjectCallback(fcallback, msg);
		}
	});
};

labshop.getQrItemData = function(id, fcallback){
	WebItemqrGenerator.getQrItemData(id, location.host, {
		callback: function(re){
			fcallback({isErr:false, qritem:re});
		},
		errorHandler: function(msg){
			fcallback({isErr:true, errMsg:msg});
		}
	});
};

labshop.getQrItemDataList = function(all, fcallback){
	WebItemqrGenerator.getQrItemDataList(all, location.host, {
		callback: function(re){
			fcallback({isErr:false, list:re});
		},
		errorHandler: function(msg){
			fcallback({isErr:true, errMsg:msg});
		}
	});
};