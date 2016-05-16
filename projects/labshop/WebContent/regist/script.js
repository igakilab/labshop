function generateAuthForm(formId){
	var form = $("<form></form>");
	form.attr("id", formId);

	form.append(
		$("<table></table").append(
			$("<tr></tr>").append(
				$("<td></td>").text("メンバーID")
			).append(
				$("<td></td>").append(
					$("<input></input>").attr("type", "text")
					.attr("name", "memberId")
					.css("font-size", "x-large")
			))
		).append(
			$("<tr></tr>").append(
				$("<td></td>").text("パスワード")
			).append(
				$("<td></td>").append(
					$("<input></input>").attr("type", "password")
					.attr("name", "password")
					.css("font-size", "x-large")
		))).attr("class", "text-large")
	);

	return form;
}

function generateLoginedTable(memberId, memberName){
	var table = $("<table></table>");
	table.attr("class", "text-large");

	if( memberId != undefined ){
		table.append(
			$("<tr></tr>").append(
				$("<td></td>").text("ID")
			).append(
				$("<td></td>").text(memberId)
		));
	}

	if( memberName != undefined ){
		table.append(
			$("<tr></tr>").append(
				$("<td></td>").text("名前")
			).append(
				$("<td></td>").text(memberName)
		));
	}

	return table;
}