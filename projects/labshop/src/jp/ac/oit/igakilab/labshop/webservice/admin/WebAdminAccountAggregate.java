package jp.ac.oit.igakilab.labshop.webservice.admin;

import java.util.Calendar;
import java.util.List;

import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.extension.AggregateAccountDB;
import jp.ac.oit.igakilab.labshop.dbcontroller.extension.DateFilters;
import jp.ac.oit.igakilab.labshop.member.MemberData;
import jp.ac.oit.igakilab.labshop.sessions.SessionManager;
import jp.ac.oit.igakilab.labshop.shopping.MemberSalesChart;
import jp.ac.oit.igakilab.labshop.webservice.ExcuteFailedException;
import jp.ac.oit.igakilab.labshop.webservice.forms.AccountDataMonthlyQueryForm;
import jp.ac.oit.igakilab.labshop.webservice.forms.MemberSalesChartForm;

public class WebAdminAccountAggregate {
	public static String ERRMSG_AUTH_FAILED = "認証に失敗しました";

	public MemberSalesChartForm getMonthlyMemberSalesChart(String sid, int monthVal)
	throws ExcuteFailedException{
		SessionManager sm = new SessionManager();
		if( !sm.isSessionAdmin(sid) ){
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}

		AggregateAccountDB aadb = new AggregateAccountDB(sm.getDBConnector());
		MemberDBController mdb = new MemberDBController(sm.getDBConnector());
		MemberSalesChart chart = new MemberSalesChart();

		List<MemberData> memberList = mdb.getAllMemberList();
		mdb.close();
		for(MemberData md : memberList){
			chart.addMember(md.getId());
		}

		Calendar cal = AccountDataMonthlyQueryForm.toCalendar(monthVal);
		chart.buildChart(aadb, DateFilters.oneMonth("timestamp", cal));
		aadb.close();

		MemberSalesChartForm form = MemberSalesChartForm.getInstance(chart, sm.getDBConnector());
		sm.close();
		return form;
	}
}
