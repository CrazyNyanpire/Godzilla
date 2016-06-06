
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

import com.acetecsemi.godzilla.Godzilla.application.core.BomListApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.StaticValue;

@Controller
@RequestMapping("/BomList")
public class BomListController {
		
	@Inject
	private BomListApplication bomListApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(BomListDTO bomListDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		bomListApplication.saveBomList(bomListDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(BomListDTO bomListDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		bomListApplication.updateBomList(bomListDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(BomListDTO bomListDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<BomListDTO> all = bomListApplication.pageQueryBomList(bomListDTO, page, pagesize);
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
        bomListApplication.removeBomLists(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", bomListApplication.getBomList(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/findCreateUserByBomList/{id}")
	public Map<String, Object> findCreateUserByBomList(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", bomListApplication.findCreateUserByBomList(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByBomList/{id}")
	public Map<String, Object> findLastModifyUserByBomList(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", bomListApplication.findLastModifyUserByBomList(id));
		return result;
	}


	@ResponseBody
	@RequestMapping("/findBomListItemsByBomList/{id}")
	public Page findBomListItemsByBomList(@PathVariable Long id, @RequestParam int page, @RequestParam int pagesize) {
		Page<BomListItemDTO> all = bomListApplication.findBomListItemsByBomList(id, page, pagesize);
		return all;
	}		
	
	@ResponseBody
	@RequestMapping("/findBomListByManufactureProcess/{id}")
	public Map<String, Object> findBomListByManufactureProcess(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		BomListDTO bomListDTO= bomListApplication.findBomListByProcess(id,StaticValue.BOMLIST_TYPE_ASSEMBLY);
		Map<String, List<BomListItemDTO>> map = bomListApplication.findBomListItemsByBomList(bomListDTO.getId(),false);
		result.put("productName", bomListDTO.getProductName());
		result.put("type", StaticValue.BOMLIST_TYPE_ASSEMBLY);
		result.put("bomList", map);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/findBomListByWaferProcess/{id}")
	public Map<String, Object> findBomListByWaferProcess(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		BomListDTO bomListDTO= bomListApplication.findBomListByProcess(id,StaticValue.BOMLIST_TYPE_PREASSY);
		Map<String, List<BomListItemDTO>> map = bomListApplication.findBomListItemsByBomList(bomListDTO.getId(),false);
		result.put("productName", bomListDTO.getProductName());
		result.put("type", StaticValue.BOMLIST_TYPE_PREASSY);
		result.put("bomList", map);
		return result;
	}	
}
