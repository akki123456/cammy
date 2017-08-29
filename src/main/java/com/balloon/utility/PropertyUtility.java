package com.balloon.utility;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class PropertyUtility {

	public static Properties prop =null;
	
	/*
	 * DataCreate properties
	 */
	public static final long rangeStart= Long.parseLong(getProperty
			("datacreator.rangeInMs.start",(System.currentTimeMillis()-10*24*3600*1000)+""));
	public static final long rangeEnd= Long.parseLong(getProperty
			("datacreator.rangeInMs.end",(System.currentTimeMillis()+10*24*3600*1000)+""));
	public static final DateFormat dateFormat = new SimpleDateFormat(getProperty("datacreator.dateFormat",
			"yyyy-MM-dd'T'hh:mm"));
	public static final int rangeDistance = Integer.parseInt(getProperty("datecreator.rangeDistance","2000"));
	public static final int rangeTempMin = Integer.parseInt(getProperty("datecreator.rangeTemp.min","-500"));
	public static final int rangeTempMax = Integer.parseInt(getProperty("datecreator.rangeTemp.max","500"));

	public static final String dcFilePath = getProperty("datacreator.filePath.dir", "C:\\workSpace");
	public static final int dcThreads = Integer.parseInt(getProperty("datacreator.fileWrite.threads", "4"));
	public static final int dcRecordsPerFile = Integer.parseInt(getProperty("datacreator.fileWrite.recordsPerFile", "10000000"));
	public static final int dcNumberofFiles = Integer.parseInt(getProperty("datacreator.fileWrite.numberOfFiles", "50"));
	
	/*
	 * DataRollup properties
	 */
	
	public static final String drFilePath = getProperty("dataRollup.filePath.dir", "C:\\workSpace\\newer");
	public static final int drThreads = Integer.parseInt(getProperty("dataRollup.fileRead.threads", "4"));
	
	public static final String dnFilePathouput = getProperty("dataNormalize.filePath.output.dir", "C:\\workSpace\\output");
	public static final String dntempUnit = getProperty("dataNormalize.tempUnit", "CELSIUS");
	public static final String dndisUnit = getProperty("dataNormalize.distanceUnit", "KM");
	
	
	
	

	private static String getProperty(String key,String defaultValue){
		if (prop==null) loadProperties();
		return prop.getProperty(key, defaultValue);
	}
	
	private static Properties loadProperties() {
        prop = new Properties();
        InputStream in;
		try {
			in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("config.properties");
            prop.load(in);
            in.close();
        } catch (IOException e) {
        }
		return prop;
    }
	
	
	
}
