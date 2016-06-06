package com.acetecsemi.godzilla.Godzilla.application.utils;

import java.util.HashMap;
import java.util.Map;

public class StaticValue {
	public static String NOT_SHOW_STATUS = "'Split','Merge','Finish','RTV'";
	public static String NOT_SHOW_STATUS_CUSTOMER = "'Received'";
	
	public static Map<Long,String> getWaferDebitStation() {
		Map<Long,String> stationMap =  new HashMap<Long,String>();
		stationMap.put((long)503, "上片1");
		stationMap.put((long)506, "上片1");
		stationMap.put((long)508, "上片1");
		stationMap.put((long)301, "基板烘烤");
		return stationMap;
	}
	
	public static String BOMLIST_TYPE_PREASSY = "PREASSY";
	
	public static String BOMLIST_TYPE_ASSEMBLY = "ASSEMBLY";
	
	public static String BOMLIST_TYPE_SUBSTRATE = "SUBSTRATE";
	
	public static Map<Integer,Long> getMaterialTypeDefineStationProcess() {
		Map<Integer,Long> materialTypeDefineStationProcessMap =  new HashMap<Integer,Long>();
		materialTypeDefineStationProcessMap.put(1, (long)3);
		materialTypeDefineStationProcessMap.put(2, (long)4);
		materialTypeDefineStationProcessMap.put(3, (long)9);
		materialTypeDefineStationProcessMap.put(4, (long)10);
		return materialTypeDefineStationProcessMap;
	}
}
