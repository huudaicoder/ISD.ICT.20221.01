package test;

import com.sun.scenario.effect.Reflection;

import entities.Bike;
import entities.NormalBike;
import exceptions.ecobike.InvalidEcoBikeInformationException;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			NormalBike b = new NormalBike("a", "b", "c", "d", "e", 10, "f", 10, "g");
			System.out.println(b.getClass());
			System.out.println(Test.class);
//			Class clzz = Class.forName("NormalBike");
//			System.out.println(clzz.getClass().toString());
			Reflections ref = new Reflections(Bike.class);
		} catch (InvalidEcoBikeInformationException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
