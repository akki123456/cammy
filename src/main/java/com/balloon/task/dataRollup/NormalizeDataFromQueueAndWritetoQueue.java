package com.balloon.task.dataRollup;


import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.balloon.app.DataRollup;
import com.balloon.model.DistanceUnit;
import com.balloon.model.Observatory;
import com.balloon.model.TempUnit;


public class NormalizeDataFromQueueAndWritetoQueue implements Runnable{

	private final BlockingQueue<String> readQueue;
	private final BlockingQueue<String> writeQueue;
	private final TempUnit tempUnit;
	private final DistanceUnit disUnit;
	private final CountDownLatch cdl;
	public NormalizeDataFromQueueAndWritetoQueue( BlockingQueue<String> readQueue,
			BlockingQueue<String> writeQueue,TempUnit tempUnit, DistanceUnit disUnit
			,CountDownLatch cdl){
		this.readQueue=readQueue;
		this.writeQueue=writeQueue;
		this.tempUnit = tempUnit;
		this.disUnit = disUnit;
		this.cdl = cdl;
	}
	
	
	@Override
	public void run(){
		 String line;
		 System.out.println("Starting...");
		 
	        while(!(readQueue.isEmpty()&&DataRollup.readCompleted)) {
	            try {
	                line = readQueue.poll(5, TimeUnit.SECONDS);
	                if(line!=null){
	                	try{
	                	String[] values = line.split("\\|");
	                	if(values.length==4){
	                		String obs = values[3].toUpperCase();
	                		
	                		Observatory ob = Observatory.valueOf(values[3]);
	                		if(ob==null) ob=Observatory.All;
	                		double temp = getTempInReqUnit(ob,
	                				Double.parseDouble(values[2]));
	                				
	                		String[] dis = values[1].split(",");
	                		long x = Long.parseLong(dis[0]);
	                		long y = Long.parseLong(dis[1]);
	                		double xr = getDisInReqUnit(ob, x);
	                		double yr = getDisInReqUnit(ob, y);
	                		
	                		StringBuilder sb = new StringBuilder();
	                	sb.append(values[0]).append("|").append(xr).append(",").append(yr)
	       				 .append("|").append(temp).append("|").append(values[3]);
	       			
	       				 writeQueue.put(sb.toString());
	                	}
	                	}catch(Exception e){
	                		e.printStackTrace();
	                	}
	                }
	                
	                
	                
	            } catch (InterruptedException ex) {
	                break; 
	            }
	        }
	        cdl.countDown();
	        
	}

	
	double getDisInReqUnit(Observatory ob , long sourceValue){
		switch(disUnit){
		case KM:
			return ob.distanceUnit.toKM(sourceValue);
		case METRE:
			return ob.distanceUnit.toMeter(sourceValue);
		case MILES:
			return ob.distanceUnit.toMiles(sourceValue);
		}
		return -1;
	}

	
	double getTempInReqUnit(Observatory ob , double sourceValue){
		switch(tempUnit){
		case CELSIUS:
			return ob.tempUnit.toCelsius(sourceValue);
		case FAHRENHEIT:
			return ob.tempUnit.toFahrenheit(sourceValue);
		case KELVIN:
			return ob.tempUnit.toKelvin(sourceValue);
		}
		return -1;
	}
	

	 
}

