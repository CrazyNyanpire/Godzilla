
package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.acetecsemi.godzilla.Godzilla.application.core.LotSummaryManufactureApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.LotSummaryMaterialApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.LotSummarySubstrateApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.LotSummaryWaferApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.TotalWIPApplication;

@Controller
@RequestMapping("/TotalWIP")
public class TotalWIPController {
		
	@Inject
	private TotalWIPApplication totalWIPApplication;
	
	
	@ResponseBody
	@RequestMapping("/Wafer/getNowTotalWIP")
	public Map<String, Object> getWaferNowTotalWIP(@RequestParam String queryData) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> maps = null;
		if(!"".equals(queryData))
			maps = mapper.readValue(queryData, Map.class);
		result.put("data", this.changeToJSONObject(totalWIPApplication.getNowTotalWIP(maps,TotalWIPApplication.WAFER)));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Manufacture/getNowTotalWIP")
	public Map<String, Object> getManufactureNowTotalWIP(@RequestParam String queryData) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> maps = null;
		if(!"".equals(queryData))
			maps = mapper.readValue(queryData, Map.class);
		result.put("data", this.changeToJSONObject(totalWIPApplication.getNowTotalWIP(maps,TotalWIPApplication.MANUFACTURE)));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Material/getNowTotalWIP")
	public Map<String, Object> getMaterialNowTotalWIP(@RequestParam String queryData) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> maps = null;
		if(!"".equals(queryData))
			maps = mapper.readValue(queryData, Map.class);
		result.put("data", this.changeToJSONObject(totalWIPApplication.getNowTotalWIP(maps,TotalWIPApplication.MATERIAL)));
		return result;
	}
	
	private JSONObject changeToJSONObject( List<Map.Entry<String, List<Map<String, Object>>>> listMapEntry){
		JSONObject jsonObject = new JSONObject();
		for(Map.Entry<String, List<Map<String, Object>>> mapEntry : listMapEntry){
			jsonObject.put(mapEntry.getKey(), mapEntry.getValue());
		}
		return jsonObject;
	}
	
}
