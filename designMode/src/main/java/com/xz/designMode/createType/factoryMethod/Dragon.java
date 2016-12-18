package com.xz.designMode.createType.factoryMethod;

import com.xz.designMode.createType.abstractFactory.Animal;

public class Dragon implements Animal {

	@Override
	public String eat() {
		return "eat";
	}

	@Override
	public String hue() {
		return "hue";
	}

}
