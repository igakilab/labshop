var MEMBER_DATA = [
	{"id":213001, "name":"‚Å‚±‚Û‚ñ‚·", isAdmin:false}
];

for(var data in MEMBER_DATA){
	var cnt = db.member.count({id:{$eq:data.id}});
	if( cnt == 0 ){
		db.member.save(data);
	}
}
