package com.xz.createType.abstractFactory;

public class SmallAnimalFactory extends Factory {

	@Override
	public <T extends Animal> T buyone(String name) {
		String holeName = "com.xz.createType.abstractFactory."+name.substring(0,1).toUpperCase()+name.substring(1) ;
		T t = null ;
		try {
			@SuppressWarnings("unchecked")
			Class<T> cz = (Class<T>) Class.forName(holeName) ;
			t = (T)cz.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return t;
	}

}
