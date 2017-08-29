package com.balloon.task.dataRollup;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.balloon.app.DataRollup;
import com.balloon.model.Observatory;


public class ParseDataFromQueue implements Callable<ParsedParams>{

	private final BlockingQueue<String> queue;
	
	public ParseDataFromQueue( BlockingQueue<String> queue){
		this.queue=queue;
	}
	
	
	@Override
	public ParsedParams call() throws Exception {
		 String line;
		 System.out.println("Starting...");
		 ParsedParams  pp = new ParsedParams();
	        while(!(queue.isEmpty()&&DataRollup.readCompleted)) {
	            try {
	                line = queue.poll(5, TimeUnit.SECONDS);
	                //System.out.println(line);
	                if(line!=null){
	                	try{
	                	String[] values = line.split("\\|");
	                	//System.out.println(values.length);
	                	if(values.length==4){
	                		//System.out.println(line);
	                		String obs = values[3].toUpperCase();
	                		String[] dis = values[1].split(",");
	                		long x = Long.parseLong(dis[0]);
	                		long y = Long.parseLong(dis[1]);
	                		Observatory ob = Observatory.valueOf(values[3]);
	                		if(ob==null) ob=Observatory.All;
	                		double temp = ob.tempUnit.toCelsius
	                				(Double.parseDouble(values[2]));
	                		if(temp>pp.maxTempInCelcius)
	                			pp.maxTempInCelcius=temp;
	                		if(temp<pp.minTempInCelcius)
	                			pp.minTempInCelcius=temp;
	                		
	                		pp.sumTempInCelcius=temp+pp.sumTempInCelcius;
	                		addCountToObs(pp.obser2count, obs);
	                		replaceMaxDistance(pp.distance, obs, x, y);
	                		pp.totalObsRead = pp.totalObsRead+1;
	                		
	                	}
	                	}catch(Exception e){
	                		e.printStackTrace();
	                	}
	                }
	                
	                
	                
	            } catch (InterruptedException ex) {
	                break; 
	            }
	        }
	     
	        
	        return pp;
	}
	
	void addCountToObs(final Map<String,Integer> m,String obs){
		Integer count = m.get(obs);
		if(count==null){
			count = 0;
		}
		count=count+1;
		m.put(obs, count);
	}
	
	void replaceMaxDistance(final Map<String,Long> m,String obs,long x ,long y){
		Long maxDistance = m.get(obs);
		if(maxDistance==null){
			maxDistance = 0L;
		}
		long  currentDistance = x*x +y*y;
		if(currentDistance>maxDistance){
		m.put(obs, currentDistance);
//		System.out.println("replaced data");
		}
	}
	

	 
}
