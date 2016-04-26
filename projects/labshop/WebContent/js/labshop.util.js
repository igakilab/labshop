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