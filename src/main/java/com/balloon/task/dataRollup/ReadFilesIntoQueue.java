package com.balloon.task.dataRollup;

import static com.balloon.utility.PropertyUtility.drFilePath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import com.balloon.app.DataRollup;

public class ReadFilesIntoQueue implements Runnable{
	private final BlockingQueue<String> queue;
	
	
	public ReadFilesIntoQueue( BlockingQueue<String> queue){
		this.queue=queue;
	}

	@Override
	public void run() {
		File file = new File(drFilePath);
    	for (final File fileEntry : file.listFiles()) {
    		System.out.println("Reading file .."+ fileEntry.getAbsolutePath());
            if (fileEntry.isFile()) {
                BufferedReader br = null;
                try {
                br = new BufferedReader(new FileReader(fileEntry));
                String line;
                while ((line = br.readLine()) != null) {
                    queue.put(line);
            //        System.out.println(line);
                }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    	DataRollup.readCompleted=true;
		
	}
	
	
}