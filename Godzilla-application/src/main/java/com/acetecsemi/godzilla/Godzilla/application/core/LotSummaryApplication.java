package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import java.util.Map;

public interface LotSummaryApplication {

	public List<Map.Entry<String, List<Map<String, Object>>>> getLotSummary(Long id);
	
	public List<Map<String ,Object>> getLotHold(Long id);

	public List<Map<String, Object>> getLotMerge(Long id);

	public List<Map<String, Object>> getLotSplit(Long id);
	
	public List<Map<String, Object>> getLotReject(Long id);

}
