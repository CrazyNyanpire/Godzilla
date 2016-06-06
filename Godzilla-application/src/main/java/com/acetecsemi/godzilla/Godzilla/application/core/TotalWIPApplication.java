package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import java.util.Map;

public interface TotalWIPApplication {

	public static final String WAFER = "GODZILLA_PROCESS";
	public static final String MANUFACTURE = "GODZILLA_MANUFACTUREPROCESS";
	public static final String MATERIAL = "GODZILLA_MATERIALPROCESS";
	
	public List<Map.Entry<String, List<Map<String, Object>>>> getNowTotalWIP(Map<String, Object> maps,String table);
}
