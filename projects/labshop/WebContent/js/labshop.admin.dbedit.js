/* labshop admin dbedit js */

labshop.editMemberDB = function(sid, cmd, mform, fcallback){
	WebAdminDBEditor.memberDBEdit(sid, cmd, mform, {
		callback: function(ret){
			fcallback({isErr: false, isSuccess: ret});
		},
		errorHandler: function(msg){
			fcallback({isErr: true, errMsg: msg});
		}
	});
}

labshop.getMemberData = function(sid, memberId, fcallback){
	WebAdminDBEditor.getMember(sid, memberId, {
		callback: function(rdata){
			fcallback({isErr: false, data: rdata});
		},
		errorHandler: function(msg){
			fcallback({isErr: true, errMsg: msg});
		}
	});
}

labshop.getMemberList = function(sid, fcallback){
	WebAdminDBEditor.getMemberList(sid, {
		callback: function(rlist){
			fcallback({isErr: false, list: rlist});
		},
		errorHandler: function(msg){
			fcallback({isErr: true, errMsg: msg});
		}
	});
}

labshop.editItemDB = function(sid, cmd, iform, fcallback){
	WebAdminDBEditor.itemDBEdit(sid, cmd, iform, {
		callback: function(ret){
			fcallback({isErr: false, isSuccess: ret});
		},
		errorHandler: function(msg){
			fcallback({isErr: true, errMsg: msg});
		}
	});
}

labshop.getItemData = function(sid, itemId, fcallback){
	WebAdminDBEditor.getItem(sid, itemId, {
		callback: function(rdata){
			fcallback({isErr: false, data: rdata});
		},
		errorHandler: function(msg){
			fcallback({isErr: true, errMsg: msg});
		}
	});
}

labshop.getItemList = function(sid, fcallback){
	WebAdminDBEditor.getItemList(sid, {
		callback: function(rlist){
			fcallback({isErr: false, list: rlist});
		},
		errorHandler: function(msg){
			fcallback({isErr: true, errMsg: msg});
		}
	});
}

labshop.editAccountDB = function(sid, cmd, aform, fcallback){
	WebAdminDBEditor.accountDBEdit(sid, cmd, mform, {
		callback: function(ret){
			fcallback({isErr: false, isSuccess: ret});
		},
		errorHandler: function(msg){
			fcallback({isErr: true, errMsg: msg});
		}
	});
}

labshop.registAccount = function(sid, aform, fcallback){
	WebAdminDBEditor.registNewAccount(sid, afom, {
		callback: function(ret){
			fcallback({isErr: false, registedData: ret});
		},
		errorHandler: function(msg){
			fcallback({isErr: true, errMsg: msg});
		}
	});
}

labshop.getAccountData = function(sid, accountId, fcallback){
	WebAdminDBEditor.getAccount(sid, accountId, {
		callback: function(rdata){
			fcallback({isErr: false, data: rdata});
		},
		errorHandler: function(msg){
			fcallback({isErr: true, errMsg: msg});
		}
	});
}

labshop.getAccountList = function(sid, fcallback){
	WebAdminDBEditor.getAccountList(sid, {
		callback: function(rlist){
			fcallback({isErr: false, list: rlist});
		},
		errorHandler: function(msg){
			fcallback({isErr: true, errMsg: msg});
		}
	});
}