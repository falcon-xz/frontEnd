package com.xz.designMode.createType.factoryMethod;

import com.xz.designMode.createType.abstractFactory.Animal;

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
