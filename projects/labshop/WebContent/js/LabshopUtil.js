var LabshopUtil = {};

LabshopUtil.createLabelsRow = function(labels){
	var row = $("<tr></tr>");
	for(var i=0; i<labels.length; i++){
		row.append($("<th></th>").text(labels[i]));
	}
	return row;
}

LabshopUtil.addMonthOptions = function(jquery_object){
	for(var i=1; i<=12; i++){
		jquery_object.append(
			$("<option></option>")
				.attr("value", i)
				.text(i)
		);
	}
}

LabshopUtil.shimpleErrorHandler = function(message){
	alert(message);
}