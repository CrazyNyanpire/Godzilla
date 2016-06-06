
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
import com.acetecsemi.godzilla.Godzilla.application.core.WaferStatusOptLogApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/WaferStatusOptLog")
public class WaferStatusOptLogController {
		
	@Inject
	private WaferStatusOptLogApplication waferStatusOptLogApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(WaferStatusOptLogDTO waferStatusOptLogDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		waferStatusOptLogApplication.saveWaferStatusOptLog(waferStatusOptLogDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(WaferStatusOptLogDTO waferStatusOptLogDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		waferStatusOptLogApplication.updateWaferStatusOptLog(waferStatusOptLogDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(WaferStatusOptLogDTO waferStatusOptLogDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<WaferStatusOptLogDTO> all = waferStatusOptLogApplication.pageQueryWaferStatusOptLog(waferStatusOptLogDTO, page, pagesize);
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
        waferStatusOptLogApplication.removeWaferStatusOptLogs(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferStatusOptLogApplication.getWaferStatusOptLog(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByWaferStatusOptLog/{id}")
	public Map<String, Object> findCreateUserByWaferStatusOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferStatusOptLogApplication.findCreateUserByWaferStatusOptLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByWaferStatusOptLog/{id}")
	public Map<String, Object> findLastModifyUserByWaferStatusOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferStatusOptLogApplication.findLastModifyUserByWaferStatusOptLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findOptLogByWaferStatusOptLog/{id}")
	public Map<String, Object> findOptLogByWaferStatusOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferStatusOptLogApplication.findOptLogByWaferStatusOptLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findWaferProcessByWaferStatusOptLog/{id}")
	public Map<String, Object> findWaferProcessByWaferStatusOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferStatusOptLogApplication.findWaferStatusOptLogByWaferProcess(id));
		return result;
	}

	
}
