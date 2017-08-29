package com.balloon.task.dataRollup;

import java.util.HashMap;
import java.util.Map;

public class ParsedParams {

	public double minTempInCelcius=0;
	public double maxTempInCelcius=0;
	public double sumTempInCelcius=0;
	public double meanTempInCelcius=0;
	public int totalObsRead=0;
	public Map<String, Integer> obser2count = new HashMap<String,Integer>();
	public Map<String,Long> distance = new HashMap<String,Long>();
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
				sb.append("mintemp=").append(minTempInCelcius).append(";")
				.append("maxTempInCelcius").append(maxTempInCelcius).append(";")
				.append("meanTempInCelcius").append(meanTempInCelcius).append(";")
				.append("ObservatorySpecificCount").append(obser2count).append(";")
				.append("MaximumDistanceTravelledPerObservatory").append(distance).append(";");
		return sb.toString();
	}
}
