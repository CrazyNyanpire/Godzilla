
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
import com.acetecsemi.godzilla.Godzilla.application.core.SubstrateStatusOptLogApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/SubstrateStatusOptLog")
public class SubstrateStatusOptLogController {
		
	@Inject
	private SubstrateStatusOptLogApplication substrateStatusOptLogApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(SubstrateStatusOptLogDTO substrateStatusOptLogDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		substrateStatusOptLogApplication.saveSubstrateStatusOptLog(substrateStatusOptLogDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(SubstrateStatusOptLogDTO substrateStatusOptLogDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		substrateStatusOptLogApplication.updateSubstrateStatusOptLog(substrateStatusOptLogDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(SubstrateStatusOptLogDTO substrateStatusOptLogDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<SubstrateStatusOptLogDTO> all = substrateStatusOptLogApplication.pageQuerySubstrateStatusOptLog(substrateStatusOptLogDTO, page, pagesize);
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
        substrateStatusOptLogApplication.removeSubstrateStatusOptLogs(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateStatusOptLogApplication.getSubstrateStatusOptLog(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserBySubstrateStatusOptLog/{id}")
	public Map<String, Object> findCreateUserBySubstrateStatusOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateStatusOptLogApplication.findCreateUserBySubstrateStatusOptLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserBySubstrateStatusOptLog/{id}")
	public Map<String, Object> findLastModifyUserBySubstrateStatusOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateStatusOptLogApplication.findLastModifyUserBySubstrateStatusOptLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findOptLogBySubstrateStatusOptLog/{id}")
	public Map<String, Object> findOptLogBySubstrateStatusOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateStatusOptLogApplication.findOptLogBySubstrateStatusOptLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findSubstrateProcessBySubstrateStatusOptLog/{id}")
	public Map<String, Object> findSubstrateProcessBySubstrateStatusOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateStatusOptLogApplication.findSubstrateStatusOptLogBySubstrateProcess(id));
		return result;
	}

	
}
