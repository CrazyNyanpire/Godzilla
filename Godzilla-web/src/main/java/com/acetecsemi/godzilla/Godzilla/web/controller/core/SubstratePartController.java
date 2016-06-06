
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

import com.acetecsemi.godzilla.Godzilla.application.core.SubstratePartApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/SubstratePart")
public class SubstratePartController {
		
	@Inject
	private SubstratePartApplication substratePartApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(SubstratePartDTO substratePartDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		substratePartApplication.saveSubstratePart(substratePartDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(SubstratePartDTO substratePartDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		substratePartApplication.updateSubstratePart(substratePartDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(SubstratePartDTO substratePartDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<SubstratePartDTO> all = substratePartApplication.pageQuerySubstratePart(substratePartDTO, page, pagesize);
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
        substratePartApplication.removeSubstrateParts(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substratePartApplication.getSubstratePart(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserBySubstratePart/{id}")
	public Map<String, Object> findCreateUserBySubstratePart(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substratePartApplication.findCreateUserBySubstratePart(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserBySubstratePart/{id}")
	public Map<String, Object> findLastModifyUserBySubstratePart(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substratePartApplication.findLastModifyUserBySubstratePart(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/all")
	public Map<String, Object> findAllParts() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<SubstratePartDTO> list = substratePartApplication.findAllSubstratePart();
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else
			result.put("result", "fail");
		return result;
	}
	
}
