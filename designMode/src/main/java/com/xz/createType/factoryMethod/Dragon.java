package com.xz.createType.factoryMethod;

import com.xz.createType.abstractFactory.Animal;

public class Dragon implements Animal {

	@Override
	public String eat() {
		return "������";
	}

	@Override
	public String hue() {
		return "���";
	}

}
