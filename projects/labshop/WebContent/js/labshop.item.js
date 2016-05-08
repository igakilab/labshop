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