# cammy
The solution consists of three main classes as demanded by the problem set.
All the programs share the same properties file named as 'config.properties' located in the resource directory.
Default values of properties can be viewed in com.balloon.utility.PropertyUtility. To overrride use the config.properties
1) com.balloon.app.DataCreator  
      This class can be used to generate test data. You can specify number of files to be generated with number of records each file can have.
      As we can generate multiple files we can leverage multi threading .
      Number of threads can be configured via properties to get better performance based on the server config.
 2) com.balloon.app.DataRollup
      This class prints the different values requested on the console. Total number of threads is configurable.
      One dedicated thread is used to read the all the files and put string lines into a blocking queue. 
      Other threads poll the blockingqueue and parse the data . 
      Finally the main thread collects the mapped data from the threads and reduce to return the final result
3)  com.balloon.app.DataNormalizer
    This class normalizes the  values and writes on an output file . Total number of threads is configurable.
      One dedicated thread is used to read the all the files and put string lines into a blocking queue. 
      Other threads poll the blockingqueue and parse the data and put into other blockingqueue. 
      One write thread reads from the second queue and writes into a file.
     
