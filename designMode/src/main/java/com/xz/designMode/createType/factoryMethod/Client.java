package com.xz.designMode.createType.factoryMethod;

import com.xz.designMode.createType.abstractFactory.Animal;

public class Client {
	public static void main(String[] args) {
		Animal animal = Factory.find("cow");
		System.out.println(animal.eat());
		System.out.println(animal.hue());
	}
}
