
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
import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureDebitProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/ManufactureDebitProcess")
public class ManufactureDebitProcessController {
		
	@Inject
	private ManufactureDebitProcessApplication manufactureDebitProcessApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(ManufactureDebitProcessDTO manufactureDebitProcessDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		manufactureDebitProcessApplication.saveManufactureDebitProcess(manufactureDebitProcessDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(ManufactureDebitProcessDTO manufactureDebitProcessDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		manufactureDebitProcessApplication.updateManufactureDebitProcess(manufactureDebitProcessDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(ManufactureDebitProcessDTO manufactureDebitProcessDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<ManufactureDebitProcessDTO> all = manufactureDebitProcessApplication.pageQueryManufactureDebitProcess(manufactureDebitProcessDTO, page, pagesize);
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
        manufactureDebitProcessApplication.removeManufactureDebitProcesss(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureDebitProcessApplication.getManufactureDebitProcess(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByManufactureDebitProcess/{id}")
	public Map<String, Object> findCreateUserByManufactureDebitProcess(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureDebitProcessApplication.findCreateUserByManufactureDebitProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByManufactureDebitProcess/{id}")
	public Map<String, Object> findLastModifyUserByManufactureDebitProcess(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureDebitProcessApplication.findLastModifyUserByManufactureDebitProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findManufactureProcessByManufactureDebitProcess/{id}")
	public Map<String, Object> findManufactureProcessByManufactureDebitProcess(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureDebitProcessApplication.findManufactureProcessByManufactureDebitProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findMaterialProcessByManufactureDebitProcess/{id}")
	public Map<String, Object> findMaterialProcessByManufactureDebitProcess(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureDebitProcessApplication.findMaterialProcessByManufactureDebitProcess(id));
		return result;
	}

	
}
