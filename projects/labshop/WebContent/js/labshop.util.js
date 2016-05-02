labshop.createLabelsRow = function(labels){
	var row = $("<tr></tr>");
	for(var i=0; i<labels.length; i++){
		row.append($("<th></th>").text(labels[i]));
	}
	return row;
}

labshop.addMonthOptions = function(jquery_object){
	for(var i=1; i<=12; i++){
		jquery_object.append(
			$("<option></option>")
				.attr("value", i)
				.text(i)
		);
	}
}

labshop.addSelectOptions = function(jquery_object, options){
	if( !$.isArray(options) ) return;

	for(var i=0; i<options.length; i++){
		jquery_object.append(
			$("<option></option>")
				.attr("value", options[i])
				.text(options[i])
		);
	}
}

labshop.formatDate = function (date, format) {
	/*
	 * http://qiita.com/osakanafish
	 * http://qiita.com/osakanafish/items/c64fe8a34e7221e811d0
	 */
	if (!format) format = 'YYYY-MM-DD hh:mm:ss.SSS';
	format = format.replace(/YYYY/g, date.getFullYear());
	format = format.replace(/MM/g, ('0' + (date.getMonth() + 1)).slice(-2));
	format = format.replace(/DD/g, ('0' + date.getDate()).slice(-2));
	format = format.replace(/hh/g, ('0' + date.getHours()).slice(-2));
	format = format.replace(/mm/g, ('0' + date.getMinutes()).slice(-2));
	format = format.replace(/ss/g, ('0' + date.getSeconds()).slice(-2));
	if (format.match(/S/g)) {
		var milliSeconds = ('00' + date.getMilliseconds()).slice(-3);
		var length = format.match(/S/g).length;
		for (var i = 0; i < length; i++) format = format.replace(/S/, milliSeconds.substring(i, i + 1));
	}
	return format;
};