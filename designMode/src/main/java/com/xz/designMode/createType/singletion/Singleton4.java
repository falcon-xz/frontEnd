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

	public static void main(String[] args) {
		Singleton4 singleton1 = Singleton4.getInstance() ;
		Singleton4 singleton2 = Singleton4.getInstance() ;
		Singleton4 singleton3 = Singleton4.getInstance() ;
		System.out.println(singleton1==singleton2);
		System.out.println(singleton3==singleton2);
	}
}
