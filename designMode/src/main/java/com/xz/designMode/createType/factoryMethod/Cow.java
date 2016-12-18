package com.xz.designMode.createType.factoryMethod;

import com.xz.designMode.createType.abstractFactory.Animal;

public class Cow implements Animal {

	@Override
	public String eat() {
		return "Cow";
	}

	@Override
	public String hue() {
		return "hue";
	}

}
