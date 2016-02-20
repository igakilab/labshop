var LabshopUtil = {};

LabshopUtil.createLabelsRow = function(labels){
	var row = $("<tr></tr>");
	for(var i=0; i<labels.length; i++){
		row.append($("<th></th>").text(labels[i]));
	}
	return row;
}