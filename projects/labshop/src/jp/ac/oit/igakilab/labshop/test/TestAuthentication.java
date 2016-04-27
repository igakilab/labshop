package jp.ac.oit.igakilab.labshop.test;

import jp.ac.oit.igakilab.labshop.member.AuthMemberData;
import jp.ac.oit.igakilab.labshop.member.MemberData;

public class TestAuthentication {
	public static void main(String args[]){
		String[] inputs = {"super", "next", "prev", "this", ""};
		String answer = "prev";

		AuthMemberData amd = AuthMemberData.getInstance(new MemberData(100202, "John"));
		amd.setPassword(answer);
		System.out.println("PASSWORD_HASH : " + amd.getPasswordHash());

		for(String inp : inputs){
			System.out.println("-----");
			System.out.println("INPUT : " + AuthMemberData.hashString(inp) + " (" + inp + ")");
			System.out.println("CONFG : " + amd.getPasswordHash());
			if( amd.authentication(inp) ){
				System.out.println("++ OK ++");
				System.out.println("MEMBER(id: " + amd.getId() + " name: " + amd.getName() + ")");
			}else{
				System.out.println("++ failed ++");
			}
		}
	}
}
