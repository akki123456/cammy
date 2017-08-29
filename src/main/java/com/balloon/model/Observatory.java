package com.balloon.model;

import java.util.Random;

public enum Observatory {
	
	AU(TempUnit.CELSIUS, DistanceUnit.KM),
	FR(TempUnit.KELVIN,DistanceUnit.METRE),
	US(TempUnit.FAHRENHEIT,DistanceUnit.MILES),
	All(TempUnit.KELVIN,DistanceUnit.KM);
	
	public TempUnit tempUnit;
	public DistanceUnit distanceUnit;
	
	private Observatory(TempUnit tempUnit,DistanceUnit distanceUnit) {
	 this.tempUnit = tempUnit;
	 this.distanceUnit = distanceUnit;
	}
	
	  public static Observatory getRandomObservatory() {
          Random random = new Random();
          return values()[random.nextInt(values().length)];
      }
}
