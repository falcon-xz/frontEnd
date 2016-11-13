package com.xz.createType.abstractFactory;

public abstract class Factory {
	public abstract <T extends Animal> T buyone(String name);
	
}
