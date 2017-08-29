package com.balloon.model;

public enum DistanceUnit {
	 KM,METRE,MILES;
	   public double toKM(final double sourceMeasure)
	   {
	      double km = 0;
	      switch (this)
	      {
	         case KM:
	            km = sourceMeasure;
	            break;
	         case METRE:
	            km = sourceMeasure/1000;
	            break;
	         case MILES:
	            km = sourceMeasure*1.609;
	            break;
	      }
	      return km;
	   }

	   public double toMiles(final double sourceMeasure)
	   {
	      double miles = 0;
	      switch (this)
	      {
	         case KM:
	            miles = sourceMeasure/1.609;
	            break;
	         case METRE:
	            miles = sourceMeasure/1609;
	            break;
	         case MILES:
	            miles = sourceMeasure;
	            break;
	      }
	      return miles;
	   }
	   public double toMeter(final double sourceMeasure)
	   {
	      double meter = 0;
	      switch (this)
	      {
	         case KM:
	        	 meter = sourceMeasure*1000;
	            break;
	         case METRE:
	        	 meter = sourceMeasure;
	            break;
	         case MILES:
	        	 meter = sourceMeasure*1.609*1000;
	            break;
	      }
	      return meter;
	   }
	
	
}
