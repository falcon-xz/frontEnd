package com.xz.createType.factoryMethod;

import com.xz.createType.abstractFactory.Animal;

public class Cow implements Animal {

	@Override
	public String eat() {
		return "�Բ�";
	}

	@Override
	public String hue() {
		return "����";
	}

}
