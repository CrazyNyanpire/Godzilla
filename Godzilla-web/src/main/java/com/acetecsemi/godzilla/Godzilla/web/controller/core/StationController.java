
package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.core.StationApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/Station")
public class StationController {
		
	@Inject
	private StationApplication stationApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(StationDTO stationDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		stationApplication.saveStation(stationDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(StationDTO stationDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		stationApplication.updateStation(stationDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(StationDTO stationDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<StationDTO> all = stationApplication.pageQueryStation(stationDTO, page, pagesize);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i ++) {
        	
        	        					idArrs[i] = Long.parseLong(value[i]);
						        	
        }
        stationApplication.removeStations(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", stationApplication.getStation(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByStation/{id}")
	public Map<String, Object> findCreateUserByStation(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", stationApplication.findCreateUserByStation(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByStation/{id}")
	public Map<String, Object> findLastModifyUserByStation(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", stationApplication.findLastModifyUserByStation(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findEquipments/{id}")
	public Map<String, Object> findEquipmentsByStation(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<EquipmentDTO> list = stationApplication.findEquipmentsByStation(id);
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else
			result.put("result", "fail");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/findConsumeStation")
	public Map<String, Object> findConsumeStation() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<StationDTO> list = stationApplication.findConsumeStation();
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else
			result.put("result", "fail");
		return result;
	}
}
