
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
import com.acetecsemi.godzilla.Godzilla.application.core.MaterialStatusOptLogApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/MaterialStatusOptLog")
public class MaterialStatusOptLogController {
		
	@Inject
	private MaterialStatusOptLogApplication materialStatusOptLogApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(MaterialStatusOptLogDTO materialStatusOptLogDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		materialStatusOptLogApplication.saveMaterialStatusOptLog(materialStatusOptLogDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(MaterialStatusOptLogDTO materialStatusOptLogDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		materialStatusOptLogApplication.updateMaterialStatusOptLog(materialStatusOptLogDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(MaterialStatusOptLogDTO materialStatusOptLogDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<MaterialStatusOptLogDTO> all = materialStatusOptLogApplication.pageQueryMaterialStatusOptLog(materialStatusOptLogDTO, page, pagesize);
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
        materialStatusOptLogApplication.removeMaterialStatusOptLogs(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialStatusOptLogApplication.getMaterialStatusOptLog(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByMaterialStatusOptLog/{id}")
	public Map<String, Object> findCreateUserByMaterialStatusOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialStatusOptLogApplication.findCreateUserByMaterialStatusOptLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByMaterialStatusOptLog/{id}")
	public Map<String, Object> findLastModifyUserByMaterialStatusOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialStatusOptLogApplication.findLastModifyUserByMaterialStatusOptLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findOptLogByMaterialStatusOptLog/{id}")
	public Map<String, Object> findOptLogByMaterialStatusOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialStatusOptLogApplication.findOptLogByMaterialStatusOptLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findMaterialProcessByMaterialStatusOptLog/{id}")
	public Map<String, Object> findMaterialProcessByMaterialStatusOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialStatusOptLogApplication.findMaterialProcessByMaterialStatusOptLog(id));
		return result;
	}

	
}
