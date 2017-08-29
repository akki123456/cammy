package com.balloon.app;

import static com.balloon.utility.PropertyUtility.dndisUnit;
import static com.balloon.utility.PropertyUtility.dntempUnit;
import static com.balloon.utility.PropertyUtility.drThreads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.balloon.model.DistanceUnit;
import com.balloon.model.TempUnit;
import com.balloon.task.dataRollup.NormalizeDataFromQueueAndWritetoQueue;
import com.balloon.task.dataRollup.ReadFilesIntoQueue;
import com.balloon.task.dataRollup.WriteFilefromQueue;

public class DataNormalizer {

	public static boolean flag = false;
	
	public static void main(String args[]) throws InterruptedException{
    	BlockingQueue<String> readQueue = new ArrayBlockingQueue<>(10000);
    	BlockingQueue<String> writeQueue = new ArrayBlockingQueue<>(10000);
	        ExecutorService executorService = Executors.newFixedThreadPool(drThreads);
	    executorService.submit(new ReadFilesIntoQueue(readQueue));  
	    Thread.currentThread().sleep(5*1000);
	    
	    CountDownLatch cdl = new CountDownLatch(drThreads -2);
	    for (int i = 0; i <drThreads -2; i++) {
	    	executorService.submit(
	    			new NormalizeDataFromQueueAndWritetoQueue(readQueue,
	    					writeQueue, TempUnit.valueOf(dntempUnit),
	    					DistanceUnit.valueOf(dndisUnit), cdl));
        }
	    
	    executorService.submit(new WriteFilefromQueue(writeQueue));
	    try{
	    	cdl.await();
	    }catch(InterruptedException ie){
	    	
	    }
	    flag=true;
	    executorService.shutdown();
	    
	}
}
