package com.xz.designMode.createType.singletion;

public class Singleton3 {
	private static Singleton3 singleton2 = new Singleton3() ;
	
	private Singleton3(){
		
	}
	
	public static Singleton3 getInstance(){
		return singleton2 ;
	}

}
