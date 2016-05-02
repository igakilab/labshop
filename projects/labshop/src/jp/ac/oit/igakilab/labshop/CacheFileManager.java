package jp.ac.oit.igakilab.labshop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CacheFileManager {
	static String RANDOM_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";
	static int FILENAME_LENGTH = 8;
	static String DEFAULT_FEXT = "txt";

	static String generateRandomString(int length){
		String str = "cache_";
		for(int i=0; i<length; i++){
			int idx = (int)(Math.random() * RANDOM_CHARS.length());
			str += RANDOM_CHARS.charAt(idx);
		}
		return str;
	}



	private String cacheDir;
	private String fileName;
	private String fext;

	public CacheFileManager(String d0, String e0){
		setCacheDirectory(d0);
		setFileExtension(e0);
		init();
	}

	public CacheFileManager(){
		this("", DEFAULT_FEXT);
	}

	public void init(){
		fileName = generateRandomString(FILENAME_LENGTH);
	}

	public void setCacheDirectory(String d0){
		if( d0 != null ){
			cacheDir = d0;
		}else{
			cacheDir = "";
		}
	}

	public void setFileExtension(String e0){
		fext = e0;
	}

	public String getPath(){
		return cacheDir + getFileName();
	}

	public String getFileName(){
		if( fext != null ){
			return fileName + "." + fext;
		}else{
			return fileName;
		}
	}

	public File getFile(){
		return new File(getPath());
	}

	public void saveString(String str)
	throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(getFile()));
		writer.write(str);
		writer.close();
	}
}
