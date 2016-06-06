
package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.core.BomListItemApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/BomListItem")
public class BomListItemController {
		
	@Inject
	private BomListItemApplication bomListItemApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(BomListItemDTO bomListItemDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		bomListItemApplication.saveBomListItem(bomListItemDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(BomListItemDTO bomListItemDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		bomListItemApplication.updateBomListItem(bomListItemDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(BomListItemDTO bomListItemDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<BomListItemDTO> all = bomListItemApplication.pageQueryBomListItem(bomListItemDTO, page, pagesize);
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
        bomListItemApplication.removeBomListItems(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", bomListItemApplication.getBomListItem(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByBomListItem/{id}")
	public Map<String, Object> findCreateUserByBomListItem(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", bomListItemApplication.findCreateUserByBomListItem(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByBomListItem/{id}")
	public Map<String, Object> findLastModifyUserByBomListItem(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", bomListItemApplication.findLastModifyUserByBomListItem(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findMaterialByBomListItem/{id}")
	public Map<String, Object> findMaterialByBomListItem(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", bomListItemApplication.findMaterialByBomListItem(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findBomListByBomListItem/{id}")
	public Map<String, Object> findBomListByBomListItem(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", bomListItemApplication.findBomListByBomListItem(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findBomListItemByManufactureProcessAndMaterial/{id}")
	public Map<String, Object> findBomListItemByManufactureProcessAndMaterial(
			@PathVariable Long id, @RequestParam Long materialId){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", bomListItemApplication.findBomListItemByManufactureProcessAndMaterial(id, materialId));
		return result;
	}
}
