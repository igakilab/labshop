labshop.getMemberData = function(id, fcallback){
	WebMemberManager.getMemberData(id, {
		callback: function(retval){
			fcallback({isErr:false, member:retval});
		},
		errorHandler: function(msg){
			labshop.errorObjectCallback(fcallback, msg);
		}
	});
};

labshop.getPrimaryMemberList = function(fcallback){
	WebMemberManager.getPrimaryMemberList({
		callback: function(retval){
			fcallback({isErr:false, list:retval});
		},
		errorHandler: function(msg){
			labshop.errorObjectCallback(fcallback, msg);
		}
	});
};

labshop.getAllMemberList = function(fcallback){
	WebMemberManager.getMemberList({
		callback: function(retval){
			fcallback({isErr:false, list:retval});
		},
		errorHandler: function(msg){
			labshop.errorObjectCallback(fcallback, msg);
		}
	});
};