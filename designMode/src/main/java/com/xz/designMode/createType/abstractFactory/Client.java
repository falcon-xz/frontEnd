package com.xz.designMode.createType.abstractFactory;

public class Client {
	public static void main(String[] args) {
		Factory factory1 = new SmallAnimalFactory() ;
		Animal a1 = factory1.buyone("dog") ;
		System.out.println(a1.eat());
		System.out.println(a1.hue());
		Factory factory2 = new BigAnimalFactory() ;
		Animal a2 = factory2.buyone("cow") ;
		System.out.println(a2.eat());
		System.out.println(a2.hue());
	}
}
