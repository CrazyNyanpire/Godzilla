
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

import com.acetecsemi.godzilla.Godzilla.application.core.EquipmentApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/Equipment")
public class EquipmentController {
		
	@Inject
	private EquipmentApplication equipmentApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(EquipmentDTO equipmentDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		equipmentApplication.saveEquipment(equipmentDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(EquipmentDTO equipmentDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		equipmentApplication.updateEquipment(equipmentDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(EquipmentDTO equipmentDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<EquipmentDTO> all = equipmentApplication.pageQueryEquipment(equipmentDTO, page, pagesize);
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
        equipmentApplication.removeEquipments(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", equipmentApplication.getEquipment(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByEquipment/{id}")
	public Map<String, Object> findCreateUserByEquipment(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", equipmentApplication.findCreateUserByEquipment(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByEquipment/{id}")
	public Map<String, Object> findLastModifyUserByEquipment(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", equipmentApplication.findLastModifyUserByEquipment(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findWaferProcessByEquipment/{id}")
	public Map<String, Object> findWaferProcessByEquipment(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", equipmentApplication.findWaferProcessByEquipment(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findStationByEquipment/{id}")
	public Map<String, Object> findStationByEquipment(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", equipmentApplication.findStationByEquipment(id));
		return result;
	}
	
}
