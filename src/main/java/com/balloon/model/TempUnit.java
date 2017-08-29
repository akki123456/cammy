package com.balloon.model;

import java.math.BigDecimal;

public enum TempUnit
{
   CELSIUS( "C"),
   FAHRENHEIT("F"),
   KELVIN("K");

   private String units;



   TempUnit(final String newUnits){
      this.units = newUnits;
   }


   public String getUnits()
   {
      return this.units;
   }


   public double toFahrenheit(final double sourceMeasure)
   {
      double fahrenheit = 0;
      switch (this)
      {
         case CELSIUS:
            fahrenheit = (sourceMeasure*9)/5 + 32;
            break;
         case FAHRENHEIT:
            fahrenheit = sourceMeasure;
            break;
         case KELVIN:
             fahrenheit = (sourceMeasure*9)/5 - 459.67;
            break;
      }
      return fahrenheit;
   }

   public double toCelsius(final double sourceMeasure)
   {
      double celsius = 0;
      switch (this)
      {
         case CELSIUS:
            celsius = sourceMeasure;
            break;
         case FAHRENHEIT:
            celsius = ((sourceMeasure -32)*5)/9;
            break;
         case KELVIN:
            celsius = sourceMeasure - 273.15 ;
            break;
      }
      return celsius;
   }

   public double toKelvin(final double sourceMeasure)
   {
      double kelvin = 0;
      switch (this)
      {
         case CELSIUS:
            kelvin = sourceMeasure + 273.15;
            break;
         case FAHRENHEIT:
            kelvin = ((sourceMeasure+459.67)*5)/9;
            break;
         case KELVIN:
            kelvin = sourceMeasure;
            break;
      }
      return kelvin;
   }

}
