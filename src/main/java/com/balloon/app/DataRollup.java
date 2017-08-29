package com.balloon.app;

import static com.balloon.utility.PropertyUtility.drThreads;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.balloon.task.dataRollup.ParseDataFromQueue;
import com.balloon.task.dataRollup.ParsedParams;
import com.balloon.task.dataRollup.ReadFilesIntoQueue;
public class DataRollup {

	public static boolean readCompleted=false;
    
    public static void main(String args[]) throws InterruptedException{
    	BlockingQueue<String> queue = new ArrayBlockingQueue<>(10000);
	        ExecutorService executorService = Executors.newFixedThreadPool(drThreads);
	    executorService.submit(new ReadFilesIntoQueue(queue));  
	    Thread.currentThread().sleep(10*1000);
	    
	    List<Future<ParsedParams>> futures = new ArrayList<Future<ParsedParams>>();
	    for (int i = 0; i <drThreads -1; i++) {
	    	futures.add(executorService.submit(new ParseDataFromQueue(queue)));
        }
	    List<ParsedParams> ppList = new ArrayList<ParsedParams>();
	    for(Future<ParsedParams> future: futures){
	    	ParsedParams pp=null;
			try {
				pp = future.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(pp!=null)
	    	ppList.add(pp);
	    }
	    ParsedParams result = reduceResults(ppList); 
	    System.out.println(result);
	    executorService.shutdown();
	    
	}
	
    static ParsedParams reduceResults(List<ParsedParams> ppList){
    	ParsedParams finalResult = new ParsedParams();
    	for(ParsedParams pp : ppList){
    		
    		if(finalResult.maxTempInCelcius<pp.maxTempInCelcius)
    			finalResult.maxTempInCelcius =pp.maxTempInCelcius;
    		if(pp.minTempInCelcius<finalResult.minTempInCelcius)
    			finalResult.minTempInCelcius=pp.minTempInCelcius;
    		
    		finalResult.sumTempInCelcius=finalResult.sumTempInCelcius+pp.sumTempInCelcius;
    		reduceObSpecificCount(finalResult.obser2count,pp.obser2count);
    		reduceMaxDistance(finalResult.distance,pp.distance);
    		finalResult.totalObsRead = finalResult.totalObsRead +pp.totalObsRead;
    	}
    	
    	finalResult.meanTempInCelcius = finalResult.sumTempInCelcius/finalResult.totalObsRead;
    	return finalResult;
    	
    }
    
	static void reduceObSpecificCount(final Map<String,Integer> finalm,final Map<String,Integer> currentM){
		for(String obs : currentM.keySet()){
			Integer count = finalm.get(obs);
			if(count==null){
				count = 0;
			}
			count=count+currentM.get(obs);
			finalm.put(obs, count);
		}
	}
	
	static void reduceMaxDistance(final Map<String,Long> finalm,
			final Map<String,Long> currentM){
		for(String obs : currentM.keySet()){
		Long maxDistance = finalm.get(obs);
		if(maxDistance==null){
			maxDistance = 0L;
		}
		long  currentDistance =currentM.get(obs);
		if(currentDistance>maxDistance)
			finalm.put(obs, currentDistance);
	}
}
}