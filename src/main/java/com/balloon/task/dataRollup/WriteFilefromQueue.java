package com.balloon.task.dataRollup;

import static com.balloon.utility.PropertyUtility.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.balloon.app.DataNormalizer;
import com.balloon.app.DataRollup;

public class WriteFilefromQueue implements Runnable{
	private final BlockingQueue<String> queue;
	
	
	public WriteFilefromQueue( BlockingQueue<String> queue){
		this.queue=queue;
	}

	@Override
	public void run() {
		try {
			FileWriter fw = new FileWriter(dnFilePathouput+File.separator+"NormalizedOutput.file");
			while(!(queue.isEmpty()&&DataNormalizer.flag)){
				String line = queue.poll(5, TimeUnit.SECONDS);
				fw.write(line);
				fw.write("\n");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}