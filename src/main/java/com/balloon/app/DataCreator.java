package com.balloon.app;

import static com.balloon.utility.PropertyUtility.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import com.balloon.model.Observatory;

public class DataCreator {
		
	public static void main(String args[]) throws IOException{
		File fileDir = new File(dcFilePath);
		fileDir.mkdirs();
		ExecutorService executorService = Executors.newFixedThreadPool(dcThreads);
		for(int i=0;i<dcNumberofFiles;i++)
		executorService.execute(new Task());
		executorService.shutdown();
	}
	
	
	static class Task implements Runnable{

		@Override
		public void run() {
			String path = dcFilePath + File.separator+ System.currentTimeMillis()+ThreadLocalRandom.current().nextInt(10);
			FileWriter fw;
			try {
				fw = new FileWriter(path);
			BufferedWriter bw = new BufferedWriter(fw, 4194304);
			for(long i=0; i<dcRecordsPerFile;i++){
				StringBuilder sb = new StringBuilder();
				
				 String df = dateFormat.format(
						 new Date(ThreadLocalRandom.current().nextLong(rangeStart, rangeEnd)));
				 int fc = ThreadLocalRandom.current().nextInt(0, rangeDistance);
				 int sc = ThreadLocalRandom.current().nextInt(0, rangeDistance);
				 
				 int temp = ThreadLocalRandom.current().nextInt(rangeTempMin,rangeTempMax);
				 
				 Observatory ob = Observatory.getRandomObservatory();
				 sb.append(df).append("|").append(fc).append(",").append(sc)
				 .append("|").append(temp).append("|").append(ob.name());
				 sb.append("\n");
				 bw.write(sb.toString());
			}
			bw.close();
			fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
}
