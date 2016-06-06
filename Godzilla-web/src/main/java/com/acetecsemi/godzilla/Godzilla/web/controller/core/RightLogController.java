
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
import com.acetecsemi.godzilla.Godzilla.application.core.RightLogApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/RightLog")
public class RightLogController {
		
	@Inject
	private RightLogApplication rightLogApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(RightLogDTO rightLogDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		rightLogApplication.saveRightLog(rightLogDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(RightLogDTO rightLogDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		rightLogApplication.updateRightLog(rightLogDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(RightLogDTO rightLogDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<RightLogDTO> all = rightLogApplication.pageQueryRightLog(rightLogDTO, page, pagesize);
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
        rightLogApplication.removeRightLogs(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", rightLogApplication.getRightLog(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByRightLog/{id}")
	public Map<String, Object> findCreateUserByRightLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", rightLogApplication.findCreateUserByRightLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByRightLog/{id}")
	public Map<String, Object> findLastModifyUserByRightLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", rightLogApplication.findLastModifyUserByRightLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findResourceByRightLog/{id}")
	public Map<String, Object> findResourceByRightLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", rightLogApplication.findResourceByRightLog(id));
		return result;
	}

	
}
