
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
import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureStatusOptLogApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/ManufactureStatusOptLog")
public class ManufactureStatusOptLogController {
		
	@Inject
	private ManufactureStatusOptLogApplication manufactureStatusOptLogApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(ManufactureStatusOptLogDTO manufactureStatusOptLogDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		manufactureStatusOptLogApplication.saveManufactureStatusOptLog(manufactureStatusOptLogDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(ManufactureStatusOptLogDTO manufactureStatusOptLogDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		manufactureStatusOptLogApplication.updateManufactureStatusOptLog(manufactureStatusOptLogDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(ManufactureStatusOptLogDTO manufactureStatusOptLogDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<ManufactureStatusOptLogDTO> all = manufactureStatusOptLogApplication.pageQueryManufactureStatusOptLog(manufactureStatusOptLogDTO, page, pagesize);
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
        manufactureStatusOptLogApplication.removeManufactureStatusOptLogs(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureStatusOptLogApplication.getManufactureStatusOptLog(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByManufactureStatusOptLog/{id}")
	public Map<String, Object> findCreateUserByManufactureStatusOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureStatusOptLogApplication.findCreateUserByManufactureStatusOptLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByManufactureStatusOptLog/{id}")
	public Map<String, Object> findLastModifyUserByManufactureStatusOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureStatusOptLogApplication.findLastModifyUserByManufactureStatusOptLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findOptLogByManufactureStatusOptLog/{id}")
	public Map<String, Object> findOptLogByManufactureStatusOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureStatusOptLogApplication.findOptLogByManufactureStatusOptLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findManufactureProcessByManufactureStatusOptLog/{id}")
	public Map<String, Object> findManufactureProcessByManufactureStatusOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureStatusOptLogApplication.findManufactureProcessByManufactureStatusOptLog(id));
		return result;
	}

	
}
