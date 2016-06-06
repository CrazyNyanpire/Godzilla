
package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.acetecsemi.godzilla.Godzilla.application.core.LotSummaryManufactureApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.LotSummaryMaterialApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.LotSummarySubstrateApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.LotSummaryWaferApplication;

@Controller
@RequestMapping("/LotSummary")
public class LotSummaryController {
		
	@Inject
	private LotSummaryWaferApplication lotSummaryWaferApplication;
	
	@Inject
	private LotSummaryManufactureApplication lotSummaryManufactureApplication;
	
	@Inject
	private LotSummarySubstrateApplication lotSummarySubstrateApplication;
	
	@Inject
	private LotSummaryMaterialApplication lotSummaryMaterialApplication;
	
	@ResponseBody
	@RequestMapping("/Wafer/getLotSummary/{id}")
	public Map<String, Object> getWaferLotSummary(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", this.changeToJSONObject(lotSummaryWaferApplication.getLotSummary(id)));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Wafer/getHold/{id}")
	public Map<String, Object> getWaferLotHold(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", lotSummaryWaferApplication.getLotHold(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Wafer/getMerge/{id}")
	public Map<String, Object> getWaferLotMerge(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", lotSummaryWaferApplication.getLotMerge(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Wafer/getSplit/{id}")
	public Map<String, Object> getWaferLotSplit(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", lotSummaryWaferApplication.getLotSplit(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Wafer/getReject/{id}")
	public Map<String, Object> getWaferLotReject(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", lotSummaryWaferApplication.getLotReject(id));
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/Manufacture/getLotSummary/{id}")
	public Map<String, Object> getManufactureLotSummary(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", this.changeToJSONObject(lotSummaryManufactureApplication.getLotSummary(id)));
		//result.put("data", lotSummaryManufactureApplication.getLotSummary(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Manufacture/getHold/{id}")
	public Map<String, Object> getManufactureLotHold(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", lotSummaryManufactureApplication.getLotHold(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Manufacture/getMerge/{id}")
	public Map<String, Object> getManufactureLotMerge(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", lotSummaryManufactureApplication.getLotMerge(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Manufacture/getSplit/{id}")
	public Map<String, Object> getManufactureLotSplit(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", lotSummaryManufactureApplication.getLotSplit(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Manufacture/getReject/{id}")
	public Map<String, Object> getManufactureLotReject(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", lotSummaryManufactureApplication.getLotReject(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Manufacture/getDebit/{id}")
	public Map<String, Object> getManufactureLotDebit(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", lotSummaryManufactureApplication.getLotDebit(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Substrate/getLotSummary/{id}")
	public Map<String, Object> getSubstrateLotSummary(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", this.changeToJSONObject(lotSummarySubstrateApplication.getLotSummary(id)));
		//result.put("data", lotSummarySubstrateApplication.getLotSummary(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Substrate/getHold/{id}")
	public Map<String, Object> getSubstrateLotHold(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", lotSummarySubstrateApplication.getLotHold(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Substrate/getMerge/{id}")
	public Map<String, Object> getSubstrateLotMerge(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", lotSummarySubstrateApplication.getLotMerge(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Substrate/getSplit/{id}")
	public Map<String, Object> getSubstrateLotSplit(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", lotSummarySubstrateApplication.getLotSplit(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Substrate/getReject/{id}")
	public Map<String, Object> getSubstrateLotReject(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", lotSummarySubstrateApplication.getLotReject(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Material/getLotSummary/{id}")
	public Map<String, Object> getMaterialLotSummary(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", this.changeToJSONObject(lotSummaryMaterialApplication.getLotSummary(id)));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Material/getHold/{id}")
	public Map<String, Object> getMaterialLotHold(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", lotSummaryMaterialApplication.getLotHold(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Material/getMerge/{id}")
	public Map<String, Object> getMaterialLotMerge(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", lotSummaryMaterialApplication.getLotMerge(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Material/getSplit/{id}")
	public Map<String, Object> getMaterialLotSplit(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", lotSummaryMaterialApplication.getLotSplit(id));
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
