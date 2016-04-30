function setChartTable(jquery_table_elem, chart){
	var table = jquery_table_elem;

	table.empty();

	//setHeadItemLabels
	var row = $("<tr></tr>");
	row.append(
		$("<th></th>").text("メンバー名").attr("rowspan", "2")
	).append(
		$("<th></th>").text("金額").attr("rowspan", "2")
	);

}