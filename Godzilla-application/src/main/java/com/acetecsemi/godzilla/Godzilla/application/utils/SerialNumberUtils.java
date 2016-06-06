package com.acetecsemi.godzilla.Godzilla.application.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class SerialNumberUtils {

	public static String sn(int len,Integer i,String driver){
		String dr = "";
		AtomicInteger z = new AtomicInteger(i);
		z.getAndIncrement();
		if(z.toString().length()>(len-(driver!=null?driver.length():0))){
			dr = driverCheck(driver,len);
			if(dr.equals(".N")){//如超出限定长度并字母都为Z的时候，限定长度加1，dr重新开始，默认为空
				len++;
				dr = "";
			}else{
				z.set(1);
			}
		}else{
			dr = driver;
		}
		
		if(dr.length()==len){
			return dr;
		}else{
			//System.out.println(dr+getNumberStr(len,z.intValue()));
			//System.out.println(String.format("%0"+(len-dr.length())+"d", z.intValue())+dr);
		}
		return dr+getNumberStr(len-driver.length(),z.intValue());
	}

	/**
	 * 字母有效检查
	 * 1.检查字母是否都为Z
	 * 2.检查字母长度
	 * @param driver
	 * @param len
	 * @return
	 */
	public static String driverCheck(String driver,int len){
		char[] charArray = driver.toCharArray();
		AtomicInteger z = new AtomicInteger(0);
		
		for (char c : charArray) {
			if(c=='Z'){
				z.getAndIncrement();
			}
		}

		if(z.intValue()==driver.length() && z.intValue()==len){//如所有字母都为Z，并且长度达到限定长度，返回.N
			return ".N";
		}else if(z.intValue()==driver.length() && z.intValue()<len){//如果所有字母都为Z，但长度未达到限定长度，则在调用字母递增方法之前加入@用以递增A
			return driver("@"+driver);
		}else{//以上两个条件都不满足，则直接递增
			return driver(driver);
		}
		
	}
	
	/**
	 * 字母递增
	 * @param driver
	 * @return
	 */
	public static String driver(String driver){
		if(driver!=null && driver.length()>0){
			char[] charArray = driver.toCharArray();
			AtomicInteger z = new AtomicInteger(0);
			for(int i = charArray.length-1;i>-1;i--){
				if(charArray[i]=='Z'){
					charArray[i]='0';
					z.set(z.incrementAndGet());
				}else if(charArray[i]=='9'){
					charArray[i]=(char)(charArray[i] + 8);
				}else{
					if(z.intValue()>0 || i==charArray.length-1){
						AtomicInteger atomic = new AtomicInteger(charArray[i]);
						charArray[i]=(char) atomic.incrementAndGet();
						z.set(0);
					}
				}
			}
			
			return String.valueOf(charArray);
		}else{
			return "A";
		}
	}
	
	public static String getNumberStr(int len , Integer no){
		String numberStr = "0";
		for(int i = 0 ; i < len ; i ++){
			numberStr += "0";
		}
		String ns = numberStr + no.toString();
		ns = ns.substring(ns.length()-len);
		return ns;
	}
	
	public static void main(String[] args) {
		System.out.println(sn(6, 0, "A"));
		System.out.println(sn(6, 99999, ""));
		System.out.println(sn(4, Integer.parseInt("14380002".substring(4)), ""));
		String str = "123456789".substring(0,1);
		System.out.println(str);
		System.out.println(MyDateUtils.getYYWW());
	}
}