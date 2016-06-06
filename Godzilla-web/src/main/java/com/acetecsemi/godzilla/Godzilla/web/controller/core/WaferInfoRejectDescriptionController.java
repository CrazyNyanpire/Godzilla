
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
import com.acetecsemi.godzilla.Godzilla.application.core.WaferInfoRejectDescriptionApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/WaferInfoRejectDescription")
public class WaferInfoRejectDescriptionController {
		
	@Inject
	private WaferInfoRejectDescriptionApplication waferInfoRejectDescriptionApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(WaferInfoRejectDescriptionDTO waferInfoRejectDescriptionDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		waferInfoRejectDescriptionApplication.saveWaferInfoRejectDescription(waferInfoRejectDescriptionDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(WaferInfoRejectDescriptionDTO waferInfoRejectDescriptionDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		waferInfoRejectDescriptionApplication.updateWaferInfoRejectDescription(waferInfoRejectDescriptionDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(WaferInfoRejectDescriptionDTO waferInfoRejectDescriptionDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<WaferInfoRejectDescriptionDTO> all = waferInfoRejectDescriptionApplication.pageQueryWaferInfoRejectDescription(waferInfoRejectDescriptionDTO, page, pagesize);
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
        waferInfoRejectDescriptionApplication.removeWaferInfoRejectDescriptions(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferInfoRejectDescriptionApplication.getWaferInfoRejectDescription(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findWaferProcessByWaferInfoRejectDescription/{id}")
	public Map<String, Object> findWaferProcessByWaferInfoRejectDescription(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferInfoRejectDescriptionApplication.findWaferProcessByWaferInfoRejectDescription(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findWaferInfoByWaferInfoRejectDescription/{id}")
	public Map<String, Object> findWaferInfoByWaferInfoRejectDescription(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferInfoRejectDescriptionApplication.findWaferInfoByWaferInfoRejectDescription(id));
		return result;
	}

	
}
