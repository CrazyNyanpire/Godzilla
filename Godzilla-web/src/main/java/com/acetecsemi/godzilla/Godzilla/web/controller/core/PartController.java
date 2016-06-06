
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

import com.acetecsemi.godzilla.Godzilla.application.core.PartApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/Part")
public class PartController {
		
	@Inject
	private PartApplication partApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(PartDTO partDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		partApplication.savePart(partDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(PartDTO partDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		partApplication.updatePart(partDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(PartDTO partDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<PartDTO> all = partApplication.pageQueryPart(partDTO, page, pagesize);
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
        partApplication.removeParts(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", partApplication.getPart(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByPart/{id}")
	public Map<String, Object> findCreateUserByPart(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", partApplication.findCreateUserByPart(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByPart/{id}")
	public Map<String, Object> findLastModifyUserByPart(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", partApplication.findLastModifyUserByPart(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/all")
	public Map<String, Object> findAllParts() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<PartDTO> list = partApplication.findAllPart();
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else
			result.put("result", "fail");
		return result;
	}
}
