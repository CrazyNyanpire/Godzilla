
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

import com.acetecsemi.godzilla.Godzilla.application.core.RunCardTemplateApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.StaticValue;

@Controller
@RequestMapping("/RunCardTemplate")
public class RunCardTemplateController {
		
	@Inject
	private RunCardTemplateApplication runCardTemplateApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(RunCardTemplateDTO runCardTemplateDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		runCardTemplateApplication.saveRunCardTemplate(runCardTemplateDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(RunCardTemplateDTO runCardTemplateDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		runCardTemplateApplication.updateRunCardTemplate(runCardTemplateDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(RunCardTemplateDTO runCardTemplateDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<RunCardTemplateDTO> all = runCardTemplateApplication.pageQueryRunCardTemplate(runCardTemplateDTO, page, pagesize);
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
        runCardTemplateApplication.removeRunCardTemplates(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", runCardTemplateApplication.getRunCardTemplate(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/findPreassyRunCardByWaferProcessId/{id}")
	public Map<String, Object> findPreassyRunCardByWaferProcessId(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",runCardTemplateApplication.findRunCardByProcessAndType(id, StaticValue.BOMLIST_TYPE_PREASSY));
		return result;
	}	
	
	@ResponseBody
	@RequestMapping("/findAssemblyRunCardByManufactureProcessId/{id}")
	public Map<String, Object> findAssemblyRunCardByManufactureProcessId(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",runCardTemplateApplication.findRunCardByProcessAndType(id, StaticValue.BOMLIST_TYPE_ASSEMBLY));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/findAssemblyRunCardBySubstrateProcessId/{id}")
	public Map<String, Object> findAssemblyRunCardBySubstrateProcessId(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",runCardTemplateApplication.findRunCardByProcessAndType(id, StaticValue.BOMLIST_TYPE_SUBSTRATE));
		return result;
	}
}
