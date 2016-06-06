
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

import com.acetecsemi.godzilla.Godzilla.application.core.SpartPartApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/SpartPart")
public class SpartPartController {
		
	@Inject
	private SpartPartApplication spartPartApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(SpartPartDTO spartPartDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		spartPartApplication.saveSpartPart(spartPartDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(SpartPartDTO spartPartDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		spartPartApplication.updateSpartPart(spartPartDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(SpartPartDTO spartPartDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<SpartPartDTO> all = spartPartApplication.pageQuerySpartPart(spartPartDTO, page, pagesize);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/pageTotal")
	public  Map<String, Object> pageTotal(SpartPartDTO spartPartDTO)
	{
		Map<String, Object> result = spartPartApplication.pageQuerySpartPartTotal(spartPartDTO);
		return result;
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
        spartPartApplication.removeSpartParts(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", spartPartApplication.getSpartPart(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserBySpartPart/{id}")
	public Map<String, Object> findCreateUserBySpartPart(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", spartPartApplication.findCreateUserBySpartPart(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserBySpartPart/{id}")
	public Map<String, Object> findLastModifyUserBySpartPart(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", spartPartApplication.findLastModifyUserBySpartPart(id));
		return result;
	}


	@ResponseBody
	@RequestMapping("/findProductsBySpartPart/{id}")
	public Page findProductsBySpartPart(@PathVariable Long id, @RequestParam int page, @RequestParam int pagesize) {
		Page<ProductDTO> all = spartPartApplication.findProductsBySpartPart(id, page, pagesize);
		return all;
	}		

	@ResponseBody
	@RequestMapping("/findStationsBySpartPart/{id}")
	public Page findStationsBySpartPart(@PathVariable Long id, @RequestParam int page, @RequestParam int pagesize) {
		Page<StationDTO> all = spartPartApplication.findStationsBySpartPart(id, page, pagesize);
		return all;
	}		
	@ResponseBody
	@RequestMapping("/findResLotTypeBySpartPart/{id}")
	public Map<String, Object> findResLotTypeBySpartPart(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", spartPartApplication.findResLotTypeBySpartPart(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findVenderBySpartPart/{id}")
	public Map<String, Object> findVenderBySpartPart(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", spartPartApplication.findVenderBySpartPart(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findDefineStationProcessBySpartPart/{id}")
	public Map<String, Object> findDefineStationProcessBySpartPart(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", spartPartApplication.findDefineStationProcessBySpartPart(id));
		return result;
	}

	
}
