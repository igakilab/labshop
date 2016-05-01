function setChartTable(jquery_table_elem, chart){
	var table = jquery_table_elem;

	table.empty();

	//setHeadItemLabels
	var hrow1 = $("<tr></tr>");
	var hrow2 = $("<tr></tr>");
	hrow1.append([$("<th></th>").attr("rowspan", "2").text("メンバー"),
	            $("<th></th>").attr("rowspan", "2").text("金額")]);
	for(var i=0; i<chart.itemList.length; i++){
		hrow1.append($("<th></th>").text(chart.itemList[i]));
		hrow2.append($("<th></th>").text(chart.itemNameList[i]));
	}
	table.append(hrow1);
	table.append(hrow2);

	//setMemberRow
	for(var i=0; i<chart.memberList.length; i++){
		var mrow = $("<tr></tr>");
		var memberName = chart.memberList[i] + "(" + chart.memberNameList[i] + ")";
		var memberObj = chart.members[i];

		mrow.append($("<th></th>").text(memberName));
		mrow.append($("<th></th>").text(memberObj.sumPrice));

		for(var j=0; j<memberObj.itemCounts.length; j++){
			if( memberObj.itemCounts[j] > 0 ){
				mrow.append(
					$("<td></td>").append(
						$("<b></b>").text(memberObj.itemCounts[j])
					).append(
						" (" + memberObj.itemPrices[j] + ")"
					)
				);
			}else{
				mrow.append($("<td></td>").text("0"));
			}
		}

		table.append(mrow);
	}

	//setRearItemRow
	var rrow1 = $("<tr></tr>");
	rrow1.append($("<th></th>").text("合計金額"));
	rrow1.append($("<th></th>").text(chart.sumPrice));
	for(var i=0; i<chart.items.length; i++){
		var itemObj = chart.items[i];
		if( itemObj.count > 0 ){
			rrow1.append(
				$("<td></td>").append(
					$("<b></b>").text(itemObj.count)
				).append(
					" (" + itemObj.sumPrice + ")"
				)
			);
		}else{
			rrow1.append($("<td></td>").text("0"));
		}
	}
	table.append(rrow1);
}


function setAccountListTable(jqueryTable, accountAry, showMID, showIID){
	var table = jqueryTable;
	var list = accountAry;
	var colspanVal = 2;

	table.empty();

	//appendLabelRow
	var labelRow = $("<tr></tr>");
	labelRow.append([$("<th></th>").text("ID"), $("<th></th>").text("日付")]);
	if( showMID ){
		labelRow.append([$("<th></th>").text("メンバーID"), $("<th></th>").text("メンバー名")]);
		colspanVal += 2;
	}
	if( showIID ){
		labelRow.append([$("<th></th>").text("アイテムID"), $("<th></th>").text("アイテム名")]);
		colspanVal += 2;
	}
	labelRow.append($("<th></th>").text("販売価格"));
	table.append(labelRow);

	//appendAccountData
	var sum = 0;
	for(var i=0; i<list.length; i++){
		var arow = $("<tr></tr>");
		var timeStr = labshop.formatDate(list[i].timestamp, "MM月DD日 hh:mm");

		arow.append([$("<td></td>").text(list[i].id), $("<td></td>").text(timeStr)]);
		if( showMID ){
			arow.append($("<td></td>").text(list[i].memberId));
			arow.append($("<td></td>").text(list[i].memberName));
		}
		if( showIID ){
			arow.append($("<td></td>").text(list[i].itemId));
			arow.append($("<td></td>").text(list[i].itemName));
		}
		arow.append($("<td></td>").text(list[i].sellPrice));

		sum += list[i].sellPrice;

		table.append(arow);
	}

	//appendSumRow
	table.append(
		$("<tr></tr>").append(
			$("<td></td>").attr("colspan", colspanVal).text("合計金額")
		).append(
			$("<td></td>").text(sum)
	));
}
















