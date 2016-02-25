package jp.ac.oit.igakilab.labshop.member;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthMemberData extends MemberData {
	public static String HASH_ALGORITHM = "MD5";

	public static String hashString(String s0){
		byte[] result = null;

		try{
			MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
			result = md.digest(s0.getBytes());
		}catch(NoSuchAlgorithmException e0){
			return null;
		}

		return convertHexString(result);
	}

	public static String convertHexString(byte[] bt){
		StringBuilder stb = new StringBuilder();

		for(int i=0; i<bt.length; i++){
			stb.append(String.format("%02x", bt[i]));
		}
		return stb.toString();
	}

	public static AuthMemberData getInstance(MemberData md){
		AuthMemberData amd = new AuthMemberData();
		amd.setId(md.getId());
		amd.setName(md.getName());
		amd.setPasswordHash(md.getPasswordHash());
		return amd;
	}


	public AuthMemberData(){
		super();
	}

	public boolean setPassword(String new_passwd){
		String hashed = hashString(new_passwd);
		if( hashed != null ){
			setPasswordHash(hashed);
			return true;
		}
		return false;
	}

	public boolean authentication(String passwd){
		if( getPasswordHash() != null ){
			return getPasswordHash().equals(hashString(passwd));
		}else{
			return passwd.length() == 0;
		}
	}
}
