package com.acetecsemi.godzilla.Godzilla.application.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.commons.beanutils.Converter;

/**
* 重写日期转换
*
* @author harlow
*/
public class DateConvert implements Converter {

   public Object convert(Class arg0, Object arg1) {
	   if (arg1 == null) {
           return null;
       }else{
    	   return arg1;
       }

   }

}