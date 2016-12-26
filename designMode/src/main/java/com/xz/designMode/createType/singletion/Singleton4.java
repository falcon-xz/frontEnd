package com.xz.designMode.createType.singletion;

public class Singleton4 {

	private Singleton4(){
		
	}
	public static Singleton4 getInstance(){
		return Singleton4Holder.singleton4 ;
	}

	private static class Singleton4Holder{
		private static Singleton4 singleton4 = new Singleton4() ;
	}
}
