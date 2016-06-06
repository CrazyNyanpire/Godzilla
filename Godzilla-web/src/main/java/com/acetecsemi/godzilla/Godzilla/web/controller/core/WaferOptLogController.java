
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
import com.acetecsemi.godzilla.Godzilla.application.core.WaferOptLogApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/WaferOptLog")
public class WaferOptLogController {
		
	@Inject
	private WaferOptLogApplication waferOptLogApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(WaferOptLogDTO waferOptLogDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		waferOptLogApplication.saveWaferOptLog(waferOptLogDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(WaferOptLogDTO waferOptLogDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		waferOptLogApplication.updateWaferOptLog(waferOptLogDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(WaferOptLogDTO waferOptLogDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<WaferOptLogDTO> all = waferOptLogApplication.pageQueryWaferOptLog(waferOptLogDTO, page, pagesize);
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
        waferOptLogApplication.removeWaferOptLogs(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferOptLogApplication.getWaferOptLog(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByWaferOptLog/{id}")
	public Map<String, Object> findCreateUserByWaferOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferOptLogApplication.findCreateUserByWaferOptLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByWaferOptLog/{id}")
	public Map<String, Object> findLastModifyUserByWaferOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferOptLogApplication.findLastModifyUserByWaferOptLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findOptLogByWaferOptLog/{id}")
	public Map<String, Object> findOptLogByWaferOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferOptLogApplication.findOptLogByWaferOptLog(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findWaferProcessByWaferOptLog/{id}")
	public Map<String, Object> findWaferProcessByWaferOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferOptLogApplication.findWaferProcessByWaferOptLog(id));
		return result;
	}

	
}
