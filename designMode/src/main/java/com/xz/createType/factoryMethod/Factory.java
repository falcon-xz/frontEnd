package com.xz.createType.factoryMethod;

import com.xz.createType.abstractFactory.Animal;

public class Factory {
	public static Animal find(String name){
		if (name.equalsIgnoreCase("dragon")) {
			return new Dragon() ;
		}else {
			return new Cow();
		}
	}
}
