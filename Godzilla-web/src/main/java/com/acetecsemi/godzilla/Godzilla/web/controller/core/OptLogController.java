
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
import com.acetecsemi.godzilla.Godzilla.application.core.OptLogApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/OptLog")
public class OptLogController {
		
	@Inject
	private OptLogApplication optLogApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(OptLogDTO optLogDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		optLogApplication.saveOptLog(optLogDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(OptLogDTO optLogDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		optLogApplication.updateOptLog(optLogDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(OptLogDTO optLogDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<OptLogDTO> all = optLogApplication.pageQueryOptLog(optLogDTO, page, pagesize);
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
        optLogApplication.removeOptLogs(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", optLogApplication.getOptLog(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByOptLog/{id}")
	public Map<String, Object> findCreateUserByOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", optLogApplication.findCreateUserByOptLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByOptLog/{id}")
	public Map<String, Object> findLastModifyUserByOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", optLogApplication.findLastModifyUserByOptLog(id));
		return result;
	}

	
}
